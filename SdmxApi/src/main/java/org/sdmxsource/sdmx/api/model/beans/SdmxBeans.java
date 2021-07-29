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
package org.sdmxsource.sdmx.api.model.beans;

import org.sdmxsource.sdmx.api.constants.DATASET_ACTION;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
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
import org.sdmxsource.sdmx.api.model.beans.registry.*;
import org.sdmxsource.sdmx.api.model.header.HeaderBean;
import org.sdmxsource.sdmx.api.model.mutable.MutableBeans;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;


/**
 * Container for Structure Sdmx Beans, contains methods to add, remove, merge, and retrieve beans.  Also contains the means to
 * retrieve a mutable instance of the same container (<code>getMutableBeans</code>)
 *
 * @author Matt Nelson
 */
public interface SdmxBeans extends Serializable {

    /**
     * Returns a new, read-only identifier for this beans container
     *
     * @return id id
     */
    String getId();

    /**
     * Returns the action for this container
     *
     * @return action action
     */
    DATASET_ACTION getAction();

    /**
     * Sets the action for this container
     *
     * @param action the action
     */
    void setAction(DATASET_ACTION action);

    /**
     * Returns true if this container contains any MaintainableBeans which are not registration or subscription beans.
     *
     * @return true if this container contains any MaintainableBeans which are not registration or subscription beans.
     */
    boolean hasStructures();

    /**
     * Returns true if this container contains SubscriptionBeans.
     *
     * @return true if this container contains SubscriptionBeans.
     */
    boolean hasSubscriptions();

    /**
     * Returns true if this container contains RegistrationBeans.
     *
     * @return true if this container contains RegistrationBeans.
     */
    boolean hasRegistrations();

    /**
     * Returns a new instance of SdmxBeansInfo containing information about what is stored in this beans container.
     *
     * @return sdmx beans info
     */
    SdmxBeansInfo getSdmxBeansInfo();

    /**
     * Adds all the Beans in the supplied beans to this set of beans,
     * any duplicates found in this set will be overwritten by the provided beans.  If the beans argument has a header this will overwrite
     * and header set on this beans.
     * <p>
     * The id of this SdmxBeans container will not be overwritten
     *
     * @param beans the beans
     */
    void merge(SdmxBeans beans);

    /**
     * Returns the header for this beans container. Returns null if there is no header associated with this container.
     *
     * @return the header for this beans container.
     */
    HeaderBean getHeader();

    /**
     * Sets the header on this set of beans
     *
     * @param header the header
     */
    void setHeader(HeaderBean header);

    //ADD

    /**
     * Adds many identifiables to the SdmxBeans container.
     * <p>
     * If the identifiable belongs to an already stored maintainable, then the stored maintainable will be overwritten
     *
     * @param beans the beans
     * @see IdentifiableBean
     */
    void addIdentifiables(Collection<? extends IdentifiableBean> beans);

    /**
     * Adds an identifiable to the SdmxBeans container.
     * If the bean is a maintainable then it will be added directly into the container.
     * If the bean is an identifiable that has a maintainable ancestor, then the maintainable ancestor will be added to the container,
     * and will be retrievable through the respective getter method.
     * <p>
     * If the identifiable belongs to an already stored maintainable, then the stored maintainable will be overwritten.
     *
     * @param bean the bean
     */
    void addIdentifiable(IdentifiableBean bean);

    /**
     * Adds an AgencySchemeBean to this container, if the agency already exists in this container then it will be overwritten.
     *
     * @param agencyScheme the agency scheme
     */
    void addAgencyScheme(AgencySchemeBean agencyScheme);

    /**
     * Adds a attachment constraint bean to this container, if the category scheme already exists in this container then it will be overwritten.
     *
     * @param bean - the bean to add
     */
    void addAttachmentConstraint(AttachmentConstraintBean bean);

    /**
     * Adds a category scheme bean to this container, if the category scheme already exists in this container then it will be overwritten.
     *
     * @param bean - the bean to add
     */
    void addCategoryScheme(CategorySchemeBean bean);

    /**
     * Adds a codelist bean to this container, if the codelist already exists in this container then it will be overwritten.
     *
     * @param bean - the bean to add
     */
    void addCodelist(CodelistBean bean);

