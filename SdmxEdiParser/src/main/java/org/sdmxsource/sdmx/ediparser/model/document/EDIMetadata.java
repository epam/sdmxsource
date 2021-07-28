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
package org.sdmxsource.sdmx.ediparser.model.document;

import java.util.Date;
import java.util.List;

/**
 * The interface Edi metadata.
 */
public interface EDIMetadata {

    /**
     * Gets receiver identification.
     *
     * @return the receiver identification
     */
    String getReceiverIdentification();

    /**
     * Gets date of preparation.
     *
     * @return the date of preparation
     */
    Date getDateOfPreparation();

    /**
     * Gets interchange reference.
     *
     * @return the interchange reference
     */
    String getInterchangeReference();

    /**
     * Gets application reference.
     *
     * @return the application reference
     */
    String getApplicationReference();

    /**
     * Gets document index.
     *
     * @return the document index
     */
    List<EDIDocumentPosition> getDocumentIndex();

    /**
     * Add document index.
     *
     * @param pos the pos
     */
    void addDocumentIndex(EDIDocumentPosition pos);

    /**
     * Gets message name.
     *
     * @return the message name
     */
    String getMessageName();

    /**
     * Sets message name.
     *
     * @param messageName the message name
     */
    void setMessageName(String messageName);


    /**
     * Gets reporting begin.
     *
     * @return the reporting begin
     */
    Date getReportingBegin();

    /**
     * Sets reporting begin.
     *
     * @param date the date
     */
    void setReportingBegin(Date date);

    /**
     * Gets reporting end.
     *
     * @return the reporting end
     */
    Date getReportingEnd();

    /**
     * Sets reporting end.
     *
     * @param date the date
     */
    void setReportingEnd(Date date);

    /**
     * Is test boolean.
     *
     * @return the boolean
     */
    boolean isTest();
}
