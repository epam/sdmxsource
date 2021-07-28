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
import org.sdmxsource.sdmx.api.model.beans.base.ContactBean;
import org.sdmxsource.sdmx.api.model.beans.base.OrganisationBean;
import org.sdmxsource.sdmx.api.model.mutable.base.ContactMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.OrganisationMutableBean;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Organisation mutable bean.
 */
public abstract class OrganisationMutableBeanImpl extends ItemMutableBeanImpl implements OrganisationMutableBean {
    private static final long serialVersionUID = 4846360904109815354L;
    private List<ContactMutableBean> contacts = new ArrayList<ContactMutableBean>();

    /**
     * Instantiates a new Organisation mutable bean.
     *
     * @param bean the bean
     */
    public OrganisationMutableBeanImpl(OrganisationBean bean) {
        super(bean);
        for (ContactBean contact : bean.getContacts()) {
            addContact(new ContactMutableBeanImpl(contact));
        }
    }

    /**
     * Instantiates a new Organisation mutable bean.
     *
     * @param structureType the structure type
     */
    public OrganisationMutableBeanImpl(SDMX_STRUCTURE_TYPE structureType) {
        super(structureType);
    }

    @Override
    public List<ContactMutableBean> getContacts() {
        return contacts;
    }

    @Override
    public void setContacts(List<ContactMutableBean> contacts) {
        this.contacts = contacts;
    }

    @Override
    public void addContact(ContactMutableBean contact) {
        if (contacts == null) {
            contacts = new ArrayList<ContactMutableBean>();
        }
        contacts.add(contact);
    }
}
