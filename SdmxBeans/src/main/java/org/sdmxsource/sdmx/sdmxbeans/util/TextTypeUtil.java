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
package org.sdmxsource.sdmx.sdmxbeans.util;

import org.sdmx.resources.sdmxml.schemas.v20.common.TextType;
import org.sdmx.resources.sdmxml.schemas.v21.common.DataType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.TEXT_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.TextTypeWrapperImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Text type util.
 */
public class TextTypeUtil {

    /**
     * Gets text type.
     *
     * @param txtBean the txt bean
     * @return the text type
     */
    public static TEXT_TYPE getTextType(DataType.Enum txtBean) {
        if (txtBean == null) {
            return null;
        }
        switch (txtBean.intValue()) {
            case DataType.INT_ALPHA:
                return TEXT_TYPE.ALPHA;
            case DataType.INT_ALPHA_NUMERIC:
                return TEXT_TYPE.ALPHA_NUMERIC;
            case DataType.INT_BASIC_TIME_PERIOD:
                return TEXT_TYPE.BASIC_TIME_PERIOD;
            case DataType.INT_BIG_INTEGER:
                return TEXT_TYPE.BIG_INTEGER;
            case DataType.INT_BOOLEAN:
                return TEXT_TYPE.BOOLEAN;
            case DataType.INT_COUNT:
                return TEXT_TYPE.COUNT;
            case DataType.INT_DATA_SET_REFERENCE:
                return TEXT_TYPE.DATA_SET_REFERENCE;
            case DataType.INT_DATE_TIME:
                return TEXT_TYPE.DATE_TIME;
            case DataType.INT_DAY:
                return TEXT_TYPE.DAY;
            case DataType.INT_DECIMAL:
                return TEXT_TYPE.DECIMAL;
            case DataType.INT_DOUBLE:
                return TEXT_TYPE.DOUBLE;
            case DataType.INT_DURATION:
                return TEXT_TYPE.DURATION;
            case DataType.INT_EXCLUSIVE_VALUE_RANGE:
                return TEXT_TYPE.EXCLUSIVE_VALUE_RANGE;
            case DataType.INT_FLOAT:
                return TEXT_TYPE.FLOAT;
            case DataType.INT_GREGORIAN_DAY:
                return TEXT_TYPE.GREGORIAN_DAY;
            case DataType.INT_GREGORIAN_TIME_PERIOD:
                return TEXT_TYPE.GREGORIAN_TIME_PERIOD;
            case DataType.INT_GREGORIAN_YEAR:
                return TEXT_TYPE.GREGORIAN_YEAR;
            case DataType.INT_GREGORIAN_YEAR_MONTH:
                return TEXT_TYPE.GREGORIAN_YEAR_MONTH;
            case DataType.INT_IDENTIFIABLE_REFERENCE:
                return TEXT_TYPE.IDENTIFIABLE_REFERENCE;
            case DataType.INT_INCLUSIVE_VALUE_RANGE:
                return TEXT_TYPE.INCLUSIVE_VALUE_RANGE;
            case DataType.INT_INCREMENTAL:
                return TEXT_TYPE.INCREMENTAL;
            case DataType.INT_INTEGER:
                return TEXT_TYPE.INTEGER;
            case DataType.INT_KEY_VALUES:
                return TEXT_TYPE.KEY_VALUES;
            case DataType.INT_LONG:
                return TEXT_TYPE.LONG;
            case DataType.INT_MONTH:
                return TEXT_TYPE.MONTH;
            case DataType.INT_MONTH_DAY:
                return TEXT_TYPE.MONTH_DAY;
            case DataType.INT_NUMERIC:
                return TEXT_TYPE.NUMERIC;
            case DataType.INT_OBSERVATIONAL_TIME_PERIOD:
                return TEXT_TYPE.OBSERVATIONAL_TIME_PERIOD;
            case DataType.INT_REPORTING_DAY:
                return TEXT_TYPE.REPORTING_DAY;
            case DataType.INT_REPORTING_MONTH:
                return TEXT_TYPE.REPORTING_MONTH;
            case DataType.INT_REPORTING_QUARTER:
                return TEXT_TYPE.REPORTING_QUARTER;
            case DataType.INT_REPORTING_SEMESTER:
                return TEXT_TYPE.REPORTING_SEMESTER;
            case DataType.INT_REPORTING_TIME_PERIOD:
                return TEXT_TYPE.REPORTING_TIME_PERIOD;
            case DataType.INT_REPORTING_TRIMESTER:
                return TEXT_TYPE.REPORTING_TRIMESTER;
            case DataType.INT_REPORTING_WEEK:
                return TEXT_TYPE.REPORTING_WEEK;
            case DataType.INT_REPORTING_YEAR:
                return TEXT_TYPE.REPORTING_YEAR;
            case DataType.INT_SHORT:
                return TEXT_TYPE.SHORT;
            case DataType.INT_STANDARD_TIME_PERIOD:
                return TEXT_TYPE.STANDARD_TIME_PERIOD;
            case DataType.INT_STRING:
                return TEXT_TYPE.STRING;
            case DataType.INT_TIME:
                return TEXT_TYPE.TIME;
            case DataType.INT_TIME_RANGE:
                return TEXT_TYPE.TIMES_RANGE;
            case DataType.INT_URI:
                return TEXT_TYPE.URI;
            case DataType.INT_XHTML:
                return TEXT_TYPE.XHTML;

            default:
                throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, txtBean);
        }

    }

    /**
     * Wrap text type v 1 list.
     *
     * @param textTypeList the text type list
     * @param parent       the parent
     * @return the list
     */
    public static List<TextTypeWrapper> wrapTextTypeV1(List<org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.common.TextType> textTypeList, SDMXBean parent) {
        List<TextTypeWrapper> returnList = new ArrayList<TextTypeWrapper>();

        if (textTypeList != null) {
            for (org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.common.TextType currentTextType : textTypeList) {
                if (ObjectUtil.validString(currentTextType.getStringValue())) {
                    returnList.add(new TextTypeWrapperImpl(currentTextType, parent));
                }
            }
        }

        return returnList;
    }

    /**
     * Wrap text type v 21 list.
     *
     * @param textTypeList the text type list
     * @param parent       the parent
     * @return the list
     */
    public static List<TextTypeWrapper> wrapTextTypeV21(List<org.sdmx.resources.sdmxml.schemas.v21.common.TextType> textTypeList, SDMXBean parent) {
        List<TextTypeWrapper> returnList = new ArrayList<TextTypeWrapper>();

        if (textTypeList != null) {
            for (org.sdmx.resources.sdmxml.schemas.v21.common.TextType currentTextType : textTypeList) {
                if (ObjectUtil.validString(currentTextType.getStringValue())) {
                    returnList.add(new TextTypeWrapperImpl(currentTextType, parent));
                }
            }
        }

        return returnList;
    }

    /**
     * Wrap text type v 2 list.
     *
     * @param textTypeList the text type list
     * @param parent       the parent
     * @return the list
     */
    public static List<TextTypeWrapper> wrapTextTypeV2(List<TextType> textTypeList, SDMXBean parent) {
        List<TextTypeWrapper> returnList = new ArrayList<TextTypeWrapper>();

        if (textTypeList != null) {
            for (TextType currentTextType : textTypeList) {
                if (ObjectUtil.validString(currentTextType.getStringValue())) {
                    returnList.add(new TextTypeWrapperImpl(currentTextType, parent));
                }
            }
        }

        return returnList;
    }

    /**
     * Unwrap text type text type [ ].
     *
     * @param text the text
     * @return the text type [ ]
     */
    public static TextType[] unwrapTextType(List<TextTypeWrapper> text) {
        TextType[] textType = new TextType[text.size()];
        for (int i = 0; i < text.size(); i++) {
            TextTypeWrapper currentWrapper = text.get(i);
            TextType type = TextType.Factory.newInstance();
            type.setLang(currentWrapper.getLocale());
            type.setStringValue(currentWrapper.getValue());
            textType[i] = type;
        }
        return textType;
    }

    /**
     * Gets default locale.
     *
     * @param textTypes the text types
     * @return the default locale
     */
    public static TextTypeWrapper getDefaultLocale(List<TextTypeWrapper> textTypes) {
        //FUNC This only returns the first locale
        if (ObjectUtil.validCollection(textTypes)) {
            for (TextTypeWrapper tt : textTypes) {
                return tt;
            }
        }
        return null;
    }

}
