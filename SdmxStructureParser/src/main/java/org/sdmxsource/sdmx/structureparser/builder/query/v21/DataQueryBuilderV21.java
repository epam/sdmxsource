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
package org.sdmxsource.sdmx.structureparser.builder.query.v21;

import org.sdmx.resources.sdmxml.schemas.v21.common.DataProviderReferenceType;
import org.sdmx.resources.sdmxml.schemas.v21.common.DataStructureRequestType;
import org.sdmx.resources.sdmxml.schemas.v21.common.TimePeriodRangeType;
import org.sdmx.resources.sdmxml.schemas.v21.common.TimeRangeValueType;
import org.sdmx.resources.sdmxml.schemas.v21.query.*;
import org.sdmxsource.sdmx.api.constants.*;
import org.sdmxsource.sdmx.api.exception.SdmxNoResultsException;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.base.SdmxDate;
import org.sdmxsource.sdmx.api.model.beans.base.DataProviderBean;
import org.sdmxsource.sdmx.api.model.beans.base.DataProviderSchemeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.complex.TimeRange;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.data.query.complex.ComplexComponentValue;
import org.sdmxsource.sdmx.api.model.data.query.complex.ComplexDataQuery;
import org.sdmxsource.sdmx.api.model.data.query.complex.ComplexDataQuerySelection;
import org.sdmxsource.sdmx.api.model.data.query.complex.ComplexDataQuerySelectionGroup;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.SdmxDateImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.reference.complex.TimeRangeImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.data.query.complex.ComplexComponentValueImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.data.query.complex.ComplexDataQueryImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.data.query.complex.ComplexDataQuerySelectionGroupImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.data.query.complex.ComplexDataQuerySelectionImpl;
import org.sdmxsource.sdmx.util.beans.reference.MaintainableRefBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The type Data query builder v 21.
 */
public class DataQueryBuilderV21 {


    /**
     * Build complex data query list.
     *
     * @param dataQueryType             the data query type
     * @param structureRetrievalManager the structure retrieval manager
     * @return the list
     */
    public List<ComplexDataQuery> buildComplexDataQuery(DataQueryType dataQueryType, SdmxBeanRetrievalManager structureRetrievalManager) {
        if (structureRetrievalManager == null) {
            throw new RuntimeException("ComplexDataQueryBuilder expectes a SdmxBeanRetrievalManager");
        }

        List<ComplexDataQuery> returnList = new ArrayList<ComplexDataQuery>();
        DataReturnDetailsType returnDetails = dataQueryType.getReturnDetails();
        DataParametersAndType dataWhere = dataQueryType.getDataWhere();
        /**process the DataReturnDetailsType*/
        DATA_QUERY_DETAIL queryDetail = getReturnDetailsDetail(returnDetails);
        Integer firstNObs = getReturnDetailsFirstNobs(returnDetails);
        Integer lastNObs = getReturnDetailsLastNobs(returnDetails);
        Integer defaultLimit = getReturnDetailsDefaultLimit(returnDetails);
        OBSERVATION_ACTION obsAction = getReturnDetailsObsAction(returnDetails);
        List structureReferenceDetails = getStructureRefDetails(returnDetails);
        String dimensionAtObservation = (structureReferenceDetails != null) ? (String) structureReferenceDetails.get(0) : null;
        boolean hasExplicitMeasures = (structureReferenceDetails != null && structureReferenceDetails.size() > 1) ? (Boolean) structureReferenceDetails.get(1) : false;
        /**process the DataParametersAndType*/
        //Get the Data Providers
        Set<DataProviderBean> dataProviders = getDataWhereDataProviders(dataWhere, structureRetrievalManager);
        //Get the DatasetId
        String datasetArray[] = getDataWhereDatasetId(dataWhere);
        String datasetId = (datasetArray != null) ? datasetArray[0] : null;
        TEXT_SEARCH datasetIdOper = (datasetArray != null) ? TEXT_SEARCH.parseString(datasetArray[1]) : null;
        //Get the DataFlow
        DataflowBean dataFlow = getDataWhereDataFlow(dataWhere, structureRetrievalManager);
        //Get the Data Structure Definition
        DataStructureBean dataStructure = getDataWhereDataStrucuture(dataWhere, structureRetrievalManager, dataFlow);
        //Get the Provision Agreement
        ProvisionAgreementBean provisionAgreement = getProvisionAgreement(dataWhere, structureRetrievalManager);
        //Get the Updated dates
        List<TimeRange> updatedDates = getDataWhereUpdatedDates(dataWhere);
        //Get the ComplexDataQueryGoups
        Set<ComplexDataQuerySelectionGroup> complexDataQuerySelectionGroups = buildComplexDataQueryGroups(dataWhere, structureRetrievalManager, dataProviders);
        //Build the complex Query
        ComplexDataQuery complexQuery = new ComplexDataQueryImpl(datasetId, datasetIdOper, dataProviders, dataStructure, dataFlow, provisionAgreement, updatedDates, firstNObs, lastNObs, defaultLimit, obsAction, dimensionAtObservation, hasExplicitMeasures, queryDetail, complexDataQuerySelectionGroups);
        returnList.add(complexQuery);
        return returnList;
    }

