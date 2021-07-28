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
package org.sdmxsource.sdmx.sdmxbeans.model.data.query;

import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.data.query.DataQuerySelection;
import org.sdmxsource.util.ObjectUtil;

import java.util.HashSet;
import java.util.Set;


/**
 * The type Data query dimension selection.
 */
public class DataQueryDimensionSelectionImpl implements DataQuerySelection {
    private static final long serialVersionUID = -7578131456147153451L;
    private String concept;
    private Set<String> values = new HashSet<String>();

    /**
     * Instantiates a new Data query dimension selection.
     *
     * @param concept the concept
     * @param value   the value
     */
    public DataQueryDimensionSelectionImpl(String concept, String... value) {
        if (!ObjectUtil.validString(concept)) {
            throw new SdmxSemmanticException(ExceptionCode.QUERY_SELECTION_MISSING_CONCEPT);
        }
        this.concept = concept;
        if (value == null || value.length == 0) {
            throw new SdmxSemmanticException(ExceptionCode.QUERY_SELECTION_MISSING_CONCEPT_VALUE);
        }
        for (String currentValue : value) {
            values.add(currentValue);
        }
    }

    /**
     * Instantiates a new Data query dimension selection.
     *
     * @param concept the concept
     * @param values  the values
     */
    public DataQueryDimensionSelectionImpl(String concept, Set<String> values) {
        if (!ObjectUtil.validString(concept)) {
            throw new SdmxSemmanticException(ExceptionCode.QUERY_SELECTION_MISSING_CONCEPT);
        }
        this.concept = concept;
        if (!ObjectUtil.validCollection(values)) {
            throw new SdmxSemmanticException(ExceptionCode.QUERY_SELECTION_MISSING_CONCEPT_VALUE);
        }
        this.values = new HashSet<String>(values);
    }

    @Override
    public String getComponentId() {
        return concept;
    }

    @Override
    public String getValue() {
        if (values.size() > 1) {
            throw new IllegalArgumentException("More then one value exists for this selection");
        }
        return (String) values.toArray()[0];
    }

    /**
     * Add value.
     *
     * @param value the value
     */
    public void addValue(String value) {
        this.values.add(value);
    }

    @Override
    public Set<String> getValues() {
        return values;
    }

    @Override
    public boolean hasMultipleValues() {
        return values.size() > 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DataQuerySelection) {
            DataQuerySelection that = (DataQuerySelection) obj;
            if (this.getComponentId().equals(that.getComponentId())) {
                if (this.getValues().containsAll(that.getValues())) {
                    if (that.getValues().containsAll(this.getValues())) {
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
        sb.append(concept);
        sb.append("");
        sb.append(" : ");
        String concat = "";
        for (String currentValue : values) {
            sb.append(concat);
            sb.append(currentValue);
            concat = ",";
        }
        return sb.toString();
    }
}
