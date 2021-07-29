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

import org.sdmx.resources.sdmxml.schemas.v21.common.DistinctKeyType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.ConstraintType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.DataKeySetType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.MetadataKeySetType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ConstraintAttachmentBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ConstraintBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ConstraintDataKeySetBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.ConstraintMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.MaintainableBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.net.URL;
import java.util.List;
import java.util.Set;


/**
 * The type Constraint bean.
 */
public abstract class ConstraintBeanImpl extends MaintainableBeanImpl implements ConstraintBean {
    private static final long serialVersionUID = 1L;
    private ConstraintDataKeySetBean includedSeriesKeys;
    private ConstraintDataKeySetBean excludedSeriesKeys;


    private ConstraintDataKeySetBean includedMetadataKeys;
    private ConstraintDataKeySetBean excludedMetadataKeys;

    private ConstraintAttachmentBean constraintAttachment;

    /**
     * Instantiates a new Constraint bean.
     *
     * @param bean           the bean
     * @param actualLocation the actual location
     * @param isServiceUrl   the is service url
     */
    public ConstraintBeanImpl(MaintainableBean bean, URL actualLocation, boolean isServiceUrl) {
        super(bean, actualLocation, isServiceUrl);
    }

    /**
     * Instantiates a new Constraint bean.
     *
     * @param bean the bean
     */
    public ConstraintBeanImpl(ConstraintMutableBean bean) {
        super(bean);
        if (this.getMaintainableParent().isExternalReference().isTrue()) {
            return;
        }
        if (bean.getIncludedSeriesKeys() != null &&
                bean.getIncludedSeriesKeys().getConstrainedDataKeys() != null &&
                bean.getIncludedSeriesKeys().getConstrainedDataKeys().size() > 0) {
            this.includedSeriesKeys = new ConstraintDataKeySetBeanImpl(bean.getIncludedSeriesKeys(), this);
        }
        if (bean.getExcludedSeriesKeys() != null &&
                bean.getExcludedSeriesKeys().getConstrainedDataKeys() != null &&
                bean.getExcludedSeriesKeys().getConstrainedDataKeys().size() > 0) {
            this.excludedSeriesKeys = new ConstraintDataKeySetBeanImpl(bean.getExcludedSeriesKeys(), this);
        }
        if (bean.getIncludedMetadataKeys() != null &&
                bean.getIncludedMetadataKeys().getConstrainedDataKeys() != null &&
                bean.getIncludedMetadataKeys().getConstrainedDataKeys().size() > 0) {
            this.includedMetadataKeys = new ConstraintDataKeySetBeanImpl(bean.getIncludedSeriesKeys(), this);
        }
        if (bean.getExcludedMetadataKeys() != null &&
                bean.getExcludedMetadataKeys().getConstrainedDataKeys() != null &&
                bean.getExcludedMetadataKeys().getConstrainedDataKeys().size() > 0) {
            this.excludedMetadataKeys = new ConstraintDataKeySetBeanImpl(bean.getExcludedSeriesKeys(), this);
        }
        if (bean.getConstraintAttachment() != null) {
            this.constraintAttachment = new ConstraintAttachmentBeanImpl(bean.getConstraintAttachment(), this);
        }
        validate();
    }

    /**
     * Instantiates a new Constraint bean.
     *
     * @param createdFrom   the created from
     * @param structureType the structure type
     */
    public ConstraintBeanImpl(ConstraintType createdFrom, SDMX_STRUCTURE_TYPE structureType) {
        super(createdFrom, structureType);
        if (ObjectUtil.validCollection(createdFrom.getDataKeySetList())) {
            DataKeySetType includedKeySet = DataKeySetType.Factory.newInstance();
            DataKeySetType excludedKeySet = DataKeySetType.Factory.newInstance();
            populateKeySets(createdFrom.getDataKeySetList(), includedKeySet, excludedKeySet);

            if (includedKeySet.getKeyList().size() > 0) {
                this.includedSeriesKeys = new ConstraintDataKeySetBeanImpl(includedKeySet, this);
            }
            if (excludedKeySet.getKeyList().size() > 0) {
                this.excludedSeriesKeys = new ConstraintDataKeySetBeanImpl(excludedKeySet, this);
            }
        }

        if (ObjectUtil.validCollection(createdFrom.getMetadataKeySetList())) {
            DataKeySetType includedMetadataKeySet = DataKeySetType.Factory.newInstance();
            DataKeySetType excludedMetadataKeySet = DataKeySetType.Factory.newInstance();
            populateMetadataKeySets(createdFrom.getMetadataKeySetList(), includedMetadataKeySet, excludedMetadataKeySet);

            if (includedMetadataKeySet.getKeyList().size() > 0) {
                this.includedMetadataKeys = new ConstraintDataKeySetBeanImpl(includedMetadataKeySet, this);
            }
            if (excludedMetadataKeySet.getKeyList().size() > 0) {
                this.excludedMetadataKeys = new ConstraintDataKeySetBeanImpl(excludedMetadataKeySet, this);
            }
        }
        if (createdFrom.getConstraintAttachment() != null) {
            this.constraintAttachment = new ConstraintAttachmentBeanImpl(createdFrom.getConstraintAttachment(), this);
        }
        validate();
    }

