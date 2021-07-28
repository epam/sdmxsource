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

import java.util.Set;

/**
 * An SdmxStructureBean is a bean which represents an SDMX Structural metadata artefact, such as a Code / Codelist etc.
 *
 * @author Matt Nelson
 */
public interface SdmxStructureBean extends SDMXBean {

    /**
     * Returns the parent that this SDMXBean belongs to
     * <p>
     * If this is a MaintainableBean, then there will be no parent to return, so will return a value of null
     *
     * @return the parent
     */
    @Override
    SdmxStructureBean getParent();

    /**
     * Returns the first identifiable parent of this SDMXBean
     * <p>
     * If this is a MaintainableBean, then there will be no parent to return, so will return a value of null
     *
     * @return identifiable parent
     */
    IdentifiableBean getIdentifiableParent();

    /**
     * Recurses up the parent tree to find the maintainable parent.  If this is a maintainable then
     * it will return a reference to itself
     *
     * @return maintainable parent
     */
    MaintainableBean getMaintainableParent();


    /**
     * Returns a set of identifiables that are contained within this identifiable
     *
     * @return identifiable composites
     */
    Set<IdentifiableBean> getIdentifiableComposites();
}
