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
import org.sdmx.resources.sdmxml.schemas.v21.registry.SubscriptionStatusType;
import org.sdmxsource.sdmx.api.builder.Builder;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.ErrorList;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.submissionresponse.SubmitSubscriptionResponse;
import org.sdmxsource.sdmx.sdmxbeans.model.ErrorListImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.submissionresponse.SubmitSubscriptionResponseImpl;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * Parses and SDMX Document to build the submit subscription response, can either be a RegisteryInterfaceDocument or a
 * SubmitSubscriptionResponse Document
 *
 * @author Matt Nelson
 */
public class SubmitSubscriptionResponseBuilder_V21 implements Builder<List<SubmitSubscriptionResponse>, RegistryInterfaceDocument> {

    @Override
    public List<SubmitSubscriptionResponse> build(RegistryInterfaceDocument rid) throws SdmxException {
        List<SubmitSubscriptionResponse> returnList = new ArrayList<SubmitSubscriptionResponse>();
        for (SubscriptionStatusType ssType : rid.getRegistryInterface().getSubmitSubscriptionsResponse().getSubscriptionStatusList()) {
            if (ssType.getStatusMessage() != null && ssType.getStatusMessage().getStatus() != null) {
                List<String> messages = new ArrayList<String>();
                if (ssType.getStatusMessage().getMessageTextList() != null) {
                    //TODO Message Codes and Multilingual
                    for (StatusMessageType smt : ssType.getStatusMessage().getMessageTextList()) {
                        if (smt.getTextList() != null) {
                            for (TextType tt : smt.getTextList()) {
                                messages.add(tt.getStringValue());
                            }
                        }
                    }
                }
                ErrorList errors = null;
                if (ssType.getStatusMessage().getStatus().intValue() == StatusType.FAILURE.intValue()) {
                    errors = new ErrorListImpl(messages, false);
                } else if (ssType.getStatusMessage().getStatus().intValue() == StatusType.WARNING.intValue()) {
                    errors = new ErrorListImpl(messages, true);
                }
                String urn = ssType.getSubscriptionURN();
                StructureReferenceBean sRef = null;
                if (urn != null) {
                    sRef = new StructureReferenceBeanImpl(urn);
                }
                returnList.add(new SubmitSubscriptionResponseImpl(sRef, errors, ssType.getSubscriberAssignedID()));
            }
        }
        return returnList;
    }
}