    /**
     * Process the datawhere updated element to get the last updated information
     *
     * @param dataWhere
     * @return, can be null
     */
    private List<TimeRange> getDataWhereUpdatedDates(
            DataParametersAndType dataWhere) {

        List<TimeRange> updatedDates = new ArrayList<TimeRange>();
        if (dataWhere.getUpdatedList().size() > 0) {
            //there should exist one or 2 time range values
            for (int i = 0; i < dataWhere.getUpdatedList().size(); i++) {
                TimeRangeValueType timeRangeValueType = dataWhere.getUpdatedList().get(i);
                updatedDates.add(buildTimeRange(timeRangeValueType));
            }
        }
        return updatedDates;
    }

    /**
     * Processes the dataWhere element to get the Provision Agreement reference and retrieve the respective artefact bean.
     * If the provision agreement cannot be found it throws an exception
     *
     * @param dataWhere
     * @param structureRetrievalManager
     * @return, can be NULL.
     */
    private ProvisionAgreementBean getProvisionAgreement(DataParametersAndType dataWhere,
                                                         SdmxBeanRetrievalManager structureRetrievalManager) {

        ProvisionAgreementBean provisionAgreement = null;

        if (dataWhere.getProvisionAgreementList() != null && dataWhere.getProvisionAgreementList().size() > 0) {
            String praAgency = dataWhere.getProvisionAgreementList().get(0).getRef().getAgencyID();
            String praId = dataWhere.getProvisionAgreementList().get(0).getRef().getId();
            String praVersion = null;
            if (dataWhere.getProvisionAgreementList().get(0).getRef().isSetVersion()) {
                praVersion = dataWhere.getProvisionAgreementList().get(0).getRef().getVersion();
            }
            MaintainableRefBean praRef = new MaintainableRefBeanImpl(praAgency, praId, praVersion);
            provisionAgreement = structureRetrievalManager.getMaintainableBean(ProvisionAgreementBean.class, praRef);
            if (provisionAgreement == null) {
                throw new SdmxNoResultsException("Provision Agreement not found: " + praRef);
            }
        }
        return provisionAgreement;
    }

    /**
     * Processes the dataWhere element to get the DSD reference and retrieve the respective DSD.
     * It throws an exception if the DSD cannot be retrieved
     *
     * @param dataWhere
     * @param structureRetrievalManager
     * @param dataFlow
     * @return, cannot be NULL
     */
    private DataStructureBean getDataWhereDataStrucuture(DataParametersAndType dataWhere,
                                                         SdmxBeanRetrievalManager structureRetrievalManager, DataflowBean dataFlow) {

        DataStructureBean dataStructure = null;

        if (dataWhere.getDataStructureList() != null && dataWhere.getDataStructureList().size() > 0) {
            String dataStructureAgency = dataWhere.getDataStructureList().get(0).getRef().getAgencyID();
            String dataStructureId = dataWhere.getDataStructureList().get(0).getRef().getId();
            String dataStructureVersion = null;
            if (dataWhere.getDataStructureList().get(0).getRef().isSetVersion()) {
                dataStructureVersion = dataWhere.getDataStructureList().get(0).getRef().getVersion();
            }

            MaintainableRefBean dsdRef = new MaintainableRefBeanImpl(dataStructureAgency, dataStructureId, dataStructureVersion);
            dataStructure = structureRetrievalManager.getMaintainableBean(DataStructureBean.class, dsdRef);
            if (dataStructure == null) {
                throw new SdmxNoResultsException("DSD not found: " + dsdRef);
            }
        } else {
            MaintainableRefBean dsdRef = dataFlow.getDataStructureRef().getMaintainableReference();
            dataStructure = structureRetrievalManager.getMaintainableBean(DataStructureBean.class, dsdRef);
            if (dataStructure == null) {
                throw new RuntimeException("Data Structure not found: " + dataFlow.getDataStructureRef());
            }
        }
        return dataStructure;

    }

