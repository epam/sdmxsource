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

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Serialize util.
 *
 * @param <T> the type parameter
 */
public class SerializeUtil<T extends Serializable> {

    /**
     * Serialize as chunked byte array list.
     *
     * @param serializable the serializable
     * @param chunkLength  the chunk length
     * @return the list
     */
    public List<byte[]> serializeAsChunkedByteArray(T serializable, int chunkLength) {
        ByteArrayOutputStream serialized = serializeAsByteArray(serializable);
        List<byte[]> byteList = new ArrayList<byte[]>();
        splitBytes(byteList, serialized.toByteArray(), chunkLength, 0);
        return byteList;
    }

    private void splitBytes(List<byte[]> list, byte[] bytes, int chunkLength, int offset) {
        int lengthTocopy;
        if ((bytes.length - offset) > chunkLength) {
            lengthTocopy = chunkLength;
        } else {
            lengthTocopy = bytes.length - offset;
        }
        byte[] dest = new byte[lengthTocopy];
        System.arraycopy(bytes, offset, dest, 0, lengthTocopy);
        list.add(dest);
        if ((bytes.length - offset) > chunkLength) {
            splitBytes(list, bytes, chunkLength, offset + lengthTocopy);
        }
    }

    /**
     * De serialize chunked byte array t.
     *
     * @param buffer the buffer
     * @return the t
     */
    public T deSerializeChunkedByteArray(List<byte[]> buffer) {
        int length = 0;
        for (byte[] currentArray : buffer) {
            length += currentArray.length;
        }
        byte[] merged = new byte[length];
        length = 0;
        for (byte[] currentArray : buffer) {
            System.arraycopy(currentArray, 0, merged, length, currentArray.length);
            length += currentArray.length;
        }
        InputStream is = new ByteArrayInputStream(merged);
        return deSerialize(is);
    }

    /**
     * Serialize to output stream.
     *
     * @param serializable the serializable
     * @param output       the output
     */
    public void serializeToOutputStream(T serializable, OutputStream output) {
        ObjectOutputStream obj_out = null;
        try {
            obj_out = new ObjectOutputStream(output);
            obj_out.writeObject(serializable);
        } catch (Throwable th) {
            throw new RuntimeException(th);
        } finally {
            closeStream(output);
            closeStream(obj_out);
        }
    }

    /**
     * Serialize as byte array byte array output stream.
     *
     * @param serializable the serializable
     * @return the byte array output stream
     */
    public ByteArrayOutputStream serializeAsByteArray(T serializable) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        serializeToOutputStream(serializable, output);
        return output;
    }

    /**
     * Serialize.
     *
     * @param filelocation the filelocation
     * @param serializable the serializable
     */
    public void serialize(URI filelocation, T serializable) {
        File f = URIUtil.getFile(filelocation);
        this.serialize(f, serializable);
    }

    /**
     * Serialize.
     *
     * @param fileName     the file name
     * @param serializable the serializable
     */
    public void serialize(String fileName, T serializable) {
        this.serialize(new File(fileName), serializable);
    }

    /**
     * Serialize.
     *
     * @param f            the f
     * @param Serializable the serializable
     */
    public void serialize(File f, T Serializable) {
        FileOutputStream f_out = null;
        ObjectOutputStream obj_out = null;
        try {
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();

            f_out = new FileOutputStream(f);
            obj_out = new ObjectOutputStream(f_out);
            obj_out.writeObject(Serializable);
        } catch (Throwable th) {
            System.out.println(th);
        } finally {
            closeStream(f_out);
            closeStream(obj_out);
        }
    }

    /**
     * De serialize t.
     *
     * @param inputStream the input stream
     * @return the t
     */
    @SuppressWarnings("unchecked")
    public T deSerialize(InputStream inputStream) {
        ObjectInputStream obj_in = null;
        try {
            // Read object using ObjectInputStream.
            obj_in = new ObjectInputStream(inputStream);

            // Read an object in
            Object obj = obj_in.readObject();

            return (T) obj;

        } catch (Throwable th) {
            throw new IllegalArgumentException(th);
        } finally {
            closeStream(inputStream);
            closeStream(obj_in);
        }
    }

    /**
     * De serialize t.
     *
     * @param filePath the file path
     * @return the t
     */
    @SuppressWarnings("unchecked")
    public T deSerialize(String filePath) {
        FileInputStream f_in = null;
        ObjectInputStream obj_in = null;
        if (!FileUtil.exists(filePath)) {
            return null;
        }
        try {
            f_in = new FileInputStream(filePath);

            // Read object using ObjectInputStream.
            obj_in = new ObjectInputStream(f_in);

            // Read an object in
            Object obj = obj_in.readObject();

            return (T) obj;

        } catch (Throwable th) {
            throw new IllegalArgumentException(th);
        } finally {
            closeStream(f_in);
            closeStream(obj_in);
        }
    }

    private void closeStream(OutputStream stream) {
        try {
            if (stream != null) {
                stream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeStream(InputStream stream) {
        try {
            if (stream != null) {
                stream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
