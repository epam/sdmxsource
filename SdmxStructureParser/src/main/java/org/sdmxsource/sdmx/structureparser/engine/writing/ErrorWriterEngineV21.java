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
package org.sdmxsource.sdmx.structureparser.engine.writing;

import org.apache.xmlbeans.XmlObject;
import org.sdmxsource.sdmx.api.engine.ErrorWriterEngine;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.error.ErrorResponseBuilder;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.error.impl.ErrorResponseBuilderImpl;

import java.io.IOException;
import java.io.OutputStream;


/**
 * The type Error writer engine v 21.
 */
public class ErrorWriterEngineV21 implements ErrorWriterEngine {

    private final ErrorResponseBuilder errorResponseBuilder;

    /**
     * Instantiates a new Error writer engine v 21.
     */
    public ErrorWriterEngineV21() {
        this(null);
    }

    /**
     * Instantiates a new Error writer engine v 21.
     *
     * @param errorResponseBuilder the error response builder
     */
    public ErrorWriterEngineV21(final ErrorResponseBuilder errorResponseBuilder) {
        this.errorResponseBuilder = errorResponseBuilder != null ? errorResponseBuilder : new ErrorResponseBuilderImpl();
    }

    @Override
    public int writeError(Throwable th, OutputStream out) {
        XmlObject error;
        int statusCode;
        if (th instanceof SdmxException) {
            SdmxException ex = (SdmxException) th;
            statusCode = ex.getHttpRestErrorCode();
            error = errorResponseBuilder.buildErrorResponse(th, ex.getSdmxErrorCode().getClientErrorCode().toString());
        } else {
            th.printStackTrace(System.err);
            error = errorResponseBuilder.buildErrorResponse(th, "500");
            statusCode = 500;
        }
        try {
            error.save(out);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return statusCode;
    }
}
