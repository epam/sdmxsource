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
import org.sdmxsource.sdmx.api.model.beans.registry.ReferencePeriodBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.ReferencePeriodMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.MutableBeanImpl;

import java.util.Date;


/**
 * The type Reference period mutable bean.
 */
public class ReferencePeriodMutableBeanImpl extends MutableBeanImpl implements ReferencePeriodMutableBean {
    private static final long serialVersionUID = 1L;

    private Date startTime;
    private Date endTime;

    /**
     * Instantiates a new Reference period mutable bean.
     */
    public ReferencePeriodMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.REFERENCE_PERIOD);
    }

    /**
     * Instantiates a new Reference period mutable bean.
     *
     * @param immutable the immutable
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM IMMUTABLE BEAN				 //////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ReferencePeriodMutableBeanImpl(ReferencePeriodBean immutable) {
        super(immutable);
        if (immutable.getStartTime() != null) {
            this.startTime = immutable.getStartTime().getDate();
        }
        if (immutable.getEndTime() != null) {
            this.endTime = immutable.getEndTime().getDate();
        }
    }

    @Override
    public Date getStartTime() {
        return this.startTime;
    }

    @Override
    public void setStartTime(Date start) {
        this.startTime = start;
    }

    @Override
    public Date getEndTime() {
        return this.endTime;
    }

    @Override
    public void setEndTime(Date end) {
        this.endTime = end;
    }
}
