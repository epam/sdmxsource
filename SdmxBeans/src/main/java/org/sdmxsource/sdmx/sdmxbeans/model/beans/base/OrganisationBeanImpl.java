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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.base;

import org.apache.xmlbeans.XmlObject;
import org.sdmx.resources.sdmxml.schemas.v20.common.AnnotationsType;
import org.sdmx.resources.sdmxml.schemas.v20.common.TextType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.ContactType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.*;
import org.sdmxsource.sdmx.api.model.mutable.base.ContactMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.OrganisationMutableBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * The type Organisation bean.
 */
public abstract class OrganisationBeanImpl extends ItemBeanImpl implements OrganisationBean {
    private static final long serialVersionUID = -2778853131268394387L;
    private List<ContactBean> contacts = new ArrayList<ContactBean>();


    /**
     * Instantiates a new Organisation bean.
     *
     * @param bean   the bean
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public OrganisationBeanImpl(OrganisationMutableBean bean, ItemSchemeBean parent) {
        super(bean, parent);
        for (ContactMutableBean currentContact : bean.getContacts()) {
            this.contacts.add(new ContactBeanImpl(currentContact, this));
        }
    }

    /**
     * Instantiates a new Organisation bean.
     *
     * @param type          the type
     * @param structureType the structure type
     * @param parent        the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public OrganisationBeanImpl(org.sdmx.resources.sdmxml.schemas.v21.structure.OrganisationType type,
                                SDMX_STRUCTURE_TYPE structureType,
                                ItemSchemeBean parent) {
        super(type, structureType, parent);
        for (ContactType contact : type.getContactList()) {
            this.contacts.add(new ContactBeanImpl(contact, this));
        }
    }

    /**
     * Instantiates a new Organisation bean.
     *
     * @param createdFrom     the created from
     * @param structureType   the structure type
     * @param contact         the contact
     * @param id              the id
     * @param uri             the uri
     * @param name            the name
     * @param description     the description
     * @param annotationsType the annotations type
     * @param parent          the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public OrganisationBeanImpl(XmlObject createdFrom, SDMX_STRUCTURE_TYPE structureType,
                                org.sdmx.resources.sdmxml.schemas.v20.structure.ContactType contact,
                                String id,
                                String uri,
                                List<TextType> name,
                                List<TextType> description,
                                AnnotationsType annotationsType,
                                IdentifiableBean parent) {
        super(createdFrom, structureType, id, uri, name, description, annotationsType, parent);
    }

    /**
     * Instantiates a new Organisation bean.
     *
     * @param createdFrom     the created from
     * @param structureType   the structure type
     * @param contact         the contact
     * @param id              the id
     * @param uri             the uri
     * @param name            the name
     * @param description     the description
     * @param annotationsType the annotations type
     * @param parent          the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public OrganisationBeanImpl(XmlObject createdFrom,
                                SDMX_STRUCTURE_TYPE structureType,
                                org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.ContactType contact,
                                String id,
                                String uri,
                                List<org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.common.TextType> name,
                                List<org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.common.TextType> description,
                                org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.common.AnnotationsType annotationsType,
                                IdentifiableBean parent) {
        super(createdFrom, structureType, id, uri, name, description, annotationsType, parent);

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(contacts, composites);
        return composites;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public List<ContactBean> getContacts() {
        return new ArrayList<ContactBean>(contacts);
    }

    /**
     * Deep equals internal boolean.
     *
     * @param bean                   the bean
     * @param includeFinalProperties the include final properties
     * @return the boolean
     */
    protected boolean deepEqualsInternal(OrganisationBean bean, boolean includeFinalProperties) {
        if (!super.equivalent(this.contacts, bean.getContacts(), includeFinalProperties)) {
            return false;
        }
        return super.deepEqualsInternal(bean, includeFinalProperties);
    }
}
