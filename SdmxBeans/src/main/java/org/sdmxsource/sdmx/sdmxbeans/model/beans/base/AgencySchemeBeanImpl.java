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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.base;

import org.sdmx.resources.sdmxml.schemas.v20.structure.AgenciesType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.OrganisationSchemeType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.OrganisationType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.AgencySchemeType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.AgencyType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TERTIARY_BOOL;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.AgencyBean;
import org.sdmxsource.sdmx.api.model.beans.base.AgencySchemeBean;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.mutable.base.AgencyMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.AgencySchemeMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.AgencyMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.AgencySchemeMutableBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.net.URL;


/**
 * The type Agency scheme bean.
 */
public class AgencySchemeBeanImpl extends OrganisationSchemeBeanImpl<AgencyBean> implements AgencySchemeBean {
    private static final long serialVersionUID = -787661696574731583L;

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM ITSELF, CREATES STUB BEAN //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private AgencySchemeBeanImpl(AgencySchemeBean bean, URL actualLocation, boolean isServiceUrl) {
        super(bean, actualLocation, isServiceUrl);
    }

    /**
     * Instantiates a new Agency scheme bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public AgencySchemeBeanImpl(AgencySchemeMutableBean bean) {
        super(bean);
        if (bean.getItems() != null) {
            for (AgencyMutableBean item : bean.getItems()) {
                this.items.add(new AgencyBeanImpl(item, this));
            }
        }
    }

    /**
     * Instantiates a new Agency scheme bean.
     *
     * @param type the type
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public AgencySchemeBeanImpl(AgencySchemeType type) {
        super(type, SDMX_STRUCTURE_TYPE.AGENCY_SCHEME);
        if (ObjectUtil.validCollection(type.getAgencyList())) {
            for (AgencyType currentAgency : type.getAgencyList()) {
                this.items.add(new AgencyBeanImpl(currentAgency, this));
            }
        }
    }

    /**
     * Instantiates a new Agency scheme bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public AgencySchemeBeanImpl(OrganisationSchemeType bean) {
        super(bean, SDMX_STRUCTURE_TYPE.AGENCY_SCHEME,
                bean.getValidTo(),
                bean.getValidFrom(),
                FIXED_VERSION,
                TERTIARY_BOOL.FALSE,
                DEFAULT_SCHEME,
                FIXED_ID,
                bean.getUri(),
                bean.getNameList(),
                bean.getDescriptionList(),
                createTertiary(bean.isSetIsExternalReference(), bean.getIsExternalReference()),
                bean.getAnnotations());

        try {
            if (bean.getAgenciesList() != null) {
                for (AgenciesType dcList : bean.getAgenciesList()) {
                    for (OrganisationType agencyType : dcList.getAgencyList()) {
                        items.add(new AgencyBeanImpl(agencyType, this));
                    }
                }
            }
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
    }


    /**
     * Create default scheme agency scheme bean.
     *
     * @return the agency scheme bean
     */
    public static AgencySchemeBean createDefaultScheme() {
        AgencySchemeMutableBean mutable = new AgencySchemeMutableBeanImpl();
        mutable.setAgencyId(DEFAULT_SCHEME);
        mutable.setId(FIXED_ID);
        mutable.setVersion(FIXED_VERSION);
        mutable.addName("en", "SDMX Agency Scheme");
        AgencyMutableBean agencyMutableBean = new AgencyMutableBeanImpl();
        agencyMutableBean.addName("en", AgencyBean.DEFAULT_AGENCY);
        agencyMutableBean.setId(AgencyBean.DEFAULT_AGENCY);
        mutable.addItem(agencyMutableBean);
        return mutable.getImmutableInstance();
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////DEEP VALIDATION						 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean deepEquals(SDMXBean bean, boolean includeFinalProperties) {
        if (bean != null && bean.getStructureType() == this.getStructureType()) {
            return super.deepEqualsInternal((AgencySchemeBean) bean, includeFinalProperties);
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public String getVersion() {
        return FIXED_VERSION;
    }

    @Override
    public String getId() {
        return FIXED_ID;
    }

    @Override
    public boolean isDefaultScheme() {
        return this.getAgencyId().equals(DEFAULT_SCHEME);
    }

    @Override
    public MaintainableBean getStub(URL actualLocation, boolean isServiceUrl) {
        return new AgencySchemeBeanImpl(this, actualLocation, isServiceUrl);
    }

    @Override
    public AgencySchemeMutableBean getMutableInstance() {
        return new AgencySchemeMutableBeanImpl(this);
    }
}
