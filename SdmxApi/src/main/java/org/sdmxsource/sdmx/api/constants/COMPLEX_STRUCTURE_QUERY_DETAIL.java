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
 * The enum Complex structure query detail.
 */
public enum COMPLEX_STRUCTURE_QUERY_DETAIL {
    /**
     * Full complex structure query detail.
     */
    FULL("Full"),
    /**
     * Stub complex structure query detail.
     */
    STUB("Stub"),
    /**
     * Complete stub complex structure query detail.
     */
    COMPLETE_STUB("CompleteStub"),
    /**
     * Matched items complex structure query detail.
     */
    MATCHED_ITEMS("MatchedItems"),
    /**
     * Cascaded matched items complex structure query detail.
     */
    CASCADED_MATCHED_ITEMS("CascadedMatchedItems");

    /**
     * The Value.
     */
    String value;

    private COMPLEX_STRUCTURE_QUERY_DETAIL(String name) {
        this.value = name;
    }

    /**
     * Returns the COMPLEX_STRUCTURE_QUERY_DETAIL equivalent of the input string (ignores case):
     * <ul>
     *  <li>Full - COMPLEX_STRUCTURE_QUERY_DETAIL.FULL</li>
     *  <li>Stub - COMPLEX_STRUCTURE_QUERY_DETAIL.STUB</li>
     *  <li>CompleteStub - COMPLEX_STRUCTURE_QUERY_DETAIL.COMPLETE_STUB</li>
     *  <li>MatchedItems - COMPLEX_STRUCTURE_QUERY_DETAIL.MATCHED_ITEMS</li>
     *  <li>CascadedMatchedItems - COMPLEX_STRUCTURE_QUERY_DETAIL.CASCADED_MATCHED_ITEMS</li>
     * </ul>
     *
     * @param str the str
     * @return complex structure query detail
     * @throws SdmxSemmanticException if the input string is not one of the strings as mentions above
     */
    public static COMPLEX_STRUCTURE_QUERY_DETAIL parseString(String str) {
        for (COMPLEX_STRUCTURE_QUERY_DETAIL currentQueryDetail : COMPLEX_STRUCTURE_QUERY_DETAIL.values()) {
            if (currentQueryDetail.value.equalsIgnoreCase(str)) {
                return currentQueryDetail;
            }
        }
        StringBuilder sb = new StringBuilder();
        String concat = "";
        for (COMPLEX_STRUCTURE_QUERY_DETAIL currentQueryDetail : COMPLEX_STRUCTURE_QUERY_DETAIL.values()) {
            sb.append(concat + currentQueryDetail.value);
            concat = ", ";
        }
        throw new SdmxSemmanticException("Unknown Parameter " + str + " allowed parameters: " + sb.toString());
    }


}
