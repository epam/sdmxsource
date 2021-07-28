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

import org.sdmxsource.sdmx.api.util.ReadableDataLocation;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

/**
 * Used to create ReadableDataLocations from various sources of information
 */
public interface ReadableDataLocationFactory {


    /**
     * Create a readable data location from a String, this may represent a URI (file or URL)
     *
     * @param uriStr the uri str
     * @return readable data location
     */
    ReadableDataLocation getReadableDataLocation(String uriStr);

    /**
     * Gets readable data location.
     *
     * @param bytes the bytes
     * @return the readable data location
     */
    ReadableDataLocation getReadableDataLocation(byte[] bytes);

    /**
     * Gets readable data location.
     *
     * @param file the file
     * @return the readable data location
     */
    ReadableDataLocation getReadableDataLocation(File file);

    /**
     * Gets readable data location.
     *
     * @param url the url
     * @return the readable data location
     */
    ReadableDataLocation getReadableDataLocation(URL url);

    /**
     * Gets readable data location.
     *
     * @param uri the uri
     * @return the readable data location
     */
    ReadableDataLocation getReadableDataLocation(URI uri);

    /**
     * Gets readable data location.
     *
     * @param is the is
     * @return the readable data location
     */
    ReadableDataLocation getReadableDataLocation(InputStream is);


}
