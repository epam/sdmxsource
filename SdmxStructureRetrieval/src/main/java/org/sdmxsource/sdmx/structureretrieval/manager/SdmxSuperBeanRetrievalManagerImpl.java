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

import org.sdmxsource.sdmx.api.builder.SuperBeansBuilder;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxSuperBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.ResolutionSettings.RESOLVE_CROSS_REFERENCES;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.superbeans.SuperBeans;
import org.sdmxsource.sdmx.api.model.superbeans.categoryscheme.CategorySchemeSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodelistSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.HierarchicalCodelistSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.conceptscheme.ConceptSchemeSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DataStructureSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DataflowSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.metadata.MetadataStructureSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.registry.ProvisionAgreementSuperBean;
import org.sdmxsource.sdmx.util.beans.SuperBeanRefUtil;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.Set;


/**
 * This implementation of the SdmxSuperBeanRetrievalManager wraps a SdmxBeanRetrievalManager to gather the information required to build SuperBeans.
 */
public class SdmxSuperBeanRetrievalManagerImpl implements SdmxSuperBeanRetrievalManager {

    private final SuperBeansBuilder superBeanBuilder;

    private final SdmxBeanRetrievalManager beanRetrievalManager;

    /**
     * Instantiates a new Sdmx super bean retrieval manager.
     *
     * @param superBeanBuilder     the super bean builder
     * @param beanRetrievalManager the bean retrieval manager
     */
    public SdmxSuperBeanRetrievalManagerImpl(
            final SuperBeansBuilder superBeanBuilder,
            final SdmxBeanRetrievalManager beanRetrievalManager) {
        this.superBeanBuilder = superBeanBuilder;
        this.beanRetrievalManager = beanRetrievalManager;
    }

    /**
     * Returns a set of super beans that match the structure reference.
     *
     * @param sRef
     * @param resolveCrossReferences
     * @return
     */
    private SuperBeans getSuperBeans(StructureReferenceBean sRef) {
        if (superBeanBuilder == null) {
            throw new RuntimeException("'superBeanBuilder' is null");
        }
        SdmxBeans sdmxBeans = beanRetrievalManager.getSdmxBeans(sRef, RESOLVE_CROSS_REFERENCES.RESOLVE_EXCLUDE_AGENCIES);
        return superBeanBuilder.build(sdmxBeans);
    }

    @Override
    public CategorySchemeSuperBean getCategorySchemeSuperBean(MaintainableRefBean ref) {
        Set<CategorySchemeSuperBean> beans = getCategorySchemeSuperBeans(ref);
        if (!ObjectUtil.validCollection(beans)) {
            return null;
        }
        return (CategorySchemeSuperBean) SuperBeanRefUtil.resolveReference(beans, ref);
    }

    @Override
    public Set<CategorySchemeSuperBean> getCategorySchemeSuperBeans(MaintainableRefBean ref) {
        return getSuperBeans(new StructureReferenceBeanImpl(ref, SDMX_STRUCTURE_TYPE.CATEGORY_SCHEME)).getCategorySchemes();
    }


    @Override
    public CodelistSuperBean getCodelistSuperBean(MaintainableRefBean ref) {
        Set<CodelistSuperBean> beans = getCodelistSuperBeans(ref);
        if (!ObjectUtil.validCollection(beans)) {
            return null;
        }
        return (CodelistSuperBean) SuperBeanRefUtil.resolveReference(beans, ref);
    }

    @Override
    public Set<CodelistSuperBean> getCodelistSuperBeans(MaintainableRefBean ref) {
        return getSuperBeans(new StructureReferenceBeanImpl(ref, SDMX_STRUCTURE_TYPE.CODE_LIST)).getCodelists();
    }

    @Override
    public ConceptSchemeSuperBean getConceptSchemeSuperBean(MaintainableRefBean ref) {
        Set<ConceptSchemeSuperBean> beans = getConceptSchemeSuperBeans(ref);
        if (!ObjectUtil.validCollection(beans)) {
            return null;
        }
        return (ConceptSchemeSuperBean) SuperBeanRefUtil.resolveReference(beans, ref);
    }

