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

import org.sdmx.resources.sdmxml.schemas.v21.common.*;
import org.sdmx.resources.sdmxml.schemas.v21.message.CategorisationQueryType;
import org.sdmx.resources.sdmxml.schemas.v21.message.CategorySchemeQueryType;
import org.sdmx.resources.sdmxml.schemas.v21.message.CodelistQueryType;
import org.sdmx.resources.sdmxml.schemas.v21.message.ConceptSchemeQueryType;
import org.sdmx.resources.sdmxml.schemas.v21.message.ConstraintQueryType;
import org.sdmx.resources.sdmxml.schemas.v21.message.DataStructureQueryType;
import org.sdmx.resources.sdmxml.schemas.v21.message.DataflowQueryType;
import org.sdmx.resources.sdmxml.schemas.v21.message.HierarchicalCodelistQueryType;
import org.sdmx.resources.sdmxml.schemas.v21.message.MetadataStructureQueryType;
import org.sdmx.resources.sdmxml.schemas.v21.message.MetadataflowQueryType;
import org.sdmx.resources.sdmxml.schemas.v21.message.OrganisationSchemeQueryType;
import org.sdmx.resources.sdmxml.schemas.v21.message.ProcessQueryType;
import org.sdmx.resources.sdmxml.schemas.v21.message.ProvisionAgreementQueryType;
import org.sdmx.resources.sdmxml.schemas.v21.message.ReportingTaxonomyQueryType;
import org.sdmx.resources.sdmxml.schemas.v21.message.StructureSetQueryType;
import org.sdmx.resources.sdmxml.schemas.v21.message.StructuresQueryType;
import org.sdmx.resources.sdmxml.schemas.v21.query.*;
import org.sdmxsource.sdmx.api.constants.*;
import org.sdmxsource.sdmx.api.model.base.SdmxDate;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.complex.*;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.SdmxDateImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.reference.complex.*;
import org.sdmxsource.sdmx.sdmxbeans.util.RefUtil;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;
import org.sdmxsource.util.StringUtil;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Query bean builder v 2 1.
 */
public class QueryBeanBuilderV2_1 {


    /**
     * Builds a list of provision references from a version 2.1 registry query registration request message
     *
     * @param queryRegistrationRequestType the query registration request type
     * @return provision references
     */
    public StructureReferenceBean build(org.sdmx.resources.sdmxml.schemas.v21.registry.QueryRegistrationRequestType queryRegistrationRequestType) {
        if (queryRegistrationRequestType != null) {
            if (queryRegistrationRequestType.getAll() != null) {
                return new StructureReferenceBeanImpl(SDMX_STRUCTURE_TYPE.PROVISION_AGREEMENT);
            }
            DataflowReferenceType dataflowRef = queryRegistrationRequestType.getDataflow();
            DataProviderReferenceType dataProviderRef = queryRegistrationRequestType.getDataProvider();
            ProvisionAgreementReferenceType provRef = queryRegistrationRequestType.getProvisionAgreement();
            MetadataflowReferenceType mdfRef = queryRegistrationRequestType.getMetadataflow();

            if (dataProviderRef != null) {
                return RefUtil.createReference(dataProviderRef);
            }
            if (provRef != null) {
                return RefUtil.createReference(provRef);
            }
            if (dataflowRef != null) {
                return RefUtil.createReference(dataflowRef);
            }
            if (mdfRef != null) {
                return RefUtil.createReference(mdfRef);
            }
        }
        return null;
    }


