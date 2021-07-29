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
 * Contains the Data Formats, such as Generic and Compact - without specifying any SDMX schema version
 * information
 *
 * @author Matt Nelson
 */
public enum BASE_DATA_FORMAT {
    /**
     * Generic Data Messages - includes 2.1 Generic and GenericTimeSeries
     */
    GENERIC("GenericData"),
    /**
     * Relates to Compact (1.0/2.0) and StructureSpecific, StructureSpecificTime Series (2.1)
     */
    COMPACT("CompactData"),
    /**
     * Relates to Utility Data (1.0 and 2.0 only)
     */
    UTILITY("UtilityData"),
    /**
     * Relates to EDI
     */
    EDI(null),
    /**
     * Relates to 2.0 Cross Sectional Data
     */
    CROSS_SECTIONAL("CrossSectionalData"),
    /**
     * Relates to Message Group Data (2.0 only)
     */
    MESSAGE_GROUP("MessageGroup"),
    /**
     * Any type of delimited data
     */
    CSV(null),
    /**
     * Sdmx Json Format
     */
    SDMXJSON(null);

    private String rootNode;  //ROOT NODE AS IT APPEARS IN THE XML

    private BASE_DATA_FORMAT(String rootNode) {
        this.rootNode = rootNode;
    }

    public static BASE_DATA_FORMAT getDataFormat(MESSAGE_TYPE messageType) {
        switch (messageType) {
            case COMPACT_DATA:
                return COMPACT;
            case CROSS_SECTIONAL_DATA:
                return CROSS_SECTIONAL;
            case GENERIC_DATA:
                return GENERIC;
            case UTILITY_DATA:
                return UTILITY;
            default:
                throw new IllegalArgumentException(messageType + " is not a data message");
        }
    }

    public String getRootNode() {
        return rootNode;
    }

    @Override
    public String toString() {
        switch (this) {
            case COMPACT:
                return "Structure Specific (Compact)";
            case CROSS_SECTIONAL:
                return "Cross Sectional";
            case CSV:
                return "CSV";
            case EDI:
                return "EDI";
            case GENERIC:
                return "Generic";
            case MESSAGE_GROUP:
                return "Message Group";
            case SDMXJSON:
                return "SDMX JSON";
            case UTILITY:
                return "Utility";
        }
        return super.toString();
    }
}
