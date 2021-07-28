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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.datastructure;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.GroupBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.base.ComponentMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.RepresentationMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.*;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.datastructure.DataStructureBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.MaintainableMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.RepresentationMutableBeanImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Data structure mutable bean.
 */
public class DataStructureMutableBeanImpl extends MaintainableMutableBeanImpl
        implements DataStructureMutableBean {
    private static final long serialVersionUID = 1L;

    private List<GroupMutableBean> groups = new ArrayList<GroupMutableBean>();
    private DimensionListMutableBean dimensionList;
    private AttributeListMutableBean attributeList;
    private MeasureListMutableBean measureList;

    /**
     * Instantiates a new Data structure mutable bean.
     */
    public DataStructureMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.DSD);
    }

    /**
     * Instantiates a new Data structure mutable bean.
     *
     * @param bean the bean
     */
    public DataStructureMutableBeanImpl(DataStructureBean bean) {
        super(bean);
        if (bean.getGroups() != null) {
            for (GroupBean currentGroupBean : bean.getGroups()) {
                this.groups.add(new GroupMutableBeanImpl(currentGroupBean));
            }
        }
        if (bean.getDimensionList() != null) {
            this.dimensionList = new DimensionListMutableBeanImpl(
                    bean.getDimensionList());
        }
        if (bean.getAttributeList() != null) {
            this.attributeList = new AttributeListMutableBeanImpl(
                    bean.getAttributeList());
        }
        if (bean.getMeasureList() != null) {
            this.measureList = new MeasureListMutableBeanImpl(
                    bean.getMeasureList());
        }
    }

    @Override
    public DataStructureMutableBean removeComponent(String id) {
        removeComponent(getDimensions(), id);
        removeComponent(getAttributes(), id);
        return this;
    }


    @Override
    public DimensionMutableBean getDimension(String id) {
        return (DimensionMutableBean) getComponentById(getDimensions(), id);
    }

    @Override
    public AttributeMutableBean getAttribute(String id) {
        return (AttributeMutableBean) getComponentById(getAttributes(), id);
    }

    private void removeComponent(List<? extends ComponentMutableBean> comps, String removeId) {
        if (comps == null) {
            return;
        }
        ComponentMutableBean toRemove = getComponentById(comps, removeId);
        if (toRemove != null) {
            comps.remove(toRemove);
        }
    }

    private ComponentMutableBean getComponentById(List<? extends ComponentMutableBean> comps, String removeId) {
        if (comps == null) {
            return null;
        }
        for (ComponentMutableBean currentComponent : comps) {
            if (currentComponent.getId() != null && currentComponent.getId().equals(removeId)) {
                return currentComponent;
            }
        }
        return null;
    }


    @Override
    public DimensionMutableBean addDimension(StructureReferenceBean conceptRef,
                                             StructureReferenceBean codelistRef) {
        DimensionMutableBean newDimension = new DimensionMutableBeanImpl();
        newDimension.setConceptRef(conceptRef);
        if (codelistRef != null) {
            RepresentationMutableBean representation = new RepresentationMutableBeanImpl();
            representation.setRepresentation(codelistRef);
            newDimension.setRepresentation(representation);
        }
        this.addDimension(newDimension);
        return newDimension;
    }

    @Override
    public AttributeMutableBean addAttribute(StructureReferenceBean conceptRef,
                                             StructureReferenceBean codelistRef) {
        AttributeMutableBean newAttribute = new AttributeMutableBeanImpl();
        newAttribute.setConceptRef(conceptRef);
        if (codelistRef != null) {
            RepresentationMutableBean representation = new RepresentationMutableBeanImpl();
            representation.setRepresentation(codelistRef);
            newAttribute.setRepresentation(representation);
        }
        this.addAttribute(newAttribute);
        return newAttribute;
    }

    @Override
    public PrimaryMeasureMutableBean addPrimaryMeasure(
            StructureReferenceBean conceptRef) {
        PrimaryMeasureMutableBean primaryMeasure = new PrimaryMeasureMutableBeanImpl();
        primaryMeasure.setConceptRef(conceptRef);
        setPrimaryMeasure(primaryMeasure);
        return primaryMeasure;
    }

    @Override
    public List<DimensionMutableBean> getDimensions() {
        if (this.dimensionList != null) {
            return this.dimensionList.getDimensions();
        }
        return null;
    }

    @Override
    public List<AttributeMutableBean> getAttributes() {
        if (this.attributeList != null) {
            return this.attributeList.getAttributes();
        }
        return null;
    }

    @Override
    public void setPrimaryMeasure(PrimaryMeasureMutableBean primaryMeasure) {
        if (measureList == null) {
            this.measureList = new MeasureListMutableBeanImpl();
        }
        this.measureList.setPrimaryMeasure(primaryMeasure);
    }

    @Override
    public void addGroup(GroupMutableBean group) {
        if (this.groups == null) {
            this.groups = new ArrayList<GroupMutableBean>();
        }
        this.groups.add(group);
    }

    @Override
    public DataStructureBean getImmutableInstance() {
        return new DataStructureBeanImpl(this);
    }

    @Override
    public List<GroupMutableBean> getGroups() {
        return groups;
    }

    @Override
    public void setGroups(List<GroupMutableBean> groups) {
        this.groups = groups;
    }

    @Override
    public DimensionListMutableBean getDimensionList() {
        return dimensionList;
    }

    @Override
    public void setDimensionList(DimensionListMutableBean dimensionList) {
        this.dimensionList = dimensionList;
    }

    @Override
    public AttributeListMutableBean getAttributeList() {
        return attributeList;
    }

    @Override
    public void setAttributeList(AttributeListMutableBean attributeList) {
        this.attributeList = attributeList;
    }

    @Override
    public MeasureListMutableBean getMeasureList() {
        return measureList;
    }

    @Override
    public void setMeasureList(MeasureListMutableBean measureList) {
        this.measureList = measureList;
    }

    @Override
    public void addAttribute(AttributeMutableBean attribute) {
        if (this.attributeList == null) {
            this.attributeList = new AttributeListMutableBeanImpl();
        }
        this.attributeList.addAttribute(attribute);
    }

    @Override
    public void addDimension(DimensionMutableBean dimension) {
        if (this.dimensionList == null) {
            this.dimensionList = new DimensionListMutableBeanImpl();
        }
        this.dimensionList.addDimension(dimension);
    }
}