    @Override
    public Set<ConceptSchemeSuperBean> getConceptSchemeSuperBeans(MaintainableRefBean ref) {
        return getSuperBeans(new StructureReferenceBeanImpl(ref, SDMX_STRUCTURE_TYPE.CONCEPT_SCHEME)).getConceptSchemes();
    }

    @Override
    public DataflowSuperBean getDataflowSuperBean(MaintainableRefBean ref) {
        Set<DataflowSuperBean> beans = getDataflowSuperBeans(ref);
        if (!ObjectUtil.validCollection(beans)) {
            return null;
        }
        return (DataflowSuperBean) SuperBeanRefUtil.resolveReference(beans, ref);
    }

    @Override
    public Set<DataflowSuperBean> getDataflowSuperBeans(MaintainableRefBean ref) {
        return getSuperBeans(new StructureReferenceBeanImpl(ref, SDMX_STRUCTURE_TYPE.DATAFLOW)).getDataflows();
    }

    @Override
    public HierarchicalCodelistSuperBean getHierarchicCodeListSuperBean(MaintainableRefBean ref) {
        Set<HierarchicalCodelistSuperBean> beans = getHierarchicCodeListSuperBeans(ref);
        if (!ObjectUtil.validCollection(beans)) {
            return null;
        }
        return (HierarchicalCodelistSuperBean) SuperBeanRefUtil.resolveReference(beans, ref);
    }

    @Override
    public Set<HierarchicalCodelistSuperBean> getHierarchicCodeListSuperBeans(MaintainableRefBean ref) {
        return getSuperBeans(new StructureReferenceBeanImpl(ref, SDMX_STRUCTURE_TYPE.HIERARCHICAL_CODELIST)).getHierarchicalCodelists();
    }

    @Override
    public DataStructureSuperBean getDataStructureSuperBean(MaintainableRefBean ref) {
        Set<DataStructureSuperBean> beans = getDataStructureSuperBeans(ref);
        if (!ObjectUtil.validCollection(beans)) {
            return null;
        }
        return (DataStructureSuperBean) SuperBeanRefUtil.resolveReference(beans, ref);
    }

    @Override
    public Set<DataStructureSuperBean> getDataStructureSuperBeans(MaintainableRefBean ref) {
        return getSuperBeans(new StructureReferenceBeanImpl(ref, SDMX_STRUCTURE_TYPE.DSD)).getDataStructures();
    }


    @Override
    public MetadataStructureSuperBean getMetadataStructureSuperBean(MaintainableRefBean ref) {
        Set<MetadataStructureSuperBean> beans = getMetadataStructureSuperBeans(ref);
        if (!ObjectUtil.validCollection(beans)) {
            return null;
        }
        return (MetadataStructureSuperBean) SuperBeanRefUtil.resolveReference(beans, ref);
    }

    @Override
    public Set<MetadataStructureSuperBean> getMetadataStructureSuperBeans(MaintainableRefBean ref) {
        return getSuperBeans(new StructureReferenceBeanImpl(ref, SDMX_STRUCTURE_TYPE.MSD)).getMetadataStructures();
    }

    @Override
    public ProvisionAgreementSuperBean getProvisionAgreementSuperBean(MaintainableRefBean ref) {
        Set<ProvisionAgreementSuperBean> beans = getProvisionAgreementSuperBeans(ref);
        if (!ObjectUtil.validCollection(beans)) {
            return null;
        }
        return (ProvisionAgreementSuperBean) SuperBeanRefUtil.resolveReference(beans, ref);
    }

    @Override
    public Set<ProvisionAgreementSuperBean> getProvisionAgreementSuperBeans(MaintainableRefBean ref) {
        return getSuperBeans(new StructureReferenceBeanImpl(ref, SDMX_STRUCTURE_TYPE.PROVISION_AGREEMENT)).getProvisions();
    }
}