    /**
     * Adds a concept scheme bean to this container, if the concept scheme already exists in this container then it will be overwritten.
     *
     * @param bean - the bean to add
     */
    void addConceptScheme(ConceptSchemeBean bean);

    /**
     * Adds a content constraint bean to this container, if the category scheme already exists in this container then it will be overwritten.
     *
     * @param bean - the bean to add
     */
    void addContentConstraintBean(ContentConstraintBean bean);

    /**
     * Adds a categorisation bean to this container, if the categorisation already exists in this container then it will be overwritten.
     *
     * @param bean - the bean to add
     */
    void addCategorisation(CategorisationBean bean);

    /**
     * Adds a data provider scheme to this container, if the data provider scheme already exists in this container then it will be overwritten.
     *
     * @param bean - the bean to add
     */
    void addDataProviderScheme(DataProviderSchemeBean bean);

    /**
     * Adds a dataflow to this container, if the dataflow already exists in this container then it will be overwritten.
     *
     * @param bean - the bean to add
     */
    void addDataflow(DataflowBean bean);

    /**
     * Adds a data consumer scheme to this container, if the data consumer scheme already exists in this container then it will be overwritten.
     *
     * @param bean - the bean to add
     */
    void addDataConsumerScheme(DataConsumerSchemeBean bean);

    /**
     * Adds a hierarchical codelist bean to this container, if the hierarchical codelist already exists in this container then it will be overwritten.
     *
     * @param bean - the bean to add
     */
    void addHierarchicalCodelist(HierarchicalCodelistBean bean);

    /**
     * Adds a Data Structure to this container.
     * If the Data Structure already exists in this container then it will be overwritten.
     *
     * @param bean - the bean to add
     */
    void addDataStructure(DataStructureBean bean);

    /**
     * Adds a metadataflow bean to this container, if the metadataflow already exists in this container then it will be overwritten.
     *
     * @param bean - the bean to add
     */
    void addMetadataFlow(MetadataFlowBean bean);

    /**
     * Adds a metadata structure bean to this container, if the metadata structure already exists in this container then it will be overwritten.
     *
     * @param bean - the bean to add
     */
    void addMetadataStructure(MetadataStructureDefinitionBean bean);

    /**
     * Adds a organisation unit scheme bean to this container, if the organisation unit scheme already exists in this container then it will be overwritten.
     *
     * @param bean - the bean to add
     */
    void addOrganisationUnitScheme(OrganisationUnitSchemeBean bean);

    /**
     * Adds a reporting taxonomy bean to this container, if the reporting taxonomy already exists in this container then it will be overwritten.
     *
     * @param bean - the bean to add
     */
    void addReportingTaxonomy(ReportingTaxonomyBean bean);


    /**
     * Adds a structure set bean to this container, if the structure set already exists in this container then it will be overwritten.
     *
     * @param bean - the bean to add
     */
    void addStructureSet(StructureSetBean bean);

    /**
     * Adds a process bean to this container, if the process bean already exists in this container then it will be overwritten.
     *
     * @param bean - the bean to add
     */
    void addProcess(ProcessBean bean);

    /**
     * Adds a provision agreement bean to this container, if the provision agreement bean already exists in this container then it will be overwritten.
     *
     * @param provisionAgreement the provision agreement
     */
    void addProvisionAgreement(ProvisionAgreementBean provisionAgreement);

    /**
     * Adds a registration bean to this container, if the registration bean already exists in this container then it will be overwritten.
     *
     * @param registration the registration
     */
    void addRegistration(RegistrationBean registration);

    /**
     * Adds a subscription bean to this container, if the subscription bean already exists in this container then it will be overwritten.
     *
     * @param subscription the subscription
     */
    void addSubscription(SubscriptionBean subscription);


    //REMOVE

    /**
     * Removes the maintainable from the container, if it exists
     *
     * @param bean the bean
     */
    void removeMaintainable(MaintainableBean bean);

    /**
     * Removes the agency scheme from the container, if it exists
     *
     * @param bean the bean
     */
    void removeAgencyScheme(AgencySchemeBean bean);

    /**
     * Removes the AttachmentConstraintBean from the container, if it exists
     *
     * @param bean the bean
     */
    void removeAttachmentConstraintBean(AttachmentConstraintBean bean);

    /**
     * Removes the category scheme from the container, if it exists
     *
     * @param bean the bean
     */
    void removeCategoryScheme(CategorySchemeBean bean);

