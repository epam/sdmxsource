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
package org.sdmxsource.sdmx.sdmxbeans.model.superbeans.registry;

import org.sdmxsource.sdmx.api.constants.TERTIARY_BOOL;
import org.sdmxsource.sdmx.api.model.base.SdmxDate;
import org.sdmxsource.sdmx.api.model.beans.base.DataSourceBean;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.registry.RegistrationBean;
import org.sdmxsource.sdmx.api.model.superbeans.registry.ProvisionAgreementSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.registry.RegistrationSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.base.MaintainableSuperBeanImpl;

import java.util.Set;


/**
 * The type Registration super bean.
 */
public class RegistrationSuperBeanImpl extends MaintainableSuperBeanImpl implements RegistrationSuperBean {
    private static final long serialVersionUID = 3085101294783912114L;
    private ProvisionAgreementSuperBean provBean;
    private RegistrationBean builtFrom;


    /**
     * Instantiates a new Registration super bean.
     *
     * @param reigstrationbean the reigstrationbean
     * @param provBean         the prov bean
     */
    public RegistrationSuperBeanImpl(RegistrationBean reigstrationbean, ProvisionAgreementSuperBean provBean) {
        super(reigstrationbean);
        this.provBean = provBean;
        this.builtFrom = reigstrationbean;
    }

    @Override
    public TERTIARY_BOOL getIndexTimeSeries() {
        return builtFrom.getIndexTimeSeries();
    }

    @Override
    public TERTIARY_BOOL getIndexDataset() {
        return builtFrom.getIndexDataset();
    }

    @Override
    public TERTIARY_BOOL getIndexAttributes() {
        return builtFrom.getIndexAttributes();
    }

    @Override
    public TERTIARY_BOOL getIndexReportingPeriod() {
        return builtFrom.getIndexReportingPeriod();
    }

    @Override
    public SdmxDate getLastUpdated() {
        return builtFrom.getLastUpdated();
    }

    @Override
    public SdmxDate getValidFrom() {
        return builtFrom.getValidFrom();
    }

    @Override
    public SdmxDate getValidTo() {
        return builtFrom.getValidTo();
    }

    @Override
    public DataSourceBean getDataSource() {
        return builtFrom.getDataSource();
    }

    @Override
    public ProvisionAgreementSuperBean getProvisionAgreement() {
        return provBean;
    }

    @Override
    public RegistrationBean getBuiltFrom() {
        return builtFrom;
    }

    @Override
    public Set<MaintainableBean> getCompositeBeans() {
        Set<MaintainableBean> returnSet = super.getCompositeBeans();
        returnSet.add(getBuiltFrom());
        returnSet.addAll(provBean.getCompositeBeans());
        return returnSet;
    }
}
