/**
 * Copyright (c) 2013 Metadata Technology Ltd.
 * <p>
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Lesser General Public License v 3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * This file is part of the SDMX Component Library.
 * <p>
 * The SDMX Component Library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * <p>
 * The SDMX Component Library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser
 * General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with The SDMX Component Library If not, see
 * http://www.gnu.org/licenses/lgpl.
 * <p>
 * Contributors:
 * Metadata Technology - initial API and implementation
 */
/**
 *
 */
package org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.categoryscheme;

import org.sdmxsource.sdmx.api.model.mutablesuperbeans.categoryscheme.CategoryMutableSuperBean;
import org.sdmxsource.sdmx.api.model.mutablesuperbeans.categoryscheme.CategorySchemeMutableSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.categoryscheme.CategorySuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.base.ItemMutableSuperBeanImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Category mutable super bean.
 *
 * @author Matt Nelson
 */
public class CategoryMutableSuperBeanImpl extends ItemMutableSuperBeanImpl<CategorySchemeMutableSuperBean> implements CategoryMutableSuperBean {
    private static final long serialVersionUID = 1L;

    private CategoryMutableSuperBean parent;
    private List<CategoryMutableSuperBean> children = new ArrayList<CategoryMutableSuperBean>();

    /**
     * Instantiates a new Category mutable super bean.
     *
     * @param categopryScheme the categopry scheme
     * @param cat             the cat
     * @param parent          the parent
     */
    public CategoryMutableSuperBeanImpl(CategorySchemeMutableSuperBean categopryScheme, CategorySuperBean cat, CategoryMutableSuperBean parent) {
        super(categopryScheme, cat);
        this.parent = parent;
        if (cat.hasChildren()) {
            for (CategorySuperBean currentBean : cat.getChildren()) {
                children.add(new CategoryMutableSuperBeanImpl(categopryScheme, currentBean, this));
            }
        }
    }

    /**
     * Instantiates a new Category mutable super bean.
     */
    public CategoryMutableSuperBeanImpl() {
    }

    @Override
    public CategoryMutableSuperBean getParent() {
        return parent;
    }

    @Override
    public void setParent(CategoryMutableSuperBean parent) {
        this.parent = parent;
    }

    @Override
    public List<CategoryMutableSuperBean> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<CategoryMutableSuperBean> children) {
        this.children = children;
    }
}
