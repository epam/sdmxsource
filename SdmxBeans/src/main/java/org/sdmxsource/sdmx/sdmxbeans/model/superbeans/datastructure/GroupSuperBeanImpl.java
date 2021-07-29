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

import org.sdmxsource.sdmx.api.model.beans.datastructure.GroupBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DataStructureSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DimensionSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.GroupSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.base.IdentifiableSuperBeanImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The type Group super bean.
 */
public class GroupSuperBeanImpl extends IdentifiableSuperBeanImpl implements GroupSuperBean {
    private static final long serialVersionUID = 1L;
    private CrossReferenceBean attachmentConstraintRef;
    private List<DimensionSuperBean> dimension = new ArrayList<DimensionSuperBean>();
    private Map<String, DimensionSuperBean> dimensionMap = new HashMap<String, DimensionSuperBean>();
    private String id;

    /**
     * Instantiates a new Group super bean.
     *
     * @param group     the group
     * @param keyFamily the key family
     */
    public GroupSuperBeanImpl(GroupBean group, DataStructureSuperBean keyFamily) {
        super(group);
        this.attachmentConstraintRef = group.getAttachmentConstraintRef();
        for (String conceptId : group.getDimensionRefs()) {
            DimensionSuperBean dim = keyFamily.getDimensionById(conceptId);
            dimension.add(dim);
            dimensionMap.put(conceptId, dim);
        }
        this.id = group.getId();
    }

    @Override
    public CrossReferenceBean getAttachmentConstraintRef() {
        return attachmentConstraintRef;
    }

    @Override
    public DimensionSuperBean getDimensionById(String conceptId) {
        return dimensionMap.get(conceptId);
    }

    @Override
    public List<DimensionSuperBean> getDimensions() {
        return new ArrayList<DimensionSuperBean>(dimension);
    }

    @Override
    public String getId() {
        return id;
    }
}
