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
package org.sdmxsource.sdmx.api.model.header;

import org.sdmxsource.sdmx.api.constants.DATASET_ACTION;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;

import java.util.Date;


/**
 * Contains information specifically related to a single dataset
 */
public interface DatasetHeaderBean {

    /**
     * Creates a new DatasetHeaderBean, copying over all the attributes of this header, but replacing the DatasetStructureReferenceBean with the one passed in
     *
     * @param datasetStructureReferenceBean the dataset structure reference bean
     * @return dataset header bean
     */
    DatasetHeaderBean modifyDataStructureReference(DatasetStructureReferenceBean datasetStructureReferenceBean);

    /**
     * Returns the action for this dataset, defaults to INFORMATION
     *
     * @return action
     */
    DATASET_ACTION getAction();

    /**
     * Returns true if the DataReaderEngine is reading time series data (observations iterate over time).  If false time will be at the series level
     *
     * @return boolean
     */
    boolean isTimeSeries();


    /**
     * Returns a reference to the data provider for this data.
     *
     * @return returns null if there is no reference
     */
    MaintainableRefBean getDataProviderReference();

    /**
     * Returns a DatasetStructureReferenceBean containing information about what structure is used to descirbe the structure of this dataset
     *
     * @return returns null if there is no reference
     */
    DatasetStructureReferenceBean getDataStructureReference();


    /**
     * Returns an id for the dataset
     *
     * @return returns null if no id was given
     */
    String getDatasetId();

    /**
     * Returns the reporting begin date for this dataset
     *
     * @return returns null if there is no reporting begin date defined
     */
    Date getReportingBeginDate();

    /**
     * Returns the reporting end date for this dataset
     *
     * @return returns null if there is no reporting end date defined
     */
    Date getReportingEndDate();

    /**
     * Returns the reporting end date for this dataset
     *
     * @return returns null if there is no valid from date defined
     */
    Date getValidFrom();

    /**
     * Returns the reporting end date for this dataset
     *
     * @return returns null if there is no valid to defined
     */
    Date getValidTo();

    /**
     * Returns the reporting end date for this dataset
     *
     * @return returns -1 if there is no publication year given defined
     */
    int getPublicationYear();

    /**
     * Returns the reporting end date for this dataset
     *
     * @return returns null if there is no publication period defined
     */
    String getPublicationPeriod();
}
