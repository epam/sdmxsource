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
package org.sdmxsource.sdmx.dataparser.manager;

import org.sdmxsource.sdmx.api.constants.DATA_TYPE;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DataStructureSuperBean;

import java.io.OutputStream;
import java.util.Map;
import java.util.Set;


/**
 * The interface Schema generation manager.
 */
public interface SchemaGenerationManager {

    /**
     * Generates a time-series schema in the format specified by the data type for the key family
     * which is written to the specified OutputStream.
     *
     * @param out             the OutputStream to write the schema to  this is closed on completion
     * @param dsd             the DSD to generate the schema for
     * @param targetNamespace the target namespace to use
     * @param schema          the schema type to generate
     * @param constraintsMap  the map key is the dimension id, values is the valid codes for the dimension
     */
    void generateSchema(OutputStream out, DataStructureSuperBean dsd, String targetNamespace, DATA_TYPE schema, Map<String, Set<String>> constraintsMap);

    /**
     * Generates a cross-sectional schema in the format specified by the data type for the key family
     * which is written to the specified OutputStream.
     *
     * @param out                       the OutputStream to write the schema to  this is closed on completion
     * @param dsd                       the DSD to generate the schema for
     * @param targetNamespace           the target namespace to use
     * @param schema                    the schema type to generate
     * @param crossSectionalDimensionId the id of the dimension that will be used at the observation level
     * @param constraintsMap            the map key is the dimension id, values is the valid codes for the dimension
     */
    void generateCrossSectionalSchema(OutputStream out, DataStructureSuperBean dsd, String targetNamespace, DATA_TYPE schema, String crossSectionalDimensionId, Map<String, Set<String>> constraintsMap);
}
