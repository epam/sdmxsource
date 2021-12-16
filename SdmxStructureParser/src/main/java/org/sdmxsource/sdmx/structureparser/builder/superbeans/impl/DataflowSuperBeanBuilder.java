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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sdmxsource.sdmx.api.exception.CrossReferenceException;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxSuperBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.superbeans.SuperBeans;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DataStructureSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DataflowSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.datastructure.DataflowSuperBeanImpl;
import org.sdmxsource.sdmx.structureretrieval.manager.InMemorySdmxSuperBeanRetrievalManager;
import org.sdmxsource.sdmx.util.beans.container.SuperBeansImpl;


/**
 * The type Dataflow super bean builder.
 */
public class DataflowSuperBeanBuilder extends StructureBuilderImpl<DataflowSuperBean, DataflowBean> {
    private static final Logger LOG = LogManager.getLogger(SuperBeansBuilderImpl.class);

    private final DataStructureSuperBeanBuilder dataStructureSuperBeanBuilder;

    /**
     * Instantiates a new Dataflow super bean builder.
     */
    public DataflowSuperBeanBuilder() {
        this(new DataStructureSuperBeanBuilder());
    }

    /**
     * Instantiates a new Dataflow super bean builder.
     *
     * @param dataStructureSuperBeanBuilder the data structure super bean builder
     */
    public DataflowSuperBeanBuilder(final DataStructureSuperBeanBuilder dataStructureSuperBeanBuilder) {
        this.dataStructureSuperBeanBuilder = dataStructureSuperBeanBuilder;
    }

    @Override
    public DataflowSuperBean build(DataflowBean buildFrom,
                                   SdmxBeanRetrievalManager retrievalManager,
                                   SuperBeans existingBeans) {
        if (existingBeans == null) {
            existingBeans = new SuperBeansImpl();
        }
        SdmxSuperBeanRetrievalManager superBeanRetrievalManager = new InMemorySdmxSuperBeanRetrievalManager(existingBeans);

        CrossReferenceBean dsdRef = buildFrom.getDataStructureRef();

        LOG.debug("Build Dataflow SuperBean. Get DSD Superbean :" + dsdRef);

        DataStructureSuperBean kfSuperBean = superBeanRetrievalManager.getDataStructureSuperBean(dsdRef);

        if (kfSuperBean == null) {
            LOG.debug("No existing dsd super bean found, build new");
            DataStructureBean keyFamily = retrievalManager.getIdentifiableBean(dsdRef, DataStructureBean.class);
            if (keyFamily != null) {
                kfSuperBean = dataStructureSuperBeanBuilder.build(keyFamily, retrievalManager, existingBeans);
                existingBeans.addDataStructure(kfSuperBean);
            } else {
                throw new CrossReferenceException(buildFrom.getDataStructureRef());
            }
        }
        return new DataflowSuperBeanImpl(buildFrom, kfSuperBean);
    }
}
