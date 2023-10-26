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

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Stream util.
 */
public class StreamUtil {
    private static final Logger LOG = LoggerFactory.getLogger(StreamUtil.class);


    /**
     * Returns the byte[] as a Stream
     *
     * @param bytesList the bytes list
     * @return input stream
     */
    public static InputStream asStream(List<byte[]> bytesList) {
        int length = 0;
        for (byte[] currentArray : bytesList) {
            length += currentArray.length;
        }
        byte[] merged = new byte[length];
        length = 0;
        for (byte[] currentArray : bytesList) {
            System.arraycopy(currentArray, 0, merged, length, currentArray.length);
            length += currentArray.length;
        }
        return new ByteArrayInputStream(merged);
    }

    /**
     * Splits the input byte[] into a List of byte[] of length chunkLength
     *
     * @param list        the list
     * @param bytes       the bytes
     * @param chunkLength the chunk length
     * @param offset      the offset
     */
    public static void splitBytes(List<byte[]> list, byte[] bytes, int chunkLength, int offset) {
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

    //HACK - THIS IS FOR INPUT STREAMS THAT ARE NOT UTF*, BUT IF THE STREAM IS UTF- THIS METHOD BREAKS THE ENCODING
    //BY CONVERTING AN INT TO A CHAR
    private static void copyNonUTF8InputStream(InputStream is, OutputStream os) {
        Writer bos = null;
        BufferedInputStream bis = null;
        try {

            bis = new BufferedInputStream(is);
            bos = new OutputStreamWriter(new BufferedOutputStream(os), "UTF-8");

            byte[] bytes = new byte[1024];
            int i;
            while ((i = bis.read(bytes)) > 0) {
                bos.write(new String(bytes, 0, i));
            }
            bos.flush();

        } catch (Throwable th) {
            throw new RuntimeException(th);
        } finally {
            try {
                is.close();
                os.flush();
                os.close();
                if (bos != null) {
                    bos.close();
                }
                if (bis != null) {
                    bis.close();
                }
            } catch (Throwable th) {
                throw new RuntimeException(th);
            }
        }
    }

    /**
     * Copies the first 'x' number of lines to a list, each list element represents a line.
     *
     * @param stream   the stream
     * @param numLines the num lines
     * @return list list
     */
    public static List<String> copyFirstXLines(InputStream stream, int numLines) {
        ArrayList<String> firstXRows = new ArrayList<String>();
        int rowCount = 0;
        boolean lastCharWasEndOfLine = false;

        StringBuffer buff = new StringBuffer();
        byte[] bytes = new byte[1024];
        int i;
        boolean keepProcessing = true;
        try {
            while ((i = stream.read(bytes)) > 0 && keepProcessing) {
                for (int j = 0; j < i; j++) {
                    byte aByte = bytes[j];
                    if (aByte == 0x0A || aByte == 0x0D) {
                        if (!lastCharWasEndOfLine) {
                            lastCharWasEndOfLine = true;
                            rowCount++;
                            firstXRows.add(buff.toString());
                            buff = new StringBuffer();
                        }
                    } else {
                        lastCharWasEndOfLine = false;
                        buff.append((char) aByte);
                    }

                    if (rowCount == numLines) {
                        keepProcessing = false;
                        break;
                    }
                }
            }
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
        return firstXRows;
    }


    /**
     * Copies the supplied InputStream to the supplied OutputStream.
     * Converts the OutputStream to UTF-8.
     * Both streams are closed on completion, uses a buffer of 1Kb
     *
     * @param is the InputStream to copy.
     * @param os the OutputStream to write to.
     */
    public static void copyStream(InputStream is, OutputStream os) {
        copyUTF8InputStream(is, os, false);
    }

    /**
     * Copy stream.
     *
     * @param is                the is
     * @param os                the os
     * @param closeOuptutStream the close ouptut stream
     */
    public static void copyStream(InputStream is, OutputStream os, boolean closeOuptutStream) {
        copyUTF8InputStream(is, os, closeOuptutStream);
    }

    private static void copyUTF8InputStream(InputStream is, OutputStream os, boolean closeOuptutStream) {
        LOG.debug("Copy Input Stream:" + is + " to output stream:" + os + ", close output stream on completion=" + closeOuptutStream);
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        try {
            if (is instanceof BufferedInputStream) {
                bis = (BufferedInputStream) is;
            } else {
                bis = new BufferedInputStream(is);
            }
            if (os instanceof BufferedOutputStream) {
                bos = (BufferedOutputStream) os;
            } else {
                bos = new BufferedOutputStream(os);
            }

            byte[] bytes = new byte[1024];
            int i;
            while ((i = bis.read(bytes)) > 0) {
                bos.write(bytes, 0, i);
            }
            bos.flush();

        } catch (Throwable th) {
            th.printStackTrace();
            throw new RuntimeException(th);
        } finally {
            try {
                StreamUtil.closeStream(bis);
                if (bos != null) {
                    bos.flush();
                }
                if (os != null) {
                    os.flush();
                }
                if (closeOuptutStream) {
                    StreamUtil.closeStream(bos);
                }
            } catch (Throwable th) {
                throw new RuntimeException(th);
            }
        }
    }


    /**
     * Create a byte[] from the supplied InputStream.
     * The InputStream is closed after use.
     *
     * @param is the is
     * @return the byte [ ]
     */
    public static byte[] toByteArray(InputStream is) {
        ByteArrayOutputStream byteOs = null;
        BufferedOutputStream bos = null;
        try {
            byteOs = new ByteArrayOutputStream();
            bos = new BufferedOutputStream(byteOs);

            byte[] bytes = new byte[1024];
            int i;
            while ((i = is.read(bytes)) > 0) {
                bos.write(bytes, 0, i);
            }
            bos.flush();

            return byteOs.toByteArray();
        } catch (Throwable th) {
            throw new RuntimeException(th);
        } finally {
            try {
                is.close();
                if (byteOs != null) {
                    byteOs.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (Throwable th) {
                throw new RuntimeException(th);
            }
        }
    }


    /**
     * Closes all of the supplied OutputStreams.
     *
     * @param out the OutputStream
     */
    public static void closeStream(OutputStream... out) {
        if (out == null) {
            return;
        }
        for (OutputStream currentOut : out) {
            try {
                currentOut.close();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    /**
     * Closes all of the supplied InputStreams.
     *
     * @param in the InputStream
     */
    public static void closeStream(InputStream... in) {
        if (in == null) {
            return;
        }
        for (InputStream currentIn : in) {
            try {
                currentIn.close();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }
}
