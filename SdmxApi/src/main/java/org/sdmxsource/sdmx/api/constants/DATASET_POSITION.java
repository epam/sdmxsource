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
package org.sdmxsource.sdmx.api.constants;

/**
 * Contains a list of possible position in a dataset
 *
 * @author Matt Nelson
 */
public enum DATASET_POSITION {
    /**
     * Position is at the dataset level
     */
    DATASET,
    /**
     * Position is at the dataset attribute level
     */
    DATASET_ATTRIBUTE,
    /**
     * Position is at the series level
     */
    SERIES,
    /**
     * Position is at the series key level
     */
    SERIES_KEY,
    /**
     * Position is at the series key attribute level
     */
    SERIES_KEY_ATTRIBUTE,
    /**
     * Position is at the group level
     */
    GROUP,
    /**
     * Position is at the group key level
     */
    GROUP_KEY,
    /**
     * Position is at the group key attribute level
     */
    GROUP_KEY_ATTRIBUTE,
//	GROUP_SERIES_KEY,
//	GROUP_SERIES_KEY_ATTRIBUTE,
    /**
     * Position is at the observation level
     */
    OBSERVATION,
    /**
     * Position is at the observation attribute level
     */
    OBSERVATION_ATTRIBUTE,
    /**
     * Position is at the series level, when the series also contains the observation information (SDMX 2.1 only)
     */
    OBSERAVTION_AS_SERIES;
}
