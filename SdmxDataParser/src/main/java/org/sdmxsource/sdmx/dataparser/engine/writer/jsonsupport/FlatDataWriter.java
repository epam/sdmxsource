package org.sdmxsource.sdmx.dataparser.engine.writer.jsonsupport;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.sdmxsource.sdmx.api.constants.ATTRIBUTE_ATTACHMENT_LEVEL;
import org.sdmxsource.sdmx.api.constants.DATASET_ACTION;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxSuperBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.base.AnnotationBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.AttributeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.model.data.Keyable;
import org.sdmxsource.sdmx.api.model.data.Observation;
import org.sdmxsource.sdmx.api.model.header.DatasetHeaderBean;
import org.sdmxsource.sdmx.api.model.header.HeaderBean;
import org.sdmxsource.sdmx.api.model.superbeans.base.ComponentSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.AttributeSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DimensionSuperBean;
import org.sdmxsource.sdmx.util.date.DateUtil;
import org.sdmxsource.util.ObjectUtil;

import java.io.IOException;

/**
 * The type Flat data writer.
 */
public class FlatDataWriter extends AbstractJsonDataWriter {
    private static Logger LOG = LoggerFactory.getLogger(FlatDataWriter.class);

    /**
     * Instantiates a new Flat data writer.
     *
     * @param jsonGenerator             the json generator
     * @param superBeanRetrievalManager the super bean retrieval manager
     */
    public FlatDataWriter(JsonGenerator jsonGenerator, SdmxSuperBeanRetrievalManager superBeanRetrievalManager) {
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
//			if(currentPosition != DATA_POSITION.DATASET) {
//				if(!currentDSDSuperBean.getBuiltFrom().equals(dsd)) {
//					throw new SdmxNotImplementedException("JSON Does not support multiple datasets conforming to differnt data structures");
//				}
//				flushDatasetAttributes();
//				flushKey();
//				flushObs();
//				
//				//END DataSet
//				LOG.debug("{/observations}");
//				jsonGenerator.writeEndObject(); //End observations
//				
//				LOG.debug("{/dataset}");
//				jsonGenerator.writeEndObject(); //End dataset
//			} else {
//				LOG.debug("[dataSets]");
//				jsonGenerator.writeArrayFieldStart("dataSets"); // Start the JSON "datasSets" section
//			}


            LOG.debug("{}");
            jsonGenerator.writeStartObject();  //Start Dataset
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

            // This is flat, so do not write a series tag but go straight into the observations tag
            LOG.debug("{observations}");
            jsonGenerator.writeObjectFieldStart("observations");

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
    }

    private void writeJsonObs(String currentKey, String prevKey, Observation obs) {
        String seriesKey = currentKey + ":" + getReportedIndex(DimensionBean.TIME_DIMENSION_FIXED_ID, obs.getObsTime());
        writeJsonObs(obs, seriesKey);
    }

    private void writeJsonObs(Observation obs, String seriesKey) {
        try {
            LOG.debug("[" + seriesKey + "]");

            jsonGenerator.writeArrayFieldStart(seriesKey);

            // Write the observation value as the first array field
            jsonGenerator.writeString(obs.getObservationValue());

            // Write the attribute values (or null)
            for (AttributeSuperBean attr : currentDSDSuperBean.getAttributes()) {
                if (attr.getAttachmentLevel() == ATTRIBUTE_ATTACHMENT_LEVEL.DATA_SET) {
                    continue;
                }
                KeyValue kv = obs.getAttribute(attr.getId());
                if (kv == null) {
                    kv = obs.getSeriesKey().getAttribute(attr.getId());
                }
                String attrValue = kv != null ? kv.getCode() : null;
                if (attrValue == null) {
                    jsonGenerator.writeNull();
                } else {
                    int idx = getReportedIndex(attr.getId(), attrValue);
                    jsonGenerator.writeNumber(idx);
                }
            }

            // Write the annotation values
            for (AnnotationBean currentAnnotation : obs.getSeriesKey().getAnnotations()) {
                if (!annotations.contains(currentAnnotation)) {
                    annotations.add(currentAnnotation);
                }
                int idx = annotations.indexOf(currentAnnotation);
                jsonGenerator.writeNumber(idx);
            }
            for (AnnotationBean currentAnnotation : obs.getAnnotations()) {
                if (!annotations.contains(currentAnnotation)) {
                    annotations.add(currentAnnotation);
                }
                int idx = annotations.indexOf(currentAnnotation);
                jsonGenerator.writeNumber(idx);
            }
            LOG.debug("[/'" + seriesKey + "]");
            jsonGenerator.writeEndArray();
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }


    // Write: root -> structure -> dimensions -> series
    @Override
    protected void writeDimensionsSeries() throws JsonGenerationException, IOException {
        // Flat data types do not need an entry in the series
        jsonGenerator.writeArrayFieldStart("series");
        jsonGenerator.writeEndArray(); //End series array
    }

    // Write structure -> dimensions -> observations
    @Override
    protected void writeDimensionsObservation() throws JsonGenerationException, IOException {
        LOG.debug("[observation]");
        jsonGenerator.writeArrayFieldStart("observation");

        int i = 0;
        DimensionSuperBean timeDimension = null;
        for (DimensionSuperBean dimension : currentDSDSuperBean.getDimensions()) {
            if (dimension.isTimeDimension()) {
                // Always write the Time Dimension at the end
                timeDimension = dimension;
            } else {
                writeComponent(dimension, i);
                i++;
            }
        }

        if (timeDimension != null) {
            writeComponent(timeDimension, i);
        }

        jsonGenerator.writeEndArray(); //End observation array
        LOG.debug("[/observation]");
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
        jsonGenerator.writeEndArray(); //End series array

        LOG.debug("[observation]");
        jsonGenerator.writeArrayFieldStart("observation");
        for (AttributeSuperBean attribuet : currentDSDSuperBean.getAttributes()) {
            if (attribuet.getAttachmentLevel() == ATTRIBUTE_ATTACHMENT_LEVEL.DATA_SET) {
                continue;
            }
            writeComponent(attribuet, -1);
        }
        LOG.debug("[/observation]");
        jsonGenerator.writeEndArray(); //End observation array
        LOG.debug("{/attributes}");
        jsonGenerator.writeEndObject(); //End attributes
    }


    @Override
    public void close(FooterMessage... footer) {
        super.close(footer);

        try {
            LOG.debug("{/observations}");
            jsonGenerator.writeEndObject();  // End observations
            writeDatasetAttributes();

            LOG.debug("{/}");
            jsonGenerator.writeEndObject(); //End dataset


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
}
