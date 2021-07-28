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
package org.sdmxsource.sdmx.sdmxbeans.model.data.query.complex;

import org.sdmxsource.sdmx.api.constants.*;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.base.SdmxDate;
import org.sdmxsource.sdmx.api.model.beans.base.DataProviderBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.complex.TimeRange;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.data.query.complex.ComplexComponentValue;
import org.sdmxsource.sdmx.api.model.data.query.complex.ComplexDataQuery;
import org.sdmxsource.sdmx.api.model.data.query.complex.ComplexDataQuerySelection;
import org.sdmxsource.sdmx.api.model.data.query.complex.ComplexDataQuerySelectionGroup;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.SdmxDateImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.data.query.BaseDataQuery;
import org.sdmxsource.util.ObjectUtil;

import java.util.*;

/**
 * The type Complex data query.
 */
public class ComplexDataQueryImpl extends BaseDataQuery implements ComplexDataQuery {

    private static final long serialVersionUID = 2107508015963353910L;

    private Integer defaultLimit;
    private OBSERVATION_ACTION obsAction;
    private boolean hasExplicitMeasures = false;
    private DATA_QUERY_DETAIL queryDetail;
    private String datasetId;
    private TEXT_SEARCH datasetIdOperator;

    private Set<DataProviderBean> dataProviders = new HashSet<DataProviderBean>();
    private ProvisionAgreementBean provisionAgreement;
    private List<TimeRange> lastUpdatedDate = new ArrayList<TimeRange>();
    private List<ComplexDataQuerySelectionGroup> complexDataQuerySelectionGroups = new ArrayList<ComplexDataQuerySelectionGroup>();

    /**
     * Instantiates a new Complex data query.
     *
     * @param datasetId                      the dataset id
     * @param datasetIdOperator              the dataset id operator
     * @param dataProviders                  the data providers
     * @param dataStructure                  the data structure
     * @param dataFlow                       the data flow
     * @param provisionAgreement             the provision agreement
     * @param lastUpdatedDate                the last updated date
     * @param maxObs                         the max obs
     * @param defaultLimit                   the default limit
     * @param orderAsc                       the order asc
     * @param obsAction                      the obs action
     * @param dimensionAtObservation         the dimension at observation
     * @param hasExplicitMeasures            the has explicit measures
     * @param queryDetail                    the query detail
     * @param complexDataQuerySelectionGroup the complex data query selection group
     */
    public ComplexDataQueryImpl(String datasetId,
                                TEXT_SEARCH datasetIdOperator,
                                Set<DataProviderBean> dataProviders,
                                DataStructureBean dataStructure,
                                DataflowBean dataFlow,
                                ProvisionAgreementBean provisionAgreement,
                                List<TimeRange> lastUpdatedDate,
                                Integer maxObs,
                                Integer defaultLimit,
                                boolean orderAsc,
                                OBSERVATION_ACTION obsAction,
                                String dimensionAtObservation,
                                boolean hasExplicitMeasures,
                                DATA_QUERY_DETAIL queryDetail,
                                Collection<ComplexDataQuerySelectionGroup> complexDataQuerySelectionGroup) {

        this.datasetId = datasetId;
        if (datasetIdOperator != null)
            this.datasetIdOperator = datasetIdOperator;
        else
            this.datasetIdOperator = TEXT_SEARCH.EQUAL;
        if (dataProviders != null) {
            this.dataProviders = new HashSet<DataProviderBean>(dataProviders);
        }
        this.dataStructureBean = dataStructure;
        this.dataflowBean = dataFlow;
        this.provisionAgreement = provisionAgreement;
        if (lastUpdatedDate != null) {
            this.lastUpdatedDate = new ArrayList<TimeRange>(lastUpdatedDate);
        }
        if (orderAsc)
            this.firstNObs = maxObs;
        else
            this.lastNObs = maxObs;

        this.defaultLimit = defaultLimit;

        if (obsAction != null)
            this.obsAction = obsAction;
        else
            this.obsAction = OBSERVATION_ACTION.ACTIVE;

        this.dimensionAtObservation = dimensionAtObservation;
        if (dimensionAtObservation != null) {
            //the values: 'AllDimensions' and 'TIME_PERIOD' are valid values.
            if (dimensionAtObservation.equals(DIMENSION_AT_OBSERVATION.ALL.getVal()) || dimensionAtObservation.equals(DIMENSION_AT_OBSERVATION.TIME.getVal()))
                this.dimensionAtObservation = dimensionAtObservation;
            else//check if the value is a dimension Value
                checkDimensionExistence(dimensionAtObservation, dataStructure);
        } else {
            this.dimensionAtObservation = getDimensionAtObservationLevel(dataStructure);
        }
        this.hasExplicitMeasures = hasExplicitMeasures;
        if (queryDetail != null)
            this.queryDetail = queryDetail;
        else
            this.queryDetail = DATA_QUERY_DETAIL.FULL;

        if (complexDataQuerySelectionGroup != null) {
            for (ComplexDataQuerySelectionGroup cdqsg : complexDataQuerySelectionGroup) {
                if (cdqsg != null) {
                    this.complexDataQuerySelectionGroups.add(cdqsg);
                }
            }
        }
        //perform validation
        validateQuery();
        validateProvisionAgreement();
    }