    /**
     * Processes the dataWhere element to get the dataflow reference and retrieve the respective dataflow.
     * It throws an exception if the dataflow retrieved is null
     *
     * @param dataWhere
     * @param structureRetrievalManager
     * @param dataFlow
     * @return, cannot be NULL
     */
    private DataflowBean getDataWhereDataFlow(DataParametersAndType dataWhere,
                                              SdmxBeanRetrievalManager structureRetrievalManager) {

        DataflowBean dataFlow = null;
        if (dataWhere.getDataflowList() != null && dataWhere.getDataflowList().size() > 0) {
            String dataFlowAgency = dataWhere.getDataflowList().get(0).getRef().getAgencyID();
            String dataFlowId = dataWhere.getDataflowList().get(0).getRef().getId();
            String dataFlowVersion = null; // null if not specified so as to get the latest
            if (dataWhere.getDataflowList().get(0).getRef().isSetVersion()) {
                dataFlowVersion = dataWhere.getDataflowList().get(0).getRef().getVersion();
            }

            MaintainableRefBean flowRef = new MaintainableRefBeanImpl(dataFlowAgency, dataFlowId, dataFlowVersion);
            dataFlow = structureRetrievalManager.getMaintainableBean(DataflowBean.class, flowRef);
            if (dataFlow == null) {
                throw new SdmxNoResultsException("Dataflow not found: " + flowRef);
            }
        } else {
            throw new IllegalArgumentException("Can not create DataQuery, Dataflow is required");
        }

        return dataFlow;

    }

    /**
     * Processes an XML data where element to return the data set identification and its operator
     *
     * @param dataWhere
     * @return, can be NULL, if not specified in the xml data query
     */
    private String[] getDataWhereDatasetId(DataParametersAndType dataWhere) {

        String datasetArray[] = null;
        String datasetId = null;
        String datasetIdOperator = null;
        if (dataWhere.getDataSetIDList() != null && dataWhere.getDataSetIDList().size() > 0) {
            datasetArray = new String[2];
            datasetId = dataWhere.getDataSetIDList().get(0).getStringValue();
            datasetArray[0] = datasetId;
            String operator = dataWhere.getDataSetIDList().get(0).getOperator();
            if (operator != null)
                datasetIdOperator = operator;
            else
                datasetIdOperator = "equals";
            datasetArray[1] = datasetIdOperator;
        }

        return datasetArray;
    }

    /**
     * Processes an XML data where element to return the data providers.
     *
     * @param dataWhere
     * @param structureRetrievalManager
     * @return an empty list if unspecified in the data query
     */
    private Set<DataProviderBean> getDataWhereDataProviders(DataParametersAndType dataWhere,
                                                            SdmxBeanRetrievalManager structureRetrievalManager) {
        Set<DataProviderBean> dataProviders = new HashSet<DataProviderBean>();
        if (dataWhere.getDataProviderList() != null && dataWhere.getDataProviderList().size() > 0) {
            for (DataProviderReferenceType dataProviderRefType : dataWhere.getDataProviderList()) {
                DataProviderBean dataProviderBean = processDataProviderType(dataProviderRefType, structureRetrievalManager);
                dataProviders.add(dataProviderBean);
            }
        }
        return dataProviders;
    }


    /**
     * Processes an XML return details to return the detail parameters.
     * DEFAULT set to FULL
     *
     * @param returnDetails
     * @return
     */
    private DATA_QUERY_DETAIL getReturnDetailsDetail(DataReturnDetailsType returnDetails) {

        if (returnDetails.isSetDetail())
            return DATA_QUERY_DETAIL.parseString(returnDetails.getDetail());
        else
            return DATA_QUERY_DETAIL.FULL;

    }

    /**
     * Processes an XML return details to return the first N observations
     *
     * @param returnDetails
     * @return, can be NULL
     */
    private Integer getReturnDetailsFirstNobs(DataReturnDetailsType returnDetails) {

        if (returnDetails.isSetFirstNObservations())
            return returnDetails.getFirstNObservations();
        else return null;
    }

