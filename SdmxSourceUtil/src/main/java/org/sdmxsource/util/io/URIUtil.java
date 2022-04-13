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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sdmxsource.util.ObjectUtil;
import org.sdmxsource.util.log.LoggingUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.*;


/**
 * The type Uri util.
 */
public class URIUtil {
    //SAME FOR ALL INSTANCES
    private static Logger log = LogManager.getLogger(URIUtil.class);
    private static URIUtil TEMPORARY_URI_UTIL = new URIUtil("resources/streams/tmp", "tmpFile", 1440000, true); //DELETE FILES ONE DAY OLD
    private static Set<String> uris = new HashSet<String>();

    private static Map<URI, List<OutputStream>> outputstreamMap = new HashMap<URI, List<OutputStream>>();
    private static Map<URI, List<InputStream>> inputstreamMap = new HashMap<URI, List<InputStream>>();

    //INSTANCE SPECIFIC
    private String fileBaseName = "tmp_file.";
    //	private String uriDirectory = "resources/streams/tmp";
    //	private ScheduledTimerTask task;
    //	private long deleteFilesOlderThen;
    //	private boolean overwright = false;

    private URIUtil(String uriDirectory, String fileBaseName, long deleteFilesOlderThen, boolean overwright) {
        //		this.deleteFilesOlderThen = deleteFilesOlderThen;
        //		if(deleteFilesOlderThen > 0) {
        //			task = new ScheduledTimerTask();
        //			task.setDelay(deleteFilesOlderThen / 2);
        //			task.setPeriod(deleteFilesOlderThen);
        //			task.setRunnable(new FileDeleter());
        //		}
        //		this.uriDirectory = uriDirectory;
        this.fileBaseName = fileBaseName;
        //		this.overwright = overwright;
    }

    /**
     * Creates a formatted string to be used for a URI
     *
     * @param input the input
     * @return string string
     */
    public static String formatStringForURI(String input) {
        return input.replaceAll(" ", "%20").replaceAll("\\\\", "/");
    }

    /**
     * Returns a URIUtil class which creates temporary URI locations for storing files.
     * <p>
     * Files which have been created through this which have not been modified for 24 hours, are deleted.
     *
     * @return uri util
     */
    public static URIUtil getURIUtil() {
        return TEMPORARY_URI_UTIL;

    }

    /**
     * Returns a URI which will be deleted after a period of inactivity.
     * URIs obtained through this method which have not been modified within a 24 hours period will be deleted.
     *
     * @return a URI
     */
    public static URI getTemporaryURI() {
        URI uri = TEMPORARY_URI_UTIL.getUri();
        return uri;
    }

    /**
     * Gets uri util.
     *
     * @param directoryName the directory name
     * @param fileBaseName  the file base name
     * @param overwrite     the overwrite
     * @return the uri util
     */
    public static URIUtil getURIUtil(String directoryName, String fileBaseName, boolean overwrite) {
        return new URIUtil(directoryName, fileBaseName, 0, overwrite);
    }

    /**
     * Close uri.
     *
     * @param uri the uri
     */
    public static void closeUri(URI uri) {
        if (uri != null) {
            uris.remove(uri.toString());
        }
    }

    /**
     * Gets full path.
     *
     * @param uri the uri
     * @return the full path
     */
    public static String getFullPath(URI uri) {
        File file = new File(uri.getPath());
        return file.getAbsolutePath();
    }

