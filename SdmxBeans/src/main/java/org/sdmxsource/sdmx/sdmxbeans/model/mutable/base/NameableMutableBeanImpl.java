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
import org.sdmxsource.sdmx.api.model.SdmxReader;
import org.sdmxsource.sdmx.api.model.beans.base.NameableBean;
import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.sdmx.api.model.mutable.base.NameableMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.TextTypeWrapperMutableBean;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Nameable mutable bean.
 */
public abstract class NameableMutableBeanImpl extends IdentifiableMutableBeanImpl implements NameableMutableBean {
    private static final long serialVersionUID = 1L;
    private List<TextTypeWrapperMutableBean> names;
    private List<TextTypeWrapperMutableBean> descriptions;

    /**
     * Instantiates a new Nameable mutable bean.
     *
     * @param structureType the structure type
     */
    public NameableMutableBeanImpl(SDMX_STRUCTURE_TYPE structureType) {
        super(structureType);
    }

    /**
     * Instantiates a new Nameable mutable bean.
     *
     * @param bean the bean
     */
    public NameableMutableBeanImpl(NameableBean bean) {
        super(bean);

        if (bean.getName() != null) {
            names = new ArrayList<TextTypeWrapperMutableBean>();
            for (TextTypeWrapper currentTextType : bean.getNames()) {
                names.add(new TextTypeWrapperMutableBeanImpl(currentTextType));
            }
        }
        if (bean.getDescription() != null) {
            descriptions = new ArrayList<TextTypeWrapperMutableBean>();
            for (TextTypeWrapper currentTextType : bean.getDescriptions()) {
                descriptions.add(new TextTypeWrapperMutableBeanImpl(currentTextType));
            }
        }
    }

    @Override
    protected boolean processReader(SdmxReader reader) {
        if (super.processReader(reader)) {
            return true;
        }
        if (reader.getCurrentElement().equals("Name")) {
            String lang = reader.getAttributeValue("lang", false);
            addName(lang, reader.getCurrentElementValue());
            return true;
        }
        if (reader.getCurrentElement().equals("Description")) {
            String lang = reader.getAttributeValue("lang", false);
            addDescription(lang, reader.getCurrentElementValue());
            return true;
        }
        return false;
    }

    @Override
    public void addDescription(String locale, String name) {
        if (this.descriptions == null) {
            this.descriptions = new ArrayList<TextTypeWrapperMutableBean>();
        }
        for (TextTypeWrapperMutableBean currentTT : this.descriptions) {
            if (currentTT.getLocale().equals(locale)) {
                currentTT.setValue(name);
                return;
            }
        }
        TextTypeWrapperMutableBean tt = new TextTypeWrapperMutableBeanImpl();
        tt.setLocale(locale);
        tt.setValue(name);
        this.descriptions.add(tt);
    }

    @Override
    public void addName(String locale, String name) {
        if (this.names == null) {
            this.names = new ArrayList<TextTypeWrapperMutableBean>();
        }
        for (TextTypeWrapperMutableBean currentTT : this.names) {
            if (currentTT.getLocale().equals(locale)) {
                currentTT.setValue(name);
                return;
            }
        }
        TextTypeWrapperMutableBean tt = new TextTypeWrapperMutableBeanImpl();
        tt.setLocale(locale);
        tt.setValue(name);
        this.names.add(tt);
    }


    @Override
    public String getName(boolean defaultIfNull) {
        //HACK This does not work properly
        for (TextTypeWrapperMutableBean mutable : getNames()) {
            return mutable.getValue();
        }
        return null;
    }

    @Override
    public List<TextTypeWrapperMutableBean> getNames() {
        return names;
    }

    @Override
    public void setNames(List<TextTypeWrapperMutableBean> names) {
        this.names = names;
    }

    @Override
    public List<TextTypeWrapperMutableBean> getDescriptions() {
        return descriptions;
    }

    @Override
    public void setDescriptions(List<TextTypeWrapperMutableBean> description) {
        this.descriptions = description;
    }
}
