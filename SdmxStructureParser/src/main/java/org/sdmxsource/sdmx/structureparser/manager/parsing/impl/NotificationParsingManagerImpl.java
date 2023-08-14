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

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.sdmx.resources.sdmxml.schemas.v20.message.RegistryInterfaceDocument;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.model.beans.registry.NotificationEvent;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.registry.NotificationEventImpl;
import org.sdmxsource.sdmx.structureparser.manager.parsing.NotificationParsingManager;
import org.sdmxsource.sdmx.util.sdmx.SdmxMessageUtil;
import org.sdmxsource.springutil.xml.XMLParser;
import org.sdmxsource.util.log.LoggingUtil;

import java.io.IOException;
import java.io.InputStream;

//JAVADOC missing

/**
 * The type Notification parsing manager.
 */
public class NotificationParsingManagerImpl implements NotificationParsingManager {
    private Logger log = LoggerFactory.getLogger(NotificationParsingManagerImpl.class);


    //TODO Test that there is a notification event in this message!
    @Override
    public NotificationEvent createNotificationEvent(ReadableDataLocation dataLocation) {
        LoggingUtil.debug(log, "Parse Structure request, for xml at location: " + dataLocation.toString());
        InputStream stream = null;
        NotificationEvent notificationEvent = null;
        try {
            SDMX_SCHEMA schemaVersion = SdmxMessageUtil.getSchemaVersion(dataLocation);
            LoggingUtil.debug(log, "Schema Version Determined to be : " + schemaVersion);
            XMLParser.validateXML(dataLocation, schemaVersion);
            LoggingUtil.debug(log, "XML VALID");
            stream = dataLocation.getInputStream();

            switch (schemaVersion) {
                case VERSION_TWO:
                    RegistryInterfaceDocument rid = RegistryInterfaceDocument.Factory.parse(stream);
                    if (rid.getRegistryInterface().getNotifyRegistryEvent() == null) {
                        throw new IllegalArgumentException("Can not parse message as NotifyRegistryEvent, as there are no NotifyRegistryEvent in message");
                    }
                    notificationEvent = new NotificationEventImpl(rid.getRegistryInterface().getNotifyRegistryEvent());
                    break;
                case VERSION_TWO_POINT_ONE:
                    org.sdmx.resources.sdmxml.schemas.v21.message.RegistryInterfaceDocument rid21 = org.sdmx.resources.sdmxml.schemas.v21.message.RegistryInterfaceDocument.Factory.parse(stream);
                    if (rid21.getRegistryInterface().getNotifyRegistryEvent() == null) {
                        throw new IllegalArgumentException("Can not parse message as NotifyRegistryEvent, as there are no NotifyRegistryEvent in message");
                    }
                    notificationEvent = new NotificationEventImpl(rid21.getRegistryInterface().getNotifyRegistryEvent());
                    break;
                default:
                    throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "Parse NotificationEvent at version: " + schemaVersion);
            }
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
        //TODO Validation
        return notificationEvent;
    }
}
