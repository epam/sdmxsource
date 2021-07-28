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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.registry;

import org.sdmx.resources.sdmxml.schemas.v21.common.ReferencePeriodType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TIME_FORMAT;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.base.SdmxDate;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ContentConstraintBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ReferencePeriodBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.ReferencePeriodMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.SdmxDateImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.SdmxStructureBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.registry.ReferencePeriodMutableBeanImpl;
import org.sdmxsource.util.ObjectUtil;


/**
 * The type Reference period bean.
 */
public class ReferencePeriodBeanImpl extends SdmxStructureBeanImpl implements ReferencePeriodBean {
    private static final long serialVersionUID = -2749222689002910521L;
    private SdmxDate startTime;
    private SdmxDate endTime;

    /**
     * Instantiates a new Reference period bean.
     *
     * @param mutable the mutable
     * @param parent  the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEAN				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ReferencePeriodBeanImpl(ReferencePeriodMutableBean mutable, ContentConstraintBean parent) {
        super(SDMX_STRUCTURE_TYPE.REFERENCE_PERIOD, parent);
        // These items are mandatory and thus should exist
        if (mutable.getStartTime() != null) {
            this.startTime = new SdmxDateImpl(mutable.getStartTime(), TIME_FORMAT.DATE_TIME);
        }
        if (mutable.getEndTime() != null) {
            this.endTime = new SdmxDateImpl(mutable.getEndTime(), TIME_FORMAT.DATE_TIME);
        }
        if (startTime == null) {
            throw new SdmxSemmanticException("ReferencePeriodBeanImpl - start time can not be null");
        }
        if (endTime == null) {
            throw new SdmxSemmanticException("ReferencePeriodBeanImpl - end time can not be null");
        }
    }

    /**
     * Instantiates a new Reference period bean.
     *
     * @param refPeriodType the ref period type
     * @param parent        the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ReferencePeriodBeanImpl(ReferencePeriodType refPeriodType, ContentConstraintBean parent) {
        super(SDMX_STRUCTURE_TYPE.REFERENCE_PERIOD, parent);
        this.startTime = new SdmxDateImpl(refPeriodType.getStartTime().getTime(), TIME_FORMAT.DATE_TIME);
        this.endTime = new SdmxDateImpl(refPeriodType.getEndTime().getTime(), TIME_FORMAT.DATE_TIME);
        if (startTime == null) {
            throw new SdmxSemmanticException("ReferencePeriodBeanImpl - start time can not be null");
        }
        if (endTime == null) {
            throw new SdmxSemmanticException("ReferencePeriodBeanImpl - start time can not be null");
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
            ReferencePeriodBean that = (ReferencePeriodBean) bean;
            if (!ObjectUtil.equivalent(startTime, that.getStartTime())) {
                return false;
            }
            if (!ObjectUtil.equivalent(endTime, that.getEndTime())) {
                return false;
            }
            return true;
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public SdmxDate getStartTime() {
        return startTime;
    }

    @Override
    public SdmxDate getEndTime() {
        return endTime;
    }

    @Override
    public ReferencePeriodMutableBean createMutableBean() {
        return new ReferencePeriodMutableBeanImpl(this);
    }
}
