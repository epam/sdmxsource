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
package org.sdmxsource.sdmx.api.manager.output;

import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.format.StructureFormat;
import org.sdmxsource.sdmx.api.model.header.HeaderBean;

import java.io.OutputStream;


/**
 * The structure writing manager is responsible for writing SdmxBean objects to an output stream as SDMX / EDI documents.
 * <p>
 * The Interface gives options for the type of SDMX document to be output
 *
 * @author Matt Nelson
 */
public interface StructureWriterManager {


    /**
     * Writes the contents of the beans out to the output stream in the format specified.
     * <p>
     * Will write the header information contained within the SdmxBeans container if there is a header present.  If the header
     * is not present a default header will be created
     *
     * @param beans        the beans to write to the output stream
     * @param outputFormat the output format of the message (required)
     * @param out          - the stream to write to, the stream is closed on completion, this can be null if not required (i.e the outputFormat may contain a writer)
     */
    void writeStructures(SdmxBeans beans, StructureFormat outputFormat, OutputStream out);

    /**
     * Writes the contents of the bean out to the output stream in the version specified.
     * <p>
     *
     * @param bean         the bean
     * @param header       can be null, if null will create a default header
     * @param outputFormat the output format of the message (required)
     * @param out          - the stream to write to, the stream is NOT closed on completion, this can be null if not required (i.e the outputFormat may contain a writer)
     */
    void writeStructure(MaintainableBean bean, HeaderBean header, StructureFormat outputFormat, OutputStream out);

}
