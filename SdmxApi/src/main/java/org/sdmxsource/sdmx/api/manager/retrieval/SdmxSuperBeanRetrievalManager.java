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
package org.sdmxsource.sdmx.api.manager.retrieval;

import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.sdmx.api.model.superbeans.categoryscheme.CategorySchemeSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodelistSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.HierarchicalCodelistSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.conceptscheme.ConceptSchemeSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DataStructureSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DataflowSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.metadata.MetadataStructureSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.registry.ProvisionAgreementSuperBean;

import java.util.Set;


/**
 * Manages the retrieval of structures and returns the responses as SDMX SuperBeans
 *
 * @author Matt Nelson
 */
public interface SdmxSuperBeanRetrievalManager {

    /**
     * Returns a single CodelistSuperBean , this expects the ref object either to contain
     * a URN or all the attributes required to uniquely identify the object.  If version information
     * is missing then the latest version is assumed.
     * <p>
     * If no codelist is found with the given refernce, then null is returned
     *
     * @param ref the ref
     * @return codelist super bean
     */
    CodelistSuperBean getCodelistSuperBean(MaintainableRefBean ref);

    /**
     * Returns a single ConceptSchemeSuperBean , this expects the ref object either to contain
     * a URN or all the attributes required to uniquely identify the object.  If version information
     * is missing then the latest version is assumed.
     * <p>
     * If no concept scheme is found with the given reference, then null is returned
     *
     * @param ref the ref
     * @return concept scheme super bean
     */
    ConceptSchemeSuperBean getConceptSchemeSuperBean(MaintainableRefBean ref);

    /**
     * Returns a single CategorySchemeSuperBean , this expects the ref object either to contain
     * a URN or all the attributes required to uniquely identify the object.  If version information
     * is missing then the latest version is assumed.
     * <p>
     * If no category scheme is found with the given reference, then null is returned
     *
     * @param ref the ref
     * @return category scheme super bean
     */
    CategorySchemeSuperBean getCategorySchemeSuperBean(MaintainableRefBean ref);


    /**
     * Returns a single Dataflow , this expects the ref object either to contain
     * a URN or all the attributes required to uniquely identify the object.  If version information
     * is missing then the latest version is assumed.
     * <p>
     * If no dataflow is found with the given reference, then null is returned
     *
     * @param ref the ref
     * @return dataflow super bean
     */
    DataflowSuperBean getDataflowSuperBean(MaintainableRefBean ref);

    /**
     * Returns a single DataStructureSuperBean , this expects the ref object either to contain
     * a URN or all the attributes required to uniquely identify the object.  If version information
     * is missing then the latest version is assumed.
     * <p>
     * If no DSD is found with the given reference, then null is returned
     *
     * @param ref the ref
     * @return data structure super bean
     */
    DataStructureSuperBean getDataStructureSuperBean(MaintainableRefBean ref);

    /**
     * Returns a single HierarchicCodeList , this expects the ref object either to contain
     * a URN or all the attributes required to uniquely identify the object.  If version information
     * is missing then the latest version is assumed.
     * <p>
     * If no hierarchical codelist is found with the given reference, then null is returned
     *
     * @param ref the ref
     * @return hierarchic code list super bean
     */
    HierarchicalCodelistSuperBean getHierarchicCodeListSuperBean(MaintainableRefBean ref);

    /**
     * Returns a single MetadataStructureSuperBean , this expects the ref object either to contain
     * a URN or all the attributes required to uniquely identify the object.  If version information
     * is missing then the latest version is assumed.
     * <p>
     * If no MetadataStructure is found with the given reference, then null is returned
     *
     * @param ref the ref
     * @return metadata structure super bean
     */
    MetadataStructureSuperBean getMetadataStructureSuperBean(MaintainableRefBean ref);

    /**
     * Returns a single ProvisionAgreementSuperBean , this expects the ref object either to contain
     * a URN or all the attributes required to uniquely identify the object.  If version information
     * is missing then the latest version is assumed.
     * <p>
     * If no Provision Agreement is found with the given reference, then null is returned
     *
     * @param ref the ref
     * @return provision agreement super bean
     */
    ProvisionAgreementSuperBean getProvisionAgreementSuperBean(MaintainableRefBean ref);

    /**
     * Returns CodelistSuperBean that match the parameters in the ref bean.  If the ref bean is null or
     * has no attributes set, then this will be interpreted as a search for all CodelistSuperBean
     *
     * @param ref - the reference object defining the search parameters, can be empty or null
     * @return list of beans that match the search criteria
     */
    Set<CodelistSuperBean> getCodelistSuperBeans(MaintainableRefBean ref);

    /**
     * Returns ConceptSchemeSuperBean that match the parameters in the ref bean.  If the ref bean is null or
     * has no attributes set, then this will be interpreted as a search for all ConceptSchemeSuperBeans
     *
     * @param ref - the reference object defining the search parameters, can be empty or null
     * @return list of beans that match the search criteria
     */
    Set<ConceptSchemeSuperBean> getConceptSchemeSuperBeans(MaintainableRefBean ref);

    /**
     * Returns CategorySchemeSuperBean that match the parameters in the ref bean.  If the ref bean is null or
     * has no attributes set, then this will be interpreted as a search for all CategorySchemeSuperBean
     *
     * @param ref - the reference object defining the search parameters, can be empty or null
     * @return list of beans that match the search criteria
     */
    Set<CategorySchemeSuperBean> getCategorySchemeSuperBeans(MaintainableRefBean ref);


    /**
     * Returns DataflowSuperBean that match the parameters in the ref bean.  If the ref bean is null or
     * has no attributes set, then this will be interpreted as a search for all DataflowSuperBean
     *
     * @param ref - the reference object defining the search parameters, can be empty or null
     * @return list of beans that match the search criteria
     */
    Set<DataflowSuperBean> getDataflowSuperBeans(MaintainableRefBean ref);

    /**
     * Returns DataStructureSuperBeans that match the parameters in the ref bean.  If the ref bean is null or
     * has no attributes set, then this will be interpreted as a search for all DataStructureSuperBeans.
     *
     * @param ref - the reference object defining the search parameters, can be empty or null
     * @return list of beans that match the search criteria
     */
    Set<DataStructureSuperBean> getDataStructureSuperBeans(MaintainableRefBean ref);

    /**
     * Returns HierarchicalCodelistSuperBean that match the parameters in the ref bean.  If the ref bean is null or
     * has no attributes set, then this will be interpreted as a search for all HierarchicalCodelistSuperBean
     *
     * @param ref - the reference object defining the search parameters, can be empty or null
     * @return list of beans that match the search criteria
     */
    Set<HierarchicalCodelistSuperBean> getHierarchicCodeListSuperBeans(MaintainableRefBean ref);


    /**
     * Returns MetadataStructureSuperBeans that match the parameters in the ref bean.  If the ref bean is null or
     * has no attributes set, then this will be interpreted as a search for all MetadataStructureSuperBean
     *
     * @param ref - the reference object defining the search parameters, can be empty or null
     * @return list of beans that match the search criteria
     */
    Set<MetadataStructureSuperBean> getMetadataStructureSuperBeans(MaintainableRefBean ref);

    /**
     * Returns ProvisionAgreementSuperBeans that match the parameters in the ref bean.  If the ref bean is null or
     * has no attributes set, then this will be interpreted as a search for all ProvisionAgreementSuperBeans
     *
     * @param ref - the reference object defining the search parameters, can be empty or null
     * @return list of beans that match the search criteria
     */
    Set<ProvisionAgreementSuperBean> getProvisionAgreementSuperBeans(MaintainableRefBean ref);

}
