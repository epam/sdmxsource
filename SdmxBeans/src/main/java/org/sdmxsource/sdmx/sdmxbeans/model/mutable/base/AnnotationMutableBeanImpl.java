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
import org.sdmxsource.sdmx.api.model.beans.base.AnnotationBean;
import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.sdmx.api.model.mutable.base.AnnotationMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.TextTypeWrapperMutableBean;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Annotation mutable bean.
 */
public class AnnotationMutableBeanImpl extends MutableBeanImpl implements AnnotationMutableBean {
    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    private String type;
    private String uri;
    private List<TextTypeWrapperMutableBean> text;

    /**
     * Instantiates a new Annotation mutable bean.
     */
    public AnnotationMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.ANNOTATION);
    }

    /**
     * Instantiates a new Annotation mutable bean.
     *
     * @param annotation the annotation
     */
    public AnnotationMutableBeanImpl(AnnotationBean annotation) {
        super(annotation);
        this.id = annotation.getId();
        this.title = annotation.getTitle();
        this.type = annotation.getType();
        if (annotation.getUri() != null) {
            this.uri = annotation.getUri().toString();
        }
        if (annotation.getText() != null) {
            text = new ArrayList<TextTypeWrapperMutableBean>();
            for (TextTypeWrapper currentTextType : annotation.getText()) {
                text.add(new TextTypeWrapperMutableBeanImpl(currentTextType));
            }
        }
    }

    /**
     * Instantiates a new Annotation mutable bean.
     *
     * @param reader the reader
     */
    public AnnotationMutableBeanImpl(SdmxReader reader) {
        super(SDMX_STRUCTURE_TYPE.ANNOTATION);
        this.id = reader.getAttributeValue("id", false);

        reader.moveNextElement();
        while (processReader(reader)) {
            reader.moveNextElement();
        }
    }

    /**
     * Process reader boolean.
     *
     * @param reader the reader
     * @return the boolean
     */
    protected boolean processReader(SdmxReader reader) {
        if (reader.getCurrentElement().equals("AnnotationType")) {
            this.type = reader.getCurrentElementValue();
            return true;
        }
        if (reader.getCurrentElement().equals("AnnotationTitle")) {
            this.title = reader.getCurrentElementValue();
            return true;
        }
        if (reader.getCurrentElement().equals("AnnotationText")) {
            addText(reader.getAttributeValue("lang", false), reader.getCurrentElementValue());
            return true;
        }
        if (reader.getCurrentElement().equals("AnnotationURL")) {
            this.uri = reader.getCurrentElementValue();
            return true;
        }
        return false;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String value) {
        this.id = value;
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public List<TextTypeWrapperMutableBean> getText() {
        return text;
    }

    @Override
    public void setText(List<TextTypeWrapperMutableBean> text) {
        this.text = text;
    }

    @Override
    public void addText(TextTypeWrapperMutableBean text) {
        if (this.text == null) {
            this.text = new ArrayList<TextTypeWrapperMutableBean>();
        }
        this.text.add(text);
    }

    @Override
    public void addText(String locale, String text) {
        if (this.text == null) {
            this.text = new ArrayList<TextTypeWrapperMutableBean>();
        }
        TextTypeWrapperMutableBean tt = new TextTypeWrapperMutableBeanImpl();
        tt.setLocale(locale);
        tt.setValue(text);
        this.text.add(tt);
    }
}
