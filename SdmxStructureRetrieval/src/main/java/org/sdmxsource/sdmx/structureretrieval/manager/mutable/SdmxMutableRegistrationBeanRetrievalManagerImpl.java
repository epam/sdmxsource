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
package org.sdmxsource.sdmx.structureretrieval.manager.mutable;

import org.sdmxsource.sdmx.api.manager.retrieval.RegistrationBeanRetrievalManager;
import org.sdmxsource.sdmx.api.manager.retrieval.mutable.SdmxMutableRegistrationBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.RegistrationBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.RegistrationMutableBean;

import java.util.HashSet;
import java.util.Set;


/**
 * This implementation of the SdmxMutableRegistrationBeanRetrievalManager wraps a RegistrationBeanRetrievalManager and mutates the responses.
 */
public class SdmxMutableRegistrationBeanRetrievalManagerImpl implements SdmxMutableRegistrationBeanRetrievalManager {

    private RegistrationBeanRetrievalManager registrationBeanRetrievalManager;

    /**
     * Instantiates a new Sdmx mutable registration bean retrieval manager.
     *
     * @param registrationBeanRetrievalManager the registration bean retrieval manager
     */
    public SdmxMutableRegistrationBeanRetrievalManagerImpl(RegistrationBeanRetrievalManager registrationBeanRetrievalManager) {
        this.registrationBeanRetrievalManager = registrationBeanRetrievalManager;
        if (registrationBeanRetrievalManager == null) {
            throw new IllegalArgumentException("RegistrationBeanRetrievalManager can not be null");
        }
    }

    @Override
    public Set<RegistrationMutableBean> getRegistrations(StructureReferenceBean ref) {
        Set<RegistrationMutableBean> returnSet = new HashSet<RegistrationMutableBean>();
        Set<RegistrationBean> registrationBeans = registrationBeanRetrievalManager.getRegistrations(ref);
        if (registrationBeans != null) {
            for (RegistrationBean currentRegistration : registrationBeans) {
                returnSet.add(currentRegistration.getMutableInstance());
            }
        }
        return returnSet;
    }
}
