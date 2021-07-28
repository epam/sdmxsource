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

import org.sdmxsource.sdmx.api.exception.SdmxSyntaxException;
import org.sdmxsource.sdmx.api.model.beans.base.*;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategorisationBean;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategorySchemeBean;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.ReportingTaxonomyBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.HierarchicalCodelistBean;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptSchemeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.*;
import org.sdmxsource.sdmx.api.model.beans.mapping.StructureSetBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataFlowBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataStructureDefinitionBean;
import org.sdmxsource.sdmx.api.model.beans.process.ProcessBean;
import org.sdmxsource.sdmx.api.model.beans.registry.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Defines all the structures that SDMX supports, includes methods to get urn prefix + is maintainable and identifiable.
 *
 * @author Matt Nelson
 */
public enum SDMX_STRUCTURE_TYPE {

    /**
     * Any sdmx structure type.
     */
//NON-IDENTIFIABLES
    ANY("Any"),
    /**
     * Annotation sdmx structure type.
     */
    ANNOTATION("Annotation"),
    /**
     * The Category id.
     */
    CATEGORY_ID("Category ID"),
    /**
     * Datasource sdmx structure type.
     */
    DATASOURCE("DataSource"),
    /**
     * The Text format.
     */
    TEXT_FORMAT("Text Format"),
    /**
     * The Text type.
     */
    TEXT_TYPE("Text Type"),
    /**
     * The Related structures.
     */
    RELATED_STRUCTURES("Related Structures"),
    /**
     * Code list ref sdmx structure type.
     */
    CODE_LIST_REF("CodelistRef"),
    /**
     * Contact sdmx structure type.
     */
    CONTACT("Contact"),
    /**
     * Component sdmx structure type.
     */
    COMPONENT("Component"),
    /**
     * Dataset sdmx structure type.
     */
    DATASET("DataSet"),
    /**
     * The Dataset reference.
     */
    DATASET_REFERENCE("Dataset Reference"),
    /**
     * The Item map.
     */
    ITEM_MAP("Item Map"),
    /**
     * The Local representation.
     */
    LOCAL_REPRESENTATION("Local Representation"),
    /**
     * Computation sdmx structure type.
     */
    COMPUTATION("Computation"),
    /**
     * Input output sdmx structure type.
     */
    INPUT_OUTPUT("Input/Output"),
    /**
     * The Constrained data key.
     */
    CONSTRAINED_DATA_KEY("Constrained Data Key"),
    /**
     * The Constrained data key set.
     */
    CONSTRAINED_DATA_KEY_SET("Data Key Set"),
    /**
     * The Attachment constraint attachment.
     */
    ATTACHMENT_CONSTRAINT_ATTACHMENT("Attachment Constraint Attachment"),
    /**
     * The Content constraint attachment.
     */
    CONTENT_CONSTRAINT_ATTACHMENT("Content Constraint Attachment"),
    /**
     * The Reference period.
     */
    REFERENCE_PERIOD("Reference Period"),
    /**
     * The Release calendar.
     */
    RELEASE_CALENDAR("Release Calendar"),
    /**
     * The Time range.
     */
    TIME_RANGE("Time Range"),
    /**
     * The Key values.
     */
    KEY_VALUES("Key Values"),
    /**
     * The Cube region.
     */
    CUBE_REGION("Cube Region"),
    /**
     * Constraint sdmx structure type.
     */
    CONSTRAINT("Constraint"),
    /**
     * The Metadata document.
     */
    METADATA_DOCUMENT("Metadata Document"),
    /**
     * The Metadata set.
     */
    METADATA_SET("Metadata Set"),
    /**
     * The Metadata report.
     */
    METADATA_REPORT("Metadata Report"),
    /**
     * The Metadata report target.
     */
    METADATA_REPORT_TARGET("Metadata Report Target"),
    /**
     * The Metadata reference value.
     */
    METADATA_REFERENCE_VALUE("Metadata Reference Value"),
    /**
     * The Metadata report attribute.
     */
    METADATA_REPORT_ATTRIBUTE("Metadata Reported Attribute"),
    /**
     * The Metadata target region.
     */
    METADATA_TARGET_REGION("Metadata Target Region"),
    /**
     * Organisation sdmx structure type.
     */
    ORGANISATION("Organisation"),
    /**
     * The Organisation scheme.
     */
    ORGANISATION_SCHEME("Organisation Scheme"),


