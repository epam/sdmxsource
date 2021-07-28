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

import org.sdmx.resources.sdmxml.schemas.v21.common.ComponentValueSetType;
import org.sdmx.resources.sdmxml.schemas.v21.metadata.generic.ReferenceValueType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.base.SdmxDate;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.metadata.DataKeyBean;
import org.sdmxsource.sdmx.api.model.metadata.ReferenceValueBean;
import org.sdmxsource.sdmx.api.model.metadata.TargetBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.SDMXBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.SdmxDateImpl;
import org.sdmxsource.sdmx.sdmxbeans.util.RefUtil;
import org.sdmxsource.util.ObjectUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The type Reference value bean.
 */
public class ReferenceValueBeanImpl extends SDMXBeanImpl implements ReferenceValueBean {
    private static final long serialVersionUID = 2865110943018956204L;
    private String id;
    private String datasetId;
    private CrossReferenceBean identifiableReference;
    private CrossReferenceBean constraintReference;
    private List<DataKeyBean> dataKeys = new ArrayList<DataKeyBean>();
    private SdmxDate reportPeriod;


    /**
     * Instantiates a new Reference value bean.
     *
     * @param parent the parent
     * @param type   the type
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ReferenceValueBeanImpl(TargetBean parent, ReferenceValueType type) {
        super(SDMX_STRUCTURE_TYPE.METADATA_REFERENCE_VALUE, parent);
        this.id = type.getId();
        if (type.getConstraintContentReference() != null) {
            constraintReference = RefUtil.createReference(this, type.getConstraintContentReference());
        }
        if (type.getObjectReference() != null) {
            identifiableReference = RefUtil.createReference(this, type.getObjectReference());
        }
        if (type.getDataSetReference() != null) {
            this.datasetId = type.getDataSetReference().getID();
            if (type.getDataSetReference().getDataProvider() == null) {
                throw new SdmxSemmanticException("Can not create Reference Value for Metadata Set - Data Provider Reference is Missing");
            }
            identifiableReference = RefUtil.createReference(this, type.getDataSetReference().getDataProvider());
        }
        if (type.getDataKey() != null) {
            for (ComponentValueSetType cvst : type.getDataKey().getKeyValueList()) {
                this.dataKeys.add(new DataKeyBeanImpl(this, cvst));
            }
        }
        if (type.getReportPeriod() != null) {
            reportPeriod = new SdmxDateImpl(type.getReportPeriod().toString());
        }
        validate();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATE							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() {
        if (!ObjectUtil.validString(id)) {
            throw new SdmxSemmanticException("Reference Value must have an Id");
        }
        if (ObjectUtil.validString(datasetId)) {
            if (dataKeys.size() > 0) {
                throw new SdmxSemmanticException("Reference Value can only contain one target, a datakey, dataset, report period, or an identifiable.  '" + id + "' references both a dataset, and a DataKey");
            }
            if (reportPeriod != null) {
                throw new SdmxSemmanticException("Reference Value can only contain one target, a datakey, dataset, report period, or an identifiable.  '" + id + "' references both a dataset, and a Report Period");
            }
            if (constraintReference != null) {
                throw new SdmxSemmanticException("Reference Value can only contain one target, a datakey, dataset, report period, or an identifiable.  '" + id + "' references both a dataset, and a Constraint");
            }
        } else if (dataKeys.size() > 0) {
            if (identifiableReference != null) {
                throw new SdmxSemmanticException("Reference Value can only contain one target, a datakey, dataset, report period, or an identifiable.  '" + id + "' references both a DataKey, and an Identifiable");
            }
            if (ObjectUtil.validString(datasetId)) {
                throw new SdmxSemmanticException("Reference Value can only contain one target, a datakey, dataset, report period, or an identifiable.  '" + id + "' references both a DataKey, and a dataset");
            }
            if (reportPeriod != null) {
                throw new SdmxSemmanticException("Reference Value can only contain one target, a datakey, dataset, report period, or an identifiable.  '" + id + "' references both a DataKey, and a Report Period");
            }
            if (constraintReference != null) {
                throw new SdmxSemmanticException("Reference Value can only contain one target, a datakey, dataset, report period, or an identifiable.  '" + id + "' references both a DataKey, and a Constraint");
            }
        } else if (identifiableReference != null) {
            if (dataKeys.size() > 0) {
                throw new SdmxSemmanticException("Reference Value can only contain one target, a datakey, dataset, report period, or an identifiable.  '" + id + "' references both an Identifiable, and a DataKey");
            }
            if (ObjectUtil.validString(datasetId)) {
                throw new SdmxSemmanticException("Reference Value can only contain one target, a datakey, dataset, report period, or an identifiable.  '" + id + "' references both an Identifiable, and a dataset");
            }
            if (reportPeriod != null) {
                throw new SdmxSemmanticException("Reference Value can only contain one target, a datakey, dataset, report period, or an identifiable.  '" + id + "' references both an Identifiable, and a Report Period");
            }
            if (constraintReference != null) {
                throw new SdmxSemmanticException("Reference Value can only contain one target, a datakey, dataset, report period, or an identifiable.  '" + id + "' references both an Identifiable, and a Constraint");
            }
        } else if (reportPeriod != null) {
            if (identifiableReference != null) {
                throw new SdmxSemmanticException("Reference Value can only contain one target, a datakey, dataset, report period, or an identifiable.  '" + id + "' references both a Report Period, and an Identifiable");
            }
            if (dataKeys.size() > 0) {
                throw new SdmxSemmanticException("Reference Value can only contain one target, a datakey, dataset, report period, or an identifiable.  '" + id + "' references both a Report Period, and a DataKey");
            }
            if (ObjectUtil.validString(datasetId)) {
                throw new SdmxSemmanticException("Reference Value can only contain one target, a datakey, dataset, report period, or an identifiable.  '" + id + "' references both a Report Period, and a dataset");
            }
            if (constraintReference != null) {
                throw new SdmxSemmanticException("Reference Value can only contain one target, a datakey, dataset, report period, or an identifiable.  '" + id + "' references both a Report Period, and a Constraint");
            }
        } else if (constraintReference != null) {
            if (identifiableReference != null) {
                throw new SdmxSemmanticException("Reference Value can only contain one target, a datakey, dataset, report period, or an identifiable.  '" + id + "' references both a Constraint, and an Identifiable");
            }
            if (ObjectUtil.validString(datasetId)) {
                throw new SdmxSemmanticException("Reference Value can only contain one target, a datakey, dataset, report period, or an identifiable.  '" + id + "' references both a Constraint, and a dataset");
            }
            if (dataKeys.size() > 0) {
                throw new SdmxSemmanticException("Reference Value can only contain one target, a datakey, dataset, report period, or an identifiable.  '" + id + "' references both a Constraint, and a DataKey");
            }
            if (reportPeriod != null) {
                throw new SdmxSemmanticException("Reference Value can only contain one target, a datakey, dataset, report period, or an identifiable.  '" + id + "' references both a Constraint, and a Report Period");
            }
        } else {
            throw new SdmxSemmanticException("Metadata Reference Value must referenece either a datakey, dataset, report period, or an identifiable");
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
    public TARGET_TYPE getTargetType() {
        if (constraintReference != null) {
            return TARGET_TYPE.CONSTRAINT;
        }
        if (ObjectUtil.validString(datasetId)) {
            return TARGET_TYPE.DATASET;
        }
        if (dataKeys.size() > 0) {
            return TARGET_TYPE.DATA_KEY;
        }
        if (reportPeriod != null) {
            return TARGET_TYPE.REPORT_PERIOD;
        }
        if (identifiableReference != null) {
            return TARGET_TYPE.IDENTIFIABLE;
        }
        //THIS POINT SHOULD NEVER BE REACHED AND PICKED UP BY THE VALIDATE METHOD
        throw new RuntimeException("Reference value is not referencing anything");
    }

    @Override
    public String getDatasetId() {
        return datasetId;
    }

    @Override
    public SdmxDate getReportPeriod() {
        return reportPeriod;
    }

    @Override
    public CrossReferenceBean getIdentifiableReference() {
        return identifiableReference;
    }


    @Override
    public CrossReferenceBean getContentConstraintReference() {
        return constraintReference;
    }

    @Override
    public boolean isDatakeyReference() {
        return dataKeys.size() > 0;
    }

    @Override
    public boolean isContentConstriantReference() {
        return constraintReference != null;
    }

    @Override
    public List<DataKeyBean> getDataKeys() {
        return new ArrayList<DataKeyBean>(dataKeys);
    }

    @Override
    public boolean isDatasetReference() {
        return ObjectUtil.validString(datasetId);
    }

    @Override
    public boolean isIdentifiableReference() {
        return !isDatasetReference() && identifiableReference != null;
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
            ReferenceValueBean that = (ReferenceValueBean) bean;
            if (!ObjectUtil.equivalent(this.id, that.getId())) {
                return false;
            }
            if (!ObjectUtil.equivalent(this.datasetId, that.getDatasetId())) {
                return false;
            }
            if (!ObjectUtil.equivalent(this.constraintReference, that.getContentConstraintReference())) {
                return false;
            }
            if (!ObjectUtil.equivalent(this.identifiableReference, that.getIdentifiableReference())) {
                return false;
            }
            if (!ObjectUtil.equivalent(this.reportPeriod, that.getReportPeriod())) {
                return false;
            }
            if (!super.equivalent(dataKeys, that.getDataKeys(), includeFinalProperties)) {
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
        super.addToCompositeSet(dataKeys, composites);
        return composites;
    }

    @Override
    public String toString() {
        return "Metadata Target Reference " + getId();
    }
}
