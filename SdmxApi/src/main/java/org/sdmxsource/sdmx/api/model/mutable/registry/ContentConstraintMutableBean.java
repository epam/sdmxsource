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
package org.sdmxsource.sdmx.api.model.mutable.registry;

import org.sdmxsource.sdmx.api.model.beans.registry.ContentConstraintBean;

/**
 * The interface Content constraint mutable bean.
 */
public interface ContentConstraintMutableBean extends ConstraintMutableBean {

    /**
     * Gets is defining actual data present.
     *
     * @return the is defining actual data present
     */
    boolean getIsDefiningActualDataPresent();

    /**
     * Sets is defining actual data present.
     *
     * @param present the present
     */
    void setIsDefiningActualDataPresent(boolean present);

    /**
     * Returns the metadata target region, or null if this is undefined.
     *
     * @return metadata target region
     */
    MetadataTargetRegionMutableBean getMetadataTargetRegion();

    /**
     * Sets metadata target region.
     *
     * @param mtr the mtr
     */
    void setMetadataTargetRegion(MetadataTargetRegionMutableBean mtr);

    /**
     * Returns a CubeRegion where the Keys and Attributes are defining data that is present or allowed (depending on isDefiningActualDataPresent() value)
     *
     * @return included cube region
     */
    CubeRegionMutableBean getIncludedCubeRegion();

    /**
     * Sets included cube region.
     *
     * @param cubeRegionMutable the cube region mutable
     */
    void setIncludedCubeRegion(CubeRegionMutableBean cubeRegionMutable);

    /**
     * Returns a CubeRegion where the Keys and Attributes are defining data is is not present or disallowed (depending on isDefiningActualDataPresent() value)
     *
     * @return excluded cube region
     */
    CubeRegionMutableBean getExcludedCubeRegion();

    /**
     * Sets excluded cube region.
     *
     * @param cubeRegionMutable the cube region mutable
     */
    void setExcludedCubeRegion(CubeRegionMutableBean cubeRegionMutable);

    /**
     * Gets reference period.
     *
     * @return the reference period
     */
    ReferencePeriodMutableBean getReferencePeriod();

    /**
     * Sets reference period.
     *
     * @param bean the bean
     */
    void setReferencePeriod(ReferencePeriodMutableBean bean);

    /**
     * Gets release calendar.
     *
     * @return the release calendar
     */
    ReleaseCalendarMutableBean getReleaseCalendar();

    /**
     * Sets release calendar.
     *
     * @param bean the bean
     */
    void setReleaseCalendar(ReleaseCalendarMutableBean bean);

    @Override
    ContentConstraintBean getImmutableInstance();
}
