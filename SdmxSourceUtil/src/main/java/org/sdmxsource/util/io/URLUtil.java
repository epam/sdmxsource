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
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxServiceUnavailableException;
import org.sdmxsource.sdmx.api.exception.SdmxUnauthorisedException;
import org.sdmxsource.util.ObjectUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.zip.GZIPInputStream;

/**
 * The type Url util.
 */
public class URLUtil {
    private static final Logger LOG = LogManager.getLogger(URLUtil.class);

    /**
     * Opens an input stream to the URL, accepts GZIP encoding.
     *
     * @param url the url
     * @return input stream
     */
    public static InputStream getInputStream(URL url) {
        LOG.debug("Get Input Stream from URL: " + url);
        URLConnection connection = getconnection(url);
        return getInputStream(connection, null);
    }

    private static URLConnection getconnection(URL url) {
        LOG.debug("Get URLConnection: " + url);
        // Make connection, use post mode, and send query
        URLConnection urlc;
        try {
            urlc = url.openConnection();
        } catch (IOException e) {
            throw new SdmxServiceUnavailableException(e, ExceptionCode.WEB_SERVICE_BAD_CONNECTION, url.toString());
        }
        urlc.setDoOutput(true);
        urlc.setAllowUserInteraction(false);
        urlc.addRequestProperty("Accept-Encoding", "gzip");
        return urlc;
    }

    private static InputStream getInputStream(URLConnection urlc, Object payload) {
        InputStream stream;
        try {
            if (payload != null) {
                //Send Payload
                PrintStream ps = new PrintStream(urlc.getOutputStream());
                ps.print(payload);
                ps.close();
            }
            stream = getInputStream(urlc);
        } catch (IOException e) {
            throw new SdmxServiceUnavailableException(e, ExceptionCode.WEB_SERVICE_BAD_CONNECTION, e.getMessage());
        }
        if (urlc.getContentEncoding() != null && urlc.getContentEncoding().equals("gzip")) {
            LOG.debug("Response received as GZIP");
            try {
                stream = new GZIPInputStream(stream);
            } catch (IOException e) {
                throw new SdmxException(e, "I/O Ecception while trying to unzip stream retrieved from service:" + urlc.getURL());
            }
        }
        return stream;
    }

