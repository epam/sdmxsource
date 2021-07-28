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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.datastructure;

import org.sdmx.resources.sdmxml.schemas.v20.structure.ComponentsType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.KeyFamilyType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.DimensionType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionListBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.DimensionListMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.DimensionMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.IdentifiableBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;


/**
 * The type Dimension list bean.
 */
public class DimensionListBeanImpl extends IdentifiableBeanImpl implements DimensionListBean {
    private static final long serialVersionUID = 8263386049386394325L;
    private List<DimensionBean> dimensions = new ArrayList<DimensionBean>();


    /**
     * Instantiates a new Dimension list bean.
     *
     * @param bean   the bean
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS 			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public DimensionListBeanImpl(DimensionListMutableBean bean, DataStructureBean parent) {
        super(bean, parent);
        if (bean.getDimensions() != null) {
            int pos = 1;
            for (DimensionMutableBean currentDimension : bean.getDimensions()) {
                this.dimensions.add(new DimensionBeanImpl(currentDimension, pos, this));
                pos++;
            }
        }
    }

    /**
     * Instantiates a new Dimension list bean.
     *
     * @param dimensionList the dimension list
     * @param parent        the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public DimensionListBeanImpl(org.sdmx.resources.sdmxml.schemas.v21.structure.DimensionListType dimensionList, MaintainableBean parent) {
        super(dimensionList, SDMX_STRUCTURE_TYPE.DIMENSION_DESCRIPTOR, parent);
        int pos = 1;
        if (dimensionList.getDimensionList() != null) {
            for (DimensionType dimension : dimensionList.getDimensionList()) {
                dimensions.add(new DimensionBeanImpl(dimension, this, pos));
                pos++;
            }
        }
        if (ObjectUtil.validCollection(dimensionList.getMeasureDimensionList())) {
            if (dimensionList.getMeasureDimensionList().size() > 1) {
                throw new SdmxSemmanticException("Can not have more then one measure dimension");
            }
            dimensions.add(new DimensionBeanImpl(dimensionList.getMeasureDimensionList().get(0), this, pos));
            pos++;
        }
        if (ObjectUtil.validCollection(dimensionList.getTimeDimensionList())) {
            if (dimensionList.getTimeDimensionList().size() > 1) {
                throw new SdmxSemmanticException("Can not have more then one time dimension");
            }
            dimensions.add(new DimensionBeanImpl(dimensionList.getTimeDimensionList().get(0), this, pos));
        }
        validateDimensionList();
    }


    /**
     * Instantiates a new Dimension list bean.
     *
     * @param bean   the bean
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public DimensionListBeanImpl(KeyFamilyType bean, MaintainableBean parent) {
        super(FIXED_ID, SDMX_STRUCTURE_TYPE.DIMENSION_DESCRIPTOR, parent);
        int pos = 1;
        ComponentsType components = bean.getComponents();
        try {
            if (components != null) {
                for (org.sdmx.resources.sdmxml.schemas.v20.structure.DimensionType currentDimension : components.getDimensionList()) {
                    dimensions.add(new DimensionBeanImpl(currentDimension, this, pos));
                    pos++;
                }
                if (components.getTimeDimension() != null) {
                    dimensions.add(new DimensionBeanImpl(components.getTimeDimension(), this, pos));
                }
            }
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
        validateDimensionList();
    }

    /**
     * Instantiates a new Dimension list bean.
     *
     * @param bean   the bean
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public DimensionListBeanImpl(org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.KeyFamilyType bean, MaintainableBean parent) {
        super(FIXED_ID, SDMX_STRUCTURE_TYPE.DIMENSION_DESCRIPTOR, parent);
        org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.ComponentsType components = bean.getComponents();
        int pos = 1;
        if (components != null) {
            for (org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.DimensionType currentDimension : components.getDimensionList()) {
                dimensions.add(new DimensionBeanImpl(currentDimension, this, pos));
                pos++;
            }
            if (components.getTimeDimension() != null) {
                dimensions.add(new DimensionBeanImpl(components.getTimeDimension(), this, pos));
            }
        }
        validateDimensionList();
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
            DimensionListBean that = (DimensionListBean) bean;
            if (!super.equivalent(getDimensions(), that.getDimensions(), includeFinalProperties)) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validateDimensionList() {
        List<String> idList = new ArrayList<String>();
        Collections.sort(dimensions);
        for (DimensionBean currentDimensionBean : dimensions) {
            if (idList.contains(currentDimensionBean.getId())) {
                throw new SdmxSemmanticException("Duplicate Dimension Id : " + currentDimensionBean.getId());
            }
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public String getId() {
        return FIXED_ID;
    }

    @Override
    public List<DimensionBean> getDimensions() {
        return new ArrayList<DimensionBean>(dimensions);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(dimensions, composites);
        return composites;
    }
}