    /**
     * builds an identifiable reference from XML bean values.
     *
     * @param itemWhereType
     * @param itemType      the type of the item
     * @return null if the itemWhereType is null
     */
    private ComplexIdentifiableReferenceBean buildIdentifiableReference(ItemWhereType itemWhereType, SDMX_STRUCTURE_TYPE itemType) {
        if (itemWhereType == null) {
            return null;
        }

        QueryIDType idType = itemWhereType.getID();
        ComplexTextReference id = null;
        if (idType != null) {
            id = buildTextReference(null, idType.getOperator(), idType.getStringValue());
        }

        ComplexAnnotationReference annotationRef = buildAnnotationReference(itemWhereType.getAnnotation());

        QueryTextType nameType = itemWhereType.getName();
        ComplexTextReference nameRef = null;
        if (nameType != null) {
            nameRef = buildTextReference(nameType.getLang(), nameType.getOperator(), nameType.getStringValue());
        }

        QueryTextType descriptionType = itemWhereType.getDescription();
        ComplexTextReference descriptionRef = null;
        if (descriptionType != null) {
            descriptionRef = buildTextReference(descriptionType.getLang(), descriptionType.getOperator(), descriptionType.getStringValue());
        }

        ComplexIdentifiableReferenceBean childRef = null;
        List<ItemWhereType> itemWhereList = itemWhereType.getItemWhereList();
        if (itemWhereList != null && !itemWhereList.isEmpty()) { // this should be the case only for the Categories, ReportingTaxonomies.
            if (itemWhereList.size() > 1) {
                //TODO warning or error that is not supported?????
            }
            childRef = buildIdentifiableReference(itemWhereList.get(0), itemType);
        }

        return new ComplexIdentifiableReferenceBeanImpl(id, itemType, annotationRef, nameRef, descriptionRef, childRef);
    }

    /**
     * builds a complex structure reference from XML beans values.
     *
     * @param maintainableWhere
     * @param childRef          if items in the where clauses (for ItemSchemes) otherwise null
     * @return
     */
    private ComplexStructureReferenceBean buildMaintainableWhere(MaintainableWhereType maintainableWhere, ComplexIdentifiableReferenceBean childRef) {

        QueryNestedIDType agencyIDType = maintainableWhere.getAgencyID();
        ComplexTextReference agencyId = null;
        if (agencyIDType != null) {
            agencyId = buildTextReference(null, agencyIDType.getOperator(), agencyIDType.getStringValue());
        }

        QueryIDType queryIDType = maintainableWhere.getID();
        ComplexTextReference id = null;
        if (queryIDType != null) {
            id = buildTextReference(null, queryIDType.getOperator(), queryIDType.getStringValue());
        }

        ComplexVersionReference versionRef = buildVersionReference(maintainableWhere.getVersion(), maintainableWhere.getVersionFrom(), maintainableWhere.getVersionTo());

        SDMX_STRUCTURE_TYPE structureType;
        if (maintainableWhere.getType().toString().equals("OrganisationScheme")) { // hack. This checked are done since it is not identifiable no element name in enum and cannot use parseClass()
            structureType = SDMX_STRUCTURE_TYPE.ORGANISATION_SCHEME;
        } else if (maintainableWhere.getType().toString().equals("Constraint")) {
            structureType = SDMX_STRUCTURE_TYPE.CONSTRAINT;
        } else if (maintainableWhere.getType().toString().equals("Any")) {
            structureType = SDMX_STRUCTURE_TYPE.ANY;
        } else {
            structureType = SDMX_STRUCTURE_TYPE.parseClass(maintainableWhere.getType().toString());
        }

        ComplexAnnotationReference annotationRef = buildAnnotationReference(maintainableWhere.getAnnotation());

        QueryTextType nameType = maintainableWhere.getName();
        ComplexTextReference nameRef = null;
        if (nameType != null) {
            nameRef = buildTextReference(nameType.getLang(), nameType.getOperator(), nameType.getStringValue());
        }


        QueryTextType descriptionType = maintainableWhere.getDescription();
        ComplexTextReference descriptionRef = null;
        if (descriptionType != null) {
            descriptionRef = buildTextReference(descriptionType.getLang(), descriptionType.getOperator(), descriptionType.getStringValue());
        }


        return new ComplexStructureReferenceBeanImpl(agencyId, id, versionRef, structureType, annotationRef, nameRef, descriptionRef, childRef);
    }

