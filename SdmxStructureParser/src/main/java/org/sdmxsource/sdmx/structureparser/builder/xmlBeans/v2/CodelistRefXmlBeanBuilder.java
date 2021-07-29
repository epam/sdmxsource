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

import org.sdmx.resources.sdmxml.schemas.v20.structure.CodelistRefType;
import org.sdmxsource.sdmx.api.builder.Builder;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;


/**
 * The type Codelist ref xml bean builder.
 */
public class CodelistRefXmlBeanBuilder extends AbstractBuilder implements Builder<CodelistRefType, CodelistRefBean> {

    @Override
    public CodelistRefType build(CodelistRefBean buildFrom) throws SdmxException {
        CodelistRefType builtObj = CodelistRefType.Factory.newInstance();
        if (buildFrom.getCodelistReference() != null) {
            MaintainableRefBean maintRef = buildFrom.getCodelistReference().getMaintainableReference();
            if (validString(maintRef.getAgencyId())) {
                builtObj.setAgencyID(maintRef.getAgencyId());
            }
            if (validString(buildFrom.getAlias())) {
                builtObj.setAlias(buildFrom.getAlias());
            }
            if (validString(maintRef.getMaintainableId())) {
                builtObj.setCodelistID(maintRef.getMaintainableId());
            }
            if (validString(buildFrom.getCodelistReference().getTargetUrn())) {
                builtObj.setURN(buildFrom.getCodelistReference().getTargetUrn());
            }
            if (validString(maintRef.getVersion())) {
                builtObj.setVersion(maintRef.getVersion());
            }
        }

        return builtObj;
    }
}
