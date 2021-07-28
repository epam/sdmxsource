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
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategorySchemeBean;
import org.sdmxsource.sdmx.api.model.mutable.categoryscheme.CategoryMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.categoryscheme.CategorySchemeMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.categoryscheme.CategorySchemeBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.ItemSchemeMutableBeanImpl;


/**
 * The type Category scheme mutable bean.
 */
public class CategorySchemeMutableBeanImpl extends ItemSchemeMutableBeanImpl<CategoryMutableBean> implements CategorySchemeMutableBean {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Category scheme mutable bean.
     */
    public CategorySchemeMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.CATEGORY_SCHEME);
    }

    /**
     * Instantiates a new Category scheme mutable bean.
     *
     * @param bean the bean
     */
    public CategorySchemeMutableBeanImpl(CategorySchemeBean bean) {
        super(bean);

        // convert Category list to Mutable Category beans
        if (bean.getItems() != null) {
            for (CategoryBean category : bean.getItems()) {
                this.addItem(new CategoryMutableBeanImpl(category));
            }
        }
    }

    @Override
    public CategorySchemeBean getImmutableInstance() {
        return new CategorySchemeBeanImpl(this);
    }


    @Override
    public CategoryMutableBean createItem(String id, String name) {
        CategoryMutableBean cat = new CategoryMutableBeanImpl();
        cat.setId(id);
        cat.addName("en", name);
        addItem(cat);
        return cat;
    }


    @Override
    public boolean removeItem(String id) {
        if (id.contains(".")) {
            String[] idArray = id.split("\\.");
            for (CategoryMutableBean cat : getItems()) {
                if (cat.getId().equals(idArray[0])) {
                    return removeItem(cat, idArray, 1);
                }
            }
            return false;
        }
        return super.removeItem(id);

    }

    private boolean removeItem(CategoryMutableBean parent, String[] idArray, int pos) {
        String searchId = idArray[pos];
        CategoryMutableBean removeCat = null;
        for (CategoryMutableBean cat : parent.getItems()) {
            if (cat.getId().equals(searchId)) {
                if (idArray.length > ++pos) {
                    return removeItem(cat, idArray, ++pos);
                } else {
                    removeCat = cat;
                    break;
                }
            }
        }
        if (removeCat != null) {
            parent.getItems().remove(removeCat);
        }
        return removeCat != null;
    }
}
