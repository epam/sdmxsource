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

import org.sdmx.resources.sdmxml.schemas.v21.structure.HierarchicalCodelistType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.HierarchyType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.IncludedCodelistReferenceType;
import org.sdmxsource.sdmx.api.builder.Builder;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistRefBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.HierarchicalCodelistBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.HierarchyBean;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers.CodelistRefBeanAssembler;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers.HierarchyBeanAssembler;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers.MaintainableBeanAssembler;


/**
 * The type Hierarchical codelist xml builder.
 */
public class HierarchicalCodelistXmlBuilder extends MaintainableBeanAssembler implements Builder<HierarchicalCodelistType, HierarchicalCodelistBean> {

    private final CodelistRefBeanAssembler codelistRefBeanAssemblerBean = new CodelistRefBeanAssembler();

    private final HierarchyBeanAssembler hierarchyBeanAssemblerBean = new HierarchyBeanAssembler();

    @Override
    public HierarchicalCodelistType build(HierarchicalCodelistBean buildFrom) throws SdmxException {
        // Create outgoing build
        HierarchicalCodelistType builtObj = HierarchicalCodelistType.Factory.newInstance();
        // Populate it from inherited super
        assembleMaintainable(builtObj, buildFrom);
        // Populate it using this class's specifics
        // Code refs
        for (CodelistRefBean eachCodelistRef : buildFrom.getCodelistRef()) {
            IncludedCodelistReferenceType newCodelistRef = builtObj.addNewIncludedCodelist();
            codelistRefBeanAssemblerBean.assemble(newCodelistRef, eachCodelistRef);
        }
        // Hierarchies
        if (buildFrom.getHierarchies() != null) {
            for (HierarchyBean eachHierarchy : buildFrom.getHierarchies()) {
                HierarchyType newValueHierarchy = builtObj.addNewHierarchy();
                hierarchyBeanAssemblerBean.assemble(newValueHierarchy, eachHierarchy);
            }
        }
        return builtObj;
    }
}
