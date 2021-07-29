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

import org.sdmx.resources.sdmxml.schemas.v20.registry.NotifyRegistryEventType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.*;
import org.sdmxsource.sdmx.api.constants.DATASET_ACTION;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.registry.NotificationEvent;
import org.sdmxsource.sdmx.api.model.beans.registry.RegistrationBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.AgencySchemeBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.DataConsumerSchemeBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.DataProviderSchemeBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.OrganisationUnitSchemeBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.categoryscheme.CategorisationBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.categoryscheme.CategorySchemeBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.categoryscheme.ReportingTaxonomyBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.codelist.CodelistBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.codelist.HierarchicalCodelistBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.conceptscheme.ConceptSchemeBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.datastructure.DataStructureBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.datastructure.DataflowBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.mapping.StructureSetBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.metadatastructure.MetadataStructureDefinitionBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.metadatastructure.MetadataflowBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.process.ProcessBeanImpl;
import org.sdmxsource.sdmx.util.beans.container.SdmxBeansImpl;
import org.sdmxsource.sdmx.util.date.DateUtil;

import java.util.Date;


/**
 * The type Notification event.
 */
public class NotificationEventImpl implements NotificationEvent {
    private Date eventTime;
    private String objectUrn;
    private String subscriptionUrn;
    private DATASET_ACTION action;
    private RegistrationBean registration;
    private SdmxBeans beans;


    /**
     * Instantiates a new Notification event.
     *
     * @param notification the notification
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public NotificationEventImpl(org.sdmx.resources.sdmxml.schemas.v21.registry.NotifyRegistryEventType notification) {
        this.eventTime = DateUtil.formatDate(notification.getEventTime(), true);
        this.objectUrn = notification.getObjectURN();
        this.subscriptionUrn = notification.getSubscriptionURN();
        this.action = DATASET_ACTION.valueOf(notification.getEventAction().toString().toUpperCase());
        if (notification.getRegistrationEvent() != null) {
            this.registration = new RegistrationBeanImpl(notification.getRegistrationEvent().getRegistration());
        } else {
            beans = new SdmxBeansImpl();
            StructuresType structures = notification.getStructuralEvent().getStructures();
            if (structures.getCategorisations() != null) {
                for (CategorisationType type : structures.getCategorisations().getCategorisationList()) {
                    beans.addCategorisation(new CategorisationBeanImpl(type));
                }
            }
            if (structures.getCategorySchemes() != null) {
                for (CategorySchemeType type : structures.getCategorySchemes().getCategorySchemeList()) {
                    beans.addCategoryScheme(new CategorySchemeBeanImpl(type));
                }
            }
            if (structures.getCodelists() != null) {
                for (CodelistType type : structures.getCodelists().getCodelistList()) {
                    beans.addCodelist(new CodelistBeanImpl(type));
                }
            }
            if (structures.getConcepts() != null) {
                for (ConceptSchemeType type : structures.getConcepts().getConceptSchemeList()) {
                    beans.addConceptScheme(new ConceptSchemeBeanImpl(type));
                }
            }
            if (structures.getConstraints() != null) {
                for (AttachmentConstraintType type : structures.getConstraints().getAttachmentConstraintList()) {
                    beans.addAttachmentConstraint(new AttachmentConstraintBeanImpl(type));
                }
                for (ContentConstraintType type : structures.getConstraints().getContentConstraintList()) {
                    beans.addContentConstraintBean(new ContentConstraintBeanImpl(type));
                }
            }
            if (structures.getDataflows() != null) {
                for (DataflowType type : structures.getDataflows().getDataflowList()) {
                    beans.addDataflow(new DataflowBeanImpl(type));
                }
            }
            if (structures.getDataStructures() != null) {
                for (DataStructureType type : structures.getDataStructures().getDataStructureList()) {
                    beans.addDataStructure(new DataStructureBeanImpl(type));
                }
            }
            if (structures.getHierarchicalCodelists() != null) {
                for (HierarchicalCodelistType type : structures.getHierarchicalCodelists().getHierarchicalCodelistList()) {
                    beans.addHierarchicalCodelist(new HierarchicalCodelistBeanImpl(type));
                }
            }
            if (structures.getMetadataflows() != null) {
                for (MetadataflowType type : structures.getMetadataflows().getMetadataflowList()) {
                    beans.addMetadataFlow(new MetadataflowBeanImpl(type));
                }
            }
            if (structures.getMetadataStructures() != null) {
                for (MetadataStructureType type : structures.getMetadataStructures().getMetadataStructureList()) {
                    beans.addMetadataStructure(new MetadataStructureDefinitionBeanImpl(type));
                }
            }
            if (structures.getOrganisationSchemes() != null) {
                for (AgencySchemeType type : structures.getOrganisationSchemes().getAgencySchemeList()) {
                    beans.addAgencyScheme(new AgencySchemeBeanImpl(type));
                }
                for (DataConsumerSchemeType type : structures.getOrganisationSchemes().getDataConsumerSchemeList()) {
                    beans.addDataConsumerScheme(new DataConsumerSchemeBeanImpl(type));
                }
                for (DataProviderSchemeType type : structures.getOrganisationSchemes().getDataProviderSchemeList()) {
                    beans.addDataProviderScheme(new DataProviderSchemeBeanImpl(type));
                }
                for (OrganisationUnitSchemeType type : structures.getOrganisationSchemes().getOrganisationUnitSchemeList()) {
                    beans.addOrganisationUnitScheme(new OrganisationUnitSchemeBeanImpl(type));
                }
            }
            if (structures.getProcesses() != null) {
                for (ProcessType type : structures.getProcesses().getProcessList()) {
                    beans.addProcess(new ProcessBeanImpl(type));
                }
            }
            if (structures.getProvisionAgreements() != null) {
                for (ProvisionAgreementType type : structures.getProvisionAgreements().getProvisionAgreementList()) {
                    beans.addProvisionAgreement(new ProvisionAgreementBeanImpl(type));
                }
            }
            if (structures.getReportingTaxonomies() != null) {
                for (ReportingTaxonomyType type : structures.getReportingTaxonomies().getReportingTaxonomyList()) {
                    beans.addReportingTaxonomy(new ReportingTaxonomyBeanImpl(type));
                }
            }
            if (structures.getStructureSets() != null) {
                for (StructureSetType type : structures.getStructureSets().getStructureSetList()) {
                    beans.addStructureSet(new StructureSetBeanImpl(type));
                }
            }
        }
    }

    /**
     * Instantiates a new Notification event.
     *
     * @param notification the notification
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.0 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public NotificationEventImpl(NotifyRegistryEventType notification) {
        this.eventTime = DateUtil.formatDate(notification.getEventTime(), true);
        this.objectUrn = notification.getObjectURN();
        this.subscriptionUrn = notification.getSubscriptionURN();
        this.action = DATASET_ACTION.valueOf(notification.getEventAction().toString().toUpperCase());
        if (notification.getRegistrationEvent() != null) {
            this.registration = new RegistrationBeanImpl(notification.getRegistrationEvent().getRegistration());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public Date getEventTime() {
        return eventTime;
    }

    @Override
    public String getObjectUrn() {
        return objectUrn;
    }

    @Override
    public String getSubscriptionUrn() {
        return subscriptionUrn;
    }

    @Override
    public DATASET_ACTION getAction() {
        return action;
    }

    @Override
    public SdmxBeans getBeans() {
        return beans;
    }

    @Override
    public RegistrationBean getRegistration() {
        return registration;
    }
}
