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
package org.sdmxsource.sdmx.api.model.superbeans.datastructure;

import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.superbeans.base.ComponentSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.conceptscheme.ConceptSchemeSuperBean;

/**
 * A dimension is one component of a key that together with other dimensions of the key
 * uniquely identify the observation.
 *
 * @author Matt Nelson
 */
public interface DimensionSuperBean extends ComponentSuperBean {

    /**
     * Returns true if this dimension is the frequency dimension, false otherwise.
     *
     * @return true if this dimension is the frequency dimension, false otherwise.
     */
    boolean isFrequencyDimension();

    /**
     * Returns true if this dimension is the measure dimension, false otherwise.
     *
     * @return true if this dimension is the measure dimension, false otherwise.
     */
    boolean isMeasureDimension();

    /**
     * Return the Concept Scheme that is used to represent the measure dimension.
     * <p>
     * If isMeasureDimension() returns true, then this will not return null. Otherwise it will return null.
     *
     * @return measure representation
     */
    ConceptSchemeSuperBean getMeasureRepresentation();

    /**
     * Returns true if this dimension is the time dimension, false otherwise.
     *
     * @return true if this dimension is the time dimension, false otherwise.
     */
    boolean isTimeDimension();

    @Override
    DimensionBean getBuiltFrom();
}