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
package org.sdmxsource.sdmx.api.model.beans.datastructure;

import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.ComponentBean;
import org.sdmxsource.sdmx.api.model.beans.base.ConstrainableBean;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.DataStructureMutableBean;

import java.net.URL;
import java.util.List;


/**
 * A Data Structure also known as a Data Structure Definition (DSD) or KeyFamily describes the dimensionality
 * of the data.
 * <p>
 * A Data Structure contains Dimensions, Attributes, Groups, and a Measure. Each of these components is
 * held in its respective identifiable list container (DimensionList, AttributeList, MeasureList),
 * however the DataStructureBean exposes the methods to obtain the Dimensions, Attributes, Measure, and Groups
 * without having to navigate through the list.
 */
public interface DataStructureBean extends MaintainableBean, ConstrainableBean {

    /**
     * Returns false if this data structure is not compatible with the target schema version.
     * <p>
     * For SDMX Version 1.0 all dimensions had to be coded.
     * <p>
     * For SDMX Version 1.0 and 2.0 each components had to reference a concept with a unique id
     * <p>
     * For EDI ?
     *
     * @param schemaVersion the schema version
     * @return false if this data structure is not compatible with the target schema version.
     */
    boolean isCompatible(SDMX_SCHEMA schemaVersion);


    /**
     * Returns the Dimension List. The Dimension List is an identifiable container for the DataStructure's dimensions.
     *
     * @return dimension list
     */
    DimensionListBean getDimensionList();

    /**
     * Returns the Attribute List. The Attribute List is an identifiable container for the DataStructure's attributes.
     *
     * @return attribute list
     */
    AttributeListBean getAttributeList();

    /**
     * Returns the Measure List. The Measure List is an identifiable container for the DataStructure's measure.
     *
     * @return measure list
     */
    MeasureListBean getMeasureList();

    /**
     * Returns the list of dimensions that belong to this DataStructure.
     *
     * @param include - an optional parameter of dimension types to include, types not specified will be excluded - the list must be from one of the following: <ul>   <li>DIMENSION - referring to a normal dimension, not measure or time</li>   <li>MEASURE_DIMENSION</li>   <li>TIME_DIMENSION</li> </ul>
     * @return the list of dimensions that belong to this DataStructure.
     */
    List<DimensionBean> getDimensions(SDMX_STRUCTURE_TYPE... include);

    /**
     * Returns the dimension that is playing the role of frequency. Returns <code>null</code> if no dimension is playing the role of frequency.
     *
     * @return frequency dimension
     */
    DimensionBean getFrequencyDimension();

    /**
     * Returns true if there is a dimension that is playing the role of frequency.
     *
     * @return true if there is a dimension that is playing the role of frequency.
     */
    boolean hasFrequencyDimension();

    /**
     * Returns the dimension with the given Id.
     *
     * @param id the id
     * @return the dimension with the given Id.
     */
    DimensionBean getDimension(String id);

    /**
     * Returns the component with the given id.
     *
     * @param id the id
     * @return the component with the given id.
     */
    ComponentBean getComponent(String id);

    /**
     * Returns a list of all components that belong to this DSD
     *
     * @return a list of all components that belong to this DSD
     */
    List<ComponentBean> getComponents();

    /**
     * Returns the list of groups that belongs to this DataStructure. If there are no groups then an empty list is returned.
     *
     * @return the list of groups that belongs to this DataStructure. If there are no groups then an empty list is returned.
     */
    List<GroupBean> getGroups();

    /**
     * Gets the group with the given id that belongs to this DataStructure.
     * <p>
     * If there are no groups with the given id then <code>null</code> will be returned.
     *
     * @param groupId the group id
     * @return the group with the given id that belongs to this DataStructure.
     */
    GroupBean getGroup(String groupId);

    /**
     * Gets the time dimension that belongs to this DataStructure.
     * <p>
     * If there is no time dimension then <code>null</code> is returned
     *
     * @return time dimension
     */
    DimensionBean getTimeDimension();

    /**
     * Gets the primary measure that belongs to this DataStructure.
     * A DataStructure must have a primary measure so this method will always return a value.
     *
     * @return primary measure
     */
    PrimaryMeasureBean getPrimaryMeasure();

    /**
     * Returns the attributes that belong to this DataStructure.
     * If the DataStructure has no attributes then an empty list is returned.
     *
     * @return the attributes that belong to this DataStructure.
     * If the DataStructure has no attributes then an empty list is returned.
     */
    List<AttributeBean> getAttributes();

