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
package org.sdmxsource.sdmx.ediparser.model;

import org.sdmxsource.sdmx.api.engine.DataReaderEngine;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.header.DatasetHeaderBean;
import org.sdmxsource.sdmx.api.model.header.HeaderBean;

import java.util.List;


/**
 * The interface Edi workspace.
 */
public interface EDIWorkspace {

    /**
     * Returns the header of an EDI message
     *
     * @return the header of an EDI message
     */
    HeaderBean getHeader();

    /**
     * Returns a list of all the dataset headers contained in the workspace
     * <p>
     * If hasData() returns false then there will be dataset headers and an empty list will be returned
     *
     * @return dataset headers
     */
    List<DatasetHeaderBean> getDatasetHeaders();

    /**
     * Returns a list of the SdmxBeans created by
     *
     * @return beans beans
     */
    List<SdmxBeans> getBeans();

    /**
     * Returns a dataset reader engine that can be used to iterate over the contained datasets.
     *
     * @param beanRetrieval the bean retrieval
     * @return will return null if hasData() is false
     */
    DataReaderEngine getDataReader(SdmxBeanRetrievalManager beanRetrieval);

    /**
     * Returns a data reader for the key family, only the workspaces that match the given key family bean will be used
     *
     * @param keyFamily    the key family
     * @param dataflowBean (optional) if provided it will be accessible from the data reader
     * @return DataReaderEngine for DSD, null if there is no data for the given DSD
     */
    DataReaderEngine getDataReader(DataStructureBean keyFamily, DataflowBean dataflowBean);

    /**
     * If there are more then one SdmxBeans, this method will return the beans in one merged container.
     *
     * @return An SdmxBeans object
     * @throws RuntimeException if there are no structures.
     */
    SdmxBeans getMergedBeans();

    /**
     * Returns true if there are any EDIDataDocuments in this workspace
     *
     * @return true if there are any EDIDataDocuments in this workspace
     */
    boolean hasData();

    /**
     * Returns true if there are any SdmxBeans in this workspace
     *
     * @return true if there are any SdmxBeans in this workspace
     */
    boolean hasStructures();
}
