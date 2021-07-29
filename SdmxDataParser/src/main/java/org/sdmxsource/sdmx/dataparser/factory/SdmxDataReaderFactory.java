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
package org.sdmxsource.sdmx.dataparser.factory;

import org.apache.log4j.Logger;
import org.sdmxsource.sdmx.api.engine.DataReaderEngine;
import org.sdmxsource.sdmx.api.factory.DataReaderFactory;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.data.DataFormat;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.dataparser.engine.reader.CompactDataReaderEngine;
import org.sdmxsource.sdmx.dataparser.engine.reader.CrossSectionDataReaderEngine;
import org.sdmxsource.sdmx.dataparser.engine.reader.GenericDataReaderEngine;
import org.sdmxsource.sdmx.dataparser.engine.reader.csv.StreamCsvDataReaderEngine;
import org.sdmxsource.sdmx.dataparser.engine.reader.json.JsonDataReaderEngine;
import org.sdmxsource.sdmx.dataparser.manager.DataInformationManager;
import org.sdmxsource.sdmx.ediparser.manager.EdiParseManager;

import java.util.Objects;

/**
 * The SdmxChainedDataReaderFactory is used to get a DataReaderEngine from a source location.
 * <p>
 * The SdmxChainedDataReaderFactory will first check any child DataReaderFactory instances that have been provided to ensure that there are not
 * alternative DataReaderEngine for the source data. If there are no child DataReaderFactory's or any child DataReaderFactorys that return the expected DataReaderEngine
 * then the SdmxChainedDataReaderFactory will assume the data is SDMX or EDI.
 */
public class SdmxDataReaderFactory implements DataReaderFactory {
    private static Logger LOG = Logger.getLogger(SdmxDataReaderFactory.class);

    private final DataInformationManager dataInformationManager;

    private final EdiParseManager ediParseManager;

    /**
     * Instantiates a new Sdmx data reader factory.
     *
     * @param dataInformationManager the data information manager
     * @param ediParseManager        the edi parse manager
     */
    public SdmxDataReaderFactory(
            final DataInformationManager dataInformationManager,
            final EdiParseManager ediParseManager) {
        this.dataInformationManager = Objects.requireNonNull(dataInformationManager, "dataInformationManager");
        this.ediParseManager = ediParseManager;
    }

    @Override
    public DataReaderEngine getDataReaderEngine(ReadableDataLocation sourceData, DataStructureBean dsd, DataflowBean dataflowBean, ProvisionAgreementBean provisionAgreement) {
//		LOG.debug("Get Data Reader Engine");
//		MESSAGE_TYPE message; 
//		try {
//			message = SdmxMessageUtil.getMessageType(sourceData);
//		} catch(Throwable th) {
//			return null;
//		}
//		if(message == MESSAGE_TYPE.ERROR) {
//			SdmxMessageUtil.parseSdmxErrorMessage(sourceData);
//		}
//		if(!message.isData()) {
//			throw new SdmxException("Can not process message of type:" + message.getNodeName());
//		}
//		LOG.debug("No chained factories found reader for data, assume data is SDMX/EDI");
        DataFormat dataFormat;
        try {
            dataFormat = dataInformationManager.getDataType(sourceData);
        } catch (Throwable th) {
            return null;
        }
        if (dataFormat != null && dataFormat.getSdmxDataFormat() != null) {
            switch (dataFormat.getSdmxDataFormat().getBaseDataFormat()) {
                case EDI:
                    return ediParseManager.parseEDIMessage(sourceData).getDataReader(dsd, dataflowBean);
                case COMPACT:
                    return new CompactDataReaderEngine(sourceData, dsd, dataflowBean, provisionAgreement);
                case GENERIC:
                    return new GenericDataReaderEngine(sourceData, dsd, dataflowBean, provisionAgreement);
                case CROSS_SECTIONAL:
                    return new CrossSectionDataReaderEngine(sourceData, dsd, dataflowBean, provisionAgreement);
                case SDMXJSON:
                    return new JsonDataReaderEngine(sourceData, null, dsd, dataflowBean, provisionAgreement);
                case CSV:
                    return new StreamCsvDataReaderEngine(sourceData, null, dsd, dataflowBean, provisionAgreement);
                default:
                    LOG.warn("SdmxDataReaderFactory encountered unsuported SDMX format: " + dataFormat);
            }
        }
        return null;
    }

    @Override
    public DataReaderEngine getDataReaderEngine(ReadableDataLocation sourceData, SdmxBeanRetrievalManager retrievalManager) {
        DataFormat dataFormat;
        try {
            dataFormat = dataInformationManager.getDataType(sourceData);
        } catch (Throwable th) {
            return null;
        }
        if (dataFormat != null && dataFormat.getSdmxDataFormat() != null) {
            switch (dataFormat.getSdmxDataFormat().getBaseDataFormat()) {
                case EDI:
                    return ediParseManager.parseEDIMessage(sourceData).getDataReader(retrievalManager);
                case COMPACT:
                    return new CompactDataReaderEngine(sourceData, retrievalManager, null, null, null);
                case GENERIC:
                    return new GenericDataReaderEngine(sourceData, retrievalManager, null, null, null);
                case CROSS_SECTIONAL:
                    return new CrossSectionDataReaderEngine(sourceData, retrievalManager, null, null, null);
                case SDMXJSON:
                    return new JsonDataReaderEngine(sourceData, retrievalManager, null, null, null);
                case CSV:
                    return new StreamCsvDataReaderEngine(sourceData, null, null, null, null);
                default:
                    LOG.warn("SdmxDataReaderFactory encountered unsuported SDMX format: " + dataFormat);
            }
        }
        return null;
    }
}



