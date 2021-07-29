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
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DataStructureSuperBean;
import org.sdmxsource.sdmx.dataparser.transform.impl.SchemaGeneratorUtility;

import java.io.OutputStream;
import java.util.Map;
import java.util.Set;

/**
 * The type Utility schema writer engine.
 */
public class UtilitySchemaWriterEngine implements SchemaWriterEngine {

    private SDMX_SCHEMA schemaVersion;


    /**
     * Instantiates a new Utility schema writer engine.
     *
     * @param schemaVersion the schema version
     */
    public UtilitySchemaWriterEngine(SDMX_SCHEMA schemaVersion) {
        this.schemaVersion = schemaVersion;
    }

    @Override
    public void generateSchema(OutputStream out, DataStructureSuperBean dsd,
                               String targetNamespace, Map<String, Set<String>> validCodes) {

        new SchemaGeneratorUtility().transform(out, targetNamespace, schemaVersion, dsd, validCodes);
    }

    @Override
    public void generateCrossSectionalSchema(OutputStream out,
                                             DataStructureSuperBean dsd, String targetNamespace,
                                             String crossSectionalDimensionId,
                                             Map<String, Set<String>> constraintsMap) {
        throw new SdmxNotImplementedException("Utility Cross Sectional Schema Generation");
    }


}
