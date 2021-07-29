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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.mapping;

import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.base.SdmxStructureBean;
import org.sdmxsource.sdmx.api.model.beans.mapping.ItemMapBean;
import org.sdmxsource.sdmx.api.model.mutable.mapping.ItemMapMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.SdmxStructureBeanImpl;
import org.sdmxsource.sdmx.util.beans.ValidationUtil;
import org.sdmxsource.util.ObjectUtil;


/**
 * The type Item map bean.
 */
public class ItemMapBeanImpl extends SdmxStructureBeanImpl implements ItemMapBean {
    private static final long serialVersionUID = 1L;

    private String sourceId;
    private String targetId;

    /**
     * Instantiates a new Item map bean.
     *
     * @param bean   the bean
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ItemMapBeanImpl(ItemMapMutableBean bean, SdmxStructureBean parent) {
        super(bean, parent);
        this.sourceId = bean.getSourceId();
        this.targetId = bean.getTargetId();
        validate();
    }

    /**
     * Instantiates a new Item map bean.
     *
     * @param id     the id
     * @param target the target
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ItemMapBeanImpl(String id, String target, SdmxStructureBean parent) {
        super(SDMX_STRUCTURE_TYPE.ITEM_MAP, parent);
        this.sourceId = id;
        this.targetId = target;
        validate();
    }

    /**
     * Instantiates a new Item map bean.
     *
     * @param alias  the alias
     * @param id     the id
     * @param target the target
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ItemMapBeanImpl(String alias, String id, String target, SdmxStructureBean parent) {
        super(SDMX_STRUCTURE_TYPE.ITEM_MAP, parent);
        this.sourceId = id;
        this.targetId = target;
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
            ItemMapBean that = (ItemMapBean) bean;
            if (!ObjectUtil.equivalent(sourceId, that.getSourceId())) {
                return false;
            }
            if (!ObjectUtil.equivalent(targetId, that.getTargetId())) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Validate.
     *
     * @throws SdmxSemmanticException the sdmx semmantic exception
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected void validate() throws SdmxSemmanticException {
        if (!ObjectUtil.validString(this.sourceId)) {
            throw new SdmxSemmanticException(ExceptionCode.BEAN_MISSING_REQUIRED_ELEMENT, "ItemMap", "Source Id");
        }
        if (!ObjectUtil.validString(this.targetId)) {
            throw new SdmxSemmanticException(ExceptionCode.BEAN_MISSING_REQUIRED_ELEMENT, "ItemMap", "Target Id");
        }
        sourceId = ValidationUtil.cleanAndValidateId(sourceId, true);
        targetId = ValidationUtil.cleanAndValidateId(targetId, true);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS							 	 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public String getSourceId() {
        return sourceId;
    }

    @Override
    public String getTargetId() {
        return targetId;
    }
}
