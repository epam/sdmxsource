package org.sdmxsource.sdmx.dataparser.engine.writer;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.sdmxsource.sdmx.api.engine.DataWriterEngine;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxSuperBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.base.AnnotationBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.header.DatasetHeaderBean;
import org.sdmxsource.sdmx.api.model.superbeans.base.ComponentSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DataStructureSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DimensionSuperBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Captures all the information about a datasets series and series codes as that are being written
 */
public abstract class DatasetInfoDataWriterEngine extends DecoratedDataWriterEngine implements DataWriterEngine {

    /**
     * The Current dsd super bean.
     */
//The current data structure that the writer engine is outputting data for
    protected DataStructureSuperBean currentDSDSuperBean;
    /**
     * The Super bean retrieval manager.
     */
//To get the DSD SuperBean
    protected SdmxSuperBeanRetrievalManager superBeanRetrievalManager;
    //A map containing the codes that have been reported against the given dimension
    private Map<String, Pair<Map<String, Integer>, List<String>>> codesForComponentMap;

    //The following is relevant for calculating index of series
    //A list of all the dimensions and series level attributes for a DSD
    private List<DimensionSuperBean> allDimensions;


    /**
     * Instantiates a new Dataset info data writer engine.
     *
     * @param superBeanRetrievalManager the super bean retrieval manager
     */
    public DatasetInfoDataWriterEngine(SdmxSuperBeanRetrievalManager superBeanRetrievalManager) {
        super(null);
        if (superBeanRetrievalManager == null) {
            throw new IllegalArgumentException("Can not construct DatasetInfoDataWriterEngine, missing requred argument: SdmxSuperBeanRetrievalManager");
        }
        //Store Variables
        this.superBeanRetrievalManager = superBeanRetrievalManager;
    }


    @Override
    public void startDataset(ProvisionAgreementBean prov, DataflowBean flow, DataStructureBean dsd, DatasetHeaderBean header, AnnotationBean... annotations) {
        super.startDataset(prov, flow, dsd, header);

        this.currentDSDSuperBean = superBeanRetrievalManager.getDataStructureSuperBean(dsd.asReference().getMaintainableReference());
        this.flow = flow;

        codesForComponentMap = new HashMap<>();
        allDimensions = currentDSDSuperBean.getDimensions();
        for (ComponentSuperBean currentComponent : currentDSDSuperBean.getComponents()) {
            codesForComponentMap.put(currentComponent.getId(), ImmutablePair.of(new HashMap<>(), new ArrayList<>()));
        }
        codesForComponentMap.put(DimensionBean.TIME_DIMENSION_FIXED_ID, ImmutablePair.of(new HashMap<>(), new ArrayList<>()));
    }


    @Override
    public void writeAttributeValue(String attributeId, String attributeValue) {
        super.writeAttributeValue(attributeId, attributeValue);
        storeComponentValue(attributeId, attributeValue);
    }

    @Override
    public void writeObservation(String observationConceptId, String obsIdValue, String obsValue, AnnotationBean... annotations) {
        storeComponentValue(observationConceptId, obsIdValue);
        super.writeObservation(observationConceptId, obsIdValue, obsValue, annotations);
    }

    @Override
    public void writeObservation(String date, String value, AnnotationBean... annotations) {
        storeComponentValue(value, date);
        super.writeObservation(date, value, annotations);
    }


    @Override
    public void writeSeriesKeyValue(String dimensionId, String dimensionValue) {
        super.writeSeriesKeyValue(dimensionId, dimensionValue);
        storeComponentValue(dimensionId, dimensionValue);
    }

    private void storeComponentValue(String componentId, String componentValue) {
        final Pair<Map<String, Integer>, List<String>> codes = codesForComponentMap.get(componentId);

        if (codes != null) {
            final Map<String, Integer> map = codes.getLeft();

            if (!map.containsKey(componentValue)){
                final List<String> list = codes.getRight();
                final int index = list.size();

                list.add(componentValue);
                map.put(componentValue, index);
            }
        }
    }

    /**
     * Gets all dimensions.
     *
     * @return the all dimensions
     */
    public List<DimensionSuperBean> getAllDimensions() {
        return allDimensions;
    }

    /**
     * Gets reported values.
     *
     * @param componentId the component id
     * @return the reported values
     */
    public List<String> getReportedValues(String componentId) {
        return codesForComponentMap.get(componentId).getRight();
    }

    /**
     * Gets reported index.
     *
     * @param concept the concept
     * @param code    the code
     * @return the reported index
     */
    public int getReportedIndex(String concept, String code) {
        final Integer index = codesForComponentMap.get(concept).getLeft().get(code);
        return (index == null) ? -1 : index;
    }
}

