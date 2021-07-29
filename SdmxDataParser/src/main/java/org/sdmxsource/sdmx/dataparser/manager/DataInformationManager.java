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
package org.sdmxsource.sdmx.dataparser.manager;

import org.sdmxsource.sdmx.api.constants.TIME_FORMAT;
import org.sdmxsource.sdmx.api.engine.DataReaderEngine;
import org.sdmxsource.sdmx.api.model.data.DataFormat;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.dataparser.model.DataInformation;
import org.sdmxsource.sdmx.util.exception.ParseException;

import java.util.List;
import java.util.Map;

/**
 * The purpose of the data information manager is to get metadata off the dataset.
 */
public interface DataInformationManager {

    /**
     * Returns the data type for the sourceData
     *
     * @param sourceData the source data
     * @return data type
     * @throws ParseException when the type of data can not be determined
     */
    DataFormat getDataType(ReadableDataLocation sourceData) throws ParseException;

    /**
     * Returns the target namespace of the dataset
     *
     * @param sourceData the source data
     * @return target namespace
     */
    String getTargetNamespace(ReadableDataLocation sourceData);

//	/**
//	 * Returns the dataset attributes 
//	 * @param sourceData
//	 * @return
//	 */
//	DataSetAttributes getDataSetAttributes(ReadableDataLocation sourceData);

    /**
     * Returns DataInformation about the data, this processes the entire dataset to give an overview of what is in the dataset.
     * <p>
     *
     * @param dre the DataReaderEngine
     * @return data information
     */
    DataInformation getDataInformation(DataReaderEngine dre);


//	/**
//	 * Returns the key family for the data stored at the URI.
//	 * <p>
//	 * For this method to work, the data must contain a reference to the DSD agency and DSD id, if no version
//	 * information is present then version 1.0 is assumed
//	 * @param sourceData to obtain the data
//	 * @param sdmxBeanRetrievalManager used to resolve the key family
//	 * 
//	 * @throws ReferenceException if the dataStructureBean was either not referenced, or not found, or is no sdmxStructureRetrievalManager was provided
//	 */
//	DataStructureBean getKeyFamilyForData(ReadableDataLocation sourceData, SdmxBeanRetrievalManager sdmxBeanRetrievalManager);

    /**
     * Returns an ordered list of all the unique dates for each time format in the dataset.
     * <p>
     * This list is ordered with the earliest date first.
     * <p>
     * This method will call <code>reset()</code> on the dataReaderEngine before and after the
     * information has been gathered
     *
     * @param dataReaderEngine the data reader engine
     * @return a map of time format to the list of dates that are contained for the time format
     */
    Map<TIME_FORMAT, List<String>> getAllReportedDates(DataReaderEngine dataReaderEngine);

    /**
     * Returns a list of dimension - value pairs where there is only a single value in the data for the dimension.  For example if the entire
     * dataset had FREQ=A then one of the returned KeyValue pairs would be FREQ,A.  If FREQ=A and Q this would not be returned.
     * <p>
     * <b>Note : an initial call to DataReaderEngine.reset will be made</b>
     *
     * @param dre               the dre
     * @param includeObs        the include obs
     * @param includeAttributes if true will also report the attributes that have only one value in the entire dataset
     * @return fixed concepts
     */
    List<KeyValue> getFixedConcepts(DataReaderEngine dre, boolean includeObs, boolean includeAttributes);
}
