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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.datastructure;

import org.sdmx.resources.sdmxml.schemas.v20.structure.KeyFamilyType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.AttributeType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.AttributeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.AttributeListBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.AttributeListMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.AttributeMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.IdentifiableBeanImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * The type Attribute list bean.
 */
public class AttributeListBeanImpl extends IdentifiableBeanImpl implements AttributeListBean {
    private static final long serialVersionUID = -8089384199182589066L;
    private List<AttributeBean> attributes = new ArrayList<AttributeBean>();


    /**
     * Instantiates a new Attribute list bean.
     *
     * @param bean   the bean
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS 			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public AttributeListBeanImpl(AttributeListMutableBean bean, DataStructureBean parent) {
        super(bean, parent);
        if (bean.getAttributes() != null) {
            for (AttributeMutableBean currentAttribute : bean.getAttributes()) {
                this.attributes.add(new AttributeBeanImpl(currentAttribute, this));
            }
        }
    }

    /**
     * Instantiates a new Attribute list bean.
     *
     * @param attributeList the attribute list
     * @param parent        the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public AttributeListBeanImpl(org.sdmx.resources.sdmxml.schemas.v21.structure.AttributeListType attributeList, MaintainableBean parent) {
        super(attributeList, SDMX_STRUCTURE_TYPE.ATTRIBUTE_DESCRIPTOR, parent);
        if (attributeList.getAttributeList() != null) {
            for (AttributeType currentAttribute : attributeList.getAttributeList()) {
                attributes.add(new AttributeBeanImpl(currentAttribute, this));
            }
        }
    }

    /**
     * Instantiates a new Attribute list bean.
     *
     * @param bean   the bean
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.0 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public AttributeListBeanImpl(KeyFamilyType bean, MaintainableBean parent) {
        super(FIXED_ID, SDMX_STRUCTURE_TYPE.ATTRIBUTE_DESCRIPTOR, parent);
        if (bean.getComponents() != null && bean.getComponents().getAttributeList() != null) {
            for (org.sdmx.resources.sdmxml.schemas.v20.structure.AttributeType currentAttribute : bean.getComponents().getAttributeList()) {
                attributes.add(new AttributeBeanImpl(currentAttribute, this));
            }
        }
    }


    /**
     * Instantiates a new Attribute list bean.
     *
     * @param bean   the bean
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V1.0 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public AttributeListBeanImpl(org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.KeyFamilyType bean, MaintainableBean parent) {
        super(FIXED_ID, SDMX_STRUCTURE_TYPE.ATTRIBUTE_DESCRIPTOR, parent);
        if (bean.getComponents() != null && bean.getComponents().getAttributeList() != null) {
            for (org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.AttributeType currentAttribute : bean.getComponents().getAttributeList()) {
                attributes.add(new AttributeBeanImpl(currentAttribute, this));
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////DEEP EQUALS							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean deepEquals(SDMXBean bean, boolean includeFinalProperties) {
        if (bean == null) {
            return false;
        }
        if (bean.getStructureType() == this.getStructureType()) {
            AttributeListBean that = (AttributeListBean) bean;
            if (!super.equivalent(attributes, that.getAttributes(), includeFinalProperties)) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public String getId() {
        return FIXED_ID;
    }

    @Override
    public List<AttributeBean> getAttributes() {
        return new ArrayList<AttributeBean>(attributes);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(attributes, composites);
        return composites;
    }
}
