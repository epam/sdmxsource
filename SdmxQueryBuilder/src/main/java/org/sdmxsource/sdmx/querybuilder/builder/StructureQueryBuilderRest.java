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
package org.sdmxsource.sdmx.querybuilder.builder;

import org.sdmxsource.sdmx.api.builder.StructureQueryBuilder;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.query.RESTStructureQuery;
import org.sdmxsource.sdmx.api.model.query.StructureQueryMetadata;
import org.sdmxsource.util.ObjectUtil;


/**
 * The type Structure query builder rest.
 */
public class StructureQueryBuilderRest implements StructureQueryBuilder<String> {

    @Override
    public String buildStructureQuery(RESTStructureQuery sQuery) {
        if (sQuery == null) {
            throw new IllegalArgumentException("StructureQueryBuilderRest.buildStructureQuery StructureQuery is required, null was passed");
        }
        String returnUrl = "";
        StructureReferenceBean sRef = sQuery.getStructureReference();
        if (sRef.getMaintainableStructureType() == SDMX_STRUCTURE_TYPE.ANY) {
            returnUrl += "structure/";
        } else if (sRef.getMaintainableStructureType() == SDMX_STRUCTURE_TYPE.ORGANISATION_SCHEME) {
            returnUrl += "organisationscheme/";
        } else {
            returnUrl += sRef.getMaintainableStructureType().getUrnClass().toLowerCase() + "/";
        }
        MaintainableRefBean mRef = sRef.getMaintainableReference();
        if (ObjectUtil.validString(mRef.getAgencyId()) && !mRef.getAgencyId().equals("*")) {
            returnUrl += mRef.getAgencyId() + "/";
        } else {
            returnUrl += "all/";
        }
        if (ObjectUtil.validString(mRef.getMaintainableId()) && !mRef.getMaintainableId().equals("*")) {
            returnUrl += mRef.getMaintainableId() + "/";
        } else {
            returnUrl += "all/";
        }
        StructureQueryMetadata sQueryMetadata = sQuery.getStructureQueryMetadata();
        if (sQueryMetadata.isReturnLatest()) {
            returnUrl += "latest/";
        } else if (ObjectUtil.validString(mRef.getVersion()) && !mRef.getVersion().equals("*")) {
            returnUrl += mRef.getVersion() + "/";
        } else {
            returnUrl += "all/";
        }
        String concat = "?";
        if (sQueryMetadata.getSpecificStructureReference() != null) {
            returnUrl += concat + "references=" + sQueryMetadata.getSpecificStructureReference().getUrnClass().toLowerCase();
            concat = "&";
        } else if (sQueryMetadata.getStructureReferenceDetail() != null) {
            returnUrl += concat + "references=" + sQueryMetadata.getStructureReferenceDetail().toString();
            concat = "&";
        }
        if (sQueryMetadata.getStructureQueryDetail() != null) {
            returnUrl += concat + "detail=" + sQueryMetadata.getStructureQueryDetail().toString();
            concat = "&";
        }
        return returnUrl;
    }

}
