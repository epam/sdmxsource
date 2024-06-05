package org.sdmxsource.sdmx.structureparser.builder.sdmxbeans.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.sdmx.resources.sdmxml.schemas.v21.common.DataType;
import org.sdmxsource.sdmx.api.constants.ATTRIBUTE_ATTACHMENT_LEVEL;
import org.sdmxsource.sdmx.api.constants.DATASET_ACTION;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TERTIARY_BOOL;
import org.sdmxsource.sdmx.api.exception.MaintainableBeanException;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.header.DatasetStructureReferenceBean;
import org.sdmxsource.sdmx.api.model.header.HeaderBean;
import org.sdmxsource.sdmx.api.model.header.PartyBean;
import org.sdmxsource.sdmx.api.model.mutable.base.*;
import org.sdmxsource.sdmx.api.model.mutable.categoryscheme.CategorisationMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.categoryscheme.CategorySchemeMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.codelist.CodeMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.codelist.CodelistMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.codelist.HierarchicalCodelistMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.conceptscheme.ConceptMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.conceptscheme.ConceptSchemeMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.*;
import org.sdmxsource.sdmx.api.model.mutable.reference.CodeRefMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.reference.CodelistRefMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.ConstraintAttachmentMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.ContentConstraintMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.KeyValuesMutable;
import org.sdmxsource.sdmx.api.model.mutable.registry.MetadataTargetRegionMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.ReleaseCalendarMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.SdmxStructureJsonFormat;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.AgencySchemeBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.categoryscheme.CategorisationBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.categoryscheme.CategorySchemeBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.codelist.CodelistBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.codelist.HierarchicalCodelistBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.conceptscheme.ConceptSchemeBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.datastructure.DataflowBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.registry.ContentConstraintBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.header.HeaderBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.header.PartyBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.*;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.categoryscheme.CategorisationMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.categoryscheme.CategoryMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.categoryscheme.CategorySchemeMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.codelist.CodeMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.codelist.CodelistMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.codelist.HierarchicalCodelistMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.codelist.HierarchyMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.conceptscheme.ConceptMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.conceptscheme.ConceptSchemeMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.datastructure.*;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.metadatastructure.DataflowMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.reference.CodeRefMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.reference.CodelistRefMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.registry.ContentConstraintAttachmentMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.registry.ContentConstraintMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.registry.CubeRegionMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.registry.KeyValuesMutableImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.registry.MetadataTargetRegionMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.registry.ReleaseCalendarMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.util.TextTypeUtil;
import org.sdmxsource.sdmx.util.beans.container.SdmxBeansImpl;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;

import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Sdmx objects json v 1 builder.
 */
public class SdmxObjectsJsonV1Builder extends AbstractSdmxBeansBuilder {

    private final SdmxStructureJsonFormat _sdmxStructureJsonFormat;
    private final ObjectMapper objectMapper = new ObjectMapper();


    /**
     * Instantiates a new Sdmx objects json v 1 builder.
     *
     * @param sdmxStructureJsonFormat the sdmx structure json format
     */
    public SdmxObjectsJsonV1Builder(SdmxStructureJsonFormat sdmxStructureJsonFormat) {
        if (sdmxStructureJsonFormat == null)
            throw new IllegalArgumentException("sdmxStructureJsonFormat");

        this._sdmxStructureJsonFormat = sdmxStructureJsonFormat;
    }

    private static void addAnnotations(
            DataStructureDTO.Annotation[] annotations,
            AnnotableMutableBean annotable) {
        if (annotations == null)
            return;
        for (DataStructureDTO.Annotation annotation : annotations) {
            AnnotationMutableBean annotationMutableCore = new AnnotationMutableBeanImpl();
            if (annotation.id != null && annotation.id.length() != 0)
                annotationMutableCore.setId(annotation.id);
            if (annotation.title != null && annotation.title.length() != 0)
                annotationMutableCore.setTitle(annotation.title);
            if (annotation.type != null && annotation.type.length() != 0)
                annotationMutableCore.setType(annotation.type);
            if (annotation.texts != null) {
                for (Map.Entry<String, String> entry : annotation.texts.entrySet())
                    annotationMutableCore.addText(entry.getKey(), entry.getValue());
            }
            if (annotation.links != null) {
                String urnString = null;
                String urlString = null;

                for (DataStructureDTO.Link link : annotation.links) {
                    if (link.urn != null && link.urn.length() > 0) {
                        urnString = link.urn;
                        break;
                    }

                    if (urlString != null && link.url != null && link.url.length() > 0)
                        urlString = link.url;
                }

                if (urnString != null)
                    annotationMutableCore.setUri(urnString);
                else if (urlString != null)
                    annotationMutableCore.setUri(urlString);
            }
            annotable.addAnnotation(annotationMutableCore);
        }
    }

