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
package org.sdmxsource.sdmx.structureparser.engine;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;

import java.net.URI;

/**
 * Splits a structure document into sub documents
 */
public interface SdmxStructureDocumentSplitter {

    /**
     * Splits an SDMX Structure Document into 2 documents, the first containing a structure document with all structures that are not included in the second
     * argument (splitTypes) the second document containing all the structures in splitTypes.  Both documents will contain the same header information.  If
     * there are no structures in the input document that match the split types, then the returned array will be of size 1.
     *
     * @param uri        the uri
     * @param splitTypes the split types
     * @return readable data location [ ]
     */
    ReadableDataLocation[] splitDocument(URI uri, SDMX_STRUCTURE_TYPE... splitTypes);
}
