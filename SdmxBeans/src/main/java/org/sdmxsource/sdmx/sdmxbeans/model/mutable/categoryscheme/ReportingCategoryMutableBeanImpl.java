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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.categoryscheme;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.ReportingCategoryBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.categoryscheme.ReportingCategoryMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.ItemMutableBeanImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Reporting category mutable bean.
 */
public class ReportingCategoryMutableBeanImpl extends ItemMutableBeanImpl implements ReportingCategoryMutableBean {
    private static final long serialVersionUID = 1L;

    private List<StructureReferenceBean> structuralMetadata = new ArrayList<StructureReferenceBean>();
    private List<StructureReferenceBean> provisioningMetadata = new ArrayList<StructureReferenceBean>();
    private List<ReportingCategoryMutableBean> reportingCategories = new ArrayList<ReportingCategoryMutableBean>();

    /**
     * Default Constructor
     */
    public ReportingCategoryMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.REPORTING_CATEGORY);
    }

    /**
     * Construct from ReportingCategoryBean Bean
     *
     * @param bean the bean
     */
    public ReportingCategoryMutableBeanImpl(ReportingCategoryBean bean) {
        super(bean);

        if (bean.getStructuralMetadata() != null) {
            for (CrossReferenceBean currentBean : bean.getStructuralMetadata()) {
                structuralMetadata.add(currentBean.createMutableInstance());
            }
        }
        if (bean.getProvisioningMetadata() != null) {
            for (CrossReferenceBean currentBean : bean.getProvisioningMetadata()) {
                provisioningMetadata.add(currentBean.createMutableInstance());
            }
        }
        if (bean.getItems() != null) {
            for (ReportingCategoryBean reportingCategoryBean : bean.getItems()) {
                this.reportingCategories.add(new ReportingCategoryMutableBeanImpl(reportingCategoryBean));
            }
        }
    }

    @Override
    public List<StructureReferenceBean> getStructuralMetadata() {
        return structuralMetadata;
    }

    @Override
    public void setStructuralMetadata(List<StructureReferenceBean> structuralMetadata) {
        this.structuralMetadata = structuralMetadata;
    }

    @Override
    public List<StructureReferenceBean> getProvisioningMetadata() {
        return provisioningMetadata;
    }

    @Override
    public void setProvisioningMetadata(
            List<StructureReferenceBean> provisioningMetadata) {
        this.provisioningMetadata = provisioningMetadata;
    }

    @Override
    public List<ReportingCategoryMutableBean> getItems() {
        return reportingCategories;
    }

    @Override
    public void setItems(List<ReportingCategoryMutableBean> reportingCategories) {
        this.reportingCategories = reportingCategories;
    }

    @Override
    public void addItem(ReportingCategoryMutableBean reportingCategory) {
        if (this.reportingCategories == null) {
            this.reportingCategories = new ArrayList<ReportingCategoryMutableBean>();
        }
        this.reportingCategories.add(reportingCategory);
    }
}