    /**
     * Build sdmx beans.
     *
     * @param buildFrom the build from
     * @return the sdmx beans
     */
    public SdmxBeans build(JsonNode buildFrom) {
        if (buildFrom == null)
            throw new IllegalArgumentException("buildFrom");

        JsonNode baseHeaderType = buildFrom.get("meta");
        if (baseHeaderType == null)
            return new SdmxBeansImpl();

        try {
            return build(buildFrom, processHeader(baseHeaderType), null);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                InvocationTargetException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private SdmxBeans build(
            JsonNode structures,
            HeaderBean header,
            DATASET_ACTION action) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, JsonProcessingException {
        SdmxBeansImpl beans = new SdmxBeansImpl(header, action);
        JsonNode structure = structures.get("data");
        if (structure != null && structure.size() > 0) {
            this.processDataflows(structure.get("dataflows"), beans);
            this.processAgencySchemes(structure.get("agencySchemes"), beans);
            this.processCategorySchemes(structure.get("categorySchemes"), beans);
            this.processCategorisations(structure.get("categorisations"), beans);
            this.processConceptSchemes(structure.get("conceptSchemes"), beans);
            this.processCodeLists(structure.get("codelists"), beans);
            this.processHierarchicalCodelists(structure.get("hierarchicalCodelists"), beans);
            this.processDatastructures(structure.get("dataStructures"), beans);
            this.processContentConstraint(structure.get("contentconstraint"), beans);
        }
        return beans;
    }

    private void processDatastructures(JsonNode dataStructures, SdmxBeansImpl beans) throws JsonProcessingException {
        if (dataStructures == null || beans == null)
            return;

        for (JsonNode dataStructure : dataStructures) {
            DataStructureDTO dataStructureDto = objectMapper.treeToValue(dataStructure, DataStructureDTO.class);
            DataStructureMutableBean structureMutableObject = new DataStructureMutableBeanImpl();
            for (Map.Entry<String, String> name : dataStructureDto.names.entrySet())
                structureMutableObject.addName(name.getKey(), name.getValue());
            if (dataStructureDto.description != null) {
                for (Map.Entry<String, String> description : dataStructureDto.descriptions.entrySet())
                    structureMutableObject.addDescription(description.getKey(), description.getValue());
            }
            structureMutableObject.setId(dataStructureDto.id);
            structureMutableObject.setAgencyId(dataStructureDto.agencyID);
            structureMutableObject.setVersion(dataStructureDto.version);
            if (dataStructureDto.validFrom != null)
                structureMutableObject.setStartDate(Date.from(dataStructureDto.validFrom));
            if (dataStructureDto.validTo != null)
                structureMutableObject.setEndDate(Date.from(dataStructureDto.validTo));
            structureMutableObject.setFinalStructure(TERTIARY_BOOL.parseBoolean(dataStructureDto.isFinal));
            SdmxObjectsJsonV1Builder.addAnnotations(dataStructureDto.annotations, structureMutableObject);

            if (dataStructureDto.dataStructureComponents != null) {
                if (dataStructureDto.dataStructureComponents.attributeList != null &&
                        dataStructureDto.dataStructureComponents.attributeList.attributes != null) {
                    for (DataStructureDTO.Attribute attributeDto : dataStructureDto.dataStructureComponents.attributeList.attributes) {
                        AttributeMutableBean attribute = new AttributeMutableBeanImpl();
                        attribute.setId(attributeDto.id);
                        attribute.setAssignmentStatus(attributeDto.assignmentStatus);
                        attribute.setConceptRef(this.setConceptRef(attributeDto.conceptIdentity));
                        if (attributeDto.conceptRoles != null) {
                            for (String conceptRole : attributeDto.conceptRoles)
                                attribute.addConceptRole(this.setConceptRole(conceptRole));
                        }
                        attribute.setRepresentation(this.setRepresentation(attributeDto.localRepresentation, true));
                        if (attributeDto.attributeRelationship != null) {
                            if (attributeDto.attributeRelationship.primaryMeasure != null &&
                                    attributeDto.attributeRelationship.primaryMeasure.length() != 0) {
                                attribute.setPrimaryMeasureReference(attributeDto.attributeRelationship.primaryMeasure);
                                attribute.setAttachmentLevel(ATTRIBUTE_ATTACHMENT_LEVEL.OBSERVATION);
                            } else if (attributeDto.attributeRelationship.attachmentGroups != null &&
                                    attributeDto.attributeRelationship.attachmentGroups.length != 0) {
                                attribute.setAttachmentGroup(attributeDto.attributeRelationship.attachmentGroups[0]);
                                attribute.setAttachmentLevel(ATTRIBUTE_ATTACHMENT_LEVEL.GROUP);
                            } else if (attributeDto.attributeRelationship.dimensions != null) {
                                attribute.setAttachmentLevel(ATTRIBUTE_ATTACHMENT_LEVEL.DIMENSION_GROUP);
                                for (String dimension : attributeDto.attributeRelationship.dimensions)
                                    attribute.getDimensionReferences().add(dimension);
                            } else
                                attribute.setAttachmentLevel(ATTRIBUTE_ATTACHMENT_LEVEL.DATA_SET);
                        }

                        SdmxObjectsJsonV1Builder.addAnnotations(attributeDto.annotations, attribute);
                        structureMutableObject.addAttribute(attribute);
                    }
                }

                if (dataStructureDto.dataStructureComponents.dimensionList != null) {
                    Map<Integer, DimensionMutableBean> dictionary = new HashMap<>(); // TODO: @MS map with boxed Integer keys - super bad!
                    if (dataStructureDto.dataStructureComponents.dimensionList.dimensions != null) {
                        for (DataStructureDTO.Dimension dimension : dataStructureDto.dataStructureComponents.dimensionList.dimensions) {
                            DimensionMutableBean dimensionMutableObject = new DimensionMutableBeanImpl();
                            dimensionMutableObject.setId(dimension.id);
                            if (dimension.conceptRoles != null) {
                                for (String conceptRole : dimension.conceptRoles)
                                    dimensionMutableObject.getConceptRole().add(this.setConceptRole(conceptRole));
                            }
                            dimensionMutableObject.setConceptRef(this.setConceptRef(dimension.conceptIdentity));
                            dimensionMutableObject.setRepresentation(this.setRepresentation(dimension.localRepresentation, true));
                            if (dimensionMutableObject.getId().equalsIgnoreCase("FREQ"))
                                dimensionMutableObject.setFrequencyDimension(true);
                            SdmxObjectsJsonV1Builder.addAnnotations(dimension.annotations, dimensionMutableObject);
                            int position = dimension.position;
                            while (dictionary.containsKey(position))
                                ++position;
                            dictionary.put(position, dimensionMutableObject);
                        }
                    }
                    if (dataStructureDto.dataStructureComponents.dimensionList.measureDimensions != null) {
                        for (DataStructureDTO.Measuredimension measureDimension : dataStructureDto.dataStructureComponents.dimensionList.measureDimensions) {
                            DimensionMutableBean dimensionMutableObject = new DimensionMutableBeanImpl();
                            dimensionMutableObject.setId(measureDimension.id);
                            if (measureDimension.conceptRoles != null) {
                                for (String conceptRole : measureDimension.conceptRoles)
                                    dimensionMutableObject.getConceptRole().add(this.setConceptRole(conceptRole));
                            }
                            dimensionMutableObject.setMeasureDimension(true);
                            dimensionMutableObject.setConceptRef(this.setConceptRef(measureDimension.conceptIdentity));
                            dimensionMutableObject.setRepresentation(this.setRepresentation(measureDimension.localRepresentation, false));
                            SdmxObjectsJsonV1Builder.addAnnotations(measureDimension.annotations, dimensionMutableObject);
                            int position = measureDimension.position;
                            while (dictionary.containsKey(position))
                                ++position;
                            dictionary.put(position, dimensionMutableObject);
                        }
                    }
                    if (dataStructureDto.dataStructureComponents.dimensionList.timeDimensions != null) {
                        for (DataStructureDTO.Timedimension timeDimension : dataStructureDto.dataStructureComponents.dimensionList.timeDimensions) {
                            DimensionMutableBean dimensionMutableObject = new DimensionMutableBeanImpl();
                            dimensionMutableObject.setId(timeDimension.id);
                            if (timeDimension.conceptRoles != null) {
                                for (String conceptRole : timeDimension.conceptRoles)
                                    dimensionMutableObject.getConceptRole().add(this.setConceptRole(conceptRole));
                            }
                            dimensionMutableObject.setTimeDimension(true);
                            dimensionMutableObject.setConceptRef(this.setConceptRef(timeDimension.conceptIdentity));
                            dimensionMutableObject.setRepresentation(this.setRepresentation(timeDimension.localRepresentation, true));
                            SdmxObjectsJsonV1Builder.addAnnotations(timeDimension.annotations, dimensionMutableObject);
                            int position = timeDimension.position;
                            while (dictionary.containsKey(position))
                                ++position;
                            dictionary.put(position, dimensionMutableObject);
                        }
                    }

                    List<Integer> list = dictionary.keySet().stream().sorted().collect(Collectors.toList());

                    for (Integer key : list)
                        structureMutableObject.addDimension(dictionary.get(key));
                }
                if (dataStructureDto.dataStructureComponents.measureList != null && dataStructureDto.dataStructureComponents.measureList.primaryMeasure != null) {
                    PrimaryMeasureMutableBean primaryMeasure = new PrimaryMeasureMutableBeanImpl();
                    primaryMeasure.setConceptRef(this.setConceptRef(dataStructureDto.dataStructureComponents.measureList.primaryMeasure.conceptIdentity));
                    primaryMeasure.setRepresentation(this.setRepresentation(dataStructureDto.dataStructureComponents.measureList.primaryMeasure.localRepresentation, true));
                    structureMutableObject.setPrimaryMeasure(primaryMeasure);
                    SdmxObjectsJsonV1Builder.addAnnotations(dataStructureDto.dataStructureComponents.measureList.primaryMeasure.annotations, structureMutableObject.getMeasureList().getPrimaryMeasure());
                }
                if (dataStructureDto.dataStructureComponents.groups != null) {
                    for (DataStructureDTO.Group groupDto : dataStructureDto.dataStructureComponents.groups) {
                        GroupMutableBean group = new GroupMutableBeanImpl();
                        group.setId(groupDto.id);

                        for (String groupDimension : groupDto.groupDimensions)
                            group.getDimensionRef().add(groupDimension);
                        structureMutableObject.addGroup(group);
                    }
                }
            }
            beans.addDataStructure(structureMutableObject.getImmutableInstance());
        }
    }

    private StructureReferenceBean setConceptRole(String urn) {
        return new StructureReferenceBeanImpl(urn);
    }

    private StructureReferenceBean setConceptRef(String conceptIdentity) {
        if (conceptIdentity == null || conceptIdentity.length() == 0)
            return null;

        String[] strArray1 = conceptIdentity.split("=");
        if (strArray1.length <= 1)
            return null;

//        strArray1[1] = strArray1[1]
//                .replace('(', '+')
//                .replace(')', '+')
//                .replace(':', '+');
        String[] strArray2 = strArray1[1].split("(\\()|(\\))|(:)");
        String agencyId = strArray2[0];
        String maintainableId = strArray2[1];
        String version = strArray2[2];
        String id = null;
        if (strArray2.length > 3)
            id = strArray2[3].replace(".", "");
        return new StructureReferenceBeanImpl(agencyId, maintainableId, version, SDMX_STRUCTURE_TYPE.CONCEPT, id);
    }

    private RepresentationMutableBean setRepresentation(
            DataStructureDTO.Localrepresentation representation,
            boolean codeList) {
        if (representation != null) {
            RepresentationMutableBean result = new RepresentationMutableBeanImpl();

            if (representation.enumeration != null && representation.enumeration.length() > 0) {
                String[] strArray1 = representation.enumeration.split("=");
                if (strArray1.length > 1) {
                    //strArray1[1] = strArray1[1].Replace('(', '+').Replace(')', '+').Replace(':', '+');
                    String[] strArray2 = strArray1[1].split("(\\()|(\\))|(:)");//strArray1[1].Split('+');
                    String agencyId = strArray2[0];
                    String maintainableId = strArray2[1];
                    String version = strArray2[2];

                    result.setRepresentation(new StructureReferenceBeanImpl(agencyId, maintainableId, version, codeList ? SDMX_STRUCTURE_TYPE.CODE_LIST : SDMX_STRUCTURE_TYPE.CONCEPT_SCHEME));
                }
            }

            if (representation.textFormat != null) {
                TextFormatMutableBeanImpl textFormat = new TextFormatMutableBeanImpl();
                textFormat.setMultiLingual(TERTIARY_BOOL.parseBoolean(representation.textFormat.isMultiLingual));
                textFormat.setTextType(TextTypeUtil.getTextType(DataType.Enum.forString(representation.textFormat.textType)));
                textFormat.setSequence(TERTIARY_BOOL.parseBoolean(representation.textFormat.isSequence));
                textFormat.setMinLength(representation.textFormat.minLength);
                textFormat.setMaxLength(representation.textFormat.maxLength);
                textFormat.setStartValue(representation.textFormat.startValue);
                textFormat.setEndValue(representation.textFormat.endValue);
                textFormat.setInterval(representation.textFormat.interval);
                textFormat.setMaxValue(representation.textFormat.maxValue);
                textFormat.setMinValue(representation.textFormat.minValue);
                textFormat.setTimeInterval(representation.textFormat.timeInterval);
                textFormat.setDecimals(representation.textFormat.decimals);
                textFormat.setPattern(representation.textFormat.pattern);
                result.setTextFormat(textFormat);
            }

            return result;
        }
        return null;
    }

    private void processAnnotations(JsonNode data, AnnotableMutableBean annotableMutableObject) {
        JsonNode annotationsNode = data.get("annotations");

        if (annotationsNode == null || annotableMutableObject == null)
            return;

        for (JsonNode jobject : annotationsNode) {
            AnnotationMutableBean annotation = new AnnotationMutableBeanImpl();
            JsonNode node = jobject.get("id");
            if (node != null)
                annotation.setId(node.textValue());
            node = jobject.get("title");
            if (node != null)
                annotation.setTitle(node.textValue());
            node = jobject.get("type");
            if (node != null)
                annotation.setType(node.textValue());
            node = jobject.get("texts");
            if (node != null) {
                annotation.setText(new ArrayList<>());
                this.setLocalTexts(node, annotation.getText());
            }
            //node = jobject.get("links");
            annotableMutableObject.addAnnotation(annotation);
        }
    }

    private void setLocalTexts(
            JsonNode data,
            List<TextTypeWrapperMutableBean> textTypeWrapper) {
        if (data == null || textTypeWrapper == null)
            return;

        for (Iterator<Map.Entry<String, JsonNode>> it = data.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> field = it.next();
            textTypeWrapper.add(new TextTypeWrapperMutableBeanImpl(field.getKey(), field.getValue().textValue()));
        }
    }

    private void processAgencySchemes(JsonNode agencySchemes, SdmxBeans beans) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (agencySchemes == null || beans == null)
            return;
        HashSet<String> uriSet = new HashSet<>();
        for (JsonNode agencyScheme : agencySchemes) {
            AgencySchemeMutableBean schemeMutableCore = new AgencySchemeMutableBeanImpl();
            SdmxObjectsJsonV1Builder.Core core = this.processMaintainableMutableObject(agencyScheme, schemeMutableCore);
            JsonNode node = agencyScheme.get("isPartial");
            if (node != null)
                schemeMutableCore.setPartial(node.booleanValue());
            this.processItems(agencyScheme.get("agencies"), "agencies", schemeMutableCore.getItems(), AgencyMutableBeanImpl.class);
            try {
                this.addIfNotDuplicateURN(beans, uriSet, new AgencySchemeBeanImpl(schemeMutableCore));
            } catch (Exception ex) {
                String agencyId = core.agencyID;
                String id = core.identifiable.getId();
                String version = core.version;
                throw new MaintainableBeanException(ex, SDMX_STRUCTURE_TYPE.AGENCY_SCHEME, agencyId, id, version);
            }
        }
    }

    private void processConceptSchemes(JsonNode conceptSchemes, SdmxBeans beans) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (conceptSchemes == null || beans == null)
            return;
        HashSet<String> uriSet = new HashSet<>();
        for (JsonNode conceptScheme : conceptSchemes) {
            ConceptSchemeMutableBean schemeMutableCore = new ConceptSchemeMutableBeanImpl();
            SdmxObjectsJsonV1Builder.Core core = this.processMaintainableMutableObject(conceptScheme, schemeMutableCore);
            JsonNode node = conceptScheme.get("isPartial");
            if (node != null)
                schemeMutableCore.setPartial(node.booleanValue());
            this.processItems(conceptScheme.get("concepts"), "concepts", schemeMutableCore.getItems(), ConceptMutableBeanImpl.class);
            try {
                this.addIfNotDuplicateURN(beans, uriSet, new ConceptSchemeBeanImpl(schemeMutableCore));
            } catch (Exception ex) {
                String agencyId = core.agencyID;
                String id = core.identifiable.getId();
                String version = core.version;
                throw new MaintainableBeanException(ex, SDMX_STRUCTURE_TYPE.CONCEPT_SCHEME, agencyId, id, version);
            }
        }
    }

