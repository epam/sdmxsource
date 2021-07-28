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
package org.sdmxsource.sdmx.structureparser.builder.sdmxbeans.impl;

import org.sdmx.resources.sdmxml.schemas.v21.common.ActionType;
import org.sdmx.resources.sdmxml.schemas.v21.message.BaseHeaderType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.*;
import org.sdmxsource.sdmx.api.constants.DATASET_ACTION;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.MaintainableBeanException;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategorisationBean;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategorySchemeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataFlowBean;
import org.sdmxsource.sdmx.api.model.header.HeaderBean;
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
import org.sdmxsource.sdmx.sdmxbeans.model.beans.registry.AttachmentConstraintBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.registry.ContentConstraintBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.registry.ProvisionAgreementBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.header.HeaderBeanImpl;
import org.sdmxsource.sdmx.util.beans.container.SdmxBeansImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.HashSet;
import java.util.Set;


/**
 * The type Sdmx beans v 21 builder.
 */
public class SdmxBeansV21Builder extends AbstractSdmxBeansBuilder {
///////////////////////////////////////////////////////////////////////////////////////////////
    //////////			PROCESS 2.1  MESSAGES						///////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Build beans from a v2.1 Registry Document
     *
     * @param rid the rid
     * @return the sdmx beans
     */
    public SdmxBeans build(org.sdmx.resources.sdmxml.schemas.v21.message.RegistryInterfaceDocument rid) {
        org.sdmx.resources.sdmxml.schemas.v21.message.RegistryInterfaceType rit = rid.getRegistryInterface();
        if (rit.getSubmitStructureRequest() != null) {
            if (rit.getSubmitStructureRequest().getStructures() != null) {
                DATASET_ACTION action = null;
                if (rit.getSubmitStructureRequest().getAction() != null) {
                    if (rit.getSubmitStructureRequest().getAction() == ActionType.APPEND) {
                        action = DATASET_ACTION.APPEND;
                    } else if (rit.getSubmitStructureRequest().getAction() == ActionType.REPLACE) {
                        action = DATASET_ACTION.REPLACE;
                    } else if (rit.getSubmitStructureRequest().getAction() == ActionType.DELETE) {
                        action = DATASET_ACTION.DELETE;
                    }
                }
                return build(rit.getSubmitStructureRequest().getStructures(), processHeader(rit.getHeader()), action);
            }
        }
        return new SdmxBeansImpl();
    }

    /**
     * Build beans from a v2.1 Structure Document
     *
     * @param structuresDoc the structures doc
     * @return the sdmx beans
     */
    public SdmxBeans build(org.sdmx.resources.sdmxml.schemas.v21.message.StructureDocument structuresDoc) {
        org.sdmx.resources.sdmxml.schemas.v21.message.StructureType structures = structuresDoc.getStructure();
        if (structures != null && structures.getStructures() != null) {
            return build(structures.getStructures(), processHeader(structures.getHeader()), null);
        }

        return new SdmxBeansImpl();
    }

    private HeaderBean processHeader(BaseHeaderType baseHeaderType) {
        return new HeaderBeanImpl(baseHeaderType);
    }

