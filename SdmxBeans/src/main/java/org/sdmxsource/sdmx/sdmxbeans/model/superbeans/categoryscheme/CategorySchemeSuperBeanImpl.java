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

import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategoryBean;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategorySchemeBean;
import org.sdmxsource.sdmx.api.model.superbeans.categoryscheme.CategorySchemeSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.categoryscheme.CategorySuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.base.MaintainableSuperBeanImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * The type Category scheme super bean.
 *
 * @author Matt Nelson
 */
public class CategorySchemeSuperBeanImpl extends MaintainableSuperBeanImpl implements CategorySchemeSuperBean {
    private static final long serialVersionUID = 1L;

    private List<CategorySuperBean> categories;

    private CategorySchemeBean builtFrom;

    /**
     * Instantiates a new Category scheme super bean.
     *
     * @param categoryScheme the category scheme
     */
    public CategorySchemeSuperBeanImpl(CategorySchemeBean categoryScheme) {
        super(categoryScheme);
        this.builtFrom = categoryScheme;
        categories = new ArrayList<CategorySuperBean>();
        if (categoryScheme.getItems() != null) {
            for (CategoryBean currentCategory : categoryScheme.getItems()) {
                CategorySuperBean category = new CategorySuperBeanImpl(this, currentCategory, null);
                categories.add(category);
            }
        }
    }

    @Override
    public CategorySchemeBean getBuiltFrom() {
        return builtFrom;
    }

    @Override
    public Set<MaintainableBean> getCompositeBeans() {
        Set<MaintainableBean> returnSet = super.getCompositeBeans();
        returnSet.add(builtFrom);
        return returnSet;
    }

    /* (non-Javadoc)
     * @see org.sdmxsource.dataweb.external.model.CategoryScheme#getCategories()
     */
    @Override
    public List<CategorySuperBean> getCategories() {
        return new ArrayList<CategorySuperBean>(categories);
    }
}
