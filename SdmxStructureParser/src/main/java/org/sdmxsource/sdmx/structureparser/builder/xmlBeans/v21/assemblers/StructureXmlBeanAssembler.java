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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers;

import org.sdmx.resources.sdmxml.schemas.v21.structure.*;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.base.AgencySchemeBean;
import org.sdmxsource.sdmx.api.model.beans.base.DataConsumerSchemeBean;
import org.sdmxsource.sdmx.api.model.beans.base.DataProviderSchemeBean;
import org.sdmxsource.sdmx.api.model.beans.base.OrganisationUnitSchemeBean;
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
import org.sdmxsource.sdmx.api.model.beans.registry.AttachmentConstraintBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ContentConstraintBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.*;

import java.util.Set;


/**
 * Build a v2.1 SDMX Structure Document from SDMXBeans, by incorporating other builders for its parts.
 */
public class StructureXmlBeanAssembler implements Assembler<StructuresType, SdmxBeans> {

    private final DataflowXmlBeanBuilder dataflowXmlBeanBuilderBean = new DataflowXmlBeanBuilder();

    private final MetadataflowXmlBeanBuilder metadataflowXmlBeanBuilderBean = new MetadataflowXmlBeanBuilder();

    private final CategorySchemeXmlBeanBuilder categorySchemeXmlBeanBuilderBean = new CategorySchemeXmlBeanBuilder();

    private final CategorisationXmlBeanBuilder categorisationXmlBeanBuilder = new CategorisationXmlBeanBuilder();

    private final CodelistXmlBeanBuilder codelistXmlBeanBuilderBean = new CodelistXmlBeanBuilder();

    private final HierarchicalCodelistXmlBuilder hierarchicalCodelistXmlBuilderBean = new HierarchicalCodelistXmlBuilder();

    private final ConceptSchemeXmlBeanBuilder conceptSchemeXmlBeanBuilderBean = new ConceptSchemeXmlBeanBuilder();

    private final MetadataStructureXmlBeanBuilder metadataStructureXmlBeanBuilderBean = new MetadataStructureXmlBeanBuilder();

    private final DataStructureXmlBeanBuilder dataStructureXmlBeanBuilderBean = new DataStructureXmlBeanBuilder();
    //FUNC SUPPORT STRUCTURE SET

    private final StructureSetXmlBeanBuilder structureSetXmlBeanBuilderBean = new StructureSetXmlBeanBuilder();

    private final ReportingTaxonomyXmlBeanBuilder reportingTaxonomyXmlBeanBuilderBean = new ReportingTaxonomyXmlBeanBuilder();

    private final ProcessXmlBeanBuilder processXmlBeanBuilderBean = new ProcessXmlBeanBuilder();

    private final AttachmentConstraintXmlBeanBuilder attachmentConstraintXmlBeanBuilder = new AttachmentConstraintXmlBeanBuilder();

    private final ContentConstraintXmlBeanBuilder contentConstraintXmlBeanBuilder = new ContentConstraintXmlBeanBuilder();

    private final ProvisionAgreementXmlBeanBuilder provisionAgreementXmlBeanBuilder = new ProvisionAgreementXmlBeanBuilder();

    private final OrganisationUnitSchemeXmlBeanBuilder organisationUnitSchemeXmlBeanBuilder = new OrganisationUnitSchemeXmlBeanBuilder();

    private final AgencySchemeXmlBeanBuilder agencySchemeXmlBeanBuilder = new AgencySchemeXmlBeanBuilder();

    private final DataConsumerSchemeXmBeanlBuilder dataConsumerSchemeXmBeanlBuilder = new DataConsumerSchemeXmBeanlBuilder();

    private final DataProviderSchemeXmlBeanBuilder dataProviderSchemeXmlBeanBuilder = new DataProviderSchemeXmlBeanBuilder();

