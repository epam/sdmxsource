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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans.registry.response.impl.v2;

import org.sdmx.resources.sdmxml.schemas.v20.message.RegistryInterfaceDocument;
import org.sdmx.resources.sdmxml.schemas.v20.message.RegistryInterfaceType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.DatasourceType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.ProvisionAgreementRefType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.RegistrationStatusType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.SubmitRegistrationResponseType;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.RegistrationBean;

import java.util.Map;


//JAVADOC missing

/**
 * The type Submit registration response builder v 2.
 */
public class SubmitRegistrationResponseBuilderV2 extends AbstractResponseBuilder {
    /**
     * The constant INSTANCE.
     */
    public static SubmitRegistrationResponseBuilderV2 INSTANCE = new SubmitRegistrationResponseBuilderV2();

    //PRIVATE CONSTRUCTOR
    private SubmitRegistrationResponseBuilderV2() {
    }

    /**
     * Build error response registry interface document.
     *
     * @param th the th
     * @return the registry interface document
     */
    public RegistryInterfaceDocument buildErrorResponse(Throwable th) {
        RegistryInterfaceDocument responseType = RegistryInterfaceDocument.Factory.newInstance();
        RegistryInterfaceType regInterface = responseType.addNewRegistryInterface();
        V2Helper.setHeader(regInterface);
        SubmitRegistrationResponseType returnType = regInterface.addNewSubmitRegistrationResponse();
        RegistrationStatusType registrationStatusType = returnType.addNewRegistrationStatus();
        addStatus(registrationStatusType.addNewStatusMessage(), th);
        return responseType;
    }

    /**
     * Build response registry interface document.
     *
     * @param response the response
     * @return the registry interface document
     */
    public RegistryInterfaceDocument buildResponse(Map<RegistrationBean, Throwable> response) {
        RegistryInterfaceDocument responseType = RegistryInterfaceDocument.Factory.newInstance();
        RegistryInterfaceType regInterface = responseType.addNewRegistryInterface();
        SubmitRegistrationResponseType returnType = regInterface.addNewSubmitRegistrationResponse();
        V2Helper.setHeader(regInterface);
        for (RegistrationBean registration : response.keySet()) {
            processResponse(returnType, registration, response.get(registration));
        }

        return responseType;
    }

    private void processResponse(SubmitRegistrationResponseType returnType, RegistrationBean registration, Throwable th) {
        RegistrationStatusType registrationStatusType = returnType.addNewRegistrationStatus();
        addStatus(registrationStatusType.addNewStatusMessage(), th);
        if (registration.getDataSource() != null) {
            DatasourceType datasourceType = registrationStatusType.addNewDatasource();
            addDatasource(registration.getDataSource(), datasourceType);
        }
        if (registration.getProvisionAgreementRef() != null) {
            CrossReferenceBean provRef = registration.getProvisionAgreementRef();
            ProvisionAgreementRefType provRefType = registrationStatusType.addNewProvisionAgreementRef();
            if (provRef.getTargetUrn() != null) {
                provRefType.setURN(provRef.getTargetUrn());
            }
        }
    }
}
