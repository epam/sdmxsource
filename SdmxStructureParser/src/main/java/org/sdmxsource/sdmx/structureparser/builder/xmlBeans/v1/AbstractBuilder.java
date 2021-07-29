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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v1;

import org.apache.log4j.Logger;
import org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.common.AnnotationType;
import org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.common.AnnotationsType;
import org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.common.TextType;
import org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.TextFormatType;
import org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.TextTypeType;
import org.sdmxsource.sdmx.api.model.beans.base.AnnotableBean;
import org.sdmxsource.sdmx.api.model.beans.base.AnnotationBean;
import org.sdmxsource.sdmx.api.model.beans.base.TextFormatBean;
import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.util.ObjectUtil;
import org.sdmxsource.util.log.LoggingUtil;

import java.util.Collection;
import java.util.List;


/**
 * Builds Version 1.o SDMX from SdmxBean
 */
public class AbstractBuilder {
    /**
     * The Log.
     */
    static Logger log;

    /**
     * Valid string boolean.
     *
     * @param string the string
     * @return the boolean
     */
    boolean validString(String string) {
        return ObjectUtil.validString(string);
    }

    /**
     * Valid collection boolean.
     *
     * @param collection the collection
     * @return the boolean
     */
    @SuppressWarnings("rawtypes")
    boolean validCollection(Collection collection) {
        return ObjectUtil.validCollection(collection);
    }

    /**
     * Populate text format type.
     *
     * @param textFormatType the text format type
     * @param textFormat     the text format
     */
    void populateTextFormatType(TextFormatType textFormatType, TextFormatBean textFormat) {
        if (textFormat.getMaxLength() != null) {
            textFormatType.setLength(textFormat.getMaxLength());
        }
        if (textFormat.getDecimals() != null) {
            textFormatType.setDecimals(textFormat.getDecimals());
        }
        if (textFormat.getTextType() != null) {
            switch (textFormat.getTextType()) {
                case BIG_INTEGER:
                    textFormatType.setTextType(TextTypeType.NUM);
                    break;
                case BOOLEAN:
                    LoggingUtil.warn(log, "can not map text type BOOLEAN to SDMX version 1.0 schema instance, property ignored ");
                    break;
                case COUNT:
                    LoggingUtil.warn(log, "can not map text type COUNT to SDMX version 1.0 schema instance, property ignored ");
                    break;
                case DATE:
                    LoggingUtil.warn(log, "can not map text type DATE to SDMX version 1.0 schema instance, property ignored ");
                    break;
                case DATE_TIME:
                    LoggingUtil.warn(log, "can not map text type DATE TIME to SDMX version 1.0 schema instance, property ignored ");
                    break;
                case DAY:
                    LoggingUtil.warn(log, "can not map text type DAY to SDMX version 1.0 schema instance, property ignored ");
                    break;
                case DECIMAL:
                    textFormatType.setTextType(TextTypeType.NUM);
                    break;
                case DOUBLE:
                    textFormatType.setTextType(TextTypeType.NUM);
                    break;
                case DURATION:
                    LoggingUtil.warn(log, "can not map text type DURATION to SDMX version 1.0 schema instance, property ignored ");
                    break;
                case EXCLUSIVE_VALUE_RANGE:
                    LoggingUtil.warn(log, "can not map text type EXCLUSIVE VALUE RANGE to SDMX version 1.0 schema instance, property ignored ");
                    break;
                case FLOAT:
                    textFormatType.setTextType(TextTypeType.NUM);
                    break;
                case INCLUSIVE_VALUE_RANGE:
                    LoggingUtil.warn(log, "can not map text type INCLUSIVE VALUE RANGE to SDMX version 1.0 schema instance, property ignored ");
                    break;
                case INCREMENTAL:
                    LoggingUtil.warn(log, "can not map text type INCREMENTAL to SDMX version 1.0 schema instance, property ignored ");
                    break;
                case LONG:
                    textFormatType.setTextType(TextTypeType.NUM);
                    break;
                case MONTH:
                    LoggingUtil.warn(log, "can not map text type MONTH to SDMX version 1.0 schema instance, property ignored ");
                    break;
                case MONTH_DAY:
                    LoggingUtil.warn(log, "can not map text type MONTH DAY to SDMX version 1.0 schema instance, property ignored ");
                    break;
                case OBSERVATIONAL_TIME_PERIOD:
                    LoggingUtil.warn(log, "can not map text type OBSERVATIONAL TIME PERIOD to SDMX version 1.0 schema instance, property ignored ");
                    break;
                case SHORT:
                    textFormatType.setTextType(TextTypeType.NUM);
                    break;
                case STRING:
                    textFormatType.setTextType(TextTypeType.ALPHA);
                    break;
                case TIME:
                    LoggingUtil.warn(log, "can not map text type TIME to SDMX version 1.0 schema instance, property ignored ");
                    break;
                case TIMESPAN:
                    LoggingUtil.warn(log, "can not map text type TIMESPAN to SDMX version 1.0 schema instance, property ignored ");
                    break;
                case URI:
                    LoggingUtil.warn(log, "can not map text type URI to SDMX version 1.0 schema instance, property ignored ");
                    break;
                case YEAR:
                    LoggingUtil.warn(log, "can not map text type YEAR to SDMX version 1.0 schema instance, property ignored ");
                    break;
                case YEAR_MONTH:
                    LoggingUtil.warn(log, "can not map text type YEAR MONTH to SDMX version 1.0 schema instance, property ignored ");
                    break;
            }
        }
    }

    /**
     * Get text type text type [ ].
     *
     * @param ttWrapper the tt wrapper
     * @return the text type [ ]
     */
    TextType[] getTextType(List<TextTypeWrapper> ttWrapper) {
        if (!ObjectUtil.validCollection(ttWrapper)) {
            return null;
        }
        TextType[] ttArr = new TextType[ttWrapper.size()];
        for (int i = 0; i < ttWrapper.size(); i++) {
            TextType tt = getTextType(ttWrapper.get(i));
            ttArr[i] = tt;
        }
        return ttArr;
    }

    /**
     * Gets text type.
     *
     * @param ttWrapper the tt wrapper
     * @return the text type
     */
    TextType getTextType(TextTypeWrapper ttWrapper) {
        TextType tt = TextType.Factory.newInstance();
        tt.setLang(ttWrapper.getLocale());
        tt.setStringValue(ttWrapper.getValue());
        return tt;
    }

    /**
     * Has annotations boolean.
     *
     * @param annotable the annotable
     * @return the boolean
     */
    boolean hasAnnotations(AnnotableBean annotable) {
        if (ObjectUtil.validCollection(annotable.getAnnotations())) {
            return true;
        }
        return false;
    }

    /**
     * Gets annotations type.
     *
     * @param annotable the annotable
     * @return the annotations type
     */
    AnnotationsType getAnnotationsType(AnnotableBean annotable) {
        if (!ObjectUtil.validCollection(annotable.getAnnotations())) {
            return null;
        }
        AnnotationsType returnType = AnnotationsType.Factory.newInstance();

        for (AnnotationBean currentAnnotationBean : annotable.getAnnotations()) {
            AnnotationType annotation = returnType.addNewAnnotation();
            annotation.setAnnotationTextArray(getTextType(currentAnnotationBean.getText()));
            annotation.setAnnotationTitle(currentAnnotationBean.getTitle());
            annotation.setAnnotationType(currentAnnotationBean.getType());
            if (currentAnnotationBean.getUri() != null) {
                annotation.setAnnotationURL(currentAnnotationBean.getUri().toString());
            }
        }
        return returnType;
    }
}
