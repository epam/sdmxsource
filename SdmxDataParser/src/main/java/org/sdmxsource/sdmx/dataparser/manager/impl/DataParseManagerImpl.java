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
package org.sdmxsource.sdmx.dataparser.manager.impl;

import org.apache.log4j.Logger;
import org.sdmxsource.sdmx.api.engine.DataReaderEngine;
import org.sdmxsource.sdmx.api.engine.DataWriterEngine;
import org.sdmxsource.sdmx.api.factory.WriteableDataLocationFactory;
import org.sdmxsource.sdmx.api.manager.parse.StructureParsingManager;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.data.DataFormat;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.api.util.WriteableDataLocation;
import org.sdmxsource.sdmx.dataparser.manager.DataParseManager;
import org.sdmxsource.sdmx.dataparser.manager.DataReaderManager;
import org.sdmxsource.sdmx.dataparser.manager.DataWriterManager;
import org.sdmxsource.sdmx.dataparser.transform.DataReaderWriterTransform;
import org.sdmxsource.sdmx.structureretrieval.manager.InMemoryRetrievalManager;
import org.sdmxsource.util.io.StreamUtil;
import org.sdmxsource.util.log.LoggingUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * The type Data parse manager.
 */
public class DataParseManagerImpl implements DataParseManager {
    private final Logger log = Logger.getLogger(DataParseManagerImpl.class);

    private final DataReaderManager dataReaderManager;

    private final DataWriterManager dataWriterManager;

    private final DataReaderWriterTransform dataReaderWriterTransform;

    private final StructureParsingManager structureParsingManager;

    private final WriteableDataLocationFactory writeableDataLocationFactory;

    /**
     * Instantiates a new Data parse manager.
     *
     * @param dataReaderManager            the data reader manager
     * @param dataWriterManager            the data writer manager
     * @param dataReaderWriterTransform    the data reader writer transform
     * @param structureParsingManager      the structure parsing manager
     * @param writeableDataLocationFactory the writeable data location factory
     */
    public DataParseManagerImpl(
            final DataReaderManager dataReaderManager,
            final DataWriterManager dataWriterManager,
            final DataReaderWriterTransform dataReaderWriterTransform,
            final StructureParsingManager structureParsingManager,
            final WriteableDataLocationFactory writeableDataLocationFactory) {
        this.dataReaderManager = dataReaderManager;
        this.dataWriterManager = dataWriterManager;
        this.dataReaderWriterTransform = dataReaderWriterTransform;
        this.structureParsingManager = structureParsingManager;
        this.writeableDataLocationFactory = writeableDataLocationFactory;
    }

    @Override
    public void performTransform(ReadableDataLocation sourceData, ReadableDataLocation dsdLocation, OutputStream out, DataFormat dataFormat) {
        SdmxBeans beans = structureParsingManager.parseStructures(dsdLocation).getStructureBeans(false);
        performTransform(sourceData, out, dataFormat, new InMemoryRetrievalManager(beans));
    }

    @Override
    public void performTransform(ReadableDataLocation sourceData, OutputStream out, DataFormat dataFormat, DataStructureBean dsd, DataflowBean flow) {
        LoggingUtil.debug(log, "Perform transform request");
        try {
            DataReaderEngine dre = dataReaderManager.getDataReaderEngine(sourceData, dsd, flow);
            DataWriterEngine dwe = dataWriterManager.getDataWriterEngine(dataFormat, out);
            dataReaderWriterTransform.copyToWriter(dre, dwe, true, true);
        } finally {
            StreamUtil.closeStream(out);
        }
    }

    @Override
    public List<ReadableDataLocation> performTransformAndSplit(ReadableDataLocation sourceData, ReadableDataLocation dsdLocation, DataFormat dataFormat) {
        SdmxBeans beans = structureParsingManager.parseStructures(dsdLocation).getStructureBeans(false);
        SdmxBeanRetrievalManager retrievalManager = new InMemoryRetrievalManager(beans);
        return performTransformAndSplit(sourceData, dataFormat, retrievalManager);
    }

    @Override
    public List<ReadableDataLocation> performTransformAndSplit(ReadableDataLocation sourceData, DataFormat dataFormat, SdmxBeanRetrievalManager retrievalManager) {
        DataReaderEngine dre = dataReaderManager.getDataReaderEngine(sourceData, retrievalManager);

        List<ReadableDataLocation> returnList = new ArrayList<ReadableDataLocation>();
        WriteableDataLocation tmpDataLocation;
        while (dre.moveNextDataset()) {
            tmpDataLocation = writeableDataLocationFactory.getTemporaryWriteableDataLocation();
            DataWriterEngine dwe = dataWriterManager.getDataWriterEngine(dataFormat, tmpDataLocation.getOutputStream());
            dataReaderWriterTransform.copyDatasetToWriter(dre, dwe, null, true, null, null, null, true, true);
            returnList.add(tmpDataLocation);
        }
        return returnList;
    }

    @Override
    public void performTransform(ReadableDataLocation sourceData, OutputStream out, DataFormat dataFormat, SdmxBeanRetrievalManager beanRetrievalManager) {
        LoggingUtil.debug(log, "Perform transform request");

        DataReaderEngine dre = null;
        DataWriterEngine dwe = null;
        try {
            dre = dataReaderManager.getDataReaderEngine(sourceData, beanRetrievalManager);
            dwe = dataWriterManager.getDataWriterEngine(dataFormat, out);
            dataReaderWriterTransform.copyToWriter(dre, dwe, true, true);
        } finally {
            StreamUtil.closeStream(out);
            if (dre != null) {
                dre.close();
            }
            if (dwe != null) {
                dwe.close();
            }
        }
    }

    @Override
    public ReadableDataLocation performTransform(ReadableDataLocation sourceData, DataFormat dataFormat, SdmxBeanRetrievalManager beanRetrievalManager) {
        WriteableDataLocation tmpDataLocation = writeableDataLocationFactory.getTemporaryWriteableDataLocation();
        try {
            LoggingUtil.debug(log, "URI generated to write transformed dataset to :" + tmpDataLocation.toString());
            performTransform(sourceData, tmpDataLocation.getOutputStream(), dataFormat, beanRetrievalManager);
            return tmpDataLocation;
        } catch (RuntimeException rtEx) {
            tmpDataLocation.close();
            throw rtEx;
        } finally {
            if (tmpDataLocation != null) {
                StreamUtil.closeStream(tmpDataLocation.getOutputStream());
            }
        }
    }

    @Override
    public ReadableDataLocation performTransform(ReadableDataLocation sourceData, DataFormat dataFormat, DataStructureBean dsd, DataflowBean flow) {
        OutputStream outputstream = null;
        WriteableDataLocation tmpBuffer = writeableDataLocationFactory.getTemporaryWriteableDataLocation();
        try {
            LoggingUtil.debug(log, "URI generated to write transformed dataset to :" + tmpBuffer.toString());
            outputstream = tmpBuffer.getOutputStream();
            performTransform(sourceData, outputstream, dataFormat, dsd, flow);
            return tmpBuffer;
        } catch (RuntimeException rtEx) {
            tmpBuffer.close();
            throw rtEx;
        } finally {
            if (outputstream != null) {
                try {
                    outputstream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
