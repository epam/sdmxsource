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
import org.sdmxsource.sdmx.api.model.beans.codelist.HierarchicalCodeBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.HierarchyBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.LevelBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.HierarchicalCodeSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.HierarchicalCodelistSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.HierarchySuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.base.CodeRefSuperBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.base.ItemSuperBeanImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * The type Hierarchy super bean.
 */
public class HierarchySuperBeanImpl extends ItemSuperBeanImpl<HierarchicalCodelistSuperBean> implements HierarchySuperBean<HierarchicalCodelistSuperBean> {
    private static final long serialVersionUID = 6722781540214953382L;
    private List<HierarchicalCodeSuperBean> codeRefs = new ArrayList<HierarchicalCodeSuperBean>();
    private HierarchyBean hierarchyBean;

    /**
     * Instantiates a new Hierarchy super bean.
     *
     * @param hierarchyBean the hierarchy bean
     * @param itemScheme    the item scheme
     * @param codelists     the codelists
     */
    public HierarchySuperBeanImpl(HierarchyBean hierarchyBean,
                                  HierarchicalCodelistSuperBean itemScheme,
                                  List<CodelistBean> codelists) {
        super(hierarchyBean, itemScheme);
        this.hierarchyBean = hierarchyBean;
        for (HierarchicalCodeBean currentCodeRef : hierarchyBean.getHierarchicalCodeBeans()) {
            codeRefs.add(new CodeRefSuperBeanImpl(hierarchyBean, currentCodeRef, codelists));
        }
    }

    @Override
    public List<HierarchicalCodeSuperBean> getCodes() {
        return new ArrayList<HierarchicalCodeSuperBean>(codeRefs);
    }

    @Override
    public LevelBean getLevel() {
        return hierarchyBean.getLevel();
    }

    @Override
    public LevelBean getLevelAtPosition(int levelPos) {
        return hierarchyBean.getLevelAtPosition(levelPos);
    }

    @Override
    public boolean hasFormalLevels() {
        return hierarchyBean.hasFormalLevels();
    }

    @Override
    public Set<MaintainableBean> getCompositeBeans() {
        Set<MaintainableBean> returnSet = super.getCompositeBeans();
        returnSet.add(hierarchyBean.getMaintainableParent());
        for (HierarchicalCodeSuperBean hCodeSuperBean : codeRefs) {
            returnSet.addAll(hCodeSuperBean.getCompositeBeans());
        }
        return returnSet;
    }
}
