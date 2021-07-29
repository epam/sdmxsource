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
package org.sdmxsource.sdmx.api.exception;

import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_ERROR_CODE;

/**
 * The type Sdmx syntax exception.
 */
public class SdmxSyntaxException extends SdmxException {
    private static final long serialVersionUID = 2966382157011760240L;

    /**
     * Creates Exception from an error String and an Error code
     *
     * @param code the code
     * @param args the args
     */
    public SdmxSyntaxException(ExceptionCode code, Object... args) {
        super(SDMX_ERROR_CODE.SYNTAX_ERROR, code, args);
    }

    /**
     * Instantiates a new Sdmx syntax exception.
     *
     * @param str the str
     */
    public SdmxSyntaxException(String str) {
        super(str, SDMX_ERROR_CODE.SYNTAX_ERROR);
    }

    /**
     * Instantiates a new Sdmx syntax exception.
     *
     * @param th the th
     */
    public SdmxSyntaxException(Throwable th) {
        super(th, SDMX_ERROR_CODE.SYNTAX_ERROR, null);
    }


    /**
     * Instantiates a new Sdmx syntax exception.
     *
     * @param th            the th
     * @param exceptionCode the exception code
     */
    public SdmxSyntaxException(Throwable th, ExceptionCode exceptionCode) {
        super(th, SDMX_ERROR_CODE.SYNTAX_ERROR, exceptionCode);
    }
}
