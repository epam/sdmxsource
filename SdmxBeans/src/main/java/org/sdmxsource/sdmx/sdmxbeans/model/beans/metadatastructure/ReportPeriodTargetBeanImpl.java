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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.metadatastructure;

import org.sdmx.resources.sdmxml.schemas.v21.structure.ReportPeriodTargetType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.RepresentationType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TEXT_TYPE;
import org.sdmxsource.sdmx.api.constants.TIME_FORMAT;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.base.SdmxDate;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataTargetBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.ReportPeriodTargetBean;
import org.sdmxsource.sdmx.api.model.mutable.metadatastructure.ReportPeriodTargetMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.IdentifiableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.SdmxDateImpl;
import org.sdmxsource.sdmx.sdmxbeans.util.TextTypeUtil;
import org.sdmxsource.util.ObjectUtil;

import javax.xml.bind.ValidationException;


/**
 * The type Report period target bean.
 */
public class ReportPeriodTargetBeanImpl extends IdentifiableBeanImpl implements ReportPeriodTargetBean {
    private static final long serialVersionUID = -7145150029575176604L;
    private SdmxDate startTime;
    private SdmxDate endTime;
    private TEXT_TYPE textType = TEXT_TYPE.OBSERVATIONAL_TIME_PERIOD;

    /**
     * Instantiates a new Report period target bean.
     *
     * @param parent the parent
     * @param bean   the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEAN				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ReportPeriodTargetBeanImpl(IdentifiableBean parent, ReportPeriodTargetMutableBean bean) {
        super(bean, parent);
        if (bean.getStartTime() != null) {
            this.startTime = new SdmxDateImpl(bean.getStartTime(), TIME_FORMAT.DATE_TIME);
        }
        if (bean.getEndTime() != null) {
            this.endTime = new SdmxDateImpl(bean.getEndTime(), TIME_FORMAT.DATE_TIME);
        }
        if (bean.getTextType() != null) {
            this.textType = bean.getTextType();
        }
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
    }

    /**
     * Instantiates a new Report period target bean.
     *
     * @param reportPeriodTargetType the report period target type
     * @param parent                 the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected ReportPeriodTargetBeanImpl(ReportPeriodTargetType reportPeriodTargetType, MetadataTargetBean parent) {
        super(reportPeriodTargetType, SDMX_STRUCTURE_TYPE.REPORT_PERIOD_TARGET, parent);
        if (reportPeriodTargetType.getLocalRepresentation() != null) {
            RepresentationType repType = reportPeriodTargetType.getLocalRepresentation();
            if (repType.getTextFormat() != null) {
                if (repType.getTextFormat().getStartTime() != null) {
                    this.startTime = new SdmxDateImpl(repType.getTextFormat().getStartTime().toString());
                }

                if (repType.getTextFormat().getEndTime() != null) {
                    this.endTime = new SdmxDateImpl(repType.getTextFormat().getEndTime().toString());
                }
                if (repType.getTextFormat().getTextType() != null) {
                    this.textType = TextTypeUtil.getTextType(repType.getTextFormat().getTextType());
                }
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
            ReportPeriodTargetBean that = (ReportPeriodTargetBean) bean;
            if (!ObjectUtil.equivalent(startTime, that.getStartTime())) {
                return false;
            }
            if (!ObjectUtil.equivalent(endTime, that.getEndTime())) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() throws ValidationException {
        setId(FIXED_ID);
        if (startTime != null && endTime != null) {
            if (startTime.isLater(endTime)) {
                throw new SdmxSemmanticException("Report Period Target - start time can not be later then end time");
            }
        }
    }

    @Override
    public TEXT_TYPE getTextType() {
        return textType;
    }

    @Override
    public SdmxDate getStartTime() {
        return startTime;
    }

    @Override
    public SdmxDate getEndTime() {
        return endTime;
    }

    @Override
    public String getId() {
        return FIXED_ID;
    }
}
