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
package org.sdmxsource.sdmx.structureparser.manager.parsing.impl;

import org.sdmxsource.sdmx.api.constants.STRUCTURE_OUTPUT_FORMAT;
import org.sdmxsource.sdmx.api.factory.ReadableDataLocationFactory;
import org.sdmxsource.sdmx.api.manager.output.StructureWriterManager;
import org.sdmxsource.sdmx.api.manager.parse.StructureParsingManager;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.sdmxbeans.model.SdmxStructureFormat;
import org.sdmxsource.sdmx.structureparser.manager.impl.StructureWriterManagerImpl;
import org.sdmxsource.sdmx.structureparser.manager.parsing.StructureValidationManager;
import org.sdmxsource.util.factory.SdmxSourceReadableDataLocationFactory;
import org.sdmxsource.util.io.StreamUtil;

import java.io.ByteArrayOutputStream;


/**
 * The type Structure validation manager.
 */
public class StructureValidationManagerImpl implements StructureValidationManager {

    private final StructureParsingManager structureParsingManager;

    private final StructureWriterManager structureWritingManager;

    private final ReadableDataLocationFactory readableDataLocationFactory;

    /**
     * Instantiates a new Structure validation manager.
     *
     * @param structureParsingManager     the structure parsing manager
     * @param structureWritingManager     the structure writing manager
     * @param readableDataLocationFactory the readable data location factory
     */
    public StructureValidationManagerImpl(
            final StructureParsingManager structureParsingManager,
            final StructureWriterManager structureWritingManager,
            final ReadableDataLocationFactory readableDataLocationFactory) {
        this.readableDataLocationFactory = readableDataLocationFactory != null ? readableDataLocationFactory : new SdmxSourceReadableDataLocationFactory();
        this.structureWritingManager = structureWritingManager != null ? structureWritingManager : new StructureWriterManagerImpl(null);
        this.structureParsingManager = structureParsingManager != null ? structureParsingManager : new StructureParsingManagerImpl(this.readableDataLocationFactory, null);
    }

    @Override
    public void validateStructure(MaintainableBean maintainableBean) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        structureWritingManager.writeStructure(maintainableBean, null, new SdmxStructureFormat(STRUCTURE_OUTPUT_FORMAT.SDMX_V21_STRUCTURE_DOCUMENT), out);
        ReadableDataLocation dataLocation = readableDataLocationFactory.getReadableDataLocation(out.toByteArray());
        try {
            structureParsingManager.parseStructures(dataLocation);
        } finally {
            dataLocation.close();
            StreamUtil.closeStream(out);
        }
    }
}
