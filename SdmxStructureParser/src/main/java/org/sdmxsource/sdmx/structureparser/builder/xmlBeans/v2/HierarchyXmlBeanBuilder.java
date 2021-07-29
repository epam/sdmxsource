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

import org.sdmx.resources.sdmxml.schemas.v20.structure.HierarchyType;
import org.sdmxsource.sdmx.api.builder.Builder;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.codelist.HierarchicalCodeBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.HierarchyBean;
import org.sdmxsource.util.ObjectUtil;


/**
 * The type Hierarchy xml bean builder.
 */
public class HierarchyXmlBeanBuilder extends AbstractBuilder implements Builder<HierarchyType, HierarchyBean> {

    private final CodeRefXmlBeanBuilder codeRefXmlBeanBuilder = new CodeRefXmlBeanBuilder();

    private final LevelXmlBeansBuilder levelXmlBeanBuilder = new LevelXmlBeansBuilder();

    @Override
    public HierarchyType build(HierarchyBean buildFrom) throws SdmxException {
        HierarchyType builtObj = HierarchyType.Factory.newInstance();
        if (validString(buildFrom.getId())) {
            builtObj.setId(buildFrom.getId());
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
        if (ObjectUtil.validCollection(buildFrom.getHierarchicalCodeBeans())) {
            for (HierarchicalCodeBean currentCodeRef : buildFrom.getHierarchicalCodeBeans()) {
                builtObj.getCodeRefList().add(codeRefXmlBeanBuilder.build(currentCodeRef));
            }
        }
        if (buildFrom.getLevel() != null) {
            levelXmlBeanBuilder.buildList(builtObj, buildFrom.getLevel(), 1);
        }
        return builtObj;
    }


}
