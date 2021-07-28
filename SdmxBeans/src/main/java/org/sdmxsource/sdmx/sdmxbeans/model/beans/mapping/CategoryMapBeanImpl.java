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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.mapping;

import org.sdmx.resources.sdmxml.schemas.v20.structure.CategoryIDType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.CategoryMapType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.base.SdmxStructureBean;
import org.sdmxsource.sdmx.api.model.beans.mapping.CategoryMapBean;
import org.sdmxsource.sdmx.api.model.mutable.mapping.CategoryMapMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.SdmxStructureBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Category map bean.
 */
public class CategoryMapBeanImpl extends SdmxStructureBeanImpl implements CategoryMapBean {
    private static final long serialVersionUID = 1L;
    private String alias;
    private List<String> sourceId = new ArrayList<String>();
    private List<String> targetId = new ArrayList<String>();


    /**
     * Instantiates a new Category map bean.
     *
     * @param bean   the bean
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected CategoryMapBeanImpl(CategoryMapMutableBean bean, SdmxStructureBean parent) {
        super(bean, parent);
        this.alias = bean.getAlias();
        if (bean.getSourceId() != null) {
            this.sourceId = new ArrayList<String>(bean.getSourceId());
        }
        if (bean.getTargetId() != null) {
            this.targetId = new ArrayList<String>(bean.getTargetId());
        }
        validate();
    }


    /**
     * Instantiates a new Category map bean.
     *
     * @param catType the cat type
     * @param parent  the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected CategoryMapBeanImpl(org.sdmx.resources.sdmxml.schemas.v21.structure.CategoryMapType catType, SdmxStructureBean parent) {
        super(SDMX_STRUCTURE_TYPE.CATEGORY_MAP, parent);
        //FUNC 2.1 - this referernce is wrong as it only allows for a single id
        validate();
    }


    /**
     * Instantiates a new Category map bean.
     *
     * @param catType the cat type
     * @param parent  the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected CategoryMapBeanImpl(CategoryMapType catType, SdmxStructureBean parent) {
        super(SDMX_STRUCTURE_TYPE.CATEGORY_MAP, parent);
        this.alias = catType.getCategoryAlias();
        populateCategoryIdList(this.sourceId, catType.getCategoryID());
        populateCategoryIdList(this.targetId, catType.getTargetCategoryID());
        validate();
    }

    private void populateCategoryIdList(List<String> catIdList, CategoryIDType currentIdType) {
        catIdList.add(currentIdType.getID());
        if (currentIdType.getCategoryID() != null) {
            populateCategoryIdList(catIdList, currentIdType.getCategoryID());
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
            CategoryMapBean that = (CategoryMapBean) bean;
            if (!ObjectUtil.equivalentCollection(sourceId, that.getSourceId())) {
                return false;
            }
            if (!ObjectUtil.equivalentCollection(targetId, that.getTargetId())) {
                return false;
            }
            if (!ObjectUtil.equivalent(alias, that.getAlias())) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Validate.
     *
     * @throws SdmxSemmanticException the sdmx semmantic exception
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected void validate() throws SdmxSemmanticException {
        if (this.sourceId == null) {
            throw new SdmxSemmanticException(ExceptionCode.BEAN_MISSING_REQUIRED_ELEMENT, "CategoryMap", "CategoryID");
        }
        if (this.targetId == null) {
            throw new SdmxSemmanticException(ExceptionCode.BEAN_MISSING_REQUIRED_ELEMENT, "CategoryMap", "TargetCategoryID");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public List<String> getSourceId() {
        return new ArrayList<String>(sourceId);
    }

    @Override
    public List<String> getTargetId() {
        return new ArrayList<String>(targetId);
    }
}
