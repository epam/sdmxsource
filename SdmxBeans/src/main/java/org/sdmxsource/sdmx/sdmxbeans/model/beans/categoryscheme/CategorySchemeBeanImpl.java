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

import org.sdmx.resources.sdmxml.schemas.v20.structure.CategorySchemeType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.CategoryType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategoryBean;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategorySchemeBean;
import org.sdmxsource.sdmx.api.model.mutable.categoryscheme.CategoryMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.categoryscheme.CategorySchemeMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.ItemSchemeBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.categoryscheme.CategorySchemeMutableBeanImpl;

import javax.xml.bind.ValidationException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The type Category scheme bean.
 */
public class CategorySchemeBeanImpl extends ItemSchemeBeanImpl<CategoryBean> implements CategorySchemeBean {
    private static final long serialVersionUID = 1L;

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM ITSELF, CREATES STUB BEAN //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private CategorySchemeBeanImpl(CategorySchemeBean bean, URL actualLocation, boolean isServiceUrl) {
        super(bean, actualLocation, isServiceUrl);
    }

    /**
     * Instantiates a new Category scheme bean.
     *
     * @param categoryScheme the category scheme
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public CategorySchemeBeanImpl(CategorySchemeMutableBean categoryScheme) {
        super(categoryScheme);
        try {
            if (categoryScheme.getItems() != null) {
                for (CategoryMutableBean currentcategory : categoryScheme.getItems()) {
                    items.add(new CategoryBeanImpl(this, currentcategory));
                }
            }
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
        try {
            validate();
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
    }


    /**
     * Instantiates a new Category scheme bean.
     *
     * @param categoryScheme the category scheme
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public CategorySchemeBeanImpl(org.sdmx.resources.sdmxml.schemas.v21.structure.CategorySchemeType categoryScheme) {
        super(categoryScheme, SDMX_STRUCTURE_TYPE.CATEGORY_SCHEME);

        try {
            for (org.sdmx.resources.sdmxml.schemas.v21.structure.CategoryType currentcategory : categoryScheme.getCategoryList()) {
                items.add(new CategoryBeanImpl(this, currentcategory));
            }
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
        try {
            validate();
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
    }

    /**
     * Instantiates a new Category scheme bean.
     *
     * @param categoryScheme the category scheme
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public CategorySchemeBeanImpl(CategorySchemeType categoryScheme) {
        super(categoryScheme,
                SDMX_STRUCTURE_TYPE.CATEGORY_SCHEME,
                categoryScheme.getValidTo(),
                categoryScheme.getValidFrom(),
                categoryScheme.getVersion(),
                createTertiary(categoryScheme.isSetIsFinal(), categoryScheme.getIsFinal()),
                categoryScheme.getAgencyID(),
                categoryScheme.getId(),
                categoryScheme.getUri(),
                categoryScheme.getNameList(),
                categoryScheme.getDescriptionList(),
                createTertiary(categoryScheme.isSetIsExternalReference(), categoryScheme.getIsExternalReference()),
                categoryScheme.getAnnotations());

        try {
            for (CategoryType currentcategory : categoryScheme.getCategoryList()) {
                items.add(new CategoryBeanImpl(this, currentcategory));
            }
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
        try {
            validate();
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////DEEP VALIDATION						 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean deepEquals(SDMXBean bean, boolean includeFinalProperties) {
        if (bean == null) {
            return false;
        }
        if (bean.getStructureType() == this.getStructureType()) {
            return super.deepEqualsInternal((CategorySchemeBean) bean, includeFinalProperties);
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() throws ValidationException {
        Set<String> urns = new HashSet<String>();
        if (items != null) {
            for (CategoryBean category : items) {
                if (urns.contains(category.getUrn())) {
                    throw new SdmxSemmanticException(ExceptionCode.DUPLICATE_URN, category.getUrn());
                }
                urns.add(category.getUrn());
            }
        }
    }

    @Override
    protected void validateId(boolean startWithIntAllowed) {
        //Not allowed to start with an integer
        super.validateId(false);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS  							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public CategorySchemeMutableBean getMutableInstance() {
        return new CategorySchemeMutableBeanImpl(this);
    }

    @Override
    public CategoryBean getCategory(String... id) {
        return getCategory(getItems(), id, 0);
    }

    private CategoryBean getCategory(String id, List<CategoryBean> categories) {
        for (CategoryBean currentCategory : categories) {
            if (currentCategory.getId().equals(id)) {
                return currentCategory;
            }
        }
        return null;
    }

    private CategoryBean getCategory(List<CategoryBean> categories, String[] ids, int position) {
        String categoryId = ids[position];

        CategoryBean cat = getCategory(categoryId, categories);
        if (cat == null) {
            return null;
        }
        int newPos = ++position;
        if (ids.length > newPos) {
            return getCategory(cat.getItems(), ids, newPos);
        }
        return cat;
    }

    @Override
    public CategorySchemeBean getStub(URL actualLocation, boolean isServiceUrl) {
        return new CategorySchemeBeanImpl(this, actualLocation, isServiceUrl);
    }
}
