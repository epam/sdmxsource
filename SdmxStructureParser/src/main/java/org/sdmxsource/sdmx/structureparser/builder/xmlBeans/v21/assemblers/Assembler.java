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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers;

import org.sdmxsource.sdmx.api.exception.SdmxException;

/**
 * Any Classes implementing this interface are capable of assembling a given object of one type &lt;K&gt;
 * from an object of another type &lt;V&gt;.
 * <p>
 * &lt;K&gt; given object to be assembled
 * &lt;V&gt; given object to source assembly data from
 *
 * @param <K> the type parameter
 * @param <V> the type parameter
 */
public interface Assembler<K, V> {

    /**
     * Assembles an object of type &lt;K&gt; from an Object of type &lt;V&gt;
     *
     * @param assembleInto An Object already instantiated that needs to be assembled using data from assembleFrom
     * @param assembleFrom the assemble from
     * @throws SdmxException - If anything goes wrong during the assemble process
     * @since 1.0
     */
    public void assemble(K assembleInto, V assembleFrom) throws SdmxException;
}
