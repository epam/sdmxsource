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
package org.sdmxsource.sdmx.api.model.mutable;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
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
import java.util.Set;


/**
 * MutableBeans is a container for MaintainableMutableBeans
 *
 * @author Matt Nelson
 */
public interface MutableBeans {
    //ADD

    /**
     * Adds an identifiable to the SdmxBeans container.  If the bean is a maintainable
     * then it will be added directly into the container.  If the bean is an identifiable that
     * has a maintainable ancestor, then the maintainable ancestor will be added to the container,
     * and will be retrievable through the respective getter method.
     *
     * @param bean the bean
     */
    void addIdentifiables(Collection<? extends IdentifiableMutableBean> bean);

    /**
     * Add identifiable.
     *
     * @param bean the bean
     */
    void addIdentifiable(IdentifiableMutableBean bean);

    /**
     * Add agency scheme.
     *
     * @param bean the bean
     */
    void addAgencyScheme(AgencySchemeMutableBean bean);

    // FUNC - implement AttachmentConstraintMutableBean
    // void addAttachmentConstraint(AttachmentConstraintMutableBean bean);

    /**
     * Add categorisation.
     *
     * @param bean the bean
     */
    void addCategorisation(CategorisationMutableBean bean);

    /**
     * Add category scheme.
     *
     * @param bean the bean
     */
    void addCategoryScheme(CategorySchemeMutableBean bean);

    /**
     * Add codelist.
     *
     * @param bean the bean
     */
    void addCodelist(CodelistMutableBean bean);

    /**
     * Add concept scheme.
     *
     * @param bean the bean
     */
    void addConceptScheme(ConceptSchemeMutableBean bean);

    /**
     * Add content constraint.
     *
     * @param bean the bean
     */
    void addContentConstraint(ContentConstraintMutableBean bean);

    /**
     * Add data consumer scheme.
     *
     * @param bean the bean
     */
    void addDataConsumerScheme(DataConsumerSchemeMutableBean bean);

    /**
     * Add dataflow.
     *
     * @param bean the bean
     */
    void addDataflow(DataflowMutableBean bean);

    /**
     * Add data provider scheme.
     *
     * @param bean the bean
     */
    void addDataProviderScheme(DataProviderSchemeMutableBean bean);

    /**
     * Add hierarchical codelist.
     *
     * @param bean the bean
     */
    void addHierarchicalCodelist(HierarchicalCodelistMutableBean bean);

    /**
     * Add data structure.
     *
     * @param bean the bean
     */
    void addDataStructure(DataStructureMutableBean bean);

    /**
     * Add metadata flow.
     *
     * @param bean the bean
     */
    void addMetadataFlow(MetadataFlowMutableBean bean);

    /**
     * Add metadata structure.
     *
     * @param bean the bean
     */
    void addMetadataStructure(MetadataStructureDefinitionMutableBean bean);

    /**
     * Add organisation unit scheme.
     *
     * @param bean the bean
     */
    void addOrganisationUnitScheme(OrganisationUnitSchemeMutableBean bean);

    /**
     * Add reporting taxonomy.
     *
     * @param bean the bean
     */
    void addReportingTaxonomy(ReportingTaxonomyMutableBean bean);

    /**
     * Add structure set.
     *
     * @param bean the bean
     */
    void addStructureSet(StructureSetMutableBean bean);

    /**
     * Add process.
     *
     * @param bean the bean
     */
    void addProcess(ProcessMutableBean bean);

    /**
     * Add provision.
     *
     * @param bean the bean
     */
    void addProvision(ProvisionAgreementMutableBean bean);

    /**
     * Add registration.
     *
     * @param bean the bean
     */
    void addRegistration(RegistrationMutableBean bean);

    /**
     * Add subscription.
     *
     * @param bean the bean
     */
    void addSubscription(SubscriptionMutableBean bean);

    //REMOVE

    /**
     * Remove agency scheme mutable beans.
     *
     * @param bean the bean
     */
    void removeAgencySchemeMutableBeans(AgencySchemeMutableBean bean);

    // FUNC - implement AttachmentConstraintMutableBean
    // void removeAttachmentConstraint(AttachmentConstraintMutableBean bean);

    /**
     * Remove data consumber scheme mutable beans.
     *
     * @param bean the bean
     */
    void removeDataConsumberSchemeMutableBeans(DataConsumerSchemeMutableBean bean);

    /**
     * Remove data provider scheme mutable beans.
     *
     * @param bean the bean
     */
    void removeDataProviderSchemeMutableBeans(DataProviderSchemeMutableBean bean);

    /**
     * Remove categorisation.
     *
     * @param bean the bean
     */
    void removeCategorisation(CategorisationMutableBean bean);

    /**
     * Remove category scheme.
     *
     * @param bean the bean
     */
    void removeCategoryScheme(CategorySchemeMutableBean bean);

    /**
     * Remove codelist.
     *
     * @param bean the bean
     */
    void removeCodelist(CodelistMutableBean bean);

    /**
     * Remove concept scheme.
     *
     * @param bean the bean
     */
    void removeConceptScheme(ConceptSchemeMutableBean bean);

    /**
     * Remove content constraint.
     *
     * @param bean the bean
     */
    void removeContentConstraint(ContentConstraintMutableBean bean);

    /**
     * Remove dataflow.
     *
     * @param bean the bean
     */
    void removeDataflow(DataflowMutableBean bean);

