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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans.registry.response;

import org.apache.xmlbeans.XmlObject;
import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.api.model.beans.registry.RegistrationBean;

import java.util.Collection;


/**
 * A class supporting this interface can build error and success responses for QueryRegistrationResponses.
 */
public interface QueryRegistrationResponseBuilder {

    /**
     * Returns a QueryRegistrationResponse with a failure status along with an
     * error message generated from the Throwable
     *
     * @param th            the th
     * @param schemaVersion the schema version
     * @return xml object
     */
    XmlObject buildErrorResponse(Throwable th, SDMX_SCHEMA schemaVersion);

    /**
     * Builds a QueryRegistrationResponse based on the registrations supplied.
     *
     * @param registrations - the registration to be output in the response XML
     * @param schemaVersion - the schema version to output the response in
     * @return xml object
     */
    XmlObject buildSuccessResponse(Collection<RegistrationBean> registrations, SDMX_SCHEMA schemaVersion);
}
