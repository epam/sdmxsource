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
 * For a 2.1 REST structure query, this enum contains a list of all the possible reference parameters
 *
 * @author Matt Nelson
 */
public enum STRUCTURE_REFERENCE_DETAIL {
    /**
     * None structure reference detail.
     */
    NONE("none"),
    /**
     * Parents structure reference detail.
     */
    PARENTS("parents"),
    /**
     * Parents siblings structure reference detail.
     */
    PARENTS_SIBLINGS("parentsandsiblings"),
    /**
     * Children structure reference detail.
     */
    CHILDREN("children"),
    /**
     * Descendants structure reference detail.
     */
    DESCENDANTS("descendants"),
    /**
     * All structure reference detail.
     */
    ALL("all"),
    /**
     * Specific structure reference detail.
     */
    SPECIFIC("");

    private String value;


    private STRUCTURE_REFERENCE_DETAIL(String name) {
        this.value = name;
    }

    /**
     * Returns the STRUCTURE_REFERENCE_DETAIL equivalent of the input string (ignores case):
     * <ul>
     *  <li>none - STRUCTURE_REFERENCE_DETAIL.NONE</li>
     *  <li>parents - STRUCTURE_REFERENCE_DETAIL.PARENTS</li>
     *  <li>parentsandsiblings - STRUCTURE_REFERENCE_DETAIL.PARENTS_SIBLINGS</li>
     *  <li>children - STRUCTURE_REFERENCE_DETAIL.CHILDREN</li>
     *  <li>descendants - STRUCTURE_REFERENCE_DETAIL.DESCENDANTS</li>
     *  <li>all - STRUCTURE_REFERENCE_DETAIL.ALL</li>
     *  <li>(empty string) - STRUCTURE_REFERENCE_DETAIL.SPECIFIC</li>
     * </ul>
     *
     * @param str the str
     * @return structure reference detail
     * @throws SdmxSemmanticException if the input string is not one of the strings as mentions above
     */
    public static STRUCTURE_REFERENCE_DETAIL parseString(String str) {
        for (STRUCTURE_REFERENCE_DETAIL currentQueryDetail : STRUCTURE_REFERENCE_DETAIL.values()) {
            if (currentQueryDetail.value.equalsIgnoreCase(str)) {
                return currentQueryDetail;
            }
        }
        StringBuilder sb = new StringBuilder();
        String concat = "";
        try {
            SDMX_STRUCTURE_TYPE structType = SDMX_STRUCTURE_TYPE.parseClass(str);
            if (!structType.isMaintainable()) {
                for (SDMX_STRUCTURE_TYPE currentStructType : SDMX_STRUCTURE_TYPE.values()) {
                    if (currentStructType.isMaintainable()) {
                        sb.append(concat + currentStructType.getUrnClass().toLowerCase());
                        concat = ", ";
                    }
                }
                throw new SdmxSemmanticException("Disallowed structure type " + structType.getUrnClass().toLowerCase() + " allowed parameters: " + sb.toString());
            }
        } catch (Throwable th) {
            for (STRUCTURE_REFERENCE_DETAIL currentQueryDetail : STRUCTURE_REFERENCE_DETAIL.values()) {
                sb.append(concat + currentQueryDetail.value);
                concat = ", ";
            }

            throw new SdmxSemmanticException("Unknown Parameter " + str + " allowed parameters: " + sb.toString() + " or a specific structure reference such as 'codelist'");
        }
        return STRUCTURE_REFERENCE_DETAIL.SPECIFIC;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
