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

import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.ediparser.engine.reader.EDIStructureReaderEngine;
import org.sdmxsource.sdmx.ediparser.model.document.EDIDocumentPosition;
import org.sdmxsource.sdmx.ediparser.model.document.EDIMetadata;
import org.sdmxsource.sdmx.ediparser.model.document.EDIStructureDocument;
import org.sdmxsource.sdmx.ediparser.model.reader.EDIStructureReader;
import org.sdmxsource.sdmx.ediparser.model.reader.impl.EDIStructureReaderImpl;


/**
 * The type Edi structure document.
 */
public class EDIStructureDocumentImpl implements EDIStructureDocument {

    private final EDIStructureReaderEngine structureReaderEngine;

    private final EDIStructureReader structureReader;

    /**
     * Instantiates a new Edi structure document.
     *
     * @param structureReaderEngine the structure reader engine
     * @param documentURI           the document uri
     * @param documentPosition      the document position
     * @param ediMetadata           the edi metadata
     */
    public EDIStructureDocumentImpl(
            final EDIStructureReaderEngine structureReaderEngine,
            final ReadableDataLocation documentURI,
            final EDIDocumentPosition documentPosition,
            final EDIMetadata ediMetadata) {
        this.structureReaderEngine = structureReaderEngine;
        structureReader = new EDIStructureReaderImpl(documentURI, documentPosition, ediMetadata);
    }

    @Override
    public SdmxBeans getSdmxBeans() {
        if (structureReaderEngine == null) {
            throw new RuntimeException("'structureReaderEngine' is null");
        }
        try {
            return structureReaderEngine.createSdmxBeans(structureReader);
        } finally {
            structureReader.close();
        }
    }
}
