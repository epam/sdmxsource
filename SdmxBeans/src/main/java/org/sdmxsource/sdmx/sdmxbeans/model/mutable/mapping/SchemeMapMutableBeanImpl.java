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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.mapping;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.mapping.SchemeMapBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.base.SchemeMapMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.NameableMutableBeanImpl;


/**
 * The type Scheme map mutable bean.
 */
public abstract class SchemeMapMutableBeanImpl extends NameableMutableBeanImpl implements SchemeMapMutableBean {

    private static final long serialVersionUID = 1L;

    private StructureReferenceBean sourceRef;
    private StructureReferenceBean targetRef;

    /**
     * Instantiates a new Scheme map mutable bean.
     *
     * @param structureType the structure type
     */
    public SchemeMapMutableBeanImpl(SDMX_STRUCTURE_TYPE structureType) {
        super(structureType);
    }

    /**
     * Instantiates a new Scheme map mutable bean.
     *
     * @param bean the bean
     */
    public SchemeMapMutableBeanImpl(SchemeMapBean bean) {
        super(bean);
        if (bean.getSourceRef() != null) {
            this.sourceRef = bean.getSourceRef().createMutableInstance();
        }
        if (bean.getTargetRef() != null) {
            this.targetRef = bean.getTargetRef().createMutableInstance();
        }
    }

    @Override
    public StructureReferenceBean getSourceRef() {
        return sourceRef;
    }

    @Override
    public void setSourceRef(StructureReferenceBean sourceRef) {
        this.sourceRef = sourceRef;
    }

    @Override
    public StructureReferenceBean getTargetRef() {
        return targetRef;
    }

    @Override
    public void setTargetRef(StructureReferenceBean targetRef) {
        this.targetRef = targetRef;
    }
}

	