    /**
     * Instantiates a new Complex data query.
     *
     * @param datasetId                      the dataset id
     * @param datasetIdOperator              the dataset id operator
     * @param dataProviders                  the data providers
     * @param dataStructure                  the data structure
     * @param dataFlow                       the data flow
     * @param provisionAgreement             the provision agreement
     * @param lastUpdatedDate                the last updated date
     * @param firstNObs                      the first n obs
     * @param lastNObs                       the last n obs
     * @param defaultLimit                   the default limit
     * @param obsAction                      the obs action
     * @param dimensionAtObservation         the dimension at observation
     * @param hasExplicitMeasures            the has explicit measures
     * @param queryDetail                    the query detail
     * @param complexDataQuerySelectionGroup the complex data query selection group
     */
    public ComplexDataQueryImpl(String datasetId, TEXT_SEARCH datasetIdOperator, Set<DataProviderBean> dataProviders,
                                DataStructureBean dataStructure, DataflowBean dataFlow,
                                ProvisionAgreementBean provisionAgreement, List<TimeRange> lastUpdatedDate,
                                Integer firstNObs, Integer lastNObs, Integer defaultLimit, OBSERVATION_ACTION obsAction, String dimensionAtObservation,
                                boolean hasExplicitMeasures, DATA_QUERY_DETAIL queryDetail, Collection<ComplexDataQuerySelectionGroup> complexDataQuerySelectionGroup) {

        this.datasetId = datasetId;
        if (datasetIdOperator != null) {
            this.datasetIdOperator = datasetIdOperator;
        } else {
            this.datasetIdOperator = TEXT_SEARCH.EQUAL;
        }

        if (dataProviders != null) {
            this.dataProviders = new HashSet<DataProviderBean>(dataProviders);
        }
        if (lastUpdatedDate != null) {
            this.lastUpdatedDate = new ArrayList<TimeRange>(lastUpdatedDate);
        }

        this.dimensionAtObservation = dimensionAtObservation;
        this.dataStructureBean = dataStructure;
        this.dataflowBean = dataFlow;
        this.provisionAgreement = provisionAgreement;
        this.firstNObs = firstNObs;
        this.lastNObs = lastNObs;
        this.defaultLimit = defaultLimit;

        if (obsAction != null) {
            this.obsAction = obsAction;
        } else {
            this.obsAction = OBSERVATION_ACTION.ACTIVE;
        }

        if (dimensionAtObservation != null) {
            //the values: 'AllDimensions' and 'TIME_PERIOD' are valid values.
            if (dimensionAtObservation.equals(DIMENSION_AT_OBSERVATION.ALL.getVal()) || dimensionAtObservation.equals(DIMENSION_AT_OBSERVATION.TIME.getVal()))
                this.dimensionAtObservation = dimensionAtObservation;
            else//check if the value is a dimension Value
                checkDimensionExistence(dimensionAtObservation, dataStructure);
        } else {
            this.dimensionAtObservation = getDimensionAtObservationLevel(dataStructure);
        }

        this.hasExplicitMeasures = hasExplicitMeasures;
        if (queryDetail != null)
            this.queryDetail = queryDetail;
        else
            this.queryDetail = DATA_QUERY_DETAIL.FULL;
        if (complexDataQuerySelectionGroup != null) {
            for (ComplexDataQuerySelectionGroup cdqsg : complexDataQuerySelectionGroup) {
                if (cdqsg != null) {
                    this.complexDataQuerySelectionGroups.add(cdqsg);
                }
            }
        }
        //perform validation
        validateQuery();
        validateProvisionAgreement();
    }

