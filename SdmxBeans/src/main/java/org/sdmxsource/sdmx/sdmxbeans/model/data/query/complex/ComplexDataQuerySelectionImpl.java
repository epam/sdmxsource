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
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.data.query.complex.ComplexComponentValue;
import org.sdmxsource.sdmx.api.model.data.query.complex.ComplexDataQuerySelection;
import org.sdmxsource.util.ObjectUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * The type Complex data query selection.
 */
public class ComplexDataQuerySelectionImpl implements ComplexDataQuerySelection {

    private static final long serialVersionUID = 3184465505938333131L;
    /**
     * The Component id.
     */
    String componentId;
    private Set<ComplexComponentValue> values = new HashSet<ComplexComponentValue>();


    /**
     * Instantiates a new Complex data query selection.
     *
     * @param componentId the component id
     * @param value       the value
     */
    public ComplexDataQuerySelectionImpl(String componentId, ComplexComponentValue... value) {
        if (!ObjectUtil.validString(componentId)) {
            throw new SdmxSemmanticException(ExceptionCode.QUERY_SELECTION_MISSING_CONCEPT);
        }
        this.componentId = componentId;

        if (value == null || value.length == 0) {
            throw new SdmxSemmanticException(ExceptionCode.QUERY_SELECTION_MISSING_CONCEPT_VALUE);
        }
        //check
        for (ComplexComponentValue currentValue : value) {
            values.add(currentValue);
        }
    }

    /**
     * Instantiates a new Complex data query selection.
     *
     * @param componentId the component id
     * @param values      the values
     */
    public ComplexDataQuerySelectionImpl(String componentId, Set<ComplexComponentValue> values) {
        if (!ObjectUtil.validString(componentId)) {
            throw new SdmxSemmanticException(ExceptionCode.QUERY_SELECTION_MISSING_CONCEPT);
        }
        this.componentId = componentId;
        if (!ObjectUtil.validCollection(values)) {
            throw new SdmxSemmanticException(ExceptionCode.QUERY_SELECTION_MISSING_CONCEPT_VALUE);
        }
        this.values = new HashSet<ComplexComponentValue>(values);
    }

    /**
     * Add value.
     *
     * @param value the value
     */
    public void addValue(ComplexComponentValue value) {
        this.values.add(value);
    }

    @Override
    public String getComponentId() {
        return componentId;
    }

    @Override
    public ComplexComponentValue getValue() {
        if (values.size() > 1) {
            throw new IllegalArgumentException("More than one value exists for this selection");
        }
        return (ComplexComponentValue) values.toArray()[0];
    }

    @Override
    public Set<ComplexComponentValue> getValues() {
        return values;
    }

    @Override
    public boolean hasMultipleValues() {
        return values.size() > 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ComplexDataQuerySelection) {
            if (this.getComponentId().equals(((ComplexDataQuerySelection) obj).getComponentId())) {
                if (this.getValues().containsAll(((ComplexDataQuerySelection) obj).getValues())) {
                    if (((ComplexDataQuerySelection) obj).getValues().containsAll(this.getValues())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(componentId);
        sb.append("");
        sb.append(" : ");
        String concat = "";
        for (ComplexComponentValue currentValue : values) {
            sb.append(concat);
            sb.append(currentValue.getValue());
            concat = ",";
        }
        return sb.toString();
    }
}
