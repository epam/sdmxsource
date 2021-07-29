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
import org.sdmx.resources.sdmxml.schemas.v20.message.RegistryInterfaceDocument;
import org.sdmx.resources.sdmxml.schemas.v20.message.RegistryInterfaceType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.*;
import org.sdmxsource.sdmx.api.model.beans.base.DataSourceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.RegistrationBean;
import org.sdmxsource.util.ObjectUtil;

import java.util.Collection;


//JAVADOC missing

/**
 * The type Query registration response builder v 2.
 */
public class QueryRegistrationResponseBuilderV2 extends AbstractResponseBuilder {
    /**
     * The constant INSTANCE.
     */
    public static QueryRegistrationResponseBuilderV2 INSTANCE = new QueryRegistrationResponseBuilderV2();

    //PRIVATE CONSTRUCTOR
    private QueryRegistrationResponseBuilderV2() {
    }

    /**
     * Build error response registry interface document.
     *
     * @param th the th
     * @return the registry interface document
     */
    public RegistryInterfaceDocument buildErrorResponse(Throwable th) {
        RegistryInterfaceDocument responseType = RegistryInterfaceDocument.Factory.newInstance();
        RegistryInterfaceType regInterface = responseType.addNewRegistryInterface();
        QueryRegistrationResponseType returnType = regInterface.addNewQueryRegistrationResponse();
        V2Helper.setHeader(regInterface);

        QueryResultType queryResult = returnType.addNewQueryResult();
        queryResult.setTimeSeriesMatch(false);
        StatusMessageType statusMessage = queryResult.addNewStatusMessage();
        addStatus(statusMessage, th);

        return responseType;
    }

    /**
     * Build success response registry interface document.
     *
     * @param registrations the registrations
     * @return the registry interface document
     */
    public RegistryInterfaceDocument buildSuccessResponse(Collection<RegistrationBean> registrations) {
        RegistryInterfaceDocument responseType = RegistryInterfaceDocument.Factory.newInstance();
        RegistryInterfaceType regInterface = responseType.addNewRegistryInterface();
        QueryRegistrationResponseType returnType = regInterface.addNewQueryRegistrationResponse();
        V2Helper.setHeader(regInterface);

        if (!ObjectUtil.validCollection(registrations)) {
            QueryResultType queryResult = returnType.addNewQueryResult();
            queryResult.setTimeSeriesMatch(false);
            StatusMessageType statusMessage = queryResult.addNewStatusMessage();
            statusMessage.setStatus(StatusType.WARNING);
            TextType tt = statusMessage.addNewMessageText();
            tt.setStringValue("No Registrations Match The Query Parameters");
        } else {
            for (RegistrationBean currentRegistration : registrations) {
                QueryResultType queryResult = returnType.addNewQueryResult();
                StatusMessageType statusMessage = queryResult.addNewStatusMessage();
                addStatus(statusMessage, null);

                queryResult.setTimeSeriesMatch(false);  //FUNC 1 - when is this true?  Also We need MetadataResult

                ResultType resultType = queryResult.addNewDataResult();

                if (currentRegistration.getDataSource() != null) {
                    DataSourceBean datasourceBean = currentRegistration.getDataSource();
                    DatasourceType datasourceType = resultType.addNewDatasource();
                    if (datasourceBean.isSimpleDatasource()) {
                        datasourceType.setSimpleDatasource(datasourceBean.getDataUrl().toString());
                    } else {
                        QueryableDatasourceType queryableDatasource = datasourceType.addNewQueryableDatasource();
                        queryableDatasource.setIsRESTDatasource(datasourceBean.isRESTDatasource());
                        queryableDatasource.setIsWebServiceDatasource(datasourceBean.isWebServiceDatasource());
                        queryableDatasource.setDataUrl(datasourceBean.getDataUrl().toString());
                        if (datasourceBean.getWSDLUrl() != null) {
                            queryableDatasource.setWSDLUrl(datasourceBean.getWSDLUrl().toString());
                        }
                    }
                }
                if (currentRegistration.getProvisionAgreementRef() != null) {
                    writeProvisionAgreementRef(currentRegistration.getProvisionAgreementRef(), resultType);
                }
            }
        }
        return responseType;
    }

    private void writeProvisionAgreementRef(CrossReferenceBean provRefBean, ResultType resultType) {
        ProvisionAgreementRefType provRef = resultType.addNewProvisionAgreementRef();

        if (provRefBean.getTargetUrn() != null) {
            provRef.setURN(provRefBean.getTargetUrn());
        }
    }

}
