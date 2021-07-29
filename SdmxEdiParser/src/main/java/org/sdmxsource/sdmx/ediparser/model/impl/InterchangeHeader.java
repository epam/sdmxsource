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
package org.sdmxsource.sdmx.ediparser.model.impl;

import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.ediparser.constants.EDI_PREFIX;
import org.sdmxsource.util.ObjectUtil;

import java.util.Date;


/**
 * The type Interchange header.
 */
public class InterchangeHeader extends AbstractEdiModel {
    private String syntaxIdentifier = "UNOC";
    private String syntaxVersion = "3";
    private String senderIdentification;
    private String receiverIdentification;
    private Date dateOfPreperation;
    private String interchangeRef;
    private String applicationRef = "SDMX-EDI";
    private String testIndicator;

    /**
     * Instantiates a new Interchange header.
     *
     * @param senderIdentification   the sender identification
     * @param receiverIdentification the receiver identification
     * @param dateOfPreperation      the date of preperation
     * @param interchangeRefNum      the interchange ref num
     * @param testIndicator          the test indicator
     */
    public InterchangeHeader(String senderIdentification,
                             String receiverIdentification,
                             Date dateOfPreperation,
                             int interchangeRefNum,
                             String testIndicator) {
        this.interchangeRef = "IREF" + prependZeros(interchangeRefNum, 6);
        setupHeader(senderIdentification, receiverIdentification, dateOfPreperation, interchangeRef, testIndicator);
    }

    /**
     * Constructor with two values for the InterchangeRef. The String value will be used if it is valid, otherwise the numeric value will be used.
     *
     * @param senderIdentification     the sender identification
     * @param receiverIdentification   the receiver identification
     * @param dateOfPreperation        the date of preperation
     * @param interchangeRef           the interchange ref
     * @param defaultInterchangeRefNum The value that will be used (although this value may be formatted) if the interchangeRef value is deemed to be illegal.
     * @param testIndicator            the test indicator
     */
    public InterchangeHeader(String senderIdentification,
                             String receiverIdentification,
                             Date dateOfPreperation,
                             String interchangeRef,
                             int defaultInterchangeRefNum,
                             String testIndicator) {
        if (!doesInterchageRefConformToStandard(interchangeRef)) {
            this.interchangeRef = "IREF" + prependZeros(defaultInterchangeRefNum, 6);
        }
        setupHeader(senderIdentification, receiverIdentification, dateOfPreperation, interchangeRef, testIndicator);
    }

    private void setupHeader(String senderIdentification,
                             String receiverIdentification,
                             Date dateOfPreperation,
                             String interchangeRef,
                             String testIndicator) {

        if (!ObjectUtil.validString(senderIdentification)) {
            throw new SdmxSemmanticException("Can not write EDI Interchange Header, no sender identification provided");
        }
        if (!ObjectUtil.validString(receiverIdentification)) {
            throw new SdmxSemmanticException("Can not write EDI Interchange Header, no receiver identification provided");
        }
        if (dateOfPreperation == null) {
            dateOfPreperation = new Date();
        }
        this.senderIdentification = senderIdentification;
        this.receiverIdentification = receiverIdentification;
        this.dateOfPreperation = dateOfPreperation;

        this.interchangeRef = interchangeRef;
        this.testIndicator = testIndicator;

    }

    // The standard for IRefs:
    //  Must be 10 characters
    //  1st four characters must be the string: IREF
    //  characters 5-10 must be numeric
    private boolean doesInterchageRefConformToStandard(String iref) {
        if (iref.length() != 10) {
            return false;
        }
        if (!iref.substring(0, 4).equals("IREF")) {
            return false;
        }
        for (int i = 4; i < iref.length(); i++) {
            char c = iref.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Gets interchange ref.
     *
     * @return the interchange ref
     */
    public String getInterchangeRef() {
        return interchangeRef;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(EDI_PREFIX.INTERCHANGE_HEADER);
        sb.append(syntaxIdentifier);
        sb.append(COLON);
        sb.append(syntaxVersion);
        sb.append(PLUS);
        sb.append(senderIdentification);
        sb.append(PLUS);
        sb.append(receiverIdentification);
        sb.append(PLUS);
        sb.append(formatDate(dateOfPreperation)); //TODO Date and time of prep
        sb.append(PLUS);
        sb.append(interchangeRef);
        sb.append(PLUS);
        sb.append(PLUS);
        sb.append(applicationRef);
        if (testIndicator != null) {
            sb.append(PLUS);
            sb.append(PLUS);
            sb.append(PLUS);
            sb.append(PLUS);
            sb.append(testIndicator);
        }
        sb.append(END_TAG);
        return sb.toString();
    }
}
