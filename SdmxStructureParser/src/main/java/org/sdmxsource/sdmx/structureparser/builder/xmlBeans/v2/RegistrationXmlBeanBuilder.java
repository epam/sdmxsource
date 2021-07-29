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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v2;

import org.sdmx.resources.sdmxml.schemas.v20.registry.DatasourceType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.ProvisionAgreementRefType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.QueryableDatasourceType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.RegistrationType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.base.DataSourceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.RegistrationBean;


/**
 * The type Registration xml bean builder.
 */
public class RegistrationXmlBeanBuilder extends AbstractBuilder {
    /**
     * The constant INSTANCE.
     */
    public static RegistrationXmlBeanBuilder INSTANCE = new RegistrationXmlBeanBuilder();

    //PRIVATE CONSTRUCTOR
    private RegistrationXmlBeanBuilder() {
    }


    /**
     * Build registration type.
     *
     * @param buildFrom the build from
     * @return the registration type
     * @throws SdmxException the sdmx exception
     */
    public RegistrationType build(RegistrationBean buildFrom) throws SdmxException {
        RegistrationType builtObj = RegistrationType.Factory.newInstance();
        if (buildFrom.getLastUpdated() != null) {
            builtObj.setLastUpdated(buildFrom.getLastUpdated().getDateAsCalendar());
        }
        if (buildFrom.getValidFrom() != null) {
            builtObj.setValidFrom(buildFrom.getValidFrom().getDateAsCalendar());
        }
        if (buildFrom.getValidTo() != null) {
            builtObj.setValidTo(buildFrom.getValidTo().getDateAsCalendar());
        }
        if (buildFrom.getProvisionAgreementRef() != null) {
            CrossReferenceBean provRefBean = buildFrom.getProvisionAgreementRef();
            ProvisionAgreementRefType provRefType = builtObj.addNewProvisionAgreementRef();
            if (provRefBean.getTargetReference() == SDMX_STRUCTURE_TYPE.PROVISION_AGREEMENT) {
                if (validString(provRefBean.getTargetUrn())) {
                    provRefType.setURN(provRefBean.getTargetUrn());
                }
            }
        }
        if (buildFrom.getDataSource() != null) {
            DataSourceBean datasourceBean = buildFrom.getDataSource();
            DatasourceType datasourceType = builtObj.addNewDatasource();
            if (datasourceBean.isSimpleDatasource()) {
                datasourceType.setSimpleDatasource(datasourceBean.getDataUrl().toString());
            } else {
                QueryableDatasourceType qdst = datasourceType.addNewQueryableDatasource();
                qdst.setIsRESTDatasource(datasourceBean.isRESTDatasource());
                qdst.setIsWebServiceDatasource(datasourceBean.isWebServiceDatasource());
                qdst.setDataUrl(datasourceBean.getDataUrl().toString());
            }
        }
        return builtObj;
    }
}
