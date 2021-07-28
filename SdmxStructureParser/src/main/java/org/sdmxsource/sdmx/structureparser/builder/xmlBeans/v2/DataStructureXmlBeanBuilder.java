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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v2;

import org.sdmx.resources.sdmxml.schemas.v20.common.IDType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.*;
import org.sdmxsource.sdmx.api.builder.Builder;
import org.sdmxsource.sdmx.api.constants.ATTRIBUTE_ATTACHMENT_LEVEL;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.base.ComponentBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.*;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.util.ObjectUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Data structure xml bean builder.
 */
public class DataStructureXmlBeanBuilder extends AbstractBuilder implements Builder<KeyFamilyType, DataStructureBean> {

    private final DimensionXmlBeanBuilder dimensionXmlBeanBuilder = new DimensionXmlBeanBuilder();

    private final CrossSectionalMeasureXmlBeanBuilder crossSectionalXmlBeanBuilder = new CrossSectionalMeasureXmlBeanBuilder();

    private final TimeDimensionXmlBeanBuilder timeDimensionXmlBeanBuilder = new TimeDimensionXmlBeanBuilder();

    private final GroupXmlBeansBuilder groupXmlBeansBuilder = new GroupXmlBeansBuilder();

    private final PrimaryMeasureXmlBeanBuilder primaryMeasureXmlBeanBuilder = new PrimaryMeasureXmlBeanBuilder();

    private final AttributeXmlBeanBuilder attributeXmlBeanBuilder = new AttributeXmlBeanBuilder();

