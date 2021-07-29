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
package org.sdmxsource.sdmx.ediparser.model.document;

import org.sdmxsource.sdmx.api.constants.DATASET_ACTION;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.model.header.PartyBean;

import java.util.Date;
import java.util.List;


/**
 * The interface Edi document position.
 */
public interface EDIDocumentPosition {

    /**
     * Only relevant for data messages. Returns a list of dataset attributes, or an empty list if there are none
     *
     * @return dataset attributes
     */
    List<KeyValue> getDatasetAttributes();

    /**
     * Gets preparation date.
     *
     * @return the preparation date
     */
    Date getPreparationDate();

    /**
     * Gets reporting period.
     *
     * @return the reporting period
     */
    Date getReportingPeriod();

    /**
     * Gets dataset id.
     *
     * @return the dataset id
     */
    String getDatasetId();

    /**
     * Gets message agency.
     *
     * @return the message agency
     */
    String getMessageAgency();

    /**
     * Gets sending agency.
     *
     * @return the sending agency
     */
    PartyBean getSendingAgency();

    /**
     * Gets recieving agency.
     *
     * @return the recieving agency
     */
    String getRecievingAgency();

    /**
     * Is structure boolean.
     *
     * @return the boolean
     */
    boolean isStructure();

    /**
     * Is data boolean.
     *
     * @return the boolean
     */
    boolean isData();

    /**
     * Gets start line.
     *
     * @return the start line
     */
    int getStartLine();

    /**
     * Gets end line.
     *
     * @return the end line
     */
    int getEndLine();

    /**
     * Gets dataset action.
     *
     * @return the dataset action
     */
//Data related
    DATASET_ACTION getDatasetAction();

    /**
     * Gets data structure identifier.
     *
     * @return the data structure identifier
     */
    String getDataStructureIdentifier();

    /**
     * Gets missing value.
     *
     * @return the missing value
     */
    String getMissingValue();

}
