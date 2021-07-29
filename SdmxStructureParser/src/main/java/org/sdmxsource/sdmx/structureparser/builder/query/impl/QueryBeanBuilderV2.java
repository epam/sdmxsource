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
package org.sdmxsource.sdmx.structureparser.builder.query.impl;

import org.sdmx.resources.sdmxml.schemas.v20.message.QueryMessageType;
import org.sdmx.resources.sdmxml.schemas.v20.query.*;
import org.sdmx.resources.sdmxml.schemas.v20.registry.*;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptSchemeBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Singleton factory pattern to build Version 2 reference beans from query types
 */
public class QueryBeanBuilderV2 {


    /**
     * Build a list of structure references
     *
     * @param queryStructureRequests given structure request query containing lists of references for each structure type.
     * @return list of structure references
     */
    public List<StructureReferenceBean> build(QueryStructureRequestType queryStructureRequests) {
        List<StructureReferenceBean> reutrnList = new ArrayList<StructureReferenceBean>();

        if (queryStructureRequests.getAgencyRefList() != null) {
            for (AgencyRefType agencyRefType : queryStructureRequests.getAgencyRefList()) {
                String urn = agencyRefType.getURN();
                if (ObjectUtil.validString(urn)) {
                    reutrnList.add(new StructureReferenceBeanImpl(urn));
                } else {
                    String agencyId = agencyRefType.getAgencyID();
                    String maintId = agencyRefType.getOrganisationSchemeID();
                    String version = agencyRefType.getOrganisationSchemeAgencyID();
                    reutrnList.add(new StructureReferenceBeanImpl(agencyId, maintId, version, SDMX_STRUCTURE_TYPE.AGENCY));
                }
            }
        }
        if (queryStructureRequests.getCategorySchemeRefList() != null) {
            for (CategorySchemeRefType refType : queryStructureRequests.getCategorySchemeRefList()) {
                String urn = refType.getURN();
                if (ObjectUtil.validString(urn)) {
                    reutrnList.add(new StructureReferenceBeanImpl(urn));
                } else {
                    String agencyId = refType.getAgencyID();
                    String maintId = refType.getCategorySchemeID();
                    String version = refType.getVersion();
                    reutrnList.add(new StructureReferenceBeanImpl(agencyId, maintId, version, SDMX_STRUCTURE_TYPE.CATEGORY_SCHEME));
                }
            }
        }
        if (queryStructureRequests.getCodelistRefList() != null) {
            for (CodelistRefType refType : queryStructureRequests.getCodelistRefList()) {
                String urn = refType.getURN();
                if (ObjectUtil.validString(urn)) {
                    reutrnList.add(new StructureReferenceBeanImpl(urn));
                } else {
                    String agencyId = refType.getAgencyID();
                    String maintId = refType.getCodelistID();
                    String version = refType.getVersion();
                    reutrnList.add(new StructureReferenceBeanImpl(agencyId, maintId, version, SDMX_STRUCTURE_TYPE.CODE_LIST));
                }
            }
        }
        if (queryStructureRequests.getConceptSchemeRefList() != null) {
            for (ConceptSchemeRefType refType : queryStructureRequests.getConceptSchemeRefList()) {
                String urn = refType.getURN();
                if (ObjectUtil.validString(urn)) {
                    reutrnList.add(new StructureReferenceBeanImpl(urn));
                } else {
                    String agencyId = refType.getAgencyID();
                    String maintId = refType.getConceptSchemeID();
                    String version = refType.getVersion();
                    reutrnList.add(new StructureReferenceBeanImpl(agencyId, maintId, version, SDMX_STRUCTURE_TYPE.CONCEPT_SCHEME));
                }
            }
        }
        if (queryStructureRequests.getDataflowRefList() != null) {
            for (DataflowRefType refType : queryStructureRequests.getDataflowRefList()) {
                reutrnList.add(buildDataflowQuery(refType));
            }
        }
        if (queryStructureRequests.getDataProviderRefList() != null) {
            for (DataProviderRefType refType : queryStructureRequests.getDataProviderRefList()) {
                String urn = refType.getURN();
                if (ObjectUtil.validString(urn)) {
                    reutrnList.add(new StructureReferenceBeanImpl(urn));
                } else {
                    String agencyId = refType.getOrganisationSchemeAgencyID();
                    String maintId = refType.getOrganisationSchemeAgencyID();
                    String version = refType.getVersion();
                    String id = refType.getDataProviderID();
                    reutrnList.add(new StructureReferenceBeanImpl(agencyId, maintId, version, SDMX_STRUCTURE_TYPE.DATA_PROVIDER, id));
                }
            }
        }
        if (queryStructureRequests.getHierarchicalCodelistRefList() != null) {
            for (HierarchicalCodelistRefType refType : queryStructureRequests.getHierarchicalCodelistRefList()) {
                String urn = refType.getURN();
                if (ObjectUtil.validString(urn)) {
                    reutrnList.add(new StructureReferenceBeanImpl(urn));
                } else {
                    String agencyId = refType.getAgencyID();
                    String maintId = refType.getHierarchicalCodelistID();
                    String version = refType.getVersion();
                    reutrnList.add(new StructureReferenceBeanImpl(agencyId, maintId, version, SDMX_STRUCTURE_TYPE.HIERARCHICAL_CODELIST));
                }
            }
        }
        if (queryStructureRequests.getKeyFamilyRefList() != null) {
            for (KeyFamilyRefType refType : queryStructureRequests.getKeyFamilyRefList()) {
                String urn = refType.getURN();
                if (ObjectUtil.validString(urn)) {
                    reutrnList.add(new StructureReferenceBeanImpl(urn));
                } else {
                    String agencyId = refType.getAgencyID();
                    String maintId = refType.getKeyFamilyID();
                    String version = refType.getVersion();
                    reutrnList.add(new StructureReferenceBeanImpl(agencyId, maintId, version, SDMX_STRUCTURE_TYPE.DSD));
                }
            }
        }
        if (queryStructureRequests.getMetadataflowRefList() != null) {
            for (MetadataflowRefType refType : queryStructureRequests.getMetadataflowRefList()) {
                String urn = refType.getURN();
                if (ObjectUtil.validString(urn)) {
                    reutrnList.add(new StructureReferenceBeanImpl(urn));
                } else {
                    String agencyId = refType.getAgencyID();
                    String maintId = refType.getMetadataflowID();
                    String version = refType.getVersion();
                    reutrnList.add(new StructureReferenceBeanImpl(agencyId, maintId, version, SDMX_STRUCTURE_TYPE.METADATA_FLOW));
                }
            }
        }
        if (queryStructureRequests.getMetadataStructureRefList() != null) {
            for (MetadataStructureRefType refType : queryStructureRequests.getMetadataStructureRefList()) {
                String urn = refType.getURN();
                if (ObjectUtil.validString(urn)) {
                    reutrnList.add(new StructureReferenceBeanImpl(urn));
                } else {
                    String agencyId = refType.getAgencyID();
                    String maintId = refType.getMetadataStructureID();
                    String version = refType.getVersion();
                    reutrnList.add(new StructureReferenceBeanImpl(agencyId, maintId, version, SDMX_STRUCTURE_TYPE.MSD));
                }
            }
        }
        if (queryStructureRequests.getOrganisationSchemeRefList() != null) {
            for (OrganisationSchemeRefType refType : queryStructureRequests.getOrganisationSchemeRefList()) {
                String urn = refType.getURN();
                if (ObjectUtil.validString(urn)) {
                    reutrnList.add(new StructureReferenceBeanImpl(urn));
                } else {
                    String agencyId = refType.getAgencyID();
                    String maintId = refType.getOrganisationSchemeID();
                    String version = refType.getVersion();
                    reutrnList.add(new StructureReferenceBeanImpl(agencyId, maintId, version, SDMX_STRUCTURE_TYPE.AGENCY_SCHEME));
                    reutrnList.add(new StructureReferenceBeanImpl(agencyId, maintId, version, SDMX_STRUCTURE_TYPE.DATA_CONSUMER_SCHEME));
                    reutrnList.add(new StructureReferenceBeanImpl(agencyId, maintId, version, SDMX_STRUCTURE_TYPE.DATA_PROVIDER_SCHEME));
                }
            }
        }
        if (queryStructureRequests.getProcessRefList() != null) {
            for (ProcessRefType refType : queryStructureRequests.getProcessRefList()) {
                String urn = refType.getURN();
                if (ObjectUtil.validString(urn)) {
                    reutrnList.add(new StructureReferenceBeanImpl(urn));
                } else {
                    String agencyId = refType.getAgencyID();
                    String maintId = refType.getProcessID();
                    String version = refType.getVersion();
                    reutrnList.add(new StructureReferenceBeanImpl(agencyId, maintId, version, SDMX_STRUCTURE_TYPE.PROCESS));
                }
            }
        }
        if (queryStructureRequests.getReportingTaxonomyRefList() != null) {
            for (ReportingTaxonomyRefType refType : queryStructureRequests.getReportingTaxonomyRefList()) {
                String urn = refType.getURN();
                if (ObjectUtil.validString(urn)) {
                    reutrnList.add(new StructureReferenceBeanImpl(urn));
                } else {
                    String agencyId = refType.getAgencyID();
                    String maintId = refType.getReportingTaxonomyID();
                    String version = refType.getVersion();
                    reutrnList.add(new StructureReferenceBeanImpl(agencyId, maintId, version, SDMX_STRUCTURE_TYPE.REPORTING_TAXONOMY));
                }
            }
        }
        if (queryStructureRequests.getStructureSetRefList() != null) {
            for (StructureSetRefType refType : queryStructureRequests.getStructureSetRefList()) {
                String urn = refType.getURN();
                if (ObjectUtil.validString(urn)) {
                    reutrnList.add(new StructureReferenceBeanImpl(urn));
                } else {
                    String agencyId = refType.getAgencyID();
                    String maintId = refType.getStructureSetID();
                    String version = refType.getVersion();
                    reutrnList.add(new StructureReferenceBeanImpl(agencyId, maintId, version, SDMX_STRUCTURE_TYPE.STRUCTURE_SET));
                }
            }
        }
        return reutrnList;
    }

