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

import org.sdmx.resources.sdmxml.schemas.v20.registry.DataProviderRefType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.DataflowRefType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.MetadataflowRefType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.ProvisionAgreementType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.util.ObjectUtil;


/**
 * The type Provision agreement xml bean builder.
 */
public class ProvisionAgreementXmlBeanBuilder extends AbstractBuilder {
    /**
     * The constant INSTANCE.
     */
    public static ProvisionAgreementXmlBeanBuilder INSTANCE = new ProvisionAgreementXmlBeanBuilder();

    private ProvisionAgreementXmlBeanBuilder() {
    }


    /**
     * Build provision agreement type.
     *
     * @param buildFrom the build from
     * @return the provision agreement type
     * @throws SdmxException the sdmx exception
     */
    public ProvisionAgreementType build(ProvisionAgreementBean buildFrom) throws SdmxException {
        ProvisionAgreementType builtObj = ProvisionAgreementType.Factory.newInstance();

        if (ObjectUtil.validString(buildFrom.getId())) {
            builtObj.setId(buildFrom.getId());
        }
        if (buildFrom.getUri() != null) {
            builtObj.setUri(buildFrom.getUri().toString());
        } else if (buildFrom.getStructureURL() != null) {
            builtObj.setUri(buildFrom.getStructureURL().toString());
        } else if (buildFrom.getServiceURL() != null) {
            builtObj.setUri(buildFrom.getStructureURL().toString());
        }
        if (ObjectUtil.validString(buildFrom.getUrn())) {
            builtObj.setUrn(buildFrom.getUrn());
        }
        if (validCollection(buildFrom.getNames())) {
            builtObj.setNameArray(getTextType(buildFrom.getNames()));
        }
        if (validCollection(buildFrom.getDescriptions())) {
            builtObj.setDescriptionArray(getTextType(buildFrom.getDescriptions()));
        }
        if (hasAnnotations(buildFrom)) {
            builtObj.setAnnotations(getAnnotationsType(buildFrom));
        }
        if (buildFrom.getStructureUseage() != null) {
            if (buildFrom.getStructureUseage().getTargetReference() == SDMX_STRUCTURE_TYPE.DATAFLOW) {
                DataflowRefType dfRef = builtObj.addNewDataflowRef();
                populateDataflowRef(buildFrom.getStructureUseage(), dfRef);
            } else if (buildFrom.getStructureUseage().getTargetReference() == SDMX_STRUCTURE_TYPE.METADATA_FLOW) {
                MetadataflowRefType mdRef = builtObj.addNewMetadataflowRef();
                populateMetadataflowRef(buildFrom.getStructureUseage(), mdRef);
            }
        }
        if (buildFrom.getDataproviderRef() != null) {
            DataProviderRefType dpRef = builtObj.addNewDataProviderRef();
            populateDataproviderRef(buildFrom.getDataproviderRef(), dpRef);
        }
        return builtObj;
    }
}
