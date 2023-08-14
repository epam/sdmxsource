package org.sdmxsource.sdmx.dataparser.engine.reader.json;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.sdmxsource.sdmx.api.constants.DATASET_ACTION;
import org.sdmxsource.sdmx.api.constants.DATASET_POSITION;
import org.sdmxsource.sdmx.api.engine.DataReaderEngine;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.base.AnnotationBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.model.data.Keyable;
import org.sdmxsource.sdmx.api.model.data.Observation;
import org.sdmxsource.sdmx.api.model.header.DatasetStructureReferenceBean;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.dataparser.engine.reader.AbstractDataReaderEngine;
import org.sdmxsource.sdmx.dataparser.engine.reader.json.StructureIterator.JsonDatasetStructuralMetadata;
import org.sdmxsource.sdmx.dataparser.model.JsonReader;
import org.sdmxsource.sdmx.sdmxbeans.model.data.KeyableImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.data.ObservationImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.header.DatasetHeaderBeanImpl;
import org.sdmxsource.sdmx.util.date.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * The type Json data reader engine.
 */
public class JsonDataReaderEngine extends AbstractDataReaderEngine {
    private static final Logger LOG = LoggerFactory.getLogger(JsonDataReaderEngine.class);

    private static final long serialVersionUID = 1L;

    //READER
    private JsonReader jReader;
    private JsonDatasetStructuralMetadata jsonDatasetStructuralMetadata;

    //STATE
    private List<KeyValue> datasetAttributes;
    private boolean isFlat = false;

    private String currentKeyEncoded;  //String representation of current key in encoded form, e.g 0:0:0:0 [missing last dimension]
    /**
     * The data message is either flat, or grouped by series, here is how each one would look:
     * <p>
     * A) FLAT
     * "0:0":[1.5931,0,0],
     * "0:1":[1.5925, 0,0],
     * "1:0":[40.3426,1,0],
     * <p>
     * B) SERIES
     * "series":{
     * "0":{
     * "attributes":[
     * 0
     * ],
     * "observations":{
     * "0":[
     * 1.5931,
     * 0
     * ],
     * "1":[
     * 1.5925,
     * 0
     * ]
     * }
     * },
     * }
     * <p>
     * A) For Flat:
     * The reader is either positioned on the start of the array, if no observations have been read yet for this series, or
     * on the end of the array if we have just finished reading an observation.  To move next, we need to ensure we are positioned on the start
     * of the array, and ensure that the series key is the same as the previous key.  If we are already on the start of the array, simply return true without doing anything
     * <p>
     * B) For Series
     * The reader is either positioned at the start of the observations object, or at the end of an observations array.  In either case, the reader needs to be moved
     * forward to either leave it on the start of the next observations array, or at the end of the observations object.  If the reader encounters the end of the observation object, then the series has come to an end.
     */
    private Integer obsKey = null;           //Obs Key (typically time)
    private List<String> obsValues = null;  //Obs Value, Obs Attributes, Obs Annotations


    /**
     * Instantiates a new Json data reader engine.
     *
     * @param dataLocation              the data location
     * @param beanRetrieval             the bean retrieval
     * @param defaultDsd                the default dsd
     * @param defaultDataflow           the default dataflow
     * @param defaultProvisionAgreement the default provision agreement
     */
    public JsonDataReaderEngine(ReadableDataLocation dataLocation,
                                SdmxBeanRetrievalManager beanRetrieval,
                                DataStructureBean defaultDsd,
                                DataflowBean defaultDataflow,
                                ProvisionAgreementBean defaultProvisionAgreement) {
        super(dataLocation, beanRetrieval, defaultDsd, defaultDataflow, defaultProvisionAgreement);
        reset();
    }

    @Override
    public void reset() {
        super.reset();
        if (jReader != null) {
            jReader.close();
        }
        jReader = new JsonReader(dataLocation);
        SdmxJsonMetadataIterator metadataIterator = new SdmxJsonMetadataIterator(jReader);

        LOG.debug("Iterate JSON Data Message");
        jReader.iterate(metadataIterator);

        //Store Header
        headerBean = metadataIterator.getHeaderIterator().getHeader();

        //Store Metadata to Decode Datasets
        jsonDatasetStructuralMetadata = metadataIterator.getStructureIterator().getJsonDatasetStructuralMetadata();


        LOG.debug("Reset JsonReader");
        jReader.reset();

        LOG.debug("Move to start of dataSets Array");
        while (jReader.moveNextStartArray()) {
            if ("dataSets".equals(jReader.getCurrentFieldName())) {
                datasetPosition = null;
                LOG.debug("Move sucessful");
                return;
            }
        }
        LOG.debug("dataSets Array not found - no data");
        super.hasNext = false;  //Mark this as the end of the file
    }

