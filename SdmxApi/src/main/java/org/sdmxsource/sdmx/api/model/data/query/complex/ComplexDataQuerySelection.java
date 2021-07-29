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
package org.sdmxsource.sdmx.api.model.data.query.complex;


import java.io.Serializable;
import java.util.Set;


/**
 * It represents a single component (dimension or attribute), <br>
 * with one or more code selections for it along with an operator.
 *
 * @author fch
 */
public interface ComplexDataQuerySelection extends Serializable {

    /**
     * Returns the component Id that the code selection(s) are for.  The component Id is either a referenece to a dimension
     * or an attribute
     *
     * @return component id
     */
    String getComponentId();

    /**
     * Returns the component value of the code that has been selected for the component.
     * <p>
     * If more then one value exists then calling this method will result in a exception.
     * Check that hasMultipleValues() returns false before calling this method
     *
     * @return value
     */
    ComplexComponentValue getValue();

    /**
     * Returns all the code values that have been selected for this component
     *
     * @return values
     */
    Set<ComplexComponentValue> getValues();

    /**
     * Returns true if more then one value exists for this component
     *
     * @return boolean
     */
    boolean hasMultipleValues();
}
