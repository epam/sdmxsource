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

import org.sdmx.resources.sdmxml.schemas.v21.common.DataProviderReferenceType;
import org.sdmx.resources.sdmxml.schemas.v21.common.StructureUsageReferenceType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.ProvisionAgreementType;
import org.sdmxsource.sdmx.api.builder.Builder;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers.MaintainableBeanAssembler;


/**
 * The type Provision agreement xml bean builder.
 */
public class ProvisionAgreementXmlBeanBuilder extends MaintainableBeanAssembler implements Builder<ProvisionAgreementType, ProvisionAgreementBean> {

    @Override
    public ProvisionAgreementType build(ProvisionAgreementBean buildFrom) throws SdmxException {
        // Create outgoing build
        ProvisionAgreementType builtObj = ProvisionAgreementType.Factory.newInstance();
        // Populate it from inherited super
        super.assembleMaintainable(builtObj, buildFrom);
        if (buildFrom.getStructureUseage() != null) {
            StructureUsageReferenceType structureUSeageType = builtObj.addNewStructureUsage();
            super.setReference(structureUSeageType.addNewRef(), buildFrom.getStructureUseage());
        }
        if (buildFrom.getDataproviderRef() != null) {
            DataProviderReferenceType dataProviderRef = builtObj.addNewDataProvider();
            super.setReference(dataProviderRef.addNewRef(), buildFrom.getDataproviderRef());
        }
        return builtObj;
    }
}
