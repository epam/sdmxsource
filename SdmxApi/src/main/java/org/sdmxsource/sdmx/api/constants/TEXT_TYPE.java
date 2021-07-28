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
package org.sdmxsource.sdmx.api.constants;

/**
 * Contains all the SDMX Text Types
 *
 * @author Matt Nelson
 */
public enum TEXT_TYPE {
    /**
     * Alpha text type.
     */
    ALPHA(false),
    /**
     * Alpha numeric text type.
     */
    ALPHA_NUMERIC(false),
    /**
     * Attachment constraint reference text type.
     */
    ATTACHMENT_CONSTRAINT_REFERENCE(false),
    /**
     * Basic time period text type.
     */
    BASIC_TIME_PERIOD(true),
    /**
     * String text type.
     */
    STRING(false),   //ALPHA
    /**
     * Big integer text type.
     */
    BIG_INTEGER(false),   //NUM
    /**
     * Integer text type.
     */
    INTEGER(false),       //NUM
    /**
     * Long text type.
     */
    LONG(false), //NUM
    /**
     * Short text type.
     */
    SHORT(false), //NUM
    /**
     * Decimal text type.
     */
    DECIMAL(false), //NUM
    /**
     * Float text type.
     */
    FLOAT(false), //NUM
    /**
     * Double text type.
     */
    DOUBLE(false), //NUM
    /**
     * Boolean text type.
     */
    BOOLEAN(false),
    /**
     * Date time text type.
     */
    DATE_TIME(true),
    /**
     * Date text type.
     */
    DATE(false),
    /**
     * Time text type.
     */
    TIME(false),
    /**
     * Year text type.
     */
    YEAR(false),
    /**
     * Month text type.
     */
    MONTH(false),
    /**
     * Numeric text type.
     */
    NUMERIC(false),
    /**
     * Day text type.
     */
    DAY(false),
    /**
     * Month day text type.
     */
    MONTH_DAY(false),
    /**
     * Year month text type.
     */
    YEAR_MONTH(false),
    /**
     * Duration text type.
     */
    DURATION(false),
    /**
     * Uri text type.
     */
    URI(false),
    /**
     * Timespan text type.
     */
    TIMESPAN(false),
    /**
     * Count text type.
     */
    COUNT(false),
    /**
     * Data set reference text type.
     */
    DATA_SET_REFERENCE(false),
    /**
     * Inclusive value range text type.
     */
    INCLUSIVE_VALUE_RANGE(false),
    /**
     * Exclusive value range text type.
     */
    EXCLUSIVE_VALUE_RANGE(false),
    /**
     * Incremental text type.
     */
    INCREMENTAL(false),
    /**
     * Observational time period text type.
     */
    OBSERVATIONAL_TIME_PERIOD(true),
    /**
     * Key values text type.
     */
    KEY_VALUES(false),
    /**
     * Time period text type.
     */
    TIME_PERIOD(false),
    /**
     * Gregorian day text type.
     */
    GREGORIAN_DAY(true),
    /**
     * Gregorian time period text type.
     */
    GREGORIAN_TIME_PERIOD(true),
    /**
     * Gregorian year text type.
     */
    GREGORIAN_YEAR(true),
    /**
     * Gregorian year month text type.
     */
    GREGORIAN_YEAR_MONTH(true),
    /**
     * Reporting day text type.
     */
    REPORTING_DAY(true),
    /**
     * Reporting month text type.
     */
    REPORTING_MONTH(true),
    /**
     * Reporting quarter text type.
     */
    REPORTING_QUARTER(true),
    /**
     * Reporting semester text type.
     */
    REPORTING_SEMESTER(true),
    /**
     * Reporting time period text type.
     */
    REPORTING_TIME_PERIOD(true),
    /**
     * Reporting trimester text type.
     */
    REPORTING_TRIMESTER(true),
    /**
     * Reporting week text type.
     */
    REPORTING_WEEK(true),
    /**
     * Reporting year text type.
     */
    REPORTING_YEAR(true),
    /**
     * Standard time period text type.
     */
    STANDARD_TIME_PERIOD(true),
    /**
     * Times range text type.
     */
    TIMES_RANGE(true),
    /**
     * Identifiable reference text type.
     */
    IDENTIFIABLE_REFERENCE(false),
    /**
     * Xhtml text type.
     */
    XHTML(false);

    private boolean isValidTimeDimensionTextType;

    private TEXT_TYPE(boolean isValidTimeDimensionTextType) {
        this.isValidTimeDimensionTextType = isValidTimeDimensionTextType;
    }

    /**
     * Returns the TEXT_SEARCH from a String. The match is insensitive.
     *
     * @param value the value
     * @return text type
     */
    public static TEXT_TYPE parseString(String value) {
        for (TEXT_TYPE ts : TEXT_TYPE.values()) {
            if (ts.toString().equalsIgnoreCase(value)) {
                return ts;
            }
        }

        throw new IllegalArgumentException("TEXT_TYPE.parseString unknown value: " + value);
    }

    /**
     * Returns true is this text type is a valid time dimension text type
     *
     * @return the boolean
     */
    public boolean isValidTimeDimensionTextType() {
        return isValidTimeDimensionTextType;
    }
}
