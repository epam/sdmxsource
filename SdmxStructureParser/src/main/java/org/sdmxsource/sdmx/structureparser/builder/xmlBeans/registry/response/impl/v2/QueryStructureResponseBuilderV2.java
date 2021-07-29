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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans.registry.response.impl.v2;

import org.sdmx.resources.sdmxml.schemas.v20.common.TextType;
import org.sdmx.resources.sdmxml.schemas.v20.message.HeaderType;
import org.sdmx.resources.sdmxml.schemas.v20.message.RegistryInterfaceDocument;
import org.sdmx.resources.sdmxml.schemas.v20.message.RegistryInterfaceType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.QueryStructureResponseType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.StatusMessageType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.StatusType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.*;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
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
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptSchemeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.mapping.StructureSetBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataFlowBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataStructureDefinitionBean;
import org.sdmxsource.sdmx.api.model.beans.process.ProcessBean;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v2.*;
import org.sdmxsource.sdmx.util.beans.MaintainableUtil;
import org.sdmxsource.util.ObjectUtil;

import java.util.HashSet;
import java.util.Set;


/**
 * The type Query structure response builder v 2.
 */
public class QueryStructureResponseBuilderV2 extends AbstractResponseBuilder {

    private final StructureHeaderXmlBeanBuilder headerXmlBeansBuilder = new StructureHeaderXmlBeanBuilder();

    private final CategorySchemeXmlBeanBuilder categorySchemeXmlBeanBuilder = new CategorySchemeXmlBeanBuilder();

    private final CodelistXmlBeanBuilder codelistXmlBeanBuilder = new CodelistXmlBeanBuilder();

    private final ConceptSchemeXmlBeanBuilder conceptSchemeXmlBeanBuilder = new ConceptSchemeXmlBeanBuilder();

    private final DataflowXmlBeanBuilder dataflowXmlBeanBuilder = new DataflowXmlBeanBuilder();

    private final HierarchicalCodelistXmlBeanBuilder hierarchicalCodelistXmlBeanBuilder = new HierarchicalCodelistXmlBeanBuilder();

    private final DataStructureXmlBeanBuilder dataStructureXmlBeanBuilder = new DataStructureXmlBeanBuilder();

    private final MetadataflowXmlBeanBuilder metadataflowXmlBeanBuilder = new MetadataflowXmlBeanBuilder();

    private final MetadataStructureDefinitionXmlBeansBuilder metadataStructureDefinitionXmlBeansBuilder = new MetadataStructureDefinitionXmlBeansBuilder();

    private final OrganisationSchemeXmlBeanBuilder organisationSchemeXmlBeanBuilder = new OrganisationSchemeXmlBeanBuilder();

    private final ProcessXmlBeanBuilder processXmlBeanBuilder = new ProcessXmlBeanBuilder();

    private final ReportingTaxonomyXmlBeanBuilder reportingTaxonomyXmlBeanBuilder = new ReportingTaxonomyXmlBeanBuilder();

    private final StructureSetXmlBeanBuilder structureSetXmlBeanBuilder = new StructureSetXmlBeanBuilder();

    /**
     * Build error response registry interface document.
     *
     * @param th the th
     * @return the registry interface document
     * @throws SdmxException the sdmx exception
     */
    public RegistryInterfaceDocument buildErrorResponse(Throwable th) throws SdmxException {
        RegistryInterfaceDocument responseType = RegistryInterfaceDocument.Factory.newInstance();
        RegistryInterfaceType regInterface = responseType.addNewRegistryInterface();
        QueryStructureResponseType returnType = regInterface.addNewQueryStructureResponse();
        V2Helper.setHeader(regInterface);

        StatusMessageType statusMessage = returnType.addNewStatusMessage();
        statusMessage.setStatus(StatusType.FAILURE);

        TextType tt = statusMessage.addNewMessageText();
        if (th instanceof SdmxException) {
            tt.setStringValue(((SdmxException) th).getFullMessage());
        } else {
            tt.setStringValue(th.getMessage());
        }

        return responseType;
    }

    /**
     * Build success response registry interface document.
     *
     * @param beans the beans
     * @return the registry interface document
     * @throws SdmxException the sdmx exception
     */
    public RegistryInterfaceDocument buildSuccessResponse(SdmxBeans beans) throws SdmxException {
        return buildSuccessResponse(beans, null);
    }

