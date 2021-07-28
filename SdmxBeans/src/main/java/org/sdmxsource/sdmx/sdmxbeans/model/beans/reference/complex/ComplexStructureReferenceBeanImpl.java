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

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.reference.complex.*;

/**
 * The type Complex structure reference bean.
 */
public class ComplexStructureReferenceBeanImpl extends ComplexNameableReferenceImpl
        implements ComplexStructureReferenceBean {

    private ComplexTextReference id;
    private ComplexTextReference agencyId;
    private ComplexVersionReference versionRef;
    private ComplexIdentifiableReferenceBean childRef;

    /**
     * Instantiates a new Complex structure reference bean.
     *
     * @param agencyId       the agency id
     * @param id             the id
     * @param versionRef     the version ref
     * @param structureType  the structure type
     * @param annotationRef  the annotation ref
     * @param nameRef        the name ref
     * @param descriptionRef the description ref
     * @param childRef       the child ref
     */
    public ComplexStructureReferenceBeanImpl(ComplexTextReference agencyId, ComplexTextReference id, ComplexVersionReference versionRef,
                                             SDMX_STRUCTURE_TYPE structureType,
                                             ComplexAnnotationReference annotationRef,
                                             ComplexTextReference nameRef,
                                             ComplexTextReference descriptionRef,
                                             ComplexIdentifiableReferenceBean childRef
    ) {
        super(structureType, annotationRef, nameRef, descriptionRef);

        this.id = id;
        this.agencyId = agencyId;
        this.versionRef = versionRef;
        //TODO childRef defaults && null check...
        this.childRef = childRef;
    }

    @Override
    public ComplexTextReference getId() {
        return id;
    }

    @Override
    public ComplexTextReference getAgencyId() {
        return agencyId;
    }

    @Override
    public ComplexVersionReference getVersionReference() {
        return versionRef;
    }

    @Override
    public ComplexIdentifiableReferenceBean getChildReference() {
        return childRef;
    }


}
