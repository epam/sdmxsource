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
package org.sdmxsource.sdmx.sdmxbeans.model.superbeans.codelist;

import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.HierarchicalCodelistBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.HierarchyBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.HierarchicalCodelistSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.HierarchySuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.base.MaintainableSuperBeanImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The type Hierarchical codelist super bean.
 */
public class HierarchicalCodelistSuperBeanImpl extends MaintainableSuperBeanImpl implements HierarchicalCodelistSuperBean {
    private static final long serialVersionUID = 8192545947210661996L;

    private Set<HierarchySuperBean<HierarchicalCodelistSuperBean>> hierarchies = new HashSet<HierarchySuperBean<HierarchicalCodelistSuperBean>>();

    /**
     * Instantiates a new Hierarchical codelist super bean.
     *
     * @param hcl                 the hcl
     * @param referencedCodelists the referenced codelists
     */
    public HierarchicalCodelistSuperBeanImpl(HierarchicalCodelistBean hcl,
                                             List<CodelistBean> referencedCodelists) {
        super(hcl);
        for (HierarchyBean hBean : hcl.getHierarchies()) {
            hierarchies.add(new HierarchySuperBeanImpl(hBean, this, referencedCodelists));
        }
    }

    @Override
    public Set<HierarchySuperBean<HierarchicalCodelistSuperBean>> getHierarchies() {
        return new HashSet<HierarchySuperBean<HierarchicalCodelistSuperBean>>(hierarchies);
    }

    @Override
    public Set<MaintainableBean> getCompositeBeans() {
        Set<MaintainableBean> returnSet = super.getCompositeBeans();
        for (HierarchySuperBean<HierarchicalCodelistSuperBean> hierarchySuperBean : hierarchies) {
            returnSet.addAll(hierarchySuperBean.getCompositeBeans());
        }

        return returnSet;
    }
}
