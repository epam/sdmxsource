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
import org.sdmx.resources.sdmxml.schemas.v20.structure.ReportStructureType;
import org.sdmx.resources.sdmxml.schemas.v21.common.LocalMetadataTargetReferenceType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataAttributeBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataStructureDefinitionBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.ReportStructureBean;
import org.sdmxsource.sdmx.api.model.mutable.metadatastructure.MetadataAttributeMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.metadatastructure.ReportStructureMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.IdentifiableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.util.RefUtil;
import org.sdmxsource.util.ObjectUtil;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * The type Report structure bean.
 */
public class ReportStructureBeanImpl extends IdentifiableBeanImpl implements ReportStructureBean {
    private static final long serialVersionUID = 1L;
    private List<MetadataAttributeBean> metadataAttributes = new ArrayList<MetadataAttributeBean>();
    private List<String> targetMetadatas = new ArrayList<String>();

    /**
     * Instantiates a new Report structure bean.
     *
     * @param parent the parent
     * @param rs     the rs
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ReportStructureBeanImpl(MetadataStructureDefinitionBean parent, ReportStructureMutableBean rs) {
        super(rs, parent);
        try {
            if (rs.getMetadataAttributes() != null) {
                for (MetadataAttributeMutableBean currentMa : rs.getMetadataAttributes()) {
                    metadataAttributes.add(new MetadataAttributeBeanImpl(this, currentMa));
                }
            }
            if (rs.getTargetMetadatas() != null) {
                this.targetMetadatas = new ArrayList<String>(rs.getTargetMetadatas());
            }
        } catch (Throwable th) {
            throw new SdmxSemmanticException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this);
        }
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
    }

    /**
     * Instantiates a new Report structure bean.
     *
     * @param parent the parent
     * @param rs     the rs
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ReportStructureBeanImpl(MetadataStructureDefinitionBean parent, org.sdmx.resources.sdmxml.schemas.v21.structure.ReportStructureType rs) {
        super(rs, SDMX_STRUCTURE_TYPE.REPORT_STRUCTURE, parent);
        try {
            for (org.sdmx.resources.sdmxml.schemas.v21.structure.MetadataAttributeType currentMa : rs.getMetadataAttributeList()) {
                metadataAttributes.add(new MetadataAttributeBeanImpl(currentMa, this));
            }
            if (rs.getMetadataTargetList() != null) {
                for (LocalMetadataTargetReferenceType mtRefType : rs.getMetadataTargetList()) {
                    this.targetMetadatas.add(RefUtil.createLocalIdReference(mtRefType));
                }
            }

        } catch (Throwable th) {
            throw new SdmxSemmanticException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this);
        }
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
    }


    /**
     * Instantiates a new Report structure bean.
     *
     * @param parent the parent
     * @param rs     the rs
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ReportStructureBeanImpl(MetadataStructureDefinitionBean parent, ReportStructureType rs) {
        super(rs, SDMX_STRUCTURE_TYPE.REPORT_STRUCTURE, rs.getId(), rs.getUri(), rs.getAnnotations(), parent);
        try {
            for (MetadataAttributeType currentMa : rs.getMetadataAttributeList()) {
                metadataAttributes.add(new MetadataAttributeBeanImpl(this, currentMa));
            }
            if (ObjectUtil.validString(rs.getTarget())) {
                this.targetMetadatas.add(rs.getTarget());
            }
        } catch (Throwable th) {
            throw new SdmxSemmanticException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this);
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
            ReportStructureBean that = (ReportStructureBean) bean;
            if (!super.equivalent(metadataAttributes, that.getMetadataAttributes(), includeFinalProperties)) {
                return false;
            }
            if (!ObjectUtil.equivalentCollection(targetMetadatas, that.getTargetMetadatas())) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() throws ValidationException {
        if (!ObjectUtil.validCollection(metadataAttributes)) {
            throw new SdmxSemmanticException("Report Structure requires at least one Metadata Attribute");
        }
        if (!ObjectUtil.validCollection(targetMetadatas)) {
            throw new SdmxSemmanticException("Report Structure requires at least one Metadata Target");
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
    public List<String> getTargetMetadatas() {
        return new ArrayList<String>(targetMetadatas);
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
