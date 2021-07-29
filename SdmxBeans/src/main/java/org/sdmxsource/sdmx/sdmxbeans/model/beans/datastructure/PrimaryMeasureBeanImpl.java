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
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.MeasureListBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.PrimaryMeasureBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.PrimaryMeasureMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.ComponentBeanImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Primary measure bean.
 */
public class PrimaryMeasureBeanImpl extends ComponentBeanImpl implements PrimaryMeasureBean {
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Primary measure bean.
     *
     * @param bean   the bean
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS 			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public PrimaryMeasureBeanImpl(PrimaryMeasureMutableBean bean, MeasureListBean parent) {
        super(bean, parent);
        validate();
    }

    /**
     * Instantiates a new Primary measure bean.
     *
     * @param primaryMeasure the primary measure
     * @param parent         the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public PrimaryMeasureBeanImpl(org.sdmx.resources.sdmxml.schemas.v21.structure.PrimaryMeasureType primaryMeasure, MeasureListBean parent) {
        super(primaryMeasure, SDMX_STRUCTURE_TYPE.PRIMARY_MEASURE, parent);
        validate();
    }

    /**
     * Instantiates a new Primary measure bean.
     *
     * @param primaryMeasure the primary measure
     * @param parent         the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public PrimaryMeasureBeanImpl(PrimaryMeasureType primaryMeasure, MeasureListBean parent) {
        super(primaryMeasure, SDMX_STRUCTURE_TYPE.PRIMARY_MEASURE, primaryMeasure.getAnnotations(), primaryMeasure.getTextFormat(), primaryMeasure.getCodelistAgency(), primaryMeasure.getCodelist(), primaryMeasure.getCodelistVersion(),
                primaryMeasure.getConceptSchemeAgency(), primaryMeasure.getConceptSchemeRef(), primaryMeasure.getConceptVersion(),
                primaryMeasure.getConceptAgency(), primaryMeasure.getConceptRef(), parent);
        validate();
    }

    /**
     * Instantiates a new Primary measure bean.
     *
     * @param primaryMeasure the primary measure
     * @param parent         the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public PrimaryMeasureBeanImpl(org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.PrimaryMeasureType primaryMeasure, MeasureListBean parent) {
        super(primaryMeasure, SDMX_STRUCTURE_TYPE.PRIMARY_MEASURE, primaryMeasure.getAnnotations(), null, primaryMeasure.getConcept(), parent);
        validate();
    }

    @Override
    protected List<String> getParentIds(boolean includeDifferentTypes) {
        List<String> returnList = new ArrayList<String>();
        returnList.add(this.getId());
        return returnList;
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
            return super.deepEqualsInternal((PrimaryMeasureBean) bean, includeFinalProperties);
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

    private void validate() {
        setId(FIXED_ID);
    }
}
