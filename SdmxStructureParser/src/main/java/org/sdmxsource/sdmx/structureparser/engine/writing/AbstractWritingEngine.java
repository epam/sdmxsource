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

import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.api.constants.SdmxConstants;
import org.sdmxsource.sdmx.api.engine.StructureWriterEngine;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.util.beans.container.SdmxBeansImpl;

import java.io.IOException;
import java.io.OutputStream;


/**
 * The type Abstract writing engine.
 */
public abstract class AbstractWritingEngine implements StructureWriterEngine {

    private OutputStream outputStream;
    private boolean prettyfy;
    private SDMX_SCHEMA schemaVersion;

    private SchemaLocationWriter schemaLocationWriter;

    /**
     * Instantiates a new Abstract writing engine.
     *
     * @param schemaVersion the schema version
     * @param outputStream  the output stream
     * @param prettyfy      the prettyfy
     */
    public AbstractWritingEngine(SDMX_SCHEMA schemaVersion, OutputStream outputStream, boolean prettyfy) {
        this.outputStream = outputStream;
        this.schemaVersion = schemaVersion;
        this.prettyfy = prettyfy;
    }

    /**
     * Sets schema location writer.
     *
     * @param schemaLocationWriter the schema location writer
     */
    public void setSchemaLocationWriter(final SchemaLocationWriter schemaLocationWriter) {
        this.schemaLocationWriter = schemaLocationWriter;
    }

    @Override
    public void writeStructure(MaintainableBean bean) {
        SdmxBeans beans = new SdmxBeansImpl();
        beans.addIdentifiable(bean);
        writeStructures(beans);
    }

    @Override
    public void writeStructures(SdmxBeans beans) {
        XmlObject doc = build(beans);
        try {
            if (schemaLocationWriter != null) {
                String schemaUri = null;
                switch (schemaVersion) {
                    case VERSION_ONE:
                        schemaUri = SdmxConstants.MESSAGE_NS_1_0;
                        break;
                    case VERSION_TWO:
                        schemaUri = SdmxConstants.MESSAGE_NS_2_0;
                        break;
                    case VERSION_TWO_POINT_ONE:
                        schemaUri = SdmxConstants.MESSAGE_NS_2_1;
                        break;
                    default:
                        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "Schema Version " + schemaVersion);
                }
                schemaLocationWriter.writeSchemaLocation(doc, schemaUri);
            }
            if (prettyfy) {
                XmlOptions options = new XmlOptions();
                options.setSaveAggressiveNamespaces();
                options.setSavePrettyPrint();
                doc.save(outputStream, options);
            } else {
                doc.save(outputStream);
            }
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);  //TODO Exception
        }
    }


    /**
     * Build xml object.
     *
     * @param beans the beans
     * @return the xml object
     */
    protected abstract XmlObject build(SdmxBeans beans);
}