    /**
     * Builds annotation reference from XML beans values.
     *
     * @param annotationWhereType
     * @return null if annotationWhereType is null
     */
    private ComplexAnnotationReference buildAnnotationReference(AnnotationWhereType annotationWhereType) {
        if (annotationWhereType == null) {
            return null;
        }

        QueryStringType type = annotationWhereType.getType();
        ComplexTextReference typeRef = null;
        if (type != null) {
            typeRef = buildTextReference(null, type.getOperator(), type.getStringValue());
        }

        QueryStringType title = annotationWhereType.getTitle();
        ComplexTextReference titleRef = null;
        if (title != null) {
            titleRef = buildTextReference(null, title.getOperator(), title.getStringValue());
        }

        QueryTextType text = annotationWhereType.getText();
        ComplexTextReference textRef = null;
        if (text != null) {
            textRef = buildTextReference(text.getLang(), text.getOperator(), text.getStringValue());
        }

        return new ComplexAnnotationReferenceImpl(typeRef, titleRef, textRef);
    }


    /**
     * builds the version reference from the XML beans values
     *
     * @param version
     * @param versionFromType
     * @param versionToType
     * @return
     */
    private ComplexVersionReference buildVersionReference(String version, TimeRangeValueType versionFromType, TimeRangeValueType versionToType) {

        TERTIARY_BOOL returnLatest = TERTIARY_BOOL.UNSET;
        String emptyCheckedString = null;
        if (StringUtil.hasText(version)) {
            if (version.equals("*")) {
                returnLatest = TERTIARY_BOOL.TRUE;
                emptyCheckedString = null;
            } else {
                emptyCheckedString = version;
                returnLatest = TERTIARY_BOOL.FALSE;
            }
        }

        TimeRange validFrom = buildTimeRange(versionFromType);
        TimeRange validTo = buildTimeRange(versionToType);

        return new ComplexVersionReferenceImpl(returnLatest, emptyCheckedString, validFrom, validTo);
    }

    /**
     * Builds a time range from time range value from XML
     *
     * @param timeRangeValueType
     * @return
     */
    private TimeRange buildTimeRange(TimeRangeValueType timeRangeValueType) {
        if (timeRangeValueType == null) {
            return null;
        }

        boolean range = false;
        SdmxDate startDate = null;
        SdmxDate endDate = null;
        boolean endInclusive = false;
        boolean startInclusive = false;

        if (timeRangeValueType.isSetAfterPeriod()) {
            TimePeriodRangeType afterPeriod = timeRangeValueType.getAfterPeriod();
            startDate = new SdmxDateImpl(afterPeriod.getStringValue());
            startInclusive = afterPeriod.getIsInclusive();
        } else if (timeRangeValueType.isSetBeforePeriod()) {
            TimePeriodRangeType beforePeriod = timeRangeValueType.getBeforePeriod();
            endDate = new SdmxDateImpl(beforePeriod.getStringValue());
            endInclusive = beforePeriod.getIsInclusive();
        } else { //case that range is set
            range = true;
            TimePeriodRangeType startPeriod = timeRangeValueType.getStartPeriod();
            startDate = new SdmxDateImpl(startPeriod.getStringValue());
            startInclusive = startPeriod.getIsInclusive();

            TimePeriodRangeType endPeriod = timeRangeValueType.getEndPeriod();
            endDate = new SdmxDateImpl(endPeriod.getStringValue());
            endInclusive = endPeriod.getIsInclusive();
        }


        return new TimeRangeImpl(range, startDate, endDate, startInclusive, endInclusive);
    }


    /**
     * build the text reference from values acquired from XML beans.
     * Check if empty or unset to check default values according to XSD.
     *
     * @param lang
     * @param operator
     * @param value
     * @return the complex text reference for the given values.
     */
    private ComplexTextReference buildTextReference(String lang, String operator, String value) {
        String emptyCheckedLang = "en";
        if (StringUtil.hasText(lang)) {
            emptyCheckedLang = lang;
        }

        TEXT_SEARCH defaultCheckedOperator = TEXT_SEARCH.EQUAL;
        if (StringUtil.hasText(operator)) {
            defaultCheckedOperator = TEXT_SEARCH.parseString(operator);
        }

        return new ComplexTextReferenceImpl(emptyCheckedLang, defaultCheckedOperator, value);
    }


