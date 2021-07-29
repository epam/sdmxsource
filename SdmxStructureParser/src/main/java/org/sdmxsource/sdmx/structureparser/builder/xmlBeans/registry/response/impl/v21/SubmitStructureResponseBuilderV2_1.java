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

import org.sdmx.resources.sdmxml.schemas.v21.common.MaintainableReferenceType;
import org.sdmx.resources.sdmxml.schemas.v21.common.RefBaseType;
import org.sdmx.resources.sdmxml.schemas.v21.message.RegistryInterfaceDocument;
import org.sdmx.resources.sdmxml.schemas.v21.message.RegistryInterfaceType;
import org.sdmx.resources.sdmxml.schemas.v21.registry.SubmissionResultType;
import org.sdmx.resources.sdmxml.schemas.v21.registry.SubmitStructureResponseType;
import org.sdmx.resources.sdmxml.schemas.v21.registry.SubmittedStructureType;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.StructureHeaderXmlBeanBuilder;
import org.sdmxsource.util.ObjectUtil;

import java.util.Set;


/**
 * The type Submit structure response builder v 2 1.
 */
//JAVADOC missing
public class SubmitStructureResponseBuilderV2_1 extends AbstractResponseBuilder {

    private final StructureHeaderXmlBeanBuilder headerXmlBeansBuilder = new StructureHeaderXmlBeanBuilder();

    /**
     * Build error response registry interface document.
     *
     * @param th        the th
     * @param errorBean the error bean
     * @return the registry interface document
     * @throws SdmxException the sdmx exception
     */
    public RegistryInterfaceDocument buildErrorResponse(Throwable th, StructureReferenceBean errorBean) throws SdmxException {
        if (errorBean == null) {
            throw new SdmxSemmanticException(th, "Registry could not determine Maintainable Artefact in error");
        }
        RegistryInterfaceDocument responseType = RegistryInterfaceDocument.Factory.newInstance();
        RegistryInterfaceType regInterface = responseType.addNewRegistryInterface();
        SubmitStructureResponseType returnType = regInterface.addNewSubmitStructureResponse();
        V2_1Helper.setHeader(regInterface);
        addSubmissionResult(returnType, errorBean, th);
        return responseType;
    }

    /**
     * Build success response registry interface document.
     *
     * @param beans the beans
     * @return the registry interface document
     * @throws SdmxException the sdmx exception
     */
    public RegistryInterfaceDocument buildSuccessResponse(SdmxBeans beans) throws SdmxException {
        RegistryInterfaceDocument responseType = RegistryInterfaceDocument.Factory.newInstance();
        RegistryInterfaceType regInterface = responseType.addNewRegistryInterface();
        SubmitStructureResponseType returnType = regInterface.addNewSubmitStructureResponse();

        regInterface.setHeader(headerXmlBeansBuilder.build(beans.getHeader()));

        processMaintainables(returnType, beans.getAllMaintainables());
        return responseType;
    }

    private void processMaintainables(SubmitStructureResponseType returnType, Set<MaintainableBean> maints) {
        for (MaintainableBean maint : maints) {
            addSubmissionResult(returnType, maint.asReference(), null);
        }
    }

    private void addSubmissionResult(SubmitStructureResponseType returnType, StructureReferenceBean sRef, Throwable th) {
        SubmissionResultType submissionResult = returnType.addNewSubmissionResult();
        addStatus(submissionResult.addNewStatusMessage(), th);
        SubmittedStructureType submittedStructure = submissionResult.addNewSubmittedStructure();
        MaintainableReferenceType refType = submittedStructure.addNewMaintainableObject();
        if (ObjectUtil.validString(sRef.getMaintainableUrn())) {
            refType.setURN(sRef.getMaintainableUrn());
        } else {
            RefBaseType ref = refType.addNewRef();
            MaintainableRefBean mRef = sRef.getMaintainableReference();
            if (ObjectUtil.validString(mRef.getAgencyId())) {
                ref.setAgencyID(mRef.getAgencyId());
            }
            if (ObjectUtil.validString(mRef.getMaintainableId())) {
                ref.setAgencyID(mRef.getMaintainableId());
            }
            if (ObjectUtil.validString(mRef.getVersion())) {
                ref.setAgencyID(mRef.getVersion());
            }
        }
    }
}
