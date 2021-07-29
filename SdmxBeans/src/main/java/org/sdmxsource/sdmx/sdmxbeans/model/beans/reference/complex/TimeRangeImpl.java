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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.reference.complex;

import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.base.SdmxDate;
import org.sdmxsource.sdmx.api.model.beans.reference.complex.TimeRange;

/**
 * The type Time range.
 */
public class TimeRangeImpl implements TimeRange {

    private boolean range;
    private SdmxDate startDate;
    private SdmxDate endDate;
    private boolean startInclusive = true;
    private boolean endInclusive = true;

    /**
     * Instantiates a new Time range.
     *
     * @param range          the range
     * @param startDate      the start date
     * @param endDate        the end date
     * @param startInclusive the start inclusive
     * @param endInclusive   the end inclusive
     */
    public TimeRangeImpl(boolean range, SdmxDate startDate, SdmxDate endDate, boolean startInclusive, boolean endInclusive) {
        this.range = range;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startInclusive = startInclusive;
        this.endInclusive = endInclusive;

        if (startDate == null && endDate == null) {
            throw new SdmxSemmanticException("When setting range, cannot have both start/end periods null.");
        }

        if (isRange()) {
            if (startDate == null || endDate == null) {
                throw new SdmxSemmanticException("When range is defined then both start/end periods should be set.");
            }
        } else {
            if (startDate != null && endDate != null) {
                throw new SdmxSemmanticException("When it is not a range then not both start/end periods can be set.");
            }
        }
    }

    @Override
    public boolean isRange() {
        return range;
    }

    @Override
    public SdmxDate getStartDate() {
        return startDate;
    }

    @Override
    public SdmxDate getEndDate() {
        return endDate;
    }

    @Override
    public boolean isStartInclusive() {
        return startInclusive;
    }

    @Override
    public boolean isEndInclusive() {
        return endInclusive;
    }

}
