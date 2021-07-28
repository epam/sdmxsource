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
package org.sdmxsource.sdmx.sdmxbeans.model.metadata;

import org.sdmx.resources.sdmxml.schemas.v21.metadata.generic.ReportedAttributeType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.base.SdmxDate;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.metadata.*;
import org.sdmxsource.sdmx.api.model.metadata.ReferenceValueBean.TARGET_TYPE;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.SDMXBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The type Metadata report bean.
 */
public class MetadataReportBeanImpl extends SDMXBeanImpl implements MetadataReportBean {
    private static final long serialVersionUID = 4729253989941473368L;

    private TargetBean _target;
    private List<ReportedAttributeBean> _reportedAttributes = new ArrayList<ReportedAttributeBean>();
    private String id;

    /**
     * Instantiates a new Metadata report bean.
     *
     * @param parent the parent
     * @param bean   the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public MetadataReportBeanImpl(MetadataSetBean parent, org.sdmx.resources.sdmxml.schemas.v21.metadata.generic.ReportType bean) {
        super(SDMX_STRUCTURE_TYPE.METADATA_REPORT, parent);
        this.id = bean.getId();
        if (bean.getTarget() != null) {
            this._target = new TargetBeanImpl(this, bean.getTarget());
        }
        if (bean.getAttributeSet() != null) {
            if (ObjectUtil.validCollection(bean.getAttributeSet().getReportedAttributeList())) {
                this._reportedAttributes.clear();
                for (ReportedAttributeType each : bean.getAttributeSet().getReportedAttributeList()) {
                    this._reportedAttributes.add(new ReportedAttributeBeanImpl(this, each));
                }
            }
        }
        validate();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() {
        if (!ObjectUtil.validString(id)) {
            throw new SdmxSemmanticException("Metadata Report must have an Id");
        }
        if (this._target == null) {
            throw new SdmxSemmanticException("Metadata Report must have a Target");
        }
        if (!ObjectUtil.validCollection(this._reportedAttributes)) {
            throw new SdmxSemmanticException("Metadata Report must have at least one Reported Attribute");
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public String getId() {
        return id;
    }

    @Override
    public TargetBean getTarget() {
        return this._target;
    }

    @Override
    public List<ReportedAttributeBean> getReportedAttributes() {
        return new ArrayList<ReportedAttributeBean>(this._reportedAttributes);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////TARGET GETTERS						 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Set<TARGET_TYPE> getTargets() {
        Set<TARGET_TYPE> targets = new HashSet<ReferenceValueBean.TARGET_TYPE>();
        for (ReferenceValueBean rv : _target.getReferenceValues()) {
            targets.add(rv.getTargetType());
        }
        return targets;
    }

    @Override
    public String getTargetDatasetId() {
        for (ReferenceValueBean rv : _target.getReferenceValues()) {
            if (rv.getTargetType() == TARGET_TYPE.DATASET) {
                return rv.getDatasetId();
            }
        }
        return null;
    }

    @Override
    public SdmxDate getTargetReportPeriod() {
        for (ReferenceValueBean rv : _target.getReferenceValues()) {
            if (rv.getTargetType() == TARGET_TYPE.REPORT_PERIOD) {
                return rv.getReportPeriod();
            }
        }
        return null;
    }

    @Override
    public CrossReferenceBean getTargetIdentifiableReference() {
        for (ReferenceValueBean rv : _target.getReferenceValues()) {
            if (rv.getTargetType() == TARGET_TYPE.IDENTIFIABLE || rv.getTargetType().equals(TARGET_TYPE.DATASET)) {
                return rv.getIdentifiableReference();
            }
        }
        return null;
    }

    @Override
    public CrossReferenceBean getTargetContentConstraintReference() {
        for (ReferenceValueBean rv : _target.getReferenceValues()) {
            if (rv.getTargetType() == TARGET_TYPE.CONSTRAINT) {
                return rv.getContentConstraintReference();
            }
        }
        return null;
    }

    @Override
    public List<DataKeyBean> getTargetDataKeys() {
        for (ReferenceValueBean rv : _target.getReferenceValues()) {
            if (rv.getTargetType() == TARGET_TYPE.DATA_KEY) {
                return rv.getDataKeys();
            }
        }
        return null;
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
            MetadataReportBean that = (MetadataReportBean) bean;
            if (!ObjectUtil.equivalent(this.id, that.getId())) {
                return false;
            }
            if (!super.equivalent(_target, that.getTarget(), includeFinalProperties)) {
                return false;
            }
            if (!super.equivalent(_reportedAttributes, that.getReportedAttributes(), includeFinalProperties)) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES		                     //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = new HashSet<SDMXBean>();
        super.addToCompositeSet(_target, composites);
        super.addToCompositeSet(_reportedAttributes, composites);
        return composites;
    }
}
