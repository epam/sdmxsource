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
import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.sdmx.api.model.mutable.base.TextTypeWrapperMutableBean;

/**
 * The type Text type wrapper mutable bean.
 */
public class TextTypeWrapperMutableBeanImpl extends MutableBeanImpl implements TextTypeWrapperMutableBean {
    private static final long serialVersionUID = 1L;
    private String locale;
    private String value;

    /**
     * Instantiates a new Text type wrapper mutable bean.
     */
    public TextTypeWrapperMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.TEXT_TYPE);
    }

    /**
     * Instantiates a new Text type wrapper mutable bean.
     *
     * @param locale the locale
     * @param value  the value
     */
    public TextTypeWrapperMutableBeanImpl(String locale, String value) {
        super(SDMX_STRUCTURE_TYPE.TEXT_TYPE);
        this.locale = locale;
        this.value = value;
    }

    /**
     * Instantiates a new Text type wrapper mutable bean.
     *
     * @param textType the text type
     */
    public TextTypeWrapperMutableBeanImpl(TextTypeWrapper textType) {
        super(textType);
        this.locale = textType.getLocale();
        this.value = textType.getValue();
    }

    @Override
    public String getLocale() {
        return locale;
    }

    @Override
    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

}
