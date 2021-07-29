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
package org.sdmxsource.sdmx.sdmxbeans.model.superbeans.datastructure;

import org.sdmxsource.sdmx.api.constants.ATTRIBUTE_ATTACHMENT_LEVEL;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.AttributeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.GroupBean;
import org.sdmxsource.sdmx.api.model.superbeans.base.ComponentSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodelistSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.conceptscheme.ConceptSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.*;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.base.MaintainableSuperBeanImpl;

import java.util.*;


/**
 * The type Data structure super bean.
 */
public class DataStructureSuperBeanImpl extends MaintainableSuperBeanImpl implements DataStructureSuperBean {
    private static final long serialVersionUID = 1L;

    private List<DimensionSuperBean> dimensions;
    private List<AttributeSuperBean> attributes;
    private PrimaryMeasureSuperBean primaryMeasure;
    private List<GroupSuperBean> groups = new ArrayList<GroupSuperBean>();
    private Map<String, DimensionSuperBean> conceptDimensionMap;
    private Map<String, ComponentSuperBean> conceptComponentMap = new HashMap<String, ComponentSuperBean>();
    private Map<String, CodelistSuperBean> conceptCodelistMap = new HashMap<String, CodelistSuperBean>();
    private Set<CodelistSuperBean> referencedCodelists = new HashSet<CodelistSuperBean>();

    private DataStructureBean keyFamily;

    /**
     * Instantiates a new Data structure super bean.
     *
     * @param dataStructure  the data structure
     * @param dimensions     the dimensions
     * @param attributes     the attributes
     * @param primaryMeasure the primary measure
     */
    public DataStructureSuperBeanImpl(DataStructureBean dataStructure,
                                      List<DimensionSuperBean> dimensions,
                                      List<AttributeSuperBean> attributes,
                                      PrimaryMeasureSuperBean primaryMeasure) {
        super(dataStructure);
        this.keyFamily = dataStructure;
        this.dimensions = dimensions;
        this.attributes = attributes;
        this.primaryMeasure = primaryMeasure;

        //Create Mapping of Concept Id To Dimension that refers to that concept
        conceptDimensionMap = new HashMap<String, DimensionSuperBean>();

        for (DimensionSuperBean currentDimension : dimensions) {
            conceptDimensionMap.put(currentDimension.getId(), currentDimension);
            conceptComponentMap.put(currentDimension.getId(), currentDimension);
            if (currentDimension.getCodelist(true) != null) {
                conceptCodelistMap.put(currentDimension.getId(), currentDimension.getCodelist(true));
                referencedCodelists.add(currentDimension.getCodelist(true));
            }
        }
        if (attributes != null) {
            for (AttributeSuperBean currentBean : attributes) {
                conceptComponentMap.put(currentBean.getId(), currentBean);
                if (currentBean.getCodelist(true) != null) {
                    conceptCodelistMap.put(currentBean.getId(), currentBean.getCodelist(true));
                    referencedCodelists.add(currentBean.getCodelist(true));
                }
            }
        }
        if (primaryMeasure != null) {
            conceptComponentMap.put(primaryMeasure.getId(), primaryMeasure);
            if (primaryMeasure.getCodelist(true) != null) {
                conceptCodelistMap.put(primaryMeasure.getId(), primaryMeasure.getCodelist(true));
                referencedCodelists.add(primaryMeasure.getCodelist(true));
            }
        }
        for (GroupBean currentGroup : dataStructure.getGroups()) {
            groups.add(new GroupSuperBeanImpl(currentGroup, this));
        }
    }

    @Override
    public DataStructureBean getBuiltFrom() {
        return keyFamily;
    }

    @Override
    public Set<CodelistSuperBean> getReferencedCodelists() {
        return new HashSet<CodelistSuperBean>(referencedCodelists);
    }

    @Override
    public Set<ComponentSuperBean> getComponents() {
        return new HashSet<ComponentSuperBean>(conceptComponentMap.values());
    }


    @Override
    public Set<ConceptSuperBean> getReferencedConcepts() {
        Set<ConceptSuperBean> returnConcepts = new HashSet<ConceptSuperBean>();
        for (ComponentSuperBean currentComponent : conceptComponentMap.values()) {
            returnConcepts.add(currentComponent.getConcept());
        }
        return returnConcepts;
    }

    @Override
    public CodelistSuperBean getCodelistByComponentId(String conceptId) {
        return conceptCodelistMap.get(conceptId);
    }

    @Override
    public ComponentSuperBean getComponentById(String conceptId) {
        return conceptComponentMap.get(conceptId);
    }


    @Override
    public DimensionSuperBean getTimeDimension() {
        for (DimensionSuperBean currentDimension : dimensions) {
            if (currentDimension.isTimeDimension()) {
                return currentDimension;
            }
        }
        return null;
    }

