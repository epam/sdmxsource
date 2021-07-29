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
import org.sdmxsource.sdmx.api.model.beans.datastructure.AttributeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.AttributeListBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.AttributeListMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.AttributeMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.IdentifiableMutableBeanImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Attribute list mutable bean.
 */
public class AttributeListMutableBeanImpl extends IdentifiableMutableBeanImpl implements AttributeListMutableBean {
    private static final long serialVersionUID = 7413119331825537279L;
    private List<AttributeMutableBean> attributes = new ArrayList<AttributeMutableBean>();

    /**
     * Instantiates a new Attribute list mutable bean.
     */
    public AttributeListMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.ATTRIBUTE_DESCRIPTOR);
    }

    /**
     * Instantiates a new Attribute list mutable bean.
     *
     * @param bean the bean
     */
    public AttributeListMutableBeanImpl(AttributeListBean bean) {
        super(bean);
        if (bean.getAttributes() != null) {
            for (AttributeBean currentAttribute : bean.getAttributes()) {
                attributes.add(new AttributeMutableBeanImpl(currentAttribute));
            }
        }
    }

    @Override
    public List<AttributeMutableBean> getAttributes() {
        return attributes;
    }

    @Override
    public void setAttributes(List<AttributeMutableBean> attributes) {
        this.attributes = attributes;
    }

    @Override
    public void addAttribute(AttributeMutableBean attribute) {
        if (attributes == null) {
            this.attributes = new ArrayList<AttributeMutableBean>();
        }
        this.attributes.add(attribute);
    }
}
