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

import org.sdmx.resources.sdmxml.schemas.v20.structure.HierarchicalCodelistType;
import org.sdmxsource.sdmx.api.builder.Builder;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistRefBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.HierarchicalCodelistBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.HierarchyBean;
import org.sdmxsource.util.ObjectUtil;


/**
 * The type Hierarchical codelist xml bean builder.
 */
public class HierarchicalCodelistXmlBeanBuilder extends AbstractBuilder implements Builder<HierarchicalCodelistType, HierarchicalCodelistBean> {

    private final HierarchyXmlBeanBuilder hierarchyXmlBeanBuilder = new HierarchyXmlBeanBuilder();

    private final CodelistRefXmlBeanBuilder codelistRefXmlBeanBuilder = new CodelistRefXmlBeanBuilder();

    @Override
    public HierarchicalCodelistType build(HierarchicalCodelistBean buildFrom) throws SdmxException {
        HierarchicalCodelistType builtObj = HierarchicalCodelistType.Factory.newInstance();
        if (validString(buildFrom.getAgencyId())) {
            builtObj.setAgencyID(buildFrom.getAgencyId());
        }
        if (validString(buildFrom.getId())) {
            builtObj.setId(buildFrom.getId());
        }
        if (buildFrom.getUri() != null) {
            builtObj.setUri(buildFrom.getUri().toString());
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
        if (ObjectUtil.validCollection(buildFrom.getHierarchies())) {
            for (HierarchyBean hierarchyBean : buildFrom.getHierarchies()) {
                builtObj.getHierarchyList().add(hierarchyXmlBeanBuilder.build(hierarchyBean));
            }
        }
        if (ObjectUtil.validCollection(buildFrom.getCodelistRef())) {
            for (CodelistRefBean codelistRefBean : buildFrom.getCodelistRef()) {
                builtObj.getCodelistRefList().add(codelistRefXmlBeanBuilder.build(codelistRefBean));
            }
        }

        return builtObj;
    }


}
