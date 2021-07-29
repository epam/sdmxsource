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

/**
 * The type Edi constants.
 */
public class EDI_CONSTANTS {
    /**
     * The constant CHARSET_ENCODING.
     */
    public final static String CHARSET_ENCODING = "ISO-8859-1";
    /**
     * The constant PLUS.
     */
    public static final String PLUS = "+";
    /**
     * The constant COLON.
     */
    public static final String COLON = ":";
    /**
     * The constant END_TAG.
     */
    public static final String END_TAG = "'";
    /**
     * The constant NEW_LINE.
     */
    public static final String NEW_LINE = "/n";
    /**
     * The constant END_OF_LINE_REG_EX.
     */
    public static final String END_OF_LINE_REG_EX = "(?<!\\?)\\'";
    /**
     * The constant MISSING_VAL.
     */
    public static final String MISSING_VAL = "-";
    /**
     * The constant EDI_LENIENT_MODE.
     */
    public static final String EDI_LENIENT_MODE = "EDI_LENIENT_MODE";
    /**
     * The constant DEFAULT_FIELD_LENGTH_CODED.
     */
    public static String DEFAULT_FIELD_LENGTH_CODED = "AN..18";
    /**
     * The constant DEFAULT_FIELD_LENGTH_UNCODED.
     */
    public static String DEFAULT_FIELD_LENGTH_UNCODED = "AN..35";
    /**
     * The constant DEFAULT_FIELD_LENGTH_PRIMARY_MEASURE.
     */
    public static String DEFAULT_FIELD_LENGTH_PRIMARY_MEASURE = "AN..15";
}
