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

import org.apache.log4j.Logger;
import org.sdmxsource.sdmx.api.constants.DATA_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.exception.SdmxSyntaxException;
import org.sdmxsource.sdmx.api.factory.WriteableDataLocationFactory;
import org.sdmxsource.sdmx.api.model.data.DataFormat;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DataStructureSuperBean;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.api.util.WriteableDataLocation;
import org.sdmxsource.sdmx.dataparser.manager.DataInformationManager;
import org.sdmxsource.sdmx.dataparser.manager.SchemaGenerationManager;
import org.sdmxsource.sdmx.dataparser.manager.SchemaValidationManager;
import org.sdmxsource.springutil.xml.XMLParser;

import java.io.OutputStream;

/**
 * Validates data from a DataStructure against a Generated Schema
 */
public class SchemaDataValidationManager implements SchemaValidationManager {
    private static Logger LOG = Logger.getLogger(SchemaDataValidationManager.class);

    private final DataInformationManager dataInformationManager;

    private final SchemaGenerationManager schemaGenerationManager;

    private final WriteableDataLocationFactory writeableDataLocationFactory;

    /**
     * Instantiates a new Schema data validation manager.
     *
     * @param dataInformationManager       the data information manager
     * @param schemaGenerationManager      the schema generation manager
     * @param writeableDataLocationFactory the writeable data location factory
     */
    public SchemaDataValidationManager(
            final DataInformationManager dataInformationManager,
            final SchemaGenerationManager schemaGenerationManager,
            final WriteableDataLocationFactory writeableDataLocationFactory) {
        this.dataInformationManager = dataInformationManager;
        this.schemaGenerationManager = schemaGenerationManager;
        this.writeableDataLocationFactory = writeableDataLocationFactory;
    }

    @Override
    //TODO Move the validation DataFormat into an engine which can be plugged in?
    public void validateDatasetAgainstSchema(ReadableDataLocation dataset, DataStructureSuperBean dsd) throws SdmxSemmanticException, SdmxSyntaxException {
        LOG.info("Validating dataset against schema");
        DataFormat dataFormat = dataInformationManager.getDataType(dataset);
        if (dataFormat.getSdmxDataFormat() == null) {
            throw new IllegalArgumentException("Could not determine datatype of data.  Expecting an SDMX message");
        }

        //Only validate if it is an SDMX data format
        if (dataFormat.getSdmxDataFormat() != null) {
            DATA_TYPE dataType = dataFormat.getSdmxDataFormat();
            if (dataType == DATA_TYPE.GENERIC_1_0 || dataType == DATA_TYPE.GENERIC_2_0) {
                LOG.debug("Validate against Generic XSD");
                XMLParser.validateXML(dataset, dataType.getSchemaVersion());
            } else {
                if (dsd == null) {
                    throw new IllegalArgumentException("Can not generate a Schema, no DataStructure was supplied");
                }
                WriteableDataLocation schemaLocation = writeableDataLocationFactory.getTemporaryWriteableDataLocation();
                OutputStream out = null;
                try {
                    out = schemaLocation.getOutputStream();
                    String targetNamespace = dataInformationManager.getTargetNamespace(dataset);
                    schemaGenerationManager.generateSchema(out, dsd, targetNamespace, dataType, null);
                    //GET THE TARGET NAMESPACE OF THE DATA XML FILE
                    LOG.debug("schema generated, perform validation");
                    XMLParser.validateXML(dataset, dataType.getSchemaVersion(), schemaLocation);
                } finally {
                    // Tidy up
                    schemaLocation.close();
                }
            }
            LOG.info("Dataset is valid against schema");
        }
    }
}
