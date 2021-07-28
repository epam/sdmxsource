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
 * Defines the different versions of the SDMX-ML schema + EDI / Delimited
 *
 * @author Matt Nelson
 */
public enum SDMX_SCHEMA {
    /**
     * Version one sdmx schema.
     */
    VERSION_ONE(true),
    /**
     * Version two sdmx schema.
     */
    VERSION_TWO(true),
    /**
     * Version two point one sdmx schema.
     */
    VERSION_TWO_POINT_ONE(true),
    /**
     * Edi sdmx schema.
     */
    EDI(false),
    /**
     * Ecv sdmx schema.
     */
    ECV(false),
    /**
     * Csv sdmx schema.
     */
    CSV(false),
    /**
     * Json sdmx schema.
     */
    JSON(false),
    /**
     * Xlsx sdmx schema.
     */
    XLSX(false);

    private boolean xmlFormat;


    private SDMX_SCHEMA(boolean xmlFormat) {
        this.xmlFormat = xmlFormat;
    }

    /**
     * Returns true is this SDMX_SCHEMA is representing an XML (SDMX) format.
     *
     * @return boolean
     */
    public boolean isXmlFormat() {
        return xmlFormat;
    }

    @Override
    public String toString() {
        switch (this) {
            case VERSION_ONE:
                return "1.0";
            case VERSION_TWO:
                return "2.0";
            case VERSION_TWO_POINT_ONE:
                return "2.1";
            case CSV:
                return "CSV";
            case ECV:
                return "ECV";
            case EDI:
                return "SDMX-EDI";
            case JSON:
                return "JSON";
            case XLSX:
                return "XLSX";
        }
        return super.toString();
    }

    /**
     * Returns a slightly more human-readable version of the toString() method.
     *
     * @return A String representing this Enum
     */
    public String toEnglishString() {
        switch (this) {
            case VERSION_ONE:
            case VERSION_TWO:
            case VERSION_TWO_POINT_ONE:
                return "SDMX " + toString();
            case CSV:
            case ECV:
            case EDI:
            case JSON:
                return toString();
        }
        return toString();
    }
}