    /**
     * Removes the categorisation from the container, if it exists
     *
     * @param bean the bean
     */
    void removeCategorisation(CategorisationBean bean);

    /**
     * Removes the codelist from the container, if it exists
     *
     * @param bean the bean
     */
    void removeCodelist(CodelistBean bean);

    /**
     * Removes the concept scheme from the container, if it exists
     *
     * @param bean the bean
     */
    void removeConceptScheme(ConceptSchemeBean bean);

    /**
     * Removes the ContentConstraintBean from the container, if it exists
     *
     * @param bean the bean
     */
    void removeContentConstraintBean(ContentConstraintBean bean);

    /**
     * Removes the data provider scheme from the container, if it exists
     *
     * @param bean the bean
     */
    void removeDataProviderScheme(DataProviderSchemeBean bean);

    /**
     * Removes the dataflow from the container, if it exists
     *
     * @param bean the bean
     */
    void removeDataflow(DataflowBean bean);

    /**
     * Removes the data consumer scheme from the container, if it exists
     *
     * @param bean the bean
     */
    void removeDataConsumerScheme(DataConsumerSchemeBean bean);

    /**
     * Removes the hierarchical codelist from the container, if it exists
     *
     * @param bean the bean
     */
    void removeHierarchicalCodelist(HierarchicalCodelistBean bean);

    /**
     * Removes the Data Structure from the container, if it exists
     *
     * @param bean the bean
     */
    void removeDataStructure(DataStructureBean bean);

    /**
     * Removes the metadataflow from the container, if it exists
     *
     * @param bean the bean
     */
    void removeMetadataFlow(MetadataFlowBean bean);

    /**
     * Removes the metadata structure definition from the container, if it exists
     *
     * @param bean the bean
     */
    void removeMetadataStructure(MetadataStructureDefinitionBean bean);

    /**
     * Removes the organisation unit scheme from the container, if it exists
     *
     * @param bean the bean
     */
    void removeOrganisationUnitScheme(OrganisationUnitSchemeBean bean);

    /**
     * Removes the reporting taxonomy from the container, if it exists
     *
     * @param bean the bean
     */
    void removeReportingTaxonomy(ReportingTaxonomyBean bean);

    /**
     * Removes the structure set from the container, if it exists
     *
     * @param bean the bean
     */
    void removeStructureSet(StructureSetBean bean);

    /**
     * Removes the process from the container, if it exists
     *
     * @param bean the bean
     */
    void removeProcess(ProcessBean bean);

    /**
     * Removes the provision agreement from the container, if it exists
     *
     * @param bean the bean
     */
    void removeProvisionAgreement(ProvisionAgreementBean bean);


    /**
     * Adds a registration bean to this container, if the registration bean already exists in this container then it will be overwritten.
     *
     * @param registration the registration
     */
    void removeRegistration(RegistrationBean registration);

    /**
     * Adds a subscription bean to this container, if the subscription bean already exists in this container then it will be overwritten.
     *
     * @param subscription the subscription
     */
    void removeSubscription(SubscriptionBean subscription);

    /*****************************************************************************************************************/
    /**
     * Returns true if there are attachment constraints in this container
     *
     * @return the boolean
     */
    boolean hasAttachmentConstraints();

    /**
     * Returns true if there are agency schemes in this container
     *
     * @return the boolean
     */
    boolean hasAgenciesSchemes();

    /**
     * Returns true if there are content constraints in this container
     *
     * @return the boolean
     */
    boolean hasContentConstraintBeans();

    /**
     * Returns true if there are organisation unit schemes in this container
     *
     * @return the boolean
     */
    boolean hasOrganisationUnitSchemes();

    /**
     * Returns true if there are data consumer schemes in this container
     *
     * @return the boolean
     */
    boolean hasDataConsumerSchemes();

    /**
     * Returns true if there are dataflows in this container
     *
     * @return the boolean
     */
    boolean hasDataflows();

    /**
     * Returns true if there are data provider schemes in this container
     *
     * @return the boolean
     */
    boolean hasDataProviderSchemes();

    /**
     * Returns true if there are metadataflows in this container
     *
     * @return the boolean
     */
    boolean hasMetadataflows();

    /**
     * Returns true if there are category schemes in this container
     *
     * @return the boolean
     */
    boolean hasCategorySchemes();

