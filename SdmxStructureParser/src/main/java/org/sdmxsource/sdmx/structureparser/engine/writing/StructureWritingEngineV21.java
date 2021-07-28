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
import org.apache.xmlbeans.XmlObject;
import org.sdmxsource.sdmx.api.constants.DATASET_ACTION;
import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.RegistrySubmitXmlBeanBuilder;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.StructureXmlBeanBuilder;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.rid.SubmitRegistrationBuilder;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.rid.SubmitSubscriptionBuilder;

import java.io.OutputStream;


/**
 * The type Structure writing engine v 21.
 */
public class StructureWritingEngineV21 extends AbstractWritingEngine {
    private static final Logger LOG = Logger.getLogger(StructureWritingEngineV21.class);

    private final StructureXmlBeanBuilder structureXmlBeanBuilderBean = new StructureXmlBeanBuilder();

    private final SubmitRegistrationBuilder registrationXmlBeanBuilderBean = new SubmitRegistrationBuilder();

    private final RegistrySubmitXmlBeanBuilder registrySubmitXmlBeanBuilderBean = new RegistrySubmitXmlBeanBuilder();

    private final SubmitSubscriptionBuilder submitSubscriptionBuilder = new SubmitSubscriptionBuilder();

    private boolean isRegistrySubmitDocument;

    /**
     * Instantiates a new Structure writing engine v 21.
     *
     * @param outputStream             the output stream
     * @param isRegistrySubmitDocument the is registry submit document
     * @param prettyFy                 the pretty fy
     */
    public StructureWritingEngineV21(OutputStream outputStream,
                                     boolean isRegistrySubmitDocument,
                                     boolean prettyFy) {
        super(SDMX_SCHEMA.VERSION_TWO_POINT_ONE, outputStream, prettyFy);
        this.isRegistrySubmitDocument = isRegistrySubmitDocument;
    }

    /**
     * Instantiates a new Structure writing engine v 21.
     *
     * @param outputStream the output stream
     */
    public StructureWritingEngineV21(OutputStream outputStream) {
        super(SDMX_SCHEMA.VERSION_TWO_POINT_ONE, outputStream, true);
        this.isRegistrySubmitDocument = false;
    }


    /**
     * Instantiates a new Structure writing engine v 21.
     *
     * @param outputStream             the output stream
     * @param isRegistrySubmitDocument the is registry submit document
     */
    public StructureWritingEngineV21(OutputStream outputStream, boolean isRegistrySubmitDocument) {
        super(SDMX_SCHEMA.VERSION_TWO_POINT_ONE, outputStream, true);
        this.isRegistrySubmitDocument = isRegistrySubmitDocument;
    }

    @Override
    protected XmlObject build(SdmxBeans beans) {
        boolean hasStructures = beans.hasStructures();
        boolean hasRegistrations = beans.hasRegistrations();
        boolean hasSubscriptions = beans.hasSubscriptions();
        if (hasStructures && hasRegistrations) {
            throw new IllegalArgumentException("Container sent to be written contains both structures and registrations, this can not be written out to a single SDMX Message");
        }
        if (hasStructures && hasRegistrations) {
            throw new IllegalArgumentException("Container sent to be written contains both structures and subscriptions, this can not be written out to a single SDMX Message");
        }
        if (hasSubscriptions && hasStructures) {
            throw new IllegalArgumentException("Container sent to be written contains both structures and subscriptions, this can not be written out to a single SDMX Message");
        }
        if (hasSubscriptions && hasRegistrations) {
            throw new IllegalArgumentException("Container sent to be written contains both registrations and subscriptions, this can not be written out to a single SDMX Message");
        }

        DATASET_ACTION action = beans.getAction();
        if (action == null) {
            action = DATASET_ACTION.APPEND;
        }

        if (hasRegistrations) {
            LOG.info("Write registrations in SDMX 2.1");

            return registrationXmlBeanBuilderBean.buildRegistryInterfaceDocument(beans.getRegistrations(), action);
        } else if (hasSubscriptions) {
            LOG.info("Write subscriptions in SDMX 2.1");
            return submitSubscriptionBuilder.buildRegistryInterfaceDocument(beans.getSubscriptions(), action);
        }
        LOG.info("Write structures in SDMX 2.1");
        if (isRegistrySubmitDocument) {
            return registrySubmitXmlBeanBuilderBean.build(beans);
        }
        return structureXmlBeanBuilderBean.build(beans);
    }
}

