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
package org.sdmxsource.sdmx.dataparser.engine.impl;

import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.api.engine.SchemaWriterEngine;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DataStructureSuperBean;
import org.sdmxsource.sdmx.dataparser.transform.impl.CompactSchemaCreator;
import org.sdmxsource.sdmx.dataparser.transform.impl.CompactSchemaCreatorSdmx;
import org.sdmxsource.sdmx.dataparser.transform.impl.CompactSchemaCreatorSdmx2_1;
import org.sdmxsource.util.io.StreamUtil;

import java.io.OutputStream;
import java.util.Map;
import java.util.Set;

/**
 * The type Compact schema writer engine.
 */
public class CompactSchemaWriterEngine implements SchemaWriterEngine {
    private SDMX_SCHEMA targetSchemaVersion;
    private String schemaLocation;


    /**
     * Instantiates a new Compact schema writer engine.
     *
     * @param schemaLocation      the schema location
     * @param targetSchemaVersion the target schema version
     */
    public CompactSchemaWriterEngine(String schemaLocation, SDMX_SCHEMA targetSchemaVersion) {
        this.schemaLocation = schemaLocation;
        this.targetSchemaVersion = targetSchemaVersion;
    }

    @Override
    public void generateSchema(OutputStream out, DataStructureSuperBean dsd, String targetNamespace, Map<String, Set<String>> validCodes) {
        CompactSchemaCreator creator;
        if (targetSchemaVersion.equals(SDMX_SCHEMA.VERSION_TWO_POINT_ONE)) {
            creator = new CompactSchemaCreatorSdmx2_1(out, targetNamespace, targetSchemaVersion, dsd, schemaLocation, validCodes);
        } else {
            creator = new CompactSchemaCreatorSdmx(out, targetNamespace, targetSchemaVersion, dsd, schemaLocation, validCodes);
        }
        try {
            creator.createSchema();
        } finally {
            StreamUtil.closeStream(out);
        }
    }

    @Override
    public void generateCrossSectionalSchema(OutputStream out, DataStructureSuperBean dsd, String targetNamespace, String crossSectionDimensionId, Map<String, Set<String>> validCodes) {
        CompactSchemaCreator creator;
        if (targetSchemaVersion.equals(SDMX_SCHEMA.VERSION_TWO_POINT_ONE)) {
            creator = new CompactSchemaCreatorSdmx2_1(out, targetNamespace, targetSchemaVersion, dsd, crossSectionDimensionId, schemaLocation, validCodes);
        } else {
            creator = new CompactSchemaCreatorSdmx(out, targetNamespace, targetSchemaVersion, dsd, crossSectionDimensionId, schemaLocation, validCodes);
        }
        try {
            creator.createSchema();
        } finally {
            StreamUtil.closeStream(out);
        }
    }


}
