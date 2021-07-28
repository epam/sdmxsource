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
import org.sdmxsource.sdmx.api.model.beans.datastructure.CrossSectionalMeasureBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.CrossSectionalMeasureMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.ComponentMutableBeanImpl;


/**
 * The type Cross sectional measure mutable bean.
 */
public class CrossSectionalMeasureMutableBeanImpl extends ComponentMutableBeanImpl implements CrossSectionalMeasureMutableBean {
    private static final long serialVersionUID = 1L;

    private String measureDimension;
    private String code;

    /**
     * Instantiates a new Cross sectional measure mutable bean.
     *
     * @param bean the bean
     */
    public CrossSectionalMeasureMutableBeanImpl(CrossSectionalMeasureBean bean) {
        super(bean);
        this.measureDimension = bean.getMeasureDimension();
        this.code = bean.getCode();
    }

    /**
     * Instantiates a new Cross sectional measure mutable bean.
     */
    public CrossSectionalMeasureMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.CROSS_SECTIONAL_MEASURE);
    }

    @Override
    public String getMeasureDimension() {
        return measureDimension;
    }

    @Override
    public void setMeasureDimension(String measureDimension) {
        this.measureDimension = measureDimension;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }
}
