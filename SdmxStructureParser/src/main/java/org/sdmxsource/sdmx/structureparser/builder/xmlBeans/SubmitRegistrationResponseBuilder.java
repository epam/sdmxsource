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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans;

import org.sdmx.resources.sdmxml.schemas.v21.message.RegistryInterfaceDocument;
import org.sdmxsource.sdmx.api.constants.REGISTRY_MESSAGE_TYPE;
import org.sdmxsource.sdmx.api.model.submissionresponse.SubmitRegistrationResponse;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.response.SubmitRegistrationResponseBuilder_V21;

import java.util.List;


/**
 * The type Submit registration response builder.
 */
public class SubmitRegistrationResponseBuilder extends AbstractResponseBuilder<SubmitRegistrationResponse> {

    private final SubmitRegistrationResponseBuilder_V21 sBuilder_V21 = SubmitRegistrationResponseBuilder_V21.Instance;

    @Override
    List<SubmitRegistrationResponse> buildInternal(RegistryInterfaceDocument rid) {
        return sBuilder_V21.build(rid);
    }

    @Override
    REGISTRY_MESSAGE_TYPE getExpectedMessageType() {
        return REGISTRY_MESSAGE_TYPE.SUBMIT_REGISTRATION_RESPONSE;
    }
}
