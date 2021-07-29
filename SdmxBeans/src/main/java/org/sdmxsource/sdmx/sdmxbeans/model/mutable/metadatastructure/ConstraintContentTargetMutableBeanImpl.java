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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.metadatastructure;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TEXT_TYPE;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.ConstraintContentTargetBean;
import org.sdmxsource.sdmx.api.model.mutable.metadatastructure.ConstraintContentTargetMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.IdentifiableMutableBeanImpl;


/**
 * The type Constraint content target mutable bean.
 */
public class ConstraintContentTargetMutableBeanImpl extends IdentifiableMutableBeanImpl implements ConstraintContentTargetMutableBean {
    private static final long serialVersionUID = 1L;

    private TEXT_TYPE textType;

    /**
     * Instantiates a new Constraint content target mutable bean.
     *
     * @param bean the bean
     */
    public ConstraintContentTargetMutableBeanImpl(ConstraintContentTargetBean bean) {
        super(bean);
        this.textType = bean.getTextType();
    }

    /**
     * Instantiates a new Constraint content target mutable bean.
     */
    public ConstraintContentTargetMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.CONTENT_CONSTRAINT_ATTACHMENT);
    }

    @Override
    public TEXT_TYPE getTextType() {
        return textType;
    }

    @Override
    public void setTextType(TEXT_TYPE textType) {
        this.textType = textType;
    }
}