    /**
     * Processes an XML return details to return the last N observations
     *
     * @param returnDetails
     * @return, can be NULL
     */
    private Integer getReturnDetailsLastNobs(DataReturnDetailsType returnDetails) {

        if (returnDetails.isSetLastNObservations())
            return returnDetails.getLastNObservations();
        else
            return null;
    }

    /**
     * Processes an XML return details to return the default limit value
     *
     * @param returnDetails
     * @return
     */
    private Integer getReturnDetailsDefaultLimit(
            DataReturnDetailsType returnDetails) {

        if (returnDetails.isSetDefaultLimit())
            return returnDetails.getDefaultLimit().intValue();
        else
            return null;
    }

    /**
     * Processes an XML return details to return the observation Action. Default to ACTIVE
     *
     * @param returnDetails
     * @return
     */
    private OBSERVATION_ACTION getReturnDetailsObsAction(DataReturnDetailsType returnDetails) {
        if (returnDetails.isSetObservationAction())
            return OBSERVATION_ACTION.parseString(returnDetails.getObservationAction().toString());
        else
            return OBSERVATION_ACTION.ACTIVE;
    }


    /**
     * Processes an XML return details to return the structure ref elements.
     *
     * @return a List with the structure ref details, first element is the dimensionAtObservation
     * and second element of the list the hasExplicitMeasures value if exists. Can be NULL if a structure Ref is not specified
     */
    private List getStructureRefDetails(DataReturnDetailsType returnDetails) {
        List structureRefDetails = null;
        String dimensionAtObservation = null;
        if (returnDetails.getStructureList() != null && returnDetails.getStructureList().size() > 0) {
            structureRefDetails = new ArrayList();
            List<DataStructureRequestType> structureList = returnDetails.getStructureList();
            DataStructureRequestType structure = structureList.get(0);
            structureRefDetails.add(structure.getDimensionAtObservation());
            if (structure.isSetExplicitMeasures())
                structureRefDetails.add(structure.getExplicitMeasures());
        }
        return structureRefDetails;
    }

