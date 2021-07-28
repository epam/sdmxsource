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
package org.sdmxsource.sdmx.api.model.base;

import org.sdmxsource.sdmx.api.constants.TIME_FORMAT;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;


/**
 * An SDMX date contains a Java Date, and gives access to the String representation
 * and TIME_FORMAT which is used to define the date.
 */
public interface SdmxDate extends Serializable {

    /**
     * Returns a copy of the date which can never be null.
     *
     * @return date
     */
    Date getDate();

    /**
     * Gets date as calendar.
     *
     * @return the date as calendar
     */
    Calendar getDateAsCalendar();

    /**
     * Returns the time format for the date - returns null if this information is not present
     *
     * @return time format of date
     */
    TIME_FORMAT getTimeFormatOfDate();

    /**
     * Returns the Date in SDMX format
     *
     * @return the date in sdmx format
     */
    String getDateInSdmxFormat();

    /**
     * Returns true if this date is later then the date provided
     *
     * @param date the date
     * @return boolean
     */
    boolean isLater(SdmxDate date);
}
