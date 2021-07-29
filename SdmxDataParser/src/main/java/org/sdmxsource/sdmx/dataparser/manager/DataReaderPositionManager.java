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

import org.sdmxsource.sdmx.api.engine.DataReaderEngine;
import org.sdmxsource.sdmx.api.model.data.Keyable;
import org.sdmxsource.sdmx.api.model.data.Observation;

/**
 * Moves the data reader position backwards or forwards
 */
public interface DataReaderPositionManager {

    /**
     * Moves the data reader engine to the position at the given index
     *
     * @param dataReaderEngine the data reader engine
     * @param datasetIndex     the dataset to read the key for
     * @param keyPosition      the key index in the dataset
     * @return keyable keyable
     */
    Keyable moveToPosition(DataReaderEngine dataReaderEngine, int datasetIndex, int keyPosition);

    /**
     * Moves the data reader engine to the observation at the given index, from the key otained by the given index
     *
     * @param dataReaderEngine the data reader engine
     * @param datasetIndex     the dataset to read the key for
     * @param keyPosition      the key index that the observation belongs to
     * @param obsNum           the observation index in the key
     * @return observation observation
     */
    Observation moveToObs(DataReaderEngine dataReaderEngine, int datasetIndex, int keyPosition, int obsNum);
}
