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
package org.sdmxsource.sdmx.structureparser.factory;

import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.api.constants.STRUCTURE_OUTPUT_FORMAT;
import org.sdmxsource.sdmx.api.engine.StructureWriterEngine;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.factory.StructureWriterFactory;
import org.sdmxsource.sdmx.api.model.format.StructureFormat;
import org.sdmxsource.sdmx.ediparser.manager.impl.EdiParseManagerImpl;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.registry.response.impl.v2.QueryStructureResponseBuilderV2;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v2.StructureXmlBeanBuilder;
import org.sdmxsource.sdmx.structureparser.engine.writing.*;

import java.io.OutputStream;


/**
 * The type Sdmx structure writer factory.
 */
public class SdmxStructureWriterFactory implements StructureWriterFactory {

    private final StructureXmlBeanBuilder structureXmlBeanBuilderBean = new StructureXmlBeanBuilder();
    private final QueryStructureResponseBuilderV2 queryStructureResponseBuilderV2 = new QueryStructureResponseBuilderV2();

    @Override
    public StructureWriterEngine getStructureWriterEngine(StructureFormat sFormat, OutputStream out) {
        if (sFormat.getSdmxOutputFormat() != null) {
            STRUCTURE_OUTPUT_FORMAT outputFormat = sFormat.getSdmxOutputFormat();
            SDMX_SCHEMA schemaVersion = outputFormat.getOutputVersion();
            if (!outputFormat.isQueryResponse()) {
                if (!outputFormat.isRegistryDocument()) {
                    return getStructureBeanEngine(schemaVersion, out);
                } else {
                    return getRegistryStructureSubmitBeanEngine(schemaVersion, out);
                }
            }
            if (outputFormat == STRUCTURE_OUTPUT_FORMAT.SDMX_V2_REGISTRY_QUERY_RESPONSE_DOCUMENT) {
                return new RegistryQueryResponseWritingEngineV2(out, this.queryStructureResponseBuilderV2);
            }
        }
        return null;
    }

    private StructureWriterEngine getRegistryStructureSubmitBeanEngine(SDMX_SCHEMA schemaVersion, OutputStream out) {
        switch (schemaVersion) {
            case VERSION_TWO_POINT_ONE:
                return new StructureWritingEngineV21(out, true);
            default:
                throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, schemaVersion + " - StructureWritingManagerImpl.writeStructure");
        }
    }

    private StructureWriterEngine getStructureBeanEngine(SDMX_SCHEMA schemaVersion, OutputStream out) {
        switch (schemaVersion) {
            case VERSION_ONE:
                return new StructureWritingEngineV1(out);
            case VERSION_TWO:
                return new StructureWritingEngineV2(out, structureXmlBeanBuilderBean);
            case EDI:
                return new StructureWritingEngineEDI(out, new EdiParseManagerImpl(null, null, null, null));
            case VERSION_TWO_POINT_ONE:
                return new StructureWritingEngineV21(out, false);
            default:
                throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, schemaVersion + " - StructureWritingManagerImpl.writeStructure");
        }
    }
}
