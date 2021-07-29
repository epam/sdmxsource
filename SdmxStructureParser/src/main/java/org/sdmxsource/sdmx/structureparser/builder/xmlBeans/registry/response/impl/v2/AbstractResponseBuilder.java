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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans.registry.response.impl.v2;

import org.sdmx.resources.sdmxml.schemas.v20.common.TextType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.DatasourceType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.QueryableDatasourceType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.StatusMessageType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.StatusType;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.base.AnnotableBean;
import org.sdmxsource.sdmx.api.model.beans.base.DataSourceBean;
import org.sdmxsource.util.ObjectUtil;


/**
 * The type Abstract response builder.
 */
//JAVADOC missing
public abstract class AbstractResponseBuilder {

    /**
     * Instantiates a new Abstract response builder.
     */
//DEFAULT CONSTRUCTOR
    AbstractResponseBuilder() {
    }

    /**
     * Add status.
     *
     * @param statusMessage the status message
     * @param th            the th
     */
    public void addStatus(StatusMessageType statusMessage, Throwable th) {
        if (th == null) {
            statusMessage.setStatus(StatusType.SUCCESS);
        } else {
            statusMessage.setStatus(StatusType.FAILURE);
            TextType tt = statusMessage.addNewMessageText();
            if (th instanceof SdmxException) {
                tt.setStringValue(((SdmxException) th).getFullMessage());
            } else {
                tt.setStringValue(th.getMessage());
            }
        }
    }

    /**
     * Add datasource.
     *
     * @param datasourceBean the datasource bean
     * @param datasourceType the datasource type
     */
    public void addDatasource(DataSourceBean datasourceBean, DatasourceType datasourceType) {
        if (datasourceBean.isSimpleDatasource()) {
            if (datasourceBean.getDataUrl() != null) {
                datasourceType.setSimpleDatasource(datasourceBean.getDataUrl().toString());
            }
        } else {
            QueryableDatasourceType queryableDatasourceType = datasourceType.addNewQueryableDatasource();
            if (datasourceBean.getDataUrl() != null) {
                queryableDatasourceType.setDataUrl(datasourceBean.getDataUrl().toString());
            }
            queryableDatasourceType.setIsRESTDatasource(datasourceBean.isRESTDatasource());
            queryableDatasourceType.setIsWebServiceDatasource(datasourceBean.isWebServiceDatasource());
            if (datasourceBean.getWSDLUrl() != null) {
                queryableDatasourceType.setWSDLUrl(datasourceBean.getWSDLUrl().toString());
            }
        }
    }

    /**
     * Has annotations boolean.
     *
     * @param annotable the annotable
     * @return the boolean
     */
    boolean hasAnnotations(AnnotableBean annotable) {
        if (ObjectUtil.validCollection(annotable.getAnnotations())) {
            return true;
        }
        return false;
    }
}
