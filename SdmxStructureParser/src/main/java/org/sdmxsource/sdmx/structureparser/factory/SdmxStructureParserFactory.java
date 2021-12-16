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
package org.sdmxsource.sdmx.structureparser.factory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.sdmx.resources.sdmxml.schemas.v20.message.RegistryInterfaceDocument;
import org.sdmx.resources.sdmxml.schemas.v20.message.StructureDocument;
import org.sdmxsource.sdmx.api.constants.ARTIFACT_TYPE;
import org.sdmxsource.sdmx.api.constants.MESSAGE_TYPE;
import org.sdmxsource.sdmx.api.constants.REGISTRY_MESSAGE_TYPE;
import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxNoResultsException;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.factory.StructureParserFactory;
import org.sdmxsource.sdmx.api.model.beans.RegistrationInformation;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.structureparser.builder.sdmxbeans.SdmxBeansBuilder;
import org.sdmxsource.sdmx.structureparser.builder.sdmxbeans.impl.SdmxBeansBuilderImpl;
import org.sdmxsource.sdmx.structureparser.manager.parsing.ProvisionParsingManager;
import org.sdmxsource.sdmx.structureparser.manager.parsing.RegistrationParsingManager;
import org.sdmxsource.sdmx.structureparser.manager.parsing.SubscriptionParsingManager;
import org.sdmxsource.sdmx.structureparser.manager.parsing.impl.ProvisionParsingManagerImpl;
import org.sdmxsource.sdmx.structureparser.manager.parsing.impl.RegistrationParsingManagerImpl;
import org.sdmxsource.sdmx.structureparser.manager.parsing.impl.SubscriptionParsingManagerImpl;
import org.sdmxsource.sdmx.util.beans.container.SdmxBeansImpl;
import org.sdmxsource.sdmx.util.sdmx.SdmxMessageUtil;
import org.sdmxsource.util.io.StreamUtil;
import org.sdmxsource.util.log.LoggingUtil;

import java.io.IOException;
import java.io.InputStream;


/**
 * The type Sdmx structure parser factory.
 */
public class SdmxStructureParserFactory implements StructureParserFactory {
    private static final Logger LOG = LogManager.getLogger(SdmxStructureParserFactory.class);

    private final SdmxBeansBuilder sdmxBeansBuilder;

    private final ProvisionParsingManager provisionParsingManager;

    private final RegistrationParsingManager registrationParsingManager;

    private final SubscriptionParsingManager subscriptionParsingManager;

    /**
     * Instantiates a new Sdmx structure parser factory.
     */
    public SdmxStructureParserFactory() {
        this(null, null, null, null);
    }

    /**
     * Instantiates a new Sdmx structure parser factory.
     *
     * @param sdmxBeansBuilder           the sdmx beans builder
     * @param provisionParsingManager    the provision parsing manager
     * @param registrationParsingManager the registration parsing manager
     * @param subscriptionParsingManager the subscription parsing manager
     */
    public SdmxStructureParserFactory(
            final SdmxBeansBuilder sdmxBeansBuilder,
            final ProvisionParsingManager provisionParsingManager,
            final RegistrationParsingManager registrationParsingManager,
            final SubscriptionParsingManager subscriptionParsingManager) {
        this.sdmxBeansBuilder = sdmxBeansBuilder != null ? sdmxBeansBuilder : new SdmxBeansBuilderImpl();
        this.provisionParsingManager = provisionParsingManager != null ? provisionParsingManager : new ProvisionParsingManagerImpl();
        this.registrationParsingManager = registrationParsingManager != null ? registrationParsingManager : new RegistrationParsingManagerImpl();
        this.subscriptionParsingManager = subscriptionParsingManager != null ? subscriptionParsingManager : new SubscriptionParsingManagerImpl();
    }

