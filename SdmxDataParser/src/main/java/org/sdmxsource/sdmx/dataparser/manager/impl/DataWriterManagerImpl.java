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

import org.sdmxsource.sdmx.api.engine.DataWriterEngine;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.factory.DataWriterFactory;
import org.sdmxsource.sdmx.api.model.data.DataFormat;
import org.sdmxsource.sdmx.dataparser.factory.SdmxDataWriterFactory;
import org.sdmxsource.sdmx.dataparser.manager.DataWriterManager;

import java.io.OutputStream;

/**
 * The type Data writer manager.
 */
public class DataWriterManagerImpl implements DataWriterManager {


    private final DataWriterFactory[] factories;

    /**
     * Instantiates a new Data writer manager.
     *
     * @param factories the factories
     */
    public DataWriterManagerImpl(final DataWriterFactory[] factories) {
        if (factories == null || factories.length == 0) {
            this.factories = new DataWriterFactory[]{new SdmxDataWriterFactory()};
        } else {
            this.factories = factories;
        }
    }

    @Override
    public DataWriterEngine getDataWriterEngine(DataFormat dataFormat, OutputStream out) {
        for (DataWriterFactory dwf : getDataWriterFactories()) {
            DataWriterEngine dwe = dwf.getDataWriterEngine(dataFormat, out);
            if (dwe != null) {
                return dwe;
            }
        }
        throw new SdmxNotImplementedException("Could not write data out in type: " + dataFormat);
    }


    private DataWriterFactory[] getDataWriterFactories() throws IllegalArgumentException {
        return factories;
    }
}
