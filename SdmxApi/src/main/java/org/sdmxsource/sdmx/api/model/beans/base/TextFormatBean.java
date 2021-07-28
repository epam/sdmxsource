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
package org.sdmxsource.sdmx.api.model.beans.base;

import org.sdmxsource.sdmx.api.constants.TERTIARY_BOOL;
import org.sdmxsource.sdmx.api.constants.TEXT_TYPE;
import org.sdmxsource.sdmx.api.model.base.SdmxDate;

import java.math.BigDecimal;
import java.math.BigInteger;


/**
 * TextFormatBean gives information about the restrictions on a text value
 *
 * @author Matt Nelson
 */
public interface TextFormatBean extends SDMXBean {

    /**
     * Returns the type the values the text value can take
     *
     * @return TEXT_TYPE or null if not defined
     */
    TEXT_TYPE getTextType();

    /**
     * Returns the earliest start time the value can take -
     *
     * @return SdmxDate or null if not defined
     */
    SdmxDate getStartTime();

    /**
     * Returns the latest end time the value can take -
     *
     * @return null if not defined
     */
    SdmxDate getEndTime();

    /**
     * Indicates whether the values are intended to be ordered, and it may work in combination with the interval attribute
     * <p>
     * If null, then interpret as N/A
     *
     * @return sequence sequence
     */
    TERTIARY_BOOL getSequence();

    /**
     * Returns the minimum length of text field that the value must take
     * <p>
     * If null, then interpret as N/A
     *
     * @return min length
     */
    BigInteger getMinLength();

    /**
     * Returns the maximum length of text field that the value must take
     * <p>
     * If null, then interpret as N/A
     *
     * @return max length
     */
    BigInteger getMaxLength();

    /**
     * Returns the start value of text field that the value must take
     * <p>
     * If null, then interpret as N/A
     *
     * @return start value
     */
    BigDecimal getStartValue();

    /**
     * Returns the end value of text field that the value must take
     * <p>
     * If null, then interpret as N/A
     *
     * @return end value
     */
    BigDecimal getEndValue();

    /**
     * Returns the maximum value that the numerical value must take
     * <p>
     * If null, then interpret as N/A
     *
     * @return max value
     */
    BigDecimal getMaxValue();

    /**
     * Returns the maximum value that the numerical value must take
     * <p>
     * If null, then interpret as N/A
     *
     * @return min value
     */
    BigDecimal getMinValue();

    /**
     * Returns the interval of for the text values, this is typically given if isInterval() is true
     * <p>
     * If null, then interpret as N/A
     *
     * @return interval interval
     */
    BigDecimal getInterval();

    /**
     * Returns the interval of for the text values, this is typically given if isInterval() is true
     * <p>
     * If null, then interpret as N/A
     *
     * @return time interval
     */
    String getTimeInterval();

    /**
     * Returns the number of decimal places, if the type is number
     * <p>
     * If null, then interpret as N/A
     *
     * @return decimals decimals
     */
    BigInteger getDecimals();

    /**
     * Returns the pattern that the reported value must adhere to
     *
     * @return pattern pattern
     */
    String getPattern();


    /**
     * Gets multi lingual.
     *
     * @return the multi lingual
     */
    TERTIARY_BOOL getMultiLingual();

    /**
     * Returns whether this TextFormat enforces restrictions.
     *
     * @return the boolean
     */
    boolean hasRestrictions();
}
