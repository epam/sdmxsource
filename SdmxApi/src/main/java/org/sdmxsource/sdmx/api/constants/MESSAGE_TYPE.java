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
 * Contains a list of the types of SDMX document that can be processed
 *
 * @author Matt Nelson
 */
public enum MESSAGE_TYPE {
    /**
     * Structure message type.
     */
    STRUCTURE("Structure"),
    /**
     * Registry interface message type.
     */
    REGISTRY_INTERFACE("RegistryInterface"),
    /**
     * Query message type.
     */
    QUERY("QueryMessage"),
    /**
     * Generic data message type.
     */
    GENERIC_DATA("GenericData"),
    /**
     * Utility data message type.
     */
    UTILITY_DATA("UtilityData"),
    /**
     * Compact data message type.
     */
    COMPACT_DATA("CompactData"),
    /**
     * Cross sectional data message type.
     */
    CROSS_SECTIONAL_DATA("CrossSectionalData"),
    /**
     * Generic metadata message type.
     */
    GENERIC_METADATA("GenericMetadata"),
    /**
     * Metadata report message type.
     */
    METADATA_REPORT("MetadataReport"),
    /**
     * Message group message type.
     */
    MESSAGE_GROUP("MessageGroup"),
    /**
     * Sdmx edi message type.
     */
    SDMX_EDI("EDI"),
    /**
     * Error message type.
     */
    ERROR("Error"),
    /**
     * Unknown message type.
     */
    UNKNOWN("Unknown");


    private String nodeName;

    private MESSAGE_TYPE(String nodeName) {
        this.nodeName = nodeName;
    }

    /**
     * Returns the MESSAGE_TYPE from a String (representing the Node name given by the SDMX schema).  The
     * following shows the map of string to enum:
     * <ul>
     *   <li>Structure=MESSAGE_TYPE.STRUCTURE</li>
     *   <li>RegistryInterface=MESSAGE_TYPE.REGISTRY_INTERFACE</li>
     *   <li>QueryMessage=MESSAGE_TYPE.QUERY</li>
     *   <li>GenericDataQuery, StructureSpecificDataQuery, GenericTimeSeriesDataQuery, StructureSpecificTimeSeriesDataQuery, GenericMetadataQuery, StructureSpecificMetadataQuery=MESSAGE_TYPE.QUERY</li>
     *   <li>StructuresQuery, DataflowQuery, MetadataflowQuery, DataStructureQuery, MetadataStructureQuery, CategorySchemeQuery, ConceptSchemeQuery, CodelistQuery, HierarchicalCodelistQuery, OrganisationSchemeQuery, ReportingTaxonomyQuery, StructureSetQuery, ProcessQuery, CategorisationQuery, ProvisionAgreementQuery, ConstraintQuery=MESSAGE_TYPE.QUERY</li>
     *   <li>GenericData=MESSAGE_TYPE.GENERIC_DATA</li>
     *   <li>UtilityData=MESSAGE_TYPE.UTILITY_DATA</li>
     *   <li>CompactData=MESSAGE_TYPE.COMPACT_DATA</li>
     *   <li>CrossSectionalData=MESSAGE_TYPE.CROSS_SECTIONAL_DATA</li>
     *   <li>GenericMetadata=MESSAGE_TYPE.GENERIC_METADATA</li>
     *   <li>MetadataReport=MESSAGE_TYPE.METADATA_REPORT</li>
     *   <li>MessageGroup=MESSAGE_TYPE.MESSAAGE_GROUP</li>
     *   <li>EDI=MESSAGE_TYPE.SDMX_EDI</li>
     * </ul>
     *
     * @param messageType the message type
     * @return message type
     */
    public static MESSAGE_TYPE parseString(String messageType) {
        if (messageType == null) {
            throw new IllegalArgumentException("MESSAGE_TYPE.parseString can not parse null");
        }

        // Dataset messages
        if (messageType.equals("StructureSpecificTimeSeriesData")
                || messageType.equals("StructureSpecificData")) {
            return MESSAGE_TYPE.COMPACT_DATA;
        }
        if (messageType.equals("GenericTimeSeriesData")) {
            return MESSAGE_TYPE.GENERIC_DATA;
        }

        // Data and metadata query v2.1 messages
        if (messageType.equals("GenericDataQuery")) {
            return MESSAGE_TYPE.QUERY;
        }
        if (messageType.equals("StructureSpecificDataQuery")) {
            return MESSAGE_TYPE.QUERY;
        }
        if (messageType.equals("GenericTimeSeriesDataQuery")) {
            return MESSAGE_TYPE.QUERY;
        }
        if (messageType.equals("StructureSpecificTimeSeriesDataQuery")) {
            return MESSAGE_TYPE.QUERY;
        }
        if (messageType.equals("GenericMetadataQuery")) {
            return MESSAGE_TYPE.QUERY;
        }
        if (messageType.equals("StructureSpecificMetadataQuery")) {
            return MESSAGE_TYPE.QUERY;
        }

        // Structure query v2.1 messages
        if (messageType.equals("StructuresQuery")) {
            return MESSAGE_TYPE.QUERY;
        }
        if (messageType.equals("DataflowQuery")) {
            return MESSAGE_TYPE.QUERY;
        }
        if (messageType.equals("MetadataflowQuery")) {
            return MESSAGE_TYPE.QUERY;
        }
        if (messageType.equals("DataStructureQuery")) {
            return MESSAGE_TYPE.QUERY;
        }
        if (messageType.equals("MetadataStructureQuery")) {
            return MESSAGE_TYPE.QUERY;
        }
        if (messageType.equals("CategorySchemeQuery")) {
            return MESSAGE_TYPE.QUERY;
        }
        if (messageType.equals("ConceptSchemeQuery")) {
            return MESSAGE_TYPE.QUERY;
        }
        if (messageType.equals("CodelistQuery")) {
            return MESSAGE_TYPE.QUERY;
        }
        if (messageType.equals("HierarchicalCodelistQuery")) {
            return MESSAGE_TYPE.QUERY;
        }
        if (messageType.equals("OrganisationSchemeQuery")) {
            return MESSAGE_TYPE.QUERY;
        }
        if (messageType.equals("ReportingTaxonomyQuery")) {
            return MESSAGE_TYPE.QUERY;
        }
        if (messageType.equals("StructureSetQuery")) {
            return MESSAGE_TYPE.QUERY;
        }
        if (messageType.equals("ProcessQuery")) {
            return MESSAGE_TYPE.QUERY;
        }
        if (messageType.equals("CategorisationQuery")) {
            return MESSAGE_TYPE.QUERY;
        }
        if (messageType.equals("ProvisionAgreementQuery")) {
            return MESSAGE_TYPE.QUERY;
        }
        if (messageType.equals("ConstraintQuery")) {
            return MESSAGE_TYPE.QUERY;
        }

        // check messages that the enumeration value correspont to message root element name
        for (MESSAGE_TYPE currentType : MESSAGE_TYPE.values()) {
            if (currentType.getNodeName().equalsIgnoreCase(messageType)) {
                return currentType;
            }
        }
        throw new IllegalArgumentException("'" + messageType + "' is not a known root node for an SDMX message");
    }

    /**
     * Gets node name.
     *
     * @return the node name
     */
    public String getNodeName() {
        return nodeName;
    }

    /**
     * Is data boolean.
     *
     * @return the boolean
     */
    public boolean isData() {
        return this == COMPACT_DATA || this == GENERIC_DATA || this == UTILITY_DATA || this == CROSS_SECTIONAL_DATA || this == SDMX_EDI;
    }
}
