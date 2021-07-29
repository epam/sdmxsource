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
package org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.codelist;

import org.sdmxsource.sdmx.api.model.mutablesuperbeans.codelist.HierarchicalCodelistMutableSuperBean;
import org.sdmxsource.sdmx.api.model.mutablesuperbeans.codelist.HierarchyMutableSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.HierarchicalCodelistSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.HierarchySuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.base.MaintainableMutableSuperBeanImpl;

import java.util.HashSet;
import java.util.Set;


/**
 * The type Hierarchical codelist mutable super bean.
 */
public class HierarchicalCodelistMutableSuperBeanImpl extends MaintainableMutableSuperBeanImpl implements HierarchicalCodelistMutableSuperBean {
    private static final long serialVersionUID = -2632900501883738769L;
    private Set<HierarchyMutableSuperBean> hierarchies = new HashSet<HierarchyMutableSuperBean>();


    /**
     * Instantiates a new Hierarchical codelist mutable super bean.
     */
    public HierarchicalCodelistMutableSuperBeanImpl() {
    }

    /**
     * Instantiates a new Hierarchical codelist mutable super bean.
     *
     * @param hCodelist the h codelist
     */
    public HierarchicalCodelistMutableSuperBeanImpl(HierarchicalCodelistSuperBean hCodelist) {
        super(hCodelist);
        for (HierarchySuperBean<HierarchicalCodelistSuperBean> hSuperBean : hCodelist.getHierarchies()) {
            this.hierarchies.add(new HierarchyMutableSuperBeanImpl(hSuperBean));
        }
    }

    @Override
    public Set<HierarchyMutableSuperBean> getHierarchies() {
        return hierarchies;
    }

    @Override
    public void setHierarchies(Set<HierarchyMutableSuperBean> hierarchies) {
        this.hierarchies = hierarchies;
    }
}
