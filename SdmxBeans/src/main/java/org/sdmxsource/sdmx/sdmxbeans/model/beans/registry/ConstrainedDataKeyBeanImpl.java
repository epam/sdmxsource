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
import org.sdmx.resources.sdmxml.schemas.v21.common.DistinctKeyType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ConstrainedDataKeyBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ConstraintDataKeySetBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ContentConstraintBean;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.model.mutable.registry.ConstrainedDataKeyMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.SdmxStructureBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.data.KeyValueImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The type Constrained data key bean.
 */
public class ConstrainedDataKeyBeanImpl extends SdmxStructureBeanImpl implements ConstrainedDataKeyBean {
    private static final long serialVersionUID = 4311899020748834418L;
    private List<KeyValue> keyValues = new ArrayList<KeyValue>();

    /**
     * Instantiates a new Constrained data key bean.
     *
     * @param mutable the mutable
     * @param parent  the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEAN				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ConstrainedDataKeyBeanImpl(ConstrainedDataKeyMutableBean mutable, ConstraintDataKeySetBean parent) {
        super(SDMX_STRUCTURE_TYPE.CONSTRAINED_DATA_KEY, parent);
        for (KeyValue each : mutable.getKeyValues()) {
//			if(!each.getCode().equals("*")) {
            keyValues.add(new KeyValueImpl(each.getCode(), each.getConcept()));
//			}
        }
        validate();
    }

    /**
     * Instantiates a new Constrained data key bean.
     *
     * @param dataKeyType the data key type
     * @param parent      the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ConstrainedDataKeyBeanImpl(DistinctKeyType dataKeyType, ConstraintDataKeySetBean parent) {
        super(SDMX_STRUCTURE_TYPE.CONSTRAINED_DATA_KEY, parent);
        for (ComponentValueSetType componentValueSet : dataKeyType.getKeyValueList()) {
            String concept = componentValueSet.getId();
            if (componentValueSet.getValueList() == null
                    || componentValueSet.getValueList().size() < 1
                    || componentValueSet.getValueList().size() > 1) {
                throw new SdmxSemmanticException("KeyValue expected to contain a single value");
            }
            String value = componentValueSet.getValueList().get(0).getStringValue();
            keyValues.add(new KeyValueImpl(value, concept));
        }
        validate();
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
            ConstrainedDataKeyBean that = (ConstrainedDataKeyBean) bean;
            if (!ObjectUtil.equivalentCollection(getKeyValues(), that.getKeyValues())) {
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
    public List<KeyValue> getKeyValues() {
        return new ArrayList<KeyValue>(keyValues);
    }

    @Override
    public KeyValue getKeyValue(String dimensionId) {
        for (KeyValue kv : keyValues) {
            if (kv.getConcept().equals(dimensionId)) {
                return kv;
            }
        }
        return null;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATE				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() throws SdmxSemmanticException {
        Set<String> kvIdSet = new HashSet<String>();
        int count = 0;
        int countWildcard = 0;
        for (KeyValue kv : keyValues) {
            if (kvIdSet.contains(kv.getConcept())) {
                throw new SdmxSemmanticException("Constrained data key contains more then one value for dimension id: " + kv.getConcept());
            }
            kvIdSet.add(kv.getConcept());
            if (!kv.getCode().equals(ContentConstraintBean.WILDCARD_CODE)) {
                count++;
            } else {
                countWildcard++;
            }
        }
        if (count == 1 && countWildcard > 0) {
            throw new SdmxSemmanticException("Can not define a datakey set with only one code.  Please use Cube Region instead to mark code for inclusion or exclusion");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        // Can match another of same type
        if (!(obj instanceof ConstrainedDataKeyBean)) return false;
        ConstrainedDataKeyBean other = (ConstrainedDataKeyBean) obj;
        if (other.getKeyValues().size() != getKeyValues().size()) return false;
        for (KeyValue otherKV : other.getKeyValues()) {
            boolean found = false;
            for (KeyValue thisKV : getKeyValues()) {
                if (otherKV.equals(thisKV)) {
                    found = true;
                    break;
                }
            }
            if (!found) return false;
        }
        return true;
    }
}
