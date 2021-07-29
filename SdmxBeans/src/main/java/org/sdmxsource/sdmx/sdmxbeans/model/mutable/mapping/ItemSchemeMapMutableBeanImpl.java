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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.mapping;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.mapping.ItemMapBean;
import org.sdmxsource.sdmx.api.model.beans.mapping.ItemSchemeMapBean;
import org.sdmxsource.sdmx.api.model.mutable.mapping.ItemMapMutableBean;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Item scheme map mutable bean.
 */
public abstract class ItemSchemeMapMutableBeanImpl extends SchemeMapMutableBeanImpl {
    private static final long serialVersionUID = 1L;

    private List<ItemMapMutableBean> items = new ArrayList<ItemMapMutableBean>();

    /**
     * Instantiates a new Item scheme map mutable bean.
     *
     * @param structureType the structure type
     */
    public ItemSchemeMapMutableBeanImpl(SDMX_STRUCTURE_TYPE structureType) {
        super(structureType);
    }

    /**
     * Instantiates a new Item scheme map mutable bean.
     *
     * @param bean the bean
     */
    public ItemSchemeMapMutableBeanImpl(ItemSchemeMapBean bean) {
        super(bean);
        if (bean.getItems() != null) {
            for (ItemMapBean item : bean.getItems()) {
                items.add(new ItemMapMutableBeanImpl(item));
            }
        }
    }

    /**
     * Add item.
     *
     * @param item the item
     */
    public void addItem(ItemMapMutableBean item) {
        this.items.add(item);
    }

    /**
     * Gets items.
     *
     * @return the items
     */
    public List<ItemMapMutableBean> getItems() {
        return items;
    }

    /**
     * Sets items.
     *
     * @param items the items
     */
    public void setItems(List<ItemMapMutableBean> items) {
        this.items = items;
    }

}