    /**
     * Instantiates a new Complex data query.
     *
     * @param datasetId                      the dataset id
     * @param datasetIdOperator              the dataset id operator
     * @param dataProviders                  the data providers
     * @param dataStructure                  the data structure
     * @param dataFlow                       the data flow
     * @param provisionAgreement             the provision agreement
     * @param lastUpdatedDate                the last updated date
     * @param maxObs                         the max obs
     * @param orderAsc                       the order asc
     * @param defaultLimit                   the default limit
     * @param obsAction                      the obs action
     * @param dimensionAtObservation         the dimension at observation
     * @param hasExplicitMeasures            the has explicit measures
     * @param queryDetail                    the query detail
     * @param complexDataQuerySelectionGroup the complex data query selection group
     */
    public ComplexDataQueryImpl(String datasetId, TEXT_SEARCH datasetIdOperator, Set<DataProviderBean> dataProviders,
                                DataStructureBean dataStructure, DataflowBean dataFlow,
                                ProvisionAgreementBean provisionAgreement, List<TimeRange> lastUpdatedDate,
                                Integer maxObs, boolean orderAsc, Integer defaultLimit, OBSERVATION_ACTION obsAction, String dimensionAtObservation,
                                boolean hasExplicitMeasures, DATA_QUERY_DETAIL queryDetail, ComplexDataQuerySelectionGroup... complexDataQuerySelectionGroup) {
        this.datasetId = datasetId;
        if (datasetIdOperator != null) {
            this.datasetIdOperator = datasetIdOperator;
        } else {
            this.datasetIdOperator = TEXT_SEARCH.EQUAL;
        }
        if (dataProviders != null) {
            this.dataProviders = new HashSet<DataProviderBean>(dataProviders);
        }
        if (lastUpdatedDate != null) {
            this.lastUpdatedDate = new ArrayList<TimeRange>(lastUpdatedDate);
        }


        this.dimensionAtObservation = dimensionAtObservation;
        this.dataStructureBean = dataStructure;
        this.dataflowBean = dataFlow;
        this.provisionAgreement = provisionAgreement;
        if (orderAsc) {
            this.firstNObs = maxObs;
        } else {
            this.lastNObs = maxObs;
        }

        this.defaultLimit = defaultLimit;
        if (obsAction != null) {
            this.obsAction = obsAction;
        } else {
            this.obsAction = OBSERVATION_ACTION.ACTIVE;
        }

        if (dimensionAtObservation != null) {
            //the values: 'AllDimensions' and 'TIME_PERIOD' are valid values.
            if (dimensionAtObservation.equals(DIMENSION_AT_OBSERVATION.ALL.getVal()) || dimensionAtObservation.equals(DIMENSION_AT_OBSERVATION.TIME.getVal()))
                this.dimensionAtObservation = dimensionAtObservation;
            else//check if the value is a dimension Value
                checkDimensionExistence(dimensionAtObservation, dataStructure);
        } else {
            this.dimensionAtObservation = getDimensionAtObservationLevel(dataStructure);
        }

        this.hasExplicitMeasures = hasExplicitMeasures;
        if (queryDetail != null) {
            this.queryDetail = queryDetail;
        } else {
            this.queryDetail = DATA_QUERY_DETAIL.FULL;
        }

        if (complexDataQuerySelectionGroup != null) {
            for (ComplexDataQuerySelectionGroup cdqsg : complexDataQuerySelectionGroup) {
                if (cdqsg != null) {
                    this.complexDataQuerySelectionGroups.add(cdqsg);
                }
            }
        }
        //perform validation
        validateQuery();
        validateProvisionAgreement();

    }

