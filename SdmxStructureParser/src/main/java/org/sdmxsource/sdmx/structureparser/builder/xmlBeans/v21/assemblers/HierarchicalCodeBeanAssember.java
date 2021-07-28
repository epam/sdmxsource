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

import org.sdmx.resources.sdmxml.schemas.v21.common.CodeReferenceType;
import org.sdmx.resources.sdmxml.schemas.v21.common.LocalCodeReferenceType;
import org.sdmx.resources.sdmxml.schemas.v21.common.LocalLevelReferenceType;
import org.sdmx.resources.sdmxml.schemas.v21.common.RefBaseType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.HierarchicalCodeType;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.codelist.HierarchicalCodeBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;


/**
 * Assembles a HierarchicalCodelist's CodeRef to facilitate Builder&lt;K,V&gt; for the top level bean.
 * v2.1 SDMX uses inheritance, so thus can we, unlike in previous versions.
 * Builder&lt;K,V&gt; doesn't lend itself to inheritance.
 */
public class HierarchicalCodeBeanAssember extends IdentifiableBeanAssembler implements Assembler<HierarchicalCodeType, HierarchicalCodeBean> {

    @Override
    public void assemble(HierarchicalCodeType assembleInto, HierarchicalCodeBean assembleFrom) throws SdmxException {
        // Populate it from inherited super
        assembleIdentifiable(assembleInto, assembleFrom);
        if (assembleFrom.getCodelistAliasRef() != null) {
            assembleInto.setCodelistAliasRef(assembleFrom.getCodelistAliasRef());
            LocalCodeReferenceType localRef = assembleInto.addNewCodeID();
            RefBaseType ref = localRef.addNewRef();
            ref.setId(assembleFrom.getCodeId());
        } else {
            CrossReferenceBean crossReference = assembleFrom.getCodeReference();
            CodeReferenceType code = assembleInto.addNewCode();
            setReference(code.addNewRef(), crossReference);
        }

        //LEVEL
        if (assembleFrom.getLevel(false) != null) {
            LocalLevelReferenceType ref = assembleInto.addNewLevel();
            ref.addNewRef().setId(assembleFrom.getLevel(false).getFullIdPath(false));
        }

        // Children
        for (HierarchicalCodeBean eachCodeRefBean : assembleFrom.getCodeRefs()) {
            HierarchicalCodeType eachHierarchicalCode = assembleInto.addNewHierarchicalCode();
            this.assemble(eachHierarchicalCode, eachCodeRefBean);
        }
    }

}
