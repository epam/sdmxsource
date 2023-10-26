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

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.AttachmentLevelType;
import org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.AttributeType;
import org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.ComponentsType;
import org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.KeyFamilyType;
import org.sdmxsource.sdmx.api.builder.Builder;
import org.sdmxsource.sdmx.api.constants.ATTRIBUTE_ATTACHMENT_LEVEL;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.datastructure.AttributeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.GroupBean;
import org.sdmxsource.util.ObjectUtil;

import java.util.List;


/**
 * The type Data structure xml bean builder.
 */
public class DataStructureXmlBeanBuilder extends AbstractBuilder implements Builder<KeyFamilyType, DataStructureBean> {

    static {
        log = LoggerFactory.getLogger(DataStructureXmlBeanBuilder.class);
    }

    private final DimensionXmlBeanBuilder dimensionXmlBeanBuilder = new DimensionXmlBeanBuilder();
    private final TimeDimensionXmlBeanBuilder timeDimensionXmlBeanBuilder = new TimeDimensionXmlBeanBuilder();
    private final GroupXmlBeansBuilder groupXmlBeansBuilder = new GroupXmlBeansBuilder();
    private final PrimaryMeasureXmlBeanBuilder primaryMeasureXmlBeanBuilder = new PrimaryMeasureXmlBeanBuilder();
    private final AttributeXmlBeanBuilder attributeXmlBeanBuilder = new AttributeXmlBeanBuilder();

    @Override
    public KeyFamilyType build(DataStructureBean buildFrom) throws SdmxException {
        KeyFamilyType builtObj = KeyFamilyType.Factory.newInstance();
        if (validString(buildFrom.getAgencyId())) {
            builtObj.setAgency(buildFrom.getAgencyId());
        }
        if (validString(buildFrom.getId())) {
            builtObj.setId(buildFrom.getId());
        }
        if (buildFrom.getUri() != null) {
            builtObj.setUri(buildFrom.getUri().toString());
        } else if (buildFrom.getStructureURL() != null) {
            builtObj.setUri(buildFrom.getStructureURL().toString());
        } else if (buildFrom.getServiceURL() != null) {
            builtObj.setUri(buildFrom.getStructureURL().toString());
        }
        if (validString(buildFrom.getVersion())) {
            builtObj.setVersion(buildFrom.getVersion());
        }
        if (validCollection(buildFrom.getNames())) {
            builtObj.setNameArray(getTextType(buildFrom.getNames()));
        }
        if (hasAnnotations(buildFrom)) {
            builtObj.setAnnotations(getAnnotationsType(buildFrom));
        }

        ComponentsType componentsType = builtObj.addNewComponents();

        if (ObjectUtil.validCollection(buildFrom.getDimensions(SDMX_STRUCTURE_TYPE.DIMENSION, SDMX_STRUCTURE_TYPE.MEASURE_DIMENSION))) {
            for (DimensionBean currentDim : buildFrom.getDimensions(SDMX_STRUCTURE_TYPE.DIMENSION, SDMX_STRUCTURE_TYPE.MEASURE_DIMENSION)) {
                componentsType.getDimensionList().add(dimensionXmlBeanBuilder.build(currentDim));
            }
        }
        if (buildFrom.getTimeDimension() != null) {
            componentsType.setTimeDimension(timeDimensionXmlBeanBuilder.build(buildFrom.getTimeDimension()));
        }
        if (ObjectUtil.validCollection(buildFrom.getGroups())) {
            for (GroupBean currentGroup : buildFrom.getGroups()) {
                componentsType.getGroupList().add(groupXmlBeansBuilder.build(currentGroup));
            }
        }
        if (buildFrom.getPrimaryMeasure() != null) {
            componentsType.setPrimaryMeasure(primaryMeasureXmlBeanBuilder.build(buildFrom.getPrimaryMeasure()));
        }
        if (ObjectUtil.validCollection(buildFrom.getAttributes())) {
            for (AttributeBean currentAttr : buildFrom.getAttributes()) {
                AttributeType attribute = attributeXmlBeanBuilder.build(currentAttr);
                if (currentAttr.getAttachmentLevel() == ATTRIBUTE_ATTACHMENT_LEVEL.DIMENSION_GROUP) {
                    //If the group of dimensions is also a group, do not create the attribute;
                    List<String> dimensionReferences = currentAttr.getDimensionReferences();
                    for (GroupBean grp : buildFrom.getGroups()) {
                        if (grp.getDimensionRefs().containsAll(dimensionReferences) &&
                                dimensionReferences.containsAll(grp.getDimensionRefs())) {
                            attribute.setAttachmentLevel(AttachmentLevelType.GROUP);
                            attribute.addAttachmentGroup(grp.getId());
                            break;
                        }
                    }
                }
                componentsType.getAttributeList().add(attribute);
            }
        }
        return builtObj;
    }
}
