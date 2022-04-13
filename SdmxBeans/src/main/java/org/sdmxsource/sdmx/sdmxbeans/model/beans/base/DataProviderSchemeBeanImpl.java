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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sdmx.resources.sdmxml.schemas.v20.structure.DataProvidersType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.OrganisationSchemeType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.OrganisationType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.DataProviderSchemeType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.DataProviderType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TERTIARY_BOOL;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.DataProviderBean;
import org.sdmxsource.sdmx.api.model.beans.base.DataProviderSchemeBean;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.mutable.base.DataProviderMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.DataProviderSchemeMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.DataProviderSchemeMutableBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.net.URL;


/**
 * The type Data provider scheme bean.
 */
public class DataProviderSchemeBeanImpl extends OrganisationSchemeBeanImpl<DataProviderBean> implements DataProviderSchemeBean {
    private static final long serialVersionUID = 3160896936707022679L;
    private static Logger LOG = LogManager.getLogger(DataProviderSchemeBeanImpl.class);

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM ITSELF, CREATES STUB BEAN //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private DataProviderSchemeBeanImpl(DataProviderSchemeBean bean, URL actualLocation, boolean isServiceUrl) {
        super(bean, actualLocation, isServiceUrl);
        LOG.debug("Stub DataProviderSchemeBean Built");
    }

    /**
     * Instantiates a new Data provider scheme bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public DataProviderSchemeBeanImpl(DataProviderSchemeMutableBean bean) {
        super(bean);
        LOG.debug("Building DataProviderSchemeBean from Mutable Bean");
        if (bean.getItems() != null) {
            for (DataProviderMutableBean currrentBean : bean.getItems()) {
                this.items.add(new DataProviderBeanImpl(currrentBean, this));
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("DataProviderSchemeBean Built " + this.getUrn());
        }
    }


    /**
     * Instantiates a new Data provider scheme bean.
     *
     * @param type the type
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public DataProviderSchemeBeanImpl(DataProviderSchemeType type) {
        super(type, SDMX_STRUCTURE_TYPE.DATA_PROVIDER_SCHEME);
        LOG.debug("Building DataProviderSchemeBean from 2.1 SDMX");
        if (ObjectUtil.validCollection(type.getDataProviderList())) {
            for (DataProviderType currentDataProvider : type.getDataProviderList()) {
                this.items.add(new DataProviderBeanImpl(currentDataProvider, this));
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("DataProviderSchemeBean Built " + this.getUrn());
        }
    }


    /**
     * Instantiates a new Data provider scheme bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public DataProviderSchemeBeanImpl(OrganisationSchemeType bean) {
        super(bean, SDMX_STRUCTURE_TYPE.DATA_PROVIDER_SCHEME,
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

        LOG.debug("Building DataProviderSchemeBean from 2.0 SDMX");
        try {
            if (bean.getDataProvidersList() != null) {
                for (DataProvidersType dpList : bean.getDataProvidersList()) {
                    for (OrganisationType dataProvider : dpList.getDataProviderList()) {
                        items.add(new DataProviderBeanImpl(dataProvider, this));
                    }
                }
            }
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("DataProviderSchemeBean Built " + this.getUrn());
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
            return super.deepEqualsInternal((DataProviderSchemeBean) bean, includeFinalProperties);
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
        return new DataProviderSchemeBeanImpl(this, actualLocation, isServiceUrl);
    }

    @Override
    public DataProviderSchemeMutableBean getMutableInstance() {
        return new DataProviderSchemeMutableBeanImpl(this);
    }
}
