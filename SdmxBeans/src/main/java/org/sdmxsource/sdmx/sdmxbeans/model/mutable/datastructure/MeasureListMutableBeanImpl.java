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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.datastructure;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.datastructure.MeasureListBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.MeasureListMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.PrimaryMeasureMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.IdentifiableMutableBeanImpl;


/**
 * The type Measure list mutable bean.
 */
public class MeasureListMutableBeanImpl extends IdentifiableMutableBeanImpl implements MeasureListMutableBean {
    private static final long serialVersionUID = 1L;

    private PrimaryMeasureMutableBean primaryMeasureMutableBean;

    /**
     * Instantiates a new Measure list mutable bean.
     */
    public MeasureListMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.MEASURE_DESCRIPTOR);
    }

    /**
     * Instantiates a new Measure list mutable bean.
     *
     * @param bean the bean
     */
    public MeasureListMutableBeanImpl(MeasureListBean bean) {
        super(bean);
        if (bean.getPrimaryMeasure() != null) {
            this.primaryMeasureMutableBean = new PrimaryMeasureMutableBeanImpl(bean.getPrimaryMeasure());
        }
    }

    @Override
    public PrimaryMeasureMutableBean getPrimaryMeasure() {
        return primaryMeasureMutableBean;
    }

    @Override
    public void setPrimaryMeasure(PrimaryMeasureMutableBean primaryMeasure) {
        this.primaryMeasureMutableBean = primaryMeasure;
    }
}
