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

import org.apache.log4j.Logger;
import org.sdmxsource.sdmx.api.engine.StructureWriterEngine;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.ediparser.manager.EdiParseManager;
import org.sdmxsource.sdmx.util.beans.container.SdmxBeansImpl;

import java.io.OutputStream;
import java.util.Objects;

/**
 * The type Structure writing engine edi.
 */
//JAVADOC missing
public class StructureWritingEngineEDI implements StructureWriterEngine {
    private static final Logger LOG = Logger.getLogger(StructureWritingEngineEDI.class);

    private final EdiParseManager editParseManager;

    private final OutputStream out;

    /**
     * Instantiates a new Structure writing engine edi.
     *
     * @param out              the out
     * @param editParseManager the edit parse manager
     */
    public StructureWritingEngineEDI(
            final OutputStream out, final EdiParseManager editParseManager) {
        this.out = Objects.requireNonNull(out, "out");
        this.editParseManager = Objects.requireNonNull(editParseManager, "editParseManager");
    }

    @Override
    public void writeStructure(MaintainableBean bean) {
        SdmxBeans beans = new SdmxBeansImpl();
        beans.addIdentifiable(bean);
        writeStructures(beans);
    }

    @Override
    public void writeStructures(SdmxBeans beans) {
        if (editParseManager == null) {
            throw new RuntimeException("'structureReaderEngine' is null");
        }
        LOG.info("Write structures in SDMX-EDI");
        editParseManager.writeToEDI(beans, out);
    }
}
