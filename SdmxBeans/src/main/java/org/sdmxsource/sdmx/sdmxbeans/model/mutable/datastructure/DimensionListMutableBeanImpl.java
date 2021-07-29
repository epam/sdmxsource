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
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionListBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.DimensionListMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.DimensionMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.IdentifiableMutableBeanImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Dimension list mutable bean.
 */
public class DimensionListMutableBeanImpl extends IdentifiableMutableBeanImpl implements DimensionListMutableBean {
    private static final long serialVersionUID = 1L;

    private List<DimensionMutableBean> dimensions = new ArrayList<DimensionMutableBean>();

    /**
     * Instantiates a new Dimension list mutable bean.
     */
    public DimensionListMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.DIMENSION_DESCRIPTOR);
    }

    /**
     * Instantiates a new Dimension list mutable bean.
     *
     * @param bean the bean
     */
    public DimensionListMutableBeanImpl(DimensionListBean bean) {
        super(bean);
        if (bean.getDimensions() != null) {
            for (DimensionBean currentDimension : bean.getDimensions()) {
                this.dimensions.add(new DimensionMutableBeanImpl(currentDimension));
            }
        }
    }

    @Override
    public List<DimensionMutableBean> getDimensions() {
        return dimensions;
    }

    @Override
    public void setDimensions(List<DimensionMutableBean> dimensions) {
        this.dimensions = dimensions;
    }

    @Override
    public void addDimension(DimensionMutableBean dimension) {
        if (this.dimensions == null) {
            this.dimensions = new ArrayList<DimensionMutableBean>();
        }
        this.dimensions.add(dimension);
    }
}
