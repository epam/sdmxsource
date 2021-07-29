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
 * Access exception is a specialised form of SdmxException which
 * has a fixed Sdmx Exception type of SDMX_ERROR_CODE.UNAUTHORISED
 */
public class SdmxUnauthorisedException extends SdmxException {

    private static final long serialVersionUID = 6333338144646600587L;

    public SdmxUnauthorisedException(String message) {
        super(message, SDMX_ERROR_CODE.UNAUTHORISED);
    }

    public SdmxUnauthorisedException(ExceptionCode code, Object... args) {
        super(SDMX_ERROR_CODE.UNAUTHORISED, code, args);
    }

    @Override
    public String getErrorType() {
        return "Security Exception";
    }
}
