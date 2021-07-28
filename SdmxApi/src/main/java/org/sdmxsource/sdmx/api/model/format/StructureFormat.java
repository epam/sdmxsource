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
package org.sdmxsource.sdmx.api.model.format;

import org.sdmxsource.sdmx.api.constants.STRUCTURE_OUTPUT_FORMAT;

import java.io.Serializable;

/**
 * StructureFormat is an Interface which wrappers a strongly typed and non-extensible {@link STRUCTURE_OUTPUT_FORMAT} enumeration.  The purpose of the wrapper is to
 * allow for additional implementations to be provided which may describe non-sdmx formats which are not supported by the {@link STRUCTURE_OUTPUT_FORMAT} enum.
 */
public interface StructureFormat extends Serializable {
    /**
     * Returns the SDMX Structure Output Type that this interface is describing.
     * <p>
     * If this is not describing an SDMX message then this will return null and the implementation class will be expected to expose additional methods
     * to describe the output format
     *
     * @return sdmx output format, or null if this is not describing an SDMX output
     */
    STRUCTURE_OUTPUT_FORMAT getSdmxOutputFormat();

    /**
     * Returns a string representation of the format, that can be used for auditing and debugging purposes.
     * <p>
     * This is expected to return a not null response.
     *
     * @return format as string
     */
    String getFormatAsString();
}
