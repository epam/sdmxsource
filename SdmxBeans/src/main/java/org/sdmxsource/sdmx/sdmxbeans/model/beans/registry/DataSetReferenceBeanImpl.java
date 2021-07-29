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

import org.sdmx.resources.sdmxml.schemas.v21.common.SetReferenceType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.DataSetReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.MetadataTargetKeyValuesBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.DataSetReferenceMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.SdmxStructureBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.util.RefUtil;
import org.sdmxsource.sdmx.util.beans.reference.CrossReferenceBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * The type Data set reference bean.
 */
public class DataSetReferenceBeanImpl extends SdmxStructureBeanImpl implements DataSetReferenceBean {
    private static final long serialVersionUID = -2093848883988082080L;
    private String datasetId;
    private CrossReferenceBean dataProviderReference;


    /**
     * Instantiates a new Data set reference bean.
     *
     * @param mutableBean the mutable bean
     * @param parent      the parent
     */
    public DataSetReferenceBeanImpl(DataSetReferenceMutableBean mutableBean, MetadataTargetKeyValuesBean parent) {
        super(mutableBean, parent);
        this.datasetId = mutableBean.getDatasetId();
        if (mutableBean.getDataProviderReference() != null) {
            this.dataProviderReference = new CrossReferenceBeanImpl(this, mutableBean.getDataProviderReference());
            try {
                validate();
            } catch (SdmxSemmanticException e) {
                throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
            }
        }
    }

    /**
     * Instantiates a new Data set reference bean.
     *
     * @param sRefType the s ref type
     * @param parent   the parent
     */
    public DataSetReferenceBeanImpl(SetReferenceType sRefType, MetadataTargetKeyValuesBean parent) {
        super(SDMX_STRUCTURE_TYPE.DATASET_REFERENCE, parent);
        this.datasetId = sRefType.getID();
        this.dataProviderReference = RefUtil.createReference(this, sRefType.getDataProvider());
        try {
            validate();
        } catch (SdmxSemmanticException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATE								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() {
        if (!ObjectUtil.validString(datasetId)) {
            throw new SdmxSemmanticException("Dataset Reference missing mandatory 'id' identifier");
        }
        if (dataProviderReference == null) {
            throw new SdmxSemmanticException("Dataset Reference missing mandatory 'data provider reference'");
        }
    }

    @Override
    public boolean deepEquals(SDMXBean bean, boolean includeFinalProperties) {
        if (bean == null) {
            return false;
        }
        if (bean instanceof DataSetReferenceBean) {
            DataSetReferenceBean that = (DataSetReferenceBean) bean;
            if (!ObjectUtil.equivalent(this.getDataProviderReference(), that.getDataProviderReference())) {
                return false;
            }
            if (!ObjectUtil.equivalent(this.getDatasetId(), that.getDatasetId())) {
                return false;
            }
        }
        return false;
    }

    @Override
    protected Set<CrossReferenceBean> getCrossReferenceInternal() {
        Set<CrossReferenceBean> references = new HashSet<CrossReferenceBean>();
        if (dataProviderReference != null) {
            references.add(dataProviderReference);
        }
        return references;
    }

    @Override
    public String getDatasetId() {
        return datasetId;
    }

    @Override
    public CrossReferenceBean getDataProviderReference() {
        return dataProviderReference;
    }
}
