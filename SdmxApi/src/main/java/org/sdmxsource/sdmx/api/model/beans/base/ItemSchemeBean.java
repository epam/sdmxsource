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
package org.sdmxsource.sdmx.api.model.beans.base;

import java.util.Collection;
import java.util.List;


/**
 * An ItemScheme contains a list of items
 *
 * @param <T> the type parameter
 * @author Matt Nelson
 */
public interface ItemSchemeBean<T extends ItemBean> extends MaintainableBean {

    /**
     * Returns the list of items contained within this scheme.
     *
     * <b>NOTE</b>The list is a copy so modify the returned set will not
     * be reflected in the ItemSchemeBean instance
     *
     * @return items items
     */
    List<T> getItems();

    /**
     * Returns true if this is a partial item scheme
     *
     * @return boolean
     */
    boolean isPartial();

    /**
     * Returns a new item scheme based on the item filters, which are either removed or kept depending on the isKeepSet argument
     *
     * @param filterIds the filter ids
     * @param isKeepSet if true then the returned item scheme will contain only items with these ids
     * @return item scheme bean
     */
    ItemSchemeBean<T> filterItems(Collection<String> filterIds, boolean isKeepSet);
}
