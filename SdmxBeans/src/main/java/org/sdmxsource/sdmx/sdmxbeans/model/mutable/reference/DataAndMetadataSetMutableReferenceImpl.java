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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.reference;

import org.sdmxsource.sdmx.api.model.beans.reference.DataAndMetadataSetReference;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.reference.DataAndMetadataSetMutableReference;

/**
 * The type Data and metadata set mutable reference.
 */
public class DataAndMetadataSetMutableReferenceImpl implements DataAndMetadataSetMutableReference {

    private StructureReferenceBean dataSetReference = null;
    private String setId = null;
    private boolean isDataSetReference = false;

    /**
     * Instantiates a new Data and metadata set mutable reference.
     */
    public DataAndMetadataSetMutableReferenceImpl() {
        super();
    }

    /**
     * Instantiates a new Data and metadata set mutable reference.
     *
     * @param immutable the immutable
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM IMMUTABLE BEAN				 //////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public DataAndMetadataSetMutableReferenceImpl(DataAndMetadataSetReference immutable) {
        super();
        if (immutable.getDataSetReference() != null) {
            this.dataSetReference = immutable.getDataSetReference().createMutableInstance();
        }
        this.setId = immutable.getSetId();
        this.isDataSetReference = immutable.isDataSetReference();
    }

    @Override
    public StructureReferenceBean getDataSetReference() {
        return this.dataSetReference;
    }

    @Override
    public String getSetId() {
        return this.setId;
    }

    @Override
    public void setSetId(String id) {
        this.setId = id;
    }

    @Override
    public boolean isDataSetReference() {
        return this.isDataSetReference;
    }

    @Override
    public void setDataSetReference(StructureReferenceBean bean) {
        this.dataSetReference = bean;
    }

    @Override
    public void setIsDataSetReference(boolean isRef) {
        this.isDataSetReference = isRef;
    }
}