    @Override
    public SdmxBeans getSdmxBeans(ReadableDataLocation dataLocation) {
        SDMX_SCHEMA schemaVersion = null;

        try {
            schemaVersion = SdmxMessageUtil.getSchemaVersion(dataLocation);
        } catch (Throwable th) {
            return null;
        }

        LOG.debug("Schema Version : " + schemaVersion);

        if (schemaVersion == SDMX_SCHEMA.EDI) {
            return null;
        }

        MESSAGE_TYPE messageType = SdmxMessageUtil.getMessageType(dataLocation);
        LOG.debug("Message type: " + messageType);
        if (schemaVersion.isXmlFormat()) {
            LOG.debug("Validate XML");
            //XMLParser.validateXML(dataLocation, schemaVersion);
            LoggingUtil.debug(LOG, "XML VALID");
        }
        REGISTRY_MESSAGE_TYPE registryMessage = null;
        if (messageType == MESSAGE_TYPE.REGISTRY_INTERFACE) {
            registryMessage = SdmxMessageUtil.getRegistryMessageType(dataLocation);
            return processRegistryInterfaceDocument(dataLocation, registryMessage, schemaVersion);
        } else if (messageType == MESSAGE_TYPE.ERROR) {
            try {
                LOG.debug("parse SDMX Error Message");
                if (LOG.isDebugEnabled()) {
                    String msg = new String(StreamUtil.toByteArray(dataLocation.getInputStream()));
                    LOG.debug(msg);
                }
                SdmxMessageUtil.parseSdmxErrorMessage(dataLocation);
            } catch (SdmxNoResultsException e) {
                LOG.debug("No results found");
                return new SdmxBeansImpl();
            }
        }

        InputStream stream = dataLocation.getInputStream();
        try {
            return parseSdmxStructureMessage(dataLocation.getInputStream(), schemaVersion, messageType);
        } catch (XmlException e) {
            throw new SdmxException(e, "Error while attempting to process SDMX-ML Structure file");
        } catch (IOException e) {
            throw new SdmxException(e, "Error while attempting to process SDMX-ML Structure file");
        } finally {
            StreamUtil.closeStream(stream);
        }
    }

    private SdmxBeans parseSdmxStructureMessage(InputStream stream, SDMX_SCHEMA schemaVersion, MESSAGE_TYPE messageType) throws XmlException, IOException {
        switch (schemaVersion) {
            case VERSION_ONE:
                org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.message.StructureDocument sdV1 = org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.message.StructureDocument.Factory.parse(stream);
                return sdmxBeansBuilder.build(sdV1);
            case VERSION_TWO:
                switch (messageType) {
                    case STRUCTURE:
                        StructureDocument sdV2 = StructureDocument.Factory.parse(stream);
                        return sdmxBeansBuilder.build(sdV2);
                    case REGISTRY_INTERFACE:
                        RegistryInterfaceDocument rid = RegistryInterfaceDocument.Factory.parse(stream);
                        return sdmxBeansBuilder.build(rid);
                    default:
                        throw new IllegalArgumentException("StructureParsingManagerImpl can not parse document '" + messageType + "' was expecting Structure document or RegistryInterface document");
                }
            case VERSION_TWO_POINT_ONE:
                switch (messageType) {
                    case STRUCTURE:
                        org.sdmx.resources.sdmxml.schemas.v21.message.StructureDocument sdV2_1 = org.sdmx.resources.sdmxml.schemas.v21.message.StructureDocument.Factory.parse(stream);
                        return sdmxBeansBuilder.build(sdV2_1);
                    case ERROR:
                        return new SdmxBeansImpl();
                    default:
                        throw new IllegalArgumentException("StructureParsingManagerImpl can not parse document '" + messageType + "' was expecting Structure document or Error document");
                }
        }
        return null;
    }

    private SdmxBeans processRegistryInterfaceDocument(ReadableDataLocation dataLocation, REGISTRY_MESSAGE_TYPE registryMessage, SDMX_SCHEMA schemaVersion) {
        ARTIFACT_TYPE artifactType = registryMessage.getArtifactType();
        SdmxBeans returnBeans = new SdmxBeansImpl();
        switch (artifactType) {
            case PROVISION:
                returnBeans.addIdentifiables(provisionParsingManager.parseXML(dataLocation));
                break;
            case REGISTRATION:
                for (RegistrationInformation regInfo : registrationParsingManager.parseRegXML(dataLocation)) {
                    returnBeans.addRegistration(regInfo.getRegistration());
                    returnBeans.setAction(regInfo.getRegistrationAction());
                }
                break;
            case STRUCTURE:
                InputStream stream = dataLocation.getInputStream();
                try {
                    switch (schemaVersion) {
                        case VERSION_TWO:
                            RegistryInterfaceDocument rid = RegistryInterfaceDocument.Factory.parse(stream);
                            return sdmxBeansBuilder.build(rid);
                        case VERSION_TWO_POINT_ONE:
                            org.sdmx.resources.sdmxml.schemas.v21.message.RegistryInterfaceDocument rid2_1 = org.sdmx.resources.sdmxml.schemas.v21.message.RegistryInterfaceDocument.Factory.parse(stream);
                            return sdmxBeansBuilder.build(rid2_1);
                        default:
                            throw new IllegalArgumentException("Schema version unsupported for RegistryInterfaceDocument: " + schemaVersion.toString());
                    }

                } catch (XmlException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    if (stream != null) {
                        StreamUtil.closeStream(stream);
                    }
                }
            case SUBSCRIPTION:
                returnBeans.addIdentifiables(subscriptionParsingManager.parseSubscriptionXML(dataLocation));
                break;
            default:
                throw new SdmxNotImplementedException("StructureParsingManager does not support message of type : " + registryMessage.toString());
        }
        return returnBeans;
    }


}
