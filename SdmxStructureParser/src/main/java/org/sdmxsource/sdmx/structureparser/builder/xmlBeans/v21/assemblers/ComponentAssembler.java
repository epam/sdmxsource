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

import org.sdmx.resources.sdmxml.schemas.v21.common.ConceptReferenceType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.ComponentType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.RepresentationType;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.base.ComponentBean;


/**
 * The type Component assembler.
 */
public final class ComponentAssembler extends IdentifiableBeanAssembler {

    private final RepresentationXmlBeanBuilder representationXmlBeanBuilder = new RepresentationXmlBeanBuilder();

    /**
     * Assemble component.
     *
     * @param builtObj  the built obj
     * @param buildFrom the build from
     * @throws SdmxException the sdmx exception
     */
    public void assembleComponent(ComponentType builtObj, ComponentBean buildFrom) throws SdmxException {
        super.assembleIdentifiable(builtObj, buildFrom);
        if (buildFrom.getConceptRef() != null) {
            ConceptReferenceType conceptReference = builtObj.addNewConceptIdentity();
            super.setReference(conceptReference.addNewRef(), buildFrom.getConceptRef());
        }
        if (buildFrom.getRepresentation() != null) {
            RepresentationType representationType = builtObj.addNewLocalRepresentation();
            representationXmlBeanBuilder.assemble(representationType, buildFrom.getRepresentation());
        }
//		else {
//			RepresentationType representationType = builtObj.addNewLocalRepresentation();
//			representationType.addNewTextFormat();
//		}

    }
}
