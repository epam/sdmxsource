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

import org.apache.xmlbeans.XmlException;
import org.sdmx.resources.sdmxml.schemas.v21.message.RegistryInterfaceDocument;
import org.sdmxsource.sdmx.api.builder.Builder;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.REGISTRY_MESSAGE_TYPE;
import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.util.sdmx.SdmxMessageUtil;
import org.sdmxsource.springutil.xml.XMLParser;
import org.sdmxsource.util.io.StreamUtil;
import org.sdmxsource.util.xml.XmlUtil;

import java.io.IOException;
import java.util.List;


/**
 * The type Abstract response builder.
 *
 * @param <T> the type parameter
 */
public abstract class AbstractResponseBuilder<T> implements Builder<List<T>, ReadableDataLocation> {

    @Override
    public List<T> build(ReadableDataLocation buildFrom) throws SdmxException {
        //2. Validate the response is XML
        if (!XmlUtil.isXML(buildFrom)) {
            throw new SdmxSemmanticException(ExceptionCode.PARSE_ERROR_NOT_XML, new String(StreamUtil.toByteArray(buildFrom.getInputStream())));
        }

        //3. Validate it is valid SDMX-ML
        SDMX_SCHEMA schemaVersion = SdmxMessageUtil.getSchemaVersion(buildFrom);
        XMLParser.validateXML(buildFrom, schemaVersion);

        REGISTRY_MESSAGE_TYPE message = SdmxMessageUtil.getRegistryMessageType(buildFrom);
        if (message != getExpectedMessageType()) {
            String type = message.getType();
            throw new SdmxSemmanticException("Expected '" + getExpectedMessageType().getType() + "' message, got " + type);
        }
        switch (schemaVersion) {
            case VERSION_TWO_POINT_ONE:
                RegistryInterfaceDocument rid;
                try {
                    rid = RegistryInterfaceDocument.Factory.parse(buildFrom.getInputStream());
                } catch (XmlException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    buildFrom.close();
                }
                return buildInternal(rid);
        }
        throw new SdmxNotImplementedException(schemaVersion);
    }

    /**
     * Build internal list.
     *
     * @param rid the rid
     * @return the list
     */
    abstract List<T> buildInternal(RegistryInterfaceDocument rid);

    /**
     * Gets expected message type.
     *
     * @return the expected message type
     */
    abstract REGISTRY_MESSAGE_TYPE getExpectedMessageType();
}
