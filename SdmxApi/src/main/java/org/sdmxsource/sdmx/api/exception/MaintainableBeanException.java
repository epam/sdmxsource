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
package org.sdmxsource.sdmx.api.exception;

import org.sdmxsource.sdmx.api.constants.SDMX_ERROR_CODE;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;

public class MaintainableBeanException extends SdmxException {
    private static final long serialVersionUID = -8099955589762191856L;
    private String agencyId;
    private String id;
    private String version;
    private SDMX_STRUCTURE_TYPE structureType;

    public MaintainableBeanException(Throwable th, SDMX_STRUCTURE_TYPE structureType, String agencyId, String id, String version) {
        super(th, "Error with maintainable artefact of type '" + structureType.getType() + "' and identifiers '" + getAgencyId(agencyId) + ":" + getId(id) + "(" + getVersion(version) + ")'");
        this.agencyId = agencyId;
        this.id = id;
        this.version = version;
        this.structureType = structureType;
    }

    public MaintainableBeanException(Throwable th, MaintainableBean maintainableBean) {
        this(th, maintainableBean.getStructureType(), maintainableBean.getAgencyId(), maintainableBean.getId(), maintainableBean.getVersion());
    }

    private static String getAgencyId(String agencyId) {
        if (agencyId == null || agencyId.length() == 0) {
            return "agency missing";
        }
        return agencyId;
    }

    private static String getId(String id) {
        if (id == null || id.length() == 0) {
            return "id missing";
        }
        return id;
    }

    private static String getVersion(String version) {
        if (version == null || version.length() == 0) {
            return MaintainableBean.DEFAULT_VERSION;
        }
        return version;
    }

    @Override
    public SDMX_ERROR_CODE getSdmxErrorCode() {
        return SDMX_ERROR_CODE.INTERNAL_SERVER_ERROR;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public String getId() {
        return id;
    }

    public String getVersion() {
        return version;
    }

    public SDMX_STRUCTURE_TYPE getStructureType() {
        return structureType;
    }

    @Override
    public String getErrorType() {
        return "Maintainable Bean Exception";
    }
}
