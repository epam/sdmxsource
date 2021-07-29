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
package org.sdmxsource.sdmx.dataparser.manager.impl;

import org.sdmxsource.sdmx.api.engine.DataReaderEngine;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.data.Keyable;
import org.sdmxsource.sdmx.api.model.data.Observation;
import org.sdmxsource.sdmx.dataparser.manager.DataReaderPositionManager;


/**
 * The type Data reader position manager.
 */
public class DataReaderPositionManagerImpl implements DataReaderPositionManager {

    @Override
    public Keyable moveToPosition(DataReaderEngine dataReaderEngine, int datasetPos, int i) {
        moveToDataset(dataReaderEngine, datasetPos);
        int keyableIndex = dataReaderEngine.getKeyablePosition();
        int stepsToGo;
        if (i == keyableIndex) {
            return dataReaderEngine.getCurrentKey();
        }
        if (i > keyableIndex) {
            stepsToGo = i - (keyableIndex + 1);
        } else {
            stepsToGo = i; // starts at zero, which is one step
            dataReaderEngine.reset();
        }
        for (int j = 0; j <= stepsToGo; j++) {
            if (!dataReaderEngine.moveNextKeyable()) {
                return null;
            }
        }
        return dataReaderEngine.getCurrentKey();
    }

    @Override
    public Observation moveToObs(DataReaderEngine dataReaderEngine, int datasetPos, int keyPosition, int obsNum) {
        moveToPosition(dataReaderEngine, datasetPos, keyPosition);
        for (int j = 0; j <= keyPosition; j++) {
            if (!dataReaderEngine.moveNextObservation()) {
                return null;
            }
        }
        return dataReaderEngine.getCurrentObservation();
    }

    private void moveToDataset(DataReaderEngine dataReaderEngine, int i) {
        int currentDatasetIndex = dataReaderEngine.getDatasetPosition();
        if (currentDatasetIndex > i) {
            dataReaderEngine.reset();
            currentDatasetIndex = -1;
        }
        while (currentDatasetIndex < i) {
            boolean moveNextDatasetSucceed = dataReaderEngine.moveNextDataset();
            if (!moveNextDatasetSucceed) {
                throw new SdmxSemmanticException("Unable to move to next dataset");
            }
            currentDatasetIndex = dataReaderEngine.getDatasetPosition();
        }
    }
}
