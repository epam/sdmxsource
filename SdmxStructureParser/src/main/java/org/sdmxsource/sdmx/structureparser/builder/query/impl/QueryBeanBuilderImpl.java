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
package org.sdmxsource.sdmx.structureparser.builder.query.impl;

import org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.message.QueryMessageType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.QueryProvisioningRequestType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.QueryRegistrationRequestType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.QueryStructureRequestType;
import org.sdmx.resources.sdmxml.schemas.v21.message.*;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.complex.ComplexStructureQuery;
import org.sdmxsource.sdmx.structureparser.builder.query.QueryBeanBuilder;

import java.util.List;


/**
 * Builds query structure objects from SDMX query messages, this includes structure queries, registration queries and provision queries
 */
public class QueryBeanBuilderImpl implements QueryBeanBuilder {

    private final QueryBeanBuilderV1 queryBeanBuilderV1;

    private final QueryBeanBuilderV2 queryBeanBuilderV2;

    private final QueryBeanBuilderV2_1 queryBeanBuilderV2_1;

    /**
     * Instantiates a new Query bean builder.
     */
    public QueryBeanBuilderImpl() {
        this(null, null, null);
    }

    /**
     * Instantiates a new Query bean builder.
     *
     * @param queryBeanBuilderV1   the query bean builder v 1
     * @param queryBeanBuilderV2   the query bean builder v 2
     * @param queryBeanBuilderV2_1 the query bean builder v 2 1
     */
    public QueryBeanBuilderImpl(
            final QueryBeanBuilderV1 queryBeanBuilderV1,
            final QueryBeanBuilderV2 queryBeanBuilderV2,
            final QueryBeanBuilderV2_1 queryBeanBuilderV2_1) {
        this.queryBeanBuilderV1 = queryBeanBuilderV1 != null ? queryBeanBuilderV1 : new QueryBeanBuilderV1();
        this.queryBeanBuilderV2 = queryBeanBuilderV2 != null ? queryBeanBuilderV2 : new QueryBeanBuilderV2();
        this.queryBeanBuilderV2_1 = queryBeanBuilderV2_1 != null ? queryBeanBuilderV2_1 : new QueryBeanBuilderV2_1();
    }

    @Override
    public List<StructureReferenceBean> build(QueryStructureRequestType queryStructureRequests) {
        return queryBeanBuilderV2.build(queryStructureRequests);
    }

    @Override
    public StructureReferenceBean build(QueryRegistrationRequestType queryRegistrationRequestType) {
        return queryBeanBuilderV2.build(queryRegistrationRequestType);
    }


    @Override
    public StructureReferenceBean build(
            org.sdmx.resources.sdmxml.schemas.v21.registry.QueryRegistrationRequestType queryRegistrationRequestType) {
        return queryBeanBuilderV2_1.build(queryRegistrationRequestType);
    }

    @Override
    public StructureReferenceBean build(QueryProvisioningRequestType queryProvisionRequestType) {
        return queryBeanBuilderV2.build(queryProvisionRequestType);
    }

    @Override
    public List<StructureReferenceBean> build(QueryMessageType queryMessage) {
        return queryBeanBuilderV1.build(queryMessage);
    }

    @Override
    public List<StructureReferenceBean> build(org.sdmx.resources.sdmxml.schemas.v20.message.QueryMessageType queryMessage) {
        return queryBeanBuilderV2.build(queryMessage);
    }

    @Override
    public ComplexStructureQuery build(CodelistQueryType codelistQueryMsg) {
        return queryBeanBuilderV2_1.build(codelistQueryMsg);
    }

    @Override
    public ComplexStructureQuery build(DataflowQueryType dataflowQueryMsg) {
        return queryBeanBuilderV2_1.build(dataflowQueryMsg);
    }

    @Override
    public ComplexStructureQuery build(MetadataflowQueryType metadataflowQueryMsg) {
        return queryBeanBuilderV2_1.build(metadataflowQueryMsg);
    }

    @Override
    public ComplexStructureQuery build(DataStructureQueryType dataStructureQueryMsg) {
        return queryBeanBuilderV2_1.build(dataStructureQueryMsg);
    }

    @Override
    public ComplexStructureQuery build(MetadataStructureQueryType metadataStructureQueryMsg) {
        return queryBeanBuilderV2_1.build(metadataStructureQueryMsg);
    }

    @Override
    public ComplexStructureQuery build(CategorySchemeQueryType categorySchemeQueryMsg) {
        return queryBeanBuilderV2_1.build(categorySchemeQueryMsg);

    }

    @Override
    public ComplexStructureQuery build(ConceptSchemeQueryType conceptSchemeQueryMsg) {
        return queryBeanBuilderV2_1.build(conceptSchemeQueryMsg);
    }

    @Override
    public ComplexStructureQuery build(HierarchicalCodelistQueryType hierarchicalCodelistQueryMsg) {
        return queryBeanBuilderV2_1.build(hierarchicalCodelistQueryMsg);
    }

    @Override
    public ComplexStructureQuery build(OrganisationSchemeQueryType organisationSchemeQueryMsg) {
        return queryBeanBuilderV2_1.build(organisationSchemeQueryMsg);
    }

    @Override
    public ComplexStructureQuery build(ReportingTaxonomyQueryType reportingTaxonomyQueryMsg) {
        return queryBeanBuilderV2_1.build(reportingTaxonomyQueryMsg);
    }

    @Override
    public ComplexStructureQuery build(StructureSetQueryType structureSetQueryMsg) {
        return queryBeanBuilderV2_1.build(structureSetQueryMsg);
    }

    @Override
    public ComplexStructureQuery build(ProcessQueryType processQueryMsg) {
        return queryBeanBuilderV2_1.build(processQueryMsg);
    }

    @Override
    public ComplexStructureQuery build(CategorisationQueryType categorisationQueryMsg) {
        return queryBeanBuilderV2_1.build(categorisationQueryMsg);
    }

    @Override
    public ComplexStructureQuery build(ProvisionAgreementQueryType provisionAgreementQueryMsg) {
        return queryBeanBuilderV2_1.build(provisionAgreementQueryMsg);
    }

    @Override
    public ComplexStructureQuery build(ConstraintQueryType constraintQueryMsg) {
        return queryBeanBuilderV2_1.build(constraintQueryMsg);
    }

    @Override
    public ComplexStructureQuery build(StructuresQueryType structuresQueryMsg) {
        return queryBeanBuilderV2_1.build(structuresQueryMsg);
    }

}
