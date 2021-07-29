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

import org.sdmx.resources.sdmxml.schemas.v21.common.DataType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.*;
import org.sdmxsource.sdmx.api.builder.Builder;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.*;
import org.sdmxsource.sdmx.sdmxbeans.util.XmlBeansEnumUtil;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers.ComponentAssembler;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers.MaintainableBeanAssembler;
import org.sdmxsource.util.ObjectUtil;

import java.math.BigInteger;
import java.util.List;


/**
 * The type Metadata structure xml bean builder.
 */
public class MetadataStructureXmlBeanBuilder extends MaintainableBeanAssembler implements Builder<MetadataStructureType, MetadataStructureDefinitionBean> {

    private final ComponentAssembler componentAssembler = new ComponentAssembler();

    private final DataTypeBuilder dataTypeBuilder = new DataTypeBuilder();

    @Override
    public MetadataStructureType build(MetadataStructureDefinitionBean buildFrom) throws SdmxException {
        MetadataStructureType builtObj = MetadataStructureType.Factory.newInstance();
        super.assembleMaintainable(builtObj, buildFrom);
        if (ObjectUtil.validCollection(buildFrom.getMetadataTargets()) || ObjectUtil.validCollection(buildFrom.getReportStructures())) {
            MetadataStructureComponentsType msdComponentType = builtObj.addNewMetadataStructureComponents();
            if (buildFrom.getMetadataTargets() != null) {
                for (MetadataTargetBean currentMetadataTarget : buildFrom.getMetadataTargets()) {
                    assembleMetadataTargetType(msdComponentType.addNewMetadataTarget(), currentMetadataTarget);
                }
            }
            if (buildFrom.getReportStructures() != null) {
                for (ReportStructureBean currentBean : buildFrom.getReportStructures()) {
                    assembleReportStructure(msdComponentType.addNewReportStructure(), currentBean);
                }
            }
        }
        return builtObj;
    }

    private void assembleMetadataTargetType(MetadataTargetType type, MetadataTargetBean bean) {
        super.assembleIdentifiable(type, bean);
        if (bean.getDataSetTargetBean() != null) {
            assembleDataSetTarget(type.addNewDataSetTarget(), bean.getDataSetTargetBean());
        }
        if (bean.getKeyDescriptorValuesTargetBean() != null) {
            assembleKeyDescriptorValuesTarget(type.addNewKeyDescriptorValuesTarget(), bean.getKeyDescriptorValuesTargetBean());
        }
        if (bean.getReportPeriodTargetBean() != null) {
            assembleReportPeriodTarget(type.addNewReportPeriodTarget(), bean.getReportPeriodTargetBean());
        }
        if (bean.getConstraintContentTargetBean() != null) {
            assembleConstraintContentTarget(type.addNewConstraintContentTarget(), bean.getConstraintContentTargetBean());
        }
        if (bean.getIdentifiableTargetBean() != null) {
            for (IdentifiableTargetBean currentBean : bean.getIdentifiableTargetBean()) {
                assembleIdentifiableObjectTarget(type.addNewIdentifiableObjectTarget(), currentBean);
            }
        }
    }

    private void assembleConstraintContentTarget(ConstraintContentTargetType type, ConstraintContentTargetBean bean) {
        super.assembleIdentifiable(type, bean);
        type.addNewLocalRepresentation().addNewTextFormat().setTextType(DataType.ATTACHMENT_CONSTRAINT_REFERENCE);
    }

    private void assembleKeyDescriptorValuesTarget(KeyDescriptorValuesTargetType type, KeyDescriptorValuesTargetBean bean) {
        super.assembleIdentifiable(type, bean);
        type.addNewLocalRepresentation().addNewTextFormat().setTextType(DataType.KEY_VALUES);
    }

    private void assembleDataSetTarget(DataSetTargetType type, DataSetTargetBean bean) {
        super.assembleIdentifiable(type, bean);
        type.addNewLocalRepresentation().addNewTextFormat().setTextType(DataType.DATA_SET_REFERENCE);
    }

    private void assembleReportPeriodTarget(ReportPeriodTargetType type, ReportPeriodTargetBean bean) {
        super.assembleIdentifiable(type, bean);

        TextFormatType ttType = type.addNewLocalRepresentation().addNewTextFormat();
        if (bean.getTextType() != null) {
            ttType.setTextType(dataTypeBuilder.build(bean.getTextType()));
        }
        if (bean.getStartTime() != null) {
            ttType.setStartTime(bean.getStartTime().getDateInSdmxFormat());
        }
        if (bean.getEndTime() != null) {
            ttType.setEndTime(bean.getEndTime().getDateInSdmxFormat());
        }
    }

    private void assembleIdentifiableObjectTarget(IdentifiableObjectTargetType type, IdentifiableTargetBean bean) {
        componentAssembler.assembleComponent(type, bean);
        type.setObjectType(XmlBeansEnumUtil.build(bean.getReferencedStructureType()));
    }

    private void assembleReportStructure(ReportStructureType type, ReportStructureBean bean) {
        super.assembleIdentifiable(type, bean);
        if (bean.getMetadataAttributes() != null) {
            for (MetadataAttributeBean currentMa : bean.getMetadataAttributes()) {
                assembleMetadataAttributes(type.addNewMetadataAttribute(), currentMa);
            }
        }
        if (bean.getTargetMetadatas() != null) {
            for (String metadataTarget : bean.getTargetMetadatas()) {
                type.addNewMetadataTarget().addNewRef().setId(metadataTarget);
            }
        }
    }

    private void assembleMetadataAttributes(MetadataAttributeType type, MetadataAttributeBean bean) {
        componentAssembler.assembleComponent(type, bean);
        if (bean.getMinOccurs() != null) {
            type.setMinOccurs(new BigInteger(bean.getMinOccurs().toString()));
        }
        if (bean.getMaxOccurs() != null) {
            type.setMaxOccurs(bean.getMaxOccurs());
        } else {
            type.setMaxOccurs("unbounded");
        }
        if (bean.getPresentational().isSet()) {
            type.setIsPresentational(bean.getPresentational().isTrue());
        }
        if (bean.getMetadataAttributes() != null) {
            assembleMetadataAttributes(type, bean.getMetadataAttributes());
        }
    }

    private void assembleMetadataAttributes(MetadataAttributeType metadaAttributeType, List<MetadataAttributeBean> metadataAttributes) {
        for (MetadataAttributeBean currentMa : metadataAttributes) {
            assembleMetadataAttributes(metadaAttributeType.addNewMetadataAttribute(), currentMa);
        }
    }
}
