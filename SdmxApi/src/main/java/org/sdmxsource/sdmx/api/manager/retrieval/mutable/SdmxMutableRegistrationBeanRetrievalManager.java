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
package org.sdmxsource.sdmx.api.manager.retrieval.mutable;

import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.RegistrationMutableBean;

import java.util.Set;


/**
 * Manages the retrieval of mutable registrations using simple reference of structures that directly reference a registration
 *
 * @author Matt Nelson
 */
public interface SdmxMutableRegistrationBeanRetrievalManager {


    /**
     * Returns a list of registrations that match the StructureReferenceBean
     * <p>
     * Returns an empty set if no registrations match the criteria
     *
     * @param ref The structure reference can either be referencing a Provision structure, a Data or MetdataFlow, or a DataProvider.
     * @return registrations
     */
    Set<RegistrationMutableBean> getRegistrations(StructureReferenceBean ref);


}
