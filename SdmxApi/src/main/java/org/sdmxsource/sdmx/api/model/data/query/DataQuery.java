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

import org.sdmxsource.sdmx.api.model.base.SdmxDate;

import java.util.List;


/**
 * A DataQuery is a Schema independent representation of a DataWhere/REST query.
 *
 * @author Matt Nelson
 */
public interface DataQuery extends BaseDataQuery {


    /**
     * Returns a copy of all the selection groups.
     *
     * @return selection groups
     */
    List<DataQuerySelectionGroup> getSelectionGroups();


    /**
     * If true then the response message will contain previous versions of the data as they were disseminated in the past
     * past ("history" or "timeline" functionality)
     *
     * @return true then the response message will contain previous versions of the data as they were disseminated in the past past ("history" or "timeline" functionality)
     */
    boolean includeHistory();

    /**
     * The last updated date is a filter on the data meaning 'only return data that was updated after this time'.
     * <p>
     * If this attribute is used, the returned message should only
     * include the latest version of what has changed in the database since that point in time (updates and revisions).
     * <p>
     * This should include:
     * <ul>
     *  <li>Observations that have been added since the last time the query was performed (INSERT)</li>
     *  <li>Observations that have been revised since the last time the query was performed (UPDATE)</li>
     *  <li>Observations that have been revised since the last time the query was performed (UPDATE)</li>
     *  <li>Observations that have been deleted since the last time the query was performed (DELETE)</li>
     * </ul>
     * If no offset is specified, default to local.
     * <p>
     * Returns null if unspecified
     *
     * @return last updated date
     */
    SdmxDate getLastUpdatedDate();

    /**
     * Returns the dataset id to query, this can be null
     *
     * @return dataset id
     */
    String getDatasetId();
}