    /**
     * Builds the complex data query groups, by processing the or types, dimension/primary measure/attribute/time dimension values.
     * It then calls the 'addGroupIfSelectionsExist' method in order to add the newly built-in ComplexDataQuerySelectionGroup. It returns
     * a set of Complex Data Query Selection Groups.
     *
     * @param dataWhere
     * @param structureRetrievalManager
     * @param dataProviders
     * @return
     */
    private Set<ComplexDataQuerySelectionGroup> buildComplexDataQueryGroups(DataParametersAndType dataWhere, SdmxBeanRetrievalManager structureRetrievalManager, Set<DataProviderBean> dataProviders) {

        Set<ComplexDataQuerySelectionGroup> complexDataQuerySelectionGroups = new HashSet<ComplexDataQuerySelectionGroup>();
        Set<ComplexDataQuerySelection> complexSelections = new HashSet<ComplexDataQuerySelection>();
        Set<ComplexComponentValue> primaryMeasureValues = new HashSet<ComplexComponentValue>();
        ORDERED_OPERATOR dateFromOperator = ORDERED_OPERATOR.EQUAL;
        ORDERED_OPERATOR dateToOperator = ORDERED_OPERATOR.EQUAL;
        SdmxDate dateFrom = null;
        SdmxDate dateTo = null;

        //primary measure
        if (dataWhere.getPrimaryMeasureValueList() != null && (dataWhere.getPrimaryMeasureValueList().size() > 0)) {
            PrimaryMeasureValueType primaryMeasure = dataWhere.getPrimaryMeasureValueList().get(0);
            Set<ComplexComponentValue> complexValues = getComplexComponentValues(primaryMeasure, SDMX_STRUCTURE_TYPE.PRIMARY_MEASURE);
            for (ComplexComponentValue complexValue : complexValues) {
                primaryMeasureValues.add(complexValue);
            }
        }
        //add all
        //time dimension
        if (dataWhere.getTimeDimensionValueList() != null && dataWhere.getTimeDimensionValueList().size() > 0) {
            TimeDimensionValueType timeValue = dataWhere.getTimeDimensionValueList().get(0);
            Set<ComplexComponentValue> complexValues = getComplexComponentValues(timeValue, SDMX_STRUCTURE_TYPE.TIME_DIMENSION);
            ComplexComponentValue[] complexTimeValues = complexValues.toArray(new ComplexComponentValue[0]);

            if (complexValues != null && complexValues.size() > 0) {
                if (complexTimeValues[0].getOrderedOperator().equals(ORDERED_OPERATOR.GREATER_THAN) ||
                        complexTimeValues[0].getOrderedOperator().equals(ORDERED_OPERATOR.GREATER_THAN_OR_EQUAL) ||
                        complexTimeValues[0].getOrderedOperator().equals(ORDERED_OPERATOR.EQUAL)) {
                    dateFrom = new SdmxDateImpl(complexTimeValues[0].getValue());
                    dateFromOperator = complexTimeValues[0].getOrderedOperator();
                    if (complexValues.size() == 2) {
                        dateTo = new SdmxDateImpl(complexTimeValues[1].getValue());
                        dateToOperator = complexTimeValues[1].getOrderedOperator();
                    }
                } else {
                    dateTo = new SdmxDateImpl(complexTimeValues[0].getValue());
                    dateToOperator = complexTimeValues[0].getOrderedOperator();
                    if (complexValues.size() == 2) {
                        dateFrom = new SdmxDateImpl(complexTimeValues[1].getValue());
                        dateFromOperator = complexTimeValues[1].getOrderedOperator();
                    }
                }
                if (complexValues.size() == 2) {
                    //interchange dates if not the correct order
                    if (dateFrom.isLater(dateTo)) {
                        SdmxDate tempDate = dateTo;
                        dateTo = dateFrom;
                        dateFrom = tempDate;
                        ORDERED_OPERATOR tempOperator = dateToOperator;
                        dateToOperator = dateFromOperator;
                        dateFromOperator = tempOperator;
                    }
                    //cases when same operator is used
                    if (dateToOperator.equals(dateFromOperator)) {
                        if (ORDERED_OPERATOR.GREATER_THAN.equals(dateToOperator) ||
                                ORDERED_OPERATOR.GREATER_THAN_OR_EQUAL.equals(dateToOperator)) {
                            //only the greatest date is considered
                            dateFrom = dateTo;
                            dateTo = null;
                        }
                        if (ORDERED_OPERATOR.LESS_THAN.equals(dateToOperator) ||
                                ORDERED_OPERATOR.LESS_THAN_OR_EQUAL.equals(dateToOperator)) {
                            //only the lowest date is considered
                            dateTo = dateFrom;
                            dateFrom = null;
                        }
                    }
                }
            }
        }
        //dimensions
        if (dataWhere.getDimensionValueList() != null && dataWhere.getDimensionValueList().size() > 0) {
            for (DimensionValueType dimValue : dataWhere.getDimensionValueList()) {
                ComplexComponentValue comValue = getComplexComponentValue(dimValue, SDMX_STRUCTURE_TYPE.DIMENSION);
                addComponentSelection(complexSelections, dimValue.getID(), comValue);
            }
        }
        //attributes
        if (dataWhere.getAttributeValueList() != null && dataWhere.getAttributeValueList().size() > 0) {
            for (AttributeValueType attrValue : dataWhere.getAttributeValueList()) {
                ComplexComponentValue comValue = getComplexComponentValue(attrValue, SDMX_STRUCTURE_TYPE.DATA_ATTRIBUTE);
                addComponentSelection(complexSelections, attrValue.getID(), comValue);
            }
        }
        //DataParametersOrType
        processParametersOrType(dataWhere.getOrList(), complexSelections, structureRetrievalManager, dataProviders);
        addGroupIfSelectionsExist(complexSelections, dateFrom, dateFromOperator, dateTo, dateToOperator, primaryMeasureValues, complexDataQuerySelectionGroups);
        return complexDataQuerySelectionGroups;
    }

    /**
     * Builds a data provider bean from DataProviderReferenceType from XML
     *
     * @param dataProvider
     * @param structureRetrievalManager
     * @return, can be NULL
     */
    private DataProviderBean processDataProviderType(DataProviderReferenceType dataProviderRef, SdmxBeanRetrievalManager structureRetrievalManager) {

        String agencyId = dataProviderRef.getRef().getAgencyID();
        String id = dataProviderRef.getRef().getMaintainableParentID();
        String version = dataProviderRef.getRef().getMaintainableParentVersion();

        MaintainableRefBean orgSchemeRef = new MaintainableRefBeanImpl(agencyId, id, version);
        DataProviderSchemeBean dataProviderScheme = structureRetrievalManager.getMaintainableBean(DataProviderSchemeBean.class, orgSchemeRef);
        for (DataProviderBean dp : dataProviderScheme.getItems()) {
            if (dp.getId().equals(dataProviderRef.getRef().getId())) {
                return dp;
            }
        }
        return null;

    }