    /**
     * Builds {@link ComplexStructureQueryMetadata} from the return detail part of the query message
     *
     * @param returnDetails
     * @return
     */
    private ComplexStructureQueryMetadata buildQueryDetails(StructureReturnDetailsType returnDetails) {

        boolean returnMatchedartefact = returnDetails.getReturnMatchedArtefact();

        COMPLEX_STRUCTURE_QUERY_DETAIL queryDetail = COMPLEX_STRUCTURE_QUERY_DETAIL.parseString(returnDetails.getDetail());

        ReferencesType references = returnDetails.getReferences();

        COMPLEX_MAINTAINABLE_QUERY_DETAIL referencesQueryDetail;
        if (references.isSetDetail()) {
            referencesQueryDetail = COMPLEX_MAINTAINABLE_QUERY_DETAIL.parseString(references.getDetail().toString());
        } else {
            referencesQueryDetail = COMPLEX_MAINTAINABLE_QUERY_DETAIL.FULL;
        }


        STRUCTURE_REFERENCE_DETAIL referenceDetail = null;
        List<SDMX_STRUCTURE_TYPE> referenceSpecificStructures = null;

        if (references.isSetAll()) {
            referenceDetail = STRUCTURE_REFERENCE_DETAIL.ALL;
        } else if (references.isSetChildren()) {
            referenceDetail = STRUCTURE_REFERENCE_DETAIL.CHILDREN;
        } else if (references.isSetDescendants()) {
            referenceDetail = STRUCTURE_REFERENCE_DETAIL.DESCENDANTS;
        } else if (references.isSetNone()) {
            referenceDetail = STRUCTURE_REFERENCE_DETAIL.NONE;
        } else if (references.isSetParents()) {
            referenceDetail = STRUCTURE_REFERENCE_DETAIL.PARENTS;
        } else if (references.isSetParentsAndSiblings()) {
            referenceDetail = STRUCTURE_REFERENCE_DETAIL.PARENTS_SIBLINGS;
        } else if (references.isSetSpecificObjects()) {
            referenceDetail = STRUCTURE_REFERENCE_DETAIL.SPECIFIC;

            referenceSpecificStructures = new ArrayList<SDMX_STRUCTURE_TYPE>();

            MaintainableObjectTypeListType specificObjects = references.getSpecificObjects();
            Node domNode = specificObjects.getDomNode();
            NodeList childNodes = domNode.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node item = childNodes.item(i);
                if (StringUtil.hasText(item.getLocalName())) {
                    referenceSpecificStructures.add(SDMX_STRUCTURE_TYPE.parseClass(item.getLocalName()));
                }
            }
        }

