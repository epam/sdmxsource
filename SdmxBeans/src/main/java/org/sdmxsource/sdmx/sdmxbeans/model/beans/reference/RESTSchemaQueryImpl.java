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
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.query.RESTSchemaQuery;
import org.sdmxsource.sdmx.util.beans.reference.MaintainableRefBeanImpl;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.HashMap;
import java.util.Map;


/**
 * The type Rest schema query.
 */
public class RESTSchemaQueryImpl implements RESTSchemaQuery {
    private static final long serialVersionUID = 1L;

    private StructureReferenceBean reference;
    private String dimAtObs = DimensionBean.TIME_DIMENSION_FIXED_ID;
    private boolean explicitMeasure;

    private String context;
    private String agencyId;
    private String id;
    private String version;

    /**
     * Instantiates a new Rest schema query.
     *
     * @param reference the reference
     * @param dimAtObs  the dim at obs
     */
    public RESTSchemaQueryImpl(StructureReferenceBean reference, String dimAtObs) {
        this(reference, dimAtObs, true);
    }

    /**
     * Instantiates a new Rest schema query.
     *
     * @param reference       the reference
     * @param dimAtObs        the dim at obs
     * @param explicitMeasure the explicit measure
     */
    public RESTSchemaQueryImpl(StructureReferenceBean reference, String dimAtObs, boolean explicitMeasure) {
        this.reference = reference;
        if (ObjectUtil.validString(dimAtObs)) {
            this.dimAtObs = dimAtObs;
        }
        this.explicitMeasure = explicitMeasure;
    }

    /**
     * Constructs a schema query from a full or partial REST URL.
     * The URL must start before the Schema segment and be complete, example input:
     * /schema/provision/IMF/PGI/1.0?dimensionAtObservation=freq
     *
     * @param restString the rest string
     */
    public RESTSchemaQueryImpl(String restString) {
        this(restString, null);
    }


    /**
     * Constructs a schema query from a full or partial REST URL.
     * The URL must start before the Schema segment and be complete, example input:
     * /schema/provision/IMF/PGI/1.0?dimensionAtObservation=freq
     *
     * @param restString      the rest string
     * @param queryParameters the query parameters
     */
    public RESTSchemaQueryImpl(String restString, Map<String, String> queryParameters) {
        // Construct a String[] for the queryString
        String queryString = restString.substring(restString.indexOf("schema/"));
        String[] queryStringArr = queryString.split("/");

        if (queryParameters == null) {
            queryParameters = new HashMap<String, String>();
        }
        if (queryString.indexOf("?") > 0) {
            String params = queryString.substring(queryString.indexOf("?") + 1);
            queryString = queryString.substring(0, queryString.indexOf("?"));

            for (String currentParam : params.split("&")) {
                String[] param = currentParam.split("=");
                queryParameters.put(param[0], param[1]);
            }
        }

        evaluate(queryStringArr, queryParameters);
    }

    /**
     * Instantiates a new Rest schema query.
     *
     * @param queryString     the query string
     * @param queryParameters the query parameters
     */
    public RESTSchemaQueryImpl(String[] queryString, Map<String, String> queryParameters) {
        evaluate(queryString, queryParameters);
    }

    private void evaluate(String[] queryString, Map<String, String> queryParameters) {
        parseQueryString(queryString);
        parseQueryParameters(queryParameters);

        SDMX_STRUCTURE_TYPE referencedStructure = SDMX_STRUCTURE_TYPE.parseClass(context);

        if (referencedStructure != SDMX_STRUCTURE_TYPE.DSD &&
                referencedStructure != SDMX_STRUCTURE_TYPE.DATAFLOW &&
                referencedStructure != SDMX_STRUCTURE_TYPE.PROVISION_AGREEMENT &&
                referencedStructure != SDMX_STRUCTURE_TYPE.METADATA_FLOW &&
                referencedStructure != SDMX_STRUCTURE_TYPE.MSD) {
            throw new SdmxSemmanticException("The referenced structure is not a legitimate type!");
        }

        MaintainableRefBean ref = new MaintainableRefBeanImpl(agencyId, id, version);
        reference = new StructureReferenceBeanImpl(ref, referencedStructure);
    }

    private void parseQueryString(String[] queryString) {
        if (queryString.length < 2) {
            throw new SdmxSemmanticException("Schema query expected to contain context as the second argument");
        }
        context = queryString[1];

        if (queryString.length < 3) {
            throw new SdmxSemmanticException("Schema query expected to contain Agency ID as the third argument");
        }
        agencyId = queryString[2];

        if (queryString.length < 4) {
            throw new SdmxSemmanticException("Schema query expected to contain Resource ID as the fourth argument");
        }
        id = queryString[3];

        if (queryString.length > 4) {
            version = queryString[4];
            if (version.equalsIgnoreCase("latest")) {
                version = null;
            }
        }

        if (queryString.length > 5) {
            throw new SdmxSemmanticException("Schema query has unexpected sixth argument");
        }
    }

    private void parseQueryParameters(Map<String, String> params) {
        if (params != null) {
            for (String key : params.keySet()) {
                if (key.equalsIgnoreCase("dimensionAtObservation")) {
                    dimAtObs = params.get(key);
                } else if (key.equalsIgnoreCase("explicitMeasure")) {
                    String val = params.get(key);
                    explicitMeasure = Boolean.parseBoolean(val);
                } else {
                    throw new SdmxSemmanticException("Unknown query parameter : " + key +
                            " allowed parameters [dimensionAtObservation, explicitMeasure]");
                }
            }
        }
    }

    public StructureReferenceBean getReference() {
        return reference;
    }

    public String getDimAtObs() {
        return dimAtObs;
    }

    public boolean isExplicitMeasure() {
        return explicitMeasure;
    }
}
