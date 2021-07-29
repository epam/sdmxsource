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
package org.sdmxsource.sdmx.dataparser.model;

import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;

import java.io.OutputStream;

/**
 * Contains information required for data parsing.
 */
public class DataParseMetadata {
    private ReadableDataLocation sourceData;
    private OutputStream out;
    private DataStructureBean dataStructure;
    private SDMX_SCHEMA outputSchemaVersion;

    /**
     * Instantiates a new Data parse metadata.
     *
     * @param sourceData          the source data
     * @param out                 the out
     * @param outputSchemaVersion the output schema version
     * @param keyFamily           the key family
     */
    public DataParseMetadata(ReadableDataLocation sourceData,
                             OutputStream out,
                             SDMX_SCHEMA outputSchemaVersion,
                             DataStructureBean keyFamily) {
        this.sourceData = sourceData;
        this.out = out;
        this.dataStructure = keyFamily;
        this.outputSchemaVersion = outputSchemaVersion;
    }

    /**
     * Gets source data.
     *
     * @return the source data
     */
    public ReadableDataLocation getSourceData() {
        return sourceData;
    }

    /**
     * Gets out.
     *
     * @return the out
     */
    public OutputStream getOut() {
        return out;
    }

    /**
     * Gets data structure.
     *
     * @return the data structure
     */
    public DataStructureBean getDataStructure() {
        return dataStructure;
    }

    /**
     * Gets output schema version.
     *
     * @return the output schema version
     */
    public SDMX_SCHEMA getOutputSchemaVersion() {
        return outputSchemaVersion;
    }
}
