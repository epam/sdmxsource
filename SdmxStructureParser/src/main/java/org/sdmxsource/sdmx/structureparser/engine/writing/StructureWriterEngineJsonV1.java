package org.sdmxsource.sdmx.structureparser.engine.writing;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.log4j.Logger;
import org.sdmx.resources.sdmxml.schemas.v21.message.StructureDocument;
import org.sdmx.resources.sdmxml.schemas.v21.message.StructureHeaderType;
import org.sdmx.resources.sdmxml.schemas.v21.message.StructureType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TIME_FORMAT;
import org.sdmxsource.sdmx.api.engine.StructureWriterEngine;
import org.sdmxsource.sdmx.api.model.base.SdmxDate;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.base.*;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategorisationBean;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategorySchemeBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.*;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptBean;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptSchemeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.*;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.DataAndMetadataSetReference;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.*;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.model.header.PartyBean;
import org.sdmxsource.sdmx.sdmxbeans.model.SdmxStructureJsonFormat;
import org.sdmxsource.sdmx.sdmxbeans.model.header.PartyBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.util.TextTypeUtil;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.registry.response.impl.v21.V2_1Helper;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.DataTypeBuilder;
import org.sdmxsource.sdmx.util.date.DateUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

/**
 * The type Structure writer engine json v 1.
 */
public class StructureWriterEngineJsonV1 implements StructureWriterEngine, AutoCloseable {

    private static final Logger LOG = Logger.getLogger(StructureWritingEngineV21.class);
    private final SdmxStructureJsonFormat sdmxStructureJsonFormat;
    private final DataTypeBuilder dataTypeBuilder = new DataTypeBuilder();
    private final JsonGenerator jsonGenerator;
    private final RequestContext requestContext = new RequestContext();

