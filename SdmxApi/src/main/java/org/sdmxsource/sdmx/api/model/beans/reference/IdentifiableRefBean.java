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
package org.sdmxsource.sdmx.api.model.beans.reference;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;

import java.io.Serializable;


/**
 * Used to reference an SDMX identifiable artifact
 *
 * @author Matt Nelson
 */
public interface IdentifiableRefBean extends Serializable {

    /**
     * Returns the identifiable structure that is being referenced (at this level of the referencing hierarchy)
     *
     * @return structure type
     */
    SDMX_STRUCTURE_TYPE getStructureType();

    /**
     * Returns the id of the identifiable that is being referenced (at this level of the referencing hierarchy)
     *
     * @return id
     */
    String getId();

    /**
     * Returns a reference to the child identifiable artifact that is being referenced.
     * returns null if there is no child identifiable being referenced (i.e. this is the end of the reference chain)
     *
     * @return child reference
     */
    IdentifiableRefBean getChildReference();

    /**
     * Returns a reference to the parent identifiable artifact (not maintainable) that this identifiable
     * artifact is a child of, returns null if the identifiable artifact has no parent identifiable artifact
     *
     * @return parent identifiable reference
     */
    IdentifiableRefBean getParentIdentifiableReference();

    /**
     * Returns a reference to the parent maintainable that this identifiable is a child of, this will never be null
     *
     * @return parent maintainable referece
     */
    StructureReferenceBean getParentMaintainableReferece();

    /**
     * Returns the matched identifiable bean, from the given identifiable - returns null if no match is found
     *
     * @param reference the reference
     * @return match
     */
    IdentifiableBean getMatch(IdentifiableBean reference);

}
