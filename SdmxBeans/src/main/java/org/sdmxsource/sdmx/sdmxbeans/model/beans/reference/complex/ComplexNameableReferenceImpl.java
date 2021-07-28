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
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.reference.complex.ComplexAnnotationReference;
import org.sdmxsource.sdmx.api.model.beans.reference.complex.ComplexNameableReference;
import org.sdmxsource.sdmx.api.model.beans.reference.complex.ComplexTextReference;

/**
 * The type Complex nameable reference.
 */
public class ComplexNameableReferenceImpl implements ComplexNameableReference {

    private SDMX_STRUCTURE_TYPE structureType;
    private ComplexAnnotationReference annotationRef;
    private ComplexTextReference nameRef;
    private ComplexTextReference descriptionRef;

    /**
     * Instantiates a new Complex nameable reference.
     *
     * @param structureType  the structure type
     * @param annotationRef  the annotation ref
     * @param nameRef        the name ref
     * @param descriptionRef the description ref
     */
    public ComplexNameableReferenceImpl(SDMX_STRUCTURE_TYPE structureType,
                                        ComplexAnnotationReference annotationRef,
                                        ComplexTextReference nameRef,
                                        ComplexTextReference descriptionRef) {

        if (structureType == null) {
            throw new SdmxSemmanticException("Null structure type provided for reference in query.");
        }

        this.structureType = structureType;
        if (annotationRef != null) {
            this.annotationRef = annotationRef;
        }
        if (nameRef != null) {
            this.nameRef = nameRef;
        }
        if (descriptionRef != null) {
            this.descriptionRef = descriptionRef;
        }
    }

    @Override
    public SDMX_STRUCTURE_TYPE getReferencedStructureType() {
        return structureType;
    }

    @Override
    public ComplexAnnotationReference getAnnotationReference() {
        return annotationRef;
    }

    @Override
    public ComplexTextReference getNameReference() {
        return nameRef;
    }

    @Override
    public ComplexTextReference getDescriptionReference() {
        return descriptionRef;
    }

}
