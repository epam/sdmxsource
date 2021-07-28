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
package org.sdmxsource.sdmx.structureparser.workspace.impl;

import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.complex.ComplexStructureQuery;
import org.sdmxsource.sdmx.structureparser.workspace.QueryWorkspace;

import java.util.ArrayList;
import java.util.List;


//JAVADOC missing

/**
 * The type Query workspace.
 */
public class QueryWorkspaceImpl implements QueryWorkspace {
    private StructureReferenceBean provisionReferences;
    private StructureReferenceBean registrationReferences;
    private List<StructureReferenceBean> simpleStructureQueries;
    private boolean resolveReferences;
    private ComplexStructureQuery complexStructureQuery;

    /**
     * Instantiates a new Query workspace.
     *
     * @param structureReference the structure reference
     * @param resolveReferences  the resolve references
     */
    public QueryWorkspaceImpl(StructureReferenceBean structureReference, boolean resolveReferences) {
        this.simpleStructureQueries = new ArrayList<StructureReferenceBean>();
        this.simpleStructureQueries.add(structureReference);
        this.resolveReferences = resolveReferences;
    }

    /**
     * Instantiates a new Query workspace.
     *
     * @param provisionReferences    the provision references
     * @param registrationReferences the registration references
     * @param structureReferences    the structure references
     * @param resolveReferences      the resolve references
     */
    public QueryWorkspaceImpl(
            StructureReferenceBean provisionReferences,
            StructureReferenceBean registrationReferences,
            List<StructureReferenceBean> structureReferences,
            boolean resolveReferences) {
        this.provisionReferences = provisionReferences;
        this.registrationReferences = registrationReferences;
        this.simpleStructureQueries = structureReferences;
        this.resolveReferences = resolveReferences;
    }

    /**
     * Instantiates a new Query workspace.
     *
     * @param complexStructureQuery the complex structure query
     */
    public QueryWorkspaceImpl(ComplexStructureQuery complexStructureQuery) {
        this.complexStructureQuery = complexStructureQuery;
    }


    @Override
    public ComplexStructureQuery getComplexStructureQuery() {
        return complexStructureQuery;
    }

    @Override
    public StructureReferenceBean getProvisionReferences() {
        return provisionReferences;
    }

    @Override
    public StructureReferenceBean getRegistrationReferences() {
        return registrationReferences;
    }

    @Override
    public List<StructureReferenceBean> getSimpleStructureQueries() {
        return simpleStructureQueries;
    }

    @Override
    public boolean hasProvisionQueries() {
        return provisionReferences != null;
    }

    @Override
    public boolean hasRegistrationQueries() {
        return registrationReferences != null;
    }

    @Override
    public boolean hasStructureQueries() {
        return simpleStructureQueries != null && simpleStructureQueries.size() > 0;
    }

    @Override
    public boolean isResolveReferences() {
        return resolveReferences;
    }
}
