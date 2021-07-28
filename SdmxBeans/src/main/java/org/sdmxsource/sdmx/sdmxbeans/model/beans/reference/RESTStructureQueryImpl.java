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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.reference;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.STRUCTURE_QUERY_DETAIL;
import org.sdmxsource.sdmx.api.constants.STRUCTURE_REFERENCE_DETAIL;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.query.RESTStructureQuery;
import org.sdmxsource.sdmx.api.model.query.StructureQueryMetadata;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * The type Rest structure query.
 */
public class RESTStructureQueryImpl implements RESTStructureQuery, Serializable {
    private static final long serialVersionUID = 1L;

    private StructureReferenceBean structureReference;
    private StructureQueryMetadata structureQueryMetadata = new StructureQueryMetadataImpl(null, null, null, true);

    /**
     * Creation of a Structure Query for structures that match the given reference
     *
     * @param structureReference the structure reference
     */
    public RESTStructureQueryImpl(StructureReferenceBean structureReference) {
        this.structureReference = structureReference;
        if (structureReference.getVersion() != null) {
            structureQueryMetadata = new StructureQueryMetadataImpl(null, null, null, false);
        }
    }

    /**
     * Instantiates a new Rest structure query.
     *
     * @param structureQueryDetail       the structure query detail
     * @param structureReferenceDetail   the structure reference detail
     * @param specificStructureReference the specific structure reference
     * @param structureReference         the structure reference
     * @param returnLatest               the return latest
     */
    public RESTStructureQueryImpl(
            STRUCTURE_QUERY_DETAIL structureQueryDetail,
            STRUCTURE_REFERENCE_DETAIL structureReferenceDetail,
            SDMX_STRUCTURE_TYPE specificStructureReference,
            StructureReferenceBean structureReference, boolean returnLatest) {
        this.structureQueryMetadata = new StructureQueryMetadataImpl(structureQueryDetail, structureReferenceDetail, specificStructureReference, returnLatest);
        this.structureReference = structureReference;
    }

    /**
     * Instantiates a new Rest structure query.
     *
     * @param restString the rest string
     */
    public RESTStructureQueryImpl(String restString) {
        this(restString, null);
    }

    /**
     * Constructs a REST query from the rest query string, example:
     * /dataflow/ALL/ALL/ALL
     *
     * @param queryString     the query
     * @param queryParameters the query parameters
     */
    public RESTStructureQueryImpl(String queryString, Map<String, String> queryParameters) {
        if (queryString.startsWith("/")) {
            queryString = queryString.substring(1);
        }

        if (queryParameters == null) {
            queryParameters = new HashMap<String, String>();
        }

        //Parse any additional parameters
        if (queryString.indexOf("?") > 0) {
            String params = queryString.substring(queryString.indexOf("?") + 1);
            queryString = queryString.substring(0, queryString.indexOf("?"));

            for (String currentParam : params.split("&")) {
                String[] param = currentParam.split("=");
                queryParameters.put(param[0], param[1]);
            }
        }

        String[] args = queryString.split("/");
        parserQueryString(args);
        structureQueryMetadata = new StructureQueryMetadataImpl(args, queryParameters);
    }


    /**
     * Instantiates a new Rest structure query.
     *
     * @param queryString     the query string
     * @param queryParameters the query parameters
     */
    public RESTStructureQueryImpl(String[] queryString, Map<String, String> queryParameters) {
        parserQueryString(queryString);
        structureQueryMetadata = new StructureQueryMetadataImpl(queryString, queryParameters);
    }

    private static SDMX_STRUCTURE_TYPE getStructureType(String str) {
        if (str.equalsIgnoreCase("structure")) {
            return SDMX_STRUCTURE_TYPE.ANY;
        }
        if (str.equalsIgnoreCase("organisationscheme")) {
            return SDMX_STRUCTURE_TYPE.ORGANISATION_SCHEME;
        } else return SDMX_STRUCTURE_TYPE.parseClass(str);
    }

    @Override
    public StructureQueryMetadata getStructureQueryMetadata() {
        return structureQueryMetadata;
    }

    @Override
    public StructureReferenceBean getStructureReference() {
        return structureReference;
    }

    private void parserQueryString(String[] queryString) {
        if (queryString.length < 1) {
            throw new SdmxSemmanticException("Structure Query Expecting at least 1 parameter (structure type)");
        }
        SDMX_STRUCTURE_TYPE structureType = getStructureType(queryString[0]);
        String agencyId = null;
        String id = null;
        String version = null;
        String[] childId = null;
        if (queryString.length >= 2) {
            agencyId = parseQueryString(queryString[1]);
        }
        if (queryString.length >= 3) {
            id = parseQueryString(queryString[2]);
        }
        if (queryString.length >= 4) {
            version = parseQueryString(queryString[3]);
        }
        if (queryString.length >= 5) {
            childId = parseQueryString(queryString[4]).split("\\.");
            switch (structureType) {
                case CODE_LIST:
                    structureType = SDMX_STRUCTURE_TYPE.CODE;
                    break;
                case CONCEPT_SCHEME:
                    structureType = SDMX_STRUCTURE_TYPE.CONCEPT;
                    break;
                case CATEGORY_SCHEME:
                    structureType = SDMX_STRUCTURE_TYPE.CATEGORY;
                    break;
                case AGENCY_SCHEME:
                    structureType = SDMX_STRUCTURE_TYPE.AGENCY;
                    break;
                case DATA_PROVIDER_SCHEME:
                    structureType = SDMX_STRUCTURE_TYPE.DATA_PROVIDER;
                    break;
                case DATA_CONSUMER_SCHEME:
                    structureType = SDMX_STRUCTURE_TYPE.DATA_CONSUMER;
                    break;
                case ORGANISATION_UNIT_SCHEME:
                    structureType = SDMX_STRUCTURE_TYPE.ORGANISATION_UNIT;
                    break;
                case REPORTING_TAXONOMY:
                    structureType = SDMX_STRUCTURE_TYPE.REPORTING_CATEGORY;
                    break;
                default:
                    throw new SdmxSemmanticException("Query by identifiable Id is only supported when querying Item Schemes. This path parameter is supported for the following structure types: categoryscheme, codelist, conceptscheme, agencyscheme, dataproviderscheme, dataconsumerscheme, organisationunitscheme, reportingtaxonomy");
            }
        }
        if (queryString.length >= 6) {
            throw new SdmxSemmanticException("Unexpected path parameter: " + queryString[5]);
        }
        this.structureReference = new StructureReferenceBeanImpl(agencyId, id, version, structureType, childId);
    }

    private String parseQueryString(String query) {
        if (!ObjectUtil.validString(query)) {
            return null;
        }
        if (query.equalsIgnoreCase("all")) {
            return "*";
        }
        if (query.equalsIgnoreCase("latest")) {
            return null;
        }
        return query;
    }

    @Override
    public String toString() {
        return "ref: " + structureReference +
                " -detail: " + structureQueryMetadata.getStructureQueryDetail() +
                " -references: " + structureReference +
                " -specific: " + structureQueryMetadata.getSpecificStructureReference() +
                " -latest: " + structureQueryMetadata.isReturnLatest();
    }
}
