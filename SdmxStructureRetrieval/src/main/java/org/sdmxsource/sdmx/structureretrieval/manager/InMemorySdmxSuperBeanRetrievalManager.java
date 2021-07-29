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

import org.sdmxsource.sdmx.api.manager.retrieval.SdmxSuperBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
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
import org.sdmxsource.sdmx.util.beans.container.SuperBeansImpl;

import java.util.Set;


/**
 * The type In memory sdmx super bean retrieval manager.
 */
public class InMemorySdmxSuperBeanRetrievalManager implements SdmxSuperBeanRetrievalManager {

    /**
     * The Super beans.
     */
    protected SuperBeans superBeans;


    /**
     * Minimal constructor
     *
     * @param superBeans the super beans
     */
    public InMemorySdmxSuperBeanRetrievalManager(SuperBeans superBeans) {
        this.superBeans = superBeans;
        if (this.superBeans == null) {
            this.superBeans = new SuperBeansImpl();
        }
    }

    @Override
    public CodelistSuperBean getCodelistSuperBean(MaintainableRefBean ref) {
        return (CodelistSuperBean) SuperBeanRefUtil.resolveReference(superBeans.getCodelists(), ref);
    }

    @Override
    public ConceptSchemeSuperBean getConceptSchemeSuperBean(MaintainableRefBean ref) {
        return (ConceptSchemeSuperBean) SuperBeanRefUtil.resolveReference(superBeans.getConceptSchemes(), ref);
    }

    @Override
    public CategorySchemeSuperBean getCategorySchemeSuperBean(MaintainableRefBean ref) {
        return (CategorySchemeSuperBean) SuperBeanRefUtil.resolveReference(superBeans.getCategorySchemes(), ref);
    }

    @Override
    public DataflowSuperBean getDataflowSuperBean(MaintainableRefBean ref) {
        return (DataflowSuperBean) SuperBeanRefUtil.resolveReference(superBeans.getDataflows(), ref);
    }

    @Override
    public HierarchicalCodelistSuperBean getHierarchicCodeListSuperBean(MaintainableRefBean ref) {
        return (HierarchicalCodelistSuperBean) SuperBeanRefUtil.resolveReference(superBeans.getHierarchicalCodelists(), ref);
    }

    @Override
    public DataStructureSuperBean getDataStructureSuperBean(MaintainableRefBean ref) {
        return (DataStructureSuperBean) SuperBeanRefUtil.resolveReference(superBeans.getDataStructures(), ref);
    }

    @Override
    public MetadataStructureSuperBean getMetadataStructureSuperBean(MaintainableRefBean ref) {
        return (MetadataStructureSuperBean) SuperBeanRefUtil.resolveReference(superBeans.getMetadataStructures(), ref);
    }

    @Override
    public ProvisionAgreementSuperBean getProvisionAgreementSuperBean(MaintainableRefBean ref) {
        return (ProvisionAgreementSuperBean) SuperBeanRefUtil.resolveReference(superBeans.getProvisions(), ref);
    }

    @Override
    public Set<CodelistSuperBean> getCodelistSuperBeans(MaintainableRefBean ref) {
        return new SuperBeanRefUtil<CodelistSuperBean>().resolveReferences(superBeans.getCodelists(), ref);
    }

    @Override
    public Set<ConceptSchemeSuperBean> getConceptSchemeSuperBeans(MaintainableRefBean ref) {
        return new SuperBeanRefUtil<ConceptSchemeSuperBean>().resolveReferences(superBeans.getConceptSchemes(), ref);
    }

    @Override
    public Set<CategorySchemeSuperBean> getCategorySchemeSuperBeans(MaintainableRefBean ref) {
        return new SuperBeanRefUtil<CategorySchemeSuperBean>().resolveReferences(superBeans.getCategorySchemes(), ref);
    }

    @Override
    public Set<DataflowSuperBean> getDataflowSuperBeans(MaintainableRefBean ref) {
        return new SuperBeanRefUtil<DataflowSuperBean>().resolveReferences(superBeans.getDataflows(), ref);
    }

    @Override
    public Set<HierarchicalCodelistSuperBean> getHierarchicCodeListSuperBeans(MaintainableRefBean ref) {
        return new SuperBeanRefUtil<HierarchicalCodelistSuperBean>().resolveReferences(superBeans.getHierarchicalCodelists(), ref);
    }

    @Override
    public Set<DataStructureSuperBean> getDataStructureSuperBeans(MaintainableRefBean ref) {
        return new SuperBeanRefUtil<DataStructureSuperBean>().resolveReferences(superBeans.getDataStructures(), ref);
    }


    @Override
    public Set<MetadataStructureSuperBean> getMetadataStructureSuperBeans(MaintainableRefBean ref) {
        return new SuperBeanRefUtil<MetadataStructureSuperBean>().resolveReferences(superBeans.getMetadataStructures(), ref);
    }

    @Override
    public Set<ProvisionAgreementSuperBean> getProvisionAgreementSuperBeans(MaintainableRefBean ref) {
        return new SuperBeanRefUtil<ProvisionAgreementSuperBean>().resolveReferences(superBeans.getProvisions(), ref);
    }
}