    /**
     * Instantiates a new Complex data query.
     *
     * @param datasetId              the dataset id
     * @param datasetIdOperator      the dataset id operator
     * @param dataProviders          the data providers
     * @param dataStructure          the data structure
     * @param dataFlow               the data flow
     * @param provisionAgreement     the provision agreement
     * @param lastUpdatedDate        the last updated date
     * @param maxObs                 the max obs
     * @param orderAsc               the order asc
     * @param defaultLimit           the default limit
     * @param obsAction              the obs action
     * @param dimensionAtObservation the dimension at observation
     * @param hasExplicitMeasures    the has explicit measures
     * @param queryDetail            the query detail
     * @param complexSelections      the complex selections
     * @param dateFrom               the date from
     * @param dateFromOperator       the date from operator
     * @param dateTo                 the date to
     * @param dateToOperator         the date to operator
     * @param primaryMeasureValues   the primary measure values
     */
    public ComplexDataQueryImpl(String datasetId, TEXT_SEARCH datasetIdOperator, Set<DataProviderBean> dataProviders,
                                DataStructureBean dataStructure, DataflowBean dataFlow,
                                ProvisionAgreementBean provisionAgreement, List<TimeRange> lastUpdatedDate,
                                Integer maxObs, boolean orderAsc, Integer defaultLimit, OBSERVATION_ACTION obsAction,
                                String dimensionAtObservation, boolean hasExplicitMeasures, DATA_QUERY_DETAIL queryDetail,
                                Set<ComplexDataQuerySelection> complexSelections,
                                Date dateFrom, ORDERED_OPERATOR dateFromOperator, Date dateTo, ORDERED_OPERATOR dateToOperator, Set<ComplexComponentValue> primaryMeasureValues) {
        this.datasetId = datasetId;
        if (datasetIdOperator != null) {
            this.datasetIdOperator = datasetIdOperator;
        } else {
            this.datasetIdOperator = TEXT_SEARCH.EQUAL;
        }

        if (dataProviders != null) {
            this.dataProviders = new HashSet<DataProviderBean>(dataProviders);
        }
        if (lastUpdatedDate != null) {
            this.lastUpdatedDate = new ArrayList<TimeRange>(lastUpdatedDate);
        }


        this.dimensionAtObservation = dimensionAtObservation;
        this.dataStructureBean = dataStructure;
        this.dataflowBean = dataFlow;
        this.provisionAgreement = provisionAgreement;
        if (orderAsc) {
            this.firstNObs = maxObs;
        } else {
            this.lastNObs = maxObs;
        }
        this.defaultLimit = defaultLimit;
        if (obsAction != null)
            this.obsAction = obsAction;
        else
            this.obsAction = OBSERVATION_ACTION.ACTIVE;

        if (dimensionAtObservation != null) {
            //the values: 'AllDimensions' and 'TIME_PERIOD' are valid values.
            if (dimensionAtObservation.equals(DIMENSION_AT_OBSERVATION.ALL.getVal()) || dimensionAtObservation.equals(DIMENSION_AT_OBSERVATION.TIME.getVal())) {
                this.dimensionAtObservation = dimensionAtObservation;
            } else {
                //check if the value is a dimension Value
                checkDimensionExistence(dimensionAtObservation, dataStructure);
            }
        } else {
            this.dimensionAtObservation = getDimensionAtObservationLevel(dataStructure);
        }

        this.hasExplicitMeasures = hasExplicitMeasures;
        if (queryDetail != null) {
            this.queryDetail = queryDetail;
        } else {
            this.queryDetail = DATA_QUERY_DETAIL.FULL;
        }
        if (ObjectUtil.validCollection(complexSelections) || dateFrom != null || dateTo != null) {
            SdmxDate sdmxDateFrom = null;
            if (dateFrom != null) {
                sdmxDateFrom = new SdmxDateImpl(dateFrom, TIME_FORMAT.DATE);
            }
            SdmxDate sdmxDateTo = null;
            if (dateFrom != null) {
                sdmxDateTo = new SdmxDateImpl(dateTo, TIME_FORMAT.DATE);
            }
            this.complexDataQuerySelectionGroups.add(new ComplexDataQuerySelectionGroupImpl(complexSelections, sdmxDateFrom, dateFromOperator, sdmxDateTo, dateToOperator, primaryMeasureValues));
        }

        //perform validation
        validateQuery();
        validateProvisionAgreement();

    }

