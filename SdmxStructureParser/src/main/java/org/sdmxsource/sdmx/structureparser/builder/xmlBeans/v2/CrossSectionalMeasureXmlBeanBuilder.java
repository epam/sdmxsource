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

import org.sdmx.resources.sdmxml.schemas.v20.structure.CrossSectionalMeasureType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.TextFormatType;
import org.sdmxsource.sdmx.api.builder.Builder;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.datastructure.CrossSectionalMeasureBean;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;


/**
 * The type Cross sectional measure xml bean builder.
 */
public class CrossSectionalMeasureXmlBeanBuilder extends AbstractBuilder implements Builder<CrossSectionalMeasureType, CrossSectionalMeasureBean> {

    @Override
    public CrossSectionalMeasureType build(CrossSectionalMeasureBean buildFrom) throws SdmxException {
        CrossSectionalMeasureType builtObj = CrossSectionalMeasureType.Factory.newInstance();

        if (hasAnnotations(buildFrom)) {
            builtObj.setAnnotations(getAnnotationsType(buildFrom));
        }
        if (buildFrom.hasCodedRepresentation()) {
            MaintainableRefBean maintRef = buildFrom.getRepresentation().getRepresentation().getMaintainableReference();
            if (validString(maintRef.getMaintainableId())) {
                builtObj.setCodelist(maintRef.getMaintainableId());
            }
            if (validString(maintRef.getAgencyId())) {
                builtObj.setCodelistAgency(maintRef.getAgencyId());
            }
            if (validString(maintRef.getVersion())) {
                builtObj.setCodelistVersion(maintRef.getVersion());
            }
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

        builtObj.setCode(buildFrom.getCode());
        builtObj.setMeasureDimension(buildFrom.getMeasureDimension());
        if (buildFrom.getRepresentation() != null && buildFrom.getRepresentation().getTextFormat() != null) {
            TextFormatType textFormatType = TextFormatType.Factory.newInstance();
            populateTextFormatType(textFormatType, buildFrom.getRepresentation().getTextFormat());
            builtObj.setTextFormat(textFormatType);
        }
        return builtObj;
    }
}
