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
package org.sdmxsource.sdmx.api.model.superbeans.conceptscheme;

import org.sdmxsource.sdmx.api.model.beans.base.TextFormatBean;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptBean;
import org.sdmxsource.sdmx.api.model.superbeans.base.NameableSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodelistSuperBean;

/**
 * A concept is a characteristic.  It is the basic building block of data and metadata structures e.g all dimensions, attributes and
 * dimensions in a data structure must reference a concept.
 */
public interface ConceptSuperBean extends NameableSuperBean {

    /**
     * Returns true if this concept is a stand alone concept
     *
     * @return boolean
     */
    boolean isStandAloneConcept();

    /**
     * Returns the text format, which is typically present for an uncoded component (one which does not reference a codelist).
     * <p>
     * Returns null if there is no text format present
     *
     * @return text format
     */
    TextFormatBean getTextFormat();

    /**
     * Returns the representation of this concept - this will return null if hasRepresentation() is false
     *
     * @return core representation
     */
    CodelistSuperBean getCoreRepresentation();

    /**
     * Returns true if this concept has core representation
     *
     * @return boolean
     */
    boolean hasRepresentation();

    @Override
    ConceptBean getBuiltFrom();
}
