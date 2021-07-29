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
package org.sdmxsource.sdmx.util.beans.container;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.mutable.MutableBeans;
import org.sdmxsource.sdmx.api.model.mutable.base.*;
import org.sdmxsource.sdmx.api.model.mutable.categoryscheme.CategorisationMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.categoryscheme.CategorySchemeMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.categoryscheme.ReportingTaxonomyMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.codelist.CodelistMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.codelist.HierarchicalCodelistMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.conceptscheme.ConceptSchemeMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.DataStructureMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.DataflowMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.mapping.StructureSetMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.metadatastructure.MetadataFlowMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.metadatastructure.MetadataStructureDefinitionMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.process.ProcessMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.ContentConstraintMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.ProvisionAgreementMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.RegistrationMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.SubscriptionMutableBean;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


/**
 * The type Mutable beans.
 */
public class MutableBeansImpl implements MutableBeans {
    private Set<AgencySchemeMutableBean> agencyScheme = new HashSet<AgencySchemeMutableBean>();
    // FUNC this needs to be implemented
//	private Set<AttachmentConstraintMutableBean> attachmentConstraint = new HashSet<AttachmentConstraintMutableBean>();
    private Set<DataProviderSchemeMutableBean> dataProviderScheme = new HashSet<DataProviderSchemeMutableBean>();
    private Set<DataConsumerSchemeMutableBean> dataConsumerScheme = new HashSet<DataConsumerSchemeMutableBean>();
    private Set<OrganisationUnitSchemeMutableBean> organisationUnitScheme = new HashSet<OrganisationUnitSchemeMutableBean>();
    private Set<CategorisationMutableBean> categorisations = new HashSet<CategorisationMutableBean>();
    private Set<CategorySchemeMutableBean> categorySchemes = new HashSet<CategorySchemeMutableBean>();
    private Set<CodelistMutableBean> codelists = new HashSet<CodelistMutableBean>();
    private Set<ConceptSchemeMutableBean> conceptSchemes = new HashSet<ConceptSchemeMutableBean>();
    private Set<ContentConstraintMutableBean> contentConstraint = new HashSet<ContentConstraintMutableBean>();
    private Set<DataflowMutableBean> dataflows = new HashSet<DataflowMutableBean>();
    private Set<HierarchicalCodelistMutableBean> hcls = new HashSet<HierarchicalCodelistMutableBean>();
    private Set<DataStructureMutableBean> dataStructures = new HashSet<DataStructureMutableBean>();
    private Set<MetadataFlowMutableBean> metadataflows = new HashSet<MetadataFlowMutableBean>();
    private Set<MetadataStructureDefinitionMutableBean> metadataStructures = new HashSet<MetadataStructureDefinitionMutableBean>();
    private Set<ProcessMutableBean> processes = new HashSet<ProcessMutableBean>();
    private Set<StructureSetMutableBean> structureSet = new HashSet<StructureSetMutableBean>();
    private Set<ReportingTaxonomyMutableBean> reportingTaxonomy = new HashSet<ReportingTaxonomyMutableBean>();
    private Set<ProvisionAgreementMutableBean> provisionAgreement = new HashSet<ProvisionAgreementMutableBean>();
    private Set<RegistrationMutableBean> registrations = new HashSet<RegistrationMutableBean>();
    private Set<SubscriptionMutableBean> subscriptions = new HashSet<SubscriptionMutableBean>();

    /**
     * Instantiates a new Mutable beans.
     */
    public MutableBeansImpl() {
    }

    /**
     * Instantiates a new Mutable beans.
     *
     * @param maintainables the maintainables
     */
    public MutableBeansImpl(Collection<MaintainableMutableBean> maintainables) {
        if (maintainables != null) {
            for (MaintainableMutableBean currentMaintainable : maintainables) {
                addIdentifiable(currentMaintainable);
            }
        }
    }

    @Override
    public SdmxBeans getImmutableBeans() {
        SdmxBeans returnBeans = new SdmxBeansImpl();
        for (MaintainableMutableBean currentMaint : getAllMaintainables()) {
            returnBeans.addIdentifiable(currentMaint.getImmutableInstance());
        }
        return returnBeans;
    }


    @Override
    public void addIdentifiables(Collection<? extends IdentifiableMutableBean> beans) {
        for (IdentifiableMutableBean identifiable : beans) {
            addIdentifiable(identifiable);
        }
    }

