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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.registry;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.registry.AttachmentConstraintBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.AttachmentConstraintMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.registry.AttachmentConstraintBeanImpl;

/**
 * The type Attachment constraint mutable bean.
 */
public class AttachmentConstraintMutableBeanImpl extends ConstraintMutableBeanImpl implements AttachmentConstraintMutableBean {
    private static final long serialVersionUID = 1860337398769051088L;

    /**
     * Instantiates a new Attachment constraint mutable bean.
     *
     * @param bean the bean
     */
    public AttachmentConstraintMutableBeanImpl(AttachmentConstraintBean bean) {
        super(bean);
    }

    /**
     * Instantiates a new Attachment constraint mutable bean.
     */
    public AttachmentConstraintMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.ATTACHMENT_CONSTRAINT);
    }

    @Override
    public AttachmentConstraintBean getImmutableInstance() {
        return new AttachmentConstraintBeanImpl(this);
    }

}
