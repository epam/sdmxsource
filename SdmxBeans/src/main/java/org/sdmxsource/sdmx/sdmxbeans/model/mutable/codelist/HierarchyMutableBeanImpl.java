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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.codelist;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.codelist.HierarchicalCodeBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.HierarchyBean;
import org.sdmxsource.sdmx.api.model.mutable.base.HierarchyMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.codelist.LevelMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.reference.CodeRefMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.NameableMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.reference.CodeRefMutableBeanImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Hierarchy mutable bean.
 */
public class HierarchyMutableBeanImpl extends NameableMutableBeanImpl implements HierarchyMutableBean {
    private static final long serialVersionUID = 1L;

    private List<CodeRefMutableBean> codeRefs = new ArrayList<CodeRefMutableBean>();
    private LevelMutableBean level;
    private boolean hasFormalLevels;

    /**
     * Instantiates a new Hierarchy mutable bean.
     */
    public HierarchyMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.HIERARCHY);
    }

    /**
     * Instantiates a new Hierarchy mutable bean.
     *
     * @param bean the bean
     */
    public HierarchyMutableBeanImpl(HierarchyBean bean) {
        super(bean);
        if (bean.getHierarchicalCodeBeans() != null) {
            for (HierarchicalCodeBean currentBean : bean.getHierarchicalCodeBeans()) {
                this.addHierarchicalCodeBean(new CodeRefMutableBeanImpl(currentBean));
            }
        }
        if (bean.getLevel() != null) {
            this.level = new LevelMutableBeanImpl(bean.getLevel());
        }
        this.hasFormalLevels = bean.hasFormalLevels();
    }

    @Override
    public boolean isFormalLevels() {
        return hasFormalLevels;
    }

    @Override
    public void setFormalLevels(boolean bool) {
        this.hasFormalLevels = bool;
    }

    @Override
    public LevelMutableBean getChildLevel() {
        return level;
    }

    @Override
    public void setChildLevel(LevelMutableBean level) {
        this.level = level;
    }

    @Override
    public void addHierarchicalCodeBean(CodeRefMutableBean codeRef) {
        this.codeRefs.add(codeRef);
    }

    @Override
    public List<CodeRefMutableBean> getHierarchicalCodeBeans() {
        return codeRefs;
    }

    @Override
    public void setHierarchicalCodeBeans(List<CodeRefMutableBean> codeRefs) {
        this.codeRefs = codeRefs;
    }
}
