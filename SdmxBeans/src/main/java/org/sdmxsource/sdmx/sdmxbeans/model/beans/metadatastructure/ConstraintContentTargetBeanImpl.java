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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.metadatastructure;

import org.sdmx.resources.sdmxml.schemas.v21.structure.ConstraintContentTargetType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TEXT_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.ConstraintContentTargetBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataTargetBean;
import org.sdmxsource.sdmx.api.model.mutable.metadatastructure.ConstraintContentTargetMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.IdentifiableBeanImpl;


/**
 * The type Constraint content target bean.
 */
public class ConstraintContentTargetBeanImpl extends IdentifiableBeanImpl implements ConstraintContentTargetBean {
    private static final long serialVersionUID = -890998300721688045L;

    /**
     * Instantiates a new Constraint content target bean.
     *
     * @param parent the parent
     * @param bean   the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ConstraintContentTargetBeanImpl(MetadataTargetBean parent, ConstraintContentTargetMutableBean bean) {
        super(bean, parent);
        validate();
    }

    /**
     * Instantiates a new Constraint content target bean.
     *
     * @param target the target
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected ConstraintContentTargetBeanImpl(ConstraintContentTargetType target, MetadataTargetBean parent) {
        super(target, SDMX_STRUCTURE_TYPE.CONSTRAINT_CONTENT_TARGET, parent);
        validate();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////DEEP VALIDATION						 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean deepEquals(SDMXBean bean, boolean includeFinalProperties) {
        if (bean == null) {
            return false;
        }
        if (bean.getStructureType() == this.getStructureType()) {
            return super.deepEqualsInternal((ConstraintContentTargetBean) bean, includeFinalProperties);
        }
        return false;
    }

    private void validate() {
        setId(FIXED_ID);
    }

    @Override
    public TEXT_TYPE getTextType() {
        //FIXED VALUE
        return TEXT_TYPE.ATTACHMENT_CONSTRAINT_REFERENCE;
    }

    @Override
    public String getId() {
        return FIXED_ID;
    }
}
