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
package org.sdmxsource.sdmx.sdmxbeans.model.data;

import org.sdmxsource.sdmx.api.constants.DATA_TYPE;
import org.sdmxsource.sdmx.api.model.data.DataFormat;

/**
 * The type Sdmx data format.
 */
public class SdmxDataFormat implements DataFormat {
    private static final long serialVersionUID = 4645305040609339151L;
    private DATA_TYPE dataType;

    /**
     * Instantiates a new Sdmx data format.
     *
     * @param dataType the data type
     */
    public SdmxDataFormat(DATA_TYPE dataType) {
        if (dataType == null) {
            throw new IllegalArgumentException("Data Type can not be null for SdmxDataFormat");
        }
        this.dataType = dataType;
    }

    @Override
    public DATA_TYPE getSdmxDataFormat() {
        return dataType;
    }


    @Override
    public String getFileExtension() {
        if (dataType == DATA_TYPE.EDI_TS) {
            return "edi";
        }
        if (dataType == DATA_TYPE.SDMXJSON) {
            return "json";
        }
        return "xml";
    }

    @Override
    public String toString() {
        return dataType.toString();
    }

    @Override
    public String getFormatAsString() {
        return toString();
    }
}
