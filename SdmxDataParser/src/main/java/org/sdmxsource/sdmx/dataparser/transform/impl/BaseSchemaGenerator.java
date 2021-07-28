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
package org.sdmxsource.sdmx.dataparser.transform.impl;

import org.sdmxsource.sdmx.api.constants.*;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.model.beans.base.TextFormatBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.AttributeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.GroupBean;
import org.sdmxsource.sdmx.api.model.superbeans.base.ComponentSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodeSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodelistSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.AttributeSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DataStructureSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DimensionSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.GroupSuperBean;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.OutputStream;
import java.util.*;

/**
 * Abstract class which contains the code necessary for writing information common to the various XML schema types.
 */
public abstract class BaseSchemaGenerator {
    /**
     * The constant SCHEMA_NS.
     */
    public static final String SCHEMA_NS = "http://www.w3.org/2001/XMLSchema";
    /**
     * The constant COMPACT.
     */
    public static final int COMPACT = 1;
    /**
     * The constant UTILITY.
     */
    public static final int UTILITY = 2;
    /**
     * The Compact ns.
     */
    public String COMPACT_NS;
    /**
     * The Common ns.
     */
    public String COMMON_NS;
    /**
     * The Utility ns.
     */
    public String UTILITY_NS;
    /**
     * The Message ns.
     */
    public String MESSAGE_NS;
    /**
     * The Schema location.
     */
    protected String schemaLocation;
    /**
     * The Valid code map.
     */
//A code map containing
    protected Map<String, Set<String>> validCodeMap;
    /**
     * The Dsd.
     */
    DataStructureSuperBean dsd;
    /**
     * The Writer.
     */
    XMLStreamWriter writer;
    private SDMX_SCHEMA targetSchemaVersion;

    //	//By default the type name for a sinmpleType defining a Codelist id the codelist Id.  However,
    //	private Map<String, String> componetCodelistIdToTypeName = new HashMap<String, String>();

    /**
     * Instantiates a new Base schema generator.
     *
     * @param validCodeMap the valid code map
     */
    public BaseSchemaGenerator(Map<String, Set<String>> validCodeMap) {
        this.validCodeMap = validCodeMap;
    }

    /**
     * Init method output the various namespaces and schemas at the start of the XML document
     *
     * @param out                 the out
     * @param xsdType             the xsd type
     * @param targetNamespace     the target namespace
     * @param targetSchemaVersion the target schema version
     * @param keyFamily           the key family
     */
    protected void init(OutputStream out, int xsdType, String targetNamespace, SDMX_SCHEMA targetSchemaVersion, DataStructureSuperBean keyFamily) {
        this.targetSchemaVersion = targetSchemaVersion;
        switch (targetSchemaVersion) {
            case VERSION_ONE:
                COMPACT_NS = SdmxConstants.COMPACT_NS_1_0;
                COMMON_NS = SdmxConstants.COMMON_NS_1_0;
                UTILITY_NS = SdmxConstants.UTILITY_NS_1_0;
                MESSAGE_NS = SdmxConstants.MESSAGE_NS_1_0;
                break;
            case VERSION_TWO:
                COMPACT_NS = SdmxConstants.COMPACT_NS_2_0;
                COMMON_NS = SdmxConstants.COMMON_NS_2_0;
                UTILITY_NS = SdmxConstants.UTILITY_NS_2_0;
                MESSAGE_NS = SdmxConstants.MESSAGE_NS_2_0;
                break;
            case VERSION_TWO_POINT_ONE:
                COMPACT_NS = SdmxConstants.COMPACT_NS_2_1;
                COMMON_NS = SdmxConstants.COMMON_NS_2_1;
                MESSAGE_NS = SdmxConstants.STRUCTURE_SPECIFIC_NS_2_1;
                break;
            default:
                throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, targetSchemaVersion);
        }