    /**
     * Returns true if there are codelists in this container
     *
     * @return the boolean
     */
    boolean hasCodelists();

    /**
     * Returns true if there are hierarchical codelists in this container
     *
     * @return the boolean
     */
    boolean hasHierarchicalCodelists();

    /**
     * Returns true if there are categorisations in this container
     *
     * @return the boolean
     */
    boolean hasCategorisations();

    /**
     * Returns true if there are concept schemes in this container
     *
     * @return the boolean
     */
    boolean hasConceptSchemes();

    /**
     * Returns true if there are metadata structures in this container
     *
     * @return the boolean
     */
    boolean hasMetadataStructures();

    /**
     * Returns true if there are data structures in this container
     *
     * @return the boolean
     */
    boolean hasDataStructures();

    /**
     * Returns true if there are reporting taxonomies in this container
     *
     * @return the boolean
     */
    boolean hasReportingTaxonomys();

    /**
     * Returns true if there are structure sets in this container
     *
     * @return the boolean
     */
    boolean hasStructureSets();

    /**
     * Returns true if there are processes in this container
     *
     * @return the boolean
     */
    boolean hasProcesses();

    /**
     * Returns true if there are provision agreements in this container
     *
     * @return the boolean
     */
    boolean hasProvisionAgreements();


    /*****************************************************************************************************************/
    /**
     * Returns all the agencies that exist in the beans collection
     *
     * @return the agencies
     */
    Set<AgencyBean> getAgencies();

    /**
     * Returns all the attachment constraints that exist in the beans collection
     *
     * @return the attachment constraints
     */
    Set<AttachmentConstraintBean> getAttachmentConstraints();

    /**
     * Returns all the agency schemes in this container, returns an empty set if no agencies exist
     *
     * @return the agencies schemes
     */
    Set<AgencySchemeBean> getAgenciesSchemes();

    /**
     * Returns all the content constraints that exist in the beans collection
     *
     * @return the content constraint beans
     */
    Set<ContentConstraintBean> getContentConstraintBeans();

    /**
     * Returns all the organisation unit schemes in this container, returns an empty set if no organisation schemes exist
     *
     * @return the organisation unit schemes
     */
    Set<OrganisationUnitSchemeBean> getOrganisationUnitSchemes();

    /**
     * Returns all the data consumers in this container, returns an empty set if no data consumers exist
     *
     * @return the data consumer schemes
     */
    Set<DataConsumerSchemeBean> getDataConsumerSchemes();

    /**
     * Returns all the dataflows in this container, returns an empty set if no dataflows exist
     *
     * @return the dataflows
     */
    Set<DataflowBean> getDataflows();

    /**
     * Returns all the data provider in this container, returns an empty set if no data providers exist
     *
     * @return the data provider schemes
     */
    Set<DataProviderSchemeBean> getDataProviderSchemes();

    /**
     * Returns all the metadataflows in this container, returns an empty set if no metadataflows exist
     *
     * @return the metadataflows
     */
    Set<MetadataFlowBean> getMetadataflows();

    /**
     * Returns all the category schemes in this container, returns an empty set if no category schemes exist
     *
     * @return the category schemes
     */
    Set<CategorySchemeBean> getCategorySchemes();

    /**
     * Returns all the codelists in this container, returns an empty set if no codelists exist
     *
     * @return the codelists
     */
    Set<CodelistBean> getCodelists();

    /**
     * Returns all the hierarchical codelists in this container, returns an empty set if no hierarchical codelists exist
     *
     * @return the hierarchical codelists
     */
    Set<HierarchicalCodelistBean> getHierarchicalCodelists();

    /**
     * Returns all the categorisations in this container, returns an empty set if no categorisations exist
     *
     * @return the categorisations
     */
    Set<CategorisationBean> getCategorisations();

    /**
     * Returns all the concept schemes in this container, returns an empty set if no concept schemes exist
     *
     * @return the concept schemes
     */
    Set<ConceptSchemeBean> getConceptSchemes();

    /**
     * Returns all the metadata structures in this container, returns an empty set if no metadata structures exist
     *
     * @return the metadata structures
     */
    Set<MetadataStructureDefinitionBean> getMetadataStructures();

    /**
     * Returns all the key families in this container, returns an empty set if no key families exist
     *
     * @return the data structures
     */
    Set<DataStructureBean> getDataStructures();

