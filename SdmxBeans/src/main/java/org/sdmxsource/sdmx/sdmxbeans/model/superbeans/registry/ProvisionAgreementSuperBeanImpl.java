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


import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.DataProviderBean;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DataflowSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.registry.ProvisionAgreementSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.base.MaintainableSuperBeanImpl;

import java.util.Set;


/**
 * The type Provision agreement super bean.
 */
public class ProvisionAgreementSuperBeanImpl extends MaintainableSuperBeanImpl implements ProvisionAgreementSuperBean {
    private static final long serialVersionUID = -6376327465068585635L;
    private DataflowSuperBean dataflowSuperBean;
    private DataProviderBean dataProviderBean;
    private ProvisionAgreementBean provisionAgreement;

    /**
     * Instantiates a new Provision agreement super bean.
     *
     * @param provisionAgreement the provision agreement
     * @param dataflowSuperBean  the dataflow super bean
     * @param dataProviderBean   the data provider bean
     */
    public ProvisionAgreementSuperBeanImpl(ProvisionAgreementBean provisionAgreement,
                                           DataflowSuperBean dataflowSuperBean,
                                           DataProviderBean dataProviderBean) {
        super(provisionAgreement);
        this.provisionAgreement = provisionAgreement;
        this.dataflowSuperBean = dataflowSuperBean;
        this.dataProviderBean = dataProviderBean;
        try {
            validate();
        } catch (SdmxSemmanticException e) {
            throw new SdmxSemmanticException(e, "Error creating provision agreement super bean : " + provisionAgreement.getUrn());
        }
    }

    @Override
    public DataflowSuperBean getDataflowSuperBean() {
        return dataflowSuperBean;
    }

    @Override
    public DataProviderBean getDataProviderBean() {
        return dataProviderBean;
    }

    private void validate() {
        if (dataflowSuperBean == null) {
            throw new SdmxSemmanticException("Madatory Dataflow not provided");
        }
        if (dataProviderBean == null) {
            throw new SdmxSemmanticException("Madatory Dataflow not provided");
        }
    }

    @Override
    public ProvisionAgreementBean getBuiltFrom() {
        return provisionAgreement;
    }

    @Override
    public Set<MaintainableBean> getCompositeBeans() {
        Set<MaintainableBean> returnSet = super.getCompositeBeans();
        returnSet.add(getBuiltFrom());
        returnSet.addAll(dataflowSuperBean.getCompositeBeans());
        returnSet.add(dataProviderBean.getMaintainableParent());
        return returnSet;
    }
}
