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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.base;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.DataSourceBean;
import org.sdmxsource.sdmx.api.model.mutable.base.DataSourceMutableBean;

/**
 * The type Data source mutable bean.
 */
public class DataSourceMutableBeanImpl extends MutableBeanImpl implements DataSourceMutableBean {
    private static final long serialVersionUID = 1L;
    private String dataUrl;
    private String wsdlUrl;
    private String wadlUrl;
    private boolean isRestDatasource;
    private boolean isSimpleDatasource;
    private boolean isWebServiceDatasource;

    /**
     * Instantiates a new Data source mutable bean.
     */
    public DataSourceMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.DATASOURCE);
    }

    /**
     * Instantiates a new Data source mutable bean.
     *
     * @param datasourceBean the datasource bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM IMMUTABLE BEAN				 //////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public DataSourceMutableBeanImpl(DataSourceBean datasourceBean) {
        super(datasourceBean);
        if (datasourceBean.getDataUrl() != null) {
            this.dataUrl = datasourceBean.getDataUrl().toString();
        }
        if (datasourceBean.getWSDLUrl() != null) {
            this.wsdlUrl = datasourceBean.getWSDLUrl().toString();
        }
        if (datasourceBean.getWadlUrl() != null) {
            this.wadlUrl = datasourceBean.getWadlUrl().toString();
        }
        this.isRestDatasource = datasourceBean.isRESTDatasource();
        this.isSimpleDatasource = datasourceBean.isSimpleDatasource();
        this.isWebServiceDatasource = datasourceBean.isWebServiceDatasource();
    }

    @Override
    public String getDataUrl() {
        return dataUrl;
    }

    @Override
    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    @Override
    public String getWSDLUrl() {
        return wsdlUrl;
    }

    @Override
    public void setWSDLUrl(String wsdlUrl) {
        this.wsdlUrl = wsdlUrl;
    }

    @Override
    public String getWADLUrl() {
        return wadlUrl;
    }

    @Override
    public void setWADLUrl(String wadlUrl) {
        this.wadlUrl = wadlUrl;
    }

    @Override
    public boolean isRESTDatasource() {
        return isRestDatasource;
    }

    @Override
    public void setRESTDatasource(boolean isRestDatasource) {
        this.isRestDatasource = isRestDatasource;
    }

    @Override
    public boolean isSimpleDatasource() {
        return isSimpleDatasource;
    }

    @Override
    public void setSimpleDatasource(boolean isSimpleDatasource) {
        this.isSimpleDatasource = isSimpleDatasource;
    }

    @Override
    public boolean isWebServiceDatasource() {
        return isWebServiceDatasource;
    }

    @Override
    public void setWebServiceDatasource(boolean isWebServiceDatasource) {
        this.isWebServiceDatasource = isWebServiceDatasource;
    }
}
