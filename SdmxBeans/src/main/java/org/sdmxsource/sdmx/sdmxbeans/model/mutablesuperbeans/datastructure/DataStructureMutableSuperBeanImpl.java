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
package org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.datastructure;

import org.sdmxsource.sdmx.api.model.mutablesuperbeans.datastructure.*;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.AttributeSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DataStructureSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DimensionSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.GroupSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.base.MaintainableMutableSuperBeanImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Data structure mutable super bean.
 */
public class DataStructureMutableSuperBeanImpl extends MaintainableMutableSuperBeanImpl implements DataStructureMutableSuperBean {

    private static final long serialVersionUID = 1L;

    private List<DimensionMutableSuperBean> dimensions = new ArrayList<DimensionMutableSuperBean>();
    private List<AttributeMutableSuperBean> attributes = new ArrayList<AttributeMutableSuperBean>();
    private PrimaryMeasureMutableSuperBean primaryMeasure;
    private List<GroupMutableSuperBean> groups = new ArrayList<GroupMutableSuperBean>();

    /**
     * Instantiates a new Data structure mutable super bean.
     *
     * @param keyFamily the key family
     */
    public DataStructureMutableSuperBeanImpl(DataStructureSuperBean keyFamily) {
        super(keyFamily);
        if (keyFamily.getDimensions() != null) {
            for (DimensionSuperBean currentBean : keyFamily.getDimensions()) {
                this.dimensions.add(new DimensionMutableSuperBeanImpl(currentBean));
            }
        }
        if (keyFamily.getAttributes() != null) {
            for (AttributeSuperBean currentBean : keyFamily.getAttributes()) {
                this.attributes.add(new AttributeMutableSuperBeanImpl(currentBean));
            }
        }
        if (keyFamily.getGroups() != null) {
            for (GroupSuperBean currentBean : keyFamily.getGroups()) {
                this.groups.add(new GroupMutableSuperBeanImpl(currentBean));
            }
        }
        if (keyFamily.getPrimaryMeasure() != null) {
            this.primaryMeasure = new PrimaryMeasureMutableSuperBeanImpl(keyFamily.getPrimaryMeasure());
        }
    }

    /**
     * Instantiates a new Data structure mutable super bean.
     */
    public DataStructureMutableSuperBeanImpl() {

    }

    @Override
    public List<DimensionMutableSuperBean> getDimensions() {
        return dimensions;
    }

    @Override
    public void setDimensions(List<DimensionMutableSuperBean> dimensions) {
        this.dimensions = dimensions;
    }

    @Override
    public List<AttributeMutableSuperBean> getAttributes() {
        return attributes;
    }

    @Override
    public void setAttributes(List<AttributeMutableSuperBean> attributes) {
        this.attributes = attributes;
    }

    @Override
    public PrimaryMeasureMutableSuperBean getPrimaryMeasure() {
        return primaryMeasure;
    }

    @Override
    public void setPrimaryMeasure(PrimaryMeasureMutableSuperBean primaryMeasure) {
        this.primaryMeasure = primaryMeasure;
    }

    @Override
    public List<GroupMutableSuperBean> getGroups() {
        return groups;
    }

    @Override
    public void setGroups(List<GroupMutableSuperBean> groups) {
        this.groups = groups;
    }
}
