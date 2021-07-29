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
package org.sdmxsource.sdmx.structureparser.manager.rest;

import org.sdmxsource.sdmx.api.manager.output.StructureWriterManager;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.manager.retrieval.rest.RestStructureQueryManager;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.format.StructureFormat;
import org.sdmxsource.sdmx.api.model.query.RESTStructureQuery;

import java.io.OutputStream;
import java.util.Objects;

/**
 * The type Rest structure query manager.
 */
public class RestStructureQueryManagerImpl implements RestStructureQueryManager {

    private final StructureWriterManager structureWritingManager;

    private SdmxBeanRetrievalManager beanRetrievalManager;

    /**
     * Instantiates a new Rest structure query manager.
     *
     * @param structureWritingManager the structure writing manager
     */
    public RestStructureQueryManagerImpl(final StructureWriterManager structureWritingManager) {
        this.structureWritingManager = Objects.requireNonNull(structureWritingManager, "structureWritingManager");
    }


    public void getStructures(RESTStructureQuery query, OutputStream out, StructureFormat outputFormat) {
        SdmxBeans beans = beanRetrievalManager.getMaintainables(query);
        structureWritingManager.writeStructures(beans, outputFormat, out);
    }

    /**
     * Sets bean retrieval manager.
     *
     * @param beanRetrievalManager the bean retrieval manager
     */
    public void setBeanRetrievalManager(SdmxBeanRetrievalManager beanRetrievalManager) {
        this.beanRetrievalManager = beanRetrievalManager;
    }
}
