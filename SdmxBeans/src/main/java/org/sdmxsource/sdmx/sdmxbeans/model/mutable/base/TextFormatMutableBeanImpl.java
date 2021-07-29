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

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TERTIARY_BOOL;
import org.sdmxsource.sdmx.api.constants.TEXT_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.TextFormatBean;
import org.sdmxsource.sdmx.api.model.mutable.base.TextFormatMutableBean;

import java.math.BigDecimal;
import java.math.BigInteger;


/**
 * The type Text format mutable bean.
 */
public class TextFormatMutableBeanImpl extends MutableBeanImpl implements TextFormatMutableBean {
    private static final long serialVersionUID = 1L;

    private TEXT_TYPE textType;
    private TERTIARY_BOOL isSequence = TERTIARY_BOOL.UNSET;
    private TERTIARY_BOOL isMultiLingual = TERTIARY_BOOL.UNSET;
    private BigDecimal minValue;
    private BigDecimal maxValue;
    private BigInteger minLength;
    private BigInteger maxLength;
    private BigDecimal startValue;
    private BigDecimal endValue;
    private BigDecimal interval;
    private String timeInterval;
    private BigInteger decimals;
    private String pattern;

    /**
     * Instantiates a new Text format mutable bean.
     */
    public TextFormatMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.TEXT_FORMAT);
    }

    /**
     * Instantiates a new Text format mutable bean.
     *
     * @param txtBean the txt bean
     */
    public TextFormatMutableBeanImpl(TextFormatBean txtBean) {
        super(txtBean);
        this.textType = txtBean.getTextType();
        this.isSequence = txtBean.getSequence();
        this.maxLength = txtBean.getMaxLength();
        this.minLength = txtBean.getMinLength();
        this.startValue = txtBean.getStartValue();
        this.endValue = txtBean.getEndValue();
        this.interval = txtBean.getInterval();
        this.maxValue = txtBean.getMaxValue();
        this.minValue = txtBean.getMinValue();
        this.isMultiLingual = txtBean.getMultiLingual();
        if (txtBean.getTimeInterval() != null) {
            this.timeInterval = txtBean.getTimeInterval().toString();
        }
        this.decimals = txtBean.getDecimals();
        this.pattern = txtBean.getPattern();
    }

    @Override
    public BigDecimal getMinValue() {
        return minValue;
    }

    @Override
    public void setMinValue(BigDecimal minValue) {
        this.minValue = minValue;
    }

    @Override
    public BigDecimal getMaxValue() {
        return maxValue;
    }

    @Override
    public void setMaxValue(BigDecimal maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    public TERTIARY_BOOL getMultiLingual() {
        return isMultiLingual;
    }

    @Override
    public void setMultiLingual(TERTIARY_BOOL isMultiLingual) {
        this.isMultiLingual = isMultiLingual;
    }

    @Override
    public TEXT_TYPE getTextType() {
        return textType;
    }

    @Override
    public void setTextType(TEXT_TYPE textType) {
        this.textType = textType;
    }

    @Override
    public BigInteger getMinLength() {
        return minLength;
    }

    @Override
    public void setMinLength(BigInteger minLength) {
        this.minLength = minLength;
    }

    @Override
    public BigInteger getMaxLength() {
        return maxLength;
    }

    @Override
    public void setMaxLength(BigInteger maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public BigDecimal getStartValue() {
        return startValue;
    }

    @Override
    public void setStartValue(BigDecimal startValue) {
        this.startValue = startValue;
    }

    @Override
    public BigDecimal getEndValue() {
        return endValue;
    }

    @Override
    public void setEndValue(BigDecimal endValue) {
        this.endValue = endValue;
    }

    @Override
    public BigDecimal getInterval() {
        return interval;
    }

    @Override
    public void setInterval(BigDecimal interval) {
        this.interval = interval;
    }

    @Override
    public String getTimeInterval() {
        return timeInterval;
    }

    @Override
    public void setTimeInterval(String timeInterval) {
        this.timeInterval = timeInterval;
    }

    @Override
    public BigInteger getDecimals() {
        return decimals;
    }

    @Override
    public void setDecimals(BigInteger decimals) {
        this.decimals = decimals;
    }

    @Override
    public String getPattern() {
        return pattern;
    }

    @Override
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public TERTIARY_BOOL getSequence() {
        return isSequence;
    }

    @Override
    public void setSequence(TERTIARY_BOOL isSequence) {
        this.isSequence = isSequence;
    }


}
