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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.registry;

import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.DataSetReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.MetadataTargetKeyValuesBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.DataSetReferenceMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.MetadataTargetKeyValuesMutable;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Metadata target key values mutable.
 */
public class MetadataTargetKeyValuesMutableImpl extends KeyValuesMutableImpl implements MetadataTargetKeyValuesMutable {
    private static final long serialVersionUID = -5410698485721209102L;
    private List<StructureReferenceBean> objectReferences = new ArrayList<StructureReferenceBean>();
    private List<DataSetReferenceMutableBean> datasetReferences = new ArrayList<DataSetReferenceMutableBean>();

    /**
     * Instantiates a new Metadata target key values mutable.
     */
    public MetadataTargetKeyValuesMutableImpl() {
        super();
    }

    /**
     * Instantiates a new Metadata target key values mutable.
     *
     * @param createdFrom the created from
     */
    public MetadataTargetKeyValuesMutableImpl(MetadataTargetKeyValuesBean createdFrom) {
        super(createdFrom);
        for (CrossReferenceBean crossRef : createdFrom.getObjectReferences()) {
            this.objectReferences.add(new StructureReferenceBeanImpl(crossRef.getTargetUrn()));
        }
        for (DataSetReferenceBean dsRef : createdFrom.getDatasetReferences()) {
            this.datasetReferences.add(new DataSetReferenceMutableBeanImpl(dsRef));
        }
    }

    @Override
    public List<StructureReferenceBean> getObjectReferences() {
        return objectReferences;
    }

    @Override
    public void setObjectReferences(List<StructureReferenceBean> sRef) {
        this.objectReferences = sRef;
    }

    @Override
    public void addObjectReference(StructureReferenceBean sRef) {
        if (this.objectReferences == null) {
            this.objectReferences = new ArrayList<StructureReferenceBean>();
        }
        this.objectReferences.add(sRef);
    }

    @Override
    public List<DataSetReferenceMutableBean> getDatasetReferences() {
        return datasetReferences;
    }

    @Override
    public void setDatasetReferences(List<DataSetReferenceMutableBean> datasetReference) {
        this.datasetReferences = datasetReference;
    }

    @Override
    public void addDatasetReference(DataSetReferenceMutableBean reference) {
        if (this.datasetReferences == null) {
            this.datasetReferences = new ArrayList<DataSetReferenceMutableBean>();
        }
        this.datasetReferences.add(reference);
    }


}
