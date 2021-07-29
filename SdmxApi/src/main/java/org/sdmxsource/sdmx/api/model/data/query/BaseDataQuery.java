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
package org.sdmxsource.sdmx.api.model.data.query;

import org.sdmxsource.sdmx.api.constants.DATA_QUERY_DETAIL;
import org.sdmxsource.sdmx.api.model.beans.base.DataProviderBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;

import java.io.Serializable;
import java.util.Set;

/**
 * Base interface for both standard and complex data queries
 */
public interface BaseDataQuery extends Serializable {
    /**
     * Returns the id of the dimension to be attached at the observation level,
     * if not specified the returned data will be time series.
     *
     * @return the id of the dimension to be attached at the observation level, if not specified the returned data will be time series.
     */
    String dimensionAtObservation();

    /**
     * Returns true if this query has one or more selection groups on it, false means the query is a query for all.
     *
     * @return boolean
     */
    boolean hasSelections();

    /**
     * Returns the Key Family (Data Structure Definition) that this query is returning data for.
     *
     * @return data structure
     */
    DataStructureBean getDataStructure();

    /**
     * Returns the Dataflow that the query is returning data for.
     *
     * @return dataflow dataflow
     */
    DataflowBean getDataflow();

    /**
     * Returns the data provider(s) that the query is for, an empty list represents ALL.
     *
     * @return data provider
     */
    Set<DataProviderBean> getDataProvider();


    /**
     * Returns the last 'n' observations, for each series key, to return as a result of a data query.
     *
     * @return last 'n' observations or null if unspecified
     */
    Integer getLastNObservations();


    /**
     * Returns the first 'n' observations, for each series key,  to return as a result of a data query.
     *
     * @return first 'n' observations or null if unspecified
     */
    Integer getFirstNObservations();

    /**
     * Returns the detail of the query.  The detail specifies whether to return just the series keys, just the data or everything.
     * <p>
     * Defaults to FULL (everything)
     *
     * @return data query detail
     */
    DATA_QUERY_DETAIL getDataQueryDetail();
}
