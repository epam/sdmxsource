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
package org.sdmxsource.sdmx.api.model;

import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.header.HeaderBean;
import org.sdmxsource.sdmx.api.model.superbeans.SuperBeans;

import java.io.OutputStream;
import java.util.Map;
import java.util.Set;

/**
 * The structure workspace is built from an input source of SdmxBeans, the workspace provides means to retrieve the contained beans,
 * output the contained beans in different flavours and also provide a simple mechanism for retrieving subsets.
 * <p>
 * The structure workspace has the concept of what it was created with, and can supply, if given enough information the artifacts that
 * the workspace artifacts cross reference.
 */
public interface StructureWorkspace {

    /**
     * Returns the header that was present with the file.
     *
     * @return header
     */
    HeaderBean getHeader();

    /**
     * Writes all of the structures in the workspace to the specified OutputStream as a SDMX Structure document
     *
     * @param structureType          the structure type
     * @param out                    the out
     * @param includeCrossReferences the include cross references
     */
    void writeStructures(SDMX_SCHEMA structureType, OutputStream out, boolean includeCrossReferences);

    /**
     * Returns structure beans, if include cross references is set to true then it will also include
     * any cross referenced beans - if there were no cross references supplied, then an exception will
     * be thrown
     *
     * @param includeCrossReferences the include cross references
     * @return structure beans
     */
    SdmxBeans getStructureBeans(boolean includeCrossReferences);

    /**
     * Returns the super beans.
     * All the cross-referenced beans must be present for the super beans to be built successfully.
     *
     * @return super beans
     */
    SuperBeans getSuperBeans();

    /**
     * Returns a map containing identifiable keys against a set of identifiables that the
     * identifiable key cross references
     *
     * @return cross references
     */
    Map<IdentifiableBean, Set<IdentifiableBean>> getCrossReferences();

    /**
     * Returns a subset of the workspace, based on the query objects.
     *
     * @param query the query
     * @return subset workspace
     */
    StructureWorkspace getSubsetWorkspace(StructureReferenceBean... query);

    /**
     * Merges a structure workspace into the current workspace. It will not overwrite duplicates.
     *
     * @param workspace the workspace
     */
    void mergeWorkspace(StructureWorkspace workspace);
}