    private void processCategorisations(JsonNode categorisations, SdmxBeans beans) {
        HashSet<String> uriSet = new HashSet<>();
        if (categorisations == null || beans == null)
            return;
        for (JsonNode categorisation : categorisations) {
            CategorisationMutableBean categorisationMutableCore = new CategorisationMutableBeanImpl();
            SdmxObjectsJsonV1Builder.Core core = this.processMaintainableMutableObject(categorisation, categorisationMutableCore);
            JsonNode node = categorisation.get("target");
            if (node != null)
                categorisationMutableCore.setCategoryReference(new StructureReferenceBeanImpl(node.textValue()));
            node = categorisation.get("source");
            if (node != null)
                categorisationMutableCore.setStructureReference(new StructureReferenceBeanImpl(node.textValue()));
            try {
                this.addIfNotDuplicateURN(beans, uriSet, new CategorisationBeanImpl(categorisationMutableCore));
            } catch (Exception ex) {
                String agencyId = core.agencyID;
                String id = core.identifiable.getId();
                String version = core.version;
                throw new MaintainableBeanException(ex, SDMX_STRUCTURE_TYPE.CATEGORISATION, agencyId, id, version);
            }
        }
    }

    private void processCategorySchemes(JsonNode categorySchemes, SdmxBeans beans) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (categorySchemes == null || beans == null)
            return;
        HashSet<String> uriSet = new HashSet<>();
        for (JsonNode categoryScheme : categorySchemes) {
            CategorySchemeMutableBean schemeMutableCore = new CategorySchemeMutableBeanImpl();
            SdmxObjectsJsonV1Builder.Core core = this.processMaintainableMutableObject(categoryScheme, schemeMutableCore);
            JsonNode node = categoryScheme.get("isPartial");
            if (node != null)
                schemeMutableCore.setPartial(node.booleanValue());
            this.processItems(categoryScheme.get("categories"), "categories", schemeMutableCore.getItems(), CategoryMutableBeanImpl.class);
            try {
                this.addIfNotDuplicateURN(beans, uriSet, new CategorySchemeBeanImpl(schemeMutableCore));
            } catch (Exception ex) {
                String agencyId = core.agencyID;
                String id = core.identifiable.getId();
                String version = core.version;
                throw new MaintainableBeanException(ex, SDMX_STRUCTURE_TYPE.CATEGORY_SCHEME, agencyId, id, version);
            }
        }
    }

    private void processCodeLists(JsonNode codeLists, SdmxBeans beans) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (codeLists == null || beans == null)
            return;
        HashSet<String> uriSet = new HashSet<>();
        for (JsonNode codeList : codeLists) {
            CodelistMutableBean codelistMutableCore = new CodelistMutableBeanImpl();
            SdmxObjectsJsonV1Builder.Core core = this.processMaintainableMutableObject(codeList, codelistMutableCore);
            JsonNode node = codeList.get("isPartial");
            if (node != null)
                codelistMutableCore.setIsPartial(node.booleanValue());
            this.processItems(codeList.get("codes"), "codes", codelistMutableCore.getItems(), CodeMutableBeanImpl.class);
            try {
                this.addIfNotDuplicateURN(beans, uriSet, new CodelistBeanImpl(codelistMutableCore));
            } catch (Exception ex) {
                String agencyId = core.agencyID;
                String id = core.identifiable.getId();
                String version = core.version;
                throw new MaintainableBeanException(ex, SDMX_STRUCTURE_TYPE.CODE_LIST, agencyId, id, version);
            }
        }
    }

    private void processHierarchicalCodelists(JsonNode hierarchicalCodelists, SdmxBeansImpl beans) {
        if (hierarchicalCodelists == null || beans == null)
            return;
        HashSet<String> uriSet = new HashSet<>();
        for (JsonNode hierarchicalCodelist : hierarchicalCodelists) {
            HierarchicalCodelistMutableBean bean = new HierarchicalCodelistMutableBeanImpl();
            this.processMaintainableMutableObject(hierarchicalCodelist, bean);
            this.processCodelistRef(hierarchicalCodelist.get("codelistRef"), bean);
            this.processHierarchies(hierarchicalCodelist.get("hierarchies"), bean);
            try {
                this.addIfNotDuplicateURN(beans, uriSet, new HierarchicalCodelistBeanImpl(bean));
            } catch (Exception ex) {
                String agencyId = bean.getAgencyId();
                String id = bean.getId();
                String version = bean.getVersion();
                throw new MaintainableBeanException(ex, SDMX_STRUCTURE_TYPE.CODE_LIST, agencyId, id, version);
            }
        }
    }

    private void processHierarchies(JsonNode hierarchies, HierarchicalCodelistMutableBean bean) {
        if (hierarchies == null)
            return;
        for (JsonNode hierarchy : hierarchies) {
            HierarchyMutableBean hierarchyMutableBean = new HierarchyMutableBeanImpl();
            this.processNameableMutableObject(hierarchy, hierarchyMutableBean);
            this.processHierarchyCodes(hierarchy.get("codes"), hierarchyMutableBean);
            bean.addHierarchies(hierarchyMutableBean);
        }
    }

    private void processHierarchyCodes(JsonNode hierarchyCodes, HierarchyMutableBean hierarchyMutableBean) {
        if (hierarchyCodes == null)
            return;
        for (JsonNode code : hierarchyCodes) {
            CodeRefMutableBeanImpl codeRef = new CodeRefMutableBeanImpl();
            processCodeRef(code, codeRef);
            hierarchyMutableBean.addHierarchicalCodeBean(codeRef);
        }
    }

    private void processHierarchyCodes(JsonNode hierarchyCodes, CodeRefMutableBean codeRefMutableBean) {
        if (hierarchyCodes == null)
            return;
        for (JsonNode code : hierarchyCodes) {
            CodeRefMutableBeanImpl codeRef = new CodeRefMutableBeanImpl();
            processCodeRef(code, codeRef);
            codeRefMutableBean.addCodeRef(codeRef);
        }
    }

    private void processCodeRef(JsonNode codeRef, CodeRefMutableBean codeRefMutableBean) {
        this.processIdentifiable(codeRef, codeRefMutableBean);
        final JsonNode level = codeRef.get("level");
        codeRefMutableBean.setLevelReference(level != null ? level.textValue() : null);
        final JsonNode codeId = codeRef.get("codeId");
        codeRefMutableBean.setCodeId(codeId != null ? codeId.textValue() : null);
        final JsonNode codelistAliasRef = codeRef.get("codelistAliasRef");
        codeRefMutableBean.setCodelistAliasRef(codelistAliasRef != null ? codelistAliasRef.textValue() : null);
        final JsonNode code = codeRef.get("code");
        codeRefMutableBean.setCodeReference(code != null ? new StructureReferenceBeanImpl(code.textValue()) : null);
        this.processHierarchyCodes(codeRef.get("codes"), codeRefMutableBean);
    }

    private void processCodelistRef(JsonNode codelistRefs, HierarchicalCodelistMutableBean bean) {
        if (codelistRefs == null)
            return;
        for (JsonNode ref : codelistRefs) {
            CodelistRefMutableBean refMutableBean = new CodelistRefMutableBeanImpl();
            refMutableBean.setAlias(ref.get("alias").textValue());
            refMutableBean.setCodelistReference(new StructureReferenceBeanImpl(ref.get("structureReference").textValue()));
            bean.addCodelistRef(refMutableBean);
        }
    }

    private void processIdentifiable(JsonNode identifiable, IdentifiableMutableBean bean) {
        this.processAnnotations(identifiable, bean);
        final JsonNode id = identifiable.get("id");
        if (id != null) {
            bean.setId(id.textValue());
        }
        final JsonNode uri = identifiable.get("uri");
        if (uri != null) {
            bean.setUri(uri.textValue());
        }
    }

    private <T extends ItemMutableBean, V extends T> void processItems(JsonNode items, String type, List<T> itemScheme, Class<V> clazz)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
