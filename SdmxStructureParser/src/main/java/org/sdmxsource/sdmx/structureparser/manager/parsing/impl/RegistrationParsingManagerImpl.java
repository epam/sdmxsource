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

import org.apache.xmlbeans.XmlException;
import org.sdmx.resources.sdmxml.schemas.v20.message.RegistryInterfaceDocument;
import org.sdmx.resources.sdmxml.schemas.v20.registry.QueryResultType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.RegistrationType;
import org.sdmx.resources.sdmxml.schemas.v21.common.CodedStatusMessageType;
import org.sdmx.resources.sdmxml.schemas.v21.message.ErrorDocument;
import org.sdmx.resources.sdmxml.schemas.v21.message.RegistryInterfaceType;
import org.sdmxsource.sdmx.api.constants.DATASET_ACTION;
import org.sdmxsource.sdmx.api.constants.MESSAGE_TYPE;
import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.api.model.beans.RegistrationInformation;
import org.sdmxsource.sdmx.api.model.beans.registry.RegistrationBean;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.sdmxbeans.model.RegistrationInformationImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.registry.RegistrationBeanImpl;
import org.sdmxsource.sdmx.structureparser.manager.parsing.RegistrationParsingManager;
import org.sdmxsource.sdmx.util.sdmx.SdmxMessageUtil;
import org.sdmxsource.springutil.xml.XMLParser;
import org.sdmxsource.util.ObjectUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

//JAVADOC missing

/**
 * The type Registration parsing manager.
 */
public class RegistrationParsingManagerImpl implements RegistrationParsingManager {

