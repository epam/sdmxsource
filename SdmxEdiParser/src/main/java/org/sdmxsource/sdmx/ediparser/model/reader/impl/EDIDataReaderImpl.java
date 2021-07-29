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
package org.sdmxsource.sdmx.ediparser.model.reader.impl;

import org.sdmxsource.sdmx.api.constants.DATASET_ACTION;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.model.header.DatasetHeaderBean;
import org.sdmxsource.sdmx.api.model.header.DatasetStructureReferenceBean;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.ediparser.model.document.EDIDocumentPosition;
import org.sdmxsource.sdmx.ediparser.model.document.EDIMetadata;
import org.sdmxsource.sdmx.ediparser.model.reader.EDIDataReader;
import org.sdmxsource.sdmx.sdmxbeans.model.header.DatasetHeaderBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.header.DatasetStructureReferenceBeanImpl;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;

import java.util.List;


/**
 * The type Edi data reader.
 */
public class EDIDataReaderImpl extends EDIAbstractPositionalReaderImpl implements EDIDataReader {

    private EDIDocumentPosition documentPosition;

    /**
     * Instantiates a new Edi data reader.
     *
     * @param dataFile         the data file
     * @param documentPosition the document position
     * @param ediMetadata      the edi metadata
     */
    public EDIDataReaderImpl(ReadableDataLocation dataFile,
                             EDIDocumentPosition documentPosition,
                             EDIMetadata ediMetadata) {
        super(dataFile, documentPosition, ediMetadata);
        this.documentPosition = documentPosition;
    }

    @Override
    public DatasetHeaderBean getDatasetHeaderBean() {
        String datasetId = documentPosition.getDatasetId();
        DATASET_ACTION datasetAction = documentPosition.getDatasetAction();
        String dsdId = documentPosition.getDataStructureIdentifier();

        StructureReferenceBean dsdRef = new StructureReferenceBeanImpl(getMessageAgency(), dsdId, MaintainableBean.DEFAULT_VERSION, SDMX_STRUCTURE_TYPE.DSD);

        DatasetStructureReferenceBean structureReference = new DatasetStructureReferenceBeanImpl(dsdRef);

        return new DatasetHeaderBeanImpl(datasetId, datasetAction, structureReference);
    }


    @Override
    public EDIDataReader createCopy() {
        return new EDIDataReaderImpl(dataFile, documentPosition, getEdiDocumentMetadata());
    }

    @Override
    public List<KeyValue> getDatasetAttributes() {
        return documentPosition.getDatasetAttributes();
    }

    @Override
    public String getMissingValue() {
        return documentPosition.getMissingValue();
    }

    @Override
    public void close() {
        super.close();
        dataFile.close();
    }
}
