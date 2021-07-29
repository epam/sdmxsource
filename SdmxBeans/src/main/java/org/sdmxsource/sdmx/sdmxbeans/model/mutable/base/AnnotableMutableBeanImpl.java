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
import org.sdmxsource.sdmx.api.model.beans.base.AnnotableBean;
import org.sdmxsource.sdmx.api.model.beans.base.AnnotationBean;
import org.sdmxsource.sdmx.api.model.mutable.base.AnnotableMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.AnnotationMutableBean;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Annotable mutable bean.
 */
public abstract class AnnotableMutableBeanImpl extends MutableBeanImpl implements AnnotableMutableBean {
    private static final long serialVersionUID = 1L;
    private List<AnnotationMutableBean> annotations = new ArrayList<AnnotationMutableBean>();

    /**
     * Instantiates a new Annotable mutable bean.
     *
     * @param structureType the structure type
     */
    public AnnotableMutableBeanImpl(SDMX_STRUCTURE_TYPE structureType) {
        super(structureType);
    }

    /**
     * Instantiates a new Annotable mutable bean.
     *
     * @param bean the bean
     */
    public AnnotableMutableBeanImpl(AnnotableBean bean) {
        super(bean);
        if (bean.getAnnotations() != null) {
            for (AnnotationBean annotationBean : bean.getAnnotations()) {
                annotations.add(new AnnotationMutableBeanImpl(annotationBean));
            }
        }
    }

    /**
     * Process reader boolean.
     *
     * @param reader the reader
     * @return the boolean
     */
    protected boolean processReader(SdmxReader reader) {
        if (reader.getCurrentElement().equals("Annotations")) {
            reader.moveNextElement();
            while (reader.getCurrentElement().equals("Annotation")) {
                addAnnotation(new AnnotationMutableBeanImpl(reader));
            }
        }
        return false;
    }

    @Override
    public List<AnnotationMutableBean> getAnnotations() {
        return annotations;
    }

    @Override
    public void setAnnotations(List<AnnotationMutableBean> annotations) {
        this.annotations = annotations;
    }

    @Override
    public void addAnnotation(AnnotationMutableBean annotation) {
        if (annotations == null) {
            annotations = new ArrayList<AnnotationMutableBean>();
        }
        this.annotations.add(annotation);
    }

    @Override
    public AnnotationMutableBean addAnnotation(String title, String type, String url) {
        AnnotationMutableBean mutable = new AnnotationMutableBeanImpl();
        mutable.setTitle(title);
        mutable.setType(type);
        mutable.setUri(url);
        addAnnotation(mutable);
        return mutable;
    }
}
