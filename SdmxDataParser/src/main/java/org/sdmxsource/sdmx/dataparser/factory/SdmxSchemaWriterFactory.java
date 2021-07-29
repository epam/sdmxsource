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
package org.sdmxsource.sdmx.dataparser.factory;

import org.sdmxsource.sdmx.api.constants.DATA_TYPE;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.api.engine.SchemaWriterEngine;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.factory.SchemaWriterFactory;
import org.sdmxsource.sdmx.api.model.format.SchemaFormat;
import org.sdmxsource.sdmx.dataparser.engine.impl.CompactSchemaWriterEngine;
import org.sdmxsource.sdmx.dataparser.engine.impl.GenericSchemaWriterEngine;
import org.sdmxsource.sdmx.dataparser.engine.impl.UtilitySchemaWriterEngine;
import org.sdmxsource.sdmx.dataparser.model.SdmxSchemaFormat;
import org.sdmxsource.util.resourceBundle.PropertiesToMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * The type Sdmx schema writer factory.
 */
public class SdmxSchemaWriterFactory implements SchemaWriterFactory {
    private Map<SDMX_SCHEMA, String> schemaLocationMap = new HashMap<SDMX_SCHEMA, String>();


    @Override
    public SchemaWriterEngine getSchemaWriterEngine(SchemaFormat format) {
        if (format instanceof SdmxSchemaFormat) {
            DATA_TYPE dataType = ((SdmxSchemaFormat) format).getDataType();
            return getWriterEngine(dataType);
        }
        return null;
    }


    private SchemaWriterEngine getWriterEngine(DATA_TYPE schema) {
        SDMX_SCHEMA schemaVersion = schema.getSchemaVersion();

        switch (schema.getBaseDataFormat()) {
            case GENERIC:
                return new GenericSchemaWriterEngine(schemaVersion);
            case COMPACT:
                return new CompactSchemaWriterEngine(schemaLocationMap.get(schemaVersion), schemaVersion);
            case UTILITY:
                if (schemaVersion == SDMX_SCHEMA.VERSION_TWO) {
                    return new UtilitySchemaWriterEngine(schemaVersion);
                } else {
                    throw new IllegalArgumentException("Can not create utility schema in version '" + schemaVersion + "' only version 2.0 supported");
                }

            default:
                throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "Schema generation for format " + schema.getBaseDataFormat());
        }
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
}
