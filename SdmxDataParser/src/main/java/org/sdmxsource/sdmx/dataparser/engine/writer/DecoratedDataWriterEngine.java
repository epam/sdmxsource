package org.sdmxsource.sdmx.dataparser.engine.writer;

import org.sdmxsource.sdmx.api.constants.DIMENSION_AT_OBSERVATION;
import org.sdmxsource.sdmx.api.constants.TIME_FORMAT;
import org.sdmxsource.sdmx.api.engine.DataWriterEngine;
import org.sdmxsource.sdmx.api.model.beans.base.AnnotationBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.model.data.Keyable;
import org.sdmxsource.sdmx.api.model.data.Observation;
import org.sdmxsource.sdmx.api.model.header.DatasetHeaderBean;
import org.sdmxsource.sdmx.api.model.header.DatasetStructureReferenceBean;
import org.sdmxsource.sdmx.api.model.header.HeaderBean;
import org.sdmxsource.sdmx.dataparser.model.DATA_POSITION;
import org.sdmxsource.sdmx.sdmxbeans.model.data.KeyValueImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.data.KeyableImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.data.ObservationImpl;
import org.sdmxsource.sdmx.util.date.DateUtil;
import org.sdmxsource.util.ObjectUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Decorates a data writer engine
 */
public class DecoratedDataWriterEngine implements DataWriterEngine {
    /**
     * The Dwe.
     */
    protected DataWriterEngine dwe;
    /**
     * The Flow.
     */
    protected DataflowBean flow;
    /**
     * The Prov.
     */
    protected ProvisionAgreementBean prov;
    /**
     * The Dsd.
     */
    protected DataStructureBean dsd;
    /**
     * The Current position.
     */
    protected DATA_POSITION currentPosition = DATA_POSITION.DATASET;
    /**
     * The Data type.
     */
    protected DATA_TYPE_REPRESENTATION dataType;
    /**
     * The Dimension at observation.
     */
    protected String dimensionAtObservation;
    /**
     * The Current annotations.
     */
    protected AnnotationBean[] currentAnnotations;
    private boolean isClosed = false;
    private String groupName;
    private List<KeyValue> key;
    private List<KeyValue> attributes;
    private String obsConceptValue;
    private String obsValue;
    private Keyable currentKey;
    private boolean flushDatasetAttributesRequired;
    private boolean flushKeyRequired;
    private boolean flushObsRequired;
    private String obsConceptId;


    /**
     * Instantiates a new Decorated data writer engine.
     *
     * @param dwe the dwe
     */
    public DecoratedDataWriterEngine(DataWriterEngine dwe) {
        this.dwe = dwe;
        clearCurrentKeys();
    }

    private void clearCurrentKeys() {
        key = new ArrayList<KeyValue>();
        attributes = new ArrayList<KeyValue>();
        groupName = null;
        obsConceptValue = null;
        obsValue = null;
        flushDatasetAttributesRequired = false;
        flushObsRequired = false;
        flushKeyRequired = false;
        currentAnnotations = null;
    }

    protected void checkClosed() {
        if (isClosed) {
            throw new RuntimeException("Data Writer has already been closed and can not have any more information written to it!");
        }
    }

    @Override
    public void startDataset(ProvisionAgreementBean prov, DataflowBean flow, DataStructureBean dsd, DatasetHeaderBean header, AnnotationBean... annotations) {
        checkClosed();

        flushDatasetAttributes();
        flushKey();
        flushObs();
        clearCurrentKeys();

        this.flow = flow;
        this.dsd = dsd;
        this.prov = prov;


        currentPosition = DATA_POSITION.DATASET;
        if (dwe != null) {
            dwe.startDataset(prov, flow, dsd, header, annotations);
        }
        if (header != null && header.getDataStructureReference() != null) {
            this.dimensionAtObservation = header.getDataStructureReference().getDimensionAtObservation();
        }
        if (!ObjectUtil.validString(dimensionAtObservation)) {
            this.dimensionAtObservation = DimensionBean.TIME_DIMENSION_FIXED_ID;
        }

        dataType = DATA_TYPE_REPRESENTATION.getDataType(this.dimensionAtObservation);
    }

