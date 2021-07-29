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

import org.sdmxsource.sdmx.api.engine.DataReaderEngine;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.factory.DataReaderFactory;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.dataparser.factory.SdmxDataReaderFactory;
import org.sdmxsource.sdmx.dataparser.manager.DataReaderManager;
import org.sdmxsource.sdmx.ediparser.manager.impl.EdiParseManagerImpl;


/**
 * The type Data reader manager.
 */
public class DataReaderManagerImpl implements DataReaderManager {

    private final DataReaderFactory[] factories;

    /**
     * Instantiates a new Data reader manager.
     *
     * @param factories the factories
     */
    public DataReaderManagerImpl(final DataReaderFactory[] factories) {
        if (factories == null || factories.length == 0) {
            this.factories = new DataReaderFactory[]{new SdmxDataReaderFactory(new DataInformationManagerImpl(), new EdiParseManagerImpl())};
        } else {
            this.factories = factories;
        }
    }

    @Override
    public DataReaderEngine getDataReaderEngine(ReadableDataLocation sourceData, DataStructureBean dsd, DataflowBean dataflowBean) {
        return getDataReaderEngine(sourceData, dsd, dataflowBean, null);
    }

    @Override
    public DataReaderEngine getDataReaderEngine(ReadableDataLocation sourceData, DataStructureBean dsd, DataflowBean dataflowBean, ProvisionAgreementBean provisionAgreement) {
        for (DataReaderFactory currentFactory : getDataReaderFactories()) {
            DataReaderEngine dre = currentFactory.getDataReaderEngine(sourceData, dsd, dataflowBean, provisionAgreement);
            if (dre != null) {
                return dre;
            }
        }
        throw new SdmxNotImplementedException("Data format is either not supported, or has an invalid syntax");
    }

    @Override
    public DataReaderEngine getDataReaderEngine(ReadableDataLocation sourceData, SdmxBeanRetrievalManager retrievalManager) {
        for (DataReaderFactory currentFactory : getDataReaderFactories()) {
            DataReaderEngine dre = currentFactory.getDataReaderEngine(sourceData, retrievalManager);
            if (dre != null) {
                return dre;
            }
        }
        throw new SdmxNotImplementedException("Data format is either not supported, or has an invalid syntax");
    }

    private DataReaderFactory[] getDataReaderFactories() throws IllegalArgumentException {
        return factories;
    }
}
