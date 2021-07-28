/*******************************************************************************
 * Copyright (c) 2013 Metadata Technology Ltd.
 *
 * All rights reserved. This program and the accompanying materials are made 
 * available under the terms of the GNU Lesser General Public License v 3.0 
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 *
 * This file is part of the SDMX Component Library.
 *
 * The SDMX Component Library is free software: you can redistribute it and/or 
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * The SDMX Component Library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with The SDMX Component Library If not, see 
 * http://www.gnu.org/licenses/lgpl.
 *
 * Contributors:
 * Metadata Technology - initial API and implementation
 ******************************************************************************/
package org.sdmxsource.sdmx.sdmxbeans.model.data.query;

import org.sdmxsource.sdmx.api.constants.DATA_QUERY_DETAIL;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TIME_FORMAT;
import org.sdmxsource.sdmx.api.exception.SdmxNoResultsException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.base.SdmxDate;
import org.sdmxsource.sdmx.api.model.beans.base.DataProviderBean;
import org.sdmxsource.sdmx.api.model.beans.base.DataProviderSchemeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.data.query.DataQuery;
import org.sdmxsource.sdmx.api.model.data.query.DataQuerySelection;
import org.sdmxsource.sdmx.api.model.data.query.DataQuerySelectionGroup;
import org.sdmxsource.sdmx.api.model.query.RESTDataQuery;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.SdmxDateImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.*;


/**
 * The type Data query.
 */
public class DataQueryImpl extends BaseDataQuery implements DataQuery {
    private static final long serialVersionUID = 1L;


    private Set<DataProviderBean> dataProviders = new HashSet<DataProviderBean>();
    private List<DataQuerySelectionGroup> dataQuerySelectionGroups = new ArrayList<DataQuerySelectionGroup>();
    private SdmxDate lastUpdated;
    private DATA_QUERY_DETAIL dataQueryDetail = DATA_QUERY_DETAIL.FULL;
    private boolean includeHistory;
    private String datasetId;

    /**
     * Build from a REST query and a bean retrival manager
     *
     * @param dataQuery        the data query
     * @param retrievalManager the retrieval manager
     */
    public DataQueryImpl(RESTDataQuery dataQuery, SdmxBeanRetrievalManager retrievalManager) {
        this.lastUpdated = dataQuery.getUpdatedAfter();
        if (dataQuery.getQueryDetail() != null) {
            this.dataQueryDetail = dataQuery.getQueryDetail();
        }
        this.includeHistory = dataQuery.includeHistory();
        this.firstNObs = dataQuery.getFirstNObservations();
        this.lastNObs = dataQuery.getlastNObsertations();
        if (ObjectUtil.validString(dataQuery.getDimensionAtObservation())) {
            this.dimensionAtObservation = dataQuery.getDimensionAtObservation();
        }

        this.dataflowBean = retrievalManager.getMaintainableBean(DataflowBean.class, dataQuery.getFlowRef().getMaintainableReference());
        if (dataflowBean == null) {
            throw new SdmxNoResultsException("No Dataflow exists for query : " + dataQuery.getFlowRef());
        }

        this.dataStructureBean = retrievalManager.getMaintainableBean(DataStructureBean.class, dataflowBean.getDataStructureRef().getMaintainableReference());
        if (dataStructureBean == null) {
            throw new SdmxSemmanticException("DSD could not be found for query : " + dataflowBean.getDataStructureRef());
        }

        if (dataQuery.getProviderRef() != null) {
            Set<DataProviderSchemeBean> dataProviderSchemes = retrievalManager.getMaintainableBeans(DataProviderSchemeBean.class, dataQuery.getProviderRef().getMaintainableReference());
            for (DataProviderSchemeBean currentDpScheme : dataProviderSchemes) {
                for (DataProviderBean dataProvider : currentDpScheme.getItems()) {
                    if (dataProvider.getId().equals(dataQuery.getProviderRef().getChildReference().getId())) {
                        dataProviders.add(dataProvider);
                    }
                }
            }
        }
        Set<DataQuerySelection> selections = new HashSet<DataQuerySelection>();
        if (dataQuery.getQueryList().size() > 0) {
            int i = 0;
            for (DimensionBean dimension : dataStructureBean.getDimensions(SDMX_STRUCTURE_TYPE.DIMENSION, SDMX_STRUCTURE_TYPE.MEASURE_DIMENSION)) {
                if (dataQuery.getQueryList().size() <= i) {
                    throw new SdmxSemmanticException("Not enough key values in query, expecting " + dataStructureBean.getDimensions(SDMX_STRUCTURE_TYPE.DIMENSION, SDMX_STRUCTURE_TYPE.MEASURE_DIMENSION).size() + " got " + dataQuery.getQueryList().size());
                }
                Set<String> queriesForDimension = dataQuery.getQueryList().get(i);
                if (queriesForDimension != null && queriesForDimension.size() > 0) {
                    DataQuerySelection selectionsForDimension =
                            new DataQueryDimensionSelectionImpl(dimension.getId(), new HashSet<String>(queriesForDimension));
                    selections.add(selectionsForDimension);
                }
                i++;
            }
        }


        if (ObjectUtil.validCollection(selections) || dataQuery.getStartPeriod() != null || dataQuery.getEndPeriod() != null) {
            dataQuerySelectionGroups.add(new DataQuerySelectionGroupImpl(selections, dataQuery.getStartPeriod(), dataQuery.getEndPeriod()));
        }
        validateQuery();
    }

