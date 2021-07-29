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
import org.sdmxsource.sdmx.api.model.query.StructureQueryMetadata;

import java.util.Map;

/**
 * The type Structure query metadata.
 */
public class StructureQueryMetadataImpl implements StructureQueryMetadata {
    private static final long serialVersionUID = 4511749640571382502L;
    private STRUCTURE_QUERY_DETAIL structureQueryDetail = STRUCTURE_QUERY_DETAIL.FULL;
    private STRUCTURE_REFERENCE_DETAIL structureReferenceDetail = STRUCTURE_REFERENCE_DETAIL.NONE;
    private SDMX_STRUCTURE_TYPE specificStructureReference;
    private boolean returnLatest;

    /**
     * Instantiates a new Structure query metadata.
     *
     * @param structureQueryDetail       the structure query detail
     * @param structureReferenceDetail   the structure reference detail
     * @param specificStructureReference the specific structure reference
     * @param returnLatest               the return latest
     */
    public StructureQueryMetadataImpl(
            STRUCTURE_QUERY_DETAIL structureQueryDetail,
            STRUCTURE_REFERENCE_DETAIL structureReferenceDetail,
            SDMX_STRUCTURE_TYPE specificStructureReference, boolean returnLatest) {
        if (structureQueryDetail != null) {
            this.structureQueryDetail = structureQueryDetail;
        }
        if (structureReferenceDetail != null) {
            this.structureReferenceDetail = structureReferenceDetail;
        }
        if (specificStructureReference != null) {
            this.specificStructureReference = specificStructureReference;
        }
        this.returnLatest = returnLatest;
    }

    /**
     * Instantiates a new Structure query metadata.
     *
     * @param queryString     the query string
     * @param queryParameters the query parameters
     */
    public StructureQueryMetadataImpl(String[] queryString, Map<String, String> queryParameters) {
        parserQueryString(queryString);
        parserQueryParameters(queryParameters);
    }

    private void parserQueryString(String[] queryString) {
        if (queryString.length >= 4) {
            if (queryString[3].equalsIgnoreCase("latest")) {
                this.returnLatest = true;
            }
        } else {
            this.returnLatest = true;
        }
    }

    private void parserQueryParameters(Map<String, String> params) {
        if (params != null) {
            for (String key : params.keySet()) {
                String value = params.get(key);
                if (key.equalsIgnoreCase("detail")) {
                    try {
                        structureQueryDetail = STRUCTURE_QUERY_DETAIL.parseString(value);
                    } catch (SdmxSemmanticException e) {
                        throw new SdmxSemmanticException(e, "unable to parse value for key " + key);
                    }
                } else if (key.equalsIgnoreCase("references")) {
                    try {
                        structureReferenceDetail = STRUCTURE_REFERENCE_DETAIL.parseString(value);
                        if (structureReferenceDetail == STRUCTURE_REFERENCE_DETAIL.SPECIFIC) {
                            specificStructureReference = SDMX_STRUCTURE_TYPE.parseClass(value);
                        }
                    } catch (SdmxSemmanticException e) {
                        throw new SdmxSemmanticException(e, "unable to parse value for key " + key);
                    }
                } else {
                    throw new SdmxSemmanticException("Unknown query parameter : " + key);
                }
            }
        }
    }

    @Override
    public STRUCTURE_QUERY_DETAIL getStructureQueryDetail() {
        return structureQueryDetail;
    }

    @Override
    public STRUCTURE_REFERENCE_DETAIL getStructureReferenceDetail() {
        return structureReferenceDetail;
    }

    @Override
    public SDMX_STRUCTURE_TYPE getSpecificStructureReference() {
        return specificStructureReference;
    }

    @Override
    public boolean isReturnLatest() {
        return returnLatest;
    }

}
