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

import org.sdmx.resources.sdmxml.schemas.v21.common.ComponentValueSetType;
import org.sdmx.resources.sdmxml.schemas.v21.common.SimpleValueType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.registry.KeyValues;
import org.sdmxsource.sdmx.api.model.mutable.base.TimeRangeMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.KeyValuesMutable;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.MutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.TimeRangeMutableBeanImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Key values mutable.
 */
public class KeyValuesMutableImpl extends MutableBeanImpl implements KeyValuesMutable {
    private static final long serialVersionUID = 1L;
    private String id;
    private List<String> values = new ArrayList<String>();
    private List<String> cascadeList = new ArrayList<String>();
    private TimeRangeMutableBean timeRangeBean;

    /**
     * Instantiates a new Key values mutable.
     */
    public KeyValuesMutableImpl() {
        super(SDMX_STRUCTURE_TYPE.KEY_VALUES);
    }

    /**
     * Instantiates a new Key values mutable.
     *
     * @param immutable the immutable
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM IMMUTABLE BEAN				 //////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public KeyValuesMutableImpl(KeyValues immutable) {
        super(SDMX_STRUCTURE_TYPE.KEY_VALUES);
        this.id = immutable.getId();
        this.values.addAll(immutable.getValues());
        for (String each : this.values) {
            if (immutable.isCascadeValue(each)) {
                this.cascadeList.add(each);
            }
        }
        if (immutable.getTimeRange() != null) {
            timeRangeBean = new TimeRangeMutableBeanImpl(immutable.getTimeRange());
        }
    }

    /**
     * Instantiates a new Key values mutable.
     *
     * @param keyValueType the key value type
     */
    public KeyValuesMutableImpl(ComponentValueSetType keyValueType) {
        super(SDMX_STRUCTURE_TYPE.KEY_VALUES);

        this.id = keyValueType.getId();

        if (keyValueType.getValueList() != null) {
            for (SimpleValueType dataKeyType : keyValueType.getValueList()) {
                values.add(dataKeyType.getStringValue());
                if (dataKeyType.getCascadeValues()) {
                    cascadeList.add(dataKeyType.getStringValue());
                }
            }
        }
        if (keyValueType.getTimeRange() != null) {
            this.timeRangeBean = new TimeRangeMutableBeanImpl(keyValueType.getTimeRange());
        }
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public List<String> getCascade() {
        return cascadeList;
    }

    @Override
    public void setCascade(List<String> cascadeValues) {
        cascadeList = cascadeValues;
    }

    @Override
    public void addCascade(String value) {
        if (cascadeList == null) {
            cascadeList = new ArrayList<String>();
        }
        cascadeList.add(value);
    }

    @Override
    public boolean isCascadeValue(String value) {
        return cascadeList.contains(value);
    }

    @Override
    public TimeRangeMutableBean getTimeRange() {
        return timeRangeBean;
    }

    @Override
    public void setTimeRange(TimeRangeMutableBean timeRange) {
        this.timeRangeBean = timeRange;
    }

    @Override
    public List<String> getKeyValues() {
        return values;
    }

    @Override
    public void setKeyValues(List<String> kv) {
        this.values = kv;
    }

    @Override
    public void addValue(String value) {
        if (values == null) {
            this.values = new ArrayList<String>();
        }

        if (value != null) {
            this.values.add(value);
        }
    }
}