    /**
     * Returns a subset of the DataStructure's attributes.
     * Returns the attributes with Attachment Level of DataSet.
     * <p>
     * If there are no attributes that match this criteria then an empty list is returned
     *
     * @return dataset attributes
     */
    List<AttributeBean> getDatasetAttributes();

    /**
     * Returns a subset of the DataStructure's attributes.
     * Returns the attributes with Attachment Level of Group.
     * If there are no attributes that match this criteria then an empty list is returned.
     *
     * @return group attributes
     */
    List<AttributeBean> getGroupAttributes();

    /**
     * Returns a subset of the DataStructure's attributes.
     * Returns the attributes with Attachment Level of Group, that are attached to the group with the given id.
     * If there are no attributes that match this criteria then an empty list is returned.
     *
     * @param groupId                the group id of the group to return the attributes for
     * @param includeDimensionGroups if true, this will include attributes which are attached to a dimension group, which group the same dimensions as the  group with the given id
     * @return group attributes
     * @throws IllegalArgumentException if the groupId does not match any groups for this Data Structure
     */
    List<AttributeBean> getGroupAttributes(String groupId, boolean includeDimensionGroups);

    /**
     * Returns a subset of the DataStructure's attributes.
     * Returns the attributes with Attachment Level of DimensionGroup.
     * If there are no attributes that match this criteria then an empty list is returned.
     *
     * @return dimension group attributes
     */
    List<AttributeBean> getDimensionGroupAttributes();

    /**
     * Returns a subset of the DataStructure's attributes.
     * Returns the attributes with Attachment Level of DimensionGroup,
     * where none of the dimensions referenced in the group are the cross sectional attribute.
     * If there are no attributes that match this criteria then an empty list is returned.
     *
     * @param crossSectionalConcept the cross sectional concept
     * @return series attributes
     */
    List<AttributeBean> getSeriesAttributes(String crossSectionalConcept);

    /**
     * Returns a subset of the DataStructure's attributes.
     * Returns the attributes with Attachment Level of Observation when time is at the observation level.
     * If there are no attributes that match this criteria then an empty list is returned.
     *
     * @return observation attributes
     */
    List<AttributeBean> getObservationAttributes();

    /**
     * Returns a subset of the DataStructure's attributes.
     * Returns the attributes with Attachment Level of Observation,
     * or an Attachment level of DimenisonGroup, where one of the dimensions in the group is the one at the cross sectional level.
     * If there are no attributes that match this criteria then an empty list is returned.
     *
     * @param crossSectionalConcept the cross sectional concept
     * @return observation attributes
     */
    List<AttributeBean> getObservationAttributes(String crossSectionalConcept);

    /**
     * Returns the attribute with the given id, returns <code>null</code> if no such attribute exists.
     *
     * @param id the id
     * @return the attribute with the given id, returns <code>null</code> if no such attribute exists.
     */
    AttributeBean getAttribute(String id);

    /**
     * Returns the group attribute with the given id, returns <code>null</code> if no such attribute exists.
     *
     * @param id the id
     * @return group attribute
     */
    AttributeBean getGroupAttribute(String id);

    /**
     * Returns the series attribute with the given id, returns <code>null</code> if no such attribute exists.
     *
     * @param id the id
     * @return dimension group attribute
     */
    AttributeBean getDimensionGroupAttribute(String id);

    /**
     * Returns the observation attribute with the given id, returns <code>null</code> if no such attribute exists.
     *
     * @param id the id
     * @return observation attribute
     */
    AttributeBean getObservationAttribute(String id);

    /**
     * Returns a stub reference of itself.
     * A stub bean only contains Maintainable and Identifiable Attributes, not the composite Objects that are
     * contained within the Maintainable.
     *
     * @param actualLocation the URL indicating where the full structure can be returned from
     * @param isServiceUrl   if true this URL will be present on the serviceURL attribute, otherwise it will be treated as a structureURL attribute
     * @return the stub
     */
    @Override
    DataStructureBean getStub(URL actualLocation, boolean isServiceUrl);

    /**
     * Returns a representation of itself in a bean which can be modified, modifications to the mutable bean
     * are not reflected in the instance of the MaintainableBean.
     *
     * @return the mutable instance
     */
    @Override
    DataStructureMutableBean getMutableInstance();
}
