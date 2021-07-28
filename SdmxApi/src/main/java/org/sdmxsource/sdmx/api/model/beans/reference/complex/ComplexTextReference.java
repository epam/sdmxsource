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
package org.sdmxsource.sdmx.api.model.beans.reference.complex;

import org.sdmxsource.sdmx.api.constants.TEXT_SEARCH;

/**
 * Used to search for items with certain text fields
 *
 * @see ComplexStructureReferenceBean
 */
public interface ComplexTextReference {

    /**
     * Returns the language to search, this can be null
     *
     * @return language
     */
    String getLanguage();

    /**
     * Returns the operator to apply, can not be null, defaults to EQUAL
     *
     * @return operator
     */
    TEXT_SEARCH getOperator();

    /**
     * Returns the seach String, can not be null or empty
     *
     * @return search parameter
     */
    String getSearchParameter();
}
