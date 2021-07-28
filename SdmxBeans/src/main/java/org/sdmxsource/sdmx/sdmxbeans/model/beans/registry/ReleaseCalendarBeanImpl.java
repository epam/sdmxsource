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

import org.sdmx.resources.sdmxml.schemas.v21.structure.ReleaseCalendarType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ContentConstraintBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ReleaseCalendarBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.ReleaseCalendarMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.SdmxStructureBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.registry.ReleaseCalendarMutableBeanImpl;
import org.sdmxsource.util.ObjectUtil;


/**
 * The type Release calendar bean.
 */
public class ReleaseCalendarBeanImpl extends SdmxStructureBeanImpl implements ReleaseCalendarBean {
    private static final long serialVersionUID = 1244215476642998552L;
    private String offset;
    private String periodicity;
    private String tolerance;

    /**
     * Instantiates a new Release calendar bean.
     *
     * @param mutable the mutable
     * @param parent  the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEAN				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ReleaseCalendarBeanImpl(ReleaseCalendarMutableBean mutable, ContentConstraintBean parent) {
        super(SDMX_STRUCTURE_TYPE.RELEASE_CALENDAR, parent);
        if (mutable != null) {
            this.offset = mutable.getOffset();
            this.periodicity = mutable.getPeriodicity();
            this.tolerance = mutable.getTolerance();
        }
    }

    /**
     * Instantiates a new Release calendar bean.
     *
     * @param releaseCalendarType the release calendar type
     * @param parent              the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ReleaseCalendarBeanImpl(ReleaseCalendarType releaseCalendarType, ContentConstraintBean parent) {
        super(SDMX_STRUCTURE_TYPE.RELEASE_CALENDAR, parent);
        this.offset = releaseCalendarType.getOffset();
        this.periodicity = releaseCalendarType.getPeriodicity();
        this.tolerance = releaseCalendarType.getTolerance();
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
            ReleaseCalendarBean that = (ReleaseCalendarBean) bean;
            if (!ObjectUtil.equivalent(offset, that.getOffset())) {
                return false;
            }
            if (!ObjectUtil.equivalent(periodicity, that.getPeriodicity())) {
                return false;
            }
            if (!ObjectUtil.equivalent(tolerance, that.getTolerance())) {
                return false;
            }
            return true;
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public String getPeriodicity() {
        return periodicity;
    }

    @Override
    public String getOffset() {
        return offset;
    }

    @Override
    public String getTolerance() {
        return tolerance;
    }

    @Override
    public ReleaseCalendarMutableBean createMutableBean() {
        return new ReleaseCalendarMutableBeanImpl(this);
    }
}
