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
package org.sdmxsource.sdmx.structureparser.engine.writing;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.apache.xmlbeans.XmlObject;
import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.registry.response.impl.v2.QueryStructureResponseBuilderV2;

import java.io.OutputStream;


/**
 * The type Registry query response writing engine v 2.
 */
public class RegistryQueryResponseWritingEngineV2 extends AbstractWritingEngine {
    private static final Logger LOG = LoggerFactory.getLogger(StructureWritingEngineEDI.class);

    private final QueryStructureResponseBuilderV2 queryStructureResponseBuilderV2;

    /**
     * Instantiates a new Registry query response writing engine v 2.
     *
     * @param outputStream                    the output stream
     * @param prettyFy                        the pretty fy
     * @param queryStructureResponseBuilderV2 the query structure response builder v 2
     */
    public RegistryQueryResponseWritingEngineV2(OutputStream outputStream, boolean prettyFy, final QueryStructureResponseBuilderV2 queryStructureResponseBuilderV2) {
        super(SDMX_SCHEMA.VERSION_TWO, outputStream, prettyFy);
        this.queryStructureResponseBuilderV2 = queryStructureResponseBuilderV2;
    }

    /**
     * Instantiates a new Registry query response writing engine v 2.
     *
     * @param outputStream                    the output stream
     * @param queryStructureResponseBuilderV2 the query structure response builder v 2
     */
    public RegistryQueryResponseWritingEngineV2(OutputStream outputStream, final QueryStructureResponseBuilderV2 queryStructureResponseBuilderV2) {
        super(SDMX_SCHEMA.VERSION_TWO, outputStream, true);
        this.queryStructureResponseBuilderV2 = queryStructureResponseBuilderV2;
    }

    @Override
    protected XmlObject build(SdmxBeans beans) {

        LOG.info("Write structures as a SDMX 2.0 Registry response message");
        return queryStructureResponseBuilderV2.buildSuccessResponse(beans);
    }

}
