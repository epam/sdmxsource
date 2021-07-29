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
package org.sdmxsource.sdmx.sdmxbeans.model;

import org.sdmxsource.sdmx.api.constants.STRUCTURE_OUTPUT_FORMAT;
import org.sdmxsource.sdmx.api.model.format.StructureFormat;

/**
 * The type Sdmx structure format.
 */
public class SdmxStructureFormat implements StructureFormat {
    private static final long serialVersionUID = -8780344285791826634L;
    private STRUCTURE_OUTPUT_FORMAT structureFormat;

    /**
     * Instantiates a new Sdmx structure format.
     *
     * @param structureFormat the structure format
     */
    public SdmxStructureFormat(STRUCTURE_OUTPUT_FORMAT structureFormat) {
        if (structureFormat == null) {
            throw new IllegalArgumentException("STRUCTURE_OUTPUT_FORMAT can not be null");
        }
        this.structureFormat = structureFormat;
    }

    @Override
    public STRUCTURE_OUTPUT_FORMAT getSdmxOutputFormat() {
        return structureFormat;
    }

    @Override
    public String toString() {
        return structureFormat.toString();
    }

    @Override
    public String getFormatAsString() {
        return toString();
    }
}
