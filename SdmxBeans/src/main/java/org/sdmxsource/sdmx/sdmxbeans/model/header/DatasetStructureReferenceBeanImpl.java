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
package org.sdmxsource.sdmx.sdmxbeans.model.header;

import org.sdmx.resources.sdmxml.schemas.v21.common.PayloadStructureType;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.header.DatasetStructureReferenceBean;
import org.sdmxsource.sdmx.sdmxbeans.util.RefUtil;
import org.sdmxsource.util.ObjectUtil;

import java.io.Serializable;
import java.util.UUID;


/**
 * The type Dataset structure reference bean.
 */
public class DatasetStructureReferenceBeanImpl implements DatasetStructureReferenceBean, Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private StructureReferenceBean structureReference;
    private String serviceURL;
    private String structureURL;
    private String dimensionAtObservation = DimensionBean.TIME_DIMENSION_FIXED_ID;

    /**
     * Minimal Constructor
     *
     * @param structureReference the structure reference
     */
    public DatasetStructureReferenceBeanImpl(StructureReferenceBean structureReference) {
        this.structureReference = structureReference;
        validate();
    }

    /**
     * Instantiates a new Dataset structure reference bean.
     *
     * @param id                     the id
     * @param structureReference     the structure reference
     * @param serviceURL             the service url
     * @param structureURL           the structure url
     * @param dimensionAtObservation the dimension at observation
     */
    public DatasetStructureReferenceBeanImpl(String id,
                                             StructureReferenceBean structureReference,
                                             String serviceURL,
                                             String structureURL,
                                             String dimensionAtObservation) {
        this.id = id;
        this.structureReference = structureReference;
        this.serviceURL = serviceURL;
        this.structureURL = structureURL;
        if (ObjectUtil.validString(dimensionAtObservation)) {
            this.dimensionAtObservation = dimensionAtObservation;
        }
        validate();
    }

//	///////////////////////////////////////////////////////////////////////////////////////////////////
//	////////////BUILD FROM V2.1 XMLStreamReader	///////////////////////////////////////////////////////
//	///////////////////////////////////////////////////////////////////////////////////////////////////
//	public DatasetStructureReferenceBeanImpl(XMLStreamReader reader) {
//		
//	}

    /**
     * Instantiates a new Dataset structure reference bean.
     *
     * @param payloadSt the payload st
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA			///////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public DatasetStructureReferenceBeanImpl(PayloadStructureType payloadSt) {
        if (payloadSt.getProvisionAgrement() != null) {
            this.structureReference = RefUtil.createReference(payloadSt.getProvisionAgrement());
        } else if (payloadSt.getStructureUsage() != null) {
            this.structureReference = RefUtil.createReference(payloadSt.getStructureUsage());
        } else if (payloadSt.getStructure() != null) {
            this.structureReference = RefUtil.createReference(payloadSt.getStructure());
        }
        this.id = payloadSt.getStructureID();
        this.serviceURL = payloadSt.getServiceURL();
        this.structureURL = payloadSt.getStructureURL();
        this.dimensionAtObservation = payloadSt.getDimensionAtObservation();
        validate();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS							///////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getServiceURL() {
        return serviceURL;
    }

    @Override
    public String getStructureURL() {
        return structureURL;
    }

    @Override
    public String getDimensionAtObservation() {
        return dimensionAtObservation;
    }

    @Override
    public StructureReferenceBean getStructureReference() {
        return structureReference;
    }

    @Override
    public boolean isTimeSeries() {
        return dimensionAtObservation.equals(DimensionBean.TIME_DIMENSION_FIXED_ID);
    }

    /**
     * Validate.
     *
     * @throws SdmxSemmanticException the sdmx semmantic exception
     */
/////////////////////////////////////////////////////////////////////////////////////////////////
    //////////VALIDATE						/////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////
    public void validate() throws SdmxSemmanticException {
        if (!ObjectUtil.validString(id)) {
            this.id = UUID.randomUUID().toString();
        }
        if (structureReference == null) {
            throw new SdmxSemmanticException("Header 'Structure' missing Structure Reference");
        }
        if (dimensionAtObservation == null) {
            dimensionAtObservation = DimensionBean.TIME_DIMENSION_FIXED_ID;
        }
    }
}