    /**
     * Instantiates a new Complex data query.
     *
     * @param datasetId              the dataset id
     * @param datasetIdOperator      the dataset id operator
     * @param dataProviders          the data providers
     * @param dataStructure          the data structure
     * @param dataFlow               the data flow
     * @param provisionAgreement     the provision agreement
     * @param lastUpdatedDate        the last updated date
     * @param firstNObs              the first n obs
     * @param lastNObs               the last n obs
     * @param defaultLimit           the default limit
     * @param obsAction              the obs action
     * @param dimensionAtObservation the dimension at observation
     * @param hasExplicitMeasures    the has explicit measures
     * @param queryDetail            the query detail
     * @param complexSelections      the complex selections
     * @param dateFrom               the date from
     * @param dateFromOperator       the date from operator
     * @param dateTo                 the date to
     * @param dateToOperator         the date to operator
     * @param primaryMeasureValues   the primary measure values
     */
    public ComplexDataQueryImpl(String datasetId, TEXT_SEARCH datasetIdOperator, Set<DataProviderBean> dataProviders,
                                DataStructureBean dataStructure, DataflowBean dataFlow,
                                ProvisionAgreementBean provisionAgreement, List<TimeRange> lastUpdatedDate,
                                Integer firstNObs, Integer lastNObs, Integer defaultLimit, OBSERVATION_ACTION obsAction, String dimensionAtObservation,
                                boolean hasExplicitMeasures, DATA_QUERY_DETAIL queryDetail, Set<ComplexDataQuerySelection> complexSelections,
                                Date dateFrom, ORDERED_OPERATOR dateFromOperator, Date dateTo, ORDERED_OPERATOR dateToOperator, Set<ComplexComponentValue> primaryMeasureValues) {

        this.datasetId = datasetId;
        if (datasetIdOperator != null)
            this.datasetIdOperator = datasetIdOperator;
        else
            this.datasetIdOperator = TEXT_SEARCH.EQUAL;
        this.dataProviders = dataProviders;
        this.dataStructureBean = dataStructure;
        this.dataflowBean = dataFlow;
        this.provisionAgreement = provisionAgreement;
        this.lastUpdatedDate = lastUpdatedDate;
        this.firstNObs = firstNObs;
        this.lastNObs = lastNObs;
        this.defaultLimit = defaultLimit;
        if (obsAction != null)
            this.obsAction = obsAction;
        else
            this.obsAction = OBSERVATION_ACTION.ACTIVE;


        this.dimensionAtObservation = dimensionAtObservation;
        if (dimensionAtObservation != null) {
            //the values: 'AllDimensions' and 'TIME_PERIOD' are valid values.
            if (dimensionAtObservation.equals(DIMENSION_AT_OBSERVATION.ALL.getVal()) || dimensionAtObservation.equals(DIMENSION_AT_OBSERVATION.TIME.getVal()))
                this.dimensionAtObservation = dimensionAtObservation;
            else//check if the value is a dimension Value
                checkDimensionExistence(dimensionAtObservation, dataStructure);
        } else {
            this.dimensionAtObservation = getDimensionAtObservationLevel(dataStructure);
        }

        this.hasExplicitMeasures = hasExplicitMeasures;
        if (queryDetail != null)
            this.queryDetail = queryDetail;
        else
            this.queryDetail = DATA_QUERY_DETAIL.FULL;
        if (ObjectUtil.validCollection(complexSelections) || dateFrom != null || dateTo != null) {
            SdmxDate sdmxDateFrom = null;
            if (dateFrom != null) {
                sdmxDateFrom = new SdmxDateImpl(dateFrom, TIME_FORMAT.DATE);
            }
            SdmxDate sdmxDateTo = null;
            if (dateFrom != null) {
                sdmxDateTo = new SdmxDateImpl(dateTo, TIME_FORMAT.DATE);
            }
            this.complexDataQuerySelectionGroups.add(new ComplexDataQuerySelectionGroupImpl(complexSelections, sdmxDateFrom, dateFromOperator, sdmxDateTo, dateToOperator, primaryMeasureValues));
        }

        //perform validation
        validateQuery();
        validateProvisionAgreement();
    }

