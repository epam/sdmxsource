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
package org.sdmxsource.sdmx.sdmxbeans.model.metadata;

import org.sdmx.resources.sdmxml.schemas.v21.common.ComponentValueSetType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.model.metadata.DataKeyBean;
import org.sdmxsource.sdmx.api.model.metadata.ReferenceValueBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.SDMXBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.data.KeyValueImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.HashSet;
import java.util.Set;


/**
 * The type Data key bean.
 */
public class DataKeyBeanImpl extends SDMXBeanImpl implements DataKeyBean {
    private static final long serialVersionUID = -4394139041344288008L;
    private boolean included;
    private KeyValue keyValue;

    /**
     * Instantiates a new Data key bean.
     *
     * @param parent the parent
     * @param type   the type
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public DataKeyBeanImpl(ReferenceValueBean parent, ComponentValueSetType type) {
        super(SDMX_STRUCTURE_TYPE.KEY_VALUES, parent);
        this.included = type.getInclude();
        this.keyValue = new KeyValueImpl(type.getValueList().get(0).getStringValue(), type.getId());
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean isIncluded() {
        return included;
    }

    @Override
    public KeyValue getKeyValue() {
        return keyValue;
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
            DataKeyBean that = (DataKeyBean) bean;
            if (this.included != that.isIncluded()) {
                return false;
            }
            if (!ObjectUtil.equivalent(this.keyValue, that.getKeyValue())) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES		                     //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        return new HashSet<SDMXBean>();
    }
}
