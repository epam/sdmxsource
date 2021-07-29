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
import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.sdmx.api.model.mutable.base.ContactMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.TextTypeWrapperMutableBean;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Contact mutable bean.
 */
public class ContactMutableBeanImpl extends MutableBeanImpl implements ContactMutableBean {
    private static final long serialVersionUID = -9000931565464863175L;
    private String id;
    private List<TextTypeWrapperMutableBean> names = new ArrayList<TextTypeWrapperMutableBean>();
    private List<TextTypeWrapperMutableBean> roles = new ArrayList<TextTypeWrapperMutableBean>();
    private List<TextTypeWrapperMutableBean> departments = new ArrayList<TextTypeWrapperMutableBean>();
    private List<String> email = new ArrayList<String>();
    private List<String> fax = new ArrayList<String>();
    private List<String> telephone = new ArrayList<String>();
    private List<String> uri = new ArrayList<String>();
    private List<String> x400 = new ArrayList<String>();


    /**
     * Instantiates a new Contact mutable bean.
     */
    public ContactMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.CONTACT);
    }

    /**
     * Instantiates a new Contact mutable bean.
     *
     * @param contact the contact
     */
    public ContactMutableBeanImpl(ContactBean contact) {
        super(contact);
        this.id = contact.getId();

        copyTextTypes(contact.getName(), names);
        copyTextTypes(contact.getRole(), roles);
        copyTextTypes(contact.getDepartments(), departments);
        this.email = new ArrayList<String>(contact.getEmail());
        this.fax = new ArrayList<String>(contact.getFax());
        this.telephone = new ArrayList<String>(contact.getTelephone());
        this.uri = new ArrayList<String>(contact.getUri());
        this.x400 = new ArrayList<String>(contact.getX400());
    }

    private void copyTextTypes(List<TextTypeWrapper> textType, List<TextTypeWrapperMutableBean> copyTo) {
        if (textType != null) {
            for (TextTypeWrapper currentTextType : textType) {
                copyTo.add(new TextTypeWrapperMutableBeanImpl(currentTextType));
            }
        }
    }

    @Override
    public void addName(TextTypeWrapperMutableBean name) {
        if (this.names == null) {
            this.names = new ArrayList<TextTypeWrapperMutableBean>();
        }
        this.names.add(name);
    }

    @Override
    public void addRole(TextTypeWrapperMutableBean role) {
        if (this.roles == null) {
            this.roles = new ArrayList<TextTypeWrapperMutableBean>();
        }
        this.roles.add(role);
    }

    @Override
    public void addDepartment(TextTypeWrapperMutableBean dept) {
        if (this.departments == null) {
            this.departments = new ArrayList<TextTypeWrapperMutableBean>();
        }
        this.departments.add(dept);
    }

    @Override
    public void addEmail(String email) {
        if (this.email == null) {
            this.email = new ArrayList<String>();
        }
        this.email.add(email);
    }

    @Override
    public void addFax(String fax) {
        if (this.fax == null) {
            this.fax = new ArrayList<String>();
        }
        this.fax.add(fax);
    }

    @Override
    public void addTelephone(String telephone) {
        if (this.telephone == null) {
            this.telephone = new ArrayList<String>();
        }
        this.telephone.add(telephone);
    }

    @Override
    public void addUri(String uri) {
        if (this.uri == null) {
            this.uri = new ArrayList<String>();
        }
        this.uri.add(uri);
    }

    @Override
    public void addX400(String x400) {
        if (this.x400 == null) {
            this.x400 = new ArrayList<String>();
        }
        this.x400.add(x400);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public List<TextTypeWrapperMutableBean> getNames() {
        return names;
    }

    @Override
    public void setNames(List<TextTypeWrapperMutableBean> name) {
        this.names = name;
    }

    @Override
    public List<TextTypeWrapperMutableBean> getRoles() {
        return roles;
    }

    @Override
    public void setRoles(List<TextTypeWrapperMutableBean> role) {
        this.roles = role;
    }

    @Override
    public List<TextTypeWrapperMutableBean> getDepartments() {
        return departments;
    }

    @Override
    public void setDepartments(List<TextTypeWrapperMutableBean> departments) {
        this.departments = departments;
    }

    @Override
    public List<String> getEmail() {
        return email;
    }

    @Override
    public void setEmail(List<String> email) {
        this.email = email;
    }

    @Override
    public List<String> getFax() {
        return fax;
    }

    @Override
    public void setFax(List<String> fax) {
        this.fax = fax;
    }

    @Override
    public List<String> getTelephone() {
        return telephone;
    }

    @Override
    public void setTelephone(List<String> telephone) {
        this.telephone = telephone;
    }

    @Override
    public List<String> getUri() {
        return uri;
    }

    @Override
    public void setUri(List<String> uri) {
        this.uri = uri;
    }

    @Override
    public List<String> getX400() {
        return x400;
    }

    @Override
    public void setX400(List<String> x400) {
        this.x400 = x400;
    }


}
