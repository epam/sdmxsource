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
package org.sdmxsource.sdmx.api.model.metadata;

import org.sdmxsource.sdmx.api.model.base.SdmxDate;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;

import java.util.List;


/**
 * The interface Metadata set bean.
 */
public interface MetadataSetBean extends SDMXBean {

    /**
     * Returns a list of metadata sets, built from this metadata set and metadata reports.  Each metadata set has one metadata report in it.
     *
     * @return a metadataset for each metadata report contained in the metadata set
     */
    List<MetadataSetBean> splitReports();

    /**
     * Returns an identification for the metadata set
     *
     * @return set id
     */
    String getSetId();

    /**
     * Returns a list of names for this component - will return an empty list if no Names exist.
     *
     * <b>NOTE: </b>The list is a copy so modifying the returned list will not
     * be reflected in the IdentifiableBean instance
     *
     * @return first locale value it finds or null if there are none
     */
    List<TextTypeWrapper> getNames();

    /**
     * Mandatory returns a reference to the metadata structure that defines the structure of this metadata set
     *
     * @return the msd reference
     */
    CrossReferenceBean getMsdReference();

    /**
     * Optional
     *
     * @return the inclusive start time of the data reported in the metadata set
     */
    SdmxDate getReportingBeginDate();

    /**
     * Optional
     *
     * @return the inclusive end time of the data reported in the metadata set
     */
    SdmxDate getReportingEndDate();

    /**
     * Optional
     *
     * @return the inclusive start time of the validity of the info in the metadata set
     */
    SdmxDate getValidFromDate();

    /**
     * Optional
     *
     * @return the inclusive end time of the validity of the info in the metadata set
     */
    SdmxDate getValidToDate();

    /**
     * Optional
     *
     * @return the four digit ISO 8601 year of publication
     */
    SdmxDate getPublicationYear();

    /**
     * Optional
     *
     * @return the publication period
     */
    Object getPublicationPeriod();

    /**
     * Optional
     *
     * @return reference to the DataProvider
     */
    CrossReferenceBean getDataProviderReference();


    /**
     * Gets reports.
     *
     * @return the reports
     */
    List<MetadataReportBean> getReports();

}
