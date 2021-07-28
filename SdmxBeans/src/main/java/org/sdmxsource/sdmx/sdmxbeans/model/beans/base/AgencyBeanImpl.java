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

import org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.AgencyType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.OrganisationType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.AgencyBean;
import org.sdmxsource.sdmx.api.model.beans.base.AgencySchemeBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.mutable.base.AgencyMutableBean;
import org.sdmxsource.util.ObjectUtil;


/**
 * The type Agency bean.
 */
public class AgencyBeanImpl extends OrganisationBeanImpl implements AgencyBean {
    private static final long serialVersionUID = 2L;

    /**
     * Instantiates a new Agency bean.
     *
     * @param bean   the bean
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public AgencyBeanImpl(AgencyMutableBean bean, AgencySchemeBean parent) {
        super(bean, parent);
    }

    /**
     * Instantiates a new Agency bean.
     *
     * @param type   the type
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public AgencyBeanImpl(org.sdmx.resources.sdmxml.schemas.v21.structure.AgencyType type, AgencySchemeBean parent) {
        super(type, SDMX_STRUCTURE_TYPE.AGENCY, parent);
    }

    /**
     * Instantiates a new Agency bean.
     *
     * @param bean   the bean
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public AgencyBeanImpl(OrganisationType bean, AgencySchemeBean parent) {
        super(bean, SDMX_STRUCTURE_TYPE.AGENCY, bean.getCollectorContact(), bean.getId(), bean.getUri(),
                bean.getNameList(), bean.getDescriptionList(), bean.getAnnotations(), parent);
    }

    /**
     * Instantiates a new Agency bean.
     *
     * @param bean   the bean
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public AgencyBeanImpl(AgencyType bean, AgencySchemeBean parent) {
        super(bean, SDMX_STRUCTURE_TYPE.AGENCY, bean.getCollectorContact(), bean.getId(), bean.getUri(), bean.getNameList(), null, null, parent);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////DEEP VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean deepEquals(SDMXBean bean, boolean includeFinalProperties) {
        if (bean != null && bean.getStructureType() == SDMX_STRUCTURE_TYPE.AGENCY) {
            AgencyBean that = (AgencyBean) bean;
            if (!ObjectUtil.equivalent(getFullId(), that.getFullId())) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void validateId(boolean startWithIntAllowed) {
        //Not allowed to start with an integer
        super.validateId(false);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public String getFullId() {
        AgencySchemeBean parent = getMaintainableParent();
        if (parent.isDefaultScheme()) {
            return this.getId();
        }
        return parent.getAgencyId() + "." + this.getId();
    }


    @Override
    public String getUrn() {
        return structureType.getUrnPrefix() + getFullId();
    }

    @Override
    public AgencySchemeBean getMaintainableParent() {
        return (AgencySchemeBean) super.getMaintainableParent();
    }
}
