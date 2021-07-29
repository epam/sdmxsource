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
import org.sdmxsource.sdmx.api.model.beans.registry.ReleaseCalendarBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.ReleaseCalendarMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.MutableBeanImpl;


/**
 * The type Release calendar mutable bean.
 */
public class ReleaseCalendarMutableBeanImpl extends MutableBeanImpl implements ReleaseCalendarMutableBean {
    private static final long serialVersionUID = 1L;

    private String periodicity;
    private String offset;
    private String tolerance;

    /**
     * Instantiates a new Release calendar mutable bean.
     */
    public ReleaseCalendarMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.RELEASE_CALENDAR);
    }

    /**
     * Instantiates a new Release calendar mutable bean.
     *
     * @param immutable the immutable
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM IMMUTABLE BEAN				 //////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ReleaseCalendarMutableBeanImpl(ReleaseCalendarBean immutable) {
        super(immutable);
        this.periodicity = immutable.getPeriodicity();
        this.offset = immutable.getOffset();
        this.tolerance = immutable.getTolerance();
    }

    @Override
    public String getPeriodicity() {
        return this.periodicity;
    }

    @Override
    public void setPeriodicity(String periodicity) {
        this.periodicity = periodicity;
    }

    @Override
    public String getOffset() {
        return this.offset;
    }

    @Override
    public void setOffset(String offset) {
        this.offset = offset;
    }

    @Override
    public String getTolerance() {
        return this.tolerance;
    }

    @Override
    public void setTolerance(String tolerance) {
        this.tolerance = tolerance;
    }
}
