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
 * The enum Text search.
 */
public enum TEXT_SEARCH {
    /**
     * Contains text search.
     */
    CONTAINS("contains"),
    /**
     * Starts with text search.
     */
    STARTS_WITH("startsWith"),
    /**
     * Ends with text search.
     */
    ENDS_WITH("endsWith"),
    /**
     * Does not contain text search.
     */
    DOES_NOT_CONTAIN("doesNotContain"),
    /**
     * Does not start with text search.
     */
    DOES_NOT_START_WITH("doesNotStartWith"),
    /**
     * Does not end with text search.
     */
    DOES_NOT_END_WITH("doesNotEndWith"),
    /**
     * Equal text search.
     */
    EQUAL("equal"),
    /**
     * Not equal text search.
     */
    NOT_EQUAL("notEqual");

    /**
     * The Operator.
     */
    String operator;

    private TEXT_SEARCH(String operator) {
        this.operator = operator;
    }

    /**
     * Returns the TEXT_SEARCH from a String representing the operator name in the SDMX-ML. The match is insensitive.
     *
     * @param operator the operator
     * @return text search
     */
    public static TEXT_SEARCH parseString(String operator) {
        for (TEXT_SEARCH ts : TEXT_SEARCH.values()) {
            if (ts.getOperator().equalsIgnoreCase(operator)) {
                return ts;
            }
        }

        throw new IllegalArgumentException("TEXT_SEARCH.parseString unknown operator: " + operator);
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