    private DataQueryImpl(DataStructureBean dataStructureBean, DataflowBean dataflowBean, DATA_QUERY_DETAIL dataQueryDetail) {
        this(dataStructureBean, dataflowBean, null, dataQueryDetail);
    }

    private DataQueryImpl(DataStructureBean dataStructureBean, DataflowBean dataflowBean, String datasetId, DATA_QUERY_DETAIL dataQueryDetail) {
        this.dataStructureBean = dataStructureBean;
        this.dataQueryDetail = dataQueryDetail;
        this.dataflowBean = dataflowBean;
        this.datasetId = datasetId;
        validateQuery();
    }

    /**
     * Full Constructor
     *
     * @param dataStructureBean      the dataStructure (required)
     * @param lastUpdated            the last updated
     * @param dataQueryDetail        the data query detail
     * @param firstNObs              the first n obs
     * @param lastNObs               the last n obs
     * @param dataProviders          the data providers
     * @param dataflowBean           the dataflow (required)
     * @param dimensionAtObservation the dimension at observation
     * @param selections             the selections
     * @param dateFrom               the date from
     * @param dateTo                 the date to
     */
    public DataQueryImpl(DataStructureBean dataStructureBean,
                         SdmxDate lastUpdated,
                         DATA_QUERY_DETAIL dataQueryDetail,
                         Integer firstNObs,
                         Integer lastNObs,
                         Set<DataProviderBean> dataProviders,
                         DataflowBean dataflowBean,
                         String dimensionAtObservation,
                         Set<DataQuerySelection> selections,
                         Date dateFrom, Date dateTo) {
        this.dataStructureBean = dataStructureBean;
        if (dataProviders != null) {
            this.dataProviders = new HashSet<DataProviderBean>(dataProviders);
        }
        if (ObjectUtil.validCollection(selections) || dateFrom != null || dateTo != null) {
            SdmxDate sdmxDateFrom = null;
            if (dateFrom != null) {
                sdmxDateFrom = new SdmxDateImpl(dateFrom, TIME_FORMAT.DATE);
            }
            SdmxDate sdmxDateTo = null;
            if (dateTo != null) {
                sdmxDateTo = new SdmxDateImpl(dateTo, TIME_FORMAT.DATE);
            }
            this.dataQuerySelectionGroups.add(new DataQuerySelectionGroupImpl(selections, sdmxDateFrom, sdmxDateTo));
        }
        this.dataflowBean = dataflowBean;
        this.lastUpdated = lastUpdated;
        this.dataQueryDetail = dataQueryDetail;
        this.dimensionAtObservation = dimensionAtObservation;
        this.firstNObs = firstNObs;
        this.lastNObs = lastNObs;
        validateQuery();
    }

