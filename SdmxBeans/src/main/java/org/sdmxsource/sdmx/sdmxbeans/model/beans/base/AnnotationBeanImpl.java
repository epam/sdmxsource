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

import org.sdmx.resources.sdmxml.schemas.v20.common.AnnotationType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.base.AnnotationBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.sdmx.api.model.mutable.base.AnnotationMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.TextTypeWrapperMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.util.TextTypeUtil;
import org.sdmxsource.sdmx.util.beans.ValidationUtil;
import org.sdmxsource.util.ObjectUtil;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The type Annotation bean.
 */
public class AnnotationBeanImpl extends SDMXBeanImpl implements AnnotationBean {
    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    private String type;
    private URI uri;
    private List<TextTypeWrapper> text = new ArrayList<TextTypeWrapper>();

    /**
     * Instantiates a new Annotation bean.
     *
     * @param annotationMutable the annotation mutable
     * @param parent            the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEAN				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public AnnotationBeanImpl(AnnotationMutableBean annotationMutable, SDMXBean parent) {
        super(annotationMutable, parent);
        this.id = annotationMutable.getId();
        this.title = annotationMutable.getTitle();
        this.type = annotationMutable.getType();
        if (annotationMutable.getText() != null) {
            for (TextTypeWrapperMutableBean mutable : annotationMutable.getText()) {
                if (ObjectUtil.validString(mutable.getValue())) {
                    text.add(new TextTypeWrapperImpl(mutable, this));
                }
            }
        }
        setURI(annotationMutable.getUri());
        try {
            validate();
        } catch (SdmxException ex) {
            throw new SdmxException(ex, "Annotation is not valid");
        } catch (Throwable th) {
            throw new SdmxException(th, "Annotation is not valid");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM READER                    //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    //	public AnnotationBeanImpl(SdmxReader reader, SDMXBean parent) {
    //		super(SDMX_STRUCTURE_TYPE.ANNOTATION, parent);
    //		this.id = reader.getAttributeValue("id", false);
    //		while(processNextElement(reader)) {
    //
    //		}
    //		return;
    //	}

    //	private boolean processNextElement(SdmxReader reader) {
    //		String nextEl = reader.peek();
    //		if(nextEl.equals("AnnotationTitle")) {
    //			reader.moveNextElement();
    //			this.title = reader.getCurrentElementValue();
    //			return true;
    //		}
    //
    //		if(nextEl.equals("AnnotationType")) {
    //			reader.moveNextElement();
    //			this.type = reader.getCurrentElementValue();
    //			return true;
    //		}
    //
    //		if(nextEl.equals("AnnotationURL")) {
    //			reader.moveNextElement();
    //			setURL(reader.getCurrentElementValue());
    //			return true;
    //		}
    //
    //		if(nextEl.equals("AnnotationText")) {
    //			reader.moveNextElement();
    //			this.text.add(new TextTypeWrapperImpl(reader.getAttributeValue("lang", false), reader.getCurrentElementValue(), this));
    //			return true;
    //		}
    //		return false;
    //	}

    /**
     * Instantiates a new Annotation bean.
     *
     * @param annotation the annotation
     * @param parent     the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public AnnotationBeanImpl(org.sdmx.resources.sdmxml.schemas.v21.common.AnnotationType annotation, SDMXBean parent) {
        super(SDMX_STRUCTURE_TYPE.ANNOTATION, parent);
        this.title = annotation.getAnnotationTitle();
        this.type = annotation.getAnnotationType();
        this.id = annotation.getId();
        this.text = TextTypeUtil.wrapTextTypeV21(annotation.getAnnotationTextList(), this);
        setURI(annotation.getAnnotationURL());
        try {
            validate();
        } catch (SdmxException ex) {
            throw new SdmxException(ex, "Annotation is not valid");
        } catch (Throwable th) {
            throw new SdmxException(th, "Annotation is not valid");
        }
    }

    /**
     * Instantiates a new Annotation bean.
     *
     * @param annotation the annotation
     * @param parent     the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public AnnotationBeanImpl(AnnotationType annotation, SDMXBean parent) {
        super(SDMX_STRUCTURE_TYPE.ANNOTATION, parent);
        this.title = annotation.getAnnotationTitle();
        this.type = annotation.getAnnotationType();
        this.text = TextTypeUtil.wrapTextTypeV2(annotation.getAnnotationTextList(), this);
        setURI(annotation.getAnnotationURL());
        try {
            validate();
        } catch (SdmxException ex) {
            throw new SdmxException(ex, "Annotation is not valid");
        } catch (Throwable th) {
            throw new SdmxException(th, "Annotation is not valid");
        }
    }

    /**
     * Instantiates a new Annotation bean.
     *
     * @param annotation the annotation
     * @param parent     the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public AnnotationBeanImpl(org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.common.AnnotationType annotation, SDMXBean parent) {
        super(SDMX_STRUCTURE_TYPE.ANNOTATION, parent);
        this.title = annotation.getAnnotationTitle();
        this.type = annotation.getAnnotationType();
        this.text = TextTypeUtil.wrapTextTypeV1(annotation.getAnnotationTextList(), this);
        setURI(annotation.getAnnotationURL());
        try {
            validate();
        } catch (SdmxException ex) {
            throw new SdmxException(ex, "Annotation is not valid");
        } catch (Throwable th) {
            throw new SdmxException(th, "Could not create Annotation as it did not validate");
        }
    }

    private void setURI(String serviceURIStr) {
        if (serviceURIStr == null) {
            this.uri = null;
            return;
        }
        try {
            this.uri = new URI(serviceURIStr);
        } catch (SdmxException ex) {
            throw new SdmxException(ex, "Could not create attribute 'annotationURL' with value '" + serviceURIStr + "'");
        } catch (Throwable th) {
            throw new SdmxException(th, "Could not create attribute 'annotationURL' with value '" + serviceURIStr + "'");
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
        if (!includeFinalProperties) {
            //If we don't care about the final properties, then don't check this
            return true;
        }

        return this.equals(bean);
    }


    @Override
    public int hashCode() {
        StringBuilder sb = new StringBuilder();
        sb.append(id + title + type);
        if (uri != null) {
            sb.append(uri.toString());
        }
        for (TextTypeWrapper tt : text) {
            sb.append(tt.getLocale() + ":" + tt.getValue());
        }
        return sb.toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AnnotationBean) {
            AnnotationBean annotation = (AnnotationBean) obj;
            if (!ObjectUtil.equivalent(id, annotation.getId())) {
                return false;
            }
            if (!ObjectUtil.equivalent(title, annotation.getTitle())) {
                return false;
            }
            if (!ObjectUtil.equivalent(type, annotation.getType())) {
                return false;
            }
            if (!ObjectUtil.equivalent(uri, annotation.getUri())) {
                return false;
            }
            if (!super.equivalent(text, annotation.getText(), true)) {
                return false;
            }
            return true;
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION 							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() {
        ValidationUtil.validateTextType(text, null);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////	
    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = new HashSet<SDMXBean>();
        super.addToCompositeSet(text, composites);
        return composites;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public URI getUri() {
        return uri;
    }

    @Override
    public List<TextTypeWrapper> getText() {
        return new ArrayList<TextTypeWrapper>(text);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Title:" + getTitle());
        sb.append("Type:" + getType());
        sb.append("URI:" + getUri());

        return sb.toString();
    }
}
