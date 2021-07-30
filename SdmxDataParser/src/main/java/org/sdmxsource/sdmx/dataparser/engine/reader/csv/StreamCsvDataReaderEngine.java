package org.sdmxsource.sdmx.dataparser.engine.reader.csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.sdmxsource.sdmx.api.engine.DataReaderEngine;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.base.AnnotationBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.AttributeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.model.data.Keyable;
import org.sdmxsource.sdmx.api.model.data.Observation;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.dataparser.engine.reader.AbstractDataReaderEngine;
import org.sdmxsource.sdmx.sdmxbeans.model.data.KeyValueImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.data.KeyableImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.data.ObservationImpl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * The type Csv data reader engine.
 */
public class StreamCsvDataReaderEngine extends AbstractDataReaderEngine {
    /**
     * The constant DATAFLOW_KEY.
     */
    public static final String DATAFLOW_KEY = "DATAFLOW";
    /**
     * The constant OBS_VALUE_KEY.
     */
    public static final String OBS_VALUE_KEY = "OBS_VALUE";
    /**
     * The constant EMPTY_STRING.
     */
    public static final String EMPTY_STRING = "";
    private static final Logger LOG = Logger.getLogger(StreamCsvDataReaderEngine.class);
    /**
     * The Annotations.
     */
    AnnotationBean[] annotations = null;
    private CSVReader reader;
    private String[] row;
    private Map<String, Integer> columns;
    private Map<Integer, String> columns2;
    private List<Pair<DimensionBean, Integer>> dimensionColumns;
    private Map<String, Integer> attributeColumns;
    private int dataFlowOffset = 0;
    private int timeDimensionOffset;
    private int obsValueOffset;
    private List<KeyValue> datasetAttributes;
    private String currentDataFlowId = EMPTY_STRING;
    private String nextDataFlowId = EMPTY_STRING;
    private boolean nextDataFlowFound = false;
    private KeyableImpl currentKey = new KeyableImpl(defaultDataflow, defaultDsd, Collections.emptyList(), Collections.emptyList(), (String) null, annotations);
    private String currentKeyAsString = EMPTY_STRING;
    private KeyableImpl nextKey = new KeyableImpl(defaultDataflow, defaultDsd, Collections.emptyList(), Collections.emptyList(), (String) null, annotations);
    private String nextKeyAsString = EMPTY_STRING;
    private boolean nextKeyFound = false;
    private Observation currentObservation = null;
    private Observation nextObservation = null;
    private boolean nextObservationFound = false;

    /**
     * Creates a reader engine based on the data location, the location of available data structures that can be used to retrieve dsds, and the default dsd to use
     *
     * @param dataLocation       the location of the data
     * @param beanRetrieval      giving the ability to retrieve dsds for the datasets this reader engine is reading.  This can be null if there is only one relevant dsd - in which case the default dsd should be provided
     * @param defaultDsd         the default dsd to use if the beanRetrieval is null, or if the bean retrieval does not return the dsd for the given dataset
     * @param dataflowBean       the dataflow bean
     * @param provisionAgreement the provision agreement
     */
    public StreamCsvDataReaderEngine(ReadableDataLocation dataLocation, SdmxBeanRetrievalManager beanRetrieval, DataStructureBean defaultDsd, DataflowBean dataflowBean, ProvisionAgreementBean provisionAgreement) {
        super(dataLocation, beanRetrieval, defaultDsd, dataflowBean, provisionAgreement);
        reset();
//        throw new SdmxNotImplementedException("CsvDataReaderEngine not implemented yet.");
    }

