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

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.sdmxsource.sdmx.api.exception.CrossReferenceException;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxSuperBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.base.DataProviderBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.superbeans.SuperBeans;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DataflowSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.registry.ProvisionAgreementSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.registry.ProvisionAgreementSuperBeanImpl;
import org.sdmxsource.sdmx.structureretrieval.manager.InMemorySdmxSuperBeanRetrievalManager;
import org.sdmxsource.sdmx.util.beans.container.SuperBeansImpl;


/**
 * The type Provision super bean builder.
 */
public class ProvisionSuperBeanBuilder extends StructureBuilderImpl<ProvisionAgreementSuperBean, ProvisionAgreementBean> {
    private static final Logger LOG = LoggerFactory.getLogger(ProvisionSuperBeanBuilder.class);

    private final DataflowSuperBeanBuilder dataflowSuperBeanBuilder;

    /**
     * Instantiates a new Provision super bean builder.
     */
    public ProvisionSuperBeanBuilder() {
        this(new DataflowSuperBeanBuilder());
    }

    /**
     * Instantiates a new Provision super bean builder.
     *
     * @param dataflowSuperBeanBuilder the dataflow super bean builder
     */
    public ProvisionSuperBeanBuilder(final DataflowSuperBeanBuilder dataflowSuperBeanBuilder) {
        this.dataflowSuperBeanBuilder = dataflowSuperBeanBuilder;
    }

    @Override
    public ProvisionAgreementSuperBean build(ProvisionAgreementBean buildFrom,
                                             SdmxBeanRetrievalManager retrievalManager,
                                             SuperBeans existingBeans) {
        if (existingBeans == null) {
            existingBeans = new SuperBeansImpl();
        }
        SdmxSuperBeanRetrievalManager superBeanRetrievalManager = new InMemorySdmxSuperBeanRetrievalManager(existingBeans);


        CrossReferenceBean dataflowRef = buildFrom.getStructureUseage();

        LOG.debug("Build provision, resolve dataflow ref: " + dataflowRef);


        DataflowSuperBean dataflowSuperBean = superBeanRetrievalManager.getDataflowSuperBean(dataflowRef);

        if (dataflowSuperBean == null) {
            LOG.debug("Dataflow ref super bean not found, build new: " + dataflowRef);
            DataflowBean dataflow = retrievalManager.getIdentifiableBean(dataflowRef, DataflowBean.class);
            if (dataflow == null) {
                throw new CrossReferenceException(buildFrom.getStructureUseage());
            }
            dataflowSuperBean = dataflowSuperBeanBuilder.build(dataflow, retrievalManager, existingBeans);
            existingBeans.addDataflow(dataflowSuperBean);
        }

        DataProviderBean dataProvider = retrievalManager.getIdentifiableBean(buildFrom.getDataproviderRef(), DataProviderBean.class);
        if (dataProvider == null) {
            throw new CrossReferenceException(buildFrom.getDataproviderRef());
        }
        return new ProvisionAgreementSuperBeanImpl(buildFrom, dataflowSuperBean, dataProvider);
    }
}
