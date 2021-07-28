/*******************************************************************************
 * Copyright (c) 2013 Metadata Technology Ltd.
 *
 * All rights reserved. This program and the accompanying materials are made 
 * available under the terms of the GNU Lesser General Public License v 3.0 
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 *
 * This file is part of the SDMX Component Library.
 *
 * The SDMX Component Library is free software: you can redistribute it and/or 
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * The SDMX Component Library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with The SDMX Component Library If not, see 
 * http://www.gnu.org/licenses/lgpl.
 *
 * Contributors:
 * Metadata Technology - initial API and implementation
 ******************************************************************************/
package org.sdmxsource.sdmx.filequeryprocessor.engine;

import org.sdmxsource.sdmx.api.constants.DATASET_ACTION;
import org.sdmxsource.sdmx.api.constants.DATA_QUERY_DETAIL;
import org.sdmxsource.sdmx.api.engine.DataReaderEngine;
import org.sdmxsource.sdmx.api.engine.DataWriterEngine;
import org.sdmxsource.sdmx.api.manager.retrieval.data.SdmxDataRetrievalWithWriter;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.data.Keyable;
import org.sdmxsource.sdmx.api.model.data.query.DataQuery;
import org.sdmxsource.sdmx.api.model.data.query.DataQuerySelection;
import org.sdmxsource.sdmx.api.model.data.query.DataQuerySelectionGroup;
import org.sdmxsource.sdmx.api.model.header.DatasetHeaderBean;
import org.sdmxsource.sdmx.api.model.header.DatasetStructureReferenceBean;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.dataparser.manager.DataReaderManager;
import org.sdmxsource.sdmx.dataparser.transform.DataReaderWriterTransform;
import org.sdmxsource.sdmx.sdmxbeans.model.header.DatasetHeaderBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.header.DatasetStructureReferenceBeanImpl;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * The type File query engine.
 */
public class FileQueryEngine implements SdmxDataRetrievalWithWriter {
    private final ReadableDataLocation dataLocation;

    private final DataReaderManager dataReadManager;

    private final DataReaderWriterTransform dataReaderWriterTransform;


    /**
     * Instantiates a new File query engine.
     *
     * @param dataReadManager           the data read manager
     * @param dataReaderWriterTransform the data reader writer transform
     * @param dataLocation              the data location
     */
    public FileQueryEngine(
            final DataReaderManager dataReadManager,
            final DataReaderWriterTransform dataReaderWriterTransform,
            final ReadableDataLocation dataLocation) {
        if (dataLocation == null) {
            throw new IllegalArgumentException("FileQueryEngine dataLocation can not be null");
        }
        this.dataReadManager = dataReadManager;
        this.dataReaderWriterTransform = dataReaderWriterTransform;
        this.dataLocation = dataLocation;
    }

    @Override
    public void getData(DataQuery dataQuery, DataWriterEngine dataWriterEngine) {
        try {
            DataStructureBean dataStructureBean = dataQuery.getDataStructure();

            DataReaderEngine dataReaderEngine = dataReadManager.getDataReaderEngine(dataLocation, dataStructureBean, dataQuery.getDataflow());
            DATASET_ACTION action = DATASET_ACTION.INFORMATION;
            if (dataReaderEngine.getHeader() != null && dataReaderEngine.getHeader().getAction() != null) {
                action = dataReaderEngine.getHeader().getAction();
            }
            DatasetStructureReferenceBean dsRef;
            if (dataQuery.getDataflow() != null) {
                dsRef = new DatasetStructureReferenceBeanImpl(dataQuery.getDataflow().asReference());
            } else {
                dsRef = new DatasetStructureReferenceBeanImpl(dataStructureBean.asReference());
            }


            DatasetHeaderBean dsHeader = new DatasetHeaderBeanImpl(UUID.randomUUID().toString(), action, dsRef);
            dataWriterEngine.startDataset(null, dataQuery.getDataflow(), dataStructureBean, dsHeader);

            //FUNC not been updated to new query
            Date queryFrom = null;
            Date queryTo = null;
            if (dataQuery.hasSelections()) {
                DataQuerySelectionGroup dqSg = dataQuery.getSelectionGroups().get(0);
                if (dqSg.getDateFrom() != null) {
                    queryFrom = dqSg.getDateFrom().getDate();
                }
                if (dqSg.getDateTo() != null) {
                    queryTo = dqSg.getDateTo().getDate();
                }
            }
            boolean includeObs = dataQuery.getDataQueryDetail() != DATA_QUERY_DETAIL.SERIES_KEYS_ONLY;
            Integer maxObs = null;
            if (!includeObs) {
                maxObs = 0;
            } else {
                //TODO this should change, and so should the reader writer transform API
                maxObs = dataQuery.getLastNObservations();
                if (maxObs == null) {
                    maxObs = dataQuery.getFirstNObservations();
                }
            }
            while (dataReaderEngine.moveNextDataset()) {
                while (dataReaderEngine.moveNextKeyable()) {
                    Keyable key = dataReaderEngine.getCurrentKey();
                    if (dataQuery.hasSelections() && !seriesMatch(dataQuery, key)) {
                        continue;
                    }
                    dataReaderWriterTransform.writeKeyableToWriter(dataReaderEngine, dataWriterEngine, key, maxObs, queryFrom, queryTo);
                }
            }
        } finally {
            dataWriterEngine.close();
        }
    }

    //FUNC not been updated to new query
    private boolean seriesMatch(DataQuery dataQuery, Keyable key) {
        key.getKey();
        for (DataQuerySelectionGroup sg : dataQuery.getSelectionGroups()) {
            for (DataQuerySelection selection : sg.getSelections()) {
                Set<String> selectionValues = selection.getValues();
                String val = key.getKeyValue(selection.getComponentId());
                if (val == null) {
                    return false;  //NOT IN THE SERIES , ROLLED UP CONCEPTS DOES NOT CONTAIN THIS CONCEPT
                }
                if (!selectionValues.contains(val)) {
                    return false;
                }
            }
        }
        return true;
    }
}
