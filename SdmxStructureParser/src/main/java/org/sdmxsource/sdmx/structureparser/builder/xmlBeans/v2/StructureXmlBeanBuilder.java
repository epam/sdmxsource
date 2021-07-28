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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v2;

import org.apache.xmlbeans.XmlObject;
import org.sdmx.resources.sdmxml.schemas.v20.message.HeaderType;
import org.sdmx.resources.sdmxml.schemas.v20.message.StructureDocument;
import org.sdmx.resources.sdmxml.schemas.v20.message.StructureType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.*;
import org.sdmxsource.sdmx.api.builder.Builder;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.base.AgencySchemeBean;
import org.sdmxsource.sdmx.api.model.beans.base.DataConsumerSchemeBean;
import org.sdmxsource.sdmx.api.model.beans.base.DataProviderSchemeBean;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategorisationBean;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategorySchemeBean;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.ReportingTaxonomyBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.HierarchicalCodelistBean;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptBean;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptSchemeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.mapping.StructureSetBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataFlowBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataStructureDefinitionBean;
import org.sdmxsource.sdmx.api.model.beans.process.ProcessBean;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.registry.response.impl.v2.V2Helper;
import org.sdmxsource.sdmx.util.beans.MaintainableUtil;

import java.util.HashSet;
import java.util.Set;


/**
 * The type Structure xml bean builder.
 */
public class StructureXmlBeanBuilder implements Builder<StructureDocument, SdmxBeans> {

    private final CategorySchemeXmlBeanBuilder categorySchemeXmlBeanBuilder = new CategorySchemeXmlBeanBuilder();

    private final CodelistXmlBeanBuilder codelistXmlBeanBuilder = new CodelistXmlBeanBuilder();

    private final ConceptSchemeXmlBeanBuilder conceptSchemeXmlBeanBuilder = new ConceptSchemeXmlBeanBuilder();

    private final ConceptXmlBeanBuilder conceptXmlBeanBuilder = new ConceptXmlBeanBuilder();

    private final DataflowXmlBeanBuilder dataflowXmlBeanBuilder = new DataflowXmlBeanBuilder();

    private final HierarchicalCodelistXmlBeanBuilder hierarchicalCodelistXmlBeanBuilder = new HierarchicalCodelistXmlBeanBuilder();

    private final DataStructureXmlBeanBuilder dataStructureXmlBeanBuilder = new DataStructureXmlBeanBuilder();

    private final MetadataflowXmlBeanBuilder metadataflowXmlBeanBuilder = new MetadataflowXmlBeanBuilder();

    private final MetadataStructureDefinitionXmlBeansBuilder metadataStructureDefinitionXmlBeansBuilder = new MetadataStructureDefinitionXmlBeansBuilder();

    private final OrganisationSchemeXmlBeanBuilder organisationSchemeXmlBeanBuilder = new OrganisationSchemeXmlBeanBuilder();

    private final ProcessXmlBeanBuilder processXmlBeanBuilder = new ProcessXmlBeanBuilder();

    private final ReportingTaxonomyXmlBeanBuilder reportingTaxonomyXmlBeanBuilder = new ReportingTaxonomyXmlBeanBuilder();

    private final StructureSetXmlBeanBuilder structureSetXmlBeanBuilder = new StructureSetXmlBeanBuilder();

    private final StructureHeaderXmlBeanBuilder structureHeaderXmlBeanBuilder = new StructureHeaderXmlBeanBuilder();

