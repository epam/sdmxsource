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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans.registry.response.impl.v21;

import org.apache.xmlbeans.XmlAnyURI;
import org.sdmx.resources.sdmxml.schemas.v21.common.QueryableDataSourceType;
import org.sdmx.resources.sdmxml.schemas.v21.common.TextType;
import org.sdmx.resources.sdmxml.schemas.v21.registry.DataSourceType;
import org.sdmx.resources.sdmxml.schemas.v21.registry.StatusMessageType;
import org.sdmx.resources.sdmxml.schemas.v21.registry.StatusType;
import org.sdmxsource.sdmx.api.model.beans.base.AnnotableBean;
import org.sdmxsource.sdmx.api.model.beans.base.DataSourceBean;
import org.sdmxsource.util.ObjectUtil;
import org.sdmxsource.util.model.impl.ErrorReport;


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
        //FUNC 2.1 Where is the status attribute?
        if (th == null) {
            statusMessage.setStatus(StatusType.SUCCESS);
        } else {
            statusMessage.setStatus(StatusType.FAILURE);
            ErrorReport errorReport = ErrorReport.build(th);
            if (ObjectUtil.validCollection(errorReport.getErrorMessage())) {
                org.sdmx.resources.sdmxml.schemas.v21.common.StatusMessageType tt = statusMessage.addNewMessageText();
                for (String errors : errorReport.getErrorMessage()) {
                    TextType text = tt.addNewText();
                    text.setStringValue(errors);
                }
            }
        }
    }

    /**
     * Add datasource.
     *
     * @param datasourceBean the datasource bean
     * @param datasourceType the datasource type
     */
    public void addDatasource(DataSourceBean datasourceBean, DataSourceType datasourceType) {
        if (datasourceBean.isSimpleDatasource()) {
            XmlAnyURI simpleDatasource = datasourceType.addNewSimpleDataSource();
            simpleDatasource.setStringValue(datasourceBean.getDataUrl().toString());
        } else {
            QueryableDataSourceType queryableDatasourceType = datasourceType.addNewQueryableDataSource();
            queryableDatasourceType.setDataURL(datasourceBean.getDataUrl().toString());
            queryableDatasourceType.setIsRESTDatasource(datasourceBean.isRESTDatasource());
            queryableDatasourceType.setIsWebServiceDatasource(datasourceBean.isWebServiceDatasource());
            if (datasourceBean.getWSDLUrl() != null) {
                queryableDatasourceType.setWSDLURL(datasourceBean.getWSDLUrl().toString());
            }
            if (datasourceBean.getWadlUrl() != null) {
                queryableDatasourceType.setWADLURL(datasourceBean.getWSDLUrl().toString());
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