    /**
     * Iterates through an unbound number of DataParametersAndType and call the respective method to process the AndType
     *
     * @param andTypeList
     * @param complexSelections
     */
    private void processParametersAndType(List<DataParametersAndType> andTypeList, Set<ComplexDataQuerySelection> complexSelections) {
        if (andTypeList != null) {
            for (DataParametersAndType andType : andTypeList) {
                processParametersAndType(andType, complexSelections);
            }
        }
    }

    /**
     * Processes the ParametersAndType, to be supported in the future
     * TODO analyse to implement in the future
     *
     * @param andType
     * @param complexSelections
     */
    private void processParametersAndType(DataParametersAndType andType, Set<ComplexDataQuerySelection> complexSelections) {
        //processParametersOrType(andType.getOrList(),complexSelections);
    }

    /**
     * Iterates through an unbound number of DataParametersOrType and call the respective method to process the OrType
     *
     * @param orTypeList
     * @param complexSelections
     * @param structureRetrievalManager
     * @param dataProviders
     */
    private void processParametersOrType(List<DataParametersOrType> orTypeList, Set<ComplexDataQuerySelection> complexSelections, SdmxBeanRetrievalManager structureRetrievalManager, Set<DataProviderBean> dataProviders) {
        if (orTypeList != null) {
            for (DataParametersOrType orType : orTypeList) {
                processParametersOrType(orType, complexSelections, structureRetrievalManager, dataProviders);
            }
        }
    }

    /**
     * Builds complex selections from DataParametersOr from XML
     *
     * @param orType
     * @param complexSelections
     */
    private void processParametersOrType(DataParametersOrType orType, Set<ComplexDataQuerySelection> complexSelections, SdmxBeanRetrievalManager structureRetrievalManager, Set<DataProviderBean> dataProviders) {

        //data provider
        if (orType.getDataProviderList() != null && orType.getDataProviderList().size() > 0) {
            for (DataProviderReferenceType dataProviderRefType : orType.getDataProviderList()) {
                DataProviderBean dataProviderBean = processDataProviderType(dataProviderRefType, structureRetrievalManager);
                dataProviders.add(dataProviderBean);
            }
        }
        //add a complex selection for dimensions
        if (orType.getDimensionValueList() != null && orType.getDimensionValueList().size() > 0) {
            for (DimensionValueType dimValue : orType.getDimensionValueList()) {
                ComplexComponentValue comValue = getComplexComponentValue(dimValue, SDMX_STRUCTURE_TYPE.DIMENSION);
                addComponentSelection(complexSelections, dimValue.getID(), comValue);
            }
        }
        //add complex selections for attributes
        if (orType.getAttributeValueList() != null && orType.getAttributeValueList().size() > 0) {
            for (AttributeValueType attrValue : orType.getAttributeValueList()) {
                ComplexComponentValue comValue = getComplexComponentValue(attrValue, SDMX_STRUCTURE_TYPE.DATA_ATTRIBUTE);
                addComponentSelection(complexSelections, attrValue.getID(), comValue);
            }
        }
        //add complex selections for measure
        if (orType.getPrimaryMeasureValueList() != null && orType.getPrimaryMeasureValueList().size() > 0) {
            for (PrimaryMeasureValueType measureValue : orType.getPrimaryMeasureValueList()) {
                Set<ComplexComponentValue> comValues = getComplexComponentValues(measureValue, SDMX_STRUCTURE_TYPE.PRIMARY_MEASURE);
                addComponentSelection(complexSelections, measureValue.getID(), comValues);
            }
        }
        //add complex selections for time dimension
        if (orType.getTimeDimensionValueList() != null && orType.getTimeDimensionValueList().size() > 0) {
            for (TimeDimensionValueType timeValue : orType.getTimeDimensionValueList()) {
                Set<ComplexComponentValue> comValues = getComplexComponentValues(timeValue, SDMX_STRUCTURE_TYPE.TIME_DIMENSION);
                addComponentSelection(complexSelections, timeValue.getID(), comValues);
            }
        }
        //TODO analyse to support in the future
        //processParametersAndType(orType.getAndList(), complexSelections);
    }