    @Override
    public void assemble(StructuresType structures, SdmxBeans beans) throws SdmxException {
        OrganisationSchemesType orgType = null;
        if (beans.getOrganisationUnitSchemes().size() > 0) {
            orgType = structures.addNewOrganisationSchemes();
            OrganisationUnitSchemeType[] type = new OrganisationUnitSchemeType[beans.getOrganisationUnitSchemes().size()];
            int i = 0;
            for (OrganisationUnitSchemeBean currentBean : beans.getOrganisationUnitSchemes()) {
                type[i] = organisationUnitSchemeXmlBeanBuilder.build(currentBean);
                i++;
            }
            orgType.setOrganisationUnitSchemeArray(type);
        }
        if (beans.getDataConsumerSchemes().size() > 0) {
            if (orgType == null) {
                orgType = structures.addNewOrganisationSchemes();
            }
            int i = 0;
            DataConsumerSchemeType[] type = new DataConsumerSchemeType[beans.getDataConsumerSchemes().size()];
            for (DataConsumerSchemeBean currentBean : beans.getDataConsumerSchemes()) {
                type[i] = dataConsumerSchemeXmBeanlBuilder.build(currentBean);
                orgType.getDataConsumerSchemeList().add(dataConsumerSchemeXmBeanlBuilder.build(currentBean));
            }
        }
        if (beans.getDataProviderSchemes().size() > 0) {
            if (orgType == null) {
                orgType = structures.addNewOrganisationSchemes();
            }
            for (DataProviderSchemeBean currentBean : beans.getDataProviderSchemes()) {
                orgType.getDataProviderSchemeList().add(dataProviderSchemeXmlBeanBuilder.build(currentBean));
            }
        }
        if (beans.getAgenciesSchemes().size() > 0) {
            if (orgType == null) {
                orgType = structures.addNewOrganisationSchemes();
            }
            for (AgencySchemeBean currentBean : beans.getAgenciesSchemes()) {
                orgType.getAgencySchemeList().add(agencySchemeXmlBeanBuilder.build(currentBean));
            }
        }
        //CONSTRAINTS
        if (beans.getAttachmentConstraints().size() > 0
                || beans.getContentConstraintBeans().size() > 0) {
            ConstraintsType constraintsType = structures.addNewConstraints();
            for (AttachmentConstraintBean currentBean : beans.getAttachmentConstraints()) {
                constraintsType.getAttachmentConstraintList().add(attachmentConstraintXmlBeanBuilder.build(currentBean));
            }
            for (ContentConstraintBean currentBean : beans.getContentConstraintBeans()) {
                constraintsType.getContentConstraintList().add(contentConstraintXmlBeanBuilder.build(currentBean));
            }
        }

        // DATAFLOWS
        if (beans.getDataflows().size() > 0) {
            DataflowsType dataflowsType = structures.addNewDataflows();
            for (DataflowBean currentBean : beans.getDataflows()) {
                dataflowsType.getDataflowList().add(dataflowXmlBeanBuilderBean.build(currentBean));
            }
        }
        // METADATAFLOWS
        if (beans.getMetadataflows().size() > 0) {
            MetadataflowsType metadataflowsType = structures.addNewMetadataflows();
            for (MetadataFlowBean currentBean : beans.getMetadataflows()) {
                metadataflowsType.getMetadataflowList().add(metadataflowXmlBeanBuilderBean.build(currentBean));
            }
        }
        // CATEGORY SCHEMES
        if (beans.getCategorySchemes().size() > 0) {
            CategorySchemesType catSchemesType = structures.addNewCategorySchemes();
            for (CategorySchemeBean categorySchemeBean : beans.getCategorySchemes()) {
                catSchemesType.getCategorySchemeList().add(categorySchemeXmlBeanBuilderBean.build(categorySchemeBean));
            }
        }
        // CATEGORISATIONS
        if (beans.getCategorisations().size() > 0) {
            CategorisationsType categorisations = structures.addNewCategorisations();
            for (CategorisationBean categorisationBean : beans.getCategorisations()) {
                categorisations.getCategorisationList().add(categorisationXmlBeanBuilder.build(categorisationBean));
            }
        }
        // CODELISTS
        Set<CodelistBean> codelists = beans.getCodelists();
        if (codelists.size() > 0) {
            CodelistsType codelistsType = structures.addNewCodelists();
            CodelistType[] type = new CodelistType[codelists.size()];
            int i = 0;
            for (CodelistBean codelistBean : codelists) {
                type[i] = codelistXmlBeanBuilderBean.build(codelistBean);
                i++;
            }
            codelistsType.setCodelistArray(type);
        }
        // HIERARCHICAL CODELISTS
        if (beans.getHierarchicalCodelists().size() > 0) {
            HierarchicalCodelistsType hierarchicalCodelistsType = structures.addNewHierarchicalCodelists();
            for (HierarchicalCodelistBean currentBean : beans.getHierarchicalCodelists()) {
                hierarchicalCodelistsType.getHierarchicalCodelistList().add(hierarchicalCodelistXmlBuilderBean.build(currentBean));
            }
        }
        // CONCEPTS
        if (beans.getConceptSchemes().size() > 0) {
            ConceptsType conceptsType = structures.addNewConcepts();
            for (ConceptSchemeBean conceptSchemeBean : beans.getConceptSchemes()) {
                conceptsType.getConceptSchemeList().add(conceptSchemeXmlBeanBuilderBean.build(conceptSchemeBean));
            }
        }
        // METADATA STRUCTURE
        if (beans.getMetadataStructures().size() > 0) {
            MetadataStructuresType msdsType = structures.addNewMetadataStructures();
            for (MetadataStructureDefinitionBean currentBean : beans.getMetadataStructures()) {
                msdsType.getMetadataStructureList().add(metadataStructureXmlBeanBuilderBean.build(currentBean));
            }
        }
        // DATA STRUCTURE
        if (beans.getDataStructures().size() > 0) {
            DataStructuresType dataStructuresType = structures.addNewDataStructures();
            for (DataStructureBean currentBean : beans.getDataStructures()) {
                dataStructuresType.getDataStructureList().add(dataStructureXmlBeanBuilderBean.build(currentBean));
            }
        }
        // STRUCTURE SETS
        if (beans.getStructureSets().size() > 0) {
            StructureSetsType structureSetsType = structures.addNewStructureSets();
            for (StructureSetBean currentBean : beans.getStructureSets()) {
                structureSetsType.getStructureSetList().add(structureSetXmlBeanBuilderBean.build(currentBean));
            }
        }
        // REPORTING TAXONOMIES
        if (beans.getReportingTaxonomys().size() > 0) {
            ReportingTaxonomiesType reportingTaxonomiesType = structures.addNewReportingTaxonomies();
            for (ReportingTaxonomyBean currentBean : beans.getReportingTaxonomys()) {
                reportingTaxonomiesType.getReportingTaxonomyList().add(reportingTaxonomyXmlBeanBuilderBean.build(currentBean));
            }
        }
        // PROCESSES
        if (beans.getProcesses().size() > 0) {
            ProcessesType processesType = structures.addNewProcesses();
            for (ProcessBean currentBean : beans.getProcesses()) {
                processesType.getProcessList().add(processXmlBeanBuilderBean.build(currentBean));
            }
        }
        // PROVISION AGREEMENTS
        if (beans.getProvisionAgreements().size() > 0) {
            ProvisionAgreementsType provisionAgreementsType = structures.addNewProvisionAgreements();
            for (ProvisionAgreementBean currentBean : beans.getProvisionAgreements()) {
                provisionAgreementsType.getProvisionAgreementList().add(provisionAgreementXmlBeanBuilder.build(currentBean));
            }
        }
    }
}
