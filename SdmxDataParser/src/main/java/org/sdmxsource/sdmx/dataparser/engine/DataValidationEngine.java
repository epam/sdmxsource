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
package org.sdmxsource.sdmx.dataparser.engine;

import org.sdmxsource.sdmx.api.exception.ExceptionHandler;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.model.data.Keyable;
import org.sdmxsource.sdmx.api.model.data.Observation;

import java.util.List;

/**
 * A DataValidationEngine validates the data that it is supplied with.
 */
public interface DataValidationEngine {

    /**
     * Validates the supplied attribute values throwing an exception if key is not valid
     *
     * @param key the key
     */
    void validateDataSetAttributes(List<KeyValue> key);

    /**
     * Validates the supplied key throwing an exception if key is not valid
     *
     * @param key the key
     */
    void validateKey(Keyable key);

    /**
     * Validates the supplied observation throwing an exception if observation is not valid
     *
     * @param obs the obs
     */
    void validateObs(Observation obs);

    /**
     * A method that may be used to perform any handling once all the observations have been processed
     *
     * @param exceptionHandler the exception handler
     */
    void finishedObs(ExceptionHandler exceptionHandler);
}