    /**
     * The Agency scheme.
     */
//BASE
    AGENCY_SCHEME(AgencySchemeBean.class, "base", "AgencyScheme", "Agency Scheme"),
    /**
     * Agency sdmx structure type.
     */
    AGENCY("base", "Agency", "Agency", AGENCY_SCHEME),
    /**
     * The Data provider scheme.
     */
    DATA_PROVIDER_SCHEME(DataProviderSchemeBean.class, "base", "DataProviderScheme", "Data Provider Scheme"),
    /**
     * The Data provider.
     */
    DATA_PROVIDER("base", "DataProvider", "Data Provider", DATA_PROVIDER_SCHEME),
    /**
     * The Data consumer scheme.
     */
    DATA_CONSUMER_SCHEME(DataConsumerSchemeBean.class, "base", "DataConsumerScheme", "Data Consumer Scheme"),
    /**
     * The Data consumer.
     */
    DATA_CONSUMER("base", "DataConsumer", "Data Consumer", DATA_CONSUMER_SCHEME),
    /**
     * The Organisation unit scheme.
     */
    ORGANISATION_UNIT_SCHEME(OrganisationUnitSchemeBean.class, "base", "OrganisationUnitScheme", "Organisation Unit Scheme"),
    /**
     * The Organisation unit.
     */
    ORGANISATION_UNIT("base", "OrganisationUnit", "Organisation Unit", ORGANISATION_UNIT_SCHEME),

    /**
     * Code list sdmx structure type.
     */
//CODELIST
    CODE_LIST(CodelistBean.class, "codelist", "CodeList", "codelist", "Codelist", "Codelist"),
    /**
     * Code sdmx structure type.
     */
    CODE("codelist", "Code", "Code", CODE_LIST),
    /**
     * The Hierarchical codelist.
     */
    HIERARCHICAL_CODELIST(HierarchicalCodelistBean.class, "codelist", "HierarchicCodeList", "codelist", "HierarchicalCodelist", "Hierarchical Code List"),
    /**
     * Hierarchy sdmx structure type.
     */
    HIERARCHY("codelist", "Hierarchy", "Hierarchy", HIERARCHICAL_CODELIST),
    /**
     * The Hierarchical code.
     */
    HIERARCHICAL_CODE("codelist", "HierarchicalCode", "Hierarchical Code", HIERARCHY),
    /**
     * Level sdmx structure type.
     */
    LEVEL("codelist", "Level", "Level", HIERARCHY),

    /**
     * The Categorisation.
     */
//CATEGORY SCHEME
    CATEGORISATION(CategorisationBean.class, "categoryscheme", "Categorisation", "Categorisation"),
    /**
     * The Category scheme.
     */
    CATEGORY_SCHEME(CategorySchemeBean.class, "categoryscheme", "CategoryScheme", "Category Scheme"),
    /**
     * Category sdmx structure type.
     */
    CATEGORY("categoryscheme", "Category", "Category", CATEGORY_SCHEME),
    /**
     * The Reporting taxonomy.
     */
    REPORTING_TAXONOMY(ReportingTaxonomyBean.class, "categoryscheme", "ReportingTaxonomy", "Reporting Taxonomy"),
    /**
     * The Reporting category.
     */
    REPORTING_CATEGORY("categoryscheme", "ReportingCategory", "Reporting Category", REPORTING_TAXONOMY),

    /**
     * The Concept scheme.
     */
//CONCEPT SCHEME
    CONCEPT_SCHEME(ConceptSchemeBean.class, "conceptscheme", "ConceptScheme", "Concept Scheme"),
    /**
     * Concept sdmx structure type.
     */
    CONCEPT("conceptscheme", "Concept", "Concept", CONCEPT_SCHEME),

