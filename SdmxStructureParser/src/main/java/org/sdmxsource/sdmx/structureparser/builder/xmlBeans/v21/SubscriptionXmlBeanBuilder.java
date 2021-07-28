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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21;

import org.apache.xmlbeans.XmlCursor;
import org.sdmx.resources.sdmxml.schemas.v21.common.*;
import org.sdmx.resources.sdmxml.schemas.v21.registry.*;
import org.sdmxsource.sdmx.api.builder.Builder;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.SubscriptionBean;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers.NameableBeanAssembler;
import org.sdmxsource.util.ObjectUtil;

import javax.xml.namespace.QName;
import java.util.List;


/**
 * The type Subscription xml bean builder.
 */
public class SubscriptionXmlBeanBuilder extends NameableBeanAssembler implements Builder<SubscriptionType, SubscriptionBean> {

    @Override
    public SubscriptionType build(SubscriptionBean buildFrom) throws SdmxException {
        SubscriptionType subscriptionType = SubscriptionType.Factory.newInstance();

        //1. Set organisation reference
        OrganisationReferenceType orfRef = subscriptionType.addNewOrganisation();
        RefBaseType ref = orfRef.addNewRef();

        super.setReference(ref, buildFrom.getOwner());

        //I HATE SUBSTITUTION GROUPS - CLEARLY THIS IS NOT THE WAY TO DO IT - BUT THIS IS THE ONLY WAY I CAN GET THIS BLOODY
        //PIECE OF CODE TO GENERATE A VALID REFERENCE - SUBSTITUTION GROUPS, WELCOME TO A WORLD OF PAIN
        XmlCursor cursor = ref.newCursor();
        cursor.insertNamespace("com", AgencyRefType.type.getName().getNamespaceURI());
        switch (buildFrom.getOwner().getTargetReference()) {
            case AGENCY:
                cursor.setAttributeText(new QName("http://www.w3.org/2001/XMLSchema-instance", "type"), "com:" + AgencyRefType.type.getName().getLocalPart());
                break;
            case DATA_PROVIDER:
                cursor.setAttributeText(new QName("http://www.w3.org/2001/XMLSchema-instance", "type"), "com:" + DataProviderRefType.type.getName().getLocalPart());
                break;
            case DATA_CONSUMER:
                cursor.setAttributeText(new QName("http://www.w3.org/2001/XMLSchema-instance", "type"), "com:" + DataConsumerRefType.type.getName().getLocalPart());
                break;
            case ORGANISATION_UNIT:
                cursor.setAttributeText(new QName("http://www.w3.org/2001/XMLSchema-instance", "type"), "com:" + OrganisationUnitRefType.type.getName().getLocalPart());
                break;
        }

        //2. Set notification HTTP and Email
        for (String currentHttp : buildFrom.getHTTPPostTo()) {
            NotificationURLType notificationUrl = subscriptionType.addNewNotificationHTTP();
            notificationUrl.setStringValue(currentHttp);
        }
        for (String currentHttp : buildFrom.getMailTo()) {
            NotificationURLType notificationUrl = subscriptionType.addNewNotificationMailTo();
            notificationUrl.setStringValue(currentHttp);
        }

        //3. Set it
        subscriptionType.setSubscriberAssignedID(buildFrom.getId());

        //4. Set Validity Period
        ValidityPeriodType validityPeriod = subscriptionType.addNewValidityPeriod();
        if (buildFrom.getStartDate() != null) {
            validityPeriod.setStartDate(buildFrom.getStartDate().getDateAsCalendar());
        }
        if (buildFrom.getEndDate() != null) {
            validityPeriod.setEndDate(buildFrom.getEndDate().getDateAsCalendar());
        }

        //5. Build Subscription Events
        EventSelectorType eventSelector = subscriptionType.addNewEventSelector();
        List<StructureReferenceBean> structureReferences = buildFrom.getReferences();
        switch (buildFrom.getSubscriptionType()) {
            case DATA_REGISTRATION:
                buildDataSubscription(eventSelector, structureReferences);
                break;
            case METADATA_REGISTRATION:
                buildMetadataSubscription(eventSelector, structureReferences);
                break;
            case STRUCTURE:
                buildStructureSubscription(eventSelector, structureReferences);
                break;
        }
        return subscriptionType;
    }

