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

import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Defines namespace constants and root node names for SDMX-ML messages
 *
 * @author Matt Nelson
 */
public class SdmxConstants {
    /**
     * The constant XML_NS.
     */
    public static final String XML_NS = "http://www.w3.org/XML/1998/namespace";
    /**
     * The constant GENERIC_NS_1_0.
     */
    public static final String GENERIC_NS_1_0 = "http://www.SDMX.org/resources/SDMXML/schemas/v1_0/generic";
    /**
     * The constant MESSAGE_NS_1_0.
     */
    public static final String MESSAGE_NS_1_0 = "http://www.SDMX.org/resources/SDMXML/schemas/v1_0/message";
    /**
     * The constant COMPACT_NS_1_0.
     */
    public static final String COMPACT_NS_1_0 = "http://www.SDMX.org/resources/SDMXML/schemas/v1_0/compact";
    /**
     * The constant UTILITY_NS_1_0.
     */
    public static final String UTILITY_NS_1_0 = "http://www.SDMX.org/resources/SDMXML/schemas/v1_0/utility";
    /**
     * The constant CROSS_NS_1_0.
     */
    public static final String CROSS_NS_1_0 = "http://www.SDMX.org/resources/SDMXML/schemas/v1_0/cross";
    /**
     * The constant COMMON_NS_1_0.
     */
    public static final String COMMON_NS_1_0 = "http://www.SDMX.org/resources/SDMXML/schemas/v1_0/common";
    /**
     * The constant QUERY_NS_1_0.
     */
    public static final String QUERY_NS_1_0 = "http://www.SDMX.org/resources/SDMXML/schemas/v1_0/query";
    /**
     * The constant REGISTRY_NS_1_0.
     */
    public static final String REGISTRY_NS_1_0 = "http://www.SDMX.org/resources/SDMXML/schemas/v1_0/registry";
    /**
     * The constant STRUCTURE_NS_1_0.
     */
    public static final String STRUCTURE_NS_1_0 = "http://www.SDMX.org/resources/SDMXML/schemas/v1_0/structure";
    /**
     * The constant GENERICMETADATA_NS_1_0.
     */
    public static final String GENERICMETADATA_NS_1_0 = "http://www.SDMX.org/resources/SDMXML/schemas/v1_0/genericmetadata";
    /**
     * The constant METADATAREPORT_NS_1_0.
     */
    public static final String METADATAREPORT_NS_1_0 = "http://www.SDMX.org/resources/SDMXML/schemas/v1_0/metadatareport";
    /**
     * The constant GENERIC_NS_2_0.
     */
    public static final String GENERIC_NS_2_0 = "http://www.SDMX.org/resources/SDMXML/schemas/v2_0/generic";
    /**
     * The constant MESSAGE_NS_2_0.
     */
    public static final String MESSAGE_NS_2_0 = "http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message";
    /**
     * The constant COMPACT_NS_2_0.
     */
    public static final String COMPACT_NS_2_0 = "http://www.SDMX.org/resources/SDMXML/schemas/v2_0/compact";
    /**
     * The constant UTILITY_NS_2_0.
     */
    public static final String UTILITY_NS_2_0 = "http://www.SDMX.org/resources/SDMXML/schemas/v2_0/utility";
    /**
     * The constant CROSS_NS_2_0.
     */
    public static final String CROSS_NS_2_0 = "http://www.SDMX.org/resources/SDMXML/schemas/v2_0/cross";
    /**
     * The constant COMMON_NS_2_0.
     */
    public static final String COMMON_NS_2_0 = "http://www.SDMX.org/resources/SDMXML/schemas/v2_0/common";
    /**
     * The constant QUERY_NS_2_0.
     */
    public static final String QUERY_NS_2_0 = "http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query";
    /**
     * The constant REGISTRY_NS_2_0.
     */
    public static final String REGISTRY_NS_2_0 = "http://www.SDMX.org/resources/SDMXML/schemas/v2_0/registry";
    /**
     * The constant STRUCTURE_NS_2_0.
     */
    public static final String STRUCTURE_NS_2_0 = "http://www.SDMX.org/resources/SDMXML/schemas/v2_0/structure";
    /**
     * The constant GENERICMETADATA_NS_2_0.
     */
    public static final String GENERICMETADATA_NS_2_0 = "http://www.SDMX.org/resources/SDMXML/schemas/v2_0/genericmetadata";
    /**
     * The constant METADATAREPORT_NS_2_0.
     */
    public static final String METADATAREPORT_NS_2_0 = "http://www.SDMX.org/resources/SDMXML/schemas/v2_0/metadatareport";
    /**
     * The constant GENERIC_NS_2_0_REGISTRY.
     */
    public static final String GENERIC_NS_2_0_REGISTRY = "http://metadatatechnology.com/sdmx/registry/schemas/v2_0/generic";
    /**
     * The constant MESSAGE_NS_2_0_REGISTRY.
     */
    public static final String MESSAGE_NS_2_0_REGISTRY = "http://metadatatechnology.com/sdmx/registry/schemas/v2_0/message";
    /**
     * The constant COMPACT_NS_2_0_REGISTRY.
     */
    public static final String COMPACT_NS_2_0_REGISTRY = "http://metadatatechnology.com/sdmx/registry/schemas/v2_0/compact";
    /**
     * The constant UTILITY_NS_2_0_REGISTRY.
     */
    public static final String UTILITY_NS_2_0_REGISTRY = "http://metadatatechnology.com/sdmx/registry/schemas/v2_0/utility";
    /**
     * The constant CROSS_NS_2_0_REGISTRY.
     */
    public static final String CROSS_NS_2_0_REGISTRY = "http://metadatatechnology.com/sdmx/registry/schemas/v2_0/cross";
    /**
     * The constant COMMON_NS_2_0_REGISTRY.
     */
    public static final String COMMON_NS_2_0_REGISTRY = "http://metadatatechnology.com/sdmx/registry/schemas/v2_0/common";
    /**
     * The constant QUERY_NS_2_0_REGISTRY.
     */
    public static final String QUERY_NS_2_0_REGISTRY = "http://metadatatechnology.com/sdmx/registry/schemas/v2_0/query";
    /**
     * The constant REGISTRY_NS_2_0_REGISTRY.
     */
    public static final String REGISTRY_NS_2_0_REGISTRY = "http://metadatatechnology.com/sdmx/registry/schemas/v2_0/registry";
    /**
     * The constant STRUCTURE_NS_2_0_REGISTRY.
     */
    public static final String STRUCTURE_NS_2_0_REGISTRY = "http://metadatatechnology.com/sdmx/registry/schemas/v2_0/structure";
    /**
     * The constant GENERICMETADATA_NS_2_0_REGISTRY.
     */
    public static final String GENERICMETADATA_NS_2_0_REGISTRY = "http://metadatatechnology.com/sdmx/registry/schemas/v2_0/genericmetadata";
    /**
     * The constant METADATAREPORT_NS_2_0_REGISTRY.
     */
    public static final String METADATAREPORT_NS_2_0_REGISTRY = "http://metadatatechnology.com/sdmx/registry/schemas/v2_0/metadatareport";
    /**
     * The constant GENERIC_NS_2_1.
     */
    public static final String GENERIC_NS_2_1 = "http://www.sdmx.org/resources/sdmxml/schemas/v2_1/data/generic";
    /**
     * The constant MESSAGE_NS_2_1.
     */
    public static final String MESSAGE_NS_2_1 = "http://www.sdmx.org/resources/sdmxml/schemas/v2_1/message";
    /**
     * The constant COMPACT_NS_2_1.
     */
    public static final String COMPACT_NS_2_1 = "http://www.sdmx.org/resources/sdmxml/schemas/v2_1/structurespecific";
    /**
     * The constant COMMON_NS_2_1.
     */
    public static final String COMMON_NS_2_1 = "http://www.sdmx.org/resources/sdmxml/schemas/v2_1/common";
    /**
     * The constant QUERY_NS_2_1.
     */
    public static final String QUERY_NS_2_1 = "http://www.sdmx.org/resources/sdmxml/schemas/v2_1/query";
    /**
     * The constant REGISTRY_NS_2_1.
     */
    public static final String REGISTRY_NS_2_1 = "http://www.sdmx.org/resources/sdmxml/schemas/v2_1/registry";
    /**
     * The constant STRUCTURE_NS_2_1.
     */
    public static final String STRUCTURE_NS_2_1 = "http://www.sdmx.org/resources/sdmxml/schemas/v2_1/structure";
    /**
     * The constant GENERICMETADATA_NS_2_1.
     */
    public static final String GENERICMETADATA_NS_2_1 = "http://www.sdmx.org/resources/sdmxml/schemas/v2_1/genericmetadata";
    /**
     * The constant METADATAREPORT_NS_2_1.
     */
    public static final String METADATAREPORT_NS_2_1 = "http://www.sdmx.org/resources/sdmxml/schemas/v2_1/metadatareport";
    /**
     * The constant STRUCTURE_SPECIFIC_NS_2_1.
     */
    public static final String STRUCTURE_SPECIFIC_NS_2_1 = "http://www.sdmx.org/resources/sdmxml/schemas/v2_1/data/structurespecific";
    /**
     * The constant ERROR_NS_2_1.
     */
    public static final String ERROR_NS_2_1 = "http://www.sdmx.org/resources/sdmxml/schemas/v2_1/message";
    /**
     * The constant GENERIC_DATA_ROOT_NODE.
     */
//DATA ROOT NODES
    public static final String GENERIC_DATA_ROOT_NODE = "GenericData";
    /**
     * The constant UTILITY_DATA_ROOT_NODE.
     */
    public static final String UTILITY_DATA_ROOT_NODE = "UtilityData";
    /**
     * The constant COMPACT_DATA_ROOT_NODE.
     */
    public static final String COMPACT_DATA_ROOT_NODE = "CompactData";
    /**
     * The constant CROSS_SECTIONAL_DATA_ROOT_NODE.
     */
    public static final String CROSS_SECTIONAL_DATA_ROOT_NODE = "CrossSectionalData";
    /**
     * The constant MESSAGE_GROUP_ROOT_NODE.
     */
    public static final String MESSAGE_GROUP_ROOT_NODE = "MessageGroup";
    /**
     * The constant STRUCTURE_SPECIFIC_TIME_SERIES_DATA.
     */
//2.1
    public static final String STRUCTURE_SPECIFIC_TIME_SERIES_DATA = "StructureSpecificTimeSeriesData";
    /**
     * The constant STRUCTURE_SPECIFIC_DATA.
     */
    public static final String STRUCTURE_SPECIFIC_DATA = "StructureSpecificData";
    /**
     * The constant GENERIC_TIME_SERIES_DATA_ROOT_NODE.
     */
    public static final String GENERIC_TIME_SERIES_DATA_ROOT_NODE = "GenericTimeSeriesData";
    /**
     * The constant STRUCTURE_ROOT_NODE.
     */
//STRUCTURE ROOT NODES
    public static final String STRUCTURE_ROOT_NODE = "Structure";
    /**
     * The constant REGISTRY_INTERFACE_ROOT_NODE.
     */
    public static final String REGISTRY_INTERFACE_ROOT_NODE = "RegistryInterface";
    /**
     * The constant QUERY_MESSAGE_ROOT_NODE.
     */
    public static final String QUERY_MESSAGE_ROOT_NODE = "QueryMessage";
    /**
     * The constant MISSING_DATA_VALUE.
     */
    public static final String MISSING_DATA_VALUE = "NaN";
    /**
     * The constant REST_WILDCARD.
     */
    public static final String REST_WILDCARD = "*";
    private static final Map<String, String> v1Tov2Map;
    private static final Map<String, String> v2Tov1Map;
    private static final Map<String, String> namespaceToSchema;