//    where T : class, IItemMutableObject
//    where V : T, new()
    {
        if (items == null || itemScheme == null)
            return;
        for (JsonNode jtoken : items) {
            T obj = clazz.getDeclaredConstructor().newInstance();
            if (obj != null) {
                SdmxObjectsJsonV1Builder.IdentifiableObject identifiableObject = new SdmxObjectsJsonV1Builder.IdentifiableObject(jtoken, obj.getStructureType());
                obj.setId(identifiableObject.getId());
                obj.setUri(identifiableObject.getUri());
                this.processNameableMutableObject(jtoken, obj);
                if (obj instanceof ConceptMutableBean)
                    this.processConceptSpecific(jtoken, (ConceptMutableBean) obj);
                JsonNode node = jtoken.get("parent");
                if (obj instanceof CodeMutableBean && node != null)
                    ((CodeMutableBean) obj).setParentCode(node.textValue());
                //HierarchicalItemMutableBean<T> itemMutableObject = (HierarchicalItemMutableBean<T>) obj;
                node = jtoken.get(type);
                if (obj instanceof HierarchicalItemMutableBean && node != null)
                    this.processItems(node, type, ((HierarchicalItemMutableBean<T>) obj).getItems(), clazz);
                itemScheme.add(obj);
            }
        }
    }

    private void processConceptSpecific(
            JsonNode conceptData,
            ConceptMutableBean conceptMutableObject) {
        if (conceptData == null || conceptMutableObject == null)
            return;
        JsonNode jtoken = conceptData.get("coreRepresentation");
        if (jtoken != null) {
            RepresentationMutableBean representation = new RepresentationMutableBeanImpl();
            JsonNode node = jtoken.get("enumeration");
            if (node != null)
                representation.setRepresentation(new StructureReferenceBeanImpl(node.textValue()));
            node = jtoken.get("textFormat");
            if (node != null) {
                TextFormatMutableBeanImpl textFormat = new TextFormatMutableBeanImpl();
                this.processTextFormat(node, textFormat);
                representation.setTextFormat(textFormat);
            }

            conceptMutableObject.setCoreRepresentation(representation);
        }

        JsonNode node = conceptData.get("isoConceptReference");
        if (node != null)
            conceptMutableObject.setIsoConceptReference(new StructureReferenceBeanImpl(node.textValue()));
        node = conceptData.get("parent");
        if (node != null)
            conceptMutableObject.setParentConcept(node.textValue());
    }

    private void processTextFormat(
            JsonNode textFormatData,
            TextFormatMutableBean textFormatMutableObject) {
        if (textFormatData == null || textFormatMutableObject == null)
            return;

        JsonNode node = textFormatData.get("textType");
        if (node != null)
            textFormatMutableObject.setTextType(TextTypeUtil.getTextType(DataType.Enum.forString(node.textValue())));
        node = textFormatData.get("decimals");
        if (node != null)
            textFormatMutableObject.setDecimals(node.bigIntegerValue());
        node = textFormatData.get("startValue");
        if (node != null)
            textFormatMutableObject.setStartValue(node.decimalValue());
        node = textFormatData.get("endValue");
        if (node != null)
            textFormatMutableObject.setEndValue(node.decimalValue());
        node = textFormatData.get("interval");
        if (node != null)
            textFormatMutableObject.setInterval(node.decimalValue());
        node = textFormatData.get("isSequence");
        if (node != null)
            textFormatMutableObject.setSequence(TERTIARY_BOOL.parseBoolean(node.asBoolean()));
        node = textFormatData.get("maxLength");
        if (node != null)
            textFormatMutableObject.setMaxLength(node.bigIntegerValue());
        node = textFormatData.get("minLength");
        if (node != null)
            textFormatMutableObject.setMinLength(node.bigIntegerValue());
        node = textFormatData.get("pattern");
        if (node != null)
            textFormatMutableObject.setPattern(node.textValue());
        node = textFormatData.get("minValue");
        if (node != null)
            textFormatMutableObject.setMinValue(node.decimalValue());
        node = textFormatData.get("maxValue");
        if (node != null)
            textFormatMutableObject.setMaxValue(node.decimalValue());
        // @MS not present in java?
//        node = textFormatData.get("startTime");
//        if (node != null)
//            textFormatMutableObject.StartTime = (ISdmxDate) this.getSdmxDate(textFormatData.Value<DateTime?>((object) "startTime"), TimeFormatEnumType.DateTime);
//        node = textFormatData.get("endTime");
//        if (node != null)
//            textFormatMutableObject.EndTime = (ISdmxDate) this.getSdmxDate(textFormatData.Value<DateTime?>((object) "endTime"), TimeFormatEnumType.DateTime);
        node = textFormatData.get("timeInterval");
        if (node != null)
            textFormatMutableObject.setTimeInterval(node.textValue());
        node = textFormatData.get("isMultiLingual");
        if (node != null)
            textFormatMutableObject.setMultiLingual(TERTIARY_BOOL.parseBoolean(node.booleanValue()));
    }

