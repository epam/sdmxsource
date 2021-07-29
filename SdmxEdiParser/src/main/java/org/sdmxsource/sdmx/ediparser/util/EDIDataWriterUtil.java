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

import org.sdmxsource.sdmx.api.constants.ATTRIBUTE_ATTACHMENT_LEVEL;
import org.sdmxsource.sdmx.api.constants.DATASET_ACTION;
import org.sdmxsource.sdmx.ediparser.constants.EDI_PREFIX;
import org.sdmxsource.sdmx.ediparser.constants.EDI_TIME_FORMAT;
import org.sdmxsource.util.ObjectUtil;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.sdmxsource.sdmx.ediparser.constants.EDI_CONSTANTS.*;


/**
 * The type Edi data writer util.
 */
public class EDIDataWriterUtil {

    /**
     * Parse data set identifier string.
     *
     * @param dataSetId the data set id
     * @return the string
     */
    public static String parseDataSetIdentifier(String dataSetId) {
        String dataSetInEdiForm = EDIUtil.stringToEDIWithMaxLength(dataSetId, 18);
        return EDI_PREFIX.DATA_START + dataSetInEdiForm + END_TAG;
    }

    /**
     * Parse status string.
     *
     * @param action the action
     * @return the string
     */
    public static String parseStatus(DATASET_ACTION action) {
        // The actions Append, Replace and Information are represented in EDI
        // by the value 7 whereas DELETE is represented by the value 6
        String status;
        if (action == DATASET_ACTION.DELETE) {
            status = "6";
        } else {
            status = "7";
        }
        return EDI_PREFIX.DATASET_ACTION + status + END_TAG;
    }

    /**
     * Parse missing value string.
     *
     * @return the string
     */
    public static String parseMissingValue() {
        return EDI_PREFIX.DATASET_MISSING_VALUE_SYMBOL + MISSING_VAL + END_TAG;
    }

    /**
     * Parse preperation date string.
     *
     * @param date the date
     * @return the string
     */
    public static String parsePreperationDate(Date date) {
        EDI_TIME_FORMAT timeFormat = EDI_TIME_FORMAT.MINUTE_FOUR_DIG_YEAR;
        return "DTM+242:" + timeFormat.formatDate(date) + COLON + timeFormat.getEdiValue() + END_TAG;
    }

    /**
     * Parse reporting period date string.
     *
     * @param dateFrom the date from
     * @param dateTo   the date to
     * @return the string
     */
    public static String parseReportingPeriodDate(Date dateFrom, Date dateTo) {
        if (dateFrom.getTime() != dateTo.getTime()) {
            EDI_TIME_FORMAT timeFormat = EDI_TIME_FORMAT.RANGE_DAILY;
            return "DTM+Z02:" + timeFormat.formatDate(dateFrom) + timeFormat.formatDate(dateTo) + COLON + timeFormat.getEdiValue() + END_TAG;
        }

        // dateFrom and dateTo are the same - has any time values been set?
        // If not use EDI date format 102 CCYYMMDD
        // If so, use EDI date format 203 CCYYMMDDHHMM
        // Note using the deprecated date methods here so that we don't have timezone issues.
        if (dateFrom.getHours() == 0 && dateFrom.getMinutes() == 0 && dateFrom.getSeconds() == 0) {
            EDI_TIME_FORMAT timeFormat = EDI_TIME_FORMAT.DAILY_FOUR_DIG_YEAR;
            return "DTM+Z02:" + timeFormat.formatDate(dateFrom) + COLON + timeFormat.getEdiValue() + END_TAG;
        }

        EDI_TIME_FORMAT timeFormat = EDI_TIME_FORMAT.MINUTE_FOUR_DIG_YEAR;
        return "DTM+Z02:" + timeFormat.formatDate(dateFrom) + COLON + timeFormat.getEdiValue() + END_TAG;
    }

    /**
     * Parse data structure identifier string.
     *
     * @param kfId the kf id
     * @return the string
     */
    public static String parseDataStructureIdentifier(String kfId) {
        return EDI_PREFIX.DSD_REFERENCE + kfId + END_TAG;
    }

    /**
     * Parse method to send data set string.
     *
     * @return the string
     */
    public static String parseMethodToSendDataSet() {
        return EDI_PREFIX.DATASET_SEND_METHOD + END_TAG;
    }

    /**
     * Parse start attributes string.
     *
     * @return the string
     */
    public static String parseStartAttributes() {
        return EDI_PREFIX.DATASET_FOOTNOTE_SECTION + "Attributes:10" + END_TAG;  //TODO is Attributes correct
    }

    /**
     * Parse attribute attachment string.
     *
     * @param lastDimensionPosition the last dimension position
     * @param key                   the key
     * @return the string
     */
    public static String parseAttributeAttachment(int lastDimensionPosition, String key) {
        if (ObjectUtil.validString(key)) {
            return EDI_PREFIX.DATASET_DATAATTRIBUTE.getPrefix() + lastDimensionPosition + PLUS + key + END_TAG;
        }
        return EDI_PREFIX.DATASET_DATAATTRIBUTE.getPrefix() + lastDimensionPosition + END_TAG;
    }

    /**
     * Parse attribute identifier string.
     *
     * @param conceptId the concept id
     * @param isCoded   the is coded
     * @return the string
     */
    public static String parseAttributeIdentifier(String conceptId, boolean isCoded) {
        if (isCoded) {
            return EDI_PREFIX.DATASET_ATTRIBUTE_CODED + conceptId + END_TAG;
        }
        return EDI_PREFIX.DATASET_ATTRIBUTE_UNCODED + conceptId + END_TAG;
    }

    /**
     * Parse attribute value list.
     *
     * @param value  the value
     * @param isCode the is code
     * @return the list
     */
    public static List<String> parseAttributeValue(String value, boolean isCode) {
        if (isCode) {
            String codeValue = EDI_PREFIX.CODE_VALUE.getPrefix() + EDIUtil.stringToEdi(value) + END_TAG;
            return Collections.singletonList(codeValue);
        }
        return EDIUtil.stringToEDIFreeText(value, 70, 350, 20); //The max length is 70 per : separated segment, there can be 5 segments per FTX segment, and there can be 20 FTX segments in a row
    }

    /**
     * Parse attribute scope string.
     *
     * @param attachment the attachment
     * @return the string
     */
    public static String parseAttributeScope(ATTRIBUTE_ATTACHMENT_LEVEL attachment) {
        String scope;
        switch (attachment) {
            case DATA_SET:
                scope = "1";
                break;
            case OBSERVATION:
                scope = "5";
                break;
            default:
                scope = "4";
        }
        return EDI_PREFIX.DATASET_ATTRIBUTE_SCOPE + scope + END_TAG;
    }
}
