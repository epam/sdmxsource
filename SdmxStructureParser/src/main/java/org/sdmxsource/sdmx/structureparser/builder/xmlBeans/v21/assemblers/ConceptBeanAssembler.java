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

import org.sdmx.resources.sdmxml.schemas.v21.common.LocalItemReferenceType;
import org.sdmx.resources.sdmxml.schemas.v21.common.RefBaseType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.ConceptType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.RepresentationType;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptBean;
import org.sdmxsource.util.ObjectUtil;


/**
 * Assembles a ConceptBean to facilitate Builder&lt;K,V&gt; for the top level bean.
 * v2.1 SDMX uses inheritance, so thus can we, unlike in previous versions.
 * Builder&lt;K,V&gt; doesn't lend itself to inheritance.
 */
public class ConceptBeanAssembler extends NameableBeanAssembler implements Assembler<ConceptType, ConceptBean> {

    private final RepresentationXmlBeanBuilder representationXmlBeanBuilder = new RepresentationXmlBeanBuilder();

    @Override
    public void assemble(ConceptType assembleInto, ConceptBean assembleFrom) throws SdmxException {
        // Populate it from inherited super
        assembleNameable(assembleInto, assembleFrom);

        if (assembleFrom.getCoreRepresentation() != null) {
            RepresentationType representationType = assembleInto.addNewCoreRepresentation();
            representationXmlBeanBuilder.assemble(representationType, assembleFrom.getCoreRepresentation());
        }

        if (ObjectUtil.validString(assembleFrom.getParentConcept())) {
            LocalItemReferenceType parent = assembleInto.addNewParent();
            RefBaseType ref = parent.addNewRef();
            ref.setId(assembleFrom.getParentConcept());
        }
    }

}
