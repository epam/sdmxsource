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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.base;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.sdmx.resources.sdmxml.schemas.v20.structure.DataConsumersType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.OrganisationSchemeType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.OrganisationType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.DataConsumerSchemeType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.DataConsumerType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TERTIARY_BOOL;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.base.DataConsumerBean;
import org.sdmxsource.sdmx.api.model.beans.base.DataConsumerSchemeBean;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.mutable.base.DataConsumerMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.DataConsumerSchemeMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.DataConsumerSchemeMutableBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.net.URL;


/**
 * The type Data consumer scheme bean.
 */
public class DataConsumerSchemeBeanImpl extends OrganisationSchemeBeanImpl<DataConsumerBean> implements DataConsumerSchemeBean {
    private static final long serialVersionUID = 2335090671092865670L;
    private static Logger LOG = LoggerFactory.getLogger(DataConsumerSchemeBeanImpl.class);


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM ITSELF, CREATES STUB BEAN //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private DataConsumerSchemeBeanImpl(DataConsumerSchemeBean bean, URL actualLocation, boolean isServiceUrl) {
        super(bean, actualLocation, isServiceUrl);
        LOG.debug("Stub DataConsumerSchemeBean Built");
    }

    /**
     * Instantiates a new Data consumer scheme bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public DataConsumerSchemeBeanImpl(DataConsumerSchemeMutableBean bean) {
        super(bean);
        LOG.debug("Building DataConsumerSchemeBean from Mutable Bean");
        if (bean.getItems() != null) {
            for (DataConsumerMutableBean item : bean.getItems()) {
                this.items.add(new DataConsumerBeanImpl(item, this));
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("DataConsumerSchemeBean Built " + this.getUrn());
        }
    }


    /**
     * Instantiates a new Data consumer scheme bean.
     *
     * @param type the type
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public DataConsumerSchemeBeanImpl(DataConsumerSchemeType type) {
        super(type, SDMX_STRUCTURE_TYPE.DATA_CONSUMER_SCHEME);
        LOG.debug("Building DataConsumerSchemeBean from 2.1");
        if (ObjectUtil.validCollection(type.getDataConsumerList())) {
            for (DataConsumerType currentDataConsumer : type.getDataConsumerList()) {
                this.items.add(new DataConsumerBeanImpl(currentDataConsumer, this));
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("DataConsumerSchemeBean Built " + this.getUrn());
        }
    }


    /**
     * Instantiates a new Data consumer scheme bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public DataConsumerSchemeBeanImpl(OrganisationSchemeType bean) {
        super(bean, SDMX_STRUCTURE_TYPE.DATA_CONSUMER_SCHEME,
                bean.getValidTo(),
                bean.getValidFrom(),
                FIXED_VERSION,
                TERTIARY_BOOL.FALSE,
                bean.getAgencyID(),
                FIXED_ID,
                bean.getUri(),
                bean.getNameList(),
                bean.getDescriptionList(),
                createTertiary(bean.isSetIsExternalReference(), bean.getIsExternalReference()),
                bean.getAnnotations());
        LOG.debug("Building DataConsumerSchemeBean from 2.0");
        try {
            if (bean.getDataProvidersList() != null) {
                for (DataConsumersType dcList : bean.getDataConsumersList()) {
                    for (OrganisationType dataConsumer : dcList.getDataConsumerList()) {
                        items.add(new DataConsumerBeanImpl(dataConsumer, this));
                    }
                }
            }
        } catch (SdmxException ex) {
            throw new SdmxException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("DataConsumerSchemeBean Built " + this.getUrn());
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////DEEP VALIDATION						 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean deepEquals(SDMXBean bean, boolean includeFinalProperties) {
        if (bean == null) {
            return false;
        }
        if (bean.getStructureType() == this.getStructureType()) {
            return super.deepEqualsInternal((DataConsumerSchemeBean) bean, includeFinalProperties);
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public String getVersion() {
        return FIXED_VERSION;
    }

    @Override
    public String getId() {
        return FIXED_ID;
    }

    @Override
    public MaintainableBean getStub(URL actualLocation, boolean isServiceUrl) {
        return new DataConsumerSchemeBeanImpl(this, actualLocation, isServiceUrl);
    }

    @Override
    public DataConsumerSchemeMutableBean getMutableInstance() {
        return new DataConsumerSchemeMutableBeanImpl(this);
    }
}
