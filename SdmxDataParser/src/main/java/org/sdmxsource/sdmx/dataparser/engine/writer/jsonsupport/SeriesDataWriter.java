package org.sdmxsource.sdmx.dataparser.engine.writer.jsonsupport;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.log4j.Logger;
import org.sdmxsource.sdmx.api.constants.DATASET_ACTION;
import org.sdmxsource.sdmx.api.constants.TIME_FORMAT;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxSuperBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.base.AnnotationBean;
import org.sdmxsource.sdmx.api.model.beans.base.ComponentBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.AttributeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.model.data.Keyable;
import org.sdmxsource.sdmx.api.model.data.Observation;
import org.sdmxsource.sdmx.api.model.header.DatasetHeaderBean;
import org.sdmxsource.sdmx.api.model.header.HeaderBean;
import org.sdmxsource.sdmx.api.model.superbeans.base.ComponentSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodeSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodelistSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DimensionSuperBean;
import org.sdmxsource.sdmx.util.date.DateUtil;
import org.sdmxsource.util.ObjectUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The type Series data writer.
 */
public class SeriesDataWriter extends AbstractJsonDataWriter {
    private static Logger LOG = Logger.getLogger(SeriesDataWriter.class);
    private boolean observationsKeyOpened;

    /**
     * Instantiates a new Series data writer.
     *
     * @param jsonGenerator             the json generator
     * @param superBeanRetrievalManager the super bean retrieval manager
     */
    public SeriesDataWriter(JsonGenerator jsonGenerator, SdmxSuperBeanRetrievalManager superBeanRetrievalManager) {
        super(jsonGenerator, superBeanRetrievalManager);
    }

    @Override
    public void writeHeader(HeaderBean header) {
        super.writeHeader(header);
        // Don't attempt to write the JSON for the header yet
    }

    // Write: root -> dataSets
    @Override
    protected void writeJsonDataSet(DatasetHeaderBean datasetHeader) {
        try {


            LOG.debug("{}");
            jsonGenerator.writeStartObject();
            DATASET_ACTION action = DATASET_ACTION.INFORMATION;
            if (datasetHeader != null) {
                action = datasetHeader.getAction();
                if (datasetHeader.getReportingBeginDate() != null) {
                    jsonGenerator.writeStringField("reportingBegin", DateUtil.formatDate(datasetHeader.getReportingBeginDate()));
                }
                if (datasetHeader.getReportingEndDate() != null) {
                    jsonGenerator.writeStringField("reportingEnd", DateUtil.formatDate(datasetHeader.getReportingEndDate()));
                }
                if (datasetHeader.getValidFrom() != null) {
                    jsonGenerator.writeStringField("validFrom", DateUtil.formatDate(datasetHeader.getValidFrom()));
                }
                if (datasetHeader.getValidTo() != null) {
                    jsonGenerator.writeStringField("validTo", DateUtil.formatDate(datasetHeader.getValidTo()));
                }
                if (datasetHeader.getPublicationYear() > 0) {
                    jsonGenerator.writeNumberField("publicationYear", datasetHeader.getPublicationYear());
                }
                if (ObjectUtil.validString(datasetHeader.getPublicationPeriod())) {
                    jsonGenerator.writeStringField("publicationPeriod", datasetHeader.getPublicationPeriod());
                }
            }

            jsonGenerator.writeStringField("action", action.getAction());

            if (ObjectUtil.validCollection(annotations)) {
                LOG.debug("[annotations]");
                jsonGenerator.writeArrayFieldStart("annotations");

                // Write the annotation values
                for (AnnotationBean currentAnnotation : annotations) {
                    if (!annotations.contains(currentAnnotation)) {
                        annotations.add(currentAnnotation);
                    }
                    int idx = annotations.indexOf(currentAnnotation);
                    jsonGenerator.writeNumber(idx);
                }
                LOG.debug("[/annotations]");
                jsonGenerator.writeEndArray();
            }

            // Non-flat now needs the series tag
            LOG.debug("{series}");
            jsonGenerator.writeObjectFieldStart("series");
        } catch (Throwable th) {
            throw new RuntimeException(th);

        }


    }

    @Override
    protected void writeKey(Keyable key) {

        if (key.isSeries()) {
            super.writeKey(key);
        }
    }

    @Override
    protected void writeObs(Observation obs) {

        writeJsonObs(currentKey, prevKey, obs);
        prevKey = currentKey;
    }

    private void writeJsonObs(String currentKey, String prevKey, Observation obs) {
        int idx = getReportedIndex(dimensionAtObservation, obs.getObsTime());

        if (!currentKey.equals(prevKey)) {
            writeSeriesBlock(currentKey, prevKey, obs, obs.getSeriesKey());
        }

        writeJsonObs(obs, idx);
    }

