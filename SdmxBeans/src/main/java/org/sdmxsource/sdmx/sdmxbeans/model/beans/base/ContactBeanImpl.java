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

import org.sdmx.resources.sdmxml.schemas.v21.common.TextType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.ContactType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.ContactBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.sdmx.api.model.mutable.base.ContactMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.TextTypeWrapperMutableBean;
import org.sdmxsource.sdmx.util.beans.ValidationUtil;
import org.sdmxsource.util.ObjectUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The type Contact bean.
 */
public class ContactBeanImpl extends SDMXBeanImpl implements ContactBean {
    private static final long serialVersionUID = -1785478523789992314L;
    private String id;
    private List<TextTypeWrapper> name = new ArrayList<TextTypeWrapper>();
    private List<TextTypeWrapper> role = new ArrayList<TextTypeWrapper>();
    private List<TextTypeWrapper> departments = new ArrayList<TextTypeWrapper>();
    private List<String> email = new ArrayList<String>();
    private List<String> fax = new ArrayList<String>();
    private List<String> telephone = new ArrayList<String>();
    private List<String> uri = new ArrayList<String>();
    private List<String> x400 = new ArrayList<String>();


    /**
     * Instantiates a new Contact bean.
     *
     * @param mutableBean the mutable bean
     */
    public ContactBeanImpl(ContactMutableBean mutableBean) {
        this(mutableBean, null);
    }

    /**
     * Instantiates a new Contact bean.
     *
     * @param mutableBean the mutable bean
     * @param parent      the parent
     */
    public ContactBeanImpl(ContactMutableBean mutableBean, SDMXBean parent) {
        super(mutableBean, parent);
        this.id = mutableBean.getId();
        copyTextTypes(name, mutableBean.getNames());
        copyTextTypes(role, mutableBean.getRoles());
        copyTextTypes(departments, mutableBean.getDepartments());
        if (mutableBean.getEmail() != null) {
            this.email = new ArrayList<String>(mutableBean.getEmail());
        }
        if (mutableBean.getTelephone() != null) {
            this.telephone = new ArrayList<String>(mutableBean.getTelephone());
        }
        if (mutableBean.getFax() != null) {
            this.fax = new ArrayList<String>(mutableBean.getFax());
        }
        if (mutableBean.getUri() != null) {
            this.uri = new ArrayList<String>(mutableBean.getUri());
        }
        if (mutableBean.getX400() != null) {
            this.x400 = new ArrayList<String>(mutableBean.getX400());
        }
        validate();
    }

