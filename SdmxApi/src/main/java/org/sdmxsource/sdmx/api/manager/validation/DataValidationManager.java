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
package org.sdmxsource.sdmx.api.manager.validation;

import org.sdmxsource.sdmx.api.engine.DataReaderEngine;
import org.sdmxsource.sdmx.api.exception.ExceptionHandler;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.exception.SdmxSyntaxException;

/**
 * The DataValidationManager is responsible for validating data read from a DataReaderEngine
 */
public interface DataValidationManager {

    /**
     * Validates the data read from the DataReaderEngine
     *
     * @param dre              the reader engine used to read the dataset.  The DataValidationManager will reset the DataReaderEngine
     * @param exceptionHandler the exception handler
     * @throws SdmxSyntaxException    if the data is not syntactically valid
     * @throws SdmxSemmanticException if the data is syntactically valid, however it contains invalid content
     */
    void validateData(DataReaderEngine dre, ExceptionHandler exceptionHandler) throws SdmxSyntaxException, SdmxSemmanticException;

}
