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

import org.sdmx.resources.sdmxml.schemas.v21.structure.MetadataflowType;
import org.sdmxsource.sdmx.api.builder.Builder;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataFlowBean;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers.MaintainableBeanAssembler;


/**
 * The type Metadataflow xml bean builder.
 */
public class MetadataflowXmlBeanBuilder extends MaintainableBeanAssembler implements Builder<MetadataflowType, MetadataFlowBean> {

    @Override
    public MetadataflowType build(MetadataFlowBean buildFrom) throws SdmxException {
        // Create outgoing build
        MetadataflowType builtObj = MetadataflowType.Factory.newInstance();
        // Populate it from inherited super
        assembleMaintainable(builtObj, buildFrom);
        // Populate it using this class's specifics
        if (buildFrom.getMetadataStructureRef() != null) {
            super.setReference(builtObj.addNewStructure().addNewRef(), buildFrom.getMetadataStructureRef());
        }
        return builtObj;
    }
}
