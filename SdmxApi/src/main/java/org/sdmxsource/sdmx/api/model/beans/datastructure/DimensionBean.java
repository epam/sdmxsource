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
package org.sdmxsource.sdmx.api.model.beans.datastructure;

import org.sdmxsource.sdmx.api.model.beans.base.ComponentBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;

import java.util.List;


/**
 * Represents an SDMX Dimension in a Data Structure.
 * <p>
 * A dimension defines the semantic using a <code>ConceptBean</code>, and the representation using either <code>CodelistBean</code>
 * a <code>TextFormatBean</code> or just plain text.
 *
 * @author Matt Nelson
 */
public interface DimensionBean extends ComponentBean, Comparable<DimensionBean> {
    /**
     * The constant TIME_DIMENSION_FIXED_ID.
     */
    String TIME_DIMENSION_FIXED_ID = "TIME_PERIOD";

    /**
     * Returns true if this dimension is the measure dimension.
     *
     * <b>NOTE: </b> This is mutually exclusive with isFrequencyDimension() and isTimeDimension().
     *
     * @return true if this dimension is the measure dimension.
     */
    boolean isMeasureDimension();

    /**
     * Returns true if this dimension is the frequency dimension.
     *
     * <b>NOTE: </b> This is mutually exclusive with isMeasureDimension() and isTimeDimension().
     *
     * @return true if this dimension is the frequency dimension.
     */
    boolean isFrequencyDimension();

    /**
     * Returns true if this dimension is the time dimension.
     *
     * <b>NOTE: </b> This is mutually exclusive with isFrequencyDimension() and isMeasureDimension().
     *
     * @return true if this dimension is the time dimension.
     */
    boolean isTimeDimension();

    /**
     * Returns the index of this dimension in the dimension list.
     *
     * @return the index of this dimension in the dimension list.
     */
    int getPosition();

    /**
     * Returns cross references to the concept(s) that are defining the role of this dimension.
     * The returned list is a copy of the underlying list.
     * <p>
     * Returns an empty list if there are no concept roles defined.
     *
     * @return concept role
     */
    List<CrossReferenceBean> getConceptRole();

}
