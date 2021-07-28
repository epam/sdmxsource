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
package org.sdmxsource.sdmx.api.model.data;

import org.sdmxsource.sdmx.api.constants.DATA_TYPE;

import java.io.Serializable;

/**
 * Data format is an Interface which wrappers a strongly typed and non-extensible {@link DATA_TYPE} enumeration.  The purpose of the wrapper is to
 * allow for additional implementations to be provided which may describe non-sdmx formats which are not supported by the {@link DATA_TYPE} enum.
 */
public interface DataFormat extends Serializable {

    /**
     * Returns the SDMX Data Type that this interface is describing.
     * <p>
     * If this is not describing an SDMX message then this will return null and the implementation class will be expected to expose additional methods
     * to understand the data
     *
     * @return sdmx data format
     */
    DATA_TYPE getSdmxDataFormat();

    /**
     * Returns a string representation of the format, that can be used for auditing and debugging purposes.
     * <p>
     * This is expected to return a not null response.
     *
     * @return format as string
     */
    String getFormatAsString();

    /**
     * Returns the file extension used for this data fomat, for example
     * if the data format is an SDMX-ML format, this  will return the String "xml"
     *
     * @return file extension
     */
    String getFileExtension();


}
