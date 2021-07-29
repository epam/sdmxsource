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
import org.sdmxsource.sdmx.api.model.beans.mapping.CategoryMapBean;
import org.sdmxsource.sdmx.api.model.mutable.mapping.CategoryMapMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.MutableBeanImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Category map mutable bean.
 */
public class CategoryMapMutableBeanImpl extends MutableBeanImpl implements CategoryMapMutableBean {
    private static final long serialVersionUID = -8052410592565621383L;
    private String alias;
    private List<String> sourceId = new ArrayList<String>();
    private List<String> targetId = new ArrayList<String>();

    /**
     * Instantiates a new Category map mutable bean.
     */
    public CategoryMapMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.CATEGORY_MAP);
    }

    /**
     * Instantiates a new Category map mutable bean.
     *
     * @param catType the cat type
     */
    public CategoryMapMutableBeanImpl(CategoryMapBean catType) {
        super(catType);
        this.alias = catType.getAlias();
        if (catType.getSourceId() != null) {
            this.sourceId = catType.getSourceId();
        }
        if (catType.getTargetId() != null) {
            this.targetId = catType.getTargetId();
        }
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public List<String> getSourceId() {
        return sourceId;
    }

    @Override
    public void setSourceId(List<String> sourceId) {
        this.sourceId = sourceId;
    }

    @Override
    public List<String> getTargetId() {
        return targetId;
    }

    @Override
    public void setTargetId(List<String> targetId) {
        this.targetId = targetId;
    }
}
