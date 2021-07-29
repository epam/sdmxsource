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

import org.sdmx.resources.sdmxml.schemas.v21.common.ItemSchemeReferenceBaseType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.RepresentationType;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.base.RepresentationBean;


/**
 * The type Representation xml bean builder.
 */
public class RepresentationXmlBeanBuilder extends AbstractBeanAssembler implements Assembler<RepresentationType, RepresentationBean> {

    private final TextFormatAssembler textFormatAssembler = new TextFormatAssembler();

    @Override
    public void assemble(RepresentationType assembleInto, RepresentationBean assembleFrom) throws SdmxException {
        if (assembleFrom.getRepresentation() != null) {
            ItemSchemeReferenceBaseType itemSchemeRefType = assembleInto.addNewEnumeration();
            super.setReference(itemSchemeRefType.addNewRef(), assembleFrom.getRepresentation());
            if (assembleFrom.getTextFormat() != null) {
                textFormatAssembler.assemble(assembleInto.addNewEnumerationFormat(), assembleFrom.getTextFormat());
            }
        } else if (assembleFrom.getTextFormat() != null) {
            textFormatAssembler.assemble(assembleInto.addNewTextFormat(), assembleFrom.getTextFormat());
        }
    }
}
