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
package org.sdmxsource.sdmx.api.builder;

import org.sdmxsource.sdmx.api.exception.SdmxException;


/**
 * Any Classes implementing this interface are capable of building an object of one type K
 * from an object of another type V.
 * <p>
 * &lt;K&gt;The type of object that gets built by the builder.
 * &lt;V&gt;The type of object that the builder requires to build the object from.
 *
 * @author Matt Nelson
 */
public interface Builder<K, V> {

    /**
     * Builds an object of type K from an Object of type V.
     *
     * @param buildFrom An Object to build the output object from
     * @return Object of type K
     * @throws SdmxException If anything goes wrong during the build process
     */
    K build(V buildFrom) throws SdmxException;
}
