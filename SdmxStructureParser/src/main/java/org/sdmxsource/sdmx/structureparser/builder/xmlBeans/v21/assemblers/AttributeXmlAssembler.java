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
import org.sdmx.resources.sdmxml.schemas.v21.common.LocalDimensionReferenceType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.AttributeRelationshipType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.AttributeType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.UsageStatusType;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.datastructure.AttributeBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.util.ObjectUtil;


/**
 * The type Attribute xml assembler.
 */
public class AttributeXmlAssembler implements Assembler<AttributeType, AttributeBean> {

    private final ComponentAssembler componentAssembler = new ComponentAssembler();

    @Override
    public void assemble(AttributeType assembleInto, AttributeBean assembleFrom) throws SdmxException {
        componentAssembler.assembleComponent(assembleInto, assembleFrom);
        if (assembleFrom.getConceptRoles() != null) {
            for (CrossReferenceBean currentConceptRole : assembleFrom.getConceptRoles()) {
                ConceptReferenceType conceptRef = assembleInto.addNewConceptRole();
                componentAssembler.setReference(conceptRef.addNewRef(), currentConceptRole);
            }
        }
        if (assembleFrom.getAssignmentStatus() != null) {
            assembleInto.setAssignmentStatus(UsageStatusType.Enum.forString(assembleFrom.getAssignmentStatus()));
        }
        AttributeRelationshipType attributeRelationship = assembleInto.addNewAttributeRelationship();
        if (ObjectUtil.validCollection(assembleFrom.getDimensionReferences())) {
            for (String currentDimensionRef : assembleFrom.getDimensionReferences()) {
                LocalDimensionReferenceType dimRef = attributeRelationship.addNewDimension();
                dimRef.addNewRef().setId(currentDimensionRef);
            }
        } else if (ObjectUtil.validString(assembleFrom.getAttachmentGroup())) {
            attributeRelationship.addNewGroup().addNewRef().setId(assembleFrom.getAttachmentGroup());
        } else if (ObjectUtil.validString(assembleFrom.getPrimaryMeasureReference())) {
            attributeRelationship.addNewPrimaryMeasure().addNewRef().setId(assembleFrom.getPrimaryMeasureReference());
        } else {
            attributeRelationship.addNewNone();
        }
    }
}
