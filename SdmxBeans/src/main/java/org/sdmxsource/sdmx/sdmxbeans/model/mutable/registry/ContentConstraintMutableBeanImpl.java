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
import org.sdmxsource.sdmx.api.model.beans.registry.ContentConstraintBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.*;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.registry.ContentConstraintBeanImpl;


/**
 * The type Content constraint mutable bean.
 */
public class ContentConstraintMutableBeanImpl extends ConstraintMutableBeanImpl implements ContentConstraintMutableBean {
    private static final long serialVersionUID = 1L;

    private MetadataTargetRegionMutableBean metadataTargetRegionBean;

    private CubeRegionMutableBean includedCubeRegion;
    private CubeRegionMutableBean excludedCubeRegion;

    private ReferencePeriodMutableBean referencePeriodBean = null;
    private ReleaseCalendarMutableBean releaseCalendarBean = null;
    private boolean isDefiningActualDataPresent = true;  //Default Value

    /**
     * Instantiates a new Content constraint mutable bean.
     */
    public ContentConstraintMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.CONTENT_CONSTRAINT);
    }

    /**
     * Instantiates a new Content constraint mutable bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM IMMUTABLE BEAN				 //////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ContentConstraintMutableBeanImpl(ContentConstraintBean bean) {
        super(bean);
        if (bean.getIncludedCubeRegion() != null) {
            includedCubeRegion = new CubeRegionMutableBeanImpl(bean.getIncludedCubeRegion());
        }
        if (bean.getExcludedCubeRegion() != null) {
            excludedCubeRegion = new CubeRegionMutableBeanImpl(bean.getExcludedCubeRegion());
        }
        if (bean.getReferencePeriod() != null) {
            this.referencePeriodBean = bean.getReferencePeriod().createMutableBean();
        }
        if (bean.getReleaseCalendar() != null) {
            this.releaseCalendarBean = bean.getReleaseCalendar().createMutableBean();
        }
        if (bean.getMetadataTargetRegion() != null) {
            this.metadataTargetRegionBean = new MetadataTargetRegionMutableBeanImpl(bean.getMetadataTargetRegion());
        }
        this.isDefiningActualDataPresent = bean.isDefiningActualDataPresent();
    }


    @Override
    public MetadataTargetRegionMutableBean getMetadataTargetRegion() {
        return metadataTargetRegionBean;
    }

    @Override
    public void setMetadataTargetRegion(MetadataTargetRegionMutableBean mtr) {
        this.metadataTargetRegionBean = mtr;
    }

    @Override
    public boolean getIsDefiningActualDataPresent() {
        return this.isDefiningActualDataPresent;
    }

    @Override
    public void setIsDefiningActualDataPresent(boolean present) {
        this.isDefiningActualDataPresent = present;
    }

    @Override
    public CubeRegionMutableBean getIncludedCubeRegion() {
        return includedCubeRegion;
    }

    @Override
    public void setIncludedCubeRegion(CubeRegionMutableBean includedCubeRegion) {
        this.includedCubeRegion = includedCubeRegion;
    }

    @Override
    public CubeRegionMutableBean getExcludedCubeRegion() {
        return excludedCubeRegion;
    }

    @Override
    public void setExcludedCubeRegion(CubeRegionMutableBean excludedCubeRegion) {
        this.excludedCubeRegion = excludedCubeRegion;
    }

    @Override
    public ReferencePeriodMutableBean getReferencePeriod() {
        return this.referencePeriodBean;
    }

    @Override
    public void setReferencePeriod(ReferencePeriodMutableBean bean) {
        this.referencePeriodBean = bean;
    }

    @Override
    public ReleaseCalendarMutableBean getReleaseCalendar() {
        return this.releaseCalendarBean;
    }

    @Override
    public void setReleaseCalendar(ReleaseCalendarMutableBean bean) {
        this.releaseCalendarBean = bean;
    }

    @Override
    public ContentConstraintBean getImmutableInstance() {
        return new ContentConstraintBeanImpl(this);
    }
}
