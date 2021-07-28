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
package org.sdmxsource.sdmx.api.model.superbeans.base;

import java.util.Locale;
import java.util.Map;

/**
 * The interface Nameable super bean.
 */
public interface NameableSuperBean extends IdentifiableSuperBean {

    /**
     * Returns the description of the Identifiable Object for the given Locale
     *
     * @param loc the loc
     * @return description description
     */
    String getDescription(Locale loc);

    /**
     * Returns the Description of the Identifiable object for a the default Locale
     *
     * @return description description
     */
    String getDescription();

    /**
     * Returns all the descriptions for each locale, the map is (locale, description)
     *
     * @return descriptions descriptions
     */
    Map<Locale, String> getDescriptions();

    /**
     * Returns the Name of the Identifiable object for a given Locale
     *
     * @param loc the loc
     * @return name name
     */
    String getName(Locale loc);

    /**
     * Returns the Name of the Identifiable object for a the default Locale
     *
     * @return name name
     */
    String getName();

    /**
     * Returns all the names for each locale, the map is (locale, description)
     *
     * @return names names
     */
    Map<Locale, String> getNames();

}
