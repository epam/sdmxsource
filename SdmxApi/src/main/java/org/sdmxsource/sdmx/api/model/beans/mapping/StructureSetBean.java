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
package org.sdmxsource.sdmx.api.model.beans.mapping;

import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.mutable.mapping.StructureSetMutableBean;

import java.net.URL;
import java.util.List;


/**
 * Represents an SDMX Structure Set
 *
 * @author Matt Nelson
 */
public interface StructureSetBean extends MaintainableBean {

    /**
     * RelatedStructures contains references to structures (key families and metadata structure definitions) and
     * structure usages (data flows and metadata flows) to indicate that a semantic relationship exist between them.
     * The details of these relationships can be found in the structure maps.
     *
     * @return related structures
     */
    RelatedStructuresBean getRelatedStructures();

    /**
     * StructureMap maps components from one structure to components to another structure,
     * and can describe how the value of the components are related.
     *
     * @return structure map list
     */
    List<StructureMapBean> getStructureMapList();

    /**
     * CodelistMap links a source and target codes from different
     * lists where there is a semantic equivalence between them.
     *
     * @return codelist map list
     */
    List<CodelistMapBean> getCodelistMapList();


    /**
     * Gets category scheme map list.
     *
     * @return the category scheme map list
     */
    List<CategorySchemeMapBean> getCategorySchemeMapList();

    /**
     * ConceptSchemeMap links a source and target concepts from different schemes where there is a semantic equivalence between them.
     *
     * @return concept scheme map list
     */
    List<ConceptSchemeMapBean> getConceptSchemeMapList();


    /**
     * Gets organisation scheme map list.
     *
     * @return the organisation scheme map list
     */
    List<OrganisationSchemeMapBean> getOrganisationSchemeMapList();


    //ReportingTaxonomyMap links a source and target reporting categories from different taxonomies where there is a semantic equivalence between them.


    /**
     * Returns a stub reference of itself.
     * <p>
     * A stub bean only contains Maintainable and Identifiable Attributes, not the composite Objects that are
     * contained within the Maintainable
     *
     * @param actualLocation the URL indicating where the full structure can be returned from
     * @param isServiceUrl   if true this URL will be present on the serviceURL attribute, otherwise it will be treated as a structureURL attribute
     * @return the stub
     */
    @Override
    StructureSetBean getStub(URL actualLocation, boolean isServiceUrl);

    /**
     * Returns a representation of itself in a bean which can be modified, modifications to the mutable bean
     * are not reflected in the instance of the MaintainableBean.
     *
     * @return the mutable instance
     */
    @Override
    StructureSetMutableBean getMutableInstance();
}
