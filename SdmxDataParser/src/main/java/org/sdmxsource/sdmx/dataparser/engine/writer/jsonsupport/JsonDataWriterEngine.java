package org.sdmxsource.sdmx.dataparser.engine.writer.jsonsupport;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.log4j.Logger;
import org.sdmxsource.sdmx.api.constants.TIME_FORMAT;
import org.sdmxsource.sdmx.api.engine.DataWriterEngine;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxSuperBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.base.AnnotationBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.header.DatasetHeaderBean;
import org.sdmxsource.sdmx.api.model.header.DatasetStructureReferenceBean;
import org.sdmxsource.sdmx.api.model.header.HeaderBean;
import org.sdmxsource.sdmx.api.model.header.PartyBean;
import org.sdmxsource.sdmx.dataparser.engine.writer.DatasetInfoDataWriterEngine;
import org.sdmxsource.sdmx.util.date.DateUtil;
import org.sdmxsource.util.ObjectUtil;
import org.sdmxsource.util.io.StreamUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.UUID;

/**
 * The type Json data writer engine.
 */
public class JsonDataWriterEngine implements DataWriterEngine {
    private static Logger LOG = Logger.getLogger(JsonDataWriterEngine.class);

    private DatasetInfoDataWriterEngine internalWriter;
    private OutputStream out;
    private HeaderBean header;
    private boolean headerWritten = false;
    private SdmxSuperBeanRetrievalManager superBeanRetrievalManager;
    private JsonGenerator jsonGenerator;

    private boolean forceFlat;  //If true, then the output will always be flat

