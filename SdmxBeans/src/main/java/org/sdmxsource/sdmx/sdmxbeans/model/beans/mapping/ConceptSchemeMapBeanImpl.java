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

import org.sdmx.resources.sdmxml.schemas.v20.structure.ConceptMapType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.ConceptSchemeMapType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.mapping.ConceptSchemeMapBean;
import org.sdmxsource.sdmx.api.model.beans.mapping.ItemMapBean;
import org.sdmxsource.sdmx.api.model.beans.mapping.StructureSetBean;
import org.sdmxsource.sdmx.api.model.mutable.mapping.ConceptSchemeMapMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.util.RefUtil;
import org.sdmxsource.sdmx.util.beans.reference.CrossReferenceBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import javax.xml.bind.ValidationException;


/**
 * The type Concept scheme map bean.
 */
public class ConceptSchemeMapBeanImpl extends ItemSchemeMapBeanImpl implements ConceptSchemeMapBean {
    private static final long serialVersionUID = 1L;


    /**
     * Instantiates a new Concept scheme map bean.
     *
     * @param conBean the con bean
     * @param parent  the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ConceptSchemeMapBeanImpl(ConceptSchemeMapMutableBean conBean, StructureSetBean parent) {
        super(conBean, SDMX_STRUCTURE_TYPE.CONCEPT_SCHEME_MAP, parent);
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
    }

    /**
     * Instantiates a new Concept scheme map bean.
     *
     * @param conMapType the con map type
     * @param parent     the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ConceptSchemeMapBeanImpl(org.sdmx.resources.sdmxml.schemas.v21.structure.ConceptSchemeMapType conMapType, StructureSetBean parent) {
        super(conMapType, SDMX_STRUCTURE_TYPE.CONCEPT_SCHEME_MAP, parent);

        this.sourceRef = RefUtil.createReference(this, conMapType.getSource());
        this.targetRef = RefUtil.createReference(this, conMapType.getTarget());

        if (conMapType.getConceptMapList() != null) {
            for (org.sdmx.resources.sdmxml.schemas.v21.structure.ConceptMapType conMap : conMapType.getConceptMapList()) {
                ItemMapBean item = new ItemMapBeanImpl(conMap.getSource().getRef().getId(), conMap.getTarget().getRef().getId(), this);
                items.add(item);
            }
        }
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
    }

    /**
     * Instantiates a new Concept scheme map bean.
     *
     * @param conBean the con bean
     * @param parent  the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ConceptSchemeMapBeanImpl(ConceptSchemeMapType conBean, StructureSetBean parent) {
        super(conBean, SDMX_STRUCTURE_TYPE.CONCEPT_SCHEME_MAP,
                conBean.getId(), null, conBean.getNameList(),
                conBean.getDescriptionList(), conBean.getAnnotations(), parent);

        if (conBean.getConceptSchemeRef() != null) {
            if (ObjectUtil.validString(conBean.getConceptSchemeRef().getURN())) {
                this.sourceRef = new CrossReferenceBeanImpl(this, conBean.getConceptSchemeRef().getURN());
            } else {
                this.sourceRef = new CrossReferenceBeanImpl(this,
                        conBean.getConceptSchemeRef().getAgencyID(),
                        conBean.getConceptSchemeRef().getConceptSchemeID(),
                        conBean.getConceptSchemeRef().getVersion(), SDMX_STRUCTURE_TYPE.CONCEPT_SCHEME);
            }
        }
        if (conBean.getTargetConceptSchemeRef() != null) {
            if (ObjectUtil.validString(conBean.getTargetConceptSchemeRef().getURN())) {
                this.targetRef = new CrossReferenceBeanImpl(this, conBean.getTargetConceptSchemeRef().getURN());
            } else {
                this.targetRef = new CrossReferenceBeanImpl(this,
                        conBean.getTargetConceptSchemeRef().getAgencyID(),
                        conBean.getTargetConceptSchemeRef().getConceptSchemeID(),
                        conBean.getTargetConceptSchemeRef().getVersion(), SDMX_STRUCTURE_TYPE.CONCEPT_SCHEME);
            }
        }
        // get list of code maps
        if (conBean.getConceptMapList() != null) {
            for (ConceptMapType conMap : conBean.getConceptMapList()) {
                ItemMapBean item = new ItemMapBeanImpl(conMap.getConceptAlias(),
                        conMap.getConceptID(),
                        conMap.getTargetConceptID(), this);
                items.add(item);
            }
        }
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////DEEP VALIDATION						 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean deepEquals(SDMXBean bean, boolean includeFinalProperties) {
        if (bean == null) {
            return false;
        }
        if (bean.getStructureType() == this.getStructureType()) {
            return super.deepEqualsInternal((ConceptSchemeMapBean) bean, includeFinalProperties);
        }
        return false;
    }

    /**
     * Validate.
     *
     * @throws ValidationException the validation exception
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected void validate() throws ValidationException {
        if (this.sourceRef == null) {
            throw new SdmxSemmanticException(ExceptionCode.BEAN_MISSING_REQUIRED_ELEMENT, this.structureType, "ConceptSchemeRef");
        }
        if (this.targetRef == null) {
            throw new SdmxSemmanticException(ExceptionCode.BEAN_MISSING_REQUIRED_ELEMENT, this.structureType, "TargetConceptSchemeSchemeRef");
        }
        if (this.items == null) {
            throw new SdmxSemmanticException(ExceptionCode.BEAN_MISSING_REQUIRED_ELEMENT, this.structureType, "conceptMap");
        }
    }
}