    private static InputStream getInputStream(URLConnection urlc) {
        try {
            return urlc.getInputStream();
        } catch (ConnectException c) {
            throw new SdmxServiceUnavailableException(c, ExceptionCode.WEB_SERVICE_BAD_CONNECTION, urlc.getURL());
        } catch (SocketException c) {
            throw new SdmxServiceUnavailableException(c, ExceptionCode.WEB_SERVICE_BAD_CONNECTION, urlc.getURL());
        } catch (SocketTimeoutException c) {
            throw new SdmxServiceUnavailableException(c, ExceptionCode.WEB_SERVICE_SOCKET_TIMEOUT, urlc.getReadTimeout());
        } catch (IOException e) {
            if (urlc instanceof HttpURLConnection) {
                try {
                    if (((HttpURLConnection) urlc).getResponseCode() == 401) {
                        throw new SdmxUnauthorisedException(e.getMessage());
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                HttpURLConnection httpConnection = (HttpURLConnection) urlc;
                InputStream is = httpConnection.getErrorStream();
                if (is != null) {
                    return is;
                }
            }
            String message = null;
            if (e.getMessage().contains("Server returned HTTP response code:")) {
                String split = e.getMessage().split(":")[1];
                split = split.trim();
                split = split.substring(0, split.indexOf(" "));

                try {
                    int responseCode = Integer.parseInt(split);

                    switch (responseCode) {
                        case 400:
                            message = "Response Code 400 = The request could not be understood by the server due to malformed syntax";
                            break;
                        case 401:
                            message = "Response Code 401 = Authentication failure";
                            break;
                        case 403:
                            message = "Response Code 403 = The server understood the request, but is refusing to fulfill it";
                            break;
                        case 404:
                            message = "Response Code 404 = Page not found";
                            break;
                        //TODO Do others
                    }
                    System.out.println(responseCode);
                } catch (Throwable th) {
                    //DO NOTHING
                }
            }
            if (message != null) {
                throw new SdmxServiceUnavailableException(e, ExceptionCode.WEB_SERVICE_BAD_CONNECTION, message);
            } else {
                throw new SdmxServiceUnavailableException(e, ExceptionCode.WEB_SERVICE_BAD_CONNECTION, urlc.getURL());
            }
        }
    }

    /**
     * Returns a URL from a String, throws a SdmxException if the URL String is not a valid URL
     *
     * @param urlStr the url str
     * @return url url
     */
    public static URL getURL(String urlStr) {
        try {
            return new URL(urlStr);
        } catch (MalformedURLException e) {
            throw new SdmxException("Malformed URL: " + urlStr);
        }
    }

    /**
     * Returns true if the HTTP Status is any of the following values:
     * <ul>
     * <li> 1xx - Informational</li>
     * <li> 2xx - Successful</li>
     * <li> 3xx - Redirection</li>
     * <li> 401 Unauthorized</li>
     * <li> 402 Payment Required</li>
     * <li> 405 Method Not Allowed</li>
     * <li> 407 Proxy Authentication Required </li>
     * </ul>
     *
     * @param url the url
     * @return true if the HTTP Status.
     */
    public static boolean urlExists(URL url) {
        try {
            HttpURLConnection.setFollowRedirects(false);
            // note : you may also need
            //        HttpURLConnection.setInstanceFollowRedirects(false)
            HttpURLConnection con =
                    (HttpURLConnection) url.openConnection();
            con.setRequestMethod("HEAD");

            // Check the HTTP response code
            String responseCodeStr = Integer.toString(con.getResponseCode());
            if (responseCodeStr.startsWith("1") ||
                    responseCodeStr.startsWith("2") ||
                    responseCodeStr.startsWith("3") ||
                    responseCodeStr.equals("401") ||
                    responseCodeStr.equals("402") ||
                    responseCodeStr.equals("405") ||
                    responseCodeStr.equals("407")) {
                return true;
            }

            LOG.warn("URL " + url + " returns status code: " + con.getResponseCode());
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Tests if the current url has been encoded.
     * <br>
     * This will return true even if one char in the URl is encoded. it will not check if the whole URL has been encoded correctly
     * <br>
     * For example, the url "http://www.example.com/some url after slash" then this would return false, but for the url "http://www.example.com/some%20url%20after%20slash" it would return true.<br>
     * however this "http://www.example.com/some url after%20slash" would also return true.
     *
     * @param url     the url
     * @param charSet -  the decoding charset to use. example "UTF-8"
     * @return true even if one char in the URl is encoded. it will not check if the whole URL has been encoded correctly
     * @throws UnsupportedEncodingException - If character encoding needs to be consulted, but named character encoding is not supported
     */
    public static boolean isUrlEncoded(String url, String charSet) throws UnsupportedEncodingException {
        return !url.equals(URLDecoder.decode(url, charSet));
    }

    /**
     * <p>
     * Given a URL of type String, this method will make sure that HTTP or HTTPS has been places at the start.
     * <p>
     * For example. if url is "localhost:8080/FusionRegistry", then this method will concatenate http to the start.
     * <p>
     * If the URL has https or http at the start, then it will just return it back
     * <p>
     *
     * @param url the url
     * @return given a URL of type String, this method will make sure that HTTP or HTTPS has been places at the start.
     */
    public static String enforceHttpProtocol(String url) {
        if (!ObjectUtil.validString(url)) {
            return url;
        }
        url = url.trim();
        if (!(url.startsWith("http") || url.startsWith("https"))) {
            url = "http://" + url;
        }
        return url;

    }

}
