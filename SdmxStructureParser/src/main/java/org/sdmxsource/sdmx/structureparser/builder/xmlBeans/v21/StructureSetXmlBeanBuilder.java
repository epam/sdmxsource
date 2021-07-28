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

import org.sdmx.resources.sdmxml.schemas.v21.structure.*;
import org.sdmxsource.sdmx.api.builder.Builder;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.mapping.*;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers.MaintainableBeanAssembler;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers.OrganisationSchemeMapBeanAssembler;


/**
 * The type Structure set xml bean builder.
 */
public class StructureSetXmlBeanBuilder extends MaintainableBeanAssembler implements Builder<StructureSetType, StructureSetBean> {

    private final OrganisationSchemeMapBeanAssembler organisationSchemeMapBeanAssemblerBean = new OrganisationSchemeMapBeanAssembler();

    private final CategorySchemeMapBeanAssembler categorySchemeMapBeanAssemblerBean = new CategorySchemeMapBeanAssembler();

    private final ConceptSchemeMapBeanAssembler conceptSchemeMapBeanAssemblerBean = new ConceptSchemeMapBeanAssembler();

    private final CodelistMapBeanAssembler codelistMapBeanAssemblerBean = new CodelistMapBeanAssembler();

    private final StructureMapBeanAssembler structureMapBeanAssemblerBean = new StructureMapBeanAssembler();

    @Override
    public StructureSetType build(StructureSetBean buildFrom) throws SdmxException {
        // Create outgoing build
        StructureSetType builtObj = StructureSetType.Factory.newInstance();
        // Populate it from inherited super
        assembleMaintainable(builtObj, buildFrom);
        // Populate it using this class's specifics
        for (OrganisationSchemeMapBean eachOrganisationMapBean : buildFrom.getOrganisationSchemeMapList()) {
            OrganisationSchemeMapType newOrganisationSchemeMapType = builtObj.addNewOrganisationSchemeMap();
            organisationSchemeMapBeanAssemblerBean.assemble(newOrganisationSchemeMapType, eachOrganisationMapBean);
        }
        for (CategorySchemeMapBean eachCategoryMapBean : buildFrom.getCategorySchemeMapList()) {
            CategorySchemeMapType newCategorySchemeMapType = builtObj.addNewCategorySchemeMap();
            categorySchemeMapBeanAssemblerBean.assemble(newCategorySchemeMapType, eachCategoryMapBean);
        }
        for (ConceptSchemeMapBean eachConceptMapBean : buildFrom.getConceptSchemeMapList()) {
            ConceptSchemeMapType newConceptSchemeMapType = builtObj.addNewConceptSchemeMap();
            conceptSchemeMapBeanAssemblerBean.assemble(newConceptSchemeMapType, eachConceptMapBean);
        }
        //TODO RSG REPORTING TAXONOMY MAP CURRENTLY NOT YET IN MODEL ###
        for (CodelistMapBean eachCodelistMapBean : buildFrom.getCodelistMapList()) {
            CodelistMapType newCodelistMapType = builtObj.addNewCodelistMap();
            codelistMapBeanAssemblerBean.assemble(newCodelistMapType, eachCodelistMapBean);
        }
        for (StructureMapBean eachStructureMapBean : buildFrom.getStructureMapList()) {
            StructureMapType newStructureMapType = builtObj.addNewStructureMap();
            structureMapBeanAssemblerBean.assemble(newStructureMapType, eachStructureMapBean);
        }
        return builtObj;
    }

}
