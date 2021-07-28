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
package org.sdmxsource.sdmx.structureparser.builder.sdmxbeans;

import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;

/**
 * SDMXBeans builder builds SDMXBeans from XMLObjects
 */
public interface SdmxBeansBuilder {
    /**
     * Builds SdmxBeans from a V1 StructureDocument.
     * <p>
     * If the StructureDocument contains no Structures then there will be an empty SdmxBeans Object returned.
     * <p>
     * If the StructureDocument contains duplicate maintainable structures (defined by the same URN) - then an exception will be thrown
     *
     * @param structuresDoc the structures doc
     * @return sdmx beans
     */
    SdmxBeans build(org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.message.StructureDocument structuresDoc);

    /**
     * Builds SdmxBeans from a V2 StructureDocument.
     * <p>
     * If the StructureDocument contains no Structures then there will be an empty SdmxBeans Object returned.
     * <p>
     * If the StructureDocument contains duplicate maintainable structures (defined by the same URN) - then an exception will be thrown
     *
     * @param structuresDoc the structures doc
     * @return sdmx beans
     */
    SdmxBeans build(org.sdmx.resources.sdmxml.schemas.v20.message.StructureDocument structuresDoc);

    /**
     * Builds SdmxBeans from a QueryStructureResponseType.
     * <p>
     * If the QueryStructureResponseType contains no Structures then there will be an empty SdmxBeans Object returned.
     * <p>
     * If the RegistryInterface docuement, contains Structures, of which there are duplicate maintainable structures (defined by the same URN) - then an exception will be thrown
     *
     * @param rid the rid
     * @return sdmx beans
     */
    SdmxBeans build(org.sdmx.resources.sdmxml.schemas.v20.message.RegistryInterfaceDocument rid);

    /**
     * Builds SdmxBeans from a QueryStructureResponseType.
     * <p>
     * If the QueryStructureResponseType contains no Structures then there will be an empty SdmxBeans Object returned.
     * <p>
     * If the RegistryInterface docuement, contains Structures, of which there are duplicate maintainable structures (defined by the same URN) - then an exception will be thrown
     *
     * @param rid the rid
     * @return sdmx beans
     */
    SdmxBeans build(org.sdmx.resources.sdmxml.schemas.v21.message.RegistryInterfaceDocument rid);

    /**
     * Build sdmx beans.
     *
     * @param structuresDoc the structures doc
     * @return the sdmx beans
     */
    SdmxBeans build(org.sdmx.resources.sdmxml.schemas.v21.message.StructureDocument structuresDoc);
}
