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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.categoryscheme;

import org.sdmx.resources.sdmxml.schemas.v20.structure.CategoryType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategoryBean;
import org.sdmxsource.sdmx.api.model.mutable.categoryscheme.CategoryMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.ItemBeanImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * The type Category bean.
 */
public class CategoryBeanImpl extends ItemBeanImpl implements CategoryBean {
    private static final long serialVersionUID = 1L;
    private List<CategoryBean> categories = new ArrayList<CategoryBean>();

    /**
     * Instantiates a new Category bean.
     *
     * @param parent the parent
     * @param bean   the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public CategoryBeanImpl(IdentifiableBean parent, CategoryMutableBean bean) {
        super(bean, parent);
        if (bean.getItems() != null) {
            for (CategoryMutableBean currentCat : bean.getItems()) {
                categories.add(new CategoryBeanImpl(this, currentCat));
            }
        }
    }


    /**
     * Instantiates a new Category bean.
     *
     * @param parent the parent
     * @param bean   the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public CategoryBeanImpl(IdentifiableBean parent, org.sdmx.resources.sdmxml.schemas.v21.structure.CategoryType bean) {
        super(bean, SDMX_STRUCTURE_TYPE.CATEGORY, parent);
        if (bean.getCategoryList() != null) {
            for (org.sdmx.resources.sdmxml.schemas.v21.structure.CategoryType currentCat : bean.getCategoryList()) {
                categories.add(new CategoryBeanImpl(this, currentCat));
            }
        }
    }


    /**
     * Instantiates a new Category bean.
     *
     * @param parent the parent
     * @param bean   the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public CategoryBeanImpl(IdentifiableBean parent, CategoryType bean) {
        super(bean, SDMX_STRUCTURE_TYPE.CATEGORY, bean.getId(), bean.getUri(), bean.getNameList(), bean.getDescriptionList(), bean.getAnnotations(), parent);
        if (bean.getCategoryList() != null) {
            for (CategoryType currentCat : bean.getCategoryList()) {
                categories.add(new CategoryBeanImpl(this, currentCat));
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////DEEP EQUALS							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean deepEquals(SDMXBean bean, boolean includeFinalProperties) {
        if (bean == null) {
            return false;
        }
        if (bean.getStructureType() == this.getStructureType()) {
            CategoryBean that = (CategoryBean) bean;
            if (!super.equivalent(categories, that.getItems(), includeFinalProperties)) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public List<CategoryBean> getItems() {
        return new ArrayList<CategoryBean>(categories);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES                           //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(categories, composites);
        return composites;
    }

}
