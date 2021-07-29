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
package org.sdmxsource.sdmx.api.manager.retrieval;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.AgencyMetadata;

import java.util.List;

/**
 * Provides meta information about the contents of a structure source
 */
public interface SdmxStructureContentsInformationManager {

    /**
     * Returns a count of maintainable structures for the given structure type and agency.
     *
     * @param structureType the maintainable structure type
     * @param agencyId      the agency to count the structures for
     * @return a count of the number of maintainables for the given structure type and agency.
     * @throws IllegalArgumentException if either argument supplied is null, or it is not a maintainable structure type
     */
    int getCountOfMaintainables(String agencyId, SDMX_STRUCTURE_TYPE structureType) throws IllegalArgumentException;

    /**
     * @return a list of all available languages available at this structure source
     */
    List<String> getAllLanguages();

    /**
     * @return a List of AgencyMetadata objects containing information about the Agencies in the system
     */
    List<AgencyMetadata> getAllAgencies();
}
