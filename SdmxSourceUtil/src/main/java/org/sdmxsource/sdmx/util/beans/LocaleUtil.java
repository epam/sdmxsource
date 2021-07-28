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
package org.sdmxsource.sdmx.util.beans;

import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.util.ObjectUtil;

import java.util.*;


/**
 * The type Locale util.
 */
public class LocaleUtil {
    //TODO should this be user defined?
    private static List<Locale> defaultLocales;

    /**
     * Gets default locales.
     *
     * @return the default locales
     */
    public static List<Locale> getDefaultLocales() {
        if (defaultLocales == null) {
            defaultLocales = new ArrayList<Locale>();
            defaultLocales.add(Locale.ENGLISH);
        }
        return defaultLocales;
    }

    /**
     * Gets string by default locale.
     *
     * @param localToStringMap the local to string map
     * @return the string by default locale
     */
    public static String getStringByDefaultLocale(Map<Locale, String> localToStringMap) {
        if (!ObjectUtil.validCollection(LocaleUtil.getDefaultLocales())) {
            throw new IllegalArgumentException("No Default Locale found");
        }
        for (Locale currentLocale : LocaleUtil.getDefaultLocales()) {
            String str = localToStringMap.get(currentLocale);
            if (ObjectUtil.validString(str)) {
                return str;
            }
        }
        return null;
    }

    /**
     * Build local map map.
     *
     * @param textTypes the text types
     * @return the map
     */
    public static Map<Locale, String> buildLocalMap(List<TextTypeWrapper> textTypes) {
        if (textTypes == null) {
            return null;
        }
        Map<Locale, String> buildLocalMap = new HashMap<Locale, String>();
        for (TextTypeWrapper currentTextType : textTypes) {
            String lang = currentTextType.getLocale();
            String value = currentTextType.getValue();
            Locale loc = new Locale(lang);
            buildLocalMap.put(loc, value);
        }
        return buildLocalMap;
    }

    /**
     * Sets default locale.
     *
     * @param defaultLocale the default locale
     */
    public void setDefaultLocale(Locale defaultLocale) {
        defaultLocales = new ArrayList<Locale>();
        defaultLocales.add(defaultLocale);
    }

    /**
     * Sets default locale.
     *
     * @param defaultLocales the default locales
     */
    public void setDefaultLocale(List<Locale> defaultLocales) {
        LocaleUtil.defaultLocales = defaultLocales;
    }
}