    private void writeSeriesBlock(String currentKey, String prevKey, Observation obs, Keyable series) {
        try {
            // Close the previous key if there was one open
            tryClosePrevSeries();
            this.prevKey = currentKey;

            String seriesIndex = currentKey;

            LOG.debug("{<seriesIndex>}");
            jsonGenerator.writeObjectFieldStart("" + seriesIndex);   // Start Series

            LOG.debug("[attributes]");
            jsonGenerator.writeArrayFieldStart("attributes");

            for (AttributeBean attr : currentDSDSuperBean.getBuiltFrom().getSeriesAttributes(dimensionAtObservation)) {
                KeyValue kv = null;
                if (obs != null)
                    kv = obs.getAttribute(attr.getId());
                if (kv == null && series != null) {
                    kv = series.getAttribute(attr.getId());
                }
                if (kv == null) {
                    jsonGenerator.writeNull();
                } else {
                    int reportedIndex = getReportedIndex(kv.getConcept(), kv.getCode());
                    jsonGenerator.writeNumber(reportedIndex);
                }
            }
            LOG.debug("[/attributes]");
            jsonGenerator.writeEndArray();

            List<AnnotationBean> annotations = series != null ? series.getAnnotations() : null;
            if (ObjectUtil.validCollection(annotations)) {
                LOG.debug("[annotations]");
                jsonGenerator.writeArrayFieldStart("annotations");
                // Write the annotation values
                for (AnnotationBean currentAnnotation : series.getAnnotations()) {
                    if (!super.annotations.contains(currentAnnotation)) {
                        super.annotations.add(currentAnnotation);
                    }
                    int index = annotations.indexOf(currentAnnotation);
                    jsonGenerator.writeNumber(index);
                }
                LOG.debug("[/annotations]");
                jsonGenerator.writeEndArray();
            }

            LOG.debug("{observations}");
            jsonGenerator.writeObjectFieldStart("observations");

            observationsKeyOpened = true;

        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    private boolean tryClosePrevSeries() throws IOException {
        if (observationsKeyOpened) {
            LOG.debug("{/observations}");
            jsonGenerator.writeEndObject();  // End Observations
            LOG.debug("{/<seriesIndex>}");
            jsonGenerator.writeEndObject();  // End Series
            observationsKeyOpened = false;
            return true;
        }
        return false;
    }

    private void writeJsonObs(Observation obs, int idx) {
        String seriesKey = "" + idx;
        try {
            LOG.debug("[obs key: '" + seriesKey + "']");
            jsonGenerator.writeArrayFieldStart(seriesKey);

            // Write the observation value as the first array field
            jsonGenerator.writeString(obs.getObservationValue());

            // Write the attribute values
            List<Integer> obsAttrs = new ArrayList<Integer>();
            List<AttributeBean> attr = currentDSDSuperBean.getBuiltFrom().getObservationAttributes(dimensionAtObservation);
            for (AttributeBean currentAttr : attr) {
                KeyValue kv = obs.getAttribute(currentAttr.getId());
                if (kv == null) {
                    kv = obs.getSeriesKey().getAttribute(currentAttr.getId());
                }
                String attrValue = kv != null ? kv.getCode() : null;
                if (attrValue == null) {
                    obsAttrs.add(null);
                } else {
                    int index = getReportedIndex(currentAttr.getId(), attrValue);
                    obsAttrs.add(index);
                }
            }

            // Only write out the attributes, if they are not all null and there are no annotations following
            if (ObjectUtil.validCollection(obsAttrs)) {
                if (!ObjectUtil.isAllNulls(obsAttrs) || obs.getAnnotations().size() > 0) {
                    for (Integer attrIdx : obsAttrs) {
                        if (attrIdx == null) {
                            jsonGenerator.writeNull();
                        } else {
                            jsonGenerator.writeNumber(attrIdx);
                        }
                    }
                }
            }

            for (AnnotationBean currentAnnotation : obs.getAnnotations()) {
                if (!annotations.contains(currentAnnotation)) {
                    annotations.add(currentAnnotation);
                }
                int index = annotations.indexOf(currentAnnotation);
                jsonGenerator.writeNumber(index);
            }
            LOG.debug("[/'variable series key']");
            jsonGenerator.writeEndArray();
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    @Override
    public void close(FooterMessage... footer) {
        super.close(footer);

        try {

            if (!Objects.equals(currentKey, prevKey)) {
                writeSeriesBlock(currentKey, prevKey, null, null);
                prevKey = currentKey;
            }

            tryClosePrevSeries();

            LOG.debug("{/}");
            jsonGenerator.writeEndObject();  // End the current series object

            writeDatasetAttributes();

            LOG.debug("{/series}");
            jsonGenerator.writeEndObject();  //End the series tag


            LOG.debug("[/dataSets]");
            jsonGenerator.writeEndArray();  // End dataSets array

            writeStructure();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        } finally {
            try {
                jsonGenerator.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    // Write: root -> structure -> dimensions -> series
    @Override
    protected void writeDimensionsSeries() throws JsonGenerationException, IOException {
        LOG.debug("[series]");
        jsonGenerator.writeArrayFieldStart("series");

        int i = 0;
        DimensionSuperBean timeDimension = null;
        for (DimensionSuperBean dimension : currentDSDSuperBean.getDimensions()) {
            if (dimension.getId().equals(dimensionAtObservation)) {
                continue;
            }
            if (dimension.isTimeDimension()) {
                // Always write the time dimension at the end
                timeDimension = dimension;
            } else {
                writeComponent(dimension, i);
                i++;
            }
        }

        if (timeDimension != null) {
            writeComponent(timeDimension, i);
        }

        LOG.debug("[/series]");
        jsonGenerator.writeEndArray(); //End series array
    }

    // Write: root -> structure -> dimensions -> observations
    @Override
    protected void writeDimensionsObservation() throws JsonGenerationException, IOException {
        LOG.debug("[observation]");
        jsonGenerator.writeArrayFieldStart("observation");

        DimensionBean dimension = dsd.getDimension(dimensionAtObservation);
        DimensionSuperBean superBean = currentDSDSuperBean.getDimensionById(dimensionAtObservation);
        // Note that a Dimensions' position is 1 indexed, but we need it 0-indexed, so subtract 1
        writeComponent(dimension, superBean, dimension.getPosition() - 1);

        LOG.debug("[/observation]");
        jsonGenerator.writeEndArray(); //End observation array
    }

    private void writeComponent(ComponentBean component, ComponentSuperBean superBean, int position) throws JsonGenerationException, IOException {
        LOG.debug("{}");
        jsonGenerator.writeStartObject(); //Start component
        jsonGenerator.writeStringField("id", component.getId());
        jsonGenerator.writeStringField("name", superBean.getConcept().getName());

        if (position >= 0) {
            jsonGenerator.writeNumberField("keyPosition", position);
        }

        boolean isTime = component.getId().equals(DimensionBean.TIME_DIMENSION_FIXED_ID);
        if (isTime) {
            jsonGenerator.writeStringField("role", "time");
        } else {
            jsonGenerator.writeNullField("role");
        }

        LOG.debug("[values]");
        jsonGenerator.writeArrayFieldStart("values");
        List<String> allCodes = getReportedValues(component.getId());
        for (String currentCode : allCodes) {
            LOG.debug("{}");
            jsonGenerator.writeStartObject();  //Start code
            String id = currentCode;
            String name = currentCode;
            if (isTime) {
                String start = DateUtil.formatDate(DateUtil.formatDate(currentCode, true), TIME_FORMAT.DATE_TIME);
                String end = DateUtil.formatDate(DateUtil.formatDate(currentCode, false), TIME_FORMAT.DATE_TIME);
                jsonGenerator.writeStringField("start", start);
                jsonGenerator.writeStringField("end", end);
            } else {
                CodelistSuperBean codelist = superBean.getCodelist(true);
                if (codelist != null) {
                    CodeSuperBean code = codelist.getCodeByValue(currentCode);
                    if (code != null) {
                        id = code.getId();
                        name = code.getName();
                    }
                }
            }
            jsonGenerator.writeStringField("id", id);
            jsonGenerator.writeStringField("name", name);
            LOG.debug("{/}");
            jsonGenerator.writeEndObject(); //End code
        }

        LOG.debug("[/values]");
        jsonGenerator.writeEndArray();
        LOG.debug("{/}");
        jsonGenerator.writeEndObject(); // End component
    }

    @Override
    protected void writeAttributes() throws JsonGenerationException, IOException {
        LOG.debug("{attributes}");
        jsonGenerator.writeObjectFieldStart("attributes");

        jsonGenerator.writeArrayFieldStart("dataset");
        for (AttributeBean attribute : dsd.getDatasetAttributes()) {
            ComponentSuperBean attrSb = currentDSDSuperBean.getComponentById(attribute.getId());
            writeComponent(attrSb, -1);
        }
        jsonGenerator.writeEndArray(); //End dataset array

        jsonGenerator.writeArrayFieldStart("series");
        for (AttributeBean attribute : dsd.getSeriesAttributes(dimensionAtObservation)) {
            ComponentSuperBean attrSb = currentDSDSuperBean.getComponentById(attribute.getId());
            writeComponent(attrSb, -1);


        }
        jsonGenerator.writeEndArray(); //End series array


        LOG.debug("[observation]");
        jsonGenerator.writeArrayFieldStart("observation");
        for (AttributeBean attribute : dsd.getObservationAttributes(dimensionAtObservation)) {
            ComponentSuperBean attrSb = currentDSDSuperBean.getComponentById(attribute.getId());
            writeComponent(attrSb, -1);
        }


        LOG.debug("[/observation]");
        jsonGenerator.writeEndArray(); //End observation array

        LOG.debug("{/attributes}");
        jsonGenerator.writeEndObject(); //End attributes
    }
}
