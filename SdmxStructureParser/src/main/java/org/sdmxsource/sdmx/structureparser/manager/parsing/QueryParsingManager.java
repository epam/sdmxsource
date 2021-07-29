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
package org.sdmxsource.sdmx.structureparser.manager.parsing;

import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.structureparser.workspace.QueryWorkspace;
import org.sdmxsource.sdmx.util.exception.ParseException;


/**
 * The interface Query parsing manager.
 */
public interface QueryParsingManager {

    /**
     * Processes the SDMX at the given URI and returns a workspace containing the information on what was being queried.
     * <p>
     * The Query parsing manager processes queries that are in a RegsitryInterface document, this includes queries for
     * Provisions, Registrations and Structures.  It also processes Queries that are in a QueryMessage document
     *
     * @param dataLocation the data location
     * @return query workspace
     * @throws ParseException the parse exception
     */
    QueryWorkspace parseQueries(ReadableDataLocation dataLocation) throws ParseException;

}