    //TODO Handle error responses (AS IN REGISTRY ERROR DOCUMENT AS THIS CAUSES THE PARSING TO FAIL)
    @Override
    @SuppressWarnings("deprecation")
    public List<RegistrationInformation> parseRegXML(ReadableDataLocation dataLocation) {
        SDMX_SCHEMA schemaVersion = SdmxMessageUtil.getSchemaVersion(dataLocation);
        XMLParser.validateXML(dataLocation, schemaVersion);
        MESSAGE_TYPE messageType = SdmxMessageUtil.getMessageType(dataLocation);
        if (messageType != MESSAGE_TYPE.ERROR && messageType != MESSAGE_TYPE.REGISTRY_INTERFACE) {
            throw new RuntimeException("Unexpected Document found, expecting RegistryInterfaceDocument containing Registrations, received " + messageType.getNodeName());
        }
        InputStream stream = dataLocation.getInputStream();
        try {
            if (messageType == MESSAGE_TYPE.ERROR) {
                ErrorDocument errorDocument = ErrorDocument.Factory.parse(stream);
                for (CodedStatusMessageType csmt : errorDocument.getError().getErrorMessageList()) {
                    if (csmt.getCode() != null && csmt.getCode().equals("100")) {
                        return new ArrayList<RegistrationInformation>();
                    } else {
                        //IMPORTANT THIS SHOULD THROW A BETTER EXCEPTION - AND SHOULD BE REFACTORED INTO A ERROR PARSING CLASS
                        throw new RuntimeException(csmt.getTextArray()[0].getStringValue());
                    }
                }
                return new ArrayList<RegistrationInformation>();
            } else {
                return processResigtrationResponse(schemaVersion, stream);
            }
        } catch (IOException e) {
            throw new RuntimeException("A error occured whilst trying to read the XML stream", e);
        } catch (XmlException e) {
            throw new RuntimeException("A error occured whilst trying to process the XML stream", e);
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

    private List<RegistrationInformation> processResigtrationResponse(SDMX_SCHEMA schemaVersion, InputStream stream) throws IOException, XmlException {
        List<RegistrationInformation> returnList = new ArrayList<RegistrationInformation>();
        switch (schemaVersion) {
            case VERSION_TWO:
                RegistryInterfaceDocument rid = RegistryInterfaceDocument.Factory.parse(stream);
                if (rid.getRegistryInterface().getQueryRegistrationResponse() != null &&
                        rid.getRegistryInterface().getQueryRegistrationResponse().getQueryResultList() != null) {
                    for (QueryResultType resultType : rid.getRegistryInterface().getQueryRegistrationResponse().getQueryResultList()) {
                        if (resultType.getDataResult() != null) {
                            RegistrationBean registration = new RegistrationBeanImpl(resultType.getDataResult().getProvisionAgreementRef(), resultType.getDataResult().getDatasource());
                            DATASET_ACTION action = DATASET_ACTION.INFORMATION;
                            returnList.add(new RegistrationInformationImpl(action, registration));
                        }
                    }
                }
                if (rid.getRegistryInterface().getSubmitRegistrationRequest() != null &&
                        rid.getRegistryInterface().getSubmitRegistrationRequest().getRegistrationList() != null) {
                    for (RegistrationType registrationType : rid.getRegistryInterface().getSubmitRegistrationRequest().getRegistrationList()) {
                        RegistrationBean registration = new RegistrationBeanImpl(registrationType);
                        DATASET_ACTION action = DATASET_ACTION.APPEND;
                        switch (registrationType.getAction().intValue()) {
                            case org.sdmx.resources.sdmxml.schemas.v20.common.ActionType.INT_APPEND:
                                action = DATASET_ACTION.APPEND;
                                break;
                            case org.sdmx.resources.sdmxml.schemas.v20.common.ActionType.INT_DELETE:
                                action = DATASET_ACTION.DELETE;
                                break;
                            case org.sdmx.resources.sdmxml.schemas.v20.common.ActionType.INT_REPLACE:
                                action = DATASET_ACTION.REPLACE;
                                break;
                        }
                        returnList.add(new RegistrationInformationImpl(action, registration));
                    }
                }
                break;
            case VERSION_TWO_POINT_ONE:
                org.sdmx.resources.sdmxml.schemas.v21.message.RegistryInterfaceDocument rid2_1 = org.sdmx.resources.sdmxml.schemas.v21.message.RegistryInterfaceDocument.Factory.parse(stream);
                RegistryInterfaceType rit = rid2_1.getRegistryInterface();
                if (rit.getSubmitRegistrationsRequest() != null && rit.getSubmitRegistrationsRequest().getRegistrationRequestList() != null) {
                    for (org.sdmx.resources.sdmxml.schemas.v21.registry.RegistrationRequestType rt : rit.getSubmitRegistrationsRequest().getRegistrationRequestList()) {
                        DATASET_ACTION action = DATASET_ACTION.APPEND;
                        switch (rt.getAction().intValue()) {
                            case org.sdmx.resources.sdmxml.schemas.v21.common.ActionType.INT_APPEND:
                                action = DATASET_ACTION.APPEND;
                                break;
                            case org.sdmx.resources.sdmxml.schemas.v21.common.ActionType.INT_DELETE:
                                action = DATASET_ACTION.DELETE;
                                break;
                            case org.sdmx.resources.sdmxml.schemas.v21.common.ActionType.INT_REPLACE:
                                action = DATASET_ACTION.REPLACE;
                                break;
                        }
                        if (action == DATASET_ACTION.DELETE || action == DATASET_ACTION.REPLACE) {
                            if (!ObjectUtil.validString(rt.getRegistration().getId())) {
                                throw new IllegalArgumentException("Registration submissions with REPLACE or DELETE actions must contain an id identifying the Registration to perform this action on");
                            }
                        }
                        RegistrationBean registration = new RegistrationBeanImpl(rt.getRegistration());
                        returnList.add(new RegistrationInformationImpl(action, registration));
                    }
                }
                if (rit.getQueryRegistrationResponse() != null && rit.getQueryRegistrationResponse().getQueryResultList() != null) {
                    for (org.sdmx.resources.sdmxml.schemas.v21.registry.QueryResultType queryResult : rit.getQueryRegistrationResponse().getQueryResultList()) {
                        if (queryResult.getDataResult() != null && queryResult.getDataResult().getRegistration() != null) {
                            RegistrationBean registration = new RegistrationBeanImpl(queryResult.getDataResult().getRegistration());
                            returnList.add(new RegistrationInformationImpl(DATASET_ACTION.INFORMATION, registration));
                        }
                        if (queryResult.getMetadataResult() != null && queryResult.getMetadataResult().getRegistration() != null) {
                            RegistrationBean registration = new RegistrationBeanImpl(queryResult.getDataResult().getRegistration());
                            returnList.add(new RegistrationInformationImpl(DATASET_ACTION.INFORMATION, registration));
                        }
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Schema version unsupported: " + schemaVersion.toString());
        }
        return returnList;

    }


}
