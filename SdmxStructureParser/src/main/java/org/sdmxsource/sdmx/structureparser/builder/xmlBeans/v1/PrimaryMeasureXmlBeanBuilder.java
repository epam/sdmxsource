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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.PrimaryMeasureType;
import org.sdmxsource.sdmx.api.builder.Builder;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.datastructure.PrimaryMeasureBean;
import org.sdmxsource.sdmx.util.beans.ConceptRefUtil;


/**
 * The type Primary measure xml bean builder.
 */
public class PrimaryMeasureXmlBeanBuilder extends AbstractBuilder implements Builder<PrimaryMeasureType, PrimaryMeasureBean> {
    static {
        log = LogManager.getLogger(PrimaryMeasureXmlBeanBuilder.class);
    }

    @Override
    public PrimaryMeasureType build(PrimaryMeasureBean buildFrom) throws SdmxException {
        PrimaryMeasureType builtObj = PrimaryMeasureType.Factory.newInstance();

        if (hasAnnotations(buildFrom)) {
            builtObj.setAnnotations(getAnnotationsType(buildFrom));
        }

        if (buildFrom.getConceptRef() != null) {
            builtObj.setConcept(ConceptRefUtil.getConceptId(buildFrom.getConceptRef()));
        }
        return builtObj;
    }
}