    /**
     * Instantiates a new Data query.
     *
     * @param dataStructureBean      the data structure bean
     * @param lastUpdated            the last updated
     * @param dataQueryDetail        the data query detail
     * @param maxObs                 the max obs
     * @param orderAsc               the order asc
     * @param dataProviders          the data providers
     * @param dataflowBean           the dataflow bean
     * @param dimensionAtObservation the dimension at observation
     * @param selections             the selections
     * @param dateFrom               the date from
     * @param dateTo                 the date to
     */
    public DataQueryImpl(DataStructureBean dataStructureBean,
                         SdmxDate lastUpdated,
                         DATA_QUERY_DETAIL dataQueryDetail,
                         Integer maxObs,
                         boolean orderAsc,
                         Set<DataProviderBean> dataProviders,
                         DataflowBean dataflowBean,
                         String dimensionAtObservation,
                         Set<DataQuerySelection> selections,
                         Date dateFrom, Date dateTo) {
        this.dataStructureBean = dataStructureBean;
        this.lastUpdated = lastUpdated;
        if (dataQueryDetail != null) {
            this.dataQueryDetail = dataQueryDetail;
        }
        if (orderAsc) {
            this.firstNObs = maxObs;
        } else {
            this.lastNObs = maxObs;
        }
        if (dataProviders != null) {
            this.dataProviders = new HashSet<DataProviderBean>(dataProviders);
        }
        this.dataflowBean = dataflowBean;
        this.dimensionAtObservation = dimensionAtObservation;
        if (ObjectUtil.validCollection(selections) || dateFrom != null || dateTo != null) {
            SdmxDate sdmxDateFrom = null;
            if (dateFrom != null) {
                sdmxDateFrom = new SdmxDateImpl(dateFrom, TIME_FORMAT.DATE);
            }
            SdmxDate sdmxDateTo = null;
            if (dateTo != null) {
                sdmxDateTo = new SdmxDateImpl(dateTo, TIME_FORMAT.DATE);
            }
            this.dataQuerySelectionGroups.add(new DataQuerySelectionGroupImpl(selections, sdmxDateFrom, sdmxDateTo));
        }
        validateQuery();
    }

    /**
     * Instantiates a new Data query.
     *
     * @param dataStructureBean      the data structure bean
     * @param lastUpdated            the last updated
     * @param dataQueryDetail        the data query detail
     * @param maxObs                 the max obs
     * @param orderAsc               the order asc
     * @param dataProviders          the data providers
     * @param dataflowBean           the dataflow bean
     * @param dimensionAtObservation the dimension at observation
     * @param selectionGroup         the selection group
     */
    public DataQueryImpl(DataStructureBean dataStructureBean,
                         SdmxDate lastUpdated,
                         DATA_QUERY_DETAIL dataQueryDetail,
                         Integer maxObs,
                         boolean orderAsc,
                         Set<DataProviderBean> dataProviders,
                         DataflowBean dataflowBean,
                         String dimensionAtObservation,
                         Collection<DataQuerySelectionGroup> selectionGroup) {
        this.dataStructureBean = dataStructureBean;
        this.lastUpdated = lastUpdated;
        if (dataQueryDetail != null) {
            this.dataQueryDetail = dataQueryDetail;
        }
        if (orderAsc) {
            this.firstNObs = maxObs;
        } else {
            this.lastNObs = maxObs;
        }
        if (dataProviders != null) {
            this.dataProviders = new HashSet<DataProviderBean>(dataProviders);
        }
        this.dataflowBean = dataflowBean;
        this.dimensionAtObservation = dimensionAtObservation;
        if (selectionGroup != null) {
            for (DataQuerySelectionGroup dqsg : selectionGroup) {
                if (dqsg != null) {
                    this.dataQuerySelectionGroups.add(dqsg);
                }
            }
        }
        validateQuery();
    }