    @Override
    public void addIdentifiable(IdentifiableMutableBean bean) {
        if (bean == null) {
            return;
        }
        if (bean instanceof AgencySchemeMutableBean) {
            addAgencyScheme((AgencySchemeMutableBean) bean);
        }
        // FUNC = implement AttachmentConstraintMutableBean
//		else if(bean instanceof AttachmentConstraintMutableBean) {
//			addAttachmentConstraint((AttachmentConstraintMutableBean)bean);
//		}
        else if (bean instanceof DataProviderSchemeMutableBean) {
            addDataProviderScheme((DataProviderSchemeMutableBean) bean);
        } else if (bean instanceof DataConsumerSchemeMutableBean) {
            addDataConsumerScheme((DataConsumerSchemeMutableBean) bean);
        } else if (bean instanceof OrganisationUnitSchemeMutableBean) {
            addOrganisationUnitScheme((OrganisationUnitSchemeMutableBean) bean);
        } else if (bean instanceof CategorisationMutableBean) {
            addCategorisation((CategorisationMutableBean) bean);
        } else if (bean instanceof CategorySchemeMutableBean) {
            addCategoryScheme((CategorySchemeMutableBean) bean);
        } else if (bean instanceof CodelistMutableBean) {
            addCodelist((CodelistMutableBean) bean);
        } else if (bean instanceof ConceptSchemeMutableBean) {
            addConceptScheme((ConceptSchemeMutableBean) bean);
        } else if (bean instanceof ContentConstraintMutableBean) {
            addContentConstraint((ContentConstraintMutableBean) bean);
        } else if (bean instanceof DataflowMutableBean) {
            addDataflow((DataflowMutableBean) bean);
        } else if (bean instanceof HierarchicalCodelistMutableBean) {
            addHierarchicalCodelist((HierarchicalCodelistMutableBean) bean);
        } else if (bean instanceof DataStructureMutableBean) {
            addDataStructure((DataStructureMutableBean) bean);
        } else if (bean instanceof MetadataFlowMutableBean) {
            addMetadataFlow((MetadataFlowMutableBean) bean);
        } else if (bean instanceof MetadataStructureDefinitionMutableBean) {
            addMetadataStructure((MetadataStructureDefinitionMutableBean) bean);
        } else if (bean instanceof ProcessMutableBean) {
            addProcess((ProcessMutableBean) bean);
        } else if (bean instanceof ReportingTaxonomyMutableBean) {
            addReportingTaxonomy((ReportingTaxonomyMutableBean) bean);
        } else if (bean instanceof StructureSetMutableBean) {
            addStructureSet((StructureSetMutableBean) bean);
        } else if (bean instanceof ProvisionAgreementMutableBean) {
            addProvision((ProvisionAgreementMutableBean) bean);
        } else if (bean instanceof StructureSetMutableBean) {
            addStructureSet((StructureSetMutableBean) bean);
        } else if (bean instanceof StructureSetMutableBean) {
            addStructureSet((StructureSetMutableBean) bean);
        }
    }

    @Override
    public Set<MaintainableMutableBean> getAllMaintainables() {
        Set<MaintainableMutableBean> returnSet = new HashSet<MaintainableMutableBean>();
        returnSet.addAll(this.agencyScheme);
// FUNC this needs to be implemented		
//		returnSet.addAll(this.attachmentConstraint);
        returnSet.addAll(this.dataConsumerScheme);
        returnSet.addAll(this.dataProviderScheme);
        returnSet.addAll(this.organisationUnitScheme);
        returnSet.addAll(this.categorisations);
        returnSet.addAll(this.categorySchemes);
        returnSet.addAll(this.codelists);
        returnSet.addAll(this.conceptSchemes);
        returnSet.addAll(this.contentConstraint);
        returnSet.addAll(this.dataflows);
        returnSet.addAll(this.hcls);
        returnSet.addAll(this.dataStructures);
        returnSet.addAll(this.metadataflows);
        returnSet.addAll(this.metadataStructures);
        returnSet.addAll(this.processes);
        returnSet.addAll(this.structureSet);
        returnSet.addAll(this.reportingTaxonomy);
        returnSet.addAll(this.provisionAgreement);
        returnSet.addAll(this.registrations);
        returnSet.addAll(this.subscriptions);
        return returnSet;
    }

