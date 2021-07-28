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
package org.sdmxsource.sdmx.ediparser.util;

import org.sdmxsource.sdmx.util.date.DateUtil;

import java.text.DateFormat;
import java.util.regex.Pattern;

/**
 * The type Edi date util.
 */
public class EDIDateUtil {

    /**
     * The constant DATE_FORMAT_DAILY_SHORT_YEAR.
     */
    public static final DateFormat DATE_FORMAT_DAILY_SHORT_YEAR = DateUtil.getDateFormatter("yyMMdd");
    /**
     * The constant DATE_FORMAT_DAILY_LONG_YEAR.
     */
    public static final DateFormat DATE_FORMAT_DAILY_LONG_YEAR = DateUtil.getDateFormatter("yyyyMMdd");
    /**
     * The constant DATE_FORMAT_MINUTE_SHORT_YEAR.
     */
    public static final DateFormat DATE_FORMAT_MINUTE_SHORT_YEAR = DateUtil.getDateFormatter("yyMMddHHmm");
    /**
     * The constant DATE_FORMAT_MINUTE_LONG_YEAR.
     */
    public static final DateFormat DATE_FORMAT_MINUTE_LONG_YEAR = DateUtil.getDateFormatter("yyyyMMddHHmm");
    /**
     * The constant DATE_FORMAT_YEARLY.
     */
    public static final DateFormat DATE_FORMAT_YEARLY = DateUtil.getDateFormatter("yyyy");
    /**
     * The constant DATE_FORMAT_MONTHLY.
     */
    public static final DateFormat DATE_FORMAT_MONTHLY = DateUtil.getDateFormatter("yyyyMM");
    /**
     * The constant DATE_FORMAT_WEEKLY.
     */
    public static final DateFormat DATE_FORMAT_WEEKLY = DateUtil.getDateFormatter("yyyyww");
    private static final String YEAR_SHORT = "[0-9][0-9]";
    private static final String YEAR_LONG = "[0-9][0-9][0-9][0-9]";
    /**
     * The constant HALF_YEAR.
     */
    public static final String HALF_YEAR = YEAR_LONG + "[1-2]";
    /**
     * The constant QUATERLY.
     */
    public static final String QUATERLY = YEAR_LONG + "[1-4]";
    /**
     * The constant PATTERN_HALF_YEAR.
     */
    public static final Pattern PATTERN_HALF_YEAR = Pattern.compile(HALF_YEAR);
    /**
     * The constant PATTERN_QUATERLY.
     */
    public static final Pattern PATTERN_QUATERLY = Pattern.compile(QUATERLY);
    /**
     * The constant PATTERN_YEARLY.
     */
    public static final Pattern PATTERN_YEARLY = Pattern.compile(YEAR_LONG);
    private static final String MONTH_DAY = "((01|03|05|07|08|10|12)((0[1-9])|(1[0-9])|(2[0-9])|3[0-1])" +
            "|02-((0[1-9])|(1[0-9])|(2[0-9]))" +
            "|(04|06|09|11)((0[1-9])|(1[0-9])|(2[0-9])|30))";
    /**
     * The constant PATTERN_DAILY_SHORT_YEAR.
     */
    public static final Pattern PATTERN_DAILY_SHORT_YEAR = Pattern.compile(YEAR_SHORT + MONTH_DAY);
    /**
     * The constant PATTERN_DAILY_LONG_YEAR.
     */
    public static final Pattern PATTERN_DAILY_LONG_YEAR = Pattern.compile(YEAR_LONG + MONTH_DAY);
    private static final String HOUR_MINUTE = "([0-1][0-9]|2[0-3])([0-5][0-9])";
    /**
     * The constant PATTERN_MINUTE_SHORT_YEAR.
     */
    public static final Pattern PATTERN_MINUTE_SHORT_YEAR = Pattern.compile(YEAR_SHORT + MONTH_DAY + HOUR_MINUTE);
    /**
     * The constant PATTERN_MINUTE_LONG_YEAR.
     */
    public static final Pattern PATTERN_MINUTE_LONG_YEAR = Pattern.compile(YEAR_LONG + MONTH_DAY + HOUR_MINUTE);
    private static final String MONTHLY = YEAR_LONG + "(0[1-9]|1[1-2])";
    /**
     * The constant PATTERN_MONTHLY.
     */
    public static final Pattern PATTERN_MONTHLY = Pattern.compile(MONTHLY);
    private static final String WEEKLY = YEAR_LONG + "[1-53]";
    /**
     * The constant PATTERN_WEEKLY.
     */
    public static final Pattern PATTERN_WEEKLY = Pattern.compile(WEEKLY);
}
