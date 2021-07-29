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
package org.sdmxsource.sdmx.ediparser.manager.impl;

import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.factory.WriteableDataLocationFactory;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.ediparser.engine.EdiParseEngine;
import org.sdmxsource.sdmx.ediparser.engine.impl.EdiParseEngineImpl;
import org.sdmxsource.sdmx.ediparser.engine.reader.EDIStructureReaderEngine;
import org.sdmxsource.sdmx.ediparser.engine.reader.impl.EDIStructureReaderEngineImpl;
import org.sdmxsource.sdmx.ediparser.engine.writer.impl.EDIStructureWriterEngineImpl;
import org.sdmxsource.sdmx.ediparser.manager.EdiParseManager;
import org.sdmxsource.sdmx.ediparser.model.EDIWorkspace;
import org.sdmxsource.sdmx.ediparser.model.impl.EDIWorkspaceImpl;
import org.sdmxsource.util.factory.SdmxSourceWriteableDataLocationFactory;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * The type Edi parse manager.
 */
public class EdiParseManagerImpl implements EdiParseManager {

    private final EDIStructureReaderEngine structureReaderEngine;
    private final EdiParseEngine ediParseEngine;
    private final EDIStructureWriterEngineImpl ediStructureEngine;
    private final WriteableDataLocationFactory writeableDataLocationFactory;

    /**
     * Instantiates a new Edi parse manager.
     */
    public EdiParseManagerImpl() {
        this(null, null, null, null);
    }

    /**
     * Instantiates a new Edi parse manager.
     *
     * @param writeableDataLocationFactory the writeable data location factory
     * @param structureReaderEngine        the structure reader engine
     * @param ediParseEngine               the edi parse engine
     * @param ediStructureEngine           the edi structure engine
     */
    public EdiParseManagerImpl(
            final WriteableDataLocationFactory writeableDataLocationFactory,
            final EDIStructureReaderEngine structureReaderEngine,
            final EdiParseEngine ediParseEngine,
            final EDIStructureWriterEngineImpl ediStructureEngine) {
        this.writeableDataLocationFactory = writeableDataLocationFactory != null ? writeableDataLocationFactory : new SdmxSourceWriteableDataLocationFactory();
        this.structureReaderEngine = structureReaderEngine != null ? structureReaderEngine : new EDIStructureReaderEngineImpl();
        this.ediParseEngine = ediParseEngine != null ? ediParseEngine : new EdiParseEngineImpl();
        this.ediStructureEngine = ediStructureEngine;
    }

    @Override
    public EDIWorkspace parseEDIMessage(ReadableDataLocation ediMessageLocation) {
        return new EDIWorkspaceImpl(writeableDataLocationFactory, structureReaderEngine, ediParseEngine, ediMessageLocation);
    }

    @Override
    public void writeToEDI(SdmxBeans beans, OutputStream out) {
        Objects.requireNonNull(ediStructureEngine, "ediStructureEngine");
        //Check that what is being written is supported by EDI
        validateSupport(beans);

        ediStructureEngine.writeToEDI(beans, out);
    }

    /**
     * Validates all the Maintainable Artefacts in the beans container are supported by the SDMX v1.0 syntax
     *
     * @param beans
     */
    private void validateSupport(SdmxBeans beans) {
        List<SDMX_STRUCTURE_TYPE> supportedStructres = new ArrayList<SDMX_STRUCTURE_TYPE>();
        supportedStructres.add(SDMX_STRUCTURE_TYPE.AGENCY_SCHEME);
        supportedStructres.add(SDMX_STRUCTURE_TYPE.DSD);
        supportedStructres.add(SDMX_STRUCTURE_TYPE.CONCEPT_SCHEME);
        supportedStructres.add(SDMX_STRUCTURE_TYPE.CODE_LIST);

        for (MaintainableBean maintainableBean : beans.getAllMaintainables()) {
            if (!supportedStructres.contains(maintainableBean.getStructureType())) {
                throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, maintainableBean.getStructureType().getType() + " is not a supported by SDMX-EDI");
            }
        }
    }
}
