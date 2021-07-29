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
package org.sdmxsource.sdmx.api.model.superbeans.codelist;

import org.sdmxsource.sdmx.api.model.superbeans.base.MaintainableSuperBean;

import java.util.Set;


/**
 * A hierarchical codelist is made up of one or more hierarchies,
 * each containing codes, which can be hierarchical, where the definition
 * of each code is in a SDMX codelist.
 * <p>
 * Note that the hierarchical codelist allows the same code to be used in more than one hierarchy.
 */
public interface HierarchicalCodelistSuperBean extends MaintainableSuperBean {

    /**
     * Returns the set of hierarchies that this hierarchical codelist
     * has reference to
     *
     * @return hierarchies
     */
    Set<HierarchySuperBean<HierarchicalCodelistSuperBean>> getHierarchies();

}
