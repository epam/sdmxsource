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
package org.sdmxsource.sdmx.querybuilder.model;

import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.api.model.format.DataQueryFormat;

/**
 * The type Sdmx data query format.
 */
public class SdmxDataQueryFormat implements DataQueryFormat {
    private SDMX_SCHEMA version;

    /**
     * Instantiates a new Sdmx data query format.
     *
     * @param version the version
     */
    public SdmxDataQueryFormat(SDMX_SCHEMA version) {
        this.version = version;
        if (version == null) {
            throw new IllegalArgumentException("SdmxDataQueryFormat.SDMX_SCHEMA can not be null");
        }
    }

    /**
     * Gets version.
     *
     * @return the version
     */
    public SDMX_SCHEMA getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return version.toString();
    }
}
