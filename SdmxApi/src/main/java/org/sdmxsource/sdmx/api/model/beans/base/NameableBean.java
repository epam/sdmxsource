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

import java.util.List;


/**
 * Nameable beans carry a mandatory name and an optional description, Nameable beans are also Identifiable
 *
 * @author Matt Nelson
 */
public interface NameableBean extends IdentifiableBean {

    /**
     * Returns a list of names for this component - will return an empty list if no Names exist.
     *
     * <b>NOTE: </b>The list is a copy so modifying the returned list will not
     * be reflected in the IdentifiableBean instance
     *
     * @return first locale value it finds or null if there are none
     */
    List<TextTypeWrapper> getNames();

    /**
     * Returns the name in the default locale
     *
     * @return name name
     */
    String getName();

    /**
     * Returns a list of descriptions for this component
     *
     * <b>NOTE: </b>The list is a copy so modifying the returned list will not
     * be reflected in the IdentifiableBean instance
     *
     * @return descriptions descriptions
     */
    List<TextTypeWrapper> getDescriptions();

    /**
     * Returns the description in the default locale
     *
     * @return first locale value it finds or null if there are none
     */
    String getDescription();


}
