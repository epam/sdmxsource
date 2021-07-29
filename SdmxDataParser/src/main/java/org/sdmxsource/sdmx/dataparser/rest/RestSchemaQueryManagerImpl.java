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
package org.sdmxsource.sdmx.dataparser.rest;

import org.sdmxsource.sdmx.api.exception.SdmxNoResultsException;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.manager.output.SchemaWriterManager;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxSuperBeanRetrievalManager;
import org.sdmxsource.sdmx.api.manager.retrieval.rest.RestSchemaQueryManager;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.format.SchemaFormat;
import org.sdmxsource.sdmx.api.model.query.RESTSchemaQuery;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DataStructureSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DataflowSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.registry.ProvisionAgreementSuperBean;

import java.io.OutputStream;

/**
 * The type Rest schema query manager.
 */
public class RestSchemaQueryManagerImpl implements RestSchemaQueryManager {

    private SdmxSuperBeanRetrievalManager superBeanRetrievalManager;

    private SchemaWriterManager schemaWriterManager;

    @Override
    public void writeSchema(RESTSchemaQuery schemaQuery, SchemaFormat format, OutputStream out) {
        DataStructureSuperBean dsd = null;
        StructureReferenceBean sRef = schemaQuery.getReference();

        MaintainableRefBean mRef = sRef.getMaintainableReference();

        //TODO GET CONSTRAINTS
        switch (sRef.getTargetReference()) {
            case DATAFLOW:
                DataflowSuperBean flow = superBeanRetrievalManager.getDataflowSuperBean(mRef);
                if (flow == null) {
                    throw new SdmxNoResultsException("No dataflow found for argument:" + mRef);
                }
                dsd = flow.getDataStructure();
                break;
            case DSD:
                dsd = superBeanRetrievalManager.getDataStructureSuperBean(mRef);

                if (dsd == null) {
                    throw new SdmxNoResultsException("No data structure found for argument:" + mRef);
                }
                break;
            case PROVISION_AGREEMENT:
                ProvisionAgreementSuperBean provision = superBeanRetrievalManager.getProvisionAgreementSuperBean(mRef);
                if (provision == null) {
                    throw new SdmxNoResultsException("No provision found for argument:" + mRef);
                }
                dsd = provision.getDataflowSuperBean().getDataStructure();
                break;
            default:
                throw new SdmxNotImplementedException("Schema query by " + sRef.getTargetReference().getType());
        }

        if (DimensionBean.TIME_DIMENSION_FIXED_ID.equals(schemaQuery.getDimAtObs())) {
            schemaWriterManager.generateSchema(out, dsd, format, null, null);
        } else {
            schemaWriterManager.generateCrossSectionalSchema(out, dsd, format, null, schemaQuery.getDimAtObs(), null);
        }
    }

    /**
     * Sets super bean retrieval manager.
     *
     * @param superBeanRetrievalManager the super bean retrieval manager
     */
    public void setSuperBeanRetrievalManager(SdmxSuperBeanRetrievalManager superBeanRetrievalManager) {
        this.superBeanRetrievalManager = superBeanRetrievalManager;
    }
}
