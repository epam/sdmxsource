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
package org.sdmxsource.sdmx.ediparser.manager;

import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.ediparser.model.EDIWorkspace;

import java.io.OutputStream;


/**
 * The interface Edi parse manager.
 */
public interface EdiParseManager {

    /**
     * Writes the Sdmx Beans contents out as EDI-TS to the output stream.
     * <p>
     * Note - this will only write out codelists, concepts and key families
     *
     * @param beans the beans
     * @param out   the out
     */
    void writeToEDI(SdmxBeans beans, OutputStream out);

    /**
     * Processes an EDI message and returns a workspace containing the SDMX structures and data that were contained in the message
     *
     * @param ediMessageLocation the edi message location
     * @return edi workspace
     */
    EDIWorkspace parseEDIMessage(ReadableDataLocation ediMessageLocation);
}
