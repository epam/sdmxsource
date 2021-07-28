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

import org.sdmxsource.sdmx.api.constants.DATA_TYPE;
import org.sdmxsource.sdmx.api.engine.DataWriterEngine;
import org.sdmxsource.sdmx.api.factory.DataWriterFactory;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxSuperBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.data.DataFormat;
import org.sdmxsource.sdmx.dataparser.engine.writer.jsonsupport.JsonDataWriterEngine;

import java.io.OutputStream;

/**
 * The type Sdmx json data writer factory.
 */
public class SdmxJsonDataWriterFactory implements DataWriterFactory {

    private SdmxSuperBeanRetrievalManager superBeanRetrievalManager;

    @Override
    public DataWriterEngine getDataWriterEngine(DataFormat dataFormat, OutputStream out) {
        if (dataFormat.getSdmxDataFormat() != null && dataFormat.getSdmxDataFormat() == DATA_TYPE.SDMXJSON) {
            return new JsonDataWriterEngine(out, superBeanRetrievalManager, false);
        }
        return null;
    }

    /**
     * Sets super bean retrieval manager.
     *
     * @param superBeanRetrievalManager the super bean retrieval manager
     */
    public void setSuperBeanRetrievalManager(SdmxSuperBeanRetrievalManager superBeanRetrievalManager) {
        this.superBeanRetrievalManager = superBeanRetrievalManager;
    }
}
