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
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;

/**
 * Any Classes implementing this interface are capable of building an object of one type K
 * from an object of another type V, using a given bean retrieval manager
 * <p>
 * &lt;K&gt;the type parameter
 * &lt;V&gt;the type parameter
 *
 * @author Richard
 */
public interface RetrievalBuilder<K, V> {

    /**
     * Builds an object of type K from an Object of type V, using a given structure retrieval manager
     *
     * @param buildFrom                An Object to build the output object from
     * @param sdmxBeanRetrievalManager to use to build bean references
     * @return Object of type K
     * @throws SdmxException - If anything goes wrong during the build process
     * @since 1.0
     */
    K build(V buildFrom, SdmxBeanRetrievalManager sdmxBeanRetrievalManager) throws SdmxException;
}
