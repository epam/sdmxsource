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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.base;

import org.sdmx.resources.sdmxml.schemas.v21.common.TimeRangeValueType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TIME_FORMAT;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.base.SdmxDate;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.base.SdmxStructureBean;
import org.sdmxsource.sdmx.api.model.beans.base.TimeRangeBean;
import org.sdmxsource.sdmx.api.model.mutable.base.TimeRangeMutableBean;
import org.sdmxsource.util.ObjectUtil;


/**
 * The type Time range bean.
 */
public class TimeRangeBeanImpl extends SdmxStructureBeanImpl implements TimeRangeBean {
    private static final long serialVersionUID = -1105081442071478375L;
    private SdmxDate startDate;
    private SdmxDate endDate;
    private boolean isRange;
    private boolean isStartInclusive;
    private boolean isEndInclusive;

    /**
     * Instantiates a new Time range bean.
     *
     * @param mutable the mutable
     * @param parent  the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEAN				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public TimeRangeBeanImpl(TimeRangeMutableBean mutable, SdmxStructureBean parent) {
        super(SDMX_STRUCTURE_TYPE.TIME_RANGE, parent);
        if (mutable.getStartDate() != null) {
            this.startDate = new SdmxDateImpl(mutable.getStartDate(), TIME_FORMAT.DATE_TIME);
        }
        if (mutable.getEndDate() != null) {
            this.endDate = new SdmxDateImpl(mutable.getEndDate(), TIME_FORMAT.DATE_TIME);
        }
        this.isRange = mutable.isRange();
        this.isStartInclusive = mutable.isStartInclusive();
        this.isEndInclusive = mutable.isEndInclusive();
    }

    /**
     * Instantiates a new Time range bean.
     *
     * @param type   the type
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public TimeRangeBeanImpl(TimeRangeValueType type, SdmxStructureBean parent) {
        super(SDMX_STRUCTURE_TYPE.TIME_RANGE, parent);
        if (type.getAfterPeriod() != null) {
            this.isRange = false;
            //FUNC 2.1 ObservationalTimePeriodType - does this work?
            this.endDate = new SdmxDateImpl(type.getAfterPeriod().getObjectValue().toString());
            this.isEndInclusive = type.getAfterPeriod().getIsInclusive();
        }
        if (type.getBeforePeriod() != null) {
            this.isRange = false;
            this.startDate = new SdmxDateImpl(type.getBeforePeriod().getObjectValue().toString());
            this.isStartInclusive = type.getBeforePeriod().getIsInclusive();
        }
        if (type.getStartPeriod() != null) {
            this.isRange = true;
            this.startDate = new SdmxDateImpl(type.getStartPeriod().getObjectValue().toString());
            this.isStartInclusive = type.getStartPeriod().getIsInclusive();
        }
        if (type.getEndPeriod() != null) {
            this.isRange = true;
            this.endDate = new SdmxDateImpl(type.getEndPeriod().getObjectValue().toString());
            this.isEndInclusive = type.getEndPeriod().getIsInclusive();
        }
        validate();
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
            TimeRangeBean that = (TimeRangeBean) bean;
            if (!ObjectUtil.equivalent(startDate, that.getStartDate())) {
                return false;
            }
            if (!ObjectUtil.equivalent(endDate, that.getEndDate())) {
                return false;
            }
            if (isRange != that.isRange()) {
                return false;
            }
            if (isStartInclusive != that.isStartInclusive()) {
                return false;
            }
            if (isEndInclusive != that.isEndInclusive()) {
                return false;
            }
            return true;
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATE				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() throws SdmxSemmanticException {
        if (startDate == null && endDate == null) {
            throw new SdmxSemmanticException("Time period must define at least one date");
        }
        if (isRange) {
            if (startDate == null || endDate == null) {
                throw new SdmxSemmanticException("Time period with a range requires both a start and end period");
            }
            if (startDate.isLater(endDate)) {
                throw new SdmxSemmanticException("Time range can not specify start period after end period");
            }
        } else {
            if (startDate != null && endDate != null) {
                throw new SdmxSemmanticException("Time period can not define both a before period and after period");
            }
        }
    }

    @Override
    public boolean isRange() {
        return isRange;
    }

    @Override
    public SdmxDate getStartDate() {
        return startDate;
    }

    @Override
    public SdmxDate getEndDate() {
        return endDate;
    }

    @Override
    public boolean isStartInclusive() {
        return isStartInclusive;
    }

    @Override
    public boolean isEndInclusive() {
        return isEndInclusive;
    }
}
