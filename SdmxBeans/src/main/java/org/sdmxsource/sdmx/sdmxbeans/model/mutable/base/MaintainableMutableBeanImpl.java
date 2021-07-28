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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.base;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TERTIARY_BOOL;
import org.sdmxsource.sdmx.api.model.SdmxReader;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.MaintainableMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.SdmxDateImpl;
import org.sdmxsource.util.ObjectUtil;
import org.sdmxsource.util.VersionableUtil;

import java.util.Date;
import java.util.Map;


/**
 * The type Maintainable mutable bean.
 */
public abstract class MaintainableMutableBeanImpl extends NameableMutableBeanImpl implements MaintainableMutableBean {
    private static final long serialVersionUID = 1L;
    private Date endDate;
    private Date startDate;
    private String version;
    private String agencyId;
    private TERTIARY_BOOL isFinalStructure;
    private TERTIARY_BOOL isExternalReference;
    private boolean isStub;
    private String serviceURL;
    private String structureURL;

    /**
     * Instantiates a new Maintainable mutable bean.
     *
     * @param structureType the structure type
     */
    public MaintainableMutableBeanImpl(SDMX_STRUCTURE_TYPE structureType) {
        super(structureType);
    }

    /**
     * Instantiates a new Maintainable mutable bean.
     *
     * @param bean the bean
     */
    public MaintainableMutableBeanImpl(MaintainableBean bean) {
        super(bean);
        setAgencyId(bean.getAgencyId());
        if (bean.getStartDate() != null) {
            setStartDate(bean.getStartDate().getDate());
        }
        if (bean.getEndDate() != null) {
            setEndDate(bean.getEndDate().getDate());
        }
        if (bean.getServiceURL() != null) {
            this.serviceURL = bean.getServiceURL().toString();
        }
        if (bean.getStructureURL() != null) {
            this.structureURL = bean.getStructureURL().toString();
        }
        setFinalStructure(bean.isFinal());
        setVersion(bean.getVersion());
        setExternalReference(bean.isExternalReference());
    }


    /**
     * Build maintainable attributes.
     *
     * @param reader the reader
     */
    protected void buildMaintainableAttributes(SdmxReader reader) {
        super.buildIdentifiableAttributes(reader);
        Map<String, String> attributes = reader.getAttributes();
        this.version = attributes.get("version");
        this.agencyId = attributes.get("agencyID");

        if (attributes.containsKey("validFrom")) {
            this.startDate = new SdmxDateImpl(attributes.get("validFrom")).getDate();
        }
        if (attributes.containsKey("validTo")) {
            this.endDate = new SdmxDateImpl(attributes.get("validTo")).getDate();
        }

        if (attributes.containsKey("serviceURL")) {
            setServiceURL(attributes.get("serviceURL"));
        }

        if (attributes.containsKey("structureURL")) {
            setStructureURL(attributes.get("structureURL"));
        }
    }

    @Override
    public TERTIARY_BOOL getFinalStructure() {
        return isFinalStructure;
    }

    @Override
    public void setFinalStructure(TERTIARY_BOOL isFinalStructure) {
        this.isFinalStructure = isFinalStructure;
    }

    @Override
    public String getAgencyId() {
        return agencyId;
    }

    @Override
    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public void setVersion(String version) {
        if (!ObjectUtil.validString(version)) {
            version = "1.0";
        }
        if (!VersionableUtil.validVersion(version)) {
            throw new IllegalArgumentException("Version invalid : " + version);
        }
        this.version = VersionableUtil.formatVersion(version);
    }

    @Override
    public String getServiceURL() {
        return serviceURL;
    }

    @Override
    public void setServiceURL(String serviceURL) {
        this.serviceURL = serviceURL;
    }

    @Override
    public String getStructureURL() {
        return structureURL;
    }

    @Override
    public void setStructureURL(String structureURL) {
        this.structureURL = structureURL;
    }

    @Override
    public TERTIARY_BOOL getExternalReference() {
        return this.isExternalReference;
    }

    @Override
    public void setExternalReference(TERTIARY_BOOL isExternalReference) {
        this.isExternalReference = isExternalReference;
    }

    @Override
    public boolean isStub() {
        return isStub;
    }

    @Override
    public void setStub(boolean isStub) {
        this.isStub = isStub;
    }

    /**
     * Compare to int.
     *
     * @param maintainableBean the maintainable bean
     * @return the int
     */
//TODO Test this method orders a list index 0 higher version then a list with index 1
    public int compareTo(MaintainableBean maintainableBean) {
        return VersionableUtil.isHigherVersion(this.version, maintainableBean.getVersion()) ? -1 : +1;
    }
}
