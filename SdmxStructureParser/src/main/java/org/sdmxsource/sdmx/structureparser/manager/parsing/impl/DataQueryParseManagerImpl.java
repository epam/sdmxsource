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
package org.sdmxsource.sdmx.structureparser.manager.parsing.impl;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.sdmx.resources.sdmxml.schemas.v20.message.QueryMessageDocument;
import org.sdmx.resources.sdmxml.schemas.v21.message.GenericDataQueryDocument;
import org.sdmx.resources.sdmxml.schemas.v21.message.GenericTimeSeriesDataQueryDocument;
import org.sdmx.resources.sdmxml.schemas.v21.message.StructureSpecificDataQueryDocument;
import org.sdmx.resources.sdmxml.schemas.v21.message.StructureSpecificTimeSeriesDataQueryDocument;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.QUERY_MESSAGE_TYPE;
import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.exception.SdmxSyntaxException;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.data.query.DataQuery;
import org.sdmxsource.sdmx.api.model.data.query.complex.ComplexDataQuery;
import org.sdmxsource.sdmx.api.model.query.RESTDataQuery;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.reference.RESTDataQueryImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.data.query.DataQueryImpl;
import org.sdmxsource.sdmx.structureparser.builder.query.DataQueryBuilder;
import org.sdmxsource.sdmx.structureparser.builder.query.impl.DataQueryBuilderImpl;
import org.sdmxsource.sdmx.structureparser.manager.parsing.DataQueryParseManager;
import org.sdmxsource.sdmx.util.sdmx.SdmxMessageUtil;
import org.sdmxsource.springutil.xml.XMLParser;
import org.sdmxsource.util.log.LoggingUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The type Data query parse manager.
 */
public class DataQueryParseManagerImpl implements DataQueryParseManager {
    private final DataQueryBuilder dataQueryBuilder = new DataQueryBuilderImpl();
    private Logger log = Logger.getLogger(DataQueryParseManagerImpl.class);

    @Override
    public DataQuery parseRESTQuery(String query, SdmxBeanRetrievalManager beanRetrievalManager) {
        if (!query.toLowerCase().startsWith("data/")) {
            throw new SdmxSemmanticException("Expecting REST Query for Data to start with 'data/'");
        }
        String[] split = query.split("\\?");


        //Everything one split[1] this point are query parameters
        Map<String, String> queryParameters = new HashMap<String, String>();
        if (split.length > 1) {
            String[] queryParametersSplit = split[1].split("&");
            for (int i = 0; i < queryParametersSplit.length; i++) {
                String[] queryParam = queryParametersSplit[i].split("=");
                if (queryParam.length != 2) {
                    throw new SdmxSemmanticException("Missing equals '=' in query parameters '" + split[i] + "'");
                }
                queryParameters.put(queryParam[0], queryParam[1]);
            }
        }

        String queryPrefix = split[0];
        RESTDataQuery dataQuery = new RESTDataQueryImpl(queryPrefix.split("/"), queryParameters);
        return new DataQueryImpl(dataQuery, beanRetrievalManager);
    }

