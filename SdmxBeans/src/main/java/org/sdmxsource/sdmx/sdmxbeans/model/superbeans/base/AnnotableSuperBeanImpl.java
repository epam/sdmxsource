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

import org.sdmxsource.sdmx.api.model.beans.base.AnnotableBean;
import org.sdmxsource.sdmx.api.model.beans.base.AnnotationBean;
import org.sdmxsource.sdmx.api.model.superbeans.base.AnnotableSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.base.AnnotationSuperBean;
import org.sdmxsource.util.ObjectUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * The type Annotable super bean.
 *
 * @author Matt Nelson
 */
public abstract class AnnotableSuperBeanImpl extends SuperBeanImpl implements AnnotableSuperBean {
    private static final long serialVersionUID = 1L;

    private Set<AnnotationSuperBean> annotations = new HashSet<AnnotationSuperBean>();
    private Map<String, AnnotationSuperBean> annotationByType = new HashMap<String, AnnotationSuperBean>();

    /**
     * Instantiates a new Annotable super bean.
     *
     * @param annotableType the annotable type
     */
    public AnnotableSuperBeanImpl(AnnotableBean annotableType) {
        super(annotableType);
        if (annotableType != null && annotableType.getAnnotations() != null) {
            for (AnnotationBean currentAnnotation : annotableType.getAnnotations()) {
                AnnotationSuperBean annotation = new AnnotationSuperBeanImpl(currentAnnotation);
                annotations.add(annotation);
                if (ObjectUtil.validString(annotation.getType())) {
                    annotationByType.put(annotation.getType(), annotation);
                }
            }
        }
    }

    /* (non-Javadoc)
     * @see org.sdmxsource.dataweb.external.model.Annotable#getAnnotationByTitle(java.lang.String)
     */
    @Override
    public Set<AnnotationSuperBean> getAnnotationByTitle(String title) {
        Set<AnnotationSuperBean> returnSet = new HashSet<AnnotationSuperBean>();
        if (hasAnnotations()) {
            for (AnnotationSuperBean currentAnnotation : annotations) {
                if (currentAnnotation.getTitle() != null && currentAnnotation.getTitle().equals(title)) {
                    returnSet.add(currentAnnotation);
                }
            }
        }
        return returnSet;
    }

    /* (non-Javadoc)
     * @see org.sdmxsource.dataweb.external.model.Annotable#getAnnotationByType(java.lang.String)
     */
    @Override
    public AnnotationSuperBean getAnnotationByType(String type) {
        return annotationByType.get(type);
    }

    /* (non-Javadoc)
     * @see org.sdmxsource.dataweb.external.model.Annotable#getAnnotationByUrl(java.lang.String)
     */
    @Override
    public Set<AnnotationSuperBean> getAnnotationByUrl(String url) {
        Set<AnnotationSuperBean> returnSet = new HashSet<AnnotationSuperBean>();
        if (hasAnnotations()) {
            for (AnnotationSuperBean currentAnnotation : annotations) {
                if (currentAnnotation.getUri() != null && currentAnnotation.getUri().toString().equals(url)) {
                    returnSet.add(currentAnnotation);
                }
            }
        }
        return returnSet;
    }

    /* (non-Javadoc)
     * @see org.sdmxsource.dataweb.external.model.Annotable#getAnnotations()
     */
    @Override
    public Set<AnnotationSuperBean> getAnnotations() {
        return new HashSet<AnnotationSuperBean>(this.annotations);
    }

    /* (non-Javadoc)
     * @see org.sdmxsource.dataweb.external.model.Annotable#hasAnnotations()
     */
    @Override
    public boolean hasAnnotations() {
        return this.annotations != null && this.annotations.size() > 0;
    }
}
