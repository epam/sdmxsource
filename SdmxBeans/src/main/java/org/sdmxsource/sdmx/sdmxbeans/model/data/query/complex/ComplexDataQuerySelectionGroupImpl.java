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
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.base.SdmxDate;
import org.sdmxsource.sdmx.api.model.data.query.complex.ComplexComponentValue;
import org.sdmxsource.sdmx.api.model.data.query.complex.ComplexDataQuerySelection;
import org.sdmxsource.sdmx.api.model.data.query.complex.ComplexDataQuerySelectionGroup;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The type Complex data query selection group.
 */
public class ComplexDataQuerySelectionGroupImpl implements ComplexDataQuerySelectionGroup {

    private static final long serialVersionUID = 6323143498242961423L;

    private Set<ComplexDataQuerySelection> complexSelections = new HashSet<ComplexDataQuerySelection>();
    private Set<ComplexComponentValue> primaryMeasureValues = new HashSet<ComplexComponentValue>();
    private Map<String, ComplexDataQuerySelection> complexSelectionForConcept = new HashMap<String, ComplexDataQuerySelection>();
    private SdmxDate dateFrom;
    private ORDERED_OPERATOR dateToOperator;
    private ORDERED_OPERATOR dateFromOperator;
    private SdmxDate dateTo;

    /**
     * Instantiates a new Complex data query selection group.
     *
     * @param complexSelections    the complex selections
     * @param dateFrom             the date from
     * @param dateFromOperator     the date from operator
     * @param dateTo               the date to
     * @param dateToOperator       the date to operator
     * @param primaryMeasureValues the primary measure values
     */
    public ComplexDataQuerySelectionGroupImpl(Set<ComplexDataQuerySelection> complexSelections, SdmxDate dateFrom, ORDERED_OPERATOR dateFromOperator, SdmxDate dateTo, ORDERED_OPERATOR dateToOperator, Set<ComplexComponentValue> primaryMeasureValues) {
        //check if the operator to be applied on the time has not the 'NOT_EQUAL' value
        if (dateFromOperator.equals(ORDERED_OPERATOR.NOT_EQUAL) || dateToOperator.equals(ORDERED_OPERATOR.NOT_EQUAL))
            throw new SdmxSemmanticException(ExceptionCode.QUERY_SELECTION_ILLEGAL_OPERATOR);

        if (complexSelections == null) {
            return;
        }

        this.dateFrom = dateFrom;
        this.dateFromOperator = dateFromOperator;
        this.dateTo = dateTo;
        this.dateToOperator = dateToOperator;
        this.complexSelections = complexSelections;
        this.primaryMeasureValues = primaryMeasureValues;

        // Add each of the Component Selections to the selection concept map.
        for (ComplexDataQuerySelection compSel : complexSelections) {
            if (complexSelectionForConcept.containsKey(compSel.getComponentId())) {
                //TODO Does this require a exception, or can the code selections be merged?
                throw new IllegalArgumentException("Duplicate concept");
            }
            complexSelectionForConcept.put(compSel.getComponentId(), compSel);
        }
    }


    @Override
    public ComplexDataQuerySelection getSelectionsForConcept(String componentId) {
        return complexSelectionForConcept.get(componentId);
    }

    @Override
    public boolean hasSelectionForConcept(String componentId) {
        return complexSelectionForConcept.containsKey(componentId);
    }

    @Override
    public Set<ComplexDataQuerySelection> getSelections() {
        return complexSelections;
    }

    @Override
    public SdmxDate getDateFrom() {
        return dateFrom;
    }

    @Override
    public ORDERED_OPERATOR getDateFromOperator() {
        return dateFromOperator;
    }

    @Override
    public SdmxDate getDateTo() {
        return dateTo;
    }

    @Override
    public ORDERED_OPERATOR getDateToOperator() {
        return dateToOperator;
    }

    @Override
    public Set<ComplexComponentValue> getPrimaryMeasureValue() {
        return primaryMeasureValues;
    }

}
