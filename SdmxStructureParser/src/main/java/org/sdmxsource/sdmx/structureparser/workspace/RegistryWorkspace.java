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
package org.sdmxsource.sdmx.structureparser.workspace;

import org.sdmxsource.sdmx.api.model.StructureWorkspace;

/**
 * The structure workspace holds reference to a SubmitRegistryRequest document,
 * the contents of this document can be Structures, Registrations or Provisions
 */
public interface RegistryWorkspace {

    /**
     * Returns the provision workspace for this workspace
     *
     * @return provision workspace
     */
    ProvisionWorkspace getProvisionWorkspace();

    /**
     * Returns the registration workspace for this workspace
     *
     * @return registration workspace
     */
    RegistrationWorkspace getRegistrationWorkspace();

    /**
     * Returns the structure workspace for this workspace
     *
     * @return structure workspace
     */
    StructureWorkspace getStructureWorkspace();

    /**
     * Returns true if getProvisionWorkspace() returns a not null object
     *
     * @return provision workspace
     */
    boolean hasProvisionWorkspace();

    /**
     * Returns true if getRegistrationWorkspace() returns a not null object
     *
     * @return regitration workspace
     */
    boolean hasRegitrationWorkspace();

    /**
     * Returns true if getStructureWorkspace() returns a not null object
     *
     * @return structure workspace
     */
    boolean hasStructureWorkspace();
}