    /**
     * Builds a structure reference from a DataflowRefType, ignores the any Constraint information
     *
     * @param refType the ref type
     * @return structure reference bean
     */
    protected StructureReferenceBean buildDataflowQuery(DataflowRefType refType) {
        String urn = refType.getURN();
        if (ObjectUtil.validString(urn)) {
            return new StructureReferenceBeanImpl(urn);
        } else {
            String agencyId = refType.getAgencyID();
            String maintId = refType.getDataflowID();
            String version = refType.getVersion();
            return new StructureReferenceBeanImpl(agencyId, maintId, version, SDMX_STRUCTURE_TYPE.DATAFLOW);
        }
    }

    /**
     * Build a provision agreement reference bean
     * If only dataProviderRef is supplied then this is used and the flow type is assumed to be a dataflow
     *
     * @param queryRegistrationRequestType given registration request query containing references to its beans
     * @return provision agreement reference bean of query reference, which ever of provision agreement, dataflow, data provider or metadataflow is given, in that order.
     */
    public StructureReferenceBean build(QueryRegistrationRequestType queryRegistrationRequestType) {
        DataflowRefType dataflowRef = queryRegistrationRequestType.getDataflowRef();
        DataProviderRefType dataProviderRef = queryRegistrationRequestType.getDataProviderRef();
        MetadataflowRefType metadataflowRef = queryRegistrationRequestType.getMetadataflowRef();
        ProvisionAgreementRefType provRef = queryRegistrationRequestType.getProvisionAgreementRef();

        if (dataProviderRef != null) {
            if (ObjectUtil.validString(dataProviderRef.getURN())) {
                return new StructureReferenceBeanImpl(dataProviderRef.getURN());
            } else {
                String agencyId = dataProviderRef.getOrganisationSchemeAgencyID();
                String maintId = dataProviderRef.getOrganisationSchemeID();
                String version = dataProviderRef.getVersion();
                String id = dataProviderRef.getDataProviderID();
                return new StructureReferenceBeanImpl(agencyId, maintId, version, SDMX_STRUCTURE_TYPE.DATA_PROVIDER, id);
            }
        }
        if (provRef != null) {
            if (ObjectUtil.validString(provRef.getURN())) {
                return new StructureReferenceBeanImpl(provRef.getURN());
            } else {
                throw new IllegalArgumentException("Version 2.0 query for registration by provision agreement must use the provision URN");
            }
        }
        if (dataflowRef != null) {
            if (ObjectUtil.validString(dataflowRef.getURN())) {
                return new StructureReferenceBeanImpl(dataflowRef.getURN());
            } else {
                String agencyId = dataflowRef.getAgencyID();
                String maintId = dataflowRef.getDataflowID();
                String version = dataflowRef.getVersion();
                return new StructureReferenceBeanImpl(agencyId, maintId, version, SDMX_STRUCTURE_TYPE.DATAFLOW);
            }
        }
        if (metadataflowRef != null) {
            if (ObjectUtil.validString(metadataflowRef.getURN())) {
                return new StructureReferenceBeanImpl(metadataflowRef.getURN());
            } else {
                String agencyId = metadataflowRef.getAgencyID();
                String maintId = metadataflowRef.getMetadataflowID();
                String version = metadataflowRef.getVersion();
                return new StructureReferenceBeanImpl(agencyId, maintId, version, SDMX_STRUCTURE_TYPE.METADATA_FLOW);
            }
        }
        return null;
    }

