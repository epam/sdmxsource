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
package org.sdmxsource.sdmx.structureparser.builder.query;

import org.sdmx.resources.sdmxml.schemas.v20.message.QueryMessageType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.QueryProvisioningRequestType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.QueryRegistrationRequestType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.QueryStructureRequestType;
import org.sdmx.resources.sdmxml.schemas.v21.message.*;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.complex.ComplexStructureQuery;

import java.util.List;


/**
 * Builds Query reference objects from SDMX query messages.
 * The SDMX queries can be structure queries, registration queries or provision queries
 */
public interface QueryBeanBuilder {

    /**
     * Builds a list of structure references from a version 2.0 registry query structure request message
     *
     * @param queryStructureRequests the query structure requests
     * @return list of structure references
     */
    List<StructureReferenceBean> build(QueryStructureRequestType queryStructureRequests);

    /**
     * Builds a list of provision references from a version 2.0 registry query registration request message
     *
     * @param queryRegistrationRequestType the query registration request type
     * @return provision references
     */
    StructureReferenceBean build(QueryRegistrationRequestType queryRegistrationRequestType);

    /**
     * Builds a list of provision references from a version 2.1 registry query registration request message
     *
     * @param queryRegistrationRequestType the query registration request type
     * @return provision references
     */
    StructureReferenceBean build(org.sdmx.resources.sdmxml.schemas.v21.registry.QueryRegistrationRequestType queryRegistrationRequestType);

    /**
     * Builds a list of provision references from a version 2.0 registry query provision request message
     *
     * @param queryProvisionRequestType the query provision request type
     * @return provision references
     */
    StructureReferenceBean build(QueryProvisioningRequestType queryProvisionRequestType);

    /**
     * Builds a list of structure references from a version 1.0 query message
     *
     * @param queryMessage the query message
     * @return list of structure references
     */
    List<StructureReferenceBean> build(org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.message.QueryMessageType queryMessage);

    /**
     * Builds a list of structure references from a version 2.0 query message
     *
     * @param queryMessage the query message
     * @return list of structure references
     */
    List<StructureReferenceBean> build(QueryMessageType queryMessage);

    /**
     * Builds a {@link ComplexStructureQuery} from a version 2.1 Codelist query message
     *
     * @param codelistQueryMsg the codelist query msg
     * @return complex structure query
     */
    ComplexStructureQuery build(CodelistQueryType codelistQueryMsg);

    /**
     * Builds a {@link ComplexStructureQuery} from a version 2.1 Dataflow query message
     *
     * @param dataflowQueryMsg the dataflow query msg
     * @return complex structure query
     */
    ComplexStructureQuery build(DataflowQueryType dataflowQueryMsg);

    /**
     * Builds a {@link ComplexStructureQuery} from a version 2.1 Metadataflow query message
     *
     * @param metadataflowQueryMsg the metadataflow query msg
     * @return complex structure query
     */
    ComplexStructureQuery build(MetadataflowQueryType metadataflowQueryMsg);

    /**
     * Builds a {@link ComplexStructureQuery} from a version 2.1 Datastructure query message
     *
     * @param dataStructureQueryMsg the data structure query msg
     * @return complex structure query
     */
    ComplexStructureQuery build(DataStructureQueryType dataStructureQueryMsg);

    /**
     * Builds a {@link ComplexStructureQuery} from a version 2.1 Metadatastructure query message
     *
     * @param metadataStructureQueryMsg the metadata structure query msg
     * @return complex structure query
     */
    ComplexStructureQuery build(MetadataStructureQueryType metadataStructureQueryMsg);

    /**
     * Builds a {@link ComplexStructureQuery} from a version 2.1 CategoryScheme query message
     *
     * @param categorySchemeQueryMsg the category scheme query msg
     * @return complex structure query
     */
    ComplexStructureQuery build(CategorySchemeQueryType categorySchemeQueryMsg);

    /**
     * Builds a {@link ComplexStructureQuery} from a version 2.1 ConceptScheme query message
     *
     * @param conceptSchemeQueryMsg the concept scheme query msg
     * @return complex structure query
     */
    ComplexStructureQuery build(ConceptSchemeQueryType conceptSchemeQueryMsg);

    /**
     * Builds a {@link ComplexStructureQuery} from a version 2.1 HierarchicalCodelist query message
     *
     * @param hierarchicalCodelistQueryMsg the hierarchical codelist query msg
     * @return complex structure query
     */
    ComplexStructureQuery build(HierarchicalCodelistQueryType hierarchicalCodelistQueryMsg);

    /**
     * Builds a {@link ComplexStructureQuery} from a version 2.1 OrganisationScheme query message
     *
     * @param organisationSchemeQueryMsg the organisation scheme query msg
     * @return complex structure query
     */
    ComplexStructureQuery build(OrganisationSchemeQueryType organisationSchemeQueryMsg);

    /**
     * Builds a {@link ComplexStructureQuery} from a version 2.1 ReportingTaxonomy query message
     *
     * @param reportingTaxonomyQueryMsg the reporting taxonomy query msg
     * @return complex structure query
     */
    ComplexStructureQuery build(ReportingTaxonomyQueryType reportingTaxonomyQueryMsg);

    /**
     * Builds a {@link ComplexStructureQuery} from a version 2.1 StructureSet query message
     *
     * @param structureSetQueryMsg the structure set query msg
     * @return complex structure query
     */
    ComplexStructureQuery build(StructureSetQueryType structureSetQueryMsg);

    /**
     * Builds a {@link ComplexStructureQuery} from a version 2.1 Process query message
     *
     * @param processQueryMsg the process query msg
     * @return complex structure query
     */
    ComplexStructureQuery build(ProcessQueryType processQueryMsg);

    /**
     * Builds a {@link ComplexStructureQuery} from a version 2.1 Categorisation query message
     *
     * @param categorisationQueryMsg the categorisation query msg
     * @return complex structure query
     */
    ComplexStructureQuery build(CategorisationQueryType categorisationQueryMsg);

    /**
     * Builds a {@link ComplexStructureQuery} from a version 2.1 ProvisionAgreement query message
     *
     * @param provisionAgreementQueryMsg the provision agreement query msg
     * @return complex structure query
     */
    ComplexStructureQuery build(ProvisionAgreementQueryType provisionAgreementQueryMsg);

    /**
     * Builds a {@link ComplexStructureQuery} from a version 2.1 Constraint query message
     *
     * @param constraintQueryMsg the constraint query msg
     * @return complex structure query
     */
    ComplexStructureQuery build(ConstraintQueryType constraintQueryMsg);

    /**
     * Builds a {@link ComplexStructureQuery} from a version 2.1 Structures query message
     *
     * @param structuresQueryMsg the structures query msg
     * @return complex structure query
     */
    ComplexStructureQuery build(StructuresQueryType structuresQueryMsg);
}
