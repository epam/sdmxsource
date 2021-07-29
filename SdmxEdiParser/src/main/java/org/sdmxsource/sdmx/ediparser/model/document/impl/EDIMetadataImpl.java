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
package org.sdmxsource.sdmx.ediparser.model.document.impl;

import org.sdmxsource.sdmx.ediparser.model.document.EDIDocumentPosition;
import org.sdmxsource.sdmx.ediparser.model.document.EDIMetadata;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The type Edi metadata.
 */
public class EDIMetadataImpl implements EDIMetadata {
    /**
     * The Receiver identification.
     */
    public String receiverIdentification;
    /**
     * The Date of preparation.
     */
    public Date dateOfPreparation;
    /**
     * The Interchange reference.
     */
    public String interchangeReference;
    /**
     * The Application reference.
     */
    public String applicationReference;
    /**
     * The Document positions.
     */
    public List<EDIDocumentPosition> documentPositions = new ArrayList<EDIDocumentPosition>();
    /**
     * The Reporting begin.
     */
    public Date reportingBegin;
    /**
     * The Reporting end.
     */
    public Date reportingEnd;
    /**
     * The Is test.
     */
    public boolean isTest;
    private String messageName;

    /**
     * Instantiates a new Edi metadata.
     *
     * @param receiverIdentification the receiver identification
     * @param dateOfPreparation      the date of preparation
     * @param interchangeReference   the interchange reference
     * @param applicationReference   the application reference
     * @param reportingBegin         the reporting begin
     * @param reportingEnd           the reporting end
     * @param isTest                 the is test
     */
    public EDIMetadataImpl(String receiverIdentification,
                           Date dateOfPreparation, String interchangeReference,
                           String applicationReference,
                           Date reportingBegin,
                           Date reportingEnd,
                           boolean isTest) {
        this.receiverIdentification = receiverIdentification;
        this.dateOfPreparation = dateOfPreparation;
        this.interchangeReference = interchangeReference;
        this.applicationReference = applicationReference;
        this.reportingBegin = reportingBegin;
        this.reportingEnd = reportingEnd;
        this.isTest = isTest;
    }

    public String getMessageName() {
        return messageName;
    }

    public void setMessageName(String messageName) {
        this.messageName = messageName;
    }

    @Override
    public String getReceiverIdentification() {
        return receiverIdentification;
    }

    @Override
    public Date getDateOfPreparation() {
        return dateOfPreparation;
    }

    @Override
    public String getInterchangeReference() {
        return interchangeReference;
    }

    @Override
    public String getApplicationReference() {
        return applicationReference;
    }

    @Override
    public void addDocumentIndex(EDIDocumentPosition index) {
        this.documentPositions.add(index);
    }

    @Override
    public List<EDIDocumentPosition> getDocumentIndex() {
        return documentPositions;
    }

    @Override
    public Date getReportingBegin() {
        return reportingBegin;
    }

    @Override
    public void setReportingBegin(Date date) {
        this.reportingBegin = date;
    }

    @Override
    public Date getReportingEnd() {
        return reportingEnd;
    }

    @Override
    public void setReportingEnd(Date date) {
        this.reportingEnd = date;
    }

    @Override
    public boolean isTest() {
        return this.isTest;
    }
}
