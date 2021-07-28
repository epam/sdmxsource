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

import org.sdmx.resources.sdmxml.schemas.v21.common.DistinctKeyType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.DataKeySetType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.MetadataKeySetType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ConstrainedDataKeyBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ConstraintBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ConstraintDataKeySetBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.ConstrainedDataKeyMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.ConstraintDataKeySetMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.SdmxStructureBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * The type Constraint data key set bean.
 */
public class ConstraintDataKeySetBeanImpl extends SdmxStructureBeanImpl implements ConstraintDataKeySetBean {
    private static final long serialVersionUID = 740679251759233195L;
    private List<ConstrainedDataKeyBean> contstrainedKeys = new ArrayList<ConstrainedDataKeyBean>();

    /**
     * Instantiates a new Constraint data key set bean.
     *
     * @param mutable the mutable
     * @param parent  the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEAN				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ConstraintDataKeySetBeanImpl(ConstraintDataKeySetMutableBean mutable, ConstraintBean parent) {
        super(SDMX_STRUCTURE_TYPE.CONSTRAINED_DATA_KEY_SET, parent);
        for (ConstrainedDataKeyMutableBean each : mutable.getConstrainedDataKeys()) {
            ConstrainedDataKeyBean cdk = new ConstrainedDataKeyBeanImpl(each, this);
            if (ObjectUtil.validCollection(cdk.getKeyValues())) {
                contstrainedKeys.add(cdk);
            }
        }
    }

    /**
     * Instantiates a new Constraint data key set bean.
     *
     * @param dataKeySetType the data key set type
     * @param parent         the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ConstraintDataKeySetBeanImpl(DataKeySetType dataKeySetType, ConstraintBean parent) {
        super(SDMX_STRUCTURE_TYPE.CONSTRAINED_DATA_KEY_SET, parent);
        if (dataKeySetType.getKeyList() != null) {
            for (DistinctKeyType currentKey : dataKeySetType.getKeyList()) {
                ConstrainedDataKeyBean cdk = new ConstrainedDataKeyBeanImpl(currentKey, this);
                if (ObjectUtil.validCollection(cdk.getKeyValues())) {
                    contstrainedKeys.add(cdk);
                }
            }
        }
    }

    /**
     * Instantiates a new Constraint data key set bean.
     *
     * @param mdKeySetType the md key set type
     * @param parent       the parent
     */
    public ConstraintDataKeySetBeanImpl(MetadataKeySetType mdKeySetType, ConstraintBean parent) {
        super(SDMX_STRUCTURE_TYPE.CONSTRAINED_DATA_KEY_SET, parent);
        if (mdKeySetType.getKeyList() != null) {
            for (DistinctKeyType currentKey : mdKeySetType.getKeyList()) {
                ConstrainedDataKeyBean cdk = new ConstrainedDataKeyBeanImpl(currentKey, this);
                if (ObjectUtil.validCollection(cdk.getKeyValues())) {
                    contstrainedKeys.add(cdk);
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
            ConstraintDataKeySetBean that = (ConstraintDataKeySetBean) bean;
            if (!super.equivalent(contstrainedKeys, that.getConstrainedDataKeys(), includeFinalProperties)) {
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
    public List<ConstrainedDataKeyBean> getConstrainedDataKeys() {
        return new ArrayList<ConstrainedDataKeyBean>(contstrainedKeys);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES		                     //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(contstrainedKeys, composites);
        return composites;
    }
}
