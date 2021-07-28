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
 * The enum Messsage function.
 */
public enum MESSSAGE_FUNCTION {
    /**
     * Statistical definitions messsage function.
     */
    STATISTICAL_DEFINITIONS("73"),
    /**
     * Statistical data messsage function.
     */
    STATISTICAL_DATA("74"),
    /**
     * Data set list messsage function.
     */
    DATA_SET_LIST("DSL");

    private String ediStr;

    private MESSSAGE_FUNCTION(String ediStr) {
        this.ediStr = ediStr;
    }

    /**
     * Gets from edi str.
     *
     * @param ediStr the edi str
     * @return the from edi str
     */
    public static MESSSAGE_FUNCTION getFromEdiStr(String ediStr) {
        for (MESSSAGE_FUNCTION currentMF : MESSSAGE_FUNCTION.values()) {
            if (currentMF.getEDIString().equals(ediStr)) {
                return currentMF;
            }
        }
        StringBuilder sb = new StringBuilder();
        String concat = "";
        for (MESSSAGE_FUNCTION currentMF : MESSSAGE_FUNCTION.values()) {
            sb.append(concat);
            sb.append(currentMF.getEDIString());
            concat = ", ";
        }
        throw new IllegalArgumentException("Unknown Message Function : " + ediStr + " (valid types are - " + sb.toString() + ")");
    }

    /**
     * Gets edi string.
     *
     * @return the edi string
     */
    public String getEDIString() {
        return ediStr;
    }

    /**
     * Returns true if the message function defines an EDI message that can contain data
     *
     * @return boolean boolean
     */
    public boolean isData() {
        return this == STATISTICAL_DATA || this == DATA_SET_LIST;
    }

    /**
     * Returns true if the message function defines an EDI message that can contain structures
     *
     * @return boolean boolean
     */
    public boolean isStructure() {
        return this == STATISTICAL_DEFINITIONS || this == DATA_SET_LIST;
    }
}