    /**
     * Delete uri boolean.
     *
     * @param uri the uri
     * @return the boolean
     */
    public static boolean deleteUri(URI uri) {
        if (uri == null) {
            return false;
        }
        if (outputstreamMap.containsKey(uri)) {
            for (OutputStream out : outputstreamMap.get(uri)) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
        if (inputstreamMap.containsKey(uri)) {
            for (InputStream in : inputstreamMap.get(uri)) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        outputstreamMap.remove(uri);
        inputstreamMap.remove(uri);
        File file = new File(uri.getPath());
        boolean deleted = file.delete();

        /*
         * DEBUG Code useful for when tracking down issues
         */
        //		if(!deleted) {
        //			Throwable t = new Throwable();
        //			StackTraceElement[] elements = t.getStackTrace();

        //			String callerMethodName = elements[1].getMethodName();
        //			String callerClassName = elements[1].getClassName();
        //			LoggingUtil.error(log, "file not deleted: " + uri.getPath());
        //			LoggingUtil.error(log, "DELETE FILE FAILED CALLED FROM [CLASS, METHOD, METHOD NAME]:" + callerClassName + ", " + callerMethodName);
        //			throw new RuntimeException("Unable to delete file : " + uri.getPath());
        //		}
        closeUri(uri);
        return deleted;
    }

    /**
     * Copy ur is.
     *
     * @param uriIn  the uri in
     * @param uriOut the uri out
     */
    public static void copyURIs(URI uriIn, URI uriOut) {
        try {
            StreamUtil.copyStream(new FileInputStream(uriIn.getPath()), new FileOutputStream(uriOut.getPath()), true);
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    /**
     * Gets input stream.
     *
     * @param uri the uri
     * @return the input stream
     */
    public static InputStream getInputStream(URI uri) {
        String filePath;
        InputStream stream;
        try {
            URL url = uri.toURL();
            stream = URLUtil.getInputStream(url);
        } catch (Throwable th) {
            if (ObjectUtil.validString(uri.getPath())) {
                filePath = uri.getPath();
            } else {
                filePath = uri.getSchemeSpecificPart();
            }
            try {
                stream = new BufferedInputStream(new FileInputStream(filePath));
            } catch (FileNotFoundException e) {
                Resource mr = new ClassPathResource(filePath);
                try {
                    stream = mr.getInputStream();
                } catch (IOException e1) {
                    throw new RuntimeException("Could not load resource: " + filePath);
                }
            }
        }
        storeStream(uri, stream);
        return stream;
    }

    private static void storeStream(URI uri, InputStream stream) {
        List<InputStream> streams = null;
        if (inputstreamMap.containsKey(uri)) {
            streams = inputstreamMap.get(uri);
        } else {
            streams = new ArrayList<InputStream>();
            inputstreamMap.put(uri, streams);
        }
        streams.add(stream);
    }

    /**
     * Gets output stream.
     *
     * @param uri the uri
     * @return the output stream
     */
    public static OutputStream getOutputStream(URI uri) {
        try {
            OutputStream out = new BufferedOutputStream(new FileOutputStream(uri.getPath(), true));
            List<OutputStream> streams = null;
            if (outputstreamMap.containsKey(uri)) {
                streams = outputstreamMap.get(uri);
            } else {
                streams = new ArrayList<OutputStream>();
                outputstreamMap.put(uri, streams);
            }
            streams.add(out);
            return out;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get byte array byte [ ].
     *
     * @param uri the uri
     * @return the byte [ ]
     */
    public static byte[] getByteArray(URI uri) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        StreamUtil.copyStream(getInputStream(uri), bos);
        return bos.toByteArray();
    }

    /**
     * Gets file.
     *
     * @param uri the uri
     * @return the file
     */
    public static File getFile(URI uri) {
        return new File(uri.getPath());
    }

    /**
     * Gets uri.
     *
     * @return the uri
     */
    public synchronized URI getUri() {
        try {
            File f = FileUtil.createTemporaryFile(fileBaseName, "sdmxsource_tmp");

            URI uri = f.toURI();

            if (!uris.contains(uri.toString())) {
                uris.add(uri.toString());
            }

            if (log.isDebugEnabled()) {
                Throwable t = new Throwable();
                StackTraceElement[] elements = t.getStackTrace();

                String callerMethodName = null;
                String callerClassName = null;
                for (int x = 0; x < elements.length; x++) {
                    callerMethodName = elements[x].getMethodName();
                    callerClassName = elements[x].getClassName();
                    if (!callerClassName.equals(URIUtil.class.getName())) {
                        break;
                    }
                }

                LoggingUtil.debug(log, "getTemporaryURI request from (class.method)" + callerClassName + "." + callerMethodName);
                LoggingUtil.debug(log, "URI created " + uri.getPath());
            }

            return uri;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Copies the byte array into the returned URI
     *
     * @param bytes the bytes
     * @return uri uri
     */
    public URI getUri(byte[] bytes) {
        URI uri = getUri();
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(uri.getPath()));
            bos.write(bytes, 0, bytes.length);
        } catch (Throwable th) {
            throw new RuntimeException(th);
        } finally {
            try {
                if (bos != null) {
                    bos.flush();
                    bos.close();
                }
            } catch (Throwable th) {
                throw new RuntimeException(th);
            }
        }
        return uri;
    }

    /**
     * Copies the input stream into a temporary file location that can be discovered with the returned URI
     *
     * @param stream the stream
     * @return uri uri
     */
    public URI getUri(InputStream stream) {
        URI uri = getUri();
        try {
            StreamUtil.copyStream(stream, new BufferedOutputStream(new FileOutputStream(uri.getPath())));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return uri;
    }
}
