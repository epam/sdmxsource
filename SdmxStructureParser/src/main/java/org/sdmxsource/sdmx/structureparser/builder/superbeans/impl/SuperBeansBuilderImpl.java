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
package org.sdmxsource.sdmx.structureparser.builder.superbeans.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sdmxsource.sdmx.api.builder.SuperBeansBuilder;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategorySchemeBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.HierarchicalCodelistBean;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptSchemeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataStructureDefinitionBean;
import org.sdmxsource.sdmx.api.model.beans.process.ProcessBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.beans.registry.RegistrationBean;
import org.sdmxsource.sdmx.api.model.superbeans.SuperBeans;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.metadata.MetadataStructureSuperBeanImpl;
import org.sdmxsource.sdmx.structureretrieval.manager.InMemoryRetrievalManager;
import org.sdmxsource.sdmx.util.beans.container.SuperBeansImpl;


/**
 * The type Super beans builder.
 */
public class SuperBeansBuilderImpl implements SuperBeansBuilder {
    private static final Logger LOG = LogManager.getLogger(SuperBeansBuilderImpl.class);

    private final CategorySchemeSuperBeanBuilder categorySchemeSuperBeanBuilder;

    private final CodelistSuperBeanBuilder codelistSuperBeanBuilder;

    private final ConceptSchemeSuperBeanBuilder conceptSchemeSuperBeanBuilder;

    private final DataflowSuperBeanBuilder dataflowSuperBeanBuilder;

    private final DataStructureSuperBeanBuilder dataStructureSuperBeanBuilder;

    private final HierarchicalCodelistSuperBeanBuilder hierarchicalCodelistSuperBeanBuilder;

    private final ProvisionSuperBeanBuilder provisionSuperBeanBuilder;

    private final ProcessSuperBeanBuilder processSuperBeanBuilder;

    private final RegistrationSuperBeanBuilder registrationSuperBeanBuilder;

    /**
     * Instantiates a new Super beans builder.
     */
    public SuperBeansBuilderImpl() {
        this(new CategorySchemeSuperBeanBuilder(),
                new CodelistSuperBeanBuilder(),
                new ConceptSchemeSuperBeanBuilder(),
                new DataflowSuperBeanBuilder(),
                new DataStructureSuperBeanBuilder(),
                new HierarchicalCodelistSuperBeanBuilder(),
                new ProvisionSuperBeanBuilder(),
                new ProcessSuperBeanBuilder(),
                new RegistrationSuperBeanBuilder());
    }

    /**
     * Instantiates a new Super beans builder.
     *
     * @param categorySchemeSuperBeanBuilder       the category scheme super bean builder
     * @param codelistSuperBeanBuilder             the codelist super bean builder
     * @param conceptSchemeSuperBeanBuilder        the concept scheme super bean builder
     * @param dataflowSuperBeanBuilder             the dataflow super bean builder
     * @param dataStructureSuperBeanBuilder        the data structure super bean builder
     * @param hierarchicalCodelistSuperBeanBuilder the hierarchical codelist super bean builder
     * @param provisionSuperBeanBuilder            the provision super bean builder
     * @param processSuperBeanBuilder              the process super bean builder
     * @param registrationSuperBeanBuilder         the registration super bean builder
     */
    public SuperBeansBuilderImpl(
            final CategorySchemeSuperBeanBuilder categorySchemeSuperBeanBuilder,
            final CodelistSuperBeanBuilder codelistSuperBeanBuilder,
            final ConceptSchemeSuperBeanBuilder conceptSchemeSuperBeanBuilder,
            final DataflowSuperBeanBuilder dataflowSuperBeanBuilder,
            final DataStructureSuperBeanBuilder dataStructureSuperBeanBuilder,
            final HierarchicalCodelistSuperBeanBuilder hierarchicalCodelistSuperBeanBuilder,
            final ProvisionSuperBeanBuilder provisionSuperBeanBuilder,
            final ProcessSuperBeanBuilder processSuperBeanBuilder,
            final RegistrationSuperBeanBuilder registrationSuperBeanBuilder) {
        this.categorySchemeSuperBeanBuilder = categorySchemeSuperBeanBuilder;
        this.codelistSuperBeanBuilder = codelistSuperBeanBuilder;
        this.conceptSchemeSuperBeanBuilder = conceptSchemeSuperBeanBuilder;
        this.dataflowSuperBeanBuilder = dataflowSuperBeanBuilder;
        this.dataStructureSuperBeanBuilder = dataStructureSuperBeanBuilder;
        this.hierarchicalCodelistSuperBeanBuilder = hierarchicalCodelistSuperBeanBuilder;
        this.provisionSuperBeanBuilder = provisionSuperBeanBuilder;
        this.processSuperBeanBuilder = processSuperBeanBuilder;
        this.registrationSuperBeanBuilder = registrationSuperBeanBuilder;
    }


