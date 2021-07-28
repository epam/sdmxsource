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
import org.sdmx.resources.sdmxml.schemas.v20.registry.ProvisionAgreementRefType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.ProvisioningStatusType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.StatusMessageType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.SubmitProvisioningResponseType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.util.ObjectUtil;

import java.util.Collection;


//JAVADOC missing

/**
 * The type Submit provision response builder v 2.
 */
public class SubmitProvisionResponseBuilderV2 extends AbstractResponseBuilder {
    /**
     * The constant INSTANCE.
     */
    public static SubmitProvisionResponseBuilderV2 INSTANCE = new SubmitProvisionResponseBuilderV2();

    //PRIVATE CONSTRUCTOR
    private SubmitProvisionResponseBuilderV2() {
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
        SubmitProvisioningResponseType returnType = regInterface.addNewSubmitProvisioningResponse();
        V2Helper.setHeader(regInterface);
        ProvisioningStatusType statusType = returnType.addNewProvisioningStatus();
        StatusMessageType statusMessage = statusType.addNewStatusMessage();
        addStatus(statusMessage, th);
        return responseType;
    }

    /**
     * Build response registry interface document.
     *
     * @param response the response
     * @return the registry interface document
     */
    public RegistryInterfaceDocument buildResponse(Collection<ProvisionAgreementBean> response) {
        RegistryInterfaceDocument responseType = RegistryInterfaceDocument.Factory.newInstance();
        RegistryInterfaceType regInterface = responseType.addNewRegistryInterface();
        SubmitProvisioningResponseType returnType = regInterface.addNewSubmitProvisioningResponse();
        V2Helper.setHeader(regInterface);
        for (ProvisionAgreementBean provisionAgreement : response) {
            processResponse(returnType, provisionAgreement);
        }

        return responseType;
    }

    private void processResponse(SubmitProvisioningResponseType returnType, ProvisionAgreementBean provisionAgreement) {
        ProvisioningStatusType statusType = returnType.addNewProvisioningStatus();
        StatusMessageType statusMessage = statusType.addNewStatusMessage();
        addStatus(statusMessage, null);

        ProvisionAgreementRefType provRefType = statusType.addNewProvisionAgreementRef();

        if (ObjectUtil.validString(provisionAgreement.getUrn())) {
            provRefType.setURN(provisionAgreement.getUrn());
        }
        if (provisionAgreement.getDataproviderRef() != null) {
            CrossReferenceBean crossRef = provisionAgreement.getDataproviderRef();
            MaintainableRefBean maintRef = crossRef.getMaintainableReference();
            if (ObjectUtil.validString(maintRef.getAgencyId())) {
                provRefType.setOrganisationSchemeAgencyID(maintRef.getAgencyId());
            }
            if (ObjectUtil.validString(maintRef.getMaintainableId())) {
                provRefType.setOrganisationSchemeID(maintRef.getMaintainableId());
            }
            if (crossRef.getChildReference() != null && ObjectUtil.validString(crossRef.getChildReference().getId())) {
                provRefType.setDataProviderID(crossRef.getChildReference().getId());
            }
            if (ObjectUtil.validString(maintRef.getVersion())) {
                provRefType.setDataProviderVersion(maintRef.getVersion());
            }
        }
        if (provisionAgreement.getStructureUseage() != null) {
            CrossReferenceBean structUseageCrossRef = provisionAgreement.getStructureUseage();
            MaintainableRefBean maintRef = structUseageCrossRef.getMaintainableReference();
            if (structUseageCrossRef.getTargetReference() == SDMX_STRUCTURE_TYPE.DATAFLOW) {
                if (ObjectUtil.validString(maintRef.getAgencyId())) {
                    provRefType.setDataflowAgencyID(maintRef.getAgencyId());
                }
                if (ObjectUtil.validString(maintRef.getMaintainableId())) {
                    provRefType.setDataflowID(maintRef.getMaintainableId());
                }
                if (ObjectUtil.validString(maintRef.getVersion())) {
                    provRefType.setDataflowVersion(maintRef.getVersion());
                }
            }
        }
    }
}
