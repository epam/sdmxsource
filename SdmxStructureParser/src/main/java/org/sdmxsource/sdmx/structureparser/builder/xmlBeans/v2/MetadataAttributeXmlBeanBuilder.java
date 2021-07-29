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

import org.sdmx.resources.sdmxml.schemas.v20.structure.MetadataAttributeType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.TextFormatType;
import org.sdmxsource.sdmx.api.builder.Builder;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataAttributeBean;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;


/**
 * The type Metadata attribute xml bean builder.
 */
public class MetadataAttributeXmlBeanBuilder extends AbstractBuilder implements Builder<MetadataAttributeType, MetadataAttributeBean> {
    /**
     * The constant INSTANCE.
     */
    public static MetadataAttributeXmlBeanBuilder INSTANCE = new MetadataAttributeXmlBeanBuilder();

    private MetadataAttributeXmlBeanBuilder() {
    }

    @Override
    public MetadataAttributeType build(MetadataAttributeBean buildFrom) throws SdmxException {
        MetadataAttributeType builtObj = MetadataAttributeType.Factory.newInstance();

        if (hasAnnotations(buildFrom)) {
            builtObj.setAnnotations(getAnnotationsType(buildFrom));
        }
        if (buildFrom.getConceptRef() != null) {
            MaintainableRefBean maintRef = buildFrom.getConceptRef().getMaintainableReference();
            if (validString(maintRef.getAgencyId())) {
                builtObj.setConceptSchemeAgency(maintRef.getAgencyId());
            }
            if (validString(maintRef.getMaintainableId())) {
                builtObj.setConceptSchemeRef(maintRef.getMaintainableId());
            }
            if (validString(buildFrom.getConceptRef().getChildReference().getId())) {
                builtObj.setConceptRef(buildFrom.getConceptRef().getChildReference().getId());
            }
            if (validString(maintRef.getVersion())) {
                builtObj.setConceptVersion(maintRef.getVersion());
            }
        }
        if (buildFrom.getRepresentation() != null && buildFrom.getRepresentation().getTextFormat() != null) {
            TextFormatType textFormatType = TextFormatType.Factory.newInstance();
            populateTextFormatType(textFormatType, buildFrom.getRepresentation().getTextFormat());
            builtObj.setTextFormat(textFormatType);
        }

        return builtObj;
    }


}
