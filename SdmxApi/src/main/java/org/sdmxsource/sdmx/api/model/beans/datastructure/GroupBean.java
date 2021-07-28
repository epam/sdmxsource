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
package org.sdmxsource.sdmx.api.model.beans.datastructure;

import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;

import java.util.List;


/**
 * A Group is responsible for grouping a subset of the Dimensions in a Data Structure and giving the group an id.
 * <p>
 * A group then contains its own unique id, which can then be referenced when reporting metadata attributes.
 *
 * @author Matt Nelson
 */
public interface GroupBean extends IdentifiableBean {

    /**
     * Returns the list of dimensions that this group is referencing - the list is in the order that the dimensions appear in the <code>KeyFamilyBean</code>.
     * The list is mutually exclusive with <code>getAttachmentConstraintRef()</code>, and will have a size of 1 or more only if <code>getAttachmentConstraintRef()</code>
     * is null. If <code>getAttachmentConstraintRef()</code> is not null then this method will return an empty list.
     *
     * <b>NOTE: </b>The list is a copy so modifying the returned set will not be reflected in the GroupBean instance.
     *
     * @return dimension refs
     */
    List<String> getDimensionRefs();

    /**
     * Returns a reference to an attachment constraint which defines the cube to which the metadata can be attached.
     *
     * @return attachment constraint ref
     */
    CrossReferenceBean getAttachmentConstraintRef();
}
