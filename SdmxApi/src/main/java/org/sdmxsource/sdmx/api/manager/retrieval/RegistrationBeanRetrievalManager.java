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
package org.sdmxsource.sdmx.api.manager.retrieval;

import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.beans.registry.RegistrationBean;

import java.util.Set;


/**
 * Manages the retrieval of registrations by using simple structures that directly reference a registration
 *
 * @author Matt Nelson
 */
public interface RegistrationBeanRetrievalManager {


    /**
     * Returns all the registrations.  Returns an empty set if no registrations exist.
     *
     * @return all registrations
     */
    Set<RegistrationBean> getAllRegistrations();

    /**
     * Returns all the registrations that match the maintainable reference.  Returns an empty set if no registrations exist that match the criteria.
     *
     * @param ref the ref
     * @return registrations
     */
    Set<RegistrationBean> getRegistrations(MaintainableRefBean ref);

    /**
     * Returns the registration that matches the maintainable reference.  Returns null if no registrations match the criteria.
     *
     * @param ref the ref
     * @return registration
     * @throws IllegalArgumentException if more then one match is found for the reference (this is only possible if the reference does not uniquely identify  a registration, this is only possible if the reference does not have all three parameters populated (agencyId, id and version)
     */
    RegistrationBean getRegistration(MaintainableRefBean ref);

    /**
     * Returns all the registrations against the structure references
     * <p>
     * The structure reference can either be referencing a Provision structure, a Data or MetdataFlow, or a DataProvider.
     *
     * @param structureRef the structure ref
     * @return registrations
     */
    Set<RegistrationBean> getRegistrations(StructureReferenceBean structureRef);

    /**
     * Returns all the registrations against the provision
     *
     * @param provision - the provision to return the registration for
     * @return registrations
     */
    Set<RegistrationBean> getRegistrations(ProvisionAgreementBean provision);


}