    @Override
    public KeyFamilyType build(DataStructureBean buildFrom) throws SdmxException {
        KeyFamilyType builtObj = KeyFamilyType.Factory.newInstance();
        if (validString(buildFrom.getAgencyId())) {
            builtObj.setAgencyID(buildFrom.getAgencyId());
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
        if (validString(buildFrom.getUrn())) {
            builtObj.setUrn(buildFrom.getUrn());
        }
        if (validString(buildFrom.getVersion())) {
            builtObj.setVersion(buildFrom.getVersion());
        }
        if (buildFrom.getStartDate() != null) {
            builtObj.setValidFrom(buildFrom.getStartDate().getDate());
        }
        if (buildFrom.getEndDate() != null) {
            builtObj.setValidTo(buildFrom.getEndDate().getDate());
        }
        if (validCollection(buildFrom.getNames())) {
            builtObj.setNameArray(getTextType(buildFrom.getNames()));
        }
        if (validCollection(buildFrom.getDescriptions())) {
            builtObj.setDescriptionArray(getTextType(buildFrom.getDescriptions()));
        }
        if (hasAnnotations(buildFrom)) {
            builtObj.setAnnotations(getAnnotationsType(buildFrom));
        }
        if (buildFrom.isExternalReference().isSet()) {
            builtObj.setIsExternalReference(buildFrom.isExternalReference().isTrue());
        }
        if (buildFrom.isFinal().isSet()) {
            builtObj.setIsFinal(buildFrom.isFinal().isTrue());
        }

        ComponentsType componentsType = null;

        List<ComponentBean> crossSectionalAttachDataSet = new ArrayList<ComponentBean>();
        List<ComponentBean> crossSectionalAttachGroup = new ArrayList<ComponentBean>();
        List<ComponentBean> crossSectionalAttachSection = new ArrayList<ComponentBean>();
        List<ComponentBean> crossSectionalAttachObservation = new ArrayList<ComponentBean>();
        CrossSectionalDataStructureBean crossSectionalBean = null;
        if (buildFrom instanceof CrossSectionalDataStructureBean) {
            crossSectionalBean = (CrossSectionalDataStructureBean) buildFrom;

            crossSectionalAttachDataSet = crossSectionalBean.getCrossSectionalAttachDataSet(false);
            crossSectionalAttachGroup = crossSectionalBean.getCrossSectionalAttachGroup(false);
            crossSectionalAttachSection = crossSectionalBean.getCrossSectionalAttachSection(false);
            crossSectionalAttachObservation = crossSectionalBean.getCrossSectionalAttachObservation();


            if (componentsType == null) {
                componentsType = builtObj.addNewComponents();
            }

            for (CrossSectionalMeasureBean currentMeasure : crossSectionalBean.getCrossSectionalMeasures()) {
                componentsType.getCrossSectionalMeasureList().add(crossSectionalXmlBeanBuilder.build(currentMeasure));
            }
        }

        if (ObjectUtil.validCollection(buildFrom.getDimensions(SDMX_STRUCTURE_TYPE.DIMENSION, SDMX_STRUCTURE_TYPE.MEASURE_DIMENSION))) {
            if (componentsType == null) {
                componentsType = builtObj.addNewComponents();
            }

            for (DimensionBean currentDim : buildFrom.getDimensions(SDMX_STRUCTURE_TYPE.DIMENSION, SDMX_STRUCTURE_TYPE.MEASURE_DIMENSION)) {
                DimensionType newDimension = dimensionXmlBeanBuilder.build(currentDim);
                if (crossSectionalBean != null && currentDim.isMeasureDimension()) {
                    CrossReferenceBean xsRef = crossSectionalBean.getCodelistForMeasureDimension(currentDim.getId());
                    newDimension.setCodelist(xsRef.getMaintainableReference().getMaintainableId());
                    newDimension.setCodelistAgency(xsRef.getMaintainableReference().getAgencyId());
                    newDimension.setCodelistVersion(xsRef.getMaintainableReference().getVersion());
                }
                if (crossSectionalAttachDataSet.contains(currentDim)) {
                    newDimension.setCrossSectionalAttachDataSet(true);
                }
                if (crossSectionalAttachGroup.contains(currentDim)) {
                    newDimension.setCrossSectionalAttachGroup(true);
                }
                if (crossSectionalAttachSection.contains(currentDim)) {
                    newDimension.setCrossSectionalAttachSection(true);
                }
                if (crossSectionalAttachObservation.contains(currentDim)) {
                    newDimension.setCrossSectionalAttachObservation(true);
                }
                componentsType.getDimensionList().add(newDimension);
            }
        }

        if (buildFrom.getTimeDimension() != null) {
            if (componentsType == null) {
                componentsType = builtObj.addNewComponents();
            }
            TimeDimensionType newDimension = timeDimensionXmlBeanBuilder.build(buildFrom.getTimeDimension());
            if (crossSectionalAttachDataSet.contains(buildFrom.getTimeDimension())) {
                newDimension.setCrossSectionalAttachDataSet(true);
            }
            if (crossSectionalAttachGroup.contains(buildFrom.getTimeDimension())) {
                newDimension.setCrossSectionalAttachGroup(true);
            }
            if (crossSectionalAttachSection.contains(buildFrom.getTimeDimension())) {
                newDimension.setCrossSectionalAttachSection(true);
            }
            if (crossSectionalAttachObservation.contains(buildFrom.getTimeDimension())) {
                newDimension.setCrossSectionalAttachObservation(true);
            }
            componentsType.setTimeDimension(newDimension);
        }

        if (ObjectUtil.validCollection(buildFrom.getGroups())) {
            if (componentsType == null) {
                componentsType = builtObj.addNewComponents();
            }

            for (GroupBean currentGroup : buildFrom.getGroups()) {
                componentsType.getGroupList().add(groupXmlBeansBuilder.build(currentGroup));
            }
        }

        if (buildFrom.getPrimaryMeasure() != null) {
            if (componentsType == null) {
                componentsType = builtObj.addNewComponents();
            }

            componentsType.setPrimaryMeasure(primaryMeasureXmlBeanBuilder.build(buildFrom.getPrimaryMeasure()));
        }

        if (ObjectUtil.validCollection(buildFrom.getAttributes())) {
            if (componentsType == null) {
                componentsType = builtObj.addNewComponents();
            }

            for (AttributeBean currentAttr : buildFrom.getAttributes()) {
                AttributeType newAttribute = attributeXmlBeanBuilder.build(currentAttr);

                if (currentAttr.getAttachmentLevel() == ATTRIBUTE_ATTACHMENT_LEVEL.DIMENSION_GROUP) {
                    //If the group of dimensions is also a group, do not create the attribute;
                    List<String> dimensionReferences = currentAttr.getDimensionReferences();
                    for (GroupBean grp : buildFrom.getGroups()) {
                        if (grp.getDimensionRefs().containsAll(dimensionReferences) &&
                                dimensionReferences.containsAll(grp.getDimensionRefs())) {
                            newAttribute.setAttachmentLevel(AttachmentLevelType.GROUP);
                            newAttribute.addAttachmentGroup(grp.getId());
                            break;
                        }
                    }
                }

                if (crossSectionalAttachDataSet.contains(currentAttr)) {
                    newAttribute.setCrossSectionalAttachDataSet(true);
                }
                if (crossSectionalAttachGroup.contains(currentAttr)) {
                    newAttribute.setCrossSectionalAttachGroup(true);
                }
                if (crossSectionalAttachSection.contains(currentAttr)) {
                    newAttribute.setCrossSectionalAttachSection(true);
                }
                if (crossSectionalAttachObservation.contains(currentAttr)) {
                    newAttribute.setCrossSectionalAttachObservation(true);
                }
                if (crossSectionalBean != null) {
                    for (CrossSectionalMeasureBean crossSectionalMeasure : crossSectionalBean.getAttachmentMeasures(currentAttr)) {
                        IDType idType = newAttribute.addNewAttachmentMeasure();
                        idType.setStringValue(crossSectionalMeasure.getId());
                    }
                }
                componentsType.getAttributeList().add(newAttribute);
            }
        }
        return builtObj;
    }
}
