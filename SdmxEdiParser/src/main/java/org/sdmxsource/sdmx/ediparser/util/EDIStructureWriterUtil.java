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
package org.sdmxsource.sdmx.ediparser.util;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.ComponentBean;
import org.sdmxsource.sdmx.api.model.beans.base.TextFormatBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodeBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistBean;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.AttributeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.PrimaryMeasureBean;
import org.sdmxsource.sdmx.ediparser.constants.EDI_CONSTANTS;
import org.sdmxsource.sdmx.ediparser.constants.EDI_PREFIX;
import org.sdmxsource.sdmx.util.beans.ConceptRefUtil;

import java.math.BigInteger;


/**
 * The type Edi structure writer util.
 */
public class EDIStructureWriterUtil {


    /**
     * Parse codelist identifier string.
     *
     * @param codelist the codelist
     * @return the string
     */
//*********************************************************************************************//
    //***********************          CODELISTS                          *************************//
    //*********************************************************************************************//
    public static String parseCodelistIdentifier(CodelistBean codelist) {
        String codeListId = codelist.getId();
        if (codeListId.length() > 18) {
            throw new IllegalArgumentException("Codelist Id can not be more then 18 characters in EDI");
        }
        String codeListname = codelist.getName();

        if (codeListname.length() > 70) {
            throw new IllegalArgumentException("Codelist name can not be more then 70 characters in EDI");
        }

        StringBuilder sb = new StringBuilder();
        sb.append(EDI_PREFIX.CODELIST);
        sb.append(codeListId);
        sb.append(EDI_CONSTANTS.PLUS);
        sb.append(EDI_CONSTANTS.PLUS);
        sb.append(EDI_CONSTANTS.PLUS);
        sb.append(codeListname);
        sb.append(EDI_CONSTANTS.END_TAG);
        return sb.toString();
    }

    /**
     * Parse code id string.
     *
     * @param code the code
     * @return the string
     */
    public static String parseCodeId(CodeBean code) {
        String codeId = code.getId();
        EDIUtil.parseId(codeId);

        StringBuilder sb = new StringBuilder();
        sb.append(EDI_PREFIX.CODE_VALUE);
        sb.append(code.getId());
        sb.append(EDI_CONSTANTS.END_TAG);
        return sb.toString();
    }

    /**
     * Parse code name string.
     *
     * @param code the code
     * @return the string
     */
    public static String parseCodeName(CodeBean code) {
        return EDIUtil.stringToEDIFreeText(code.getName(), 70, 350);
    }

    /**
     * Parse concept identifier string.
     *
     * @param concept the concept
     * @return the string
     */
//*********************************************************************************************//
    //***********************          CONCEPTS                           *************************//
    //*********************************************************************************************//
    public static String parseConceptIdentifier(ConceptBean concept) {
        String conceptId = concept.getId().toUpperCase();
        EDIUtil.parseId(conceptId);
        return EDI_PREFIX.CONCEPT + conceptId + EDI_CONSTANTS.END_TAG;
    }

    /**
     * Parse concept name string.
     *
     * @param concept the concept
     * @return the string
     */
    public static String parseConceptName(ConceptBean concept) {
        return EDIUtil.stringToEDIFreeText(concept.getName(), 70, 70);
    }


    /**
     * Parse data structure identifier string.
     *
     * @param kf the kf
     * @return the string
     */
//*********************************************************************************************//
    //***********************          DSD                                *************************//
    //*********************************************************************************************//
    public static String parseDataStructureIdentifier(DataStructureBean kf) {
        String dsdId = kf.getId().toUpperCase();
        EDIUtil.parseId(dsdId);
        return EDI_PREFIX.DSD + dsdId + EDI_CONSTANTS.END_TAG;
    }

    /**
     * Parse data structure name string.
     *
     * @param kf the kf
     * @return the string
     */
    public static String parseDataStructureName(DataStructureBean kf) {
        return EDIUtil.stringToEDIFreeText(EDIUtil.stringToEdi(kf.getName()), 70, 70);
    }

    /**
     * Parse time format string.
     *
     * @param position  the position
     * @param conceptId the concept id
     * @return the string
     */
    public static String parseTimeFormat(int position, String conceptId) {
        return parseSCDSegment(conceptId, 1, position);
    }

    /**
     * Parse observation attribute string.
     *
     * @param attributeBean the attribute bean
     * @param position      the position
     * @return the string
     */
    public static String parseObservationAttribute(AttributeBean attributeBean, int position) {
        return parseSCDSegment(ConceptRefUtil.getConceptId(attributeBean.getConceptRef()).toUpperCase(), 3, position);
    }

    /**
     * Parse primary measure identification string.
     *
     * @param primaryMeasureBean the primary measure bean
     * @param position           the position
     * @return the string
     */
    public static String parsePrimaryMeasureIdentification(PrimaryMeasureBean primaryMeasureBean, int position) {
        return parseSCDSegment(ConceptRefUtil.getConceptId(primaryMeasureBean.getConceptRef()).toUpperCase(), 3, position);
    }

    /**
     * Parse dimension identification string.
     *
     * @param dimension the dimension
     * @param position  the position
     * @return the string
     */
    public static String parseDimensionIdentification(ComponentBean dimension, int position) {
        int segmentType = -1;
        if (dimension.getStructureType() == SDMX_STRUCTURE_TYPE.TIME_DIMENSION) {
            segmentType = 1;
        } else {
            DimensionBean dim = (DimensionBean) dimension;
            if (dim.isFrequencyDimension()) {
                segmentType = 13;
            } else {
                segmentType = 4;
            }
        }
        return parseSCDSegment(ConceptRefUtil.getConceptId(dimension.getConceptRef()).toUpperCase(), segmentType, position);
    }

