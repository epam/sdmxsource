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

import org.sdmx.resources.sdmxml.schemas.v21.common.RefBaseType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.ItemAssociationType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.ItemSchemeMapType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.mapping.ItemMapBean;
import org.sdmxsource.sdmx.api.model.beans.mapping.ItemSchemeMapBean;
import org.sdmxsource.sdmx.sdmxbeans.util.XmlBeansEnumUtil;


/**
 * Assembles an ItemSchemeMap to facilitate Builder&lt;K,V&gt; for the top level bean.
 * v2.1 SDMX uses inheritance, so thus can we, unlike in previous versions.
 * Builder&lt;K,V&gt; doesn't lend itself to inheritance.
 * Unable to use Assembler interface because Java Generics doesn't allow multiple use of an interface.
 */
abstract public class AbstractItemSchemeMapBeanAssembler extends AbstractSchemeMapBeanAssembler {


    /**
     * Assemble scheme map.
     *
     * @param assembleInto the assemble into
     * @param assembleFrom the assemble from
     * @throws SdmxException the sdmx exception
     */
    public void assembleSchemeMap(ItemSchemeMapType assembleInto, ItemSchemeMapBean assembleFrom) throws SdmxException {
        // Populate it from inherited super
        assembleMap(assembleInto, assembleFrom);
        // Populate it using this class's specifics
        // Child maps
        for (ItemMapBean eachMapBean : assembleFrom.getItems()) {
            // Defer child creation to subclass
            ItemAssociationType newMap = createNewMap(assembleInto);
            // Annotations
            //TODO RSG AWAITING MODEL CHANGES
//			if(ObjectUtil.validCollection(eachMapBean.getAnnotations())) {
//				newMap.setAnnotations(getAnnotationsType(eachMapBean));
//			}
            // Common source and target id allocation
            RefBaseType newSourceRef = newMap.addNewSource().addNewRef();
            newSourceRef.setId(eachMapBean.getSourceId());
            RefBaseType newTargetRef = newMap.addNewTarget().addNewRef();
            newTargetRef.setId(eachMapBean.getTargetId());
            SDMX_STRUCTURE_TYPE stype = mapStructureType();
            if (stype != null) {
                newSourceRef.setClass1(XmlBeansEnumUtil.build(stype));
                newTargetRef.setClass1(XmlBeansEnumUtil.build(stype));
            }
        }
    }

    /**
     * Create new map item association type.
     *
     * @param assembleInto the assemble into
     * @return the item association type
     */
    abstract protected ItemAssociationType createNewMap(ItemSchemeMapType assembleInto);

    /**
     * Map structure type sdmx structure type.
     *
     * @return the sdmx structure type
     */
    abstract protected SDMX_STRUCTURE_TYPE mapStructureType(); //TODO RSG NOT FOR CONCEPT MAP = NULL
}
