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
package org.sdmxsource.sdmx.dataparser.model;

import org.sdmxsource.sdmx.api.constants.DATASET_ACTION;
import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.util.ObjectUtil;

import java.util.Date;
import java.util.Set;


/**
 * The type Header attributes.
 */
public class HeaderAttributes {
    private String id;
    private String test;
    private String truncated;
    private Set<TextTypeWrapper> name;
    private Date prepard;
    private String dataStructureRef;
    private String dataStructureAgency;
    private String datasetAgency;
    private String datasetId;
    private DATASET_ACTION datasetAction;
    private Date extracted;
    private Date reportingBegin;
    private Date reportingEnd;

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets test.
     *
     * @return the test
     */
    public String getTest() {
        return test;
    }

    /**
     * Sets test.
     *
     * @param test the test
     */
    public void setTest(String test) {
        this.test = test;
    }

    /**
     * Gets truncated.
     *
     * @return the truncated
     */
    public String getTruncated() {
        return truncated;
    }

    /**
     * Sets truncated.
     *
     * @param truncated the truncated
     */
    public void setTruncated(String truncated) {
        this.truncated = truncated;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public Set<TextTypeWrapper> getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(Set<TextTypeWrapper> name) {
        this.name = name;
    }

    /**
     * Gets prepard.
     *
     * @return the prepard
     */
    public Date getPrepard() {
        return prepard;
    }

    /**
     * Sets prepard.
     *
     * @param prepard the prepard
     */
    public void setPrepard(Date prepard) {
        this.prepard = prepard;
    }

    /**
     * Gets data structure ref.
     *
     * @return the data structure ref
     */
    public String getDataStructureRef() {
        return dataStructureRef;
    }

    /**
     * Sets data structure ref.
     *
     * @param dataStructureRef the data structure ref
     */
    public void setDataStructureRef(String dataStructureRef) {
        this.dataStructureRef = dataStructureRef;
    }

    /**
     * Gets data structure agency.
     *
     * @return the data structure agency
     */
    public String getDataStructureAgency() {
        return dataStructureAgency;
    }

    /**
     * Sets data structure agency.
     *
     * @param dataStructureAgency the data structure agency
     */
    public void setDataStructureAgency(String dataStructureAgency) {
        this.dataStructureAgency = dataStructureAgency;
    }

    /**
     * Gets dataset agency.
     *
     * @return the dataset agency
     */
    public String getDatasetAgency() {
        return datasetAgency;
    }

    /**
     * Sets dataset agency.
     *
     * @param datasetAgency the dataset agency
     */
    public void setDatasetAgency(String datasetAgency) {
        this.datasetAgency = datasetAgency;
    }

    /**
     * Gets dataset id.
     *
     * @return the dataset id
     */
    public String getDatasetId() {
        return datasetId;
    }

    /**
     * Sets dataset id.
     *
     * @param datasetId the dataset id
     */
    public void setDatasetId(String datasetId) {
        this.datasetId = datasetId;
    }

    /**
     * Gets dataset action.
     *
     * @return the dataset action
     */
    public DATASET_ACTION getDatasetAction() {
        return datasetAction;
    }

    /**
     * Sets dataset action.
     *
     * @param datasetAction the dataset action
     */
    public void setDatasetAction(String datasetAction) {
        if (!ObjectUtil.validString(datasetAction)) {
            this.datasetAction = DATASET_ACTION.INFORMATION;
        }
        if (datasetAction.equals("Append") || datasetAction.equals("Update")) {
            this.datasetAction = DATASET_ACTION.APPEND;
        }
        if (datasetAction.equals("Replace")) {
            this.datasetAction = DATASET_ACTION.REPLACE;
        }
        if (datasetAction.equals("Delete")) {
            this.datasetAction = DATASET_ACTION.DELETE;
        }
    }

    /**
     * Gets extracted.
     *
     * @return the extracted
     */
    public Date getExtracted() {
        return extracted;
    }

    /**
     * Sets extracted.
     *
     * @param extracted the extracted
     */
    public void setExtracted(Date extracted) {
        this.extracted = extracted;
    }

    /**
     * Gets reporting begin.
     *
     * @return the reporting begin
     */
    public Date getReportingBegin() {
        return reportingBegin;
    }

    /**
     * Sets reporting begin.
     *
     * @param reportingBegin the reporting begin
     */
    public void setReportingBegin(Date reportingBegin) {
        this.reportingBegin = reportingBegin;
    }

    /**
     * Gets reporting end.
     *
     * @return the reporting end
     */
    public Date getReportingEnd() {
        return reportingEnd;
    }

    /**
     * Sets reporting end.
     *
     * @param reportingEnd the reporting end
     */
    public void setReportingEnd(Date reportingEnd) {
        this.reportingEnd = reportingEnd;
    }
}
