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
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.model.mutable.registry.ConstrainedDataKeyMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.data.KeyValueImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.MutableBeanImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Constrained data key mutable bean.
 */
public class ConstrainedDataKeyMutableBeanImpl extends MutableBeanImpl implements ConstrainedDataKeyMutableBean {
    private static final long serialVersionUID = 1L;

    private List<KeyValue> keyValues = new ArrayList<KeyValue>();

    /**
     * Instantiates a new Constrained data key mutable bean.
     */
    public ConstrainedDataKeyMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.CONSTRAINED_DATA_KEY);
    }

    /**
     * Instantiates a new Constrained data key mutable bean.
     *
     * @param immutable the immutable
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM IMMUTABLE BEAN				 //////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ConstrainedDataKeyMutableBeanImpl(ConstrainedDataKeyBean immutable) {
        super(immutable);
        for (KeyValue each : immutable.getKeyValues()) {
            this.keyValues.add(new KeyValueImpl(each.getCode(), each.getConcept()));
        }
    }

    @Override
    public List<KeyValue> getKeyValues() {
        return this.keyValues;
    }

    @Override
    public void setKeyValues(List<KeyValue> keyvalues) {
        if (keyvalues != null)
            this.keyValues = keyvalues;
    }

    @Override
    public void addKeyValue(KeyValue keyvalue) {
        if (keyvalue != null)
            this.keyValues.add(keyvalue);
    }
}
