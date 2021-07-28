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

import org.sdmxsource.sdmx.api.constants.DATA_TYPE;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DataStructureSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DimensionSuperBean;
import org.sdmxsource.sdmx.dataparser.manager.SchemaGenerationManager;
import org.sdmxsource.sdmx.dataparser.transform.impl.SchemaGeneratorCompact;
import org.sdmxsource.sdmx.dataparser.transform.impl.SchemaGeneratorUtility;
import org.sdmxsource.util.io.StreamUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Set;


/**
 * The type Schema generation manager.
 */
public class SchemaGenerationManagerImpl implements SchemaGenerationManager {

    private final SchemaGeneratorCompact schemaGeneratorCompact;

    private final SchemaGeneratorUtility schemaGeneratorUtility;

    /**
     * Instantiates a new Schema generation manager.
     *
     * @param schemaGeneratorCompact the schema generator compact
     * @param schemaGeneratorUtility the schema generator utility
     */
    public SchemaGenerationManagerImpl(
            final SchemaGeneratorCompact schemaGeneratorCompact,
            final SchemaGeneratorUtility schemaGeneratorUtility) {
        this.schemaGeneratorCompact = schemaGeneratorCompact != null ? schemaGeneratorCompact : new SchemaGeneratorCompact();
        this.schemaGeneratorUtility = schemaGeneratorUtility;
    }

    @Override
    public void generateSchema(OutputStream out, DataStructureSuperBean dsd, String targetNamespace, DATA_TYPE schema, Map<String, Set<String>> constraintsMap) {
        SDMX_SCHEMA schemaVersion = schema.getSchemaVersion();

        switch (schema.getBaseDataFormat()) {
            case GENERIC:
                InputStream is;
                try {
                    switch (schemaVersion) {
                        case VERSION_ONE:
                            is = new FileInputStream("resources/xsd/v1_0/SDMXMessage.xsd");
                            break;
                        case VERSION_TWO:
                            is = new FileInputStream("resources/xsd/v2_0/SDMXMessage.xsd");
                            break;
                        case VERSION_TWO_POINT_ONE:
                            is = new FileInputStream("resources/xsd/v2_1/SDMXMessage.xsd");
                            break;

                        default:
                            throw new IllegalArgumentException("Generic format not supported in version : " + schemaVersion);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                StreamUtil.copyStream(is, out);
                break;
            case COMPACT:
                if (dsd == null) {
                    throw new SdmxException("Can not generate Schema. Data Structure was not provided");
                }
                schemaGeneratorCompact.transform(out, targetNamespace, schemaVersion, dsd, constraintsMap);
                break;
            case UTILITY:
                if (dsd == null) {
                    throw new SdmxException("Can not generate Schema. Data Structure was not provided");
                }
                //Only Version 2.0 of Utility Supported
                if (schemaVersion == SDMX_SCHEMA.VERSION_TWO) {
                    schemaGeneratorUtility.transform(out, targetNamespace, schemaVersion, dsd, constraintsMap);
                } else {
                    throw new IllegalArgumentException("Can not create utility schema in version '" + schemaVersion + "' only version 2.0 supported");
                }
                break;
            default:
                throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "Schema generation for format " + schema.getBaseDataFormat());
        }
    }

    @Override
    public void generateCrossSectionalSchema(OutputStream out, DataStructureSuperBean dsd, String targetNamespace, DATA_TYPE schema, String crossSectionalDimensionId, Map<String, Set<String>> constraintsMap) {
        SDMX_SCHEMA schemaVersion = schema.getSchemaVersion();
        validateDimensionAtObservation(dsd, crossSectionalDimensionId, schemaVersion);

        if (schemaVersion.equals(SDMX_SCHEMA.VERSION_ONE) ||
                schemaVersion.equals(SDMX_SCHEMA.VERSION_TWO) ||
                schemaVersion.equals(SDMX_SCHEMA.VERSION_TWO_POINT_ONE)) {
            schemaGeneratorCompact.transformCrossSectional(out, targetNamespace, schema.getSchemaVersion(), dsd, crossSectionalDimensionId, constraintsMap);
        } else {
            throw new IllegalArgumentException("Invalid DATA_TYPE supplied. Invalid value of: " + schema);
        }
    }

    private void validateDimensionAtObservation(DataStructureSuperBean dsd, String dimAtObs, SDMX_SCHEMA schemaVersion) {
        if (dimAtObs.equals("AllDimensions")) {
            if (schemaVersion != SDMX_SCHEMA.VERSION_TWO_POINT_ONE) {
                throw new SdmxSemmanticException("AllDimensions is only allowed for version 2.1 schemas");
            }
            return;
        }
        if (dsd.getDimensionById(dimAtObs) == null) {
            StringBuilder sb = new StringBuilder();
            String concat = "";
            for (DimensionSuperBean dim : dsd.getDimensions()) {
                sb.append(concat + dim.getId());
                concat = ", ";
            }
            throw new SdmxSemmanticException("Illegal dimension id '" + dimAtObs + "' for data structure: " + dsd + " allowable values are: " + sb.toString());
        }
    }
}