    /**
     * The Dsd.
     */
//DATA STRUCTURE
    DSD(DataStructureBean.class, "keyfamily", "KeyFamily", "datastructure", "DataStructure", "Data Structure Definition"),
    /**
     * The Attribute descriptor.
     */
    ATTRIBUTE_DESCRIPTOR("datastructure", "AttributeDescriptor", "Attribute Descriptor", DSD, AttributeListBean.FIXED_ID),
    /**
     * The Data attribute.
     */
    DATA_ATTRIBUTE("datastructure", "DataAttribute", "Data Attribute", ATTRIBUTE_DESCRIPTOR),
    /**
     * Dataflow sdmx structure type.
     */
    DATAFLOW(DataflowBean.class, "keyfamily", "Dataflow", "datastructure", "Dataflow", "Dataflow"),
    /**
     * The Dimension descriptor.
     */
    DIMENSION_DESCRIPTOR("datastructure", "DimensionDescriptor", "Dimension Descriptor", DSD, DimensionListBean.FIXED_ID),
    /**
     * Dimension sdmx structure type.
     */
    DIMENSION("datastructure", "Dimension", "Dimension", DIMENSION_DESCRIPTOR),
    /**
     * The Group.
     */
    GROUP("datastructure", "GroupDimensionDescriptor", "Group Dimension Descriptor", DSD),
    /**
     * The Cross sectional measure.
     */
    CROSS_SECTIONAL_MEASURE("datastructure", "CrossSectionalMeasure", "Cross Sectional Measure", DSD),
    /**
     * The Measure dimension.
     */
    MEASURE_DIMENSION("datastructure", "MeasureDimension", "Measure Dimension", DSD),
    /**
     * The Measure descriptor.
     */
    MEASURE_DESCRIPTOR("datastructure", "MeasureDescriptor", "Measure Descriptor", DSD, MeasureListBean.FIXED_ID),
    /**
     * The Primary measure.
     */
    PRIMARY_MEASURE("datastructure", "PrimaryMeasure", "Primary Measure", MEASURE_DESCRIPTOR, PrimaryMeasureBean.FIXED_ID),
    /**
     * The Time dimension.
     */
    TIME_DIMENSION("datastructure", "TimeDimension", "Time Dimension", DSD),

    /**
     * The Msd.
     */
//METADATA STRUCTURE
    MSD(MetadataStructureDefinitionBean.class, "metadatastructure", "MetadataStructure", "Metadata Structure"),
    /**
     * The Report structure.
     */
    REPORT_STRUCTURE("metadatastructure", "ReportStructure", "Report Structure", MSD),
    /**
     * The Metadata attribute.
     */
    METADATA_ATTRIBUTE("metadatastructure", "MetadataAttribute", "Metadata Attribute", REPORT_STRUCTURE),
    /**
     * The Metadata target.
     */
    METADATA_TARGET("metadatastructure", "MetadataTarget", "Metadata Target", MSD),
    /**
     * Metadata flow sdmx structure type.
     */
    METADATA_FLOW(MetadataFlowBean.class, "metadatastructure", "Metadataflow", "Metadataflow"),
    /**
     * The Identifiable object target.
     */
    IDENTIFIABLE_OBJECT_TARGET("metadatastructure", "IdentifiableObjectTarget", "Identifiable Object Target", METADATA_TARGET),
    /**
     * The Dataset target.
     */
    DATASET_TARGET("metadatastructure", "DataSetTarget", "Data Set Target", METADATA_TARGET),
    /**
     * The Constraint content target.
     */
    CONSTRAINT_CONTENT_TARGET("metadatastructure", "ConstraintContentTarget", "Constraint Content Set Target", METADATA_TARGET),

    /**
     * The Dimension descriptor values target.
     */
    DIMENSION_DESCRIPTOR_VALUES_TARGET("metadatastructure", "DimensionDescriptorValuesTarget", "Dimension Descriptor Values Target", METADATA_TARGET),
    /**
     * The Report period target.
     */
    REPORT_PERIOD_TARGET("metadatastructure", "ReportPeriodTarget", "Report Period Target", METADATA_TARGET),


    /**
     * Process sdmx structure type.
     */
//PROCESS
    PROCESS(ProcessBean.class, "process", "Process", "Process"),
    /**
     * The Process step.
     */
    PROCESS_STEP("process", "ProcessStep", "Process Step", PROCESS),
    /**
     * Transition sdmx structure type.
     */
    TRANSITION("process", "Transition", "Transition", PROCESS_STEP),

