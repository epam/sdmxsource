package org.sdmxsource.sdmx.dataparser.engine.reader.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.AnnotationBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.model.header.DatasetStructureReferenceBean;
import org.sdmxsource.sdmx.dataparser.model.JsonReader;
import org.sdmxsource.sdmx.dataparser.model.JsonReader.Iterator;
import org.sdmxsource.sdmx.sdmxbeans.model.header.DatasetStructureReferenceBeanImpl;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;

/**
 * The type Structure iterator.
 */
public class StructureIterator extends AbstractIterator {
    private final List<ComponentIterator> componentIterators = new ArrayList<>();
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

    private List<Component> getList(LEVEL lvl) {
        List<Component> returnList = new ArrayList<>();
        for (ComponentIterator iterator : componentIterators) {
            if (iterator.getLevel() == lvl) {
                returnList.add(iterator.component);
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
        private final List<AnnotationBean> annotationList;
        private final List<Component> datasetAttributeList;
        private final List<Component> seriesAttributeList;
        private final List<Component> obsAttributeList;
        private List<Component> seriesList;  //Series keys
        private final List<String> obsIds;  //All the concepts at the observation level
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

            Component observationComponent = null;
            if (seriesList.isEmpty()) {
                //Flat - Observation Concept is last one in list
                seriesList = getList(LEVEL.OBS);
                observationComponent = seriesList.get(seriesList.size() - 1);

            } else {
                ComponentIterator observationCompIterator = null;
                for (ComponentIterator iterator : componentIterators) {
                    if (iterator.getLevel() == LEVEL.OBS) {
                        observationCompIterator = iterator;
                        break;
                    }
                }
                if (observationCompIterator != null) {
                    observationComponent = observationCompIterator.component;
                }
            }
            if (observationComponent == null) {
                throw new SdmxSemmanticException("Can not read JSON Data Message, missing Observation information in the Structure part of the message");
            }
            dimensionAtObservation = observationComponent.id;
            obsIds = observationComponent.values;


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
        public List<Component> getDatasetAttributeList() {
            return datasetAttributeList;
        }

        /**
         * Gets series attribute list.
         *
         * @return the series attribute list
         */
        public List<Component> getSeriesAttributeList() {
            return seriesAttributeList;
        }

        /**
         * Gets obs attribute list.
         *
         * @return the obs attribute list
         */
        public List<Component> getObsAttributeList() {
            return obsAttributeList;
        }

        /**
         * Gets series list.
         *
         * @return the series list
         */
        public List<Component> getSeriesList() {
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

    private static class ComponentIterator extends AbstractIterator {
        private final LEVEL level;
        private Component component;

        private boolean inValues;

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
                    component.getValues().add(jReader.getValueAsString());
                }
            } else if ("id".equals(fieldName)) {
                this.component = new Component(jReader.getValueAsString(), new ArrayList<>());
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

    static class Component {

        private final String id;
        private final List<String> values;


        public Component(String id, List<String> values) {
            this.id = id;
            this.values = values;
        }

        public String getId() {
            return id;
        }

        public List<String> getValues() {
            return values;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Component)) {
                return false;
            }
            Component that = (Component) o;
            return Objects.equals(id, that.id) && Objects.equals(values, that.values);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, values);
        }
    }

}
