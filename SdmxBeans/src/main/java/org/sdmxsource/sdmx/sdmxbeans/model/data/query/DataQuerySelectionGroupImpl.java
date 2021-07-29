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

import org.sdmxsource.sdmx.api.model.base.SdmxDate;
import org.sdmxsource.sdmx.api.model.data.query.DataQuerySelection;
import org.sdmxsource.sdmx.api.model.data.query.DataQuerySelectionGroup;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * The type Data query selection group.
 */
public class DataQuerySelectionGroupImpl implements DataQuerySelectionGroup {
    private static final long serialVersionUID = -2517041257822059001L;
    private Set<DataQuerySelection> selections = new HashSet<DataQuerySelection>();
    private Map<String, DataQuerySelection> selectionForConcept = new HashMap<String, DataQuerySelection>();
    private SdmxDate dateFrom;
    private SdmxDate dateTo;

    /**
     * Instantiates a new Data query selection group.
     *
     * @param selections the selections
     * @param dateFrom   the date from
     * @param dateTo     the date to
     */
    public DataQuerySelectionGroupImpl(Set<DataQuerySelection> selections, SdmxDate dateFrom, SdmxDate dateTo) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        // NPE defence. If the selections are null, then exit this method.
        if (selections == null) {
            return;
        }
        this.selections = selections;
        // Add each of the Dimension Selections to the selection concept map.
        for (DataQuerySelection dimSel : selections) {
            if (selectionForConcept.containsKey(dimSel.getComponentId())) {
                //TODO Does this require a exception, or can the code selections be merged?
                throw new IllegalArgumentException("Duplicate concept");
            }
            selectionForConcept.put(dimSel.getComponentId(), dimSel);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public DataQuerySelection getSelectionsForConcept(String coneptId) {
        return selectionForConcept.get(coneptId);
    }

    @Override
    public boolean hasSelectionForConcept(String conceptId) {
        return selectionForConcept.containsKey(conceptId);
    }

    @Override
    public Set<DataQuerySelection> getSelections() {
        return new HashSet<DataQuerySelection>(selections);
    }

    @Override
    public SdmxDate getDateFrom() {
        return dateFrom;
    }

    @Override
    public SdmxDate getDateTo() {
        return dateTo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String concat = "";
        for (DataQuerySelection dqs : selections) {
            sb.append(concat + dqs.getComponentId() + " = (");
            concat = "";
            for (String val : dqs.getValues()) {
                sb.append(concat + val);
                concat = " OR ";
            }
            sb.append(")");
            concat = " AND ";
        }
        if (dateFrom != null) {
            sb.append(concat + " Date >= " + dateFrom.getDateInSdmxFormat());
            concat = " AND ";
        }
        if (dateTo != null) {
            sb.append(concat + " Date <= " + dateTo.getDateInSdmxFormat());
            concat = " AND ";
        }
        return sb.toString();
    }
}
