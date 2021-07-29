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
package org.sdmxsource.sdmx.api.manager.persist;

import org.sdmxsource.sdmx.api.model.beans.registry.RegistrationBean;

/**
 * Manages the persistence of registrations
 *
 * @author Matt Nelson
 */
public interface RegistrationPersistenceManager {


    /**
     * Persists the Registration, treated as DATASET_ACTION.APPEND
     * <p>
     * Will not overwrite a Registration if one already exists with the same URN
     *
     * @param registration the registration
     */
    void saveRegistration(RegistrationBean registration);

    /**
     * Persists the Registration, treated as DATASET_ACTION.REPLACE
     * <p>
     * REPLACE action will replace any registration that matches the URN of the passed in Registration
     *
     * @param registration the registration
     */
    void replaceRegistration(RegistrationBean registration);

    /**
     * Deletes a registration with the same urn of the passed in Registrations - if any registrations could not be found then an error will NOT be issued, the
     * registration will be ignored
     *
     * @param registration the registration
     */
    void deleteRegistration(RegistrationBean registration);
}
