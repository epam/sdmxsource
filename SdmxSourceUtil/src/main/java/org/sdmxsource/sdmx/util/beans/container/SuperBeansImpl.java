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
package org.sdmxsource.sdmx.util.beans.container;

import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.model.superbeans.SuperBeans;
import org.sdmxsource.sdmx.api.model.superbeans.base.MaintainableSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.categoryscheme.CategorySchemeSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodelistSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.HierarchicalCodelistSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.conceptscheme.ConceptSchemeSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DataStructureSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DataflowSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.metadata.MetadataStructureSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.process.ProcessSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.registry.ProvisionAgreementSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.registry.RegistrationSuperBean;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


/**
 * The type Super beans.
 */
public class SuperBeansImpl implements SuperBeans {
    private Set<CategorySchemeSuperBean> categorySchemes = new HashSet<CategorySchemeSuperBean>();
    private Set<CodelistSuperBean> codelists = new HashSet<CodelistSuperBean>();
    private Set<ConceptSchemeSuperBean> conceptSchemes = new HashSet<ConceptSchemeSuperBean>();
    private Set<DataflowSuperBean> dataflows = new HashSet<DataflowSuperBean>();
    private Set<HierarchicalCodelistSuperBean> hcls = new HashSet<HierarchicalCodelistSuperBean>();
    private Set<DataStructureSuperBean> dataStructures = new HashSet<DataStructureSuperBean>();
    private Set<MetadataStructureSuperBean> msdSuperBean = new HashSet<MetadataStructureSuperBean>();
    private Set<ProvisionAgreementSuperBean> provisionAgreement = new HashSet<ProvisionAgreementSuperBean>();
    private Set<ProcessSuperBean> processes = new HashSet<ProcessSuperBean>();
    private Set<RegistrationSuperBean> registrations = new HashSet<RegistrationSuperBean>();

    /**
     * Instantiates a new Super beans.
     */
    public SuperBeansImpl() {
    }

    /**
     * Instantiates a new Super beans.
     *
     * @param allBeans the all beans
     */
    public SuperBeansImpl(Collection<MaintainableSuperBean> allBeans) {
        if (allBeans != null) {
            for (MaintainableSuperBean currentBean : allBeans) {
                addMaintainable(currentBean);
            }
        }
    }

    /**
     * Instantiates a new Super beans.
     *
     * @param beans the beans
     */
    public SuperBeansImpl(SuperBeans... beans) {
        for (SuperBeans currentBean : beans) {
            this.categorySchemes.addAll(currentBean.getCategorySchemes());
            this.codelists.addAll(currentBean.getCodelists());
            this.conceptSchemes.addAll(currentBean.getConceptSchemes());
            this.dataflows.addAll(currentBean.getDataflows());
            this.hcls.addAll(currentBean.getHierarchicalCodelists());
            this.dataStructures.addAll(currentBean.getDataStructures());
            this.msdSuperBean.addAll(currentBean.getMetadataStructures());
            this.provisionAgreement.addAll(currentBean.getProvisions());
            this.processes.addAll(currentBean.getProcesses());
            this.registrations.addAll(currentBean.getRegistartions());
        }
    }


    @Override
    public void merge(SuperBeans superBeans) {
        for (MaintainableSuperBean currentSb : superBeans.getAllMaintainables()) {
            addMaintainable(currentSb);
        }
    }

    @Override
    public void addMaintainable(MaintainableSuperBean bean) {
        switch (bean.getBuiltFrom().getStructureType()) {
            case CATEGORY_SCHEME:
                addCategoryScheme((CategorySchemeSuperBean) bean);
                break;
            case CODE_LIST:
                addCodelist((CodelistSuperBean) bean);
                break;
            case CONCEPT_SCHEME:
                addConceptScheme((ConceptSchemeSuperBean) bean);
                break;
            case DATAFLOW:
                addDataflow((DataflowSuperBean) bean);
                break;
            case HIERARCHICAL_CODELIST:
                addHierarchicalCodelist((HierarchicalCodelistSuperBean) bean);
                break;
            case DSD:
                addDataStructure((DataStructureSuperBean) bean);
                break;
            case MSD:
                addMetadataStructure((MetadataStructureSuperBean) bean);
                break;
            case PROCESS:
                addProcess((ProcessSuperBean) bean);
                break;
            case PROVISION_AGREEMENT:
                addHierarchicalCodelist((HierarchicalCodelistSuperBean) bean);
                break;
            case REGISTRATION:
                addRegistration((RegistrationSuperBean) bean);
                break;
            default:
                throw new SdmxNotImplementedException("SuperBeansImpl.addMaintainable of type : " + bean.getBuiltFrom().getStructureType().getType());
        }
    }

    @Override
    public void addCategoryScheme(CategorySchemeSuperBean bean) {
        if (bean != null) {
            categorySchemes.remove(bean);
            categorySchemes.add(bean);
        }
    }

    @Override
    public void addCodelist(CodelistSuperBean bean) {
        if (bean != null) {
            codelists.remove(bean);
            codelists.add(bean);
        }
    }

