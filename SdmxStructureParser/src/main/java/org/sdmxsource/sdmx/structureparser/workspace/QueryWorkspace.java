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
package org.sdmxsource.sdmx.structureparser.workspace;

import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.complex.ComplexStructureQuery;

import java.util.List;


//JAVADOC missing

/**
 * The interface Query workspace.
 */
public interface QueryWorkspace {

    /**
     * Returns a ComplexStructureQuery
     *
     * @return complex structure query
     */
    ComplexStructureQuery getComplexStructureQuery();

    /**
     * Returns a list of simple queries, these are queries by agency id, maintainable id version and id.
     * <p>
     * More complex queries such as query for DSD by dimension concept are not returned from this method call.
     *
     * @return simple structure queries
     */
    List<StructureReferenceBean> getSimpleStructureQueries();

    /**
     * Returns a list of provision references
     *
     * @return provision references
     */
    StructureReferenceBean getProvisionReferences();

    /**
     * Returns a list of registration references
     *
     * @return registration references
     */
    StructureReferenceBean getRegistrationReferences();

    /**
     * Returns true if getSimpleStructureQueries() returns a list of 1 or more or getComplexStructureQuery() returns a not null value
     *
     * @return structure queries
     */
    boolean hasStructureQueries();

    /**
     * Returns true if getProvisionReferences() returns a not null object
     *
     * @return provision queries
     */
    boolean hasProvisionQueries();

    /**
     * Returns true if getRegistrationReferences() returns a not null object
     *
     * @return registration queries
     */
    boolean hasRegistrationQueries();

    /**
     * Returns true if structure references should be resolved
     *
     * @return true if structure references should be resolved
     */
    boolean isResolveReferences();

}
