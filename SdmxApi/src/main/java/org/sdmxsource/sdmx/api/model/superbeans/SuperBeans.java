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
package org.sdmxsource.sdmx.api.model.superbeans;

import org.sdmxsource.sdmx.api.model.superbeans.base.MaintainableSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.base.SuperBean;
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

import java.util.Set;


/**
 * SuperBeans is a container for objects of type <b>SuperBean</b>
 *
 * @author Matt Nelson
 * @see SuperBean
 */
public interface SuperBeans {

    /**
     * Merges the super
     *
     * @param superBeans the super beans
     */
    void merge(SuperBeans superBeans);


    /**
     * Add maintainable.
     *
     * @param bean the bean
     */
    void addMaintainable(MaintainableSuperBean bean);

    //ADD

    /**
     * Add a CategorySchemeSuperBean to this container, overwrite if one already exists with the same URN
     *
     * @param bean the bean
     */
    void addCategoryScheme(CategorySchemeSuperBean bean);

    /**
     * Add a CodelistSuperBean to this container, overwrite if one already exists with the same URN
     *
     * @param bean the bean
     */
    void addCodelist(CodelistSuperBean bean);

    /**
     * Add a ConceptSchemeSuperBean to this container, overwrite if one already exists with the same URN
     *
     * @param bean the bean
     */
    void addConceptScheme(ConceptSchemeSuperBean bean);

    /**
     * Add a DataflowSuperBean to this container, overwrite if one already exists with the same URN
     *
     * @param bean the bean
     */
    void addDataflow(DataflowSuperBean bean);

    /**
     * Add a HierarchicalCodelistSuperBean to this container, overwrite if one already exists with the same URN
     *
     * @param bean the bean
     */
    void addHierarchicalCodelist(HierarchicalCodelistSuperBean bean);

    /**
     * Add a HierarchicalCodelistSuperBean to this container, overwrite if one already exists with the same URN
     *
     * @param bean the bean
     */
    void addMetadataStructure(MetadataStructureSuperBean bean);

    /**
     * Add a DataStructureSuperBean to this container, overwrite if one already exists with the same URN
     *
     * @param bean the bean
     */
    void addDataStructure(DataStructureSuperBean bean);

    /**
     * Add a ProvisionAgreementSuperBean to this container, overwrite if one already exists with the same URN
     *
     * @param bean the bean
     */
    void addProvision(ProvisionAgreementSuperBean bean);

    /**
     * Add a ProcessSuperBean to this container, overwrite if one already exists with the same URN
     *
     * @param bean the bean
     */
    void addProcess(ProcessSuperBean bean);

    /**
     * Add a RegistrationSuperBean to this container, overwrite if one already exists with the same URN
     *
     * @param bean the bean
     */
    void addRegistration(RegistrationSuperBean bean);

    //REMOVE

    /**
     * Remove the given CategorySchemeSuperBean from this container, do nothing if it is not in this container
     *
     * @param bean the bean
     */
    void removeCategoryScheme(CategorySchemeSuperBean bean);

    /**
     * Remove the given CategorySchemeSuperBean from this container, do nothing if it is not in this container
     *
     * @param bean the bean
     */
    void removeCodelist(CodelistSuperBean bean);

    /**
     * Remove the given CategorySchemeSuperBean from this container, do nothing if it is not in this container
     *
     * @param bean the bean
     */
    void removeConceptScheme(ConceptSchemeSuperBean bean);

    /**
     * Remove the given DataflowSuperBean from this container, do nothing if it is not in this container
     *
     * @param bean the bean
     */
    void removeDataflow(DataflowSuperBean bean);

    /**
     * Remove the given HierarchicalCodelistSuperBean from this container, do nothing if it is not in this container
     *
     * @param bean the bean
     */
    void removeHierarchicalCodelist(HierarchicalCodelistSuperBean bean);

    /**
     * Remove the given DataStructureSuperBean from this container, do nothing if it is not in this container
     *
     * @param bean the bean
     */
    void removeDataStructure(DataStructureSuperBean bean);

    /**
     * Remove the given MetadataStructureSuperBean from this container, do nothing if it is not in this container
     *
     * @param bean the bean
     */
    void removeMetadataStructure(MetadataStructureSuperBean bean);

    /**
     * Remove the given ProvisionAgreementSuperBean from this container, do nothing if it is not in this container
     *
     * @param bean the bean
     */
    void removeProvision(ProvisionAgreementSuperBean bean);

    /**
     * Remove the given ProcessSuperBean from this container, do nothing if it is not in this container
     *
     * @param bean the bean
     */
    void removeProcess(ProcessSuperBean bean);

    /**
     * Remove the given RegistrationSuperBean from this container, do nothing if it is not in this container
     *
     * @param bean the bean
     */
    void removeRegistration(RegistrationSuperBean bean);

    //GET

    /**
     * Returns a <b>copy</b> of the DataflowSuperBean Set.  Returns an empty set if no DataflowSuperBeans are stored in this container
     *
     * @return dataflows
     */
    Set<DataflowSuperBean> getDataflows();

    /**
     * Returns a <b>copy</b> of the CategorySchemeSuperBean Set.  Returns an empty set if no CategorySchemeSuperBeans are stored in this container
     *
     * @return category schemes
     */
    Set<CategorySchemeSuperBean> getCategorySchemes();

    /**
     * Returns a <b>copy</b> of the ConceptSchemeSuperBean Set.  Returns an empty set if no ConceptSchemeSuperBeans are stored in this container
     *
     * @return concept schemes
     */
    Set<ConceptSchemeSuperBean> getConceptSchemes();

    /**
     * Returns a <b>copy</b> of the CodelistSuperBean Set.  Returns an empty set if no CodelistSuperBeans are stored in this container
     *
     * @return codelists
     */
    Set<CodelistSuperBean> getCodelists();

    /**
     * Returns a <b>copy</b> of the HierarchicalCodelistSuperBean Set.  Returns an empty set if no HierarchicalCodelistSuperBeans are stored in this container
     *
     * @return hierarchical codelists
     */
    Set<HierarchicalCodelistSuperBean> getHierarchicalCodelists();

    /**
     * Returns a <b>copy</b> of the DataStructureSuperBean Set.  Returns an empty set if no DataStructureSuperBean are stored in this container
     *
     * @return data structures
     */
    Set<DataStructureSuperBean> getDataStructures();

    /**
     * Returns a <b>copy</b> of the MetadataStructureSuperBean Set.  Returns an empty set if no MetadataStructureSuperBean are stored in this container
     *
     * @return metadata structures
     */
    Set<MetadataStructureSuperBean> getMetadataStructures();

    /**
     * Returns a <b>copy</b> of the ProvisionAgreementSuperBean Set.  Returns an empty set if no ProvisionAgreementSuperBeans are stored in this container
     *
     * @return provisions
     */
    Set<ProvisionAgreementSuperBean> getProvisions();

    /**
     * Returns a <b>copy</b> of the MaintainableSuperBean Set.  Returns an empty set if no MaintainableSuperBeans are stored in this container
     *
     * @return all maintainables
     */
    Set<MaintainableSuperBean> getAllMaintainables();

    /**
     * Returns a <b>copy</b> of the ProcessSuperBean Set.  Returns an empty set if no ProcessSuperBeans are stored in this container
     *
     * @return processes
     */
    Set<ProcessSuperBean> getProcesses();

    /**
     * Returns a <b>copy</b> of the RegistrationSuperBean Set.  Returns an empty set if no RegistrationSuperBeans are stored in this container
     *
     * @return registartions
     */
    Set<RegistrationSuperBean> getRegistartions();
}
