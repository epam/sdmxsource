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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21;

import org.sdmx.resources.sdmxml.schemas.v21.structure.CategorySchemeType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.CategoryType;
import org.sdmxsource.sdmx.api.builder.Builder;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategoryBean;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategorySchemeBean;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers.CategoryBeanAssembler;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers.MaintainableBeanAssembler;


/**
 * The type Category scheme xml bean builder.
 */
public class CategorySchemeXmlBeanBuilder extends MaintainableBeanAssembler implements Builder<CategorySchemeType, CategorySchemeBean> {

    private final CategoryBeanAssembler categoryBeanAssemblerBean = new CategoryBeanAssembler();

    @Override
    public CategorySchemeType build(CategorySchemeBean buildFrom) throws SdmxException {
        // Create outgoing build
        CategorySchemeType returnType = CategorySchemeType.Factory.newInstance();
        if (buildFrom.isPartial()) {
            returnType.setIsPartial(true);
        }
        // Populate it from inherited super
        assembleMaintainable(returnType, buildFrom);
        // Populate it using this class's specifics
        for (CategoryBean eachCat : buildFrom.getItems()) {
            CategoryType newCat = returnType.addNewCategory();
            categoryBeanAssemblerBean.assemble(newCat, eachCat);
            assembleChildCategories(newCat, eachCat);
        }
        return returnType;
    }

    /**
     * Recursively pickup and assemble child Categories of Categories
     *
     * @param catType parent destination Category xml bean
     * @param catBean parent source Category bean
     */
    private void assembleChildCategories(CategoryType catType, CategoryBean catBean) {
        for (CategoryBean eachCat : catBean.getItems()) {
            CategoryType newCat = catType.addNewCategory();
            categoryBeanAssemblerBean.assemble(newCat, eachCat);
            assembleChildCategories(newCat, eachCat);
        }
    }
}