    /**
     * The Provision agreement.
     */
//REGISTRY
    PROVISION_AGREEMENT(ProvisionAgreementBean.class, "registry", "ProvisionAgreement", "Provision Agreement"),
    /**
     * Registration sdmx structure type.
     */
    REGISTRATION(RegistrationBean.class, "registry", "Registration", "Registration"),
    /**
     * Subscription sdmx structure type.
     */
    SUBSCRIPTION(SubscriptionBean.class, "registry", "Subscription", "Subscription"),
    /**
     * The Attachment constraint.
     */
    ATTACHMENT_CONSTRAINT(AttachmentConstraintBean.class, "registry", "AttachmentConstraint", "Attachment Constraint"),
    /**
     * The Content constraint.
     */
    CONTENT_CONSTRAINT(ContentConstraintBean.class, "registry", "ContentConstraint", "Content Constraint"),

    /**
     * The Structure set.
     */
//MAPPING
    STRUCTURE_SET(StructureSetBean.class, "mapping", "StructureSet", "Structure Set"),
    /**
     * The Structure map.
     */
    STRUCTURE_MAP("mapping", "StructureMap", "Structure Map", STRUCTURE_SET),
    /**
     * The Reporting taxonomy map.
     */
    REPORTING_TAXONOMY_MAP("mapping", "ReportingTaxonomyMap", "Reporting Taxonomy Map", STRUCTURE_SET),
    /**
     * The Representation map.
     */
    REPRESENTATION_MAP("mapping", "RepresentationMap", "Representation Map", STRUCTURE_SET, false),   //IMPORTANT WHAT IS THE OBJECT CLASS HERE?
    /**
     * The Category map.
     */
    CATEGORY_MAP("mapping", "CategoryMap", "Category Map", STRUCTURE_SET, false),
    /**
     * The Category scheme map.
     */
    CATEGORY_SCHEME_MAP("mapping", "CategorySchemeMap", "Category Scheme Map", STRUCTURE_SET),
    /**
     * The Concept scheme map.
     */
    CONCEPT_SCHEME_MAP("mapping", "ConceptSchemeMap", "Concept Scheme Map", STRUCTURE_SET),
    /**
     * The Code map.
     */
    CODE_MAP("mapping", "CodeMap", "Code Map", STRUCTURE_SET),
    /**
     * Code list map sdmx structure type.
     */
    CODE_LIST_MAP("mapping", "CodelistMap", "CodelistMap", STRUCTURE_SET),
    /**
     * The Component map.
     */
    COMPONENT_MAP("mapping", "ComponentMap", "Component Map", STRUCTURE_SET, false),
    /**
     * The Concept map.
     */
    CONCEPT_MAP("mapping", "ConceptMap", "Concept Map", STRUCTURE_SET),
    /**
     * The Organisation map.
     */
    ORGANISATION_MAP("mapping", "OrganisationMap", "Organisation Map", STRUCTURE_SET),
    /**
     * The Organisation scheme map.
     */
    ORGANISATION_SCHEME_MAP("mapping", "OrganisationSchemeMap", "Organisation Scheme Map", STRUCTURE_SET),
    /**
     * The Hybrid codelist map.
     */
    HYBRID_CODELIST_MAP("mapping", "HybridCodeListMap", "Hybrid Code List Map", STRUCTURE_SET),
    /**
     * The Hybrid code.
     */
    HYBRID_CODE("mapping", "HybridCodeMap", "Hybrid Code Map", STRUCTURE_SET);


    private static List<SDMX_STRUCTURE_TYPE> maintainableTypes = new ArrayList<SDMX_STRUCTURE_TYPE>();

    static {
        for (SDMX_STRUCTURE_TYPE currentStructureType : SDMX_STRUCTURE_TYPE.values()) {
            if (currentStructureType.isMaintainable) {
                maintainableTypes.add(currentStructureType);
            }
        }
    }

    private String v2package;
    private String v2Class;
    private String v2UrnPrefix;
    private String urnPackage;
    private String urnClass;
    private String urnPrefix;
    private String fixedId;
    private String type;
    private boolean isMaintainable;
    private boolean isIdentifiable;
    private SDMX_STRUCTURE_TYPE parentStructureType;
    private Class<? extends MaintainableBean> maintainableClass;

