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
package org.sdmxsource.sdmx.api.model.beans.base;

import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;

/**
 * Represents an SDMX Representation, this Bean references a TextFormat and / or a Codelist to define how the data should be
 * represented
 *
 * @author Matt Nelson
 */
public interface RepresentationBean extends SdmxStructureBean {

    /**
     * Returns the text format, which is typically present for an uncoded component (one which does not reference a codelist).
     * <p>
     * Returns null if there is no text format present
     *
     * @return text format
     */
    TextFormatBean getTextFormat();

    /**
     * Returns the codelist reference, returns null if there is no reference
     *
     * @return representation
     */
    CrossReferenceBean getRepresentation();
}
