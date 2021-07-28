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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.base;

import org.sdmx.resources.sdmxml.schemas.v21.common.TimeRangeValueType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.SdmxStructureBean;
import org.sdmxsource.sdmx.api.model.beans.base.TimeRangeBean;
import org.sdmxsource.sdmx.api.model.mutable.base.TimeRangeMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.TimeRangeBeanImpl;
import org.sdmxsource.sdmx.util.date.DateUtil;

import java.util.Date;


/**
 * The type Time range mutable bean.
 */
public class TimeRangeMutableBeanImpl extends MutableBeanImpl implements TimeRangeMutableBean {

    private static final long serialVersionUID = -1105081442071478375L;
    private Date startDate;
    private Date endDate;
    private boolean isRange;
    private boolean isStartInclusive;
    private boolean isEndInclusive;


    /**
     * Instantiates a new Time range mutable bean.
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////DEFAULT CONSTRUCTOR						 //////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public TimeRangeMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.TIME_RANGE);
    }

    /**
     * Instantiates a new Time range mutable bean.
     *
     * @param immutable the immutable
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM IMMUTABLE BEAN				 //////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public TimeRangeMutableBeanImpl(TimeRangeBean immutable) {
        super(SDMX_STRUCTURE_TYPE.TIME_RANGE);
        if (immutable.getStartDate() != null) {
            this.startDate = immutable.getStartDate().getDate();
        }
        if (immutable.getEndDate() != null) {
            this.endDate = immutable.getEndDate().getDate();
        }
        this.isRange = immutable.isRange();
        this.isStartInclusive = immutable.isStartInclusive();
        this.isEndInclusive = immutable.isEndInclusive();
    }

    /**
     * Instantiates a new Time range mutable bean.
     *
     * @param type the type
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public TimeRangeMutableBeanImpl(TimeRangeValueType type) {
        super(SDMX_STRUCTURE_TYPE.TIME_RANGE);
        if (type.getAfterPeriod() != null) {
            this.isRange = false;
            //FUNC 2.1 ObservationalTimePeriodType - does this work?
            this.endDate = DateUtil.formatDate(type.getAfterPeriod().getObjectValue(), true);
            this.isEndInclusive = type.getAfterPeriod().getIsInclusive();
        }
        if (type.getBeforePeriod() != null) {
            this.isRange = false;
            this.startDate = DateUtil.formatDate(type.getBeforePeriod().getObjectValue(), true);
            this.isStartInclusive = type.getBeforePeriod().getIsInclusive();
        }
        if (type.getStartPeriod() != null) {
            this.isRange = true;
            this.startDate = DateUtil.formatDate(type.getStartPeriod().getObjectValue(), true);
            this.isStartInclusive = type.getStartPeriod().getIsInclusive();
        }
        if (type.getEndPeriod() != null) {
            this.isRange = true;
            this.startDate = DateUtil.formatDate(type.getEndPeriod().getObjectValue(), true);
            this.isEndInclusive = type.getEndPeriod().getIsInclusive();
        }
        validate();
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
            if (startDate.getTime() > endDate.getTime()) {
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
    public void setIsRange(boolean isRange) {
        this.isRange = isRange;
    }

    @Override
    public Date getStartDate() {
        if (startDate != null) {
            return new Date(startDate.getTime());
        }
        return null;
    }

    @Override
    public void setStartDate(Date start) {
        this.startDate = start;
    }

    @Override
    public Date getEndDate() {
        if (endDate != null) {
            return new Date(endDate.getTime());
        }
        return null;
    }

    @Override
    public void setEndDate(Date end) {
        this.endDate = end;
    }

    @Override
    public boolean isStartInclusive() {
        return isStartInclusive;
    }

    @Override
    public boolean isEndInclusive() {
        return isEndInclusive;
    }

    @Override
    public void setIsStartInclusive(boolean includeStart) {
        this.isStartInclusive = includeStart;
    }

    @Override
    public void setIsEndInclusive(boolean includeEnd) {
        this.isEndInclusive = includeEnd;
    }

    @Override
    public TimeRangeBean createImmutableInstance(SdmxStructureBean parent) {
        return new TimeRangeBeanImpl(this, parent);
    }
}