    private void buildStructureSubscription(EventSelectorType eventSelector, List<StructureReferenceBean> subscriptions) {
        StructuralRepositoryEventsType structureEvents = eventSelector.addNewStructuralRepositoryEvents();

        boolean addedAgency = false;
        for (StructureReferenceBean sRef : subscriptions) {
            MaintainableRefBean mRef = sRef.getMaintainableReference();
            if (ObjectUtil.validString(mRef.getAgencyId())) {
                structureEvents.addAgencyID(mRef.getAgencyId());
                addedAgency = true;
            }
        }
        if (!addedAgency) {
            structureEvents.addAgencyID(SubscriptionBean.WILDCARD);
        }
        for (StructureReferenceBean sRef : subscriptions) {
            MaintainableRefBean mRef = sRef.getMaintainableReference();
            switch (sRef.getTargetReference()) {
                case ANY:
                    structureEvents.addNewAllEvents();
                    break;
                case AGENCY_SCHEME:
                    setVersionObjectEventInfo(structureEvents.addNewAgencyScheme(), mRef);
                    break;
                case ATTACHMENT_CONSTRAINT:
                    setVersionObjectEventInfo(structureEvents.addNewAttachmentConstraint(), mRef);
                    break;
                case CATEGORY_SCHEME:
                    setVersionObjectEventInfo(structureEvents.addNewCategoryScheme(), mRef);
                    break;
                case CODE_LIST:
                    setVersionObjectEventInfo(structureEvents.addNewCodelist(), mRef);
                    break;
                case CONCEPT_SCHEME:
                    setVersionObjectEventInfo(structureEvents.addNewConceptScheme(), mRef);
                    break;
                case CONTENT_CONSTRAINT:
                    setVersionObjectEventInfo(structureEvents.addNewContentConstraint(), mRef);
                    break;
                case DATA_CONSUMER_SCHEME:
                    setVersionObjectEventInfo(structureEvents.addNewDataConsmerScheme(), mRef);
                    break;
                case DATAFLOW:
                    setVersionObjectEventInfo(structureEvents.addNewDataflow(), mRef);
                    break;
                case DATA_PROVIDER_SCHEME:
                    setVersionObjectEventInfo(structureEvents.addNewDataProviderScheme(), mRef);
                    break;
                case HIERARCHICAL_CODELIST:
                    setVersionObjectEventInfo(structureEvents.addNewHierarchicalCodelist(), mRef);
                    break;
                case DSD:
                    setVersionObjectEventInfo(structureEvents.addNewKeyFamily(), mRef);
                    break;
                case METADATA_FLOW:
                    setVersionObjectEventInfo(structureEvents.addNewMetadataflow(), mRef);
                    break;
                case MSD:
                    setVersionObjectEventInfo(structureEvents.addNewMetadataStructureDefinition(), mRef);
                    break;
                case ORGANISATION_UNIT_SCHEME:
                    setVersionObjectEventInfo(structureEvents.addNewOrganisationUnitScheme(), mRef);
                    break;
                case PROCESS:
                    setVersionObjectEventInfo(structureEvents.addNewProcess(), mRef);
                    break;
                case PROVISION_AGREEMENT:
                    setVersionObjectEventInfo(structureEvents.addNewProvisionAgreement(), mRef);
                    break;
                case REPORTING_TAXONOMY:
                    setVersionObjectEventInfo(structureEvents.addNewReportingTaxonomy(), mRef);
                    break;
                case STRUCTURE_SET:
                    setVersionObjectEventInfo(structureEvents.addNewStructureSet(), mRef);
                    break;
                case CATEGORISATION:
                    setIdentifiableObjectEventInfo(structureEvents.addNewCategorisation(), mRef);
                    break;
            }
        }
    }

