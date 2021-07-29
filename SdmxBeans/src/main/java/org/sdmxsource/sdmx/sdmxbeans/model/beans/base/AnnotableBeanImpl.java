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

import org.apache.xmlbeans.XmlObject;
import org.sdmx.resources.sdmxml.schemas.v20.common.AnnotationType;
import org.sdmx.resources.sdmxml.schemas.v20.common.AnnotationsType;
import org.sdmx.resources.sdmxml.schemas.v21.common.AnnotableType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.AnnotableBean;
import org.sdmxsource.sdmx.api.model.beans.base.AnnotationBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.base.SdmxStructureBean;
import org.sdmxsource.sdmx.api.model.mutable.base.AnnotableMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.AnnotationMutableBean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The type Annotable bean.
 */
public abstract class AnnotableBeanImpl extends SdmxStructureBeanImpl implements AnnotableBean {
    private static final long serialVersionUID = 1L;
    private List<AnnotationBean> annotations = new ArrayList<AnnotationBean>();


    /**
     * Instantiates a new Annotable bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM ITSELF, CREATES STUB BEAN //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected AnnotableBeanImpl(AnnotableBean bean) {
        //DO NOTHING
        super(bean);
    }

    /**
     * Instantiates a new Annotable bean.
     *
     * @param mutableBean the mutable bean
     * @param parent      the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEAN				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected AnnotableBeanImpl(AnnotableMutableBean mutableBean, SdmxStructureBean parent) {
        super(mutableBean, parent);
        if (mutableBean != null && mutableBean.getAnnotations() != null) {
            for (AnnotationMutableBean currentAnnotation : mutableBean.getAnnotations()) {
                annotations.add(new AnnotationBeanImpl(currentAnnotation, this));
            }
        }
    }

    /**
     * Instantiates a new Annotable bean.
     *
     * @param createdFrom   the created from
     * @param structureType the structure type
     * @param parent        the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected AnnotableBeanImpl(AnnotableType createdFrom, SDMX_STRUCTURE_TYPE structureType, SdmxStructureBean parent) {
        super(structureType, parent);
        if (createdFrom != null) {
            org.sdmx.resources.sdmxml.schemas.v21.common.AnnotationsType annotationType = createdFrom.getAnnotations();
            if (annotationType != null && annotationType.getAnnotationList() != null) {
                for (org.sdmx.resources.sdmxml.schemas.v21.common.AnnotationType currentAnnotation : annotationType.getAnnotationList()) {
                    annotations.add(new AnnotationBeanImpl(currentAnnotation, this));
                }
            }
        }
    }

    /**
     * Instantiates a new Annotable bean.
     *
     * @param createdFrom    the created from
     * @param annotationType the annotation type
     * @param structureType  the structure type
     * @param parent         the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected AnnotableBeanImpl(XmlObject createdFrom, AnnotationsType annotationType, SDMX_STRUCTURE_TYPE structureType, SdmxStructureBean parent) {
        super(structureType, parent);
        if (annotationType != null && annotationType.getAnnotationList() != null) {
            for (AnnotationType currentAnnotation : annotationType.getAnnotationList()) {
                annotations.add(new AnnotationBeanImpl(currentAnnotation, this));
            }
        }
    }

    /**
     * Instantiates a new Annotable bean.
     *
     * @param createdFrom    the created from
     * @param annotationType the annotation type
     * @param structureType  the structure type
     * @param parent         the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected AnnotableBeanImpl(XmlObject createdFrom, org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.common.AnnotationsType annotationType, SDMX_STRUCTURE_TYPE structureType, SdmxStructureBean parent) {
        super(structureType, parent);
        if (annotationType != null && annotationType.getAnnotationList() != null) {
            for (org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.common.AnnotationType currentAnnotation : annotationType.getAnnotationList()) {
                annotations.add(new AnnotationBeanImpl(currentAnnotation, this));
            }
        }
    }

    /**
     * Adds an annotation, can only be called from within this package
     *
     * @param annotations the annotations
     */
    void addAnnotations(Set<AnnotationMutableBean> annotations) {
        for (AnnotationMutableBean annotation : annotations) {
            this.annotations.add(new AnnotationBeanImpl(annotation, this));
        }
    }

    /**
     * Deep equals internal boolean.
     *
     * @param bean                   the bean
     * @param includeFinalProperties the include final properties
     * @return the boolean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////DEEP EQUALS							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected boolean deepEqualsInternal(AnnotableBean bean, boolean includeFinalProperties) {
        if (includeFinalProperties) {
            List<AnnotationBean> thatAnnotations = bean.getAnnotations();
            if (!super.equivalent(thatAnnotations, annotations, includeFinalProperties)) {
                return false;
            }
        }
        return true;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(annotations, composites);
        return composites;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public List<AnnotationBean> getAnnotations() {
        return new ArrayList<AnnotationBean>(annotations);
    }

    @Override
    public boolean hasAnnotationType(String annoationType) {
        return getAnnotationsByType(annoationType).size() > 0;
    }

    @Override
    public Set<AnnotationBean> getAnnotationsByType(String type) {
        Set<AnnotationBean> returnSet = new HashSet<AnnotationBean>();
        for (AnnotationBean currentAnnotation : annotations) {
            if (currentAnnotation.getType() != null && currentAnnotation.getType().equals(type)) {
                returnSet.add(currentAnnotation);
            }
        }
        return returnSet;
    }

    @Override
    public Set<AnnotationBean> getAnnotationsByTitle(String title) {
        Set<AnnotationBean> returnSet = new HashSet<AnnotationBean>();
        for (AnnotationBean currentAnnotation : annotations) {
            if (currentAnnotation.getTitle() != null && currentAnnotation.getTitle().equals(title)) {
                returnSet.add(currentAnnotation);
            }
        }
        return returnSet;
    }

}
