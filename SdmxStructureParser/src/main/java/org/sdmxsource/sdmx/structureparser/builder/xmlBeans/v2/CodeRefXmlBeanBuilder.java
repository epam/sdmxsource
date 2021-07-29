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

import org.sdmx.resources.sdmxml.schemas.v20.structure.CodeRefType;
import org.sdmxsource.sdmx.api.builder.Builder;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.codelist.HierarchicalCodeBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.util.ObjectUtil;


/**
 * The type Code ref xml bean builder.
 */
public class CodeRefXmlBeanBuilder extends AbstractBuilder implements Builder<CodeRefType, HierarchicalCodeBean> {

    @Override
    public CodeRefType build(HierarchicalCodeBean buildFrom) throws SdmxException {
        CodeRefType builtObj = CodeRefType.Factory.newInstance();

        if (validString(buildFrom.getCodeId())) {
            builtObj.setCodeID(buildFrom.getCodeId());
        }
        if (validString(buildFrom.getId())) {
            builtObj.setNodeAliasID(buildFrom.getId());
        }
        if (validString(buildFrom.getCodelistAliasRef())) {
            builtObj.setCodelistAliasRef(buildFrom.getCodelistAliasRef());
        } else {
            CrossReferenceBean crossRefernce = buildFrom.getCodeReference();
            if (crossRefernce != null) {
                builtObj.setURN(crossRefernce.getTargetUrn());
            }
        }
        if (buildFrom.getValidFrom() != null) {
            builtObj.setValidFrom(buildFrom.getValidFrom().getDate());
        }
        if (buildFrom.getValidTo() != null) {
            builtObj.setValidTo(buildFrom.getValidTo().getDate());
        }
        if (buildFrom.getLevel(false) != null) {
            builtObj.setLevelRef(buildFrom.getLevel(false).getId());
        }

        if (ObjectUtil.validCollection(buildFrom.getCodeRefs())) {
            for (HierarchicalCodeBean currentCodeRef : buildFrom.getCodeRefs()) {
                builtObj.getCodeRefList().add(this.build(currentCodeRef));
            }
        }
        return builtObj;
    }
}