    private SDMX_STRUCTURE_TYPE(String type) {
        this.type = type;
    }

    /**
     * Maintainable Constructor (Minimal)
     *
     * @param urnPackage
     * @param urnClass
     * @param type
     */
    private SDMX_STRUCTURE_TYPE(Class<? extends MaintainableBean> maintClass,
                                String urnPackage,
                                String urnClass,
                                String type) {
        this(maintClass, null, null, urnPackage, urnClass, type);
    }

    /**
     * Maintainable Constructor (Full)
     *
     * @param v2package
     * @param v2Class
     * @param urnPackage
     * @param urnClass
     * @param type
     */
    private SDMX_STRUCTURE_TYPE(Class<? extends MaintainableBean> maintClass,
                                String v2package,
                                String v2Class,
                                String urnPackage,
                                String urnClass,
                                String type) {
        this(urnPackage, urnClass, type, null);
        this.maintainableClass = maintClass;
        this.v2Class = v2Class;
        this.v2package = v2package;
        this.v2UrnPrefix = "urn:sdmx:org.sdmx.infomodel." + v2package + "." + v2Class + "=";
    }

    private SDMX_STRUCTURE_TYPE(String urnPackage,
                                String urnClass,
                                String type,
                                SDMX_STRUCTURE_TYPE parentStructureType,
                                boolean isIdentifiable) {
        this.urnPackage = urnPackage;
        this.urnClass = urnClass;
        this.type = type;
        this.isMaintainable = parentStructureType == null;
        this.parentStructureType = parentStructureType;
        this.isIdentifiable = isIdentifiable;
        this.urnPrefix = "urn:sdmx:org.sdmx.infomodel." + urnPackage + "." + urnClass + "=";
    }

    private SDMX_STRUCTURE_TYPE(String urnPackage,
                                String urnClass,
                                String type,
                                SDMX_STRUCTURE_TYPE parentStructureType) {
        this(urnPackage, urnClass, type, parentStructureType, null);
    }

    private SDMX_STRUCTURE_TYPE(String urnPackage,
                                String urnClass,
                                String type,
                                SDMX_STRUCTURE_TYPE parentStructureType,
                                String fixedId) {
        this.urnPackage = urnPackage;
        this.urnClass = urnClass;
        this.type = type;
        this.isMaintainable = parentStructureType == null;
        this.parentStructureType = parentStructureType;
        this.isIdentifiable = true;
        this.fixedId = fixedId;
        this.urnPrefix = "urn:sdmx:org.sdmx.infomodel." + urnPackage + "." + urnClass + "=";
    }

    /**
     * Gets maintainable structure types.
     *
     * @return the maintainable structure types
     */
    public static List<SDMX_STRUCTURE_TYPE> getMaintainableStructureTypes() {
        return new ArrayList<SDMX_STRUCTURE_TYPE>(maintainableTypes);
    }

    /**
     * Parses a URN prefix (ignores case) to return the STRUCTURE_TYPE, throws an exception if the prefix does not belong to a structure type.
     * Accepts SDMX v2.0 prefixes and 2.1 prefixes.
     *
     * @param prefix the prefix
     * @return sdmx structure type
     * @throws IllegalArgumentException if no SDMX_STRUCTURE_TYPE can be found with the given prefix
     */
    public static SDMX_STRUCTURE_TYPE parsePrefix(String prefix) {
        if (!prefix.endsWith("=")) {
            prefix += "=";
        }
        for (SDMX_STRUCTURE_TYPE currentType : SDMX_STRUCTURE_TYPE.values()) {
            if (currentType.getUrnPrefix() != null) {
                if (currentType.getUrnPrefix().equalsIgnoreCase(prefix)) {
                    return currentType;
                }
            }
        }
        for (SDMX_STRUCTURE_TYPE currentType : SDMX_STRUCTURE_TYPE.values()) {
            if (currentType.v2UrnPrefix != null) {
                if (currentType.v2UrnPrefix.equalsIgnoreCase(prefix)) {
                    return currentType;
                }
            }
        }
        throw new SdmxSyntaxException(ExceptionCode.STRUCTURE_URN_MALFORMED_UNKOWN_PREFIX, prefix);
    }

