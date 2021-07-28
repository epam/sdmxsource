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

import org.sdmx.resources.sdmxml.schemas.v21.common.TextType;
import org.sdmx.resources.sdmxml.schemas.v21.message.RegistryInterfaceDocument;
import org.sdmx.resources.sdmxml.schemas.v21.message.RegistryInterfaceType;
import org.sdmx.resources.sdmxml.schemas.v21.registry.*;
import org.sdmxsource.sdmx.api.model.beans.registry.RegistrationBean;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.RegistrationXmlBeanBuilder;
import org.sdmxsource.util.ObjectUtil;

import java.util.Collection;

/**
 * The type Query registration response builder v 2 1.
 */
//JAVADOC missing
public class QueryRegistrationResponseBuilderV2_1 extends AbstractResponseBuilder {

    private final RegistrationXmlBeanBuilder registrationXmlBeanBuilder = new RegistrationXmlBeanBuilder();

    /**
     * Build error response registry interface document.
     *
     * @param th the th
     * @return the registry interface document
     */
    public RegistryInterfaceDocument buildErrorResponse(Throwable th) {
        RegistryInterfaceDocument responseType = RegistryInterfaceDocument.Factory.newInstance();
        RegistryInterfaceType regInterface = responseType.addNewRegistryInterface();
        QueryRegistrationResponseType returnType = regInterface.addNewQueryRegistrationResponse();
        V2_1Helper.setHeader(regInterface);

        StatusMessageType statusMessage = returnType.addNewStatusMessage();
        addStatus(statusMessage, th);

        return responseType;
    }

    /**
     * Build success response registry interface document.
     *
     * @param registrations the registrations
     * @return the registry interface document
     */
    public RegistryInterfaceDocument buildSuccessResponse(Collection<RegistrationBean> registrations) {
        RegistryInterfaceDocument responseType = RegistryInterfaceDocument.Factory.newInstance();
        RegistryInterfaceType regInterface = responseType.addNewRegistryInterface();
        QueryRegistrationResponseType returnType = regInterface.addNewQueryRegistrationResponse();
        V2_1Helper.setHeader(regInterface);
        StatusMessageType statusMessage = returnType.addNewStatusMessage();
        if (!ObjectUtil.validCollection(registrations)) {
            //FUNC 2.1 - add warning to header?
            statusMessage.setStatus(StatusType.WARNING);
            org.sdmx.resources.sdmxml.schemas.v21.common.StatusMessageType smt = statusMessage.addNewMessageText();
            TextType tt = smt.addNewText();
            tt.setStringValue("No Registrations Match The Query Parameters");
        } else {
            statusMessage.setStatus(StatusType.SUCCESS);
            for (RegistrationBean currentRegistration : registrations) {
                QueryResultType queryResult = returnType.addNewQueryResult();
                queryResult.setTimeSeriesMatch(false);  //FUNC 1 - when is this true?  Also We need MetadataResult

                ResultType resultType = queryResult.addNewDataResult();
                RegistrationType registrationType = registrationXmlBeanBuilder.build(currentRegistration);
                resultType.setRegistration(registrationType);
            }
        }
        return responseType;
    }
}
