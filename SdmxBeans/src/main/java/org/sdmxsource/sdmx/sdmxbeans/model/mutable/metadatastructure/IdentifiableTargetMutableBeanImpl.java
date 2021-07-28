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
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.IdentifiableTargetBean;
import org.sdmxsource.sdmx.api.model.mutable.metadatastructure.IdentifiableTargetMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.ComponentMutableBeanImpl;


/**
 * The type Identifiable target mutable bean.
 */
public class IdentifiableTargetMutableBeanImpl extends ComponentMutableBeanImpl implements IdentifiableTargetMutableBean {
    private static final long serialVersionUID = 1L;

    private SDMX_STRUCTURE_TYPE referencedStructureType;

    /**
     * Instantiates a new Identifiable target mutable bean.
     *
     * @param bean the bean
     */
    public IdentifiableTargetMutableBeanImpl(IdentifiableTargetBean bean) {
        super(bean);
        this.referencedStructureType = bean.getReferencedStructureType();
    }

    /**
     * Instantiates a new Identifiable target mutable bean.
     */
    public IdentifiableTargetMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.IDENTIFIABLE_OBJECT_TARGET);
    }

    @Override
    public SDMX_STRUCTURE_TYPE getReferencedStructureType() {
        return referencedStructureType;
    }

    @Override
    public void setReferencedStructureType(SDMX_STRUCTURE_TYPE structureType) {
        this.referencedStructureType = structureType;
    }
}
