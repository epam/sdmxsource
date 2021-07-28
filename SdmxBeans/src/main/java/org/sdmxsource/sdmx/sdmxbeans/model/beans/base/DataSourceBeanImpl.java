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

import org.sdmx.resources.sdmxml.schemas.v20.registry.DatasourceType;
import org.sdmx.resources.sdmxml.schemas.v21.registry.QueryableDataSourceType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.DataSourceBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.base.SdmxStructureBean;
import org.sdmxsource.sdmx.api.model.mutable.base.DataSourceMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.DataSourceMutableBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;


/**
 * The type Data source bean.
 */
public class DataSourceBeanImpl extends SdmxStructureBeanImpl implements DataSourceBean {
    private static final long serialVersionUID = 1L;
    private URL dataUrl;
    private URL wsdlUrl;
    private URL wadlUrl;
    private boolean isRestDatasource;
    private boolean isSimpleDatasource;
    private boolean isWebServiceDatasource;

    /**
     * Instantiates a new Data source bean.
     *
     * @param datasource the datasource
     * @param parent     the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS 			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public DataSourceBeanImpl(DataSourceMutableBean datasource, SdmxStructureBean parent) {
        super(datasource, parent);
        this.dataUrl = getUrl(datasource.getDataUrl());
        this.wsdlUrl = getUrl(datasource.getWSDLUrl());
        this.isRestDatasource = datasource.isRESTDatasource();
        this.isSimpleDatasource = datasource.isSimpleDatasource();
        this.isWebServiceDatasource = datasource.isWebServiceDatasource();
        validate();
    }


    /**
     * Instantiates a new Data source bean.
     *
     * @param datasource the datasource
     * @param parent     the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public DataSourceBeanImpl(DatasourceType datasource, SdmxStructureBean parent) {
        super(SDMX_STRUCTURE_TYPE.DATASOURCE, parent);
        if (ObjectUtil.validString(datasource.getSimpleDatasource())) {
            isSimpleDatasource = true;
            dataUrl = getUrl(datasource.getSimpleDatasource());
        } else {
            if (datasource.getQueryableDatasource() != null) {
                isSimpleDatasource = false;
                isRestDatasource = datasource.getQueryableDatasource().getIsRESTDatasource();
                isWebServiceDatasource = datasource.getQueryableDatasource().getIsWebServiceDatasource();
                dataUrl = getUrl(datasource.getQueryableDatasource().getDataUrl());
                wsdlUrl = getUrl(datasource.getQueryableDatasource().getWSDLUrl());
            }
        }
        validate();
    }

    /**
     * Instantiates a new Data source bean.
     *
     * @param datasource the datasource
     * @param parent     the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public DataSourceBeanImpl(QueryableDataSourceType datasource, SdmxStructureBean parent) {
        super(SDMX_STRUCTURE_TYPE.DATASOURCE, parent);
        isSimpleDatasource = false;
        isRestDatasource = datasource.getIsRESTDatasource();
        isWebServiceDatasource = datasource.getIsWebServiceDatasource();
        dataUrl = getUrl(datasource.getDataURL());
        wsdlUrl = getUrl(datasource.getWSDLURL());
        wadlUrl = getUrl(datasource.getWADLURL());
        validate();
    }

    /**
     * Instantiates a new Data source bean.
     *
     * @param datasource the datasource
     * @param parent     the parent
     */
    public DataSourceBeanImpl(org.sdmx.resources.sdmxml.schemas.v21.common.QueryableDataSourceType datasource, SdmxStructureBean parent) {
        super(SDMX_STRUCTURE_TYPE.DATASOURCE, parent);
        isSimpleDatasource = false;
        isRestDatasource = datasource.getIsRESTDatasource();
        isWebServiceDatasource = datasource.getIsWebServiceDatasource();
        dataUrl = getUrl(datasource.getDataURL());
        wsdlUrl = getUrl(datasource.getWSDLURL());
        wadlUrl = getUrl(datasource.getWADLURL());
        validate();
    }

    /**
     * Instantiates a new Data source bean.
     *
     * @param simpleDatasoruce the simple datasoruce
     * @param parent           the parent
     */
    public DataSourceBeanImpl(String simpleDatasoruce, SdmxStructureBean parent) {
        super(SDMX_STRUCTURE_TYPE.DATASOURCE, parent);
        isSimpleDatasource = true;
        dataUrl = getUrl(simpleDatasoruce);
        validate();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////DEEP EQUALS							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean deepEquals(SDMXBean bean, boolean includeFinalProperties) {
        if (bean == null) {
            return false;
        }
        if (bean.getStructureType() == this.getStructureType()) {
            DataSourceBean that = (DataSourceBean) bean;
            if (!ObjectUtil.equivalent(dataUrl, that.getDataUrl())) {
                return false;
            }
            if (!ObjectUtil.equivalent(wsdlUrl, that.getWadlUrl())) {
                return false;
            }
            if (!ObjectUtil.equivalent(wadlUrl, that.getWadlUrl())) {
                return false;
            }
            if (isRestDatasource != that.isRESTDatasource()) {
                return false;
            }
            if (isSimpleDatasource != that.isSimpleDatasource()) {
                return false;
            }
            if (isWebServiceDatasource != that.isWebServiceDatasource()) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Validate.
     *
     * @throws SdmxSemmanticException the sdmx semmantic exception
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected void validate() throws SdmxSemmanticException {
        if (dataUrl == null) {
            throw new SdmxSemmanticException(ExceptionCode.BEAN_MISSING_REQUIRED_ATTRIBUTE, "DataSource", "URL");
        }
        //HACK The schema does not allow a constraint to be attached to both a provision and a simple datasource, so here we are setting simple datasouce to false, so that
        //the constraint will be attached to a provision and a queryable datasource - but the queryable datasource will have the following two attributes set
        //isWebServiceDatasource="false" isRESTDatasource="false"
        //This can be inferred by a system that the datasource is simple
        if (parent.getStructureType() != SDMX_STRUCTURE_TYPE.CONTENT_CONSTRAINT_ATTACHMENT) {
            if (!isRestDatasource && !isWebServiceDatasource && !isSimpleDatasource) {
                throw new SdmxSemmanticException("Registration against a queryable source must either be a REST datasource or a web service datasource");
            }
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private URL getUrl(String urlString) {
        try {
            if (ObjectUtil.validString(urlString)) {
                return new URL(urlString);
            }
        } catch (Throwable th) {
            throw new SdmxSemmanticException("Invalid URL '" + urlString + "'");
        }
        return null;
    }

    @Override
    public URL getDataUrl() {
        return dataUrl;
    }

    @Override
    public URL getWadlUrl() {
        return wadlUrl;
    }

    @Override
    public URL getWSDLUrl() {
        return wsdlUrl;
    }

    @Override
    public boolean isRESTDatasource() {
        return isRestDatasource;
    }

    @Override
    public boolean isSimpleDatasource() {
        return isSimpleDatasource;
    }

    @Override
    public boolean isWebServiceDatasource() {
        return isWebServiceDatasource;
    }

    @Override
    public DataSourceMutableBean createMutableInstance() {
        return new DataSourceMutableBeanImpl(this);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES                           //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////	
    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        return new HashSet<SDMXBean>();
    }
}