    @Override
    public Set<MaintainableMutableBean> getMaintainables(SDMX_STRUCTURE_TYPE structureType) {
        if (structureType == SDMX_STRUCTURE_TYPE.AGENCY_SCHEME) {
            return new HashSet<MaintainableMutableBean>(agencyScheme);
        }
        if (structureType == SDMX_STRUCTURE_TYPE.DATA_CONSUMER_SCHEME) {
            return new HashSet<MaintainableMutableBean>(dataConsumerScheme);
        }
        if (structureType == SDMX_STRUCTURE_TYPE.DATA_PROVIDER_SCHEME) {
            return new HashSet<MaintainableMutableBean>(dataProviderScheme);
        }
        if (structureType == SDMX_STRUCTURE_TYPE.ORGANISATION_UNIT_SCHEME) {
            return new HashSet<MaintainableMutableBean>(organisationUnitScheme);
        }
        if (structureType == SDMX_STRUCTURE_TYPE.CATEGORISATION) {
            return new HashSet<MaintainableMutableBean>(categorisations);
        }
        if (structureType == SDMX_STRUCTURE_TYPE.CATEGORY_SCHEME) {
            return new HashSet<MaintainableMutableBean>(categorySchemes);
        }
        if (structureType == SDMX_STRUCTURE_TYPE.CODE_LIST) {
            return new HashSet<MaintainableMutableBean>(codelists);
        }
        if (structureType == SDMX_STRUCTURE_TYPE.CONCEPT_SCHEME) {
            return new HashSet<MaintainableMutableBean>(conceptSchemes);
        }
        if (structureType == SDMX_STRUCTURE_TYPE.CONTENT_CONSTRAINT) {
            return new HashSet<MaintainableMutableBean>(contentConstraint);
        }
        if (structureType == SDMX_STRUCTURE_TYPE.DATAFLOW) {
            return new HashSet<MaintainableMutableBean>(dataflows);
        }
        if (structureType == SDMX_STRUCTURE_TYPE.HIERARCHICAL_CODELIST) {
            return new HashSet<MaintainableMutableBean>(hcls);
        }
        if (structureType == SDMX_STRUCTURE_TYPE.DSD) {
            return new HashSet<MaintainableMutableBean>(dataStructures);
        }
        if (structureType == SDMX_STRUCTURE_TYPE.METADATA_FLOW) {
            return new HashSet<MaintainableMutableBean>(metadataflows);
        }
        if (structureType == SDMX_STRUCTURE_TYPE.MSD) {
            return new HashSet<MaintainableMutableBean>(metadataStructures);
        }
        if (structureType == SDMX_STRUCTURE_TYPE.PROCESS) {
            return new HashSet<MaintainableMutableBean>(processes);
        }
        if (structureType == SDMX_STRUCTURE_TYPE.STRUCTURE_SET) {
            return new HashSet<MaintainableMutableBean>(structureSet);
        }
        if (structureType == SDMX_STRUCTURE_TYPE.REPORTING_TAXONOMY) {
            return new HashSet<MaintainableMutableBean>(reportingTaxonomy);
        }
        if (structureType == SDMX_STRUCTURE_TYPE.PROVISION_AGREEMENT) {
            return new HashSet<MaintainableMutableBean>(provisionAgreement);
        }
        if (structureType == SDMX_STRUCTURE_TYPE.REGISTRATION) {
            return new HashSet<MaintainableMutableBean>(registrations);
        }
        if (structureType == SDMX_STRUCTURE_TYPE.SUBSCRIPTION) {
            return new HashSet<MaintainableMutableBean>(subscriptions);
        }
        throw new IllegalArgumentException("Can not return structure type:" + structureType);
    }


    //ADDERS
    @Override
    public void addAgencyScheme(AgencySchemeMutableBean bean) {
        if (bean != null) {
            this.agencyScheme.remove(bean);
            this.agencyScheme.add(bean);
        }
    }

    // FUNC - implement AttachmentConstraintMutableBean
	/*
	public void addAttachmentConstraint(AttachmentConstraintMutableBean bean) {
		if(bean != null) {
			this.attachmentConstraint.remove(bean);
			this.attachmentConstraint.add(bean);
		}
	}
	*/

    @Override
    public void addDataConsumerScheme(DataConsumerSchemeMutableBean bean) {
        if (bean != null) {
            this.dataConsumerScheme.remove(bean);
            this.dataConsumerScheme.add(bean);
        }
    }

    @Override
    public void addDataProviderScheme(DataProviderSchemeMutableBean bean) {
        if (bean != null) {
            this.dataProviderScheme.remove(bean);
            this.dataProviderScheme.add(bean);
        }
    }