    @Override
    public StructureDocument build(SdmxBeans buildFrom) throws SdmxException {
        StructureDocument doc = StructureDocument.Factory.newInstance();

        StructureType returnType = doc.addNewStructure();

        // HEADER
        HeaderType headerType = null;
        if (buildFrom.getHeader() != null) {
            headerType = structureHeaderXmlBeanBuilder.build(buildFrom.getHeader());
            returnType.setHeader(headerType);
        } else {
            headerType = returnType.addNewHeader();
            V2Helper.setHeader(headerType, buildFrom);
        }

        // TOP LEVEL STRUCTURES ELEMENT
        Set<CategorisationBean> categorisations = buildFrom.getCategorisations();

        //GET CATEGORY SCHEMES
        if (buildFrom.getCategorySchemes().size() > 0) {
            CategorySchemesType catSchemesType = returnType.addNewCategorySchemes();
            for (CategorySchemeBean cateogrySchemeBean : buildFrom.getCategorySchemes()) {
                Set<CategorisationBean> matchingCategorisations = new HashSet<CategorisationBean>();
                for (CategorisationBean cat : categorisations) {
                    if (cat.isExternalReference().isTrue()) {
                        continue;
                    }
                    if (MaintainableUtil.match(cateogrySchemeBean, cat.getCategoryReference())) {
                        matchingCategorisations.add(cat);
                    }
                }
                catSchemesType.getCategorySchemeList().add(build(cateogrySchemeBean, categorisations));
            }
        }


        //GET CODELISTS
        if (buildFrom.getCodelists().size() > 0) {
            CodeListsType codeListsType = returnType.addNewCodeLists();
            for (CodelistBean codelistBean : buildFrom.getCodelists()) {
                codeListsType.getCodeListList().add(build(codelistBean));
            }
        }


        //CONCEPT SCHEMES
        if (buildFrom.getConceptSchemes().size() > 0) {
            ConceptsType conceptsType = returnType.addNewConcepts();
            for (ConceptSchemeBean conceptSchemeBean : buildFrom.getConceptSchemes()) {
                conceptsType.getConceptSchemeList().add(build(conceptSchemeBean));
            }
        }


        //DATAFLOWS
        if (buildFrom.getDataflows().size() > 0) {
            DataflowsType dataflowsType = returnType.addNewDataflows();
            for (DataflowBean currentBean : buildFrom.getDataflows()) {
                dataflowsType.getDataflowList().add(build(currentBean, getCategorisations(currentBean, categorisations)));
            }
        }


        //HIERARCIC CODELIST
        if (buildFrom.getHierarchicalCodelists().size() > 0) {
            HierarchicalCodelistsType hierarchicalCodelistsType = returnType.addNewHierarchicalCodelists();
            for (HierarchicalCodelistBean currentBean : buildFrom.getHierarchicalCodelists()) {
                hierarchicalCodelistsType.getHierarchicalCodelistList().add(build(currentBean));
            }
        }


        //KEY FAMILY
        if (buildFrom.getDataStructures().size() > 0) {
            KeyFamiliesType keyFamiliesType = returnType.addNewKeyFamilies();
            for (DataStructureBean currentBean : buildFrom.getDataStructures()) {
                keyFamiliesType.getKeyFamilyList().add(build(currentBean));
            }
        }


        //METADATA FLOW
        if (buildFrom.getMetadataflows().size() > 0) {
            MetadataflowsType metadataflowsType = returnType.addNewMetadataflows();
            for (MetadataFlowBean currentBean : buildFrom.getMetadataflows()) {
                metadataflowsType.getMetadataflowList().add(build(currentBean, getCategorisations(currentBean, categorisations)));
            }
        }

        //METADATA STRUCTURE
        if (buildFrom.getMetadataStructures().size() > 0) {
            MetadataStructureDefinitionsType msdsType = returnType.addNewMetadataStructureDefinitions();
            for (MetadataStructureDefinitionBean currentBean : buildFrom.getMetadataStructures()) {
                msdsType.getMetadataStructureDefinitionList().add(build(currentBean));
            }
        }

        //ORGAISATION SCHEME
        if (buildFrom.getOrganisationUnitSchemes().size() > 0) {
            throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, SDMX_STRUCTURE_TYPE.ORGANISATION_UNIT_SCHEME.getType());
        }

        OrganisationSchemesType orgSchemesType = null;