    /**
     * Build a provision agreement reference bean
     *
     * @param queryProvisionRequestType given provision request query containing references to its beans
     * @return provision agreement reference bean of query reference, which ever of provision agreement, dataflow, metadataflow and data provider is given, in that order.
     */
    public StructureReferenceBean build(QueryProvisioningRequestType queryProvisionRequestType) {
        DataflowRefType dataflowRef = queryProvisionRequestType.getDataflowRef();
        DataProviderRefType dataProviderRef = queryProvisionRequestType.getDataProviderRef();
        MetadataflowRefType metadataflowRef = queryProvisionRequestType.getMetadataflowRef();
        ProvisionAgreementRefType provRef = queryProvisionRequestType.getProvisionAgreementRef();

        if (dataProviderRef != null) {
            if (ObjectUtil.validString(dataProviderRef.getURN())) {
                return new StructureReferenceBeanImpl(dataProviderRef.getURN());
            } else {
                String agencyId = dataProviderRef.getOrganisationSchemeAgencyID();
                String maintId = dataProviderRef.getOrganisationSchemeID();
                String version = dataProviderRef.getVersion();
                String id = dataProviderRef.getDataProviderID();
                return new StructureReferenceBeanImpl(agencyId, maintId, version, SDMX_STRUCTURE_TYPE.DATA_PROVIDER, id);
            }
        }
        if (provRef != null) {
            if (ObjectUtil.validString(provRef.getURN())) {
                return new StructureReferenceBeanImpl(provRef.getURN());
            }
        }
        if (dataflowRef != null) {
            if (ObjectUtil.validString(dataflowRef.getURN())) {
                return new StructureReferenceBeanImpl(dataflowRef.getURN());
            } else {
                String agencyId = dataflowRef.getAgencyID();
                String maintId = dataflowRef.getDataflowID();
                String version = dataflowRef.getVersion();
                return new StructureReferenceBeanImpl(agencyId, maintId, version, SDMX_STRUCTURE_TYPE.DATAFLOW);
            }
        }
        if (metadataflowRef != null) {
            if (ObjectUtil.validString(metadataflowRef.getURN())) {
                return new StructureReferenceBeanImpl(metadataflowRef.getURN());
            } else {
                String agencyId = metadataflowRef.getAgencyID();
                String maintId = metadataflowRef.getMetadataflowID();
                String version = metadataflowRef.getVersion();
                return new StructureReferenceBeanImpl(agencyId, maintId, version, SDMX_STRUCTURE_TYPE.METADATA_FLOW);
            }
        }
        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "At version 2.0 provisions can only be queryies by Provision URN, Dataflow Ref, Data Provider Ref or Metadata Flow Ref");
    }

    /**
     * Build a list of structure references
     *
     * @param queryMessage given message query containing lists of references for each structure type.
     * @return list of structure references
     */
    public List<StructureReferenceBean> build(QueryMessageType queryMessage) {
        List<StructureReferenceBean> reutrnList = new ArrayList<StructureReferenceBean>();

        if (queryMessage.getQuery() != null) {
            QueryType queryType = queryMessage.getQuery();
            if (queryType.getAgencyWhereList() != null) {
                if (ObjectUtil.validCollection(queryMessage.getQuery().getAgencyWhereList())) {
                    throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "AgencyWhere");
                }
            }
            if (queryType.getCategorySchemeWhereList() != null) {
                for (CategorySchemeWhereType structQuery : queryType.getCategorySchemeWhereList()) {
                    String agencyId = structQuery.getAgencyID();
                    String maintId = structQuery.getID();
                    String version = structQuery.getVersion();
                    SDMX_STRUCTURE_TYPE structType = SDMX_STRUCTURE_TYPE.CATEGORY_SCHEME;
                    StructureReferenceBean refBean = new StructureReferenceBeanImpl(agencyId, maintId, version, structType);
                    reutrnList.add(refBean);
                }
            }
            if (queryType.getCodelistWhereList() != null) {
                for (CodelistWhereType structQuery : queryType.getCodelistWhereList()) {
                    String codelistId = null;
                    if (structQuery.getCodelist() != null) {
                        codelistId = structQuery.getCodelist().getId();
                    }
                    String agencyId = structQuery.getAgencyID();
                    String maintId = codelistId;
                    String version = structQuery.getVersion();
                    SDMX_STRUCTURE_TYPE structType = SDMX_STRUCTURE_TYPE.CODE_LIST;
                    StructureReferenceBean refBean = new StructureReferenceBeanImpl(agencyId, maintId, version, structType);
                    reutrnList.add(refBean);
                    if (structQuery.getOr() != null) {
                        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "CodelistWhere/Or");
                    }
                    if (structQuery.getAnd() != null) {
                        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "CodelistWhere/And");
                    }
                }
            }
            if (queryType.getConceptSchemeWhereList() != null) {
                for (ConceptSchemeWhereType structQuery : queryType.getConceptSchemeWhereList()) {
                    String agencyId = structQuery.getAgencyID();
                    String maintId = structQuery.getID();
                    String version = structQuery.getVersion();
                    SDMX_STRUCTURE_TYPE structType = SDMX_STRUCTURE_TYPE.CONCEPT_SCHEME;
                    StructureReferenceBean refBean = new StructureReferenceBeanImpl(agencyId, maintId, version, structType);
                    reutrnList.add(refBean);
                }
            }
            if (queryType.getConceptWhereList() != null) {
                for (ConceptWhereType structQuery : queryType.getConceptWhereList()) {
                    String agencyId = structQuery.getAgencyID();
                    String conceptId = structQuery.getConcept();
                    SDMX_STRUCTURE_TYPE structType = SDMX_STRUCTURE_TYPE.CONCEPT;
                    StructureReferenceBean refBean = new StructureReferenceBeanImpl(agencyId, ConceptSchemeBean.DEFAULT_SCHEME_ID, ConceptSchemeBean.DEFAULT_SCHEME_VERSION, structType, conceptId);
                    reutrnList.add(refBean);
                    if (structQuery.getOr() != null) {
                        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "ConceptWhere/Or");
                    }
                    if (structQuery.getAnd() != null) {
                        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "ConceptWhere/And");
                    }
                }
            }
            if (queryType.getDataflowWhereList() != null) {
                for (DataflowWhereType structQuery : queryType.getDataflowWhereList()) {
                    String agencyId = structQuery.getAgencyID();
                    String maintId = structQuery.getID();
                    String version = structQuery.getVersion();
                    SDMX_STRUCTURE_TYPE structType = SDMX_STRUCTURE_TYPE.DATAFLOW;
                    StructureReferenceBean refBean = new StructureReferenceBeanImpl(agencyId, maintId, version, structType);
                    reutrnList.add(refBean);
                }
            }
            if (queryType.getDataProviderWhereList() != null && queryType.getDataProviderWhereList().size() > 0) {
                throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "DataProviderWhere");
            }
            if (queryType.getDataWhereList() != null && queryType.getDataWhereList().size() > 0) {
                throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "DataWhere");
            }
            if (queryType.getHierarchicalCodelistWhereList() != null) {
                for (HierarchicalCodelistWhereType structQuery : queryType.getHierarchicalCodelistWhereList()) {
                    String agencyId = structQuery.getAgencyID();
                    String maintId = structQuery.getID();
                    String version = structQuery.getVersion();
                    SDMX_STRUCTURE_TYPE structType = SDMX_STRUCTURE_TYPE.HIERARCHICAL_CODELIST;
                    StructureReferenceBean refBean = new StructureReferenceBeanImpl(agencyId, maintId, version, structType);
                    reutrnList.add(refBean);
                }
            }
            if (queryType.getKeyFamilyWhereList() != null) {
                for (KeyFamilyWhereType structQuery : queryType.getKeyFamilyWhereList()) {
                    String agencyId = structQuery.getAgencyID();
                    String maintId = structQuery.getKeyFamily();
                    String version = structQuery.getVersion();
                    SDMX_STRUCTURE_TYPE structType = SDMX_STRUCTURE_TYPE.DSD;
                    StructureReferenceBean refBean = new StructureReferenceBeanImpl(agencyId, maintId, version, structType);
                    reutrnList.add(refBean);

                    if (structQuery.getOr() != null) {
                        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "KeyFamilyWhere/Or");
                    }
                    if (structQuery.getAnd() != null) {
                        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "KeyFamilyWhere/And");
                    }
                    if (structQuery.getDimension() != null) {
                        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "KeyFamilyWhere/Dimension");
                    }
                    if (structQuery.getAttribute() != null) {
                        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "KeyFamilyWhere/Attribute");
                    }
                    if (structQuery.getCodelist() != null) {
                        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "KeyFamilyWhere/Codelist");
                    }
                    if (structQuery.getCategory() != null) {
                        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "KeyFamilyWhere/Category");
                    }
                    if (structQuery.getConcept() != null) {
                        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "KeyFamilyWhere/Concept");
                    }
                }
            }
            if (queryType.getMetadataflowWhereList() != null) {
                for (MetadataflowWhereType structQuery : queryType.getMetadataflowWhereList()) {
                    String agencyId = structQuery.getAgencyID();
                    String maintId = structQuery.getID();
                    String version = structQuery.getVersion();
                    SDMX_STRUCTURE_TYPE structType = SDMX_STRUCTURE_TYPE.METADATA_FLOW;
                    StructureReferenceBean refBean = new StructureReferenceBeanImpl(agencyId, maintId, version, structType);
                    reutrnList.add(refBean);
                }
            }
            if (queryType.getMetadataWhereList() != null && queryType.getMetadataWhereList().size() > 0) {
                throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "DataWhere");
            }
            if (queryType.getOrganisationSchemeWhereList() != null) {
                for (OrganisationSchemeWhereType structQuery : queryType.getOrganisationSchemeWhereList()) {
                    String agencyId = structQuery.getAgencyID();
                    String maintId = structQuery.getID();
                    String version = structQuery.getVersion();
                    SDMX_STRUCTURE_TYPE structType = SDMX_STRUCTURE_TYPE.ORGANISATION_UNIT_SCHEME;
                    StructureReferenceBean refBean = new StructureReferenceBeanImpl(agencyId, maintId, version, structType);
                    reutrnList.add(refBean);
                }
            }
            if (queryType.getProcessWhereList() != null) {
                for (ProcessWhereType structQuery : queryType.getProcessWhereList()) {
                    String agencyId = structQuery.getAgencyID();
                    String maintId = structQuery.getID();
                    String version = structQuery.getVersion();
                    SDMX_STRUCTURE_TYPE structType = SDMX_STRUCTURE_TYPE.PROCESS;
                    StructureReferenceBean refBean = new StructureReferenceBeanImpl(agencyId, maintId, version, structType);
                    reutrnList.add(refBean);
                }
            }
            if (queryType.getStructureSetWhereList() != null) {
                for (StructureSetWhereType structQuery : queryType.getStructureSetWhereList()) {
                    String agencyId = structQuery.getAgencyID();
                    String maintId = structQuery.getID();
                    String version = structQuery.getVersion();
                    SDMX_STRUCTURE_TYPE structType = SDMX_STRUCTURE_TYPE.STRUCTURE_SET;
                    StructureReferenceBean refBean = new StructureReferenceBeanImpl(agencyId, maintId, version, structType);
                    reutrnList.add(refBean);
                }
            }
            if (queryType.getReportingTaxonomyWhereList() != null) {
                for (ReportingTaxonomyWhereType structQuery : queryType.getReportingTaxonomyWhereList()) {
                    String agencyId = structQuery.getAgencyID();
                    String maintId = structQuery.getID();
                    String version = structQuery.getVersion();
                    SDMX_STRUCTURE_TYPE structType = SDMX_STRUCTURE_TYPE.REPORTING_TAXONOMY;
                    StructureReferenceBean refBean = new StructureReferenceBeanImpl(agencyId, maintId, version, structType);
                    reutrnList.add(refBean);
                }
            }
        }
        return reutrnList;
    }
}
