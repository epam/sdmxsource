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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans;

import org.apache.xmlbeans.XmlObject;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.api.constants.SdmxConstants;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.structureparser.engine.writing.SchemaLocationWriter;

/**
 * Adds schema location to response, if there is a schema location
 */
public abstract class XmlBeanBuilder {

    private final SchemaLocationWriter schemaLocationWriter = new SchemaLocationWriter();

    /**
     * Write schema location.
     *
     * @param doc           the doc
     * @param schemaVersion the schema version
     */
    protected void writeSchemaLocation(XmlObject doc, SDMX_SCHEMA schemaVersion) {
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
    }

}
