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
import org.sdmxsource.sdmx.api.model.beans.registry.ContentConstraintBean;
import org.sdmxsource.sdmx.api.model.beans.registry.CubeRegionBean;
import org.sdmxsource.sdmx.api.model.beans.registry.KeyValues;
import org.sdmxsource.sdmx.api.model.mutable.registry.CubeRegionMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.KeyValuesMutable;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.registry.CubeRegionBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.MutableBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Cube region mutable bean.
 */
public class CubeRegionMutableBeanImpl extends MutableBeanImpl implements CubeRegionMutableBean {
    private static final long serialVersionUID = 1L;
    private List<KeyValuesMutable> keyValues = new ArrayList<KeyValuesMutable>();
    private List<KeyValuesMutable> attributeValues = new ArrayList<KeyValuesMutable>();

    /**
     * Instantiates a new Cube region mutable bean.
     */
    public CubeRegionMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.CUBE_REGION);
    }

    /**
     * Instantiates a new Cube region mutable bean.
     *
     * @param immutable the immutable
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM IMMUTABLE BEAN				 //////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public CubeRegionMutableBeanImpl(CubeRegionBean immutable) {
        super(immutable);
        if (ObjectUtil.validCollection(immutable.getKeyValues())) {
            for (KeyValues mutable : immutable.getKeyValues()) {
                this.keyValues.add(new KeyValuesMutableImpl(mutable));
            }
        }
        if (ObjectUtil.validCollection(immutable.getAttributeValues())) {
            for (KeyValues mutable : immutable.getAttributeValues()) {
                this.attributeValues.add(new KeyValuesMutableImpl(mutable));
            }
        }
    }

    @Override
    public List<KeyValuesMutable> getKeyValues() {
        return this.keyValues;
    }

    @Override
    public void setKeyValues(List<KeyValuesMutable> keyvalues) {
        if (keyValues != null)
            this.keyValues = keyvalues;
    }

    @Override
    public void addKeyValues(KeyValuesMutable inputValue) {
        if (inputValue == null) {
            return;
        }

        KeyValuesMutable foundKvm = null;
        for (KeyValuesMutable kvm : keyValues) {
            if (kvm.getId() != null &&
                    kvm.getId().equals(inputValue.getId())) {
                foundKvm = kvm;
                break;
            }
        }

        if (foundKvm == null) {
            // This id doesn't already exist so just add the KeyValuesMutable
            this.keyValues.add(inputValue);
        } else {
            // A KeyValuesMutable with this id does already exist so merge the inputVlaue
            for (String val : inputValue.getKeyValues()) {
                outer:
                {
                    for (String str : foundKvm.getKeyValues()) {
                        if (str.equals(val)) {
                            break outer;
                        }
                    }
                    foundKvm.addValue(val);
                }
            }
        }

    }

    @Override
    public List<KeyValuesMutable> getAttributeValues() {
        return this.attributeValues;
    }

    @Override
    public void setAttributeValues(List<KeyValuesMutable> attvalues) {
        if (attvalues != null)
            this.attributeValues = attvalues;
    }

    @Override
    public void addAttributeValue(KeyValuesMutable attvalue) {
        this.attributeValues.add(attvalue);
    }

    /**
     * Create mutable instance cube region bean.
     *
     * @param parent the parent
     * @return the cube region bean
     */
    public CubeRegionBean createMutableInstance(ContentConstraintBean parent) {
        return new CubeRegionBeanImpl(this, parent);
    }
}
