package org.sdmxsource.sdmx.dataparser.model;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxSuperBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.datastructure.*;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.header.DatasetHeaderBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.AttributeSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DataStructureSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DimensionSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.GroupSuperBean;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * The type Dataset information.
 */
public class DatasetInformation {
    private DataStructureBean dsd;
    private DataflowBean dataflow;
    private ProvisionAgreementBean provisionAgreement;

    private Map<String, AttributeBean> seriesAttributes = new HashMap<String, AttributeBean>();
    private Map<String, Map<String, AttributeBean>> groupAttributes = new HashMap<String, Map<String, AttributeBean>>();
    private Map<String, AttributeBean> obsAttributes = new HashMap<String, AttributeBean>();
    private Map<String, DimensionBean> dimensionMap = new HashMap<String, DimensionBean>();

    private boolean isTimeSeries;
    private String dimensionAtObservation;

    private SdmxSuperBeanRetrievalManager superBeanRetrievalManager;
    private DataStructureSuperBean dsdSuperBean;

    private Map<String, AttributeSuperBean> seriesAttributesSB = new HashMap<String, AttributeSuperBean>();
    private Map<String, Map<String, AttributeSuperBean>> groupAttributesSB = new HashMap<String, Map<String, AttributeSuperBean>>();  //GROUP ID to Attribute Id to Attribute
    private Map<String, AttributeSuperBean> obsAttributesSB = new HashMap<String, AttributeSuperBean>();
    private Map<String, DimensionSuperBean> dimensionMapSB = new HashMap<String, DimensionSuperBean>();

    private int dimSize = 0;

    /**
     * Instantiates a new Dataset information.
     *
     * @param headerBean     the header bean
     * @param dsd            the dsd
     * @param dataflow       the dataflow
     * @param pa             the pa
     * @param superRetrieval the super retrieval
     */
    public DatasetInformation(DatasetHeaderBean headerBean, DataStructureBean dsd, DataflowBean dataflow, ProvisionAgreementBean pa, SdmxSuperBeanRetrievalManager superRetrieval) {
        if (headerBean == null)
            throw new IllegalArgumentException("Can not construct DataSetInformation, the DatasetHeaderBean is required");
        if (dsd == null)
            throw new IllegalArgumentException("Can not construct DataSetInformation, the DataStructureBean is required");
        if (superRetrieval == null)
            throw new IllegalArgumentException("Can not construct DataSetInformation, the SdmxSuperBeanRetrievalManager is required");

        this.dsd = dsd;
        this.dataflow = dataflow;
        this.provisionAgreement = pa;

        calculateBeanInformation(headerBean);

        this.superBeanRetrievalManager = superRetrieval;
        if (superRetrieval != null) {
            doSuperBeans();
        }

        seriesAttributes = Collections.unmodifiableMap(seriesAttributes);
        groupAttributes = Collections.unmodifiableMap(groupAttributes);
        obsAttributes = Collections.unmodifiableMap(obsAttributes);
        dimensionMap = Collections.unmodifiableMap(dimensionMap);
        seriesAttributesSB = Collections.unmodifiableMap(seriesAttributesSB);
        groupAttributesSB = Collections.unmodifiableMap(groupAttributesSB);
        obsAttributesSB = Collections.unmodifiableMap(obsAttributesSB);
        dimensionMapSB = Collections.unmodifiableMap(dimensionMapSB);
    }

    private void calculateBeanInformation(DatasetHeaderBean headerBean) {
        dimSize = 0;
        for (DimensionBean dimension : dsd.getDimensions(SDMX_STRUCTURE_TYPE.DIMENSION, SDMX_STRUCTURE_TYPE.MEASURE_DIMENSION)) {
            dimensionMap.put(dimension.getId(), dimension);
            dimSize++;
        }
        isTimeSeries = headerBean.isTimeSeries();
        dimensionAtObservation = DimensionBean.TIME_DIMENSION_FIXED_ID;
        if (headerBean.getDataStructureReference() != null) {
            dimensionAtObservation = headerBean.getDataStructureReference().getDimensionAtObservation();
        }

        for (AttributeBean att : dsd.getDimensionGroupAttributes()) {
            if (!isTimeSeries && att.getDimensionReferences().contains(dimensionAtObservation)) {
                obsAttributes.put(att.getId(), att);
            } else {
                seriesAttributes.put(att.getId(), att);
            }
        }
        for (AttributeBean att : dsd.getObservationAttributes()) {
            obsAttributes.put(att.getId(), att);
        }

        if (!isTimeSeries && !dimensionAtObservation.equals("AllDimensions")) {
            //Cross sectional is expecting one less dimension at the series key level
            dimSize = dimSize - 1;
        }
        for (GroupBean group : dsd.getGroups()) {
            Map<String, AttributeBean> groupAttributes = new HashMap<String, AttributeBean>();
            for (AttributeBean attribute : dsd.getGroupAttributes(group.getId(), true)) {
                groupAttributes.put(attribute.getId(), attribute);
            }
        }
    }