    /**
     * Flush dataset attributes.
     */
    protected void flushDatasetAttributes() {
        if (flushDatasetAttributesRequired) {
            writeDatasetAttributes(attributes);
            attributes = new ArrayList<KeyValue>();
            flushDatasetAttributesRequired = false;
        }
    }

    /**
     * Write dataset attributes.
     *
     * @param datasetAttributes the dataset attributes
     */
    protected void writeDatasetAttributes(List<KeyValue> datasetAttributes) {
        if (dwe == null) {
            return;
        }
        for (KeyValue attr : datasetAttributes) {
            dwe.writeAttributeValue(attr.getConcept(), attr.getCode());
        }
    }

    /**
     * Creates a Keyable (currentKey) and converts it to the mapped key, writing the new key to the writer
     */
    protected void flushKey() {
        if (flushKeyRequired) {
            currentKey = new KeyableImpl(flow, dsd, key, attributes, groupName, currentAnnotations);
            writeKey(currentKey);
            clearCurrentKeys();
        }
    }

    /**
     * Write key.
     *
     * @param key the key
     */
    protected void writeKey(Keyable key) {
        if (dwe == null) {
            return;
        }
        if (key.isSeries()) {
            dwe.startSeries();

            for (KeyValue kv : key.getKey()) {
                dwe.writeSeriesKeyValue(kv.getConcept(), kv.getCode());
            }
        } else {
            dwe.startGroup(key.getGroupName());
            for (KeyValue kv : key.getKey()) {
                dwe.writeGroupKeyValue(kv.getConcept(), kv.getCode());
            }
        }
        for (KeyValue kv : key.getAttributes()) {
            dwe.writeAttributeValue(kv.getConcept(), kv.getCode());
        }
    }

    /**
     * Flush obs.
     */
    protected void flushObs() {
        if (flushObsRequired) {
            Observation obs;
            if (dataType.equals(DATA_TYPE_REPRESENTATION.TIME_SERIES)) {
                obs = new ObservationImpl(currentKey, obsConceptValue, obsValue, attributes, currentAnnotations);
            } else {
                KeyValue kv = new KeyValueImpl(obsConceptValue, obsConceptId);
                obs = new ObservationImpl(currentKey, obsConceptValue, obsValue, attributes, kv, currentAnnotations);
            }
            writeObs(obs);
            clearCurrentKeys();
        }
    }

    /**
     * Write obs.
     *
     * @param obs the obs
     */
    protected void writeObs(Observation obs) {
        if (dwe == null) {
            return;
        }
        dwe.writeObservation(obs.getObsTime(), obs.getObservationValue());
        for (KeyValue kv : obs.getAttributes()) {
            dwe.writeAttributeValue(kv.getConcept(), kv.getCode());
        }
    }

    @Override
    public void startGroup(String groupId, AnnotationBean... annotations) {
        checkClosed();
        flushDatasetAttributes();
        flushKey();
        flushObs();
        currentPosition = DATA_POSITION.GROUP;
        this.groupName = groupId;
        this.currentAnnotations = annotations;
    }

    @Override
    public void writeGroupKeyValue(String id, String value) {
        checkClosed();
        currentPosition = DATA_POSITION.GROUP_KEY_ATTRIBUTE;
        flushKeyRequired = true;
        key.add(new KeyValueImpl(value, id));
    }

    @Override
    public void startSeries(AnnotationBean... annotations) {
        checkClosed();
        currentPosition = DATA_POSITION.SERIES_KEY;
        flushDatasetAttributes();
        flushKey();
        flushObs();
        this.currentAnnotations = annotations;
    }

