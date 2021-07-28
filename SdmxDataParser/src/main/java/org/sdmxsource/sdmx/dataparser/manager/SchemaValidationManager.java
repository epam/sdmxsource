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

import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.exception.SdmxSyntaxException;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DataStructureSuperBean;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;

/**
 * The interface Schema validation manager.
 */
public interface SchemaValidationManager {

    /**
     * Validates a DataSet against the schema generated from the DataStructureSuperBean and referenced Codelists
     * <p>
     * Expects the data to be in SDMX-ML format
     *
     * @param dataset   the dataset
     * @param keyFamily the key family
     * @throws SdmxSemmanticException if the data is not valid against the schema
     * @throws SdmxSyntaxException    if the data is not SDMX-ML
     */
    void validateDatasetAgainstSchema(ReadableDataLocation dataset, DataStructureSuperBean keyFamily) throws SdmxSemmanticException, SdmxSyntaxException;

}
