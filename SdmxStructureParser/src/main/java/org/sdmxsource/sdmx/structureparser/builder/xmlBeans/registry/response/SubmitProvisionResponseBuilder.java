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
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;

import java.util.Collection;


/**
 * A class supporting this interface can build error and success responses for submission of provisions.
 */
public interface SubmitProvisionResponseBuilder {

    /**
     * Returns an response based on the submitted provision, if there is a Throwable against the provision
     * then the error will be documented, and a status of failure will be put against it.
     *
     * @param response      - a map of provision, and a error message (if there is one)
     * @param schemaVersion - the version of the schema to output the response in
     * @return xml object
     */
    XmlObject buildSuccessResponse(Collection<ProvisionAgreementBean> response, SDMX_SCHEMA schemaVersion);

    /**
     * Build error response xml object.
     *
     * @param th            the th
     * @param sRef          the s ref
     * @param schemaVersion the schema version
     * @return the xml object
     */
    XmlObject buildErrorResponse(Throwable th, StructureReferenceBean sRef, SDMX_SCHEMA schemaVersion);

}
