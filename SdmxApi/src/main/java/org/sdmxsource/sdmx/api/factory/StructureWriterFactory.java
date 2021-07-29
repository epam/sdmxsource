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
package org.sdmxsource.sdmx.api.factory;

import org.sdmxsource.sdmx.api.engine.StructureWriterEngine;
import org.sdmxsource.sdmx.api.model.format.StructureFormat;

import java.io.OutputStream;

/**
 * A StructureWriterFactory operates as a plugin to a Manager which can request a StructureWritingEngine, to which the implementation will
 * respond with an appropriate StructureWritingEngine if it is able, otherwise it will return null
 */
public interface StructureWriterFactory {

    /**
     * Obtains a StructureWritingEngine engine for the given output format
     *
     * @param outputFormat an implementation of the StructureFormat to describe the output format for the structures (required)
     * @param out          the output stream to write to (can be null if it is not required)
     * @return null if this factory is not capable of creating a data writer engine in the requested format
     * @throws IllegalArgumentException if the outputFormat is null
     */
    StructureWriterEngine getStructureWriterEngine(StructureFormat outputFormat, OutputStream out);
}
