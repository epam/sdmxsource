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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.conceptscheme;

import org.sdmx.resources.sdmxml.schemas.v20.structure.ConceptType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.base.RepresentationBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptBean;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptSchemeBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.conceptscheme.ConceptMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.ItemBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.RepresentationBeanImpl;
import org.sdmxsource.sdmx.util.beans.reference.CrossReferenceBeanImpl;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.Set;


/**
 * The type Concept bean.
 */
public class ConceptBeanImpl extends ItemBeanImpl implements ConceptBean {
    private static final long serialVersionUID = 1L;
    private String parentConcept;
    private String parentAgency;
    private CrossReferenceBean isoConceptReference;
    private RepresentationBean coreRepresentation;


    /**
     * Instantiates a new Concept bean.
     *
     * @param parent the parent
     * @param bean   the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEAN				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ConceptBeanImpl(ConceptSchemeBean parent, ConceptMutableBean bean) {
        super(bean, parent);
        if (bean.getCoreRepresentation() != null && (bean.getCoreRepresentation().getTextFormat() != null || bean.getCoreRepresentation().getRepresentation() != null)) {
            this.coreRepresentation = new RepresentationBeanImpl(bean.getCoreRepresentation(), this);
        }
        if (bean.getIsoConceptReference() != null) {
            this.isoConceptReference = new CrossReferenceBeanImpl(this, bean.getIsoConceptReference());
        }
        this.parentConcept = bean.getParentConcept();
        this.parentAgency = bean.getParentAgency();
    }

    /**
     * Instantiates a new Concept bean.
     *
     * @param parent the parent
     * @param bean   the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ConceptBeanImpl(ConceptSchemeBean parent, org.sdmx.resources.sdmxml.schemas.v21.structure.ConceptType bean) {
        super(bean, SDMX_STRUCTURE_TYPE.CONCEPT, parent);
        if (bean.getCoreRepresentation() != null) {
            this.coreRepresentation = new RepresentationBeanImpl(bean.getCoreRepresentation(), this);
        }
        if (bean.getISOConceptReference() != null) {
            isoConceptReference = new CrossReferenceBeanImpl(this,
                    bean.getISOConceptReference().getConceptAgency(),
                    bean.getISOConceptReference().getConceptSchemeID(),
                    null,
                    SDMX_STRUCTURE_TYPE.CONCEPT,
                    bean.getISOConceptReference().getConceptID());
        }
        if (bean.getParent() != null) {
            if (bean.getParent().getURN() != null) {
                StructureReferenceBean sRef = new StructureReferenceBeanImpl(bean.getParent().getURN());
                this.parentConcept = sRef.getChildReference().getId();
                this.parentAgency = sRef.getMaintainableReference().getAgencyId();
            } else {
                this.parentConcept = bean.getParent().getRef().getId();
                this.parentAgency = this.getMaintainableParent().getAgencyId();
            }
        }
    }


    /**
     * Instantiates a new Concept bean.
     *
     * @param parent the parent
     * @param bean   the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ConceptBeanImpl(ConceptSchemeBean parent, ConceptType bean) {
        super(bean, SDMX_STRUCTURE_TYPE.CONCEPT, bean.getId(), bean.getUri(), bean.getNameList(), bean.getDescriptionList(), bean.getAnnotations(), parent);

        if (ObjectUtil.validString(bean.getCoreRepresentation())) {
            String representationAgency = bean.getCoreRepresentationAgency();
            if (representationAgency == null) {
                representationAgency = parent.getAgencyId();
            }
            this.coreRepresentation = new RepresentationBeanImpl(bean.getTextFormat(), representationAgency, bean.getCoreRepresentation(), MaintainableBean.DEFAULT_VERSION, this);
        } else if (bean.isSetTextFormat()) {
            this.coreRepresentation = new RepresentationBeanImpl(bean.getTextFormat(), null, null, null, this);
        }
        this.parentConcept = bean.getParent();
        this.parentAgency = bean.getParentAgency();
    }

    /**
     * Instantiates a new Concept bean.
     *
     * @param parent the parent
     * @param bean   the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V1.0 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ConceptBeanImpl(ConceptSchemeBean parent, org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.ConceptType bean) {
        super(bean, SDMX_STRUCTURE_TYPE.CONCEPT, bean.getId(), bean.getUri(), bean.getNameList(), null, bean.getAnnotations(), parent);
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
            ConceptBean that = (ConceptBean) bean;
            if (!ObjectUtil.equivalent(parentConcept, that.getParentConcept())) {
                return false;
            }
            if (!ObjectUtil.equivalent(parentAgency, that.getParentAgency())) {
                return false;
            }
            if (!super.equivalent(isoConceptReference, that.getIsoConceptReference())) {
                return false;
            }
            if (!super.equivalent(coreRepresentation, that.getCoreRepresentation(), includeFinalProperties)) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void validateId(boolean startWithIntAllowed) {
        //Not allowed to start with an integer
        super.validateId(false);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean isStandAloneConcept() {
        return false;
    }

    @Override
    public RepresentationBean getCoreRepresentation() {
        return coreRepresentation;
    }

    @Override
    public String getParentConcept() {
        return parentConcept;
    }

    @Override
    public String getParentAgency() {
        return parentAgency;
    }

    @Override
    public CrossReferenceBean getIsoConceptReference() {
        return isoConceptReference;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(coreRepresentation, composites);
        return composites;
    }
}
