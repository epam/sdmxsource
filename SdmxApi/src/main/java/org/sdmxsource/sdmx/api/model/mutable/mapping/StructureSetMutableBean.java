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
package org.sdmxsource.sdmx.api.model.mutable.mapping;

import org.sdmxsource.sdmx.api.model.beans.mapping.StructureSetBean;
import org.sdmxsource.sdmx.api.model.mutable.base.MaintainableMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.StructureMapMutableBean;

import java.util.List;


/**
 * The interface Structure set mutable bean.
 */
public interface StructureSetMutableBean extends MaintainableMutableBean {

    /**
     * Gets related structures.
     *
     * @return the related structures
     */
    RelatedStructuresMutableBean getRelatedStructures();

    /**
     * Sets related structures.
     *
     * @param relatedStructures the related structures
     */
    void setRelatedStructures(RelatedStructuresMutableBean relatedStructures);

    /**
     * Gets structure map list.
     *
     * @return the structure map list
     */
    List<StructureMapMutableBean> getStructureMapList();

    /**
     * Sets structure map list.
     *
     * @param structureMapList the structure map list
     */
    void setStructureMapList(List<StructureMapMutableBean> structureMapList);

    /**
     * Gets codelist map list.
     *
     * @return the codelist map list
     */
    List<CodelistMapMutableBean> getCodelistMapList();

    /**
     * Sets codelist map list.
     *
     * @param codelistMapList the codelist map list
     */
    void setCodelistMapList(List<CodelistMapMutableBean> codelistMapList);

    /**
     * Gets category scheme map list.
     *
     * @return the category scheme map list
     */
    List<CategorySchemeMapMutableBean> getCategorySchemeMapList();

    /**
     * Sets category scheme map list.
     *
     * @param categorySchemeMapList the category scheme map list
     */
    void setCategorySchemeMapList(List<CategorySchemeMapMutableBean> categorySchemeMapList);

    /**
     * Gets concept scheme map list.
     *
     * @return the concept scheme map list
     */
    List<ConceptSchemeMapMutableBean> getConceptSchemeMapList();

    /**
     * Sets concept scheme map list.
     *
     * @param conceptSchemeMapList the concept scheme map list
     */
    void setConceptSchemeMapList(List<ConceptSchemeMapMutableBean> conceptSchemeMapList);

    /**
     * Gets organisation scheme map list.
     *
     * @return the organisation scheme map list
     */
    List<OrganisationSchemeMapMutableBean> getOrganisationSchemeMapList();

    /**
     * Sets organisation scheme map list.
     *
     * @param organisationSchemeMapList the organisation scheme map list
     */
    void setOrganisationSchemeMapList(List<OrganisationSchemeMapMutableBean> organisationSchemeMapList);


    /**
     * Add structure map.
     *
     * @param structureMap the structure map
     */
    void addStructureMap(StructureMapMutableBean structureMap);

    /**
     * Add codelist map.
     *
     * @param codelistMap the codelist map
     */
    void addCodelistMap(CodelistMapMutableBean codelistMap);

    /**
     * Add category scheme map.
     *
     * @param categorySchemeMap the category scheme map
     */
    void addCategorySchemeMap(CategorySchemeMapMutableBean categorySchemeMap);

    /**
     * Add concept scheme map.
     *
     * @param conceptSchemeMap the concept scheme map
     */
    void addConceptSchemeMap(ConceptSchemeMapMutableBean conceptSchemeMap);

    /**
     * Add organisation scheme map.
     *
     * @param organisationSchemeMap the organisation scheme map
     */
    void addOrganisationSchemeMap(OrganisationSchemeMapMutableBean organisationSchemeMap);

    /**
     * Returns a representation of itself in a bean which can not be modified, modifications to the mutable bean
     * are not reflected in the returned instance of the MaintainableBean.
     *
     * @return the immutable instance
     */
    @Override
    StructureSetBean getImmutableInstance();
}
