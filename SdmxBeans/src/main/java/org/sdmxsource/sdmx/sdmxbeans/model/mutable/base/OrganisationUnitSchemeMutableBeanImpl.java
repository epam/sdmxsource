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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.base;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.OrganisationUnitBean;
import org.sdmxsource.sdmx.api.model.beans.base.OrganisationUnitSchemeBean;
import org.sdmxsource.sdmx.api.model.mutable.base.OrganisationUnitMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.OrganisationUnitSchemeMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.OrganisationUnitSchemeBeanImpl;


/**
 * The type Organisation unit scheme mutable bean.
 */
public class OrganisationUnitSchemeMutableBeanImpl extends ItemSchemeMutableBeanImpl<OrganisationUnitMutableBean> implements OrganisationUnitSchemeMutableBean {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Organisation unit scheme mutable bean.
     *
     * @param bean the bean
     */
    public OrganisationUnitSchemeMutableBeanImpl(OrganisationUnitSchemeBean bean) {
        super(bean);
        for (OrganisationUnitBean organisationUnit : bean.getItems()) {
            addItem(new OrganisationUnitMutableBeanImpl(organisationUnit));
        }
    }


    /**
     * Instantiates a new Organisation unit scheme mutable bean.
     */
    public OrganisationUnitSchemeMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.ORGANISATION_UNIT_SCHEME);
    }

    @Override
    public OrganisationUnitMutableBean createItem(String id, String name) {
        OrganisationUnitMutableBean orgUnit = new OrganisationUnitMutableBeanImpl();
        orgUnit.setId(id);
        orgUnit.addName("en", name);
        addItem(orgUnit);
        return orgUnit;
    }

    @Override
    public OrganisationUnitSchemeBean getImmutableInstance() {
        return new OrganisationUnitSchemeBeanImpl(this);
    }
}
