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
package org.sdmxsource.sdmx.api.model.beans.metadatastructure;

import org.sdmxsource.sdmx.api.constants.TEXT_TYPE;
import org.sdmxsource.sdmx.api.model.base.SdmxDate;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;

/**
 * Represents a SDMX Report Period Target
 */
public interface ReportPeriodTargetBean extends IdentifiableBean {
    /**
     * The constant FIXED_ID.
     */
    String FIXED_ID = "REPORT_PERIOD_TARGET";

    /**
     * Returns the text type of this report period target, defaults to TEXT_TYPE.OBSERVATIONAL_TIME_PERIOD
     *
     * @return text type
     */
    TEXT_TYPE getTextType();

    /**
     * Returns the start time of the report period target, null if undefined
     *
     * @return start time
     */
    SdmxDate getStartTime();

    /**
     * Returns the end time of the report period target, null if undefined
     *
     * @return end time
     */
    SdmxDate getEndTime();

}