    @Override
    public void writeSeriesKeyValue(String id, String value) {
        checkClosed();
        currentPosition = DATA_POSITION.SERIES_KEY;
        if (!flushKeyRequired) {
            flushObs();
        }
        flushKeyRequired = true;
        key.add(new KeyValueImpl(value, id));
    }


    @Override
    public void writeAttributeValue(String id, String value) {
        checkClosed();
        if (currentPosition == DATA_POSITION.SERIES_KEY) {
            currentPosition = DATA_POSITION.SERIES_KEY_ATTRIBUTE;
        } else if (currentPosition == DATA_POSITION.OBSERVATION) {
            currentPosition = DATA_POSITION.OBSERVATION_ATTRIBUTE;
        } else if (currentPosition == DATA_POSITION.GROUP_KEY) {
            currentPosition = DATA_POSITION.GROUP_KEY_ATTRIBUTE;
        } else if (currentPosition == DATA_POSITION.DATASET) {
            currentPosition = DATA_POSITION.DATASET_ATTRIBUTE;
            flushDatasetAttributesRequired = true;
        }
        attributes.add(new KeyValueImpl(value, id));
    }


    @Override
    public void writeObservation(String obsIdValue, String obsValue, AnnotationBean... annotations) {
        if (dimensionAtObservation.equals(DIMENSION_AT_OBSERVATION.ALL)) {
            throw new IllegalArgumentException("Can not write observation, as no observation concept id was given, and this is writing a flat dataset. " +
                    "Plase use the method: writeObservation(String obsConceptId, String obsIdValue, String obsValue, AnnotationBean... annotations)");
        }
        writeObservation(dimensionAtObservation, obsIdValue, obsValue, annotations);
    }

    /**
     * All of the write observations methods end up here
     */
    @Override
    public void writeObservation(String observationConceptId, String obsIdValue, String obsValue, AnnotationBean... annotations) {
        checkClosed();
        currentPosition = DATA_POSITION.OBSERVATION;
        flushDatasetAttributes();
        flushKey();
        flushObs();
        this.obsConceptId = observationConceptId;
        this.obsConceptValue = obsIdValue;
        this.obsValue = obsValue;
        this.currentAnnotations = annotations;
        //dwe.writeObservation(obsIdValue, obsValue);
        flushObsRequired = true;
    }

    @Override
    public void writeObservation(Date obsTime, String obsValue, TIME_FORMAT sdmxTimeFormat, AnnotationBean... annotations) {
        checkClosed();
        currentPosition = DATA_POSITION.OBSERVATION;
        writeObservation(DateUtil.formatDate(obsTime, sdmxTimeFormat), obsValue, annotations);
    }

    @Override
    public void writeHeader(HeaderBean header) {
        checkClosed();
        if (dwe != null) {
            dwe.writeHeader(header);
        }
    }

    @Override
    public void close(FooterMessage... footer) {
        if (isClosed) {
            return;
        }
        isClosed = true;
//        flushDatasetAttributes();
        flushKey();
        flushObs();
        if (dwe != null) {
            dwe.close();
        }
    }

    /**
     * The enum Data type representation.
     */
    enum DATA_TYPE_REPRESENTATION {
        /**
         * Flat data type representation.
         */
        FLAT,
        /**
         * Time series data type representation.
         */
        TIME_SERIES,
        /**
         * Cross sectional data type representation.
         */
        CROSS_SECTIONAL;

        /**
         * Gets data type.
         *
         * @param dimensionAtObservation the dimension at observation
         * @return the data type
         */
        public static DATA_TYPE_REPRESENTATION getDataType(String dimensionAtObservation) {
            if (dimensionAtObservation.equals(DimensionBean.TIME_DIMENSION_FIXED_ID)) {
                return DATA_TYPE_REPRESENTATION.TIME_SERIES;
            }

            if (dimensionAtObservation.equals(DatasetStructureReferenceBean.ALL_DIMENSIONS)) {
                return FLAT;
            }
            return DATA_TYPE_REPRESENTATION.CROSS_SECTIONAL;
        }
    }
}