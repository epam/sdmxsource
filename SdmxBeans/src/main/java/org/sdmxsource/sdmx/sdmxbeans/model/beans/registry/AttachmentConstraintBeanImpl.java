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

import org.sdmx.resources.sdmxml.schemas.v21.structure.AttachmentConstraintType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.registry.AttachmentConstraintBean;
import org.sdmxsource.sdmx.api.model.mutable.base.MaintainableMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.AttachmentConstraintMutableBean;

import java.net.URL;


/**
 * The type Attachment constraint bean.
 */
public class AttachmentConstraintBeanImpl extends ConstraintBeanImpl implements AttachmentConstraintBean {
    private static final long serialVersionUID = 7113577370122469599L;


    /**
     * Instantiates a new Attachment constraint bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEAN //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public AttachmentConstraintBeanImpl(AttachmentConstraintMutableBean bean) {
        super(bean);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM ITSELF, CREATES STUB BEAN //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private AttachmentConstraintBeanImpl(AttachmentConstraintBean bean, URL actualLocation, boolean isServiceUrl) {
        super(bean, actualLocation, isServiceUrl);
    }

    /**
     * Instantiates a new Attachment constraint bean.
     *
     * @param type the type
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public AttachmentConstraintBeanImpl(AttachmentConstraintType type) {
        super(type, SDMX_STRUCTURE_TYPE.ATTACHMENT_CONSTRAINT);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public AttachmentConstraintBean getStub(URL actualLocation, boolean isServiceUrl) {
        return new AttachmentConstraintBeanImpl(this, actualLocation, isServiceUrl);
    }

    @Override
    public MaintainableMutableBean getMutableInstance() {
        //FUNC 2.1 AttachmentConstraintBeanImpl.getMutableInstance
        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "AttachmentConstraint.getMutableInstance()");
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
            return super.deepEqualsInternal((AttachmentConstraintBean) bean, includeFinalProperties);
        }
        return false;
    }
}