    @Override
    public void addConceptScheme(ConceptSchemeSuperBean bean) {
        if (bean != null) {
            conceptSchemes.remove(bean);
            conceptSchemes.add(bean);
        }
    }

    @Override
    public void addDataflow(DataflowSuperBean bean) {
        if (bean != null) {
            dataflows.remove(bean);
            dataflows.add(bean);
        }
    }

    @Override
    public void addHierarchicalCodelist(HierarchicalCodelistSuperBean bean) {
        if (bean != null) {
            hcls.remove(bean);
            hcls.add(bean);
        }
    }

    @Override
    public void addDataStructure(DataStructureSuperBean bean) {
        if (bean != null) {
            dataStructures.remove(bean);
            dataStructures.add(bean);
        }
    }

    @Override
    public void addMetadataStructure(MetadataStructureSuperBean bean) {
        if (bean != null) {
            msdSuperBean.remove(bean);
            msdSuperBean.add(bean);
        }
    }

    @Override
    public void addProvision(ProvisionAgreementSuperBean bean) {
        if (bean != null) {
            provisionAgreement.remove(bean);
            provisionAgreement.add(bean);
        }
    }

    @Override
    public void addProcess(ProcessSuperBean bean) {
        if (bean != null) {
            processes.remove(bean);
            processes.add(bean);
        }
    }

    @Override
    public void addRegistration(RegistrationSuperBean bean) {
        if (bean != null) {
            registrations.remove(bean);
            registrations.add(bean);
        }
    }

    @Override
    public Set<CategorySchemeSuperBean> getCategorySchemes() {
        return new HashSet<CategorySchemeSuperBean>(categorySchemes);
    }

    @Override
    public Set<CodelistSuperBean> getCodelists() {
        return new HashSet<CodelistSuperBean>(codelists);
    }

    @Override
    public Set<ConceptSchemeSuperBean> getConceptSchemes() {
        return new HashSet<ConceptSchemeSuperBean>(conceptSchemes);
    }

    @Override
    public Set<DataflowSuperBean> getDataflows() {
        return new HashSet<DataflowSuperBean>(dataflows);
    }

    @Override
    public Set<HierarchicalCodelistSuperBean> getHierarchicalCodelists() {
        return new HashSet<HierarchicalCodelistSuperBean>(hcls);
    }

    @Override
    public Set<DataStructureSuperBean> getDataStructures() {
        return new HashSet<DataStructureSuperBean>(dataStructures);
    }

    @Override
    public Set<MetadataStructureSuperBean> getMetadataStructures() {
        return new HashSet<MetadataStructureSuperBean>(msdSuperBean);
    }

    @Override
    public Set<ProvisionAgreementSuperBean> getProvisions() {
        return new HashSet<ProvisionAgreementSuperBean>(provisionAgreement);
    }

    @Override
    public Set<ProcessSuperBean> getProcesses() {
        return new HashSet<ProcessSuperBean>(processes);
    }

    @Override
    public Set<RegistrationSuperBean> getRegistartions() {
        return new HashSet<RegistrationSuperBean>(registrations);
    }

    @Override
    public void removeCategoryScheme(CategorySchemeSuperBean bean) {
        categorySchemes.remove(bean);
    }

    @Override
    public void removeCodelist(CodelistSuperBean bean) {
        codelists.remove(bean);
    }

    @Override
    public void removeConceptScheme(ConceptSchemeSuperBean bean) {
        conceptSchemes.remove(bean);
    }

    @Override
    public void removeDataflow(DataflowSuperBean bean) {
        dataflows.remove(bean);
    }

    @Override
    public void removeHierarchicalCodelist(HierarchicalCodelistSuperBean bean) {
        hcls.remove(bean);
    }

    @Override
    public void removeDataStructure(DataStructureSuperBean bean) {
        dataStructures.remove(bean);
    }

    @Override
    public void removeMetadataStructure(MetadataStructureSuperBean bean) {
        msdSuperBean.remove(bean);
    }

    @Override
    public void removeProvision(ProvisionAgreementSuperBean bean) {
        provisionAgreement.remove(bean);
    }

    @Override
    public void removeProcess(ProcessSuperBean bean) {
        processes.remove(bean);
    }

    @Override
    public void removeRegistration(RegistrationSuperBean bean) {
        registrations.remove(bean);
    }

    @Override
    public Set<MaintainableSuperBean> getAllMaintainables() {
        Set<MaintainableSuperBean> returnSet = new HashSet<MaintainableSuperBean>();
        returnSet.addAll(this.categorySchemes);
        returnSet.addAll(this.codelists);
        returnSet.addAll(this.conceptSchemes);
        returnSet.addAll(this.dataflows);
        returnSet.addAll(this.hcls);
        returnSet.addAll(this.msdSuperBean);
        returnSet.addAll(this.dataStructures);
        returnSet.addAll(this.provisionAgreement);
        returnSet.addAll(this.processes);
        returnSet.addAll(this.registrations);
        return returnSet;
    }
}
