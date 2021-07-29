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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans.error.impl;


import org.apache.xmlbeans.XmlObject;
import org.sdmx.resources.sdmxml.schemas.v21.common.CodedStatusMessageType;
import org.sdmx.resources.sdmxml.schemas.v21.common.TextType;
import org.sdmx.resources.sdmxml.schemas.v21.message.ErrorDocument;
import org.sdmx.resources.sdmxml.schemas.v21.message.ErrorType;
import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.XmlBeanBuilder;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.error.ErrorResponseBuilder;
import org.sdmxsource.sdmx.util.exception.SchemaValidationException;


/**
 * The type Error response builder.
 */
public class ErrorResponseBuilderImpl extends XmlBeanBuilder implements ErrorResponseBuilder {

    @Override
    public XmlObject buildErrorResponse(Throwable th, String exceptionCode) {
        ErrorDocument errorDocument = ErrorDocument.Factory.newInstance();

        ErrorType errorType = errorDocument.addNewError();
        CodedStatusMessageType errorMessage = errorType.addNewErrorMessage();

        errorMessage.setCode(exceptionCode);
        while (th != null) {
            if (th instanceof SchemaValidationException) {
                processSchemaValidationError(errorMessage, (SchemaValidationException) th);
            } else {
                processThrowable(errorMessage, th);
            }
            th = th.getCause();
        }

        super.writeSchemaLocation(errorDocument, SDMX_SCHEMA.VERSION_TWO_POINT_ONE);

        return errorDocument;
    }

    private void processSchemaValidationError(CodedStatusMessageType errorMessage, SchemaValidationException e) {
        for (String error : e.getValidationErrors()) {
            TextType text = errorMessage.addNewText();
            text.setStringValue(error);
        }
    }

    private void processThrowable(CodedStatusMessageType errorMessage, Throwable th) {
        TextType text = errorMessage.addNewText();
        if (th.getMessage() == null) {
            if (th.getCause() != null) {
                text.setStringValue(th.getCause().getMessage());
            } else {
                if (th instanceof NullPointerException) {
                    text.setStringValue("Null Pointer Exception");
                } else {
                    text.setStringValue("No Error Message Provided");
                }
            }
        } else {
            text.setStringValue(th.getMessage());
        }
    }
}