    private void doSuperBeans() {
        dsdSuperBean = superBeanRetrievalManager.getDataStructureSuperBean(dsd.asReference().getMaintainableReference());

        for (DimensionSuperBean dimension : dsdSuperBean.getDimensions()) {
            if (!dimension.isMeasureDimension() && !dimension.isTimeDimension()) {
                dimensionMapSB.put(dimension.getId(), dimension);
            }
        }
        for (AttributeSuperBean att : dsdSuperBean.getSeriesAttributes()) {
            if (!isTimeSeries && att.getDimensionReferences().contains(dimensionAtObservation)) {
                obsAttributesSB.put(att.getId(), att);
            } else {
                seriesAttributesSB.put(att.getId(), att);
            }
        }
        for (AttributeSuperBean att : dsdSuperBean.getObservationAttributes()) {
            obsAttributesSB.put(att.getId(), att);
        }
        for (GroupSuperBean group : dsdSuperBean.getGroups()) {
            Map<String, AttributeSuperBean> groupMap = new HashMap<String, AttributeSuperBean>();
            groupAttributesSB.put(group.getId(), groupMap);
            for (AttributeSuperBean att : dsdSuperBean.getGroupAttributes(group.getId(), true)) {
                groupMap.put(att.getId(), att);
            }
        }
    }

    /**
     * Gets dataflow.
     *
     * @return the dataflow
     */
    public DataflowBean getDataflow() {
        return dataflow;
    }

    /**
     * Gets provision agreement.
     *
     * @return the provision agreement
     */
    public ProvisionAgreementBean getProvisionAgreement() {
        return provisionAgreement;
    }

    /**
     * Gets super bean retrieval manager.
     *
     * @return the super bean retrieval manager
     */
    public SdmxSuperBeanRetrievalManager getSuperBeanRetrievalManager() {
        return superBeanRetrievalManager;
    }

    /**
     * Gets series attributes sb.
     *
     * @return the series attributes sb
     */
    public Map<String, AttributeSuperBean> getSeriesAttributesSB() {
        return seriesAttributesSB;
    }

    /**
     * Gets group attributes sb.
     *
     * @return the group attributes sb
     */
    public Map<String, Map<String, AttributeSuperBean>> getGroupAttributesSB() {
        return groupAttributesSB;
    }

    /**
     * Gets obs attributes sb.
     *
     * @return the obs attributes sb
     */
    public Map<String, AttributeSuperBean> getObsAttributesSB() {
        return obsAttributesSB;
    }

    /**
     * Gets dimension map sb.
     *
     * @return the dimension map sb
     */
    public Map<String, DimensionSuperBean> getDimensionMapSB() {
        return dimensionMapSB;
    }

    /**
     * Gets series attributes super beans.
     *
     * @return the series attributes super beans
     */
    public Map<String, AttributeSuperBean> getSeriesAttributesSuperBeans() {
        return seriesAttributesSB;
    }

    /**
     * Gets group attributes super beans.
     *
     * @return the group attributes super beans
     */
    public Map<String, Map<String, AttributeSuperBean>> getGroupAttributesSuperBeans() {
        return groupAttributesSB;
    }

    /**
     * Gets obs attributes super beans.
     *
     * @return the obs attributes super beans
     */
    public Map<String, AttributeSuperBean> getObsAttributesSuperBeans() {
        return obsAttributesSB;
    }

    /**
     * Gets dimension map super beans.
     *
     * @return the dimension map super beans
     */
    public Map<String, DimensionSuperBean> getDimensionMapSuperBeans() {
        return dimensionMapSB;
    }

    /**
     * Gets series attributes.
     *
     * @return the series attributes
     */
    public Map<String, AttributeBean> getSeriesAttributes() {
        return seriesAttributes;
    }

    /**
     * Gets group attributes.
     *
     * @return the group attributes
     */
    public Map<String, Map<String, AttributeBean>> getGroupAttributes() {
        return groupAttributes;
    }

    /**
     * Gets obs attributes.
     *
     * @return the obs attributes
     */
    public Map<String, AttributeBean> getObsAttributes() {
        return obsAttributes;
    }

    /**
     * Gets dimension map.
     *
     * @return the dimension map
     */
    public Map<String, DimensionBean> getDimensionMap() {
        return dimensionMap;
    }

    /**
     * Gets dim size.
     *
     * @return the dim size
     */
    public int getDimSize() {
        return dimSize;
    }

    /**
     * Is time series boolean.
     *
     * @return the boolean
     */
    public boolean isTimeSeries() {
        return isTimeSeries;
    }

    /**
     * Gets dimension at observation.
     *
     * @return the dimension at observation
     */
    public String getDimensionAtObservation() {
        return dimensionAtObservation;
    }

    /**
     * Gets dsd.
     *
     * @return the dsd
     */
    public DataStructureBean getDsd() {
        return dsd;
    }

    /**
     * Gets dsd super bean.
     *
     * @return the dsd super bean
     */
    public DataStructureSuperBean getDsdSuperBean() {
        return dsdSuperBean;
    }
}
