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
 * The interface Nameable mutable bean.
 */
public interface NameableMutableBean extends IdentifiableMutableBean {

    /**
     * Adds a name, edits the current value if the locale already exists
     *
     * @param locale the locale
     * @param name   the name
     */
    void addName(String locale, String name);

    /**
     * Adds a description, edits the current value if the locale already exists
     *
     * @param locale the locale
     * @param name   the name
     */
    void addDescription(String locale, String name);

    /**
     * Gets name.
     *
     * @param defaultIfNull the default if null
     * @return the name
     */
    String getName(boolean defaultIfNull);

    /**
     * Gets names.
     *
     * @return the names
     */
    List<TextTypeWrapperMutableBean> getNames();

    /**
     * Sets names.
     *
     * @param names the names
     */
    void setNames(List<TextTypeWrapperMutableBean> names);

    /**
     * Gets descriptions.
     *
     * @return the descriptions
     */
    List<TextTypeWrapperMutableBean> getDescriptions();

    /**
     * Sets descriptions.
     *
     * @param description the description
     */
    void setDescriptions(List<TextTypeWrapperMutableBean> description);

}
