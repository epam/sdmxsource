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

import org.sdmx.resources.sdmxml.schemas.v20.structure.PrimaryMeasureType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.MeasureListBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.PrimaryMeasureBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.MeasureListMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.IdentifiableBeanImpl;

import java.util.Set;


/**
 * The type Measure list bean.
 */
public class MeasureListBeanImpl extends IdentifiableBeanImpl implements MeasureListBean {
    private static final long serialVersionUID = 8902956419939417243L;
    private PrimaryMeasureBean primaryMeasureBean;


    /**
     * Instantiates a new Measure list bean.
     *
     * @param measureList the measure list
     * @param parent      the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public MeasureListBeanImpl(MeasureListMutableBean measureList, MaintainableBean parent) {
        super(measureList, parent);
        if (measureList.getPrimaryMeasure() != null) {
            this.primaryMeasureBean = new PrimaryMeasureBeanImpl(measureList.getPrimaryMeasure(), this);
        }
    }

    /**
     * Instantiates a new Measure list bean.
     *
     * @param measureList the measure list
     * @param parent      the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public MeasureListBeanImpl(org.sdmx.resources.sdmxml.schemas.v21.structure.MeasureListType measureList, MaintainableBean parent) {
        super(measureList, SDMX_STRUCTURE_TYPE.MEASURE_DESCRIPTOR, parent);
        if (measureList.getPrimaryMeasure() != null) {
            this.primaryMeasureBean = new PrimaryMeasureBeanImpl(measureList.getPrimaryMeasure(), this);
        }
    }

    /**
     * Instantiates a new Measure list bean.
     *
     * @param primaryMeasure the primary measure
     * @param parent         the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public MeasureListBeanImpl(PrimaryMeasureType primaryMeasure, MaintainableBean parent) {
        super(FIXED_ID, SDMX_STRUCTURE_TYPE.MEASURE_DESCRIPTOR, parent);
        this.primaryMeasureBean = new PrimaryMeasureBeanImpl(primaryMeasure, this);
    }

    /**
     * Instantiates a new Measure list bean.
     *
     * @param primaryMeasure the primary measure
     * @param parent         the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public MeasureListBeanImpl(org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.PrimaryMeasureType primaryMeasure, MaintainableBean parent) {
        super(FIXED_ID, SDMX_STRUCTURE_TYPE.MEASURE_DESCRIPTOR, parent);
        this.primaryMeasureBean = new PrimaryMeasureBeanImpl(primaryMeasure, this);
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
            MeasureListBean that = (MeasureListBean) bean;
            if (!super.equivalent(primaryMeasureBean, that.getPrimaryMeasure(), includeFinalProperties)) {
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
    public String getId() {
        return FIXED_ID;
    }

    @Override
    public PrimaryMeasureBean getPrimaryMeasure() {
        return primaryMeasureBean;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(primaryMeasureBean, composites);
        return composites;
    }
}
