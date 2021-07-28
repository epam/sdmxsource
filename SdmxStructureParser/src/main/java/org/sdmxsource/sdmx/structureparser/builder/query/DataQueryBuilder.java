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
package org.sdmxsource.sdmx.structureparser.builder.query;

import org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.query.QueryType;
import org.sdmx.resources.sdmxml.schemas.v20.query.DataWhereType;
import org.sdmx.resources.sdmxml.schemas.v21.query.DataQueryType;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.data.query.DataQuery;
import org.sdmxsource.sdmx.api.model.data.query.complex.ComplexDataQuery;

import java.util.List;


/**
 * The interface Data query builder.
 */
public interface DataQueryBuilder {


    /**
     * Builds a data query from a dataWhereType
     *
     * @param queryType                 the query type
     * @param structureRetrievalManager optional, if supplied the retrieval manager to use to retrieve the DSD, which is stored on the query,                                  if not supplied then the DSD will not be stored on the query
     * @return list list
     */
    List<DataQuery> buildDataQuery(QueryType queryType, SdmxBeanRetrievalManager structureRetrievalManager);

    /**
     * Builds a data query from a dataWhereType
     *
     * @param queryType                 the query type
     * @param structureRetrievalManager optional, if supplied the retrieval manager to use to retrieve the DSD, which is stored on the query,                                  if not supplied then the DSD will not be stored on the query
     * @return list list
     */
    List<DataQuery> buildDataQuery(org.sdmx.resources.sdmxml.schemas.v20.query.QueryType queryType, SdmxBeanRetrievalManager structureRetrievalManager);

    /**
     * Builds a data query from a dataQueryType
     *
     * @param dataQueryType        - the XMLBean to build the domain object (DataQuery) from
     * @param beanRetrievalManager the bean retrieval manager
     * @return list list
     */
    List<ComplexDataQuery> buildComplexDataQuery(DataQueryType dataQueryType, SdmxBeanRetrievalManager beanRetrievalManager);

    /**
     * Builds a data query from a dataWhereType
     *
     * @param dataWhereType             - the XMLBean to build the domain object (DataQuery) from
     * @param structureRetrievalManager optional, if supplied the retrieval manager to use to retrieve the DSD, which is stored on the query,                                  if not supplied then the DSD will not be stored on the query
     * @return data query
     */
    DataQuery buildDataQuery(DataWhereType dataWhereType, SdmxBeanRetrievalManager structureRetrievalManager);

    /**
     * Builds a data query from a dataWhereType
     *
     * @param dataWhereType             - the XMLBean to build the domain object (DataQuery) from
     * @param structureRetrievalManager - if supplied the retrieval manager to use to retrieve the DSD, which is stored on the query,                                  if not supplied then the DSD will not be stored on the query
     * @return data query
     */
    DataQuery buildDataQuery(org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.query.DataWhereType dataWhereType, SdmxBeanRetrievalManager structureRetrievalManager);


}