    private void populateMetadataKeySets(List<MetadataKeySetType> allKeys, DataKeySetType includedKeySet, DataKeySetType excludedKeySet) {
        for (MetadataKeySetType currentDataKeySet : allKeys) {
            if (currentDataKeySet.getIsIncluded()) {
                for (DistinctKeyType currentKey : currentDataKeySet.getKeyList()) {
                    if (currentKey.isSetInclude() && currentKey.getInclude() == true) {
                        includedKeySet.getKeyList().add(currentKey);
                    } else {
                        excludedKeySet.getKeyList().add(currentKey);
                    }
                }
            } else {
                for (DistinctKeyType currentKey : currentDataKeySet.getKeyList()) {
                    if (currentKey.isSetInclude() && currentKey.getInclude() == true) {
                        excludedKeySet.getKeyList().add(currentKey);
                    } else {
                        includedKeySet.getKeyList().add(currentKey);
                    }
                }
            }
        }
    }

    private void populateKeySets(List<DataKeySetType> allKeys, DataKeySetType includedKeySet, DataKeySetType excludedKeySet) {
        for (DataKeySetType currentDataKeySet : allKeys) {
            if (currentDataKeySet.getIsIncluded()) {
                //INCLUDED
                for (DistinctKeyType currentKey : currentDataKeySet.getKeyList()) {
                    if (currentKey.isSetInclude() && currentKey.getInclude() == false) {
                        //EXCLUDED (isInclude=false)
                        excludedKeySet.getKeyList().add(currentKey);
                    } else {
                        //INCLUDED
                        includedKeySet.getKeyList().add(currentKey);
                    }
                }
            } else {
                //EXCLUDED
                for (DistinctKeyType currentKey : currentDataKeySet.getKeyList()) {
                    if (currentKey.isSetInclude() && currentKey.getInclude() == false) {
                        //INCLUDED (include=false on an already excluded list)
                        includedKeySet.getKeyList().add(currentKey);
                    } else {
                        //EXCLUDED
                        excludedKeySet.getKeyList().add(currentKey);
                    }
                }
            }
        }
    }

    /**
     * Deep equals internal boolean.
     *
     * @param bean                   the bean
     * @param includeFinalProperties the include final properties
     * @return the boolean
     */
    protected boolean deepEqualsInternal(ConstraintBean bean, boolean includeFinalProperties) {
        if (bean == null) {
            return false;
        }
        if (bean.getStructureType() == this.getStructureType()) {
            ConstraintBean that = bean;
            if (!super.equivalent(includedSeriesKeys, that.getIncludedSeriesKeys(), includeFinalProperties)) {
                return false;
            }
            if (!super.equivalent(excludedSeriesKeys, that.getExcludedSeriesKeys(), includeFinalProperties)) {
                return false;
            }
            if (!super.equivalent(constraintAttachment, that.getConstraintAttachment(), includeFinalProperties)) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
        }
        return false;
    }


    private void validate() {
        if (includedSeriesKeys != null && !ObjectUtil.validCollection(includedSeriesKeys.getConstrainedDataKeys())) {
            this.includedSeriesKeys = null;
        }
        if (excludedSeriesKeys != null && !ObjectUtil.validCollection(excludedSeriesKeys.getConstrainedDataKeys())) {
            this.excludedSeriesKeys = null;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(includedSeriesKeys, composites);
        super.addToCompositeSet(excludedSeriesKeys, composites);
        super.addToCompositeSet(includedMetadataKeys, composites);
        super.addToCompositeSet(excludedMetadataKeys, composites);
        super.addToCompositeSet(constraintAttachment, composites);
        return composites;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public ConstraintAttachmentBean getConstraintAttachment() {
        return constraintAttachment;
    }

    @Override
    public ConstraintDataKeySetBean getIncludedSeriesKeys() {
        return includedSeriesKeys;
    }

    @Override
    public ConstraintDataKeySetBean getExcludedSeriesKeys() {
        return excludedSeriesKeys;
    }

    @Override
    public ConstraintDataKeySetBean getIncludedMetadataKeys() {
        return includedMetadataKeys;
    }

    @Override
    public ConstraintDataKeySetBean getExcludedMetadataKeys() {
        return excludedMetadataKeys;
    }
}
