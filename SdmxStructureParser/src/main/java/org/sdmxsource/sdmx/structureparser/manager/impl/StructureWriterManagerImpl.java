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
package org.sdmxsource.sdmx.structureparser.manager.impl;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.sdmxsource.sdmx.api.engine.StructureWriterEngine;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.factory.StructureWriterFactory;
import org.sdmxsource.sdmx.api.manager.output.StructureWriterManager;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.format.StructureFormat;
import org.sdmxsource.sdmx.api.model.header.HeaderBean;
import org.sdmxsource.sdmx.structureparser.factory.SdmxStructureWriterFactory;
import org.sdmxsource.util.io.StreamUtil;

import java.io.OutputStream;


/**
 * The type Structure writer manager.
 */
public class StructureWriterManagerImpl implements StructureWriterManager {
    private static Logger LOG = LoggerFactory.getLogger(StructureWriterManagerImpl.class);

    private final StructureWriterFactory[] factories;

    /**
     * Instantiates a new Structure writer manager.
     */
    public StructureWriterManagerImpl() {
        this(null);
    }

    /**
     * Instantiates a new Structure writer manager.
     *
     * @param factories the factories
     */
    public StructureWriterManagerImpl(final StructureWriterFactory[] factories) {
        if (factories == null || factories.length == 0) {
            this.factories = new StructureWriterFactory[]{new SdmxStructureWriterFactory()};
        } else {
            this.factories = factories;
        }
    }

    @Override
    public void writeStructures(SdmxBeans beans, StructureFormat outputFormat, OutputStream out) {
        LOG.debug("Write Structures as " + outputFormat);
        try {
            getStructureWritingEngine(outputFormat, out).writeStructures(beans);
        } finally {
            StreamUtil.closeStream(out);
        }
    }

    @Override
    public void writeStructure(MaintainableBean bean, HeaderBean header, StructureFormat outputFormat, OutputStream out) {
        LOG.debug("Write Structure '" + bean + "' as " + outputFormat);
        try {
            getStructureWritingEngine(outputFormat, out).writeStructure(bean);
        } finally {
            StreamUtil.closeStream(out);
        }
    }

    private StructureWriterEngine getStructureWritingEngine(StructureFormat outputFormat, OutputStream out) {
        for (StructureWriterFactory factory : factories) {
            StructureWriterEngine engine = factory.getStructureWriterEngine(outputFormat, out);
            if (engine != null) {
                return engine;
            }
        }
        throw new SdmxNotImplementedException("Could not write structures out in format: " + outputFormat);
    }
}
