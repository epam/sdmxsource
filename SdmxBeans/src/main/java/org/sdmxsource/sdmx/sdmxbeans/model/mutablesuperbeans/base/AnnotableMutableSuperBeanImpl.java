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

import org.sdmxsource.sdmx.api.model.beans.base.AnnotableBean;
import org.sdmxsource.sdmx.api.model.beans.base.AnnotationBean;
import org.sdmxsource.sdmx.api.model.mutablesuperbeans.base.AnnotableMutableSuperBean;
import org.sdmxsource.sdmx.api.model.mutablesuperbeans.base.AnnotationMutableSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.base.AnnotableSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.base.AnnotationSuperBean;

import java.util.HashSet;
import java.util.Set;


/**
 * The type Annotable mutable super bean.
 *
 * @author Matt Nelson
 */
public abstract class AnnotableMutableSuperBeanImpl implements AnnotableMutableSuperBean {
    private static final long serialVersionUID = 1L;

    private Set<AnnotationMutableSuperBean> annotations = new HashSet<AnnotationMutableSuperBean>();

    /**
     * Instantiates a new Annotable mutable super bean.
     *
     * @param superBean the super bean
     */
    public AnnotableMutableSuperBeanImpl(AnnotableSuperBean superBean) {
        if (superBean.getAnnotations() != null) {
            for (AnnotationSuperBean currentBean : superBean.getAnnotations()) {
                annotations.add(new AnnotationMutableSuperBeanImpl(currentBean));
            }
        }
    }

    /**
     * Instantiates a new Annotable mutable super bean.
     *
     * @param superBean the super bean
     */
    public AnnotableMutableSuperBeanImpl(AnnotableBean superBean) {
        if (superBean.getAnnotations() != null) {
            for (AnnotationBean currentBean : superBean.getAnnotations()) {
                annotations.add(new AnnotationMutableSuperBeanImpl(currentBean));
            }
        }
    }

    /**
     * Instantiates a new Annotable mutable super bean.
     */
    public AnnotableMutableSuperBeanImpl() {
    }

    @Override
    public Set<AnnotationMutableSuperBean> getAnnotations() {
        return annotations;
    }

    @Override
    public void setAnnotations(Set<AnnotationMutableSuperBean> annotations) {
        this.annotations = annotations;
    }
}