    /**
     * Instantiates a new Data query.
     *
     * @param dataStructureBean      the data structure bean
     * @param lastUpdated            the last updated
     * @param dataQueryDetail        the data query detail
     * @param maxObs                 the max obs
     * @param orderAsc               the order asc
     * @param dataProviders          the data providers
     * @param dataflowBean           the dataflow bean
     * @param dimensionAtObservation the dimension at observation
     * @param selectionGroup         the selection group
     */
    public DataQueryImpl(DataStructureBean dataStructureBean,
                         SdmxDate lastUpdated,
                         DATA_QUERY_DETAIL dataQueryDetail,
                         Integer maxObs,
                         boolean orderAsc,
                         Set<DataProviderBean> dataProviders,
                         DataflowBean dataflowBean,
                         String dimensionAtObservation,
                         DataQuerySelectionGroup... selectionGroup) {
        this.dataStructureBean = dataStructureBean;
        this.lastUpdated = lastUpdated;
        if (dataQueryDetail != null) {
            this.dataQueryDetail = dataQueryDetail;
        }
        if (orderAsc) {
            this.firstNObs = maxObs;
        } else {
            this.lastNObs = maxObs;
        }
        if (dataProviders != null) {
            this.dataProviders = new HashSet<DataProviderBean>(dataProviders);
        }
        this.dataflowBean = dataflowBean;
        this.dimensionAtObservation = dimensionAtObservation;

        if (selectionGroup != null) {
            for (DataQuerySelectionGroup dqsg : selectionGroup) {
                if (dqsg != null) {
                    this.dataQuerySelectionGroups.add(dqsg);
                }
            }
        }
        validateQuery();
    }

    /**
     * Build empty query data query.
     *
     * @param dataStructureBean the data structure bean
     * @param dataflowBean      the dataflow bean
     * @param dataQueryDetail   the data query detail
     * @return the data query
     */
    public static DataQuery buildEmptyQuery(DataStructureBean dataStructureBean, DataflowBean dataflowBean, DATA_QUERY_DETAIL dataQueryDetail) {
        return new DataQueryImpl(dataStructureBean, dataflowBean, dataQueryDetail);
    }

    /**
     * Build empty query data query.
     *
     * @param dataStructureBean the data structure bean
     * @param dataflowBean      the dataflow bean
     * @param datasetid         the datasetid
     * @param dataQueryDetail   the data query detail
     * @return the data query
     */
    public static DataQuery buildEmptyQuery(DataStructureBean dataStructureBean, DataflowBean dataflowBean, String datasetid, DATA_QUERY_DETAIL dataQueryDetail) {
        return new DataQueryImpl(dataStructureBean, dataflowBean, datasetid, dataQueryDetail);

    }

    @Override
    protected Set<String> getQueryComponentIds() {
        Set<String> returnSet = new HashSet<String>();
        for (DataQuerySelectionGroup dqsg : getSelectionGroups()) {
            for (DataQuerySelection dqs : dqsg.getSelections()) {
                returnSet.add(dqs.getComponentId());
            }
        }
        return returnSet;
    }

    @Override
    public boolean includeHistory() {
        return includeHistory;
    }

    @Override
    public String getDatasetId() {
        return datasetId;
    }

    @Override
    public String dimensionAtObservation() {
        return dimensionAtObservation;
    }

    @Override
    public DataflowBean getDataflow() {
        return dataflowBean;
    }

    @Override
    public Set<DataProviderBean> getDataProvider() {
        return new HashSet<DataProviderBean>(dataProviders);
    }

    @Override
    public boolean hasSelections() {
        return dataQuerySelectionGroups.size() > 0;
    }

    @Override
    public List<DataQuerySelectionGroup> getSelectionGroups() {
        return new ArrayList<DataQuerySelectionGroup>(dataQuerySelectionGroups);
    }

    @Override
    public DataStructureBean getDataStructure() {
        return dataStructureBean;
    }

    @Override
    public DATA_QUERY_DETAIL getDataQueryDetail() {
        return dataQueryDetail;
    }

    @Override
    public SdmxDate getLastUpdatedDate() {
        return lastUpdated;
    }

    @Override
    public Integer getLastNObservations() {
        return lastNObs;
    }

    @Override
    public Integer getFirstNObservations() {
        return firstNObs;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        if (dataStructureBean != null) {
            sb.append(newLine + "Data Structure : " + dataStructureBean.getUrn());
        }
        if (dataflowBean != null) {
            sb.append(newLine + "Dataflow : " + dataflowBean.getUrn());
        }
        if (dataProviders != null) {
            for (DataProviderBean dataProvider : dataProviders) {
                sb.append(newLine + "Data Provider  : " + dataProvider.getUrn());
            }
        }

        //ADD SELECTION INFORMATION
        if (hasSelections()) {
            String concat = "";
            for (DataQuerySelectionGroup selectionGroup : dataQuerySelectionGroups) {
                sb.append(concat + "(" + selectionGroup.toString() + ")");
                concat = "OR";
            }
        }

        return sb.toString();
    }

}