    //Build Maps
    static {
        v1Tov2Map = new HashMap<String, String>();
        v1Tov2Map.put(GENERIC_NS_1_0, GENERIC_NS_2_0);
        v1Tov2Map.put(MESSAGE_NS_1_0, MESSAGE_NS_2_0);
        v1Tov2Map.put(COMPACT_NS_1_0, COMPACT_NS_2_0);
        v1Tov2Map.put(UTILITY_NS_1_0, UTILITY_NS_2_0);
        v1Tov2Map.put(CROSS_NS_1_0, CROSS_NS_2_0);
        v1Tov2Map.put(COMMON_NS_1_0, COMMON_NS_2_0);
        v1Tov2Map.put(QUERY_NS_1_0, QUERY_NS_2_0);
        v1Tov2Map.put(REGISTRY_NS_1_0, REGISTRY_NS_2_0);
        v1Tov2Map.put(STRUCTURE_NS_1_0, STRUCTURE_NS_2_0);
        v1Tov2Map.put(GENERICMETADATA_NS_1_0, GENERICMETADATA_NS_2_0);
        v1Tov2Map.put(METADATAREPORT_NS_1_0, METADATAREPORT_NS_2_0);

        v2Tov1Map = new HashMap<String, String>();
        v2Tov1Map.put(GENERIC_NS_2_0, GENERIC_NS_1_0);
        v2Tov1Map.put(MESSAGE_NS_2_0, MESSAGE_NS_1_0);
        v2Tov1Map.put(COMPACT_NS_2_0, COMPACT_NS_1_0);
        v2Tov1Map.put(UTILITY_NS_2_0, UTILITY_NS_1_0);
        v2Tov1Map.put(CROSS_NS_2_0, CROSS_NS_1_0);
        v2Tov1Map.put(COMMON_NS_2_0, COMMON_NS_1_0);
        v2Tov1Map.put(QUERY_NS_2_0, QUERY_NS_1_0);
        v2Tov1Map.put(REGISTRY_NS_2_0, REGISTRY_NS_1_0);
        v2Tov1Map.put(STRUCTURE_NS_2_0, STRUCTURE_NS_1_0);
        v2Tov1Map.put(GENERICMETADATA_NS_2_0, GENERICMETADATA_NS_1_0);
        v2Tov1Map.put(METADATAREPORT_NS_2_0, METADATAREPORT_NS_1_0);

        namespaceToSchema = new HashMap<String, String>();
        namespaceToSchema.put(COMMON_NS_1_0, "SDMXCommon.xsd");
        namespaceToSchema.put(COMMON_NS_2_0, "SDMXCommon.xsd");
        namespaceToSchema.put(COMMON_NS_2_1, "SDMXCommon.xsd");
        namespaceToSchema.put(MESSAGE_NS_1_0, "SDMXMessage.xsd");
        namespaceToSchema.put(MESSAGE_NS_2_0, "SDMXMessage.xsd");
        namespaceToSchema.put(MESSAGE_NS_2_1, "SDMXMessage.xsd");
        namespaceToSchema.put(ERROR_NS_2_1, "SDMXMessage.xsd");
        namespaceToSchema.put(GENERIC_NS_1_0, "SDMXGenericData.xsd");
        namespaceToSchema.put(GENERIC_NS_2_0, "SDMXGenericData.xsd");
        namespaceToSchema.put(GENERIC_NS_2_1, "SDMXDataGeneric.xsd");
        namespaceToSchema.put(COMPACT_NS_1_0, "SDMXCompactData.xsd");
        namespaceToSchema.put(COMPACT_NS_2_0, "SDMXCompactData.xsd");
        namespaceToSchema.put(COMPACT_NS_2_1, "SDMXDataStructureSpecific.xsd");
        namespaceToSchema.put(UTILITY_NS_1_0, "SDMXUtilityData.xsd");
        namespaceToSchema.put(UTILITY_NS_2_0, "SDMXUtilityData.xsd");
        namespaceToSchema.put(CROSS_NS_1_0, "SDMXCrossSectionalData.xsd");
        namespaceToSchema.put(CROSS_NS_2_0, "SDMXCrossSectionalData.xsd");
        namespaceToSchema.put(QUERY_NS_1_0, "SDMXQuery.xsd");
        namespaceToSchema.put(QUERY_NS_2_0, "SDMXQuery.xsd");
        namespaceToSchema.put(QUERY_NS_2_1, "SDMXQuery.xsd");
        namespaceToSchema.put(REGISTRY_NS_1_0, "SDMXRegistry.xsd");
        namespaceToSchema.put(REGISTRY_NS_2_0, "SDMXRegistry.xsd");
        namespaceToSchema.put(REGISTRY_NS_2_1, "SDMXRegistry.xsd");
        namespaceToSchema.put(STRUCTURE_NS_1_0, "SDMXStructure.xsd");
        namespaceToSchema.put(STRUCTURE_NS_2_0, "SDMXStructure.xsd");
        namespaceToSchema.put(STRUCTURE_NS_2_1, "SDMXStructure.xsd");
        namespaceToSchema.put(GENERICMETADATA_NS_1_0, "SDMXStructure.xsd");
        namespaceToSchema.put(GENERICMETADATA_NS_2_0, "SDMXGenericMetadata.xsd");
        namespaceToSchema.put(GENERICMETADATA_NS_2_1, "SDMXMetadataGeneric.xsd");
        namespaceToSchema.put(METADATAREPORT_NS_1_0, "SDMXMetadataReport.xsd");
        namespaceToSchema.put(METADATAREPORT_NS_2_0, "SDMXMetadataReport.xsd");
    }

