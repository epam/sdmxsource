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

import org.sdmx.resources.sdmxml.schemas.v21.common.ObjectTypeCodelistType;
import org.sdmx.resources.sdmxml.schemas.v21.common.PackageTypeCodelistType;
import org.sdmx.resources.sdmxml.schemas.v21.common.RefBaseType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.*;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.mapping.ComponentMapBean;
import org.sdmxsource.sdmx.api.model.beans.mapping.RepresentationMapRefBean;
import org.sdmxsource.sdmx.api.model.beans.mapping.StructureMapBean;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers.Assembler;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers.NameableBeanAssembler;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers.TextFormatAssembler;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers.ToValueTypeTypeBuilder;

import java.util.Map;
import java.util.Set;


/**
 * The type Structure map bean assembler.
 */
public class StructureMapBeanAssembler extends NameableBeanAssembler implements Assembler<StructureMapType, StructureMapBean> {

    private final TextFormatAssembler textFormatAssembler = new TextFormatAssembler();

    private final ToValueTypeTypeBuilder toValueTypeTypeBuilder = new ToValueTypeTypeBuilder();

    /**
     * Sets component reference.
     *
     * @param ref              the ref
     * @param partialReference the partial reference
     */
    protected void setComponentReference(RefBaseType ref, String partialReference) {
        ref.setId(partialReference);
        ref.setContainerID("DIMENSION_LIST");
        ref.setPackage(PackageTypeCodelistType.DATASTRUCTURE);
        ref.setClass1(ObjectTypeCodelistType.DIMENSION);
    }

    @Override
    public void assemble(StructureMapType assembleInto, StructureMapBean assembleFrom) throws SdmxException {
        // Populate it using this class's specifics
        assembleNameable(assembleInto, assembleFrom);
        assembleInto.setIsExtension(assembleFrom.isExtension());
        // Source
        super.setReference(assembleInto.addNewSource().addNewRef(), assembleFrom.getSourceRef());
        super.setReference(assembleInto.addNewTarget().addNewRef(), assembleFrom.getTargetRef());

        // Child maps
        for (ComponentMapBean eachMapBean : assembleFrom.getComponents()) {
            // Defer child creation to subclass
            ComponentMapType newMap = assembleInto.addNewComponentMap();
            // Common source and target id allocation
            setComponentReference(newMap.addNewSource().addNewRef(), eachMapBean.getMapConceptRef());
            setComponentReference(newMap.addNewTarget().addNewRef(), eachMapBean.getMapTargetConceptRef());
            // Representation mapping
            if (eachMapBean.getRepMapRef() != null) {
                RepresentationMapRefBean repMapRef = eachMapBean.getRepMapRef();
                RepresentationMapType repMap = newMap.addNewRepresentationMapping();
                if (repMapRef.getCodelistMap() != null) {
                    RefBaseType codelistRef = repMap.addNewCodelistMap().addNewRef();
                    codelistRef.setId(repMapRef.getCodelistMap().getChildReference().getId());
                }
                if (repMapRef.getToTextFormat() != null) {
                    textFormatAssembler.assemble(repMap.addNewToTextFormat(), repMapRef.getToTextFormat());
                }
                if (repMapRef.getToValueType() != null) {
                    repMap.setToValueType(toValueTypeTypeBuilder.build(repMapRef.getToValueType()));
                }
                if (repMapRef.getValueMappings().size() > 0) {
                    ValueMapType vmt = repMap.addNewValueMap();

                    Map<String, Set<String>> valueMappings = repMapRef.getValueMappings();
                    for (String currentKey : valueMappings.keySet()) {
                        for (String currentValue : valueMappings.get(currentKey)) {
                            ValueMappingType vMappingType = vmt.addNewValueMapping();
                            vMappingType.setSource(currentKey);
                            vMappingType.setTarget(currentValue);
                        }
                    }
                }
            }
        }
    }


}
