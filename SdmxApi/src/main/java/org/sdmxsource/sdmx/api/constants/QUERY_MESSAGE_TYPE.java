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
package org.sdmxsource.sdmx.api.constants;

/**
 * Contains a list of Query message types.
 * <p>
 * This Enum offers the ability to retrieve the underlying <b>BASE_QUERY_MESSAGE_TYPE</b>
 *
 * @author Matt Nelson
 * @see BASE_QUERY_MESSAGE_TYPE
 */
public enum QUERY_MESSAGE_TYPE {
    DATA_WHERE("DataWhere", BASE_QUERY_MESSAGE_TYPE.DATA),
    GENERIC_DATA_QUERY("GenericDataQuery", BASE_QUERY_MESSAGE_TYPE.DATA),
    STRUCTURE_SPECIFIC_DATA_QUERY("StructureSpecificDataQuery", BASE_QUERY_MESSAGE_TYPE.DATA),
    GENERIC_TIME_SERIES_DATA_QUERY("GenericTimeSeriesDataQuery", BASE_QUERY_MESSAGE_TYPE.DATA),
    STRUCTURE_SPECIFIC_TIME_SERIES_DATA_QUERY("StructureSpecificTimeSeriesDataQuery", BASE_QUERY_MESSAGE_TYPE.DATA),
    METADATA_WHERE("MetadataWhere", BASE_QUERY_MESSAGE_TYPE.METADATA),
    GENERIC_METADATA_QUERY("GenericMetadataQuery", BASE_QUERY_MESSAGE_TYPE.METADATA),
    STRUCTURE_SPECIFIC_METADATA_QUERY("StructureSpecificMetadataQuery", BASE_QUERY_MESSAGE_TYPE.METADATA),
    DSD_WHERE("KeyFamilyWhere", BASE_QUERY_MESSAGE_TYPE.STRUCTURE), // also DataStructureWhere mapped in parseString()
    MDS_WHERE("MetadataStructureWhere", BASE_QUERY_MESSAGE_TYPE.STRUCTURE),
    CODELIST_WHERE("CodelistWhere", BASE_QUERY_MESSAGE_TYPE.STRUCTURE),
    CONCEPT_WHERE("ConceptWhere", BASE_QUERY_MESSAGE_TYPE.STRUCTURE),
    AGENCY_WHERE("AgencyWhere", BASE_QUERY_MESSAGE_TYPE.STRUCTURE),
    DATA_PROVIDER_WHERE("DataProviderWhere", BASE_QUERY_MESSAGE_TYPE.STRUCTURE),
    HCL_WHERE("HierarchicalCodelistWhere", BASE_QUERY_MESSAGE_TYPE.STRUCTURE),
    REPORTING_TAXONOMY_WHERE("ReportingTaxonomyWhere", BASE_QUERY_MESSAGE_TYPE.STRUCTURE),
    DATAFLOW_WHERE("DataflowWhere", BASE_QUERY_MESSAGE_TYPE.STRUCTURE),
    METADATAFLOW_WHERE("MetadataflowWhere", BASE_QUERY_MESSAGE_TYPE.STRUCTURE),
    STRUCTURE_SET_WHERE("StructureSetWhere", BASE_QUERY_MESSAGE_TYPE.STRUCTURE),
    PROCESS_WHERE("ProcessWhere", BASE_QUERY_MESSAGE_TYPE.STRUCTURE),
    ORGANISATION_SCHEME_WHERE("OrganisationSchemeWhere", BASE_QUERY_MESSAGE_TYPE.STRUCTURE),
    CONCEPT_SCHEME_WHERE("ConceptSchemeWhere", BASE_QUERY_MESSAGE_TYPE.STRUCTURE),
    CATEGORY_SCHEME_WHERE("CategorySchemeWhere", BASE_QUERY_MESSAGE_TYPE.STRUCTURE),
    CATEGORISATION_WHERE("CategorisationWhere", BASE_QUERY_MESSAGE_TYPE.STRUCTURE),
    PROVISION_AGREEMENT_WHERE("ProvisionAgreementWhere", BASE_QUERY_MESSAGE_TYPE.STRUCTURE),
    CONSTRAINT_WHERE("ConstraintWhere", BASE_QUERY_MESSAGE_TYPE.STRUCTURE),
    STRUCTURES_WHERE("StructuresWhere", BASE_QUERY_MESSAGE_TYPE.STRUCTURE);

    private String nodeName;
    private BASE_QUERY_MESSAGE_TYPE baseQueryMessageType;

    private QUERY_MESSAGE_TYPE(String nodeName, BASE_QUERY_MESSAGE_TYPE baseQueryMessageType) {
        this.nodeName = nodeName;
        this.baseQueryMessageType = baseQueryMessageType;
    }

    public static QUERY_MESSAGE_TYPE parseString(String messageType) {
        if (messageType == null) {
            throw new IllegalArgumentException("QUERY_MESSAGE_TYPE.parseString can not parse null");
        }

        if (messageType.equalsIgnoreCase("DataStructureWhere")) { // this 2.1. In 2.0 it has KeyFamilyWhere
            return DSD_WHERE;
        }

        for (QUERY_MESSAGE_TYPE currentType : QUERY_MESSAGE_TYPE.values()) {
            if (currentType.getNodeName().equalsIgnoreCase(messageType)) {
                return currentType;
            }
        }
        throw new IllegalArgumentException("QUERY_MESSAGE_TYPE.parseString unknown message type : " + messageType);
    }

    public String getNodeName() {
        return nodeName;
    }

    public BASE_QUERY_MESSAGE_TYPE getBaseQueryMessageType() {
        return baseQueryMessageType;
    }
}
