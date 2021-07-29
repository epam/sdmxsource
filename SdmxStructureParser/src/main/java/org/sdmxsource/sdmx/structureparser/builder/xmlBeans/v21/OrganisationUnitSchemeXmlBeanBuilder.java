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

import org.sdmx.resources.sdmxml.schemas.v21.common.LocalItemReferenceType;
import org.sdmx.resources.sdmxml.schemas.v21.common.RefBaseType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.OrganisationUnitSchemeType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.OrganisationUnitType;
import org.sdmxsource.sdmx.api.builder.Builder;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.base.OrganisationUnitBean;
import org.sdmxsource.sdmx.api.model.beans.base.OrganisationUnitSchemeBean;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers.MaintainableBeanAssembler;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers.OrganisationXmlAssembler;


/**
 * The type Organisation unit scheme xml bean builder.
 */
public class OrganisationUnitSchemeXmlBeanBuilder extends MaintainableBeanAssembler implements Builder<OrganisationUnitSchemeType, OrganisationUnitSchemeBean> {

    private final OrganisationXmlAssembler organisationXmlAssembler = new OrganisationXmlAssembler();

    @Override
    public OrganisationUnitSchemeType build(OrganisationUnitSchemeBean buildFrom) throws SdmxException {
        OrganisationUnitSchemeType returnType = OrganisationUnitSchemeType.Factory.newInstance();
        super.assembleMaintainable(returnType, buildFrom);
        if (buildFrom.getItems() != null) {
            for (OrganisationUnitBean currentBean : buildFrom.getItems()) {
                OrganisationUnitType organisationUnitType = returnType.addNewOrganisationUnit();
                organisationXmlAssembler.assemble(organisationUnitType, currentBean);
                super.assembleNameable(organisationUnitType, currentBean);
                if (currentBean.hasParentUnit()) {
                    LocalItemReferenceType parent = organisationUnitType.addNewParent();
                    RefBaseType ref = parent.addNewRef();
                    ref.setId(currentBean.getParentUnit());
                }
            }
        }
        return returnType;
    }
}
