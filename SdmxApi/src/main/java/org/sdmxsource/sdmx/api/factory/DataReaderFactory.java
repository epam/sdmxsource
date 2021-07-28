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
package org.sdmxsource.sdmx.api.factory;

import org.sdmxsource.sdmx.api.engine.DataReaderEngine;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;

/**
 * Creates DataReaderEngine from Source data, DataStructure (or BeanRetrievalManager used to get DataStructure(s)) and optionally some additional information (if not SDMX/EDI)
 */
public interface DataReaderFactory {

    /**
     * Obtains a DataReaderEngine that is capable of reading the data which is exposed via the ReadableDataLocation
     *
     * @param sourceData         - giving access to an InputStream of the data
     * @param dsd                - describes the data in terms of the dimensionality
     * @param dataflowBean       the dataflow bean
     * @param provisionAgreement the provision agreement
     * @return {@link DataReaderEngine} or null if the given sourceData could not be read
     * @throws IllegalArgumentException if ReadableDataLocation or DataStructureBean is null, also if additioanlInformation is not of the expected type
     */
    DataReaderEngine getDataReaderEngine(ReadableDataLocation sourceData, DataStructureBean dsd, DataflowBean dataflowBean, ProvisionAgreementBean provisionAgreement);

    /**
     * Obtains a DataReaderEngine that is capable of reading the data which is exposed via the ReadableDataLocation
     *
     * @param sourceData       - giving access to an InputStream of the data
     * @param retrievalManager - used to obtain the DataStructure(s) that describe the data
     * @return {@link DataReaderEngine} or null if the given sourceData could not be read
     * @throws IllegalArgumentException if ReadableDataLocation or DataStructureBean is null, also if additioanlInformation is not of the expected type
     */
    DataReaderEngine getDataReaderEngine(ReadableDataLocation sourceData, SdmxBeanRetrievalManager retrievalManager);
}
