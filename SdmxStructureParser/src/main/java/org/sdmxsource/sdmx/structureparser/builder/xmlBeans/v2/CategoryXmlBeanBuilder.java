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

import org.sdmx.resources.sdmxml.schemas.v20.structure.CategoryType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.DataflowRefType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.MetadataflowRefType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategorisationBean;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategoryBean;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.ReportingCategoryBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.util.ObjectUtil;

import java.util.Set;


/**
 * The type Category xml bean builder.
 */
public class CategoryXmlBeanBuilder extends AbstractBuilder {

    /**
     * Build category type.
     *
     * @param buildFrom the build from
     * @return the category type
     * @throws SdmxException the sdmx exception
     */
    public CategoryType build(ReportingCategoryBean buildFrom) throws SdmxException {
        CategoryType builtObj = CategoryType.Factory.newInstance();
        if (validString(buildFrom.getId())) {
            builtObj.setId(buildFrom.getId());
        }
        if (buildFrom.getUri() != null) {
            builtObj.setUri(buildFrom.getUri().toString());
        }
        if (validString(buildFrom.getUrn())) {
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

        if (hasAnnotations(buildFrom)) {
            builtObj.setAnnotations(getAnnotationsType(buildFrom));
        }
        if (ObjectUtil.validCollection(buildFrom.getItems())) {
            for (ReportingCategoryBean currentReportingCategory : buildFrom.getItems()) {
                builtObj.getCategoryList().add(this.build(currentReportingCategory));
            }
        }
        return builtObj;
    }

    /**
     * Build category type.
     *
     * @param buildFrom       the build from
     * @param categorisations the categorisations
     * @return the category type
     * @throws SdmxException the sdmx exception
     */
    public CategoryType build(CategoryBean buildFrom, Set<CategorisationBean> categorisations) throws SdmxException {
        CategoryType builtObj = CategoryType.Factory.newInstance();
        if (validString(buildFrom.getId())) {
            builtObj.setId(buildFrom.getId());
        }
        if (buildFrom.getUri() != null) {
            builtObj.setUri(buildFrom.getUri().toString());
        }
        if (validString(buildFrom.getUrn())) {
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

        if (hasAnnotations(buildFrom)) {
            builtObj.setAnnotations(getAnnotationsType(buildFrom));
        }
        if (categorisations != null) {
            for (CategorisationBean cat : categorisations) {
                CrossReferenceBean catRef = cat.getCategoryReference();
                if (catRef.isMatch(buildFrom)) {
                    if (cat.getStructureReference().getTargetReference() == SDMX_STRUCTURE_TYPE.DATAFLOW) {
                        DataflowRefType dataflowRefType = builtObj.addNewDataflowRef();
                        MaintainableRefBean dfRef = cat.getStructureReference().getMaintainableReference();
                        if (validString(dfRef.getAgencyId())) {
                            dataflowRefType.setAgencyID(dfRef.getAgencyId());
                        }
                        if (validString(dfRef.getMaintainableId())) {
                            dataflowRefType.setDataflowID(dfRef.getMaintainableId());
                        }
                        if (validString(dfRef.getVersion())) {
                            dataflowRefType.setVersion(dfRef.getVersion());
                        }
                        if (validString(cat.getStructureReference().getTargetUrn())) {
                            dataflowRefType.setURN(cat.getStructureReference().getTargetUrn());
                        }
                    } else if (cat.getStructureReference().getTargetReference() == SDMX_STRUCTURE_TYPE.METADATA_FLOW) {
                        MetadataflowRefType metdataflowRefType = builtObj.addNewMetadataflowRef();
                        MaintainableRefBean mdfRef = cat.getStructureReference().getMaintainableReference();
                        if (validString(mdfRef.getAgencyId())) {
                            metdataflowRefType.setAgencyID(mdfRef.getAgencyId());
                        }
                        if (validString(mdfRef.getMaintainableId())) {
                            metdataflowRefType.setMetadataflowID(mdfRef.getMaintainableId());
                        }
                        if (validString(mdfRef.getVersion())) {
                            metdataflowRefType.setVersion(mdfRef.getVersion());
                        }
                        if (validString(cat.getStructureReference().getTargetUrn())) {
                            metdataflowRefType.setURN(cat.getStructureReference().getTargetUrn());
                        }
                    }
                }
            }
        }

        if (ObjectUtil.validCollection(buildFrom.getItems())) {
            for (CategoryBean currentCategory : buildFrom.getItems()) {
                builtObj.getCategoryList().add(this.build(currentCategory, categorisations));
            }
        }
        return builtObj;
    }
}
