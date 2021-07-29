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

import org.sdmx.resources.sdmxml.schemas.v20.structure.AgenciesType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.DataConsumersType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.DataProvidersType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.OrganisationSchemeType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.model.beans.base.*;


/**
 * The type Organisation scheme xml bean builder.
 */
public class OrganisationSchemeXmlBeanBuilder extends AbstractBuilder {

    private final OrganisationRoleXmlBeanBuilder organisationRoleXmlBeanBuilder = new OrganisationRoleXmlBeanBuilder();

    /**
     * Build organisation scheme type.
     *
     * @param dataProviderSchemeBean the data provider scheme bean
     * @return the organisation scheme type
     * @throws SdmxException the sdmx exception
     */
    public OrganisationSchemeType build(DataProviderSchemeBean dataProviderSchemeBean) throws SdmxException {
        OrganisationSchemeType builtObj = getOrganisationSchemeType(dataProviderSchemeBean);
        if (dataProviderSchemeBean.getItems().size() > 0) {
            DataProvidersType type = builtObj.addNewDataProviders();
            for (DataProviderBean currentDc : dataProviderSchemeBean.getItems()) {
                type.getDataProviderList().add(organisationRoleXmlBeanBuilder.build(currentDc));
            }
        }
        return builtObj;
    }

    /**
     * Build organisation scheme type.
     *
     * @param dataConsumerScheme the data consumer scheme
     * @return the organisation scheme type
     * @throws SdmxException the sdmx exception
     */
    public OrganisationSchemeType build(DataConsumerSchemeBean dataConsumerScheme) throws SdmxException {
        OrganisationSchemeType builtObj = getOrganisationSchemeType(dataConsumerScheme);
        if (dataConsumerScheme.getItems().size() > 0) {
            DataConsumersType dcType = builtObj.addNewDataConsumers();
            for (DataConsumerBean currentDc : dataConsumerScheme.getItems()) {
                dcType.getDataConsumerList().add(organisationRoleXmlBeanBuilder.build(currentDc));
            }
        }
        return builtObj;
    }

    /**
     * Build organisation scheme type.
     *
     * @param agencySchemeBean the agency scheme bean
     * @return the organisation scheme type
     * @throws SdmxException the sdmx exception
     */
    public OrganisationSchemeType build(AgencySchemeBean agencySchemeBean) throws SdmxException {
        OrganisationSchemeType builtObj = getOrganisationSchemeType(agencySchemeBean);
        if (agencySchemeBean.getItems().size() > 0) {
            AgenciesType type = builtObj.addNewAgencies();
            for (AgencyBean currentBean : agencySchemeBean.getItems()) {
                type.getAgencyList().add(organisationRoleXmlBeanBuilder.build(currentBean));
            }
        }
        return builtObj;
    }

    /**
     * Build organisation scheme type.
     *
     * @param currentBean the current bean
     * @return the organisation scheme type
     */
    public OrganisationSchemeType build(OrganisationUnitSchemeBean currentBean) {
        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, SDMX_STRUCTURE_TYPE.ORGANISATION_UNIT_SCHEME.getType());
    }

    private OrganisationSchemeType getOrganisationSchemeType(MaintainableBean buildFrom) throws SdmxException {
        OrganisationSchemeType builtObj = OrganisationSchemeType.Factory.newInstance();

        if (validString(buildFrom.getAgencyId())) {
            builtObj.setAgencyID(buildFrom.getAgencyId());
        }
        if (validString(buildFrom.getId())) {
            builtObj.setId(buildFrom.getId());
        }
        if (buildFrom.getUri() != null) {
            builtObj.setUri(buildFrom.getUri().toString());
        } else if (buildFrom.getStructureURL() != null) {
            builtObj.setUri(buildFrom.getStructureURL().toString());
        } else if (buildFrom.getServiceURL() != null) {
            builtObj.setUri(buildFrom.getStructureURL().toString());
        }
        if (validString(buildFrom.getUrn())) {
            builtObj.setUrn(buildFrom.getUrn());
        }
        if (validString(buildFrom.getVersion())) {
            builtObj.setVersion(buildFrom.getVersion());
        }
        if (buildFrom.getStartDate() != null) {
            builtObj.setValidFrom(buildFrom.getStartDate().getDate());
        }
        if (buildFrom.getEndDate() != null) {
            builtObj.setValidTo(buildFrom.getEndDate().getDate());
        }
        if (validCollection(buildFrom.getNames())) {
            builtObj.setNameArray(getTextType(buildFrom.getNames()));
        }
        if (validCollection(buildFrom.getDescriptions())) {
            builtObj.setDescriptionArray(getTextType(buildFrom.getDescriptions()));
        }
        if (hasAnnotations(buildFrom)) {
            builtObj.setAnnotations(getAnnotationsType(buildFrom));
        }
        if (buildFrom.isExternalReference().isSet()) {
            builtObj.setIsExternalReference(buildFrom.isExternalReference().isTrue());
        }
        if (buildFrom.isFinal().isSet()) {
            builtObj.setIsFinal(buildFrom.isFinal().isTrue());
        }
        return builtObj;
    }
}
