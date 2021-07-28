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
package org.sdmxsource.sdmx.api.model.query;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.STRUCTURE_QUERY_DETAIL;
import org.sdmxsource.sdmx.api.constants.STRUCTURE_REFERENCE_DETAIL;

import java.io.Serializable;

/**
 * Contains information about a Structure Query.  The information includes the response detail,
 * and which referenced artefacts should be  referenced in the response
 */
public interface StructureQueryMetadata extends Serializable {


    /**
     * Returns true if this is a query for all of the latest versions of the given artefacts
     *
     * @return the boolean
     */
    boolean isReturnLatest();

    /**
     * Returns the query detail for this structure query, can not be null
     *
     * @return structure query detail
     */
    STRUCTURE_QUERY_DETAIL getStructureQueryDetail();

    /**
     * Returns the reference detail for this structure query, can not be null
     *
     * @return structure reference detail
     */
    STRUCTURE_REFERENCE_DETAIL getStructureReferenceDetail();

    /**
     * If STRUCTURE_REFERENCE_DETAIL == SPECIFIC, this method will return the specific structure reference, returns null otherwise
     *
     * @return specific structure reference
     */
    SDMX_STRUCTURE_TYPE getSpecificStructureReference();
}
