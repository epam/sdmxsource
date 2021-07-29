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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21;

import org.sdmx.resources.sdmxml.schemas.v21.message.StructureDocument;
import org.sdmx.resources.sdmxml.schemas.v21.message.StructureHeaderType;
import org.sdmx.resources.sdmxml.schemas.v21.message.StructureType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.StructuresType;
import org.sdmxsource.sdmx.api.builder.Builder;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.registry.response.impl.v21.V2_1Helper;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers.StructureXmlBeanAssembler;


/**
 * Build a v2.1 SDMX Structure Document from SDMXBeans, by incorporating other builders for its parts.
 */
public class StructureXmlBeanBuilder implements Builder<StructureDocument, SdmxBeans> {

    private final StructureHeaderXmlBeanBuilder structureHeaderXmlBeanBuilder = new StructureHeaderXmlBeanBuilder();

    private final StructureXmlBeanAssembler xmlBeanAssembler = new StructureXmlBeanAssembler();

    @Override
    public StructureDocument build(SdmxBeans buildFrom) throws SdmxException {
        StructureDocument doc = StructureDocument.Factory.newInstance();
        StructureType structureType = doc.addNewStructure();

        // HEADER
        StructureHeaderType headerType;
        if (buildFrom.getHeader() != null) {
            headerType = structureHeaderXmlBeanBuilder.build(buildFrom.getHeader());
            structureType.setHeader(headerType);
        } else {
            headerType = (StructureHeaderType) structureType.addNewHeader();
            V2_1Helper.setHeader(headerType, buildFrom);
        }

        // TOP LEVEL STRUCTURES ELEMENT
        StructuresType structures = structureType.addNewStructures();

        xmlBeanAssembler.assemble(structures, buildFrom);
        return doc;
    }
}
