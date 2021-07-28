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

/**
 * The enum Data position.
 */
public enum DATA_POSITION {
    /**
     * Dataset data position.
     */
    DATASET,
    /**
     * Dataset attribute data position.
     */
    DATASET_ATTRIBUTE,
    /**
     * Series key data position.
     */
    SERIES_KEY,
    /**
     * Series key attribute data position.
     */
    SERIES_KEY_ATTRIBUTE,
    /**
     * Group data position.
     */
    GROUP,
    /**
     * Group key data position.
     */
    GROUP_KEY,
    /**
     * Group key attribute data position.
     */
    GROUP_KEY_ATTRIBUTE,
    /**
     * Group series key data position.
     */
    GROUP_SERIES_KEY,
    /**
     * Group series key attribute data position.
     */
    GROUP_SERIES_KEY_ATTRIBUTE,
    /**
     * Observation data position.
     */
    OBSERVATION,
    /**
     * Observation attribute data position.
     */
    OBSERVATION_ATTRIBUTE;
}
