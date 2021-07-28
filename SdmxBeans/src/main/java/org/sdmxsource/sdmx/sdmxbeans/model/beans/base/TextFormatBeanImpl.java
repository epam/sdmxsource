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

import org.apache.log4j.Logger;
import org.sdmx.resources.sdmxml.schemas.v20.structure.TextFormatType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.TextTypeType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TERTIARY_BOOL;
import org.sdmxsource.sdmx.api.constants.TEXT_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.base.SdmxDate;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.base.TextFormatBean;
import org.sdmxsource.sdmx.api.model.mutable.base.TextFormatMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.util.TextTypeUtil;
import org.sdmxsource.util.ObjectUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;


/**
 * The type Text format bean.
 */
public class TextFormatBeanImpl extends SDMXBeanImpl implements TextFormatBean {
    private static final long serialVersionUID = 1L;
    private static Logger LOG = Logger.getLogger(TextFormatBeanImpl.class);
    private TEXT_TYPE textType;
    private TERTIARY_BOOL isSequence = TERTIARY_BOOL.UNSET;
    private TERTIARY_BOOL isMultiLingual = TERTIARY_BOOL.UNSET;
    private BigInteger minLength;
    private BigInteger maxLength;
    private BigDecimal startValue;
    private BigDecimal endValue;
    private BigDecimal minValue;
    private BigDecimal maxValue;
    private BigDecimal interval;
    private String timeInterval;
    private BigInteger decimals;
    private String pattern;
    private SdmxDate startTime;
    private SdmxDate endTime;

