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
package org.sdmxsource.sdmx.api.constants;

public enum STRUCTURE_OUTPUT_FORMAT {
    SDMX_V1_STRUCTURE_DOCUMENT(SDMX_SCHEMA.VERSION_ONE, false, false),
    SDMX_V2_STRUCTURE_DOCUMENT(SDMX_SCHEMA.VERSION_TWO, false, false),
    SDMX_V2_REGISTRY_SUBMIT_DOCUMENT(SDMX_SCHEMA.VERSION_TWO, false, true),
    SDMX_V2_REGISTRY_QUERY_RESPONSE_DOCUMENT(SDMX_SCHEMA.VERSION_TWO, true, true),
    SDMX_V21_STRUCTURE_DOCUMENT(SDMX_SCHEMA.VERSION_TWO_POINT_ONE, false, false),
    SDMX_V21_REGISTRY_SUBMIT_DOCUMENT(SDMX_SCHEMA.VERSION_TWO_POINT_ONE, false, true),
    SDMX_V21_QUERY_RESPONSE_DOCUMENT(SDMX_SCHEMA.VERSION_TWO_POINT_ONE, true, false),
    EDI(SDMX_SCHEMA.EDI, false, false),
    CSV(SDMX_SCHEMA.CSV, false, false);

    private SDMX_SCHEMA outputVersion;
    private boolean isQueryResponse;
    private boolean isRegistryDocument;

    private STRUCTURE_OUTPUT_FORMAT(SDMX_SCHEMA version, boolean isQueryResponse, boolean isRegistryDocument) {
        this.outputVersion = version;
        this.isQueryResponse = isQueryResponse;
        this.isRegistryDocument = isRegistryDocument;
    }

    public SDMX_SCHEMA getOutputVersion() {
        return outputVersion;
    }

    public boolean isQueryResponse() {
        return isQueryResponse;
    }

    public boolean isRegistryDocument() {
        return isRegistryDocument;
    }
}
