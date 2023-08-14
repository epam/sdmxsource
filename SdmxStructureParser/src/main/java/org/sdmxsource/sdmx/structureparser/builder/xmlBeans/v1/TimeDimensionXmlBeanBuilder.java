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
import org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.TextFormatType;
import org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.TimeDimensionType;
import org.sdmxsource.sdmx.api.builder.Builder;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.util.beans.ConceptRefUtil;


/**
 * The type Time dimension xml bean builder.
 */
public class TimeDimensionXmlBeanBuilder extends AbstractBuilder implements Builder<TimeDimensionType, DimensionBean> {

    static {
        log = LoggerFactory.getLogger(TimeDimensionXmlBeanBuilder.class);
    }

    /**
     * Instantiates a new Time dimension xml bean builder.
     */
    public TimeDimensionXmlBeanBuilder() {
    }

    @Override
    public TimeDimensionType build(DimensionBean buildFrom) throws SdmxException {
        TimeDimensionType builtObj = TimeDimensionType.Factory.newInstance();

        if (buildFrom.hasCodedRepresentation()) {
            builtObj.setCodelist(buildFrom.getRepresentation().getRepresentation().getMaintainableReference().getMaintainableId());
        }
        if (buildFrom.getConceptRef() != null) {
            builtObj.setConcept(ConceptRefUtil.getConceptId(buildFrom.getConceptRef()));
        }
        if (buildFrom.getRepresentation() != null && buildFrom.getRepresentation().getTextFormat() != null) {
            TextFormatType textFormatType = TextFormatType.Factory.newInstance();
            populateTextFormatType(textFormatType, buildFrom.getRepresentation().getTextFormat());
            builtObj.setTextFormat(textFormatType);
        }
        return builtObj;
    }
}
