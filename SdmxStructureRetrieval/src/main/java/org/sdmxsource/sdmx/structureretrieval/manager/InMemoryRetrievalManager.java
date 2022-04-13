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
package org.sdmxsource.sdmx.structureretrieval.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sdmxsource.sdmx.api.listener.Listener;
import org.sdmxsource.sdmx.api.manager.parse.StructureParsingManager;
import org.sdmxsource.sdmx.api.manager.persist.StructurePersistenceManager;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
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
import org.sdmxsource.sdmx.api.model.beans.registry.AttachmentConstraintBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ContentConstraintBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.beans.registry.SubscriptionBean;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.util.beans.MaintainableUtil;
import org.sdmxsource.sdmx.util.beans.container.SdmxBeansImpl;

import java.util.HashSet;
import java.util.Set;


/**
 * The local retrieval manager provides interfaces to retrieve structures off an in memory storage of the SdmxBeans.
 * <p>
 * This class is able to updated its cache as if it were a local storage with the interface methods provided by the StructurePersistenceManager.
 */
public class InMemoryRetrievalManager extends BaseSdmxBeanRetrievalManager implements SdmxBeanRetrievalManager,
        StructurePersistenceManager,
        Listener<ReadableDataLocation> {
    private static Logger LOG = LogManager.getLogger(InMemoryRetrievalManager.class);
    private final StructureParsingManager structureParsingManager;
    /**
     * The Beans.
     */
    protected SdmxBeans beans = new SdmxBeansImpl();
    /**
     * The Agency scheme extractor.
     */
    ResultExtractor<AgencySchemeBean> agencySchemeExtractor = new ResultExtractor<AgencySchemeBean>();
    /**
     * The Attachment constraint extractor.
     */
    ResultExtractor<AttachmentConstraintBean> attachmentConstraintExtractor = new ResultExtractor<AttachmentConstraintBean>(new MaintainableUtil<AttachmentConstraintBean>());
    /**
     * The Categorisation extractor.
     */
    ResultExtractor<CategorisationBean> categorisationExtractor = new ResultExtractor<CategorisationBean>();
    /**
     * The Codelist extractor.
     */
    ResultExtractor<CodelistBean> codelistExtractor = new ResultExtractor<CodelistBean>(new MaintainableUtil<CodelistBean>());
    /**
     * The Concept scheme extractor.
     */
    ResultExtractor<ConceptSchemeBean> conceptSchemeExtractor = new ResultExtractor<ConceptSchemeBean>(new MaintainableUtil<ConceptSchemeBean>());
    /**
     * The Content constraint extractor.
     */
    ResultExtractor<ContentConstraintBean> contentConstraintExtractor = new ResultExtractor<ContentConstraintBean>(new MaintainableUtil<ContentConstraintBean>());
    /**
     * The Category scheme extractor.
     */
    ResultExtractor<CategorySchemeBean> categorySchemeExtractor = new ResultExtractor<CategorySchemeBean>(new MaintainableUtil<CategorySchemeBean>());
    /**
     * The Data consumer scheme extractor.
     */
    ResultExtractor<DataConsumerSchemeBean> dataConsumerSchemeExtractor = new ResultExtractor<DataConsumerSchemeBean>();
    /**
     * The Dataflow extractor.
     */
    ResultExtractor<DataflowBean> dataflowExtractor = new ResultExtractor<DataflowBean>(new MaintainableUtil<DataflowBean>());
    /**
     * The Data provider scheme extractor.
     */
    ResultExtractor<DataProviderSchemeBean> dataProviderSchemeExtractor = new ResultExtractor<DataProviderSchemeBean>();
    /**
     * The Hierarchical codelist extractor.
     */
    ResultExtractor<HierarchicalCodelistBean> hierarchicalCodelistExtractor = new ResultExtractor<HierarchicalCodelistBean>(new MaintainableUtil<HierarchicalCodelistBean>());
    /**
     * The Metadata flow extractor.
     */
    ResultExtractor<MetadataFlowBean> metadataFlowExtractor = new ResultExtractor<MetadataFlowBean>(new MaintainableUtil<MetadataFlowBean>());
    /**
     * The Data structure extractor.
     */
    ResultExtractor<DataStructureBean> dataStructureExtractor = new ResultExtractor<DataStructureBean>(new MaintainableUtil<DataStructureBean>());
    /**
     * The Metadata structure definition extractor.
     */
    ResultExtractor<MetadataStructureDefinitionBean> metadataStructureDefinitionExtractor = new ResultExtractor<MetadataStructureDefinitionBean>(new MaintainableUtil<MetadataStructureDefinitionBean>());
    /**
     * The Organisation unit scheme extractor.
     */
    ResultExtractor<OrganisationUnitSchemeBean> organisationUnitSchemeExtractor = new ResultExtractor<OrganisationUnitSchemeBean>(new MaintainableUtil<OrganisationUnitSchemeBean>());
    /**
     * The Process bean extractor.
     */
    ResultExtractor<ProcessBean> processBeanExtractor = new ResultExtractor<ProcessBean>(new MaintainableUtil<ProcessBean>());
    /**
     * The Provision agreement extractor.
     */
    ResultExtractor<ProvisionAgreementBean> provisionAgreementExtractor = new ResultExtractor<ProvisionAgreementBean>(new MaintainableUtil<ProvisionAgreementBean>());
    /**
     * The Structure set extractor.
     */
    ResultExtractor<StructureSetBean> structureSetExtractor = new ResultExtractor<StructureSetBean>(new MaintainableUtil<StructureSetBean>());
    /**
     * The Reporting taxonomy extractor.
     */
    ResultExtractor<ReportingTaxonomyBean> reportingTaxonomyExtractor = new ResultExtractor<ReportingTaxonomyBean>(new MaintainableUtil<ReportingTaxonomyBean>());
    /**
     * The Subscription extractor.
     */
    ResultExtractor<SubscriptionBean> subscriptionExtractor = new ResultExtractor<SubscriptionBean>(new MaintainableUtil<SubscriptionBean>());

    /**
     * Default constructor
     */
    public InMemoryRetrievalManager() {
        structureParsingManager = null;
    }

    /**
     * Instantiates a new In memory retrieval manager.
     *
     * @param structureParsingManager the structure parsing manager
     */
    public InMemoryRetrievalManager(final StructureParsingManager structureParsingManager) {
        this.structureParsingManager = structureParsingManager;
    }

    /**
     * Create an in memory retrieval manager using a URI as a seed, the URI may reference a file (local or external on the web) or be a SDMX REST query
     *
     * @param seed                    the seed
     * @param structureParsingManager the structure parsing manager
     */
    public InMemoryRetrievalManager(ReadableDataLocation seed, final StructureParsingManager structureParsingManager) {
        this(structureParsingManager);
        invoke(seed);
    }

    /**
     * Instantiates a new In memory retrieval manager.
     *
     * @param beans the beans
     */
    public InMemoryRetrievalManager(SdmxBeans beans) {
        this();
        this.beans = beans;
        if (this.beans == null) {
            this.beans = new SdmxBeansImpl();
        }
    }

    @Override
    public void invoke(ReadableDataLocation seed) {
        beans = new SdmxBeansImpl();
        if (seed != null) {

            if (structureParsingManager == null) {
                throw new RuntimeException("Can not create an InMemoryRetrievalManager, StructureParsingManager is null.");
            }
            try {
                saveStructures(structureParsingManager.parseStructures(seed).getStructureBeans(false));
            } finally {
                seed.close();
            }
        }
    }


    @Override
    public void saveStructure(MaintainableBean maintainable) {
        LOG.info("saveStructure:" + maintainable.getUrn());
        SdmxBeans beans = new SdmxBeansImpl();
        beans.addIdentifiable(maintainable);
        saveStructures(beans);
    }

    @Override
    public void deleteStructure(MaintainableBean maintainable) {
        LOG.info("deleteStructure:" + maintainable.getUrn());
        SdmxBeans beans = new SdmxBeansImpl();
        beans.addIdentifiable(maintainable);
        deleteStructures(beans);
    }

    @Override
    public void saveStructures(SdmxBeans beans) {
        LOG.info("saveStructures:" + beans.toString());
        this.beans.merge(beans);
    }

    @Override
    public void deleteStructures(SdmxBeans beans) {
        LOG.info("deleteStructures:" + beans.toString());
        for (MaintainableBean currentMaint : beans.getAllMaintainables()) {
            this.beans.removeMaintainable(currentMaint);
        }
    }


    /**
     * Returns a copy of the underlying beans for this retrieval Manager
     *
     * @return beans beans
     */
    public SdmxBeans getBeans() {
        return new SdmxBeansImpl(beans);
    }

    @Override
    public Set<AttachmentConstraintBean> getAttachmentConstraints(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        return attachmentConstraintExtractor.filterResults(beans.getAttachmentConstraints(ref), returnLatest, returnStub);
    }

    @Override
    public Set<ContentConstraintBean> getContentConstraints(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        return contentConstraintExtractor.filterResults(beans.getContentConstraintBeans(ref), returnLatest, returnStub);
    }

    @Override
    public Set<OrganisationUnitSchemeBean> getOrganisationUnitSchemeBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        return organisationUnitSchemeExtractor.filterResults(beans.getOrganisationUnitSchemes(ref), returnLatest, returnStub);
    }

    @Override
    public Set<DataProviderSchemeBean> getDataProviderSchemeBeans(MaintainableRefBean ref, boolean returnStub) {
        return dataProviderSchemeExtractor.filterResults(beans.getDataProviderSchemes(ref), false, returnStub);
    }

    @Override
    public Set<DataConsumerSchemeBean> getDataConsumerSchemeBeans(MaintainableRefBean ref, boolean returnStub) {
        return dataConsumerSchemeExtractor.filterResults(beans.getDataConsumerSchemes(ref), false, returnStub);
    }

    @Override
    public Set<AgencySchemeBean> getAgencySchemeBeans(MaintainableRefBean ref, boolean returnStub) {
        return agencySchemeExtractor.filterResults(beans.getAgenciesSchemes(ref), false, returnStub);
    }

    @Override
    public Set<CategorisationBean> getCategorisationBeans(MaintainableRefBean ref, boolean returnStub) {
        return categorisationExtractor.filterResults(beans.getCategorisations(ref), false, returnStub);
    }

    @Override
    public Set<CategorySchemeBean> getCategorySchemeBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        return categorySchemeExtractor.filterResults(beans.getCategorySchemes(ref), returnLatest, returnStub);
    }

    @Override
    public Set<CodelistBean> getCodelistBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        return codelistExtractor.filterResults(beans.getCodelists(ref), returnLatest, returnStub);
    }

    @Override
    public Set<ConceptSchemeBean> getConceptSchemeBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        return conceptSchemeExtractor.filterResults(beans.getConceptSchemes(ref), returnLatest, returnStub);
    }

    @Override
    public Set<DataflowBean> getDataflowBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        return dataflowExtractor.filterResults(beans.getDataflows(ref), returnLatest, returnStub);
    }

    @Override
    public Set<HierarchicalCodelistBean> getHierarchicCodeListBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        return hierarchicalCodelistExtractor.filterResults(beans.getHierarchicalCodelists(ref), returnLatest, returnStub);
    }

    @Override
    public Set<DataStructureBean> getDataStructureBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        return dataStructureExtractor.filterResults(beans.getDataStructures(ref), returnLatest, returnStub);
    }

    @Override
    public Set<MetadataFlowBean> getMetadataflowBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        return metadataFlowExtractor.filterResults(beans.getMetadataflows(ref), returnLatest, returnStub);
    }

    @Override
    public Set<MetadataStructureDefinitionBean> getMetadataStructureBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        return metadataStructureDefinitionExtractor.filterResults(beans.getMetadataStructures(ref), returnLatest, returnStub);
    }

    @Override
    public Set<ProcessBean> getProcessBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        return processBeanExtractor.filterResults(beans.getProcesses(ref), returnLatest, returnStub);
    }

    @Override
    public Set<ReportingTaxonomyBean> getReportingTaxonomyBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        return reportingTaxonomyExtractor.filterResults(beans.getReportingTaxonomys(ref), returnLatest, returnStub);
    }

    @Override
    public Set<StructureSetBean> getStructureSetBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        return structureSetExtractor.filterResults(beans.getStructureSets(ref), returnLatest, returnStub);
    }

    @Override
    public Set<ProvisionAgreementBean> getProvisionAgreementBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        return provisionAgreementExtractor.filterResults(beans.getProvisionAgreements(ref), returnLatest, returnStub);
    }

    @Override
    protected Set<SubscriptionBean> getSubscriptionBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        return subscriptionExtractor.filterResults(beans.getSubscriptions(ref), returnLatest, returnStub);
    }


    private class ResultExtractor<T extends MaintainableBean> {
        private MaintainableUtil<T> maintainableUtil;

        /**
         * Instantiates a new Result extractor.
         */
        public ResultExtractor() {
        }

        /**
         * Instantiates a new Result extractor.
         *
         * @param maintainableUtil the maintainable util
         */
        public ResultExtractor(MaintainableUtil<T> maintainableUtil) {
            this.maintainableUtil = maintainableUtil;
        }

        @SuppressWarnings("unchecked")
        private Set<T> filterResults(Set<T> results, boolean returnLatest, boolean returnStub) {
            if (returnLatest && maintainableUtil != null) {
                results = maintainableUtil.filterCollectionGetLatestOfType(results);
            }
            if (returnStub && serviceRetrievalManager != null) {
                Set<T> newSet = new HashSet<T>();
                for (T result : results) {
                    newSet.add((T) serviceRetrievalManager.createStub(result));
                }
                results = newSet;
            }
            return results;
        }
    }
}
