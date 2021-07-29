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
package org.sdmxsource.sdmx.structureretrieval.manager.mutable;

import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.CrossReferenceException;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.manager.retrieval.mutable.SdmxMutableBeanRetrievalManager;
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
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ContentConstraintBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
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
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.categoryscheme.CategorisationMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.categoryscheme.CategorySchemeMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.categoryscheme.ReportingTaxonomyMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.codelist.CodelistMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.codelist.HierarchicalCodelistMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.conceptscheme.ConceptSchemeMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.datastructure.DataStructureMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.mapping.StructureSetMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.metadatastructure.DataflowMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.metadatastructure.MetadataStructureDefinitionMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.metadatastructure.MetadataflowMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.process.ProcessMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.registry.ContentConstraintMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.registry.ProvisionAgreementMutableBeanImpl;
import org.sdmxsource.sdmx.structureretrieval.manager.AbstractRetrevalManager;

import java.util.HashSet;
import java.util.Set;


/**
 * This implementation of the SdmxMutableBeanRetrievalManager wraps a SdmxBeanRetrievalManager and mutates the responses.
 */
public class MutableBeanRetrievalManager extends AbstractRetrevalManager implements SdmxMutableBeanRetrievalManager {

    /**
     * Instantiates a new Mutable bean retrieval manager.
     *
     * @param sdmxBeanRetrievalManager the sdmx bean retrieval manager
     */
    public MutableBeanRetrievalManager(SdmxBeanRetrievalManager sdmxBeanRetrievalManager) {
        super(sdmxBeanRetrievalManager);
    }

    @Override
    public AgencySchemeMutableBean getMutableAgencyScheme(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        AgencySchemeBean bean = sdmxBeanRetrievalManager.getMaintainableBean(AgencySchemeBean.class, ref, returnLatest, returnStub);
        return bean == null ? null : bean.getMutableInstance();
    }

    @Override
    public ContentConstraintMutableBean getMutableContentConstraint(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        ContentConstraintBean bean = sdmxBeanRetrievalManager.getMaintainableBean(ContentConstraintBean.class, ref, returnLatest, returnStub);
        return bean == null ? null : bean.getMutableInstance();
    }

    @Override
    public DataConsumerSchemeMutableBean getMutableDataConsumerScheme(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        DataConsumerSchemeBean bean = sdmxBeanRetrievalManager.getMaintainableBean(DataConsumerSchemeBean.class, ref, returnLatest, returnStub);
        return bean == null ? null : bean.getMutableInstance();
    }

    @Override
    public DataProviderSchemeMutableBean getMutableDataProviderScheme(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        DataProviderSchemeBean bean = sdmxBeanRetrievalManager.getMaintainableBean(DataProviderSchemeBean.class, ref, returnLatest, returnStub);
        return bean == null ? null : bean.getMutableInstance();
    }

