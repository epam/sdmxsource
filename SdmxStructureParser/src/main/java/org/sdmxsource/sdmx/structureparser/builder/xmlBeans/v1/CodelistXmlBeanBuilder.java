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

import org.apache.log4j.Logger;
import org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.CodeListType;
import org.sdmxsource.sdmx.api.builder.Builder;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodeBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistBean;


/**
 * The type Codelist xml bean builder.
 */
public class CodelistXmlBeanBuilder extends AbstractBuilder implements Builder<CodeListType, CodelistBean> {

    static {
        log = Logger.getLogger(CodelistXmlBeanBuilder.class);
    }

    private final CodeXmlBeanBuilder codeXmlBeanBuilder = new CodeXmlBeanBuilder();

    /**
     * Instantiates a new Codelist xml bean builder.
     */
    public CodelistXmlBeanBuilder() {
    }

    @Override
    public CodeListType build(CodelistBean buildFrom) throws SdmxException {
        CodeListType builtObj = CodeListType.Factory.newInstance();
        if (validString(buildFrom.getAgencyId())) {
            builtObj.setAgency(buildFrom.getAgencyId());
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
        if (validString(buildFrom.getVersion())) {
            builtObj.setVersion(buildFrom.getVersion());
        }
        if (validCollection(buildFrom.getNames())) {
            builtObj.setNameArray(getTextType(buildFrom.getNames()));
        }
        if (hasAnnotations(buildFrom)) {
            builtObj.setAnnotations(getAnnotationsType(buildFrom));
        }
        if (buildFrom.isExternalReference().isSet()) {
            builtObj.setIsExternalReference(buildFrom.isExternalReference().isTrue());
        }

        for (CodeBean codeBean : buildFrom.getItems()) {
            builtObj.getCodeList().add(codeXmlBeanBuilder.build(codeBean));
        }
        return builtObj;
    }
}