    @Override
    public List<DataQuery> buildDataQuery(ReadableDataLocation dataQueryLocation, SdmxBeanRetrievalManager beanRetrievalManager) {
        log.debug("DataParseManagerImpl.buildDataQuery");
        InputStream stream = null;
        try {
            SDMX_SCHEMA schemaVersion = SdmxMessageUtil.getSchemaVersion(dataQueryLocation);
            LoggingUtil.debug(log, "Schema Version Determined to be : " + schemaVersion);
            XMLParser.validateXML(dataQueryLocation, schemaVersion);

            stream = dataQueryLocation.getInputStream();

            switch (schemaVersion) {
                case VERSION_ONE:
                    org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.message.QueryMessageDocument queryV1 = org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.message.QueryMessageDocument.Factory.parse(stream);
                    return dataQueryBuilder.buildDataQuery(queryV1.getQueryMessage().getQuery(), beanRetrievalManager);
                case VERSION_TWO:
                    QueryMessageDocument queryV2 = QueryMessageDocument.Factory.parse(stream);
                    return dataQueryBuilder.buildDataQuery(queryV2.getQueryMessage().getQuery(), beanRetrievalManager);
                case VERSION_TWO_POINT_ONE:
                    throw new IllegalArgumentException("Build Data Query concerns sdmx messages of schema version 1.0 and 2.0 ");
                default:
                    throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "buildDataQuery in version " + schemaVersion);
            }
        } catch (XmlException e) {
            throw new SdmxSyntaxException(e);
        } catch (IOException e) {
            throw new SdmxException(e, "I/O error occured whilst trying to build data query from SDMX message");
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<ComplexDataQuery> buildComplexDataQuery(ReadableDataLocation dataQueryLocation, SdmxBeanRetrievalManager beanRetrievalManager) {

        log.debug("DataParseManagerImpl.buildComplexDataQuery");
        InputStream stream = null;
        try {
            SDMX_SCHEMA schemaVersion = SdmxMessageUtil.getSchemaVersion(dataQueryLocation);
            LoggingUtil.debug(log, "Schema Version Determined to be : " + schemaVersion);
            XMLParser.validateXML(dataQueryLocation, schemaVersion);

            stream = dataQueryLocation.getInputStream();

            switch (schemaVersion) {
                case VERSION_ONE:
                    throw new IllegalArgumentException("Build Complex Data Query concerns sdmx messages of schema version 2.1 ");
                case VERSION_TWO:
                    throw new IllegalArgumentException("Build Complex Data Query concerns sdmx messages of schema version 2.1 ");
                case VERSION_TWO_POINT_ONE:
                    List<QUERY_MESSAGE_TYPE> queryMessageTypes = SdmxMessageUtil.getQueryMessageTypes(dataQueryLocation);
                    QUERY_MESSAGE_TYPE queryMessageType = queryMessageTypes.get(0);
                    if (queryMessageType.equals(QUERY_MESSAGE_TYPE.GENERIC_DATA_QUERY)) {
                        GenericDataQueryDocument queryV21 = GenericDataQueryDocument.Factory.parse(stream);
                        return dataQueryBuilder.buildComplexDataQuery(queryV21.getGenericDataQuery().getQuery(), beanRetrievalManager);
                    } else if (queryMessageType.equals(QUERY_MESSAGE_TYPE.GENERIC_TIME_SERIES_DATA_QUERY)) {
                        GenericTimeSeriesDataQueryDocument queryV21 = GenericTimeSeriesDataQueryDocument.Factory.parse(stream);
                        return dataQueryBuilder.buildComplexDataQuery(queryV21.getGenericTimeSeriesDataQuery().getQuery(), beanRetrievalManager);
                    } else if (queryMessageType.equals(QUERY_MESSAGE_TYPE.STRUCTURE_SPECIFIC_DATA_QUERY)) {
                        StructureSpecificDataQueryDocument queryV21 = StructureSpecificDataQueryDocument.Factory.parse(stream);
                        return dataQueryBuilder.buildComplexDataQuery(queryV21.getStructureSpecificDataQuery().getQuery(), beanRetrievalManager);
                    } else if (queryMessageType.equals(QUERY_MESSAGE_TYPE.STRUCTURE_SPECIFIC_TIME_SERIES_DATA_QUERY)) {
                        StructureSpecificTimeSeriesDataQueryDocument queryV21 = StructureSpecificTimeSeriesDataQueryDocument.Factory.parse(stream);
                        return dataQueryBuilder.buildComplexDataQuery(queryV21.getStructureSpecificTimeSeriesDataQuery().getQuery(), beanRetrievalManager);
                    }
                default:
                    throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "buildComplexDataQuery in version " + schemaVersion);
            }
        } catch (XmlException e) {
            throw new SdmxSyntaxException(e);
        } catch (IOException e) {
            throw new SdmxException(e, "I/O error occured whilst trying to build data query from SDMX message");
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
