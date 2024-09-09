package org.sdmxsource.sdmx.dataparser.engine.writer.csv;

import static java.util.stream.Collectors.toList;
import static org.sdmxsource.sdmx.dataparser.engine.writer.csv.GroupsProcessingState.GROUPS_IN_PROGRESS;
import static org.sdmxsource.sdmx.dataparser.engine.writer.csv.GroupsProcessingState.GROUPS_PROCESSED;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.opencsv.CSVWriter;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CSV Data Writer engine which is capable of writing dimension group attributes. Requires more memory as opposed to
 * its streaming counterpart - {@link StreamCsvDataWriterEngine}
 */
public class DimensionGroupBufferingCsvDataWriterEngine implements DataWriterEngine {
    private static final Logger LOG = LoggerFactory.getLogger(DimensionGroupBufferingCsvDataWriterEngine.class);
    private static final String EMPTY_STRING = "";
    private final boolean csvWithHeaders;
    private final String dataflowKey = "DATAFLOW";
    private final String obsValueKey = "OBS_VALUE";
    private final OutputStream out;
    private final CSVWriter writer;
    private String[] row;
    private final Map<String, Integer> columns = new HashMap<String, Integer>();
    private final List<Integer> obsAttributes = new ArrayList<Integer>();
    private final Set<Integer> datasetAttributes = new HashSet<>();
    private int timeDimensionOffset;
    private int obsValueOffset;
    private boolean startDataset = false;
    private boolean startSeries = false;

    private final DimensionGroupTracker dimensionGroupTracker = new DimensionGroupTracker();

    /**
     * Instantiates a new Csv data writer engine.
     *
     * @param out            the out
     * @param csvWithHeaders the csv with headers
     */
    public DimensionGroupBufferingCsvDataWriterEngine(OutputStream out, boolean csvWithHeaders) {
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
            if (attribute.getAttachmentLevel() == ATTRIBUTE_ATTACHMENT_LEVEL.OBSERVATION) {
                obsAttributes.add(offset);
            } else if (attribute.getAttachmentLevel() == ATTRIBUTE_ATTACHMENT_LEVEL.DATA_SET) {
                datasetAttributes.add(offset);
            }
            row[offset] = id;
        }

        timeDimensionOffset = columns.get(DimensionBean.TIME_DIMENSION_FIXED_ID);
        obsValueOffset = columns.get(obsValueKey);

        writer.writeNext(row, false);

        row[0] = dataflow.getAgencyId() + ":" + dataflow.getId() + "(" + dataflow.getVersion() + ")";
        clearAllComponents();
        startDataset = true;
    }

    @Override
    public void startGroup(String groupId, AnnotationBean... annotations) {
        dimensionGroupTracker.setGroupsProcessingState(GROUPS_IN_PROGRESS);
        dimensionGroupTracker.startGroup();
    }

    @Override
    public void writeGroupKeyValue(String id, String value) {
        DimensionGroup dimensionGroup = dimensionGroupTracker.getCurrentGroupInProgress();
        Map<String, String> dimensions = dimensionGroup.getDimensions();
        dimensions.put(id, value);
    }

    @Override
    public void startSeries(AnnotationBean... annotations) {
        dimensionGroupTracker.clearSeries();
        dimensionGroupTracker.setGroupsProcessingState(GROUPS_PROCESSED);
        if (!startDataset) {
            writeCurrentObservation();
        }
        startDataset = false;
        startSeries = true;
        clearObservationComponents();
    }

    private void clearObservationComponents() {
        for (int i = 1; i < row.length; i++) {
            if (datasetAttributes.contains(i)) {
                continue;
            }
            row[i] = EMPTY_STRING;
        }
    }

    private void clearAllComponents() {
        for (int i = 1; i < row.length; i++) {
            row[i] = EMPTY_STRING;
        }
    }

    @Override
    public void writeSeriesKeyValue(String id, String value) {
        final Integer offset = columns.get(id);
        if (offset != null) {
            row[offset] = value;
        }
//        else throw new IllegalArgumentException("Unknown series key: " + id);
    }

    @Override
    public void writeAttributeValue(String id, String value) {
        if (dimensionGroupTracker.getGroupsProcessingState() == GROUPS_IN_PROGRESS) {
            DimensionGroup dimensionGroup = dimensionGroupTracker.getCurrentGroupInProgress();
            dimensionGroup.getAttributes().put(id, List.of(value));
            return;
        }

        final Integer offset = columns.get(id);
        if (offset != null) {
            row[offset] = value;
        }
    }

    @Override
    public void writeObservation(String obsConceptValue, String obsValue, AnnotationBean... annotations) {
        writeObservation("", obsConceptValue, obsValue, annotations);
    }

    @Override
    public void writeObservation(String observationConceptId, String obsConceptValue, String obsValue, AnnotationBean... annotations) {
        if (!startSeries) {
            writeCurrentObservation();
            for (var rowIndex : obsAttributes) {
                row[rowIndex] = EMPTY_STRING;
            }
        }
        startSeries = false;
        row[timeDimensionOffset] = obsConceptValue;
        row[obsValueOffset] = obsValue;
        writeMatchingGroupAttributes();
    }

    private void writeMatchingGroupAttributes() {
        for (DimensionGroup dimensionGroup : dimensionGroupTracker.getCurrentSeriesDimensionGroups()) {
            Map<String, List<String>> attributes = dimensionGroup.getAttributes();
            for (Map.Entry<String, List<String>> attribute : attributes.entrySet()) {
                Integer idIndex = columns.get(attribute.getKey());
                row[idIndex] = String.join(",", attribute.getValue());
            }
        }
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
                writeGroupAttributesOutOfSeries();
                writer.close();
            }
        } catch (IOException e) {
            LOG.error("Error occurred: {}", e.getMessage(), e);
            StreamUtil.closeStream(out);
        }
    }

    private void writeGroupAttributesOutOfSeries() {
        dimensionGroupTracker.setGroupsProcessingState(GROUPS_PROCESSED);

        List<DimensionGroup> dimensionGroups = dimensionGroupTracker.listAllGroups().stream()
            .filter(dimGroup -> !dimGroup.isTouched())
            .collect(toList());

        if (!dimensionGroups.isEmpty()) {
            dimensionGroups.forEach(group -> {
                clearObservationComponents();
                writeGroup(group);
                writer.writeNext(row, false);
            });
        }
    }

    private void writeGroup(DimensionGroup group) {
        Map<String, String> dimensions = group.getDimensions();
        for (Map.Entry<String, String> dimension : dimensions.entrySet()) {
            Integer columnIndex = columns.get(dimension.getKey());
            if (columnIndex != null) {
                row[columnIndex] = dimension.getValue();
            }
        }
        Map<String, List<String>> attributes = group.getAttributes();
        for (Map.Entry<String, List<String>> attribute : attributes.entrySet()) {
            Integer columnIndex = columns.get(attribute.getKey());
            if (columnIndex != null) {
                row[columnIndex] = String.join(",", attribute.getValue());
            }
        }
    }

    @Override
    public void writeHeader(HeaderBean header) {
        LOG.warn("writeHeader is not supported in StreamCsvDataWriterEngine");
    }
}
