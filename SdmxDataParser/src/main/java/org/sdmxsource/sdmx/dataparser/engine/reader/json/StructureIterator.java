package org.sdmxsource.sdmx.dataparser.engine.reader.json;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.AnnotationBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.model.header.DatasetStructureReferenceBean;
import org.sdmxsource.sdmx.dataparser.model.JsonReader;
import org.sdmxsource.sdmx.dataparser.model.JsonReader.Iterator;
import org.sdmxsource.sdmx.sdmxbeans.model.data.KeyValueImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.header.DatasetStructureReferenceBeanImpl;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Structure iterator.
 */
public class StructureIterator extends AbstractIterator {
    private List<ComponentIterator> componentIterators = new ArrayList<StructureIterator.ComponentIterator>();
    private LEVEL currentLevel = null;
    private String uri;
    private boolean inAttributes = false;
    private AnnotationsIterator annotationIterator;

    /**
     * Instantiates a new Structure iterator.
     *
     * @param jReader the j reader
     */
    public StructureIterator(JsonReader jReader) {
        super(jReader);
    }

    /**
     * Gets json dataset structural metadata.
     *
     * @return the json dataset structural metadata
     */
    public JsonDatasetStructuralMetadata getJsonDatasetStructuralMetadata() {
        return new JsonDatasetStructuralMetadata();
    }

    private List<Map<Integer, KeyValue>> getList(LEVEL lvl) {
        List<Map<Integer, KeyValue>> returnList = new ArrayList<Map<Integer, KeyValue>>();
        for (ComponentIterator comp : componentIterators) {
            if (comp.getLevel() == lvl) {
                returnList.add(comp.componentMap);
            }
        }
        return returnList;
    }

    @Override
    public JsonReader.Iterator start(String fieldName, boolean isObject) {
        if ("dimensions".equals(fieldName)) {
            inAttributes = false;
            return this;
        }

        if ("attributes".equals(fieldName)) {
            inAttributes = true;
            return this;
        }

        if ("annotations".equals(fieldName)) {
            annotationIterator = new AnnotationsIterator(jReader);
            return annotationIterator;
        }


        if ("dataSet".equals(fieldName)) {
            if (inAttributes) {
                currentLevel = LEVEL.DATASET_ATTR;
            } else {
                currentLevel = LEVEL.DATASET;
            }
        } else if ("series".equals(fieldName)) {
            if (inAttributes) {
                currentLevel = LEVEL.SERIES_ATTR;
            } else {
                currentLevel = LEVEL.SERIES;
            }
        } else if ("observation".equals(fieldName)) {
            if (inAttributes) {
                currentLevel = LEVEL.OBS_ATTR;
            } else {
                currentLevel = LEVEL.OBS;
            }
        } else if (fieldName == null) {
            //We are in an array at the level defined by currentLevel
            ComponentIterator componentIterator = new ComponentIterator(jReader, currentLevel);
            componentIterators.add(componentIterator);
            return componentIterator;
        }
        return null;
    }

    @Override
    public void next(String fieldName) {
        if ("uri".equals(fieldName)) {
            uri = jReader.getValueAsString();
        }
    }


    private enum LEVEL {
        /**
         * Dataset level.
         */
        DATASET,
        /**
         * Series level.
         */
        SERIES,
        /**
         * Obs level.
         */
        OBS,
        /**
         * Dataset attr level.
         */
        DATASET_ATTR,
        /**
         * Series attr level.
         */
        SERIES_ATTR,
        /**
         * Obs attr level.
         */
        OBS_ATTR;
    }

    /**
     * The type Json dataset structural metadata.
     */
    public class JsonDatasetStructuralMetadata {
        private DatasetStructureReferenceBean datasetStructureReference;
        private List<AnnotationBean> annotationList;
        private List<Map<Integer, KeyValue>> datasetAttributeList;
        private List<Map<Integer, KeyValue>> seriesAttributeList;
        private List<Map<Integer, KeyValue>> obsAttributeList;
        private List<Map<Integer, KeyValue>> seriesList;  //Series keys
        private List<String> obsIds;  //All the concepts at the observation level
        private String dimensionAtObservation;


