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
package org.sdmxsource.sdmx.structureparser.engine.writing;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.api.constants.SdmxConstants;
import org.sdmxsource.util.resourceBundle.PropertiesToMap;

import javax.xml.namespace.QName;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * The type Schema location writer.
 */
public class SchemaLocationWriter {
    private Map<SDMX_SCHEMA, String> schemaLocationMap = new HashMap<SDMX_SCHEMA, String>();

    /**
     * Writes the schema location(s) to the XML Document
     *
     * @param doc          the XML document to write the schema location(s) to
     * @param namespaceUri the URI of the namespace to write
     */
    public void writeSchemaLocation(XmlObject doc, String... namespaceUri) {
        if (namespaceUri == null) {
            return;
        }

        SDMX_SCHEMA schemaVersion;
        StringBuilder schemaLocation = new StringBuilder();

        String concat = "";
        for (String currentNamespaceUri : namespaceUri) {
            schemaVersion = SdmxConstants.getSchemaVersion(currentNamespaceUri);
            //Base location of schema for version e.g. http://www.sss.sss/schema/
            String schemaBaseLocation = getSchemaLocation(schemaVersion);
            String schemaName = SdmxConstants.getSchemaName(currentNamespaceUri);
            schemaLocation.append(concat + currentNamespaceUri + " " + concat + schemaBaseLocation + schemaName);
            concat = System.getProperty("line.separator");
        }
        XmlCursor cursor = doc.newCursor();
        if (cursor.toFirstChild()) {
            cursor.setAttributeText(new QName("http://www.w3.org/2001/XMLSchema-instance", "schemaLocation"), schemaLocation.toString());
        }
    }

    /**
     * Gets schema location.
     *
     * @param schemaVersion the schema version
     * @return the schema location
     */
    public String getSchemaLocation(SDMX_SCHEMA schemaVersion) {
        return schemaLocationMap.get(schemaVersion);
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