    @Override
    public SuperBeans build(SdmxBeans buildFrom) throws SdmxException {
        return this.build(buildFrom, null, new InMemoryRetrievalManager(buildFrom));
    }

    @Override
    public SuperBeans build(SdmxBeans buildFrom, SuperBeans existingBeans, SdmxBeanRetrievalManager retrievalManager) throws SdmxException {
        LOG.debug("Build Superbeans: Create LocalRetrievalManager");

        if (existingBeans == null) {
            existingBeans = new SuperBeansImpl();
        }

        for (CategorySchemeBean currentBean : buildFrom.getCategorySchemes()) {
            LOG.debug("Build SuperBean: " + currentBean.getUrn());
            existingBeans.addCategoryScheme(categorySchemeSuperBeanBuilder.build(currentBean));
        }
        for (CodelistBean currentBean : buildFrom.getCodelists()) {
            LOG.debug("Build SuperBean: " + currentBean.getUrn());
            existingBeans.addCodelist(codelistSuperBeanBuilder.build(currentBean));
        }
        for (ConceptSchemeBean currentBean : buildFrom.getConceptSchemes()) {
            LOG.debug("Build SuperBean: " + currentBean.getUrn());
            existingBeans.addConceptScheme(conceptSchemeSuperBeanBuilder.build(currentBean, retrievalManager, existingBeans));
        }
        for (DataflowBean currentBean : buildFrom.getDataflows()) {
            LOG.debug("Build SuperBean: " + currentBean.getUrn());
            existingBeans.addDataflow(dataflowSuperBeanBuilder.build(currentBean, retrievalManager, existingBeans));
        }
        for (DataStructureBean currentBean : buildFrom.getDataStructures()) {
            LOG.debug("Build SuperBean: " + currentBean.getUrn());
            existingBeans.addDataStructure(dataStructureSuperBeanBuilder.build(currentBean, retrievalManager, existingBeans));
        }
        for (HierarchicalCodelistBean currentBean : buildFrom.getHierarchicalCodelists()) {
            LOG.debug("Build SuperBean: " + currentBean.getUrn());
            existingBeans.addHierarchicalCodelist(hierarchicalCodelistSuperBeanBuilder.build(currentBean, retrievalManager));
        }
        for (MetadataStructureDefinitionBean currentBean : buildFrom.getMetadataStructures()) {
            LOG.debug("Build SuperBean: " + currentBean.getUrn());
            existingBeans.addMetadataStructure(new MetadataStructureSuperBeanImpl(retrievalManager, existingBeans, currentBean));
        }
        for (ProvisionAgreementBean currentBean : buildFrom.getProvisionAgreements()) {
            LOG.debug("Build SuperBean: " + currentBean.getUrn());
            existingBeans.addProvision(provisionSuperBeanBuilder.build(currentBean, retrievalManager, existingBeans));
        }
        for (ProcessBean currentBean : buildFrom.getProcesses()) {
            LOG.debug("Build SuperBean: " + currentBean.getUrn());
            existingBeans.addProcess(processSuperBeanBuilder.build(currentBean, retrievalManager));
        }
        for (RegistrationBean currentBean : buildFrom.getRegistrations()) {
            LOG.debug("Build SuperBean: " + currentBean.getUrn());
            existingBeans.addRegistration(registrationSuperBeanBuilder.build(currentBean, retrievalManager, existingBeans));
        }
        return existingBeans;
    }
}