    @Override
    public DimensionSuperBean getDimensionById(String conceptId) {
        return conceptDimensionMap.get(conceptId);
    }

    /* (non-Javadoc)
     * @see org.sdmxsource.sdmx.api.model.superbeans.datastructure.DataStructureSuperBean#getDimensions()
     */
    @Override
    public List<DimensionSuperBean> getDimensions() {
        return new ArrayList<DimensionSuperBean>(dimensions);
    }

    @Override
    public List<DimensionSuperBean> getDimensions(SDMX_STRUCTURE_TYPE... includeTypes) {
        if (dimensions != null) {
            List<DimensionSuperBean> returnList = new ArrayList<DimensionSuperBean>();
            for (DimensionSuperBean dim : dimensions) {
                if (includeTypes != null && includeTypes.length > 0) {
                    for (SDMX_STRUCTURE_TYPE currentType : includeTypes) {
                        if (currentType == dim.getBuiltFrom().getStructureType()) {
                            returnList.add(dim);
                        }
                    }
                } else {
                    returnList.add(dim);
                }
            }
            return returnList;
        }
        return new ArrayList<DimensionSuperBean>();
    }


    @Override
    public List<AttributeSuperBean> getAttributes() {
        return new ArrayList<AttributeSuperBean>(attributes);
    }

    @Override
    public PrimaryMeasureSuperBean getPrimaryMeasure() {
        return primaryMeasure;
    }

    @Override
    public List<AttributeSuperBean> getDatasetAttributes() {
        return getAttribute(ATTRIBUTE_ATTACHMENT_LEVEL.DATA_SET);
    }


    @Override
    public Set<AttributeSuperBean> getGroupAttributes() {
        List<AttributeSuperBean> allGroupAttributes = getAttribute(ATTRIBUTE_ATTACHMENT_LEVEL.GROUP);
        return new HashSet<AttributeSuperBean>(allGroupAttributes);
    }

    @Override
    public List<AttributeSuperBean> getGroupAttributes(String groupId, boolean includeDimensionGroups) {
        List<AttributeSuperBean> returnList = new ArrayList<AttributeSuperBean>();
        for (AttributeBean attr : getBuiltFrom().getGroupAttributes(groupId, includeDimensionGroups)) {
            returnList.add((AttributeSuperBean) getComponentById(attr.getId()));
        }
        return returnList;
    }

    @Override
    public List<GroupSuperBean> getGroups() {
        return new ArrayList<GroupSuperBean>(groups);
    }

    @Override
    public GroupSuperBean getGroup(String id) {
        if (groups != null) {
            for (GroupSuperBean currentBean : groups) {
                if (currentBean.getId().equals(id)) {
                    return currentBean;
                }
            }
        }
        return null;
    }

    @Override
    public List<AttributeSuperBean> getObservationAttributes() {
        return getAttribute(ATTRIBUTE_ATTACHMENT_LEVEL.OBSERVATION);
    }

    //FUNC rename this method
    @Override
    public List<AttributeSuperBean> getSeriesAttributes() {
        return getAttribute(ATTRIBUTE_ATTACHMENT_LEVEL.DIMENSION_GROUP);
    }

    private List<AttributeSuperBean> getAttribute(ATTRIBUTE_ATTACHMENT_LEVEL type) {
        List<AttributeSuperBean> returnList = new ArrayList<AttributeSuperBean>();
        for (AttributeSuperBean currentAttribute : attributes) {
            if (currentAttribute.getAttachmentLevel().equals(type)) {
                returnList.add(currentAttribute);
            }
        }
        return returnList;
    }

    @Override
    public String getAttributeAttachmentGroup(String id) {
        for (AttributeSuperBean a : attributes) {
            if (a.getId().equals(id)) {
                return a.getAttachmentGroup();
            }
        }
        return null;
    }

    @Override
    public Set<MaintainableBean> getCompositeBeans() {
        Set<MaintainableBean> returnSet = super.getCompositeBeans();
        returnSet.add(keyFamily);
        for (DimensionSuperBean dim : dimensions) {
            returnSet.addAll(dim.getCompositeBeans());
        }
        for (AttributeSuperBean attr : attributes) {
            returnSet.addAll(attr.getCompositeBeans());
        }
        for (GroupSuperBean grp : groups) {
            returnSet.addAll(grp.getCompositeBeans());
        }
        returnSet.addAll(primaryMeasure.getCompositeBeans());
        return returnSet;
    }

    @Override
    public String getGroupId(Set<String> dimensions) {
        for (GroupSuperBean g : groups) {
            Set<String> grpDims = new HashSet<String>();
            for (DimensionSuperBean d : g.getDimensions()) {
                grpDims.add(d.getId());
            }
            if (grpDims.equals(dimensions)) {
                return g.getId();
            }
        }
        return null;
    }
}
