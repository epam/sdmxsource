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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.base;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TERTIARY_BOOL;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.mutable.base.MutableBean;
import org.sdmxsource.sdmx.util.beans.SDMXBeanUtil;


/**
 * The type Mutable bean.
 */
public abstract class MutableBeanImpl implements MutableBean {
    private static final long serialVersionUID = 1L;
    /**
     * The Structure type.
     */
    protected SDMX_STRUCTURE_TYPE structureType;


    /**
     * Instantiates a new Mutable bean.
     *
     * @param structureType the structure type
     */
    protected MutableBeanImpl(SDMX_STRUCTURE_TYPE structureType) {
        this.structureType = structureType;
    }

    /**
     * Instantiates a new Mutable bean.
     *
     * @param createdFrom the created from
     */
    protected MutableBeanImpl(SDMXBean createdFrom) {
        this.structureType = createdFrom.getStructureType();
    }

    /**
     * Create tertiary tertiary bool.
     *
     * @param isSet the is set
     * @param value the value
     * @return the tertiary bool
     */
    protected static TERTIARY_BOOL createTertiary(boolean isSet, boolean value) {
        return SDMXBeanUtil.createTertiary(isSet, value);
    }

    @Override
    public SDMX_STRUCTURE_TYPE getStructureType() {
        return structureType;
    }
}