    /**
     * Instantiates a new Contact bean.
     *
     * @param name        the name
     * @param role        the role
     * @param departments the departments
     * @param email       the email
     * @param fax         the fax
     * @param telephone   the telephone
     * @param uri         the uri
     * @param x400        the x 400
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM ATTRIBUTES			   ////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ContactBeanImpl(List<TextTypeWrapper> name,
                           List<TextTypeWrapper> role, List<TextTypeWrapper> departments,
                           List<String> email, List<String> fax,
                           List<String> telephone, List<String> uri, List<String> x400) {
        super(SDMX_STRUCTURE_TYPE.CONTACT, null);

        if (name != null) {
            this.name = new ArrayList<TextTypeWrapper>(name);
        }
        if (role != null) {
            this.role = new ArrayList<TextTypeWrapper>(role);
        }
        if (departments != null) {
            this.departments = new ArrayList<TextTypeWrapper>(departments);
        }
        if (email != null) {
            this.email = new ArrayList<String>(email);
        }
        if (fax != null) {
            this.fax = new ArrayList<String>(fax);
        }
        if (telephone != null) {
            this.telephone = new ArrayList<String>(telephone);
        }
        if (uri != null) {
            this.uri = new ArrayList<String>(uri);
        }
        if (x400 != null) {
            this.x400 = new ArrayList<String>(x400);
        }
        validate();
    }

    /**
     * Instantiates a new Contact bean.
     *
     * @param contactType the contact type
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA			///////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ContactBeanImpl(org.sdmx.resources.sdmxml.schemas.v21.message.ContactType contactType) {
        super(SDMX_STRUCTURE_TYPE.CONTACT, null);
        if (ObjectUtil.validCollection(contactType.getDepartmentList())) {
            for (TextType tt : contactType.getDepartmentList()) {
                departments.add(new TextTypeWrapperImpl(tt.getLang(), tt.getStringValue(), null));
            }
        }
        if (ObjectUtil.validCollection(contactType.getEmailList())) {
            email = new ArrayList<String>(contactType.getEmailList());
        }
        if (ObjectUtil.validCollection(contactType.getFaxList())) {
            fax = new ArrayList<String>(contactType.getFaxList());
        }
        if (ObjectUtil.validCollection(contactType.getNameList())) {
            for (TextType tt : contactType.getNameList()) {
                name.add(new TextTypeWrapperImpl(tt.getLang(), tt.getStringValue(), null));
            }
        }
        if (ObjectUtil.validCollection(contactType.getRoleList())) {
            for (TextType tt : contactType.getRoleList()) {
                role.add(new TextTypeWrapperImpl(tt.getLang(), tt.getStringValue(), null));
            }
        }
        if (ObjectUtil.validCollection(contactType.getTelephoneList())) {
            telephone = new ArrayList<String>(
                    contactType.getTelephoneList());
        }
        if (ObjectUtil.validCollection(contactType.getURIList())) {
            uri = new ArrayList<String>(contactType.getURIList());
        }
        if (ObjectUtil.validCollection(contactType.getX400List())) {
            x400 = new ArrayList<String>(contactType.getX400List());
        }
        validate();
    }

    /**
     * Instantiates a new Contact bean.
     *
     * @param contactType the contact type
     * @param parent      the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA			///////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ContactBeanImpl(ContactType contactType, SDMXBean parent) {
        super(SDMX_STRUCTURE_TYPE.CONTACT, parent);
        this.id = contactType.getId();
        if (ObjectUtil.validCollection(contactType.getDepartmentList())) {
            for (TextType tt : contactType.getDepartmentList()) {
                departments.add(new TextTypeWrapperImpl(tt.getLang(), tt.getStringValue(), null));
            }
        }
        if (ObjectUtil.validCollection(contactType.getEmailList())) {
            email = new ArrayList<String>(contactType.getEmailList());
        }
        if (ObjectUtil.validCollection(contactType.getFaxList())) {
            fax = new ArrayList<String>(contactType.getFaxList());
        }
        if (ObjectUtil.validCollection(contactType.getNameList())) {
            for (TextType tt : contactType.getNameList()) {
                name.add(new TextTypeWrapperImpl(tt.getLang(), tt.getStringValue(), null));
            }
        }
        if (ObjectUtil.validCollection(contactType.getRoleList())) {
            for (TextType tt : contactType.getRoleList()) {
                role.add(new TextTypeWrapperImpl(tt.getLang(), tt.getStringValue(), null));
            }
        }
        if (ObjectUtil.validCollection(contactType.getTelephoneList())) {
            telephone = new ArrayList<String>(
                    contactType.getTelephoneList());
        }
        if (ObjectUtil.validCollection(contactType.getURIList())) {
            uri = new ArrayList<String>(contactType.getURIList());
        }
        if (ObjectUtil.validCollection(contactType.getX400List())) {
            x400 = new ArrayList<String>(contactType.getX400List());
        }
        validate();
    }

    /**
     * Instantiates a new Contact bean.
     *
     * @param contactType the contact type
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.0 SCHEMA			///////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ContactBeanImpl(org.sdmx.resources.sdmxml.schemas.v20.message.ContactType contactType) {
        super(SDMX_STRUCTURE_TYPE.CONTACT, null);
        if (ObjectUtil.validCollection(contactType.getDepartmentList())) {
            for (org.sdmx.resources.sdmxml.schemas.v20.common.TextType tt : contactType.getDepartmentList()) {
                // Only add departments that are non-null
                if (ObjectUtil.validString(tt.getStringValue())) {
                    departments.add(new TextTypeWrapperImpl(tt.getLang(), tt.getStringValue(), null));
                }
            }
        }
        if (ObjectUtil.validCollection(contactType.getEmailList())) {
            email = new ArrayList<String>(contactType.getEmailList());
        }
        if (ObjectUtil.validCollection(contactType.getFaxList())) {
            fax = new ArrayList<String>(contactType.getFaxList());
        }
        if (ObjectUtil.validCollection(contactType.getNameList())) {
            for (org.sdmx.resources.sdmxml.schemas.v20.common.TextType tt : contactType.getNameList()) {
                // Only add names that are non-null
                if (ObjectUtil.validString(tt.getStringValue())) {
                    name.add(new TextTypeWrapperImpl(tt.getLang(), tt.getStringValue(), null));
                }
            }
        }
        if (ObjectUtil.validCollection(contactType.getRoleList())) {
            for (org.sdmx.resources.sdmxml.schemas.v20.common.TextType tt : contactType.getRoleList()) {
                // Only add roles that are non-null
                if (ObjectUtil.validString(tt.getStringValue())) {
                    role.add(new TextTypeWrapperImpl(tt.getLang(), tt.getStringValue(), null));
                }
            }
        }
        if (ObjectUtil.validCollection(contactType.getTelephoneList())) {
            telephone = new ArrayList<String>(contactType.getTelephoneList());
        }
        if (ObjectUtil.validCollection(contactType.getURIList())) {
            uri = new ArrayList<String>(contactType.getURIList());
        }
        if (ObjectUtil.validCollection(contactType.getX400List())) {
            x400 = new ArrayList<String>(contactType.getX400List());
        }
        validate();
    }

    /**
     * Instantiates a new Contact bean.
     *
     * @param contactType the contact type
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V1.0 SCHEMA			///////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ContactBeanImpl(org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.message.ContactType contactType) {
        super(SDMX_STRUCTURE_TYPE.CONTACT, null);
        if (ObjectUtil.validCollection(contactType.getDepartmentList())) {
            for (org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.common.TextType tt : contactType.getDepartmentList()) {
                departments.add(new TextTypeWrapperImpl(tt.getLang(), tt.getStringValue(), null));
            }
        }
        if (ObjectUtil.validCollection(contactType.getEmailList())) {
            email = new ArrayList<String>(contactType.getEmailList());
        }
        if (ObjectUtil.validCollection(contactType.getFaxList())) {
            fax = new ArrayList<String>(contactType.getFaxList());
        }
        if (ObjectUtil.validCollection(contactType.getNameList())) {
            for (org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.common.TextType tt : contactType.getNameList()) {
                name.add(new TextTypeWrapperImpl(tt.getLang(), tt.getStringValue(), null));
            }
        }
        if (ObjectUtil.validCollection(contactType.getRoleList())) {
            for (org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.common.TextType tt : contactType.getRoleList()) {
                role.add(new TextTypeWrapperImpl(tt.getLang(), tt.getStringValue(), null));
            }
        }
        if (ObjectUtil.validCollection(contactType.getTelephoneList())) {
            telephone = new ArrayList<String>(
                    contactType.getTelephoneList());
        }
        if (ObjectUtil.validCollection(contactType.getURIList())) {
            uri = new ArrayList<String>(contactType.getURIList());
        }
        if (ObjectUtil.validCollection(contactType.getX400List())) {
            x400 = new ArrayList<String>(contactType.getX400List());
        }
        validate();
    }

    private void copyTextTypes(List<TextTypeWrapper> copyTo, List<TextTypeWrapperMutableBean> mutable) {
        if (mutable != null) {
            for (TextTypeWrapperMutableBean currentTextType : mutable) {
                copyTo.add(new TextTypeWrapperImpl(currentTextType, this));
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATE			///////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() {
        if (ObjectUtil.validString(id)) {
            this.id = ValidationUtil.cleanAndValidateId(getId(), true);
        }
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////
    //////////GETTTERS			//////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public String getId() {
        return id;
    }


    @Override
    public boolean deepEquals(SDMXBean bean, boolean includeFinalProperties) {
        if (bean instanceof ContactBean) {
            ContactBean contact = (ContactBean) bean;

            if (!super.equivalent(contact.getDepartments(), this.departments, includeFinalProperties)) {
                return false;
            }
            if (!super.equivalent(contact.getName(), this.name, includeFinalProperties)) {
                return false;
            }
            if (!super.equivalent(contact.getRole(), this.role, includeFinalProperties)) {
                return false;
            }
            if (!ObjectUtil.equivalent(contact.getEmail(), this.email)) {
                return false;
            }
            if (!ObjectUtil.equivalent(contact.getFax(), this.fax)) {
                return false;
            }
            if (!ObjectUtil.equivalent(contact.getUri(), this.uri)) {
                return false;
            }
            if (!ObjectUtil.equivalent(contact.getX400(), this.x400)) {
                return false;
            }
            if (!ObjectUtil.equivalent(contact.getTelephone(), this.telephone)) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public List<String> getEmail() {
        return new ArrayList<String>(email);
    }

    @Override
    public List<TextTypeWrapper> getName() {
        return new ArrayList<TextTypeWrapper>(name);
    }

    @Override
    public List<TextTypeWrapper> getRole() {
        return new ArrayList<TextTypeWrapper>(role);
    }

    @Override
    public List<TextTypeWrapper> getDepartments() {
        return new ArrayList<TextTypeWrapper>(departments);
    }

    @Override
    public List<String> getFax() {
        return new ArrayList<String>(fax);
    }

    @Override
    public List<String> getTelephone() {
        return new ArrayList<String>(telephone);
    }

    @Override
    public List<String> getUri() {
        return new ArrayList<String>(uri);
    }

    @Override
    public List<String> getX400() {
        return new ArrayList<String>(x400);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////	

    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = new HashSet<SDMXBean>();
        super.addToCompositeSet(name, composites);
        super.addToCompositeSet(role, composites);
        super.addToCompositeSet(departments, composites);
        return composites;
    }
}
