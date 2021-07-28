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
package org.sdmxsource.sdmx.structureparser.manager.parsing.impl;

import org.sdmx.resources.sdmxml.schemas.v21.message.RegistryInterfaceDocument;
import org.sdmx.resources.sdmxml.schemas.v21.registry.SubmitSubscriptionsRequestType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.model.beans.registry.SubscriptionBean;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.structureparser.builder.subscriptionbeans.SubscriptionBeansBuilder;
import org.sdmxsource.sdmx.structureparser.builder.subscriptionbeans.impl.SubscriptionBeansBuilderImpl;
import org.sdmxsource.sdmx.structureparser.manager.parsing.SubscriptionParsingManager;
import org.sdmxsource.sdmx.util.sdmx.SdmxMessageUtil;
import org.sdmxsource.springutil.xml.XMLParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Subscription parsing manager.
 */
//JAVADOC missing
public class SubscriptionParsingManagerImpl implements SubscriptionParsingManager {

    private final SubscriptionBeansBuilder subscriptionBeansBuilder = new SubscriptionBeansBuilderImpl();

    @Override
    public List<SubscriptionBean> parseSubscriptionXML(ReadableDataLocation dataLocation) {
        SDMX_SCHEMA schemaVersion = SdmxMessageUtil.getSchemaVersion(dataLocation);
        XMLParser.validateXML(dataLocation, schemaVersion);
        InputStream stream = dataLocation.getInputStream();
        try {
            List<SubscriptionBean> returnList = new ArrayList<SubscriptionBean>();
            switch (schemaVersion) {
                case VERSION_TWO_POINT_ONE:
                    RegistryInterfaceDocument rid = RegistryInterfaceDocument.Factory.parse(stream);
                    if (rid.getRegistryInterface().getSubmitSubscriptionsRequest() != null &&
                            rid.getRegistryInterface().getSubmitSubscriptionsRequest().getSubscriptionRequestList() != null) {
                        SubmitSubscriptionsRequestType subscritpionRequestType = rid.getRegistryInterface().getSubmitSubscriptionsRequest();
                        returnList = subscriptionBeansBuilder.build(subscritpionRequestType);
                    }
                    break;

                default:
                    throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "Subscription in version : " + schemaVersion.toString());
            }
            return returnList;
        } catch (Throwable th) {
            throw new RuntimeException(th);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