    /**
     * Instantiates a new Json data writer engine.
     *
     * @param out                       the out
     * @param superBeanRetrievalManager the super bean retrieval manager
     * @param forceFlat                 the force flat
     */
    public JsonDataWriterEngine(OutputStream out,
                                SdmxSuperBeanRetrievalManager superBeanRetrievalManager,
                                boolean forceFlat) {
        this.out = out;
        this.forceFlat = forceFlat;
        JsonFactory factory = new JsonFactory();
        try {
            jsonGenerator = factory.createGenerator(out, JsonEncoding.UTF8);
            jsonGenerator.writeStartObject();//The container for the message
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.superBeanRetrievalManager = superBeanRetrievalManager;
    }

    @Override
    public void writeHeader(HeaderBean header) {
        this.header = header;
        // Don't attempt to write the header yet, since the internalWriter object has not been set up.
        // The header will be written by the JSON DataWriterEngines before the first Dataset is written
    }

    @Override
    public void startDataset(ProvisionAgreementBean provision,
                             DataflowBean dataflow,
                             DataStructureBean dataStructureBean,
                             DatasetHeaderBean header,
                             AnnotationBean... annotations) {

        if (!headerWritten) {
            writeDocumentHeader();

            //End Header
            headerWritten = true;
            // Create the appropriate internal writer
            String dimensionAtObservation = null;
            if (header != null && header.getDataStructureReference() != null) {
                dimensionAtObservation = header.getDataStructureReference().getDimensionAtObservation();
            }
            if (!ObjectUtil.validString(dimensionAtObservation)) {
                dimensionAtObservation = DimensionBean.TIME_DIMENSION_FIXED_ID;
            }

            if (forceFlat || dimensionAtObservation.equals(DatasetStructureReferenceBean.ALL_DIMENSIONS)) {
                // FLAT
                internalWriter = new FlatDataWriter(jsonGenerator, superBeanRetrievalManager);
            } else {
                // SERIES
                //TODO: 'SeriesDataWriter->SeriesDataWriterRe' this replacement is reason for creating JsonDataWriterEngineRe instead of JsonDataWriterEngine
                internalWriter = new SeriesDataWriter(jsonGenerator, superBeanRetrievalManager);
            }


        }


        internalWriter.startDataset(provision, dataflow, dataStructureBean, header, annotations);
    }


    /**
     * Write document header.
     */
    protected void writeDocumentHeader() {
        try {
            String id;
            Date prepared;
            boolean isTest;
            if (header != null) {
                id = header.getId();
                prepared = header.getPrepared();
                isTest = header.isTest();
            } else {
                id = UUID.randomUUID().toString();
                prepared = new Date();
                isTest = false;
            }
            LOG.debug("{Header}");
            jsonGenerator.writeObjectFieldStart("header");
            jsonGenerator.writeStringField("id", id);
            jsonGenerator.writeStringField("prepared", DateUtil.formatDate(prepared, TIME_FORMAT.DATE_TIME));
            jsonGenerator.writeBooleanField("test", isTest);


            writeSender();
            writeReceiver();
            //			writeLinks();

            jsonGenerator.writeEndObject();
            LOG.debug("{/Header}");

            headerWritten = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeSender() throws JsonGenerationException, IOException {
        LOG.debug("{sender}");
        jsonGenerator.writeObjectFieldStart("sender");

        PartyBean sender = null;
        if (header != null) {
            sender = header.getSender();
        }
        writeContactDetails(sender);

        LOG.debug("{/sender}");
        jsonGenerator.writeEndObject();
    }

    private void writeReceiver() throws JsonGenerationException, IOException {
        if (header == null || !ObjectUtil.validCollection(header.getReceiver())) {
            // Do not write receiver since it's empty
            return;
        }

        LOG.debug("{receiver}");
        jsonGenerator.writeObjectFieldStart("receiver");

        // Simply use the first receiver
        PartyBean receiver = header.getReceiver().get(0);
        writeContactDetails(receiver);

        LOG.debug("{/receiver}");
        jsonGenerator.writeEndObject();
    }

    private void writeContactDetails(PartyBean partyBean) throws JsonGenerationException, IOException {
        String senderId = "unknown";
        String senderName = "unknown";
        if (partyBean != null) {
            senderId = partyBean.getId();
            if (ObjectUtil.validCollection(partyBean.getName())) {
                senderName = partyBean.getName().get(0).getValue();
            }
        }
        jsonGenerator.writeStringField("id", senderId);
        jsonGenerator.writeStringField("name", senderName);
    }

    private void writeLinks() throws JsonGenerationException, IOException {
        LOG.debug("[links]");
        jsonGenerator.writeArrayFieldStart("links");
        LOG.debug("{}");
        jsonGenerator.writeStartObject();

        //		jsonGenerator.writeStringField("href", requestURL);
        jsonGenerator.writeStringField("rel", "request");
        jsonGenerator.writeStringField("title", "The Title");
        jsonGenerator.writeStringField("type", "text/html");

        LOG.debug("{/}");
        jsonGenerator.writeEndObject();
        LOG.debug("[/links]}");
        jsonGenerator.writeEndArray();
    }

    @Override
    public void startGroup(String groupId, AnnotationBean... annotations) {
        internalWriter.startGroup(groupId, annotations);
    }

    @Override
    public void writeGroupKeyValue(String id, String value) {
        internalWriter.writeGroupKeyValue(id, value);
    }

    @Override
    public void startSeries(AnnotationBean... annotations) {
        internalWriter.startSeries(annotations);
    }

    @Override
    public void writeSeriesKeyValue(String id, String value) {
        internalWriter.writeSeriesKeyValue(id, value);
    }

    @Override
    public void writeAttributeValue(String id, String value) {
        internalWriter.writeAttributeValue(id, value);
    }

    @Override
    public void writeObservation(String obsConceptValue, String obsValue, AnnotationBean... annotations) {
        internalWriter.writeObservation(obsConceptValue, obsValue, annotations);
    }

    @Override
    public void writeObservation(String observationConceptId, String obsConceptValue, String obsValue, AnnotationBean... annotations) {
        internalWriter.writeObservation(observationConceptId, obsConceptValue, obsValue, annotations);
    }

    @Override
    public void writeObservation(Date obsTime, String obsValue, TIME_FORMAT sdmxTimeFormat, AnnotationBean... annotations) {
        internalWriter.writeObservation(obsTime, obsValue, sdmxTimeFormat, annotations);
    }

    @Override
    public void close(FooterMessage... footer) {
        try {
            if (internalWriter != null) {
                internalWriter.close(footer);
            }
        } finally {
            StreamUtil.closeStream(out);
        }
    }
}
