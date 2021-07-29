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

import org.sdmxsource.sdmx.api.model.mutable.base.AgencySchemeMutableBean;

/**
 * Represents an SDMX AgencyScheme
 *
 * @author Matt Nelson
 */
public interface AgencySchemeBean extends OrganisationSchemeBean<AgencyBean> {
    /**
     * The constant DEFAULT_SCHEME.
     */
    static final String DEFAULT_SCHEME = "SDMX"; // AgencyId
    /**
     * The constant FIXED_ID.
     */
    static final String FIXED_ID = "AGENCIES";
    /**
     * The constant FIXED_VERSION.
     */
    static final String FIXED_VERSION = "1.0";

    /**
     * Returns a representation of itself in a bean which can be modified, modifications to the mutable bean
     * are not reflected in the instance of the MaintainableBean.
     *
     * @return the mutable instance
     */
    @Override
    AgencySchemeMutableBean getMutableInstance();

    /**
     * Returns true if the agency Id of the scheme is that same as AgencySchemeBean.DEFAULT_SCHEME
     *
     * @return is default scheme
     */
    boolean isDefaultScheme();

}
