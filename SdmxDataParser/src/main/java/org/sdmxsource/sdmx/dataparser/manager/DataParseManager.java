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

import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.data.DataFormat;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;

import java.io.OutputStream;
import java.util.List;


/**
 * The data Manager is responsible for transforming data to adhere to one SMDX schema type to another.
 */
public interface DataParseManager {
    /**
     * Performs a transform from one data format to another data format.
     *
     * @param sourceData       the location of the the input stream which reads the data to transform
     * @param out              OutputStream to write the transform results to
     * @param dataFormat       the format to transform to
     * @param retrievalManager used to get any data structures needed to interpret the data
     */
    void performTransform(ReadableDataLocation sourceData,
                          OutputStream out,
                          DataFormat dataFormat,
                          SdmxBeanRetrievalManager retrievalManager);

    /**
     * Performs a transform from one data format to another data format.
     *
     * @param sourceData   the location of the the input stream which reads the data to transform
     * @param out          OutputStream to write the transform results to
     * @param dataFormat   the format to transform to
     * @param dsd          the Structure bean that specifies the data format
     * @param dataflowBean (optional) provides extra information about the data
     */
    void performTransform(ReadableDataLocation sourceData,
                          OutputStream out,
                          DataFormat dataFormat,
                          DataStructureBean dsd,
                          DataflowBean dataflowBean);

    /**
     * Performs a transform from one data format to another data format.
     *
     * @param sourceData  the location of the the input stream which reads the data to transform
     * @param dsdLocation the location of the structure used to transform the data
     * @param out         OutputStream to write the transform results to
     * @param dataFormat  the format to transform to
     */
    void performTransform(ReadableDataLocation sourceData,
                          ReadableDataLocation dsdLocation,
                          OutputStream out,
                          DataFormat dataFormat);

    /**
     * Perform transform and split list.
     *
     * @param sourceData  the source data
     * @param dsdLocation the dsd location
     * @param dataFormat  the data format
     * @return list list
     */
    List<ReadableDataLocation> performTransformAndSplit(ReadableDataLocation sourceData,
                                                        ReadableDataLocation dsdLocation,
                                                        DataFormat dataFormat);

    /**
     * Perform transform and split list.
     *
     * @param sourceData           the source data
     * @param dataFormat           the data format
     * @param beanRetrievalManager the bean retrieval manager
     * @return list list
     */
    List<ReadableDataLocation> performTransformAndSplit(ReadableDataLocation sourceData,
                                                        DataFormat dataFormat,
                                                        SdmxBeanRetrievalManager beanRetrievalManager);

    /**
     * Performs a transform from one data format to another data format.
     *
     * @param sourceData           giving access to the input stream which reads the data to transform
     * @param dataFormat           the data format
     * @param beanRetrievalManager the bean retrieval manager
     * @return readable data location
     */
    ReadableDataLocation performTransform(ReadableDataLocation sourceData,
                                          DataFormat dataFormat,
                                          SdmxBeanRetrievalManager beanRetrievalManager);

    /**
     * Performs a transform from one data format to another data format.
     *
     * @param sourceData   the source data
     * @param dataFormat   the data format
     * @param dsd          the dsd
     * @param dataflowBean (optional) provides extra information about the data
     * @return readable data location
     */
    ReadableDataLocation performTransform(ReadableDataLocation sourceData,
                                          DataFormat dataFormat,
                                          DataStructureBean dsd,
                                          DataflowBean dataflowBean);
}
