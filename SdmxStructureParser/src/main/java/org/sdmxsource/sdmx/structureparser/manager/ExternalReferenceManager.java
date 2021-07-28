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
package org.sdmxsource.sdmx.structureparser.manager;

import org.sdmxsource.sdmx.api.exception.CrossReferenceException;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxReferenceException;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;

/**
 * Manages the retrieval of externally referenced structures.  These are determined by the isExternal attribute of a maintainable artifact being set
 * to true, and the URI attribute containing the URI of the full artifact.
 */
public interface ExternalReferenceManager {
    /**
     * Resolves external references, where the 'externalReference' attribute is set to 'true'.
     * The external reference locations are expected to be given by a URI, and the URI is expected to point to the
     * location of a valid SDMX document containing the referenced structure.
     * <p>
     * External references can be of a different version to those that created the input StructureBeans.
     *
     * @param structures   containing structures which may have the external reference attribute set to `true`
     * @param isSubstitute if set to true, this will substitute the external reference beans for the real beans
     * @param isLienient   the is lienient
     * @return a StructureBeans containing only the externally referenced beans
     * @throws SdmxException           the sdmx exception
     * @throws CrossReferenceException the cross reference exception
     */
    SdmxBeans resolveExternalReferences(SdmxBeans structures, boolean isSubstitute, boolean isLienient) throws SdmxException, CrossReferenceException;
}
