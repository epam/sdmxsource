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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21;

import org.sdmx.resources.sdmxml.schemas.v21.structure.CodeType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.CodelistType;
import org.sdmxsource.sdmx.api.builder.Builder;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodeBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistBean;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers.CodeBeanAssembler;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers.MaintainableBeanAssembler;

import java.util.List;


/**
 * The type Codelist xml bean builder.
 */
public class CodelistXmlBeanBuilder extends MaintainableBeanAssembler implements Builder<CodelistType, CodelistBean> {

    private final CodeBeanAssembler codeBeanAssemblerBean = new CodeBeanAssembler();

    @Override
    public CodelistType build(CodelistBean buildFrom) throws SdmxException {
        // Create outgoing build
        CodelistType builtObj = CodelistType.Factory.newInstance();
        if (buildFrom.isPartial()) {
            builtObj.setIsPartial(true);
        }
        // Populate it from inherited super
        assembleMaintainable(builtObj, buildFrom);
        // Populate it using this class's specifics
        List<CodeBean> codes = buildFrom.getItems();
        if (codes.size() > 0) {
            CodeType[] type = new CodeType[codes.size()];
            int i = 0;
            for (CodeBean codeBean : codes) {
                CodeType newCode = builtObj.addNewCode();
                codeBeanAssemblerBean.assemble(newCode, codeBean);
                type[i] = newCode;
                i++;
            }
            builtObj.setCodeArray(type);
        }
        return builtObj;
    }
}
