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
package org.sdmxsource.sdmx.api.exception;

import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;


/**
 * The type Cross reference exception.
 */
public class CrossReferenceException extends SdmxSemmanticException {
    private static final long serialVersionUID = -6635423716751086527L;
    private CrossReferenceBean crossReference;

    /**
     * Creates a reference exception from a cross reference that could not be resolved in the system
     *
     * @param crossReference the cross reference
     */
    public CrossReferenceException(CrossReferenceBean crossReference) {
        super(ExceptionCode.REFERENCE_ERROR_UNRESOLVABLE, getReferenceFromIdentifier(crossReference), crossReference.getTargetUrn());
        this.crossReference = crossReference;
    }

    /**
     * Gets reference from identifier.
     *
     * @param crossReference the cross reference
     * @return the reference from identifier
     */
    public static String getReferenceFromIdentifier(CrossReferenceBean crossReference) {
        IdentifiableBean parentIdentifiable = crossReference.getReferencedFrom().getParent(IdentifiableBean.class, true);
        if (parentIdentifiable != null) {
            return parentIdentifiable.getUrn();
        }
        return crossReference.getReferencedFrom().toString();
    }

    /**
     * Returns the cross reference that could not be resolved
     *
     * @return cross reference
     */
    public CrossReferenceBean getCrossReference() {
        return crossReference;
    }

    @Override
    public String getErrorType() {
        return "Reference Exception";
    }

}
