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

import org.sdmx.resources.sdmxml.schemas.v21.common.*;
import org.sdmx.resources.sdmxml.schemas.v21.structure.ContentConstraintType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.ReleaseCalendarType;
import org.sdmxsource.sdmx.api.builder.Builder;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.registry.*;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers.TimeRangeXmlAssembler;


/**
 * The type Content constraint xml bean builder.
 */
public class ContentConstraintXmlBeanBuilder extends ConstraintBeanAssembler implements Builder<ContentConstraintType, ContentConstraintBean> {

    private final TimeRangeXmlAssembler timeRangeXmlAssembler = new TimeRangeXmlAssembler();

    @Override
    public ContentConstraintType build(ContentConstraintBean buildFrom) throws SdmxException {
        ContentConstraintType returnType = ContentConstraintType.Factory.newInstance();
        super.assemble(returnType, buildFrom);
        if (buildFrom.isDefiningActualDataPresent()) {
            returnType.setType(ContentConstraintTypeCodeType.ACTUAL);
        } else {
            returnType.setType(ContentConstraintTypeCodeType.ALLOWED);
        }
        if (buildFrom.getIncludedCubeRegion() != null) {
            buildCubeRegion(returnType.addNewCubeRegion(), buildFrom.getIncludedCubeRegion(), true);
        }
        if (buildFrom.getExcludedCubeRegion() != null) {
            buildCubeRegion(returnType.addNewCubeRegion(), buildFrom.getExcludedCubeRegion(), false);
        }
        if (buildFrom.getReleaseCalendar() != null) {
            ReleaseCalendarBean calBean = buildFrom.getReleaseCalendar();
            ReleaseCalendarType calType = returnType.addNewReleaseCalendar();
            calType.setOffset(calBean.getOffset());
            calType.setPeriodicity(calBean.getPeriodicity());
            calType.setTolerance(calBean.getTolerance());
        }
        if (buildFrom.getReferencePeriod() != null) {
            ReferencePeriodBean refPeriodBean = buildFrom.getReferencePeriod();
            ReferencePeriodType refPeriodType = returnType.addNewReferencePeriod();
            if (refPeriodBean.getStartTime() != null) {
                refPeriodType.setStartTime(refPeriodBean.getStartTime().getDateAsCalendar());
            }
            if (refPeriodBean.getEndTime() != null) {
                refPeriodType.setEndTime(refPeriodBean.getEndTime().getDateAsCalendar());
            }
        }
        if (buildFrom.getMetadataTargetRegion() != null) {
            MetadataTargetRegionType mtRegionType = returnType.addNewMetadataTargetRegion();
            MetadataTargetRegionBean mtRegionBean = buildFrom.getMetadataTargetRegion();
            buildMetadataTargetRegion(mtRegionType, mtRegionBean);
        }
        return returnType;
    }

    private void buildMetadataTargetRegion(MetadataTargetRegionType mtRegionType, MetadataTargetRegionBean mtRegionBean) {
        mtRegionType.setInclude(mtRegionBean.isInclude());
        mtRegionType.setReport(mtRegionBean.getReport());
        mtRegionType.setMetadataTarget(mtRegionBean.getMetadataTarget());
        for (KeyValues keyValues : mtRegionBean.getAttributes()) {
            buildKeyValues(mtRegionType.addNewAttribute(), keyValues);
        }
        for (MetadataTargetKeyValuesBean keyValues : mtRegionBean.getKey()) {
            ComponentValueSetType cvst = mtRegionType.addNewKeyValue();
            buildKeyValues(cvst, keyValues);
            for (DataSetReferenceBean dsRef : keyValues.getDatasetReferences()) {
                SetReferenceType dsRefType = cvst.addNewDataSet();
                dsRefType.setID(dsRef.getDatasetId());
                super.setReference(dsRefType.addNewDataProvider().addNewRef(), dsRef.getDataProviderReference());
            }
        }
    }


    private void buildCubeRegion(CubeRegionType cubeRegionType, CubeRegionBean cubeRegionBean, boolean isIncluded) {
        cubeRegionType.setInclude(isIncluded);
        for (KeyValues currentKv : cubeRegionBean.getKeyValues()) {
            ComponentValueSetType cvst = cubeRegionType.addNewKeyValue();
            cvst.setId(currentKv.getId());
            if (currentKv.getTimeRange() != null) {
                timeRangeXmlAssembler.assemble(cvst.addNewTimeRange(), currentKv.getTimeRange());
            }
            for (String value : currentKv.getValues()) {
                SimpleValueType simpleValueType = cvst.addNewValue();
                simpleValueType.setStringValue(value);
                if (currentKv.isCascadeValue(value)) {
                    simpleValueType.setCascadeValues(true);
                }
            }
        }
        for (KeyValues currentKv : cubeRegionBean.getAttributeValues()) {
            buildKeyValues(cubeRegionType.addNewAttribute(), currentKv);
        }
    }

    private void buildKeyValues(ComponentValueSetType cvst, KeyValues keyValues) {
        cvst.setId(keyValues.getId());
        if (keyValues.getTimeRange() != null) {
            timeRangeXmlAssembler.assemble(cvst.addNewTimeRange(), keyValues.getTimeRange());
        }
        for (String value : keyValues.getValues()) {
            SimpleValueType simpleValueType = cvst.addNewValue();
            simpleValueType.setStringValue(value);
            if (keyValues.isCascadeValue(value)) {
                simpleValueType.setCascadeValues(true);
            }
        }
    }
}
