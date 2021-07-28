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
 * Defines all the attachment levels of attributes in a key family
 *
 * @author Matt Nelson
 */
public enum ATTRIBUTE_ATTACHMENT_LEVEL {
    /**
     * Attribute is relevant to the entire dataset
     */
    DATA_SET,
    /**
     * Attribute is relevant to a Group, in which case the group identifier is needed to define the group the attribute attaches to
     */
    GROUP,
    /**
     * Attribute is relevant to a Group of Dimensions (no formally specified in a Group)
     */
    DIMENSION_GROUP,
    /**
     * Attribute is relevant to a single Observation
     */
    OBSERVATION;
}
