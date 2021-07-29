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
import org.sdmxsource.sdmx.api.model.beans.registry.ConstrainedDataKeyBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ConstraintDataKeySetBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.ConstrainedDataKeyMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.ConstraintDataKeySetMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.MutableBeanImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Constraint data key set mutable bean.
 */
public class ConstraintDataKeySetMutableBeanImpl extends MutableBeanImpl implements ConstraintDataKeySetMutableBean {
    private static final long serialVersionUID = 1L;

    private List<ConstrainedDataKeyMutableBean> constrainedKeys = new ArrayList<ConstrainedDataKeyMutableBean>();

    /**
     * Instantiates a new Constraint data key set mutable bean.
     */
    public ConstraintDataKeySetMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.CONSTRAINED_DATA_KEY_SET);
    }

    /**
     * Instantiates a new Constraint data key set mutable bean.
     *
     * @param immutable the immutable
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM IMMUTABLE BEAN				 //////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ConstraintDataKeySetMutableBeanImpl(ConstraintDataKeySetBean immutable) {
        super(SDMX_STRUCTURE_TYPE.CONSTRAINED_DATA_KEY_SET);
        for (ConstrainedDataKeyBean each : immutable.getConstrainedDataKeys())
            this.constrainedKeys.add(new ConstrainedDataKeyMutableBeanImpl(each));
    }

    @Override
    public List<ConstrainedDataKeyMutableBean> getConstrainedDataKeys() {
        return this.constrainedKeys;
    }

    @Override
    public void setConstrainedDataKeys(List<ConstrainedDataKeyMutableBean> keys) {
        if (keys == null) {
            constrainedKeys = new ArrayList<ConstrainedDataKeyMutableBean>();
        }
        this.constrainedKeys = keys;
    }

    @Override
    public void addConstrainedDataKey(ConstrainedDataKeyMutableBean key) {
        if (key == null) {
            constrainedKeys = new ArrayList<ConstrainedDataKeyMutableBean>();
        }
        this.constrainedKeys.add(key);
    }
}