    /**
     * Remove hierarchical codelist.
     *
     * @param bean the bean
     */
    void removeHierarchicalCodelist(HierarchicalCodelistMutableBean bean);

    /**
     * Remove data structure.
     *
     * @param bean the bean
     */
    void removeDataStructure(DataStructureMutableBean bean);

    /**
     * Remove metadata flow.
     *
     * @param bean the bean
     */
    void removeMetadataFlow(MetadataFlowMutableBean bean);

    /**
     * Remove metadata structure.
     *
     * @param bean the bean
     */
    void removeMetadataStructure(MetadataStructureDefinitionMutableBean bean);

    /**
     * Remove organisation unit scheme.
     *
     * @param bean the bean
     */
    void removeOrganisationUnitScheme(OrganisationUnitSchemeMutableBean bean);

    /**
     * Remove reporting taxonomy.
     *
     * @param bean the bean
     */
    void removeReportingTaxonomy(ReportingTaxonomyMutableBean bean);

    /**
     * Remove structure set.
     *
     * @param bean the bean
     */
    void removeStructureSet(StructureSetMutableBean bean);

    /**
     * Remove process.
     *
     * @param bean the bean
     */
    void removeProcess(ProcessMutableBean bean);

    /**
     * Remove provision.
     *
     * @param bean the bean
     */
    void removeProvision(ProvisionAgreementMutableBean bean);

    /**
     * Remove registration.
     *
     * @param bean the bean
     */
    void removeRegistration(RegistrationMutableBean bean);

    /**
     * Remove subscription.
     *
     * @param bean the bean
     */
    void removeSubscription(SubscriptionMutableBean bean);

    /**
     * Gets categorisations.
     *
     * @return the categorisations
     */
//GET
    Set<CategorisationMutableBean> getCategorisations();

    /**
     * Gets agency scheme mutable beans.
     *
     * @return the agency scheme mutable beans
     */
    Set<AgencySchemeMutableBean> getAgencySchemeMutableBeans();

    // FUNC - implement AttachmentConstraintMutableBean
    // Set<AttachmentConstraintMutableBean> getAttachmentConstraintMutableBeans();

    /**
     * Gets data consumber scheme mutable beans.
     *
     * @return the data consumber scheme mutable beans
     */
    Set<DataConsumerSchemeMutableBean> getDataConsumberSchemeMutableBeans();

    /**
     * Gets data provider scheme mutable beans.
     *
     * @return the data provider scheme mutable beans
     */
    Set<DataProviderSchemeMutableBean> getDataProviderSchemeMutableBeans();

    /**
     * Gets organisation unit schemes.
     *
     * @return the organisation unit schemes
     */
    Set<OrganisationUnitSchemeMutableBean> getOrganisationUnitSchemes();

    /**
     * Gets dataflows.
     *
     * @return the dataflows
     */
    Set<DataflowMutableBean> getDataflows();

    /**
     * Gets metadataflows.
     *
     * @return the metadataflows
     */
    Set<MetadataFlowMutableBean> getMetadataflows();

    /**
     * Gets category schemes.
     *
     * @return the category schemes
     */
    Set<CategorySchemeMutableBean> getCategorySchemes();

    /**
     * Gets codelists.
     *
     * @return the codelists
     */
    Set<CodelistMutableBean> getCodelists();

    /**
     * Gets content constraints.
     *
     * @return the content constraints
     */
    Set<ContentConstraintMutableBean> getContentConstraints();

    /**
     * Gets hierarchical codelists.
     *
     * @return the hierarchical codelists
     */
    Set<HierarchicalCodelistMutableBean> getHierarchicalCodelists();

    /**
     * Gets concept schemes.
     *
     * @return the concept schemes
     */
    Set<ConceptSchemeMutableBean> getConceptSchemes();

    /**
     * Gets metadata structures.
     *
     * @return the metadata structures
     */
    Set<MetadataStructureDefinitionMutableBean> getMetadataStructures();

    /**
     * Gets data structures.
     *
     * @return the data structures
     */
    Set<DataStructureMutableBean> getDataStructures();

    /**
     * Gets reporting taxonomys.
     *
     * @return the reporting taxonomys
     */
    Set<ReportingTaxonomyMutableBean> getReportingTaxonomys();

    /**
     * Gets structure sets.
     *
     * @return the structure sets
     */
    Set<StructureSetMutableBean> getStructureSets();

    /**
     * Gets processes.
     *
     * @return the processes
     */
    Set<ProcessMutableBean> getProcesses();

    /**
     * Gets provisions.
     *
     * @return the provisions
     */
    Set<ProvisionAgreementMutableBean> getProvisions();

    /**
     * Gets registrations.
     *
     * @return the registrations
     */
    Set<RegistrationMutableBean> getRegistrations();

    /**
     * Gets subscriptions.
     *
     * @return the subscriptions
     */
    Set<SubscriptionMutableBean> getSubscriptions();

    /**
     * Returns all the maintainables
     *
     * @return all maintainables
     */
    Set<MaintainableMutableBean> getAllMaintainables();

    /**
     * Returns all the maintainables of a given type
     *
     * @param structureType the structure type
     * @return maintainables
     */
    Set<MaintainableMutableBean> getMaintainables(SDMX_STRUCTURE_TYPE structureType);

    /**
     * Creates an SDMXBeans package containing the immutable beans instances of the mutable beans
     * contained within this package
     *
     * @return immutable beans
     */
    SdmxBeans getImmutableBeans();

}