    @Override
    public Set<AgencySchemeMutableBean> getMutableAgencySchemeBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        Set<AgencySchemeMutableBean> returnSet = new HashSet<AgencySchemeMutableBean>();
        for (AgencySchemeBean currentBean : sdmxBeanRetrievalManager.getMaintainableBeans(AgencySchemeBean.class, ref)) {
            returnSet.add(currentBean.getMutableInstance());
        }
        return returnSet;
    }

    @Override
    public Set<DataConsumerSchemeMutableBean> getMutableDataConsumerSchemeBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        Set<DataConsumerSchemeMutableBean> returnSet = new HashSet<DataConsumerSchemeMutableBean>();
        for (DataConsumerSchemeBean currentBean : sdmxBeanRetrievalManager.getMaintainableBeans(DataConsumerSchemeBean.class, ref)) {
            returnSet.add(currentBean.getMutableInstance());
        }
        return returnSet;
    }

    @Override
    public Set<DataProviderSchemeMutableBean> getMutableDataProviderSchemeBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        Set<DataProviderSchemeMutableBean> returnSet = new HashSet<DataProviderSchemeMutableBean>();
        for (DataProviderSchemeBean currentBean : sdmxBeanRetrievalManager.getMaintainableBeans(DataProviderSchemeBean.class, ref)) {
            returnSet.add(currentBean.getMutableInstance());
        }
        return returnSet;
    }

    @Override
    public CategorisationMutableBean getMutableCategorisation(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        CategorisationBean bean = sdmxBeanRetrievalManager.getMaintainableBean(CategorisationBean.class, ref, returnLatest, returnStub);
        return bean == null ? null : new CategorisationMutableBeanImpl(bean);
    }

    @Override
    public Set<CategorisationMutableBean> getMutableCategorisationBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        Set<CategorisationBean> beans = sdmxBeanRetrievalManager.getMaintainableBeans(CategorisationBean.class, ref, returnLatest, returnStub);
        Set<CategorisationMutableBean> returnSet = new HashSet<CategorisationMutableBean>();
        for (CategorisationBean csBean : beans) {
            returnSet.add(new CategorisationMutableBeanImpl(csBean));
        }
        return returnSet;
    }

    @Override
    public CategorySchemeMutableBean getMutableCategoryScheme(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        CategorySchemeBean bean = sdmxBeanRetrievalManager.getMaintainableBean(CategorySchemeBean.class, ref, returnLatest, returnStub);
        return bean == null ? null : new CategorySchemeMutableBeanImpl(bean);
    }

    @Override
    public Set<CategorySchemeMutableBean> getMutableCategorySchemeBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        Set<CategorySchemeBean> beans = sdmxBeanRetrievalManager.getMaintainableBeans(CategorySchemeBean.class, ref, returnLatest, returnStub);
        Set<CategorySchemeMutableBean> returnSet = new HashSet<CategorySchemeMutableBean>();
        for (CategorySchemeBean csBean : beans) {
            returnSet.add(new CategorySchemeMutableBeanImpl(csBean));
        }
        return returnSet;
    }

    @Override
    public CodelistMutableBean getMutableCodelist(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        CodelistBean bean = sdmxBeanRetrievalManager.getMaintainableBean(CodelistBean.class, ref, returnLatest, returnStub);
        return bean == null ? null : new CodelistMutableBeanImpl(bean);
    }

    @Override
    public Set<CodelistMutableBean> getMutableCodelistBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        Set<CodelistBean> beans = sdmxBeanRetrievalManager.getMaintainableBeans(CodelistBean.class, ref, returnLatest, returnStub);
        Set<CodelistMutableBean> returnSet = new HashSet<CodelistMutableBean>();
        for (CodelistBean bean : beans) {
            returnSet.add(new CodelistMutableBeanImpl(bean));
        }
        return returnSet;
    }

    @Override
    public ConceptSchemeMutableBean getMutableConceptScheme(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        ConceptSchemeBean bean = sdmxBeanRetrievalManager.getMaintainableBean(ConceptSchemeBean.class, ref, returnLatest, returnStub);
        return bean == null ? null : new ConceptSchemeMutableBeanImpl(bean);
    }

    @Override
    public Set<ConceptSchemeMutableBean> getMutableConceptSchemeBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        Set<ConceptSchemeBean> beans = sdmxBeanRetrievalManager.getMaintainableBeans(ConceptSchemeBean.class, ref, returnLatest, returnStub);
        Set<ConceptSchemeMutableBean> returnSet = new HashSet<ConceptSchemeMutableBean>();
        for (ConceptSchemeBean bean : beans) {
            returnSet.add(new ConceptSchemeMutableBeanImpl(bean));
        }
        return returnSet;
    }

    @Override
    public DataflowMutableBean getMutableDataflow(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        DataflowBean bean = sdmxBeanRetrievalManager.getMaintainableBean(DataflowBean.class, ref, returnLatest, returnStub);
        return bean == null ? null : new DataflowMutableBeanImpl(bean);
    }

    @Override
    public Set<DataflowMutableBean> getMutableDataflowBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        Set<DataflowBean> beans = sdmxBeanRetrievalManager.getMaintainableBeans(DataflowBean.class, ref, returnLatest, returnStub);
        Set<DataflowMutableBean> returnSet = new HashSet<DataflowMutableBean>();
        for (DataflowBean bean : beans) {
            returnSet.add(new DataflowMutableBeanImpl(bean));
        }
        return returnSet;
    }

    @Override
    public HierarchicalCodelistMutableBean getMutableHierarchicCodeList(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        HierarchicalCodelistBean bean = sdmxBeanRetrievalManager.getMaintainableBean(HierarchicalCodelistBean.class, ref, returnLatest, returnStub);
        return bean == null ? null : new HierarchicalCodelistMutableBeanImpl(bean);
    }

    @Override
    public Set<HierarchicalCodelistMutableBean> getMutableHierarchicCodeListBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        Set<HierarchicalCodelistBean> beans = sdmxBeanRetrievalManager.getMaintainableBeans(HierarchicalCodelistBean.class, ref, returnLatest, returnStub);
        Set<HierarchicalCodelistMutableBean> returnSet = new HashSet<HierarchicalCodelistMutableBean>();
        for (HierarchicalCodelistBean bean : beans) {
            returnSet.add(new HierarchicalCodelistMutableBeanImpl(bean));
        }
        return returnSet;
    }

    @Override
    public DataStructureMutableBean getMutableDataStructure(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        DataStructureBean bean = sdmxBeanRetrievalManager.getMaintainableBean(DataStructureBean.class, ref, returnLatest, returnStub);
        return bean == null ? null : new DataStructureMutableBeanImpl(bean);
    }

    @Override
    public Set<DataStructureMutableBean> getMutableDataStructureBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        Set<DataStructureBean> beans = sdmxBeanRetrievalManager.getMaintainableBeans(DataStructureBean.class, ref, returnLatest, returnStub);
        Set<DataStructureMutableBean> returnSet = new HashSet<DataStructureMutableBean>();
        for (DataStructureBean bean : beans) {
            returnSet.add(new DataStructureMutableBeanImpl(bean));
        }
        return returnSet;
    }

    @Override
    public MaintainableMutableBean getMutableMaintainable(StructureReferenceBean query, boolean returnLatest, boolean returnStub) throws CrossReferenceException {
        SDMX_STRUCTURE_TYPE structureType = query.getTargetReference();
        if (!structureType.isMaintainable()) {
            throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, structureType + " is not maintainable");
        }
        MaintainableRefBean ref = query.getMaintainableReference();
        switch (structureType) {
            case AGENCY_SCHEME:
                return getMutableAgencyScheme(ref, returnLatest, returnStub);
            case DATA_PROVIDER_SCHEME:
                return getMutableDataProviderScheme(ref, returnLatest, returnStub);
            case DATA_CONSUMER_SCHEME:
                return getMutableDataConsumerScheme(ref, returnLatest, returnStub);
            case CATEGORISATION:
                return getMutableCategorisation(ref, returnLatest, returnStub);
            case CATEGORY_SCHEME:
                return getMutableCategoryScheme(ref, returnLatest, returnStub);
            case CONTENT_CONSTRAINT:
                return getMutableContentConstraint(ref, returnLatest, returnStub);
            case CODE_LIST:
                return getMutableCodelist(ref, returnLatest, returnStub);
            case CONCEPT_SCHEME:
                return getMutableConceptScheme(ref, returnLatest, returnStub);
            case DATAFLOW:
                return getMutableDataflow(ref, returnLatest, returnStub);
            case HIERARCHICAL_CODELIST:
                return getMutableHierarchicCodeList(ref, returnLatest, returnStub);
            case DSD:
                return getMutableDataStructure(ref, returnLatest, returnStub);
            case METADATA_FLOW:
                return getMutableMetadataflow(ref, returnLatest, returnStub);
            case MSD:
                return getMutableMetadataStructure(ref, returnLatest, returnStub);
            case ORGANISATION_UNIT_SCHEME:
                return getMutableOrganisationUnitScheme(ref, returnLatest, returnStub);
            case PROCESS:
                return getMutableProcessBean(ref, returnLatest, returnStub);
            case PROVISION_AGREEMENT:
                return getMutableProvisionAgreement(ref, returnLatest, returnStub);
            case STRUCTURE_SET:
                return getMutableStructureSet(ref, returnLatest, returnStub);
            case REPORTING_TAXONOMY:
                return getMutableReportingTaxonomy(ref, returnLatest, returnStub);
            default:
                throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, structureType);
        }
    }

    @Override
    public Set<? extends MaintainableMutableBean> getMutableMaintainables(StructureReferenceBean query, boolean returnLatest, boolean returnStub) throws CrossReferenceException {
        SDMX_STRUCTURE_TYPE structureType = query.getTargetReference();
        if (!structureType.isMaintainable()) {
            throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, structureType + " is not maintainable");
        }
        MaintainableRefBean ref = query.getMaintainableReference();
        switch (structureType) {
            case AGENCY_SCHEME:
                return getMutableAgencySchemeBeans(ref, returnLatest, returnStub);
            case DATA_PROVIDER_SCHEME:
                return getMutableDataProviderSchemeBeans(ref, returnLatest, returnStub);
            case DATA_CONSUMER_SCHEME:
                return getMutableDataConsumerSchemeBeans(ref, returnLatest, returnStub);
            case CATEGORISATION:
                return getMutableCategorisationBeans(ref, returnLatest, returnStub);
            case CONTENT_CONSTRAINT:
                return getMutableContentConstraintBeans(ref, returnLatest, returnStub);
            case CATEGORY_SCHEME:
                return getMutableCategorySchemeBeans(ref, returnLatest, returnStub);
            case CODE_LIST:
                return getMutableCodelistBeans(ref, returnLatest, returnStub);
            case CONCEPT_SCHEME:
                return getMutableConceptSchemeBeans(ref, returnLatest, returnStub);
            case DATAFLOW:
                return getMutableDataflowBeans(ref, returnLatest, returnStub);
            case HIERARCHICAL_CODELIST:
                return getMutableHierarchicCodeListBeans(ref, returnLatest, returnStub);
            case DSD:
                return getMutableDataStructureBeans(ref, returnLatest, returnStub);
            case METADATA_FLOW:
                return getMutableMetadataflowBeans(ref, returnLatest, returnStub);
            case MSD:
                return getMutableMetadataStructureBeans(ref, returnLatest, returnStub);
            case ORGANISATION_UNIT_SCHEME:
                return getMutableOrganisationUnitSchemeBeans(ref, returnLatest, returnStub);
            case PROVISION_AGREEMENT:
                return getMutableProvisionAgreementBeans(ref, returnLatest, returnStub);
            case PROCESS:
                return getMutableProcessBeanBeans(ref, returnLatest, returnStub);
            case STRUCTURE_SET:
                return getMutableStructureSetBeans(ref, returnLatest, returnStub);
            case REPORTING_TAXONOMY:
                return getMutableReportingTaxonomyBeans(ref, returnLatest, returnStub);
            default:
                throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, structureType);
        }
    }


    @Override
    public ProvisionAgreementMutableBean getMutableProvisionAgreement(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        ProvisionAgreementBean bean = sdmxBeanRetrievalManager.getMaintainableBean(ProvisionAgreementBean.class, ref, returnLatest, returnStub);
        return bean == null ? null : bean.getMutableInstance();
    }

    @Override
    public Set<ProvisionAgreementMutableBean> getMutableProvisionAgreementBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        Set<ProvisionAgreementBean> beans = sdmxBeanRetrievalManager.getMaintainableBeans(ProvisionAgreementBean.class, ref, returnLatest, returnStub);
        Set<ProvisionAgreementMutableBean> returnSet = new HashSet<ProvisionAgreementMutableBean>();
        for (ProvisionAgreementBean bean : beans) {
            returnSet.add(new ProvisionAgreementMutableBeanImpl(bean));
        }
        return returnSet;
    }

    @Override
    public Set<ContentConstraintMutableBean> getMutableContentConstraintBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        Set<ContentConstraintBean> beans = sdmxBeanRetrievalManager.getMaintainableBeans(ContentConstraintBean.class, ref, returnLatest, returnStub);
        Set<ContentConstraintMutableBean> returnSet = new HashSet<ContentConstraintMutableBean>();
        for (ContentConstraintBean bean : beans) {
            returnSet.add(new ContentConstraintMutableBeanImpl(bean));
        }
        return returnSet;
    }

    @Override
    public MetadataStructureDefinitionMutableBean getMutableMetadataStructure(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        MetadataStructureDefinitionBean bean = sdmxBeanRetrievalManager.getMaintainableBean(MetadataStructureDefinitionBean.class, ref, returnLatest, returnStub);
        return bean == null ? null : new MetadataStructureDefinitionMutableBeanImpl(bean);
    }

    @Override
    public Set<MetadataStructureDefinitionMutableBean> getMutableMetadataStructureBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        Set<MetadataStructureDefinitionBean> beans = sdmxBeanRetrievalManager.getMaintainableBeans(MetadataStructureDefinitionBean.class, ref, returnLatest, returnStub);
        Set<MetadataStructureDefinitionMutableBean> returnSet = new HashSet<MetadataStructureDefinitionMutableBean>();
        for (MetadataStructureDefinitionBean bean : beans) {
            returnSet.add(new MetadataStructureDefinitionMutableBeanImpl(bean));
        }
        return returnSet;
    }

    @Override
    public MetadataFlowMutableBean getMutableMetadataflow(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        MetadataFlowBean bean = sdmxBeanRetrievalManager.getMaintainableBean(MetadataFlowBean.class, ref, returnLatest, returnStub);
        return bean == null ? null : new MetadataflowMutableBeanImpl(bean);
    }

    @Override
    public Set<MetadataFlowMutableBean> getMutableMetadataflowBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        Set<MetadataFlowBean> beans = sdmxBeanRetrievalManager.getMaintainableBeans(MetadataFlowBean.class, ref, returnLatest, returnStub);
        Set<MetadataFlowMutableBean> returnSet = new HashSet<MetadataFlowMutableBean>();
        for (MetadataFlowBean bean : beans) {
            returnSet.add(new MetadataflowMutableBeanImpl(bean));
        }
        return returnSet;
    }

    @Override
    public OrganisationUnitSchemeMutableBean getMutableOrganisationUnitScheme(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        OrganisationUnitSchemeBean bean = sdmxBeanRetrievalManager.getMaintainableBean(OrganisationUnitSchemeBean.class, ref, returnLatest, returnStub);
        return bean == null ? null : bean.getMutableInstance();
    }

    @Override
    public Set<OrganisationUnitSchemeMutableBean> getMutableOrganisationUnitSchemeBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        Set<OrganisationUnitSchemeBean> beans = sdmxBeanRetrievalManager.getMaintainableBeans(OrganisationUnitSchemeBean.class, ref, returnLatest, returnStub);
        Set<OrganisationUnitSchemeMutableBean> returnSet = new HashSet<OrganisationUnitSchemeMutableBean>();
        for (OrganisationUnitSchemeBean bean : beans) {
            returnSet.add(bean.getMutableInstance());
        }
        return returnSet;
    }

    @Override
    public ProcessMutableBean getMutableProcessBean(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        ProcessBean bean = sdmxBeanRetrievalManager.getMaintainableBean(ProcessBean.class, ref, returnLatest, returnStub);
        return bean == null ? null : new ProcessMutableBeanImpl(bean);
    }

    @Override
    public Set<ProcessMutableBean> getMutableProcessBeanBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        Set<ProcessBean> beans = sdmxBeanRetrievalManager.getMaintainableBeans(ProcessBean.class, ref, returnLatest, returnStub);
        Set<ProcessMutableBean> returnSet = new HashSet<ProcessMutableBean>();
        for (ProcessBean bean : beans) {
            returnSet.add(new ProcessMutableBeanImpl(bean));
        }
        return returnSet;
    }

    @Override
    public ReportingTaxonomyMutableBean getMutableReportingTaxonomy(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        ReportingTaxonomyBean bean = sdmxBeanRetrievalManager.getMaintainableBean(ReportingTaxonomyBean.class, ref, returnLatest, returnStub);
        return bean == null ? null : new ReportingTaxonomyMutableBeanImpl(bean);
    }

    @Override
    public Set<ReportingTaxonomyMutableBean> getMutableReportingTaxonomyBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        Set<ReportingTaxonomyBean> beans = sdmxBeanRetrievalManager.getMaintainableBeans(ReportingTaxonomyBean.class, ref, returnLatest, returnStub);
        Set<ReportingTaxonomyMutableBean> returnSet = new HashSet<ReportingTaxonomyMutableBean>();
        for (ReportingTaxonomyBean bean : beans) {
            returnSet.add(new ReportingTaxonomyMutableBeanImpl(bean));
        }
        return returnSet;
    }

    @Override
    public StructureSetMutableBean getMutableStructureSet(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        StructureSetBean bean = sdmxBeanRetrievalManager.getMaintainableBean(StructureSetBean.class, ref, returnLatest, returnStub);
        return bean == null ? null : new StructureSetMutableBeanImpl(bean);
    }

    @Override
    public Set<StructureSetMutableBean> getMutableStructureSetBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        Set<StructureSetBean> beans = sdmxBeanRetrievalManager.getMaintainableBeans(StructureSetBean.class, ref, returnLatest, returnStub);
        Set<StructureSetMutableBean> returnSet = new HashSet<StructureSetMutableBean>();
        for (StructureSetBean bean : beans) {
            returnSet.add(new StructureSetMutableBeanImpl(bean));
        }
        return returnSet;
    }
}
