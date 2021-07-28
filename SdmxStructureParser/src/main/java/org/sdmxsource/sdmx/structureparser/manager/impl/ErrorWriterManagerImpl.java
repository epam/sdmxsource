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

import org.sdmxsource.sdmx.api.engine.ErrorWriterEngine;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.factory.ErrorWriterFactory;
import org.sdmxsource.sdmx.api.manager.output.ErrorWriterManager;
import org.sdmxsource.sdmx.api.model.ErrorFormat;
import org.sdmxsource.sdmx.structureparser.factory.SdmxErrorWriterFactory;

import java.io.OutputStream;

/**
 * The type Error writer manager.
 */
public class ErrorWriterManagerImpl implements ErrorWriterManager {

    private final ErrorWriterFactory[] factories;

    /**
     * Instantiates a new Error writer manager.
     */
    public ErrorWriterManagerImpl() {
        this(null);
    }

    /**
     * Instantiates a new Error writer manager.
     *
     * @param factories the factories
     */
    public ErrorWriterManagerImpl(final ErrorWriterFactory[] factories) {
        if (factories == null || factories.length == 0) {
            this.factories = new ErrorWriterFactory[]{new SdmxErrorWriterFactory()};
        } else {
            this.factories = factories;
        }
    }

    @Override
    public int writeError(Throwable th, OutputStream out, ErrorFormat format) {
        return getErrorWriterEngine(format).writeError(th, out);
    }

    private ErrorWriterEngine getErrorWriterEngine(ErrorFormat format) {
        for (ErrorWriterFactory factory : factories) {
            ErrorWriterEngine engine = factory.getErrorWriterEngine(format);
            if (engine != null) {
                return engine;
            }
        }
        throw new SdmxNotImplementedException("Could not write error out in format: " + format);
    }
}
