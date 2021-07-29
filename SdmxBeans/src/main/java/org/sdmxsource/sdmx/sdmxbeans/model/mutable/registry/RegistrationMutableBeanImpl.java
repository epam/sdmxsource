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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.registry;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TERTIARY_BOOL;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.RegistrationBean;
import org.sdmxsource.sdmx.api.model.mutable.base.DataSourceMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.RegistrationMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.registry.RegistrationBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.DataSourceMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.MaintainableMutableBeanImpl;

import java.util.Date;


/**
 * The type Registration mutable bean.
 */
public class RegistrationMutableBeanImpl extends MaintainableMutableBeanImpl implements RegistrationMutableBean {
    private static final long serialVersionUID = 1L;
    private DataSourceMutableBean datasource;
    private Date lastUpdated;
    private Date validFrom;
    private Date validTo;
    private StructureReferenceBean provisionAgreementRef;
    private TERTIARY_BOOL indexTimeSeries = TERTIARY_BOOL.UNSET;
    private TERTIARY_BOOL indexDataset = TERTIARY_BOOL.UNSET;
    private TERTIARY_BOOL indexAttributes = TERTIARY_BOOL.UNSET;
    private TERTIARY_BOOL indexReportingPeriod = TERTIARY_BOOL.UNSET;

    /**
     * Instantiates a new Registration mutable bean.
     */
    public RegistrationMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.REGISTRATION);
    }

    /**
     * Instantiates a new Registration mutable bean.
     *
     * @param registration the registration
     */
    public RegistrationMutableBeanImpl(RegistrationBean registration) {
        super(registration);
        if (registration.getDataSource() != null) {
            this.datasource = new DataSourceMutableBeanImpl(registration.getDataSource());
        }
        if (registration.getLastUpdated() != null) {
            this.lastUpdated = registration.getLastUpdated().getDate();
        }
        if (registration.getValidFrom() != null) {
            this.validFrom = registration.getValidFrom().getDate();
        }
        if (registration.getValidTo() != null) {
            this.validTo = registration.getValidTo().getDate();
        }
        if (registration.getProvisionAgreementRef() != null) {
            this.provisionAgreementRef = registration.getProvisionAgreementRef().createMutableInstance();
        }
        this.indexTimeSeries = registration.getIndexTimeSeries();
        this.indexDataset = registration.getIndexDataset();
        this.indexAttributes = registration.getIndexAttributes();
        this.indexReportingPeriod = registration.getIndexReportingPeriod();
    }

    @Override
    public TERTIARY_BOOL getIndexTimeSeries() {
        return indexTimeSeries;
    }

    @Override
    public void setIndexTimeSeries(TERTIARY_BOOL indexTimeSeries) {
        this.indexTimeSeries = indexTimeSeries;
    }

    @Override
    public TERTIARY_BOOL getIndexDataset() {
        return indexDataset;
    }

    @Override
    public void setIndexDataset(TERTIARY_BOOL indexDataset) {
        this.indexDataset = indexDataset;
    }

    @Override
    public TERTIARY_BOOL getIndexAttributes() {
        return indexAttributes;
    }

    @Override
    public void setIndexAttributes(TERTIARY_BOOL indexAttributes) {
        this.indexAttributes = indexAttributes;
    }

    @Override
    public TERTIARY_BOOL getIndexReportingPeriod() {
        return indexReportingPeriod;
    }

    @Override
    public void setIndexReportingPeriod(TERTIARY_BOOL indexReportingPeriod) {
        this.indexReportingPeriod = indexReportingPeriod;
    }

    @Override
    public StructureReferenceBean getProvisionAgreementRef() {
        return provisionAgreementRef;
    }

    @Override
    public void setProvisionAgreementRef(
            StructureReferenceBean provisionAgreementRef) {
        this.provisionAgreementRef = provisionAgreementRef;
    }

    @Override
    public RegistrationBean getImmutableInstance() {
        return new RegistrationBeanImpl(this);
    }

    @Override
    public DataSourceMutableBean getDataSource() {
        return this.datasource;
    }

    @Override
    public void setDataSource(DataSourceMutableBean dataSource) {
        this.datasource = dataSource;
    }

    @Override
    public Date getLastUpdated() {
        return this.lastUpdated;
    }

    @Override
    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public Date getValidFrom() {
        return this.validFrom;
    }

    @Override
    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    @Override
    public Date getValidTo() {
        return this.validTo;
    }

    @Override
    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }
}