    @Override
    protected Set<String> getQueryComponentIds() {
        Set<String> returnSet = new HashSet<String>();
        for (ComplexDataQuerySelectionGroup dqsg : getSelectionGroups()) {
            for (ComplexDataQuerySelection dqs : dqsg.getSelections()) {
                returnSet.add(dqs.getComponentId());
            }
        }
        return returnSet;
    }

    /**
     * It performs validation upon the provision agreement information. If the current dataflow is not referenced
     * by the provision agreement then an exception occurs.
     */
    private void validateProvisionAgreement() {
        if (provisionAgreement != null) {
            //get the dataflow id, version and agency
            CrossReferenceBean dataflowReference = provisionAgreement.getStructureUseage();
            if (dataflowReference == null) {
                throw new SdmxException("Can not create DataQuery, Dataflow is required", SDMX_ERROR_CODE.SEMANTIC_ERROR);
            } else {
                if (!dataflowReference.getMaintainableReference().getMaintainableId().equals(dataflowBean.getId()) || !dataflowReference.getMaintainableReference().getVersion().equals(dataflowBean.getVersion()) ||
                        !dataflowReference.getMaintainableReference().getAgencyId().equals(dataflowBean.getAgencyId())) {
                    throw new SdmxException("Can not create DataQuery, Dataflow provided is not referenced by the Provision Agreement", SDMX_ERROR_CODE.SEMANTIC_ERROR);
                }
            }

        }
    }


    /**
     * It checks for the existence of the provided dimensionAtObs in the data structure definition.
     * If it is not included an error is thrown.
     *
     * @param dimensionAtObs
     * @param dataStructure
     */
    private void checkDimensionExistence(String dimensionAtObs, DataStructureBean dataStructure) {
        DimensionBean dimension = dataStructure.getDimension(dimensionAtObs);
        if (dimension == null)
            throw new IllegalArgumentException("Can not create DataQuery, The dimension at observation is not included in the Dimension list of the DSD ");
    }

    /**
     * It returns the default value for the dimension at observation in accordance with the DSD. <br>
     * For a time series DSD, the return value equals to the Time Dimension. For a crossX DSD it returns the <br>
     * measure dimension value
     *
     * @param dataStructure
     * @return
     */
    private String getDimensionAtObservationLevel(DataStructureBean dataStructure) {
        if (dataStructure.getTimeDimension() != null)
            return DIMENSION_AT_OBSERVATION.TIME.getVal();
        else//get the measure dimension id
            return dataStructure.getDimensions(SDMX_STRUCTURE_TYPE.MEASURE_DIMENSION).get(0).getId();
    }


    @Override
    public OBSERVATION_ACTION getObservationAction() {
        return obsAction;
    }

    @Override
    public String dimensionAtObservation() {
        return dimensionAtObservation;
    }

    @Override
    public boolean hasExplicitMeasures() {
        return hasExplicitMeasures;
    }

    @Override
    public DATA_QUERY_DETAIL getDataQueryDetail() {
        return this.queryDetail;
    }

    @Override
    public String getDatasetId() {
        return this.datasetId;
    }

    @Override
    public TEXT_SEARCH getDatasetIdOperator() {
        return datasetIdOperator;
    }

    @Override
    public List<TimeRange> getLastUpdatedDateTimeRange() {
        return new ArrayList<TimeRange>(lastUpdatedDate);
    }

    @Override
    public Set<DataProviderBean> getDataProvider() {
        return new HashSet<DataProviderBean>(dataProviders);
    }

    @Override
    public DataStructureBean getDataStructure() {
        return this.dataStructureBean;
    }

    @Override
    public DataflowBean getDataflow() {
        return this.dataflowBean;
    }

    @Override
    public ProvisionAgreementBean getProvisionAgreement() {
        return this.provisionAgreement;
    }

    @Override
    public List<ComplexDataQuerySelectionGroup> getSelectionGroups() {
        return new ArrayList<ComplexDataQuerySelectionGroup>(complexDataQuerySelectionGroups);
    }

    @Override
    public boolean hasSelections() {
        return this.complexDataQuerySelectionGroups.size() > 0;
    }


    @Override
    public Integer getFirstNObservations() {
        return firstNObs;
    }

    @Override
    public Integer getLastNObservations() {
        return lastNObs;
    }

    @Override
    public Integer getDefaultLimit() {
        return defaultLimit;
    }

}
