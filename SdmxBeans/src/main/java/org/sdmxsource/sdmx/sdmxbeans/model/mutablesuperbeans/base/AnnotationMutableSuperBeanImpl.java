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
package org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.base;

import org.sdmxsource.sdmx.api.model.beans.base.AnnotationBean;
import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.sdmx.api.model.mutable.base.TextTypeWrapperMutableBean;
import org.sdmxsource.sdmx.api.model.mutablesuperbeans.base.AnnotationMutableSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.base.AnnotationSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.TextTypeWrapperMutableBeanImpl;

import java.util.List;


/**
 * The type Annotation mutable super bean.
 *
 * @author Matt Nelson
 */
public class AnnotationMutableSuperBeanImpl implements AnnotationMutableSuperBean {
    private static final long serialVersionUID = 1L;

    private String title;
    private String url;
    private String type;
    private List<TextTypeWrapperMutableBean> texts;


    /**
     * Instantiates a new Annotation mutable super bean.
     *
     * @param annotation the annotation
     */
    public AnnotationMutableSuperBeanImpl(AnnotationSuperBean annotation) {
        this.title = annotation.getTitle();
        if (annotation.getUri() != null) {
            this.url = annotation.getUri().toString();
        }
        this.type = annotation.getType();
        //HACK This is a hack
//		if(annotation.getTexts() != null) {
//			for (TextTypeWrapper currentTextType : annotation.getTexts()) {
//				this.texts.add(new TextTypeWrapperMutableBeanImpl(currentTextType));
//			}
//		}
    }

    /**
     * Instantiates a new Annotation mutable super bean.
     *
     * @param annotation the annotation
     */
    public AnnotationMutableSuperBeanImpl(AnnotationBean annotation) {
        this.title = annotation.getTitle();
        if (annotation.getUri() != null) {
            this.url = annotation.getUri().toString();
        }
        this.type = annotation.getType();
        if (annotation.getText() != null) {
            for (TextTypeWrapper currentTextType : annotation.getText()) {
                this.texts.add(new TextTypeWrapperMutableBeanImpl(currentTextType));
            }
        }
    }

    /**
     * Instantiates a new Annotation mutable super bean.
     */
    public AnnotationMutableSuperBeanImpl() {
    }

    @Override
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getUrl() {
        return url;
    }

    /**
     * Sets url.
     *
     * @param url the url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public List<TextTypeWrapperMutableBean> getTexts() {
        return texts;
    }

    @Override
    public void setTexts(List<TextTypeWrapperMutableBean> texts) {
        this.texts = texts;
    }
}
