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

import org.sdmx.resources.sdmxml.schemas.v20.common.TextType;
import org.sdmx.resources.sdmxml.schemas.v20.message.RegistryInterfaceDocument;
import org.sdmx.resources.sdmxml.schemas.v20.message.RegistryInterfaceType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.ProvisionAgreementType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.QueryProvisioningResponseType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.StatusMessageType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.StatusType;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v2.ProvisionAgreementXmlBeanBuilder;
import org.sdmxsource.util.ObjectUtil;

import java.util.Collection;


//JAVADOC missing

/**
 * The type Query provision response builder v 2.
 */
public class QueryProvisionResponseBuilderV2 extends AbstractResponseBuilder {
    /**
     * The constant INSTANCE.
     */
    public static QueryProvisionResponseBuilderV2 INSTANCE = new QueryProvisionResponseBuilderV2();

    private ProvisionAgreementXmlBeanBuilder provBuilder = ProvisionAgreementXmlBeanBuilder.INSTANCE;

    //PRIVATE CONSTRUCTOR
    private QueryProvisionResponseBuilderV2() {
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
        QueryProvisioningResponseType returnType = regInterface.addNewQueryProvisioningResponse();
        V2Helper.setHeader(regInterface);

        StatusMessageType statusMessage = returnType.addNewStatusMessage();
        addStatus(statusMessage, th);


        return responseType;
    }

    /**
     * Build success response registry interface document.
     *
     * @param provisions the provisions
     * @return the registry interface document
     */
    public RegistryInterfaceDocument buildSuccessResponse(Collection<ProvisionAgreementBean> provisions) {
        RegistryInterfaceDocument responseType = RegistryInterfaceDocument.Factory.newInstance();
        RegistryInterfaceType regInterface = responseType.addNewRegistryInterface();
        QueryProvisioningResponseType returnType = regInterface.addNewQueryProvisioningResponse();
        V2Helper.setHeader(regInterface);

        StatusMessageType statusMessage = returnType.addNewStatusMessage();
        addStatus(statusMessage, null);

        if (!ObjectUtil.validCollection(provisions)) {
            statusMessage.setStatus(StatusType.WARNING);
            TextType tt = statusMessage.addNewMessageText();
            tt.setStringValue("No Provisions Match The Query Parameters");
        } else {
            statusMessage.setStatus(StatusType.SUCCESS);
            ProvisionAgreementType[] provTypes = new ProvisionAgreementType[provisions.size()];

            int i = 0;
            for (ProvisionAgreementBean currentProv : provisions) {
                provTypes[i] = provBuilder.build(currentProv);
                i++;
            }
            returnType.setProvisionAgreementArray(provTypes);
        }
        return responseType;
    }
}
