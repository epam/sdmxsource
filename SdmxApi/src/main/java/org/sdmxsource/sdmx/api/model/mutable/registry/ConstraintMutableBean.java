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

import org.sdmxsource.sdmx.api.model.mutable.base.MaintainableMutableBean;

/**
 * The interface Constraint mutable bean.
 */
public interface ConstraintMutableBean extends MaintainableMutableBean {

    /**
     * Gets constraint attachment.
     *
     * @return the constraint attachment
     */
    ConstraintAttachmentMutableBean getConstraintAttachment();

    /**
     * Sets constraint attachment.
     *
     * @param bean the bean
     */
    void setConstraintAttachment(ConstraintAttachmentMutableBean bean);


    /**
     * Returns the series keys that this constraint defines at ones that either have data, or are allowed to have data (depending on isDefiningActualDataPresent() value)
     *
     * @return included series keys
     */
    ConstraintDataKeySetMutableBean getIncludedSeriesKeys();

    /**
     * Sets included series keys.
     *
     * @param includedSeriesKeys the included series keys
     */
    void setIncludedSeriesKeys(ConstraintDataKeySetMutableBean includedSeriesKeys);

    /**
     * Returns the series keys that this constraint defines at ones that either do not have data, or are not allowed to have data (depending on isDefiningActualDataPresent() value)
     *
     * @return excluded series keys
     */
    ConstraintDataKeySetMutableBean getExcludedSeriesKeys();

    /**
     * Sets excluded series keys.
     *
     * @param excludedSeriesKeys the excluded series keys
     */
    void setExcludedSeriesKeys(ConstraintDataKeySetMutableBean excludedSeriesKeys);

    /**
     * Returns the metadata keys that this constraint defines at ones that either have data, or are allowed to have data (depending on isDefiningActualDataPresent() value)
     *
     * @return included metadata keys
     */
    ConstraintDataKeySetMutableBean getIncludedMetadataKeys();

    /**
     * Sets included metadata keys.
     *
     * @param includedSeriesKeys the included series keys
     */
    void setIncludedMetadataKeys(ConstraintDataKeySetMutableBean includedSeriesKeys);

    /**
     * Returns the metadata keys that this constraint defines at ones that either do not have data, or are not allowed to have data (depending on isDefiningActualDataPresent() value)
     *
     * @return excluded metadata keys
     */
    ConstraintDataKeySetMutableBean getExcludedMetadataKeys();

    /**
     * Sets excluded metadata keys.
     *
     * @param excludedSeriesKeys the excluded series keys
     */
    void setExcludedMetadataKeys(ConstraintDataKeySetMutableBean excludedSeriesKeys);

}
