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
package org.sdmxsource.sdmx.dataparser.factory;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.sdmxsource.sdmx.api.engine.DataWriterEngine;
import org.sdmxsource.sdmx.api.factory.DataWriterFactory;
import org.sdmxsource.sdmx.api.model.data.DataFormat;
import org.sdmxsource.sdmx.dataparser.engine.writer.CompactDataWriterEngine;
import org.sdmxsource.sdmx.dataparser.engine.writer.CrossSectionDataWriterEngine;
import org.sdmxsource.sdmx.dataparser.engine.writer.GenericDataWriterEngine;
import org.sdmxsource.sdmx.ediparser.engine.writer.impl.EDIDataWriterEngineImpl;
import org.sdmxsource.util.factory.SdmxSourceWriteableDataLocationFactory;

import java.io.OutputStream;


/**
 * The type Sdmx data writer factory.
 */
public class SdmxDataWriterFactory implements DataWriterFactory {
    private static Logger LOG = LoggerFactory.getLogger(SdmxDataWriterFactory.class);

    @Override
    public DataWriterEngine getDataWriterEngine(DataFormat dataFormat, OutputStream out) {
        if (dataFormat.getSdmxDataFormat() != null) {
            switch (dataFormat.getSdmxDataFormat().getBaseDataFormat()) {
                case COMPACT:
                    return new CompactDataWriterEngine(dataFormat.getSdmxDataFormat().getSchemaVersion(), out);
                case GENERIC:
                    return new GenericDataWriterEngine(dataFormat.getSdmxDataFormat().getSchemaVersion(), out);
                case CROSS_SECTIONAL:
                    return new CrossSectionDataWriterEngine(dataFormat.getSdmxDataFormat().getSchemaVersion(), out);
                case EDI:
                    return new EDIDataWriterEngineImpl(new SdmxSourceWriteableDataLocationFactory(), out);
                default:
                    LOG.warn("DataWriterFactoryImpl encountered unsuported SDMX format: " + dataFormat);
            }
        }
        return null;
    }
}
