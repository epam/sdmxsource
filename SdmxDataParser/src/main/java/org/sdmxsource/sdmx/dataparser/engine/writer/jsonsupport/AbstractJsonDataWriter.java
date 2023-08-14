package org.sdmxsource.sdmx.dataparser.engine.writer.jsonsupport;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.sdmxsource.sdmx.api.constants.TIME_FORMAT;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxSuperBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.base.AnnotationBean;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.model.data.Keyable;
import org.sdmxsource.sdmx.api.model.header.DatasetHeaderBean;
import org.sdmxsource.sdmx.api.model.header.HeaderBean;
import org.sdmxsource.sdmx.api.model.superbeans.base.ComponentSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodeSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodelistSuperBean;
import org.sdmxsource.sdmx.dataparser.engine.writer.DatasetInfoDataWriterEngine;
import org.sdmxsource.sdmx.dataparser.model.DATA_POSITION;
import org.sdmxsource.sdmx.sdmxbeans.util.TextTypeUtil;
import org.sdmxsource.sdmx.util.date.DateUtil;
import org.sdmxsource.util.ObjectUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Abstract json data writer.
 */
public abstract class AbstractJsonDataWriter extends DatasetInfoDataWriterEngine {
    private static Logger LOG = LoggerFactory.getLogger(AbstractJsonDataWriter.class);

    /**
     * The Current key.
     */
    protected String currentKey;
    /**
     * The Prev key.
     */
    protected String prevKey;

    /**
     * The Json generator.
     */
    protected JsonGenerator jsonGenerator;
    /**
     * The Header.
     */
    protected HeaderBean header;

    /**
     * The Header written.
     */
    protected boolean headerWritten = false;

    /**
     * The Annotations.
     */
    protected List<AnnotationBean> annotations = new ArrayList<AnnotationBean>();

    private List<KeyValue> datasetAttributes;

    /**
     * Instantiates a new Abstract json data writer.
     *
     * @param jsonGenerator             the json generator
     * @param superBeanRetrievalManager the super bean retrieval manager
     */
    public AbstractJsonDataWriter(JsonGenerator jsonGenerator, SdmxSuperBeanRetrievalManager superBeanRetrievalManager) {
        super(superBeanRetrievalManager);
        this.jsonGenerator = jsonGenerator;
    }

