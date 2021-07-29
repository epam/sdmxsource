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
package org.sdmxsource.sdmx.sdmxbeans.model.submissionresponse;

import org.sdmxsource.sdmx.api.model.ErrorList;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.submissionresponse.SubmitStructureResponse;

/**
 * The type Submit structure response.
 */
public class SubmitStructureResponseImpl implements SubmitStructureResponse {
    private StructureReferenceBean structureReferenceBean;
    private ErrorList errorList;

    /**
     * Instantiates a new Submit structure response.
     *
     * @param structureReferenceBean the structure reference bean
     * @param errorList              the error list
     */
    public SubmitStructureResponseImpl(StructureReferenceBean structureReferenceBean, ErrorList errorList) {
        this.structureReferenceBean = structureReferenceBean;
        this.errorList = errorList;
        if (structureReferenceBean != null && structureReferenceBean.getTargetUrn() == null) {
            throw new IllegalArgumentException("SubmitStructureResponseImpl expects a complete StructureReferenceBean");
        }
        if (!isError()) {
            if (structureReferenceBean == null) {
                throw new IllegalArgumentException("Sucessful SubmitStructureResponse expects a StructureReferenceBean");
            }
        }
    }

    @Override
    public boolean isError() {
        return errorList != null && !errorList.isWarning();
    }

    @Override
    public StructureReferenceBean getStructureReference() {
        return structureReferenceBean;
    }

    @Override
    public ErrorList getErrorList() {
        return errorList;
    }

}
