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

import java.util.List;


/**
 * Represents an SDMX Dimension List
 *
 * @author Matt Nelson
 */
public interface DimensionListBean extends IdentifiableBean {
    /**
     * The constant FIXED_ID.
     */
    static final String FIXED_ID = "DimensionDescriptor";

    /**
     * Returns the list of dimensions that this dimension list is holding.
     * <p>
     * The returned list will be a copy of the underlying list, and is expected to have at least one DimensionBean
     *
     * @return dimensions
     */
    List<DimensionBean> getDimensions();

}
