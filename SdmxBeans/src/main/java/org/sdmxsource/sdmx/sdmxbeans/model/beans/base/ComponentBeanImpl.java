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
import org.sdmx.resources.sdmxml.schemas.v20.common.AnnotationsType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.TextFormatType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.ComponentType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.*;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptSchemeBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.base.ComponentMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.util.RefUtil;
import org.sdmxsource.sdmx.util.beans.ConceptRefUtil;
import org.sdmxsource.sdmx.util.beans.reference.CrossReferenceBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.HashSet;
import java.util.Set;


/**
 * The type Component bean.
 */
public abstract class ComponentBeanImpl extends IdentifiableBeanImpl implements ComponentBean {
    private static final long serialVersionUID = 1L;
    /**
     * The Local representation.
     */
    protected RepresentationBean localRepresentation;
    private CrossReferenceBean conceptRef;

    /**
     * Instantiates a new Component bean.
     *
     * @param bean   the bean
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS 			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected ComponentBeanImpl(ComponentMutableBean bean, IdentifiableBean parent) {
        super(bean, parent);
        try {
            if (bean.getRepresentation() != null) {
                this.localRepresentation = new RepresentationBeanImpl(bean.getRepresentation(), this);
            }
            if (bean.getConceptRef() != null) {
                this.conceptRef = new CrossReferenceBeanImpl(this, bean.getConceptRef());
            }
        } catch (Throwable th) {
            throw new SdmxException(th, "Error creating component: " + this.toString());
        }
        validateComponentAttributes();
    }

    /**
     * Instantiates a new Component bean.
     *
     * @param createdFrom   the created from
     * @param structureType the structure type
     * @param parent        the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected ComponentBeanImpl(ComponentType createdFrom, SDMX_STRUCTURE_TYPE structureType, IdentifiableBean parent) {
        super(createdFrom, structureType, parent);
        if (createdFrom.getLocalRepresentation() != null) {
            localRepresentation = new RepresentationBeanImpl(createdFrom.getLocalRepresentation(), this);
        }
        if (createdFrom.getConceptIdentity() != null) {
            this.conceptRef = RefUtil.createReference(this, createdFrom.getConceptIdentity());
        }
        //FUNC 2.1 put in Concept Identifity = conceptRef
        validateComponentAttributes();
    }


    /**
     * Instantiates a new Component bean.
     *
     * @param createdFrom          the created from
     * @param structureType        the structure type
     * @param annotationType       the annotation type
     * @param textFormat           the text format
     * @param codelistAgency       the codelist agency
     * @param codelistId           the codelist id
     * @param codelistVersion      the codelist version
     * @param conceptSchemeAgency  the concept scheme agency
     * @param conceptSchemeId      the concept scheme id
     * @param conceptSchemeVersion the concept scheme version
     * @param conceptAgency        the concept agency
     * @param conceptId            the concept id
     * @param parent               the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected ComponentBeanImpl(XmlObject createdFrom,
                                SDMX_STRUCTURE_TYPE structureType,
                                AnnotationsType annotationType,
                                TextFormatType textFormat,
                                String codelistAgency,
                                String codelistId,
                                String codelistVersion,
                                String conceptSchemeAgency,
                                String conceptSchemeId,
                                String conceptSchemeVersion,
                                String conceptAgency,
                                String conceptId,
                                IdentifiableBean parent) {
        super(createdFrom, structureType, conceptId, null, annotationType, parent);

        if (!ObjectUtil.validString(conceptAgency)) {
            conceptAgency = getMaintainableParent().getAgencyId();
        }
        if (textFormat != null || ObjectUtil.validOneString(codelistAgency, codelistId, codelistVersion)) {
            if (ObjectUtil.validOneString(codelistAgency, codelistId, codelistVersion)) {
                if (!ObjectUtil.validString(codelistAgency)) {
                    codelistAgency = getMaintainableParent().getAgencyId();
                }
            }
            localRepresentation = new RepresentationBeanImpl(textFormat, codelistAgency, codelistId, codelistVersion, this);
        }
        this.conceptRef = ConceptRefUtil.buildConceptRef(this, conceptSchemeAgency, conceptSchemeId, conceptSchemeVersion, conceptAgency, conceptId);
        validateComponentAttributes();
    }


    /**
     * Instantiates a new Component bean.
     *
     * @param createdFrom    the created from
     * @param structureType  the structure type
     * @param annotationType the annotation type
     * @param codelistId     the codelist id
     * @param conceptId      the concept id
     * @param parent         the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected ComponentBeanImpl(XmlObject createdFrom,
                                SDMX_STRUCTURE_TYPE structureType,
                                org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.common.AnnotationsType annotationType,
                                String codelistId,
                                String conceptId,
                                SdmxStructureBean parent) {
        super(createdFrom, structureType, conceptId, null, annotationType, parent);
        if (ObjectUtil.validString(codelistId)) {
            localRepresentation = new RepresentationBeanImpl(codelistId, this);
        }
        this.conceptRef = new CrossReferenceBeanImpl(this,
                getMaintainableParent().getAgencyId(),
                ConceptSchemeBean.DEFAULT_SCHEME_ID,
                ConceptSchemeBean.DEFAULT_SCHEME_VERSION,
                SDMX_STRUCTURE_TYPE.CONCEPT, conceptId);
        validateComponentAttributes();
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
    protected boolean deepEqualsInternal(ComponentBean bean, boolean includeFinalProperties) {
        if (bean == null) {
            return false;
        }
        if (!super.equivalent(conceptRef, bean.getConceptRef())) {
            return false;
        }
        if (!super.equivalent(localRepresentation, bean.getRepresentation(), includeFinalProperties)) {
            return false;
        }
        return super.deepEqualsInternal(bean, includeFinalProperties);
    }

    /**
     * Validate component attributes.
     *
     * @throws SdmxSemmanticException the sdmx semmantic exception
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected void validateComponentAttributes() throws SdmxSemmanticException {
        validateComponetReference();
        super.validateId(false);
    }

    /**
     * Validate componet reference.
     */
    protected void validateComponetReference() {
        if (this.conceptRef == null) {
            throw new SdmxSemmanticException(ExceptionCode.BEAN_MISSING_REQUIRED_ATTRIBUTE, structureType.getType(), "conceptRef");
        }
        if (this.conceptRef.getTargetReference() != SDMX_STRUCTURE_TYPE.CONCEPT) {
            throw new SdmxSemmanticException("Component reference is invalid, expected " + SDMX_STRUCTURE_TYPE.CONCEPT.getType() + " reference, got " + this.conceptRef.getTargetReference().getType() + " reference");
        }
    }

    @Override
    protected void validateId(boolean startWithIntAllowed) {
        //Do nothing yet, not yet fully built
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES                           //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(getRepresentation(), composites);
        return composites;
    }

    @Override
    protected Set<CrossReferenceBean> getCrossReferenceInternal() {
        Set<CrossReferenceBean> references = new HashSet<CrossReferenceBean>();
        if (conceptRef != null) {
            references.add(conceptRef);
        }
        return references;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public String getId() {
        if (ObjectUtil.validString(super.getId())) {
            return super.getId();
        }
        if (conceptRef != null) {
            return conceptRef.getChildReference().getId();
        }
        throw new SdmxSemmanticException("Id not set for component");
    }

    @Override
    public CrossReferenceBean getConceptRef() {
        return conceptRef;
    }

    @Override
    public RepresentationBean getRepresentation() {
        return localRepresentation;
    }

    @Override
    public boolean hasCodedRepresentation() {
        if (localRepresentation != null) {
            return localRepresentation.getRepresentation() != null;
        }
        return false;
    }
}
