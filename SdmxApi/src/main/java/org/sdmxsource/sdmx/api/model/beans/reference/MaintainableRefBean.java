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

import java.io.Serializable;

/**
 * The interface Maintainable ref bean.
 */
public interface MaintainableRefBean extends Serializable {

    /**
     * Returns the maintainable id attribute
     *
     * @return agency id
     */
    String getAgencyId();

    /**
     * Returns true if there is an agency Id set
     *
     * @return boolean
     */
    boolean hasAgencyId();

    /**
     * Returns the maintainable id attribute
     *
     * @return maintainable id
     */
    String getMaintainableId();

    /**
     * Returns true if there is a maintainable id set
     *
     * @return boolean
     */
    boolean hasMaintainableId();

    /**
     * Returns the version attribute
     *
     * @return version
     */
    String getVersion();

    /**
     * Returns true if there is a value for version set
     *
     * @return boolean
     */
    boolean hasVersion();

}