        return new ComplexStructureQueryMetadataImpl(returnMatchedartefact, queryDetail, referencesQueryDetail, referenceDetail, referenceSpecificStructures);
    }


    /**
     * Build complex structure query.
     *
     * @param dataflowQueryMsg the dataflow query msg
     * @return the complex structure query
     */
    public ComplexStructureQuery build(DataflowQueryType dataflowQueryMsg) {
        org.sdmx.resources.sdmxml.schemas.v21.query.DataflowQueryType query = dataflowQueryMsg.getQuery();

        // process details
        StructureReturnDetailsType returnDetails = query.getReturnDetails();
        ComplexStructureQueryMetadata queryMetadata = buildQueryDetails(returnDetails);

        // process common clauses for all maintainables along with specific from child references.
        ComplexStructureReferenceBean structureRef = buildMaintainableWhere(query.getStructuralMetadataWhere(), null);
        return new ComplexStructureQueryImpl(structureRef, queryMetadata);
    }

    /**
     * Build complex structure query.
     *
     * @param metadataflowQueryMsg the metadataflow query msg
     * @return the complex structure query
     */
    public ComplexStructureQuery build(MetadataflowQueryType metadataflowQueryMsg) {
        org.sdmx.resources.sdmxml.schemas.v21.query.MetadataflowQueryType query = metadataflowQueryMsg.getQuery();

        // process details
        StructureReturnDetailsType returnDetails = query.getReturnDetails();
        ComplexStructureQueryMetadata queryMetadata = buildQueryDetails(returnDetails);

        // process common clauses for all maintainables along with specific from child references.
        ComplexStructureReferenceBean structureRef = buildMaintainableWhere(query.getStructuralMetadataWhere(), null);
        return new ComplexStructureQueryImpl(structureRef, queryMetadata);
    }

    /**
     * Build complex structure query.
     *
     * @param dataStructureQueryMsg the data structure query msg
     * @return the complex structure query
     */
    public ComplexStructureQuery build(DataStructureQueryType dataStructureQueryMsg) {
        org.sdmx.resources.sdmxml.schemas.v21.query.DataStructureQueryType query = dataStructureQueryMsg.getQuery();

        // process details
        StructureReturnDetailsType returnDetails = query.getReturnDetails();
        ComplexStructureQueryMetadata queryMetadata = buildQueryDetails(returnDetails);

        // process common clauses for all maintainables along with specific from child references.
        ComplexStructureReferenceBean structureRef = buildMaintainableWhere(query.getStructuralMetadataWhere(), null);
        return new ComplexStructureQueryImpl(structureRef, queryMetadata);
    }

    /**
     * Build complex structure query.
     *
     * @param metadataStructureQueryMsg the metadata structure query msg
     * @return the complex structure query
     */
    public ComplexStructureQuery build(MetadataStructureQueryType metadataStructureQueryMsg) {
        org.sdmx.resources.sdmxml.schemas.v21.query.MetadataStructureQueryType query = metadataStructureQueryMsg.getQuery();

        // process details
        StructureReturnDetailsType returnDetails = query.getReturnDetails();
        ComplexStructureQueryMetadata queryMetadata = buildQueryDetails(returnDetails);

        // process common clauses for all maintainables along with specific from child references.
        ComplexStructureReferenceBean structureRef = buildMaintainableWhere(query.getStructuralMetadataWhere(), null);
        return new ComplexStructureQueryImpl(structureRef, queryMetadata);
    }

    /**
     * Build complex structure query.
     *
     * @param categorySchemeQueryMsg the category scheme query msg
     * @return the complex structure query
     */
    public ComplexStructureQuery build(CategorySchemeQueryType categorySchemeQueryMsg) {
        org.sdmx.resources.sdmxml.schemas.v21.query.CategorySchemeQueryType query = categorySchemeQueryMsg.getQuery();

        // process details
        StructureReturnDetailsType returnDetails = query.getReturnDetails();
        ComplexStructureQueryMetadata queryMetadata = buildQueryDetails(returnDetails);

        // process codelist specific clauses.
        CategorySchemeWhereType categorySchemeWhere = query.getCategorySchemeWhere();
        List<ItemWhereType> itemWhereList = categorySchemeWhere.getItemWhereList();
        ComplexIdentifiableReferenceBean childRef = null;
        if (itemWhereList != null && !itemWhereList.isEmpty()) {
            if (itemWhereList.size() > 1) {
                //TODO warning or error that is not supported?????
            }
            childRef = buildIdentifiableReference(itemWhereList.get(0), SDMX_STRUCTURE_TYPE.CATEGORY);
        }


        // process common clauses for all maintainables along with specific from child references.
        ComplexStructureReferenceBean structureRef = buildMaintainableWhere(query.getStructuralMetadataWhere(), childRef);
        return new ComplexStructureQueryImpl(structureRef, queryMetadata);
    }

    /**
     * Build complex structure query.
     *
     * @param conceptSchemeQueryMsg the concept scheme query msg
     * @return the complex structure query
     */
    public ComplexStructureQuery build(ConceptSchemeQueryType conceptSchemeQueryMsg) {
        org.sdmx.resources.sdmxml.schemas.v21.query.ConceptSchemeQueryType query = conceptSchemeQueryMsg.getQuery();

        // process details
        StructureReturnDetailsType returnDetails = query.getReturnDetails();
        ComplexStructureQueryMetadata queryMetadata = buildQueryDetails(returnDetails);

        // process codelist specific clauses.
        ConceptSchemeWhereType conceptSchemeWhere = query.getConceptSchemeWhere();
        List<ItemWhereType> itemWhereList = conceptSchemeWhere.getItemWhereList();
        ComplexIdentifiableReferenceBean childRef = null;
        if (itemWhereList != null && !itemWhereList.isEmpty()) {
            if (itemWhereList.size() > 1) {
                //TODO warning or error that is not supported?????
            }
            childRef = buildIdentifiableReference(itemWhereList.get(0), SDMX_STRUCTURE_TYPE.CONCEPT);
        }


        // process common clauses for all maintainables along with specific from child references.
        ComplexStructureReferenceBean structureRef = buildMaintainableWhere(query.getStructuralMetadataWhere(), childRef);
        return new ComplexStructureQueryImpl(structureRef, queryMetadata);
    }

    /**
     * Builds a {@link ComplexStructureQuery} from a Codelist query 2.1 message
     *
     * @param codelistQueryMsg the codelist query msg
     * @return complex structure query
     */
    public ComplexStructureQuery build(CodelistQueryType codelistQueryMsg) {
        org.sdmx.resources.sdmxml.schemas.v21.query.CodelistQueryType query = codelistQueryMsg.getQuery();

        // process details
        StructureReturnDetailsType returnDetails = query.getReturnDetails();
        ComplexStructureQueryMetadata queryMetadata = buildQueryDetails(returnDetails);

        // process codelist specific clauses.
        CodelistWhereType codelistWhere = query.getCodelistWhere();
        List<ItemWhereType> itemWhereList = codelistWhere.getItemWhereList();
        ComplexIdentifiableReferenceBean childRef = null;
        if (itemWhereList != null && !itemWhereList.isEmpty()) {
            if (itemWhereList.size() > 1) {
                //TODO warning or error that is not supported?????
            }
            childRef = buildIdentifiableReference(itemWhereList.get(0), SDMX_STRUCTURE_TYPE.CODE);
        }

        // process common clauses for all maintainables along with specific from child references.
        ComplexStructureReferenceBean structureRef = buildMaintainableWhere(query.getStructuralMetadataWhere(), childRef);
        return new ComplexStructureQueryImpl(structureRef, queryMetadata);
    }


    /**
     * Build complex structure query.
     *
     * @param hierarchicalCodelistQueryMsg the hierarchical codelist query msg
     * @return the complex structure query
     */
    public ComplexStructureQuery build(HierarchicalCodelistQueryType hierarchicalCodelistQueryMsg) {
        org.sdmx.resources.sdmxml.schemas.v21.query.HierarchicalCodelistQueryType query = hierarchicalCodelistQueryMsg.getQuery();

        // process details
        StructureReturnDetailsType returnDetails = query.getReturnDetails();
        ComplexStructureQueryMetadata queryMetadata = buildQueryDetails(returnDetails);

        // process common clauses for all maintainables along with specific from child references.
        ComplexStructureReferenceBean structureRef = buildMaintainableWhere(query.getStructuralMetadataWhere(), null);
        return new ComplexStructureQueryImpl(structureRef, queryMetadata);
    }


    /**
     * Build complex structure query.
     *
     * @param organisationSchemeQueryMsg the organisation scheme query msg
     * @return the complex structure query
     */
    public ComplexStructureQuery build(OrganisationSchemeQueryType organisationSchemeQueryMsg) {
        org.sdmx.resources.sdmxml.schemas.v21.query.OrganisationSchemeQueryType query = organisationSchemeQueryMsg.getQuery();

        // process details
        StructureReturnDetailsType returnDetails = query.getReturnDetails();
        ComplexStructureQueryMetadata queryMetadata = buildQueryDetails(returnDetails);

        // process OrganisationScheme specific clauses.
        OrganisationSchemeWhereType organisationSchemeWhere = query.getOrganisationSchemeWhere();
        String orgSchemeStr = organisationSchemeWhere.getType().toString();
        SDMX_STRUCTURE_TYPE orgSchemeType;
        if (orgSchemeStr.equals("OrganisationScheme")) {
            orgSchemeType = SDMX_STRUCTURE_TYPE.ORGANISATION_SCHEME;
        } else {
            orgSchemeType = SDMX_STRUCTURE_TYPE.parseClass(orgSchemeStr);
        }

        SDMX_STRUCTURE_TYPE orgType;
        switch (orgSchemeType) {
            case AGENCY_SCHEME:
                orgType = SDMX_STRUCTURE_TYPE.AGENCY;
                break;
            case DATA_CONSUMER_SCHEME:
                orgType = SDMX_STRUCTURE_TYPE.DATA_CONSUMER;
                break;
            case DATA_PROVIDER_SCHEME:
                orgType = SDMX_STRUCTURE_TYPE.DATA_PROVIDER;
                break;
            case ORGANISATION_UNIT_SCHEME:
                orgType = SDMX_STRUCTURE_TYPE.ORGANISATION_UNIT;
                break;
            case ORGANISATION_SCHEME:
                orgType = SDMX_STRUCTURE_TYPE.ORGANISATION;
                break;
            default:
                throw new IllegalArgumentException("An organisation scheme type expected instead of: " + orgSchemeType);
        }

        List<ItemWhereType> itemWhereList = organisationSchemeWhere.getItemWhereList();
        ComplexIdentifiableReferenceBean childRef = null;
        if (itemWhereList != null && !itemWhereList.isEmpty()) {
            if (itemWhereList.size() > 1) {
                //TODO warning or error that is not supported?????
            }
            childRef = buildIdentifiableReference(itemWhereList.get(0), orgType);
        }

        // process common clauses for all maintainables along with specific from child references.
        ComplexStructureReferenceBean structureRef = buildMaintainableWhere(query.getStructuralMetadataWhere(), childRef);
        return new ComplexStructureQueryImpl(structureRef, queryMetadata);
    }


    /**
     * Build complex structure query.
     *
     * @param reportingTaxonomyQueryMsg the reporting taxonomy query msg
     * @return the complex structure query
     */
    public ComplexStructureQuery build(ReportingTaxonomyQueryType reportingTaxonomyQueryMsg) {
        org.sdmx.resources.sdmxml.schemas.v21.query.ReportingTaxonomyQueryType query = reportingTaxonomyQueryMsg.getQuery();

        // process details
        StructureReturnDetailsType returnDetails = query.getReturnDetails();
        ComplexStructureQueryMetadata queryMetadata = buildQueryDetails(returnDetails);

        // process codelist specific clauses.
        ReportingTaxonomyWhereType reportingTaxonomyWhere = query.getReportingTaxonomyWhere();
        List<ItemWhereType> itemWhereList = reportingTaxonomyWhere.getItemWhereList();
        ComplexIdentifiableReferenceBean childRef = null;
        if (itemWhereList != null && !itemWhereList.isEmpty()) {
            if (itemWhereList.size() > 1) {
                //TODO warning or error that is not supported?????
            }
            childRef = buildIdentifiableReference(itemWhereList.get(0), SDMX_STRUCTURE_TYPE.REPORTING_CATEGORY);
        }

        // process common clauses for all maintainables along with specific from child references.
        ComplexStructureReferenceBean structureRef = buildMaintainableWhere(query.getStructuralMetadataWhere(), childRef);
        return new ComplexStructureQueryImpl(structureRef, queryMetadata);
    }


    /**
     * Build complex structure query.
     *
     * @param structureSetQueryMsg the structure set query msg
     * @return the complex structure query
     */
    public ComplexStructureQuery build(StructureSetQueryType structureSetQueryMsg) {
        org.sdmx.resources.sdmxml.schemas.v21.query.StructureSetQueryType query = structureSetQueryMsg.getQuery();

        // process details
        StructureReturnDetailsType returnDetails = query.getReturnDetails();
        ComplexStructureQueryMetadata queryMetadata = buildQueryDetails(returnDetails);

        // process common clauses for all maintainables along with specific from child references.
        ComplexStructureReferenceBean structureRef = buildMaintainableWhere(query.getStructuralMetadataWhere(), null);
        return new ComplexStructureQueryImpl(structureRef, queryMetadata);
    }


    /**
     * Build complex structure query.
     *
     * @param processQueryMsg the process query msg
     * @return the complex structure query
     */
    public ComplexStructureQuery build(ProcessQueryType processQueryMsg) {
        org.sdmx.resources.sdmxml.schemas.v21.query.ProcessQueryType query = processQueryMsg.getQuery();

        // process details
        StructureReturnDetailsType returnDetails = query.getReturnDetails();
        ComplexStructureQueryMetadata queryMetadata = buildQueryDetails(returnDetails);

        // process common clauses for all maintainables along with specific from child references.
        ComplexStructureReferenceBean structureRef = buildMaintainableWhere(query.getStructuralMetadataWhere(), null);
        return new ComplexStructureQueryImpl(structureRef, queryMetadata);
    }


    /**
     * Build complex structure query.
     *
     * @param categorisationQueryMsg the categorisation query msg
     * @return the complex structure query
     */
    public ComplexStructureQuery build(CategorisationQueryType categorisationQueryMsg) {
        org.sdmx.resources.sdmxml.schemas.v21.query.CategorisationQueryType query = categorisationQueryMsg.getQuery();

        // process details
        StructureReturnDetailsType returnDetails = query.getReturnDetails();
        ComplexStructureQueryMetadata queryMetadata = buildQueryDetails(returnDetails);

        // process common clauses for all maintainables along with specific from child references.
        ComplexStructureReferenceBean structureRef = buildMaintainableWhere(query.getStructuralMetadataWhere(), null);
        return new ComplexStructureQueryImpl(structureRef, queryMetadata);
    }


    /**
     * Build complex structure query.
     *
     * @param provisionAgreementQueryMsg the provision agreement query msg
     * @return the complex structure query
     */
    public ComplexStructureQuery build(ProvisionAgreementQueryType provisionAgreementQueryMsg) {
        org.sdmx.resources.sdmxml.schemas.v21.query.ProvisionAgreementQueryType query = provisionAgreementQueryMsg.getQuery();

        // process details
        StructureReturnDetailsType returnDetails = query.getReturnDetails();
        ComplexStructureQueryMetadata queryMetadata = buildQueryDetails(returnDetails);

        // process common clauses for all maintainables along with specific from child references.
        ComplexStructureReferenceBean structureRef = buildMaintainableWhere(query.getStructuralMetadataWhere(), null);
        return new ComplexStructureQueryImpl(structureRef, queryMetadata);
    }


    /**
     * Build complex structure query.
     *
     * @param constraintQueryMsg the constraint query msg
     * @return the complex structure query
     */
    public ComplexStructureQuery build(ConstraintQueryType constraintQueryMsg) {
        org.sdmx.resources.sdmxml.schemas.v21.query.ConstraintQueryType query = constraintQueryMsg.getQuery();

        // process details
        StructureReturnDetailsType returnDetails = query.getReturnDetails();
        ComplexStructureQueryMetadata queryMetadata = buildQueryDetails(returnDetails);

        // process common clauses for all maintainables along with specific from child references.
        ComplexStructureReferenceBean structureRef = buildMaintainableWhere(query.getStructuralMetadataWhere(), null);
        return new ComplexStructureQueryImpl(structureRef, queryMetadata);
    }


    /**
     * Build complex structure query.
     *
     * @param structuresQueryMsg the structures query msg
     * @return the complex structure query
     */
    public ComplexStructureQuery build(StructuresQueryType structuresQueryMsg) {
        org.sdmx.resources.sdmxml.schemas.v21.query.StructuresQueryType query = structuresQueryMsg.getQuery();

        // process details
        StructureReturnDetailsType returnDetails = query.getReturnDetails();
        ComplexStructureQueryMetadata queryMetadata = buildQueryDetails(returnDetails);

        // process common clauses for all maintainables along with specific from child references.
        ComplexStructureReferenceBean structureRef = buildMaintainableWhere(query.getStructuralMetadataWhere(), null);
        return new ComplexStructureQueryImpl(structureRef, queryMetadata);
    }


}
