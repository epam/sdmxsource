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

import org.sdmx.resources.sdmxml.schemas.v20.structure.MetadataStructureDefinitionType;
import org.sdmxsource.sdmx.api.builder.Builder;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataStructureDefinitionBean;


/**
 * The type Metadata structure definition xml beans builder.
 */
public class MetadataStructureDefinitionXmlBeansBuilder extends AbstractBuilder implements Builder<MetadataStructureDefinitionType, MetadataStructureDefinitionBean> {

    @Override
    public MetadataStructureDefinitionType build(MetadataStructureDefinitionBean buildFrom) throws SdmxException {
        //FUNC 2.0 MSD?
        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "Metadata Structure Definition at SMDX v2.0 - please use SDMX v2.1");
//		MetadataStructureDefinitionType builtObj = MetadataStructureDefinitionType.Factory.newInstance();
//		if(validString(buildFrom.getAgencyId())){
//			builtObj.setAgencyID(buildFrom.getAgencyId());
//		}
//		if(validString(buildFrom.getId())){
//			builtObj.setId(buildFrom.getId());
//		}
//		if(validString(buildFrom.getUri())){
//			builtObj.setUri(buildFrom.getUri());
//		}
//		if(validString(buildFrom.getUrn())){
//			builtObj.setUrn(buildFrom.getUrn());
//		}
//		if(validString(buildFrom.getVersion())){
//			builtObj.setVersion(buildFrom.getVersion());
//		}
//		if(buildFrom.getStartDate() != null) {
//			builtObj.setValidFrom(buildFrom.getStartDate());
//		}
//		if(buildFrom.getEndDate() != null) {
//			builtObj.setValidTo(buildFrom.getEndDate());
//		}
//		if(validCollection(buildFrom.getName())) {
//			builtObj.setNameArray(getTextType(buildFrom.getName()));
//		}
//		if(validCollection(buildFrom.getDescription())) {
//			builtObj.setDescriptionArray(getTextType(buildFrom.getDescription()));
//		}
//		if(hasAnnotations(buildFrom)) {
//			builtObj.setAnnotations(getAnnotationsType(buildFrom));
//		}
//		if(buildFrom.isExternalReference().isSet()) {
//			builtObj.setIsExternalReference(buildFrom.isExternalReference().isTrue());			
//		}
//		if(buildFrom.isFinal().isSet()) {
//			builtObj.setIsFinal(buildFrom.isFinal().isTrue());
//		}
//		for(ReportStructureBean currentRs : buildFrom.getReportStructures()) {
//			builtObj.getReportStructureList().add(reportStructureXmlBeanBuilder.build(currentRs));
//		}
//		
//		return builtObj;
    }


}
