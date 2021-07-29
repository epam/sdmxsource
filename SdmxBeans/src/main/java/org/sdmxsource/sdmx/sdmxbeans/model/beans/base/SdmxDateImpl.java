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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.base;

import org.sdmxsource.sdmx.api.constants.TIME_FORMAT;
import org.sdmxsource.sdmx.api.model.base.SdmxDate;
import org.sdmxsource.sdmx.util.date.DateUtil;

import java.util.Calendar;
import java.util.Date;


/**
 * The type Sdmx date.
 */
public class SdmxDateImpl implements SdmxDate {
    private static final long serialVersionUID = 889352854909061494L;
    private Date date;
    private TIME_FORMAT timeFormat;
    private String dateInSdmx;


    /**
     * Instantiates a new Sdmx date.
     *
     * @param date       the date
     * @param timeFormat the time format
     */
    public SdmxDateImpl(Date date, TIME_FORMAT timeFormat) {
        this.date = date;
        this.timeFormat = timeFormat;
        dateInSdmx = DateUtil.formatDate(date, timeFormat);
    }


    /**
     * Instantiates a new Sdmx date.
     *
     * @param dateInSdmx the date in sdmx
     */
    public SdmxDateImpl(String dateInSdmx) {
        this.date = DateUtil.formatDate(dateInSdmx, true);
        this.timeFormat = DateUtil.getTimeFormatOfDate(dateInSdmx);
        this.dateInSdmx = dateInSdmx;
    }


    @Override
    public boolean isLater(SdmxDate date) {
        return this.getDate().getTime() > date.getDate().getTime();
    }


    @Override
    public Date getDate() {
        return new Date(date.getTime());
    }

    @Override
    public TIME_FORMAT getTimeFormatOfDate() {
        return timeFormat;
    }

    @Override
    public String getDateInSdmxFormat() {
        return dateInSdmx;
    }

    @Override
    public Calendar getDateAsCalendar() {
        return DateUtil.createCalendar(date);
    }


    @Override
    public int hashCode() {
        return dateInSdmx.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SdmxDate) {
            SdmxDate that = (SdmxDate) obj;
            return that.getDateInSdmxFormat().equals(this.getDateInSdmxFormat());
        }
        return false;
    }


}