        //AGENCY SCHEMES
        if (buildFrom.getAgenciesSchemes().size() > 0) {
            orgSchemesType = returnType.addNewOrganisationSchemes();
            for (AgencySchemeBean currentBean : buildFrom.getAgenciesSchemes()) {
                orgSchemesType.getOrganisationSchemeList().add(organisationSchemeXmlBeanBuilder.build(currentBean));
            }
        }

        //DATA CONSUMER SCHEMES
        if (buildFrom.getDataConsumerSchemes().size() > 0) {
            if (orgSchemesType == null) {
                orgSchemesType = returnType.addNewOrganisationSchemes();
            }
            for (DataConsumerSchemeBean currentBean : buildFrom.getDataConsumerSchemes()) {
                orgSchemesType.getOrganisationSchemeList().add(organisationSchemeXmlBeanBuilder.build(currentBean));
            }
        }

        //DATA PROVIDER SCHEMES
        if (buildFrom.getDataProviderSchemes().size() > 0) {
            if (orgSchemesType == null) {
                orgSchemesType = returnType.addNewOrganisationSchemes();
            }
            for (DataProviderSchemeBean currentBean : buildFrom.getDataProviderSchemes()) {
                orgSchemesType.getOrganisationSchemeList().add(organisationSchemeXmlBeanBuilder.build(currentBean));
            }
        }

        //PROCESSES
        if (buildFrom.getProcesses().size() > 0) {
            ProcessesType processesType = returnType.addNewProcesses();
            for (ProcessBean currentBean : buildFrom.getProcesses()) {
                processesType.getProcessList().add(build(currentBean));
            }
        }

        //STRUCTURE SETS
        if (buildFrom.getStructureSets().size() > 0) {
            StructureSetsType structureSetsType = returnType.addNewStructureSets();
            for (StructureSetBean currentBean : buildFrom.getStructureSets()) {
                structureSetsType.getStructureSetList().add(build(currentBean));
            }
        }