    /**
     * Returns all the reporting taxonomies in this container, returns an empty set if no reporting taxonomies exist
     *
     * @return the reporting taxonomys
     */
    Set<ReportingTaxonomyBean> getReportingTaxonomys();

    /**
     * Returns all the structure sets in this container, returns an empty set if no structure sets exist
     *
     * @return the structure sets
     */
    Set<StructureSetBean> getStructureSets();

    /**
     * Returns all the processes in this container, returns an empty set if no processes exist
     *
     * @return the processes
     */
    Set<ProcessBean> getProcesses();

    /**
     * Returns all the provision agreements in this container, returns an empty set if no provision agreements exist
     *
     * @return the provision agreements
     */
    Set<ProvisionAgreementBean> getProvisionAgreements();

    /**
     * Returns all the registrations in this container, returns an empty set if no registrations exist
     *
     * @return the registrations
     */
    Set<RegistrationBean> getRegistrations();

    /**
     * Returns all the subscriptions in this container, returns an empty set if no subscriptions exist
     *
     * @return the subscriptions
     */
    Set<SubscriptionBean> getSubscriptions();

    /*****************************************************************************************************************/

    /**
     * Returns all the attachment constraints that exist in the beans collection and are maintained by the given agency
     *
     * @param agencyId the agency id
     * @return the attachment constraints
     */
    Set<AttachmentConstraintBean> getAttachmentConstraints(String agencyId);

    /**
     * Returns the agency schemes in this container that is maintained by the given agency, returns null if no agency scheme exists
     *
     * @param agencyId the agency id
     * @return the agency scheme
     */
    AgencySchemeBean getAgencyScheme(String agencyId);

    /**
     * Returns all the content constraints in this container that are maintained by the given agency, returns an empty set if no agencies exist
     *
     * @param agencyId the agency id
     * @return the content constraint beans
     */
    Set<ContentConstraintBean> getContentConstraintBeans(String agencyId);

    /**
     * Returns all the organisation unit schemes in this container that are maintained by the given agency, returns an empty set if no organisation schemes exist
     *
     * @param agencyId the agency id
     * @return the organisation unit schemes
     */
    Set<OrganisationUnitSchemeBean> getOrganisationUnitSchemes(String agencyId);

    /**
     * Returns all the data consumer scheme in this container that is maintained by the given agency, returns null if no data consumer scheme exists
     *
     * @param agencyId the agency id
     * @return the data consumer scheme
     */
    DataConsumerSchemeBean getDataConsumerScheme(String agencyId);

    /**
     * Returns all the dataflows in this container that are maintained by the given agency, returns an empty set if no dataflows exist
     *
     * @param agencyId the agency id
     * @return the dataflows
     */
    Set<DataflowBean> getDataflows(String agencyId);

    /**
     * Returns the data provider scheme in this container that are maintained by the given agency, returns an empty set if no data provider scheme exist
     *
     * @param agencyId the agency id
     * @return the data provider scheme
     */
    DataProviderSchemeBean getDataProviderScheme(String agencyId);

    /**
     * Returns all the metadataflows in this container that are maintained by the given agency, returns an empty set if no metadataflows exist
     *
     * @param agencyId the agency id
     * @return the metadataflows
     */
    Set<MetadataFlowBean> getMetadataflows(String agencyId);

    /**
     * Returns all the category schemes in this container that are maintained by the given agency, returns an empty set if no category schemes exist
     *
     * @param agencyId the agency id
     * @return the category schemes
     */
    Set<CategorySchemeBean> getCategorySchemes(String agencyId);

    /**
     * Returns all the codelists in this container that are maintained by the given agency, returns an empty set if no codelists exist
     *
     * @param agencyId the agency id
     * @return the codelists
     */
    Set<CodelistBean> getCodelists(String agencyId);

    /**
     * Returns all the hierarchical codelists in this container that are maintained by the given agency, returns an empty set if no hierarchical codelists exist
     *
     * @param agencyId the agency id
     * @return the hierarchical codelists
     */
    Set<HierarchicalCodelistBean> getHierarchicalCodelists(String agencyId);

    /**
     * Returns all the categorisations in this container that are maintained by the given agency, returns an empty set if no categorisations exist
     *
     * @param agencyId the agency id
     * @return the categorisations
     */
    Set<CategorisationBean> getCategorisations(String agencyId);

