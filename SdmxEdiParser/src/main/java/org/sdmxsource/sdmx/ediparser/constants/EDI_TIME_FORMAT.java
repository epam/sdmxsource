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
package org.sdmxsource.sdmx.ediparser.constants;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.TIME_FORMAT;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.ediparser.util.EDIDateUtil;
import org.sdmxsource.sdmx.util.date.DateUtil;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;


/**
 * The enum Edi time format.
 */
public enum EDI_TIME_FORMAT {
    /**
     * Daily two dig year edi time format.
     */
    DAILY_TWO_DIG_YEAR("101", TIME_FORMAT.DATE, false),
    /**
     * Daily four dig year edi time format.
     */
    DAILY_FOUR_DIG_YEAR("102", TIME_FORMAT.DATE, false),
    /**
     * Minute two dig year edi time format.
     */
    MINUTE_TWO_DIG_YEAR("201", TIME_FORMAT.DATE_TIME, false),
    /**
     * Minute four dig year edi time format.
     */
    MINUTE_FOUR_DIG_YEAR("203", TIME_FORMAT.DATE_TIME, false),
    /**
     * Year edi time format.
     */
    YEAR("602", TIME_FORMAT.YEAR, false),
    /**
     * Half of year edi time format.
     */
    HALF_OF_YEAR("604", TIME_FORMAT.HALF_OF_YEAR, false),
    /**
     * Quarter of year edi time format.
     */
    QUARTER_OF_YEAR("608", TIME_FORMAT.QUARTER_OF_YEAR, false),
    /**
     * Month edi time format.
     */
    MONTH("610", TIME_FORMAT.MONTH, false),
    /**
     * Week edi time format.
     */
    WEEK("616", TIME_FORMAT.WEEK, false),
    /**
     * Range year edi time format.
     */
    RANGE_YEAR("702", TIME_FORMAT.YEAR, true),
    /**
     * Range half of year edi time format.
     */
    RANGE_HALF_OF_YEAR("704", TIME_FORMAT.HALF_OF_YEAR, true),
    /**
     * Range quarter of year edi time format.
     */
    RANGE_QUARTER_OF_YEAR("708", TIME_FORMAT.QUARTER_OF_YEAR, true),
    /**
     * Range monthly edi time format.
     */
    RANGE_MONTHLY("710", TIME_FORMAT.MONTH, true),
    /**
     * Range daily edi time format.
     */
    RANGE_DAILY("711", TIME_FORMAT.DATE, true),
    /**
     * Range weekly edi time format.
     */
    RANGE_WEEKLY("716", TIME_FORMAT.WEEK, true);


    private String ediValue;
    private TIME_FORMAT sdmxTimeFormat;
    private boolean isRange;
    private int expectedLength;

    private EDI_TIME_FORMAT(String ediValue,
                            TIME_FORMAT timeFormat,
                            boolean isRange) {
        this.ediValue = ediValue;
        this.sdmxTimeFormat = timeFormat;
        this.isRange = isRange;
    }

