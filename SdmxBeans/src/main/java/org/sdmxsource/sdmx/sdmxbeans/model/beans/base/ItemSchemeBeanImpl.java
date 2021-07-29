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

import org.apache.xmlbeans.XmlObject;
import org.sdmx.resources.sdmxml.schemas.v20.common.AnnotationsType;
import org.sdmx.resources.sdmxml.schemas.v20.common.TextType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.ItemSchemeType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TERTIARY_BOOL;
import org.sdmxsource.sdmx.api.model.beans.base.HierarchicalItemBean;
import org.sdmxsource.sdmx.api.model.beans.base.ItemBean;
import org.sdmxsource.sdmx.api.model.beans.base.ItemSchemeBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.mutable.base.ItemMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.ItemSchemeMutableBean;

import java.net.URL;
import java.util.*;


/**
 * The type Item scheme bean.
 *
 * @param <T> the type parameter
 */
public abstract class ItemSchemeBeanImpl<T extends ItemBean> extends MaintainableBeanImpl implements ItemSchemeBean<T> {
    private static final long serialVersionUID = -7101534599216735610L;
    /**
     * The Items.
     */
    protected List<T> items = new ArrayList<T>();
    private boolean isPartial;

    /**
     * Instantiates a new Item scheme bean.
     *
     * @param bean           the bean
     * @param actualLocation the actual location
     * @param isServiceUrl   the is service url
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM ITSELF, CREATES STUB BEAN //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected ItemSchemeBeanImpl(ItemSchemeBean<T> bean, URL actualLocation, boolean isServiceUrl) {
        super(bean, actualLocation, isServiceUrl);
        this.isPartial = bean.isPartial();
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM READER                    //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
//	public ItemSchemeBeanImpl(SDMX_STRUCTURE_TYPE structure, SdmxReader reader) {
//		super(structure, reader);
//		String partial = reader.getAttributeValue("isPartial", false);
//		if(partial != null) {
//			isPartial = Boolean.parseBoolean(partial);
//		}
//	}

    /**
     * Instantiates a new Item scheme bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEAN				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @SuppressWarnings("rawtypes")
    protected ItemSchemeBeanImpl(ItemSchemeMutableBean bean) {
        super(bean);
        this.isPartial = bean.isPartial();
    }


    /**
     * Instantiates a new Item scheme bean.
     *
     * @param createdFrom   the created from
     * @param structureType the structure type
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected ItemSchemeBeanImpl(ItemSchemeType createdFrom, SDMX_STRUCTURE_TYPE structureType) {
        super(createdFrom, structureType);
        this.isPartial = createdFrom.getIsPartial();
    }

    /**
     * Instantiates a new Item scheme bean.
     *
     * @param createdFrom         the created from
     * @param structureType       the structure type
     * @param endDate             the end date
     * @param startDate           the start date
     * @param version             the version
     * @param isFinal             the is final
     * @param agencyId            the agency id
     * @param id                  the id
     * @param uri                 the uri
     * @param name                the name
     * @param description         the description
     * @param isExternalReference the is external reference
     * @param annotationsType     the annotations type
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected ItemSchemeBeanImpl(XmlObject createdFrom, SDMX_STRUCTURE_TYPE structureType,
                                 Object endDate,
                                 Object startDate,
                                 String version,
                                 TERTIARY_BOOL isFinal,
                                 String agencyId,
                                 String id,
                                 String uri,
                                 List<TextType> name,
                                 List<TextType> description,
                                 TERTIARY_BOOL isExternalReference,
                                 AnnotationsType annotationsType) {
        super(createdFrom, structureType, endDate, startDate, version, isFinal,
                agencyId, id, uri, name, description, isExternalReference, annotationsType);
    }

    /**
     * Instantiates a new Item scheme bean.
     *
     * @param createdFrom         the created from
     * @param structureType       the structure type
     * @param version             the version
     * @param agencyId            the agency id
     * @param id                  the id
     * @param uri                 the uri
     * @param name                the name
     * @param isExternalReference the is external reference
     * @param annotationsType     the annotations type
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected ItemSchemeBeanImpl(XmlObject createdFrom,
                                 SDMX_STRUCTURE_TYPE structureType,
                                 String version,
                                 String agencyId,
                                 String id,
                                 String uri,
                                 List<org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.common.TextType> name,
                                 TERTIARY_BOOL isExternalReference,
                                 org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.common.AnnotationsType annotationsType) {
        super(createdFrom, structureType, version,
                agencyId, id, uri, name, isExternalReference, annotationsType);
    }


    /**
     * Deep equals internal boolean.
     *
     * @param bean                   the bean
     * @param includeFinalProperties the include final properties
     * @return the boolean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////DEEP EQUALS							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected boolean deepEqualsInternal(ItemSchemeBean<T> bean, boolean includeFinalProperties) {
        if (bean == null) {
            return false;
        }
        if (!super.equivalent(bean.getItems(), getItems(), includeFinalProperties)) {
            return false;
        }
        if (isPartial != bean.isPartial()) {
            return false;
        }
        return super.deepEqualsInternal(bean, includeFinalProperties);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(items, composites);
        return composites;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean isPartial() {
        return isPartial;
    }

    @Override
    public List<T> getItems() {
        if (items == null) {
            return new ArrayList<T>();
        }
        return new ArrayList<T>(items);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////FILTER								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public ItemSchemeBean<T> filterItems(Collection<String> filterIds, boolean isKeepSet) {
        ItemSchemeMutableBean<ItemMutableBean> mutableScheme = (ItemSchemeMutableBean<ItemMutableBean>) getMutableInstance();

        Collection<String> removeSet = null;
        if (isKeepSet) {
            Set<String> itemsInScheme = new HashSet<String>();
            for (T item : getItems()) {
                addToSet(filterIds, itemsInScheme, item.getFullIdPath(false));
                if (item instanceof HierarchicalItemBean) {
                    populateFilterSet(((HierarchicalItemBean) item).getItems(), itemsInScheme, filterIds);
                }
            }
            if (itemsInScheme.size() == 0) {
                return this;
            }
            removeSet = itemsInScheme;
        } else {
            removeSet = filterIds;
        }
        for (String currentRemoveId : removeSet) {
            mutableScheme.removeItem(currentRemoveId);
        }
        mutableScheme.setPartial(true);
        return (ItemSchemeBean<T>) mutableScheme.getImmutableInstance();
    }

    private void populateFilterSet(List<HierarchicalItemBean> items, Set<String> itemsInScheme, Collection<String> filterIds) {
        for (HierarchicalItemBean item : items) {
            addToSet(filterIds, itemsInScheme, item.getFullIdPath(false));
        }
    }

    /**
     * Adds the itemId to the itemsInScheme set if they do not appear in the filtersId set.
     * <p>
     * This method should not add an id if it is the child of a filter id, example if a filter Id is 'A.B' then 'A.B.C' should not be added as it
     * is a child.
     *
     * @param filterIds     ids which should not be added
     * @param itemsInScheme the set to add to
     * @param itemId        this is to add to the itemsInScheme Set
     */
    private void addToSet(Collection<String> filterIds, Set<String> itemsInScheme, String itemId) {
        for (String filterId : filterIds) {
            if (itemId.equals(filterId)) {
                //Do not Add
                return;
            }
            if (filterId.contains(".") && filterId.startsWith(itemId)) {
                //Do not add
                return;
            }
        }
        itemsInScheme.add(itemId);
    }
}
