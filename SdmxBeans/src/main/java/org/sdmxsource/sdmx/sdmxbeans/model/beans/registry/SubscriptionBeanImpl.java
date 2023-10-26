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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.registry;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.sdmx.resources.sdmxml.schemas.v21.common.ReferenceType;
import org.sdmx.resources.sdmxml.schemas.v21.registry.*;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TERTIARY_BOOL;
import org.sdmxsource.sdmx.api.constants.TIME_FORMAT;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.AgencySchemeBean;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.SubscriptionBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.SubscriptionMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.MaintainableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.SdmxDateImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.registry.SubscriptionMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.util.RefUtil;
import org.sdmxsource.sdmx.util.beans.reference.CrossReferenceBeanImpl;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;
import org.sdmxsource.util.ObjectUtil;
import org.sdmxsource.util.email.EmailValidation;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The type Subscription bean.
 */
public class SubscriptionBeanImpl extends MaintainableBeanImpl implements SubscriptionBean {
    private static final long serialVersionUID = 6358405744989772694L;
    private static Logger LOG = LoggerFactory.getLogger(SubscriptionBeanImpl.class);
    private CrossReferenceBean owner;
    private List<String> mailTo = new ArrayList<String>();
    private List<String> HTTPPostTo = new ArrayList<String>();
    private List<StructureReferenceBean> references = new ArrayList<StructureReferenceBean>();
    private SUBSCRIPTION_TYPE subscriptionType;

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM ITSELF, CREATES STUB BEAN //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private SubscriptionBeanImpl(SubscriptionBean bean, URL actualLocation, boolean isServiceUrl) {
        super(bean, actualLocation, isServiceUrl);
    }

    /**
     * Instantiates a new Subscription bean.
     *
     * @param mutableBean the mutable bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public SubscriptionBeanImpl(SubscriptionMutableBean mutableBean) {
        super(mutableBean);
        LOG.debug("Building Subscription from Mutable Bean");
        if (mutableBean.getOwner() != null) {
            this.owner = new CrossReferenceBeanImpl(this, mutableBean.getOwner());
        }
        if (mutableBean.getMailTo() != null) {
            this.mailTo = new ArrayList<String>(mutableBean.getMailTo());
        }
        if (mutableBean.getHTTPPostTo() != null) {
            this.HTTPPostTo = new ArrayList<String>(mutableBean.getHTTPPostTo());
        }
        if (mutableBean.getReferences() != null) {
            for (StructureReferenceBean ref : mutableBean.getReferences()) {
                references.add(ref.createCopy());
            }
        }

        this.subscriptionType = mutableBean.getSubscriptionType();
        validate();
        if (LOG.isDebugEnabled()) {
            LOG.debug("subscription Built " + this.getUrn());
        }
    }

    /**
     * Instantiates a new Subscription bean.
     *
     * @param subscription the subscription
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public SubscriptionBeanImpl(SubscriptionType subscription) {
        super(subscription, SDMX_STRUCTURE_TYPE.SUBSCRIPTION,
                null,
                null,
                DEFAULT_VERSION,
                TERTIARY_BOOL.UNSET,
                getSubAgencyId(subscription),
                getSubId(subscription), null, null, null, null, null);
        LOG.debug("Building Subscription from 2.1 SDMX");
        if (subscription.getOrganisation() != null) {
            this.owner = RefUtil.createReference(this, subscription.getOrganisation());
        }
        if (subscription.getValidityPeriod() != null) {
            if (subscription.getValidityPeriod().getStartDate() != null) {
                this.startDate = new SdmxDateImpl(subscription.getValidityPeriod().getStartDate().getTime(), TIME_FORMAT.DATE_TIME);
            }
            if (subscription.getValidityPeriod().getEndDate() != null) {
                this.endDate = new SdmxDateImpl(subscription.getValidityPeriod().getEndDate().getTime(), TIME_FORMAT.DATE_TIME);
            }
        }
        if (subscription.getNotificationHTTPList() != null) {
            for (NotificationURLType not : subscription.getNotificationHTTPList()) {
                this.HTTPPostTo.add(not.getStringValue());
            }
        }
        if (subscription.getNotificationMailToList() != null) {
            for (NotificationURLType not : subscription.getNotificationMailToList()) {
                this.mailTo.add(not.getStringValue());
            }
        }
        if (subscription.getEventSelector() != null) {
            EventSelectorType eventSelector = subscription.getEventSelector();
            for (StructuralRepositoryEventsType repositoryEvent : eventSelector.getStructuralRepositoryEventsList()) {
                setSubscriptionType(SUBSCRIPTION_TYPE.STRUCTURE);

                List<String> agencies = repositoryEvent.getAgencyIDList();
                for (String currentAgency : agencies) {
                    //If any of the agencies is a wildcard, then make the list null as it is not important
                    if (currentAgency.equals(WILDCARD)) {
                        agencies = null;
                        break;
                    }
                }
                if (repositoryEvent.getAllEvents() != null) {
                    if (!ObjectUtil.validCollection(agencies)) {
                        references.add(new StructureReferenceBeanImpl(null, null, null, SDMX_STRUCTURE_TYPE.ANY));
                    } else {
                        for (String currentAgency : agencies) {
                            references.add(new StructureReferenceBeanImpl(currentAgency, null, null, SDMX_STRUCTURE_TYPE.ANY));
                        }
                    }
                    //No need to process any more information, this is a subscription for everything
                    break;
                }
                addEventSubscriptions(repositoryEvent.getAgencySchemeList(), agencies, SDMX_STRUCTURE_TYPE.AGENCY_SCHEME);
                addEventSubscriptions(repositoryEvent.getAttachmentConstraintList(), agencies, SDMX_STRUCTURE_TYPE.ATTACHMENT_CONSTRAINT);
                //addEventSubscriptions(repositoryEvent.getCategorisationList(), agencies, SDMX_STRUCTURE_TYPE.C);
                addEventSubscriptions(repositoryEvent.getCategorySchemeList(), agencies, SDMX_STRUCTURE_TYPE.CATEGORY_SCHEME);
                addEventSubscriptions(repositoryEvent.getCodelistList(), agencies, SDMX_STRUCTURE_TYPE.CODE_LIST);
                addEventSubscriptions(repositoryEvent.getConceptSchemeList(), agencies, SDMX_STRUCTURE_TYPE.CONCEPT_SCHEME);
                addEventSubscriptions(repositoryEvent.getContentConstraintList(), agencies, SDMX_STRUCTURE_TYPE.CONTENT_CONSTRAINT);
                addEventSubscriptions(repositoryEvent.getDataProviderSchemeList(), agencies, SDMX_STRUCTURE_TYPE.DATA_PROVIDER_SCHEME);
                addEventSubscriptions(repositoryEvent.getDataConsmerSchemeList(), agencies, SDMX_STRUCTURE_TYPE.DATA_CONSUMER_SCHEME);
                addEventSubscriptions(repositoryEvent.getDataflowList(), agencies, SDMX_STRUCTURE_TYPE.DATAFLOW);
                addEventSubscriptions(repositoryEvent.getHierarchicalCodelistList(), agencies, SDMX_STRUCTURE_TYPE.HIERARCHICAL_CODELIST);
                addEventSubscriptions(repositoryEvent.getKeyFamilyList(), agencies, SDMX_STRUCTURE_TYPE.DSD);
                addEventSubscriptions(repositoryEvent.getMetadataflowList(), agencies, SDMX_STRUCTURE_TYPE.METADATA_FLOW);
                addEventSubscriptions(repositoryEvent.getMetadataStructureDefinitionList(), agencies, SDMX_STRUCTURE_TYPE.MSD);
                addEventSubscriptions(repositoryEvent.getOrganisationUnitSchemeList(), agencies, SDMX_STRUCTURE_TYPE.ORGANISATION_UNIT_SCHEME);
                addEventSubscriptions(repositoryEvent.getProcessList(), agencies, SDMX_STRUCTURE_TYPE.PROCESS);
                addEventSubscriptions(repositoryEvent.getProvisionAgreementList(), agencies, SDMX_STRUCTURE_TYPE.PROVISION_AGREEMENT);
                addEventSubscriptions(repositoryEvent.getReportingTaxonomyList(), agencies, SDMX_STRUCTURE_TYPE.REPORTING_TAXONOMY);
                addEventSubscriptions(repositoryEvent.getStructureSetList(), agencies, SDMX_STRUCTURE_TYPE.STRUCTURE_SET);
            }
            for (DataRegistrationEventsType repositoryEvent : eventSelector.getDataRegistrationEventsList()) {
                setSubscriptionType(SUBSCRIPTION_TYPE.DATA_REGISTRATION);
                if (repositoryEvent.getAllEvents() != null) {
                    references.add(new StructureReferenceBeanImpl(SDMX_STRUCTURE_TYPE.ANY));
                    //No need to process any more information, this is a subscription for everything
                    break;
                }
                addEventSubscriptions(repositoryEvent.getDataflowReferenceList(), SDMX_STRUCTURE_TYPE.DATAFLOW);
                addEventSubscriptions(repositoryEvent.getKeyFamilyReferenceList(), SDMX_STRUCTURE_TYPE.DSD);
                addEventSubscriptions(repositoryEvent.getProvisionAgreementList());
                addEventSubscriptions(repositoryEvent.getCategoryList());
                addEventSubscriptions(repositoryEvent.getDataProviderList());
            }
            for (MetadataRegistrationEventsType repositoryEvent : eventSelector.getMetadataRegistrationEventsList()) {
                setSubscriptionType(SUBSCRIPTION_TYPE.METADATA_REGISTRATION);
                if (repositoryEvent.getAllEvents() != null) {
                    references.add(new StructureReferenceBeanImpl(SDMX_STRUCTURE_TYPE.ANY));
                    //No need to process any more information, this is a subscription for everything
                    break;
                }
                addEventSubscriptions(repositoryEvent.getMetadataflowReferenceList(), SDMX_STRUCTURE_TYPE.METADATA_FLOW);
                addEventSubscriptions(repositoryEvent.getMetadataStructureDefinitionReferenceList(), SDMX_STRUCTURE_TYPE.MSD);
                addEventSubscriptions(repositoryEvent.getProvisionAgreementList());
                addEventSubscriptions(repositoryEvent.getCategoryList());
                addEventSubscriptions(repositoryEvent.getDataProviderList());
            }
        }
        validate();
        if (LOG.isDebugEnabled()) {
            LOG.debug("subscription Built " + this.getUrn());
        }
    }

    private static String getSubAgencyId(SubscriptionType subscription) {
        return RefUtil.createReference(subscription.getOrganisation()).getMaintainableReference().getAgencyId();
    }

    private static String getSubId(SubscriptionType subscription) {
        if (ObjectUtil.validString(subscription.getSubscriberAssignedID())) {
            return subscription.getSubscriberAssignedID();
        }
        return "Generated_" + System.currentTimeMillis();
    }

    private void addEventSubscriptions(List<VersionableObjectEventType> events, List<String> agencyIds, SDMX_STRUCTURE_TYPE structureType) {
        for (VersionableObjectEventType vot : events) {
            if (ObjectUtil.validString(vot.getURN())) {
                //FUNC Test the urn is of the correct type?
                references.add(new StructureReferenceBeanImpl(vot.getURN()));
            } else {
                String id = vot.getID();
                String version = vot.getVersion();
                if (id != null && id.equals(WILDCARD)) {
                    id = null;
                }
                if (version != null && version.equals(WILDCARD)) {
                    version = null;
                }
                if (ObjectUtil.validCollection(agencyIds)) {
                    for (String agencyId : agencyIds) {
                        references.add(new StructureReferenceBeanImpl(agencyId, id, version, structureType));
                    }
                } else {
                    references.add(new StructureReferenceBeanImpl(null, id, version, structureType));
                }
            }
        }
    }

    private void addEventSubscriptions(List<MaintainableEventType> events, SDMX_STRUCTURE_TYPE structureType) {
        for (MaintainableEventType event : events) {
            if (ObjectUtil.validString(event.getURN())) {
                references.add(new StructureReferenceBeanImpl(event.getURN()));
            } else {
                MaintainableQueryType queryType = event.getRef();
                references.add(new StructureReferenceBeanImpl(queryType.getAgencyID(), queryType.getId(), queryType.getVersion(), structureType));
            }
        }
    }

    private void addEventSubscriptions(List<? extends ReferenceType> events) {
        for (ReferenceType event : events) {
            references.add(RefUtil.createReference(this, event));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION 							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() throws SdmxSemmanticException {
        if (this.isFinal.isTrue()) {
            throw new SdmxSemmanticException("Subscription can not be made final");
        }
        if (!this.getVersion().equals(DEFAULT_VERSION)) {
            throw new SdmxSemmanticException("Subscription can not have a version, other then the default version : " + DEFAULT_VERSION);
        }
        if (this.owner == null) {
            throw new SdmxSemmanticException("Subscription must have an owner which must be a data consumer - no owner provided");
        }
        if (owner.getTargetReference() != SDMX_STRUCTURE_TYPE.DATA_CONSUMER
                && owner.getTargetReference() != SDMX_STRUCTURE_TYPE.DATA_PROVIDER
                && owner.getTargetReference() != SDMX_STRUCTURE_TYPE.AGENCY) {
            throw new SdmxSemmanticException("Subscription must have an owner which must be an agency, data provider, or data consumer - " + owner.getTargetReference().getType() + " was provided");
        }
        if (mailTo.size() == 0 && HTTPPostTo.size() == 0) {
            throw new SdmxSemmanticException("Subscription must declare at least one HTTP POST to, or mail to address to send notifications to");
        }
        for (String currentEmail : mailTo) {
            if (!EmailValidation.validateEmail(currentEmail)) {
                throw new SdmxSemmanticException("'" + currentEmail + "' is not a valid email address");
            }
        }
        for (String currentHttp : HTTPPostTo) {
            try {
                new URL(currentHttp);
            } catch (MalformedURLException e) {
                throw new SdmxSemmanticException("'" + currentHttp + "' is not a valid URL");
            }
        }
        //FUNC Validate email addresses?
        //FUNC Validate POST addresses?
        if (references.size() == 0) {
            //Subscribe to ALL
            references.add(new StructureReferenceBeanImpl(SDMX_STRUCTURE_TYPE.ANY));
        }
        if (this.subscriptionType == null) {
            throw new SdmxSemmanticException("Subscription type not declared");
        }
        super.agencyId = owner.getMaintainableReference().getAgencyId();
        super.validateAgencyId();
    }

    @Override
    protected void validateAgencyId() {
        //Do nothing yet, not yet fully built
    }

    @Override
    public boolean deepEquals(SDMXBean bean, boolean includeFinalProperties) {
        throw new SdmxNotImplementedException("deepEquals on subscription");
    }

    @Override
    protected Set<CrossReferenceBean> getCrossReferenceInternal() {
        Set<CrossReferenceBean> references = new HashSet<CrossReferenceBean>();
        if (owner != null) {
            references.add(owner);
        }
        return references;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public MaintainableBean getStub(URL actualLocation, boolean isServiceUrl) {
        return new SubscriptionBeanImpl(this, actualLocation, isServiceUrl);
    }

    @Override
    public SubscriptionMutableBean getMutableInstance() {
        return new SubscriptionMutableBeanImpl(this);
    }

    @Override
    public CrossReferenceBean getOwner() {
        return owner;
    }

    @Override
    public String getAgencyId() {
        if (owner.getTargetReference() == SDMX_STRUCTURE_TYPE.AGENCY) {
            if (owner.getMaintainableReference().getAgencyId().equals(AgencySchemeBean.DEFAULT_SCHEME)) {
                return owner.getChildReference().getId();
            }
            String id = owner.getMaintainableReference().getAgencyId();
            for (String currentIdentId : owner.getIdentifiableIds()) {
                id += "." + currentIdentId;
            }
            return id;
        }
        return owner.getMaintainableReference().getAgencyId();
    }

    @Override
    public List<String> getMailTo() {
        return new ArrayList<String>(mailTo);
    }

    @Override
    public List<String> getHTTPPostTo() {
        return new ArrayList<String>(HTTPPostTo);
    }

    @Override
    public List<StructureReferenceBean> getReferences() {
        return new ArrayList<StructureReferenceBean>(references);
    }

    @Override
    public SUBSCRIPTION_TYPE getSubscriptionType() {
        return subscriptionType;
    }

    private void setSubscriptionType(SUBSCRIPTION_TYPE type) {
        if (this.subscriptionType != null && this.subscriptionType != type) {
            throw new SdmxSemmanticException("Subscription can not be for more then one event type (structure event, registration event or a provision event)");
        }
        this.subscriptionType = type;
    }

    @Override
    public String getUrn() {
//		if(owner.getTargetReference() == SDMX_STRUCTURE_TYPE.AGENCY) {
//			return super.getUrn();
//		}
        return super.getUrn() + "." + owner.getChildReference().getId() + "." + owner.getTargetReference().getUrnClass();
    }
}
