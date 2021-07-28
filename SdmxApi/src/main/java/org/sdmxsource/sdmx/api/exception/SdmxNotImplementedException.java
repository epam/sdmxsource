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


public class SdmxNotImplementedException extends SdmxException {
    private static final long serialVersionUID = 6830799169917227358L;

    public SdmxNotImplementedException(String str) {
        super(str, SDMX_ERROR_CODE.NOT_IMPLEMENTED);
    }

    public SdmxNotImplementedException(ExceptionCode code, Object... args) {
        this(null, code, args);
    }

    public SdmxNotImplementedException(Object... args) {
        this(null, ExceptionCode.UNSUPPORTED, args);
    }

    public SdmxNotImplementedException(Throwable th) {
        this(th, null);
    }

    public SdmxNotImplementedException(Throwable e, ExceptionCode code, Object... args) {
        super(e, SDMX_ERROR_CODE.NOT_IMPLEMENTED, code, args);
    }

    @Override
    public String getErrorType() {
        return "Not Implemented Exception";
    }
}
