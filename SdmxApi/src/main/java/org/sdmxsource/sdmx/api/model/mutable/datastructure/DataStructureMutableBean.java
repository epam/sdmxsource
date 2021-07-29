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
package org.sdmxsource.sdmx.api.model.mutable.datastructure;

import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.base.MaintainableMutableBean;

import java.util.List;

/**
 * The interface Data structure mutable bean.
 */
public interface DataStructureMutableBean extends MaintainableMutableBean {

    /**
     * Add group.
     *
     * @param group the group
     */
    void addGroup(GroupMutableBean group);

    /**
     * Gets groups.
     *
     * @return the groups
     */
    List<GroupMutableBean> getGroups();

    /**
     * Sets groups.
     *
     * @param groups the groups
     */
    void setGroups(List<GroupMutableBean> groups);

    /**
     * Gets dimension list.
     *
     * @return the dimension list
     */
    DimensionListMutableBean getDimensionList();

    /**
     * Sets dimension list.
     *
     * @param dimensionList the dimension list
     */
    void setDimensionList(DimensionListMutableBean dimensionList);

    /**
     * Removes the component (dimension or attribute) with the given id
     *
     * @param id the id
     * @return this data structure mutable bean
     */
    DataStructureMutableBean removeComponent(String id);

    /**
     * Add attribute.
     *
     * @param attribute the attribute
     */
    void addAttribute(AttributeMutableBean attribute);

    /**
     * Add dimension.
     *
     * @param dimension the dimension
     */
    void addDimension(DimensionMutableBean dimension);

    /**
     * Add dimension dimension mutable bean.
     *
     * @param conceptRef  the concept ref
     * @param codelistRef the codelist ref
     * @return the dimension mutable bean
     */
    DimensionMutableBean addDimension(StructureReferenceBean conceptRef, StructureReferenceBean codelistRef);

    /**
     * Add attribute attribute mutable bean.
     *
     * @param conceptRef  the concept ref
     * @param codelistRef the codelist ref
     * @return the attribute mutable bean
     */
    AttributeMutableBean addAttribute(StructureReferenceBean conceptRef, StructureReferenceBean codelistRef);

    /**
     * Add primary measure primary measure mutable bean.
     *
     * @param conceptRef the concept ref
     * @return the primary measure mutable bean
     */
    PrimaryMeasureMutableBean addPrimaryMeasure(StructureReferenceBean conceptRef);

    /**
     * Gets attribute list.
     *
     * @return the attribute list
     */
    AttributeListMutableBean getAttributeList();

    /**
     * Sets attribute list.
     *
     * @param attributeList the attribute list
     */
    void setAttributeList(AttributeListMutableBean attributeList);

    /**
     * Gets measure list.
     *
     * @return the measure list
     */
    MeasureListMutableBean getMeasureList();

    /**
     * Sets measure list.
     *
     * @param measureList the measure list
     */
    void setMeasureList(MeasureListMutableBean measureList);

    /**
     * Sets primary measure.
     *
     * @param primaryMeasure the primary measure
     */
    void setPrimaryMeasure(PrimaryMeasureMutableBean primaryMeasure);

    /**
     * Returns the dimension with the given id, or null if there is no match
     *
     * @param id the id
     * @return the dimension with the given id, or null if there is no match
     */
    DimensionMutableBean getDimension(String id);

    /**
     * Returns the list of dimenions, this may return null or an empty list if none exist
     *
     * @return the list of dimenions, this may return null or an empty list if none exist
     */
    List<DimensionMutableBean> getDimensions();

    /**
     * Returns the list of attributes, this may return null or an empty list if none exist
     *
     * @return the list of attributes, this may return null or an empty list if none exist
     */
    List<AttributeMutableBean> getAttributes();

    /**
     * Returns the attribute with the given id, or null if there is no match
     *
     * @param id the id
     * @return the attribute with the given id, or null if there is no match
     */
    AttributeMutableBean getAttribute(String id);

    /**
     * Returns a representation of itself in a bean which can not be modified, modifications to the mutable bean
     * are not reflected in the returned instance of the MaintainableBean.
     *
     * @return the immutable instance
     */
    @Override
    DataStructureBean getImmutableInstance();
}