    @Override
    public void addOrganisationUnitScheme(OrganisationUnitSchemeMutableBean bean) {
        if (bean != null) {
            this.organisationUnitScheme.remove(bean);
            this.organisationUnitScheme.add(bean);
        }
    }

    @Override
    public void addCategoryScheme(CategorySchemeMutableBean bean) {
        if (bean != null) {
            this.categorySchemes.remove(bean);
            this.categorySchemes.add(bean);
        }
    }

    @Override
    public void addCategorisation(CategorisationMutableBean bean) {
        if (bean != null) {
            this.categorisations.remove(bean);
            this.categorisations.add(bean);
        }
    }

    @Override
    public void addCodelist(CodelistMutableBean bean) {
        if (bean != null) {
            this.codelists.remove(bean);
            this.codelists.add(bean);
        }
    }

    @Override
    public void addConceptScheme(ConceptSchemeMutableBean bean) {
        if (bean != null) {
            this.conceptSchemes.remove(bean);
            this.conceptSchemes.add(bean);
        }
    }

    @Override
    public void addContentConstraint(ContentConstraintMutableBean bean) {
        if (bean != null) {
            this.contentConstraint.remove(bean);
            this.contentConstraint.add(bean);
        }
    }

    @Override
    public void addDataflow(DataflowMutableBean bean) {
        if (bean != null) {
            this.dataflows.remove(bean);
            this.dataflows.add(bean);
        }
    }

    @Override
    public void addHierarchicalCodelist(HierarchicalCodelistMutableBean bean) {
        if (bean != null) {
            this.hcls.remove(bean);
            this.hcls.add(bean);
        }
    }

    @Override
    public void addDataStructure(DataStructureMutableBean bean) {
        if (bean != null) {
            this.dataStructures.remove(bean);
            this.dataStructures.add(bean);
        }
    }

    @Override
    public void addMetadataFlow(MetadataFlowMutableBean bean) {
        if (bean != null) {
            this.metadataflows.remove(bean);
            this.metadataflows.add(bean);
        }
    }

    @Override
    public void addMetadataStructure(MetadataStructureDefinitionMutableBean bean) {
        if (bean != null) {
            this.metadataStructures.remove(bean);
            this.metadataStructures.add(bean);
        }
    }

    @Override
    public void addProcess(ProcessMutableBean bean) {
        if (bean != null) {
            this.processes.remove(bean);
            this.processes.add(bean);
        }
    }

    @Override
    public void addReportingTaxonomy(ReportingTaxonomyMutableBean bean) {
        if (bean != null) {
            this.reportingTaxonomy.remove(bean);
            this.reportingTaxonomy.add(bean);
        }
    }

    @Override
    public void addStructureSet(StructureSetMutableBean bean) {
        if (bean != null) {
            this.structureSet.remove(bean);
            this.structureSet.add(bean);
        }
    }

    @Override
    public void addProvision(ProvisionAgreementMutableBean bean) {
        if (bean != null) {
            this.provisionAgreement.remove(bean);
            this.provisionAgreement.add(bean);
        }
    }

    @Override
    public void addRegistration(RegistrationMutableBean bean) {
        if (bean != null) {
            this.registrations.remove(bean);
            this.registrations.add(bean);
        }
    }

    @Override
    public void addSubscription(SubscriptionMutableBean bean) {
        if (bean != null) {
            this.subscriptions.remove(bean);
            this.subscriptions.add(bean);
        }
    }

    //GETTERS
    @Override
    public Set<AgencySchemeMutableBean> getAgencySchemeMutableBeans() {
        return agencyScheme;
    }

    // FUNC - implement AttachmentConstraintMutableBean
	/*
	public Set<AttachmentConstraintMutableBean> getAttachmentConstraints() {
		return attachmentConstrains;
	}
	*/

    @Override
    public Set<DataConsumerSchemeMutableBean> getDataConsumberSchemeMutableBeans() {
        return dataConsumerScheme;
    }

    @Override
    public Set<DataProviderSchemeMutableBean> getDataProviderSchemeMutableBeans() {
        return dataProviderScheme;
    }

    @Override
    public Set<OrganisationUnitSchemeMutableBean> getOrganisationUnitSchemes() {
        return organisationUnitScheme;
    }

    @Override
    public Set<CategorySchemeMutableBean> getCategorySchemes() {
        return categorySchemes;
    }

