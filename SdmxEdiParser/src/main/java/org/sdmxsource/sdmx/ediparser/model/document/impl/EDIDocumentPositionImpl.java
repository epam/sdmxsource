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
package org.sdmxsource.sdmx.ediparser.model.document.impl;

import org.sdmxsource.sdmx.api.constants.DATASET_ACTION;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.model.header.PartyBean;
import org.sdmxsource.sdmx.ediparser.model.document.EDIDocumentPosition;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The type Edi document position.
 */
public class EDIDocumentPositionImpl implements EDIDocumentPosition {
    private int startLine;
    private int endLine;
    private boolean isStructure;

    private Date datasetPreparation;
    private Date reportingPeriod;

    private String datasetId;
    private String messageAgency;
    private PartyBean sendingAgency;
    private String recievingAgency;

    private DATASET_ACTION datasetAction;
    private String keyFamilyIdentifier;
    private String missingValue;

    private List<KeyValue> datasetAttributes;

    /**
     * Instantiates a new Edi document position.
     *
     * @param startLine           the start line
     * @param endLine             the end line
     * @param isStructure         the is structure
     * @param datasetId           the dataset id
     * @param messageAgency       the message agency
     * @param sendingAgency       the sending agency
     * @param recievingAgency     the recieving agency
     * @param datasetAction       the dataset action
     * @param keyFamilyIdentifier the key family identifier
     * @param missingValue        the missing value
     * @param datasetPreparation  the dataset preparation
     * @param reportingPeriod     the reporting period
     * @param datasetAttributes   the dataset attributes
     */
    public EDIDocumentPositionImpl(int startLine, int endLine, boolean isStructure,
                                   String datasetId,
                                   String messageAgency,
                                   PartyBean sendingAgency,
                                   String recievingAgency,
                                   DATASET_ACTION datasetAction,
                                   String keyFamilyIdentifier,
                                   String missingValue,
                                   Date datasetPreparation,
                                   Date reportingPeriod,
                                   List<KeyValue> datasetAttributes) {
        this.startLine = startLine;
        this.endLine = endLine;
        this.isStructure = isStructure;
        this.datasetId = datasetId;
        this.messageAgency = messageAgency;
        this.sendingAgency = sendingAgency;
        this.recievingAgency = recievingAgency;
        this.datasetAction = datasetAction;
        this.keyFamilyIdentifier = keyFamilyIdentifier;
        this.missingValue = missingValue;
        this.datasetPreparation = datasetPreparation;
        this.reportingPeriod = reportingPeriod;
        this.datasetAttributes = datasetAttributes;
    }

    @Override
    public List<KeyValue> getDatasetAttributes() {
        return new ArrayList<KeyValue>(datasetAttributes);
    }

    @Override
    public Date getPreparationDate() {
        return datasetPreparation;
    }

    @Override
    public Date getReportingPeriod() {
        return reportingPeriod;
    }

    @Override
    public DATASET_ACTION getDatasetAction() {
        return datasetAction;
    }

    @Override
    public String getDatasetId() {
        return datasetId;
    }

    @Override
    public String getDataStructureIdentifier() {
        return keyFamilyIdentifier;
    }

    @Override
    public String getMissingValue() {
        return missingValue;
    }

    @Override
    public String getMessageAgency() {
        return messageAgency;
    }

    @Override
    public PartyBean getSendingAgency() {
        return sendingAgency;
    }

    @Override
    public String getRecievingAgency() {
        return recievingAgency;
    }

    @Override
    public boolean isStructure() {
        return isStructure;
    }

    @Override
    public boolean isData() {
        return !isStructure;
    }

    @Override
    public int getStartLine() {
        return startLine;
    }

    @Override
    public int getEndLine() {
        return endLine;
    }
}
