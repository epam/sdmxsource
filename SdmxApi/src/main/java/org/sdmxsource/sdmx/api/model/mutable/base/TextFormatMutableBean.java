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
package org.sdmxsource.sdmx.api.model.mutable.base;

import org.sdmxsource.sdmx.api.constants.TERTIARY_BOOL;
import org.sdmxsource.sdmx.api.constants.TEXT_TYPE;

import java.math.BigDecimal;
import java.math.BigInteger;


public interface TextFormatMutableBean extends MutableBean {

    TERTIARY_BOOL getMultiLingual();

    void setMultiLingual(TERTIARY_BOOL isMultiLingual);

    TEXT_TYPE getTextType();

    void setTextType(TEXT_TYPE textType);

    TERTIARY_BOOL getSequence();

    void setSequence(TERTIARY_BOOL isSequence);

    BigDecimal getMaxValue();

    void setMaxValue(BigDecimal maxValue);

    BigDecimal getMinValue();

    void setMinValue(BigDecimal minValue);

    BigInteger getMinLength();

    void setMinLength(BigInteger minLength);

    BigInteger getMaxLength();

    void setMaxLength(BigInteger maxLength);

    BigDecimal getStartValue();

    void setStartValue(BigDecimal startValue);

    BigDecimal getEndValue();

    void setEndValue(BigDecimal endValue);

    BigDecimal getInterval();

    void setInterval(BigDecimal interval);

    String getTimeInterval();

    void setTimeInterval(String timeInterval);

    BigInteger getDecimals();

    void setDecimals(BigInteger decimals);

    String getPattern();

    void setPattern(String pattern);

}
