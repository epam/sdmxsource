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
package org.sdmxsource.sdmx.dataparser.transform;

import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DataStructureSuperBean;

import java.io.OutputStream;
import java.util.Map;
import java.util.Set;

/**
 * Creates a SDMX Schema based on the DataStructure and the valid codes
 */
public interface SchemaGenerator {

    /**
     * Creates a schema using the supplied information and writes it to the OutputStream
     *
     * @param out                 OutputStream to write to - this is closed on completion
     * @param targetNamespace     the target namespace
     * @param targetSchemaVersion the target schema version
     * @param keyFamily           the key family
     * @param validCodes          the valid codes
     */
    void transform(OutputStream out, String targetNamespace,
                   SDMX_SCHEMA targetSchemaVersion, DataStructureSuperBean keyFamily, Map<String, Set<String>> validCodes);

    /**
     * Creates a schema using the supplied information and writes it to the OutputStream
     *
     * @param out                     OutputStream to write to - this is closed on completion
     * @param targetNamespace         the target namespace
     * @param targetSchemaVersion     the target schema version
     * @param keyFamily               the key family
     * @param crossSectionDimensionId the cross section dimension id
     * @param validCodes              the valid codes
     */
    void transformCrossSectional(OutputStream out, String targetNamespace, SDMX_SCHEMA targetSchemaVersion, DataStructureSuperBean keyFamily, String crossSectionDimensionId, Map<String, Set<String>> validCodes);
}
