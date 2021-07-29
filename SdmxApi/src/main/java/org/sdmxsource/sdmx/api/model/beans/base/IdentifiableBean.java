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
package org.sdmxsource.sdmx.api.model.beans.base;

import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;

import java.net.URI;
import java.util.List;


/**
 * An Identifiable Bean describes a Bean which contains a unique identifier (urn) and can therefore
 * be identified uniquely.
 *
 * @author Matt Nelson
 */
public interface IdentifiableBean extends AnnotableBean, SdmxStructureBean {

    /**
     * Returns the id for this component, this is a mandatory field and will never be null.
     *
     * @return id id
     */
    String getId();

    /**
     * Returns a period separated id of this identifiable, starting from the non-maintainable top-level ancestor to this identifiable.
     * <p>
     * For example, if this is "Category A" as a child of "Category AA", then this method will return AA.A (note the category scheme id is not present in this identifier).
     * <p>
     * Returns null if this is a maintainable bean.
     *
     * @param includeDifferentTypes if true will recurse all the way back up the tree regardless of ancestor types, up to the maintainable level. If false, will stop at the first occurrence of an ancestor who's getStructureType() is not equal to this.getStructureType().
     * @return full id path
     */
    String getFullIdPath(boolean includeDifferentTypes);

    /**
     * Returns the URN for this component.
     * The URN is unique to this instance and is a computable generated value based on other attributes set within the component.
     *
     * @return urn urn
     */
    String getUrn();

    /**
     * Returns the URI for this component, returns null if there is no URI.
     * <p>
     * URI describes where additional information can be found for this component. This is guaranteed to return
     * a value if the structure is a <code>MaintainableBean</code> and <code>isExternalReference</code> is true.
     *
     * @return uri uri
     */
    URI getUri();

    /**
     * Builds a StructureReferenceBean that is a representation of this IdentifiableBean as a reference.
     * The returned StructureReferenceBean can be used to uniquely identify this identifiable bean.
     *
     * @return structure reference bean
     */
    StructureReferenceBean asReference();

    /**
     * Returns a list of all the underlying text types for this identifiable (does not recurse down to children).
     * <p>
     * Returns an empty list if there are no text types for this identifiable.
     *
     * @return all text types
     */
    List<TextTypeWrapper> getAllTextTypes();

}