        /**
         * Instantiates a new Json dataset structural metadata.
         */
        public JsonDatasetStructuralMetadata() {
            annotationList = annotationIterator != null ? annotationIterator.getAnnotations() : null;
            datasetAttributeList = getList(LEVEL.DATASET_ATTR);
            seriesAttributeList = getList(LEVEL.SERIES_ATTR);
            obsAttributeList = getList(LEVEL.OBS_ATTR);
            seriesList = getList(LEVEL.SERIES);

            Map<Integer, KeyValue> observationMap = null;
            if (seriesList.size() == 0) {
                //Flat - Observation Concept is last one in list
                seriesList = getList(LEVEL.OBS);
                observationMap = seriesList.get(seriesList.size() - 1);

            } else {
                ComponentIterator observationComp = null;
                for (ComponentIterator comp : componentIterators) {
                    if (comp.getLevel() == LEVEL.OBS) {
                        observationComp = comp;
                        break;
                    }
                }
                if (observationComp != null) {
                    observationMap = observationComp.componentMap;
                }
            }
            if (observationMap == null) {
                throw new SdmxSemmanticException("Can not read JSON Data Message, missing Observation information in the Structure part of the message");
            }
            obsIds = new ArrayList<String>();
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                KeyValue kv = observationMap.get(i);
                if (kv == null) {
                    break;
                }
                dimensionAtObservation = kv.getConcept();
                obsIds.add(kv.getCode());
            }

            if (uri != null) {
                String[] uriSplit = uri.split("/");
                if (uriSplit.length > 4) {
                    String version = uriSplit[uriSplit.length - 1];
                    String id = uriSplit[uriSplit.length - 2];
                    String agency = uriSplit[uriSplit.length - 3];
                    SDMX_STRUCTURE_TYPE structureType = SDMX_STRUCTURE_TYPE.parseClass(uriSplit[uriSplit.length - 4]);
                    StructureReferenceBean sRef = new StructureReferenceBeanImpl(agency, id, version, structureType);
                    datasetStructureReference = new DatasetStructureReferenceBeanImpl(id, sRef, null, uri, dimensionAtObservation);
                }
            }
        }

        /**
         * Gets dataset structure reference.
         *
         * @return the dataset structure reference
         */
        public DatasetStructureReferenceBean getDatasetStructureReference() {
            return datasetStructureReference;
        }

        /**
         * Gets annotation list.
         *
         * @return the annotation list
         */
        public List<AnnotationBean> getAnnotationList() {
            return annotationList;
        }

        /**
         * Gets dataset attribute list.
         *
         * @return the dataset attribute list
         */
        public List<Map<Integer, KeyValue>> getDatasetAttributeList() {
            return datasetAttributeList;
        }

        /**
         * Gets series attribute list.
         *
         * @return the series attribute list
         */
        public List<Map<Integer, KeyValue>> getSeriesAttributeList() {
            return seriesAttributeList;
        }

        /**
         * Gets obs attribute list.
         *
         * @return the obs attribute list
         */
        public List<Map<Integer, KeyValue>> getObsAttributeList() {
            return obsAttributeList;
        }

        /**
         * Gets series list.
         *
         * @return the series list
         */
        public List<Map<Integer, KeyValue>> getSeriesList() {
            return seriesList;
        }

        /**
         * Gets obs ids.
         *
         * @return the obs ids
         */
        public List<String> getObsIds() {
            return obsIds;
        }

        /**
         * Gets dimension at observation.
         *
         * @return the dimension at observation
         */
        public String getDimensionAtObservation() {
            return dimensionAtObservation;
        }
    }

    private class ComponentIterator extends AbstractIterator {
        private LEVEL level;
        private String id;
        private Map<Integer, KeyValue> componentMap = new HashMap<Integer, KeyValue>();

        private boolean inValues;
        private int pos;

        /**
         * Instantiates a new Component iterator.
         *
         * @param jReader the j reader
         * @param level   the level
         */
        public ComponentIterator(JsonReader jReader, LEVEL level) {
            super(jReader);
            this.level = level;
        }

        @Override
        public void next(String fieldName) {
            if (inValues) {
                if ("id".equals(fieldName)) {
                    componentMap.put(pos, new KeyValueImpl(jReader.getValueAsString(), this.id));
                    pos++;
                }
            } else if ("id".equals(fieldName)) {
                this.id = jReader.getValueAsString();
            }
        }

        @Override
        public Iterator start(String fieldName, boolean isObject) {
            if ("values".equals(fieldName)) {
                inValues = true;
            }
            return null;
        }

        /**
         * Gets level.
         *
         * @return the level
         */
        public LEVEL getLevel() {
            return level;
        }
    }
}
