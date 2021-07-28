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
package org.sdmxsource.sdmx.api.model.beans.codelist;

import org.sdmxsource.sdmx.api.model.base.SdmxDate;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;

import java.util.List;


/**
 * Represents an SDMX Hierarchical Code
 *
 * @author Matt Nelson
 */
public interface HierarchicalCodeBean extends IdentifiableBean {

    /**
     * Returns the level of this code ref bean in the hierarchy, 1 indexed
     *
     * @return level in hierarchy
     */
    int getLevelInHierarchy();

    /**
     * Returns any child HierarchicalCodeBean beans as a copy of the underlying list.
     * <p>
     * Returns an empty list if there are no child beans
     *
     * @return the code refs
     */
    List<HierarchicalCodeBean> getCodeRefs();

    /**
     * Returns the code referenced by this CodeRef, this will never be null as it will be resolved from the codelist alias and code id
     *
     * @return code reference
     */
    CrossReferenceBean getCodeReference();

    /**
     * Returns the codelist alias used to resolve this codelist reference, returns null if the reference is achieved by using the
     * CrossReferenceBean (getCodeReference() returns a value)
     *
     * @return codelist alias ref
     */
    String getCodelistAliasRef();

    /**
     * Returns the code id of the code being referenced, to be used in conjunction with the codelist alias ref
     *
     * @return code id
     */
    String getCodeId();

    /**
     * Gets level.
     *
     * @param acceptDefault - if this code reference was not explicitly assigned a level, but the hierarchy is leveled then this code reference will default the level to that of the level in the same hierarchical level.  If true then will pass out the defaulted level if the level was not explicitly set on creation, if false, then will return the level if it was explicitly set, and will return null if it wasn't.
     * @return the level associated with this code
     */
    LevelBean getLevel(boolean acceptDefault);

    /**
     * Gets valid from.
     *
     * @return the valid from
     */
    SdmxDate getValidFrom();

    /**
     * Gets valid to.
     *
     * @return the valid to
     */
    SdmxDate getValidTo();
}
