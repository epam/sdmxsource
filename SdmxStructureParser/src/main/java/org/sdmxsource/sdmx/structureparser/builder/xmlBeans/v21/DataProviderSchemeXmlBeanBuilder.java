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

import org.sdmx.resources.sdmxml.schemas.v21.structure.DataProviderSchemeType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.DataProviderType;
import org.sdmxsource.sdmx.api.builder.Builder;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.base.DataProviderBean;
import org.sdmxsource.sdmx.api.model.beans.base.DataProviderSchemeBean;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers.MaintainableBeanAssembler;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers.OrganisationXmlAssembler;


/**
 * The type Data provider scheme xml bean builder.
 */
public class DataProviderSchemeXmlBeanBuilder extends MaintainableBeanAssembler implements Builder<DataProviderSchemeType, DataProviderSchemeBean> {

    private final OrganisationXmlAssembler organisationXmlAssembler;

    /**
     * Instantiates a new Data provider scheme xml bean builder.
     */
    public DataProviderSchemeXmlBeanBuilder() {
        this(null);
    }

    /**
     * Instantiates a new Data provider scheme xml bean builder.
     *
     * @param organisationXmlAssembler the organisation xml assembler
     */
    public DataProviderSchemeXmlBeanBuilder(final OrganisationXmlAssembler organisationXmlAssembler) {
        this.organisationXmlAssembler = organisationXmlAssembler != null ? organisationXmlAssembler : new OrganisationXmlAssembler();
    }

    @Override
    public DataProviderSchemeType build(DataProviderSchemeBean buildFrom) throws SdmxException {
        DataProviderSchemeType returnType = DataProviderSchemeType.Factory.newInstance();
        if (buildFrom.isPartial()) {
            returnType.setIsPartial(true);
        }
        super.assembleMaintainable(returnType, buildFrom);
        if (buildFrom.getItems() != null) {
            for (DataProviderBean currentBean : buildFrom.getItems()) {
                DataProviderType dataProviderType = returnType.addNewDataProvider();
                organisationXmlAssembler.assemble(dataProviderType, currentBean);
                super.assembleNameable(dataProviderType, currentBean);
            }
        }
        return returnType;
    }
}