    @Override
    public Set<CategorisationMutableBean> getCategorisations() {
        return categorisations;
    }

    @Override
    public Set<CodelistMutableBean> getCodelists() {
        return codelists;
    }

    @Override
    public Set<ConceptSchemeMutableBean> getConceptSchemes() {
        return conceptSchemes;
    }

    @Override
    public Set<ContentConstraintMutableBean> getContentConstraints() {
        return contentConstraint;
    }

    @Override
    public Set<DataflowMutableBean> getDataflows() {
        return dataflows;
    }

    @Override
    public Set<HierarchicalCodelistMutableBean> getHierarchicalCodelists() {
        return hcls;
    }

    @Override
    public Set<DataStructureMutableBean> getDataStructures() {
        return dataStructures;
    }

    @Override
    public Set<MetadataStructureDefinitionMutableBean> getMetadataStructures() {
        return metadataStructures;
    }

    @Override
    public Set<MetadataFlowMutableBean> getMetadataflows() {
        return metadataflows;
    }

    @Override
    public Set<ProcessMutableBean> getProcesses() {
        return processes;
    }

    @Override
    public Set<ReportingTaxonomyMutableBean> getReportingTaxonomys() {
        return reportingTaxonomy;
    }

    @Override
    public Set<StructureSetMutableBean> getStructureSets() {
        return structureSet;
    }

    @Override
    public Set<ProvisionAgreementMutableBean> getProvisions() {
        return provisionAgreement;
    }

    @Override
    public Set<RegistrationMutableBean> getRegistrations() {
        return registrations;
    }

    @Override
    public Set<SubscriptionMutableBean> getSubscriptions() {
        return subscriptions;
    }

    //REMOVERS
    @Override
    public void removeAgencySchemeMutableBeans(AgencySchemeMutableBean bean) {
        this.agencyScheme.remove(bean);
    }

    @Override
    public void removeDataConsumberSchemeMutableBeans(DataConsumerSchemeMutableBean bean) {
        this.dataConsumerScheme.remove(bean);
    }

    @Override
    public void removeDataProviderSchemeMutableBeans(DataProviderSchemeMutableBean bean) {
        this.dataProviderScheme.remove(bean);
    }

    @Override
    public void removeOrganisationUnitScheme(OrganisationUnitSchemeMutableBean bean) {
        this.organisationUnitScheme.remove(bean);
    }

    @Override
    public void removeCategoryScheme(CategorySchemeMutableBean bean) {
        this.categorySchemes.remove(bean);
    }

    @Override
    public void removeCategorisation(CategorisationMutableBean bean) {
        this.categorisations.remove(bean);
    }

    @Override
    public void removeCodelist(CodelistMutableBean bean) {
        this.codelists.remove(bean);
    }

    @Override
    public void removeConceptScheme(ConceptSchemeMutableBean bean) {
        this.conceptSchemes.remove(bean);
    }

    @Override
    public void removeContentConstraint(ContentConstraintMutableBean bean) {
        this.contentConstraint.remove(bean);
    }

    @Override
    public void removeDataflow(DataflowMutableBean bean) {
        this.dataflows.remove(bean);
    }

    @Override
    public void removeHierarchicalCodelist(HierarchicalCodelistMutableBean bean) {
        this.hcls.remove(bean);
    }

    @Override
    public void removeDataStructure(DataStructureMutableBean bean) {
        this.dataStructures.remove(bean);
    }

    @Override
    public void removeMetadataFlow(MetadataFlowMutableBean bean) {
        this.metadataflows.remove(bean);
    }

    @Override
    public void removeMetadataStructure(MetadataStructureDefinitionMutableBean bean) {
        this.metadataStructures.remove(bean);
    }

    @Override
    public void removeProcess(ProcessMutableBean bean) {
        this.processes.remove(bean);
    }

    @Override
    public void removeReportingTaxonomy(ReportingTaxonomyMutableBean bean) {
        this.reportingTaxonomy.remove(bean);
    }

    @Override
    public void removeStructureSet(StructureSetMutableBean bean) {
        this.structureSet.remove(bean);
    }

    @Override
    public void removeProvision(ProvisionAgreementMutableBean bean) {
        this.provisionAgreement.remove(bean);
    }

    @Override
    public void removeRegistration(RegistrationMutableBean bean) {
        this.registrations.remove(bean);
    }

    @Override
    public void removeSubscription(SubscriptionMutableBean bean) {
        this.subscriptions.remove(bean);
    }

}