    /**
     * Returns the name of the schema for a schema URI for example:
     * http://metadatatechnology.com/sdmx/registry/schemas/v2_0/common would return SDMXCommon.xsd
     *
     * @param schemaURI the schema uri
     * @return Schema file name, or null if the URI does not map to anything
     */
    public static String getSchemaName(String schemaURI) {
        return namespaceToSchema.get(schemaURI);
    }

    /**
     * Returns true if the namespace is a valid SDMX namespace
     *
     * @param uri the uri
     * @return true if the namespace is a valid SDMX namespace
     */
    public static boolean isKnownNamespace(String uri) {
        return getNamespacesV1().contains(uri) || getNamespacesV2().contains(uri) || getNamespacesV2_1().contains(uri) || getNamespacesV2Registry().contains(uri);
    }

    /**
     * Returns the version of the Schema for the given URI
     *
     * @param uri the uri
     * @return schema version
     */
    public static SDMX_SCHEMA getSchemaVersion(String uri) {
        if (getNamespacesV1().contains(uri)) {
            return SDMX_SCHEMA.VERSION_ONE;
        }
        if (getNamespacesV2().contains(uri)) {
            return SDMX_SCHEMA.VERSION_TWO;
        }
        if (getNamespacesV2_1().contains(uri)) {
            return SDMX_SCHEMA.VERSION_TWO_POINT_ONE;
        }
        throw new IllegalArgumentException("Unknown schema URI: " + uri);
    }