    /**
     * Parse class sdmx structure type.
     *
     * @param <T>            the type parameter
     * @param structureClass the structure class
     * @return the sdmx structure type
     */
    public static <T extends MaintainableBean> SDMX_STRUCTURE_TYPE parseClass(Class<T> structureClass) {
        for (SDMX_STRUCTURE_TYPE type : getMaintainableStructureTypes()) {
            if (type.getMaintainableInterface() == structureClass) {
                return type;
            }
        }
        throw new SdmxSyntaxException("Unknown Maintianable Type:" + structureClass);
    }

    /**
     * Parses a string representation of the Object Class (ignores case) to return the Structure Type.
     * Accepts SDMX v2.0 Class and 2.1 Class.
     * Example : Metadataflow would return SDMX_STRUCTURE_TYPE.METADATA_FLOW.
     *
     * @param structureClass the structure class
     * @return sdmx structure type
     * @throws IllegalArgumentException if no SDMX_STRUCTURE_TYPE can be found
     */
    public static SDMX_STRUCTURE_TYPE parseClass(String structureClass) {
        if (structureClass == null) {
            throw new IllegalArgumentException("SDMX_STRUCTURE_TYPE.parseClass(..) can not be passed a null value");
        }
        for (SDMX_STRUCTURE_TYPE currentType : SDMX_STRUCTURE_TYPE.values()) {
            if (currentType.isIdentifiable) {
                if (currentType.getUrnClass().equalsIgnoreCase(structureClass)) {
                    return currentType;
                }
                if (currentType.v2Class != null && currentType.v2Class.equalsIgnoreCase(structureClass)) {
                    return currentType;
                }
            }
        }

        if (structureClass.equals("Attribute")) {
            return SDMX_STRUCTURE_TYPE.DATA_ATTRIBUTE;
        }
        throw new IllegalArgumentException("Could not find structure type with class '" + structureClass + "'");
    }

    /**
     * Parses a string representation of the Object Class (ignores case) and package (ignores case) to return the Structure Type.
     * Accepts SDMX v2.0 and 2.1 package and class.
     *
     * @param structurePackage the structure package
     * @param structureClass   the structure class
     * @return sdmx structure type
     * @throws IllegalArgumentException if no SDMX_STRUCTURE_TYPE can be found
     */
    public static SDMX_STRUCTURE_TYPE parsePackageAndClass(String structurePackage, String structureClass) {

        if (structureClass == null) {
            throw new IllegalArgumentException("SDMX_STRUCTURE_TYPE.parsePackageAndClass(..) can not be passed a null value");
        }

        for (SDMX_STRUCTURE_TYPE currentType : SDMX_STRUCTURE_TYPE.values()) {
            if (currentType.isIdentifiable) {
                if (currentType.getUrnClass().equals(structureClass) &&
                        currentType.getUrnPackage().equals(structurePackage)) {
                    return currentType;
                }
                if (currentType.v2Class != null && currentType.v2package != null) {
                    if (currentType.v2Class.equals(structureClass) &&
                            currentType.v2package.equals(structurePackage)) {
                        return currentType;
                    }
                }
            }
        }
        if (structureClass.equals("Attribute")) {
            return SDMX_STRUCTURE_TYPE.DATA_ATTRIBUTE;
        }
        throw new IllegalArgumentException("Could not find structure type with package '" + structurePackage + "' and class '" + structureClass + "'");
    }

    /**
     * Gets maintainable interface.
     *
     * @return the maintainable interface
     */
    public Class<? extends MaintainableBean> getMaintainableInterface() {
        SDMX_STRUCTURE_TYPE maintType = getMaintainableStructureType();
        return maintType == null ? null : maintType.maintainableClass;
    }

    /**
     * returns the Maintainable Structure Type for this structure type
     *
     * @return maintainable structure type
     */
    public SDMX_STRUCTURE_TYPE getMaintainableStructureType() {
        SDMX_STRUCTURE_TYPE type = this;

        while (type != null) {
            if (type.isMaintainable) {
                return type;
            }
            type = type.getParentStructureType();
        }
        return null;
    }