    @Override
    public void startDataset(ProvisionAgreementBean prov, DataflowBean flow, DataStructureBean dsd, DatasetHeaderBean datasetHeader, AnnotationBean... ann) {
        DATA_POSITION pos = currentPosition;
        annotations = new ArrayList<AnnotationBean>();
        super.startDataset(prov, flow, dsd, datasetHeader, ann);
        for (AnnotationBean annotationBean : ann) {
            annotations.add(annotationBean);
        }
        try {
            if (pos != DATA_POSITION.DATASET) {
                if (!currentDSDSuperBean.getBuiltFrom().equals(dsd)) {
                    throw new SdmxNotImplementedException("JSON Does not support multiple datasets conforming to differnt data structures");
                }
                flushDatasetAttributes();
                flushKey();
                flushObs();
                prevKey = null;
                currentKey = null;
                if (!(this instanceof SeriesDataWriter)) { //TODO:FlatDataWriter
                    //END DataSet
                    LOG.debug("{/observations}");
                    jsonGenerator.writeEndObject(); //End observations

                    writeDatasetAttributes();

                    LOG.debug("{/dataset}");
                    jsonGenerator.writeEndObject(); //End dataset
                } else {
                    LOG.debug("{/observations}");
                    jsonGenerator.writeEndObject();  // End the current Observation object
//
                    LOG.debug("{/0:0:0}");
                    jsonGenerator.writeEndObject();  // End observations
//
                    LOG.debug("{/series}");
                    jsonGenerator.writeEndObject();  // End the current series object

                    writeDatasetAttributes();
//
                    LOG.debug("{/}");
                    jsonGenerator.writeEndObject();  //End the dataset tag
                }

            } else {
                LOG.debug("[dataSets]");

                jsonGenerator.writeArrayFieldStart("dataSets");
                // Start the JSON "datasSets" section
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        datasetAttributes = null;
        writeJsonDataSet(datasetHeader);
    }

    @Override
    protected void writeDatasetAttributes(List<KeyValue> datasetAttributes) {
        this.datasetAttributes = datasetAttributes;
    }

    /**
     * Write dataset attributes.
     *
     * @throws IOException the io exception
     */
    protected void writeDatasetAttributes() throws IOException {
        if (datasetAttributes != null) {
            LOG.debug("[attributes]");
            jsonGenerator.writeArrayFieldStart("attributes");
            for (KeyValue dsAttr : datasetAttributes) {
                jsonGenerator.writeNumber(super.getReportedIndex(dsAttr.getConcept(), dsAttr.getCode()));
            }
            LOG.debug("[/attributes]");
            jsonGenerator.writeEndArray();
        }
    }

    @Override
    protected void writeKey(Keyable key) {
        super.writeKey(key);
        StringBuilder sb = new StringBuilder();

        String delimiter = "";
        for (KeyValue kv : key.getKey()) {
            int currentIndex = getReportedIndex(kv.getConcept(), kv.getCode());
            sb.append(delimiter + currentIndex);
            delimiter = ":";
        }
        if (currentKey != null) {
            prevKey = currentKey.toString();
        }
        currentKey = sb.toString();
    }


    private void writeAnnotations() throws JsonGenerationException, IOException {
        if (ObjectUtil.validCollection(annotations)) {
            jsonGenerator.writeArrayFieldStart("annotations");

            for (AnnotationBean annotation : annotations) {
                writeAnnotation(annotation);
            }

            jsonGenerator.writeEndArray();
        }
    }

    private void writeAnnotation(AnnotationBean annotation) throws IOException {
        LOG.debug("{}");
        jsonGenerator.writeStartObject();

        if (annotation.getId() != null) {
            jsonGenerator.writeStringField("id", annotation.getId());
        }
        if (annotation.getTitle() != null) {
            jsonGenerator.writeStringField("title", annotation.getTitle());
        }
        if (annotation.getType() != null) {
            jsonGenerator.writeStringField("type", annotation.getType());
        }
        if (annotation.getUri() != null) {
            jsonGenerator.writeStringField("uri", annotation.getUri().toString());
        }

        if (annotation.getText().size() > 0) {
            List<TextTypeWrapper> text = annotation.getText();
            TextTypeWrapper defaultLocale = TextTypeUtil.getDefaultLocale(text);
            jsonGenerator.writeStringField("text", defaultLocale.getValue());
        }

        LOG.debug("{/}");
        jsonGenerator.writeEndObject();
    }

    /**
     * Write structure.
     *
     * @throws JsonGenerationException the json generation exception
     * @throws IOException             the io exception
     */
    protected void writeStructure() throws JsonGenerationException, IOException {
        LOG.debug("{structure}");
        jsonGenerator.writeObjectFieldStart("structure");
        MaintainableBean maint = flow;
        if (maint == null) {
            maint = currentDSDSuperBean.getBuiltFrom();
        }
        //		jsonGenerator.writeStringField("uri", serviceRetrievalManager.getStructureOrServiceURL(maint).getUrl().toString());
        jsonGenerator.writeStringField("name", maint.getName());
        jsonGenerator.writeStringField("description", maint.getDescription());

        writeStructureDimensions();
        writeAttributes();
        writeAnnotations();

        LOG.debug("{/structure}");
        jsonGenerator.writeEndObject();
    }


    /**
     * Write json data set.
     *
     * @param datasetHeader the dataset header
     */
    abstract protected void writeJsonDataSet(DatasetHeaderBean datasetHeader);

    /**
     * Write attributes.
     *
     * @throws JsonGenerationException the json generation exception
     * @throws IOException             the io exception
     */
    abstract protected void writeAttributes() throws JsonGenerationException, IOException;

    /**
     * Write dimensions series.
     *
     * @throws JsonGenerationException the json generation exception
     * @throws IOException             the io exception
     */
    abstract protected void writeDimensionsSeries() throws JsonGenerationException, IOException;

    /**
     * Write dimensions observation.
     *
     * @throws JsonGenerationException the json generation exception
     * @throws IOException             the io exception
     */
    abstract protected void writeDimensionsObservation() throws JsonGenerationException, IOException;


    /**
     * Writing the JSON section:
     * (root) --&gt; structure --&gt; dimensions
     *
     * @throws JsonGenerationException the json generation exception
     * @throws IOException             the io exception
     */
    protected void writeStructureDimensions() throws JsonGenerationException, IOException {
        LOG.debug("{dimensions}");
        jsonGenerator.writeObjectFieldStart("dimensions");

        writeDimensionsDataset();
        writeDimensionsSeries();
        writeDimensionsObservation();

        LOG.debug("{/dimensions}");
        jsonGenerator.writeEndObject();
    }


    private void writeDimensionsDataset() throws JsonGenerationException, IOException {
        // NOTE: In this release we are not writing this section.  Instead this information can be located under: root -> datasets
        jsonGenerator.writeArrayFieldStart("dataset");

        jsonGenerator.writeEndArray(); //End the dataset array
    }

    /**
     * Write component.
     *
     * @param component the component
     * @param position  the position
     * @throws JsonGenerationException the json generation exception
     * @throws IOException             the io exception
     */
    protected void writeComponent(ComponentSuperBean component, int position) throws JsonGenerationException, IOException {
        LOG.debug("{}");
        jsonGenerator.writeStartObject(); // Start component
        jsonGenerator.writeStringField("id", component.getId());
        jsonGenerator.writeStringField("name", component.getConcept().getName());
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
                CodelistSuperBean codelist = component.getCodelist(true);
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
}
