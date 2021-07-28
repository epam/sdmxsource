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
package org.sdmxsource.sdmx.api.model.mutablesuperbeans.base;

import java.io.Serializable;


/**
 * An Identifiable Object is one which can be identified uniquely with a URN.
 * <p>
 * An identifiable also has other attributes such as name and description
 *
 * @author Matt Nelson
 */
public interface IdentifiableMutableSuperBean extends AnnotableMutableSuperBean, Serializable {


    /**
     * Returns the Id of the Identifiable Object
     *
     * @return id
     */
    String getId();

    /**
     * Sets id.
     *
     * @param id the id
     */
    void setId(String id);

    /**
     * Returns the URN of the Identifiable Object
     *
     * @return urn
     */
    String getUrn();

    /**
     * Sets urn.
     *
     * @param urn the urn
     */
    void setUrn(String urn);

}