    @Override
    public DataReaderEngine createCopy() {
        return new JsonDataReaderEngine(dataLocation, beanRetrieval, defaultDsd, defaultDataflow, defaultProvisionAgreement);
    }

    @Override
    public List<KeyValue> getDatasetAttributes() {
        return datasetAttributes == null ? new ArrayList<KeyValue>() : datasetAttributes;
    }

    @Override
    /**
     * Dataset Object
     * {
     *  "links": [] (optional)
     *  "action": "Information",
     *  "reportingBegin": "2012-05-04", (optional)
     *  "reportingEnd": "2012-06-01",   (optional)
     *  "validFrom": "2012-01-01T10:00:00Z", (optional)
     *  "validTo": "2013-01-01T10:00:00Z", (optional)
     *  "publicationYear": "2005", (optional)
     *  "publicationPeriod": "2005-Q1", (optional)
     *  "annotations": [0], (optional)
     *   "attributes": [0], (optional as per attributes at dataset level)
     * }
     *
     * There are between 2 and 3 levels in a data set object, depending on the way the data in  the message is organized.
     A data set may contain a flat list of observations. In this scenario, we have 2 levels in
     the data part of the message: the data set level and the observation level.
     A data set may also organize observations in logical groups called series. These groups
     can represent time series or cross-sections. In this scenario, we have 3 levels in the data
     part of the message: the data set level, the series level and the observation level.
     Dimensions and attributes may be attached to any of these 3 levels.
     In case the data set is a flat list of observations, observations will be found directly
     under a data set object. In case the data set represents time series or cross sections, the
     observations will be found under the series elements.

     */
    protected boolean moveNextDatasetInternal() {
        while (jReader.moveNextStartObject()) {
            if (jReader.isParentField("dataSets")) {
                datasetPosition = DATASET_POSITION.DATASET;

                DATASET_ACTION action = DATASET_ACTION.INFORMATION;
                String datasetId = null;
                MaintainableRefBean dataProviderRef = null;
                Date reportingBeginDate = null;
                Date reportingEndDate = null;
                Date validFrom = null;
                Date validTo = null;
                int publicationYear = -1;
                String publicationPeriod = null;
                String reportingYearStartDate = null;
                DatasetStructureReferenceBean dsRef = jsonDatasetStructuralMetadata.getDatasetStructureReference();

                while (jReader.moveNext()) {
                    String fieldName = jReader.getCurrentFieldName();
                    if (jReader.isStartObject()) {
                        if ("observations".equals(fieldName)) {
                            isFlat = true;
                            break;
                        } else if ("series".equals(fieldName)) {
                            isFlat = false;
                            break;
                        }
                        //Start of Dataset
                    } else if (jReader.isStartArray()) {
                        if ("annotations".equals(fieldName)) {
							/*
							 * 497 Array nullable. An optional array of annotation indices for the dataset. Indices refer
    						   498 back to the array of annotations in the structure field. Example:
							   499 "annotations": [ 3, 42 ]
							 */
                            //TODO Annotations is not supported at the dataset level on the SdmxSource DataReaderEngine API
                            //Skip Node
                            LOG.warn("DataSet Annotations Skipped - Not Supported on DataReaderEngine API");
                            List<Integer> annotations = jReader.readIntegerArray();
                        } else if ("attributes".equals(fieldName)) {
							/*
							 *  501 Collection of attributes values attached to the data set level. This is
								502 typically the case when a particular attribute always has the same value for the data
								503 available in the data message. In order to avoid repetition, that value can simply be
								504 attached at the data set level. Example:
								505 "attributes": [ 0, null, 0 ]
							 */
                            List<Integer> datasetAttributeLinks = jReader.readIntegerArray();
                            datasetAttributes = decode(datasetAttributeLinks, jsonDatasetStructuralMetadata.getDatasetAttributeList());
                        }
                    } else {
                        if ("action".equals(fieldName)) {
                            action = DATASET_ACTION.getAction(jReader.getValueAsString());
                        } else if ("reportingBegin".equals(fieldName)) {
                            reportingBeginDate = DateUtil.formatDate(jReader.getValueAsString(), true);
                        } else if ("reportingEnd".equals(fieldName)) {
                            reportingEndDate = DateUtil.formatDate(jReader.getValueAsString(), true);
                        } else if ("validFrom".equals(fieldName)) {
                            validFrom = DateUtil.formatDate(jReader.getValueAsString(), true);
                        } else if ("validTo".equals(fieldName)) {
                            validTo = DateUtil.formatDate(jReader.getValueAsString(), true);
                        } else if ("publicationYear".equals(fieldName)) {
                            publicationYear = Integer.parseInt(jReader.getValueAsString());
                        } else if ("publicationPeriod".equals(fieldName)) {
                            publicationPeriod = jReader.getValueAsString();
                        }
                    }
                }
                super.datasetHeaderBean = new DatasetHeaderBeanImpl(datasetId,
                        action,
                        dsRef,
                        dataProviderRef,
                        reportingBeginDate,
                        reportingEndDate,
                        validFrom,
                        validTo,
                        publicationYear,
                        publicationPeriod,
                        reportingYearStartDate);
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean moveNextKeyableInternal() {
        if (datasetPosition == DATASET_POSITION.OBSERVATION && isFlat && jReader.isStartArray() && jReader.isParentField("observations")) {
            //We have already moved to the next key
            return true;
        }
        while (jReader.moveNext()) {
            if (isFlat) {
                if (jReader.isStartArray()) {
                    datasetPosition = DATASET_POSITION.SERIES;
                    String fieldName = jReader.getCurrentFieldName();
                    String series = fieldName.substring(0, fieldName.lastIndexOf(":"));
                    if (!series.equals(currentKeyEncoded)) {
                        currentKeyEncoded = series;
                        return true;
                    }
                } else if (jReader.isEndObject() || jReader.isEndArray()) {
                    String fieldName = jReader.getCurrentStackItem().getFieldName();
                    if ("dataSets".equals(fieldName)) {
                        return false;  //End dataSet
                    }
                }
            } else {
                //Grouped by series
                if (jReader.isStartObject() && jReader.isParentField("series")) {
                    datasetPosition = DATASET_POSITION.SERIES;
                    currentKeyEncoded = jReader.getCurrentFieldName();
                    return true;
                } else if (jReader.isEndObject()) {
                    if (jReader.isParentField("oservations")) {
                        return false;  //End Observations
                    }
                    if (jReader.isParentField("dataSets")) {
                        return false;  //End dataSet
                    }
                }
            }
        }
        return false;
    }

    @Override
    protected boolean moveNextObservationInternal() {
        datasetPosition = DATASET_POSITION.OBSERVATION;
        obsKey = null;
        obsValues = null;
        if (isFlat) {
            if (jReader.isStartArray()) {
                String[] keySplit = jReader.getCurrentFieldName().split(":");
                obsKey = Integer.parseInt(keySplit[keySplit.length - 1]);
                obsValues = jReader.readStringArray();
                return true;
            }
            if (jReader.isEndArray()) {
                jReader.moveNext(); //Move to the next element, we expect this to be the start of the next array, otherwise there are no more observations
                if (jReader.isStartArray()) {
                    String fieldName = jReader.getCurrentFieldName();
                    String series = fieldName.substring(0, fieldName.lastIndexOf(":"));
                    if (series.equals(currentKeyEncoded)) {
                        String[] keySplit = fieldName.split(":");
                        obsKey = Integer.parseInt(keySplit[keySplit.length - 1]);
                        obsValues = jReader.readStringArray();
                        return true;
                    } else {
                        currentKeyEncoded = series;
                    }
                }
                return false;
            }
        } else {
            jReader.moveNext();
            if (jReader.isStartArray()) {
                obsKey = Integer.parseInt(jReader.getCurrentFieldName());
                obsValues = jReader.readStringArray();
                return true;
            } else {
                return false;
            }
        }
        return false;
    }


    @Override
    /**
     * For Flat we should be positioned at the start of an array, which we can read to give us the observation value, and indexes of all other
     * attributes and annotations.
     * <br>
     * For Series observations, the reader will be poistioned at the start of the
     */
    protected Observation lazyLoadObservation() {
        String obsValue = null;
        List<KeyValue> attributes = new ArrayList<KeyValue>();
        List<AnnotationBean> annotations = new ArrayList<AnnotationBean>();
        List<Integer> obsAttributes = new ArrayList<Integer>();
        for (int i = 1; i < obsValues.size(); i++) {
            String obsVal = obsValues.get(i);
            if (obsVal == null) {
                continue;
            }
            Integer asInt = Integer.parseInt(obsVal);
            if (jsonDatasetStructuralMetadata.getObsAttributeList().size() < i) {
                if (jsonDatasetStructuralMetadata.getAnnotationList().size() < asInt + 1) {
                    LOG.warn("Can not read annotation at index '" + asInt + "' only '" + jsonDatasetStructuralMetadata.getAnnotationList().size() + "' annotations have been reported, so the reported index does not exist");
                } else {
                    annotations.add(jsonDatasetStructuralMetadata.getAnnotationList().get(asInt));
                }
            } else {
                obsAttributes.add(asInt);
            }
        }
        attributes = decode(obsAttributes, jsonDatasetStructuralMetadata.getObsAttributeList());
        obsValue = obsValues.get(0);
        AnnotationBean[] annArr = new AnnotationBean[annotations.size()];
        annotations.toArray(annArr);

        return new ObservationImpl(currentKey, jsonDatasetStructuralMetadata.getObsIds().get(obsKey), obsValue, attributes, annArr);
    }

    @Override
    protected Keyable lazyLoadKey() {
        String[] keyParts = currentKeyEncoded.split(":");
        List<KeyValue> key = decode(keyParts, jsonDatasetStructuralMetadata.getSeriesList());
        List<KeyValue> attributes = new ArrayList<KeyValue>();
        AnnotationBean[] annotations = null;
        if (isFlat) {
            //Do Nothing
        } else {
            while (jReader.moveNext()) {
                if (jReader.isStartArray()) {
                    if ("annotations".equals(jReader.getCurrentFieldName())) {
                        annotations = decodeAnnotations(jReader.readIntegerArray());
                    } else if ("attributes".equals(jReader.getCurrentFieldName())) {
                        attributes = decode(jReader.readIntegerArray(), jsonDatasetStructuralMetadata.getSeriesAttributeList());
                    }
                } else if (jReader.isStartObject()) {
                    if (jReader.getCurrentFieldName().equals("observations")) {
                        break;
                    }
                }
            }
        }
        return new KeyableImpl(currentDataflow, currentDsd, key, attributes, null, null, annotations);
    }

    private AnnotationBean[] decodeAnnotations(List<Integer> encodedValues) {
        List<AnnotationBean> annList = new ArrayList<AnnotationBean>();
        for (Integer annIdx : encodedValues) {
            if (jsonDatasetStructuralMetadata.getAnnotationList().size() > annIdx) {
                annList.add(jsonDatasetStructuralMetadata.getAnnotationList().get(annIdx));
            } else {
                LOG.warn("Annotation not found at index '" + annIdx + "'");
            }
        }
        AnnotationBean[] returnArray = new AnnotationBean[annList.size()];
        return annList.toArray(returnArray);
    }

    private List<KeyValue> decode(List<Integer> encodedValues, List<Map<Integer, KeyValue>> decodeList) {
        List<KeyValue> returnList = new ArrayList<KeyValue>();
        for (int i = 0; i < encodedValues.size(); i++) {
            Integer val = encodedValues.get(i);
            if (val != null && decodeList.size() > i) {
                KeyValue decoded = decodeList.get(i).get(val);
                if (decoded != null) {
                    returnList.add(decoded);
                }
            }
        }
        return returnList;
    }

    private List<KeyValue> decode(String[] encodedValues, List<Map<Integer, KeyValue>> decodeList) {
        List<Integer> l = new ArrayList<Integer>();
        for (String str : encodedValues) {
            l.add(Integer.parseInt(str));
        }
        return decode(l, decodeList);
    }

    @Override
    public void close() {
        jReader.close();
    }
}
