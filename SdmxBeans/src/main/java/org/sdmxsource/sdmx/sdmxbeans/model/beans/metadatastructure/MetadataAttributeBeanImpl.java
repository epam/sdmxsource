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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.metadatastructure;

import org.sdmx.resources.sdmxml.schemas.v20.structure.MetadataAttributeType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.UsageStatusType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TERTIARY_BOOL;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataAttributeBean;
import org.sdmxsource.sdmx.api.model.mutable.metadatastructure.MetadataAttributeMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.ComponentBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import javax.xml.bind.ValidationException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The type Metadata attribute bean.
 */
public class MetadataAttributeBeanImpl extends ComponentBeanImpl implements MetadataAttributeBean {
    private static final long serialVersionUID = 1L;
    private List<MetadataAttributeBean> metadataAttributes = new ArrayList<MetadataAttributeBean>();
    private Integer minOccurs;
    private Integer maxOccurs;
    private TERTIARY_BOOL presentational = TERTIARY_BOOL.UNSET;

    /**
     * Instantiates a new Metadata attribute bean.
     *
     * @param parent the parent
     * @param bean   the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEAN				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public MetadataAttributeBeanImpl(IdentifiableBean parent, MetadataAttributeMutableBean bean) {
        super(bean, parent);
        try {
            if (bean.getMetadataAttributes() != null) {
                for (MetadataAttributeMutableBean currentMa : bean.getMetadataAttributes()) {
                    metadataAttributes.add(new MetadataAttributeBeanImpl(this, currentMa));
                }
            }
            if (bean.getMinOccurs() != null) {
                this.minOccurs = new Integer(bean.getMinOccurs());
            }
            if (bean.getMaxOccurs() != null) {
                this.maxOccurs = new Integer(bean.getMaxOccurs());
            }
            this.presentational = bean.getPresentational();
        } catch (Throwable th) {
            throw new SdmxSemmanticException(th, "Error creating structure: " + this);
        }
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
    }

    /**
     * Instantiates a new Metadata attribute bean.
     *
     * @param metadataAttribute the metadata attribute
     * @param parent            the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public MetadataAttributeBeanImpl(org.sdmx.resources.sdmxml.schemas.v21.structure.MetadataAttributeType metadataAttribute, IdentifiableBean parent) {
        super(metadataAttribute, SDMX_STRUCTURE_TYPE.METADATA_ATTRIBUTE, parent);
        if (metadataAttribute.getMinOccurs() != null) {
            this.minOccurs = metadataAttribute.getMinOccurs().intValue();
        }
        if (metadataAttribute.getMaxOccurs() != null) {
            if (metadataAttribute.getMaxOccurs() instanceof BigInteger) {
                this.maxOccurs = ((BigInteger) metadataAttribute.getMaxOccurs()).intValue();
            }
        }
        if (metadataAttribute.isSetIsPresentational()) {
            this.presentational = TERTIARY_BOOL.parseBoolean(metadataAttribute.getIsPresentational());
        }
        if (metadataAttribute.getMetadataAttributeList() != null) {
            for (org.sdmx.resources.sdmxml.schemas.v21.structure.MetadataAttributeType currentMaType : metadataAttribute.getMetadataAttributeList()) {
                this.metadataAttributes.add(new MetadataAttributeBeanImpl(currentMaType, this));
            }
        }
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
    }

    /**
     * Instantiates a new Metadata attribute bean.
     *
     * @param parent            the parent
     * @param metadataAttribute the metadata attribute
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public MetadataAttributeBeanImpl(IdentifiableBean parent, MetadataAttributeType metadataAttribute) {
        super(metadataAttribute,
                SDMX_STRUCTURE_TYPE.METADATA_ATTRIBUTE,
                metadataAttribute.getAnnotations(),
                metadataAttribute.getTextFormat(),
                metadataAttribute.getRepresentationSchemeAgency(),
                metadataAttribute.getRepresentationScheme(),
                null,
                metadataAttribute.getConceptSchemeAgency(),
                metadataAttribute.getConceptSchemeRef(),
                metadataAttribute.getConceptVersion(),
                metadataAttribute.getConceptAgency(),
                metadataAttribute.getConceptRef(),
                parent);

        if (metadataAttribute.getUsageStatus() != null) {
            if (metadataAttribute.getUsageStatus() == UsageStatusType.MANDATORY) {
                this.minOccurs = 1;
                this.maxOccurs = 1;
            } else {
                this.minOccurs = 0;
            }
        }
        if (metadataAttribute.getMetadataAttributeList() != null) {
            for (MetadataAttributeType currentMa : metadataAttribute.getMetadataAttributeList()) {
                metadataAttributes.add(new MetadataAttributeBeanImpl(this, currentMa));
            }
        }
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
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
            MetadataAttributeBean that = (MetadataAttributeBean) bean;
            if (!super.equivalent(metadataAttributes, that.getMetadataAttributes(), includeFinalProperties)) {
                return false;
            }
            if (!ObjectUtil.equivalent(minOccurs, that.getMinOccurs())) {
                return false;
            }
            if (!ObjectUtil.equivalent(maxOccurs, that.getMaxOccurs())) {
                return false;
            }
            if (presentational != that.getPresentational()) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
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
        if (metadataAttributes != null) {
            Set<String> conceptIds = new HashSet<String>();
            for (MetadataAttributeBean metadataAttribute : metadataAttributes) {
                if (metadataAttribute.getConceptRef() == null) {
                    throw new SdmxSemmanticException("Metadata Attribute must reference a concept");
                }
                String ids = metadataAttribute.getId();
                if (conceptIds.contains(ids)) {
                    throw new SdmxSemmanticException(ExceptionCode.DUPLICATE_CONCEPT, metadataAttribute.toString());
                }
                conceptIds.add(ids);
            }
        }
        if (presentational.isTrue()) {
            if (getRepresentation() != null) {
                if (getRepresentation().getRepresentation() != null) {
                    throw new SdmxSemmanticException("Metadata Attribtue '" + getFullIdPath(false) + "' is presentational so can not have coded representation");
                }
                if (getRepresentation().getTextFormat() != null) {
                    throw new SdmxSemmanticException("Metadata Attribtue '" + getFullIdPath(false) + "' is presentational so can not define a Text Format");
                }
            }
        }
        if (minOccurs != null && maxOccurs != null) {
            if (minOccurs.compareTo(maxOccurs) > 0) {
                throw new SdmxSemmanticException("Max Occurs '" + maxOccurs + "' can not be a lower value then Min Occurs '" + minOccurs + "'.  Please note the abscence of Max Occurs defaults to value of 1 - specify the value as 'unbounded' is this is not the case.");
            }
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public List<MetadataAttributeBean> getMetadataAttributes() {
        return new ArrayList<MetadataAttributeBean>(metadataAttributes);
    }

    @Override
    public Integer getMinOccurs() {
        if (minOccurs == null) {
            return null;
        }
        return new Integer(minOccurs);
    }

    @Override
    public Integer getMaxOccurs() {
        if (maxOccurs == null) {
            return null;
        }
        return new Integer(maxOccurs);
    }

    @Override
    public TERTIARY_BOOL getPresentational() {
        return presentational;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES		                     //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(metadataAttributes, composites);
        return composites;
    }
}