    /**
     * Instantiates a new Structure writer engine json v 1.
     *
     * @param outputStream            the output stream
     * @param sdmxStructureJsonFormat the sdmx structure json format
     */
    public StructureWriterEngineJsonV1(
            OutputStream outputStream,
            SdmxStructureJsonFormat sdmxStructureJsonFormat) {
        if (outputStream == null)
            throw new IllegalArgumentException("outputStream");
        if (sdmxStructureJsonFormat == null)
            throw new IllegalArgumentException("sdmxStructureJsonFormat");

        JsonFactory factory = new JsonFactory();
        try {
            this.jsonGenerator = factory.createGenerator(outputStream, JsonEncoding.UTF8);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        this.sdmxStructureJsonFormat = sdmxStructureJsonFormat;
    }

    @Override
    public void writeStructures(SdmxBeans sdmxObjects) {
        try {
            if (sdmxObjects == null)
                throw new IllegalArgumentException("sdmxObjects");
            this.jsonGenerator.writeStartObject();
            StructureHeaderType headerType = this.writeDocumentHeader(sdmxObjects);
            LOG.debug("{data}");
            this.jsonGenerator.writeObjectFieldStart("data");
            if (sdmxObjects.getDataflows().size() > 0) {
                LOG.debug("{dataflows}");
                this.jsonGenerator.writeArrayFieldStart("dataflows");
                for (DataflowBean dataflow : sdmxObjects.getDataflows())
                    this.writeDataflow(dataflow);
                LOG.debug("{/dataflows}");
                this.jsonGenerator.writeEndArray();
            }
            if (sdmxObjects.getAgenciesSchemes().size() > 0) {
                LOG.debug("{agencySchemes}");
                this.jsonGenerator.writeArrayFieldStart("agencySchemes");
                for (AgencySchemeBean agenciesScheme : sdmxObjects.getAgenciesSchemes())
                    this.writeAgencyScheme(agenciesScheme);
                LOG.debug("{/agencySchemes}");
                this.jsonGenerator.writeEndArray();
            }
            if (sdmxObjects.getCategorySchemes().size() > 0) {
                LOG.debug("{categorySchemes}");
                this.jsonGenerator.writeArrayFieldStart("categorySchemes");
                for (CategorySchemeBean categoryScheme : sdmxObjects.getCategorySchemes())
                    this.writeCategoryScheme(categoryScheme);
                LOG.debug("{/categorySchemes}");
                this.jsonGenerator.writeEndArray();
            }
            if (sdmxObjects.getCategorisations().size() > 0) {
                LOG.debug("{categorisations}");
                this.jsonGenerator.writeArrayFieldStart("categorisations");
                for (CategorisationBean categorisation : sdmxObjects.getCategorisations())
                    this.writeCategorisation(categorisation);
                LOG.debug("{/categorisations}");
                this.jsonGenerator.writeEndArray();
            }
            if (sdmxObjects.getConceptSchemes().size() > 0) {
                LOG.debug("{conceptSchemes}");
                this.jsonGenerator.writeArrayFieldStart("conceptSchemes");
                for (ConceptSchemeBean conceptScheme : sdmxObjects.getConceptSchemes())
                    this.writeConceptScheme(conceptScheme);
                LOG.debug("{/conceptSchemes}");
                this.jsonGenerator.writeEndArray();
            }
            if (sdmxObjects.getCodelists().size() > 0) {
                LOG.debug("{codelists}");
                this.jsonGenerator.writeArrayFieldStart("codelists");
                for (CodelistBean codelist : sdmxObjects.getCodelists())
                    this.writeCodeList(codelist);
                LOG.debug("{/codelists}");
                this.jsonGenerator.writeEndArray();
            }
            if (sdmxObjects.getHierarchicalCodelists().size() > 0) {
                LOG.debug("{hierarchicalCodelists}");
                this.jsonGenerator.writeArrayFieldStart("hierarchicalCodelists");
                for (HierarchicalCodelistBean hierarchicalCodelist : sdmxObjects.getHierarchicalCodelists())
                    this.writeHierarchicalCodeList(hierarchicalCodelist);
                LOG.debug("{/hierarchicalCodelists}");
                this.jsonGenerator.writeEndArray();
            }
            if (sdmxObjects.getDataStructures().size() > 0) {
                LOG.debug("{dataStructures}");
                this.jsonGenerator.writeArrayFieldStart("dataStructures");
                for (DataStructureBean dataStructure : sdmxObjects.getDataStructures())
                    this.writeDataStructure(dataStructure);
                LOG.debug("{/dataStructures}");
                this.jsonGenerator.writeEndArray();
            }
            if (sdmxObjects.getContentConstraintBeans().size() > 0) {
                LOG.debug("{contentConstraints}");
                this.jsonGenerator.writeArrayFieldStart("contentConstraints");
                for (ContentConstraintBean constraintObject : sdmxObjects.getContentConstraintBeans())
                    this.writeContentConstraint(constraintObject);
                LOG.debug("{/contentConstraints}");
                this.jsonGenerator.writeEndArray();
            }
            LOG.debug("{/data}");
            this.jsonGenerator.writeEndObject();
            this.writeMetaTag(sdmxObjects, headerType);
            this.jsonGenerator.flush();
            this.jsonGenerator.close();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void writeStructure(MaintainableBean bean) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close() throws Exception {
        jsonGenerator.close();
    }

    private void writeAgencyScheme(AgencySchemeBean agencyScheme) throws IOException {
        if (agencyScheme == null)
            return;
        this.jsonGenerator.writeStartObject();
        this.writeIMaintainableObject(agencyScheme);
        this.jsonGenerator.writeBooleanField("isPartial", agencyScheme.isPartial());
        if (agencyScheme.getItems() != null)
            this.writeItems(agencyScheme.getItems(), "agencies");
        this.jsonGenerator.writeEndObject();
    }

    private void writeAnnotation(AnnotationBean annotation) throws IOException {
        this.jsonGenerator.writeStartObject();
        if (annotation.getId() != null)
            this.jsonGenerator.writeStringField("id", annotation.getId());
        if (annotation.getTitle() != null)
            this.jsonGenerator.writeStringField("title", annotation.getTitle());
        if (annotation.getType() != null)
            this.jsonGenerator.writeStringField("type", annotation.getType());
        if (annotation.getUri() != null)
            this.jsonGenerator.writeStringField("uri", annotation.getUri().toString());
        this.writeITextTypeWrapper("text", "texts", annotation.getText());
        this.jsonGenerator.writeEndObject();
    }

    private void writeAnnotations(List<AnnotationBean> annotations) throws IOException {
        this.jsonGenerator.writeArrayFieldStart("annotations");
        for (AnnotationBean annotation : annotations)
            this.writeAnnotation(annotation);
        this.jsonGenerator.writeEndArray();
    }

    private void writeCategoryScheme(CategorySchemeBean categoryScheme) throws IOException {
        if (categoryScheme == null)
            return;
        this.jsonGenerator.writeStartObject();
        this.writeIMaintainableObject(categoryScheme);
        this.jsonGenerator.writeBooleanField("isPartial", categoryScheme.isPartial());
        if (categoryScheme.getItems() != null)
            this.writeItems(categoryScheme.getItems(), "categories");
        this.jsonGenerator.writeEndObject();
    }

    private void writeCategorisation(CategorisationBean categorisation) throws IOException {
        if (categorisation == null)
            return;
        this.jsonGenerator.writeStartObject();
        this.writeIMaintainableObject(categorisation);
        if (categorisation.getStructureReference() != null)
            this.jsonGenerator.writeStringField("source", categorisation.getStructureReference().getTargetUrn());
        if (categorisation.getCategoryReference() != null)
            this.jsonGenerator.writeStringField("target", categorisation.getCategoryReference().getTargetUrn());
        this.jsonGenerator.writeEndObject();
    }

    private void writeContact(ContactBean contact) throws IOException {
        if (contact == null)
            return;
        this.jsonGenerator.writeStartObject();
        if (contact.getId() != null)
            this.jsonGenerator.writeStringField("id", contact.getId());
        this.writeITextTypeWrapper("name", "names", contact.getName());
        this.writeITextTypeWrapper("department", "departments", contact.getDepartments());
        this.writeITextTypeWrapper("role", "roles", contact.getRole());
        this.writeArray("email", contact.getEmail());
        this.writeArray("fax", contact.getFax());
        this.writeArray("telephone", contact.getTelephone());
        this.writeArray("uri", contact.getUri());
        this.jsonGenerator.writeEndObject();
    }

    private void writeDataflow(DataflowBean dataflow) throws IOException {
        this.jsonGenerator.writeStartObject();
        this.writeIMaintainableObject(dataflow);
        if (dataflow.getDataStructureRef() != null)
            this.jsonGenerator.writeStringField("structure", dataflow.getDataStructureRef().getTargetUrn());
        this.jsonGenerator.writeEndObject();
    }

    private void writeConceptScheme(ConceptSchemeBean conceptScheme) throws IOException {
        this.jsonGenerator.writeStartObject();
        this.writeIMaintainableObject(conceptScheme);
        this.jsonGenerator.writeBooleanField("isPartial", conceptScheme.isPartial());
        if (conceptScheme.getItems() != null)
            this.writeItems(conceptScheme.getItems(), "concepts");
        this.jsonGenerator.writeEndObject();
    }

    private void writeRepresentation(String name, RepresentationBean representation) throws IOException {
        if (representation == null)
            return;
        this.jsonGenerator.writeObjectFieldStart(name);
        if (representation.getRepresentation() != null && representation.getRepresentation().hasTargetUrn())
            this.jsonGenerator.writeStringField("enumeration", representation.getRepresentation().getTargetUrn());
        this.writeTextFormat(representation.getTextFormat());
        this.jsonGenerator.writeEndObject();
    }

    private void writeConcept(ConceptBean concept) throws IOException {
        this.writeRepresentation("coreRepresentation", concept.getCoreRepresentation());
        if (concept.getIsoConceptReference() != null && concept.getIsoConceptReference().hasMaintainableUrn())
            this.jsonGenerator.writeStringField("isoConceptReference",
                    concept.getIsoConceptReference().getMaintainableUrn());
        if (concept.getParentConcept() == null)
            return;
        this.jsonGenerator.writeStringField("parent", concept.getParentConcept());
    }

    private void writeCodeList(CodelistBean codeList) throws IOException {
        this.jsonGenerator.writeStartObject();
        this.writeIMaintainableObject(codeList);
        this.jsonGenerator.writeBooleanField("isPartial", codeList.isPartial());
        if (codeList.getItems() != null)
            this.writeItems(codeList.getItems(), "codes");
        this.jsonGenerator.writeEndObject();
    }

    private void writeHierarchicalCodeList(HierarchicalCodelistBean hierarchicalCodelist) throws IOException {
        this.jsonGenerator.writeStartObject();
        this.writeIMaintainableObject(hierarchicalCodelist);
        if (hierarchicalCodelist.getCodelistRef() != null)
            this.writeCodelistRef(hierarchicalCodelist.getCodelistRef());
        if (hierarchicalCodelist.getHierarchies() != null)
            this.writeItems(hierarchicalCodelist.getHierarchies(), "hierarchies");
        this.jsonGenerator.writeEndObject();
    }

    private void writeCodelistRef(List<CodelistRefBean> codelistRef) throws IOException {
        this.jsonGenerator.writeArrayFieldStart("codelistRef");
        for (CodelistRefBean refBean : codelistRef) {
            this.jsonGenerator.writeStartObject();
            this.jsonGenerator.writeStringField("alias", refBean.getAlias());
            this.jsonGenerator.writeStringField("structureReference", refBean.getCodelistReference().getTargetUrn());
            this.jsonGenerator.writeEndObject();
        }
        this.jsonGenerator.writeEndArray();
    }

    private void writeDataStructure(DataStructureBean dsd) throws IOException {
        if (dsd == null)
            return;
        this.jsonGenerator.writeStartObject();
        this.writeIMaintainableObject(dsd);
        this.jsonGenerator.writeObjectFieldStart("dataStructureComponents");
        this.writeDataStructureAttributeList(dsd.getAttributeList());
        this.writeDimensionList(dsd.getDimensionList());
        this.writeMeasureList(dsd.getMeasureList());
        this.writeDataStructureGroups(dsd.getGroups());
        this.jsonGenerator.writeEndObject();
        this.jsonGenerator.writeEndObject();
    }

    private void writeDataStructureAttributeList(AttributeListBean attributeList) throws IOException {
        if (attributeList == null)
            return;
        this.jsonGenerator.writeObjectFieldStart("attributeList");
        this.writeIdentifiableObject(attributeList);
        this.writeAttributes(attributeList.getAttributes());
        this.jsonGenerator.writeEndObject();
    }

    private void writeAttributes(List<AttributeBean> attributes) throws IOException {
        if (attributes == null || attributes.size() == 0)
            return;
        this.jsonGenerator.writeArrayFieldStart("attributes");
        for (AttributeBean attribute : attributes) {
            this.jsonGenerator.writeStartObject();
            this.writeComponent(attribute);
            this.jsonGenerator.writeStringField("assignmentStatus", attribute.getAssignmentStatus());
            this.jsonGenerator.writeObjectFieldStart("attributeRelationship");
            if (attribute.getAttachmentGroup() != null) {
                this.jsonGenerator.writeArrayFieldStart("attachmentGroups");
                this.jsonGenerator.writeString(attribute.getAttachmentGroup());
                this.jsonGenerator.writeEndArray();
            }
            if (attribute.getDimensionReferences() != null && attribute.getDimensionReferences().size() > 0) {
                this.jsonGenerator.writeArrayFieldStart("dimensions");
                for (String dimensionReference : attribute.getDimensionReferences())
                    this.jsonGenerator.writeString(dimensionReference);
                this.jsonGenerator.writeEndArray();
            }
            if (attribute.getPrimaryMeasureReference() != null)
                this.jsonGenerator.writeStringField("primaryMeasure", attribute.getPrimaryMeasureReference());
            this.jsonGenerator.writeEndObject();
            this.jsonGenerator.writeEndObject();
        }
        this.jsonGenerator.writeEndArray();
    }

    private void writeDataStructureGroups(List<GroupBean> groups) throws IOException {
        if (groups == null || groups.size() == 0)
            return;
        this.jsonGenerator.writeArrayFieldStart("groups");
        for (GroupBean group : groups) {
            this.jsonGenerator.writeStartObject();
            this.writeIdentifiableObject(group);
            if (group.getDimensionRefs() != null && group.getDimensionRefs().size() > 0) {
                this.jsonGenerator.writeArrayFieldStart("groupDimensions");
                for (String dimensionRef : group.getDimensionRefs())
                    this.jsonGenerator.writeString(dimensionRef);
                this.jsonGenerator.writeEndArray();
            }
            if (group.getAttachmentConstraintRef() != null)
                this.jsonGenerator.writeStringField("attachmentConstraint", group.getAttachmentConstraintRef().getTargetUrn());
            this.jsonGenerator.writeEndObject();
        }
        this.jsonGenerator.writeEndArray();
    }

    private void writeDimensionList(DimensionListBean dimensionList) throws IOException {
        if (dimensionList == null)
            return;
        this.jsonGenerator.writeObjectFieldStart("dimensionList");
        this.writeIdentifiableObject(dimensionList);
        this.writeDimension(dimensionList.getDimensions(), "dimensions", SDMX_STRUCTURE_TYPE.DIMENSION);
        this.writeDimension(dimensionList.getDimensions(), "measureDimensions", SDMX_STRUCTURE_TYPE.MEASURE_DIMENSION);
        this.writeDimension(dimensionList.getDimensions(), "timeDimensions", SDMX_STRUCTURE_TYPE.TIME_DIMENSION);
        this.jsonGenerator.writeEndObject();
    }

    private void writeMeasureList(MeasureListBean measureList) throws IOException {
        if (measureList == null)
            return;
        this.jsonGenerator.writeObjectFieldStart("measureList");
        this.writeIdentifiableObject(measureList);
        this.jsonGenerator.writeObjectFieldStart("primaryMeasure");
        this.writeComponent(measureList.getPrimaryMeasure());
        this.jsonGenerator.writeEndObject();
        this.jsonGenerator.writeEndObject();
    }

    private void writeDimension(
            List<DimensionBean> dimensions,
            String typeName,
            SDMX_STRUCTURE_TYPE sdmxStructureEnumType) throws IOException {
        if (dimensions == null)
            return;
        List<DimensionBean> source = dimensions.stream()
                .filter(item -> item.getStructureType().equals(sdmxStructureEnumType))
                .collect(Collectors.toList());
        if (source.size() == 0)
            return;
        this.jsonGenerator.writeArrayFieldStart(typeName);
        for (DimensionBean dimension : source) {
            this.jsonGenerator.writeStartObject();
            this.writeComponent(dimension);
            this.jsonGenerator.writeEndObject();
        }
        this.jsonGenerator.writeEndArray();
    }

    private void writeComponent(ComponentBean component) throws IOException {
        if (component == null)
            return;
        this.writeIdentifiableObject(component);
        if (component instanceof DimensionBean) {
            DimensionBean dimension = (DimensionBean) component;
            this.jsonGenerator.writeNumberField("position", dimension.getPosition() - 1);
            this.jsonGenerator.writeStringField("type", component.getStructureType().getUrnClass());
            this.writeConceptRoles(dimension.getConceptRole());
        } else if (component instanceof AttributeBean)
            this.writeConceptRoles(((AttributeBean) component).getConceptRoles());
        this.jsonGenerator.writeStringField("conceptIdentity", component.getConceptRef().getTargetUrn());
        this.writeRepresentation("localRepresentation", component.getRepresentation());
        this.writeAnnotations(component.getAnnotations());
    }

    private void writeContentConstraint(ContentConstraintBean contentConstraint) throws IOException {
        if (contentConstraint == null)
            return;
        this.jsonGenerator.writeStartObject();
        this.writeIMaintainableObject(contentConstraint);
        this.jsonGenerator.writeStringField("type", contentConstraint.isDefiningActualDataPresent() ?
                "Actual" : "Allowed");
        this.writeConstraintAttachment(contentConstraint.getConstraintAttachment());
        if (contentConstraint.getIncludedCubeRegion() != null || contentConstraint.getExcludedCubeRegion() != null) {
            this.jsonGenerator.writeArrayFieldStart("cubeRegions");
            this.writeCubeRegion(contentConstraint.getIncludedCubeRegion(), true);
            this.writeCubeRegion(contentConstraint.getExcludedCubeRegion(), false);
            this.jsonGenerator.writeEndArray();
        }
        if (contentConstraint.getIncludedSeriesKeys() != null || contentConstraint.getExcludedSeriesKeys() != null) {
            this.jsonGenerator.writeArrayFieldStart("dataKeySets");
            this.writeDataKeySet(contentConstraint.getIncludedSeriesKeys() != null ?
                    contentConstraint.getIncludedSeriesKeys().getConstrainedDataKeys() : null, true);
            this.writeDataKeySet(contentConstraint.getExcludedSeriesKeys() != null ?
                    contentConstraint.getIncludedSeriesKeys().getConstrainedDataKeys() : null, false);
            this.jsonGenerator.writeEndArray();
        }
        if (contentConstraint.getIncludedMetadataKeys() != null || contentConstraint.getExcludedMetadataKeys() != null) {
            this.jsonGenerator.writeArrayFieldStart("metadataKeySets");
            this.writeMetadataKeySet(contentConstraint.getIncludedMetadataKeys() != null ?
                    contentConstraint.getIncludedMetadataKeys().getConstrainedDataKeys() : null, true, contentConstraint.getMetadataTargetRegion());
            this.writeMetadataKeySet(contentConstraint.getExcludedMetadataKeys() != null ?
                    contentConstraint.getIncludedMetadataKeys().getConstrainedDataKeys() : null, false, contentConstraint.getMetadataTargetRegion());
            this.jsonGenerator.writeEndArray();
        }
        this.writeMetadataTargetRegion(contentConstraint.getMetadataTargetRegion());
        this.jsonGenerator.writeEndObject();
    }

    private void writeConstraintAttachment(ConstraintAttachmentBean constraintAttachment) throws IOException {
        if (constraintAttachment == null)
            return;
        this.jsonGenerator.writeObjectFieldStart("constraintAttachment");
        CrossReferenceBean crossReference = constraintAttachment.getStructureReference()
                .stream()
                .filter(item -> item.getTargetReference().equals(SDMX_STRUCTURE_TYPE.DATA_PROVIDER))
                .findFirst()
                .orElse(null);

        if (crossReference != null)
            this.jsonGenerator.writeStringField("dataProvider", crossReference.getTargetUrn());
        DataAndMetadataSetReference metadataSetReference = constraintAttachment.getDataOrMetadataSetReference();
        if (metadataSetReference != null)
            this.writeDataSetReference(metadataSetReference.isDataSetReference() ? "dataSets" : "metadataSets", metadataSetReference);
        List<CrossReferenceBean> source1 = constraintAttachment.getStructureReference()
                .stream()
                .filter(item -> item.getTargetReference().equals(SDMX_STRUCTURE_TYPE.DSD))
                .collect(Collectors.toList());

        if (source1.size() > 0) {
            this.jsonGenerator.writeArrayFieldStart("dataStructures");
            for (StructureReferenceBean structureReference : source1)
                this.jsonGenerator.writeString(structureReference.getTargetUrn());
            this.jsonGenerator.writeEndArray();
        }

        List<CrossReferenceBean> source2 = constraintAttachment.getStructureReference()
                .stream()
                .filter(item -> item.getTargetReference().equals(SDMX_STRUCTURE_TYPE.DATAFLOW))
                .collect(Collectors.toList());

        if (source2.size() > 0) {
            this.jsonGenerator.writeArrayFieldStart("dataflows");
            for (StructureReferenceBean structureReference : source2)
                this.jsonGenerator.writeString(structureReference.getTargetUrn());
            this.jsonGenerator.writeEndArray();
        }

        List<CrossReferenceBean> source3 = constraintAttachment.getStructureReference()
                .stream()
                .filter(item -> item.getTargetReference().equals(SDMX_STRUCTURE_TYPE.METADATA_DOCUMENT))
                .collect(Collectors.toList());

        if (source3.size() > 0) {
            this.jsonGenerator.writeArrayFieldStart("metadataStructures");
            for (StructureReferenceBean structureReference : source3)
                this.jsonGenerator.writeString(structureReference.getTargetUrn());
            this.jsonGenerator.writeEndArray();
        }

        List<CrossReferenceBean> source4 = constraintAttachment.getStructureReference()
                .stream()
                .filter(item -> item.getTargetReference().equals(SDMX_STRUCTURE_TYPE.METADATA_FLOW))
                .collect(Collectors.toList());

        if (source4.size() > 0) {
            this.jsonGenerator.writeArrayFieldStart("metadataflows");
            for (StructureReferenceBean structureReference : source4)
                this.jsonGenerator.writeString(structureReference.getTargetUrn());
            this.jsonGenerator.writeEndArray();
        }

        List<CrossReferenceBean> source5 = constraintAttachment.getStructureReference()
                .stream()
                .filter(item -> item.getTargetReference().equals(SDMX_STRUCTURE_TYPE.PROVISION_AGREEMENT))
                .collect(Collectors.toList());

        if (source5.size() > 0) {
            this.jsonGenerator.writeArrayFieldStart("metadataflows");
            for (StructureReferenceBean structureReference : source5)
                this.jsonGenerator.writeString(structureReference.getTargetUrn());
            this.jsonGenerator.writeEndArray();
        }
        this.jsonGenerator.writeEndObject();
    }

    private void writeDataSetReference(
            String propertyName,
            DataAndMetadataSetReference dataSetReference) throws IOException {
        this.jsonGenerator.writeArrayFieldStart(propertyName);
        this.jsonGenerator.writeStartObject();
        this.jsonGenerator.writeStringField("dataProvider", "");
        this.jsonGenerator.writeStringField("id", dataSetReference.getSetId());
        this.jsonGenerator.writeEndObject();
        this.jsonGenerator.writeEndArray();
    }

    private void writeCubeRegion(CubeRegionBean cubeRegion, boolean isIncluded) throws IOException {
        if (cubeRegion == null)
            return;
        this.jsonGenerator.writeStartObject();
        this.jsonGenerator.writeBooleanField("isIncluded", isIncluded);
        this.writeValueSet("attributes", cubeRegion.getAttributeValues());
        this.writeValueSet("keyValues", cubeRegion.getKeyValues());
        this.jsonGenerator.writeEndObject();
    }

    private void writeValueSet(String propertyName, List<KeyValues> keyValues) throws IOException {
        if (keyValues == null || keyValues.size() == 0)
            return;

        this.jsonGenerator.writeArrayFieldStart(propertyName);
        for (KeyValues keyValue : keyValues) {
            this.jsonGenerator.writeStartObject();
            this.jsonGenerator.writeStringField("id", keyValue.getId());
            this.writeTimeRangeValue(keyValue.getTimeRange());
            this.writeValues("values", keyValue.getValues());
            this.writeValues("cascadeValues", keyValue.getCascadeValues());
            this.jsonGenerator.writeEndObject();
        }
        this.jsonGenerator.writeEndArray();
    }

    private void writeValues(String propertyName, List<String> values) throws IOException {
        if (values == null || values.size() == 0)
            return;
        this.jsonGenerator.writeArrayFieldStart(propertyName);
        for (String str : values)
            this.jsonGenerator.writeString(str);
        this.jsonGenerator.writeEndArray();
    }

    private void writeTimeRangeValue(TimeRangeBean timeRange) throws IOException {
        if (timeRange == null)
            return;
        this.jsonGenerator.writeObjectFieldStart("timeRange");
        if (timeRange.getStartDate() != null)
            this.writeTimePeriodRange(timeRange.isRange() ? "startPeriod" : "beforePeriod",
                    timeRange.getStartDate(), timeRange.isStartInclusive());
        if (timeRange.getEndDate() != null)
            this.writeTimePeriodRange(timeRange.isRange() ? "endPeriod" : "afterPeriod",
                    timeRange.getEndDate(), timeRange.isEndInclusive());
        this.jsonGenerator.writeEndObject();
    }

    private void writeTimePeriodRange(String propertyName, SdmxDate date, boolean isInclusive) throws IOException {
        this.jsonGenerator.writeObjectFieldStart(propertyName);
        this.writeDateProperty("period", date.getDate());
        this.jsonGenerator.writeBooleanField("isInclusive", isInclusive);
        this.jsonGenerator.writeEndObject();
    }

    private void writeDataKeySet(List<ConstrainedDataKeyBean> dataKeySet, boolean isIncluded) throws IOException {
        if (dataKeySet == null || dataKeySet.size() == 0)
            return;
        this.jsonGenerator.writeStartObject();
        this.jsonGenerator.writeBooleanField("isIncluded", isIncluded);
        this.jsonGenerator.writeArrayFieldStart("keys");
        for (ConstrainedDataKeyBean dataKey : dataKeySet)
            this.writeDataKey(dataKey);
        this.jsonGenerator.writeEndArray();
        this.jsonGenerator.writeEndObject();
    }

    private void writeDataKey(ConstrainedDataKeyBean constrainedDataKey) throws IOException {
        this.jsonGenerator.writeStartObject();
        this.jsonGenerator.writeArrayFieldStart("keyValues");
        for (KeyValue keyValue : constrainedDataKey.getKeyValues())
            this.writeDataKeyValue(keyValue);
        this.jsonGenerator.writeEndArray();
        this.jsonGenerator.writeEndObject();
    }

    private void writeDataKeyValue(KeyValue keyValue) throws IOException {
        this.jsonGenerator.writeStartObject();
        this.jsonGenerator.writeStringField("id", keyValue.getConcept());
        this.jsonGenerator.writeStringField("value", keyValue.getCode());
        this.jsonGenerator.writeEndObject();
    }

    private void writeMetadataKeySet(
            List<ConstrainedDataKeyBean> dataKeySet,
            boolean isIncluded,
            MetadataTargetRegionBean targetRegion) throws IOException {
        if (dataKeySet == null || dataKeySet.size() == 0)
            return;
        this.jsonGenerator.writeStartObject();
        this.jsonGenerator.writeBooleanField("isIncluded", isIncluded);
        this.jsonGenerator.writeArrayFieldStart("keys");
        for (ConstrainedDataKeyBean dataKey : dataKeySet) {
            this.jsonGenerator.writeStartObject();
            this.jsonGenerator.writeStringField("metadataTarget", targetRegion.getMetadataTarget());
            this.jsonGenerator.writeStringField("report", targetRegion.getReport());
            this.writeMetadataKeyValues(dataKey.getKeyValues());
            this.jsonGenerator.writeEndObject();
        }
        this.jsonGenerator.writeEndArray();
        this.jsonGenerator.writeEndObject();
    }

    private void writeMetadataKeyValues(List<KeyValue> keyValues) throws IOException {
        if (keyValues == null || keyValues.size() == 0)
            return;
        this.jsonGenerator.writeArrayFieldStart("keyValues");
        for (KeyValue keyValue : keyValues) {
            this.jsonGenerator.writeStartObject();
            this.jsonGenerator.writeStringField("id", keyValue.getConcept());
            this.jsonGenerator.writeArrayFieldStart("dataKeys");
            this.jsonGenerator.writeEndArray();
            this.jsonGenerator.writeArrayFieldStart("dataSets");
            this.jsonGenerator.writeEndArray();
            this.jsonGenerator.writeStringField("object", "");
            this.jsonGenerator.writeStringField("value", keyValue.getCode());
            this.jsonGenerator.writeEndObject();
        }
        this.jsonGenerator.writeEndArray();
    }

    private void writeMetadataTargetRegion(MetadataTargetRegionBean targetRegion) throws IOException {
        if (targetRegion == null)
            return;
        this.jsonGenerator.writeArrayFieldStart("metadataTargetRegions");
        this.jsonGenerator.writeStartObject();
        this.jsonGenerator.writeBooleanField("include", targetRegion.isInclude());
        this.jsonGenerator.writeStringField("metadataTarget", targetRegion.getMetadataTarget());
        this.jsonGenerator.writeStringField("report", targetRegion.getReport());
        this.writeValueSet("attributes", targetRegion.getAttributes());
        if (targetRegion.getKey() != null && targetRegion.getKey().size() > 0) {
            this.jsonGenerator.writeArrayFieldStart("keyValues");
            for (MetadataTargetKeyValuesBean metadataTargetRegionKey : targetRegion.getKey())
                this.writeMetadataTargetRegionKey(metadataTargetRegionKey);
            this.jsonGenerator.writeEndArray();
        }
        this.jsonGenerator.writeEndObject();
        this.jsonGenerator.writeEndArray();
    }

    private void writeMetadataTargetRegionKey(MetadataTargetKeyValuesBean metadataTargetRegionKey) throws IOException {
        if (metadataTargetRegionKey == null)
            return;
        this.jsonGenerator.writeStartObject();
        this.jsonGenerator.writeStringField("id", metadataTargetRegionKey.getId());
        this.jsonGenerator.writeArrayFieldStart("dataKeys");
        this.jsonGenerator.writeEndArray();
        this.jsonGenerator.writeArrayFieldStart("dataSets");
        this.jsonGenerator.writeEndArray();
        if (metadataTargetRegionKey.getObjectReferences().size() > 0)
            this.writeValues("objects",
                    metadataTargetRegionKey.getObjectReferences().stream()
                            .map(StructureReferenceBean::getTargetUrn)
                            .collect(Collectors.toList()));
        this.writeTimeRangeValue(metadataTargetRegionKey.getTimeRange());
        this.writeValues("values", metadataTargetRegionKey.getValues());
        this.jsonGenerator.writeEndObject();
    }

    private void writeConceptRoles(List<CrossReferenceBean> conceptRoles) throws IOException {
        if (conceptRoles == null || conceptRoles.size() == 0)
            return;
        this.jsonGenerator.writeArrayFieldStart("conceptRoles");
        for (StructureReferenceBean conceptRole : conceptRoles)
            this.jsonGenerator.writeString(conceptRole.getTargetUrn());
        this.jsonGenerator.writeEndArray();
    }

    private void writeTextFormat(TextFormatBean textFormat) throws IOException {
        if (textFormat == null)
            return;
        this.jsonGenerator.writeObjectFieldStart("textFormat");
        if (textFormat.getTextType() != null)
            this.jsonGenerator.writeStringField("textType", this.dataTypeBuilder.build(textFormat.getTextType()).toString());

        BigInteger decimals = textFormat.getDecimals();
        if (decimals != null && decimals.compareTo(BigInteger.valueOf(-1)) > 0)
            this.jsonGenerator.writeNumberField("decimals", decimals);

        BigDecimal startValue = textFormat.getStartValue();
        BigDecimal endValue = textFormat.getEndValue();
        if (startValue != null && endValue != null && startValue.compareTo(endValue) < 0) {
            this.jsonGenerator.writeNumberField("startValue", startValue);
            this.jsonGenerator.writeNumberField("endValue", endValue);
        }

        BigDecimal interval = textFormat.getInterval();
        if (interval != null && interval.compareTo(BigDecimal.valueOf(-1)) > 0)
            this.jsonGenerator.writeNumberField("interval", interval);

        this.jsonGenerator.writeBooleanField("isSequence", textFormat.getSequence().isTrue());

        BigInteger maxLength = textFormat.getMaxLength();
        if (maxLength != null && maxLength.compareTo(BigInteger.valueOf(-1)) > 0)
            this.jsonGenerator.writeNumberField("maxLength", maxLength);

        BigInteger minLength = textFormat.getMinLength();
        if (minLength != null && minLength.compareTo(BigInteger.valueOf(-1)) > 0)
            this.jsonGenerator.writeNumberField("minLength", minLength);

        if (textFormat.getPattern() != null)
            this.jsonGenerator.writeStringField("pattern", textFormat.getPattern());

        BigDecimal minValue = textFormat.getMinValue();
        if (minValue != null)
            this.jsonGenerator.writeNumberField("minValue", minValue);

        BigDecimal maxValue = textFormat.getMaxValue();
        if (maxValue != null)
            this.jsonGenerator.writeNumberField("maxValue", maxValue);

        if (textFormat.getStartTime() != null)
            this.writeDateProperty("startTime", textFormat.getStartTime().getDate());
        if (textFormat.getEndTime() != null)
            this.writeDateProperty("endTime", textFormat.getEndTime().getDate());
        if (textFormat.getTimeInterval() != null)
            this.jsonGenerator.writeStringField("timeInterval", textFormat.getTimeInterval());

        this.jsonGenerator.writeBooleanField("isMultiLingual", textFormat.getMultiLingual().isTrue());
        this.jsonGenerator.writeEndObject();
    }

    private void writeDateProperty(String propertyName, Date dateTime) throws IOException {
        this.jsonGenerator.writeFieldName(propertyName);
        if (dateTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            this.jsonGenerator.writeString(sdf.format(dateTime));
        } else
            this.jsonGenerator.writeNull();
    }

    private StructureHeaderType writeDocumentHeader(SdmxBeans beans) {
        // @MS: not sure (entirely) in this code
        StructureDocument doc = StructureDocument.Factory.newInstance();
        StructureType structureType = doc.addNewStructure();

        StructureHeaderType structureHeaderType = (StructureHeaderType) structureType.addNewHeader();
        V2_1Helper.setHeader(structureHeaderType, beans);
        return structureHeaderType;
    }

    private void writeMetaTag(SdmxBeans beans, StructureHeaderType headerType) throws IOException {
        LOG.debug("{meta}");
        this.jsonGenerator.writeObjectFieldStart("meta");
        this.jsonGenerator.writeStringField("schema", "https://raw.githubusercontent.com/sdmx-twg/sdmx-json/develop/structure-message/tools/schemas/1.0/sdmx-json-structure-schema.json");
        this.jsonGenerator.writeArrayFieldStart("content-languages");
        for (String selectedLanguage : new String[]{"en"} /*this.sdmxStructureJsonFormat.Translator.SelectedLanguages*/)
            this.jsonGenerator.writeString(selectedLanguage);
        this.jsonGenerator.writeEndArray();
        this.jsonGenerator.writeStringField("id", headerType.getID());
        this.writeDateProperty("prepared", headerType.getPrepared().getTime());
        this.jsonGenerator.writeBooleanField("test", headerType.getTest());
        this.writeSender(new PartyBeanImpl(headerType.getSender()));
        this.writeReceivers(headerType.getReceiverList().stream()
                .map(PartyBeanImpl::new)
                .collect(Collectors.toList()));
        this.jsonGenerator.writeEndObject();
        LOG.debug("{/meta}");
    }

    private void writeIdentifiableObject(IdentifiableBean identifiableObject) throws IOException {
        this.jsonGenerator.writeStringField("id", identifiableObject.getId());
        if (identifiableObject.getUrn() != null && requestContext.isWriteUrn())
            this.jsonGenerator.writeStringField("urn", identifiableObject.getUrn());
        if (identifiableObject.getUri() != null)
            this.jsonGenerator.writeStringField("uri", identifiableObject.getUri().toString());
        this.writeLinks(identifiableObject);
    }

    private void writeIMaintainableObject(MaintainableBean maintainableObject) throws IOException {
        if (maintainableObject == null)
            return;
        this.writeIdentifiableObject(maintainableObject);
        if (maintainableObject.getVersion() != null)
            this.jsonGenerator.writeStringField("version", maintainableObject.getVersion());
        if (maintainableObject.getAgencyId() != null)
            this.jsonGenerator.writeStringField("agencyID", maintainableObject.getAgencyId());
        if (maintainableObject.isExternalReference() != null && maintainableObject.isExternalReference().isSet())
            this.jsonGenerator.writeBooleanField("isExternalReference", maintainableObject.isExternalReference().isTrue());
        if (maintainableObject.isFinal() != null && maintainableObject.isFinal().isSet())
            this.jsonGenerator.writeBooleanField("isFinal", maintainableObject.isFinal().isTrue());
        if (maintainableObject.getStartDate() != null)
            this.jsonGenerator.writeStringField("validFrom", DateUtil.formatDate(maintainableObject.getStartDate().getDate(), TIME_FORMAT.DATE_TIME));
        if (maintainableObject.getEndDate() != null)
            this.jsonGenerator.writeStringField("validTo", DateUtil.formatDate(maintainableObject.getEndDate().getDate(), TIME_FORMAT.DATE_TIME));
        this.writeNameableObject(maintainableObject);
        this.writeAnnotations(maintainableObject.getAnnotations());
    }

    private void writeLinks(IdentifiableBean nameable) throws IOException {
        this.jsonGenerator.writeArrayFieldStart("links");
        this.jsonGenerator.writeStartObject();
        this.jsonGenerator.writeStringField("rel", "self");
        if (requestContext.isWriteUrn())
            this.jsonGenerator.writeStringField("urn", nameable.getUrn());
        this.jsonGenerator.writeStringField("type", nameable.getStructureType().getUrnClass().toLowerCase());
        this.jsonGenerator.writeEndObject();
        if (nameable instanceof MaintainableBean) {
            MaintainableBean maintainableObject = (MaintainableBean) nameable;

            if (maintainableObject.isExternalReference() != null && maintainableObject.isExternalReference().isTrue()) {
                this.jsonGenerator.writeStartObject();
                JsonGenerator jsonGenerator = this.jsonGenerator;
                URL structureUrl = maintainableObject.getStructureURL();
                String str = structureUrl != null ? structureUrl.toString() : null;
                jsonGenerator.writeStringField("href", str);
                this.jsonGenerator.writeStringField("rel", "external");
                if (requestContext.isWriteUrn())
                    this.jsonGenerator.writeStringField("urn", nameable.getUrn());
                this.jsonGenerator.writeEndObject();
            }

            for (CrossReferenceBean crossReference : nameable.getCrossReferences()) {
                if (crossReference.getTargetReference().equals(SDMX_STRUCTURE_TYPE.DSD) ||
                        crossReference.getTargetReference().equals(SDMX_STRUCTURE_TYPE.MSD)) {
                    this.jsonGenerator.writeStartObject();
                    this.jsonGenerator.writeStringField("rel", "structure");
                    if (requestContext.isWriteUrn())
                        this.jsonGenerator.writeStringField("urn", crossReference.getTargetUrn());
                    this.jsonGenerator.writeStringField("type", crossReference.getTargetReference().getUrnClass().toLowerCase());
                    this.jsonGenerator.writeEndObject();
                }
            }
        }

        this.jsonGenerator.writeEndArray();
    }

    private void writeArray(String propertyName, List<String> list) throws IOException {
        if (list == null || propertyName == null || list.size() <= 0)
            return;

        this.jsonGenerator.writeArrayFieldStart(propertyName);

        for (String str : list)
            this.jsonGenerator.writeString(str);
        this.jsonGenerator.writeEndArray();
    }

    private void writeITextTypeWrapper(
            String propertyName,
            String arrayPropertyName,
            List<TextTypeWrapper> textTypeWrapper) throws IOException {
        writeITextTypeWrapper(propertyName, arrayPropertyName, textTypeWrapper, false);
    }

    private void writeITextTypeWrapper(
            String propertyName,
            String arrayPropertyName,
            List<TextTypeWrapper> textTypeWrapper,
            boolean provideAtLeastOneItem) throws IOException {
        if (textTypeWrapper == null ||
                propertyName == null || propertyName.equals("") ||
                textTypeWrapper.size() == 0)
            return;

        TextTypeWrapper selectedText = TextTypeUtil.getDefaultLocale(textTypeWrapper); //this.sdmxStructureJsonFormat.Translator.GetSelectedText(textTypeWrapper);
        String str = selectedText == null ?
                (provideAtLeastOneItem ? "[" + propertyName + "]" : null) :
                selectedText.getValue();

        if (str == null || str.equals(""))
            return;

        this.jsonGenerator.writeStringField(propertyName, str);
        List<TextTypeWrapper> preferredLanguages = textTypeWrapper; //this.sdmxStructureJsonFormat.Translator.GetSelectedTextInPreferredLanguages(textTypeWrapper, provideAtLeastOneItem);
        this.jsonGenerator.writeObjectFieldStart(arrayPropertyName);

        if (preferredLanguages != null && preferredLanguages.size() > 0) {
            for (TextTypeWrapper textTypeWrapper1 : preferredLanguages)
                this.jsonGenerator.writeStringField(textTypeWrapper1.getLocale(), textTypeWrapper1.getValue());
        }
        this.jsonGenerator.writeEndObject();
    }

    private void writeNameableObject(NameableBean nameableObject) throws IOException {
        if (nameableObject == null)
            return;
        this.writeITextTypeWrapper("name", "names", nameableObject.getNames(), true);
        this.writeITextTypeWrapper("description", "descriptions", nameableObject.getDescriptions());
    }

    private <T extends ItemBean> void writeItems(List<T> items, String type) throws IOException {
        if (items.size() == 0)
            return;
        this.jsonGenerator.writeArrayFieldStart(type);
        for (T obj : items) {
            this.jsonGenerator.writeStartObject();
            if (obj.getId() != null)
                this.jsonGenerator.writeStringField("id", obj.getId());
            if (obj.getUrn() != null)
                this.jsonGenerator.writeStringField("urn", obj.getUrn());
            this.writeNameableObject(obj);
            this.writeAnnotations(obj.getAnnotations());
            this.writeLinks(obj);
            if (obj instanceof OrganisationBean) {
                OrganisationBean organisation = (OrganisationBean) obj;

                if (organisation.getContacts().size() > 0) {
                    this.jsonGenerator.writeArrayFieldStart("contact");
                    for (ContactBean contact : organisation.getContacts())
                        this.writeContact(contact);
                    this.jsonGenerator.writeEndArray();
                }
            }

            if (obj instanceof ConceptBean)
                this.writeConcept((ConceptBean) obj);

            if (obj instanceof CodeBean) {
                CodeBean code = (CodeBean) obj;
                this.jsonGenerator.writeStringField("parent", code.getParentCode());
            }

            if (obj instanceof HierarchicalItemBean) {
                HierarchicalItemBean<T> hierarchicalItemObject = (HierarchicalItemBean<T>) obj;

                if (hierarchicalItemObject.getItems() != null && hierarchicalItemObject.getItems().size() > 0)
                    this.writeItems(hierarchicalItemObject.getItems(), type);
            }

            if (obj instanceof HierarchyBean) {
                HierarchyBean hierarchyBean = (HierarchyBean) obj;
                this.writeHierarchicalCodeBeans(hierarchyBean.getHierarchicalCodeBeans());
            }

            this.jsonGenerator.writeEndObject();
        }
        this.jsonGenerator.writeEndArray();
    }

    private void writeHierarchicalCodeBeans(List<HierarchicalCodeBean> hierarchicalCodeBeans) throws IOException {
        if (hierarchicalCodeBeans == null || hierarchicalCodeBeans.isEmpty())
            return;
        this.jsonGenerator.writeArrayFieldStart("codes");
        for (HierarchicalCodeBean codeBean : hierarchicalCodeBeans) {
            this.jsonGenerator.writeStartObject();
            this.jsonGenerator.writeStringField("id", codeBean.getId());
            if (codeBean.getUrn() != null && requestContext.isWriteUrn())
                this.jsonGenerator.writeStringField("urn", codeBean.getUrn());
            if (codeBean.getUri() != null)
                this.jsonGenerator.writeStringField("uri", codeBean.getUri().toString());
            this.jsonGenerator.writeNumberField("level", codeBean.getLevelInHierarchy());
            if (codeBean.getCodelistAliasRef() != null)
                this.jsonGenerator.writeStringField("codelistAliasRef", codeBean.getCodelistAliasRef());
            if (codeBean.getCodeReference() != null)
                this.jsonGenerator.writeStringField("code", codeBean.getCodeReference().getTargetUrn());
            this.jsonGenerator.writeStringField("codeId", codeBean.getCodeId());
            this.writeHierarchicalCodeBeans(codeBean.getCodeRefs());
            this.jsonGenerator.writeEndObject();
        }
        this.jsonGenerator.writeEndArray();
    }

    private void writeParty(PartyBean partyObject) throws IOException {
        if (partyObject == null)
            return;
        if (partyObject.getId() != null && !partyObject.getId().equals(""))
            this.jsonGenerator.writeStringField("id", partyObject.getId());
        this.writeITextTypeWrapper("name", "names", partyObject.getName());
        if (partyObject.getContacts().size() == 0)
            return;
        this.jsonGenerator.writeArrayFieldStart("contact");
        for (ContactBean contact : partyObject.getContacts())
            this.writeContact(contact);
        this.jsonGenerator.writeEndArray();
    }

    private void writeReceivers(List<PartyBean> receivers) throws IOException {
        if (receivers == null)
            return;
        LOG.debug("{receiver}");
        this.jsonGenerator.writeArrayFieldStart("receiver");
        for (PartyBean receiver : receivers) {
            this.jsonGenerator.writeStartObject();
            this.writeParty(receiver);
            this.jsonGenerator.writeEndObject();
        }
        LOG.debug("{/receiver}");
        this.jsonGenerator.writeEndArray();
    }

    private void writeSender(PartyBean sender) throws IOException {
        LOG.debug("{sender}");
        this.jsonGenerator.writeObjectFieldStart("sender");
        this.writeParty(sender);
        LOG.debug("{/sender}");
        this.jsonGenerator.writeEndObject();
    }

    private static class RequestContext {
        private boolean writeUrn = true;

        /**
         * Is write urn boolean.
         *
         * @return the boolean
         */
        public boolean isWriteUrn() {
            return writeUrn;
        }

        /**
         * Sets write urn.
         *
         * @param writeUrn the write urn
         */
        public void setWriteUrn(boolean writeUrn) {
            this.writeUrn = writeUrn;
        }
    }
}
