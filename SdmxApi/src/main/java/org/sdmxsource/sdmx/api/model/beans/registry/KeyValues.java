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
package org.sdmxsource.sdmx.api.model.beans.registry;

import org.sdmxsource.sdmx.api.model.beans.base.SdmxStructureBean;
import org.sdmxsource.sdmx.api.model.beans.base.TimeRangeBean;

import java.util.List;


/**
 * Represents an SDMX KeyValues as defined in a SDMX ContentConstraint
 *
 * @author Matt Nelson
 */
public interface KeyValues extends SdmxStructureBean {

    /**
     * Returns the identifier of the component that the values are for
     *
     * @return id
     */
    String getId();

    /**
     * Returns a copy of the list of the allowed / disallowed values in the constraint.
     * <p>
     * This is mutually exclusive with the getTimeRange() method, and will return an empty list if getTimeRange() returns a non-null value
     *
     * @return values
     */
    List<String> getValues();

    /**
     * Returns a list of all the values which are cascade values
     *
     * @return cascade values
     */
    List<String> getCascadeValues();

    /**
     * Where the values are coded, and the codes have child codes, if a value if to be cascaded, then include all the child
     * codes in the constraint for inclusion / exclusion
     *
     * @param value - the code id to check if it is to be cascaded (must exist in the getValues() list)
     * @return true if the value is set to be cascaded
     */
    boolean isCascadeValue(String value);

    /**
     * Returns the time range that is being constrained, this is mutually exclusive with the getValues()
     *
     * @return time range
     */
    TimeRangeBean getTimeRange();
}
