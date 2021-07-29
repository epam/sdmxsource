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
package org.sdmxsource.sdmx.structureparser.builder.query.v2;

import org.sdmx.resources.sdmxml.schemas.v20.query.*;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.exception.SdmxNoResultsException;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.base.SdmxDate;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.sdmx.api.model.data.query.DataQuery;
import org.sdmxsource.sdmx.api.model.data.query.DataQuerySelection;
import org.sdmxsource.sdmx.api.model.data.query.DataQuerySelectionGroup;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.SdmxDateImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.data.query.DataQueryDimensionSelectionImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.data.query.DataQueryImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.data.query.DataQuerySelectionGroupImpl;
import org.sdmxsource.sdmx.util.beans.reference.MaintainableRefBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The type Data query builder v 2.
 */
public class DataQueryBuilderV2 {


    /**
     * Build data query list.
     *
     * @param queryType                 the query type
     * @param structureRetrievalManager the structure retrieval manager
     * @return the list
     */
    public List<DataQuery> buildDataQuery(QueryType queryType, SdmxBeanRetrievalManager structureRetrievalManager) {
        List<DataQuery> returnList = new ArrayList<DataQuery>();

        for (DataWhereType dataWhere : queryType.getDataWhereList()) {
            BigInteger limit = null;
            if (queryType.getDefaultLimit() != null) {
                limit = queryType.getDefaultLimit();
            }
            returnList.add(new DataQueryProcessor(limit).buildDataQuery(dataWhere, structureRetrievalManager));
        }
        return returnList;
    }

    /**
     * Build data query data query.
     *
     * @param dataWhereType             the data where type
     * @param structureRetrievalManager the structure retrieval manager
     * @return the data query
     */
    public DataQuery buildDataQuery(DataWhereType dataWhereType, SdmxBeanRetrievalManager structureRetrievalManager) {
        return new DataQueryProcessor(null).buildDataQuery(dataWhereType, structureRetrievalManager);
    }

    private class DataQueryProcessor {
        private Set<DataQuerySelectionGroup> dataQuerySelectionGroups = new HashSet<DataQuerySelectionGroup>();

        //Get this information off the query to use to build the DataQuery
        private String keyFamilyId;
        private String dataflowId;
        private String agencyId;
        private String version;
        private Integer maxObs;


        /**
         * Instantiates a new Data query processor.
         *
         * @param maxObs the max obs
         */
        public DataQueryProcessor(BigInteger maxObs) {
            if (maxObs != null) {
                this.maxObs = maxObs.intValue();
            }
        }

        /**
         * Builds a data
         */
        private DataQuery buildDataQuery(DataWhereType dataWhereType, SdmxBeanRetrievalManager structureRetrievalManager) {
            if (structureRetrievalManager == null) {
                throw new RuntimeException("DataQueryBuilder expectes a SdmxBeanRetrievalManager");
            }


            processDataWhere(dataWhereType);
            processAnd(dataWhereType.getAnd());

            MaintainableRefBean flowRef = new MaintainableRefBeanImpl(agencyId, dataflowId, version);
            DataflowBean dataflow = structureRetrievalManager.getMaintainableBean(DataflowBean.class, flowRef);
            if (dataflow == null) {
                throw new SdmxNoResultsException("Dataflow not found: " + flowRef);
            }

            MaintainableRefBean dsdRef = dataflow.getDataStructureRef().getMaintainableReference();
            DataStructureBean dataStructureBean = structureRetrievalManager.getMaintainableBean(DataStructureBean.class, dsdRef);
            if (dataStructureBean == null) {
                throw new RuntimeException("Data Structure not found: " + dataflow.getDataStructureRef());
            }

            //FUNC Data Provider
            return new DataQueryImpl(dataStructureBean, null, null, maxObs, true, null, dataflow, null, dataQuerySelectionGroups);

        }

