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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.reference;

import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.DataAndMetadataSetReference;
import org.sdmxsource.sdmx.api.model.mutable.reference.DataAndMetadataSetMutableReference;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.reference.DataAndMetadataSetMutableReferenceImpl;
import org.sdmxsource.util.ObjectUtil;


/**
 * The type Data and metadata set reference.
 */
public class DataAndMetadataSetReferenceImpl implements DataAndMetadataSetReference {
    private CrossReferenceBean crossReferenceBean;
    private String id;
    private boolean isDataSetReference;

    /**
     * Instantiates a new Data and metadata set reference.
     *
     * @param mutable the mutable
     */
    public DataAndMetadataSetReferenceImpl(DataAndMetadataSetMutableReference mutable) {
        if (mutable.getDataSetReference() != null) {
            //	this.crossReferenceBean = new CrossReferenceBeanImpl(this, mutable.getDataSetReference());
        }
        this.id = mutable.getSetId();
        this.isDataSetReference = mutable.isDataSetReference();
        if (crossReferenceBean == null) {
            throw new SdmxSemmanticException("DataAndMetadataSetReferenceImpl expects crossReferenceBean (null was provided)");
        }
        if (id == null) {
            throw new SdmxSemmanticException("DataAndMetadataSetReferenceImpl expects id (null was provided)");
        }
    }

    /**
     * Instantiates a new Data and metadata set reference.
     *
     * @param crossReferenceBean the cross reference bean
     * @param id                 the id
     * @param isDataSetReference the is data set reference
     */
    public DataAndMetadataSetReferenceImpl(CrossReferenceBean crossReferenceBean, String id, boolean isDataSetReference) {
        this.crossReferenceBean = crossReferenceBean;
        this.id = id;
        this.isDataSetReference = isDataSetReference;
        if (crossReferenceBean == null) {
            throw new SdmxSemmanticException("DataAndMetadataSetReferenceImpl expects crossReferenceBean (null was provided)");
        }
        if (id == null) {
            throw new SdmxSemmanticException("DataAndMetadataSetReferenceImpl expects id (null was provided)");
        }
    }

    @Override
    public CrossReferenceBean getDataSetReference() {
        return crossReferenceBean;
    }

    @Override
    public String getSetId() {
        return id;
    }

    @Override
    public boolean isDataSetReference() {
        return isDataSetReference;
    }

    @Override
    public DataAndMetadataSetMutableReference createMutableInstance() {
        return new DataAndMetadataSetMutableReferenceImpl(this);
    }

    @Override
    public int hashCode() {
        return (crossReferenceBean.getTargetUrn() + id + isDataSetReference).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DataAndMetadataSetReference) {
            DataAndMetadataSetReference that = (DataAndMetadataSetReference) obj;
            if (!ObjectUtil.equivalent(that.getDataSetReference(), this.getDataSetReference())) {
                return false;
            }
            if (!ObjectUtil.equivalent(that.getSetId(), this.getSetId())) {
                return false;
            }
            if (isDataSetReference != that.isDataSetReference()) {
                return false;
            }
            return true;
        }
        return false;
    }
}
