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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21;

import org.apache.xmlbeans.XmlAnyURI;
import org.sdmx.resources.sdmxml.schemas.v21.registry.DataSourceType;
import org.sdmx.resources.sdmxml.schemas.v21.registry.QueryableDataSourceType;
import org.sdmx.resources.sdmxml.schemas.v21.registry.RegistrationType;
import org.sdmxsource.sdmx.api.builder.Builder;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.base.DataSourceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.RegistrationBean;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers.AbstractBeanAssembler;


/**
 * The type Registration xml bean builder.
 */
public class RegistrationXmlBeanBuilder extends AbstractBeanAssembler implements Builder<RegistrationType, RegistrationBean> {

    @Override
    public RegistrationType build(RegistrationBean buildFrom) throws SdmxException {
        RegistrationType returnType = RegistrationType.Factory.newInstance();
        returnType.setId(buildFrom.getId());
        if (buildFrom.getIndexAttributes().isSet()) {
            returnType.setIndexAttributes(buildFrom.getIndexAttributes().isTrue());
        }
        if (buildFrom.getIndexDataset().isSet()) {
            returnType.setIndexDataSet(buildFrom.getIndexDataset().isTrue());
        }
        if (buildFrom.getIndexReportingPeriod().isSet()) {
            returnType.setIndexReportingPeriod(buildFrom.getIndexReportingPeriod().isTrue());
        }
        if (buildFrom.getIndexTimeSeries().isSet()) {
            returnType.setIndexTimeSeries(buildFrom.getIndexTimeSeries().isTrue());
        }
        super.setReference(returnType.addNewProvisionAgreement().addNewRef(), buildFrom.getProvisionAgreementRef());
        if (buildFrom.getDataSource() != null) {
            DataSourceType dataSourceType = returnType.addNewDatasource();
            DataSourceBean dataSource = buildFrom.getDataSource();
            String dataUrl = dataSource.getDataUrl().toString();
            if (dataSource.isSimpleDatasource()) {
                XmlAnyURI simpleDataSource = dataSourceType.addNewSimpleDataSource();
                simpleDataSource.setStringValue(dataUrl);
            } else {
                QueryableDataSourceType queryableDataSource = dataSourceType.addNewQueryableDataSource();
                queryableDataSource.setDataURL(dataUrl);
                if (dataSource.getWSDLUrl() != null) {
                    queryableDataSource.setWADLURL(dataSource.getWSDLUrl().toString());
                }
                if (dataSource.getWadlUrl() != null) {
                    queryableDataSource.setWSDLURL(dataSource.getWadlUrl().toString());
                }
                if (dataSource.isRESTDatasource()) {
                    queryableDataSource.setIsRESTDatasource(true);
                    queryableDataSource.setIsWebServiceDatasource(false);
                } else if (dataSource.isWebServiceDatasource()) {
                    queryableDataSource.setIsWebServiceDatasource(true);
                    queryableDataSource.setIsRESTDatasource(false);
                }
            }
        }
        if (buildFrom.getLastUpdated() != null) {
            returnType.setLastUpdated(buildFrom.getLastUpdated().getDateAsCalendar());
        }
        if (buildFrom.getValidFrom() != null) {
            returnType.setValidFrom(buildFrom.getValidFrom().getDateAsCalendar());
        }
        if (buildFrom.getValidTo() != null) {
            returnType.setValidTo(buildFrom.getValidTo().getDateAsCalendar());
        }
        return returnType;
    }
}
