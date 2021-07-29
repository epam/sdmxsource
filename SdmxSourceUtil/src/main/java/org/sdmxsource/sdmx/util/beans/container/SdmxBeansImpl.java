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

import org.sdmxsource.sdmx.api.constants.DATASET_ACTION;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.model.beans.AgencyMetadata;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeansInfo;
import org.sdmxsource.sdmx.api.model.beans.base.*;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategorisationBean;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategorySchemeBean;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.ReportingTaxonomyBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.HierarchicalCodelistBean;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptSchemeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.mapping.StructureSetBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataFlowBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataStructureDefinitionBean;
import org.sdmxsource.sdmx.api.model.beans.process.ProcessBean;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.*;
import org.sdmxsource.sdmx.api.model.header.HeaderBean;
import org.sdmxsource.sdmx.api.model.mutable.MutableBeans;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;
import org.sdmxsource.sdmx.util.sort.MaintainableSortByIdentifiers;
import org.sdmxsource.util.ObjectUtil;

import java.util.*;


/**
 * The type Sdmx beans.
 */
public class SdmxBeansImpl implements SdmxBeans {
    private static final long serialVersionUID = 5981995104378086631L;
    private String id = UUID.randomUUID().toString();
    private DATASET_ACTION action = DATASET_ACTION.INFORMATION;  //If Header is not available
    private HeaderBean header;
    private Set<AgencySchemeBean> agencySchemes = new HashSet<AgencySchemeBean>();
    private Set<OrganisationUnitSchemeBean> organisationUnitSchemes = new HashSet<OrganisationUnitSchemeBean>();
    private Set<DataProviderSchemeBean> dataProviderSchemes = new HashSet<DataProviderSchemeBean>();
    private Set<DataConsumerSchemeBean> dataConsumerSchemes = new HashSet<DataConsumerSchemeBean>();
    private Set<AttachmentConstraintBean> attachmentConstraints = new HashSet<AttachmentConstraintBean>();
    private Set<ContentConstraintBean> contentConstraints = new HashSet<ContentConstraintBean>();
    private Set<CategorySchemeBean> categorySchemes = new HashSet<CategorySchemeBean>();
    private Set<CodelistBean> codelists = new HashSet<CodelistBean>();
    private Set<ConceptSchemeBean> conceptSchemes = new HashSet<ConceptSchemeBean>();
    private Set<DataflowBean> dataflows = new HashSet<DataflowBean>();
    private Set<HierarchicalCodelistBean> hcls = new HashSet<HierarchicalCodelistBean>();
    private Set<DataStructureBean> dataStructures = new HashSet<DataStructureBean>();
    private Set<MetadataFlowBean> metadataflows = new HashSet<MetadataFlowBean>();
    private Set<MetadataStructureDefinitionBean> metadataStructures = new HashSet<MetadataStructureDefinitionBean>();
    private Set<ProcessBean> processes = new HashSet<ProcessBean>();
    private Set<StructureSetBean> structureSet = new HashSet<StructureSetBean>();
    private Set<ReportingTaxonomyBean> reportingTaxonomy = new HashSet<ReportingTaxonomyBean>();
    private Set<CategorisationBean> categorisation = new HashSet<CategorisationBean>();
    private Set<ProvisionAgreementBean> provisionAgreement = new HashSet<ProvisionAgreementBean>();
    private Set<RegistrationBean> registrations = new HashSet<RegistrationBean>();
    private Set<SubscriptionBean> subscriptions = new HashSet<SubscriptionBean>();


    /**
     * Instantiates a new Sdmx beans.
     */
//////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////CONSTRUCTORS   						//////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////
    public SdmxBeansImpl() {
    }

    /**
     * Instantiates a new Sdmx beans.
     *
     * @param action the action
     */
    public SdmxBeansImpl(DATASET_ACTION action) {
        this.action = action;
    }

    /**
     * Instantiates a new Sdmx beans.
     *
     * @param header the header
     */
    public SdmxBeansImpl(HeaderBean header) {
        this.header = header;
    }

    /**
     * Instantiates a new Sdmx beans.
     *
     * @param header the header
     * @param action the action
     */
    public SdmxBeansImpl(HeaderBean header, DATASET_ACTION action) {
        this.header = header;
        this.action = action;
    }

    /**
     * Instantiates a new Sdmx beans.
     *
     * @param header        the header
     * @param maintainables the maintainables
     */
    public SdmxBeansImpl(HeaderBean header, Collection<? extends MaintainableBean> maintainables) {
        this.header = header;
        if (maintainables != null) {
            for (MaintainableBean currentMaintainable : maintainables) {
                addIdentifiable(currentMaintainable);
            }
        }
    }

    /**
     * Instantiates a new Sdmx beans.
     *
     * @param beans the beans
     */
    public SdmxBeansImpl(SdmxBeans... beans) {
        for (SdmxBeans currentBean : beans) {
            merge(currentBean);
        }
    }

    /**
     * Create Beans container from zero or more MaintainableBeans
     *
     * @param maintainableBeans the maintainable beans
     */
    public SdmxBeansImpl(MaintainableBean... maintainableBeans) {
        if (maintainableBeans != null) {
            for (MaintainableBean currentMaint : maintainableBeans) {
                this.addIdentifiable(currentMaint);
            }
        }
    }

