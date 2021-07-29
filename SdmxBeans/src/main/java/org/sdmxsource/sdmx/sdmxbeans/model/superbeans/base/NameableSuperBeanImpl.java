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
package org.sdmxsource.sdmx.sdmxbeans.model.superbeans.base;

import org.sdmxsource.sdmx.api.model.beans.base.NameableBean;
import org.sdmxsource.sdmx.api.model.superbeans.base.NameableSuperBean;
import org.sdmxsource.sdmx.util.beans.LocaleUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * The type Nameable super bean.
 */
public abstract class NameableSuperBeanImpl extends IdentifiableSuperBeanImpl implements NameableSuperBean {

    private static final long serialVersionUID = 1L;

    private Map<Locale, String> descriptions;
    private Map<Locale, String> names;

    /**
     * Instantiates a new Nameable super bean.
     *
     * @param identifiable the identifiable
     */
    public NameableSuperBeanImpl(NameableBean identifiable) {
        super(identifiable);
        names = LocaleUtil.buildLocalMap(identifiable.getNames());
        descriptions = LocaleUtil.buildLocalMap(identifiable.getDescriptions());
    }

    @Override
    public Map<Locale, String> getDescriptions() {
        return new HashMap<Locale, String>(descriptions);
    }

    @Override
    public Map<Locale, String> getNames() {
        return new HashMap<Locale, String>(names);
    }

    /* (non-Javadoc)
     * @see org.sdmxsource.dataweb.external.model.Identifiable#getDesciption(java.util.Locale)
     */
    @Override
    public String getDescription(Locale loc) {
        String returnStr = this.descriptions.get(loc);
        if (returnStr == null && this.descriptions.size() > 0) {
            return (String) this.descriptions.values().toArray()[0];
        }
        return returnStr;
    }

    /* (non-Javadoc)
     * @see org.sdmxsource.dataweb.external.model.Identifiable#getDesciption()
     */
    @Override
    public String getDescription() {
        if (descriptions == null || descriptions.size() == 0) {
            return null;
        }
        String returnStr = LocaleUtil.getStringByDefaultLocale(descriptions);
        if (returnStr == null && this.descriptions.size() > 0) {
            return (String) this.descriptions.values().toArray()[0];
        }
        return returnStr;
    }

    /* (non-Javadoc)
     * @see org.sdmxsource.dataweb.external.model.Identifiable#getName(java.util.Locale)
     */
    @Override
    public String getName(Locale loc) {
        return this.names.get(loc);
    }

    /* (non-Javadoc)
     * @see org.sdmxsource.dataweb.external.model.Identifiable#getName()
     */
    @Override
    public String getName() {  //TODO This should probably be better!
        if (names == null || names.size() == 0) {
            return null;
        }
        String returnStr = LocaleUtil.getStringByDefaultLocale(names);
        if (returnStr == null) {
            return (String) this.names.values().toArray()[0];
        }
        return returnStr;
    }
}
