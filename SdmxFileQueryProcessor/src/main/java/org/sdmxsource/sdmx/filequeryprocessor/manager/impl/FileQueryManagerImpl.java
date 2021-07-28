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
package org.sdmxsource.sdmx.filequeryprocessor.manager.impl;

import org.sdmxsource.sdmx.api.engine.DataWriterEngine;
import org.sdmxsource.sdmx.api.model.data.DataFormat;
import org.sdmxsource.sdmx.api.model.data.query.DataQuery;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.dataparser.manager.DataInformationManager;
import org.sdmxsource.sdmx.dataparser.manager.DataReaderManager;
import org.sdmxsource.sdmx.dataparser.manager.DataWriterManager;
import org.sdmxsource.sdmx.dataparser.transform.DataReaderWriterTransform;
import org.sdmxsource.sdmx.filequeryprocessor.engine.FileQueryEngine;
import org.sdmxsource.sdmx.filequeryprocessor.manager.FileQueryManager;

import java.io.OutputStream;


/**
 * The type File query manager.
 */
public class FileQueryManagerImpl implements FileQueryManager {

    private final DataInformationManager dataInformationManager;

    private final DataWriterManager dataWriterManager;

    private final DataReaderManager dataReadManager;
    private final DataReaderWriterTransform dataReaderWriterTransform;

    /**
     * Instantiates a new File query manager.
     *
     * @param dataInformationManager    the data information manager
     * @param dataWriterManager         the data writer manager
     * @param dataReadManager           the data read manager
     * @param dataReaderWriterTransform the data reader writer transform
     */
    public FileQueryManagerImpl(
            final DataInformationManager dataInformationManager,
            final DataWriterManager dataWriterManager,
            final DataReaderManager dataReadManager,
            final DataReaderWriterTransform dataReaderWriterTransform) {
        this.dataInformationManager = dataInformationManager;
        this.dataWriterManager = dataWriterManager;
        this.dataReadManager = dataReadManager;
        this.dataReaderWriterTransform = dataReaderWriterTransform;
    }

    @Override
    public void runQuery(ReadableDataLocation dataLocation,
                         DataQuery dataQuery,
                         OutputStream out) {

        DataFormat dataType = dataInformationManager.getDataType(dataLocation);
        DataWriterEngine dataWriterEngine = dataWriterManager.getDataWriterEngine(dataType, out);


        FileQueryEngine queryEngine = new FileQueryEngine(dataReadManager, dataReaderWriterTransform, dataLocation);
        queryEngine.getData(dataQuery, dataWriterEngine);

    }
}
