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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.response;

import org.sdmx.resources.sdmxml.schemas.v21.common.StatusMessageType;
import org.sdmx.resources.sdmxml.schemas.v21.common.TextType;
import org.sdmx.resources.sdmxml.schemas.v21.message.RegistryInterfaceDocument;
import org.sdmx.resources.sdmxml.schemas.v21.registry.StatusType;
import org.sdmx.resources.sdmxml.schemas.v21.registry.SubmissionResultType;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.ErrorList;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.submissionresponse.SubmitStructureResponse;
import org.sdmxsource.sdmx.sdmxbeans.model.ErrorListImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.submissionresponse.SubmitStructureResponseImpl;
import org.sdmxsource.sdmx.sdmxbeans.util.RefUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Submit structure response builder v 21.
 */
public class SubmitStructureResponseBuilder_V21 {

    /**
     * Build list.
     *
     * @param rid the rid
     * @return the list
     * @throws SdmxException the sdmx exception
     */
    public List<SubmitStructureResponse> build(RegistryInterfaceDocument rid) throws SdmxException {
        //TODO REFACTOR - THIS IS VERY SIMILAR TO SUBMIT SUBSCRIPTION RESPONSE
        List<SubmitStructureResponse> returnList = new ArrayList<SubmitStructureResponse>();
        for (SubmissionResultType rsType : rid.getRegistryInterface().getSubmitStructureResponse().getSubmissionResultList()) {
            StructureReferenceBean sRef = RefUtil.createReference(rsType.getSubmittedStructure().getMaintainableObject());
            if (rsType.getStatusMessage() != null && rsType.getStatusMessage().getStatus() != null) {
                List<String> messages = new ArrayList<String>();
                if (rsType.getStatusMessage().getMessageTextList() != null) {
                    //TODO Message Codes and Multilingual
                    for (StatusMessageType smt : rsType.getStatusMessage().getMessageTextList()) {
                        if (smt.getTextList() != null) {
                            for (TextType tt : smt.getTextList()) {
                                messages.add(tt.getStringValue());
                            }
                        }
                    }
                }
                ErrorList errors = null;
                if (rsType.getStatusMessage().getStatus().intValue() == StatusType.FAILURE.intValue()) {
                    errors = new ErrorListImpl(messages, false);
                } else if (rsType.getStatusMessage().getStatus().intValue() == StatusType.WARNING.intValue()) {
                    errors = new ErrorListImpl(messages, true);
                }
                returnList.add(new SubmitStructureResponseImpl(sRef, errors));
            }
        }
        return returnList;
    }

}
