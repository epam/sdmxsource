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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.categoryscheme;

import org.sdmx.resources.sdmxml.schemas.v20.structure.CategoryType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.DataflowRefType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.MetadataflowRefType;
import org.sdmx.resources.sdmxml.schemas.v21.common.StructureReferenceType;
import org.sdmx.resources.sdmxml.schemas.v21.common.StructureUsageReferenceType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.ReportingCategoryType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.ReportingCategoryBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.categoryscheme.ReportingCategoryMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.ItemBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.util.RefUtil;
import org.sdmxsource.sdmx.util.beans.reference.CrossReferenceBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * The type Reporting category bean.
 */
public class ReportingCategoryBeanImpl extends ItemBeanImpl implements ReportingCategoryBean {
    private static final long serialVersionUID = 1L;
    private List<CrossReferenceBean> structuralMetadata = new ArrayList<CrossReferenceBean>();
    private List<CrossReferenceBean> provisioningMetadata = new ArrayList<CrossReferenceBean>();
    private List<ReportingCategoryBean> reportingCategories = new ArrayList<ReportingCategoryBean>();

    /**
     * Instantiates a new Reporting category bean.
     *
     * @param parent            the parent
     * @param reportingCategory the reporting category
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ReportingCategoryBeanImpl(IdentifiableBean parent, ReportingCategoryMutableBean reportingCategory) {
        super(reportingCategory, parent);

        if (reportingCategory.getProvisioningMetadata() != null) {
            for (StructureReferenceBean sRef : reportingCategory.getProvisioningMetadata()) {
                provisioningMetadata.add(new CrossReferenceBeanImpl(this, sRef));
            }
        }

        if (reportingCategory.getStructuralMetadata() != null) {
            for (StructureReferenceBean sRef : reportingCategory.getStructuralMetadata()) {
                structuralMetadata.add(new CrossReferenceBeanImpl(this, sRef));
            }
        }

        if (reportingCategory.getItems() != null) {
            for (ReportingCategoryMutableBean currentBean : reportingCategory.getItems()) {
                reportingCategories.add(new ReportingCategoryBeanImpl(this, currentBean));
            }
        }
        try {
            validate();
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
    }

    /**
     * Instantiates a new Reporting category bean.
     *
     * @param parent   the parent
     * @param category the category
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ReportingCategoryBeanImpl(IdentifiableBean parent, ReportingCategoryType category) {
        super(category, SDMX_STRUCTURE_TYPE.REPORTING_CATEGORY, parent);

        if (category.getProvisioningMetadataList() != null) {
            for (StructureUsageReferenceType sRef : category.getProvisioningMetadataList()) {
                this.provisioningMetadata.add(RefUtil.createReference(this, sRef));
            }
        }
        if (category.getStructuralMetadataList() != null) {
            for (StructureReferenceType sRef : category.getStructuralMetadataList()) {
                this.structuralMetadata.add(RefUtil.createReference(this, sRef));
            }
        }
        if (category.getReportingCategoryList() != null) {
            for (ReportingCategoryType childCategory : category.getReportingCategoryList()) {
                this.reportingCategories.add(new ReportingCategoryBeanImpl(this, childCategory));
            }
        }
        try {
            validate();
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
    }


    /**
     * Instantiates a new Reporting category bean.
     *
     * @param parent   the parent
     * @param category the category
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ReportingCategoryBeanImpl(IdentifiableBean parent, CategoryType category) {
        super(category,
                SDMX_STRUCTURE_TYPE.REPORTING_CATEGORY,
                category.getId(),
                category.getUri(),
                category.getNameList(),
                category.getDescriptionList(),
                category.getAnnotations(),
                parent);

        if (category.getDataflowRefList() != null) {
            for (DataflowRefType dfRef : category.getDataflowRefList()) {
                if (ObjectUtil.validString(dfRef.getURN())) {
                    provisioningMetadata.add(new CrossReferenceBeanImpl(this, dfRef.getURN()));
                } else {
                    provisioningMetadata.add(new CrossReferenceBeanImpl(this, dfRef.getAgencyID(), dfRef.getDataflowID(), dfRef.getVersion(), SDMX_STRUCTURE_TYPE.DATAFLOW));
                }
            }
        }
        if (category.getMetadataflowRefList() != null) {
            for (MetadataflowRefType mdfRef : category.getMetadataflowRefList()) {
                if (ObjectUtil.validString(mdfRef.getURN())) {
                    provisioningMetadata.add(new CrossReferenceBeanImpl(this, mdfRef.getURN()));
                } else {
                    provisioningMetadata.add(new CrossReferenceBeanImpl(this, mdfRef.getAgencyID(), mdfRef.getMetadataflowID(), mdfRef.getVersion(), SDMX_STRUCTURE_TYPE.METADATA_FLOW));
                }
            }
        }
        if (category.getCategoryList() != null) {
            for (CategoryType childCategory : category.getCategoryList()) {
                this.reportingCategories.add(new ReportingCategoryBeanImpl(this, childCategory));
            }
        }
        try {
            validate();
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
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
            ReportingCategoryBean that = (ReportingCategoryBean) bean;
            if (!ObjectUtil.equivalent(structuralMetadata, that.getStructuralMetadata())) {
                return false;
            }
            if (!ObjectUtil.equivalent(provisioningMetadata, that.getProvisioningMetadata())) {
                return false;
            }
            if (!super.equivalent(reportingCategories, that.getItems(), includeFinalProperties)) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() {
        if (ObjectUtil.validCollection(provisioningMetadata) && ObjectUtil.validCollection(structuralMetadata)) {
            throw new SdmxSemmanticException("Reporting Category can not have both structural metadata and provisioning metadata");
        }
        //Validate StructuralMetadata is either pointing to a DSD or MSD, and only contains the same type
        for (CrossReferenceBean crossReference : structuralMetadata) {
            if (crossReference.getTargetReference() != SDMX_STRUCTURE_TYPE.DSD
                    && crossReference.getTargetReference() != SDMX_STRUCTURE_TYPE.MSD) {
                throw new SdmxSemmanticException("Reporting Category 'Structural Metadata' must either reference DSDs or MSDs, not " + crossReference.getTargetReference().getType());
            }
        }
        for (CrossReferenceBean crossReference : provisioningMetadata) {
            if (crossReference.getTargetReference() != SDMX_STRUCTURE_TYPE.DATAFLOW
                    && crossReference.getTargetReference() != SDMX_STRUCTURE_TYPE.METADATA_FLOW) {
                throw new SdmxSemmanticException("Reporting Category 'Provisioning Metadata' must either reference a Data Flow or Metadata Flow, not " + crossReference.getTargetReference().getType());
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS  							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public List<ReportingCategoryBean> getItems() {
        return new ArrayList<ReportingCategoryBean>(reportingCategories);
    }

    @Override
    public List<CrossReferenceBean> getStructuralMetadata() {
        return new ArrayList<CrossReferenceBean>(structuralMetadata);
    }

    @Override
    public List<CrossReferenceBean> getProvisioningMetadata() {
        return new ArrayList<CrossReferenceBean>(provisioningMetadata);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES                           //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////	

    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(reportingCategories, composites);
        return composites;
    }
}