    /**
     * Returns all the concept schemes in this container that are maintained by the given agency, returns an empty set if no concept schemes exist
     *
     * @param agencyId the agency id
     * @return the concept schemes
     */
    Set<ConceptSchemeBean> getConceptSchemes(String agencyId);

    /**
     * Returns all the metadata structures in this container that are maintained by the given agency, returns an empty set if no metadata structures exist
     *
     * @param agencyId the agency id
     * @return the metadata structures
     */
    Set<MetadataStructureDefinitionBean> getMetadataStructures(String agencyId);

    /**
     * Returns all the key families in this container that are maintained by the given agency, returns an empty set if no key families exist
     *
     * @param agencyId the agency id
     * @return the data structures
     */
    Set<DataStructureBean> getDataStructures(String agencyId);

    /**
     * Returns all the reporting taxonomies in this container that are maintained by the given agency, returns an empty set if no reporting taxonomies exist
     *
     * @param agencyId the agency id
     * @return the reporting taxonomys
     */
    Set<ReportingTaxonomyBean> getReportingTaxonomys(String agencyId);

    /**
     * Returns all the structure sets in this container that are maintained by the given agency, returns an empty set if no structure sets exist
     *
     * @param agencyId the agency id
     * @return the structure sets
     */
    Set<StructureSetBean> getStructureSets(String agencyId);

    /**
     * Returns all the processes in this container that are maintained by the given agency, returns an empty set if no processes exist
     *
     * @param agencyId the agency id
     * @return the processes
     */
    Set<ProcessBean> getProcesses(String agencyId);

    /**
     * Returns all the provision agreements in this container that are maintained by the given agency, returns an empty set if no provision agreements exist
     *
     * @param agencyId the agency id
     * @return the provision agreements
     */
    Set<ProvisionAgreementBean> getProvisionAgreements(String agencyId);

    /**
     * Returns all the registrations in this container that are maintained by the given agency, returns an empty set if no registrations exist
     *
     * @param agencyId the agency id
     * @return the registrations
     */
    Set<RegistrationBean> getRegistrations(String agencyId);

    /**
     * Returns all the subscriptions in this container that are maintained by the given agency, returns an empty set if no subscriptions exist
     *
     * @param agencyId the agency id
     * @return the subscriptions
     */
    Set<SubscriptionBean> getSubscriptions(String agencyId);

    /*****************************************************************************************************************************/
    /**
     * Returns all the agencies that exist in the beans collection.
     *
     * @param ref the ref
     * @return the agencies
     */
    Set<AgencyBean> getAgencies(MaintainableRefBean ref);

    /**
     * Returns all the attachment constraints that exist in the beans collection.
     *
     * @param ref the ref
     * @return the attachment constraints
     */
    Set<AttachmentConstraintBean> getAttachmentConstraints(MaintainableRefBean ref);

    /**
     * Returns all the agency schemes in this container, returns an empty set if no agencies exist.
     *
     * @param ref the ref
     * @return the agencies schemes
     */
    Set<AgencySchemeBean> getAgenciesSchemes(MaintainableRefBean ref);

    /**
     * Returns all the content constraints that exist in the beans collection.
     *
     * @param ref the ref
     * @return the content constraint beans
     */
    Set<ContentConstraintBean> getContentConstraintBeans(MaintainableRefBean ref);

    /**
     * Returns all the organisation unit schemes in this container, returns an empty set if no organisation schemes exist.
     *
     * @param ref the ref
     * @return the organisation unit schemes
     */
    Set<OrganisationUnitSchemeBean> getOrganisationUnitSchemes(MaintainableRefBean ref);

    /**
     * Returns all the data consumers in this container, returns an empty set if no data consumers exist.
     *
     * @param ref the ref
     * @return the data consumer schemes
     */
    Set<DataConsumerSchemeBean> getDataConsumerSchemes(MaintainableRefBean ref);

    /**
     * Returns all the dataflows in this container, returns an empty set if no dataflows exist.
     *
     * @param ref the ref
     * @return the dataflows
     */
    Set<DataflowBean> getDataflows(MaintainableRefBean ref);

    /**
     * Returns all the data provider in this container, returns an empty set if no data providers exist.
     *
     * @param ref the ref
     * @return the data provider schemes
     */
    Set<DataProviderSchemeBean> getDataProviderSchemes(MaintainableRefBean ref);

