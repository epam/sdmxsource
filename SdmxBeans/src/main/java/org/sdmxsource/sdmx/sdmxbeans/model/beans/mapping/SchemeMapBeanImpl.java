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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.mapping;

import org.apache.xmlbeans.XmlObject;
import org.sdmx.resources.sdmxml.schemas.v20.common.AnnotationsType;
import org.sdmx.resources.sdmxml.schemas.v20.common.TextType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.NameableType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.mapping.SchemeMapBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.base.SchemeMapMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.NameableBeanImpl;
import org.sdmxsource.sdmx.util.beans.reference.CrossReferenceBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The type Scheme map bean.
 */
public abstract class SchemeMapBeanImpl extends NameableBeanImpl implements SchemeMapBean {
    private static final long serialVersionUID = 1L;

    /**
     * The Source ref.
     */
    protected CrossReferenceBean sourceRef;
    /**
     * The Target ref.
     */
    protected CrossReferenceBean targetRef;

    /**
     * Instantiates a new Scheme map bean.
     *
     * @param bean   the bean
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    SchemeMapBeanImpl(SchemeMapMutableBean bean, IdentifiableBean parent) {
        super(bean, parent);
        if (bean.getSourceRef() != null) {
            this.sourceRef = new CrossReferenceBeanImpl(this, bean.getSourceRef());
        }
        if (bean.getTargetRef() != null) {
            this.targetRef = new CrossReferenceBeanImpl(this, bean.getTargetRef());
        }
        try {
            validate();
        } catch (SdmxSemmanticException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
    }


    /**
     * Instantiates a new Scheme map bean.
     *
     * @param createdFrom   the created from
     * @param structureType the structure type
     * @param parent        the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    SchemeMapBeanImpl(NameableType createdFrom, SDMX_STRUCTURE_TYPE structureType, IdentifiableBean parent) {
        super(createdFrom, structureType, parent);
//		try {
//			validate();
//		} catch(SdmxSemmanticException e) {
//			throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
//		}
    }


    /**
     * Instantiates a new Scheme map bean.
     *
     * @param createdFrom     the created from
     * @param structureType   the structure type
     * @param id              the id
     * @param uri             the uri
     * @param name            the name
     * @param description     the description
     * @param annotationsType the annotations type
     * @param parent          the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    SchemeMapBeanImpl(XmlObject createdFrom, SDMX_STRUCTURE_TYPE structureType,
                      String id, String uri, List<TextType> name, List<TextType> description,
                      AnnotationsType annotationsType, IdentifiableBean parent) {
        super(createdFrom, structureType, id, uri, name, description, annotationsType, parent);
//		try {
//			validate();
//		} catch(SdmxSemmanticException e) {
//			throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
//		}
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() throws SdmxSemmanticException {
        if (sourceRef == null) {
            throw new SdmxSemmanticException(getStructureType().getType() + " - Source Ref can not be null");
        }
        if (targetRef == null) {
            throw new SdmxSemmanticException(getStructureType().getType() + " - Target Ref can not be null");
        }
        if (sourceRef.equals(targetRef)) {
            throw new SdmxSemmanticException(getStructureType().getType() + " Source and Target Ref can not be the same");
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
            SchemeMapBean that = (SchemeMapBean) bean;
            if (ObjectUtil.equivalent(sourceRef, that.getSourceRef())) {
                return false;
            }
            if (ObjectUtil.equivalent(targetRef, that.getTargetRef())) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
        }
        return false;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public CrossReferenceBean getSourceRef() {
        return sourceRef;
    }

    @Override
    public CrossReferenceBean getTargetRef() {
        return targetRef;
    }

    @Override
    protected Set<CrossReferenceBean> getCrossReferenceInternal() {
        Set<CrossReferenceBean> references = new HashSet<CrossReferenceBean>();
        if (sourceRef != null) {
            references.add(sourceRef);
        }
        if (targetRef != null) {
            references.add(targetRef);
        }
        return references;
    }
}


