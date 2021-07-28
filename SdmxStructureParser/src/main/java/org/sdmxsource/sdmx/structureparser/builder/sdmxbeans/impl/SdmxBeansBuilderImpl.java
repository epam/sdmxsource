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
package org.sdmxsource.sdmx.structureparser.builder.sdmxbeans.impl;

import org.sdmx.resources.sdmxml.schemas.v20.message.RegistryInterfaceDocument;
import org.sdmx.resources.sdmxml.schemas.v20.message.StructureDocument;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.structureparser.builder.sdmxbeans.SdmxBeansBuilder;


/**
 * SDMXBeans builder builds SDMXBeans from XMLObjects.
 * <p>
 * The  XMLObjects reside in either Registry documents, structure documents, or query responses
 */
public class SdmxBeansBuilderImpl implements SdmxBeansBuilder {

    private final SdmxBeansV1Builder sdmxBeansV1Builder;

    private final SdmxBeansV2RegDocBuilder sdmxBeansV2RegDocBuilder;

    private final SdmxBeansV2StrucDocBuilder sdmxBeansV2StrucDocBuilder;

    private final SdmxBeansV21Builder sdmxBeansV21Builder;

    /**
     * Instantiates a new Sdmx beans builder.
     */
    public SdmxBeansBuilderImpl() {
        this(new SdmxBeansV1Builder(), new SdmxBeansV2RegDocBuilder(), new SdmxBeansV2StrucDocBuilder(), new SdmxBeansV21Builder());
    }

    /**
     * Instantiates a new Sdmx beans builder.
     *
     * @param sdmxBeansV1Builder         the sdmx beans v 1 builder
     * @param sdmxBeansV2RegDocBuilder   the sdmx beans v 2 reg doc builder
     * @param sdmxBeansV2StrucDocBuilder the sdmx beans v 2 struc doc builder
     * @param sdmxBeansV21Builder        the sdmx beans v 21 builder
     */
    public SdmxBeansBuilderImpl(
            final SdmxBeansV1Builder sdmxBeansV1Builder,
            final SdmxBeansV2RegDocBuilder sdmxBeansV2RegDocBuilder,
            final SdmxBeansV2StrucDocBuilder sdmxBeansV2StrucDocBuilder,
            final SdmxBeansV21Builder sdmxBeansV21Builder) {
        this.sdmxBeansV1Builder = sdmxBeansV1Builder;
        this.sdmxBeansV2RegDocBuilder = sdmxBeansV2RegDocBuilder;
        this.sdmxBeansV2StrucDocBuilder = sdmxBeansV2StrucDocBuilder;
        this.sdmxBeansV21Builder = sdmxBeansV21Builder;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    //////////			PROCESS 1.0  MESSAGES						///////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Build from Version 1.0 Structure Document
     */
    @Override
    public SdmxBeans build(org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.message.StructureDocument structuresDoc) {
        return sdmxBeansV1Builder.build(structuresDoc);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////
    //////////			PROCESS 2.0  MESSAGES						///////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Build from a Registry Document, this can be either a submit structure request, or a query structure response
     */
    @Override
    public SdmxBeans build(RegistryInterfaceDocument rid) {
        return sdmxBeansV2RegDocBuilder.build(rid);
    }

    /**
     * Build beans from a v2.0 Structure Document
     */
    @Override
    public SdmxBeans build(StructureDocument structuresDoc) {
        return sdmxBeansV2StrucDocBuilder.build(structuresDoc);
    }


    @Override
    public SdmxBeans build(org.sdmx.resources.sdmxml.schemas.v21.message.RegistryInterfaceDocument rid) {
        return sdmxBeansV21Builder.build(rid);
    }


    @Override
    public SdmxBeans build(org.sdmx.resources.sdmxml.schemas.v21.message.StructureDocument structuresDoc) {
        return sdmxBeansV21Builder.build(structuresDoc);
    }

}