    /**
     * Returns all the metadataflows in this container, returns an empty set if no metadataflows exist.
     *
     * @param ref the ref
     * @return the metadataflows
     */
    Set<MetadataFlowBean> getMetadataflows(MaintainableRefBean ref);

    /**
     * Returns all the category schemes in this container, returns an empty set if no category schemes exist.
     *
     * @param ref the ref
     * @return the category schemes
     */
    Set<CategorySchemeBean> getCategorySchemes(MaintainableRefBean ref);

    /**
     * Returns all the codelists in this container, returns an empty set if no codelists exist.
     *
     * @param ref the ref
     * @return the codelists
     */
    Set<CodelistBean> getCodelists(MaintainableRefBean ref);

    /**
     * Returns all the hierarchical codelists in this container, returns an empty set if no hierarchical codelists exist.
     *
     * @param ref the ref
     * @return the hierarchical codelists
     */
    Set<HierarchicalCodelistBean> getHierarchicalCodelists(MaintainableRefBean ref);

    /**
     * Returns all the categorisations in this container, returns an empty set if no categorisations exist.
     *
     * @param ref the ref
     * @return the categorisations
     */
    Set<CategorisationBean> getCategorisations(MaintainableRefBean ref);

    /**
     * Returns all the concept schemes in this container, returns an empty set if no concept schemes exist.
     *
     * @param ref the ref
     * @return the concept schemes
     */
    Set<ConceptSchemeBean> getConceptSchemes(MaintainableRefBean ref);

    /**
     * Returns all the metadata structures in this container, returns an empty set if no metadata structures exist.
     *
     * @param ref the ref
     * @return the metadata structures
     */
    Set<MetadataStructureDefinitionBean> getMetadataStructures(MaintainableRefBean ref);

    /**
     * Returns all the key families in this container, returns an empty set if no key families exist.
     *
     * @param ref the ref
     * @return the data structures
     */
    Set<DataStructureBean> getDataStructures(MaintainableRefBean ref);

    /**
     * Returns all the reporting taxonomies in this container, returns an empty set if no reporting taxonomies exist.
     *
     * @param ref the ref
     * @return the reporting taxonomys
     */
    Set<ReportingTaxonomyBean> getReportingTaxonomys(MaintainableRefBean ref);

    /**
     * Returns all the structure sets in this container, returns an empty set if no structure sets exist.
     *
     * @param ref the ref
     * @return the structure sets
     */
    Set<StructureSetBean> getStructureSets(MaintainableRefBean ref);

    /**
     * Returns all the processes in this container, returns an empty set if no processes exist.
     *
     * @param ref the ref
     * @return the processes
     */
    Set<ProcessBean> getProcesses(MaintainableRefBean ref);

    /**
     * Returns all the provision agreements in this container, returns an empty set if no provision agreements exist
     *
     * @param ref the ref
     * @return the provision agreements
     */
    Set<ProvisionAgreementBean> getProvisionAgreements(MaintainableRefBean ref);

    /**
     * Returns all the registrations in this container, returns an empty set if no registrations exist.
     *
     * @param ref the ref
     * @return the registrations
     */
    Set<RegistrationBean> getRegistrations(MaintainableRefBean ref);

    /**
     * Returns all the subscriptions in this container, returns an empty set if no subscriptions exist.
     *
     * @param ref the ref
     * @return the subscriptions
     */
    Set<SubscriptionBean> getSubscriptions(MaintainableRefBean ref);


    /**
     * Returns all the maintainables in this container, returns an empty set if no maintainables exist in this container.
     *
     * @param exclude do not return the maintainables which match the optional exclude parameters
     * @return set of all maintainables, minus any which match the optional exclude parameters
     */
    Set<MaintainableBean> getAllMaintainables(SDMX_STRUCTURE_TYPE... exclude);

    /**
     * Returns all the maintainables of a given type.
     *
     * @param structureType filter on this type for returned maintainables
     * @return set of all maintainables of given type
     */
    Set<MaintainableBean> getMaintainables(SDMX_STRUCTURE_TYPE structureType);


    /**
     * Returns a MutableBeans package containing all the mutable bean instances of the beans contained within this container.
     *
     * @return MutableBeans mutable beans
     */
    MutableBeans getMutableBeans();
}
