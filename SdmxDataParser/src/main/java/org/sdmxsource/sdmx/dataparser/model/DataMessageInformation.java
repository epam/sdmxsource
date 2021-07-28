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

import org.sdmxsource.sdmx.api.constants.DATA_TYPE;
import org.sdmxsource.sdmx.api.model.header.DatasetHeaderBean;
import org.sdmxsource.sdmx.api.model.header.HeaderBean;

import java.util.List;


/**
 * The type Data message information.
 */
public class DataMessageInformation {
    private DATA_TYPE dataType;
    private HeaderBean header;
    private List<DatasetHeaderBean> datasetHeaders;

    /**
     * Instantiates a new Data message information.
     *
     * @param dataType       the data type
     * @param header         the header
     * @param datasetHeaders the dataset headers
     */
    public DataMessageInformation(DATA_TYPE dataType, HeaderBean header, List<DatasetHeaderBean> datasetHeaders) {
        this.dataType = dataType;
        this.header = header;
        this.datasetHeaders = datasetHeaders;
    }

    /**
     * Gets data type.
     *
     * @return the data type
     */
    public DATA_TYPE getDataType() {
        return dataType;
    }

    /**
     * Gets header.
     *
     * @return the header
     */
    public HeaderBean getHeader() {
        return header;
    }

    /**
     * Gets dataset headers.
     *
     * @return the dataset headers
     */
    public List<DatasetHeaderBean> getDatasetHeaders() {
        return datasetHeaders;
    }
}
