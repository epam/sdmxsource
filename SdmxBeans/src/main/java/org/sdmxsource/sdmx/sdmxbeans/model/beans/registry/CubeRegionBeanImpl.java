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
import org.sdmx.resources.sdmxml.schemas.v21.common.CubeRegionType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ContentConstraintBean;
import org.sdmxsource.sdmx.api.model.beans.registry.CubeRegionBean;
import org.sdmxsource.sdmx.api.model.beans.registry.KeyValues;
import org.sdmxsource.sdmx.api.model.mutable.registry.CubeRegionMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.KeyValuesMutable;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.SdmxStructureBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The type Cube region bean.
 */
public class CubeRegionBeanImpl extends SdmxStructureBeanImpl implements CubeRegionBean {
    private static final long serialVersionUID = -280406193675261130L;
    private List<KeyValues> keyValues = new ArrayList<KeyValues>();
    private List<KeyValues> attributeValues = new ArrayList<KeyValues>();


    /**
     * Instantiates a new Cube region bean.
     *
     * @param mutable the mutable
     * @param parent  the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM IMMUTABLE BEAN				 //////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public CubeRegionBeanImpl(CubeRegionMutableBean mutable, ContentConstraintBean parent) {
        super(SDMX_STRUCTURE_TYPE.CUBE_REGION, parent);
        this.keyValues = new ArrayList<KeyValues>();
        if (ObjectUtil.validCollection(mutable.getKeyValues())) {
            for (KeyValuesMutable kvMut : mutable.getKeyValues()) {
                if (ObjectUtil.validCollection(kvMut.getKeyValues())) {
                    this.keyValues.add(new KeyValuesImpl(kvMut, this));
                }
            }
        }
        this.attributeValues = new ArrayList<KeyValues>();
        if (ObjectUtil.validCollection(mutable.getAttributeValues())) {
            for (KeyValuesMutable kvMut : mutable.getAttributeValues()) {
                if (ObjectUtil.validCollection(kvMut.getKeyValues())) {
                    this.attributeValues.add(new KeyValuesImpl(kvMut, this));
                }
            }
        }
    }

    /**
     * Instantiates a new Cube region bean.
     *
     * @param cubeRegionType the cube region type
     * @param negate         the negate
     * @param parent         the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public CubeRegionBeanImpl(CubeRegionType cubeRegionType, boolean negate, ContentConstraintBean parent) {
        super(SDMX_STRUCTURE_TYPE.CUBE_REGION, parent);

        if (cubeRegionType.getAttributeList() != null) {
            for (ComponentValueSetType valueSetType : cubeRegionType.getAttributeList()) {
                if (!valueSetType.getInclude()) {
                    if (negate) {
                        this.attributeValues.add(new KeyValuesImpl(valueSetType, this));
                    }
                } else if (!negate) {
                    this.attributeValues.add(new KeyValuesImpl(valueSetType, this));
                }
            }
        }
        if (cubeRegionType.getKeyValueList() != null) {
            for (ComponentValueSetType valueSetType : cubeRegionType.getKeyValueList()) {
                if (!valueSetType.getInclude()) {
                    if (negate) {
                        this.keyValues.add(new KeyValuesImpl(valueSetType, this));
                    }
                } else if (!negate) {
                    this.keyValues.add(new KeyValuesImpl(valueSetType, this));
                }
            }
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
            CubeRegionBean that = (CubeRegionBean) bean;
            if (!super.equivalent(keyValues, that.getKeyValues(), includeFinalProperties)) {
                return false;
            }
            if (!super.equivalent(attributeValues, that.getAttributeValues(), includeFinalProperties)) {
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
    public List<KeyValues> getKeyValues() {
        return new ArrayList<KeyValues>(keyValues);
    }

    @Override
    public List<KeyValues> getAttributeValues() {
        return new ArrayList<KeyValues>(attributeValues);
    }

    @Override
    public Set<String> getValues(String componentId) {
        for (KeyValues kvs : keyValues) {
            if (kvs.getId().equals(componentId)) {
                return new HashSet<String>(kvs.getValues());
            }
        }
        for (KeyValues kvs : attributeValues) {
            if (kvs.getId().equals(componentId)) {
                return new HashSet<String>(kvs.getValues());
            }
        }
        return new HashSet<String>();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES		                     //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(keyValues, composites);
        super.addToCompositeSet(attributeValues, composites);
        return composites;
    }
}
