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
import org.sdmx.resources.sdmxml.schemas.v20.message.HeaderType;
import org.sdmx.resources.sdmxml.schemas.v20.message.RegistryInterfaceDocument;
import org.sdmx.resources.sdmxml.schemas.v20.message.RegistryInterfaceType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.*;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v2.StructureHeaderXmlBeanBuilder;
import org.sdmxsource.util.ObjectUtil;

import java.util.Set;


/**
 * The type Submit structure response builder v 2.
 */
//JAVADOC missing
public class SubmitStructureResponseBuilderV2 extends AbstractResponseBuilder {

    private final StructureHeaderXmlBeanBuilder headerXmlBeansBuilder = new StructureHeaderXmlBeanBuilder();


    /**
     * Instantiates a new Submit structure response builder v 2.
     */
//PRIVATE CONSTRUCTOR
    public SubmitStructureResponseBuilderV2() {
    }

    /**
     * Build error response registry interface document.
     *
     * @param th        the th
     * @param errorBean the error bean
     * @return the registry interface document
     * @throws SdmxException the sdmx exception
     */
    public RegistryInterfaceDocument buildErrorResponse(Throwable th, StructureReferenceBean errorBean) throws SdmxException {
        RegistryInterfaceDocument responseType = RegistryInterfaceDocument.Factory.newInstance();
        RegistryInterfaceType regInterface = responseType.addNewRegistryInterface();
        SubmitStructureResponseType returnType = regInterface.addNewSubmitStructureResponse();
        V2Helper.setHeader(regInterface);
        processMaintainable(returnType, errorBean, th);
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

        HeaderType headerType = null;
        if (beans.getHeader() != null) {
            headerType = headerXmlBeansBuilder.build(beans.getHeader());
            regInterface.setHeader(headerType);
        } else {
            headerType = regInterface.addNewHeader();
            V2Helper.setHeader(headerType, beans);
        }

        processMaintainables(returnType, beans.getAllMaintainables());
        return responseType;
    }

    private void processMaintainables(SubmitStructureResponseType returnType, Set<MaintainableBean> maints) {
        for (MaintainableBean maint : maints) {
            processMaintainable(returnType, maint.asReference(), null);
        }
    }

    private void processMaintainable(SubmitStructureResponseType returnType, StructureReferenceBean sRef, Throwable th) {
        if (sRef == null) {
            addNewOrganisationSchemeRefSubmissionResult(returnType, sRef, th);
        }
        switch (sRef.getTargetReference()) {
            case AGENCY_SCHEME:
                addNewOrganisationSchemeRefSubmissionResult(returnType, sRef, th);
                break;
            case DATA_PROVIDER_SCHEME:
                addNewOrganisationSchemeRefSubmissionResult(returnType, sRef, th);
                break;
            case DATA_CONSUMER_SCHEME:
                addNewOrganisationSchemeRefSubmissionResult(returnType, sRef, th);
                break;
            case CATEGORY_SCHEME:
                addNewCategorySchemeRefSubmissionResult(returnType, sRef, th);
                break;
            case DATAFLOW:
                addNewDataflowRefSubmissionResult(returnType, sRef, th);
                break;
            case METADATA_FLOW:
                addNewMetadataflowRefSubmissionResult(returnType, sRef, th);
                break;
            case CODE_LIST:
                addNewCodelistRefSubmissionResult(returnType, sRef, th);
                break;
            case HIERARCHICAL_CODELIST:
                addNewHierarchicalCodelistRefSubmissionResult(returnType, sRef, th);
                break;
            case CONCEPT_SCHEME:
                addNewConceptSchemeRefSubmissionResult(returnType, sRef, th);
                break;
            case ORGANISATION_UNIT_SCHEME:
                addNewOrganisationSchemeRefSubmissionResult(returnType, sRef, th);
                break;
            case DSD:
                addNewDataStructureRefSubmissionResult(returnType, sRef, th);
                break;
            case MSD:
                addNewMetadataStructureRefSubmissionResult(returnType, sRef, th);
                break;
            case PROCESS:
                addNewProcessRefSubmissionResult(returnType, sRef, th);
                break;
            case STRUCTURE_SET:
                addNewStructureSetRefSubmissionResult(returnType, sRef, th);
                break;
            case REPORTING_TAXONOMY:
                addNewReportingTaxonomySetRefSubmissionResult(returnType, sRef, th);
                break;
        }
    }


