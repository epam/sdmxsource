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

import org.sdmx.resources.sdmxml.schemas.v21.structure.OrganisationUnitType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.OrganisationUnitBean;
import org.sdmxsource.sdmx.api.model.beans.base.OrganisationUnitSchemeBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.mutable.base.OrganisationUnitMutableBean;
import org.sdmxsource.util.ObjectUtil;


/**
 * The type Organisation unit bean.
 */
public class OrganisationUnitBeanImpl extends OrganisationBeanImpl implements OrganisationUnitBean {
    private static final long serialVersionUID = 2L;
    private String parentId;

    /**
     * Instantiates a new Organisation unit bean.
     *
     * @param bean   the bean
     * @param parent the parent
     */
    public OrganisationUnitBeanImpl(OrganisationUnitMutableBean bean, OrganisationUnitSchemeBean parent) {
        super(bean, parent);
        this.parentId = bean.getParentUnit();
    }

    /**
     * Instantiates a new Organisation unit bean.
     *
     * @param type   the type
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public OrganisationUnitBeanImpl(OrganisationUnitType type, OrganisationUnitSchemeBean parent) {
        super(type, SDMX_STRUCTURE_TYPE.ORGANISATION_UNIT, parent);
        this.parentId = (type.getParent() != null) ? type.getParent().getRef().getId() : null;
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
            OrganisationUnitBean that = (OrganisationUnitBean) bean;
            if (!ObjectUtil.equivalent(parentId, that.getParentUnit())) {
                return false;
            }
            return super.deepEqualsInternal((OrganisationUnitBean) bean, includeFinalProperties);
        }
        return false;
    }


    @Override
    public boolean hasParentUnit() {
        return getParentUnit() != null;
    }

    @Override
    public String getParentUnit() {
        return parentId;
    }
}