    /**
     * Takes a namespace and returns the equivalent in the given SDMX_SCHEMA
     *
     * @param inputNamespace the input namespace
     * @param schemaVersion  the schema version
     * @return the equivalent in the given SDMX_SCHEMA
     */
    public static String switchNamespaceURI(String inputNamespace, SDMX_SCHEMA schemaVersion) {
        if (getNamespacesV1().contains(inputNamespace)) {
            return switchNamespace(SDMX_SCHEMA.VERSION_ONE, schemaVersion, inputNamespace);
        } else if (getNamespacesV2().contains(inputNamespace)) {
            return switchNamespace(SDMX_SCHEMA.VERSION_TWO, schemaVersion, inputNamespace);
        } else if (getNamespacesV2_1().contains(inputNamespace)) {
            return switchNamespace(SDMX_SCHEMA.VERSION_TWO_POINT_ONE, schemaVersion, inputNamespace);
        }
        throw new IllegalArgumentException("unknown namespace : " + inputNamespace); //TODO Exception
    }

    private static String switchNamespace(SDMX_SCHEMA inputFormat, SDMX_SCHEMA outputFormat, String namespaceURI) {
        switch (inputFormat) {
            case VERSION_ONE:
                switch (outputFormat) {
                    case VERSION_ONE:
                        return namespaceURI;
                    case VERSION_TWO:
                        return v1Tov2Map.get(namespaceURI);
                    case VERSION_TWO_POINT_ONE:
                        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "2.1"); //TODO 2.1
                    default:
                        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, outputFormat);
                }
            case VERSION_TWO:
                switch (outputFormat) {
                    case VERSION_ONE:
                        return v2Tov1Map.get(namespaceURI);
                    case VERSION_TWO:
                        return namespaceURI;
                    case VERSION_TWO_POINT_ONE:
                        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "2.1"); //TODO 2.1
                    default:
                        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, outputFormat);
                }
            default:
                throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, outputFormat);
        }
    }

    /**
     * Returns a copy of the map containing the version 2.0 namespaces mapped to the 2.1 namespaces
     *
     * @return v 2 to v 1 map
     */
    public static Map<String, String> getV2toV1Map() {
        return new HashMap<String, String>(v2Tov1Map);
    }

    /**
     * Returns a copy of the map containing the version 1.0 namespaces mapped to the 2.1 namespaces
     *
     * @return v 1 to v 2 map
     */
    public static Map<String, String> getV1toV2Map() {
        return new HashMap<String, String>(v1Tov2Map);
    }

    /**
     * Gets namespaces v 1.
     *
     * @return the namespaces v 1
     */
    /*
     * Returns a list of all the SDMX version 1.0 namespaces
     */
    public static List<String> getNamespacesV1() {
        List<String> allNamespaces = new ArrayList<String>();
        allNamespaces.add(GENERIC_NS_1_0);
        allNamespaces.add(MESSAGE_NS_1_0);
        allNamespaces.add(COMPACT_NS_1_0);
        allNamespaces.add(UTILITY_NS_1_0);
        allNamespaces.add(CROSS_NS_1_0);
        allNamespaces.add(COMMON_NS_1_0);
        allNamespaces.add(QUERY_NS_1_0);
        allNamespaces.add(REGISTRY_NS_1_0);
        allNamespaces.add(STRUCTURE_NS_1_0);
        allNamespaces.add(GENERICMETADATA_NS_1_0);
        allNamespaces.add(METADATAREPORT_NS_1_0);
        return allNamespaces;

    }

    /**
     * Gets namespaces v 2.
     *
     * @return the namespaces v 2
     */
    /*
     * Returns a list of all the SDMX version 2.0 namespaces
     */
    public static List<String> getNamespacesV2() {
        List<String> allNamespaces = new ArrayList<String>();
        allNamespaces.add(GENERIC_NS_2_0);
        allNamespaces.add(MESSAGE_NS_2_0);
        allNamespaces.add(COMPACT_NS_2_0);
        allNamespaces.add(UTILITY_NS_2_0);
        allNamespaces.add(CROSS_NS_2_0);
        allNamespaces.add(COMMON_NS_2_0);
        allNamespaces.add(QUERY_NS_2_0);
        allNamespaces.add(REGISTRY_NS_2_0);
        allNamespaces.add(STRUCTURE_NS_2_0);
        allNamespaces.add(GENERICMETADATA_NS_2_0);
        allNamespaces.add(METADATAREPORT_NS_2_0);
        return allNamespaces;
    }

    /**
     * Gets namespaces v 2 1.
     *
     * @return the namespaces v 2 1
     */
    /*
     * Returns a list of all the SDMX version 2.1 namespaces
     */
    public static List<String> getNamespacesV2_1() {
        List<String> allNamespaces = new ArrayList<String>();
        allNamespaces.add(GENERIC_NS_2_1);
        allNamespaces.add(MESSAGE_NS_2_1);
        allNamespaces.add(COMPACT_NS_2_1);
        allNamespaces.add(COMMON_NS_2_1);
        allNamespaces.add(QUERY_NS_2_1);
        allNamespaces.add(REGISTRY_NS_2_1);
        allNamespaces.add(STRUCTURE_NS_2_1);
        allNamespaces.add(GENERICMETADATA_NS_2_1);
        allNamespaces.add(METADATAREPORT_NS_2_1);
        allNamespaces.add(ERROR_NS_2_1);
        return allNamespaces;
    }

    /**
     * Gets namespaces v 2 registry.
     *
     * @return the namespaces v 2 registry
     */
    public static List<String> getNamespacesV2Registry() {
        List<String> allNamespaces = new ArrayList<String>();
        allNamespaces.add(GENERIC_NS_2_0_REGISTRY);
        allNamespaces.add(MESSAGE_NS_2_0_REGISTRY);
        allNamespaces.add(COMPACT_NS_2_0_REGISTRY);
        allNamespaces.add(UTILITY_NS_2_0_REGISTRY);
        allNamespaces.add(CROSS_NS_2_0_REGISTRY);
        allNamespaces.add(COMMON_NS_2_0_REGISTRY);
        allNamespaces.add(QUERY_NS_2_0_REGISTRY);
        allNamespaces.add(REGISTRY_NS_2_0_REGISTRY);
        allNamespaces.add(STRUCTURE_NS_2_0_REGISTRY);
        allNamespaces.add(GENERICMETADATA_NS_2_0_REGISTRY);
        allNamespaces.add(METADATAREPORT_NS_2_0_REGISTRY);
        return allNamespaces;
    }

}