    private void addNewReportingTaxonomySetRefSubmissionResult(SubmitStructureResponseType returnType, StructureReferenceBean sRef, Throwable th) {
        SubmissionResultType result = getNewSubmissionResultType(returnType, th);
        SubmittedStructureType submittedStructure = result.addNewSubmittedStructure();
        ReportingTaxonomyRefType refType = submittedStructure.addNewReportingTaxonomyRef();


        MaintainableRefBean mRef = sRef.getMaintainableReference();
        refType.setAgencyID(mRef.getAgencyId());
        refType.setReportingTaxonomyID(mRef.getMaintainableId());
        if (ObjectUtil.validString(sRef.getMaintainableUrn())) {
            refType.setURN(sRef.getMaintainableUrn());
        }
        if (ObjectUtil.validString(mRef.getVersion())) {
            refType.setVersion(mRef.getVersion());
        }
    }

    private void addNewStructureSetRefSubmissionResult(SubmitStructureResponseType returnType, StructureReferenceBean sRef, Throwable th) {
        SubmissionResultType result = getNewSubmissionResultType(returnType, th);
        SubmittedStructureType submittedStructure = result.addNewSubmittedStructure();
        StructureSetRefType refType = submittedStructure.addNewStructureSetRef();

        MaintainableRefBean mRef = sRef.getMaintainableReference();
        refType.setAgencyID(mRef.getAgencyId());
        refType.setStructureSetID(mRef.getMaintainableId());
        if (ObjectUtil.validString(sRef.getMaintainableUrn())) {
            refType.setURN(sRef.getMaintainableUrn());
        }
        if (ObjectUtil.validString(mRef.getVersion())) {
            refType.setVersion(mRef.getVersion());
        }
    }

    private void addNewProcessRefSubmissionResult(SubmitStructureResponseType returnType, StructureReferenceBean sRef, Throwable th) {
        SubmissionResultType result = getNewSubmissionResultType(returnType, th);
        SubmittedStructureType submittedStructure = result.addNewSubmittedStructure();
        ProcessRefType refType = submittedStructure.addNewProcessRef();

        MaintainableRefBean mRef = sRef.getMaintainableReference();
        refType.setAgencyID(mRef.getAgencyId());
        refType.setProcessID(mRef.getMaintainableId());
        if (ObjectUtil.validString(sRef.getMaintainableUrn())) {
            refType.setURN(sRef.getMaintainableUrn());
        }
        if (ObjectUtil.validString(mRef.getVersion())) {
            refType.setVersion(mRef.getVersion());
        }
    }

    private void addNewMetadataStructureRefSubmissionResult(SubmitStructureResponseType returnType, StructureReferenceBean sRef, Throwable th) {
        SubmissionResultType result = getNewSubmissionResultType(returnType, th);
        SubmittedStructureType submittedStructure = result.addNewSubmittedStructure();
        MetadataStructureRefType refType = submittedStructure.addNewMetadataStructureRef();

        MaintainableRefBean mRef = sRef.getMaintainableReference();
        refType.setAgencyID(mRef.getAgencyId());
        refType.setMetadataStructureID(mRef.getMaintainableId());
        if (ObjectUtil.validString(sRef.getMaintainableUrn())) {
            refType.setURN(sRef.getMaintainableUrn());
        }
        if (ObjectUtil.validString(mRef.getVersion())) {
            refType.setVersion(mRef.getVersion());
        }
    }

