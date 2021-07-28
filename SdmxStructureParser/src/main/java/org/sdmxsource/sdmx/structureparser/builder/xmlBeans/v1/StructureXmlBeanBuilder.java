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

import org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.message.HeaderType;
import org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.message.StructureDocument;
import org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.message.StructureType;
import org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.AgenciesType;
import org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.CodeListsType;
import org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.ConceptsType;
import org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.KeyFamiliesType;
import org.sdmxsource.sdmx.api.builder.Builder;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.base.AgencyBean;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistBean;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptBean;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptSchemeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.registry.response.impl.v1.V1Helper;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Structure xml bean builder.
 */
public class StructureXmlBeanBuilder implements Builder<StructureDocument, SdmxBeans> {

    private final AgencyXmlBeanBuilder agencyXmlBeanBuilder = new AgencyXmlBeanBuilder();

    private final CodelistXmlBeanBuilder codelistXmlBeanBuilder = new CodelistXmlBeanBuilder();

    private final ConceptXmlBeanBuilder conceptXmlBeanBuilder = new ConceptXmlBeanBuilder();

    private final DataStructureXmlBeanBuilder dataStructureXmlBeanBuilder = new DataStructureXmlBeanBuilder();

    private final StructureHeaderXmlBeanBuilder structureHeaderXmlBeanBuilder = new StructureHeaderXmlBeanBuilder();

    @Override
    public StructureDocument build(SdmxBeans buildFrom) throws SdmxException {
        //Validate the structures in the beans file are supported by SDMX at version 1.0
        validateSupport(buildFrom);
        StructureDocument doc = StructureDocument.Factory.newInstance();
        StructureType returnType = doc.addNewStructure();
        HeaderType headerType = null;
        if (buildFrom.getHeader() != null) {
            headerType = structureHeaderXmlBeanBuilder.build(buildFrom.getHeader());
            returnType.setHeader(headerType);
        } else {
            headerType = returnType.addNewHeader();
            V1Helper.setHeader(headerType, buildFrom);
        }


        //GET CODELISTS
        if (buildFrom.getCodelists().size() > 0) {
            CodeListsType codeListsType = returnType.addNewCodeLists();
            for (CodelistBean codelistBean : buildFrom.getCodelists()) {
                codeListsType.getCodeListList().add(codelistXmlBeanBuilder.build(codelistBean));
            }
        }

        //CONCEPT SCHEMES
        if (buildFrom.getConceptSchemes().size() > 0) {
            ConceptsType conceptsType = returnType.addNewConcepts();
            for (ConceptSchemeBean conceptSchemeBean : buildFrom.getConceptSchemes()) {
                for (ConceptBean conceptBean : conceptSchemeBean.getItems()) {
                    conceptsType.getConceptList().add(conceptXmlBeanBuilder.build(conceptBean));
                }
            }
        }

        //KEY FAMILY
        if (buildFrom.getDataStructures().size() > 0) {
            KeyFamiliesType keyFamiliesType = returnType.addNewKeyFamilies();
            for (DataStructureBean currentBean : buildFrom.getDataStructures()) {
                keyFamiliesType.getKeyFamilyList().add(dataStructureXmlBeanBuilder.build(currentBean));
            }
        }

        AgenciesType agencies = null;
        //AGENCIES
        if (buildFrom.getAgencies().size() > 0) {
            agencies = returnType.addNewAgencies();
            for (AgencyBean agencyBean : buildFrom.getAgencies()) {
                agencies.getAgencyList().add(agencyXmlBeanBuilder.build(agencyBean));
            }
        }
        return doc;
    }

    /**
     * Validates all the Maintainable Artefacts in the beans container are supported by the SDMX v1.0 syntax
     *
     * @param beans
     */
    private void validateSupport(SdmxBeans beans) {
        List<SDMX_STRUCTURE_TYPE> supportedStructres = new ArrayList<SDMX_STRUCTURE_TYPE>();
        supportedStructres.add(SDMX_STRUCTURE_TYPE.AGENCY_SCHEME);
        supportedStructres.add(SDMX_STRUCTURE_TYPE.DSD);
        supportedStructres.add(SDMX_STRUCTURE_TYPE.CONCEPT_SCHEME);
        supportedStructres.add(SDMX_STRUCTURE_TYPE.CODE_LIST);

        for (MaintainableBean maintainableBean : beans.getAllMaintainables()) {
            if (!supportedStructres.contains(maintainableBean.getStructureType())) {
                throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, maintainableBean.getStructureType().getType() + " at SMDX v1.0 - please use SDMX v2.0 or v2.1");
            }
        }
    }
}
