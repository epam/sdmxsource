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
import org.sdmxsource.sdmx.api.model.superbeans.categoryscheme.CategorySchemeSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.categoryscheme.CategorySuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.base.MaintainableMutableSuperBeanImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Category scheme mutable super bean.
 *
 * @author Matt Nelson
 */
public class CategorySchemeMutableSuperBeanImpl extends MaintainableMutableSuperBeanImpl implements CategorySchemeMutableSuperBean {
    private static final long serialVersionUID = 1L;

    private List<CategoryMutableSuperBean> categories = new ArrayList<CategoryMutableSuperBean>();


    /**
     * Instantiates a new Category scheme mutable super bean.
     *
     * @param categoryScheme the category scheme
     */
    public CategorySchemeMutableSuperBeanImpl(CategorySchemeSuperBean categoryScheme) {
        super(categoryScheme);
        if (categoryScheme.getCategories() != null) {
            for (CategorySuperBean currentBean : categoryScheme.getCategories()) {
                categories.add(new CategoryMutableSuperBeanImpl(this, currentBean, null));
            }
        }
    }

    /**
     * Instantiates a new Category scheme mutable super bean.
     */
    public CategorySchemeMutableSuperBeanImpl() {
    }

    @Override
    public List<CategoryMutableSuperBean> getCategories() {
        return categories;
    }

    @Override
    public void setCategories(List<CategoryMutableSuperBean> categories) {
        this.categories = categories;
    }
}