    /**
     * Create Beans container from zero or more MaintainableBeans
     *
     * @param maintainableBeans the maintainable beans
     */
    public SdmxBeansImpl(Collection<? extends MaintainableBean> maintainableBeans) {
        if (maintainableBeans != null) {
            for (MaintainableBean currentMaint : maintainableBeans) {
                this.addIdentifiable(currentMaint);
            }
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////METHODS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public String getId() {
        return id;
    }

    @Override
    public HeaderBean getHeader() {
        return header;
    }

    @Override
    public void setHeader(HeaderBean header) {
        this.header = header;
    }

    @Override
    public DATASET_ACTION getAction() {
        return action;
    }

    @Override
    public void setAction(DATASET_ACTION action) {
        this.action = action;
    }

    @Override
    public MutableBeans getMutableBeans() {
        MutableBeans returnBeans = new MutableBeansImpl();
        for (MaintainableBean currentMaint : getAllMaintainables()) {
            returnBeans.addIdentifiable(currentMaint.getMutableInstance());
        }
        return returnBeans;
    }


    @Override
    public boolean hasStructures() {
        return getAllMaintainables(SDMX_STRUCTURE_TYPE.REGISTRATION, SDMX_STRUCTURE_TYPE.SUBSCRIPTION).size() > 0;
    }

    @Override
    public boolean hasSubscriptions() {
        return subscriptions.size() > 0;
    }

    @Override
    public boolean hasRegistrations() {
        return registrations.size() > 0;
    }

    @Override
    public void merge(SdmxBeans beans) {
        if (beans.getHeader() != null) {
            this.header = beans.getHeader();
        }
        if (beans.getAction() != null) {
            this.action = beans.getAction();
        }
        //REMOVE
        this.agencySchemes.removeAll(beans.getAgenciesSchemes());
        this.attachmentConstraints.removeAll(beans.getAttachmentConstraints());
        this.contentConstraints.removeAll(beans.getContentConstraintBeans());
        this.dataConsumerSchemes.removeAll(beans.getDataConsumerSchemes());
        this.dataProviderSchemes.removeAll(beans.getDataProviderSchemes());
        this.categorySchemes.removeAll(beans.getCategorySchemes());
        this.codelists.removeAll(beans.getCodelists());
        this.conceptSchemes.removeAll(beans.getConceptSchemes());
        this.dataflows.removeAll(beans.getDataflows());
        this.hcls.removeAll(beans.getHierarchicalCodelists());
        this.dataStructures.removeAll(beans.getDataStructures());
        this.metadataflows.removeAll(beans.getMetadataflows());
        this.metadataStructures.removeAll(beans.getMetadataStructures());
        this.organisationUnitSchemes.removeAll(beans.getOrganisationUnitSchemes());
        this.processes.removeAll(beans.getProcesses());
        this.structureSet.removeAll(beans.getStructureSets());
        this.reportingTaxonomy.removeAll(beans.getReportingTaxonomys());
        this.categorisation.removeAll(beans.getCategorisations());
        this.provisionAgreement.removeAll(beans.getProvisionAgreements());
        this.registrations.removeAll(beans.getRegistrations());
        this.subscriptions.removeAll(beans.getSubscriptions());

        //ADD
        this.agencySchemes.addAll(beans.getAgenciesSchemes());
        this.attachmentConstraints.addAll(beans.getAttachmentConstraints());
        this.contentConstraints.addAll(beans.getContentConstraintBeans());
        this.dataConsumerSchemes.addAll(beans.getDataConsumerSchemes());
        this.dataProviderSchemes.addAll(beans.getDataProviderSchemes());
        this.categorySchemes.addAll(beans.getCategorySchemes());
        this.codelists.addAll(beans.getCodelists());
        this.conceptSchemes.addAll(beans.getConceptSchemes());
        this.dataflows.addAll(beans.getDataflows());
        this.hcls.addAll(beans.getHierarchicalCodelists());
        this.dataStructures.addAll(beans.getDataStructures());
        this.metadataflows.addAll(beans.getMetadataflows());
        this.metadataStructures.addAll(beans.getMetadataStructures());
        this.organisationUnitSchemes.addAll(beans.getOrganisationUnitSchemes());
        this.processes.addAll(beans.getProcesses());
        this.structureSet.addAll(beans.getStructureSets());
        this.reportingTaxonomy.addAll(beans.getReportingTaxonomys());
        this.categorisation.addAll(beans.getCategorisations());
        this.provisionAgreement.addAll(beans.getProvisionAgreements());
        this.registrations.addAll(beans.getRegistrations());
        this.subscriptions.addAll(beans.getSubscriptions());
    }


    @Override
    public void addIdentifiables(Collection<? extends IdentifiableBean> beans) {
        for (IdentifiableBean identifiable : beans) {
            addIdentifiable(identifiable);
        }
    }

    @Override
    public void addIdentifiable(IdentifiableBean bean) {
        if (bean == null) {
            return;
        } else if (bean instanceof AgencySchemeBean) {
            addAgencyScheme((AgencySchemeBean) bean);
        } else if (bean instanceof AttachmentConstraintBean) {
            addAttachmentConstraint((AttachmentConstraintBean) bean);
        } else if (bean instanceof ContentConstraintBean) {
            addContentConstraintBean((ContentConstraintBean) bean);
        } else if (bean instanceof DataConsumerSchemeBean) {
            addDataConsumerScheme((DataConsumerSchemeBean) bean);
        } else if (bean instanceof DataProviderSchemeBean) {
            addDataProviderScheme((DataProviderSchemeBean) bean);
        } else if (bean instanceof CategorySchemeBean) {
            addCategoryScheme((CategorySchemeBean) bean);
        } else if (bean instanceof CodelistBean) {
            addCodelist((CodelistBean) bean);
        } else if (bean instanceof ConceptSchemeBean) {
            addConceptScheme((ConceptSchemeBean) bean);
        } else if (bean instanceof DataflowBean) {
            addDataflow((DataflowBean) bean);
        } else if (bean instanceof HierarchicalCodelistBean) {
            addHierarchicalCodelist((HierarchicalCodelistBean) bean);
        } else if (bean instanceof DataStructureBean) {
            addDataStructure((DataStructureBean) bean);
        } else if (bean instanceof MetadataFlowBean) {
            addMetadataFlow((MetadataFlowBean) bean);
        } else if (bean instanceof MetadataStructureDefinitionBean) {
            addMetadataStructure((MetadataStructureDefinitionBean) bean);
        } else if (bean instanceof OrganisationUnitSchemeBean) {
            addOrganisationUnitScheme((OrganisationUnitSchemeBean) bean);
        } else if (bean instanceof StructureSetBean) {
            addStructureSet((StructureSetBean) bean);
        } else if (bean instanceof ProcessBean) {
            addProcess((ProcessBean) bean);
        } else if (bean instanceof ReportingTaxonomyBean) {
            addReportingTaxonomy((ReportingTaxonomyBean) bean);
        } else if (bean instanceof CategorisationBean) {
            addCategorisation((CategorisationBean) bean);
        } else if (bean instanceof ProvisionAgreementBean) {
            addProvisionAgreement((ProvisionAgreementBean) bean);
        } else if (bean instanceof RegistrationBean) {
            addRegistration((RegistrationBean) bean);
        } else if (bean instanceof SubscriptionBean) {
            addSubscription((SubscriptionBean) bean);
        } else if (bean.getIdentifiableParent() != null) {
            addIdentifiable(bean.getIdentifiableParent());
        } else {
            throw new IllegalArgumentException("Could not add bean " + bean.getUrn() + " to SdmxBeans Container");
        }
    }

    @Override
    public SdmxBeansInfo getSdmxBeansInfo() {
        SdmxBeansInfoImpl info = new SdmxBeansInfoImpl();
        List<AgencyMetadata> agencyMetadataList = new ArrayList<AgencyMetadata>();
        Set<String> allAgencies = new HashSet<String>();
        for (MaintainableBean currentMaint : getAllMaintainables()) {
            allAgencies.add(currentMaint.getAgencyId());
        }
        for (String currentAgencyId : allAgencies) {
            AgencyMetadata agencyMetadata = new AgencyMetadataImpl(currentAgencyId, this);
            agencyMetadataList.add(agencyMetadata);
        }
        info.setAgencyMetadata(agencyMetadataList);
        return info;
    }


    /**
     * Gets maintainables.
     *
     * @param agency the agency
     * @return the maintainables
     */
    public Set<MaintainableBean> getMaintainables(AgencyBean agency) {
        Set<MaintainableBean> returnSet = new TreeSet<MaintainableBean>(new MaintainableSortByIdentifiers());
        addAgencyMaintainedStructuresToSet(returnSet, agency, agencySchemes);
        addAgencyMaintainedStructuresToSet(returnSet, agency, dataProviderSchemes);
        addAgencyMaintainedStructuresToSet(returnSet, agency, dataConsumerSchemes);
        addAgencyMaintainedStructuresToSet(returnSet, agency, categorySchemes);
        addAgencyMaintainedStructuresToSet(returnSet, agency, codelists);
        addAgencyMaintainedStructuresToSet(returnSet, agency, conceptSchemes);
        addAgencyMaintainedStructuresToSet(returnSet, agency, dataflows);
        addAgencyMaintainedStructuresToSet(returnSet, agency, hcls);
        addAgencyMaintainedStructuresToSet(returnSet, agency, dataStructures);
        addAgencyMaintainedStructuresToSet(returnSet, agency, metadataflows);
        addAgencyMaintainedStructuresToSet(returnSet, agency, metadataStructures);
        addAgencyMaintainedStructuresToSet(returnSet, agency, organisationUnitSchemes);
        addAgencyMaintainedStructuresToSet(returnSet, agency, processes);
        addAgencyMaintainedStructuresToSet(returnSet, agency, structureSet);
        addAgencyMaintainedStructuresToSet(returnSet, agency, reportingTaxonomy);
        addAgencyMaintainedStructuresToSet(returnSet, agency, categorisation);
        addAgencyMaintainedStructuresToSet(returnSet, agency, attachmentConstraints);
        addAgencyMaintainedStructuresToSet(returnSet, agency, contentConstraints);
        addAgencyMaintainedStructuresToSet(returnSet, agency, registrations);
        addAgencyMaintainedStructuresToSet(returnSet, agency, subscriptions);
        return returnSet;
    }

    private void addAgencyMaintainedStructuresToSet(Set<MaintainableBean> toAdd, AgencyBean agency, Set<? extends MaintainableBean> walkSet) {
        String agencyId = agency.getId();
        for (MaintainableBean currentMaint : walkSet) {
            if (currentMaint.getAgencyId().equals(agencyId)) {
                toAdd.add(currentMaint);
            }
        }
    }

    /**
     * Gets all maintainables.
     *
     * @return the all maintainables
     */
    public Set<MaintainableBean> getAllMaintainables() {
        Set<MaintainableBean> returnSet = new TreeSet<MaintainableBean>(new MaintainableSortByIdentifiers());
        for (SDMX_STRUCTURE_TYPE currentType : SDMX_STRUCTURE_TYPE.values()) {
            if (currentType.isMaintainable()) {
                returnSet.addAll(getMaintainables(currentType));
            }
        }
        return returnSet;
    }

    //TEST getAllMaintainables(SDMX_STRUCTURE_TYPE... exclude)
    @Override
    public Set<MaintainableBean> getAllMaintainables(SDMX_STRUCTURE_TYPE... exclude) {
        Set<MaintainableBean> returnSet = new TreeSet<MaintainableBean>(new MaintainableSortByIdentifiers());
        outer:
        for (SDMX_STRUCTURE_TYPE currentType : SDMX_STRUCTURE_TYPE.getMaintainableStructureTypes()) {
            if (exclude != null) {
                for (SDMX_STRUCTURE_TYPE currentExclude : exclude) {
                    if (currentExclude == currentType) {
                        continue outer;
                    }
                }
            }
            if (currentType.isMaintainable() && currentType != SDMX_STRUCTURE_TYPE.METADATA_SET) {
                returnSet.addAll(getMaintainables(currentType));
            }
        }
        return returnSet;
    }

    @Override
    public Set<MaintainableBean> getMaintainables(SDMX_STRUCTURE_TYPE structureType) {
        Set<MaintainableBean> returnSet = new TreeSet<MaintainableBean>(new MaintainableSortByIdentifiers());
        if (structureType == SDMX_STRUCTURE_TYPE.AGENCY_SCHEME) {
            returnSet.addAll(agencySchemes);
            return returnSet;
        }
        if (structureType == SDMX_STRUCTURE_TYPE.DATA_PROVIDER_SCHEME) {
            returnSet.addAll(dataProviderSchemes);
            return returnSet;
        }
        if (structureType == SDMX_STRUCTURE_TYPE.DATA_CONSUMER_SCHEME) {
            returnSet.addAll(dataConsumerSchemes);
            return returnSet;
        }
        if (structureType == SDMX_STRUCTURE_TYPE.CATEGORY_SCHEME) {
            returnSet.addAll(categorySchemes);
            return returnSet;
        }
        if (structureType == SDMX_STRUCTURE_TYPE.CODE_LIST) {
            returnSet.addAll(codelists);
            return returnSet;
        }
        if (structureType == SDMX_STRUCTURE_TYPE.CONCEPT_SCHEME) {
            returnSet.addAll(conceptSchemes);
            return returnSet;
        }
        if (structureType == SDMX_STRUCTURE_TYPE.DATAFLOW) {
            returnSet.addAll(dataflows);
            return returnSet;
        }
        if (structureType == SDMX_STRUCTURE_TYPE.HIERARCHICAL_CODELIST) {
            returnSet.addAll(hcls);
            return returnSet;
        }
        if (structureType == SDMX_STRUCTURE_TYPE.DSD) {
            returnSet.addAll(dataStructures);
            return returnSet;
        }
        if (structureType == SDMX_STRUCTURE_TYPE.METADATA_FLOW) {
            returnSet.addAll(metadataflows);
            return returnSet;
        }
        if (structureType == SDMX_STRUCTURE_TYPE.MSD) {
            returnSet.addAll(metadataStructures);
            return returnSet;
        }
        if (structureType == SDMX_STRUCTURE_TYPE.ORGANISATION_UNIT_SCHEME) {
            returnSet.addAll(organisationUnitSchemes);
            return returnSet;
        }
        if (structureType == SDMX_STRUCTURE_TYPE.PROCESS) {
            returnSet.addAll(processes);
            return returnSet;
        }
        if (structureType == SDMX_STRUCTURE_TYPE.STRUCTURE_SET) {
            returnSet.addAll(structureSet);
            return returnSet;
        }
        if (structureType == SDMX_STRUCTURE_TYPE.REPORTING_TAXONOMY) {
            returnSet.addAll(reportingTaxonomy);
            return returnSet;
        }
        if (structureType == SDMX_STRUCTURE_TYPE.CATEGORISATION) {
            returnSet.addAll(categorisation);
            return returnSet;
        }
        if (structureType == SDMX_STRUCTURE_TYPE.PROVISION_AGREEMENT) {
            returnSet.addAll(provisionAgreement);
            return returnSet;
        }
        if (structureType == SDMX_STRUCTURE_TYPE.ATTACHMENT_CONSTRAINT) {
            returnSet.addAll(attachmentConstraints);
            return returnSet;
        }
        if (structureType == SDMX_STRUCTURE_TYPE.CONTENT_CONSTRAINT) {
            returnSet.addAll(contentConstraints);
            return returnSet;
        }
        if (structureType == SDMX_STRUCTURE_TYPE.REGISTRATION) {
            returnSet.addAll(registrations);
            return returnSet;
        }
        if (structureType == SDMX_STRUCTURE_TYPE.SUBSCRIPTION) {
            returnSet.addAll(subscriptions);
            return returnSet;
        }
        throw new SdmxNotImplementedException(ExceptionCode.REFERENCE_ERROR_UNSUPPORTED_QUERY_FOR_STRUCTURE, structureType);
    }


    @Override
    public void addAgencyScheme(AgencySchemeBean bean) {
        if (bean != null) {
            this.agencySchemes.remove(bean);
            this.agencySchemes.add(bean);
        }
    }


    @Override
    public void addAttachmentConstraint(AttachmentConstraintBean bean) {
        if (bean != null) {
            this.attachmentConstraints.remove(bean);
            this.attachmentConstraints.add(bean);
        }
    }

    @Override
    public void addContentConstraintBean(ContentConstraintBean bean) {
        if (bean != null) {
            this.contentConstraints.remove(bean);
            this.contentConstraints.add(bean);
        }
    }

    @Override
    public void addDataProviderScheme(DataProviderSchemeBean bean) {
        if (bean != null) {
            this.dataProviderSchemes.remove(bean);
            this.dataProviderSchemes.add(bean);
        }
    }

    @Override
    public void addDataConsumerScheme(DataConsumerSchemeBean bean) {
        if (bean != null) {
            this.dataConsumerSchemes.remove(bean);
            this.dataConsumerSchemes.add(bean);
        }
    }

    @Override
    public void addOrganisationUnitScheme(OrganisationUnitSchemeBean bean) {
        if (bean != null) {
            this.organisationUnitSchemes.remove(bean);
            this.organisationUnitSchemes.add(bean);
        }
    }

    @Override
    public void addCategorisation(CategorisationBean bean) {
        if (bean != null) {
            this.categorisation.remove(bean);
            this.categorisation.add(bean);
        }
    }

    @Override
    public void addCategoryScheme(CategorySchemeBean bean) {
        if (bean != null) {
            this.categorySchemes.remove(bean);
            this.categorySchemes.add(bean);
        }
    }

    @Override
    public void addCodelist(CodelistBean bean) {
        if (bean != null) {
            this.codelists.remove(bean);
            this.codelists.add(bean);
        }
    }

    @Override
    public void addConceptScheme(ConceptSchemeBean bean) {
        if (bean != null) {
            this.conceptSchemes.remove(bean);
            this.conceptSchemes.add(bean);
        }
    }


    @Override
    public void addDataflow(DataflowBean bean) {
        if (bean != null) {
            this.dataflows.remove(bean);
            this.dataflows.add(bean);
        }
    }

    @Override
    public void addHierarchicalCodelist(HierarchicalCodelistBean bean) {
        if (bean != null) {
            this.hcls.remove(bean);
            this.hcls.add(bean);
        }
    }

    @Override
    public void addDataStructure(DataStructureBean bean) {
        if (bean != null) {
            this.dataStructures.remove(bean);
            this.dataStructures.add(bean);
        }
    }

    @Override
    public void addMetadataFlow(MetadataFlowBean bean) {
        if (bean != null) {
            this.metadataflows.remove(bean);
            this.metadataflows.add(bean);
        }
    }

    @Override
    public void addMetadataStructure(MetadataStructureDefinitionBean bean) {
        if (bean != null) {
            this.metadataStructures.remove(bean);
            this.metadataStructures.add(bean);
        }
    }

    @Override
    public void addProcess(ProcessBean bean) {
        if (bean != null) {
            this.processes.remove(bean);
            this.processes.add(bean);
        }
    }

    @Override
    public void addReportingTaxonomy(ReportingTaxonomyBean bean) {
        if (bean != null) {
            this.reportingTaxonomy.remove(bean);
            this.reportingTaxonomy.add(bean);
        }
    }

    @Override
    public void addStructureSet(StructureSetBean bean) {
        if (bean != null) {
            this.structureSet.remove(bean);
            this.structureSet.add(bean);
        }
    }

    @Override
    public void addProvisionAgreement(ProvisionAgreementBean bean) {
        if (bean != null) {
            this.provisionAgreement.remove(bean);
            this.provisionAgreement.add(bean);
        }
    }

    @Override
    public void addRegistration(RegistrationBean registration) {
        if (registration != null) {
            this.registrations.remove(registration);
            this.registrations.add(registration);
        }
    }

    @Override
    public void addSubscription(SubscriptionBean subscription) {
        if (subscription != null) {
            this.subscriptions.remove(subscription);
            this.subscriptions.add(subscription);
        }
    }

    @Override
    public Set<AgencyBean> getAgencies() {
        Set<AgencyBean> agencySet = new HashSet<AgencyBean>();
        for (AgencySchemeBean currentAgencyScheme : agencySchemes) {
            agencySet.addAll(currentAgencyScheme.getItems());
        }
        return agencySet;
    }


    @Override
    public boolean hasAttachmentConstraints() {
        return ObjectUtil.validCollection(attachmentConstraints);
    }

    @Override
    public boolean hasAgenciesSchemes() {
        return ObjectUtil.validCollection(agencySchemes);
    }

    @Override
    public boolean hasContentConstraintBeans() {
        return ObjectUtil.validCollection(contentConstraints);
    }

    @Override
    public boolean hasOrganisationUnitSchemes() {
        return ObjectUtil.validCollection(organisationUnitSchemes);
    }

    @Override
    public boolean hasDataConsumerSchemes() {
        return ObjectUtil.validCollection(dataConsumerSchemes);
    }

    @Override
    public boolean hasDataflows() {
        return ObjectUtil.validCollection(dataflows);
    }

    @Override
    public boolean hasDataProviderSchemes() {
        return ObjectUtil.validCollection(dataProviderSchemes);
    }

    @Override
    public boolean hasMetadataflows() {
        return ObjectUtil.validCollection(metadataflows);
    }

    @Override
    public boolean hasCategorySchemes() {
        return ObjectUtil.validCollection(categorySchemes);
    }

    @Override
    public boolean hasCodelists() {
        return ObjectUtil.validCollection(codelists);
    }

    @Override
    public boolean hasHierarchicalCodelists() {
        return ObjectUtil.validCollection(hcls);
    }

    @Override
    public boolean hasCategorisations() {
        return ObjectUtil.validCollection(categorisation);
    }

    @Override
    public boolean hasConceptSchemes() {
        return ObjectUtil.validCollection(conceptSchemes);
    }

    @Override
    public boolean hasMetadataStructures() {
        return ObjectUtil.validCollection(metadataStructures);
    }

    @Override
    public boolean hasDataStructures() {
        return ObjectUtil.validCollection(dataStructures);
    }

    @Override
    public boolean hasReportingTaxonomys() {
        return ObjectUtil.validCollection(reportingTaxonomy);
    }

    @Override
    public boolean hasStructureSets() {
        return ObjectUtil.validCollection(structureSet);
    }

    @Override
    public boolean hasProcesses() {
        return ObjectUtil.validCollection(processes);
    }

    @Override
    public boolean hasProvisionAgreements() {
        return ObjectUtil.validCollection(provisionAgreement);
    }

    @Override
    public Set<AttachmentConstraintBean> getAttachmentConstraints() {
        return new RetrunSetCreator<AttachmentConstraintBean>().createReturnSet(attachmentConstraints);
    }

    @Override
    public Set<ContentConstraintBean> getContentConstraintBeans() {
        return new RetrunSetCreator<ContentConstraintBean>().createReturnSet(contentConstraints);
    }

    @Override
    public Set<AgencySchemeBean> getAgenciesSchemes() {
        return new RetrunSetCreator<AgencySchemeBean>().createReturnSet(agencySchemes);
    }

    @Override
    public Set<OrganisationUnitSchemeBean> getOrganisationUnitSchemes() {
        return new RetrunSetCreator<OrganisationUnitSchemeBean>().createReturnSet(organisationUnitSchemes);
    }

    @Override
    public Set<DataConsumerSchemeBean> getDataConsumerSchemes() {
        return new RetrunSetCreator<DataConsumerSchemeBean>().createReturnSet(dataConsumerSchemes);
    }

    @Override
    public Set<DataProviderSchemeBean> getDataProviderSchemes() {
        return new RetrunSetCreator<DataProviderSchemeBean>().createReturnSet(dataProviderSchemes);
    }

    @Override
    public Set<CategorisationBean> getCategorisations() {
        return new RetrunSetCreator<CategorisationBean>().createReturnSet(categorisation);
    }

    @Override
    public Set<CategorySchemeBean> getCategorySchemes() {
        return new RetrunSetCreator<CategorySchemeBean>().createReturnSet(categorySchemes);
    }

    @Override
    public Set<CodelistBean> getCodelists() {
        return new RetrunSetCreator<CodelistBean>().createReturnSet(codelists);
    }

    @Override
    public Set<ConceptSchemeBean> getConceptSchemes() {
        return new RetrunSetCreator<ConceptSchemeBean>().createReturnSet(conceptSchemes);
    }

    @Override
    public Set<DataflowBean> getDataflows() {
        return new RetrunSetCreator<DataflowBean>().createReturnSet(dataflows);
    }

    @Override
    public Set<HierarchicalCodelistBean> getHierarchicalCodelists() {
        return new RetrunSetCreator<HierarchicalCodelistBean>().createReturnSet(hcls);
    }

    @Override
    public Set<DataStructureBean> getDataStructures() {
        return new RetrunSetCreator<DataStructureBean>().createReturnSet(dataStructures);
    }

    @Override
    public Set<MetadataFlowBean> getMetadataflows() {
        return new RetrunSetCreator<MetadataFlowBean>().createReturnSet(metadataflows);
    }

    @Override
    public Set<MetadataStructureDefinitionBean> getMetadataStructures() {
        return new RetrunSetCreator<MetadataStructureDefinitionBean>().createReturnSet(metadataStructures);
    }

    @Override
    public Set<ProcessBean> getProcesses() {
        return new RetrunSetCreator<ProcessBean>().createReturnSet(processes);
    }

    @Override
    public Set<ReportingTaxonomyBean> getReportingTaxonomys() {
        return new RetrunSetCreator<ReportingTaxonomyBean>().createReturnSet(reportingTaxonomy);
    }

    @Override
    public Set<StructureSetBean> getStructureSets() {
        return new RetrunSetCreator<StructureSetBean>().createReturnSet(structureSet);
    }

    @Override
    public Set<ProvisionAgreementBean> getProvisionAgreements() {
        return new RetrunSetCreator<ProvisionAgreementBean>().createReturnSet(provisionAgreement);
    }

    @Override
    public Set<RegistrationBean> getRegistrations() {
        return new RetrunSetCreator<RegistrationBean>().createReturnSet(registrations);
    }

    @Override
    public Set<SubscriptionBean> getSubscriptions() {
        return new RetrunSetCreator<SubscriptionBean>().createReturnSet(subscriptions);
    }

    //GET BY AGENCY
    @Override
    public Set<AttachmentConstraintBean> getAttachmentConstraints(String agencyId) {
        return new AgencyFilter<AttachmentConstraintBean>().filterSet(agencyId, attachmentConstraints);
    }

    @Override
    public AgencySchemeBean getAgencyScheme(String agencyId) {
        for (AgencySchemeBean acyScheme : agencySchemes) {
            if (acyScheme.getAgencyId().equals(agencyId)) {
                return acyScheme;
            }
        }
        return null;
    }

    @Override
    public Set<ContentConstraintBean> getContentConstraintBeans(String agencyId) {
        return new AgencyFilter<ContentConstraintBean>().filterSet(agencyId, contentConstraints);
    }

    @Override
    public Set<OrganisationUnitSchemeBean> getOrganisationUnitSchemes(String agencyId) {
        return new AgencyFilter<OrganisationUnitSchemeBean>().filterSet(agencyId, organisationUnitSchemes);
    }

    @Override
    public DataConsumerSchemeBean getDataConsumerScheme(String agencyId) {
        for (DataConsumerSchemeBean scheme : dataConsumerSchemes) {
            if (scheme.getAgencyId().equals(agencyId)) {
                return scheme;
            }
        }
        return null;
    }

    @Override
    public Set<DataflowBean> getDataflows(String agencyId) {
        return new AgencyFilter<DataflowBean>().filterSet(agencyId, dataflows);
    }

    @Override
    public DataProviderSchemeBean getDataProviderScheme(String agencyId) {
        for (DataProviderSchemeBean scheme : dataProviderSchemes) {
            if (scheme.getAgencyId().equals(agencyId)) {
                return scheme;
            }
        }
        return null;
    }

    @Override
    public Set<MetadataFlowBean> getMetadataflows(String agencyId) {
        return new AgencyFilter<MetadataFlowBean>().filterSet(agencyId, metadataflows);
    }

    @Override
    public Set<CategorySchemeBean> getCategorySchemes(String agencyId) {
        return new AgencyFilter<CategorySchemeBean>().filterSet(agencyId, categorySchemes);
    }

    @Override
    public Set<CodelistBean> getCodelists(String agencyId) {
        return new AgencyFilter<CodelistBean>().filterSet(agencyId, codelists);
    }

    @Override
    public Set<HierarchicalCodelistBean> getHierarchicalCodelists(String agencyId) {
        return new AgencyFilter<HierarchicalCodelistBean>().filterSet(agencyId, hcls);
    }

    @Override
    public Set<CategorisationBean> getCategorisations(String agencyId) {
        return new AgencyFilter<CategorisationBean>().filterSet(agencyId, categorisation);
    }

    @Override
    public Set<ConceptSchemeBean> getConceptSchemes(String agencyId) {
        return new AgencyFilter<ConceptSchemeBean>().filterSet(agencyId, conceptSchemes);
    }

    @Override
    public Set<MetadataStructureDefinitionBean> getMetadataStructures(String agencyId) {
        return new AgencyFilter<MetadataStructureDefinitionBean>().filterSet(agencyId, metadataStructures);
    }

    @Override
    public Set<DataStructureBean> getDataStructures(String agencyId) {
        return new AgencyFilter<DataStructureBean>().filterSet(agencyId, dataStructures);
    }

    @Override
    public Set<ReportingTaxonomyBean> getReportingTaxonomys(String agencyId) {
        return new AgencyFilter<ReportingTaxonomyBean>().filterSet(agencyId, reportingTaxonomy);
    }

    @Override
    public Set<StructureSetBean> getStructureSets(String agencyId) {
        return new AgencyFilter<StructureSetBean>().filterSet(agencyId, structureSet);
    }

    @Override
    public Set<ProcessBean> getProcesses(String agencyId) {
        return new AgencyFilter<ProcessBean>().filterSet(agencyId, processes);
    }

    @Override
    public Set<ProvisionAgreementBean> getProvisionAgreements(String agencyId) {
        return new AgencyFilter<ProvisionAgreementBean>().filterSet(agencyId, provisionAgreement);
    }

    @Override
    public Set<RegistrationBean> getRegistrations(String agencyId) {
        return new AgencyFilter<RegistrationBean>().filterSet(agencyId, registrations);
    }

    @Override
    public Set<SubscriptionBean> getSubscriptions(String agencyId) {
        return new AgencyFilter<SubscriptionBean>().filterSet(agencyId, subscriptions);
    }

    @Override
    public Set<AgencyBean> getAgencies(MaintainableRefBean ref) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<AttachmentConstraintBean> getAttachmentConstraints(MaintainableRefBean ref) {
        return new Filter<AttachmentConstraintBean>(SDMX_STRUCTURE_TYPE.ATTACHMENT_CONSTRAINT).filter(attachmentConstraints, ref);
    }

    @Override
    public Set<AgencySchemeBean> getAgenciesSchemes(MaintainableRefBean ref) {
        return new Filter<AgencySchemeBean>(SDMX_STRUCTURE_TYPE.AGENCY_SCHEME).filter(agencySchemes, ref);
    }

    @Override
    public Set<ContentConstraintBean> getContentConstraintBeans(MaintainableRefBean ref) {
        return new Filter<ContentConstraintBean>(SDMX_STRUCTURE_TYPE.CONTENT_CONSTRAINT).filter(contentConstraints, ref);
    }

    @Override
    public Set<OrganisationUnitSchemeBean> getOrganisationUnitSchemes(MaintainableRefBean ref) {
        return new Filter<OrganisationUnitSchemeBean>(SDMX_STRUCTURE_TYPE.ORGANISATION_UNIT_SCHEME).filter(organisationUnitSchemes, ref);
    }

    @Override
    public Set<DataConsumerSchemeBean> getDataConsumerSchemes(MaintainableRefBean ref) {
        return new Filter<DataConsumerSchemeBean>(SDMX_STRUCTURE_TYPE.DATA_CONSUMER_SCHEME).filter(dataConsumerSchemes, ref);
    }

    @Override
    public Set<DataflowBean> getDataflows(MaintainableRefBean ref) {
        return new Filter<DataflowBean>(SDMX_STRUCTURE_TYPE.DATAFLOW).filter(dataflows, ref);
    }

    @Override
    public Set<DataProviderSchemeBean> getDataProviderSchemes(MaintainableRefBean ref) {
        return new Filter<DataProviderSchemeBean>(SDMX_STRUCTURE_TYPE.DATA_PROVIDER_SCHEME).filter(dataProviderSchemes, ref);
    }

    @Override
    public Set<MetadataFlowBean> getMetadataflows(MaintainableRefBean ref) {
        return new Filter<MetadataFlowBean>(SDMX_STRUCTURE_TYPE.METADATA_FLOW).filter(metadataflows, ref);
    }

    @Override
    public Set<CategorySchemeBean> getCategorySchemes(MaintainableRefBean ref) {
        return new Filter<CategorySchemeBean>(SDMX_STRUCTURE_TYPE.CATEGORY_SCHEME).filter(categorySchemes, ref);
    }

    @Override
    public Set<CodelistBean> getCodelists(MaintainableRefBean ref) {
        return new Filter<CodelistBean>(SDMX_STRUCTURE_TYPE.CODE_LIST).filter(codelists, ref);
    }

    @Override
    public Set<HierarchicalCodelistBean> getHierarchicalCodelists(MaintainableRefBean ref) {
        return new Filter<HierarchicalCodelistBean>(SDMX_STRUCTURE_TYPE.HIERARCHICAL_CODELIST).filter(hcls, ref);
    }

    @Override
    public Set<CategorisationBean> getCategorisations(MaintainableRefBean ref) {
        return new Filter<CategorisationBean>(SDMX_STRUCTURE_TYPE.CATEGORISATION).filter(categorisation, ref);
    }

    @Override
    public Set<ConceptSchemeBean> getConceptSchemes(MaintainableRefBean ref) {
        return new Filter<ConceptSchemeBean>(SDMX_STRUCTURE_TYPE.CONCEPT_SCHEME).filter(conceptSchemes, ref);
    }

    @Override
    public Set<MetadataStructureDefinitionBean> getMetadataStructures(MaintainableRefBean ref) {
        return new Filter<MetadataStructureDefinitionBean>(SDMX_STRUCTURE_TYPE.MSD).filter(metadataStructures, ref);
    }

    @Override
    public Set<DataStructureBean> getDataStructures(MaintainableRefBean ref) {
        return new Filter<DataStructureBean>(SDMX_STRUCTURE_TYPE.DSD).filter(dataStructures, ref);
    }

    @Override
    public Set<ReportingTaxonomyBean> getReportingTaxonomys(MaintainableRefBean ref) {
        return new Filter<ReportingTaxonomyBean>(SDMX_STRUCTURE_TYPE.REPORTING_TAXONOMY).filter(reportingTaxonomy, ref);
    }

    @Override
    public Set<StructureSetBean> getStructureSets(MaintainableRefBean ref) {
        return new Filter<StructureSetBean>(SDMX_STRUCTURE_TYPE.STRUCTURE_SET).filter(structureSet, ref);
    }

    @Override
    public Set<ProcessBean> getProcesses(MaintainableRefBean ref) {
        return new Filter<ProcessBean>(SDMX_STRUCTURE_TYPE.PROCESS).filter(processes, ref);
    }

    @Override
    public Set<ProvisionAgreementBean> getProvisionAgreements(MaintainableRefBean ref) {
        return new Filter<ProvisionAgreementBean>(SDMX_STRUCTURE_TYPE.PROVISION_AGREEMENT).filter(provisionAgreement, ref);
    }

    @Override
    public Set<RegistrationBean> getRegistrations(MaintainableRefBean ref) {
        return new Filter<RegistrationBean>(SDMX_STRUCTURE_TYPE.REGISTRATION).filter(registrations, ref);
    }

    @Override
    public Set<SubscriptionBean> getSubscriptions(MaintainableRefBean ref) {
        return new Filter<SubscriptionBean>(SDMX_STRUCTURE_TYPE.SUBSCRIPTION).filter(subscriptions, ref);
    }

    @Override
    public void removeCategorisation(CategorisationBean bean) {
        this.categorisation.remove(bean);
    }

    @Override
    public void removeMaintainable(MaintainableBean bean) {
        if (bean instanceof AgencySchemeBean) {
            removeAgencyScheme((AgencySchemeBean) bean);
        } else if (bean instanceof AttachmentConstraintBean) {
            removeAttachmentConstraintBean((AttachmentConstraintBean) bean);
        } else if (bean instanceof CategorisationBean) {
            removeCategorisation((CategorisationBean) bean);
        } else if (bean instanceof ContentConstraintBean) {
            removeContentConstraintBean((ContentConstraintBean) bean);
        } else if (bean instanceof DataConsumerSchemeBean) {
            removeDataConsumerScheme((DataConsumerSchemeBean) bean);
        } else if (bean instanceof DataProviderSchemeBean) {
            removeDataProviderScheme((DataProviderSchemeBean) bean);
        } else if (bean instanceof CategorySchemeBean) {
            removeCategoryScheme((CategorySchemeBean) bean);
        } else if (bean instanceof CodelistBean) {
            removeCodelist((CodelistBean) bean);
        } else if (bean instanceof ConceptSchemeBean) {
            removeConceptScheme((ConceptSchemeBean) bean);
        } else if (bean instanceof DataflowBean) {
            removeDataflow((DataflowBean) bean);
        } else if (bean instanceof HierarchicalCodelistBean) {
            removeHierarchicalCodelist((HierarchicalCodelistBean) bean);
        } else if (bean instanceof DataStructureBean) {
            removeDataStructure((DataStructureBean) bean);
        } else if (bean instanceof MetadataFlowBean) {
            removeMetadataFlow((MetadataFlowBean) bean);
        } else if (bean instanceof MetadataStructureDefinitionBean) {
            removeMetadataStructure((MetadataStructureDefinitionBean) bean);
        } else if (bean instanceof OrganisationUnitSchemeBean) {
            removeOrganisationUnitScheme((OrganisationUnitSchemeBean) bean);
        } else if (bean instanceof StructureSetBean) {
            removeStructureSet((StructureSetBean) bean);
        } else if (bean instanceof ProcessBean) {
            removeProcess((ProcessBean) bean);
        } else if (bean instanceof ReportingTaxonomyBean) {
            removeReportingTaxonomy((ReportingTaxonomyBean) bean);
        } else if (bean instanceof CategorisationBean) {
            removeCategorisation((CategorisationBean) bean);
        } else if (bean instanceof ProvisionAgreementBean) {
            removeProvisionAgreement((ProvisionAgreementBean) bean);
        } else if (bean instanceof RegistrationBean) {
            removeRegistration((RegistrationBean) bean);
        } else if (bean instanceof SubscriptionBean) {
            removeSubscription((SubscriptionBean) bean);
        }
    }

    @Override
    public void removeAttachmentConstraintBean(AttachmentConstraintBean bean) {
        this.attachmentConstraints.remove(bean);
    }

    //REMOVE

    @Override
    public void removeContentConstraintBean(ContentConstraintBean bean) {
        this.contentConstraints.remove(bean);
    }

    @Override
    public void removeAgencyScheme(AgencySchemeBean bean) {
        this.agencySchemes.remove(bean);
    }

    @Override
    public void removeDataProviderScheme(DataProviderSchemeBean bean) {
        this.dataProviderSchemes.remove(bean);
    }

    @Override
    public void removeDataConsumerScheme(DataConsumerSchemeBean bean) {
        this.dataConsumerSchemes.remove(bean);
    }

    @Override
    public void removeOrganisationUnitScheme(OrganisationUnitSchemeBean bean) {
        this.organisationUnitSchemes.remove(bean);
    }

    @Override
    public void removeCategoryScheme(CategorySchemeBean bean) {
        this.categorySchemes.remove(bean);
    }

    @Override
    public void removeCodelist(CodelistBean bean) {
        this.codelists.remove(bean);
    }

    @Override
    public void removeConceptScheme(ConceptSchemeBean bean) {
        this.conceptSchemes.remove(bean);
    }

    @Override
    public void removeDataflow(DataflowBean bean) {
        this.dataflows.remove(bean);
    }

    @Override
    public void removeHierarchicalCodelist(HierarchicalCodelistBean bean) {
        this.hcls.remove(bean);
    }

    @Override
    public void removeDataStructure(DataStructureBean bean) {
        this.dataStructures.remove(bean);
    }

    @Override
    public void removeMetadataFlow(MetadataFlowBean bean) {
        this.metadataflows.remove(bean);
    }

    @Override
    public void removeMetadataStructure(MetadataStructureDefinitionBean bean) {
        this.metadataStructures.remove(bean);
    }

    @Override
    public void removeProcess(ProcessBean bean) {
        this.processes.remove(bean);
    }

    @Override
    public void removeReportingTaxonomy(ReportingTaxonomyBean bean) {
        this.reportingTaxonomy.remove(bean);
    }

    @Override
    public void removeStructureSet(StructureSetBean bean) {
        this.structureSet.remove(bean);
    }

    @Override
    public void removeProvisionAgreement(ProvisionAgreementBean bean) {
        this.provisionAgreement.remove(bean);
    }

    @Override
    public void removeRegistration(RegistrationBean registration) {
        this.registrations.remove(registration);
    }

    @Override
    public void removeSubscription(SubscriptionBean subscription) {
        this.subscriptions.remove(subscription);
    }

    private class RetrunSetCreator<T extends MaintainableBean> {
        private Set<T> createReturnSet(Set<T> immutableSet) {
            Set<T> returnSet = new TreeSet<T>(new MaintainableSortByIdentifiers());
            returnSet.addAll(immutableSet);
            return returnSet;
        }
    }

    private class Filter<T extends MaintainableBean> {
        private SDMX_STRUCTURE_TYPE structureType;

        /**
         * Instantiates a new Filter.
         *
         * @param structureType the structure type
         */
        public Filter(SDMX_STRUCTURE_TYPE structureType) {
            this.structureType = structureType;
        }

        /**
         * Filter set.
         *
         * @param input the input
         * @param ref   the ref
         * @return the set
         */
        public Set<T> filter(Set<T> input, MaintainableRefBean ref) {
            Set<T> returnSet = new TreeSet<T>(new MaintainableSortByIdentifiers());
            if (ref == null) {
                returnSet.addAll(input);
                return returnSet;
            }
            StructureReferenceBean sRef = new StructureReferenceBeanImpl(ref, structureType);

            for (T currentInput : input) {
                if (sRef.isMatch(currentInput)) {
                    returnSet.add(currentInput);
                }
            }
            return returnSet;
        }
    }

    private class AgencyFilter<T extends MaintainableBean> {
        private Set<T> filterSet(String agencyId, Set<T> walkSet) {
            Set<T> returnSet = new TreeSet<T>(new MaintainableSortByIdentifiers());
            for (T currentMaint : walkSet) {
                if (currentMaint.getAgencyId().equals(agencyId)) {
                    returnSet.add(currentMaint);
                }
            }
            return returnSet;
        }
    }
}
