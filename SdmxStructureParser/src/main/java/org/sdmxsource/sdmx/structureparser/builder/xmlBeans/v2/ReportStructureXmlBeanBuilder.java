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

import org.sdmx.resources.sdmxml.schemas.v20.common.TextType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.ReportStructureType;
import org.sdmxsource.sdmx.api.builder.Builder;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataAttributeBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.ReportStructureBean;
import org.sdmxsource.util.ObjectUtil;


/**
 * The type Report structure xml bean builder.
 */
public class ReportStructureXmlBeanBuilder extends AbstractBuilder implements Builder<ReportStructureType, ReportStructureBean> {
    /**
     * The constant INSTANCE.
     */
    public static ReportStructureXmlBeanBuilder INSTANCE = new ReportStructureXmlBeanBuilder();
    private MetadataAttributeXmlBeanBuilder metadataAttributeXmlBeanBuilder = MetadataAttributeXmlBeanBuilder.INSTANCE;

    private ReportStructureXmlBeanBuilder() {
    }

    @Override
    public ReportStructureType build(ReportStructureBean buildFrom) throws SdmxException {
        ReportStructureType builtObj = ReportStructureType.Factory.newInstance();
        if (validString(buildFrom.getId())) {
            builtObj.setId(buildFrom.getId());
        }
        if (buildFrom.getUri() != null) {
            builtObj.setUri(buildFrom.getUri().toString());
        }
        if (validString(buildFrom.getUrn())) {
            builtObj.setUrn(buildFrom.getUrn());
        }
        TextType tt = builtObj.addNewName();
        setDefaultText(tt);

        if (hasAnnotations(buildFrom)) {
            builtObj.setAnnotations(getAnnotationsType(buildFrom));
        }
        if (buildFrom.getTargetMetadatas() != null && buildFrom.getTargetMetadatas().size() > 0) {
            builtObj.setTarget(buildFrom.getTargetMetadatas().get(0));
        }
        if (ObjectUtil.validCollection(buildFrom.getMetadataAttributes())) {
            for (MetadataAttributeBean currentMa : buildFrom.getMetadataAttributes()) {
                builtObj.getMetadataAttributeList().add(metadataAttributeXmlBeanBuilder.build(currentMa));
            }
        }
        return builtObj;
    }

}
