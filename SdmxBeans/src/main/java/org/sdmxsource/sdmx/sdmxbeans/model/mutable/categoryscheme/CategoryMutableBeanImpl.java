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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.categoryscheme;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategoryBean;
import org.sdmxsource.sdmx.api.model.mutable.categoryscheme.CategoryMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.ItemMutableBeanImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Category mutable bean.
 */
public class CategoryMutableBeanImpl extends ItemMutableBeanImpl implements CategoryMutableBean {
    private static final long serialVersionUID = 4480929740944683260L;
    private List<CategoryMutableBean> categories = new ArrayList<CategoryMutableBean>();


    /**
     * Instantiates a new Category mutable bean.
     */
    public CategoryMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.CATEGORY);
    }

    /**
     * Instantiates a new Category mutable bean.
     *
     * @param bean the bean
     */
    public CategoryMutableBeanImpl(CategoryBean bean) {
        super(bean);

        // make into mutable category beans
        if (bean.getItems() != null) {
            categories = new ArrayList<CategoryMutableBean>();
            for (CategoryBean categoryBean : bean.getItems()) {
                this.addItem(new CategoryMutableBeanImpl(categoryBean));
            }
        }
    }

    @Override
    public List<CategoryMutableBean> getItems() {
        return categories;
    }

    @Override
    public void setItems(List<CategoryMutableBean> items) {
        this.categories = items;
    }

    @Override
    public void addItem(CategoryMutableBean item) {
        this.categories.add(item);
    }

}
