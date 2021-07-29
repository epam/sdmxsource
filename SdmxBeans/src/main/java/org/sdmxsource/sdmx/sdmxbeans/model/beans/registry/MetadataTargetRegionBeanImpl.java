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
import org.sdmx.resources.sdmxml.schemas.v21.common.MetadataTargetRegionType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ContentConstraintBean;
import org.sdmxsource.sdmx.api.model.beans.registry.KeyValues;
import org.sdmxsource.sdmx.api.model.beans.registry.MetadataTargetKeyValuesBean;
import org.sdmxsource.sdmx.api.model.beans.registry.MetadataTargetRegionBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.KeyValuesMutable;
import org.sdmxsource.sdmx.api.model.mutable.registry.MetadataTargetKeyValuesMutable;
import org.sdmxsource.sdmx.api.model.mutable.registry.MetadataTargetRegionMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.SdmxStructureBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * The type Metadata target region bean.
 */
public class MetadataTargetRegionBeanImpl extends SdmxStructureBeanImpl implements MetadataTargetRegionBean {
    private static final long serialVersionUID = 2908027391753862424L;
    private boolean isInclude;
    private String report;
    private String metadataTarget;
    private List<MetadataTargetKeyValuesBean> key = new ArrayList<MetadataTargetKeyValuesBean>();
    private List<KeyValues> attributes = new ArrayList<KeyValues>();


    /**
     * Instantiates a new Metadata target region bean.
     *
     * @param mutableBean the mutable bean
     * @param parent      the parent
     */
    public MetadataTargetRegionBeanImpl(MetadataTargetRegionMutableBean mutableBean, ContentConstraintBean parent) {
        super(mutableBean, parent);
        this.isInclude = mutableBean.isInclude();
        this.report = mutableBean.getReport();
        this.metadataTarget = mutableBean.getMetadataTarget();
        if (mutableBean.getKey() != null) {
            for (MetadataTargetKeyValuesMutable currentMetadataTarget : mutableBean.getKey()) {
                this.key.add(new MetadataTargetKeyValuesBeanImpl(currentMetadataTarget, this));
            }
        }
        if (mutableBean.getAttributes() != null) {
            for (KeyValuesMutable currentKeyValue : mutableBean.getAttributes()) {
                this.attributes.add(new KeyValuesImpl(currentKeyValue, this));
            }
        }
        try {
            validate();
        } catch (SdmxSemmanticException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
    }

    /**
     * Instantiates a new Metadata target region bean.
     *
     * @param type   the type
     * @param parent the parent
     */
    public MetadataTargetRegionBeanImpl(MetadataTargetRegionType type, ContentConstraintBean parent) {
        super(SDMX_STRUCTURE_TYPE.METADATA_TARGET_REGION, parent);
        this.isInclude = type.getInclude();
        this.report = type.getReport();
        this.metadataTarget = type.getMetadataTarget();
        if (type.getKeyValueList() != null) {
            for (ComponentValueSetType cv : type.getKeyValueList()) {
                this.key.add(new MetadataTargetKeyValuesBeanImpl(cv, this));
            }
        }
        if (type.getKeyValueList() != null) {
            for (ComponentValueSetType cv : type.getAttributeList()) {
                this.attributes.add(new KeyValuesImpl(cv, this));
            }
        }
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
        if (!ObjectUtil.validString(report)) {
            throw new SdmxSemmanticException("Metadata Target Region missing mandatory 'report' identifier");
        }
        if (!ObjectUtil.validString(metadataTarget)) {
            throw new SdmxSemmanticException("Metadata Target Region missing mandatory 'metadata target' identifier");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////DEEP EQUALS							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean deepEquals(SDMXBean bean, boolean includeFinalProperties) {
        if (bean == null) {
            return false;
        }
        if (bean.getStructureType() == this.getStructureType()) {
            MetadataTargetRegionBean that = (MetadataTargetRegionBean) bean;
            if (!super.equivalent(this.attributes, that.getAttributes(), includeFinalProperties)) {
                return false;
            }
            if (!super.equivalent(this.key, that.getKey(), includeFinalProperties)) {
                return false;
            }
            if (!ObjectUtil.equivalent(this.metadataTarget, that.getMetadataTarget())) {
                return false;
            }
            if (!ObjectUtil.equivalent(this.report, that.getReport())) {
                return false;
            }
            if (this.isInclude != that.isInclude()) {
                return false;
            }
        }
        return false;

    }


    @Override
    public boolean isInclude() {
        return isInclude;
    }

    @Override
    public String getReport() {
        return report;
    }

    @Override
    public String getMetadataTarget() {
        return metadataTarget;
    }

    @Override
    public List<MetadataTargetKeyValuesBean> getKey() {
        return new ArrayList<MetadataTargetKeyValuesBean>(key);
    }

    @Override
    public List<KeyValues> getAttributes() {
        return new ArrayList<KeyValues>(attributes);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES		                     //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(key, composites);
        super.addToCompositeSet(attributes, composites);
        return composites;
    }
}
