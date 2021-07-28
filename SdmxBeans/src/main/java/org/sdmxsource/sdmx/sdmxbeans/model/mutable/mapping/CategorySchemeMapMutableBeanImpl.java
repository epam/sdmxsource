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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.mapping;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.mapping.CategoryMapBean;
import org.sdmxsource.sdmx.api.model.beans.mapping.CategorySchemeMapBean;
import org.sdmxsource.sdmx.api.model.mutable.mapping.CategoryMapMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.mapping.CategorySchemeMapMutableBean;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Category scheme map mutable bean.
 */
public class CategorySchemeMapMutableBeanImpl extends SchemeMapMutableBeanImpl implements CategorySchemeMapMutableBean {
    private static final long serialVersionUID = 8829767446075036603L;
    private List<CategoryMapMutableBean> categoryMaps = new ArrayList<CategoryMapMutableBean>();

    /**
     * Instantiates a new Category scheme map mutable bean.
     */
    public CategorySchemeMapMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.CATEGORY_SCHEME_MAP);
    }

    /**
     * Instantiates a new Category scheme map mutable bean.
     *
     * @param bean the bean
     */
    public CategorySchemeMapMutableBeanImpl(CategorySchemeMapBean bean) {
        super(bean);
        //change CategoryMap list beans to mutable CategoryMap beans
        for (CategoryMapBean map : bean.getCategoryMaps()) {
            this.addCategoryMap(new CategoryMapMutableBeanImpl(map));
        }
    }

    @Override
    public List<CategoryMapMutableBean> getCategoryMaps() {
        return categoryMaps;
    }

    @Override
    public void setCategoryMaps(List<CategoryMapMutableBean> categoryMaps) {
        this.categoryMaps = categoryMaps;
    }

    @Override
    public void addCategoryMap(CategoryMapMutableBean categoryMap) {
        this.categoryMaps.add(categoryMap);
    }
}
