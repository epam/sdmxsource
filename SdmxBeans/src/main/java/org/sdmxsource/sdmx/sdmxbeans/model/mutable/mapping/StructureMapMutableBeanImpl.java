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
import org.sdmxsource.sdmx.api.model.beans.mapping.ComponentMapBean;
import org.sdmxsource.sdmx.api.model.beans.mapping.StructureMapBean;
import org.sdmxsource.sdmx.api.model.mutable.base.StructureMapMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.mapping.ComponentMapMutableBean;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Structure map mutable bean.
 */
public class StructureMapMutableBeanImpl extends SchemeMapMutableBeanImpl implements StructureMapMutableBean {

    private static final long serialVersionUID = 1L;

    private List<ComponentMapMutableBean> components;
    private boolean extension;

    /**
     * Instantiates a new Structure map mutable bean.
     */
    public StructureMapMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.STRUCTURE_MAP);
    }

    /**
     * Instantiates a new Structure map mutable bean.
     *
     * @param bean the bean
     */
    public StructureMapMutableBeanImpl(StructureMapBean bean) {
        super(bean);

        extension = bean.isExtension();
        // create mutable list of component maps
        if (bean.getComponents() != null) {
            for (ComponentMapBean currentBean : bean.getComponents()) {
                this.addComponent(new ComponentMapMutableBeanImpl(currentBean));
            }
        }
    }

    // has Component Map bean

    @Override
    public List<ComponentMapMutableBean> getComponents() {
        return components;
    }


    @Override
    public void setComponents(List<ComponentMapMutableBean> components) {
        this.components = components;
    }

    @Override
    public void addComponent(ComponentMapMutableBean component) {
        if (components == null) {
            components = new ArrayList<ComponentMapMutableBean>();
        }
        this.components.add(component);
    }

    @Override
    public boolean isExtension() {
        return extension;
    }

    @Override
    public void setExtension(boolean extension) {
        this.extension = extension;
    }
}
