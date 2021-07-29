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
package org.sdmxsource.sdmx.api.model.beans.base;

import java.util.List;


/**
 * Represents a contact (individual or organisation)
 */
public interface ContactBean extends SDMXBean {

    /**
     * Returns the id of the contact
     *
     * @return the id, or null if there is no id
     */
    String getId();

    /**
     * Returns the names of the contact
     *
     * @return list of names, or an empty list if none exist
     */
    List<TextTypeWrapper> getName();

    /**
     * Returns the roles of the contact
     *
     * @return list of roles, or an empty list if none exist
     */
    List<TextTypeWrapper> getRole();

    /**
     * Returns the departments of the contact
     *
     * @return list of departments, or an empty list if none exist
     */
    List<TextTypeWrapper> getDepartments();

    /**
     * Returns the email of the contact
     *
     * @return list of email, or an empty list if none exist
     */
    List<String> getEmail();

    /**
     * Returns the fax of the contact
     *
     * @return list of fax, or an empty list if none exist
     */
    List<String> getFax();

    /**
     * Returns the telephone of the contact
     *
     * @return list of telephone, or an empty list if none exist
     */
    List<String> getTelephone();

    /**
     * Returns the uris of the contact
     *
     * @return list of uris, or an empty list if none exist
     */
    List<String> getUri();

    /**
     * Returns the x400 of the contact
     *
     * @return list of emails, or an empty list if none exist
     */
    List<String> getX400();
}
