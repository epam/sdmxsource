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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v1;

import org.apache.log4j.Logger;
import org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.AssignmentStatusType;
import org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.AttachmentLevelType;
import org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.AttributeType;
import org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.TextFormatType;
import org.sdmxsource.sdmx.api.builder.Builder;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.datastructure.AttributeBean;
import org.sdmxsource.sdmx.util.beans.ConceptRefUtil;
import org.sdmxsource.util.ObjectUtil;


/**
 * Builds a v1 CategorySchemeType from a schema independent CategorySchemeBean
 */
public class AttributeXmlBeanBuilder extends AbstractBuilder implements Builder<AttributeType, AttributeBean> {

    static {
        log = Logger.getLogger(AttributeXmlBeanBuilder.class);
    }

    @Override
    public AttributeType build(AttributeBean buildFrom) throws SdmxException {
        AttributeType builtObj = AttributeType.Factory.newInstance();
        if (hasAnnotations(buildFrom)) {
            builtObj.setAnnotations(getAnnotationsType(buildFrom));
        }
        if (validString(buildFrom.getAssignmentStatus())) {
            if (buildFrom.getAssignmentStatus().equals(AssignmentStatusType.CONDITIONAL.toString())) {
                builtObj.setAssignmentStatus(AssignmentStatusType.CONDITIONAL);
            } else if (buildFrom.getAssignmentStatus().equals(AssignmentStatusType.MANDATORY.toString())) {
                builtObj.setAssignmentStatus(AssignmentStatusType.MANDATORY);
            } else {
                throw new IllegalArgumentException("Unknown assignment status: " + buildFrom.getAssignmentStatus());
            }
        }
        if (ObjectUtil.validString(buildFrom.getAttachmentGroup())) {
            String[] arr = new String[1];
            arr[0] = buildFrom.getAttachmentGroup();
            builtObj.setAttachmentGroupArray(arr);
        }
        if (buildFrom.getAttachmentLevel() != null) {
            switch (buildFrom.getAttachmentLevel()) {
                case DATA_SET:
                    builtObj.setAttachmentLevel(AttachmentLevelType.DATA_SET);
                    break;
                case DIMENSION_GROUP:
                    builtObj.setAttachmentLevel(AttachmentLevelType.SERIES);
                    break;
                case OBSERVATION:
                    builtObj.setAttachmentLevel(AttachmentLevelType.OBSERVATION);
                    break;
                case GROUP:
                    builtObj.setAttachmentLevel(AttachmentLevelType.GROUP);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown attachment level: " + buildFrom.getAttachmentLevel());
            }
        }
        if (buildFrom.hasCodedRepresentation()) {
            builtObj.setCodelist(buildFrom.getRepresentation().getRepresentation().getMaintainableReference().getMaintainableId());
        }
        if (buildFrom.getConceptRef() != null) {
            builtObj.setConcept(ConceptRefUtil.getConceptId(buildFrom.getConceptRef()));
        }
        if (buildFrom.getId().equals("TIME_FORMAT")) {
            builtObj.setIsTimeFormat(true);
        }
        if (buildFrom.getRepresentation() != null && buildFrom.getRepresentation().getTextFormat() != null) {
            TextFormatType textFormatType = TextFormatType.Factory.newInstance();
            populateTextFormatType(textFormatType, buildFrom.getRepresentation().getTextFormat());
            builtObj.setTextFormat(textFormatType);
        }
        return builtObj;
    }


}