    /**
     * This method parses the value types of the incoming component value type, and returns the complex component value to construct in the selection
     *
     * @param compValue, either a dimension, time dimension, attribute or primary measure
     * @param compType
     * @return
     */
    private ComplexComponentValue getComplexComponentValue(DataStructureComponentValueQueryType compValue, SDMX_STRUCTURE_TYPE compType) {
        ComplexComponentValue comValue = null;

        //Numeric Values
        if (compValue.getNumericValueList() != null) {
            for (NumericValueType numerValue : compValue.getNumericValueList()) {
                ORDERED_OPERATOR orderedOperator = ORDERED_OPERATOR.parseString(numerValue.getOperator());
                comValue = new ComplexComponentValueImpl(numerValue.getStringValue(), orderedOperator, compType);
            }
        }
        //Time Value
        if (compValue.getTimeValueList() != null) {
            for (TimePeriodValueType timeValue : compValue.getTimeValueList()) {
                ORDERED_OPERATOR orderedOperator = ORDERED_OPERATOR.parseString(timeValue.getOperator());
                comValue = new ComplexComponentValueImpl(timeValue.getStringValue(), orderedOperator, compType);
            }
        }
        //Text Value applicable only for attribute and primary measure
        if (compType.equals(SDMX_STRUCTURE_TYPE.PRIMARY_MEASURE) || compType.equals(SDMX_STRUCTURE_TYPE.DATA_ATTRIBUTE)) {
            //It's a generic type but the type will always be one
            if (compValue.getTextValueList() != null) {
                for (QueryTextType textValue : compValue.getTextValueList()) {
                    TEXT_SEARCH textOperator = TEXT_SEARCH.parseString(textValue.getOperator());
                    comValue = new ComplexComponentValueImpl(textValue.getStringValue(), textOperator, compType);
                }
            }
        }
        //Value
        if (compValue.getValue() != null) {
            ORDERED_OPERATOR orderedOperator = ORDERED_OPERATOR.parseString(compValue.getValue().getOperator().toString());
            comValue = new ComplexComponentValueImpl(compValue.getValue().getStringValue(), orderedOperator, compType);
        }
        return comValue;

    }

    /**
     * This method parses the value types of the incoming component value type, and returns the complex component value to construct in the selection
     *
     * @param compValue, either a dimension, time dimension, attribute or primary measure
     * @param comValues
     * @return the Set with the ComplexCompoentValue objects
     */
    private Set<ComplexComponentValue> getComplexComponentValues(DataStructureComponentValueQueryType compValue, SDMX_STRUCTURE_TYPE compType) {
        Set<ComplexComponentValue> comValues = new HashSet<ComplexComponentValue>();
        ComplexComponentValue comValue = null;

        //Numeric Values
        if (compValue.getNumericValueList() != null) {
            for (NumericValueType numerValue : compValue.getNumericValueList()) {
                ORDERED_OPERATOR orderedOperator = ORDERED_OPERATOR.parseString(numerValue.getOperator());
                comValue = new ComplexComponentValueImpl(numerValue.getStringValue(), orderedOperator, compType);
                comValues.add(comValue);
            }
        }
        //Time Value
        if (compValue.getTimeValueList() != null) {
            for (TimePeriodValueType timeValue : compValue.getTimeValueList()) {
                ORDERED_OPERATOR orderedOperator = ORDERED_OPERATOR.parseString(timeValue.getOperator());
                comValue = new ComplexComponentValueImpl(timeValue.getStringValue(), orderedOperator, compType);
                comValues.add(comValue);
            }
        }
        //Text Value applicable only for attribute and primary measure
        if (compType.equals(SDMX_STRUCTURE_TYPE.PRIMARY_MEASURE) || compType.equals(SDMX_STRUCTURE_TYPE.DATA_ATTRIBUTE)) {
            //It's a generic type but the type will always be one
            if (compValue.getTextValueList() != null) {
                for (QueryTextType textValue : compValue.getTextValueList()) {
                    TEXT_SEARCH textOperator = TEXT_SEARCH.parseString(textValue.getOperator());
                    comValue = new ComplexComponentValueImpl(textValue.getStringValue(), textOperator, compType);
                    comValues.add(comValue);
                }
            }
        }
        //Value
        if (compValue.getValue() != null) {
            ORDERED_OPERATOR orderedOperator = ORDERED_OPERATOR.parseString(compValue.getValue().getOperator().toString());
            comValue = new ComplexComponentValueImpl(compValue.getValue().getStringValue(), orderedOperator, compType);
            comValues.add(comValue);
        }
        return comValues;

    }

