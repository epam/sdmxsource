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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans.registry.response.impl.v21;

import org.apache.xmlbeans.XmlAnyURI;
import org.sdmx.resources.sdmxml.schemas.v21.common.ProvisionAgreementReferenceType;
import org.sdmx.resources.sdmxml.schemas.v21.message.RegistryInterfaceDocument;
import org.sdmx.resources.sdmxml.schemas.v21.message.RegistryInterfaceType;
import org.sdmx.resources.sdmxml.schemas.v21.registry.*;
import org.sdmxsource.sdmx.api.model.beans.base.DataSourceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.RegistrationBean;

import java.util.Map;

//JAVADOC missing

/**
 * The type Submit registration response builder v 2 1.
 */
public class SubmitRegistrationResponseBuilderV2_1 extends AbstractResponseBuilder {
    /**
     * The constant INSTANCE.
     */
    public static SubmitRegistrationResponseBuilderV2_1 INSTANCE = new SubmitRegistrationResponseBuilderV2_1();

    //PRIVATE CONSTRUCTOR
    private SubmitRegistrationResponseBuilderV2_1() {
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
        SubmitRegistrationsResponseType returnType = regInterface.addNewSubmitRegistrationsResponse();
        V2_1Helper.setHeader(regInterface);
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
        SubmitRegistrationsResponseType returnType = regInterface.addNewSubmitRegistrationsResponse();
        V2_1Helper.setHeader(regInterface);
        for (RegistrationBean registration : response.keySet()) {
            processResponse(returnType, registration, response.get(registration));
        }

        return responseType;
    }

    private void processResponse(SubmitRegistrationsResponseType returnType, RegistrationBean registrationBean, Throwable th) {
        RegistrationStatusType registrationStatusType = returnType.addNewRegistrationStatus();
        addStatus(registrationStatusType.addNewStatusMessage(), th);
        RegistrationType registrationType = registrationStatusType.addNewRegistration();
        registrationType.setId(registrationBean.getId());
        if (registrationBean.getDataSource() != null) {
            DataSourceBean datasourceBean = registrationBean.getDataSource();
            DataSourceType datasourceType = registrationType.addNewDatasource();

            if (datasourceBean.isSimpleDatasource()) {
                XmlAnyURI simpleDatasourceType = datasourceType.addNewSimpleDataSource();
                simpleDatasourceType.setStringValue(datasourceBean.getDataUrl().toString());
            } else {
                QueryableDataSourceType queryableDatasource = datasourceType.addNewQueryableDataSource();
                queryableDatasource.setIsRESTDatasource(datasourceBean.isRESTDatasource());
                queryableDatasource.setIsWebServiceDatasource(datasourceBean.isWebServiceDatasource());
                queryableDatasource.setDataURL(datasourceBean.getDataUrl().toString());
                if (datasourceBean.getWSDLUrl() != null) {
                    queryableDatasource.setWSDLURL(datasourceBean.getWSDLUrl().toString());
                }
                if (datasourceBean.getWadlUrl() != null) {
                    queryableDatasource.setWADLURL(datasourceBean.getWadlUrl().toString());
                }
            }
        }
        if (registrationBean.getProvisionAgreementRef() != null) {
            ProvisionAgreementReferenceType refType = registrationType.addNewProvisionAgreement();
            refType.setURN(registrationBean.getProvisionAgreementRef().getTargetUrn());
        }
    }
}
