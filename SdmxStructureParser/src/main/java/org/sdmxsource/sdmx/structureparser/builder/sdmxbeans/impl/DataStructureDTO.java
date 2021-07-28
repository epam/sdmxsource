package org.sdmxsource.sdmx.structureparser.builder.sdmxbeans.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.Map;

/**
 * The type Data structure dto.
 */
// @MS solely for JSON deserialization purpose
// could be replaced with manual node parsing
public class DataStructureDTO {
    /**
     * The Id.
     */
    public String id;

    /**
     * The Urn.
     */
    public String urn;

    /**
     * The Links.
     */
    public DataStructureDTO.Link[] links;

    /**
     * The Version.
     */
    public String version;

    /**
     * The Agency id.
     */
    public String agencyID;

    /**
     * The Valid from.
     */
    public Instant validFrom;

    /**
     * The Valid to.
     */
    public Instant validTo;

    /**
     * The Name.
     */
    public String name;

    /**
     * The Names.
     */
    public Map<String, String> names;

    /**
     * The Description.
     */
    public String description;

    /**
     * The Descriptions.
     */
    public Map<String, String> descriptions;

    /**
     * The Data structure components.
     */
    public DataStructureDTO.Datastructurecomponents dataStructureComponents;

    /**
     * The Annotations.
     */
    public DataStructureDTO.Annotation[] annotations;

    /**
     * The Is final.
     */
    public Boolean isFinal;

    /**
     * The Is external reference.
     */
    public Boolean isExternalReference;

    /**
     * The type Datastructurecomponents.
     */
    public static class Datastructurecomponents {
        /**
         * The Attribute list.
         */
        public DataStructureDTO.Attributelist attributeList;

        /**
         * The Dimension list.
         */
        public DataStructureDTO.Dimensionlist dimensionList;

        /**
         * The Measure list.
         */
        public DataStructureDTO.Measurelist measureList;

        /**
         * The Groups.
         */
        public DataStructureDTO.Group[] groups;
    }

    /**
     * The type Attributelist.
     */
    public static class Attributelist {
        /**
         * The Id.
         */
        public String id;

        /**
         * The Urn.
         */
        public String urn;

        /**
         * The Links.
         */
        public DataStructureDTO.Link[] links;

        /**
         * The Attributes.
         */
        public DataStructureDTO.Attribute[] attributes;
    }

    /**
     * The type Link.
     */
    public static class Link {
        /**
         * The Rel.
         */
        public String rel;

        /**
         * The Urn.
         */
        public String urn;

        /**
         * The Href.
         */
        public String href;

        /**
         * The Url.
         */
        public String url;

        /**
         * The Type.
         */
        public String type;
    }

    /**
     * The type Annotation.
     */
    public static class Annotation {
        /**
         * The Id.
         */
        public String id;

        /**
         * The Title.
         */
        public String title;

        /**
         * The Type.
         */
        public String type;

        /**
         * The Text.
         */
        public String text;

        /**
         * The Texts.
         */
        public Map<String, String> texts;

        /**
         * The Links.
         */
        public DataStructureDTO.Link[] links;
    }

    /**
     * The type Attribute.
     */
    public static class Attribute {
        /**
         * The Id.
         */
        public String id;

        /**
         * The Urn.
         */
        public String urn;

        /**
         * The Links.
         */
        public DataStructureDTO.Link[] links;

        /**
         * The Concept identity.
         */
        public String conceptIdentity;

        /**
         * The Local representation.
         */
        public DataStructureDTO.Localrepresentation localRepresentation;

        /**
         * The Assignment status.
         */
        public String assignmentStatus;

        /**
         * The Attribute relationship.
         */
        public DataStructureDTO.Attributerelationship attributeRelationship;

        /**
         * The Annotations.
         */
        public DataStructureDTO.Annotation[] annotations;

        /**
         * The Concept roles.
         */
        public String[] conceptRoles;
    }

    /**
     * The type Localrepresentation.
     */
    public static class Localrepresentation {
        /**
         * The Enumeration.
         */
        public String enumeration;

        /**
         * The Text format.
         */
        public DataStructureDTO.Textformat textFormat;
    }

    /**
     * The type Attributerelationship.
     */
    public static class Attributerelationship {
        /**
         * The Attachment groups.
         */
        public String[] attachmentGroups;

        /**
         * The Dimensions.
         */
        public String[] dimensions;

        /**
         * The Primary measure.
         */
        public String primaryMeasure;
    }

    /**
     * The type Dimensionlist.
     */
    public static class Dimensionlist {
        /**
         * The Id.
         */
        public String id;

        /**
         * The Urn.
         */
        public String urn;

        /**
         * The Links.
         */
        public DataStructureDTO.Link[] links;