    /**
     * Adds a selection value, either into an existing ComplexDataQuerySelection with the given concept, or a new DataQuerySelection if none exist with the given concept.
     *
     * @param selections
     * @param conceptId
     * @param value
     */
    private void addComponentSelection(Set<ComplexDataQuerySelection> complexSelections, String conceptId, ComplexComponentValue value) {

        for (ComplexDataQuerySelection selection : complexSelections) {
            if (selection.getComponentId().equals(conceptId)) {
                ((ComplexDataQuerySelectionImpl) selection).addValue(value);
                return;
            }
        }
        ComplexDataQuerySelection newSelection = new ComplexDataQuerySelectionImpl(conceptId, value);
        complexSelections.add(newSelection);
    }

    /**
     * Adds a selection value, either into an existing ComplexDataQuerySelection with the given concept, or a new DataQuerySelection if none exist with the given concept.
     *
     * @param selections
     * @param conceptId
     * @param values
     */
    private void addComponentSelection(Set<ComplexDataQuerySelection> complexSelections, String conceptId, Set<ComplexComponentValue> values) {

        for (ComplexDataQuerySelection selection : complexSelections) {
            if (selection.getComponentId().equals(conceptId)) {
                for (ComplexComponentValue value : values) {
                    ((ComplexDataQuerySelectionImpl) selection).addValue(value);
                }
                return;
            }
        }
        ComplexDataQuerySelection newSelection = new ComplexDataQuerySelectionImpl(conceptId, values);
        complexSelections.add(newSelection);
    }

    /**
     * Id adds a selection group only if it has at least one of the following date from, date to, valid collection of primary measure values, valid collection of complex selections. <br>
     * A collection is considered valid when it is not null and contains at least one element.
     *
     * @param complexSelections
     * @param dateFrom
     * @param dateFromOperator
     * @param dateTo
     * @param dateToOperator
     * @param primaryMeasureValues
     * @param complexDataQuerySelectionGroups
     */
    private void addGroupIfSelectionsExist(Set<ComplexDataQuerySelection> complexSelections, SdmxDate dateFrom, ORDERED_OPERATOR dateFromOperator, SdmxDate dateTo, ORDERED_OPERATOR dateToOperator, Set<ComplexComponentValue> primaryMeasureValues, Set<ComplexDataQuerySelectionGroup> complexDataQuerySelectionGroups) {
        if (ObjectUtil.validCollection(complexSelections) || dateFrom != null || dateTo != null || ObjectUtil.validCollection(primaryMeasureValues)) {
            complexDataQuerySelectionGroups.add(new ComplexDataQuerySelectionGroupImpl(complexSelections, dateFrom, dateFromOperator, dateTo, dateToOperator, primaryMeasureValues));
        }
    }

    /**
     * Builds a time range from time range value from XML
     *
     * @param timeRangeValueType
     * @return
     */
    private TimeRange buildTimeRange(TimeRangeValueType timeRangeValueType) {
        boolean range = false;
        SdmxDate startDate = null;
        SdmxDate endDate = null;
        boolean endInclusive = false;
        boolean startInclusive = false;

        if (timeRangeValueType.isSetAfterPeriod()) {
            TimePeriodRangeType afterPeriod = timeRangeValueType.getAfterPeriod();
            startDate = new SdmxDateImpl(afterPeriod.getStringValue());
            startInclusive = afterPeriod.getIsInclusive();
        } else if (timeRangeValueType.isSetBeforePeriod()) {
            TimePeriodRangeType beforePeriod = timeRangeValueType.getBeforePeriod();
            endDate = new SdmxDateImpl(beforePeriod.getStringValue());
            endInclusive = beforePeriod.getIsInclusive();
        } else { //case that range is set
            range = true;
            TimePeriodRangeType startPeriod = timeRangeValueType.getStartPeriod();
            startDate = new SdmxDateImpl(startPeriod.getStringValue());
            startInclusive = startPeriod.getIsInclusive();

            TimePeriodRangeType endPeriod = timeRangeValueType.getEndPeriod();
            endDate = new SdmxDateImpl(endPeriod.getStringValue());
            endInclusive = endPeriod.getIsInclusive();
        }
        return new TimeRangeImpl(range, startDate, endDate, startInclusive, endInclusive);
    }
}