    /**
     * Parses the TIME_FORMAT and if this if the time format is describing a range to return the EDI_TIME_FORMAT
     *
     * @param timeFormat the time format
     * @param isRange    the is range
     * @return edi time format
     */
    public static EDI_TIME_FORMAT parseTimeFormat(TIME_FORMAT timeFormat, boolean isRange) {
        switch (timeFormat) {
            case DATE:
                return isRange ? RANGE_DAILY : DAILY_FOUR_DIG_YEAR;
            case DATE_TIME:
                return isRange ? RANGE_DAILY : MINUTE_FOUR_DIG_YEAR;
            case HALF_OF_YEAR:
                return isRange ? RANGE_HALF_OF_YEAR : HALF_OF_YEAR;
            case HOUR:
                return isRange ? RANGE_DAILY : MINUTE_FOUR_DIG_YEAR;
            case MONTH:
                return isRange ? RANGE_MONTHLY : MONTH;
            case QUARTER_OF_YEAR:
                return isRange ? RANGE_QUARTER_OF_YEAR : QUARTER_OF_YEAR;
            case WEEK:
                return isRange ? RANGE_WEEKLY : WEEK;
            case YEAR:
                return isRange ? RANGE_YEAR : YEAR;
            default:
                throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "Output EDI time format of: " + timeFormat);
        }
    }

    /**
     * Parse string edi time format.
     *
     * @param ediString the edi string
     * @return the edi time format
     */
    public static EDI_TIME_FORMAT parseString(String ediString) {
        for (EDI_TIME_FORMAT currentTimeFormat : EDI_TIME_FORMAT.values()) {
            if (currentTimeFormat.getEdiValue().equals(ediString)) {
                return currentTimeFormat;
            }
        }
        throw new IllegalArgumentException("Unknown time format : " + ediString);
    }

    /**
     * Gets expected length.
     *
     * @return the expected length
     */
    public int getExpectedLength() {
        return expectedLength;
    }

    /**
     * Is range boolean.
     *
     * @return the boolean
     */
    public boolean isRange() {
        return isRange;
    }

    /**
     * Gets sdmx time format.
     *
     * @return the sdmx time format
     */
    public TIME_FORMAT getSdmxTimeFormat() {
        return sdmxTimeFormat;
    }

    /**
     * Gets edi value.
     *
     * @return the edi value
     */
    public String getEdiValue() {
        return ediValue;
    }

    /**
     * Parses the start date of a range, or the only date in a non-range
     *
     * @param dateString the date string
     * @return date date
     */
    public Date parseDate(String dateString) {
        try {
            if (isRange) {
                return parseRange(dateString, 0);
            }
            return parseDate(dateString, this);
        } catch (ParseException e) {
            throw new SdmxSemmanticException("Could not format date of type '" + this + "' with date string '" + dateString + "'");
        }
    }

    /**
     * Format date string.
     *
     * @param date the date
     * @return the string
     */
    public String formatDate(Date date) {
        String formatted;
        Calendar cal;

        EDI_TIME_FORMAT timeFormat = this;
        switch (this) {
            case RANGE_DAILY:
                timeFormat = DAILY_FOUR_DIG_YEAR;
                break;
            case RANGE_MONTHLY:
                timeFormat = MONTH;
                break;
            case RANGE_HALF_OF_YEAR:
                timeFormat = HALF_OF_YEAR;
                break;
            case RANGE_QUARTER_OF_YEAR:
                timeFormat = QUARTER_OF_YEAR;
                break;
            case RANGE_WEEKLY:
                timeFormat = WEEK;
                break;
            case RANGE_YEAR:
                timeFormat = YEAR;
                break;
        }

        switch (timeFormat) {
            case DAILY_FOUR_DIG_YEAR:
                return EDIDateUtil.DATE_FORMAT_DAILY_LONG_YEAR.format(date);

            case DAILY_TWO_DIG_YEAR:
                return EDIDateUtil.DATE_FORMAT_DAILY_SHORT_YEAR.format(date);

            case HALF_OF_YEAR:
                formatted = EDIDateUtil.DATE_FORMAT_YEARLY.format(date);
                cal = DateUtil.getCalendar();
                cal.setTime(date);
                if (cal.get(Calendar.MONTH) <= 6) {
                    formatted += "1";
                } else {
                    formatted += "2";
                }
                return formatted;

            case MINUTE_FOUR_DIG_YEAR:
                return EDIDateUtil.DATE_FORMAT_MINUTE_LONG_YEAR.format(date);

            case MINUTE_TWO_DIG_YEAR:
                return EDIDateUtil.DATE_FORMAT_MINUTE_SHORT_YEAR.format(date);

            case MONTH:
                return EDIDateUtil.DATE_FORMAT_MONTHLY.format(date);

            case WEEK:
                // JodaTime is very useful here since it has methods to provide the week of the year
                // Important note: When using getWeekOfWeekyear() the associated year is obtained
                // through the call getWeekYear() rather than getYear().
                LocalDate dateTimeFrom = new LocalDate(date, DateTimeZone.UTC);
                int weekNum = dateTimeFrom.getWeekOfWeekyear();
                int year = dateTimeFrom.getWeekyear();

                // The EDI Format is to always have a two digit week value, so append 0 to the week if required
                if (weekNum < 10) {
                    return year + "0" + weekNum;
                }

                return year + "" + weekNum;

            case YEAR:
                return EDIDateUtil.DATE_FORMAT_YEARLY.format(date);

            case QUARTER_OF_YEAR:
                formatted = EDIDateUtil.DATE_FORMAT_YEARLY.format(date);
                cal = DateUtil.getCalendar();
                cal.setTime(date);
                if (cal.get(Calendar.MONTH) <= 2) {
                    formatted += "1";
                } else if (cal.get(Calendar.MONTH) <= 5) {
                    formatted += "2";
                } else if (cal.get(Calendar.MONTH) <= 8) {
                    formatted += "3";
                } else {
                    formatted += "4";
                }
                return formatted;

            default:
                throw new SdmxNotImplementedException("EDI date format : " + this);
        }
    }

    private Date parseDate(String ediDateString, EDI_TIME_FORMAT timeFormat) throws ParseException {
        try {
            switch (timeFormat) {
                case DAILY_FOUR_DIG_YEAR:
                    return EDIDateUtil.DATE_FORMAT_DAILY_LONG_YEAR.parse(ediDateString);

                case DAILY_TWO_DIG_YEAR:
                    return EDIDateUtil.DATE_FORMAT_DAILY_SHORT_YEAR.parse(ediDateString);

                case HALF_OF_YEAR:
                    return parseHalfYear(ediDateString);

                case MINUTE_FOUR_DIG_YEAR:
                    return EDIDateUtil.DATE_FORMAT_MINUTE_LONG_YEAR.parse(ediDateString);

                case MINUTE_TWO_DIG_YEAR:
                    return EDIDateUtil.DATE_FORMAT_MINUTE_SHORT_YEAR.parse(ediDateString);

                case MONTH:
                    return EDIDateUtil.DATE_FORMAT_MONTHLY.parse(ediDateString);

                case WEEK:
                    return EDIDateUtil.DATE_FORMAT_WEEKLY.parse(ediDateString);

                case YEAR:
                    return EDIDateUtil.DATE_FORMAT_YEARLY.parse(ediDateString);

                case QUARTER_OF_YEAR:
                    return parseQuaterYear(ediDateString);

                default:
                    throw new SdmxNotImplementedException("EDI date format : " + this);
            }
        } catch (ParseException e) {
            if (timeFormat.isRange) {
                throw new SdmxSemmanticException("Could not parse date '" + ediDateString + "' edi time format '" + timeFormat.getEdiValue() + "' relates to " + timeFormat.getSdmxTimeFormat().getReadableCode() + " data, and is a range, and is therefore expected to provide a date with '" + timeFormat.getExpectedLength() + "' characters  ");
            }
            throw new SdmxSemmanticException("Could not parse date '" + ediDateString + "' edi time format '" + timeFormat.getEdiValue() + "' relates to " + timeFormat.getSdmxTimeFormat().getReadableCode() + " data is expected to provide a date with '" + timeFormat.getExpectedLength() + "' characters ");
        }
    }

    private Date parseRange(String dateString, int range) throws ParseException {
        int dateLength;
        EDI_TIME_FORMAT tf;
        switch (this) {
            case RANGE_DAILY:
                dateLength = 8;
                tf = DAILY_FOUR_DIG_YEAR;
                break;
            case RANGE_HALF_OF_YEAR:
                dateLength = 5;
                tf = HALF_OF_YEAR;
                break;
            case RANGE_MONTHLY:
                dateLength = 6;
                tf = MONTH;
                break;
            case RANGE_QUARTER_OF_YEAR:
                dateLength = 5;
                tf = QUARTER_OF_YEAR;
                break;
            case RANGE_WEEKLY:
                dateLength = 6;
                tf = WEEK;
                break;
            case RANGE_YEAR:
                dateLength = 4;
                tf = YEAR;
                break;
            default:
                throw new SdmxNotImplementedException("EDI date format : " + this);
        }
        int startIdx = range * dateLength;
        int endIdx = range * dateLength + dateLength;

        try {
            String split = dateString.substring(startIdx, endIdx);
            return parseDate(split, tf);
        } catch (IndexOutOfBoundsException e) {
            String errorMessage =
                    "Time Period not consistent with time format code. " +
                            "Time period '" + dateString + "'. Time format code '" + this + "'";
            throw new SdmxSemmanticException(errorMessage);
        }
    }

    private Date parseHalfYear(String dateString) throws ParseException {
        String yearHalf = dateString.substring(4);
        String year = dateString.substring(0, 4);
        int half = Integer.parseInt(yearHalf);
        switch (half) {
            case 1:
                year += "0630";
                break;
            case 2:
                year += "1231";
                break;
        }
        return EDIDateUtil.DATE_FORMAT_DAILY_LONG_YEAR.parse(year);
    }

    private Date parseQuaterYear(String dateString) throws ParseException {
        String yearQuater = dateString.substring(4);
        String year = dateString.substring(0, 4);
        int quarter = Integer.parseInt(yearQuater);
        switch (quarter) {
            case 1:
                year += "0331";
                break;
            case 2:
                year += "0630";
                break;
            case 3:
                year += "0930";
                break;
            case 4:
                year += "1231";
                break;
        }
        return EDIDateUtil.DATE_FORMAT_DAILY_LONG_YEAR.parse(year);
    }

    /**
     * Parses the end date of a range, throws exception if attempting to parse a non-range
     *
     * @param dateString the date string
     * @return date date
     */
    public Date parseEndDate(String dateString) {
        try {
            if (isRange) {
                return parseRange(dateString, 1);
            }
            throw new RuntimeException("Attempting to parse non-range date as a range:  '" + dateString + "'");
        } catch (ParseException e) {
            throw new SdmxSemmanticException("Could not format date of type '" + this + "' with date string '" + dateString + "'");
        }
    }
}
