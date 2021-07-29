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
package org.sdmxsource.sdmx.util.beans.reference;

import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.util.ObjectUtil;


/**
 * The type Maintainable ref bean.
 */
public class MaintainableRefBeanImpl implements MaintainableRefBean {

    private static final long serialVersionUID = 1183690334822022491L;
    private String agencyId;
    private String id;
    private String version;

    /**
     * Instantiates a new Maintainable ref bean.
     */
    public MaintainableRefBeanImpl() {
    }

    /**
     * Instantiates a new Maintainable ref bean.
     *
     * @param agencyId the agency id
     * @param id       the id
     * @param version  the version
     */
    public MaintainableRefBeanImpl(String agencyId, String id, String version) {
        //DO NOT SET EMPTY STRINGS
        if (ObjectUtil.validString(agencyId)) {
            this.agencyId = agencyId;
        }
        if (ObjectUtil.validString(id)) {
            this.id = id;
        }
        if (ObjectUtil.validString(version)) {
            this.version = version;
        }
    }

    @Override
    public String getAgencyId() {
        return agencyId;
    }

    /**
     * Sets agency id.
     *
     * @param agencyId the agency id
     */
    public void setAgencyId(String agencyId) {
        if (ObjectUtil.validString(agencyId)) {
            this.agencyId = agencyId;
        } else {
            this.agencyId = null;
        }
    }

    @Override
    public String getMaintainableId() {
        return id;
    }

    /**
     * Sets maintainable id.
     *
     * @param id the id
     */
    public void setMaintainableId(String id) {
        if (ObjectUtil.validString(id)) {
            this.id = id;
        } else {
            this.id = null;
        }
    }

    @Override
    public String getVersion() {
        return version;
    }

    /**
     * Sets version.
     *
     * @param version the version
     */
    public void setVersion(String version) {
        if (ObjectUtil.validString(version)) {
            this.version = version;
        } else {
            this.version = null;
        }
    }

    @Override
    public boolean hasAgencyId() {
        return ObjectUtil.validString(agencyId);
    }

    @Override
    public boolean hasMaintainableId() {
        return ObjectUtil.validString(id);
    }

    @Override
    public boolean hasVersion() {
        return ObjectUtil.validString(version);
    }

    @Override
    public String toString() {
        String returnString = "";
        String concat = "";
        if (agencyId != null) {
            returnString += "agency: " + agencyId;
            concat = ", ";
        }
        if (id != null) {
            returnString += concat + "id: " + id;
            concat = ", ";
        }
        if (version != null) {
            returnString += concat + "version: " + version;
        }
        if (returnString.length() > 0) {
            return returnString;
        }
        return "Empty Reference (no parameters defined)";
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MaintainableRefBean)) {
            return false;
        }

        MaintainableRefBean that = (MaintainableRefBean) obj;
        return ObjectUtil.equivalent(agencyId, that.getAgencyId()) &&
                ObjectUtil.equivalent(id, that.getMaintainableId()) &&
                ObjectUtil.equivalent(version, that.getVersion());
    }
}