    /**
     * Build beans from v2.1 structures
     *
     * @param structures
     * @return beans container of all beans built
     */
    private SdmxBeans build(StructuresType structures, HeaderBean header, DATASET_ACTION action) {
        SdmxBeansImpl beans = new SdmxBeansImpl(header, action);
        processOrganisationSchemes(structures.getOrganisationSchemes(), beans);
        processDataflows(structures.getDataflows(), beans);
        processMetadataFlows(structures.getMetadataflows(), beans);
        processCategorySchemes(structures.getCategorySchemes(), beans);
        processCategorisations(structures.getCategorisations(), beans);
        processCodelists(structures.getCodelists(), beans);
        processHierarchicalCodelists(structures.getHierarchicalCodelists(), beans);
        processConcepts(structures.getConcepts(), beans);
        processMetadataStructures(structures.getMetadataStructures(), beans);
        processDataStructures(structures.getDataStructures(), beans);
        processStructureSets(structures.getStructureSets(), beans);
        processReportingTaxonomies(structures.getReportingTaxonomies(), beans);
        processProcesses(structures.getProcesses(), beans);
        processConstraints(structures.getConstraints(), beans);
        processProvisions(structures.getProvisionAgreements(), beans);
        return beans;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    //////////			VERSION 2.1 METHODS FOR STRUCTURES      	///////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Creates organisation schemes and agencies based on the input organisation schemes
     *
     * @param orgSchemesType - if null will not add anything to the beans container
     * @param beans          - to add organisation schemes and agencies to
     */
    private void processOrganisationSchemes(org.sdmx.resources.sdmxml.schemas.v21.structure.OrganisationSchemesType orgSchemesType, SdmxBeans beans) {
        Set<String> urns = new HashSet<String>();
        if (orgSchemesType != null) {
            if (ObjectUtil.validCollection(orgSchemesType.getAgencySchemeList())) {
                for (AgencySchemeType currentType : orgSchemesType.getAgencySchemeList()) {
                    try {
                        addIfNotDuplicateURN(beans, urns, new AgencySchemeBeanImpl(currentType));
                    } catch (Throwable th) {
                        throw new MaintainableBeanException(th, SDMX_STRUCTURE_TYPE.AGENCY_SCHEME, currentType.getAgencyID(), currentType.getId(), currentType.getVersion());
                    }
                }
            }
            if (ObjectUtil.validCollection(orgSchemesType.getDataProviderSchemeList())) {
                for (DataProviderSchemeType currentType : orgSchemesType.getDataProviderSchemeList()) {
                    try {
                        addIfNotDuplicateURN(beans, urns, new DataProviderSchemeBeanImpl(currentType));
                    } catch (Throwable th) {
                        throw new MaintainableBeanException(th, SDMX_STRUCTURE_TYPE.DATA_PROVIDER_SCHEME, currentType.getAgencyID(), currentType.getId(), currentType.getVersion());
                    }
                }
            }
            if (ObjectUtil.validCollection(orgSchemesType.getDataConsumerSchemeList())) {
                for (DataConsumerSchemeType currentType : orgSchemesType.getDataConsumerSchemeList()) {
                    try {
                        addIfNotDuplicateURN(beans, urns, new DataConsumerSchemeBeanImpl(currentType));
                    } catch (Throwable th) {
                        throw new MaintainableBeanException(th, SDMX_STRUCTURE_TYPE.DATA_CONSUMER_SCHEME, currentType.getAgencyID(), currentType.getId(), currentType.getVersion());
                    }
                }
            }
            if (ObjectUtil.validCollection(orgSchemesType.getOrganisationUnitSchemeList())) {
                for (OrganisationUnitSchemeType currentType : orgSchemesType.getOrganisationUnitSchemeList()) {
                    try {
                        addIfNotDuplicateURN(beans, urns, new OrganisationUnitSchemeBeanImpl(currentType));
                    } catch (Throwable th) {
                        throw new MaintainableBeanException(th, SDMX_STRUCTURE_TYPE.ORGANISATION_UNIT_SCHEME, currentType.getAgencyID(), currentType.getId(), currentType.getVersion());
                    }
                }
            }
        }
    }

    /**
     * Creates dataflows and categorisations based on the input dataflows
     *
     * @param dfType - if null will not add anything to the beans container
     * @param beans  - to add dataflows to beans
     */
    private void processDataflows(org.sdmx.resources.sdmxml.schemas.v21.structure.DataflowsType dfType, SdmxBeans beans) {
        Set<String> urns = new HashSet<String>();
        if (dfType != null && ObjectUtil.validCollection(dfType.getDataflowList())) {
            for (org.sdmx.resources.sdmxml.schemas.v21.structure.DataflowType currentType : dfType.getDataflowList()) {
                try {
                    DataflowBean currentDataflow = new DataflowBeanImpl(currentType);
                    addIfNotDuplicateURN(beans, urns, currentDataflow);
                } catch (Throwable th) {
                    throw new MaintainableBeanException(th, SDMX_STRUCTURE_TYPE.DATAFLOW, currentType.getAgencyID(), currentType.getId(), currentType.getVersion());
                }
            }
        }
    }

    /**
     * Creates metadataflows on the input metadataflows
     *
     * @param mdfType - if null will not add anything to the beans container
     * @param beans   - to add metadataflow to beans
     */
    private void processMetadataFlows(org.sdmx.resources.sdmxml.schemas.v21.structure.MetadataflowsType mdfType, SdmxBeans beans) {
        Set<String> urns = new HashSet<String>();
        if (mdfType != null && ObjectUtil.validCollection(mdfType.getMetadataflowList())) {
            for (org.sdmx.resources.sdmxml.schemas.v21.structure.MetadataflowType currentType : mdfType.getMetadataflowList()) {
                try {
                    MetadataFlowBean currentMetadataflow = new MetadataflowBeanImpl(currentType);
                    addIfNotDuplicateURN(beans, urns, currentMetadataflow);
                } catch (Throwable th) {
                    throw new MaintainableBeanException(th, SDMX_STRUCTURE_TYPE.METADATA_FLOW, currentType.getAgencyID(), currentType.getId(), currentType.getVersion());
                }
            }
        }
    }

    /**
     * Creates category schemes based on the input category schemes
     *
     * @param catSchemes - if null will not add anything to the beans container
     * @param beans      - to add category schemes to
     */
    private void processCategorySchemes(org.sdmx.resources.sdmxml.schemas.v21.structure.CategorySchemesType catSchemes, SdmxBeans beans) {
        Set<String> urns = new HashSet<String>();
        if (catSchemes != null && ObjectUtil.validCollection(catSchemes.getCategorySchemeList())) {
            for (org.sdmx.resources.sdmxml.schemas.v21.structure.CategorySchemeType currentCatScheme : catSchemes.getCategorySchemeList()) {
                try {
                    CategorySchemeBean csBean = new CategorySchemeBeanImpl(currentCatScheme);
                    addIfNotDuplicateURN(beans, urns, csBean);
                } catch (Throwable th) {
                    throw new MaintainableBeanException(th, SDMX_STRUCTURE_TYPE.CATEGORY_SCHEME, currentCatScheme.getAgencyID(), currentCatScheme.getId(), currentCatScheme.getVersion());
                }
            }
        }
    }

    /**
     * Creates categorisations based on the input categorisation schemes
     *
     * @param categorisations - if null will not add anything to the beans container
     * @param beans           - to add categorisations to
     */
    private void processCategorisations(org.sdmx.resources.sdmxml.schemas.v21.structure.CategorisationsType categorisations, SdmxBeans beans) {
        Set<String> urns = new HashSet<String>();
        if (categorisations != null && ObjectUtil.validCollection(categorisations.getCategorisationList())) {
            for (CategorisationType currentCategorisation : categorisations.getCategorisationList()) {
                try {
                    CategorisationBean csBean = new CategorisationBeanImpl(currentCategorisation);
                    addIfNotDuplicateURN(beans, urns, csBean);
                } catch (Throwable th) {
                    throw new MaintainableBeanException(th, SDMX_STRUCTURE_TYPE.CATEGORISATION, currentCategorisation.getAgencyID(), currentCategorisation.getId(), currentCategorisation.getVersion());
                }
            }
        }
    }

    /**
     * Creates Codelists based on the input Codelist schemes
     *
     * @param codelists - if null will not add anything to the beans container
     * @param beans     - to add codelists to
     */
    private void processCodelists(CodelistsType codelists, SdmxBeans beans) {
        Set<String> urns = new HashSet<String>();
        if (codelists != null && codelists.getCodelistList() != null) {
            for (CodelistType currentType : codelists.getCodelistList()) {
                try {
                    addIfNotDuplicateURN(beans, urns, new CodelistBeanImpl(currentType));
                } catch (Throwable th) {
                    throw new MaintainableBeanException(th, SDMX_STRUCTURE_TYPE.CODE_LIST, currentType.getAgencyID(), currentType.getId(), currentType.getVersion());
                }
            }
        }
    }

    /**
     * Creates HierarchicalCodelists based on the input HierarchicalCodelist schemes
     *
     * @param hcodelists - if null will not add anything to the beans container
     * @param beans      - to add hierarchical codelists to
     */
    private void processHierarchicalCodelists(HierarchicalCodelistsType hcodelists, SdmxBeans beans) {
        Set<String> urns = new HashSet<String>();
        if (hcodelists != null && hcodelists.getHierarchicalCodelistList() != null) {
            for (org.sdmx.resources.sdmxml.schemas.v21.structure.HierarchicalCodelistType currentType : hcodelists.getHierarchicalCodelistList()) {
                try {
                    addIfNotDuplicateURN(beans, urns, new HierarchicalCodelistBeanImpl(currentType));
                } catch (Throwable th) {
                    throw new MaintainableBeanException(th, SDMX_STRUCTURE_TYPE.HIERARCHICAL_CODELIST, currentType.getAgencyID(), currentType.getId(), currentType.getVersion());
                }
            }
        }
    }

    /**
     * Creates ConceptSchemes based on the input Concepts
     *
     * @param concepts - if null will not add anything to the beans container
     * @param beans    - to add concepts to
     */
    private void processConcepts(ConceptsType concepts, SdmxBeans beans) {
        Set<String> urns = new HashSet<String>();
        if (concepts != null && concepts.getConceptSchemeList() != null) {
            for (org.sdmx.resources.sdmxml.schemas.v21.structure.ConceptSchemeType currentType : concepts.getConceptSchemeList()) {
                try {
                    addIfNotDuplicateURN(beans, urns, new ConceptSchemeBeanImpl(currentType));
                } catch (Throwable th) {
                    throw new MaintainableBeanException(th, SDMX_STRUCTURE_TYPE.CONCEPT_SCHEME, currentType.getAgencyID(), currentType.getId(), currentType.getVersion());
                }
            }
        }
    }

    /**
     * Creates MetadataStructures based on the input MetadataStructures
     *
     * @param metadataStructures - if null will not add anything to the beans container
     * @param beans              - to add concepts to
     */
    private void processMetadataStructures(MetadataStructuresType metadataStructures, SdmxBeans beans) {
        Set<String> urns = new HashSet<String>();
        if (metadataStructures != null && metadataStructures.getMetadataStructureList() != null) {
            for (MetadataStructureType currentType : metadataStructures.getMetadataStructureList()) {
                try {
                    addIfNotDuplicateURN(beans, urns, new MetadataStructureDefinitionBeanImpl(currentType));
                } catch (Throwable th) {
                    throw new MaintainableBeanException(th, SDMX_STRUCTURE_TYPE.MSD, currentType.getAgencyID(), currentType.getId(), currentType.getVersion());
                }
            }
        }
    }

    /**
     * Creates DataStructures based on the input DataStructures
     *
     * @param keyfamilies - if null will not add anything to the beans container
     * @param beans       - to add concepts to
     */
    private void processDataStructures(DataStructuresType keyfamilies, SdmxBeans beans) {
        Set<String> urns = new HashSet<String>();
        if (keyfamilies != null && keyfamilies.getDataStructureList() != null) {
            for (DataStructureType currentType : keyfamilies.getDataStructureList()) {
                try {
                    addIfNotDuplicateURN(beans, urns, new DataStructureBeanImpl(currentType));
                } catch (Throwable th) {
                    throw new MaintainableBeanException(th, SDMX_STRUCTURE_TYPE.DSD, currentType.getAgencyID(), currentType.getId(), currentType.getVersion());
                }
            }
        }
    }

    /**
     * Creates StructureSets based on the input StructureSets
     *
     * @param structureSets - if null will not add anything to the beans container
     * @param beans         - to add concepts to
     */
    private void processStructureSets(StructureSetsType structureSets, SdmxBeans beans) {
        Set<String> urns = new HashSet<String>();
        if (structureSets != null && structureSets.getStructureSetList() != null) {
            for (org.sdmx.resources.sdmxml.schemas.v21.structure.StructureSetType currentType : structureSets.getStructureSetList()) {
                try {
                    addIfNotDuplicateURN(beans, urns, new StructureSetBeanImpl(currentType));
                } catch (Throwable th) {
                    throw new MaintainableBeanException(th, SDMX_STRUCTURE_TYPE.STRUCTURE_SET, currentType.getAgencyID(), currentType.getId(), currentType.getVersion());
                }
            }
        }
    }

    /**
     * Creates ReportingTaxonomies based on the input ReportingTaxonomies
     *
     * @param reportingTaxonomies - if null will not add anything to the beans container
     * @param beans               - to add concepts to
     */
    private void processReportingTaxonomies(ReportingTaxonomiesType reportingTaxonomies, SdmxBeans beans) {
        Set<String> urns = new HashSet<String>();
        if (reportingTaxonomies != null && reportingTaxonomies.getReportingTaxonomyList() != null) {
            for (org.sdmx.resources.sdmxml.schemas.v21.structure.ReportingTaxonomyType currentType : reportingTaxonomies.getReportingTaxonomyList()) {
                try {
                    addIfNotDuplicateURN(beans, urns, new ReportingTaxonomyBeanImpl(currentType));
                } catch (Throwable th) {
                    throw new MaintainableBeanException(th, SDMX_STRUCTURE_TYPE.REPORTING_TAXONOMY, currentType.getAgencyID(), currentType.getId(), currentType.getVersion());
                }
            }
        }
    }

    /**
     * Creates Processes based on the input Processes
     *
     * @param processes - if null will not add anything to the beans container
     * @param beans     - to add concepts to
     */
    private void processProcesses(org.sdmx.resources.sdmxml.schemas.v21.structure.ProcessesType processes, SdmxBeans beans) {
        Set<String> urns = new HashSet<String>();
        if (processes != null && processes.getProcessList() != null) {
            for (org.sdmx.resources.sdmxml.schemas.v21.structure.ProcessType currentType : processes.getProcessList()) {
                try {
                    addIfNotDuplicateURN(beans, urns, new ProcessBeanImpl(currentType));
                } catch (Throwable th) {
                    throw new MaintainableBeanException(th, SDMX_STRUCTURE_TYPE.PROCESS, currentType.getAgencyID(), currentType.getId(), currentType.getVersion());
                }
            }
        }
    }

    /**
     * Creates Constraints based on the input Constraints
     *
     * @param constraints - if null will not add anything to the beans container
     * @param beans       - to add concepts to
     */
    private void processConstraints(ConstraintsType constraints, SdmxBeans beans) {
        Set<String> urns = new HashSet<String>();
        if (constraints != null) {
            for (AttachmentConstraintType currentType : constraints.getAttachmentConstraintList()) {
                try {
                    addIfNotDuplicateURN(beans, urns, new AttachmentConstraintBeanImpl(currentType));
                } catch (Throwable th) {
                    throw new MaintainableBeanException(th, SDMX_STRUCTURE_TYPE.ATTACHMENT_CONSTRAINT, currentType.getAgencyID(), currentType.getId(), currentType.getVersion());
                }
            }
            for (ContentConstraintType currentType : constraints.getContentConstraintList()) {
                try {
                    addIfNotDuplicateURN(beans, urns, new ContentConstraintBeanImpl(currentType));
                } catch (Throwable th) {
                    throw new MaintainableBeanException(th, SDMX_STRUCTURE_TYPE.CONTENT_CONSTRAINT, currentType.getAgencyID(), currentType.getId(), currentType.getVersion());
                }
            }
        }
    }

    private void processProvisions(ProvisionAgreementsType provisions, SdmxBeans beans) {
        Set<String> urns = new HashSet<String>();
        if (provisions != null && ObjectUtil.validCollection(provisions.getProvisionAgreementList())) {
            for (ProvisionAgreementType currentType : provisions.getProvisionAgreementList()) {
                try {
                    addIfNotDuplicateURN(beans, urns, new ProvisionAgreementBeanImpl(currentType));
                } catch (Throwable th) {
                    throw new MaintainableBeanException(th, SDMX_STRUCTURE_TYPE.PROVISION_AGREEMENT, currentType.getAgencyID(), currentType.getId(), currentType.getVersion());
                }
            }
        }
    }
}