        /**
         * The Dimensions.
         */
        public DataStructureDTO.Dimension[] dimensions;

        /**
         * The Measure dimensions.
         */
        public DataStructureDTO.Measuredimension[] measureDimensions;

        /**
         * The Time dimensions.
         */
        public DataStructureDTO.Timedimension[] timeDimensions;
    }

    /**
     * The type Dimension.
     */
    public static class Dimension {
        /**
         * The Id.
         */
        public String id;

        /**
         * The Urn.
         */
        public String urn;

        /**
         * The Links.
         */
        public DataStructureDTO.Link[] links;

        /**
         * The Position.
         */
        public int position;

        /**
         * The Type.
         */
        public String type;

        /**
         * The Concept identity.
         */
        public String conceptIdentity;

        /**
         * The Local representation.
         */
        public DataStructureDTO.Localrepresentation localRepresentation;

        /**
         * The Annotations.
         */
        public DataStructureDTO.Annotation[] annotations;

        /**
         * The Concept roles.
         */
        public String[] conceptRoles;
    }

    /**
     * The type Measuredimension.
     */
    public static class Measuredimension {
        /**
         * The Id.
         */
        public String id;

        /**
         * The Urn.
         */
        public String urn;

        /**
         * The Links.
         */
        public DataStructureDTO.Link[] links;

        /**
         * The Position.
         */
        public int position;

        /**
         * The Type.
         */
        public String type;

        /**
         * The Concept identity.
         */
        public String conceptIdentity;

        /**
         * The Local representation.
         */
        public DataStructureDTO.Localrepresentation localRepresentation;

        /**
         * The Annotations.
         */
        public DataStructureDTO.Annotation[] annotations;

        /**
         * The Concept roles.
         */
        public String[] conceptRoles;
    }

    /**
     * The type Timedimension.
     */
    public static class Timedimension {
        /**
         * The Id.
         */
        public String id;

        /**
         * The Urn.
         */
        public String urn;

        /**
         * The Links.
         */
        public DataStructureDTO.Link[] links;

        /**
         * The Position.
         */
        public int position;

        /**
         * The Type.
         */
        public String type;

        /**
         * The Concept identity.
         */
        public String conceptIdentity;

        /**
         * The Local representation.
         */
        public DataStructureDTO.Localrepresentation localRepresentation;

        /**
         * The Annotations.
         */
        public DataStructureDTO.Annotation[] annotations;

        /**
         * The Concept roles.
         */
        public String[] conceptRoles;
    }

    /**
     * The type Textformat.
     */
    public static class Textformat {
        /**
         * The Text type.
         */
        public String textType;

        /**
         * The Is sequence.
         */
        public Boolean isSequence;

        /**
         * The Is multi lingual.
         */
        public Boolean isMultiLingual;

        /**
         * The Decimals.
         */
        public BigInteger decimals;

        /**
         * The Start value.
         */
        public BigDecimal startValue;

        /**
         * The End value.
         */
        public BigDecimal endValue;

        /**
         * The Interval.
         */
        public BigDecimal interval;

        /**
         * The Min length.
         */
        public BigInteger minLength;

        /**
         * The Max length.
         */
        public BigInteger maxLength;

        /**
         * The Pattern.
         */
        public String pattern;

        /**
         * The Min value.
         */
        public BigDecimal minValue;

        /**
         * The Max value.
         */
        public BigDecimal maxValue;

        /**
         * The Time interval.
         */
        public String timeInterval;
    }

    /**
     * The type Measurelist.
     */
    public static class Measurelist {
        /**
         * The Id.
         */
        public String id;

        /**
         * The Urn.
         */
        public String urn;

        /**
         * The Links.
         */
        public DataStructureDTO.Link[] links;

        /**
         * The Primary measure.
         */
        public DataStructureDTO.Primarymeasure primaryMeasure;
    }

    /**
     * The type Primarymeasure.
     */
    public static class Primarymeasure {
        /**
         * The Id.
         */
        public String id;

        /**
         * The Urn.
         */
        public String urn;

        /**
         * The Links.
         */
        public DataStructureDTO.Link[] links;

        /**
         * The Concept identity.
         */
        public String conceptIdentity;

        /**
         * The Local representation.
         */
        public DataStructureDTO.Localrepresentation localRepresentation;

        /**
         * The Annotations.
         */
        public DataStructureDTO.Annotation[] annotations;
    }

    /**
     * The type Group.
     */
    public static class Group {
        /**
         * The Id.
         */
        public String id;

        /**
         * The Urn.
         */
        public String urn;

        /**
         * The Links.
         */
        public DataStructureDTO.Link[] links;

        /**
         * The Group dimensions.
         */
        public String[] groupDimensions;
    }
}
