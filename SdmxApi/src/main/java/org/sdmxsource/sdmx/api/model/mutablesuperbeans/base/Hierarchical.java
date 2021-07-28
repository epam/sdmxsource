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
package org.sdmxsource.sdmx.api.model.mutablesuperbeans.base;

import java.util.List;

/**
 * A Hierarchical object is one which can contain a single hierarchy of objects,
 * the type of which is defined by generics.
 *
 * @param <T> - the type of object in the hierarchy
 */
public interface Hierarchical<T> {
    /**
     * Returns any children of this object.  If there are no children
     * then a <code>null</code> will be returned.
     *
     * @return child categories
     */
    List<T> getChildren();

    /**
     * Returns the parent Object of this object, a null object reference will be
     * returned if rther is no parent
     *
     * @return parent
     */
    T getParent();

    /**
     * Returns <code>true</code> if this Object contains children,
     * if this is the case the method call <code>getChildren()</code> is
     * guaranteed to return a Set with length greater then 0.
     *
     * @return true if this Hierarchical Object contains children
     */
    boolean hasChildren();

    /**
     * Returns <code>true</code> if this Object has a parent, false otherwise.
     *
     * @return true if this Object has a parent
     */
    boolean hasParent();

}
