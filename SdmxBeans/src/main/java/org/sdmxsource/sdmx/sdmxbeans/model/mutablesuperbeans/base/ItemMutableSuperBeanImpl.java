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
package org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.base;

import org.sdmxsource.sdmx.api.model.mutablesuperbeans.base.MaintainableMutableSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.base.ItemSuperBean;

/**
 * The type Item mutable super bean.
 *
 * @param <T> the type parameter
 */
public abstract class ItemMutableSuperBeanImpl<T extends MaintainableMutableSuperBean> extends NameableMutableSuperBeanImpl {
    private static final long serialVersionUID = 1L;

    private T itemScheme;

    /**
     * Instantiates a new Item mutable super bean.
     *
     * @param itemScheme the item scheme
     * @param item       the item
     */
    public ItemMutableSuperBeanImpl(T itemScheme, @SuppressWarnings("rawtypes") ItemSuperBean item) {
        super(item);
        this.itemScheme = itemScheme;
    }

    /**
     * Instantiates a new Item mutable super bean.
     */
    public ItemMutableSuperBeanImpl() {

    }

    /**
     * Gets item scheme.
     *
     * @return the item scheme
     */
    public T getItemScheme() {
        return itemScheme;
    }

    /**
     * Sets item scheme.
     *
     * @param itemScheme the item scheme
     */
    public void setItemScheme(T itemScheme) {
        this.itemScheme = itemScheme;
    }
}
