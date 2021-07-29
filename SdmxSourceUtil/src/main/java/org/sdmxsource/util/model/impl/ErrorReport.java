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
package org.sdmxsource.util.model.impl;

import org.sdmxsource.util.io.SerializeUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * The type Error report.
 */
public class ErrorReport implements Serializable {
    private static final long serialVersionUID = -5874227775868948616L;
    private static final SerializeUtil<ArrayList<String>> su = new SerializeUtil<ArrayList<String>>();
    private static final int CHUNK_SIZE = 1024;
    private Integer id;
    private String errorType;
    // This class stores error messages in 2 forms: 1 as a serializable byte[] and the other as human-readable strings.
    // Only the serializable byte[] is persistable.
    private List<byte[]> errorMessagesAsByteArray = new ArrayList<byte[]>();
    private ArrayList<String> errorMessage = new ArrayList<String>();
    private Throwable th;

    /**
     * Instantiates a new Error report.
     */
//DEFAULT CONSTRUCTOR
    public ErrorReport() {
        super();
    }

    private ErrorReport(Throwable e) {
        addErrorMessage(e);
        this.th = e;
        Collections.reverse(errorMessage);
        errorMessagesAsByteArray = su.serializeAsChunkedByteArray(errorMessage, CHUNK_SIZE);
    }

    private ErrorReport(String msg) {
        addErrorMessage(msg);
        Collections.reverse(errorMessage);
        errorMessagesAsByteArray = su.serializeAsChunkedByteArray(errorMessage, CHUNK_SIZE);
    }

    private ErrorReport(String msg, Throwable e) {
        addErrorMessage(msg, e);
        this.th = e;
        Collections.reverse(errorMessage);
        errorMessagesAsByteArray = su.serializeAsChunkedByteArray(errorMessage, CHUNK_SIZE);
    }

    /**
     * Build error report.
     *
     * @param e the e
     * @return the error report
     */
    public static ErrorReport build(Throwable e) {
        ErrorReport report = new ErrorReport(e);
        return report;
    }

    /**
     * Build error report.
     *
     * @param msg the msg
     * @return the error report
     */
    public static ErrorReport build(String msg) {
        ErrorReport report = new ErrorReport(msg);
        return report;
    }

    /**
     * Build error report.
     *
     * @param msg the msg
     * @return the error report
     */
    public static ErrorReport build(List<String> msg) {
        ErrorReport report = new ErrorReport();
        for (String currentMsg : msg) {
            report.addErrorMessage(currentMsg);
        }
        return report;
    }

    /**
     * Build error report.
     *
     * @param msg the msg
     * @param e   the e
     * @return the error report
     */
    public static ErrorReport build(String msg, Throwable e) {
        ErrorReport report = new ErrorReport(msg, e);
        return report;
    }

    /**
     * Returns the Throwable, if there is one
     *
     * @return throwable throwable
     */
    public Throwable getThrowable() {
        return th;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    private void addErrorMessage(Throwable e) {
        if (e != null) {
            String errorMessageStr = e.getMessage();
            if (errorMessageStr == null) {
                errorMessage.add("Null Pointer");
            } else {
                for (String currentError : errorMessageStr.split(System.getProperty("line.separator"))) {
                    errorMessage.add(currentError);
                }
            }
            addErrorMessage(e.getCause());
        }
    }

    private void addErrorMessage(String msg) {
        errorMessage.add(msg);
    }

    private void addErrorMessage(String msg, Throwable e) {
        if (e != null) {
            errorMessage.add(msg + ": " + e.getMessage());
            addErrorMessage(e.getCause());
        }
    }

    /**
     * Gets error type.
     *
     * @return the error type
     */
    public String getErrorType() {
        return errorType;
    }

    /**
     * Sets error type.
     *
     * @param errorType the error type
     */
    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    /**
     * Gets error message.
     *
     * @return the error message
     */
    public List<String> getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets error message.
     *
     * @param errorMessage the error message
     */
    public void setErrorMessage(List<String> errorMessage) {
        // This method is required for Flex, but should not have any implementation.
        // Setting the "error messages" should be done on the persisted "serialized error message" field which will set the "errorMessage" field.
    }

    /**
     * Gets serialized error message.
     *
     * @return the serialized error message
     */
    public List<byte[]> getSerializedErrorMessage() {
        return errorMessagesAsByteArray;
    }

    /**
     * Sets serialized error message.
     *
     * @param ba the ba
     */
    public void setSerializedErrorMessage(List<byte[]> ba) {
        errorMessagesAsByteArray = ba;

        if (errorMessagesAsByteArray.size() > 0) {
            errorMessage = su.deSerializeChunkedByteArray(ba);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < errorMessage.size(); i++) {
            if (i > 0) {
                sb.append(System.getProperty("line.separator"));
            }
            sb.append(errorMessage.get(i));
        }
        return sb.toString();
    }

    /**
     * Gets string for html.
     *
     * @return the string for html
     */
// This method needs to be a "get" so that it can be referred to from a JSP file.
    public String getStringForHtml() {
        // Sanitizes the error message so that all illegal characters are replaced by
        // valid characters.

        StringBuilder sb = new StringBuilder();
        for (String currentMessage : errorMessage) {
//			currentMessage = currentMessage.replaceAll("\\'", "&quot;");
//			currentMessage = currentMessage.replaceAll("\"", "&quot;");
//			currentMessage = currentMessage.replaceAll("\\&", "&amp;");
//			
//			currentMessage = currentMessage.replaceAll("\\<", "&lt;");
//			currentMessage = currentMessage.replaceAll("\\>", "&gt;");
//			
//			currentMessage = currentMessage.replaceAll("\n", " ");
//			currentMessage = currentMessage.replaceAll("\r", " ");

            sb.append("Caused By " + currentMessage);
        }
        return sb.toString();
    }
}
