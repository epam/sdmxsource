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

import org.sdmx.resources.sdmxml.schemas.v20.message.RegistryInterfaceDocument;
import org.sdmx.resources.sdmxml.schemas.v20.registry.ProvisionAgreementType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.registry.ProvisionAgreementBeanImpl;
import org.sdmxsource.sdmx.structureparser.manager.parsing.ProvisionParsingManager;
import org.sdmxsource.sdmx.util.sdmx.SdmxMessageUtil;
import org.sdmxsource.springutil.xml.XMLParser;
import org.sdmxsource.util.ObjectUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

//JAVADOC missing

/**
 * The type Provision parsing manager.
 */
public class ProvisionParsingManagerImpl implements ProvisionParsingManager {

    //TODO Handle error responses
    @Override
    public List<ProvisionAgreementBean> parseXML(ReadableDataLocation dataLocation) {
        SDMX_SCHEMA schemaVersion = SdmxMessageUtil.getSchemaVersion(dataLocation);
        XMLParser.validateXML(dataLocation, schemaVersion);
        InputStream stream = dataLocation.getInputStream();
        try {
            List<ProvisionAgreementBean> returnList = new ArrayList<ProvisionAgreementBean>();
            switch (schemaVersion) {
                case VERSION_TWO:
                    RegistryInterfaceDocument rid = RegistryInterfaceDocument.Factory.parse(stream);
                    if (rid.getRegistryInterface().getQueryProvisioningResponse() != null &&
                            rid.getRegistryInterface().getQueryProvisioningResponse().getProvisionAgreementList() != null) {
                        for (ProvisionAgreementType provType : rid.getRegistryInterface().getQueryProvisioningResponse().getProvisionAgreementList()) {
                            returnList.add(new ProvisionAgreementBeanImpl(provType));
                        }
                        //FUNC Support provisions by these types
                        if (ObjectUtil.validCollection(rid.getRegistryInterface().getQueryProvisioningResponse().getDataflowRefList())) {
                            throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "Provision for Dataflow");
                        }
                        if (ObjectUtil.validCollection(rid.getRegistryInterface().getQueryProvisioningResponse().getMetadataflowRefList())) {
                            throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "Provision for Metadataflow");
                        }
                        if (ObjectUtil.validCollection(rid.getRegistryInterface().getQueryProvisioningResponse().getDataProviderRefList())) {
                            throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "Provision for Dataprovider");
                        }
                    }
                    if (rid.getRegistryInterface().getSubmitProvisioningRequest() != null &&
                            rid.getRegistryInterface().getSubmitProvisioningRequest().getProvisionAgreementList() != null) {
                        for (ProvisionAgreementType provType : rid.getRegistryInterface().getSubmitProvisioningRequest().getProvisionAgreementList()) {
                            returnList.add(new ProvisionAgreementBeanImpl(provType));
                        }
                        //FUNC Support provisions by these types
                        if (ObjectUtil.validCollection(rid.getRegistryInterface().getSubmitProvisioningRequest().getDataflowRefList())) {
                            throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "Submit Provision for Dataflow");
                        }
                        if (ObjectUtil.validCollection(rid.getRegistryInterface().getSubmitProvisioningRequest().getMetadatataflowRefList())) {
                            throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "Submit Provision for Metadataflow");
                        }
                        if (ObjectUtil.validCollection(rid.getRegistryInterface().getSubmitProvisioningRequest().getDataProviderRefList())) {
                            throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "Submit Provision for Dataprovider");
                        }
                    }
                    break;
                default:
                    throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, schemaVersion);
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
