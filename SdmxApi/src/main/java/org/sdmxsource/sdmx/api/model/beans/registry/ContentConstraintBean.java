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
package org.sdmxsource.sdmx.api.model.beans.registry;

import org.sdmxsource.sdmx.api.model.mutable.registry.ContentConstraintMutableBean;

/**
 * The interface Content constraint bean.
 */
public interface ContentConstraintBean extends ConstraintBean {
    /**
     * The constant WILDCARD_CODE.
     */
    public static String WILDCARD_CODE = "*";

    /**
     * Returns true if this constraint is defining the actual data present for this constraint, false if it is defining the
     * allowed data
     *
     * @return true if this constraint is defining the actual data present for this constraint, false if it is defining the allowed data
     */
    boolean isDefiningActualDataPresent();

    /**
     * Returns the metadata target region, or null if this is undefined.
     *
     * @return metadata target region
     */
    MetadataTargetRegionBean getMetadataTargetRegion();

    @Override
    ConstraintAttachmentBean getConstraintAttachment();

    /**
     * Returns the series keys that this constraint defines at ones that either have data, or are allowed to have data (depending on isDefiningActualDataPresent() value)
     *
     * @return the included series keys
     */
    @Override
    ConstraintDataKeySetBean getIncludedSeriesKeys();


    /**
     * Returns the series keys that this constraint defines at ones that either do not have data, or are not allowed to have data (depending on isDefiningActualDataPresent() value)
     *
     * @return the excluded series keys
     */
    @Override
    ConstraintDataKeySetBean getExcludedSeriesKeys();


    /**
     * Returns a CubeRegion where the Keys and Attributes are defining data that is present or allowed (depending on isDefiningActualDataPresent() value)
     *
     * @return included cube region
     */
    CubeRegionBean getIncludedCubeRegion();

    /**
     * Returns a CubeRegion where the Keys and Attributes are defining data is is not present or disallowed (depending on isDefiningActualDataPresent() value)
     *
     * @return excluded cube region
     */
    CubeRegionBean getExcludedCubeRegion();


    /**
     * Gets reference period.
     *
     * @return the reference period
     */
    ReferencePeriodBean getReferencePeriod();

    /**
     * Gets release calendar.
     *
     * @return the release calendar
     */
    ReleaseCalendarBean getReleaseCalendar();

    /**
     * Returns a representation of itself in a bean which can be modified, modifications to the mutable bean
     * are not reflected in the instance of the MaintainableBean.
     *
     * @return the mutable instance
     */
    @Override
    ContentConstraintMutableBean getMutableInstance();

}
