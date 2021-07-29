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
import org.sdmxsource.sdmx.api.model.beans.reference.complex.ComplexAnnotationReference;
import org.sdmxsource.sdmx.api.model.beans.reference.complex.ComplexIdentifiableReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.complex.ComplexTextReference;

/**
 * The type Complex identifiable reference bean.
 */
public class ComplexIdentifiableReferenceBeanImpl extends
        ComplexNameableReferenceImpl implements
        ComplexIdentifiableReferenceBean {

    private ComplexTextReference id;
    private ComplexIdentifiableReferenceBean childReference;

    /**
     * Instantiates a new Complex identifiable reference bean.
     *
     * @param id             the id
     * @param structureType  the structure type
     * @param annotationRef  the annotation ref
     * @param nameRef        the name ref
     * @param descriptionRef the description ref
     * @param childReference the child reference
     */
    public ComplexIdentifiableReferenceBeanImpl(
            ComplexTextReference id,
            SDMX_STRUCTURE_TYPE structureType,
            ComplexAnnotationReference annotationRef,
            ComplexTextReference nameRef,
            ComplexTextReference descriptionRef,
            ComplexIdentifiableReferenceBean childReference) {

        super(structureType, annotationRef, nameRef, descriptionRef);

        this.id = id;
        this.childReference = childReference;
    }

    @Override
    public ComplexTextReference getId() {
        return id;
    }

    @Override
    public ComplexIdentifiableReferenceBean getChildReference() {
        return childReference;
    }

}