        private void processDataWhere(DataWhereType dwType) {
            Set<DataQuerySelection> selections = new HashSet<DataQuerySelection>();
            SdmxDate dateFrom = null;
            SdmxDate dateTo = null;

            dataflowId = dwType.getDataflow();
            keyFamilyId = dwType.getKeyFamily();
            version = dwType.getVersion();

            if (dwType.getTime() != null) {
                Time t = new Time(dwType.getTime());
                dateFrom = t.dateFrom;
                dateTo = t.dateTo;
            }
            if (dwType.getDimension() != null) {
                DataQuerySelection newSelection = new DataQueryDimensionSelectionImpl(dwType.getDimension().getId(), dwType.getDimension().getStringValue());
                selections.add(newSelection);
            }
            if (dwType.getAttribute() != null) {
                DataQuerySelection newSelection = new DataQueryDimensionSelectionImpl(dwType.getAttribute().getId(), dwType.getAttribute().getStringValue());
                selections.add(newSelection);
            }
            processOr(dwType.getOr(), selections);
            addGroupIfSelectionsExist(selections, dateFrom, dateTo);
        }

        private void addGroupIfSelectionsExist(Set<DataQuerySelection> selections, SdmxDate dateFrom, SdmxDate dateTo) {
            if (ObjectUtil.validCollection(selections) || dateFrom != null || dateTo != null) {
                dataQuerySelectionGroups.add(new DataQuerySelectionGroupImpl(selections, dateFrom, dateTo));
            }
        }


        private void processAnd(AndType andType) {
            Set<DataQuerySelection> selections = new HashSet<DataQuerySelection>();
            SdmxDate dateFrom = null;
            SdmxDate dateTo = null;

            if (andType != null) {
                if (ObjectUtil.validCollection(andType.getAndList())) {
                    throw new SdmxNotImplementedException("DataWhere.AND followed by AND is not supported");
                }
                if (andType.getDimensionList() != null) {
                    for (DimensionType currentDimension : andType.getDimensionList()) {
                        for (DataQuerySelection selection : selections) {
                            //CAN NOT AND TWO DIMENSIONS WITH DIFFERNT VALUES
                            if (selection.getComponentId().equals(currentDimension.getId())) {
                                throw new SdmxSemmanticException(ExceptionCode.QUERY_SELECTION_ILLEGAL_AND_CODES_IN_SAME_DIMENSION, currentDimension.getId());
                            }
                        }
                        addComponentSelection(selections, currentDimension.getId(), currentDimension.getStringValue());
                    }
                }
                if (andType.getAttributeList() != null) {
                    for (AttributeType currentAttribute : andType.getAttributeList()) {
                        for (DataQuerySelection selection : selections) {
                            //CAN NOT AND TWO ATTRIBUTES WITH DIFFERNT VALUES
                            if (selection.getComponentId().equals(currentAttribute.getId())) {
                                throw new SdmxSemmanticException(ExceptionCode.QUERY_SELECTION_ILLEGAL_AND_CODES_IN_SAME_DIMENSION, currentAttribute.getId());
                            }
                        }
                        addComponentSelection(selections, currentAttribute.getId(), currentAttribute.getStringValue());
                    }
                }
                if (andType.getTimeList() != null) {
                    if (andType.getTimeList().size() > 1) {
                        throw new SdmxNotImplementedException("Multiple Time selection on DataWhere.And not supported");
                    }
                    for (TimeType time : andType.getTimeList()) {
                        Time t = new Time(time);
                        dateFrom = t.dateFrom;
                        dateTo = t.dateTo;
                    }
                }
                if (ObjectUtil.validCollection(andType.getAgencyIDList())) {
                    if (andType.getAgencyIDList().size() > 1) {
                        throw new SdmxSemmanticException(ExceptionCode.QUERY_SELECTION_ILLEGAL_AND_AGENCY_ID);
                    }
                    if (this.agencyId != null && !andType.getAgencyIDList().get(0).equals(agencyId)) {
                        throw new SdmxSemmanticException("Multiple agency Ids not supported on DataWhere - got '" + agencyId + "' and '" + andType.getAgencyIDList().get(0) + "'");
                    }
                    agencyId = andType.getAgencyIDList().get(0);
                }
                if (ObjectUtil.validCollection(andType.getKeyFamilyList())) {
                    if (andType.getKeyFamilyList().size() > 1) {
                        throw new SdmxSemmanticException(ExceptionCode.QUERY_SELECTION_ILLEGAL_AND_KEYFAMILY);
                    }
                    if (this.keyFamilyId != null && !andType.getKeyFamilyList().get(0).equals(keyFamilyId)) {
                        throw new SdmxSemmanticException("Multiple Data Structure Ids not supported on DataWhere - got '" + keyFamilyId + "' and '" + andType.getKeyFamilyList().get(0) + "'");
                    }
                    keyFamilyId = andType.getKeyFamilyList().get(0);
                }
                if (ObjectUtil.validCollection(andType.getDataflowList())) {
                    if (andType.getDataflowList().size() > 1) {
                        throw new SdmxNotImplementedException("Multiple Dataflow Ids not supported in an AND operation");
                    }
                    if (this.dataflowId != null && !andType.getDataflowList().get(0).equals(dataflowId)) {
                        throw new SdmxSemmanticException("Multiple Dataflow Ids not supported on DataWhere - got '" + dataflowId + "' and '" + andType.getDataflowList().get(0) + "'");
                    }
                    dataflowId = andType.getDataflowList().get(0);
                }
                processOr(andType.getOrList(), selections);
                addGroupIfSelectionsExist(selections, dateFrom, dateTo);
            }
        }

