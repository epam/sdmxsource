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
package org.sdmxsource.sdmx.api.util;

import java.io.InputStream;
import java.io.Serializable;

/**
 * ReadableDataLocation is capable of reading and re-reading the same source of data many times
 */
public interface ReadableDataLocation extends Serializable {

    /**
     * This method is guaranteed to return a new InputStream on each method call.
     * The InputStream will be reading the same underlying data source.
     *
     * @return a new InputStream
     */
    InputStream getInputStream();

    /**
     * If this ReadableDataLocation originated from a file, then this will be the original file name,
     * regardless of where the stream is actually held.
     * This method may return null if the name is not relevant.
     *
     * @return the name or null.
     */
    String getName();

    /**
     * Closes (and removes if appropriate) any resources that are held open
     */
    void close();

    /**
     * Returns whether this ReadableDataLocation is closed or not.
     *
     * @return true if closed
     */
    boolean isClosed();

    /**
     * Returns a copy of this ReadableDataLocation.
     *
     * @return a new copy of this object.
     */
    ReadableDataLocation copy();
}