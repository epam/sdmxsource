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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v2;

import org.apache.xmlbeans.GDuration;
import org.sdmx.resources.sdmxml.schemas.v20.common.AnnotationType;
import org.sdmx.resources.sdmxml.schemas.v20.common.AnnotationsType;
import org.sdmx.resources.sdmxml.schemas.v20.common.TextType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.DataProviderRefType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.DataflowRefType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.MetadataflowRefType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.TextFormatType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.TextTypeType;
import org.sdmxsource.sdmx.api.model.beans.base.AnnotableBean;
import org.sdmxsource.sdmx.api.model.beans.base.AnnotationBean;
import org.sdmxsource.sdmx.api.model.beans.base.TextFormatBean;
import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.util.ObjectUtil;

import java.util.Collection;
import java.util.List;


/**
 * The type Abstract builder.
 */
public class AbstractBuilder {

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
    boolean validCollection(@SuppressWarnings("rawtypes") Collection collection) {
        return ObjectUtil.validCollection(collection);
    }

    /**
     * Populate text format type.
     *
     * @param textFormatType the text format type
     * @param textFormat     the text format
     */
    void populateTextFormatType(TextFormatType textFormatType, TextFormatBean textFormat) {
        if (textFormat.getTextType() != null) {
            switch (textFormat.getTextType()) {
                case BIG_INTEGER:
                    textFormatType.setTextType(TextTypeType.BIG_INTEGER);
                    break;
                case BOOLEAN:
                    textFormatType.setTextType(TextTypeType.BOOLEAN);
                    break;
                case COUNT:
                    textFormatType.setTextType(TextTypeType.COUNT);
                    break;
                case DATE:
                    textFormatType.setTextType(TextTypeType.DATE);
                    break;
                case DATE_TIME:
                    textFormatType.setTextType(TextTypeType.DATE_TIME);
                    break;
                case DAY:
                    textFormatType.setTextType(TextTypeType.DAY);
                    break;
                case DECIMAL:
                    textFormatType.setTextType(TextTypeType.DECIMAL);
                    break;
                case DOUBLE:
                    textFormatType.setTextType(TextTypeType.DOUBLE);
                    break;
                case DURATION:
                    textFormatType.setTextType(TextTypeType.DURATION);
                    break;
                case EXCLUSIVE_VALUE_RANGE:
                    textFormatType.setTextType(TextTypeType.EXCLUSIVE_VALUE_RANGE);
                    break;
                case FLOAT:
                    textFormatType.setTextType(TextTypeType.FLOAT);
                    break;
                case INCLUSIVE_VALUE_RANGE:
                    textFormatType.setTextType(TextTypeType.INCLUSIVE_VALUE_RANGE);
                    break;
                case INCREMENTAL:
                    textFormatType.setTextType(TextTypeType.INCREMENTAL);
                    break;
                case INTEGER:
                    textFormatType.setTextType(TextTypeType.INTEGER);
                    break;
                case LONG:
                    textFormatType.setTextType(TextTypeType.LONG);
                    break;
                case MONTH:
                    textFormatType.setTextType(TextTypeType.MONTH);
                    break;
                case MONTH_DAY:
                    textFormatType.setTextType(TextTypeType.MONTH_DAY);
                    break;
                case OBSERVATIONAL_TIME_PERIOD:
                    textFormatType.setTextType(TextTypeType.OBSERVATIONAL_TIME_PERIOD);
                    break;
                case SHORT:
                    textFormatType.setTextType(TextTypeType.SHORT);
                    break;
                case STRING:
                    textFormatType.setTextType(TextTypeType.STRING);
                    break;
                case TIME:
                    textFormatType.setTextType(TextTypeType.TIME);
                    break;
                case TIMESPAN:
                    textFormatType.setTextType(TextTypeType.TIMESPAN);
                    break;
                case URI:
                    textFormatType.setTextType(TextTypeType.URI);
                    break;
                case YEAR:
                    textFormatType.setTextType(TextTypeType.YEAR);
                    break;
                case YEAR_MONTH:
                    textFormatType.setTextType(TextTypeType.YEAR_MONTH);
                    break;
            }
        }
        if (validString(textFormat.getPattern())) {
            textFormatType.setPattern(textFormat.getPattern());
        }
        if (validString(textFormat.getTimeInterval())) {
            textFormatType.setTimeInterval(new GDuration(textFormat.getTimeInterval()));
        }
        if (textFormat.getDecimals() != null) {
            textFormatType.setDecimals(textFormat.getDecimals());
        }
        if (textFormat.getEndValue() != null) {
            textFormatType.setEndValue(textFormat.getEndValue().doubleValue());
        }
        if (textFormat.getInterval() != null) {
            textFormatType.setInterval(textFormat.getInterval().doubleValue());
        }
        if (textFormat.getMaxLength() != null) {
            textFormatType.setMaxLength(textFormat.getMaxLength());
        }
        if (textFormat.getMinLength() != null) {
            textFormatType.setMinLength(textFormat.getMinLength());
        }
        if (textFormat.getStartValue() != null) {
            textFormatType.setStartValue(textFormat.getStartValue().doubleValue());
        }
        if (textFormat.getSequence().isSet()) {
            textFormatType.setIsSequence(textFormat.getSequence().isTrue());
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
     * Gets text type.
     *
     * @param englishString the english string
     * @return the text type
     */
    TextType getTextType(String englishString) {
        TextType tt = TextType.Factory.newInstance();
        tt.setLang("en");
        tt.setStringValue(englishString);
        return tt;
    }

    /**
     * Sets default text.
     *
     * @param tt the tt
     */
    void setDefaultText(TextType tt) {
        tt.setLang("en");
        tt.setStringValue("Undefined");
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
            if (ObjectUtil.validCollection(currentAnnotationBean.getText())) {
                annotation.setAnnotationTextArray(getTextType(currentAnnotationBean.getText()));
            }
            if (ObjectUtil.validString(currentAnnotationBean.getTitle())) {
                annotation.setAnnotationTitle(currentAnnotationBean.getTitle());
            }
            if (ObjectUtil.validString(currentAnnotationBean.getType())) {
                annotation.setAnnotationType(currentAnnotationBean.getType());
            }
            if (currentAnnotationBean.getUri() != null) {
                annotation.setAnnotationURL(currentAnnotationBean.getUri().toString());
            }
        }

        return returnType;
    }

    /**
     * Populate dataflow ref.
     *
     * @param crossReference the cross reference
     * @param dfRef          the df ref
     */
    void populateDataflowRef(CrossReferenceBean crossReference, DataflowRefType dfRef) {
        MaintainableRefBean maintRef = crossReference.getMaintainableReference();
        if (validString(crossReference.getTargetUrn())) {
            dfRef.setURN(crossReference.getTargetUrn());
        }
        if (validString(maintRef.getAgencyId())) {
            dfRef.setAgencyID(maintRef.getAgencyId());
        }
        if (validString(maintRef.getMaintainableId())) {
            dfRef.setDataflowID(maintRef.getMaintainableId());
        }
        if (validString(maintRef.getVersion())) {
            dfRef.setVersion(maintRef.getVersion());
        }
    }

    /**
     * Populate metadataflow ref.
     *
     * @param crossReference the cross reference
     * @param mdfRef         the mdf ref
     */
    void populateMetadataflowRef(CrossReferenceBean crossReference, MetadataflowRefType mdfRef) {
        MaintainableRefBean maintRef = crossReference.getMaintainableReference();
        if (validString(crossReference.getTargetUrn())) {
            mdfRef.setURN(crossReference.getTargetUrn());
        }
        if (validString(maintRef.getAgencyId())) {
            mdfRef.setAgencyID(maintRef.getAgencyId());
        }
        if (validString(maintRef.getMaintainableId())) {
            mdfRef.setMetadataflowID(maintRef.getMaintainableId());
        }
        if (validString(maintRef.getVersion())) {
            mdfRef.setVersion(maintRef.getVersion());
        }
    }

    /**
     * Populate dataprovider ref.
     *
     * @param crossReference the cross reference
     * @param dpRef          the dp ref
     */
    void populateDataproviderRef(CrossReferenceBean crossReference, DataProviderRefType dpRef) {
        MaintainableRefBean maintRef = crossReference.getMaintainableReference();
        if (validString(crossReference.getTargetUrn())) {
            dpRef.setURN(crossReference.getTargetUrn());
        }
        if (validString(maintRef.getAgencyId())) {
            dpRef.setOrganisationSchemeAgencyID(maintRef.getAgencyId());
        }
        if (validString(maintRef.getMaintainableId())) {
            dpRef.setOrganisationSchemeID(maintRef.getMaintainableId());
        }
        if (validString(maintRef.getVersion())) {
            dpRef.setVersion(maintRef.getVersion());
        }
        if (validString(crossReference.getChildReference().getId())) {
            dpRef.setDataProviderID(crossReference.getChildReference().getId());
        }
    }
}
