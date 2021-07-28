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

import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.util.WriteableDataLocation;

import java.io.*;
import java.net.URI;

/**
 * The type Overflow writeable data location.
 */
public class OverflowWriteableDataLocation implements WriteableDataLocation {
    private static final long serialVersionUID = 2278635606454143014L;
    private static long maxMemory = 1024l * 1024l * 30;  //DEFALUT TO 30Mb
    private static long currentMemory = 0l;

    private boolean isClosed = false;

    private URI tmpUri;
    private OutputStream uriOut;

    private Buffer inMemoryBuffer = new Buffer();
    private String name;

    /**
     * Instantiates a new Overflow writeable data location.
     */
    public OverflowWriteableDataLocation() {
        this(null);
    }

    /**
     * Instantiates a new Overflow writeable data location.
     *
     * @param name the name
     */
    public OverflowWriteableDataLocation(String name) {
        if (currentMemory > maxMemory) {
            tmpUri = URIUtil.getTemporaryURI();
            uriOut = URIUtil.getOutputStream(tmpUri);
        }
        this.name = name;
    }

    private OverflowWriteableDataLocation(String name, URI tmpUri) {
        this.name = name;
        this.tmpUri = tmpUri;
        uriOut = URIUtil.getOutputStream(tmpUri);
    }

    private OverflowWriteableDataLocation(String name, Buffer inMemoryBuffer) {
        this.name = name;
        this.inMemoryBuffer = inMemoryBuffer;
    }

    /**
     * Sets the maximum amount of data to store in memory, in kb
     *
     * @param maxMemory the max memory
     */
    public static void setMaxMemoryKb(long maxMemory) {
        OverflowWriteableDataLocation.maxMemory = maxMemory * 1024l;
    }

    /**
     * Returns the current memory usage
     *
     * @return current memory
     */
    public static long getCurrentMemory() {
        return currentMemory;
    }

    /**
     * Gets max memory.
     *
     * @return the max memory
     */
    public static long getMaxMemory() {
        return maxMemory;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public InputStream getInputStream() {
        if (uriOut == null) {
            return new ByteArrayInputStream(inMemoryBuffer.toByteArray());
        }
        try {
            uriOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return URIUtil.getInputStream(tmpUri);
    }

    @Override
    public OutputStream getOutputStream() {
        return inMemoryBuffer;
    }

    @Override
    public void close() {
        if (isClosed) {
            return;
        }
        isClosed = true;
        if (uriOut != null) {
            URIUtil.closeUri(tmpUri);
            URIUtil.deleteUri(tmpUri);
        } else if (inMemoryBuffer != null) {
            currentMemory -= inMemoryBuffer.getCount();
            inMemoryBuffer = null;
        }
    }

    @Override
    public boolean isClosed() {
        return isClosed;
    }

    @Override
    public String toString() {
        if (tmpUri == null) {
            return new String(inMemoryBuffer.toByteArray());
        }
        return "OverflowWriteableDataLocation: " + tmpUri.toString();
    }

    @Override
    public OverflowWriteableDataLocation copy() {
        if (this.tmpUri != null) {
            // Copy the contents of the URI since the close method deletes the original URI on close.
            URI uriCopy = URIUtil.getTemporaryURI();
            URIUtil.copyURIs(this.tmpUri, uriCopy);
            return new OverflowWriteableDataLocation(name, uriCopy);
        } else {
            Buffer newBuffer = new Buffer();
            StreamUtil.copyStream(getInputStream(), newBuffer);
            return new OverflowWriteableDataLocation(name, newBuffer);
        }
    }

    private class Buffer extends ByteArrayOutputStream {
        private boolean isOverflow;

        @Override
        public synchronized void write(byte[] b, int off, int len) {
            if (isOverflow) {
                try {
                    uriOut.write(b, off, len);
                } catch (IOException e) {
                    throw new SdmxException(e, "IO Exception whilst trying to write to file output stream: " + tmpUri);
                }
            } else {
                super.write(b, off, len);
                checkOverflow(len);
            }
        }

        /**
         * Gets count.
         *
         * @return the count
         */
        public int getCount() {
            return count;
        }

        @Override
        public void write(int b) {
            if (isOverflow) {
                try {
                    uriOut.write(b);
                } catch (IOException e) {
                    throw new SdmxException(e, "IO Exception whilst trying to write to file output stream: " + tmpUri);
                }
            } else {
                super.write(b);
                checkOverflow(1);
            }
        }

        private void checkOverflow(int increment) {
            currentMemory += increment;
            if (currentMemory >= maxMemory) {
                isOverflow = true;
                //Copy over data to file
                if (tmpUri == null) {
                    //Create an output file
                    tmpUri = URIUtil.getTemporaryURI();
                    uriOut = URIUtil.getOutputStream(tmpUri);
                }
                try {
                    this.writeTo(uriOut);
                } catch (IOException e) {
                    throw new SdmxException(e, "IO Exception whilst trying to overflow from in memory outputstream to file output stream:" + tmpUri);
                }
                currentMemory -= super.count;
                super.buf = new byte[100];
                super.count = 0;
            }
        }

        @Override
        public void flush() throws IOException {
            if (uriOut != null) {
                uriOut.flush();
            }
        }
    }
}