    private void addNewDataStructureRefSubmissionResult(SubmitStructureResponseType returnType, StructureReferenceBean sRef, Throwable th) {
        SubmissionResultType result = getNewSubmissionResultType(returnType, th);
        SubmittedStructureType submittedStructure = result.addNewSubmittedStructure();
        KeyFamilyRefType refType = submittedStructure.addNewKeyFamilyRef();

        MaintainableRefBean mRef = sRef.getMaintainableReference();
        refType.setAgencyID(mRef.getAgencyId());
        refType.setKeyFamilyID(mRef.getMaintainableId());
        if (ObjectUtil.validString(sRef.getMaintainableUrn())) {
            refType.setURN(sRef.getMaintainableUrn());
        }
        if (ObjectUtil.validString(mRef.getVersion())) {
            refType.setVersion(mRef.getVersion());
        }
    }

    private void addNewOrganisationSchemeRefSubmissionResult(SubmitStructureResponseType returnType, StructureReferenceBean sRef, Throwable th) {
        SubmissionResultType result = getNewSubmissionResultType(returnType, th);
        SubmittedStructureType submittedStructure = result.addNewSubmittedStructure();
        OrganisationSchemeRefType refType = submittedStructure.addNewOrganisationSchemeRef();
        if (sRef == null) {
            refType.setAgencyID("NOT_APPLICABLE");
        } else {
            MaintainableRefBean mRef = sRef.getMaintainableReference();
            refType.setAgencyID(mRef.getAgencyId());
            refType.setOrganisationSchemeID(mRef.getMaintainableId());
            if (ObjectUtil.validString(sRef.getMaintainableUrn())) {
                refType.setURN(sRef.getMaintainableUrn());
            }
            if (ObjectUtil.validString(mRef.getVersion())) {
                refType.setVersion(mRef.getVersion());
            }
        }
    }

    private void addNewConceptSchemeRefSubmissionResult(SubmitStructureResponseType returnType, StructureReferenceBean sRef, Throwable th) {
        SubmissionResultType result = getNewSubmissionResultType(returnType, th);
        SubmittedStructureType submittedStructure = result.addNewSubmittedStructure();
        ConceptSchemeRefType refType = submittedStructure.addNewConceptSchemeRef();

        MaintainableRefBean mRef = sRef.getMaintainableReference();
        refType.setAgencyID(mRef.getAgencyId());
        refType.setConceptSchemeID(mRef.getMaintainableId());
        if (ObjectUtil.validString(sRef.getMaintainableUrn())) {
            refType.setURN(sRef.getMaintainableUrn());
        }
        if (ObjectUtil.validString(mRef.getVersion())) {
            refType.setVersion(mRef.getVersion());
        }
    }

    private void addNewHierarchicalCodelistRefSubmissionResult(SubmitStructureResponseType returnType, StructureReferenceBean sRef, Throwable th) {
        SubmissionResultType result = getNewSubmissionResultType(returnType, th);
        SubmittedStructureType submittedStructure = result.addNewSubmittedStructure();
        HierarchicalCodelistRefType refType = submittedStructure.addNewHierarchicalCodelistRef();

        MaintainableRefBean mRef = sRef.getMaintainableReference();
        refType.setAgencyID(mRef.getAgencyId());
        refType.setHierarchicalCodelistID(mRef.getMaintainableId());
        if (ObjectUtil.validString(sRef.getMaintainableUrn())) {
            refType.setURN(sRef.getMaintainableUrn());
        }
        if (ObjectUtil.validString(mRef.getVersion())) {
            refType.setVersion(mRef.getVersion());
        }
    }

