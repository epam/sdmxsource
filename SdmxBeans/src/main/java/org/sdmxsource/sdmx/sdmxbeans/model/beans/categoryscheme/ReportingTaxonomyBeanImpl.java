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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sdmx.resources.sdmxml.schemas.v20.structure.CategoryType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.ReportingTaxonomyType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.ReportingCategoryType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.ReportingCategoryBean;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.ReportingTaxonomyBean;
import org.sdmxsource.sdmx.api.model.mutable.categoryscheme.ReportingCategoryMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.categoryscheme.ReportingTaxonomyMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.ItemSchemeBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.categoryscheme.ReportingTaxonomyMutableBeanImpl;

import javax.xml.bind.ValidationException;
import java.net.URL;


/**
 * The type Reporting taxonomy bean.
 */
public class ReportingTaxonomyBeanImpl extends ItemSchemeBeanImpl<ReportingCategoryBean> implements ReportingTaxonomyBean {
    private static final long serialVersionUID = 1L;
    private static Logger LOG = LogManager.getLogger(ReportingTaxonomyBeanImpl.class);

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM ITSELF, CREATES STUB BEAN //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private ReportingTaxonomyBeanImpl(ReportingTaxonomyBean bean, URL actualLocation, boolean isServiceUrl) {
        super(bean, actualLocation, isServiceUrl);
        LOG.debug("Stub ReportingTaxonomyBean Built");
    }

    /**
     * Instantiates a new Reporting taxonomy bean.
     *
     * @param reportingTaxonomy the reporting taxonomy
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ReportingTaxonomyBeanImpl(ReportingTaxonomyMutableBean reportingTaxonomy) {
        super(reportingTaxonomy);
        LOG.debug("Building ReportingTaxonomyBean from Mutable Bean");
        try {
            if (reportingTaxonomy.getItems() != null) {
                for (ReportingCategoryMutableBean currentcategory : reportingTaxonomy.getItems()) {
                    items.add(new ReportingCategoryBeanImpl(this, currentcategory));
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
        if (LOG.isDebugEnabled()) {
            LOG.debug("ReportingTaxonomyBean Built " + this.getUrn());
        }
    }


    /**
     * Instantiates a new Reporting taxonomy bean.
     *
     * @param reportingTaxonomy the reporting taxonomy
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ReportingTaxonomyBeanImpl(org.sdmx.resources.sdmxml.schemas.v21.structure.ReportingTaxonomyType reportingTaxonomy) {
        super(reportingTaxonomy, SDMX_STRUCTURE_TYPE.REPORTING_TAXONOMY);
        LOG.debug("Building ReportingTaxonomyBean from 2.1 SDMX");
        try {
            if (reportingTaxonomy.getReportingCategoryList() != null) {
                for (ReportingCategoryType currentcategory : reportingTaxonomy.getReportingCategoryList()) {
                    items.add(new ReportingCategoryBeanImpl(this, currentcategory));
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
        if (LOG.isDebugEnabled()) {
            LOG.debug("ReportingTaxonomyBean Built " + this.getUrn());
        }
    }

    /**
     * Instantiates a new Reporting taxonomy bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ReportingTaxonomyBeanImpl(ReportingTaxonomyType bean) {
        super(bean, SDMX_STRUCTURE_TYPE.REPORTING_TAXONOMY,
                bean.getValidTo(),
                bean.getValidFrom(),
                bean.getVersion(),
                createTertiary(bean.isSetIsFinal(), bean.getIsFinal()),
                bean.getAgencyID(),
                bean.getId(),
                bean.getUri(),
                bean.getNameList(),
                bean.getDescriptionList(),
                createTertiary(bean.isSetIsExternalReference(), bean.getIsExternalReference()),
                bean.getAnnotations());
        LOG.debug("Building ReportingTaxonomyBean from 2.0 SDMX");
        try {
            if (bean.getCategoryList() != null) {
                for (CategoryType currentcategory : bean.getCategoryList()) {
                    items.add(new ReportingCategoryBeanImpl(this, currentcategory));
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
        if (LOG.isDebugEnabled()) {
            LOG.debug("ReportingTaxonomyBean Built " + this.getUrn());
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////DEEP VALIDATION						 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean deepEquals(SDMXBean bean, boolean includeFinalProperties) {
        if (bean == null) {
            return false;
        }
        if (bean.getStructureType() == this.getStructureType()) {
            return super.deepEqualsInternal((ReportingTaxonomyBean) bean, includeFinalProperties);
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() throws ValidationException {

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public ReportingTaxonomyBean getStub(URL actualLocation, boolean isServiceUrl) {
        return new ReportingTaxonomyBeanImpl(this, actualLocation, isServiceUrl);
    }

    @Override
    public ReportingTaxonomyMutableBean getMutableInstance() {
        return new ReportingTaxonomyMutableBeanImpl(this);
    }
}