//    private SdmxDateCore getSdmxDate(
//            DateTime? dateTime,
//            TimeFormatEnumType timeFormatEnumType)
//    {
//        return new SdmxDateCore(dateTime, timeFormatEnumType);
//    }

    private void processDataflows(JsonNode dataflows, SdmxBeans beans) {
        if (dataflows == null || beans == null)
            return;
        for (JsonNode dataflow : dataflows) {
            JsonNode node = dataflow.get("structure");
            if (node != null) {
                DataflowMutableBean dataflowMutableCore = new DataflowMutableBeanImpl();
                SdmxObjectsJsonV1Builder.Core core = this.processMaintainableMutableObject(dataflow, dataflowMutableCore);
                dataflowMutableCore.setDataStructureRef(new StructureReferenceBeanImpl(node.textValue()));
                try {
                    this.addIfNotDuplicateURN(beans, new HashSet<String>(), new DataflowBeanImpl(dataflowMutableCore));
                } catch (Exception ex) {
                    String agencyId = core.agencyID;
                    String id = core.identifiable.getId();
                    String version = core.version;
                    throw new MaintainableBeanException(ex, SDMX_STRUCTURE_TYPE.DATAFLOW, agencyId, id, version);
                }
            }
        }
    }

    private void processContentConstraint(JsonNode contentConstraints, SdmxBeansImpl beans) throws JsonProcessingException {
        if (contentConstraints == null || beans == null) {
            return;
        }

        for (JsonNode contentConstraint : contentConstraints) {
            ContentConstraintMutableBean bean = new ContentConstraintMutableBeanImpl();
            SdmxObjectsJsonV1Builder.Core core = this.processMaintainableMutableObject(contentConstraint, bean);
            processConstraintAttachment(contentConstraint.get("constraintAttachment"), bean);
            processReleaseCalendar(contentConstraint.get("releaseCalendar"), bean);
            processCubeRegions(contentConstraint.get("cubeRegions"), bean);
            processMetadataTargetRegions(contentConstraint.get("metadataTargetRegions"), bean);
            try {
                addIfNotDuplicateURN(beans, new HashSet<String>(), new ContentConstraintBeanImpl(bean));
            } catch (Exception ex) {
                String agencyId = core.agencyID;
                String id = core.identifiable.getId();
                String version = core.version;
                throw new MaintainableBeanException(ex, SDMX_STRUCTURE_TYPE.AGENCY_SCHEME, agencyId, id, version);
            }
        }
    }

    private void processReleaseCalendar(JsonNode releaseCalendar, ContentConstraintMutableBean bean) {
        if (releaseCalendar == null) {
            return;
        }
        ReleaseCalendarMutableBean releaseCalendarMutableBean = new ReleaseCalendarMutableBeanImpl();
        JsonNode offset = releaseCalendar.get("offset");
        releaseCalendarMutableBean.setOffset(offset != null ? offset.textValue() : null);
        JsonNode periodicity = releaseCalendar.get("periodicity");
        releaseCalendarMutableBean.setPeriodicity(periodicity != null ? periodicity.textValue() : null);
        JsonNode tolerance = releaseCalendar.get("tolerance");
        releaseCalendarMutableBean.setTolerance(tolerance != null ? tolerance.textValue() : null);
        bean.setReleaseCalendar(releaseCalendarMutableBean);
    }

    private void processConstraintAttachment(JsonNode constraintAttachments, ContentConstraintMutableBean bean) {
        if (constraintAttachments == null) {
            return;
        }
        Set<StructureReferenceBean> structureReferenceBeans = new HashSet<>();
        for (JsonNode constraintAttachment : constraintAttachments) {
            StructureReferenceBean referenceBean = null;
            if (constraintAttachment.isArray()) {
                for(int i = 0; i < constraintAttachment.size(); i++) {
                    String value = constraintAttachment.get(i).textValue();
                    referenceBean = setConceptRef(value);
                }
            } else {
                referenceBean = setConceptRef(constraintAttachment.textValue());
            }
            structureReferenceBeans.add(referenceBean);
        }
        ConstraintAttachmentMutableBean attachmentMutableBean = new ContentConstraintAttachmentMutableBeanImpl();
        attachmentMutableBean.setStructureReference(structureReferenceBeans);
        bean.setConstraintAttachment(attachmentMutableBean);
    }

    private void processCubeRegions(JsonNode cubeRegions, ContentConstraintMutableBean bean) {
        if (cubeRegions == null) {
            return;
        }
        for (JsonNode cubeRegion : cubeRegions) {
            CubeRegionMutableBeanImpl cubeRegionMutableBean = new CubeRegionMutableBeanImpl();
            cubeRegionMutableBean.setAttributeValues(processKeyValues(cubeRegion.get("attributes")));
            cubeRegionMutableBean.setKeyValues(processKeyValues(cubeRegion.get("keyValues")));
            JsonNode isIncluded = cubeRegion.get("isIncluded");
            if (isIncluded != null) {
                boolean include = isIncluded.booleanValue();
                if (include) {
                    bean.setIncludedCubeRegion(cubeRegionMutableBean);
                } else {
                    bean.setExcludedCubeRegion(cubeRegionMutableBean);
                }
            }
        }
    }

    private List<KeyValuesMutable> processKeyValues(JsonNode keyValues) {
        if (keyValues == null) {
            return Collections.emptyList();
        }
        List<KeyValuesMutable> keyValuesMutableList = new ArrayList<>();
        for (JsonNode keyValue : keyValues) {
            KeyValuesMutable keyValuesMutable = new KeyValuesMutableImpl();
            JsonNode id = keyValue.get("id");
            keyValuesMutable.setId(id.textValue() != null ? id.textValue() : null);
            JsonNode values = keyValue.get("values");
            keyValuesMutable.setKeyValues(processValues(values));
            JsonNode cascadeValues = keyValue.get("cascadeValues");
            keyValuesMutable.setCascade(processValues(cascadeValues));
            JsonNode timeRange = keyValue.get("timeRange");
            keyValuesMutable.setTimeRange(processTimeRange(timeRange));
            keyValuesMutableList.add(keyValuesMutable);
        }

        return keyValuesMutableList;
    }

    private List<String> processValues(JsonNode valuesList) {
        if (valuesList == null) {
            return Collections.emptyList();
        }

        List<String> values = new ArrayList<>();
        for (JsonNode element : valuesList) {
            String value = element.textValue();
            if (value != null) {
                values.add(value);
            }
        }
        return values;
    }

    private TimeRangeMutableBean processTimeRange(JsonNode timeRange) {
        if (timeRange == null) {
            return null;
        }
        TimeRangeMutableBean timeRangeMutableBean = new TimeRangeMutableBeanImpl();
        JsonNode beforePeriod = timeRange.get("beforePeriod");
        setStartDate(beforePeriod, timeRangeMutableBean);
        JsonNode startPeriod = timeRange.get("startPeriod");
        setStartDate(startPeriod, timeRangeMutableBean);

        JsonNode afterPeriod = timeRange.get("afterPeriod");
        setEndDate(afterPeriod, timeRangeMutableBean);
        JsonNode endPeriod = timeRange.get("endPeriod");
        setEndDate(endPeriod, timeRangeMutableBean);

        return timeRangeMutableBean;
    }

    private void setStartDate(JsonNode node, TimeRangeMutableBean bean) {
        if (node == null) {
            return;
        }
        JsonNode period = node.get("period");
        bean.setStartDate(parseDate(period));

        JsonNode isInclusive = node.get("isInclusive");
        if (isInclusive != null) {
            bean.setIsStartInclusive(isInclusive.asBoolean());
        }
    }
    private void setEndDate(JsonNode node, TimeRangeMutableBean bean) {
        if (node == null) {
            return;
        }
        JsonNode period = node.get("period");
        bean.setEndDate(parseDate(period));

        JsonNode isInclusive = node.get("isInclusive");
        if (isInclusive != null) {
            bean.setIsEndInclusive(isInclusive.asBoolean());
        }
    }

    private Date parseDate(JsonNode period) {
        if (period == null) {
            return null;
        }
        try {
            return Date.from(Instant.parse(period.textValue()));
        } catch (Exception e) {
            return null;
        }
    }

    private void processMetadataTargetRegions(JsonNode metadataTargetRegions, ContentConstraintMutableBean bean) {
        if (metadataTargetRegions == null) {
            return;
        }
        MetadataTargetRegionMutableBean metadataTargetRegionMutableBean = new MetadataTargetRegionMutableBeanImpl();
        metadataTargetRegionMutableBean.setAttributes(processKeyValues(metadataTargetRegions.get("attributes")));
        metadataTargetRegionMutableBean.setReport("skipped");
        metadataTargetRegionMutableBean.setMetadataTarget("skipped");
        bean.setMetadataTargetRegion(metadataTargetRegionMutableBean);
    }

    private HeaderBean processHeader(JsonNode baseHeaderType) {
        String id = null;
        JsonNode node = baseHeaderType.get("id");
        if (node != null)
            id = node.textValue();

        Date prepared = null;
        node = baseHeaderType.get("prepared");
        if (node != null)
            prepared = Date.from(Instant.parse(node.textValue()));

        List<PartyBean> receiver = null;
        node = baseHeaderType.get("receiver");
        if (node != null) {
            if (node.size() > 0) {
                List<TextTypeWrapper> textTypeWrapperList = new ArrayList<>();
                receiver = new ArrayList<>();
                for (JsonNode jobject : node) {
                    //JsonNode jtoken = jobject.get("name");
                    receiver.add(new PartyBeanImpl(textTypeWrapperList, jobject.get("id").textValue(), new ArrayList<>(), null));
                }
            }
        }

        PartyBean sender = null;
        node = baseHeaderType.get("sender");
        if (node != null) {
            if (node.size() > 0) {
                //JToken jtoken = jobject["name"];
                sender = new PartyBeanImpl(null, node.get("id").textValue(), null, null);
            }
        }

        Date reportingBegin = null;
        node = baseHeaderType.get("reportingBegin");
        if (node != null)
            reportingBegin = Date.from(Instant.parse(node.textValue()));

        Date reportingEnd = null;
        node = baseHeaderType.get("reportingEnd");
        if (node != null)
            reportingEnd = Date.from(Instant.parse(node.textValue()));

        boolean test = false;
        node = baseHeaderType.get("test");
        if (node != null)
            test = node.booleanValue();

        Map<String, String> additionalAttributes = null;
        List<DatasetStructureReferenceBean> structures = null;
        StructureReferenceBean dataProviderReference = null;
        DATASET_ACTION datasetAction = null;
        String datasetId = null;
        Date embargoDate = null;
        Date extracted = null;
        List<TextTypeWrapper> name = null;
        List<TextTypeWrapper> source = null;

        return new HeaderBeanImpl(additionalAttributes, structures, dataProviderReference, datasetAction,
                id, datasetId, embargoDate, extracted, prepared, reportingBegin, reportingEnd, name, source,
                receiver, sender, test);
    }

    private SdmxObjectsJsonV1Builder.Core processMaintainableMutableObject(
            JsonNode data,
            MaintainableMutableBean mutableCore) {
        SdmxObjectsJsonV1Builder.Core core = new SdmxObjectsJsonV1Builder.Core(data);
        mutableCore.setAgencyId(core.agencyID);
        mutableCore.setFinalStructure(TERTIARY_BOOL.parseBoolean(core.isFinal));
        mutableCore.setId(core.identifiable.getId());
        if (core.startDate != null)
            mutableCore.setStartDate(Date.from(core.startDate));
        if (core.endDate != null)
            mutableCore.setEndDate(Date.from(core.endDate));
        mutableCore.setUri(core.identifiable.getUri());
        mutableCore.setVersion(core.version);
        this.processNameableMutableObject(data, mutableCore);
        return core;
    }

    private void processNameableMutableObject(JsonNode data, NameableMutableBean nameableObject) {
        if (data == null || nameableObject == null)
            return;

        this.processIdentifiable(data, nameableObject);

        this.setLocalTexts(data.get("names"), nameableObject.getNames());
        this.setLocalTexts(data.get("descriptions"), nameableObject.getDescriptions());

        // @MS: my own addition, not in C# code
        // add default name in "names" does not exist
        if (nameableObject.getNames() == null || nameableObject.getNames().size() == 0) {
            JsonNode node = data.get("name");
            if (node != null)
                nameableObject.addName("en", node.textValue());
        }

        if (nameableObject.getDescriptions() == null || nameableObject.getDescriptions().size() == 0) {
            JsonNode node = data.get("description");
            if (node != null)
                nameableObject.addDescription("en", node.textValue());
        }
    }

    private class Core {
        /**
         * The Agency id.
         */
        public String agencyID;
        /**
         * The End date.
         */
        public Instant endDate;
        /**
         * The Is final.
         */
        public Boolean isFinal;
        /**
         * The Identifiable.
         */
        public SdmxObjectsJsonV1Builder.IdentifiableObject identifiable;
        /**
         * The Start date.
         */
        public Instant startDate;
        /**
         * The Version.
         */
        public String version;
        /**
         * The Is external reference.
         */
        public Boolean isExternalReference;

        /**
         * Instantiates a new Core.
         *
         * @param jo the jo
         */
        public Core(JsonNode jo) {
            JsonNode node = jo.get("agencyID");
            if (node != null)
                this.agencyID = node.textValue();
            node = jo.get("isFinal");
            if (node != null)
                this.isFinal = node.booleanValue();
            node = jo.get("version");
            if (node != null)
                this.version = node.textValue();
            node = jo.get("validFrom");
            if (node != null)
                this.startDate = Instant.parse(node.textValue());
            node = jo.get("validTo");
            if (node != null)
                this.endDate = Instant.parse(node.textValue());
            node = jo.get("isExternalReference");
            if (node != null)
                this.isExternalReference = node.booleanValue();
            this.identifiable = new SdmxObjectsJsonV1Builder.IdentifiableObject(jo, SDMX_STRUCTURE_TYPE.ANY);
        }
    }

    private class IdentifiableObject implements IdentifiableMutableBean, AnnotableMutableBean, MutableBean {
        private final JsonNode _jidentifiableObject;
        private final SDMX_STRUCTURE_TYPE _structureType;

        /**
         * Instantiates a new Identifiable object.
         *
         * @param jo            the jo
         * @param structureType the structure type
         */
        public IdentifiableObject(JsonNode jo, SDMX_STRUCTURE_TYPE structureType) {
            this._jidentifiableObject = jo;
            this._structureType = structureType;
        }

        public List<AnnotationMutableBean> getAnnotations() {
            throw new UnsupportedOperationException();
        }

        public void setAnnotations(List<AnnotationMutableBean> value) {
            throw new UnsupportedOperationException();
        }

        public String getId() {
            return _jidentifiableObject.get("id").textValue();
        }

        public void setId(String value) {
            throw new UnsupportedOperationException();
        }

        public SDMX_STRUCTURE_TYPE getStructureType() {
            return this._structureType;
        }

        public String getUri() {
            JsonNode uri = _jidentifiableObject.get("uri");
            return uri != null ? uri.textValue() : null;
        }

        public void setUri(String value) {
            throw new UnsupportedOperationException();
        }

        public String getUrn() {
            return _jidentifiableObject.get("urn").textValue();
        }

//        public void setUrn(String value) {
//            throw new UnsupportedOperationException();
//        }


        public void addAnnotation(AnnotationMutableBean annotation) {
            throw new UnsupportedOperationException();
        }

        public AnnotationMutableBean addAnnotation(String title, String type, String url) {
            throw new UnsupportedOperationException();
        }
    }
}
