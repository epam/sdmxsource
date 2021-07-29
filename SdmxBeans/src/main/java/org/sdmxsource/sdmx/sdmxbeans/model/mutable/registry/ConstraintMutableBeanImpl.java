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
import org.sdmxsource.sdmx.api.model.beans.registry.ConstraintBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.ConstraintAttachmentMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.ConstraintDataKeySetMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.ConstraintMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.MaintainableMutableBeanImpl;


/**
 * The type Constraint mutable bean.
 */
public abstract class ConstraintMutableBeanImpl extends MaintainableMutableBeanImpl implements ConstraintMutableBean {
    private static final long serialVersionUID = 1L;
    private ConstraintAttachmentMutableBean contentConstraintAttachmentBean = null;
    private ConstraintDataKeySetMutableBean includedSeriesKeys;
    private ConstraintDataKeySetMutableBean excludedSeriesKeys;
    private ConstraintDataKeySetMutableBean includedMetadataKeys;
    private ConstraintDataKeySetMutableBean excludedMetadataKeys;

    /**
     * Instantiates a new Constraint mutable bean.
     *
     * @param bean the bean
     */
    public ConstraintMutableBeanImpl(ConstraintBean bean) {
        super(bean);
        if (bean.getConstraintAttachment() != null) {
            this.contentConstraintAttachmentBean = bean.getConstraintAttachment().createMutableInstance();
        }
        if (bean.getIncludedSeriesKeys() != null) {
            includedSeriesKeys = new ConstraintDataKeySetMutableBeanImpl(bean.getIncludedSeriesKeys());
        }
        if (bean.getExcludedSeriesKeys() != null) {
            excludedSeriesKeys = new ConstraintDataKeySetMutableBeanImpl(bean.getExcludedSeriesKeys());
        }
    }

    /**
     * Instantiates a new Constraint mutable bean.
     *
     * @param structureType the structure type
     */
    public ConstraintMutableBeanImpl(SDMX_STRUCTURE_TYPE structureType) {
        super(structureType);
    }

    @Override
    public ConstraintAttachmentMutableBean getConstraintAttachment() {
        return this.contentConstraintAttachmentBean;
    }

    @Override
    public void setConstraintAttachment(ConstraintAttachmentMutableBean bean) {
        this.contentConstraintAttachmentBean = bean;
    }

    @Override
    public ConstraintDataKeySetMutableBean getIncludedSeriesKeys() {
        return includedSeriesKeys;
    }

    @Override
    public void setIncludedSeriesKeys(ConstraintDataKeySetMutableBean includedSeriesKeys) {
        this.includedSeriesKeys = includedSeriesKeys;
    }

    @Override
    public ConstraintDataKeySetMutableBean getExcludedSeriesKeys() {
        return excludedSeriesKeys;
    }

    @Override
    public void setExcludedSeriesKeys(ConstraintDataKeySetMutableBean excludedSeriesKeys) {
        this.excludedSeriesKeys = excludedSeriesKeys;
    }

    @Override
    public ConstraintDataKeySetMutableBean getIncludedMetadataKeys() {
        return includedMetadataKeys;
    }

    @Override
    public void setIncludedMetadataKeys(ConstraintDataKeySetMutableBean includedMetadataKeys) {
        this.includedMetadataKeys = includedMetadataKeys;
    }

    @Override
    public ConstraintDataKeySetMutableBean getExcludedMetadataKeys() {
        return excludedMetadataKeys;
    }

    @Override
    public void setExcludedMetadataKeys(ConstraintDataKeySetMutableBean excludedMetadataKeys) {
        this.excludedMetadataKeys = excludedMetadataKeys;
    }
}
