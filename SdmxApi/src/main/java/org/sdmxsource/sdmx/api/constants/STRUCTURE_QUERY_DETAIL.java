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

import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;

/**
 * For a 2.1 REST structure query, this represents the detail parameter
 *
 * @author Matt Nelson
 */
public enum STRUCTURE_QUERY_DETAIL {
    /**
     * Full structure query detail.
     */
    FULL("full"),
    /**
     * All stubs structure query detail.
     */
    ALL_STUBS("allstubs"),
    /**
     * Referenced stubs structure query detail.
     */
    REFERENCED_STUBS("referencestubs");

    /**
     * The Value.
     */
    String value;

    private STRUCTURE_QUERY_DETAIL(String name) {
        this.value = name;
    }

    /**
     * Returns the STRUCTURE_QUERY_DETAIL equivalent of the input string (ignores case):
     * <ul>
     *  <li>full - STRUCTURE_QUERY_DETAIL.FULL</li>
     *  <li>allstubs - STRUCTURE_QUERY_DETAIL.ALL_STUBS</li>
     *  <li>referencestubs - STRUCTURE_QUERY_DETAIL.REFERENCED_STUBS</li>
     * </ul>
     *
     * @param str the str
     * @return structure query detail
     * @throws SdmxSemmanticException if the input string is not one of the strings as mentions above
     */
    public static STRUCTURE_QUERY_DETAIL parseString(String str) {
        for (STRUCTURE_QUERY_DETAIL currentQueryDetail : STRUCTURE_QUERY_DETAIL.values()) {
            if (currentQueryDetail.value.equalsIgnoreCase(str)) {
                return currentQueryDetail;
            }
        }
        StringBuilder sb = new StringBuilder();
        String concat = "";
        for (STRUCTURE_QUERY_DETAIL currentQueryDetail : STRUCTURE_QUERY_DETAIL.values()) {
            sb.append(concat + currentQueryDetail.value);
            concat = ", ";
        }
        throw new SdmxSemmanticException("Unknown Parameter " + str + " allowed parameters: " + sb.toString());
    }

    @Override
    public String toString() {
        return value;
    }
}
