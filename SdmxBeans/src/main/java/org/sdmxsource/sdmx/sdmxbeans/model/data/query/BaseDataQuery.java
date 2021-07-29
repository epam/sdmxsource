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
package org.sdmxsource.sdmx.sdmxbeans.model.data.query;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;

import java.util.Set;

/**
 * Base Data query for both simple and complex queries
 *
 * @author MetaTech
 */
public abstract class BaseDataQuery {
    /**
     * The Dimension at observation.
     */
    protected String dimensionAtObservation;
    /**
     * The First n obs.
     */
    protected Integer firstNObs;
    /**
     * The Last n obs.
     */
    protected Integer lastNObs;
    /**
     * The Dataflow bean.
     */
    protected DataflowBean dataflowBean;
    /**
     * The Data structure bean.
     */
    protected DataStructureBean dataStructureBean;

    /**
     * Validate query.
     */
    protected void validateQuery() {
        if (dataflowBean == null) {
            throw new IllegalArgumentException("Can not create DataQuery, Dataflow is required");
        }
        if (dataStructureBean == null) {
            throw new IllegalArgumentException("Can not create DataQuery, DataStructure is required");
        }
        validateQueryComponents();
        validateDimensionAtObservation();
    }

    /**
     * Validates that the queried components (e.g. dimension/attributes) exist on the data structure
     */
    private void validateQueryComponents() throws SdmxSemmanticException {
        for (String currentComponetId : getQueryComponentIds()) {
            if (dataStructureBean.getComponent(currentComponetId) == null) {
                throw new SdmxSemmanticException("Data Structure '" + dataStructureBean.getUrn() + "' does not contain component with id: " + currentComponetId);
            }
        }
    }

    /**
     * Returns a set of strings which represent the component ids that are being queried on
     *
     * @return query component ids
     */
    protected abstract Set<String> getQueryComponentIds();

    /**
     * If no dimension at observation is set, then the following rules apply in the order specified:
     * <ol>
     *  <li>Set to Time Dimension (if it exists)</li>
     *  <li>Set to the first Measure Dimension (if one exists)</li>
     *  <li>Set to AllDimensions</li>
     * </ol>
     * <p>
     * If the dimension at observation is set, then it is validated to exist
     */
    private void validateDimensionAtObservation() {
        if (dimensionAtObservation == null) {
            if (dataStructureBean.getTimeDimension() != null) {
                dimensionAtObservation = dataStructureBean.getTimeDimension().getId();
            } else if (dataStructureBean.getDimensions(SDMX_STRUCTURE_TYPE.MEASURE_DIMENSION).size() > 0) {
                dimensionAtObservation = dataStructureBean.getDimensions(SDMX_STRUCTURE_TYPE.MEASURE_DIMENSION).get(0).getId();
            } else {
                dimensionAtObservation = "AllDimensions";
            }
        } else if (!dimensionAtObservation.equals("AllDimensions")) {
            DimensionBean dimension = dataStructureBean.getDimension(dimensionAtObservation);
            if (dimension == null) {
                StringBuilder sb = new StringBuilder();
                for (DimensionBean dim : dataStructureBean.getDimensions(SDMX_STRUCTURE_TYPE.DIMENSION, SDMX_STRUCTURE_TYPE.MEASURE_DIMENSION, SDMX_STRUCTURE_TYPE.TIME_DIMENSION)) {
                    sb.append(dim.getId() + System.getProperty("line.separator"));
                }
                throw new IllegalArgumentException("Can not create DataQuery, The dimension at observation '" + dimensionAtObservation + "' is not included in the Dimension list of the DSD.  Allowed values are " + sb.toString());
            }
        }
    }

    /**
     * Gets dimension at observation.
     *
     * @return the dimension at observation
     */
    public String getDimensionAtObservation() {
        return dimensionAtObservation;
    }
}
