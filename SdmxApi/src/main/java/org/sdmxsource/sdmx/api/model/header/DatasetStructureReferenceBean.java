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

import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;

/**
 * Structure is used in a Dataset header, where there is a one to one mapping between a Structure (which defines the DSD/Flow/or Provision and the Dimension at the observation level)
 *
 * @author Matt Nelson
 */
public interface DatasetStructureReferenceBean {

    /**
     * The constant ALL_DIMENSIONS.
     */
    String ALL_DIMENSIONS = "AllDimensions";

    /**
     * Returns the id of this structure, this
     *
     * @return id
     */
    String getId();

    /**
     * Returns the structure reference, either a provision agreement, dataflow, a data structure definition (DSD), or metadata structure definition (MSD)
     *
     * @return structure reference
     */
    StructureReferenceBean getStructureReference();

    /**
     * The serviceURL attribute indicates the URL of an SDMX SOAP web service from which the
     * details of the object can be retrieved.
     * <p>
     * Note that this can be a registry or and SDMX structural metadata repository, as they both implement that same web service interface.
     *
     * @return service url
     */
    String getServiceURL();

    /**
     * The structureURL attribute indicates the URL of a SDMX-ML structure message
     * (in the same version as the source document) in which the externally referenced object is contained.
     * <p>
     * Note that this may be a URL of an SDMX RESTful web service which will return the referenced object.
     *
     * @return structure url
     */
    String getStructureURL();

    /**
     * The dimensionAtObservation is used to reference the dimension at the observation level for data messages.
     * <p>
     * Structure.ALL_DIMENSIONS denotes that the cross sectional data is in the flat format.
     * <p>
     * PrimaryMeasureBean.FIXED_ID denotes that the data is in time series format
     *
     * @return the dimension at observation
     */
    String getDimensionAtObservation();

    /**
     * Returns true if getDimensionAtObservation() returns PrimaryMeasureBean.FIXED_ID
     *
     * @return time series
     */
    boolean isTimeSeries();

}
