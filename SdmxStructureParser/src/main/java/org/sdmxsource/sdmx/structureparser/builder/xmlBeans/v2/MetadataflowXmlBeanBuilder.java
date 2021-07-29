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

import org.sdmx.resources.sdmxml.schemas.v20.structure.CategoryIDType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.CategoryRefType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.MetadataStructureRefType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.MetadataflowType;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategorisationBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataFlowBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.IdentifiableRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.util.ObjectUtil;

import java.util.Set;


/**
 * The type Metadataflow xml bean builder.
 */
public class MetadataflowXmlBeanBuilder extends AbstractBuilder {

    /**
     * Build metadataflow type.
     *
     * @param buildFrom       the build from
     * @param categorisations the categorisations
     * @return the metadataflow type
     * @throws SdmxException the sdmx exception
     */
    public MetadataflowType build(MetadataFlowBean buildFrom, Set<CategorisationBean> categorisations) throws SdmxException {
        MetadataflowType builtObj = MetadataflowType.Factory.newInstance();
        if (validString(buildFrom.getAgencyId())) {
            builtObj.setAgencyID(buildFrom.getAgencyId());
        }
        if (validString(buildFrom.getId())) {
            builtObj.setId(buildFrom.getId());
        }
        if (buildFrom.getUri() != null) {
            builtObj.setUri(buildFrom.getUri().toString());
        } else if (buildFrom.getStructureURL() != null) {
            builtObj.setUri(buildFrom.getStructureURL().toString());
        } else if (buildFrom.getServiceURL() != null) {
            builtObj.setUri(buildFrom.getStructureURL().toString());
        }
        if (validString(buildFrom.getUrn())) {
            builtObj.setUrn(buildFrom.getUrn());
        }
        if (validString(buildFrom.getVersion())) {
            builtObj.setVersion(buildFrom.getVersion());
        }
        if (buildFrom.getStartDate() != null) {
            builtObj.setValidFrom(buildFrom.getStartDate().getDate());
        }
        if (buildFrom.getEndDate() != null) {
            builtObj.setValidTo(buildFrom.getEndDate().getDate());
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
        if (buildFrom.isExternalReference().isSet()) {
            builtObj.setIsExternalReference(buildFrom.isExternalReference().isTrue());
        }
        if (buildFrom.isFinal().isSet()) {
            builtObj.setIsFinal(buildFrom.isFinal().isTrue());
        }
        if (ObjectUtil.validCollection(categorisations)) {
            for (CategorisationBean currentCategoryRef : categorisations) {
                CategoryRefType categoryRefType = builtObj.addNewCategoryRef();
                CrossReferenceBean refBean = currentCategoryRef.getCategoryReference();
                if (refBean != null) {
                    MaintainableRefBean ref = refBean.getMaintainableReference();
                    if (validString(ref.getAgencyId())) {
                        categoryRefType.setCategorySchemeAgencyID(ref.getAgencyId());
                    }
                    if (validString(ref.getMaintainableId())) {
                        categoryRefType.setCategorySchemeID(ref.getMaintainableId());
                    }
                    if (validString(ref.getVersion())) {
                        categoryRefType.setCategorySchemeVersion(ref.getVersion());
                    }
                }

                CategoryIDType idType = null;
                IdentifiableRefBean childRef = refBean.getChildReference();
                int i = 0;
                while (childRef != null) {
                    if (i == 0) {
                        idType = categoryRefType.addNewCategoryID();
                    } else {
                        idType = idType.addNewCategoryID();
                    }
                    idType.setID(childRef.getId());
                    childRef = childRef.getChildReference();
                    i++;
                }
                if (validString(refBean.getTargetUrn())) {
                    categoryRefType.setURN(refBean.getTargetUrn());
                }
            }
        }
        if (buildFrom.getMetadataStructureRef() != null) {
            MetadataStructureRefType mdsRefType = builtObj.addNewMetadataStructureRef();
            CrossReferenceBean refBean = buildFrom.getMetadataStructureRef();
            if (refBean != null) {
                MaintainableRefBean mRef = refBean.getMaintainableReference();
                if (validString(mRef.getAgencyId())) {
                    mdsRefType.setMetadataStructureAgencyID(mRef.getAgencyId());
                }
                if (validString(mRef.getMaintainableId())) {
                    mdsRefType.setMetadataStructureID(mRef.getMaintainableId());
                }
                if (validString(mRef.getVersion())) {
                    mdsRefType.setVersion(mRef.getVersion());
                }
                if (validString(refBean.getTargetUrn())) {
                    mdsRefType.setURN(refBean.getTargetUrn());
                }
            }
        }
        return builtObj;
    }
}
