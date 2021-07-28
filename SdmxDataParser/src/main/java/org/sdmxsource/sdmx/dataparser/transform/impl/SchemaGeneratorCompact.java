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
package org.sdmxsource.sdmx.dataparser.transform.impl;

import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DataStructureSuperBean;
import org.sdmxsource.sdmx.dataparser.transform.SchemaGenerator;
import org.sdmxsource.util.io.StreamUtil;
import org.sdmxsource.util.resourceBundle.PropertiesToMap;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;


/**
 * The type Schema generator compact.
 */
public class SchemaGeneratorCompact implements SchemaGenerator {

    private Map<SDMX_SCHEMA, String> schemaLocationMap = new HashMap<SDMX_SCHEMA, String>();

    @Override
    public void transform(OutputStream out, String targetNamespace, SDMX_SCHEMA targetSchemaVersion, DataStructureSuperBean keyFamily, Map<String, Set<String>> validCodes) {
        String schemaLocation = obtainSchemaLocation(targetSchemaVersion);

        CompactSchemaCreator creator;
        if (targetSchemaVersion.equals(SDMX_SCHEMA.VERSION_TWO_POINT_ONE)) {
            creator = new CompactSchemaCreatorSdmx2_1(out, targetNamespace, targetSchemaVersion, keyFamily, schemaLocation, validCodes);
        } else {
            creator = new CompactSchemaCreatorSdmx(out, targetNamespace, targetSchemaVersion, keyFamily, schemaLocation, validCodes);
        }
        try {
            creator.createSchema();
        } finally {
            StreamUtil.closeStream(out);
        }
    }

    @Override
    public void transformCrossSectional(OutputStream out, String targetNamespace, SDMX_SCHEMA targetSchemaVersion, DataStructureSuperBean keyFamily, String crossSectionDimensionId, Map<String, Set<String>> validCodes) {
        String schemaLocation = obtainSchemaLocation(targetSchemaVersion);
        if (keyFamily == null) {
            throw new SdmxException("Can not create Schema, no Data Structure provided");
        }
        CompactSchemaCreator creator;
        if (targetSchemaVersion.equals(SDMX_SCHEMA.VERSION_TWO_POINT_ONE)) {
            creator = new CompactSchemaCreatorSdmx2_1(out, targetNamespace, targetSchemaVersion, keyFamily, crossSectionDimensionId, schemaLocation, validCodes);
        } else {
            creator = new CompactSchemaCreatorSdmx(out, targetNamespace, targetSchemaVersion, keyFamily, crossSectionDimensionId, schemaLocation, validCodes);
        }
        try {
            creator.createSchema();
        } finally {
            StreamUtil.closeStream(out);
        }
    }

    private String obtainSchemaLocation(SDMX_SCHEMA targetSchemaVersion) {
        return schemaLocationMap.get(targetSchemaVersion);
    }

    /**
     * Sets schema location properties.
     *
     * @param properties the properties
     */
    public void setSchemaLocationProperties(Properties properties) {
        Map<String, String> propsMap = PropertiesToMap.createMap(properties);
        for (String schemaVersion : propsMap.keySet()) {
            schemaLocationMap.put(SDMX_SCHEMA.valueOf(schemaVersion), propsMap.get(schemaVersion));
        }
    }

    /**
     * Add schema location.
     *
     * @param version        the version
     * @param schemaLocation the schema location
     */
    public void addSchemaLocation(SDMX_SCHEMA version, String schemaLocation) {
        schemaLocationMap.put(version, schemaLocation);
    }


}
