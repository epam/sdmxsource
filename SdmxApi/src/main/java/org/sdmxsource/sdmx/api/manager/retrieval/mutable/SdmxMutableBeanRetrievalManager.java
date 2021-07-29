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
package org.sdmxsource.sdmx.api.manager.retrieval.mutable;

import org.sdmxsource.sdmx.api.exception.CrossReferenceException;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
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

import java.util.Set;


/**
 * Manages the retrieval of MaintainableMutableBean structures
 *
 * @author Matt Nelson
 */
public interface SdmxMutableBeanRetrievalManager {

    /**
     * Returns a single Agency Scheme, this expects the ref object either to contain
     * a URN or all the attributes required to uniquely identify the object.  If version information
     * is missing then the latest version is assumed.
     *
     * @param ref          the ref
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return mutable agency scheme
     */
    AgencySchemeMutableBean getMutableAgencyScheme(MaintainableRefBean ref, boolean returnLatest, boolean returnStub);

    /**
     * Returns AgencySchemeMutableBean that match the parameters in the ref bean.  If the ref bean is null or
     * has no attributes set, then this will be interpreted as a search for all CodelistBeans
     *
     * @param ref          - the reference object defining the search parameters, can be empty or null
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return list of beans that match the search criteria
     */
    Set<AgencySchemeMutableBean> getMutableAgencySchemeBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub);

    /**
     * Returns a single Categorisation, this expects the ref object either to contain
     * a URN or all the attributes required to uniquely identify the object.  If version information
     * is missing then the latest version is assumed.
     *
     * @param ref          the ref
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return mutable categorisation
     */
    CategorisationMutableBean getMutableCategorisation(MaintainableRefBean ref, boolean returnLatest, boolean returnStub);

    /**
     * Returns CategorisationBeans that match the parameters in the ref bean.  If the ref bean is null or
     * has no attributes set, then this will be interpreted as a search for all CodelistBeans
     *
     * @param ref          - the reference object defining the search parameters, can be empty or null
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return list of beans that match the search criteria
     */
    Set<CategorisationMutableBean> getMutableCategorisationBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub);

    /**
     * Returns a single CategoryScheme , this expects the ref object either to contain
     * a URN or all the attributes required to uniquely identify the object.  If version information
     * is missing then the latest version is assumed.
     *
     * @param ref          the ref
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return mutable category scheme
     */
    CategorySchemeMutableBean getMutableCategoryScheme(MaintainableRefBean ref, boolean returnLatest, boolean returnStub);

    /**
     * Returns CategorySchemeBeans that match the parameters in the ref bean.  If the ref bean is null or
     * has no attributes set, then this will be interpreted as a search for all CategorySchemeBeans
     *
     * @param ref          - the reference object defining the search parameters, can be empty or null
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return list of beans that match the search criteria
     */
    Set<CategorySchemeMutableBean> getMutableCategorySchemeBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub);

    /**
     * Returns a single CodeList , this expects the ref object either to contain
     * a URN or all the attributes required to uniquely identify the object.  If version information
     * is missing then the latest version is assumed.
     *
     * @param ref          the ref
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return mutable codelist
     */
    CodelistMutableBean getMutableCodelist(MaintainableRefBean ref, boolean returnLatest, boolean returnStub);

    /**
     * Returns CodelistBeans that match the parameters in the ref bean.  If the ref bean is null or
     * has no attributes set, then this will be interpreted as a search for all CodelistBeans
     *
     * @param ref          - the reference object defining the search parameters, can be empty or null
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return list of beans that match the search criteria
     */
    Set<CodelistMutableBean> getMutableCodelistBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub);

    /**
     * Returns a single ConceptScheme , this expects the ref object either to contain
     * a URN or all the attributes required to uniquely identify the object.  If version information
     * is missing then the latest version is assumed.
     *
     * @param ref          the ref
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return mutable concept scheme
     */
    ConceptSchemeMutableBean getMutableConceptScheme(MaintainableRefBean ref, boolean returnLatest, boolean returnStub);

    /**
     * Returns ConceptSchemeBeans that match the parameters in the ref bean.  If the ref bean is null or
     * has no attributes set, then this will be interpreted as a search for all ConceptSchemeBeans
     *
     * @param ref          - the reference object defining the search parameters, can be empty or null
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return list of beans that match the search criteria
     */
    Set<ConceptSchemeMutableBean> getMutableConceptSchemeBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub);

    /**
     * Returns a single data consumer scheme, this expects the ref object either to contain
     * a URN or all the attributes required to uniquely identify the object.  If version information
     * is missing then the latest version is assumed.
     *
     * @param ref          the ref
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return mutable data consumer scheme
     */
    DataConsumerSchemeMutableBean getMutableDataConsumerScheme(MaintainableRefBean ref, boolean returnLatest, boolean returnStub);

    /**
     * Returns a single Content Constraint, this expects the ref object either to contain
     * a URN or all the attributes required to uniquely identify the object.  If version information
     * is missing then the latest version is assumed.
     *
     * @param ref          the ref
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return mutable content constraint
     */
    ContentConstraintMutableBean getMutableContentConstraint(MaintainableRefBean ref, boolean returnLatest, boolean returnStub);

    /**
     * Returns ContentConstraintBeans that match the parameters in the ref bean.  If the ref bean is null or
     * has no attributes set, then this will be interpreted as a search for all ContentConstraintBeans
     *
     * @param ref          - the reference object defining the search parameters, can be empty or null
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return list of beans that match the search criteria
     */
    Set<ContentConstraintMutableBean> getMutableContentConstraintBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub);

    /**
     * Returns DataConsumerSchemeMutableBeans that match the parameters in the ref bean.  If the ref bean is null or
     * has no attributes set, then this will be interpreted as a search for all CodelistBeans
     *
     * @param ref          - the reference object defining the search parameters, can be empty or null
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return list of beans that match the search criteria
     */
    Set<DataConsumerSchemeMutableBean> getMutableDataConsumerSchemeBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub);

    /**
     * Returns a single Dataflow , this expects the ref object either to contain
     * a URN or all the attributes required to uniquely identify the object.  If version information
     * is missing then the latest version is assumed.
     *
     * @param ref          the ref
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return mutable dataflow
     */
    DataflowMutableBean getMutableDataflow(MaintainableRefBean ref, boolean returnLatest, boolean returnStub);

    /**
     * Returns DataflowBeans that match the parameters in the ref bean.  If the ref bean is null or
     * has no attributes set, then this will be interpreted as a search for all DataflowBeans
     *
     * @param ref          - the reference object defining the search parameters, can be empty or null
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return list of beans that match the search criteria
     */
    Set<DataflowMutableBean> getMutableDataflowBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub);

    /**
     * Returns a single Data Provider Scheme, this expects the ref object either to contain
     * a URN or all the attributes required to uniquely identify the object.  If version information
     * is missing then the latest version is assumed.
     *
     * @param ref          the ref
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return mutable data provider scheme
     */
    DataProviderSchemeMutableBean getMutableDataProviderScheme(MaintainableRefBean ref, boolean returnLatest, boolean returnStub);

    /**
     * Returns DataProviderSchemeMutableBeans that match the parameters in the ref bean.  If the ref bean is null or
     * has no attributes set, then this will be interpreted as a search for all CodelistBeans
     *
     * @param ref          - the reference object defining the search parameters, can be empty or null
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return list of beans that match the search criteria
     */
    Set<DataProviderSchemeMutableBean> getMutableDataProviderSchemeBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub);

    /**
     * Returns a single DataStructure.
     * This expects the ref object either to contain a URN or all the attributes required to uniquely identify the object.
     * If version information is missing then the latest version is assumed.
     *
     * @param ref          the ref
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return mutable data structure
     */
    DataStructureMutableBean getMutableDataStructure(MaintainableRefBean ref, boolean returnLatest, boolean returnStub);

    /**
     * Returns DataStructureBeans that match the parameters in the ref bean.  If the ref bean is null or
     * has no attributes set, then this will be interpreted as a search for all dataStructureBeans
     *
     * @param ref          - the reference object defining the search parameters, can be empty or null
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return list of beans that match the search criteria
     */
    Set<DataStructureMutableBean> getMutableDataStructureBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub);

    /**
     * Returns a single HierarchicCodeList , this expects the ref object either to contain
     * a URN or all the attributes required to uniquely identify the object.  If version information
     * is missing then the latest version is assumed.
     *
     * @param ref          the ref
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return mutable hierarchic code list
     */
    HierarchicalCodelistMutableBean getMutableHierarchicCodeList(MaintainableRefBean ref, boolean returnLatest, boolean returnStub);

    /**
     * Returns HierarchicalCodelistBeans that match the parameters in the ref bean.  If the ref bean is null or
     * has no attributes set, then this will be interpreted as a search for all HierarchicalCodelistBeans
     *
     * @param ref          - the reference object defining the search parameters, can be empty or null
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return list of beans that match the search criteria
     */
    Set<HierarchicalCodelistMutableBean> getMutableHierarchicCodeListBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub);

    /**
     * Returns a set of maintainables which includes the maintainable being queried for, defined by the StructureQueryObject parameter.
     * <p>
     * Expects only ONE maintainable to be returned from this query
     *
     * @param query        the query
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return mutable maintainable
     * @throws CrossReferenceException the cross reference exception
     */
    MaintainableMutableBean getMutableMaintainable(StructureReferenceBean query, boolean returnLatest, boolean returnStub) throws CrossReferenceException;

    /**
     * Returns a set of maintainables which includes the maintainable being queried for, defined by the StructureQueryObject parameter.
     *
     * @param query        the query
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return mutable maintainables
     * @throws CrossReferenceException the cross reference exception
     */
    Set<? extends MaintainableMutableBean> getMutableMaintainables(StructureReferenceBean query, boolean returnLatest, boolean returnStub) throws CrossReferenceException;

    /**
     * Returns a single Metadataflow , this expects the ref object either to contain
     * a URN or all the attributes required to uniquely identify the object.  If version information
     * is missing then the latest version is assumed.
     *
     * @param ref          the ref
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return mutable metadataflow
     */
    MetadataFlowMutableBean getMutableMetadataflow(MaintainableRefBean ref, boolean returnLatest, boolean returnStub);

    /**
     * Returns MetadataFlowBeans that match the parameters in the ref bean.  If the ref bean is null or
     * has no attributes set, then this will be interpreted as a search for all MetadataFlowBeans
     *
     * @param ref          - the reference object defining the search parameters, can be empty or null
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return list of beans that match the search criteria
     */
    Set<MetadataFlowMutableBean> getMutableMetadataflowBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub);

    /**
     * Returns a single MetadataStructure , this expects the ref object either to contain
     * a URN or all the attributes required to uniquely identify the object.  If version information
     * is missing then the latest version is assumed.
     *
     * @param ref          the ref
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return mutable metadata structure
     */
    MetadataStructureDefinitionMutableBean getMutableMetadataStructure(MaintainableRefBean ref, boolean returnLatest, boolean returnStub);

    /**
     * Returns MetadataStructureBeans that match the parameters in the ref bean.  If the ref bean is null or
     * has no attributes set, then this will be interpreted as a search for all MetadataStructureBeans
     *
     * @param ref          - the reference object defining the search parameters, can be empty or null
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return list of beans that match the search criteria
     */
    Set<MetadataStructureDefinitionMutableBean> getMutableMetadataStructureBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub);

    /**
     * Returns a single organisation scheme, this expects the ref object either to contain
     * a URN or all the attributes required to uniquely identify the object.  If version information
     * is missing then the latest version is assumed.
     *
     * @param ref          the ref
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return mutable organisation unit scheme
     */
    OrganisationUnitSchemeMutableBean getMutableOrganisationUnitScheme(MaintainableRefBean ref, boolean returnLatest, boolean returnStub);

    /**
     * Returns OrganisationUnitSchemeMutableBean that match the parameters in the ref bean.  If the ref bean is null or
     * has no attributes set, then this will be interpreted as a search for all OrganisationUnitSchemeMutableBean
     *
     * @param ref          - the reference object defining the search parameters, can be empty or null
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return list of beans that match the search criteria
     */
    Set<OrganisationUnitSchemeMutableBean> getMutableOrganisationUnitSchemeBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub);

    /**
     * Returns a process bean, this expects the ref object either to contain
     * a URN or all the attributes required to uniquely identify the object.  If version information
     * is missing then the latest version is assumed.
     *
     * @param ref          the ref
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return mutable process bean
     */
    ProcessMutableBean getMutableProcessBean(MaintainableRefBean ref, boolean returnLatest, boolean returnStub);

    /**
     * Returns ProcessBeans that match the parameters in the ref bean.  If the ref bean is null or
     * has no attributes set, then this will be interpreted as a search for all ProcessBean
     *
     * @param ref          - the reference object defining the search parameters, can be empty or null
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return list of beans that match the search criteria
     */
    Set<ProcessMutableBean> getMutableProcessBeanBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub);

    /**
     * Returns a provision agreement bean, this expects the ref object to contain
     * all the attributes required to uniquely identify the object.  If version information
     * is missing then the latest version is assumed.
     *
     * @param ref          the ref
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return mutable provision agreement
     */
    ProvisionAgreementMutableBean getMutableProvisionAgreement(MaintainableRefBean ref, boolean returnLatest, boolean returnStub);

    /**
     * Returns ProvisionAgreement beans that match the parameters in the ref bean. If the ref bean is null or
     * has no attributes set, then this will be interpreted as a search for all ProvisionAgreement beans.
     *
     * @param ref          - the reference object defining the search parameters, can be empty or null
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return list of beans that match the search criteria
     */
    Set<ProvisionAgreementMutableBean> getMutableProvisionAgreementBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub);

    /**
     * Returns a reporting taxonomy bean, this expects the ref object either to contain
     * a URN or all the attributes required to uniquely identify the object.  If version information
     * is missing then the latest version is assumed.
     *
     * @param ref          the ref
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return mutable reporting taxonomy
     */
    ReportingTaxonomyMutableBean getMutableReportingTaxonomy(MaintainableRefBean ref, boolean returnLatest, boolean returnStub);

    /**
     * Returns ReportingTaxonomyBeans that match the parameters in the ref bean.  If the ref bean is null or
     * has no attributes set, then this will be interpreted as a search for all ReportingTaxonomyBeans
     *
     * @param ref          - the reference object defining the search parameters, can be empty or null
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return list of beans that match the search criteria
     */
    Set<ReportingTaxonomyMutableBean> getMutableReportingTaxonomyBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub);

    /**
     * Returns a structure set bean, this expects the ref object either to contain
     * a URN or all the attributes required to uniquely identify the object.  If version information
     * is missing then the latest version is assumed.
     *
     * @param ref          the ref
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return mutable structure set
     */
    StructureSetMutableBean getMutableStructureSet(MaintainableRefBean ref, boolean returnLatest, boolean returnStub);

    /**
     * Returns StructureSetBeans that match the parameters in the ref bean.  If the ref bean is null or
     * has no attributes set, then this will be interpreted as a search for all StructureSetBeans
     *
     * @param ref          - the reference object defining the search parameters, can be empty or null
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return list of beans that match the search criteria
     */
    Set<StructureSetMutableBean> getMutableStructureSetBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub);
}
