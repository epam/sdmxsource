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
import org.sdmxsource.sdmx.api.model.beans.mapping.CodelistMapBean;
import org.sdmxsource.sdmx.api.model.mutable.mapping.CodelistMapMutableBean;

/**
 * The type Codelist map mutable bean.
 */
public class CodelistMapMutableBeanImpl extends ItemSchemeMapMutableBeanImpl implements CodelistMapMutableBean {
    private static final long serialVersionUID = -5966222908821523257L;
    private String srcAlias;
    private String targetAlias;

    /**
     * Instantiates a new Codelist map mutable bean.
     */
    public CodelistMapMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.CODE_LIST_MAP);
    }

    /**
     * Instantiates a new Codelist map mutable bean.
     *
     * @param bean the bean
     */
    public CodelistMapMutableBeanImpl(CodelistMapBean bean) {
        super(bean);
        setSrcAlias(bean.getSrcAlias());
        setTargetAlias(bean.getTargetAlias());
    }


    @Override
    public String getSrcAlias() {
        return srcAlias;
    }

    @Override
    public void setSrcAlias(String srcAlias) {
        this.srcAlias = srcAlias;
    }

    @Override
    public String getTargetAlias() {
        return targetAlias;
    }

    @Override
    public void setTargetAlias(String targetAlias) {
        this.targetAlias = targetAlias;
    }
}
