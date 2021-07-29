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
package org.sdmxsource.sdmx.api.model.query;

import org.sdmxsource.sdmx.api.constants.DATA_QUERY_DETAIL;
import org.sdmxsource.sdmx.api.model.base.SdmxDate;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * The interface Rest data query.
 */
public interface RESTDataQuery extends Serializable {

    /**
     * Returns a String representation of this query, in SDMX REST format starting from Data/.
     * <p>
     * Example Data/ACY,FLOW,1.0/M.Q+P....L/ALL?detail=seriesKeysOnly
     *
     * @return rest query
     */
    String getRestQuery();

    /**
     * If true then the response message will contain previous versions of the data as they were disseminated in the past
     * past ("history" or "timeline" functionality)
     *
     * @return boolean
     */
    boolean includeHistory();

    /**
     * Returns the dataflow reference
     *
     * @return flow ref
     */
    StructureReferenceBean getFlowRef();

    /**
     * Returns the data provider reference, or null if ALL
     *
     * @return the provider ref
     */
    StructureReferenceBean getProviderRef();

    /**
     * Returns the start date to get the data from, or null if undefined
     *
     * @return start period
     */
    SdmxDate getStartPeriod();

    /**
     * Returns the end date to get the data from, or null if undefined
     *
     * @return end period
     */
    SdmxDate getEndPeriod();

    /**
     * Returns the updated after date to get the data from, or null if undefined
     *
     * @return updated after
     */
    SdmxDate getUpdatedAfter();

    /**
     * Returns the first 'n' observations, for each series key, to return as a result of a data query.
     *
     * @return last 'n' observations or null if unspecified
     */
    Integer getlastNObsertations();


    /**
     * Returns the last 'n' observations, for each series key,  to return as a result of a data query
     *
     * @return first 'n' observations or null if unspecified
     */
    Integer getFirstNObservations();

    /**
     * Returns the level of detail for the returne data, or null if undefined
     *
     * @return query detail
     */
    DATA_QUERY_DETAIL getQueryDetail();

    /**
     * Returns the dimension to , or null if undefined
     *
     * @return dimension at observation
     */
    String getDimensionAtObservation();

    /**
     * Returns the list of dimension codeid filters, in the same order as the dimensions are defined by the DataStructure
     *
     * @return query list
     */
    List<Set<String>> getQueryList();

}
