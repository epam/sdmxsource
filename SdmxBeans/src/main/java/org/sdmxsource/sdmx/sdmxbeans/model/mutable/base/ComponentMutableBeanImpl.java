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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.base;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.ComponentBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.base.ComponentMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.RepresentationMutableBean;

/**
 * The type Component mutable bean.
 */
public abstract class ComponentMutableBeanImpl extends IdentifiableMutableBeanImpl implements ComponentMutableBean {
    private static final long serialVersionUID = 1L;
    private RepresentationMutableBean representation;
    private StructureReferenceBean conceptRef;

    /**
     * Instantiates a new Component mutable bean.
     *
     * @param structureType the structure type
     */
    public ComponentMutableBeanImpl(SDMX_STRUCTURE_TYPE structureType) {
        super(structureType);
    }

    /**
     * Instantiates a new Component mutable bean.
     *
     * @param bean the bean
     */
    public ComponentMutableBeanImpl(ComponentBean bean) {
        super(bean);
        if (bean.getRepresentation() != null) {
            representation = new RepresentationMutableBeanImpl(bean.getRepresentation());
        }
        if (bean.getConceptRef() != null) {
            this.conceptRef = bean.getConceptRef().createMutableInstance();
        }
    }

    @Override
    public StructureReferenceBean getConceptRef() {
        return conceptRef;
    }

    @Override
    public void setConceptRef(StructureReferenceBean conceptRef) {
        this.conceptRef = conceptRef;
    }

    @Override
    public RepresentationMutableBean getRepresentation() {
        return representation;
    }

    @Override
    public void setRepresentation(RepresentationMutableBean representation) {
        this.representation = representation;
    }
}
