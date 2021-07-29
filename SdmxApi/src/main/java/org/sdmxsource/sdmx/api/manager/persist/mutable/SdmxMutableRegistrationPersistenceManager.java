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
package org.sdmxsource.sdmx.api.manager.persist.mutable;

import org.sdmxsource.sdmx.api.model.mutable.registry.RegistrationMutableBean;

/**
 * Manages the persistence of registrations - this interface deals with mutable beans, so extra validation is performed to
 * ensure the mutable bean conforms to the SDMX rules
 *
 * @author Matt Nelson
 */
public interface SdmxMutableRegistrationPersistenceManager {


    /**
     * Stores the registration and returns a copy of the stored instance, in mutable form
     *
     * @param registrationBean the registration bean
     * @return registration mutable bean
     */
    RegistrationMutableBean saveRegistration(RegistrationMutableBean registrationBean);


    /**
     * Replaces any registrations against the provisions that the registrations specify
     *
     * @param registration the registration
     */
    void replaceRegistration(RegistrationMutableBean registration);

    /**
     * Deletes a registration with the same urn of the passed in Registrations - if any registartions could not be found then an error will NOT be issued, the
     * registration will be ignored
     *
     * @param registration the registration
     */
    void deleteRegistration(RegistrationMutableBean registration);

}
