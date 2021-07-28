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
package org.sdmxsource.sdmx.api.model.superbeans.datastructure;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.superbeans.base.ComponentSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.base.MaintainableSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodelistSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.conceptscheme.ConceptSuperBean;

import java.util.List;
import java.util.Set;

/**
 * A DataStructure identifies the dimensionality of a dataset in terms of the codelists and concepts used.  It also specifies the
 * attributes, measures and groups that can be used in the dataset.
 */
public interface DataStructureSuperBean extends MaintainableSuperBean {

    @Override
    DataStructureBean getBuiltFrom();

    /**
     * Returns a set of all the components used within this DataStructure.
     *
     * @return components
     */
    Set<ComponentSuperBean> getComponents();

    /**
     * Returns a set of all the concepts referenced within this DataStructure.
     *
     * @return referenced concepts
     */
    Set<ConceptSuperBean> getReferencedConcepts();

    /**
     * Returns a set of all the codelists referenced within this DataStructure.
     *
     * @return referenced codelists
     */
    Set<CodelistSuperBean> getReferencedCodelists();

    /**
     * Returns the group with the given id.
     * <p>
     * If no groups exist or no groups have the id, then null will be returned.
     *
     * @param id the id
     * @return GroupSuperBean group
     */
    GroupSuperBean getGroup(String id);

    /**
     * Returns all the groups in the DataStructure.
     * If there are no groups an empty list will be returned.
     *
     * @return GroupSuperBean groups
     */
    List<GroupSuperBean> getGroups();

    /**
     * Returns a list of all the dimensions in the DataStructure.
     * This does not include the primary measure dimension.
     * If there are no dimensions then an empty list will be returned.
     *
     * @return DimensionSuperBean dimensions
     */
    List<DimensionSuperBean> getDimensions();

    /**
     * Returns the list of dimensions that belong to this DataStructure.
     *
     * @param include an optional parameter of dimension types to include, types not specified will be excluded - the list must be from one of the following: <ul>   <li>DIMENSION - referring to a normal dimension, not measure or time</li>   <li>MEASURE_DIMENSION</li>   <li>TIME_DIMENSION</li> </ul>
     * @return dimensions
     */
    List<DimensionSuperBean> getDimensions(SDMX_STRUCTURE_TYPE... include);

    /**
     * Returns the time dimension from this DataStructure.
     *
     * @return The time dimension bean
     */
    DimensionSuperBean getTimeDimension();

    /**
     * Returns a dimension, that is referenced by the specified id.
     * If no such dimension exists for this DataStructure then null will be returned.
     *
     * @param dimensionId the id that the dimension has reference to
     * @return the dimension which references the specified id.
     */
    DimensionSuperBean getDimensionById(String dimensionId);

    /**
     * Returns a component from an id.
     * If no such concept exists, null will be returned.
     *
     * @param componentId the id by which to refer to the component.
     * @return the component which references the specified id.
     */
    ComponentSuperBean getComponentById(String componentId);

    /**
     * Returns a referenced codelist from a component with the given id.
     * If there are no components in the key family that have reference to the given concept
     * id or if the referenced component is uncoded then null will be returned.
     *
     * @param componentId the id by which to refer to the codelist.
     * @return the codelist referred to by the component id.
     */
    CodelistSuperBean getCodelistByComponentId(String componentId);

    /**
     * Returns the primary measure for this DataStructure.
     * If there is no primary measure then null will be returned.
     *
     * @return list of PrimaryMeasureSuperBean
     */
    PrimaryMeasureSuperBean getPrimaryMeasure();

    /**
     * Returns a list of all the attributes in this DataStructure.
     * If there are no attributes then an empty list will be returned.
     *
     * @return list of attributes
     */
    List<AttributeSuperBean> getAttributes();

    /**
     * Returns a subset of the key family attributes.
     * Returns the attributes with Attachment Level of DataSet.
     * If no such attributes exist then an empty list will be returned.
     *
     * @return list of attributes
     */
    List<AttributeSuperBean> getDatasetAttributes();

    /**
     * Returns a subset of the DataStructure attributes.
     * Returns the attributes with Attachment Level of Group, where the group id is the id given.
     * If no such attributes exist then an empty list will be returned.
     *
     * @param groupId                the group id of the group to return the attributes for
     * @param includeDimensionGroups if true, this will include attributes which are attached to a dimension group, which group the same dimensions as the  group with the given id
     * @return group attributes
     * @throws IllegalArgumentException if the groupId does not match any groups for this Data Structure
     */
    List<AttributeSuperBean> getGroupAttributes(String groupId, boolean includeDimensionGroups);

    /**
     * Returns a set of all the attributes attached to any group in the DataStructure.
     * If no such attributes exist then an empty list will be returned.
     *
     * @return list of attributes
     */
    Set<AttributeSuperBean> getGroupAttributes();

    /**
     * Returns a subset of the DataStructure attributes.
     * Returns the attributes with Attachment Level of Series.
     *
     * @return list of attributes
     */
    List<AttributeSuperBean> getSeriesAttributes();

    /**
     * Returns a subset of the DataStructure attributes.
     * Returns the attributes with Attachment Level of Observation.
     * If no such attributes exist then an empty list will be returned.
     *
     * @return list of attributes
     */
    List<AttributeSuperBean> getObservationAttributes();

    /**
     * Returns the attribute attachment group for the specified attribute.
     * a list of group identifiers, to which a group level attribute attaches.
     *
     * @param id id of the attribute
     * @return the attachment group
     */
    String getAttributeAttachmentGroup(String id);

    /**
     * Returns the group identifier that matches the dimensions set supplied .
     *
     * @param dimensions a set containing the complete collection of dimensions to be matched with a group
     * @return the identification of the group which has the same dimensions as the supplied set
     */
    String getGroupId(Set<String> dimensions);
}
