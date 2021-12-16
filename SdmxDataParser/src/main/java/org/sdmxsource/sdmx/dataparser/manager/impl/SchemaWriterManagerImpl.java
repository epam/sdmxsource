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
package org.sdmxsource.sdmx.dataparser.manager.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sdmxsource.sdmx.api.engine.SchemaWriterEngine;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.factory.SchemaWriterFactory;
import org.sdmxsource.sdmx.api.manager.output.SchemaWriterManager;
import org.sdmxsource.sdmx.api.model.format.SchemaFormat;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DataStructureSuperBean;
import org.sdmxsource.sdmx.dataparser.factory.SdmxSchemaWriterFactory;

import java.io.OutputStream;
import java.util.Map;
import java.util.Set;


/**
 * The type Schema writer manager.
 */
public class SchemaWriterManagerImpl implements SchemaWriterManager {
    private static Logger LOG = LogManager.getLogger(SchemaWriterManagerImpl.class);

    private final SchemaWriterFactory[] factories;

    /**
     * Instantiates a new Schema writer manager.
     *
     * @param factories the factories
     */
    public SchemaWriterManagerImpl(final SchemaWriterFactory[] factories) {
        if (factories == null || factories.length == 0) {
            this.factories = new SchemaWriterFactory[]{new SdmxSchemaWriterFactory()};
        } else {
            this.factories = factories;
        }
    }

    @Override
    public void generateSchema(OutputStream out, DataStructureSuperBean dsd,
                               SchemaFormat outputFormat, String targetNamespace,
                               Map<String, Set<String>> constraintsMap) {
        getSchemaWritingEngine(outputFormat).generateSchema(out, dsd, targetNamespace, constraintsMap);
    }

    @Override
    public void generateCrossSectionalSchema(OutputStream out,
                                             DataStructureSuperBean dsd, SchemaFormat outputFormat,
                                             String targetNamespace, String crossSectionalDimensionId,
                                             Map<String, Set<String>> constraintsMap) {
        getSchemaWritingEngine(outputFormat).generateCrossSectionalSchema(out, dsd, targetNamespace, crossSectionalDimensionId, constraintsMap);
    }

    private SchemaWriterEngine getSchemaWritingEngine(SchemaFormat outputFormat) {
        for (SchemaWriterFactory factory : factories) {
            SchemaWriterEngine engine = factory.getSchemaWriterEngine(outputFormat);
            if (engine != null) {
                return engine;
            }
        }
        throw new SdmxNotImplementedException("Could not write schema out in format: " + outputFormat);
    }
}
