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
package org.sdmxsource.util.io;

import org.sdmxsource.sdmx.api.util.ReadableDataLocation;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * The type In memory readable data location.
 */
public class InMemoryReadableDataLocation implements ReadableDataLocation {
    private static final long serialVersionUID = 971633373372917354L;
    private byte[] bytes;
    private String name;
    private boolean isClosed = false;

    /**
     * Instantiates a new In memory readable data location.
     *
     * @param bytes the bytes
     */
    public InMemoryReadableDataLocation(byte[] bytes) {
        this(bytes, null);
    }

    /**
     * Instantiates a new In memory readable data location.
     *
     * @param bytes the bytes
     * @param name  the name
     */
    public InMemoryReadableDataLocation(byte[] bytes, String name) {
        this.bytes = bytes;
        this.name = name;
    }

    /**
     * Instantiates a new In memory readable data location.
     *
     * @param uriStr the uri str
     */
    public InMemoryReadableDataLocation(String uriStr) {
        if (uriStr == null) {
            throw new IllegalArgumentException("Can not create StreamSourceData - uriStr can not be null");
        }
        name = uriStr;
        try {
            URI uri = new URI(uriStr);
            if (uri.isAbsolute()) {
                if (!uri.getScheme().equals("file")) {
                    URL url = uri.toURL();
                    bytes = StreamUtil.toByteArray(URLUtil.getInputStream(url));
                } else {
                    bytes = FileUtil.readFileAsString(uri.getPath()).getBytes();
                }
            } else {
                bytes = StreamUtil.toByteArray(URIUtil.getInputStream(uri));
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(bytes);
    }

    @Override
    public void close() {
        if (isClosed) {
            return;
        }
        isClosed = true;
        this.bytes = null;
    }

    @Override
    public boolean isClosed() {
        return isClosed;
    }

    @Override
    public InMemoryReadableDataLocation copy() {
        return new InMemoryReadableDataLocation(bytes, name);
    }
}