        private void processAnd(List<AndType> andTypes) {
            if (andTypes != null) {
                for (AndType andType : andTypes) {
                    processAnd(andType);
                }
            }
        }

        private void processOr(List<OrType> orTypes, Set<DataQuerySelection> selections) {
            if (orTypes != null) {
                for (OrType orType : orTypes) {
                    processOr(orType, selections);
                }
            }
        }

        private void processOr(OrType orType, Set<DataQuerySelection> selections) {
            if (orType != null) {
                if (orType.getDimensionList() != null) {
                    for (DimensionType currentDimension : orType.getDimensionList()) {
                        addComponentSelection(selections, currentDimension.getId(), currentDimension.getStringValue());
                    }
                }
                if (orType.getAttributeList() != null) {
                    for (AttributeType currentAttribute : orType.getAttributeList()) {
                        addComponentSelection(selections, currentAttribute.getId(), currentAttribute.getStringValue());
                    }
                }
                if (ObjectUtil.validCollection(orType.getKeyFamilyList())) {
                    throw new SdmxNotImplementedException("Key Family not supported in the DataQuery.OR, please put in DataQuery.AND");
                }
                if (ObjectUtil.validCollection(orType.getDataflowList())) {
                    throw new SdmxNotImplementedException("Dataflow not supported in the DataQuery.OR, please put in DataQuery.AND");
                }
                processAnd(orType.getAndList());
            }
        }

        /**
         * Adds a selection value, either into an existing DataQuerySelection with the given concept, or a new DataQuerySelection if none exist with the given concept.
         *
         * @param selections
         * @param conceptId
         * @param value
         */
        private void addComponentSelection(Set<DataQuerySelection> selections, String conceptId, String value) {
            for (DataQuerySelection selection : selections) {
                if (selection.getComponentId().equals(conceptId)) {
                    ((DataQueryDimensionSelectionImpl) selection).addValue(value);
                    return;
                }
            }
            DataQuerySelection newSelection = new DataQueryDimensionSelectionImpl(conceptId, value);
            selections.add(newSelection);
        }
    }

    private class Time {
        /**
         * The Date from.
         */
        SdmxDate dateFrom = null;
        /**
         * The Date to.
         */
        SdmxDate dateTo = null;

        private Time(TimeType timeType) {
            if (timeType.getTime() != null) {
                if (dateFrom != null) {
                    throw new SdmxSemmanticException(ExceptionCode.QUERY_SELECTION_MULTIPLE_DATE_FROM);
                }
                if (dateTo != null) {
                    throw new SdmxSemmanticException(ExceptionCode.QUERY_SELECTION_MULTIPLE_DATE_TO);
                }
                dateFrom = parseDate(timeType.getTime());
                dateTo = parseDate(timeType.getTime());
            }
            if (timeType.getStartTime() != null) {
                if (dateFrom != null) {
                    throw new SdmxSemmanticException(ExceptionCode.QUERY_SELECTION_MULTIPLE_DATE_FROM);
                }
                dateFrom = parseDate(timeType.getStartTime());
            }
            if (timeType.getEndTime() != null) {
                if (dateTo != null) {
                    throw new SdmxSemmanticException(ExceptionCode.QUERY_SELECTION_MULTIPLE_DATE_TO);
                }
                dateTo = parseDate(timeType.getEndTime());
            }
        }

        private SdmxDate parseDate(Object obj) {
            return new SdmxDateImpl(obj.toString());
        }
    }
}

