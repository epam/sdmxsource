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

import org.sdmx.resources.sdmxml.schemas.v21.structure.ItemSchemeMapType;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.mapping.SchemeMapBean;


/**
 * Assembles an SchemeMap to facilitate Builder&lt;K,V&gt; for the top level bean.
 * v2.1 SDMX uses inheritance, so thus can we, unlike in previous versions.
 * Builder&lt;K,V&gt; doesn't lend itself to inheritance.
 * Unable to use Assembler interface because Java Generics doesn't allow multiple use of an interface.
 */
public class AbstractSchemeMapBeanAssembler extends NameableBeanAssembler {


    /**
     * Assemble map.
     *
     * @param assembleInto the assemble into
     * @param assembleFrom the assemble from
     * @throws SdmxException the sdmx exception
     */
    public void assembleMap(ItemSchemeMapType assembleInto, SchemeMapBean assembleFrom) throws SdmxException {
        // Populate it from inherited super
        assembleNameable(assembleInto, assembleFrom);
        super.setReference(assembleInto.addNewSource().addNewRef(), assembleFrom.getSourceRef());
        super.setReference(assembleInto.addNewTarget().addNewRef(), assembleFrom.getTargetRef());
    }
}
