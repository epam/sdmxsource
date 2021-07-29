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
package org.sdmxsource.sdmx.dataparser.engine.impl;

import org.sdmxsource.sdmx.api.exception.ExceptionHandler;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.model.data.Keyable;
import org.sdmxsource.sdmx.api.model.data.Observation;
import org.sdmxsource.sdmx.dataparser.engine.DataValidationEngine;
import org.sdmxsource.sdmx.dataparser.model.ContentConstraintModel;

import java.util.List;

/**
 * The type Constraint validation engine.
 */
public class ConstraintValidationEngine implements DataValidationEngine {

    private ContentConstraintModel constraintModel;

    /**
     * Instantiates a new Constraint validation engine.
     *
     * @param constraintModel the constraint model
     */
    public ConstraintValidationEngine(ContentConstraintModel constraintModel) {
        if (constraintModel == null) {
            throw new IllegalArgumentException("ConstraintModel may not be null!");
        }
        this.constraintModel = constraintModel;
    }


    @Override
    public void validateDataSetAttributes(List<KeyValue> key) {
        for (KeyValue currentDsAttr : key) {
            constraintModel.isValidKeyValue(currentDsAttr);
        }
    }


    @Override
    public void validateKey(Keyable key) {
        constraintModel.isValidKey(key);
    }

    @Override
    public void validateObs(Observation obs) {
        constraintModel.isValidObservation(obs);
    }

    @Override
    public void finishedObs(ExceptionHandler exceptionHandler) {
        // Do nothing
    }
}