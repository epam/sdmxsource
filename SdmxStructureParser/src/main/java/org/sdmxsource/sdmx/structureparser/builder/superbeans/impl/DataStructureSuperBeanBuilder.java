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
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.datastructure.AttributeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.superbeans.SuperBeans;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.AttributeSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DataStructureSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DimensionSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.PrimaryMeasureSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.datastructure.DataStructureSuperBeanImpl;
import org.sdmxsource.sdmx.util.beans.container.SuperBeansImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * Build a project specific DataStructure model object from a KeyFamilyType XMLBean
 */
public class DataStructureSuperBeanBuilder extends StructureBuilderImpl<DataStructureSuperBean, DataStructureBean> {
    private static final Logger LOG = LoggerFactory.getLogger(SuperBeansBuilderImpl.class);

    private final AttributeSuperBeanBuilder attributeSuperBeanBuilder;

    private final DimensionSuperBeanBuilder dimensionSuperBeanBuilder;

    private final PrimaryMeasureSuperBeanBuilder primaryMeasureSuperBeanBuilder;

    /**
     * Instantiates a new Data structure super bean builder.
     */
    public DataStructureSuperBeanBuilder() {
        this(new AttributeSuperBeanBuilder(), new DimensionSuperBeanBuilder(), new PrimaryMeasureSuperBeanBuilder());
    }

    /**
     * Instantiates a new Data structure super bean builder.
     *
     * @param attributeSuperBeanBuilder      the attribute super bean builder
     * @param dimensionSuperBeanBuilder      the dimension super bean builder
     * @param primaryMeasureSuperBeanBuilder the primary measure super bean builder
     */
    public DataStructureSuperBeanBuilder(
            final AttributeSuperBeanBuilder attributeSuperBeanBuilder,
            final DimensionSuperBeanBuilder dimensionSuperBeanBuilder,
            final PrimaryMeasureSuperBeanBuilder primaryMeasureSuperBeanBuilder) {
        this.attributeSuperBeanBuilder = attributeSuperBeanBuilder;
        this.dimensionSuperBeanBuilder = dimensionSuperBeanBuilder;
        this.primaryMeasureSuperBeanBuilder = primaryMeasureSuperBeanBuilder;
    }

    @Override
    public DataStructureSuperBean build(DataStructureBean buildFrom,
                                        SdmxBeanRetrievalManager retrievalManager,
                                        SuperBeans existingBeans) {
        if (existingBeans == null) {
            existingBeans = new SuperBeansImpl();
        }
        LOG.debug("Build KeyFamilySuperBean SuperBean");
        List<AttributeSuperBean> attributes = new ArrayList<AttributeSuperBean>();
        List<DimensionSuperBean> dimensions = new ArrayList<DimensionSuperBean>();

        for (DimensionBean currentDimension : buildFrom.getDimensions()) {
            LOG.debug("Build Dimension: " + currentDimension.getUrn());
            dimensions.add(dimensionSuperBeanBuilder.build(currentDimension, retrievalManager, existingBeans));
        }
        for (AttributeBean currentAttribute : buildFrom.getAttributes()) {
            LOG.debug("Build Attribute: " + currentAttribute.getUrn());
            attributes.add(attributeSuperBeanBuilder.build(currentAttribute, retrievalManager, existingBeans));
        }
        PrimaryMeasureSuperBean primaryMeasure = null;
        if (buildFrom.getPrimaryMeasure() != null) {
            LOG.debug("Build Measure: " + buildFrom.getPrimaryMeasure().getUrn());
            primaryMeasure = primaryMeasureSuperBeanBuilder.build(buildFrom.getPrimaryMeasure(), retrievalManager, existingBeans);
        }
        return new DataStructureSuperBeanImpl(buildFrom, dimensions, attributes, primaryMeasure);
    }
}
