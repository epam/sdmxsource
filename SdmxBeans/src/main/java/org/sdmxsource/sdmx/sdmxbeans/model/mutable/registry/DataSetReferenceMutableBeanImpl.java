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

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.DataSetReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.DataSetReferenceMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.MutableBeanImpl;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;

/**
 * The type Data set reference mutable bean.
 */
public class DataSetReferenceMutableBeanImpl extends MutableBeanImpl implements DataSetReferenceMutableBean {
    private static final long serialVersionUID = -5015097893049847027L;
    private String datasetId;
    private StructureReferenceBean dataProviderRef;

    /**
     * Instantiates a new Data set reference mutable bean.
     */
    public DataSetReferenceMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.DATASET_REFERENCE);
    }

    /**
     * Instantiates a new Data set reference mutable bean.
     *
     * @param createdFrom the created from
     */
    public DataSetReferenceMutableBeanImpl(DataSetReferenceBean createdFrom) {
        super(createdFrom);
        if (createdFrom.getDataProviderReference() != null) {
            this.dataProviderRef = new StructureReferenceBeanImpl(createdFrom.getDataProviderReference().getTargetUrn());
        }
        this.datasetId = createdFrom.getDatasetId();
    }

    @Override
    public String getDatasetId() {
        return datasetId;
    }

    @Override
    public void setDatasetId(String datasetId) {
        this.datasetId = datasetId;
    }

    @Override
    public StructureReferenceBean getDataProviderReference() {
        return dataProviderRef;
    }

    @Override
    public void setDataProviderReference(StructureReferenceBean sRef) {
        this.dataProviderRef = sRef;
    }
}
