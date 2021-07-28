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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.reference.complex;

import org.sdmxsource.sdmx.api.constants.COMPLEX_MAINTAINABLE_QUERY_DETAIL;
import org.sdmxsource.sdmx.api.constants.COMPLEX_STRUCTURE_QUERY_DETAIL;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.STRUCTURE_REFERENCE_DETAIL;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.reference.complex.ComplexStructureQueryMetadata;

import java.util.List;

/**
 * The type Complex structure query metadata.
 */
public class ComplexStructureQueryMetadataImpl implements ComplexStructureQueryMetadata {

    private STRUCTURE_REFERENCE_DETAIL referenceDetail;
    private COMPLEX_STRUCTURE_QUERY_DETAIL queryDetail = COMPLEX_STRUCTURE_QUERY_DETAIL.FULL;
    private boolean returnMatchedartefact;
    private List<SDMX_STRUCTURE_TYPE> referenceSpecificStructures;
    private COMPLEX_MAINTAINABLE_QUERY_DETAIL referencesQueryDetail = COMPLEX_MAINTAINABLE_QUERY_DETAIL.FULL;

    /**
     * Instantiates a new Complex structure query metadata.
     *
     * @param returnMatchedartefact       the return matchedartefact
     * @param queryDetail                 the query detail
     * @param referencesQueryDetail       the references query detail
     * @param referenceDetail             the reference detail
     * @param referenceSpecificStructures the reference specific structures
     */
    public ComplexStructureQueryMetadataImpl(boolean returnMatchedartefact, COMPLEX_STRUCTURE_QUERY_DETAIL queryDetail, COMPLEX_MAINTAINABLE_QUERY_DETAIL referencesQueryDetail,
                                             STRUCTURE_REFERENCE_DETAIL referenceDetail, List<SDMX_STRUCTURE_TYPE> referenceSpecificStructures) {

        this.returnMatchedartefact = returnMatchedartefact;
        if (queryDetail != null) {
            this.queryDetail = queryDetail;
        }

        if (referencesQueryDetail != null)
            this.referencesQueryDetail = referencesQueryDetail;

        if (referenceDetail == null) {
            throw new SdmxSemmanticException("Reference Detail cannot be null.");
        }

        this.referenceDetail = referenceDetail;
        this.referenceSpecificStructures = referenceSpecificStructures;

    }

    @Override
    public boolean isReturnedMatchedArtefact() {
        return returnMatchedartefact;
    }

    @Override
    public COMPLEX_STRUCTURE_QUERY_DETAIL getStructureQueryDetail() {
        return queryDetail;
    }

    @Override
    public STRUCTURE_REFERENCE_DETAIL getStructureReferenceDetail() {
        return referenceDetail;
    }

    @Override
    public COMPLEX_MAINTAINABLE_QUERY_DETAIL getReferencesQueryDetail() {
        return referencesQueryDetail;
    }

    @Override
    public List<SDMX_STRUCTURE_TYPE> getReferenceSpecificStructures() {
        return referenceSpecificStructures;
    }

}
