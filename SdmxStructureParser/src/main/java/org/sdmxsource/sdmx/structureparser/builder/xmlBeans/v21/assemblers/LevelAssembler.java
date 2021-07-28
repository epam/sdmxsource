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

import org.sdmx.resources.sdmxml.schemas.v21.structure.LevelType;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.codelist.LevelBean;


/**
 * The type Level assembler.
 */
public class LevelAssembler extends NameableBeanAssembler implements Assembler<LevelType, LevelBean> {

    private final TextFormatAssembler textFormatAssembler = new TextFormatAssembler();

    @Override
    public void assemble(LevelType assembleInto, LevelBean assembleFrom) throws SdmxException {
        super.assembleNameable(assembleInto, assembleFrom);
        if (assembleFrom.getCodingFormat() != null) {
            textFormatAssembler.assemble(assembleInto.addNewCodingFormat(), assembleFrom.getCodingFormat());
        }
        if (assembleFrom.getChildLevel() != null) {
            this.assemble(assembleInto.addNewLevel(), assembleFrom.getChildLevel());
        }
    }
}
