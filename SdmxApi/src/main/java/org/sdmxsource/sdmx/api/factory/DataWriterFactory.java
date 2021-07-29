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

import org.sdmxsource.sdmx.api.engine.DataWriterEngine;
import org.sdmxsource.sdmx.api.model.data.DataFormat;

import java.io.OutputStream;

/**
 * A DataWriterFactory operates as a plugin to a manager which can request a DataWriterEngine, to which the implementation will
 * respond with an appropriate DataWriterEngine if it is able, otherwise it will return null
 */
public interface DataWriterFactory {

    /**
     * Obtains a data writer engine for the given data type
     *
     * @param dataFormat used to describe the format to write the data in (and potentially contain extra information in the implemenation)
     * @param out        the output stream to write to (can be null if this is not required, i.e the DataFormat may contain a writer)
     * @return null if this factory is not capable of creating a data writer engine in the requested format
     */
    DataWriterEngine getDataWriterEngine(DataFormat dataFormat, OutputStream out);
}