    private static String parseSCDSegment(String id, int segmentType, int position) {
        StringBuilder sb = new StringBuilder();
        sb.append(EDI_PREFIX.DIMENSION);
        sb.append(segmentType);
        sb.append(EDI_CONSTANTS.PLUS);
        sb.append(id);
        sb.append(EDI_CONSTANTS.PLUS);
        sb.append(EDI_CONSTANTS.PLUS);
        sb.append(EDI_CONSTANTS.PLUS);
        sb.append(EDI_CONSTANTS.PLUS);
        sb.append(EDI_CONSTANTS.COLON);
        sb.append(position);
        sb.append(EDI_CONSTANTS.END_TAG);
        return sb.toString();
    }


    /**
     * Parse field length string.
     *
     * @param component the component
     * @param coded     the coded
     * @return the string
     */
    public static String parseFieldLength(ComponentBean component, boolean coded) {
        String fieldLength = coded ? EDI_CONSTANTS.DEFAULT_FIELD_LENGTH_CODED : EDI_CONSTANTS.DEFAULT_FIELD_LENGTH_UNCODED;

        if (component instanceof PrimaryMeasureBean) {
            fieldLength = EDI_CONSTANTS.DEFAULT_FIELD_LENGTH_PRIMARY_MEASURE;
        }

        if (component.getRepresentation() != null && component.getRepresentation().getTextFormat() != null) {
            TextFormatBean tf = component.getRepresentation().getTextFormat();

            boolean valueEquals = false;  //Alternative is up to
            boolean alphaNumeric = true;
            int length = -1;
            BigInteger minLength = tf.getMinLength();
            BigInteger maxLength = tf.getMaxLength();

            if (tf.getTextType() != null) {
                switch (tf.getTextType()) {
                    case BIG_INTEGER:
                        alphaNumeric = false;
                        break;
                    case COUNT:
                        alphaNumeric = false;
                        break;
                    case INTEGER:
                        alphaNumeric = false;
                        length = 11;
                        break;
                    case LONG:
                        alphaNumeric = false;
                        length = 20;
                        break;
                    case DOUBLE:
                        alphaNumeric = false;
                        break;
                    case SHORT:
                        alphaNumeric = false;
                        length = 6;
                        break;
                }
            }

            if (minLength != null && maxLength != null) {
                valueEquals = minLength.equals(maxLength);
            }
            if (minLength != null) {
                length = minLength.intValue();
            }
            if (maxLength != null) {
                length = maxLength.intValue();
            }
            if (length < 0) {
                length = 18;
            }
            String dataFormat = alphaNumeric ? "AN" : "N";
            String equalTo = valueEquals ? "" : "..";

            //TODO WHAT ABOUT THE ALPHA NUMERIC, HOW DO WE DECIDE THIS?
            fieldLength = dataFormat + equalTo + length;

        }
        return EDI_PREFIX.FIELD_LENGTH + fieldLength + EDI_CONSTANTS.END_TAG;
    }

    /**
     * Parse time format field length string.
     *
     * @return the string
     */
    public static String parseTimeFormatFieldLength() {
        return EDI_PREFIX.FIELD_LENGTH + "AN3" + EDI_CONSTANTS.END_TAG;
    }


    /**
     * Parse codelist reference string.
     *
     * @param componentBean the component bean
     * @return the string
     */
    public static String parseCodelistReference(ComponentBean componentBean) {
        return EDI_PREFIX.CODELIST_REFERENCE + componentBean.getRepresentation().getRepresentation().getMaintainableReference().getMaintainableId().toUpperCase() + EDI_CONSTANTS.END_TAG;
    }

    /**
     * Parse dimension attribute string.
     *
     * @param attribute the attribute
     * @return the string
     */
    public static String parseDimensionAttribute(AttributeBean attribute) {
        return EDI_PREFIX.ATTRIBUTE + ConceptRefUtil.getConceptId(attribute.getConceptRef()).toUpperCase() + EDI_CONSTANTS.END_TAG;
    }

    /**
     * Parse time period string.
     *
     * @param pos the pos
     * @return the string
     */
    public static String parseTimePeriod(int pos) {
        return EDI_PREFIX.DIMENSION + "1+TIME_PERIOD++++" + pos + EDI_CONSTANTS.END_TAG;
    }

    /**
     * Parse useage status string.
     *
     * @param attribute the attribute
     * @return the string
     */
    public static String parseUseageStatus(AttributeBean attribute) {
        int useageStatus = attribute.isMandatory() ? 2 : 1;
        return EDI_PREFIX.USEAGE_STATUS.getPrefix() + useageStatus + ":USS" + EDI_CONSTANTS.END_TAG;
    }

    /**
     * Parse dimension attribute attachment level string.
     *
     * @param attribute the attribute
     * @return the string
     */
    public static String parseDimensionAttributeAttachmentLevel(AttributeBean attribute) {
        int attchementLevel = -1;
        switch (attribute.getAttachmentLevel()) {
            case DATA_SET:
                attchementLevel = 1;
                break;
            case DIMENSION_GROUP:
                attchementLevel = 4;
                break;
            case OBSERVATION:
                attchementLevel = 5;
                break;
            case GROUP:
                attchementLevel = 9;
                break;
        }
        return EDI_PREFIX.ATTRIBUTE_ATTACHMENT_VALUE.getPrefix() + attchementLevel + ":ALV" + EDI_CONSTANTS.END_TAG;
    }


}
