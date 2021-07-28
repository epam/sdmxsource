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
import org.sdmxsource.util.io.StreamUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Set;

/**
 * The type Generic schema writer engine.
 */
public class GenericSchemaWriterEngine implements SchemaWriterEngine {

    private SDMX_SCHEMA schemaVersion;


    /**
     * Instantiates a new Generic schema writer engine.
     *
     * @param schemaVersion the schema version
     */
    public GenericSchemaWriterEngine(SDMX_SCHEMA schemaVersion) {
        this.schemaVersion = schemaVersion;
    }

    @Override
    public void generateSchema(OutputStream out, DataStructureSuperBean dsd,
                               String targetNamespace, Map<String, Set<String>> constraintsMap) {
        copyToStream(out);
    }

    @Override
    public void generateCrossSectionalSchema(OutputStream out,
                                             DataStructureSuperBean dsd, String targetNamespace,
                                             String crossSectionalDimensionId,
                                             Map<String, Set<String>> constraintsMap) {
        copyToStream(out);
    }

    private void copyToStream(OutputStream out) {
        InputStream is;
        try {
            switch (schemaVersion) {
                case VERSION_ONE:
                    is = new FileInputStream("resources/xsd/v2_0/SDMXMessage.xsd");
                    break;
                case VERSION_TWO:
                    is = new FileInputStream("resources/xsd/v2_0/SDMXMessage.xsd");
                    break;
                case VERSION_TWO_POINT_ONE:
                    is = new FileInputStream("resources/xsd/v2_0/SDMXMessage.xsd");
                    break;

                default:
                    throw new IllegalArgumentException("Generic format not supported in version : " + schemaVersion);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        StreamUtil.copyStream(is, out);
    }


}
