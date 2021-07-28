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
 * This enumeration has as possible values the operators for the range of a numeric and a time value.
 * The NOT_EQUAL value does not apply for the operator for a time value.
 */
public enum ORDERED_OPERATOR {
    /**
     * Greater than or equal ordered operator.
     */
    GREATER_THAN_OR_EQUAL("greaterThanOrEqual"),
    /**
     * Less than or equal ordered operator.
     */
    LESS_THAN_OR_EQUAL("lessThanOrEqual"),
    /**
     * Greater than ordered operator.
     */
    GREATER_THAN("greaterThan"),
    /**
     * Less than ordered operator.
     */
    LESS_THAN("lessThan"),
    /**
     * Equal ordered operator.
     */
    EQUAL("equal"),
    /**
     * Not equal ordered operator.
     */
    NOT_EQUAL("notEqual");

    /**
     * The Operator.
     */
    String operator;

    private ORDERED_OPERATOR(String operator) {
        this.operator = operator;
    }

    /**
     * Returns the ORDERED_OPERATOR equivalent of the input string (ignores case)
     *
     * @param str the str
     * @return ordered operator
     */
    public static ORDERED_OPERATOR parseString(String str) {
        for (ORDERED_OPERATOR currentOrderedOperator : ORDERED_OPERATOR.values()) {
            if (currentOrderedOperator.operator.equalsIgnoreCase(str)) {
                return currentOrderedOperator;
            }
        }
        StringBuilder sb = new StringBuilder();
        String concat = "";
        for (ORDERED_OPERATOR currentOrderedOperator : ORDERED_OPERATOR.values()) {
            sb.append(concat + currentOrderedOperator.toString());
            concat = ", ";
        }
        throw new SdmxSemmanticException("Unknown Parameter " + str + " allowed parameters: " + sb.toString());
    }

    /**
     * Gets operator.
     *
     * @return the operator
     */
    public String getOperator() {
        return operator;
    }
}
