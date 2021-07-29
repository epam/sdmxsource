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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.base;

import org.apache.xmlbeans.XmlCursor;
import org.sdmx.resources.sdmxml.schemas.v20.common.TextType;
import org.sdmx.resources.sdmxml.schemas.v21.common.XHTMLType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.sdmx.api.model.mutable.base.TextTypeWrapperMutableBean;
import org.sdmxsource.util.ObjectUtil;

import java.util.HashSet;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;


/**
 * The type Text type wrapper.
 */
public class TextTypeWrapperImpl extends SDMXBeanImpl implements TextTypeWrapper {
    private static final long serialVersionUID = 1L;
    private String locale;
    private String value;
    private boolean isHtmlText;


    /**
     * Instantiates a new Text type wrapper.
     *
     * @param locale the locale
     * @param value  the value
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM VALUES BEAN				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public TextTypeWrapperImpl(String locale, String value, SDMXBean parent) {
        super(SDMX_STRUCTURE_TYPE.TEXT_TYPE, parent);
        setLocale(locale);
        setValue(value);
        validate();
    }

    /**
     * Instantiates a new Text type wrapper.
     *
     * @param textType the text type
     * @param parent   the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEAN				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public TextTypeWrapperImpl(TextTypeWrapperMutableBean textType, SDMXBean parent) {
        super(textType, parent);
        setLocale(textType.getLocale());
        setValue(textType.getValue());
        validate();
    }


    /**
     * Instantiates a new Text type wrapper.
     *
     * @param textType the text type
     * @param parent   the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public TextTypeWrapperImpl(org.sdmx.resources.sdmxml.schemas.v21.common.TextType textType, SDMXBean parent) {
        super(SDMX_STRUCTURE_TYPE.TEXT_TYPE, parent);
        setLocale(textType.getLang());
        setValue(textType.getStringValue());
        validate();
    }

    /**
     * Instantiates a new Text type wrapper.
     *
     * @param textType the text type
     * @param parent   the parent
     */
    public TextTypeWrapperImpl(XHTMLType textType, SDMXBean parent) {
        super(SDMX_STRUCTURE_TYPE.TEXT_TYPE, parent);
        XmlCursor cursor = textType.newCursor();
        setLocale(textType.getLang());
        setValue(cursor.getTextValue());
        this.isHtmlText = true;
        validate();
    }

    /**
     * Instantiates a new Text type wrapper.
     *
     * @param textType the text type
     * @param parent   the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public TextTypeWrapperImpl(TextType textType, SDMXBean parent) {
        super(SDMX_STRUCTURE_TYPE.TEXT_TYPE, parent);
        setLocale(textType.getLang());
        setValue(textType.getStringValue());
        validate();
    }

    /**
     * Instantiates a new Text type wrapper.
     *
     * @param textType the text type
     * @param parent   the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public TextTypeWrapperImpl(org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.common.TextType textType, SDMXBean parent) {
        super(SDMX_STRUCTURE_TYPE.TEXT_TYPE, parent);
        setLocale(textType.getLang());
        setValue(textType.getStringValue());
        validate();
    }

    /**
     * Is valid locale boolean.
     *
     * @param value the value
     * @return the boolean
     */
    boolean isValidLocale(String value) {
        return isValid(parseLocale(value));
    }

    private Locale parseLocale(String locale) {
        String[] parts = locale.split("-");
        switch (parts.length) {
            case 3:
                return new Locale(parts[0], parts[1], parts[2]);
            case 2:
                return new Locale(parts[0], parts[1]);
            case 1:
                return new Locale(parts[0]);
            default:
                throw new IllegalArgumentException("Invalid locale: " + locale);
        }
    }

    private boolean isValid(Locale locale) {
        try {
            return locale.getISO3Language() != null && locale.getISO3Country() != null;
        } catch (MissingResourceException e) {
            return false;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////DEEP EQUALS							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean deepEquals(SDMXBean bean, boolean includeFinalProperties) {
        if (bean == null) {
            return false;
        }
        if (bean.getStructureType() == this.getStructureType()) {
            TextTypeWrapper that = (TextTypeWrapper) bean;
            if (!ObjectUtil.equivalent(locale, that.getLocale())) {
                return false;
            }
            if (!ObjectUtil.equivalent(value, that.getValue())) {
                return false;
            }
            if (isHtmlText != that.isHtml()) {
                return false;
            }
            return true;
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() throws SdmxSemmanticException {
        if (!ObjectUtil.validString(locale)) {
            //Default to english
            locale = Locale.ENGLISH.getLanguage();
        }
        if (!isValidLocale(locale)) {
            throw new SdmxSemmanticException("Illegal Locale: " + locale);
        }

        if (!ObjectUtil.validString(value)) {
            throw new SdmxSemmanticException("Text Type can not have an empty string value");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public String getLocale() {
        return locale;
    }

    private void setLocale(String locale) {
        if (!ObjectUtil.validString(locale)) {
            locale = "en";
        }
        //Bug fix, in XML Locale contains a '-' to be valid, in Java '_' is used
        locale = locale.replace("_", "-");
        this.locale = locale;
    }

    @Override
    public String getValue() {
        return value;
    }

    private void setValue(String value) {
        if (value != null) {
            this.value = value.trim();
        }
    }

    @Override
    public boolean isHtml() {
        return isHtmlText;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES                           //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        return new HashSet<SDMXBean>();
    }
}
