/**
 * Copyright (c) 2013 Metadata Technology Ltd.
 * <p>
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Lesser General Public License v 3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * This file is part of the SDMX Component Library.
 * <p>
 * The SDMX Component Library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * <p>
 * The SDMX Component Library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser
 * General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with The SDMX Component Library If not, see
 * http://www.gnu.org/licenses/lgpl.
 * <p>
 * Contributors:
 * Metadata Technology - initial API and implementation
 */
/**
 *
 */
package org.sdmxsource.sdmx.sdmxbeans.model.superbeans.base;

import org.sdmxsource.sdmx.api.model.beans.base.AnnotationBean;
import org.sdmxsource.sdmx.api.model.superbeans.base.AnnotationSuperBean;
import org.sdmxsource.sdmx.util.beans.LocaleUtil;

import java.net.URI;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * The type Annotation super bean.
 *
 * @author Matt Nelson
 */
public class AnnotationSuperBeanImpl implements AnnotationSuperBean {
    private static final long serialVersionUID = 1L;

    private String title;
    private URI uri;
    private String type;
    private Map<Locale, String> text;

    /**
     * Instantiates a new Annotation super bean.
     *
     * @param annotation the annotation
     */
    public AnnotationSuperBeanImpl(AnnotationBean annotation) {
        this.title = annotation.getTitle();
        this.uri = annotation.getUri();
        this.type = annotation.getType();
        this.text = LocaleUtil.buildLocalMap(annotation.getText());
    }

    @Override
    public Map<Locale, String> getTexts() {
        return new HashMap<Locale, String>(text);
    }

    @Override
    public String getText(Locale locale) {
        return this.title;
    }

    @Override
    public String getText() {
        return LocaleUtil.getStringByDefaultLocale(text);
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public URI getUri() {
        return this.uri;
    }
}
