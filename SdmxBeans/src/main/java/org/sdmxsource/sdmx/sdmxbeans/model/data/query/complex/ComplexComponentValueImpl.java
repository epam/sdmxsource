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
package org.sdmxsource.sdmx.sdmxbeans.model.data.query.complex;

import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.ORDERED_OPERATOR;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TEXT_SEARCH;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.data.query.complex.ComplexComponentValue;


/**
 * The type Complex component value.
 */
public class ComplexComponentValueImpl implements ComplexComponentValue {

    private String value;
    private TEXT_SEARCH textOperator;
    private ORDERED_OPERATOR orderedOperator;

    /**
     * Instantiates a new Complex component value.
     *
     * @param value         the value
     * @param textOperator  the text operator
     * @param componentType the component type
     */
    public ComplexComponentValueImpl(String value, TEXT_SEARCH textOperator, SDMX_STRUCTURE_TYPE componentType) {

        if (componentType.equals(SDMX_STRUCTURE_TYPE.DIMENSION) || componentType.equals(SDMX_STRUCTURE_TYPE.TIME_DIMENSION))
            throw new SdmxSemmanticException(ExceptionCode.QUERY_SELECTION_ILLEGAL_OPERATOR);

        this.value = value;
        if (textOperator != null)
            this.textOperator = textOperator;
        else
            this.textOperator = TEXT_SEARCH.EQUAL;
    }

    /**
     * Instantiates a new Complex component value.
     *
     * @param value           the value
     * @param orderedOperator the ordered operator
     * @param componentType   the component type
     */
    public ComplexComponentValueImpl(String value, ORDERED_OPERATOR orderedOperator, SDMX_STRUCTURE_TYPE componentType) {

        if (componentType.equals(SDMX_STRUCTURE_TYPE.TIME_DIMENSION) && orderedOperator.equals(ORDERED_OPERATOR.NOT_EQUAL))
            throw new SdmxSemmanticException(ExceptionCode.QUERY_SELECTION_ILLEGAL_OPERATOR);

        this.value = value;
        if (orderedOperator != null)
            this.orderedOperator = orderedOperator;
        else
            this.orderedOperator = ORDERED_OPERATOR.EQUAL;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public TEXT_SEARCH getTextSearchOperator() {
        return textOperator;
    }

    @Override
    public ORDERED_OPERATOR getOrderedOperator() {
        return orderedOperator;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ComplexComponentValue && ((ComplexComponentValue) obj).getValue().equals(this.value) &&
                ((ComplexComponentValue) obj).getOrderedOperator().equals(this.orderedOperator) && ((ComplexComponentValue) obj).getTextSearchOperator().equals(this.textOperator)) {
            return true;
        } else
            return false;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Operator");
        sb.append(" : ");
        if (textOperator != null)
            sb.append(textOperator.toString());
        if (orderedOperator != null)
            sb.append(orderedOperator.toString());
        sb.append("applied upon ");
        sb.append(value);
        return sb.toString();
    }
}
