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
package org.sdmxsource.sdmx.api.model.superbeans.metadata;

import org.sdmxsource.sdmx.api.model.base.SdmxDate;
import org.sdmxsource.sdmx.api.model.beans.base.DataProviderBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataStructureDefinitionBean;
import org.sdmxsource.sdmx.api.model.metadata.MetadataSetBean;
import org.sdmxsource.sdmx.api.model.superbeans.base.SuperBean;

import java.util.List;

/**
 * Wraps a metadata set bean, has all referenced structures by compoisition
 */
public interface MetadataSetSuperBean extends SuperBean {

    /**
     * Mandatory returns the metadata structure that defines the structure of this metadata set
     *
     * @return the metadata structure
     */
    MetadataStructureDefinitionBean getMetadataStructure();

    /**
     * Returns an identification for the metadata set
     *
     * @return set id
     */
    String getSetId();

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
     * @return the four digit ISo 8601 year of publication
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
     * @return DataProvider data provider
     */
    DataProviderBean getDataProvider();


    /**
     * Gets reports.
     *
     * @return the reports
     */
    List<MetadataReportSuperBean> getReports();

    /**
     * Override from parent - returns the MetadataSetBean that was used to build this SuperBean
     */
    @Override
    MetadataSetBean getBuiltFrom();

}
