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
 * Enumerated list of all the different data formats that SDMX supports.
 * <p>
 * DATA_TYPE contains the means to retrieve the underlying <b>SDMX_SCHEMA ENUM</b> and the
 * underlying <b>BASE_DATA_FORMAT</b> ENUM
 *
 * @see SDMX_SCHEMA
 * @see BASE_DATA_FORMAT
 */
public enum DATA_TYPE {
    /**
     * Generic 1 0 data type.
     */
    GENERIC_1_0(SDMX_SCHEMA.VERSION_ONE, BASE_DATA_FORMAT.GENERIC),
    /**
     * Cross sectional 1 0 data type.
     */
    CROSS_SECTIONAL_1_0(SDMX_SCHEMA.VERSION_ONE, BASE_DATA_FORMAT.CROSS_SECTIONAL),
    /**
     * Utility 1 0 data type.
     */
    UTILITY_1_0(SDMX_SCHEMA.VERSION_ONE, BASE_DATA_FORMAT.UTILITY),
    /**
     * Compact 1 0 data type.
     */
    COMPACT_1_0(SDMX_SCHEMA.VERSION_ONE, BASE_DATA_FORMAT.COMPACT),
    /**
     * Generic 2 0 data type.
     */
    GENERIC_2_0(SDMX_SCHEMA.VERSION_TWO, BASE_DATA_FORMAT.GENERIC),
    /**
     * Cross sectional 2 0 data type.
     */
    CROSS_SECTIONAL_2_0(SDMX_SCHEMA.VERSION_TWO, BASE_DATA_FORMAT.CROSS_SECTIONAL),
    /**
     * Utility 2 0 data type.
     */
    UTILITY_2_0(SDMX_SCHEMA.VERSION_TWO, BASE_DATA_FORMAT.UTILITY),
    /**
     * Compact 2 0 data type.
     */
    COMPACT_2_0(SDMX_SCHEMA.VERSION_TWO, BASE_DATA_FORMAT.COMPACT),
    /**
     * Message group 1 0 compact data type.
     */
    MESSAGE_GROUP_1_0_COMPACT(SDMX_SCHEMA.VERSION_ONE, BASE_DATA_FORMAT.COMPACT),
    /**
     * Message group 1 0 generic data type.
     */
    MESSAGE_GROUP_1_0_GENERIC(SDMX_SCHEMA.VERSION_ONE, BASE_DATA_FORMAT.GENERIC),
    /**
     * Message group 1 0 utility data type.
     */
    MESSAGE_GROUP_1_0_UTILITY(SDMX_SCHEMA.VERSION_ONE, BASE_DATA_FORMAT.UTILITY),
    /**
     * Message group 2 0 compact data type.
     */
    MESSAGE_GROUP_2_0_COMPACT(SDMX_SCHEMA.VERSION_TWO, BASE_DATA_FORMAT.COMPACT),
    /**
     * Message group 2 0 generic data type.
     */
    MESSAGE_GROUP_2_0_GENERIC(SDMX_SCHEMA.VERSION_TWO, BASE_DATA_FORMAT.GENERIC),
    /**
     * Message group 2 0 utility data type.
     */
    MESSAGE_GROUP_2_0_UTILITY(SDMX_SCHEMA.VERSION_TWO, BASE_DATA_FORMAT.UTILITY),
    /**
     * Generic 2 1 data type.
     */
    GENERIC_2_1(SDMX_SCHEMA.VERSION_TWO_POINT_ONE, BASE_DATA_FORMAT.GENERIC),
    /**
     * Compact 2 1 data type.
     */
    COMPACT_2_1(SDMX_SCHEMA.VERSION_TWO_POINT_ONE, BASE_DATA_FORMAT.COMPACT),
    /**
     * Generic 2 1 xs data type.
     */
    GENERIC_2_1_XS(SDMX_SCHEMA.VERSION_TWO_POINT_ONE, BASE_DATA_FORMAT.CROSS_SECTIONAL),
    /**
     * Compact 2 1 xs data type.
     */
    COMPACT_2_1_XS(SDMX_SCHEMA.VERSION_TWO_POINT_ONE, BASE_DATA_FORMAT.CROSS_SECTIONAL),
    /**
     * Edi ts data type.
     */
    EDI_TS(SDMX_SCHEMA.EDI, BASE_DATA_FORMAT.EDI),
    /**
     * Sdmx json data type.
     */
    SDMXJSON(SDMX_SCHEMA.JSON, BASE_DATA_FORMAT.SDMXJSON),
    /**
     * Csv data type.
     */
    CSV(SDMX_SCHEMA.CSV, BASE_DATA_FORMAT.CSV);


    private SDMX_SCHEMA schemaVersion;
    private BASE_DATA_FORMAT baseDataFormat;

    private DATA_TYPE(SDMX_SCHEMA schemaVersion, BASE_DATA_FORMAT baseDataFormat) {
        this.schemaVersion = schemaVersion;
        this.baseDataFormat = baseDataFormat;
    }

    /**
     * Gets base data format.
     *
     * @return the base data format
     */
    public BASE_DATA_FORMAT getBaseDataFormat() {
        return baseDataFormat;
    }

    /**
     * Gets schema version.
     *
     * @return the schema version
     */
    public SDMX_SCHEMA getSchemaVersion() {
        return schemaVersion;
    }

    @Override
    public String toString() {
        return baseDataFormat.toString() + " " + schemaVersion.toString();
    }
}
