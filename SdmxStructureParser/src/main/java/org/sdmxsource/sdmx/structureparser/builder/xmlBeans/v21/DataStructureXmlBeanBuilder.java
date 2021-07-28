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

import org.sdmx.resources.sdmxml.schemas.v21.structure.*;
import org.sdmxsource.sdmx.api.builder.Builder;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.datastructure.*;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers.*;
import org.sdmxsource.util.ObjectUtil;


/**
 * The type Data structure xml bean builder.
 */
public class DataStructureXmlBeanBuilder extends MaintainableBeanAssembler implements Builder<DataStructureType, DataStructureBean> {

    private final DimensionXmlAssembler dimensionXmlAssembler = new DimensionXmlAssembler();

    private final MeasureDimensionXmlAssembler measureDimensionXmlAssembler = new MeasureDimensionXmlAssembler();

    private final TimeDimensionXmlAssembler timeDimensionXmlAssembler = new TimeDimensionXmlAssembler();

    private final GroupXmlAssembler groupXmlAssembler = new GroupXmlAssembler();

    private final AttributeXmlAssembler attributeXmlAssembler = new AttributeXmlAssembler();

    private final PrimaryMeasureXmlAssembler primaryMeasureXmlAssembler = new PrimaryMeasureXmlAssembler();

    @Override
    public DataStructureType build(DataStructureBean buildFrom) throws SdmxException {
        DataStructureType builtObj = DataStructureType.Factory.newInstance();
        super.assembleMaintainable(builtObj, buildFrom);

        DataStructureComponentsType components = null;

        if (buildFrom.getDimensions().size() > 0) {
            if (components == null) {
                components = builtObj.addNewDataStructureComponents();
            }
            processDimensionList(components.addNewDimensionList(), buildFrom.getDimensionList());
        }
        if (ObjectUtil.validCollection(buildFrom.getGroups())) {
            if (components == null) {
                components = builtObj.addNewDataStructureComponents();
            }
            for (GroupBean currentGroup : buildFrom.getGroups()) {
                groupXmlAssembler.assemble(components.addNewGroup(), currentGroup);
            }
        }
        if (buildFrom.getAttributes().size() > 0) {
            if (components == null) {
                components = builtObj.addNewDataStructureComponents();
            }
            processAttributeList(components.addNewAttributeList(), buildFrom.getAttributeList());
        }
        if (buildFrom.getPrimaryMeasure() != null) {
            if (components == null) {
                components = builtObj.addNewDataStructureComponents();
            }
            processMeasureList(components.addNewMeasureList(), buildFrom.getMeasureList());
        }
        return builtObj;
    }

    private void processDimensionList(DimensionListType dimensionListType, DimensionListBean dimensionList) {
        super.assembleIdentifiable(dimensionListType, dimensionList);
        if (dimensionList.getDimensions() != null) {
            for (DimensionBean currentDimension : dimensionList.getDimensions()) {
                if (currentDimension.isMeasureDimension()) {
                    measureDimensionXmlAssembler.assemble(dimensionListType.addNewMeasureDimension(), currentDimension);
                } else if (currentDimension.isTimeDimension()) {
                    timeDimensionXmlAssembler.assemble(dimensionListType.addNewTimeDimension(), currentDimension);
                } else {
                    dimensionXmlAssembler.assemble(dimensionListType.addNewDimension(), currentDimension);
                }
            }
        }
    }

    private void processAttributeList(AttributeListType attributeListType, AttributeListBean attributeList) {
        super.assembleIdentifiable(attributeListType, attributeList);
        if (attributeList.getAttributes() != null) {
            for (AttributeBean currentAttribute : attributeList.getAttributes()) {
                attributeXmlAssembler.assemble(attributeListType.addNewAttribute(), currentAttribute);
            }
        }
    }

    private void processMeasureList(MeasureListType measureListType, MeasureListBean measureList) {
        super.assembleIdentifiable(measureListType, measureList);
        if (measureList.getPrimaryMeasure() != null) {
            primaryMeasureXmlAssembler.assemble(measureListType.addNewPrimaryMeasure(), measureList.getPrimaryMeasure());
        }
    }
}
