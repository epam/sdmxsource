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
package org.sdmxsource.sdmx.structureparser.builder.superbeans.impl;

import org.sdmxsource.sdmx.api.exception.CrossReferenceException;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxSuperBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.beans.registry.RegistrationBean;
import org.sdmxsource.sdmx.api.model.superbeans.SuperBeans;
import org.sdmxsource.sdmx.api.model.superbeans.registry.ProvisionAgreementSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.registry.RegistrationSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.registry.RegistrationSuperBeanImpl;
import org.sdmxsource.sdmx.structureretrieval.manager.InMemorySdmxSuperBeanRetrievalManager;
import org.sdmxsource.sdmx.util.beans.container.SuperBeansImpl;


/**
 * The type Registration super bean builder.
 */
public class RegistrationSuperBeanBuilder extends StructureBuilderImpl<RegistrationSuperBean, RegistrationBean> {

    private final ProvisionSuperBeanBuilder provisionSuperBeanBuilder;

    /**
     * Instantiates a new Registration super bean builder.
     */
    public RegistrationSuperBeanBuilder() {
        this(new ProvisionSuperBeanBuilder());
    }

    /**
     * Instantiates a new Registration super bean builder.
     *
     * @param provisionSuperBeanBuilder the provision super bean builder
     */
    public RegistrationSuperBeanBuilder(final ProvisionSuperBeanBuilder provisionSuperBeanBuilder) {
        this.provisionSuperBeanBuilder = provisionSuperBeanBuilder;
    }

    @Override
    public RegistrationSuperBean build(RegistrationBean buildFrom,
                                       SdmxBeanRetrievalManager retrievalManager,
                                       SuperBeans existingBeans) {
        if (existingBeans == null) {
            existingBeans = new SuperBeansImpl();
        }
        SdmxSuperBeanRetrievalManager superBeanRetrievalManager = new InMemorySdmxSuperBeanRetrievalManager(existingBeans);

        CrossReferenceBean provRef = buildFrom.getProvisionAgreementRef();
        ProvisionAgreementSuperBean provisionSuperBean = null;

        provisionSuperBean = superBeanRetrievalManager.getProvisionAgreementSuperBean(provRef);

        if (provisionSuperBean == null) {
            ProvisionAgreementBean provision = retrievalManager.getIdentifiableBean(provRef, ProvisionAgreementBean.class);
            if (provision == null) {
                throw new CrossReferenceException(buildFrom.getProvisionAgreementRef());
            }
            provisionSuperBean = provisionSuperBeanBuilder.build(provision, retrievalManager, existingBeans);
            existingBeans.addProvision(provisionSuperBean);
        }
        return new RegistrationSuperBeanImpl(buildFrom, provisionSuperBean);
    }
}
