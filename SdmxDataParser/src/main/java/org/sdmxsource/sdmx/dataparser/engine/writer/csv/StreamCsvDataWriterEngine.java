package org.sdmxsource.sdmx.dataparser.engine.writer.csv;

import com.opencsv.CSVWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sdmxsource.sdmx.api.constants.ATTRIBUTE_ATTACHMENT_LEVEL;
import org.sdmxsource.sdmx.api.constants.TIME_FORMAT;
import org.sdmxsource.sdmx.api.engine.DataWriterEngine;
import org.sdmxsource.sdmx.api.model.beans.base.AnnotationBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.AttributeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.header.DatasetHeaderBean;
import org.sdmxsource.sdmx.api.model.header.HeaderBean;
import org.sdmxsource.sdmx.util.date.DateUtil;
import org.sdmxsource.util.io.StreamUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.*;

/**
 * The type Csv data writer engine.
 */
public class StreamCsvDataWriterEngine implements DataWriterEngine {
    private static final Logger LOG = LogManager.getLogger(StreamCsvDataWriterEngine.class);
    private static final String EMPTY_STRING = "";
    private final boolean csvWithHeaders;
    private final String dataflowKey = "DATAFLOW";
    private final String obsValueKey = "OBS_VALUE";
    private OutputStream out;
    private CSVWriter writer;
    private String[] row;
    private Map<String, Integer> columns = new HashMap<String, Integer>();
    private List<Integer> obsAttributes = new ArrayList<Integer>();
    private int timeDimensionOffset;
    private int obsValueOffset;
    private boolean startDataset = false;
    private boolean startSeries = false;

    /**
     * Instantiates a new Csv data writer engine.
     *
     * @param out            the out
     * @param csvWithHeaders the csv with headers
     */
    public StreamCsvDataWriterEngine(OutputStream out, boolean csvWithHeaders) {
        this.out = out;
        //TODO: add locale support
        writer = new CSVWriter(new OutputStreamWriter(out));
        this.csvWithHeaders = csvWithHeaders;
    }

    @Override
    public void startDataset(ProvisionAgreementBean provision, DataflowBean dataflow, DataStructureBean dataStructureBean, DatasetHeaderBean header, AnnotationBean... annotations) {
        final List<DimensionBean> dimensions = dataStructureBean.getDimensionList().getDimensions();
        final List<AttributeBean> attributes = dataStructureBean.getAttributeList().getAttributes();
        int offset = 0;
        row = new String[(dimensions.size() + attributes.size() + 2)];
        row[0] = dataflowKey;
        for (DimensionBean dimension : dimensions) {
            final String id = dimension.getId();
            columns.put(id, ++offset);
            //TODO: add csvWithHeaders support
            row[offset] = id;
        }
        columns.put(obsValueKey, ++offset);
        row[offset] = obsValueKey;
        for (AttributeBean attribute : attributes) {
            final String id = attribute.getId();
            columns.put(id, ++offset);
            if (attribute.getAttachmentLevel() == ATTRIBUTE_ATTACHMENT_LEVEL.OBSERVATION)
                obsAttributes.add(offset);
            row[offset] = id;
        }

        timeDimensionOffset = columns.get(DimensionBean.TIME_DIMENSION_FIXED_ID);
        obsValueOffset = columns.get(obsValueKey);

        writer.writeNext(row, false);

        row[0] = dataflow.getAgencyId() + ":" + dataflow.getId() + "(" + dataflow.getVersion() + ")";
        startDataset = true;
    }

    @Override
    public void startGroup(String groupId, AnnotationBean... annotations) {
        LOG.warn("startGroup is not supported in StreamCsvDataWriterEngine");
    }

    @Override
    public void writeGroupKeyValue(String id, String value) {
        LOG.warn("writeGroupKeyValue is not supported in StreamCsvDataWriterEngine");
    }

    @Override
    public void startSeries(AnnotationBean... annotations) {
        if (!startDataset) {
            writeCurrentObservation();
        }
        startDataset = false;
        startSeries = true;
        for (int i = 1; i < row.length; i++) {
            row[i] = EMPTY_STRING;
        }
    }

    @Override
    public void writeSeriesKeyValue(String id, String value) {
        final Integer offset = columns.get(id);
        if (offset != null) row[offset] = value;
//        else throw new IllegalArgumentException("Unknown series key: " + id);
    }

    @Override
    public void writeAttributeValue(String id, String value) {
        final Integer offset = columns.get(id);
        if (offset != null) row[offset] = value;
//        else throw new IllegalArgumentException("Unknown attribute: " + id);
    }

    @Override
    public void writeObservation(String obsConceptValue, String obsValue, AnnotationBean... annotations) {
        writeObservation("", obsConceptValue, obsValue, annotations);
    }

    @Override
    public void writeObservation(String observationConceptId, String obsConceptValue, String obsValue, AnnotationBean... annotations) {
        if (!startSeries) {
            writeCurrentObservation();
            for (var rowIndex : obsAttributes)
                row[rowIndex] = EMPTY_STRING;
        }
        startSeries = false;
        row[timeDimensionOffset] = obsConceptValue;
        row[obsValueOffset] = obsValue;
    }

    private void writeCurrentObservation() {
        writer.writeNext(row, false);
    }

    @Override
    public void writeObservation(Date obsTime, String obsValue, TIME_FORMAT sdmxTimeFormat, AnnotationBean... annotations) {
        writeObservation(DateUtil.formatDate(obsTime, sdmxTimeFormat), obsValue, annotations);
    }

    @Override
    public void close(FooterMessage... footer) {
        try {
            if (writer != null) {
                writeCurrentObservation();
                writer.close();
            }
        } catch (IOException e) {
            LOG.error(e);
            StreamUtil.closeStream(out);
        }
    }

    @Override
    public void writeHeader(HeaderBean header) {
        LOG.warn("writeHeader is not supported in StreamCsvDataWriterEngine");
    }
}
