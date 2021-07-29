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

import org.sdmx.resources.sdmxml.schemas.v21.structure.IdentifiableObjectTargetType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.MetadataTargetType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.*;
import org.sdmxsource.sdmx.api.model.mutable.metadatastructure.IdentifiableTargetMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.metadatastructure.MetadataTargetMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.IdentifiableBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * The type Metadata target bean.
 */
public class MetadataTargetBeanImpl extends IdentifiableBeanImpl implements MetadataTargetBean {
    private static final long serialVersionUID = 8862316239443734564L;
    private ConstraintContentTargetBean constraintContentTargetBean;
    private DataSetTargetBean dataSetTargetBean;
    private KeyDescriptorValuesTargetBean keyDescriptorValuesTargetBean;
    private ReportPeriodTargetBean reportPeriodTargetBean;
    private List<IdentifiableTargetBean> identifiableTargetBean = new ArrayList<IdentifiableTargetBean>();

    /**
     * Instantiates a new Metadata target bean.
     *
     * @param parent the parent
     * @param bean   the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEAN				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public MetadataTargetBeanImpl(IdentifiableBean parent, MetadataTargetMutableBean bean) {
        super(bean, parent);
        if (bean.getConstraintContentTargetBean() != null) {
            constraintContentTargetBean = new ConstraintContentTargetBeanImpl(this, bean.getConstraintContentTargetBean());
        }
        if (bean.getKeyDescriptorValuesTargetBean() != null) {
            keyDescriptorValuesTargetBean = new KeyDescriptorValuesTargetBeanImpl(this, bean.getKeyDescriptorValuesTargetBean());
        }
        if (bean.getDataSetTargetBean() != null) {
            dataSetTargetBean = new DataSetTargetBeanImpl(bean.getDataSetTargetBean(), this);
        }
        if (bean.getReportPeriodTargetBean() != null) {
            reportPeriodTargetBean = new ReportPeriodTargetBeanImpl(this, bean.getReportPeriodTargetBean());
        }
        if (bean.getIdentifiableTargetBean() != null) {
            for (IdentifiableTargetMutableBean currentBean : bean.getIdentifiableTargetBean()) {
                identifiableTargetBean.add(new IdentifiableTargetBeanImpl(currentBean, this));
            }
        }
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
    }


    /**
     * Instantiates a new Metadata target bean.
     *
     * @param bean   the bean
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected MetadataTargetBeanImpl(MetadataTargetType bean, MetadataStructureDefinitionBean parent) {
        super(bean, SDMX_STRUCTURE_TYPE.METADATA_TARGET, parent);
        if (ObjectUtil.validCollection(bean.getKeyDescriptorValuesTargetList())) {
            if (bean.getKeyDescriptorValuesTargetList().size() > 1) {
                throw new SdmxSemmanticException("Metadata Target can not have more then one KeyDescriptorValuesTarget");
            }
            keyDescriptorValuesTargetBean = new KeyDescriptorValuesTargetBeanImpl(bean.getKeyDescriptorValuesTargetList().get(0), this);
        }
        if (ObjectUtil.validCollection(bean.getDataSetTargetList())) {
            if (bean.getDataSetTargetList().size() > 1) {
                throw new SdmxSemmanticException("Metadata Target can not have more then one DataSetTarget");
            }
            dataSetTargetBean = new DataSetTargetBeanImpl(bean.getDataSetTargetList().get(0), this);
        }
        if (ObjectUtil.validCollection(bean.getReportPeriodTargetList())) {
            if (bean.getReportPeriodTargetList().size() > 1) {
                throw new SdmxSemmanticException("Metadata Target can not have more then one ReportPeriodTarget");
            }
            reportPeriodTargetBean = new ReportPeriodTargetBeanImpl(bean.getReportPeriodTargetList().get(0), this);
        }
        if (ObjectUtil.validCollection(bean.getConstraintContentTargetList())) {
            if (bean.getDataSetTargetList().size() > 1) {
                throw new SdmxSemmanticException("Metadata Target can not have more then one ConstraintContentTarget");
            }
            constraintContentTargetBean = new ConstraintContentTargetBeanImpl(bean.getConstraintContentTargetList().get(0), this);
        }
        if (bean.getIdentifiableObjectTargetList() != null) {
            for (IdentifiableObjectTargetType currentTarget : bean.getIdentifiableObjectTargetList()) {
                identifiableTargetBean.add(new IdentifiableTargetBeanImpl(currentTarget, this));
            }
        }
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() throws ValidationException {
        if (keyDescriptorValuesTargetBean == null && dataSetTargetBean == null && reportPeriodTargetBean == null
                && constraintContentTargetBean == null && !ObjectUtil.validCollection(identifiableTargetBean)) {
            throw new SdmxSemmanticException("Metadata Target must provide at least one target (key descriptor values, dataset, constraint content, report period, or identifiable object)");
        }
        for (IdentifiableTargetBean currentTarget : identifiableTargetBean) {
            String currentTargetId = currentTarget.getId();
            if (constraintContentTargetBean != null) {
                if (currentTargetId.equals(constraintContentTargetBean.getId())) {
                    throw new SdmxSemmanticException("IdentifiableTarget Id can not be the same as the ConstraintContentTarget Id: " + constraintContentTargetBean.getId());
                }
            }
            if (dataSetTargetBean != null) {
                if (currentTargetId.equals(dataSetTargetBean.getId())) {
                    throw new SdmxSemmanticException("IdentifiableTarget Id can not be the same as the DataSetTarget Id: " + dataSetTargetBean.getId());
                }
            }
            if (keyDescriptorValuesTargetBean != null) {
                if (currentTargetId.equals(keyDescriptorValuesTargetBean.getId())) {
                    throw new SdmxSemmanticException("IdentifiableTarget Id can not be the same as the KeyDescriptorValuesTarget Id: " + keyDescriptorValuesTargetBean.getId());
                }
            }
            if (reportPeriodTargetBean != null) {
                if (currentTargetId.equals(reportPeriodTargetBean.getId())) {
                    throw new SdmxSemmanticException("IdentifiableTarget Id can not be the same as the ReportPeriodTargetBean Id: " + reportPeriodTargetBean.getId());
                }
            }
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
            MetadataTargetBean that = (MetadataTargetBean) bean;
            if (!super.equivalent(constraintContentTargetBean, that.getConstraintContentTargetBean(), includeFinalProperties)) {
                return false;
            }
            if (!super.equivalent(dataSetTargetBean, that.getDataSetTargetBean(), includeFinalProperties)) {
                return false;
            }
            if (!super.equivalent(keyDescriptorValuesTargetBean, that.getKeyDescriptorValuesTargetBean(), includeFinalProperties)) {
                return false;
            }
            if (!super.equivalent(reportPeriodTargetBean, that.getReportPeriodTargetBean(), includeFinalProperties)) {
                return false;
            }
            if (!super.equivalent(identifiableTargetBean, that.getIdentifiableTargetBean(), includeFinalProperties)) {
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
    public ConstraintContentTargetBean getConstraintContentTargetBean() {
        return constraintContentTargetBean;
    }

    @Override
    public DataSetTargetBean getDataSetTargetBean() {
        return dataSetTargetBean;
    }

    @Override
    public KeyDescriptorValuesTargetBean getKeyDescriptorValuesTargetBean() {
        return keyDescriptorValuesTargetBean;
    }

    @Override
    public ReportPeriodTargetBean getReportPeriodTargetBean() {
        return reportPeriodTargetBean;
    }

    @Override
    public List<IdentifiableTargetBean> getIdentifiableTargetBean() {
        return new ArrayList<IdentifiableTargetBean>(identifiableTargetBean);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES		                     //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(constraintContentTargetBean, composites);
        super.addToCompositeSet(dataSetTargetBean, composites);
        super.addToCompositeSet(keyDescriptorValuesTargetBean, composites);
        super.addToCompositeSet(reportPeriodTargetBean, composites);
        super.addToCompositeSet(identifiableTargetBean, composites);
        return composites;
    }
}
