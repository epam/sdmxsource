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

import org.sdmx.resources.sdmxml.schemas.v20.structure.OrganisationType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.DataProviderBean;
import org.sdmxsource.sdmx.api.model.beans.base.DataProviderSchemeBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.base.DataProviderMutableBean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The type Data provider bean.
 */
public class DataProviderBeanImpl extends OrganisationBeanImpl implements DataProviderBean {
    private static final long serialVersionUID = 2L;

    /**
     * Instantiates a new Data provider bean.
     *
     * @param bean   the bean
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEAN				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public DataProviderBeanImpl(DataProviderMutableBean bean, DataProviderSchemeBean parent) {
        super(bean, parent);
    }

    /**
     * Instantiates a new Data provider bean.
     *
     * @param type   the type
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public DataProviderBeanImpl(org.sdmx.resources.sdmxml.schemas.v21.structure.DataProviderType type, DataProviderSchemeBean parent) {
        super(type, SDMX_STRUCTURE_TYPE.DATA_PROVIDER, parent);
    }


    /**
     * Instantiates a new Data provider bean.
     *
     * @param bean   the bean
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public DataProviderBeanImpl(OrganisationType bean, DataProviderSchemeBean parent) {
        super(bean, SDMX_STRUCTURE_TYPE.DATA_PROVIDER,
                bean.getCollectorContact(), bean.getId(), bean.getUri(),
                bean.getNameList(), bean.getDescriptionList(), bean.getAnnotations(), parent);
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
            return super.deepEqualsInternal((DataProviderBean) bean, includeFinalProperties);
        }
        return false;
    }

    @Override
    public List<CrossReferenceBean> getCrossReferencedConstrainables() {
        return new ArrayList<CrossReferenceBean>();
    }


    @Override
    public DataProviderSchemeBean getMaintainableParent() {
        return (DataProviderSchemeBean) super.getMaintainableParent();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////	

    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        return new HashSet<SDMXBean>();
    }

}
