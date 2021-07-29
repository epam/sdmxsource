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
import org.sdmxsource.sdmx.api.model.beans.base.ItemSchemeBean;
import org.sdmxsource.sdmx.api.model.mutable.base.ItemMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.ItemSchemeMutableBean;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Item scheme mutable bean.
 *
 * @param <T> the type parameter
 */
public abstract class ItemSchemeMutableBeanImpl<T extends ItemMutableBean> extends MaintainableMutableBeanImpl implements ItemSchemeMutableBean<T> {
    private static final long serialVersionUID = 1L;

    private List<T> items = new ArrayList<T>();
    private boolean isPartial;

    /**
     * Instantiates a new Item scheme mutable bean.
     *
     * @param bean the bean
     */
    @SuppressWarnings("rawtypes")
    public ItemSchemeMutableBeanImpl(ItemSchemeBean bean) {
        super(bean);
        this.isPartial = bean.isPartial();
    }

    /**
     * Instantiates a new Item scheme mutable bean.
     *
     * @param structureType the structure type
     */
    public ItemSchemeMutableBeanImpl(SDMX_STRUCTURE_TYPE structureType) {
        super(structureType);
    }


    @Override
    public boolean isPartial() {
        return isPartial;
    }

    @Override
    public void setPartial(boolean isPartial) {
        this.isPartial = isPartial;
    }

    @Override
    public List<T> getItems() {
        return items;
    }

    @Override
    public void setItems(List<T> list) {
        this.items = list;
    }

    @Override
    public boolean removeItem(String id) {
        if (items != null && id != null) {
            T item = null;
            for (T currentItem : items) {
                if (currentItem.getId().equals(id)) {
                    item = currentItem;
                    break;
                }
            }

            if (item != null) {
                return items.remove(item);
            }
        }
        return false;
    }

    @Override
    public void addItem(T item) {
        if (items == null) {
            this.items = new ArrayList<T>();
        }
        items.add(item);
    }
}
