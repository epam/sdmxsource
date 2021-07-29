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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.mapping;

import org.apache.xmlbeans.XmlObject;
import org.sdmx.resources.sdmxml.schemas.v20.common.AnnotationsType;
import org.sdmx.resources.sdmxml.schemas.v20.common.TextType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.NameableType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.mapping.ItemMapBean;
import org.sdmxsource.sdmx.api.model.beans.mapping.ItemSchemeMapBean;
import org.sdmxsource.sdmx.api.model.mutable.mapping.ItemMapMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.mapping.ItemSchemeMapMutableBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * The type Item scheme map bean.
 */
public abstract class ItemSchemeMapBeanImpl extends SchemeMapBeanImpl implements ItemSchemeMapBean {
    private static final long serialVersionUID = 1L;
    /**
     * The Items.
     */
    List<ItemMapBean> items = new ArrayList<ItemMapBean>();  //Package Protected


    /**
     * Instantiates a new Item scheme map bean.
     *
     * @param bean          the bean
     * @param structureType the structure type
     * @param parent        the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ItemSchemeMapBeanImpl(ItemSchemeMapMutableBean bean, SDMX_STRUCTURE_TYPE structureType, IdentifiableBean parent) {
        super(bean, parent);
        if (bean.getItems() != null) {
            for (ItemMapMutableBean item : bean.getItems()) {
                items.add(new ItemMapBeanImpl(item, this));
            }
        }
    }

    /**
     * Instantiates a new Item scheme map bean.
     *
     * @param createdFrom   the created from
     * @param structureType the structure type
     * @param parent        the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ItemSchemeMapBeanImpl(NameableType createdFrom, SDMX_STRUCTURE_TYPE structureType, IdentifiableBean parent) {
        super(createdFrom, structureType, parent);
    }


    /**
     * Instantiates a new Item scheme map bean.
     *
     * @param createdFrom     the created from
     * @param structureType   the structure type
     * @param id              the id
     * @param uri             the uri
     * @param name            the name
     * @param description     the description
     * @param annotationsType the annotations type
     * @param parent          the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ItemSchemeMapBeanImpl(XmlObject createdFrom,
                                 SDMX_STRUCTURE_TYPE structureType, String id, String uri,
                                 List<TextType> name, List<TextType> description,
                                 AnnotationsType annotationsType, IdentifiableBean parent) {
        super(createdFrom, structureType, id, uri, name, description, annotationsType, parent);
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
            ItemSchemeMapBean that = (ItemSchemeMapBean) bean;
            if (!super.equivalent(items, that.getItems(), includeFinalProperties)) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(items, composites);
        return composites;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public List<ItemMapBean> getItems() {
        return new ArrayList<ItemMapBean>(items);
    }
}
