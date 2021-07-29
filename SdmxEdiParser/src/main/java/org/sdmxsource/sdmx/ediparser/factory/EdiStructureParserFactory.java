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
package org.sdmxsource.sdmx.ediparser.factory;

import org.apache.log4j.Logger;
import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.api.factory.StructureParserFactory;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.ediparser.manager.EdiParseManager;
import org.sdmxsource.sdmx.ediparser.model.EDIWorkspace;
import org.sdmxsource.sdmx.util.beans.container.SdmxBeansImpl;
import org.sdmxsource.sdmx.util.sdmx.SdmxMessageUtil;


/**
 * The type Edi structure parser factory.
 */
public class EdiStructureParserFactory implements StructureParserFactory {
    private static final Logger LOG = Logger.getLogger(EdiStructureParserFactory.class);

    private final EdiParseManager ediParseManager;

    /**
     * Instantiates a new Edi structure parser factory.
     *
     * @param ediParseManager the edi parse manager
     */
    public EdiStructureParserFactory(final EdiParseManager ediParseManager) {
        this.ediParseManager = ediParseManager;
    }

    @Override
    public SdmxBeans getSdmxBeans(ReadableDataLocation dataLocation) {
        SDMX_SCHEMA schemaVersion = null;
        try {
            schemaVersion = SdmxMessageUtil.getSchemaVersion(dataLocation);
        } catch (Throwable th) {
            return null;
        }

        if (schemaVersion != SDMX_SCHEMA.EDI) {
            return null;
        }
        LOG.info("Parse EDI Message");
        EDIWorkspace ediWorkspace = ediParseManager.parseEDIMessage(dataLocation);
        if (ediWorkspace.hasStructures()) {
            return ediWorkspace.getMergedBeans();
        }
        return new SdmxBeansImpl();
    }
}
