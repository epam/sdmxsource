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

import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;

/**
 * The interface Constraint bean.
 */
public interface ConstraintBean extends MaintainableBean {

    /**
     * Returns the series keys that this constraint defines as ones that either
     * have data, or are allowed to have data (depending on isDefiningActualDataPresent() value)
     *
     * @return included series keys
     */
    ConstraintDataKeySetBean getIncludedSeriesKeys();


    /**
     * Returns the series keys that this constraint defines as ones that either
     * do not have data, or are not allowed to have data (depending on isDefiningActualDataPresent() value)
     *
     * @return excluded series keys
     */
    ConstraintDataKeySetBean getExcludedSeriesKeys();


    /**
     * Returns the Metadata keys that this constraint defines as ones that either
     * have data, or are allowed to have data (depending on isDefiningActualDataPresent() value)
     *
     * @return included metadata keys
     */
    ConstraintDataKeySetBean getIncludedMetadataKeys();


    /**
     * Returns the Metadata keys that this constraint defines as ones that either
     * do not have data, or are not allowed to have data (depending on isDefiningActualDataPresent() value)
     *
     * @return excluded metadata keys
     */
    ConstraintDataKeySetBean getExcludedMetadataKeys();

    /**
     * Returns the structure(s) that this constraint is constraining.
     *
     * @return constraint attachment
     */
    ConstraintAttachmentBean getConstraintAttachment();

}
