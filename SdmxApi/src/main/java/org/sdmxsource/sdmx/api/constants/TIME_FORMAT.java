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
 * Defines different time formats supported by SDMX-ML
 *
 * @author Matt Nelson
 */
public enum TIME_FORMAT {
    /**
     * Year time format.
     */
    YEAR("A", "Yearly"),
    /**
     * The Half of year.
     */
    HALF_OF_YEAR("S", "Half Yearly"),
    /**
     * The Third of year.
     */
    THIRD_OF_YEAR("T", "Trimesterly (Third of Year)"),
    /**
     * Quarter of year time format.
     */
    QUARTER_OF_YEAR("Q", "Quarterly"),
    /**
     * Month time format.
     */
    MONTH("M", "Monthly"),
    /**
     * Week time format.
     */
    WEEK("W", "Weekly"),
    /**
     * Date time format.
     */
    DATE("D", "Daily"),
    /**
     * Hour time format.
     */
    HOUR("H", "Hourly"),
    /**
     * The Date time.
     */
    DATE_TIME("I", "Date Time");

    private String frequencyCode;
    private String readableCode;

    private TIME_FORMAT(String frequencyCode, String readableCode) {
        this.frequencyCode = frequencyCode;
        this.readableCode = readableCode;
    }

    /**
     * Returns the time format from the code Id (case sensitive) - the code ids are listed in the getFrequencyCodeId() method
     *
     * @param codeId the code id
     * @return time format from code id
     * @throws IllegalArgumentException if the code Id does not match any ids
     * @see getFrequencyCodeId
     */
    public static TIME_FORMAT getTimeFormatFromCodeId(String codeId) {
        for (TIME_FORMAT currentTimeFormat : TIME_FORMAT.values()) {
            if (currentTimeFormat.getFrequencyCodeId().equals(codeId)) {
                return currentTimeFormat;
            }
        }
        StringBuilder sb = new StringBuilder();
        String concat = "";
        for (TIME_FORMAT currentTimeFormat : TIME_FORMAT.values()) {
            sb.append(concat + currentTimeFormat.getFrequencyCodeId());
            concat = ",";
        }
        throw new IllegalArgumentException("Time format can not be found for code id : " + codeId + " allowed values are : " + sb.toString());
    }

    /**
     * Returns a human readable string representation of this time format
     *
     * @return readable code
     */
    public String getReadableCode() {
        return readableCode;
    }

    /**
     * Returns an Id reprentation of this TIME_FORMAT:
     * <ul>
     *  <li>A = TIME_FORMAT.YEAR</li>
     *  <li>S = TIME_FORMAT.HALF_OF_YEAR</li>
     *  <li>T = TIME_FORMAT.THIRD_OF_YEAR</li>
     *  <li>Q = TIME_FORMAT.QUARTER_OF_YEAR</li>
     *  <li>M = TIME_FORMAT.MONTH</li>
     *  <li>W = TIME_FORMAT.WEEK</li>
     *  <li>D = TIME_FORMAT.DATE</li>
     *  <li>H = TIME_FORMAT.HOUR</li>
     *  <li>I = TIME_FORMAT.DATE_TIME</li>
     * </ul>
     *
     * @return frequency code id
     */
    public String getFrequencyCodeId() {
        return frequencyCode;
    }
}
