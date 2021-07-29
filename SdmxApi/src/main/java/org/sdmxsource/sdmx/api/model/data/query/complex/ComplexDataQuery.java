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
package org.sdmxsource.sdmx.api.model.data.query.complex;

import org.sdmxsource.sdmx.api.constants.OBSERVATION_ACTION;
import org.sdmxsource.sdmx.api.constants.TEXT_SEARCH;
import org.sdmxsource.sdmx.api.model.beans.reference.complex.TimeRange;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.data.query.BaseDataQuery;

import java.util.List;


/**
 * It is a representation of a SOAP DataQuery 2.1
 *
 * @author fch
 */
public interface ComplexDataQuery extends BaseDataQuery {


    /**
     * Returns the suggested maximum response size or null if unspecified
     *
     * @return default limit
     */
    Integer getDefaultLimit();

    /**
     * Returns the type of observations to be returned.<br>
     * Defaults to ACTIVE
     *
     * @return observation action
     */
    OBSERVATION_ACTION getObservationAction();

    /**
     * Returns true or false depending on the existence of explicit measures
     * Defaults to false
     *
     * @return boolean
     */
    boolean hasExplicitMeasures();

    /**
     * Returns the id of the dataset from which data will be returned or null if unspecified
     *
     * @return dataset id
     */
    String getDatasetId();

    /**
     * The id of the dataset requested should satisfy the condition implied by the operator this method returns. <br>
     * For instance if the operator is starts_with then data from a dataset with id that starts with the getDatasetId() result
     * should be returned.
     * Defaults to EQUAL
     *
     * @return the dataset id operator
     */
    TEXT_SEARCH getDatasetIdOperator();


    /**
     * This method accomplishes the following criteria :match data based on when they were last updated <br>
     * The date points to the start date or the range that the queried date must occur within and/ or to the end period of the range <br>
     * It returns null if unspecified.
     *
     * @return last updated date time range
     */
    List<TimeRange> getLastUpdatedDateTimeRange();


    /**
     * Returns a list of selection groups. The list is empty if no selection groups have been added.
     *
     * @return selection groups
     */
    List<ComplexDataQuerySelectionGroup> getSelectionGroups();

    /**
     * Returns the Provision Agreement that the query is returning data for or null if unspecified
     *
     * @return provision agreement
     */
    ProvisionAgreementBean getProvisionAgreement();


}
