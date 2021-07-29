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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.registry;

import org.sdmx.resources.sdmxml.schemas.v21.common.ComponentValueSetType;
import org.sdmx.resources.sdmxml.schemas.v21.common.SimpleValueType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.base.SdmxStructureBean;
import org.sdmxsource.sdmx.api.model.beans.base.TimeRangeBean;
import org.sdmxsource.sdmx.api.model.beans.registry.KeyValues;
import org.sdmxsource.sdmx.api.model.mutable.registry.KeyValuesMutable;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.SdmxStructureBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.TimeRangeBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * The type Key values.
 */
public class KeyValuesImpl extends SdmxStructureBeanImpl implements KeyValues {
    private static final long serialVersionUID = 6742531633522333585L;
    private String id;
    private List<String> values = new ArrayList<String>();
    private List<String> caseCadeList = new ArrayList<String>();
    private TimeRangeBean timeRangeBean;


    /**
     * Instantiates a new Key values.
     *
     * @param mutable the mutable
     * @param parent  the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEAN				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public KeyValuesImpl(KeyValuesMutable mutable, SdmxStructureBean parent) {
        super(SDMX_STRUCTURE_TYPE.KEY_VALUES, parent);
        this.id = mutable.getId();
        this.values.addAll(mutable.getKeyValues());
        for (String each : this.values) {
            if (mutable.isCascadeValue(each)) {
                this.caseCadeList.add(each);
            }
        }
        if (mutable.getTimeRange() != null) {
            this.timeRangeBean = new TimeRangeBeanImpl(mutable.getTimeRange(), this);
        }

        validate();
    }

    /**
     * Instantiates a new Key values.
     *
     * @param keyValueType the key value type
     * @param parent       the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public KeyValuesImpl(ComponentValueSetType keyValueType, SdmxStructureBean parent) {
        super(SDMX_STRUCTURE_TYPE.KEY_VALUES, parent);

        this.id = keyValueType.getId();

        if (keyValueType.getValueList() != null) {
            for (SimpleValueType dataKeyType : keyValueType.getValueList()) {
                values.add(dataKeyType.getStringValue());
                if (dataKeyType.getCascadeValues()) {
                    caseCadeList.add(dataKeyType.getStringValue());
                }
            }
        }
        if (keyValueType.getTimeRange() != null) {
            this.timeRangeBean = new TimeRangeBeanImpl(keyValueType.getTimeRange(), this);
        }
        validate();
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATE							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() {
        if (!ObjectUtil.validString(id)) {
            throw new SdmxSemmanticException("KeyValues requires an id");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////DEEP EQUALS							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean deepEquals(SDMXBean bean, boolean includeFinalProperties) {
        if (bean == null) {
            return false;
        }
        if (bean.getStructureType() == this.getStructureType()) {
            KeyValues that = (KeyValues) bean;
            if (!ObjectUtil.equivalentCollection(values, that.getValues())) {
                return false;
            }
            for (String currentValue : values) {
                if (that.isCascadeValue(currentValue) != isCascadeValue(currentValue)) {
                    return false;
                }
            }
            if (!ObjectUtil.equivalent(id, that.getId())) {
                return false;
            }
            if (!super.equivalent(timeRangeBean, that.getTimeRange(), includeFinalProperties)) {
                return false;
            }
            return true;
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public String getId() {
        return id;
    }

    @Override
    public List<String> getValues() {
        return new ArrayList<String>(values);
    }

    @Override
    public List<String> getCascadeValues() {
        return new ArrayList<String>(caseCadeList);
    }

    @Override
    public boolean isCascadeValue(String value) {
        return caseCadeList.contains(value);
    }

    @Override
    public TimeRangeBean getTimeRange() {
        return timeRangeBean;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES		                     //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(timeRangeBean, composites);
        return composites;
    }
}
