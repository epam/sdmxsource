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
package org.sdmxsource.sdmx.api.model.metadata.mutable;

import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.base.MutableBean;

import java.util.Calendar;
import java.util.List;


/**
 * The interface Metadata set mutable bean.
 */
public interface MetadataSetMutableBean extends MutableBean {

    /**
     * Mandatory
     *
     * @return reference to the structure in the Header above
     */
    String getStructureRef();

    /**
     * Mandatory
     *
     * @param structureRef reference to the structure in the Header above
     */
    void setStructureRef(String structureRef);

    /**
     * Optional
     *
     * @return the inclusive start time of the data reported in the metadata set
     */
    Calendar getReportingBeginDate();

    /**
     * Optional
     *
     * @param reportingBeginDate the reporting begin date
     */
    void setReportingBeginDate(Calendar reportingBeginDate);

    /**
     * Optional
     *
     * @return the inclusive end time of the data reported in the metadata set
     */
    Calendar getReportingEndDate();

    /**
     * Optional
     *
     * @param reportingEndDate the inclusive end time of the data reported in the metadata set
     */
    void setReportingEndDate(Calendar reportingEndDate);

    /**
     * Optional
     *
     * @return the inclusive start time of the validity of the info in the metadata set
     */
    Calendar getValidFromDate();

    /**
     * Optional
     *
     * @param validFromDate the inclusive start time of the validity of the info in the metadata set
     */
    void setValidFromDate(Calendar validFromDate);

    /**
     * Optional
     *
     * @return the inclusive end time of the validity of the info in the metadata set
     */
    Calendar getValidToDate();

    /**
     * Optional
     *
     * @param validToDate the inclusive end time of the validity of the info in the metadata set
     */
    void setValidToDate(Calendar validToDate);

    /**
     * Optional
     *
     * @return the four digit ISo 8601 year of publication
     */
    Calendar getPublicationYear();

    /**
     * Optional
     *
     * @param publicationYear the four digit ISo 8601 year of publication
     */
    void setPublicationYear(Calendar publicationYear);

    /**
     * Optional
     *
     * @return the publication period
     */
    String getPublicationPeriod(); //HACK is an Object in xmlbeans

    /**
     * Optional
     *
     * @param publicationPeriod the publication period
     */
    void setPublicationPeriod(Object publicationPeriod);

    /**
     * Optional
     *
     * @return reference to the DataProvider
     */
    StructureReferenceBean getDataProviderReference();

    /**
     * Optional
     *
     * @param dataProviderReference reference to the DataProvider
     */
    void setDataProviderReference(StructureReferenceBean dataProviderReference);

    /**
     * Gets reports.
     *
     * @return the reports
     */
    List<ReportMutableBean> getReports();

    /**
     * Sets reports.
     *
     * @param reports the reports
     */
    void setReports(List<ReportMutableBean> reports);

    /**
     * Add report.
     *
     * @param report the report
     */
    void addReport(ReportMutableBean report);

    //FUNC footer messages
}
