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

import org.sdmx.resources.sdmxml.schemas.v20.structure.TextFormatType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.RepresentationType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.beans.base.RepresentationBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.base.TextFormatBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.CrossSectionalDataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.base.RepresentationMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.TextFormatMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.util.RefUtil;
import org.sdmxsource.sdmx.util.beans.reference.CrossReferenceBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.HashSet;
import java.util.Set;


/**
 * The type Representation bean.
 */
public class RepresentationBeanImpl extends SdmxStructureBeanImpl implements RepresentationBean {
    private static final long serialVersionUID = -3680385737436648801L;
    private CrossReferenceBean representationRef;
    private TextFormatBean textFormat;

    /**
     * Instantiates a new Representation bean.
     *
     * @param bean   the bean
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEAN				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public RepresentationBeanImpl(RepresentationMutableBean bean, IdentifiableBean parent) {
        super(SDMX_STRUCTURE_TYPE.LOCAL_REPRESENTATION, parent);
        if (bean.getTextFormat() != null) {
            this.textFormat = new TextFormatBeanImpl(bean.getTextFormat(), this);
        }
        if (bean.getRepresentation() != null) {
            representationRef = new CrossReferenceBeanImpl(this, bean.getRepresentation());
        }
        validate();
    }

    /**
     * Instantiates a new Representation bean.
     *
     * @param bean   the bean
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public RepresentationBeanImpl(RepresentationType bean, IdentifiableBean parent) {
        super(SDMX_STRUCTURE_TYPE.LOCAL_REPRESENTATION, parent);
        if (bean.getTextFormat() != null) {
            this.textFormat = new TextFormatBeanImpl(bean.getTextFormat(), this);
        }
        if (bean.getEnumeration() != null) {
            this.representationRef = RefUtil.createReference(this, bean.getEnumeration());
            if (bean.getEnumerationFormat() != null) {
                this.textFormat = new TextFormatBeanImpl(bean.getEnumerationFormat(), this);
            }
        }
        validate();
    }

    /**
     * Instantiates a new Representation bean.
     *
     * @param textFormat      the text format
     * @param codelistAgency  the codelist agency
     * @param codelistId      the codelist id
     * @param codelistVersion the codelist version
     * @param parent          the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public RepresentationBeanImpl(
            TextFormatType textFormat,
            String codelistAgency,
            String codelistId,
            String codelistVersion,
            IdentifiableBean parent) {
        super(SDMX_STRUCTURE_TYPE.LOCAL_REPRESENTATION, parent);
        if (textFormat != null) {
            this.textFormat = new TextFormatBeanImpl(textFormat, this);
        }
        if (ObjectUtil.validOneString(codelistAgency, codelistId, codelistVersion)) {
            if (!ObjectUtil.validString(codelistAgency)) {
                codelistAgency = getMaintainableParent().getAgencyId();
            }
            SDMX_STRUCTURE_TYPE structureType = SDMX_STRUCTURE_TYPE.CODE_LIST;
            if (parent.getMaintainableParent() instanceof CrossSectionalDataStructureBean) {
                if (parent instanceof DimensionBean) {
                    if (((DimensionBean) parent).isMeasureDimension()) {
                        structureType = SDMX_STRUCTURE_TYPE.CONCEPT_SCHEME;
                    }
                }
            }
            this.representationRef = new CrossReferenceBeanImpl(this, codelistAgency, codelistId, codelistVersion, structureType);
        }
        validate();
    }


    /**
     * Instantiates a new Representation bean.
     *
     * @param codelistId the codelist id
     * @param parent     the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected RepresentationBeanImpl(String codelistId, IdentifiableBean parent) {
        super(SDMX_STRUCTURE_TYPE.LOCAL_REPRESENTATION, parent);
        if (ObjectUtil.validString(codelistId)) {
            this.representationRef = new CrossReferenceBeanImpl(this, parent.getMaintainableParent().getAgencyId(), codelistId, null, SDMX_STRUCTURE_TYPE.CODE_LIST);
        }
        validate();
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
            RepresentationBean that = (RepresentationBean) bean;

            if (!super.equivalent(representationRef, that.getRepresentation())) {
                return false;
            }
            if (!super.equivalent(textFormat, that.getTextFormat(), includeFinalProperties)) {
                return false;
            }
            return true;
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION                           //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() {
        if (representationRef == null && textFormat == null) {
            textFormat = new TextFormatBeanImpl(new TextFormatMutableBeanImpl(), this);
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(textFormat, composites);
        return composites;
    }

    @Override
    protected Set<CrossReferenceBean> getCrossReferenceInternal() {
        Set<CrossReferenceBean> references = new HashSet<CrossReferenceBean>();
        if (representationRef != null) {
            references.add(representationRef);
        }
        return references;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public TextFormatBean getTextFormat() {
        return textFormat;
    }

    @Override
    public CrossReferenceBean getRepresentation() {
        return representationRef;
    }
}
