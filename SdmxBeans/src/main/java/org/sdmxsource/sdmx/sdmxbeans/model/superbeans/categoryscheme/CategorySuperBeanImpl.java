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
package org.sdmxsource.sdmx.sdmxbeans.model.superbeans.categoryscheme;

import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategoryBean;
import org.sdmxsource.sdmx.api.model.superbeans.categoryscheme.CategorySchemeSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.categoryscheme.CategorySuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.base.ItemSuperBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Category super bean.
 *
 * @author Matt Nelson
 */
public class CategorySuperBeanImpl extends ItemSuperBeanImpl<CategorySchemeSuperBean> implements CategorySuperBean {
    private static final long serialVersionUID = 1L;

    private CategorySuperBean parent;
    private List<CategorySuperBean> children;

    /**
     * Instantiates a new Category super bean.
     *
     * @param catScheme the cat scheme
     * @param category  the category
     * @param parent    the parent
     */
    public CategorySuperBeanImpl(CategorySchemeSuperBean catScheme,
                                 CategoryBean category,
                                 CategorySuperBean parent) {
        super(category, catScheme);
        this.children = new ArrayList<CategorySuperBean>();
        this.parent = parent;

        if (ObjectUtil.validCollection(category.getItems())) {
            for (CategoryBean currentChild : category.getItems()) {
                CategorySuperBean child = new CategorySuperBeanImpl(catScheme, currentChild, this);
                children.add(child);
            }
        }
    }

    /* (non-Javadoc)
     * @see org.sdmxsource.dataweb.external.model.Hierarchical#getChildren()
     */
    @Override
    public List<CategorySuperBean> getChildren() {
        return new ArrayList<CategorySuperBean>(children);
    }

    /* (non-Javadoc)
     * @see org.sdmxsource.dataweb.external.model.Hierarchical#getParent()
     */
    @Override
    public CategorySuperBean getParent() {
        return parent;
    }

    /* (non-Javadoc)
     * @see org.sdmxsource.dataweb.external.model.Hierarchical#hasChildren()
     */
    @Override
    public boolean hasChildren() {
        return this.children != null && this.children.size() > 0;
    }

    /* (non-Javadoc)
     * @see org.sdmxsource.dataweb.external.model.Hierarchical#hasParent()
     */
    @Override
    public boolean hasParent() {
        return this.parent != null;
    }
}
