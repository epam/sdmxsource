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

import org.sdmx.resources.sdmxml.schemas.v20.structure.CategoryMapType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.CategorySchemeMapType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.mapping.CategoryMapBean;
import org.sdmxsource.sdmx.api.model.beans.mapping.CategorySchemeMapBean;
import org.sdmxsource.sdmx.api.model.beans.mapping.StructureSetBean;
import org.sdmxsource.sdmx.api.model.mutable.mapping.CategoryMapMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.mapping.CategorySchemeMapMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.util.RefUtil;
import org.sdmxsource.sdmx.util.beans.reference.CrossReferenceBeanImpl;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * The type Category scheme map bean.
 */
public class CategorySchemeMapBeanImpl extends SchemeMapBeanImpl implements CategorySchemeMapBean {
    private static final long serialVersionUID = 1L;

    private List<CategoryMapBean> categoryMaps = new ArrayList<CategoryMapBean>();

    /**
     * Instantiates a new Category scheme map bean.
     *
     * @param bean   the bean
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public CategorySchemeMapBeanImpl(CategorySchemeMapMutableBean bean, StructureSetBean parent) {
        super(bean, parent);
        if (bean.getCategoryMaps() != null) {
            categoryMaps = new ArrayList<CategoryMapBean>();
            for (CategoryMapMutableBean catMap : bean.getCategoryMaps()) {
                CategoryMapBean cRef = new CategoryMapBeanImpl(catMap, this);
                categoryMaps.add(cRef);
            }
        }
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
    }


    /**
     * Instantiates a new Category scheme map bean.
     *
     * @param catMapType the cat map type
     * @param parent     the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public CategorySchemeMapBeanImpl(org.sdmx.resources.sdmxml.schemas.v21.structure.CategorySchemeMapType catMapType, StructureSetBean parent) {
        super(catMapType, SDMX_STRUCTURE_TYPE.CATEGORY_SCHEME_MAP, parent);

        this.sourceRef = RefUtil.createReference(this, catMapType.getSource());
        this.targetRef = RefUtil.createReference(this, catMapType.getTarget());

        if (catMapType.getCategoryMapList() != null) {
            for (org.sdmx.resources.sdmxml.schemas.v21.structure.CategoryMapType catMap : catMapType.getCategoryMapList()) {
                CategoryMapBean cRef = new CategoryMapBeanImpl(catMap, this);
                categoryMaps.add(cRef);
            }
        }
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
    }


    /**
     * Instantiates a new Category scheme map bean.
     *
     * @param catMapType the cat map type
     * @param parent     the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public CategorySchemeMapBeanImpl(CategorySchemeMapType catMapType, StructureSetBean parent) {
        super(catMapType, SDMX_STRUCTURE_TYPE.CATEGORY_SCHEME_MAP,
                catMapType.getId(), null, catMapType.getNameList(),
                catMapType.getDescriptionList(), catMapType.getAnnotations(), parent);

        if (catMapType.getCategorySchemeRef() != null) {
            if (catMapType.getCategorySchemeRef().getURN() != null) {
                this.sourceRef = new CrossReferenceBeanImpl(this, catMapType.getCategorySchemeRef().getURN());
            } else {
                this.sourceRef = new CrossReferenceBeanImpl(this, catMapType.getCategorySchemeRef().getAgencyID(),
                        catMapType.getCategorySchemeRef().getCategorySchemeID(),
                        catMapType.getCategorySchemeRef().getVersion(), SDMX_STRUCTURE_TYPE.CATEGORY_SCHEME);
            }
        }
        if (catMapType.getTargetCategorySchemeRef() != null) {
            if (catMapType.getTargetCategorySchemeRef().getURN() != null) {
                this.targetRef = new CrossReferenceBeanImpl(this, catMapType.getTargetCategorySchemeRef().getURN());
            } else {
                this.targetRef = new CrossReferenceBeanImpl(this, catMapType.getTargetCategorySchemeRef().getAgencyID(),
                        catMapType.getTargetCategorySchemeRef().getCategorySchemeID(),
                        catMapType.getTargetCategorySchemeRef().getVersion(), SDMX_STRUCTURE_TYPE.CATEGORY_SCHEME);
            }
        }
        if (catMapType.getCategoryMapList() != null) {
            for (CategoryMapType catMap : catMapType.getCategoryMapList()) {
                CategoryMapBean cRef = new CategoryMapBeanImpl(catMap, this);
                categoryMaps.add(cRef);
            }
        }
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
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
            CategorySchemeMapBean that = (CategorySchemeMapBean) bean;
            if (!super.equivalent(categoryMaps, that.getCategoryMaps(), includeFinalProperties)) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
        }
        return false;
    }

    /**
     * Validate.
     *
     * @throws ValidationException the validation exception
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected void validate() throws ValidationException {
        if (this.sourceRef == null) {
            throw new SdmxSemmanticException(ExceptionCode.BEAN_MISSING_REQUIRED_ELEMENT, this.structureType, "CategorySchemeRef");
        }
        if (this.targetRef == null) {
            throw new SdmxSemmanticException(ExceptionCode.BEAN_MISSING_REQUIRED_ELEMENT, this.structureType, "TargetCategorySchemeRef");
        }
        if (this.categoryMaps == null) {
            throw new SdmxSemmanticException(ExceptionCode.BEAN_MISSING_REQUIRED_ELEMENT, this.structureType, "CategoryMap");
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public List<CategoryMapBean> getCategoryMaps() {
        return new ArrayList<CategoryMapBean>(categoryMaps);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(categoryMaps, composites);
        return composites;
    }
}