        //REPORTING TAXONOMIES
        if (buildFrom.getReportingTaxonomys().size() > 0) {
            ReportingTaxonomiesType reportingTaxonomiesType = returnType.addNewReportingTaxonomies();
            for (ReportingTaxonomyBean currentBean : buildFrom.getReportingTaxonomys()) {
                reportingTaxonomiesType.getReportingTaxonomyList().add(build(currentBean));
            }
        }
        if (buildFrom.getAttachmentConstraints().size() > 0) {
            throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "Attachment Constraint at SMDX v2.0 - please use SDMX v2.1");
        }
        if (buildFrom.getContentConstraintBeans().size() > 0) {
            throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "Content Constraint at SMDX v2.0 - please use SDMX v2.1");
        }
        if (buildFrom.getMetadataStructures().size() > 0) {
            throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "Metadata Structure Definition at SMDX v2.0 - please use SDMX v2.1");
        }
        return doc;
    }

    private Set<CategorisationBean> getCategorisations(MaintainableBean maint, Set<CategorisationBean> categorisations) {
        Set<CategorisationBean> returnSet = new HashSet<CategorisationBean>();
        if (maint.isExternalReference().isTrue()) {
            return returnSet;
        }
        for (CategorisationBean cat : categorisations) {
            if (cat.isExternalReference().isTrue()) {
                continue;
            }
            if (cat.getStructureReference().getTargetReference() == maint.getStructureType()) {
                if (MaintainableUtil.match(maint, cat.getStructureReference())) {
                    returnSet.add(cat);
                }
            }
        }
        return returnSet;
    }

    /**
     * Build the XMLObject if Dataflow will return DataflowType
     *
     * @param bean            - the Bean to build the type for
     * @param categorisations - optional (for v2 only if Maintainable is Dataflow, Metadataflow or Category)
     * @return xml object
     */
    public XmlObject build(MaintainableBean bean, Set<CategorisationBean> categorisations) {
        //FUNC 2.1 support AgencySchemeBean, DataConsumerSchemeBean and DataProviderSchemeBean
        switch (bean.getStructureType()) {
            case CATEGORY_SCHEME:
                return build((CategorySchemeBean) bean, categorisations);
            case CODE_LIST:
                return build((CodelistBean) bean);
            case CONCEPT_SCHEME:
                return build((ConceptSchemeBean) bean);
            case DATAFLOW:
                return build((DataflowBean) bean, categorisations);
            case HIERARCHICAL_CODELIST:
                return build((HierarchicalCodelistBean) bean);
            case DSD:
                return build((DataStructureBean) bean);
            case METADATA_FLOW:
                return build((MetadataFlowBean) bean, categorisations);
            case MSD:
                return build((MetadataStructureDefinitionBean) bean);
            case PROCESS:
                return build((ProcessBean) bean);
            case REPORTING_TAXONOMY:
                return build((ReportingTaxonomyBean) bean);
            case STRUCTURE_SET:
                return build((StructureSetBean) bean);
            default:
                throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, bean.getStructureType());
        }
    }

    /**
     * Build category scheme type.
     *
     * @param bean            the bean
     * @param categorisations the categorisations
     * @return the category scheme type
     */
    public CategorySchemeType build(CategorySchemeBean bean, Set<CategorisationBean> categorisations) {
        return categorySchemeXmlBeanBuilder.build(bean, categorisations);
    }

    /**
     * Build code list type.
     *
     * @param bean the bean
     * @return the code list type
     */
    public CodeListType build(CodelistBean bean) {
        return codelistXmlBeanBuilder.build(bean);
    }

    /**
     * Build concept scheme type.
     *
     * @param bean the bean
     * @return the concept scheme type
     */
    public ConceptSchemeType build(ConceptSchemeBean bean) {
        return conceptSchemeXmlBeanBuilder.build(bean);
    }

    /**
     * Build concept type.
     *
     * @param bean the bean
     * @return the concept type
     */
    public ConceptType build(ConceptBean bean) {
        return conceptXmlBeanBuilder.build(bean);
    }

    /**
     * Build dataflow type.
     *
     * @param bean            the bean
     * @param categorisations the categorisations
     * @return the dataflow type
     */
    public DataflowType build(DataflowBean bean, Set<CategorisationBean> categorisations) {
        return dataflowXmlBeanBuilder.build(bean, categorisations);
    }

    /**
     * Build hierarchical codelist type.
     *
     * @param bean the bean
     * @return the hierarchical codelist type
     */
    public HierarchicalCodelistType build(HierarchicalCodelistBean bean) {
        return hierarchicalCodelistXmlBeanBuilder.build(bean);
    }

    /**
     * Build key family type.
     *
     * @param bean the bean
     * @return the key family type
     */
    public KeyFamilyType build(DataStructureBean bean) {
        return dataStructureXmlBeanBuilder.build(bean);
    }

    /**
     * Build metadataflow type.
     *
     * @param bean            the bean
     * @param categorisations the categorisations
     * @return the metadataflow type
     */
    public MetadataflowType build(MetadataFlowBean bean, Set<CategorisationBean> categorisations) {
        return metadataflowXmlBeanBuilder.build(bean, categorisations);
    }

    /**
     * Build metadata structure definition type.
     *
     * @param bean the bean
     * @return the metadata structure definition type
     */
    public MetadataStructureDefinitionType build(MetadataStructureDefinitionBean bean) {
        return metadataStructureDefinitionXmlBeansBuilder.build(bean);
    }

    /**
     * Build process type.
     *
     * @param bean the bean
     * @return the process type
     */
    public ProcessType build(ProcessBean bean) {
        return processXmlBeanBuilder.build(bean);
    }

    /**
     * Build reporting taxonomy type.
     *
     * @param bean the bean
     * @return the reporting taxonomy type
     */
    public ReportingTaxonomyType build(ReportingTaxonomyBean bean) {
        return reportingTaxonomyXmlBeanBuilder.build(bean);
    }

    /**
     * Build structure set type.
     *
     * @param bean the bean
     * @return the structure set type
     */
    public StructureSetType build(StructureSetBean bean) {
        return structureSetXmlBeanBuilder.build(bean);
    }
}
