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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.registry;

import org.sdmx.resources.sdmxml.schemas.v21.common.ComponentValueSetType;
import org.sdmx.resources.sdmxml.schemas.v21.common.ObjectReferenceType;
import org.sdmx.resources.sdmxml.schemas.v21.common.SetReferenceType;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.DataSetReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.MetadataTargetKeyValuesBean;
import org.sdmxsource.sdmx.api.model.beans.registry.MetadataTargetRegionBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.DataSetReferenceMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.MetadataTargetKeyValuesMutable;
import org.sdmxsource.sdmx.sdmxbeans.util.RefUtil;
import org.sdmxsource.sdmx.util.beans.reference.CrossReferenceBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The type Metadata target key values bean.
 */
public class MetadataTargetKeyValuesBeanImpl extends KeyValuesImpl implements MetadataTargetKeyValuesBean {
    private static final long serialVersionUID = 423683652240779717L;
    private List<CrossReferenceBean> objectReferences = new ArrayList<CrossReferenceBean>();
    private List<DataSetReferenceBean> datasetReferences = new ArrayList<DataSetReferenceBean>();


    /**
     * Instantiates a new Metadata target key values bean.
     *
     * @param mutable the mutable
     * @param parent  the parent
     */
    public MetadataTargetKeyValuesBeanImpl(MetadataTargetKeyValuesMutable mutable, MetadataTargetRegionBean parent) {
        super(mutable, parent);
        if (mutable.getObjectReferences() != null) {
            for (StructureReferenceBean sRef : mutable.getObjectReferences()) {
                this.objectReferences.add(new CrossReferenceBeanImpl(this, sRef));
            }
        }
        if (mutable.getDatasetReferences() != null) {
            for (DataSetReferenceMutableBean currentRef : mutable.getDatasetReferences()) {
                this.datasetReferences.add(new DataSetReferenceBeanImpl(currentRef, this));
            }
        }
    }

    /**
     * Instantiates a new Metadata target key values bean.
     *
     * @param keyValueType the key value type
     * @param parent       the parent
     */
    public MetadataTargetKeyValuesBeanImpl(ComponentValueSetType keyValueType, MetadataTargetRegionBean parent) {
        super(keyValueType, parent);
        if (keyValueType.getDataSetList() != null) {
            for (SetReferenceType currentDatasetRef : keyValueType.getDataSetList()) {
                this.datasetReferences.add(new DataSetReferenceBeanImpl(currentDatasetRef, this));
            }
        }
        if (keyValueType.getObjectList() != null) {
            for (ObjectReferenceType currentRef : keyValueType.getObjectList()) {
                this.objectReferences.add(RefUtil.createReference(this, currentRef));
            }
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(datasetReferences, composites);
        return composites;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////DEEP EQUALS							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean deepEquals(SDMXBean bean, boolean includeFinalProperties) {
        if (bean == null) {
            return false;
        }
        if (bean instanceof MetadataTargetKeyValuesBean) {
            MetadataTargetKeyValuesBean that = (MetadataTargetKeyValuesBean) bean;
            if (!ObjectUtil.equivalentCollection(objectReferences, that.getObjectReferences())) {
                return false;
            }
            if (!super.equivalent(datasetReferences, that.getDatasetReferences(), includeFinalProperties)) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    protected Set<CrossReferenceBean> getCrossReferenceInternal() {
        Set<CrossReferenceBean> references = new HashSet<CrossReferenceBean>();
        if (objectReferences != null) {
            references.addAll(objectReferences);
        }
        return references;
    }

    @Override
    public List<CrossReferenceBean> getObjectReferences() {
        return new ArrayList<CrossReferenceBean>(objectReferences);
    }

    @Override
    public List<DataSetReferenceBean> getDatasetReferences() {
        return new ArrayList<DataSetReferenceBean>(datasetReferences);
    }
}
