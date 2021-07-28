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
package org.sdmxsource.sdmx.api.model.data.query;

import org.sdmxsource.sdmx.api.model.base.SdmxDate;

import java.io.Serializable;
import java.util.Set;


/**
 * A DataQuerySelectionGroup contains a set of DataQuerySelections which are implicitly ANDED together.
 * Each DataQuerySelection contains a concept along with one or more selection values.
 * <p>
 * For example a DataQuerySelection could be FREQ=A,B which would equate to FREQ = A OR B.
 * <p>
 * When there are more than one DataQuerySelections they are ANDED together. For example the DataQuerySelections:
 * <pre>
 * DataQuerySelection FREQ=A,B
 * DataQuerySelection COUNTRY=UK
 * </pre>
 * Equate to:
 * <pre>(FREQ = A OR B) AND (COUNTRY = UK)
 * </pre>
 *
 * @author Matt Nelson
 */
public interface DataQuerySelectionGroup extends Serializable {

    /**
     * Returns the selection(s) for the given component id (dimension or attribtue) or returns null if no selection exists for the component id.
     *
     * @param componentId the component id
     * @return selections for concept
     */
    DataQuerySelection getSelectionsForConcept(String componentId);

    /**
     * Returns true if selections exist for this dimension Id.
     *
     * @param componentId the component id
     * @return true if selections exist for this dimension Id.
     */
    boolean hasSelectionForConcept(String componentId);

    /**
     * Returns the set of selections for this group. These DataQuerySelections are implicitly ANDED together.
     *
     * @return selections
     */
    Set<DataQuerySelection> getSelections();

    /**
     * Returns the "date from" in this selection group.
     *
     * @return date from
     */
    SdmxDate getDateFrom();

    /**
     * Returns the "date to" in this selection group.
     *
     * @return date to
     */
    SdmxDate getDateTo();
}