        if (!keyFamily.getBuiltFrom().isCompatible(targetSchemaVersion)) {
            String errorString = "The Data Structure '" + keyFamily.getName() + "' is not compatible with the target schema version: " + targetSchemaVersion;
            throw new IllegalArgumentException(errorString);
        }

        if (targetNamespace == null) {
            targetNamespace = "undefined";
        }

        this.dsd = keyFamily;
        try {
            XMLOutputFactory xmlOutputfactory = XMLOutputFactory.newInstance();

            writer = xmlOutputfactory.createXMLStreamWriter(out, "UTF-8");
            writer.writeStartDocument();

            writer.writeStartElement("xs", "schema", SCHEMA_NS);    // Start Schema - not ended till end of document

            if (targetSchemaVersion == SDMX_SCHEMA.VERSION_TWO_POINT_ONE) {
                writer.writeAttribute("xmlns:dsd", "http://www.sdmx.org/resources/sdmxml/schemas/v2_1/data/structurespecific");
            }

            writer.writeDefaultNamespace(targetNamespace);
            writer.writeNamespace("xs", SCHEMA_NS);

            switch (xsdType) {
                case COMPACT:
                    writer.writeNamespace("compact", COMPACT_NS);
                    break;
                case UTILITY:
                    writer.writeNamespace("utility", UTILITY_NS);
                    break;
            }

            writer.writeNamespace("common", COMMON_NS);
            writer.writeNamespace("message", MESSAGE_NS);
            writer.writeNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
            writer.writeAttribute("targetNamespace", targetNamespace);
            writer.writeAttribute("elementFormDefault", "qualified");
            writer.writeAttribute("attributeFormDefault", "unqualified");

            //IMPORT SCHEMAS
            writer.writeStartElement(SCHEMA_NS, "import");      // Start Import
            writer.writeAttribute("namespace", COMMON_NS);

            if (schemaLocation == null) {
                schemaLocation = "";
            }
            writer.writeAttribute("schemaLocation", schemaLocation + "SDMXCommon.xsd");

            //String schema = MESSAGE_NS + " " + schemaLocation+"SDMXMessage.xsd";

            writer.writeEndElement();                            // End Import

            if (targetSchemaVersion.equals(SDMX_SCHEMA.VERSION_TWO_POINT_ONE)) {
                writer.writeStartElement(SCHEMA_NS, "import");  // Start Import
                writer.writeAttribute("namespace", MESSAGE_NS);
                writer.writeAttribute("schemaLocation", schemaLocation + "SDMXDataStructureSpecific.xsd");
                writer.writeEndElement();                       // End Import
            } else {
                writer.writeStartElement(SCHEMA_NS, "import");  // Start Import
                switch (xsdType) {
                    case COMPACT:
                        writer.writeAttribute("namespace", COMPACT_NS);
                        writer.writeAttribute("schemaLocation", schemaLocation + "SDMXCompactData.xsd");
                        break;
                    case UTILITY:
                        writer.writeAttribute("namespace", UTILITY_NS);
                        writer.writeAttribute("schemaLocation", schemaLocation + "SDMXUtilityData.xsd");
                        break;
                }

                writer.writeEndElement();                      // End Import

                writer.writeStartElement(SCHEMA_NS, "import");    // Start Import
                writer.writeAttribute("namespace", MESSAGE_NS);
                writer.writeAttribute("schemaLocation", schemaLocation + "SDMXMessage.xsd");
                writer.writeEndElement();                         // End Import
            }


            // Components
            Set<String> processed = new HashSet<String>();
            for (ComponentSuperBean csb : keyFamily.getComponents()) {
                if (csb.getCodelist(true) != null) {
                    // Coded
                    CodelistSuperBean cl = csb.getCodelist(true);
                    if (processed.contains(cl.getUrn())) {
                        continue;
                    }
                    processed.add(cl.getUrn());
                    processCodelist(csb.getId(), cl);
                } else {
                    TextFormatBean tf = csb.getTextFormat();
                    if (tf != null && tf.getTextType() != null) {
                        processUncodedWithRestriction(csb.getId(), tf);
                    }
                }
            }
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    /**
     * Returns true if the target schema version is 1.0 or 2.0 and the attachment level is to a group of dimensions, which is also already defined by a group
     *
     * @param attr the attr
     * @return boolean boolean
     */
    boolean skipAttribute(AttributeBean attr) {
        if (targetSchemaVersion == SDMX_SCHEMA.VERSION_ONE || targetSchemaVersion == SDMX_SCHEMA.VERSION_TWO) {
            if (attr.getAttachmentLevel() == ATTRIBUTE_ATTACHMENT_LEVEL.DIMENSION_GROUP) {
                //If the group of dimensions is also a group, do not create the attribute;
                List<String> dimensionReferences = attr.getDimensionReferences();
                for (GroupBean grp : dsd.getBuiltFrom().getGroups()) {
                    if (grp.getDimensionRefs().containsAll(dimensionReferences) &&
                            dimensionReferences.containsAll(grp.getDimensionRefs())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Write group attributes.
     *
     * @param group the group
     * @throws XMLStreamException the xml stream exception
     */
    void writeGroupAttributes(GroupSuperBean group) throws XMLStreamException {
        for (DimensionSuperBean dim : group.getDimensions()) {
            writer.writeStartElement(SCHEMA_NS, "attribute");
            writer.writeAttribute("name", dim.getConcept().getId());
            if (dim.getCodelist(true) != null) {
                writer.writeAttribute("type", getCodelistReference(dim.getCodelist(true)));
            } else {
                writer.writeAttribute("type", dim.getId());
//				writer.writeAttribute("type", "xs:string");
            }
            writer.writeAttribute("use", "required");
            writer.writeEndElement();   // End Attribute
        }

        //For all versions attach any dimension group attributes to the group
        for (AttributeSuperBean bean : dsd.getGroupAttributes(group.getId(), true)) {
            addAttributeBean(bean);
        }
    }

    /**
     * Add attribute bean.
     *
     * @param attributeBean the attribute bean
     * @throws XMLStreamException the xml stream exception
     */
    void addAttributeBean(AttributeSuperBean attributeBean) throws XMLStreamException {
        addComponentBean(attributeBean);
        if (attributeBean.getAssignmentStatus().equals("Mandatory")) {
            writer.writeAttribute("use", "required");
        } else {
            writer.writeAttribute("use", "optional");
        }
        writer.writeEndElement();   // End Attribute
    }

    /**
     * Add component bean.
     *
     * @param componentBean the component bean
     * @throws XMLStreamException the xml stream exception
     */
    void addComponentBean(ComponentSuperBean componentBean) throws XMLStreamException {
        writer.writeStartElement(SCHEMA_NS, "attribute");
        if (targetSchemaVersion == SDMX_SCHEMA.VERSION_TWO_POINT_ONE) {
            writer.writeAttribute("name", componentBean.getId());
        } else {
            writer.writeAttribute("name", componentBean.getConcept().getId());
        }
        if (componentBean.getBuiltFrom().getStructureType() == SDMX_STRUCTURE_TYPE.TIME_DIMENSION) {
            writer.writeAttribute("type", determineSchemaType(TEXT_TYPE.OBSERVATIONAL_TIME_PERIOD));//not in v2 or 1
        } else if (componentBean.getCodelist(true) == null) {
            String attrType = determineAttributeType(componentBean);
            writer.writeAttribute("type", attrType);
        } else {
            writer.writeAttribute("type", getCodelistReference(componentBean.getCodelist(true)));
        }

    }

    private String determineAttributeType(ComponentSuperBean componentBean) {
        // FR-772: Any attribute with a text format, use it's Id
        if (componentBean.getTextFormat() != null) {
//		if(componentBean.getTextFormat() != null && componentBean.getTextFormat().hasRestrictions()) {
            return componentBean.getId();
        }
        return "xs:string";
    }

    /**
     * Determine schema type string.
     *
     * @param textType the text type
     * @return the string
     */
    protected String determineSchemaType(TEXT_TYPE textType) {
        String attrType;
        switch (textType) {
            case ALPHA:
                attrType = "common:AlphaType";
                break;
            case ATTACHMENT_CONSTRAINT_REFERENCE:
                attrType = "common:AttachmentConstraintReferenceType";
                break;
            case ALPHA_NUMERIC:
                attrType = "common:AlphaNumericType";
                break;
            case BASIC_TIME_PERIOD:
                attrType = "common:BasicTimePeriodType";
                break;
            case BIG_INTEGER:
                attrType = "xs:integer";
                break;
            case BOOLEAN:
                attrType = "xs:boolean";
                break;
            case COUNT:
                attrType = "xs:integer";
                break;
            case DATA_SET_REFERENCE:
                attrType = "common:DataSetReferenceType";
                break;
            case DATE:
                attrType = "xs:date";
                break;
            case DATE_TIME:
                attrType = "xs:dateTime";
                break;
            case DAY:
                attrType = "xs:g*";
                break;
            case DECIMAL:
                attrType = "xs:decimal";
                break;
            case DOUBLE:
                attrType = "xs:double";
                break;
            case DURATION:
                attrType = "xs:duration";
                break;
            case EXCLUSIVE_VALUE_RANGE:
                attrType = "xs:decimal";
                break;
            case FLOAT:
                attrType = "xs:float";
                break;
            case GREGORIAN_DAY:
                attrType = "xs:date";
                break;
            case GREGORIAN_TIME_PERIOD:
                attrType = "common:GregorianTimePeriodType";
                break;
            case GREGORIAN_YEAR:
                attrType = "xs:gYear";
                break;
            case GREGORIAN_YEAR_MONTH:
                attrType = "xs:gYearMonth";
                break;
            case IDENTIFIABLE_REFERENCE:
                // TODO
                attrType = "????";
                break;
            case INCLUSIVE_VALUE_RANGE:
                attrType = "xs:decimal";
                break;
            case INCREMENTAL:
                attrType = "xs:decimal";
                break;
            case INTEGER:
                attrType = "xs:int";
                break;
            case KEY_VALUES:
                attrType = "common:DataKeyType";
                break;
            case LONG:
                attrType = "xs:long";
                break;
            case MONTH:
                attrType = "xs:g*";
                break;
            case MONTH_DAY:
                attrType = "xs:g*";
                break;
            case NUMERIC:
                attrType = "common:NumericType";
                break;
            case OBSERVATIONAL_TIME_PERIOD:
                if (targetSchemaVersion == SDMX_SCHEMA.VERSION_TWO_POINT_ONE) {
                    attrType = "common:ObservationalTimePeriodType";
                } else {
                    attrType = "common:TimePeriodType";
                }
                break;
            case REPORTING_DAY:
                attrType = "common:ReportingDayType";
                break;
            case REPORTING_MONTH:
                attrType = "common:ReportingMonthType";
                break;
            case REPORTING_QUARTER:
                attrType = "common:ReportingQuarterType";
                break;
            case REPORTING_SEMESTER:
                attrType = "common:ReportingSemesterType";
                break;
            case REPORTING_TIME_PERIOD:
                attrType = "common:ReportingTimePeriodType";
                break;
            case REPORTING_TRIMESTER:
                attrType = "common:ReportingTrimesterType";
                break;
            case REPORTING_WEEK:
                attrType = "common:ReportingWeekType";
                break;
            case REPORTING_YEAR:
                attrType = "common:ReportingYearType";
                break;
            case SHORT:
                attrType = "xs:short";
                break;
            case STANDARD_TIME_PERIOD:
                attrType = "common:StandardTimePeriodType";
                break;
            case STRING:
                attrType = "xs:string";
                break;
            case TIME:
                attrType = "time";
                break;
            case TIMESPAN:
                attrType = "xs:duration";
                break;
            case TIMES_RANGE:
                attrType = "common:TimeRangeType";
                break;
            case TIME_PERIOD:
                attrType = "common:TimePeriodRangeType";
                break;
            case URI:
                attrType = "xs:anyURI";
                break;
            case XHTML:
                attrType = "common:StructuredText";
                break;
            case YEAR:
                attrType = "xsd:g*";
                break;
            case YEAR_MONTH:
                attrType = "xsd:g*";
                break;
            default:
                attrType = "xs:string";
        }
        return attrType;
    }

    /**
     * Add text format.
     *
     * @param tx the tx
     * @throws XMLStreamException the xml stream exception
     */
    void addTextFormat(TextFormatBean tx) throws XMLStreamException {
        if (tx.getDecimals() != null) {
            writer.writeStartElement(SCHEMA_NS, "fractionDigits");
            writer.writeAttribute("value", tx.getDecimals().toString());
            writer.writeEndElement();
        }
        if (tx.getEndTime() != null) {
            // The restriction type must be xs:date
            writer.writeStartElement(SCHEMA_NS, "maxInclusive");
            writer.writeAttribute("value", tx.getEndTime().toString());
            writer.writeEndElement();
        }
        if (tx.getEndValue() != null) {
            writer.writeStartElement(SCHEMA_NS, "maxInclusive");
            writer.writeAttribute("value", tx.getEndValue().toString());
            writer.writeEndElement();
        }
        if (tx.getInterval() != null) {
        }
        if (tx.getMaxLength() != null) {
            //FR-772 The generated XSD in the Global Registry are not valid with regards to the schema standard (A double can only have a max value, not a max length.)
            if (tx == null ||
                    tx.getTextType() == TEXT_TYPE.STRING ||
                    tx.getTextType() == TEXT_TYPE.ALPHA ||
                    tx.getTextType() == TEXT_TYPE.ALPHA_NUMERIC) {
                writer.writeStartElement(SCHEMA_NS, "maxLength");
                writer.writeAttribute("value", tx.getMaxLength().toString());
                writer.writeEndElement();
            }
        }
        if (tx.getMaxValue() != null) {
            writer.writeStartElement(SCHEMA_NS, "maxInclusive");
            writer.writeAttribute("value", tx.getMaxValue().toString());
            writer.writeEndElement();
        }

        if (tx.getMinLength() != null) {
            //FR-772 The generated XSD in the Global Registry are not valid with regards to the schema standard (A double can only have a max value, not a max length.)
            if (tx == null ||
                    tx.getTextType() == TEXT_TYPE.STRING ||
                    tx.getTextType() == TEXT_TYPE.ALPHA ||
                    tx.getTextType() == TEXT_TYPE.ALPHA_NUMERIC) {
                writer.writeStartElement(SCHEMA_NS, "minLength");
                writer.writeAttribute("value", tx.getMinLength().toString());
                writer.writeEndElement();
            }
        }
        if (tx.getMinValue() != null) {
            writer.writeStartElement(SCHEMA_NS, "minInclusive");
            writer.writeAttribute("value", tx.getMinValue().toString());
            writer.writeEndElement();
        }
        if (tx.getPattern() != null) {
            writer.writeStartElement(SCHEMA_NS, "pattern");
            writer.writeAttribute("value", tx.getPattern());
            writer.writeEndElement();
        }
        if (tx.getStartTime() != null) {
            // The restriction type must be xs:date
            writer.writeStartElement(SCHEMA_NS, "minInclusive");
            writer.writeAttribute("value", tx.getStartTime().toString());
            writer.writeEndElement();
        }
        if (tx.getStartValue() != null) {
            writer.writeStartElement(SCHEMA_NS, "minInclusive");
            writer.writeAttribute("value", tx.getStartValue().toString());
            writer.writeEndElement();
        }
        if (tx.getTimeInterval() != null) {
        }
    }

    /**
     * Add annotations.
     *
     * @throws XMLStreamException the xml stream exception
     */
    void addAnnotations() throws XMLStreamException {
        writer.writeStartElement(SCHEMA_NS, "element");
        writer.writeAttribute("name", "Annotations");
        writer.writeAttribute("type", "common:AnnotationsType");
        writer.writeAttribute("minOccurs", "0");
        writer.writeEndElement();   // End Element
    }

    /**
     * Add choice.
     *
     * @param xsdType   the xsd type
     * @param minOccurs the min occurs
     * @param maxOccurs the max occurs
     * @throws XMLStreamException the xml stream exception
     */
    void addChoice(int xsdType, String minOccurs, String maxOccurs) throws XMLStreamException {
        writer.writeStartElement(SCHEMA_NS, "choice");
        // Only need "minOccurs" if Compact, not Utility
        if (xsdType == COMPACT)
            writer.writeAttribute("minOccurs", minOccurs);
        writer.writeAttribute("maxOccurs", maxOccurs);
    }

    /**
     * Add complex type.
     *
     * @param name              the name
     * @param extensionBaseName the extension base name
     * @throws XMLStreamException the xml stream exception
     */
    void addComplexType(String name, String extensionBaseName) throws XMLStreamException {
        writer.writeStartElement(SCHEMA_NS, "complexType");
        writer.writeAttribute("name", name);

        writer.writeStartElement(SCHEMA_NS, "complexContent");
        writer.writeStartElement(SCHEMA_NS, "extension");
        writer.writeAttribute("base", extensionBaseName);
    }

    /**
     * Add complex type for restriction.
     *
     * @param name                the name
     * @param restrictionBaseName the restriction base name
     * @throws XMLStreamException the xml stream exception
     */
    void addComplexTypeForRestriction(String name, String restrictionBaseName) throws XMLStreamException {
        writer.writeStartElement(SCHEMA_NS, "complexType");
        writer.writeAttribute("name", name);

        writer.writeStartElement(SCHEMA_NS, "complexContent");
        writer.writeStartElement(SCHEMA_NS, "restriction");
        writer.writeAttribute("base", restrictionBaseName);
    }

    /**
     * End complex type.
     *
     * @param writer the writer
     * @throws XMLStreamException the xml stream exception
     */
    void endComplexType(XMLStreamWriter writer) throws XMLStreamException {
        writer.writeEndElement();    // End extension
        writer.writeEndElement();    // End ComplexContent
        writer.writeEndElement();   // End ComplexType
    }

    /**
     * Add element.
     *
     * @param name the name
     * @param type the type
     * @throws XMLStreamException the xml stream exception
     */
    void addElement(String name, String type) throws XMLStreamException {
        writer.writeStartElement(SCHEMA_NS, "element");
        writer.writeAttribute("name", name);
        writer.writeAttribute("type", type);
        writer.writeEndElement();
    }

    /**
     * Add element.
     *
     * @param name              the name
     * @param type              the type
     * @param substitutionGroup the substitution group
     * @throws XMLStreamException the xml stream exception
     */
    void addElement(String name, String type, String substitutionGroup) throws XMLStreamException {
        writer.writeStartElement(SCHEMA_NS, "element");
        writer.writeAttribute("name", name);
        writer.writeAttribute("type", type);
        if (substitutionGroup != null) {
            writer.writeAttribute("substitutionGroup", substitutionGroup);
        }
        writer.writeEndElement();
    }

    /**
     * Add element.
     *
     * @param componentBean the component bean
     * @throws XMLStreamException the xml stream exception
     */
    void addElement(ComponentSuperBean componentBean) throws XMLStreamException {
        writer.writeStartElement(SCHEMA_NS, "element");
        writer.writeAttribute("name", componentBean.getConcept().getId());
        if (componentBean.getCodelist(true) == null) {
            //if(componentBean instanceof TimeDimensionBean) {
            //writer.writeAttribute("type", "TIMEType");
            //} else {
            writer.writeAttribute("type", "xs:string");
            //{
        } else {
            writer.writeAttribute("type", getCodelistReference(componentBean.getCodelist(true)));
        }

        if (componentBean instanceof AttributeSuperBean) {
            if (((AttributeSuperBean) componentBean).isMandatory()) {
                writer.writeAttribute("use", "required");
            } else
                writer.writeAttribute("use", "optional");
        }
        writer.writeEndElement();   // End Attribute
    }

    /**
     * Process codelist.
     *
     * @param componentId the component id
     * @param codelist    the codelist
     * @throws XMLStreamException the xml stream exception
     */
    void processCodelist(String componentId, CodelistSuperBean codelist) throws XMLStreamException {
        writer.writeStartElement(SCHEMA_NS, "simpleType");
        writer.writeAttribute("name", getCodelistReference(codelist));
        writer.writeStartElement(SCHEMA_NS, "restriction");
        writer.writeAttribute("base", "xs:string");
        for (CodeSuperBean code : codelist.getCodes()) {
            processCode(componentId, code);
        }
        writer.writeEndElement();    // End restriction
        writer.writeEndElement();    // End simpleType
    }

    /**
     * Process uncoded with restriction.
     *
     * @param componentId the component id
     * @param tx          the tx
     * @throws XMLStreamException the xml stream exception
     */
    void processUncodedWithRestriction(String componentId, TextFormatBean tx) throws XMLStreamException {
        if (tx == null || tx.getTextType() == null) {
            return;
        }
        writer.writeStartElement(SCHEMA_NS, "simpleType");
        writer.writeAttribute("name", componentId);
        writer.writeStartElement(SCHEMA_NS, "restriction");
        writer.writeAttribute("base", determineSchemaType(tx.getTextType()));
        addTextFormat(tx);
        writer.writeEndElement();    // End restriction
        writer.writeEndElement();    // End simpleType
    }

    /**
     * Gets codelist reference.
     *
     * @param codelist the codelist
     * @return the codelist reference
     */
    protected String getCodelistReference(CodelistSuperBean codelist) {
        String version = codelist.getVersion().replaceAll("\\.", "_");
        return codelist.getAgencyId() + "." + codelist.getId() + "." + version;
    }

    /**
     * Process code.
     *
     * @param componentId the component id
     * @param code        the code
     * @throws XMLStreamException the xml stream exception
     */
    void processCode(String componentId, CodeSuperBean code) throws XMLStreamException {
        boolean validForOutput = true;
        if (validCodeMap != null) {
            Set<String> validCodes = validCodeMap.get(componentId);
            if (validCodes != null) {
                validForOutput = validCodes.contains(code.getId());
            }
        }

        if (validForOutput) {
            writer.writeStartElement(SCHEMA_NS, "enumeration");
            writer.writeAttribute("value", code.getId());
            writer.writeStartElement(SCHEMA_NS, "annotation");
            for (Locale locale : code.getDescriptions().keySet()) {
                writer.writeStartElement(SCHEMA_NS, "documentation");
                writer.writeAttribute("xml:lang", locale.getLanguage());
                writer.writeCharacters(code.getDescriptions().get(locale));
                writer.writeEndElement();    // End Documentation
            }
            writer.writeEndElement();  // End Annotation
            writer.writeEndElement();  // End Enumeration
        }

        if (code.hasChildren()) {
            for (CodeSuperBean child : code.getChildren()) {
                processCode(componentId, child);
            }
        }
    }

    /**
     * Close.
     */
    void close() {
        try {
            if (writer != null) {
                writer.writeEndDocument();
                writer.flush();
                writer.close();
            }
        } catch (XMLStreamException e) {
            throw new RuntimeException("Error trying to close parser : " + e);
        }
    }
}
