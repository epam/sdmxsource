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
package org.sdmxsource.sdmx.api.model.mutable.base;

import java.util.List;

/**
 * The interface Contact mutable bean.
 */
public interface ContactMutableBean extends MutableBean {

    /**
     * Returns the id of the contact
     *
     * @return the id, or null if there is no id
     */
    String getId();

    /**
     * Sets the id of the contact
     *
     * @param id the id
     */
    void setId(String id);

    /**
     * Returns the names of the contact
     *
     * @return list of names, or an empty list if none exist
     */
    List<TextTypeWrapperMutableBean> getNames();

    /**
     * Overwrites the existing list with the one passed in
     *
     * @param names the names
     */
    void setNames(List<TextTypeWrapperMutableBean> names);

    /**
     * Adds a new name to the list, creates a new list if it is null
     *
     * @param name the name
     */
    void addName(TextTypeWrapperMutableBean name);

    /**
     * Returns the roles of the contact
     *
     * @return list of roles, or an empty list if none exist
     */
    List<TextTypeWrapperMutableBean> getRoles();

    /**
     * Overwrites the existing list with the one passed in
     *
     * @param roles the roles
     */
    void setRoles(List<TextTypeWrapperMutableBean> roles);

    /**
     * Adds a new role to the list, creates a new list if it is null
     *
     * @param role the role
     */
    void addRole(TextTypeWrapperMutableBean role);

    /**
     * Returns the departments of the contact
     *
     * @return list of departments, or an empty list if none exist
     */
    List<TextTypeWrapperMutableBean> getDepartments();

    /**
     * Overwrites the existing list with the one passed in
     *
     * @param depts the depts
     */
    void setDepartments(List<TextTypeWrapperMutableBean> depts);

    /**
     * Adds a new department to the list, creates a new list if it is null
     *
     * @param role the role
     */
    void addDepartment(TextTypeWrapperMutableBean role);

    /**
     * Returns the email of the contact
     *
     * @return list of email, or an empty list if none exist
     */
    List<String> getEmail();

    /**
     * Overwrites the existing list with the one passed in
     *
     * @param email the email
     */
    void setEmail(List<String> email);

    /**
     * Adds a new email to the list, creates a new list if it is null
     *
     * @param email the email
     */
    void addEmail(String email);

    /**
     * Returns the fax of the contact
     *
     * @return list of fax, or an empty list if none exist
     */
    List<String> getFax();

    /**
     * Overwrites the existing list with the one passed in
     *
     * @param fax the fax
     */
    void setFax(List<String> fax);

    /**
     * Adds a new fax to the list, creates a new list if it is null
     *
     * @param fax the fax
     */
    void addFax(String fax);

    /**
     * Returns the telephone of the contact
     *
     * @return list of telephone, or an empty list if none exist
     */
    List<String> getTelephone();

    /**
     * Overwrites the existing list with the one passed in
     *
     * @param telephone the telephone
     */
    void setTelephone(List<String> telephone);

    /**
     * Adds a new telephone to the list, creates a new list if it is null
     *
     * @param telephone the telephone
     */
    void addTelephone(String telephone);

    /**
     * Returns the uris of the contact
     *
     * @return list of uris, or an empty list if none exist
     */
    List<String> getUri();

    /**
     * Overwrites the existing list with the one passed in
     *
     * @param uri the uri
     */
    void setUri(List<String> uri);

    /**
     * Adds a new uri to the list, creates a new list if it is null
     *
     * @param uri the uri
     */
    void addUri(String uri);

    /**
     * Returns the x400 of the contact
     *
     * @return list of emails, or an empty list if none exist
     */
    List<String> getX400();

    /**
     * Overwrites the existing list with the one passed in
     *
     * @param uri the uri
     */
    void setX400(List<String> uri);

    /**
     * Adds a new X400 to the list, creates a new list if it is null
     *
     * @param uri the uri
     */
    void addX400(String uri);


}