    /**
     * Instantiates a new Text format bean.
     *
     * @param txtBean the txt bean
     * @param parent  the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEAN				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public TextFormatBeanImpl(TextFormatMutableBean txtBean, SDMXBean parent) {
        super(txtBean, parent);
        this.textType = txtBean.getTextType();
        if (txtBean.getSequence() != null) {
            this.isSequence = txtBean.getSequence();
        }
        this.maxLength = txtBean.getMaxLength();
        this.minLength = txtBean.getMinLength();
        this.startValue = txtBean.getStartValue();
        this.endValue = txtBean.getEndValue();
        this.maxValue = txtBean.getMaxValue();
        this.minValue = txtBean.getMinValue();
        this.interval = txtBean.getInterval();
        this.timeInterval = txtBean.getTimeInterval();
        this.decimals = txtBean.getDecimals();
        this.pattern = txtBean.getPattern();
        validate();
    }

    /**
     * Instantiates a new Text format bean.
     *
     * @param txtBean the txt bean
     * @param parent  the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public TextFormatBeanImpl(org.sdmx.resources.sdmxml.schemas.v21.structure.TextFormatType txtBean, SDMXBean parent) {
        super(SDMX_STRUCTURE_TYPE.TEXT_FORMAT, parent);
        if (txtBean.getTextType() != null) {
            this.textType = TextTypeUtil.getTextType(txtBean.getTextType());
        }
        if (txtBean.isSetIsMultiLingual()) {
            this.isMultiLingual = TERTIARY_BOOL.parseBoolean(txtBean.getIsMultiLingual());
        }
        if (txtBean.isSetIsSequence()) {
            this.isSequence = TERTIARY_BOOL.parseBoolean(txtBean.getIsSequence());
        }
        if (txtBean.isSetMaxLength()) {
            this.maxLength = txtBean.getMaxLength();
        }
        if (txtBean.isSetMinLength()) {
            this.minLength = txtBean.getMinLength();
        }
        if (txtBean.isSetStartValue()) {
            this.startValue = txtBean.getStartValue();
        }
        if (txtBean.isSetEndValue()) {
            this.endValue = txtBean.getEndValue();
        }
        if (txtBean.isSetMaxValue()) {
            this.maxValue = txtBean.getMaxValue();
        }
        if (txtBean.isSetMinLength()) {
            this.minValue = txtBean.getMinValue();
        }
        if (txtBean.isSetInterval()) {
            this.interval = txtBean.getInterval();
        }
        if (txtBean.getTimeInterval() != null) {
            this.timeInterval = txtBean.getTimeInterval().toString();
        }
        if (txtBean.isSetDecimals()) {
            this.decimals = txtBean.getDecimals();
        }
        if (txtBean.isSetPattern()) {
            this.pattern = txtBean.getPattern();
        }
        if (txtBean.getEndTime() != null) {
            endTime = new SdmxDateImpl(txtBean.getEndTime().toString());
        }
        if (txtBean.getStartTime() != null) {
            startTime = new SdmxDateImpl(txtBean.getStartTime().toString());
        }
        validate();
    }

    /**
     * Instantiates a new Text format bean.
     *
     * @param txtBean the txt bean
     * @param parent  the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public TextFormatBeanImpl(TextFormatType txtBean, SDMXBean parent) {
        super(SDMX_STRUCTURE_TYPE.TEXT_FORMAT, parent);
        if (txtBean.getTextType() != null) {
            TextTypeType.Enum textType = txtBean.getTextType();
            switch (textType.intValue()) {
                case TextTypeType.INT_BIG_INTEGER:
                    this.textType = TEXT_TYPE.BIG_INTEGER;
                    break;
                case TextTypeType.INT_BOOLEAN:
                    this.textType = TEXT_TYPE.BOOLEAN;
                    break;
                case TextTypeType.INT_COUNT:
                    this.textType = TEXT_TYPE.COUNT;
                    break;
                case TextTypeType.INT_DATE:
                    this.textType = TEXT_TYPE.DATE;
                    break;
                case TextTypeType.INT_DATE_TIME:
                    this.textType = TEXT_TYPE.DATE_TIME;
                    break;
                case TextTypeType.INT_DAY:
                    this.textType = TEXT_TYPE.DAY;
                    break;
                case TextTypeType.INT_DECIMAL:
                    this.textType = TEXT_TYPE.DECIMAL;
                    break;
                case TextTypeType.INT_DOUBLE:
                    this.textType = TEXT_TYPE.DOUBLE;
                    break;
                case TextTypeType.INT_DURATION:
                    this.textType = TEXT_TYPE.DURATION;
                    break;
                case TextTypeType.INT_EXCLUSIVE_VALUE_RANGE:
                    this.textType = TEXT_TYPE.EXCLUSIVE_VALUE_RANGE;
                    break;
                case TextTypeType.INT_FLOAT:
                    this.textType = TEXT_TYPE.FLOAT;
                    break;
                case TextTypeType.INT_INCLUSIVE_VALUE_RANGE:
                    this.textType = TEXT_TYPE.INCLUSIVE_VALUE_RANGE;
                    break;
                case TextTypeType.INT_INCREMENTAL:
                    this.textType = TEXT_TYPE.INCREMENTAL;
                    break;
                case TextTypeType.INT_INTEGER:
                    this.textType = TEXT_TYPE.INTEGER;
                    break;
                case TextTypeType.INT_LONG:
                    this.textType = TEXT_TYPE.LONG;
                    break;
                case TextTypeType.INT_MONTH:
                    this.textType = TEXT_TYPE.MONTH;
                    break;
                case TextTypeType.INT_MONTH_DAY:
                    this.textType = TEXT_TYPE.MONTH_DAY;
                    break;
                case TextTypeType.INT_OBSERVATIONAL_TIME_PERIOD:
                    this.textType = TEXT_TYPE.OBSERVATIONAL_TIME_PERIOD;
                    break;
                case TextTypeType.INT_SHORT:
                    this.textType = TEXT_TYPE.SHORT;
                    break;
                case TextTypeType.INT_STRING:
                    this.textType = TEXT_TYPE.STRING;
                    break;
                case TextTypeType.INT_TIME:
                    this.textType = TEXT_TYPE.TIME;
                    break;
                case TextTypeType.INT_TIMESPAN:
                    this.textType = TEXT_TYPE.TIMESPAN;
                    break;
                case TextTypeType.INT_URI:
                    this.textType = TEXT_TYPE.URI;
                    break;
                case TextTypeType.INT_YEAR:
                    this.textType = TEXT_TYPE.YEAR;
                    break;
                case TextTypeType.INT_YEAR_MONTH:
                    this.textType = TEXT_TYPE.YEAR_MONTH;
                    break;
            }
        }
        if (txtBean.isSetIsSequence()) {
            this.isSequence = TERTIARY_BOOL.parseBoolean(txtBean.getIsSequence());
        }
        if (txtBean.isSetMaxLength()) {
            this.maxLength = txtBean.getMaxLength();
        }
        if (txtBean.isSetMinLength()) {
            this.minLength = txtBean.getMinLength();
        }
        if (txtBean.isSetStartValue()) {
            this.startValue = BigDecimal.valueOf(txtBean.getStartValue());
        }
        if (txtBean.isSetEndValue()) {
            this.endValue = BigDecimal.valueOf(txtBean.getEndValue());
        }
        if (txtBean.isSetInterval()) {
            this.interval = BigDecimal.valueOf(txtBean.getInterval());
        }
        if (txtBean.getTimeInterval() != null) {
            this.timeInterval = txtBean.getTimeInterval().toString();
        }
        if (txtBean.isSetDecimals()) {
            this.decimals = txtBean.getDecimals();
        }
        if (txtBean.isSetPattern()) {
            this.pattern = txtBean.getPattern();
        }
        validate();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() throws SdmxSemmanticException {
        if (minLength != null) {
            if (minLength.intValue() == 0) {
                LOG.warn("Text format of 0 converted to 1");
                minLength = BigInteger.ONE;
            } else if (minLength.intValue() < 0) {
                throw new SdmxSemmanticException("Invalid Text Format, min length must be a positive integer - got " + minLength.intValue());
            }
        }
        if (maxLength != null) {
            if (maxLength.intValue() <= 0) {
                throw new SdmxSemmanticException("Invalid Text Format, max length must be a positive integer - got " + maxLength.intValue());
            }
        }
        if (minLength != null && maxLength != null) {
            if (minLength.compareTo(maxLength) > 0) {
                throw new SdmxSemmanticException("Invalid Text Format, min length can not be greater then max length");
            }
        }
        if (minValue != null && maxValue != null) {
            if (minValue.compareTo(maxValue) > 0) {
                throw new SdmxSemmanticException("Invalid Text Format, min value can not be greater then max value");
            }
        }
        if (decimals != null) {
            if (decimals.intValue() <= 0) {
                throw new SdmxSemmanticException("Invalid Text Format, decimals must be a positive integer - got " + decimals.intValue());
            }
        }
        if (startTime != null && endTime != null) {
            if (startTime.isLater(endTime)) {
                throw new SdmxSemmanticException("Invalid Text Format, start time can not be after end time");
            }
        }
        if (isSequence.isTrue()) {
            if (timeInterval == null && interval == null) {
                throw new SdmxSemmanticException("Invalid Text Format, time interval or interval must be set if isSequence is set to true");
            }
            if (ObjectUtil.validString(timeInterval) && startTime == null) {
                throw new SdmxSemmanticException("Invalid Text Format, start time must be set if time interval is set");
            }
            if (interval != null && startValue == null) {
                throw new SdmxSemmanticException("Invalid Text Format, start value must be set if interval is set");
            }
        } else {
            if (ObjectUtil.validString(timeInterval)) {
                throw new SdmxSemmanticException("Invalid Text Format, time interval can only be set if isSequence is set to true");
            }
            if (startTime != null) {
                throw new SdmxSemmanticException("Invalid Text Format, start time can only be set if isSequence is set to true");
            }
            if (interval != null) {
                throw new SdmxSemmanticException("Invalid Text Format, interval can only be set if isSequence is set to true");
            }
            if (startValue != null) {
                throw new SdmxSemmanticException("Invalid Text Format, start value can only be set if isSequence is set to true");
            }
        }
        if (ObjectUtil.validString(timeInterval)) {
            //Validate that the time interval matches the allowed xs:duration format PnYnMnDTnHnMnS - Use the RegEx, and make sure the string
            //is greater then length 1, as the regex does not ensure that there is any content after the P
            //The Regex ensures if there is anything after the P, it is of the valid type, example P5Y and P91DT12M are both valid formats
            Pattern timeIntervalPattern = Pattern.compile("P(([0-9]+Y)?([0-9]+M)?([0-9]+D)?)(T([0-9]+H)?([0-9]+M)?([0-9]+S)?)?");
            if (timeInterval.length() == 1 || !timeIntervalPattern.matcher(timeInterval).matches()) {
                throw new SdmxSemmanticException("Invalid time interval, pattern must be PnYnMnDTnHnMnS, where n=positive integer, and each section is optional after each n (example P5Y)");
            }
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
            TextFormatBean that = (TextFormatBean) bean;
            if (!ObjectUtil.equivalent(textType, that.getTextType())) {
                return false;
            }
            if (!ObjectUtil.equivalent(isSequence, that.getSequence())) {
                return false;
            }
            if (!ObjectUtil.equivalent(isMultiLingual, that.getMultiLingual())) {
                return false;
            }
            if (!ObjectUtil.equivalent(minLength, that.getMinLength())) {
                return false;
            }
            if (!ObjectUtil.equivalent(maxLength, that.getMaxLength())) {
                return false;
            }
            if (!ObjectUtil.equivalent(minValue, that.getMinValue())) {
                return false;
            }
            if (!ObjectUtil.equivalent(maxValue, that.getMaxValue())) {
                return false;
            }
            if (!ObjectUtil.equivalent(startValue, that.getStartValue())) {
                return false;
            }
            if (!ObjectUtil.equivalent(endValue, that.getEndValue())) {
                return false;
            }
            if (!ObjectUtil.equivalent(interval, that.getInterval())) {
                return false;
            }
            if (!ObjectUtil.equivalent(timeInterval, that.getTimeInterval())) {
                return false;
            }
            if (!ObjectUtil.equivalent(decimals, that.getDecimals())) {
                return false;
            }
            if (!ObjectUtil.equivalent(pattern, that.getPattern())) {
                return false;
            }
            if (!ObjectUtil.equivalent(startTime, that.getStartTime())) {
                return false;
            }
            if (!ObjectUtil.equivalent(endTime, that.getEndTime())) {
                return false;
            }
        }
        return true;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
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
    public TERTIARY_BOOL getSequence() {
        return isSequence;
    }

    @Override
    public BigInteger getMinLength() {
        return minLength;
    }

    @Override
    public BigInteger getMaxLength() {
        return maxLength;
    }

    @Override
    public BigDecimal getMinValue() {
        return minValue;
    }

    @Override
    public BigDecimal getMaxValue() {
        return maxValue;
    }

    @Override
    public BigDecimal getStartValue() {
        return startValue;
    }

    @Override
    public BigDecimal getEndValue() {
        return endValue;
    }

    @Override
    public BigDecimal getInterval() {
        return interval;
    }

    @Override
    public String getTimeInterval() {
        return timeInterval;
    }

    @Override
    public BigInteger getDecimals() {
        return decimals;
    }

    @Override
    public String getPattern() {
        return pattern;
    }

    @Override
    public TERTIARY_BOOL getMultiLingual() {
        return isMultiLingual;
    }

    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        return new HashSet<SDMXBean>();
    }

    public boolean hasRestrictions() {
        return (getDecimals() != null ||
                getEndTime() != null ||
                getEndValue() != null ||
                getInterval() != null ||
                getMaxLength() != null ||
                getMaxValue() != null ||
                getMinLength() != null ||
                getMaxValue() != null ||
                getPattern() != null ||
                getStartTime() != null ||
                getStartValue() != null ||
                getTimeInterval() != null);
    }
}