    private void setVersionObjectEventInfo(VersionableObjectEventType vob, MaintainableRefBean ref) {
        if (ObjectUtil.validOneString(ref.getMaintainableId(), ref.getVersion())) {
            if (ObjectUtil.validString(ref.getMaintainableId())) {
                vob.setID(ref.getMaintainableId());
            } else {
                vob.setID(SubscriptionBean.WILDCARD);
            }
            if (ObjectUtil.validString(ref.getVersion())) {
                vob.setVersion(ref.getVersion());
            } else {
                vob.setVersion(SubscriptionBean.WILDCARD);
            }
        } else {
            vob.addNewAll();
        }
    }

    private void setIdentifiableObjectEventInfo(IdentifiableObjectEventType vob, MaintainableRefBean ref) {
        if (ObjectUtil.validString(ref.getMaintainableId())) {
            vob.setID(ref.getMaintainableId());
        } else {
            vob.addNewAll();
        }
    }

    private void buildDataSubscription(EventSelectorType eventSelector, List<StructureReferenceBean> subscriptions) {
        DataRegistrationEventsType dataRegistrationEventsType = eventSelector.addNewDataRegistrationEvents();
        for (StructureReferenceBean sRef : subscriptions) {
            MaintainableRefBean mRef = sRef.getMaintainableReference();
            switch (sRef.getTargetReference()) {
                case ANY:
                    dataRegistrationEventsType.addNewAllEvents();
                    break;
                case CATEGORY:
                    CategoryReferenceType catRef = dataRegistrationEventsType.addNewCategory();
                    super.setReference(catRef.addNewRef(), sRef);
                    break;
                case PROVISION_AGREEMENT:
                    ProvisionAgreementReferenceType provisionRef = dataRegistrationEventsType.addNewProvisionAgreement();
                    super.setReference(provisionRef.addNewRef(), sRef);
                    break;
                case DATA_PROVIDER:
                    DataProviderReferenceType providerRef = dataRegistrationEventsType.addNewDataProvider();
                    super.setReference(providerRef.addNewRef(), sRef);
                case DATAFLOW:
                    setMaintainableEventInfo(dataRegistrationEventsType.addNewDataflowReference(), mRef);
                case DSD:
                    setMaintainableEventInfo(dataRegistrationEventsType.addNewKeyFamilyReference(), mRef);
                    break;
            }
        }
    }

    private void setMaintainableEventInfo(MaintainableEventType maintEventType, MaintainableRefBean ref) {
        MaintainableQueryType maintQueryType = maintEventType.addNewRef();
        if (ObjectUtil.validString(ref.getAgencyId())) {
            maintQueryType.setAgencyID(ref.getAgencyId());
        }
        if (ObjectUtil.validString(ref.getMaintainableId())) {
            maintQueryType.setId(ref.getMaintainableId());
        }
        if (ObjectUtil.validString(ref.getVersion())) {
            maintQueryType.setVersion(ref.getVersion());
        }
    }

    private void buildMetadataSubscription(EventSelectorType eventSelector, List<StructureReferenceBean> subscriptions) {
        MetadataRegistrationEventsType metadatadataRegistrationEventsType = eventSelector.addNewMetadataRegistrationEvents();
        for (StructureReferenceBean sRef : subscriptions) {
            MaintainableRefBean mRef = sRef.getMaintainableReference();
            switch (sRef.getTargetReference()) {
                case ANY:
                    metadatadataRegistrationEventsType.addNewAllEvents();
                    break;
                case CATEGORY:
                    CategoryReferenceType catRef = metadatadataRegistrationEventsType.addNewCategory();
                    super.setReference(catRef.addNewRef(), sRef);
                    break;
                case PROVISION_AGREEMENT:
                    ProvisionAgreementReferenceType provisionRef = metadatadataRegistrationEventsType.addNewProvisionAgreement();
                    super.setReference(provisionRef.addNewRef(), sRef);
                    break;
                case DATA_PROVIDER:
                    DataProviderReferenceType providerRef = metadatadataRegistrationEventsType.addNewDataProvider();
                    super.setReference(providerRef.addNewRef(), sRef);
                case METADATA_FLOW:
                    setMaintainableEventInfo(metadatadataRegistrationEventsType.addNewMetadataflowReference(), mRef);
                case MSD:
                    setMaintainableEventInfo(metadatadataRegistrationEventsType.addNewMetadataStructureDefinitionReference(), mRef);
                    break;
            }
        }
    }
}