    /**
     * Gets urn prefix.
     *
     * @return the urn prefix
     */
    public String getUrnPrefix() {
        return urnPrefix;
    }

    /**
     * Gets fixed id.
     *
     * @return the fixed id
     */
    public String getFixedId() {
        return fixedId;
    }

    /**
     * Has fixed id boolean.
     *
     * @return the boolean
     */
    public boolean hasFixedId() {
        return fixedId != null;
    }

    /**
     * Generates a URN with the given agencyId, maintainable Id, and version, and additional identifiers (if this is not a maintainable type)
     *
     * @param agencyId        required
     * @param maintId         required
     * @param version         defaults to MaintainableBean.DEFAULT_VERSION
     * @param identifiableIds required if this is an identifiable structure, otherwise not required
     * @return SDMX urn
     * @throws IllegalArgumentException if any required arguments are missing, or if identifiable arguments are supplied if this is a maintainable type
     */
    public String generateUrn(String agencyId, String maintId, String version, String... identifiableIds) {
        if (agencyId == null || agencyId.length() == 0) {
            throw new IllegalArgumentException("Generate URN missing agencyId");
        }
        if (maintId == null || maintId.length() == 0) {
            throw new IllegalArgumentException("Generate URN missing maintId");
        }
        if (version == null || version.length() == 0) {
            version = MaintainableBean.DEFAULT_VERSION;
        }

        if (this == SDMX_STRUCTURE_TYPE.AGENCY && identifiableIds != null) {
            if (agencyId.equals(AgencySchemeBean.DEFAULT_SCHEME)) {
                return getUrnPrefix() + identifiableIds[0];
            }
            String id = "";
            for (String currentIdentId : identifiableIds) {
                id += "." + currentIdentId;
            }
            return getUrnPrefix() + agencyId + id;
        }

        if (identifiableIds != null && identifiableIds.length > 0) {
            if (this.isMaintainable) {
                throw new IllegalArgumentException("Generate maintainable URN given too many args (given identifiable ids) ");
            }
        }
        if (!isMaintainable) {
            if (identifiableIds == null || identifiableIds.length == 0) {
                throw new IllegalArgumentException("Generate identifiable URN missing required identifiable id");
            }
        }
        String returnString = getUrnPrefix() + agencyId + ":" + maintId + "(" + version + ")";
        if (identifiableIds != null) {
            for (String currentIdentId : identifiableIds) {
                returnString += "." + currentIdentId;
            }
        }

        return returnString;
    }

    /**
     * Returns the parent structure type, returns null if this is a maintainable type.
     *
     * @return the parent structure type
     */
    public SDMX_STRUCTURE_TYPE getParentStructureType() {
        return parentStructureType;
    }

    /**
     * Returns the minimum nested depth this structure is from the maintainable parent, 0 indexed.
     *
     * @return nested depth
     */
    public int getNestedDepth() {
        SDMX_STRUCTURE_TYPE currentParent = getParentStructureType();
        int i = 0;
        if (currentParent != null) {
            while (!currentParent.isMaintainable()) {
                i++;
                currentParent = currentParent.getParentStructureType();
            }
        }
        return i;
    }

    /**
     * Returns the URN Class as specified in SDMX 2.1  (example Metadataflow, DataStructure).
     *
     * @return urn class
     */
    public String getUrnClass() {
        return urnClass;
    }

    /**
     * Returns the URN package (example 'mapping').
     *
     * @return urn package
     */
    public String getUrnPackage() {
        return urnPackage;
    }

    /**
     * Returns a human readable type (example 'Concept Map').
     *
     * @return type type
     */
    public String getType() {
        return type;
    }

    /**
     * Returns true if this STRUCTURE_TYPE is representing a maintainable artifact.
     *
     * @return the boolean
     */
    public boolean isMaintainable() {
        return isMaintainable;
    }

    /**
     * Returns true if this STRUCTURE_TYPE is representing an identifiable artifact (will be true if isMaintainable is true).
     *
     * @return true if this STRUCTURE_TYPE is representing an identifiable artifact (will be true if isMaintainable is true).
     */
    public boolean isIdentifiable() {
        return isIdentifiable;
    }

    /**
     * Returns getType()
     */
    @Override
    public String toString() {
        return type;
    }
}