    private void addNewCodelistRefSubmissionResult(SubmitStructureResponseType returnType, StructureReferenceBean sRef, Throwable th) {
        SubmissionResultType result = getNewSubmissionResultType(returnType, th);
        SubmittedStructureType submittedStructure = result.addNewSubmittedStructure();
        CodelistRefType refType = submittedStructure.addNewCodelistRef();

        MaintainableRefBean mRef = sRef.getMaintainableReference();
        refType.setAgencyID(mRef.getAgencyId());
        refType.setCodelistID(mRef.getMaintainableId());
        if (ObjectUtil.validString(sRef.getMaintainableUrn())) {
            refType.setURN(sRef.getMaintainableUrn());
        }
        if (ObjectUtil.validString(mRef.getVersion())) {
            refType.setVersion(mRef.getVersion());
        }
    }

    private void addNewMetadataflowRefSubmissionResult(SubmitStructureResponseType returnType, StructureReferenceBean sRef, Throwable th) {
        SubmissionResultType result = getNewSubmissionResultType(returnType, th);
        SubmittedStructureType submittedStructure = result.addNewSubmittedStructure();
        MetadataflowRefType refType = submittedStructure.addNewMetadataflowRef();

        MaintainableRefBean mRef = sRef.getMaintainableReference();
        refType.setAgencyID(mRef.getAgencyId());
        refType.setMetadataflowID(mRef.getMaintainableId());
        if (ObjectUtil.validString(sRef.getMaintainableUrn())) {
            refType.setURN(sRef.getMaintainableUrn());
        }
        if (ObjectUtil.validString(mRef.getVersion())) {
            refType.setVersion(mRef.getVersion());
        }
    }

    private void addNewDataflowRefSubmissionResult(SubmitStructureResponseType returnType, StructureReferenceBean sRef, Throwable th) {
        SubmissionResultType result = getNewSubmissionResultType(returnType, th);
        SubmittedStructureType submittedStructure = result.addNewSubmittedStructure();
        DataflowRefType refType = submittedStructure.addNewDataflowRef();

        MaintainableRefBean mRef = sRef.getMaintainableReference();
        refType.setAgencyID(mRef.getAgencyId());
        refType.setDataflowID(mRef.getMaintainableId());
        if (ObjectUtil.validString(sRef.getMaintainableUrn())) {
            refType.setURN(sRef.getMaintainableUrn());
        }
        if (ObjectUtil.validString(mRef.getVersion())) {
            refType.setVersion(mRef.getVersion());
        }
    }

    private void addNewCategorySchemeRefSubmissionResult(SubmitStructureResponseType returnType, StructureReferenceBean sRef, Throwable th) {
        SubmissionResultType result = getNewSubmissionResultType(returnType, th);
        SubmittedStructureType submittedStructure = result.addNewSubmittedStructure();
        CategorySchemeRefType refType = submittedStructure.addNewCategorySchemeRef();

        MaintainableRefBean mRef = sRef.getMaintainableReference();
        refType.setAgencyID(mRef.getAgencyId());
        refType.setCategorySchemeID(mRef.getMaintainableId());
        if (ObjectUtil.validString(sRef.getMaintainableUrn())) {
            refType.setURN(sRef.getMaintainableUrn());
        }
        if (ObjectUtil.validString(mRef.getVersion())) {
            refType.setVersion(mRef.getVersion());
        }
    }

    private SubmissionResultType getNewSubmissionResultType(SubmitStructureResponseType returnType, Throwable th) {
        SubmissionResultType submissionResult = returnType.addNewSubmissionResult();
        StatusMessageType statusMessage = submissionResult.addNewStatusMessage();
        if (th == null) {
            statusMessage.setStatus(StatusType.SUCCESS);
        } else {
            statusMessage.setStatus(StatusType.FAILURE);
            TextType tt = statusMessage.addNewMessageText();
            if (th instanceof SdmxException) {
                tt.setStringValue(((SdmxException) th).getFullMessage());
            } else {
                tt.setStringValue(th.getMessage());
            }
        }
        return submissionResult;
    }
}
