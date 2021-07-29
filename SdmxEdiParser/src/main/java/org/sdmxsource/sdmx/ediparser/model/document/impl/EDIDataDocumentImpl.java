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

import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.ediparser.model.document.EDIDataDocument;
import org.sdmxsource.sdmx.ediparser.model.document.EDIDocumentPosition;
import org.sdmxsource.sdmx.ediparser.model.document.EDIMetadata;
import org.sdmxsource.sdmx.ediparser.model.reader.EDIDataReader;
import org.sdmxsource.sdmx.ediparser.model.reader.impl.EDIDataReaderImpl;


/**
 * The type Edi data document.
 */
public class EDIDataDocumentImpl implements EDIDataDocument {

    private ReadableDataLocation dataLocation;
    private EDIDocumentPosition documentPosition;
    private EDIMetadata ediMetadata;

    private EDIDataReader dataReader;

    /**
     * Instantiates a new Edi data document.
     *
     * @param documentURI      the document uri
     * @param documentPosition the document position
     * @param ediMetadata      the edi metadata
     */
    public EDIDataDocumentImpl(ReadableDataLocation documentURI, EDIDocumentPosition documentPosition, EDIMetadata ediMetadata) {
        this.dataLocation = documentURI;
        this.documentPosition = documentPosition;
        this.ediMetadata = ediMetadata;
    }

    @Override
    public EDIDataReader getDataReader() {
        // There should only ever be a single DataReader which is used by all calling instances.
        if (dataReader == null) {
            dataReader = new EDIDataReaderImpl(dataLocation, documentPosition, ediMetadata);
        }
        return dataReader;
    }
}
