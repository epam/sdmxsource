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

import org.sdmxsource.sdmx.api.model.beans.base.SdmxStructureBean;
import org.sdmxsource.sdmx.api.model.beans.base.TimeRangeBean;

import java.util.Date;


/**
 * The interface Time range mutable bean.
 */
public interface TimeRangeMutableBean extends MutableBean {

    /**
     * If true then start date and end date both have a value, and the range is between the start and end dates.
     * <p>
     * If false, then only the start date or end date will be populated, if the start date is populated then it this period refers
     * to dates before the start date. If the end date is populated then it refers to dates after the end date.
     *
     * @return boolean
     */
    boolean isRange();

    /**
     * Sets is range.
     *
     * @param isRange the is range
     */
    void setIsRange(boolean isRange);

    /**
     * Returns the Start Date - if range is true, or the Before date if range is false
     *
     * @return start date
     */
    Date getStartDate();

    /**
     * Sets start date.
     *
     * @param start the start
     */
    void setStartDate(Date start);

    /**
     * Returns the End Date - if range is true, or the After date if range is false
     *
     * @return end date
     */
    Date getEndDate();

    /**
     * Sets end date.
     *
     * @param end the end
     */
    void setEndDate(Date end);

    /**
     * Returns true if the start date is included in the range
     *
     * @return boolean
     */
    boolean isStartInclusive();

    /**
     * Sets is start inclusive.
     *
     * @param includeStart the include start
     */
    void setIsStartInclusive(boolean includeStart);

    /**
     * Returns true if the end date is included in the range
     *
     * @return boolean
     */
    boolean isEndInclusive();

    /**
     * Sets is end inclusive.
     *
     * @param includeEnd the include end
     */
    void setIsEndInclusive(boolean includeEnd);

    /**
     * Create immutable instance time range bean.
     *
     * @param parent the parent
     * @return the time range bean
     */
    TimeRangeBean createImmutableInstance(SdmxStructureBean parent);
}
