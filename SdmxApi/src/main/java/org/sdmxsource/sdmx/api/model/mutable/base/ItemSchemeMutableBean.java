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
package org.sdmxsource.sdmx.api.model.mutable.base;

import java.util.List;

/**
 * The interface Item scheme mutable bean.
 *
 * @param <T> the type parameter
 */
public interface ItemSchemeMutableBean<T extends ItemMutableBean> extends MaintainableMutableBean {

    /**
     * Returns the list of items, this can be null
     *
     * @return items
     */
    List<T> getItems();

    /**
     * sets the list of items, overwriting any existing list.  Will set the list to null if the passed in argument is null
     *
     * @param list the list
     */
    void setItems(List<T> list);

    /**
     * removes the item with the given id, if it exists
     *
     * @param id the id
     * @return true if the item was successfully removed, false otherwise
     */
    boolean removeItem(String id);

    /**
     * Adds an item to the list of items, creates a list if it does not already exist
     *
     * @param item the item
     */
    void addItem(T item);

    /**
     * Creates an item and adds it to the scheme
     *
     * @param id   the id
     * @param name the name
     * @return the created item
     */
    T createItem(String id, String name);


    /**
     * Is partial boolean.
     *
     * @return the boolean
     */
    boolean isPartial();

    /**
     * Sets partial.
     *
     * @param isPartial the is partial
     */
    void setPartial(boolean isPartial);

}
