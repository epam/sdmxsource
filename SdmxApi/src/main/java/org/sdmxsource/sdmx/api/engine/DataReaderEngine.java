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
package org.sdmxsource.sdmx.api.engine;

import org.sdmxsource.sdmx.api.exception.DataSetStructureReferenceException;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.model.data.Keyable;
import org.sdmxsource.sdmx.api.model.data.Observation;
import org.sdmxsource.sdmx.api.model.header.DatasetHeaderBean;
import org.sdmxsource.sdmx.api.model.header.HeaderBean;

import java.io.OutputStream;
import java.util.List;


/**
 * The DataReaderEngine engine is capable of reading Data Sets in an iterative way.
 * <p>
 * The DataReaderEngine may contain the dataStructureBean that it is reading data for, and the data being read is not necessarily time series.
 */
public interface DataReaderEngine {
    /**
     * Returns the header of the datasource that this reader engine is reading data for.  The header is related to the message and not an individual dataset
     *
     * @return the header of the datasource that this reader engine is reading data for.  The header is related to the message and not an individual dataset
     */
    HeaderBean getHeader();

    /**
     * Creates a copy of this data reader engine, the copy is another iterator over the same source data
     *
     * @return data reader engine
     */
    DataReaderEngine createCopy();

    /**
     * Returns the provision agreement that this data is for.
     * <p>
     * This is not guaranteed to return a ProvisionAgreementBean, as it may be unknown or not applicable, in this case null will be returned
     * <p>
     * Note this will return null unless there has been a call to moveNextDataset(), this Provision Agreement returned by this method call may change when reading a new dataset
     *
     * @return provision agreement
     */
    ProvisionAgreementBean getProvisionAgreement();

    /**
     * Returns the dataflow that this reader engine is currently reading data for.
     * <p>
     * This is not guaranteed to return a DataflowBean, as it may be unknown or not applicable, in this case null will be returned
     * <p>
     * Note this will return null unless there has been a call to moveNextDataset(), this Dataflow returned by this method call may change when reading a new dataset
     *
     * @return data flow
     */
    DataflowBean getDataFlow();

    /**
     * Returns the data structure definition that this reader engine is currently reading data for
     * <p>
     * Note this will return null unless there has been a call to moveNextDataset(), this DataStructure returned by this method call may change when reading a new dataset
     *
     * @return data structure
     */
    DataStructureBean getDataStructure();

    /**
     * Returns the current dataset index the iterator position is at within the data source.
     * <p>
     * Index starts at -1, (no datasets have been read)
     *
     * @return dataset position
     */
    int getDatasetPosition();

    /**
     * Returns the current Keyable index the iterator position is at within the Data Set
     * <p>
     * Index starts at -1 - (no Keys have been Read)
     *
     * @return the keyable position
     */
    int getKeyablePosition();

    /**
     * Returns the current Series index the iterator position is at within the Data Set
     * <p>
     * Index starts at -1 - (no Series Keys have been Read)
     *
     * @return the series position
     */
    int getSeriesPosition();

    /**
     * Returns the current Observation index the iterator position is at within the current Keyable being read.
     * <p>
     * Index starts at -1 (no observations have been read - meaning getCurrentObservation() will return null
     *
     * @return obs position
     */
    int getObsPosition();

    /**
     * Returns the header information for the current dataset.  This may contain references to the data structure, dataflow, or provision agreement that this data is for
     *
     * @return current dataset header bean
     */
    DatasetHeaderBean getCurrentDatasetHeaderBean();

    /**
     * Returns the attributes available for the current dataset
     *
     * @return a copy of the list, returns an empty list if there are no dataset attributes
     */
    List<KeyValue> getDatasetAttributes();

    /**
     * Returns the current Keyable entry in the dataset, if there has been no initial call to moveNextKeyable, then null will be returned.
     *
     * @return current key
     */
    Keyable getCurrentKey();

    /**
     * Returns the current Observation for the current Keyable.
     * <p>
     * Returns null if any of the following conditions are met:
     * <ul>
     *  <li>getCurrentKey() returns null</li>
     *  <li>getCurrentKey() returns a Keyable which defines a GroupKey</li>
     *  <li>moveNextKeyable() has been called with no subsequent call to moveNextObservation()</li>
     *  <li>moveNextObservation() was called and returned false</li>
     * </ul>
     *
     * @return the next observation value
     */
    Observation getCurrentObservation();

    /**
     * If this reader is in a series, this will return true if the series has any more observation values.
     *
     * @return true if series has more observation values
     */
    boolean moveNextObservation();

    /**
     * Returns true if there are any more keys in the dataset
     *
     * @return boolean
     */
    boolean moveNextKeyable();


    /**
     * Returns true if there are any more datasets in the data source
     *
     * @return boolean
     * @throws DataSetStructureReferenceException if the dataset references a DataStructure, Dataflow, or Provision Agreement which could not be resolved
     */
    boolean moveNextDataset() throws DataSetStructureReferenceException;


    /**
     * Moves the read position back to the start of the Data Set (getKeyablePosition() moved back to -1)
     */
    void reset();

    /**
     * Copies the entire dataset that the reader is reading, to the output stream (irrespective of current position)
     *
     * @param outputStream output stream to copy data to
     */
    void copyToOutputStream(OutputStream outputStream);

    /**
     * Closes the reader engine, and releases all resources.
     */
    void close();

}
