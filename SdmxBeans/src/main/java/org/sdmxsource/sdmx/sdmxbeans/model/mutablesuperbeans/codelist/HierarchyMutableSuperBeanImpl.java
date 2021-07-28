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

import org.sdmxsource.sdmx.api.model.mutable.codelist.LevelMutableBean;
import org.sdmxsource.sdmx.api.model.mutablesuperbeans.codelist.CodeRefMutableSuperBean;
import org.sdmxsource.sdmx.api.model.mutablesuperbeans.codelist.HierarchyMutableSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.HierarchicalCodeSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.HierarchicalCodelistSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.HierarchySuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.codelist.LevelMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.base.NameableMutableSuperBeanImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Hierarchy mutable super bean.
 */
public class HierarchyMutableSuperBeanImpl extends NameableMutableSuperBeanImpl implements HierarchyMutableSuperBean {
    private static final long serialVersionUID = -8694260774111353443L;
    private List<CodeRefMutableSuperBean> codeRefs = new ArrayList<CodeRefMutableSuperBean>();
    private LevelMutableBean childLevel;
    private boolean hasFormalLevels;

    /**
     * Instantiates a new Hierarchy mutable super bean.
     */
    public HierarchyMutableSuperBeanImpl() {
        super();
    }

    /**
     * Instantiates a new Hierarchy mutable super bean.
     *
     * @param hSuperBean the h super bean
     */
    public HierarchyMutableSuperBeanImpl(HierarchySuperBean<HierarchicalCodelistSuperBean> hSuperBean) {
        super(hSuperBean);
        for (HierarchicalCodeSuperBean currentCodeRef : hSuperBean.getCodes()) {
            codeRefs.add(new CodeRefMutableSuperBeanImpl(currentCodeRef));
        }
        if (hSuperBean.getLevel() != null) {
            this.childLevel = new LevelMutableBeanImpl(hSuperBean.getLevel());
        }
        this.hasFormalLevels = hSuperBean.hasFormalLevels();
    }

    @Override
    public List<CodeRefMutableSuperBean> getCodeRefs() {
        return codeRefs;
    }

    @Override
    public void setCodeRefs(List<CodeRefMutableSuperBean> codeRefs) {
        this.codeRefs = codeRefs;
    }

    @Override
    public LevelMutableBean getChildLevel() {
        return childLevel;
    }

    @Override
    public void setChildLevel(LevelMutableBean level) {
        this.childLevel = level;
    }

    @Override
    public boolean hasFormalLevels() {
        return hasFormalLevels;
    }

    @Override
    public void setFormalLevels(boolean bool) {
        this.hasFormalLevels = bool;
    }

}
