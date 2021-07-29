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
package org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.base;

import org.sdmxsource.sdmx.api.model.beans.base.NameableBean;
import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.sdmx.api.model.mutable.base.TextTypeWrapperMutableBean;
import org.sdmxsource.sdmx.api.model.mutablesuperbeans.base.NameableMutableSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.base.NameableSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.TextTypeWrapperMutableBeanImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * The type Nameable mutable super bean.
 */
public class NameableMutableSuperBeanImpl extends IdentifiableMutableSuperBeanImpl implements NameableMutableSuperBean {
    private static final long serialVersionUID = 1L;
    private List<TextTypeWrapperMutableBean> descriptions = new ArrayList<TextTypeWrapperMutableBean>();
    private List<TextTypeWrapperMutableBean> names = new ArrayList<TextTypeWrapperMutableBean>();


    /**
     * Instantiates a new Nameable mutable super bean.
     *
     * @param identifiable the identifiable
     */
    public NameableMutableSuperBeanImpl(NameableSuperBean identifiable) {
        super(identifiable);
        //HACK This is a hack
        if (identifiable.getDescriptions() != null) {
            for (Locale locale : identifiable.getDescriptions().keySet()) {
                TextTypeWrapperMutableBean mBean = new TextTypeWrapperMutableBeanImpl();
                mBean.setLocale(locale.getLanguage());
                mBean.setValue(identifiable.getDescriptions().get(locale));
                descriptions.add(mBean);
            }
        }
        if (identifiable.getNames() != null) {
            for (Locale locale : identifiable.getNames().keySet()) {
                TextTypeWrapperMutableBean mBean = new TextTypeWrapperMutableBeanImpl();
                mBean.setLocale(locale.getLanguage());
                mBean.setValue(identifiable.getNames().get(locale));
                names.add(mBean);
            }
        }
    }

    /**
     * Instantiates a new Nameable mutable super bean.
     *
     * @param identifiable the identifiable
     */
    public NameableMutableSuperBeanImpl(NameableBean identifiable) {
        super(identifiable);
        if (identifiable.getDescription() != null) {
            for (TextTypeWrapper currentTextType : identifiable.getDescriptions()) {
                descriptions.add(new TextTypeWrapperMutableBeanImpl(currentTextType));
            }
        }
        if (identifiable.getName() != null) {
            for (TextTypeWrapper currentTextType : identifiable.getNames()) {
                names.add(new TextTypeWrapperMutableBeanImpl(currentTextType));
            }
        }
    }

    /**
     * Instantiates a new Nameable mutable super bean.
     */
    public NameableMutableSuperBeanImpl() {

    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        if (names != null && names.size() > 0) {
            return names.get(0).getValue();
        }
        return null;
    }

    @Override
    public List<TextTypeWrapperMutableBean> getDescriptions() {
        return descriptions;
    }

    @Override
    public void setDescriptions(List<TextTypeWrapperMutableBean> descriptions) {
        this.descriptions = descriptions;
    }

    @Override
    public List<TextTypeWrapperMutableBean> getNames() {
        return names;
    }

    @Override
    public void setNames(List<TextTypeWrapperMutableBean> names) {
        this.names = names;
    }

}
