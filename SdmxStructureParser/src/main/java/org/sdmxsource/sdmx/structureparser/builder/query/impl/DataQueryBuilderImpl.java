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
package org.sdmxsource.sdmx.structureparser.builder.query.impl;

import org.sdmx.resources.sdmxml.schemas.v20.query.DataWhereType;
import org.sdmx.resources.sdmxml.schemas.v20.query.QueryType;
import org.sdmx.resources.sdmxml.schemas.v21.query.DataQueryType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.data.query.DataQuery;
import org.sdmxsource.sdmx.api.model.data.query.complex.ComplexDataQuery;
import org.sdmxsource.sdmx.structureparser.builder.query.DataQueryBuilder;
import org.sdmxsource.sdmx.structureparser.builder.query.v2.DataQueryBuilderV2;
import org.sdmxsource.sdmx.structureparser.builder.query.v21.DataQueryBuilderV21;

import java.util.List;


/**
 * The type Data query builder.
 */
public class DataQueryBuilderImpl implements DataQueryBuilder {

    private final DataQueryBuilderV2 dataQueryBuilderV2 = new DataQueryBuilderV2();

    private final DataQueryBuilderV21 dataQueryBuilderV21 = new DataQueryBuilderV21();


    @Override
    public DataQuery buildDataQuery(org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.query.DataWhereType dataWhereType, SdmxBeanRetrievalManager structureRetrievalManager) {
        //FUNC Support 1.0 data query
        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "DataQueryBuilderImpl.buildDataQuery (Version 1.0 SDMX)");
    }

    @Override
    public List<DataQuery> buildDataQuery(org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.query.QueryType queryType, SdmxBeanRetrievalManager structureRetrievalManager) {
        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "DataQueryBuilderImpl.buildDataQuery (Version 1.0 SDMX)");
    }


    @Override
    public DataQuery buildDataQuery(DataWhereType dataWhereType, SdmxBeanRetrievalManager structureRetrievalManager) {
        return dataQueryBuilderV2.buildDataQuery(dataWhereType, structureRetrievalManager);
    }


    @Override
    public List<DataQuery> buildDataQuery(QueryType queryType, SdmxBeanRetrievalManager structureRetrievalManager) {
        return dataQueryBuilderV2.buildDataQuery(queryType, structureRetrievalManager);
    }

    @Override
    public List<ComplexDataQuery> buildComplexDataQuery(DataQueryType dataQueryType, SdmxBeanRetrievalManager structureRetrievalManager) {
        return dataQueryBuilderV21.buildComplexDataQuery(dataQueryType, structureRetrievalManager);
    }

}
