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
package org.sdmxsource.sdmx.ediparser.constants;

/**
 * The enum Edi prefix.
 */
public enum EDI_PREFIX {
    /**
     * Message start edi prefix.
     */
    MESSAGE_START("UNA:+.? "),
    /**
     * Interchange header edi prefix.
     */
    INTERCHANGE_HEADER("UNB+"),
    /**
     * Message identification edi prefix.
     */
    MESSAGE_IDENTIFICATION("UNH+"),
    /**
     * Message function edi prefix.
     */
    MESSAGE_FUNCTION("BGM+"),
    /**
     * End message administration edi prefix.
     */
    END_MESSAGE_ADMINISTRATION("UNT+"),
    /**
     * End message edi prefix.
     */
    END_MESSAGE("UNZ+"),
    /**
     * Contact information edi prefix.
     */
    CONTACT_INFORMATION("CTA+"),
    /**
     * Communication number edi prefix.
     */
    COMMUNICATION_NUMBER("COM+"),
    /**
     * Data start edi prefix.
     */
    DATA_START("DSI+"),
    /**
     * Dataset action edi prefix.
     */
    DATASET_ACTION("STS+3+"),
    /**
     * Dataset datetime edi prefix.
     */
    DATASET_DATETIME("DTM+"),
    /**
     * Dataset send method edi prefix.
     */
    DATASET_SEND_METHOD("GIS+AR3"),
    /**
     * Dataset missing value symbol edi prefix.
     */
    DATASET_MISSING_VALUE_SYMBOL("GIS+1:::"),
    /**
     * Dataset data edi prefix.
     */
    DATASET_DATA("ARR++"),
    /**
     * Dataset dataattribute edi prefix.
     */
    DATASET_DATAATTRIBUTE("ARR+"),
    /**
     * Dataset footnote section edi prefix.
     */
    DATASET_FOOTNOTE_SECTION("FNS+"),
    /**
     * Dataset attribute scope edi prefix.
     */
    DATASET_ATTRIBUTE_SCOPE("REL+Z01+"),
    /**
     * Dataset attribute coded edi prefix.
     */
    DATASET_ATTRIBUTE_CODED("IDE+Z10+"),
    /**
     * Message id provided by sender edi prefix.
     */
    MESSAGE_ID_PROVIDED_BY_SENDER("IDE+10+"),
    /**
     * Dataset attribute uncoded edi prefix.
     */
    DATASET_ATTRIBUTE_UNCODED("IDE+Z11+"),
    /**
     * Dsd reference edi prefix.
     */
    DSD_REFERENCE("IDE+5+"),
    /**
     * Message agency edi prefix.
     */
    MESSAGE_AGENCY("NAD+Z02+"),
    /**
     * Recieving agency edi prefix.
     */
    RECIEVING_AGENCY("NAD+MR+"),
    /**
     * Sending agency edi prefix.
     */
    SENDING_AGENCY("NAD+MS+"),
    /**
     * Codelist edi prefix.
     */
    CODELIST("VLI+"),
    /**
     * Code value edi prefix.
     */
    CODE_VALUE("CDV+"),
    /**
     * Dsd edi prefix.
     */
    DSD("ASI+"),
    /**
     * Attribute edi prefix.
     */
    ATTRIBUTE("SCD+Z09+"),
    /**
     * Dimension edi prefix.
     */
    DIMENSION("SCD+"),
    /**
     * Concept edi prefix.
     */
    CONCEPT("STC+"),
    /**
     * String edi prefix.
     */
    STRING("FTX+ACM+++"),
    /**
     * Field length edi prefix.
     */
    FIELD_LENGTH("ATT+3+5+:::"),
    /**
     * Useage status edi prefix.
     */
    USEAGE_STATUS("ATT+3+35+"),
    /**
     * Attribute attachment value edi prefix.
     */
    ATTRIBUTE_ATTACHMENT_VALUE("ATT+3+32+"),
    /**
     * Codelist reference edi prefix.
     */
    CODELIST_REFERENCE("IDE+1+");

    private String prefix;

    private EDI_PREFIX(String prefix) {
        this.prefix = prefix;
    }

    /**
     * Parse string edi prefix.
     *
     * @param str the str
     * @return the edi prefix
     */
    public static EDI_PREFIX parseString(String str) {
        for (EDI_PREFIX currentPrefix : EDI_PREFIX.values()) {
            if (str.startsWith(currentPrefix.getPrefix())) {
                return currentPrefix;
            }
        }
        throw new IllegalArgumentException("Unknown EDI Prefix for Line : " + str);
    }

    /**
     * Gets prefix.
     *
     * @return the prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Returns true if the current line is the message start
     *
     * @return boolean boolean
     */
    public boolean isMessageStart() {
        return this == MESSAGE_START;
    }

    /**
     * Returns true if the current line is the message start
     *
     * @return boolean boolean
     */
    public boolean isInterchangeHeader() {
        return this == INTERCHANGE_HEADER;
    }

    /**
     * Returns true if the current line is the message start
     *
     * @return true if the current line is the message start
     */
    public boolean isMessageIdentification() {
        return this == MESSAGE_IDENTIFICATION;
    }

    /**
     * Is end message administration.
     *
     * @return true if it is end message administration.
     */
    public boolean isEndMessageAdministration() {
        return this == END_MESSAGE_ADMINISTRATION;
    }

    /**
     * Is codelist reference boolean.
     *
     * @return true if it is codelist reference.
     */
    public boolean isCodelistReference() {
        return this == CODELIST_REFERENCE;
    }

    /**
     * Is attribute boolean.
     *
     * @return true if it is attribute boolean.
     */
    public boolean isAttribute() {
        return this == ATTRIBUTE;
    }


    /**
     * Returns true if the current line is the message start
     *
     * @return true if the current line is the message start
     */
    public boolean isMessageFunction() {
        return this == MESSAGE_FUNCTION;
    }

    /**
     * Is codelist segment boolean.
     *
     * @return true if it is codelist segment boolean.
     */
    public boolean isCodelistSegment() {
        return this == CODELIST;
    }

    /**
     * Is concept segment boolean.
     *
     * @return true if it is concept segment boolean.
     */
    public boolean isConceptSegment() {
        return this == CONCEPT;
    }

    /**
     * Is dsd segment boolean.
     *
     * @return true if it is dsd segment boolean.
     */
    public boolean isDSDSegment() {
        return this == DSD;
    }

    /**
     * Is structure segment boolean.
     *
     * @return true if it is structure segment boolean.
     */
    public boolean isStructureSegment() {
        return isCodelistSegment() || isConceptSegment() || isDSDSegment();
    }

    /**
     * Is data segment boolean.
     *
     * @return true if it is data segment boolean.
     */
    public boolean isDataSegment() {
        return this == DATA_START;
    }

    @Override
    public String toString() {
        return this.prefix;
    }
}
