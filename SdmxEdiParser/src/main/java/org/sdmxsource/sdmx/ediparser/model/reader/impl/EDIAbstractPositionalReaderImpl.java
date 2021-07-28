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
package org.sdmxsource.sdmx.ediparser.model.reader.impl;

import org.sdmxsource.sdmx.api.model.header.PartyBean;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.ediparser.model.document.EDIDocumentPosition;
import org.sdmxsource.sdmx.ediparser.model.document.EDIMetadata;
import org.sdmxsource.sdmx.ediparser.model.reader.EDIAbstractPositionalReader;

import java.util.Date;


/**
 * The type Edi abstract positional reader.
 */
public abstract class EDIAbstractPositionalReaderImpl extends EDIReaderImpl implements EDIAbstractPositionalReader {
    private EDIMetadata ediMetadata;
    private EDIDocumentPosition documentPosition;

    /**
     * Instantiates a new Edi abstract positional reader.
     *
     * @param dataFile         the data file
     * @param documentPosition the document position
     * @param ediMetadata      the edi metadata
     */
    public EDIAbstractPositionalReaderImpl(ReadableDataLocation dataFile,
                                           EDIDocumentPosition documentPosition,
                                           EDIMetadata ediMetadata) {
        super(dataFile);
        this.ediMetadata = ediMetadata;
        this.documentPosition = documentPosition;
    }

    @Override
    public Date getPreparationDate() {
        return documentPosition.getPreparationDate();
    }

    @Override
    public EDIMetadata getEdiDocumentMetadata() {
        return ediMetadata;
    }

    @Override
    public String getMessageAgency() {
        return documentPosition.getMessageAgency();
    }

    @Override
    public PartyBean getSendingAgency() {
        return documentPosition.getSendingAgency();
    }

    @Override
    public String getRecievingAgency() {
        return documentPosition.getRecievingAgency();
    }


}
