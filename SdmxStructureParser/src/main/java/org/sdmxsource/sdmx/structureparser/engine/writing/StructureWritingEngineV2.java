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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.xmlbeans.XmlObject;
import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v2.StructureXmlBeanBuilder;

import java.io.OutputStream;


/**
 * The type Structure writing engine v 2.
 */
public class StructureWritingEngineV2 extends AbstractWritingEngine {
    private static final Logger LOG = LogManager.getLogger(StructureWritingEngineV2.class);

    private final StructureXmlBeanBuilder structureXmlBeanBuilderBean;

    /**
     * Instantiates a new Structure writing engine v 2.
     *
     * @param outputStream                the output stream
     * @param prettyFy                    the pretty fy
     * @param structureXmlBeanBuilderBean the structure xml bean builder bean
     */
    public StructureWritingEngineV2(OutputStream outputStream, boolean prettyFy, final StructureXmlBeanBuilder structureXmlBeanBuilderBean) {
        super(SDMX_SCHEMA.VERSION_TWO, outputStream, prettyFy);
        this.structureXmlBeanBuilderBean = structureXmlBeanBuilderBean;
    }

    /**
     * Instantiates a new Structure writing engine v 2.
     *
     * @param outputStream                the output stream
     * @param structureXmlBeanBuilderBean the structure xml bean builder bean
     */
    public StructureWritingEngineV2(OutputStream outputStream, final StructureXmlBeanBuilder structureXmlBeanBuilderBean) {
        super(SDMX_SCHEMA.VERSION_TWO, outputStream, true);
        this.structureXmlBeanBuilderBean = structureXmlBeanBuilderBean;
    }

    @Override
    protected XmlObject build(SdmxBeans beans) {
        LOG.info("Write structures in SDMX 2.0");
        return structureXmlBeanBuilderBean.build(beans);
    }
}
