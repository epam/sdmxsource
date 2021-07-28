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


import org.sdmx.resources.sdmxml.schemas.v21.message.RegistryInterfaceDocument;
import org.sdmx.resources.sdmxml.schemas.v21.message.RegistryInterfaceType;
import org.sdmx.resources.sdmxml.schemas.v21.registry.SubmitSubscriptionsResponseType;
import org.sdmx.resources.sdmxml.schemas.v21.registry.SubscriptionStatusType;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.registry.SubscriptionBean;

import java.util.Collection;

//JAVADOC missing

/**
 * The type Submit subscription response builder v 2 1.
 */
public class SubmitSubscriptionResponseBuilderV2_1 extends AbstractResponseBuilder {


    /**
     * Build success response registry interface document.
     *
     * @param notifications the notifications
     * @return the registry interface document
     * @throws SdmxException the sdmx exception
     */
    public RegistryInterfaceDocument buildSuccessResponse(Collection<SubscriptionBean> notifications) throws SdmxException {
        RegistryInterfaceDocument responseType = RegistryInterfaceDocument.Factory.newInstance();
        RegistryInterfaceType regInterface = responseType.addNewRegistryInterface();
        V2_1Helper.setHeader(regInterface);
        for (SubscriptionBean currentNotification : notifications) {
            SubmitSubscriptionsResponseType returnType = regInterface.addNewSubmitSubscriptionsResponse();
            SubscriptionStatusType subscriptionStatus = returnType.addNewSubscriptionStatus();
            addStatus(subscriptionStatus.addNewStatusMessage(), null);

            subscriptionStatus.setSubscriptionURN(currentNotification.getUrn());
        }
        return responseType;
    }

    /**
     * Build error response registry interface document.
     *
     * @param notification the notification
     * @param th           the th
     * @return the registry interface document
     * @throws SdmxException the sdmx exception
     */
    public RegistryInterfaceDocument buildErrorResponse(SubscriptionBean notification, Throwable th) throws SdmxException {
        RegistryInterfaceDocument responseType = RegistryInterfaceDocument.Factory.newInstance();
        RegistryInterfaceType regInterface = responseType.addNewRegistryInterface();
        V2_1Helper.setHeader(regInterface);
        SubmitSubscriptionsResponseType returnType = regInterface.addNewSubmitSubscriptionsResponse();
        SubscriptionStatusType subscriptionStatus = returnType.addNewSubscriptionStatus();
        if (notification != null) {
            subscriptionStatus.setSubscriptionURN(notification.getUrn());
        }
        addStatus(subscriptionStatus.addNewStatusMessage(), th);
        return responseType;
    }

}
