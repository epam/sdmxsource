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
package org.sdmxsource.sdmx.structureparser.manager.parsing;

import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.data.query.DataQuery;
import org.sdmxsource.sdmx.api.model.data.query.complex.ComplexDataQuery;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;

import java.util.List;


/**
 * The interface Data query parse manager.
 */
public interface DataQueryParseManager {
    /**
     * Builds DataQuerys from a message that contains one or more data queries
     *
     * @param dataLocation         the data location
     * @param beanRetrievalManager optional, if given will retrieve the keyfamily bean the query is for
     * @return list list
     */
    List<DataQuery> buildDataQuery(ReadableDataLocation dataLocation, SdmxBeanRetrievalManager beanRetrievalManager);

    /**
     * Parse rest query data query.
     *
     * @param query                the query
     * @param beanRetrievalManager the bean retrieval manager
     * @return the data query
     */
    DataQuery parseRESTQuery(String query, SdmxBeanRetrievalManager beanRetrievalManager);

    /**
     * Builds Complex Data Queries for 2.1 data query messages
     *
     * @param dataLocation         the data location
     * @param beanRetrievalManager the bean retrieval manager
     * @return list list
     */
    List<ComplexDataQuery> buildComplexDataQuery(ReadableDataLocation dataLocation, SdmxBeanRetrievalManager beanRetrievalManager);

}
