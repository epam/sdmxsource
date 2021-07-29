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
package org.sdmxsource.sdmx.dataparser.rest;

import org.sdmxsource.sdmx.api.engine.DataWriterEngine;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.manager.retrieval.data.SdmxDataRetrievalWithWriter;
import org.sdmxsource.sdmx.api.manager.retrieval.rest.RestDataQueryManager;
import org.sdmxsource.sdmx.api.model.data.DataFormat;
import org.sdmxsource.sdmx.api.model.data.query.DataQuery;
import org.sdmxsource.sdmx.api.model.query.RESTDataQuery;
import org.sdmxsource.sdmx.dataparser.manager.DataWriterManager;
import org.sdmxsource.sdmx.sdmxbeans.model.data.query.DataQueryImpl;

import java.io.OutputStream;

/**
 * This class obtains a DataWriterEngine for a DataFormat, builds a DataQuery from a RESTDataQuery
 * and passes this information on to a SdmxDataRetrievalWithWriter to fulfil the query
 */
public class RestDataQueryManagerImpl implements RestDataQueryManager {


    private final DataWriterManager dataWriterManager;

    private SdmxBeanRetrievalManager beanRetrievalManager;
    private SdmxDataRetrievalWithWriter dataRetrievalWithWriter;

    /**
     * Instantiates a new Rest data query manager.
     *
     * @param dataWriterManager the data writer manager
     */
    public RestDataQueryManagerImpl(final DataWriterManager dataWriterManager) {
        this.dataWriterManager = dataWriterManager;
    }

    @Override
    /**
     * Execute the rest query, write the response out to the output stream based on the data format
     * @param dataQuery the REST dataQuery
     * @param dataFormat the required output format
     * @param out the output stream to write the output to
     */
    public void executeQuery(RESTDataQuery restDataQuery, DataFormat dataFormat, OutputStream out) {
        DataQuery dataQuery = new DataQueryImpl(restDataQuery, beanRetrievalManager);
        DataWriterEngine dataWriterEngine = dataWriterManager.getDataWriterEngine(dataFormat, out);
        dataRetrievalWithWriter.getData(dataQuery, dataWriterEngine);
    }

    /**
     * The implementation of this interface which runs the query and writes the results to the writer
     *
     * @param dataRetrievalWithWriter the data retrieval with writer
     */
    public void setDataRetrievalWithWriter(SdmxDataRetrievalWithWriter dataRetrievalWithWriter) {
        this.dataRetrievalWithWriter = dataRetrievalWithWriter;
    }

    /**
     * Sets bean retrieval manager.
     *
     * @param beanRetrievalManager the bean retrieval manager
     */
    public void setBeanRetrievalManager(SdmxBeanRetrievalManager beanRetrievalManager) {
        this.beanRetrievalManager = beanRetrievalManager;
    }
}




