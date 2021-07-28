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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.datastructure;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.datastructure.GroupBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.GroupMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.IdentifiableMutableBeanImpl;

import java.util.List;


/**
 * The type Group mutable bean.
 */
public class GroupMutableBeanImpl extends IdentifiableMutableBeanImpl implements GroupMutableBean {
    private static final long serialVersionUID = 1L;

    private List<String> dimensionRef;
    private StructureReferenceBean attachmentConstraintRef;

    /**
     * Instantiates a new Group mutable bean.
     */
    public GroupMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.GROUP);
    }

    /**
     * Instantiates a new Group mutable bean.
     *
     * @param bean the bean
     */
    public GroupMutableBeanImpl(GroupBean bean) {
        super(bean);
        if (bean.getAttachmentConstraintRef() != null) {
            this.attachmentConstraintRef = bean.getAttachmentConstraintRef();
        }
        this.dimensionRef = bean.getDimensionRefs();
    }

    @Override
    public StructureReferenceBean getAttachmentConstraintRef() {
        return attachmentConstraintRef;
    }

    @Override
    public void setAttachmentConstraintRef(StructureReferenceBean attachmentConstraintRef) {
        this.attachmentConstraintRef = attachmentConstraintRef;
    }

    @Override
    public List<String> getDimensionRef() {
        return dimensionRef;
    }

    @Override
    public void setDimensionRef(List<String> dimensionRef) {
        this.dimensionRef = dimensionRef;
    }
}
