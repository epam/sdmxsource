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

import org.apache.log4j.Logger;
import org.sdmx.resources.sdmxml.schemas.v21.structure.OrganisationUnitSchemeType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.OrganisationUnitType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.base.OrganisationUnitBean;
import org.sdmxsource.sdmx.api.model.beans.base.OrganisationUnitSchemeBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.mutable.base.OrganisationUnitMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.OrganisationUnitSchemeMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.OrganisationUnitSchemeMutableBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * The type Organisation unit scheme bean.
 */
public class OrganisationUnitSchemeBeanImpl extends ItemSchemeBeanImpl<OrganisationUnitBean> implements OrganisationUnitSchemeBean {
    private static final long serialVersionUID = -787661696574731583L;
    private static Logger LOG = Logger.getLogger(OrganisationUnitSchemeBeanImpl.class);

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM ITSELF, CREATES STUB BEAN //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private OrganisationUnitSchemeBeanImpl(OrganisationUnitSchemeBean bean, URL actualLocation, boolean isServiceUrl) {
        super(bean, actualLocation, isServiceUrl);
        LOG.debug("Stub OrganisationUnitSchemeBean Built");
        try {
            validate();
        } catch (SdmxSemmanticException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
    }

    /**
     * Instantiates a new Organisation unit scheme bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public OrganisationUnitSchemeBeanImpl(OrganisationUnitSchemeMutableBean bean) {
        super(bean);
        LOG.debug("Building OrganisationUnitSchemeBean from Mutable Bean");
        if (bean.getItems() != null) {
            for (OrganisationUnitMutableBean oumb : bean.getItems()) {
                items.add(new OrganisationUnitBeanImpl(oumb, this));
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("OrganisationUnitSchemeBean Built " + this.getUrn());
        }
        try {
            validate();
        } catch (SdmxSemmanticException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
    }


    /**
     * Instantiates a new Organisation unit scheme bean.
     *
     * @param type the type
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public OrganisationUnitSchemeBeanImpl(OrganisationUnitSchemeType type) {
        super(type, SDMX_STRUCTURE_TYPE.ORGANISATION_UNIT_SCHEME);
        LOG.debug("Building OrganisationUnitSchemeBean from 2.1 SDMX");
        if (ObjectUtil.validCollection(type.getOrganisationUnitList())) {
            for (OrganisationUnitType currentType : type.getOrganisationUnitList()) {
                this.items.add(new OrganisationUnitBeanImpl(currentType, this));
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("OrganisationUnitSchemeBean Built " + this.getUrn());
        }
        try {
            validate();
        } catch (SdmxSemmanticException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////DEEP VALIDATION						 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean deepEquals(SDMXBean bean, boolean includeFinalProperties) {
        if (bean == null) {
            return false;
        }
        if (bean.getStructureType() == this.getStructureType()) {
            return super.deepEqualsInternal((OrganisationUnitSchemeBean) bean, includeFinalProperties);
        }
        return false;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() throws SdmxSemmanticException {
        //CHECK FOR DUPLICATION OF URN & ILLEGAL PARENTING
        Map<OrganisationUnitBean, Set<OrganisationUnitBean>> parentChildMap = new HashMap<OrganisationUnitBean, Set<OrganisationUnitBean>>();
        for (OrganisationUnitBean currentUnit : this.getItems()) {
            if (ObjectUtil.validString(currentUnit.getParentUnit())) {
                OrganisationUnitBean parentUnit = getUnit(currentUnit.getParentUnit());
                Set<OrganisationUnitBean> children;
                if (parentChildMap.containsKey(parentUnit)) {
                    children = parentChildMap.get(parentUnit);
                } else {
                    children = new HashSet<OrganisationUnitBean>();
                    parentChildMap.put(parentUnit, children);
                }
                children.add(currentUnit);
                //Check that the parent code is not directly or indirectly a child of the code it is parenting
                recurseParentMap(parentChildMap.get(currentUnit), parentUnit, parentChildMap);
            }
        }
    }

    private OrganisationUnitBean getUnit(String id) {
        for (OrganisationUnitBean organisationUnitBean : this.getItems()) {
            if (organisationUnitBean.getId().equals(id)) {
                return organisationUnitBean;
            }
        }
        throw new SdmxSemmanticException(ExceptionCode.CAN_NOT_RESOLVE_PARENT, id);
    }


    /**
     * Recurses the map checking the children of each child, if one of the children is the parent code, then an exception is thrown
     *
     * @param children
     * @param parentBean
     * @param parentChildMap
     */
    private void recurseParentMap(Set<OrganisationUnitBean> children, OrganisationUnitBean parentBean, Map<OrganisationUnitBean, Set<OrganisationUnitBean>> parentChildMap) {
        //If the child is also a parent
        if (children != null) {
            if (children.contains(parentBean)) {
                throw new SdmxSemmanticException(ExceptionCode.PARENT_RECURSIVE_LOOP, parentBean.getId());
            }
            for (OrganisationUnitBean currentChild : children) {
                recurseParentMap(parentChildMap.get(currentChild), parentBean, parentChildMap);
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public MaintainableBean getStub(URL actualLocation, boolean isServiceUrl) {
        return new OrganisationUnitSchemeBeanImpl(this, actualLocation, isServiceUrl);
    }

    @Override
    public OrganisationUnitSchemeMutableBean getMutableInstance() {
        return new OrganisationUnitSchemeMutableBeanImpl(this);
    }
}
