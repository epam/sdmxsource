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
import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistRefBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.HierarchicalCodelistBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.HierarchyBean;
import org.sdmxsource.sdmx.api.model.mutable.base.HierarchyMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.codelist.HierarchicalCodelistMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.reference.CodelistRefMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.codelist.HierarchicalCodelistBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.MaintainableMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.reference.CodelistRefMutableBeanImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Hierarchical codelist mutable bean.
 */
public class HierarchicalCodelistMutableBeanImpl extends MaintainableMutableBeanImpl implements HierarchicalCodelistMutableBean {
    private static final long serialVersionUID = 1L;

    private List<HierarchyMutableBean> hierarchies = new ArrayList<HierarchyMutableBean>();
    private List<CodelistRefMutableBean> codelistRef = new ArrayList<CodelistRefMutableBean>();

    /**
     * Instantiates a new Hierarchical codelist mutable bean.
     */
    public HierarchicalCodelistMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.HIERARCHICAL_CODELIST);
    }

    /**
     * Instantiates a new Hierarchical codelist mutable bean.
     *
     * @param bean the bean
     */
    public HierarchicalCodelistMutableBeanImpl(HierarchicalCodelistBean bean) {
        super(bean);

        // Convert the lists into mutable bean lists
        if (bean.getHierarchies() != null) {
            for (HierarchyBean currentBean : bean.getHierarchies()) {
                this.addHierarchies(new HierarchyMutableBeanImpl(currentBean));
            }
        }
        if (bean.getCodelistRef() != null) {
            for (CodelistRefBean currentBean : bean.getCodelistRef()) {
                this.addCodelistRef(new CodelistRefMutableBeanImpl(currentBean));
            }
        }
    }

    @Override
    public HierarchicalCodelistBean getImmutableInstance() {
        return new HierarchicalCodelistBeanImpl(this);
    }

    @Override
    public void addHierarchies(HierarchyMutableBean hierarchy) {
        this.hierarchies.add(hierarchy);
    }

    @Override
    public void addCodelistRef(CodelistRefMutableBean codelistRef) {
        this.codelistRef.add(codelistRef);
    }

    @Override
    public List<HierarchyMutableBean> getHierarchies() {
        return hierarchies;
    }

    @Override
    public void setHierarchies(List<HierarchyMutableBean> hierarchies) {
        this.hierarchies = hierarchies;
    }

    @Override
    public List<CodelistRefMutableBean> getCodelistRef() {
        return codelistRef;
    }

    @Override
    public void setCodelistRef(List<CodelistRefMutableBean> codelistRefs) {
        this.codelistRef = codelistRefs;
    }
}