    @Override
    public void reset() {
        super.reset();

        columns = new HashMap<String, Integer>();
        columns2 = new HashMap<Integer, String>();
        dimensionColumns = new ArrayList<>();
        attributeColumns = new HashMap<String, Integer>();
        final List<DimensionBean> dimensions = defaultDsd.getDimensionList().getDimensions();
        final List<AttributeBean> attributes = defaultDsd.getAttributeList().getAttributes();
        row = new String[(dimensions.size() + attributes.size() + 2)];
        row[0] = DATAFLOW_KEY;
        for (DimensionBean dimension : dimensions) {
            final String id = dimension.getId();
            columns.put(id, -1);
        }
        columns.put(OBS_VALUE_KEY, -1);
        for (AttributeBean attribute : attributes) {
            final String id = attribute.getId();
            columns.put(id, -1);
        }

        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                LOG.warn("Error attempting to close CSVReader", e);
            }
        }

        reader = new CSVReader(new InputStreamReader(dataLocation.getInputStream()));
        try {
            row = reader.readNext();
            if (row[0] == null || !row[0].contains(DATAFLOW_KEY))
                throw new SdmxSemmanticException("Invalid format - no DATAFLOW column");
            columns2.put(dataFlowOffset, DATAFLOW_KEY);
            for (int i = 1; i < row.length; i++) {
                final String id = row[i];
                if (columns.get(id) != null) {
                    columns.put(id, i);
                    columns2.put(i, id);
                }
            }

            obsValueOffset = columns.get(OBS_VALUE_KEY);
            timeDimensionOffset = columns.get(DimensionBean.TIME_DIMENSION_FIXED_ID);

            for (DimensionBean dimension : dimensions) {
                final String id = dimension.getId();

                if (DimensionBean.TIME_DIMENSION_FIXED_ID.equals(id))
                    continue;

                final Integer offset = columns.get(id);
                dimensionColumns.add(Pair.of(dimension, offset));
                if (offset == null || offset == -1)
                    throw new SdmxSemmanticException("Invalid format - no " + id + " dimension found.");
            }
            dimensionColumns.sort(Comparator.comparingInt(pair -> pair.getLeft().getPosition()));

            for (AttributeBean attribute : attributes) {
                final String id = attribute.getId();
                final Integer offset = columns.get(id);
                if (attribute.isMandatory())
                    continue;
                attributeColumns.put(id, offset);
                if (offset == null || offset == -1)
                    throw new SdmxSemmanticException("Invalid format - no " + id + " attribute found.");
            }

            if (obsValueOffset == -1)
                throw new SdmxSemmanticException("Invalid format - no " + OBS_VALUE_KEY + " found.");

            if (timeDimensionOffset == -1)
                throw new SdmxSemmanticException("Invalid format - no " + DimensionBean.TIME_DIMENSION_FIXED_ID + " dimension found.");

            readNextRow();
        } catch (IOException | CsvValidationException e) {
            LOG.error(e);
            reader = null;
        }
    }

    private void readNextRow() {
        try {
            row = reader.readNext();
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
        if (row == null)
            return;

        List<KeyValue> key = new ArrayList<KeyValue>();
        StringBuilder sb = new StringBuilder();
        List<KeyValue> attributes = new ArrayList<KeyValue>();
        for (var p : dimensionColumns) {
            final String id = row[p.getRight()];
            key.add(new KeyValueImpl(id, p.getLeft().getId()));
            sb.append(id);
        }
//        final String id = row[timeDimensionOffset];
//        key.add(new KeyValueImpl(id, DimensionBean.TIME_DIMENSION_FIXED_ID));
//        sb.append(id);

        String keyAsString = sb.toString();

        for (Map.Entry<String, Integer> e : attributeColumns.entrySet()) {
            attributes.add(new KeyValueImpl(row[e.getValue()], e.getKey()));
        }
        if (!nextKeyAsString.equals(keyAsString)) {
            nextKeyFound = true;
//            currentKeyAsString = nextKeyAsString;
            nextKeyAsString = keyAsString;
            currentKey = nextKey;
            nextKey = new KeyableImpl(defaultDataflow, defaultDsd, key, attributes, (String) null, annotations);
        } else {
            nextKeyFound = false;
        }

        nextObservationFound = true;
//        currentObservation = nextObservation;
        nextObservation = new ObservationImpl(nextKey, row[timeDimensionOffset], row[obsValueOffset], attributes, annotations);

        if (!nextDataFlowId.equals(row[dataFlowOffset])) {
            nextDataFlowFound = true;
            currentDataFlowId = nextDataFlowId;
            nextDataFlowId = row[dataFlowOffset];
            nextKeyFound = true;
        }

    }

    @Override
    public DataReaderEngine createCopy() {
        return new StreamCsvDataReaderEngine(dataLocation, beanRetrieval, defaultDsd, defaultDataflow, defaultProvisionAgreement);
    }

    @Override
    public List<KeyValue> getDatasetAttributes() {
        return datasetAttributes != null ? datasetAttributes : Collections.emptyList();
    }

    @Override
    public void close() {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                LOG.warn("Error attempting to close CSVReader", e);
            }
        }
    }

    @Override
    protected Observation lazyLoadObservation() {
        return currentObservation;
    }

    @Override
    protected Keyable lazyLoadKey() {
        return currentKey;
    }

    @Override
    protected boolean moveNextDatasetInternal() {
        while (true) {
            if (row == null)
                return false;
            if (nextDataFlowFound) {
                currentDataFlowId = nextDataFlowId;
                nextDataFlowFound = false;
                return true;
            }
            readNextRow();
        }
    }

    @Override
    protected boolean moveNextKeyableInternal() {
        while (true) {
            if (row == null)
                return false;
            if (nextDataFlowFound)
                return false;
            if (!nextDataFlowFound && nextKeyFound) {
                currentKey = nextKey;
                nextKeyFound = false;
                return true;
            }
            readNextRow();
        }
    }

    @Override
    protected boolean moveNextObservationInternal() {
        if (row == null)
            return false;
        if (!nextKeyFound && nextObservationFound) {
            currentObservation = nextObservation;
            nextObservationFound = false;
            return true;
        }
        readNextRow();
        if (!nextKeyFound && nextObservationFound) {
            currentObservation = nextObservation;
            nextObservationFound = false;
            return true;
        }
        return false;
    }
}