    /**
     * Build success response registry interface document.
     *
     * @param beans          the beans
     * @param warningMessage the warning message
     * @return the registry interface document
     * @throws SdmxException the sdmx exception
     */
    public RegistryInterfaceDocument buildSuccessResponse(SdmxBeans beans, String warningMessage) throws SdmxException {
        RegistryInterfaceDocument responseType = RegistryInterfaceDocument.Factory.newInstance();


        RegistryInterfaceType regInterface = responseType.addNewRegistryInterface();
        QueryStructureResponseType returnType = regInterface.addNewQueryStructureResponse();

        HeaderType headerType = null;

        if (beans.getHeader() != null) {
            headerType = headerXmlBeansBuilder.build(beans.getHeader());
            regInterface.setHeader(headerType);
        } else {
            headerType = regInterface.addNewHeader();
            V2Helper.setHeader(headerType, beans);
        }

        StatusMessageType statusMessage = returnType.addNewStatusMessage();


        if (ObjectUtil.validString(warningMessage) || !ObjectUtil.validCollection(beans.getAllMaintainables())) {
            statusMessage.setStatus(StatusType.WARNING);
            TextType tt = statusMessage.addNewMessageText();
            if (ObjectUtil.validString(warningMessage)) {
                tt.setStringValue(warningMessage);
            } else {
                tt.setStringValue("No Structures Match The Query Parameters");
            }
        } else {
            statusMessage.setStatus(StatusType.SUCCESS);
        }

        Set<CategorisationBean> categorisations = beans.getCategorisations();
        //GET CATEGORY SCHEMES
        if (beans.getCategorySchemes().size() > 0) {
            CategorySchemesType catSchemesType = returnType.addNewCategorySchemes();
            for (CategorySchemeBean cateogrySchemeBean : beans.getCategorySchemes()) {
                Set<CategorisationBean> matchingCategorisations = new HashSet<CategorisationBean>();
                for (CategorisationBean cat : categorisations) {
                    if (MaintainableUtil.match(cateogrySchemeBean, cat.getCategoryReference())) {
                        matchingCategorisations.add(cat);
                    }
                }
                catSchemesType.getCategorySchemeList().add(categorySchemeXmlBeanBuilder.build(cateogrySchemeBean, categorisations));
            }
        }


        //GET CODELISTS
        if (beans.getCodelists().size() > 0) {
            CodeListsType codeListsType = returnType.addNewCodeLists();
            for (CodelistBean codelistBean : beans.getCodelists()) {
                codeListsType.getCodeListList().add(codelistXmlBeanBuilder.build(codelistBean));
            }
        }


        //CONCEPT SCHEMES
        if (beans.getConceptSchemes().size() > 0) {
            ConceptsType conceptsType = returnType.addNewConcepts();
            for (ConceptSchemeBean conceptSchemeBean : beans.getConceptSchemes()) {
                conceptsType.getConceptSchemeList().add(conceptSchemeXmlBeanBuilder.build(conceptSchemeBean));
            }
        }


        //DATAFLOWS
        if (beans.getDataflows().size() > 0) {
            DataflowsType dataflowsType = returnType.addNewDataflows();
            for (DataflowBean currentBean : beans.getDataflows()) {
                dataflowsType.getDataflowList().add(dataflowXmlBeanBuilder.build(currentBean, getCategorisations(currentBean, categorisations)));
            }
        }


        //HIERARCIC CODELIST
        if (beans.getHierarchicalCodelists().size() > 0) {
            HierarchicalCodelistsType hierarchicalCodelistsType = returnType.addNewHierarchicalCodelists();
            for (HierarchicalCodelistBean currentBean : beans.getHierarchicalCodelists()) {
                hierarchicalCodelistsType.getHierarchicalCodelistList().add(hierarchicalCodelistXmlBeanBuilder.build(currentBean));
            }
        }


        //KEY FAMILY
        if (beans.getDataStructures().size() > 0) {
            KeyFamiliesType keyFamiliesType = returnType.addNewKeyFamilies();
            for (DataStructureBean currentBean : beans.getDataStructures()) {
                keyFamiliesType.getKeyFamilyList().add(dataStructureXmlBeanBuilder.build(currentBean));
            }
        }


        //METADATA FLOW
        if (beans.getMetadataflows().size() > 0) {
            MetadataflowsType metadataflowsType = returnType.addNewMetadataflows();
            for (MetadataFlowBean currentBean : beans.getMetadataflows()) {
                metadataflowsType.getMetadataflowList().add(metadataflowXmlBeanBuilder.build(currentBean, getCategorisations(currentBean, categorisations)));
            }
        }

        //METADATA STRUCTURE
        if (beans.getMetadataStructures().size() > 0) {
            MetadataStructureDefinitionsType msdsType = returnType.addNewMetadataStructureDefinitions();
            for (MetadataStructureDefinitionBean currentBean : beans.getMetadataStructures()) {
                msdsType.getMetadataStructureDefinitionList().add(metadataStructureDefinitionXmlBeansBuilder.build(currentBean));
            }
        }
        OrganisationSchemesType orgSchemesType = null;
        //AGENCY SCHEMES
        if (beans.getAgenciesSchemes().size() > 0) {
            if (orgSchemesType == null) {
                orgSchemesType = returnType.addNewOrganisationSchemes();
            }
            for (AgencySchemeBean currentBean : beans.getAgenciesSchemes()) {
                orgSchemesType.getOrganisationSchemeList().add(organisationSchemeXmlBeanBuilder.build(currentBean));
            }
        }

        //DATA CONSUMER SCHEMES
        if (beans.getDataConsumerSchemes().size() > 0) {
            if (orgSchemesType == null) {
                orgSchemesType = returnType.addNewOrganisationSchemes();
            }
            for (DataConsumerSchemeBean currentBean : beans.getDataConsumerSchemes()) {
                orgSchemesType.getOrganisationSchemeList().add(organisationSchemeXmlBeanBuilder.build(currentBean));
            }
        }

        //DATA PROVIDER SCHEMES
        if (beans.getDataProviderSchemes().size() > 0) {
            if (orgSchemesType == null) {
                orgSchemesType = returnType.addNewOrganisationSchemes();
            }
            for (DataProviderSchemeBean currentBean : beans.getDataProviderSchemes()) {
                orgSchemesType.getOrganisationSchemeList().add(organisationSchemeXmlBeanBuilder.build(currentBean));
            }
        }

        //PROCESSES
        if (beans.getProcesses().size() > 0) {
            ProcessesType processesType = returnType.addNewProcesses();
            for (ProcessBean currentBean : beans.getProcesses()) {
                processesType.getProcessList().add(processXmlBeanBuilder.build(currentBean));
            }
        }

        //STRUCTURE SETS
        if (beans.getStructureSets().size() > 0) {
            StructureSetsType structureSetsType = returnType.addNewStructureSets();
            for (StructureSetBean currentBean : beans.getStructureSets()) {
                structureSetsType.getStructureSetList().add(structureSetXmlBeanBuilder.build(currentBean));
            }
        }

        //REPORTING TAXONOMIES
        if (beans.getReportingTaxonomys().size() > 0) {
            ReportingTaxonomiesType reportingTaxonomiesType = returnType.addNewReportingTaxonomies();
            for (ReportingTaxonomyBean currentBean : beans.getReportingTaxonomys()) {
                reportingTaxonomiesType.getReportingTaxonomyList().add(reportingTaxonomyXmlBeanBuilder.build(currentBean));
            }
        }
        if (beans.getAttachmentConstraints().size() > 0) {
            throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "Attachment Constraint at SMDX v2.0 - please use SDMX v2.1");
        }
        if (beans.getContentConstraintBeans().size() > 0) {
            throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "Content Constraint at SMDX v2.0 - please use SDMX v2.1");
        }
        if (beans.getMetadataStructures().size() > 0) {
            throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "Metadata Structure Definition at SMDX v2.0 - please use SDMX v2.1");
        }
        return responseType;
    }

    private Set<CategorisationBean> getCategorisations(MaintainableBean maint, Set<CategorisationBean> categorisations) {
        Set<CategorisationBean> returnSet = new HashSet<CategorisationBean>();
        for (CategorisationBean cat : categorisations) {
            if (cat.getStructureReference().getTargetReference() == maint.getStructureType()) {
                if (MaintainableUtil.match(maint, cat.getStructureReference())) {
                    returnSet.add(cat);
                }
            }
        }
        return returnSet;
    }

}
