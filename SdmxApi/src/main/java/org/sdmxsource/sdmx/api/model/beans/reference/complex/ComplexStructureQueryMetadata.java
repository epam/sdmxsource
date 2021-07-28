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
package org.sdmxsource.sdmx.api.model.beans.reference.complex;

import org.sdmxsource.sdmx.api.constants.COMPLEX_MAINTAINABLE_QUERY_DETAIL;
import org.sdmxsource.sdmx.api.constants.COMPLEX_STRUCTURE_QUERY_DETAIL;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.STRUCTURE_REFERENCE_DETAIL;

import java.util.List;

/**
 * Contains information about a Complex Structure Query.  The information includes the response detail,
 * and which referenced artefacts should be referenced in the response
 */
public interface ComplexStructureQueryMetadata {

    /**
     * true if the matched artefact should be returned in the query result besides the referenced artefacts.
     *
     * @return boolean
     */
    boolean isReturnedMatchedArtefact();

    /**
     * Returns the query detail for this structure query, can not be null
     *
     * @return structure query detail
     */
    COMPLEX_STRUCTURE_QUERY_DETAIL getStructureQueryDetail();

    /**
     * Returns the query details for the resolved references, can not be null
     *
     * @return references query detail
     */
    COMPLEX_MAINTAINABLE_QUERY_DETAIL getReferencesQueryDetail();

    /**
     * Returns the reference detail for this structure query, can not be null
     *
     * @return structure reference detail
     */
    STRUCTURE_REFERENCE_DETAIL getStructureReferenceDetail();

    /**
     * If STRUCTURE_REFERENCE_DETAIL == SPECIFIC, this method will return the specific structures for getting references, returns null otherwise
     *
     * @return reference specific structures
     */
    List<SDMX_STRUCTURE_TYPE> getReferenceSpecificStructures();
}
