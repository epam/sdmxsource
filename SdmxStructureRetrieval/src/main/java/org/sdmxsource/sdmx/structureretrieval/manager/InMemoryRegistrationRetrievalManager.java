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
package org.sdmxsource.sdmx.structureretrieval.manager;

import org.sdmxsource.sdmx.api.manager.retrieval.RegistrationBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.beans.registry.RegistrationBean;
import org.sdmxsource.sdmx.util.beans.MaintainableUtil;
import org.sdmxsource.util.ObjectUtil;

import java.util.HashSet;
import java.util.Set;


/**
 * The type In memory registration retrieval manager.
 */
public class InMemoryRegistrationRetrievalManager implements RegistrationBeanRetrievalManager {

    private Set<RegistrationBean> registrations;

    /**
     * Instantiates a new In memory registration retrieval manager.
     */
    public InMemoryRegistrationRetrievalManager() {
        this(null);
    }

    /**
     * Instantiates a new In memory registration retrieval manager.
     *
     * @param registrations the registrations
     */
    public InMemoryRegistrationRetrievalManager(Set<RegistrationBean> registrations) {
        if (registrations == null) {
            this.registrations = new HashSet<RegistrationBean>();
        } else {
            this.registrations = registrations;
        }
    }

    /**
     * Delete registration.
     *
     * @param registration the registration
     */
    public void deleteRegistration(RegistrationBean registration) {
        registrations.remove(registration);
    }

    /**
     * Add registrations.
     *
     * @param registrations the registrations
     */
    public void addRegistrations(Set<RegistrationBean> registrations) {
        if (registrations != null) {
            this.registrations.addAll(registrations);
        }
    }

    @Override
    public Set<RegistrationBean> getAllRegistrations() {
        return new HashSet<RegistrationBean>(registrations);
    }

    @Override
    public Set<RegistrationBean> getRegistrations(MaintainableRefBean ref) {
        MaintainableUtil<RegistrationBean> filter = new MaintainableUtil<RegistrationBean>();
        return filter.filterCollection(registrations, ref);
    }

    @Override
    public RegistrationBean getRegistration(MaintainableRefBean ref) {
        Set<RegistrationBean> registrations = getRegistrations(ref);
        if (!ObjectUtil.validCollection(registrations)) {
            return null;
        }
        if (registrations.size() > 1) {
            throw new IllegalArgumentException("More then one registration returned for reference (expected only one):" + ref);
        }
        return (RegistrationBean) registrations.toArray()[0];
    }

    @Override
    public Set<RegistrationBean> getRegistrations(StructureReferenceBean provisionRefs) {
        Set<RegistrationBean> returnSet = new HashSet<RegistrationBean>();
        for (RegistrationBean currentReg : registrations) {
            //HACK - this is totally incorrect, read the JavaDoc
            if (currentReg.getProvisionAgreementRef().getTargetUrn().equals(provisionRefs.getTargetUrn())) {
                returnSet.add(currentReg);
            }
        }
        return returnSet;
    }

    @Override
    public Set<RegistrationBean> getRegistrations(ProvisionAgreementBean provision) {
        return getRegistrations(provision.asReference());
    }
}
