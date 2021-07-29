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
import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * A Header Contains any information to go at the beginning of an exchange message.
 */
public interface HeaderBean {
    /**
     * The constant DSD_REF.
     */
    public static final String DSD_REF = "DSD_REFERENCE";
    /**
     * The constant DSD_AGENCY_REF.
     */
    public static final String DSD_AGENCY_REF = "DSD_AGENCY_ID";
    /**
     * The constant DATASET_AGENCY.
     */
    public static final String DATASET_AGENCY = "DATASET_AGENCY";

    /**
     * Returns true if this Header instance has a value stored for this property in its additional attributes @See getAdditionalAttributes()
     *
     * @param headerField the header field
     * @return additional attribute
     */
    boolean hasAdditionalAttribute(String headerField);

    /**
     * Returns the header value for a given field. For example getHeaderValue(Header.DSD_REF) would return the value given for the keyFamilyRef (SDMX 2.0)
     *
     * @param headerField the header field
     * @return additional attribute
     */
    String getAdditionalAttribute(String headerField);

    /**
     * Returns a map of any additional attributes that are stored in the header - the key is the field, such as Header.DSD_REF, the value is the value of that field
     *
     * @return additional attributes
     */
    Map<String, String> getAdditionalAttributes();

    /**
     * Returns the data provider reference
     *
     * @return data provider reference
     */
    StructureReferenceBean getDataProviderReference();

    /**
     * Sets the data provider reference
     *
     * @param dataProvider the data provider
     */
    void setDataProviderReference(StructureReferenceBean dataProvider);


    /**
     * Gets the action for the message
     *
     * @return action action
     */
    DATASET_ACTION getAction();

    /**
     * Sets the action for the message
     *
     * @param action the action
     */
    void setAction(DATASET_ACTION action);

    /**
     * Gets the id of the header
     *
     * @return id id
     */
    String getId();

    /**
     * Sets the id of the message, can not be null
     *
     * @param id the id
     */
    void setId(String id);

    /**
     * Gets a dataset id for the message
     *
     * @return dataset id
     */
    String getDatasetId();

    /**
     * Sets a dataset id for the message
     *
     * @param datasetId the dataset id
     */
    void setDatasetId(String datasetId);

    /**
     * Returns the embargo date for the message
     *
     * @return embargo of null if undefined
     */
    Date getEmbargoDate();

    /**
     * Sets the embargo date for the message
     *
     * @param embargoDate the embargo date
     */
    void setEmbargoDate(Date embargoDate);

    /**
     * Returns the extracted date for the message
     *
     * @return extracted or null if undefined
     */
    Date getExtracted();

    /**
     * Returns the prepared date for the message
     *
     * @return prepared or null if undefined
     */
    Date getPrepared();

    /**
     * Returns the name, or an empty list if none are defined
     *
     * @return name name
     */
    List<TextTypeWrapper> getName();

    /**
     * Adds a name to the list
     *
     * @param name the name
     */
    void addName(TextTypeWrapper name);

    /**
     * Returns the list of receivers or an empty list if none are defined
     *
     * @return receiver receiver
     */
    List<PartyBean> getReceiver();

    /**
     * Adds a receiver to the list of receivers
     *
     * @param receiver the receiver
     */
    void addReceiver(PartyBean receiver);

    /**
     * Returns the reporting begin date
     *
     * @return reporting begin
     */
    Date getReportingBegin();

    /**
     * Sets the reporting being date
     *
     * @param date the date
     */
    void setReportingBegin(Date date);

    /**
     * Returns the reporting end date
     *
     * @return reporting end
     */
    Date getReportingEnd();

    /**
     * Sets the reporting end date
     *
     * @param date the date
     */
    void setReportingEnd(Date date);

    /**
     * Returns the list of sources, or an empty list if none are defined
     *
     * @return source source
     */
    List<TextTypeWrapper> getSource();

    /**
     * Adds a source to the list of sources
     *
     * @param source the source
     */
    void addSource(TextTypeWrapper source);

    /**
     * Returns the sender
     *
     * @return sender sender
     */
    PartyBean getSender();

    /**
     * Sets the sender (can not be null)
     *
     * @param party the party
     */
    void setSender(PartyBean party);

    /**
     * Returns true if this is a test message
     *
     * @return boolean
     */
    boolean isTest();

    /**
     * True if this is a test message
     *
     * @param test the test
     */
    void setTest(boolean test);

    /**
     * Returns the structure for the given id
     *
     * @param structureId the structure id
     * @return structure by id
     */
    DatasetStructureReferenceBean getStructureById(String structureId);

    /**
     * Returns all data structure references
     *
     * @return structures structures
     */
    List<DatasetStructureReferenceBean> getStructures();

    /**
     * Adds a dataset structure reference to the list
     *
     * @param ref the ref
     */
    void addStructure(DatasetStructureReferenceBean ref);
}
