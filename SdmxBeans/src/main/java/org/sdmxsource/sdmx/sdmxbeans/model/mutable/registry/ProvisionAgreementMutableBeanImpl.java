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
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.ProvisionAgreementMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.registry.ProvisionAgreementBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.MaintainableMutableBeanImpl;


/**
 * The type Provision agreement mutable bean.
 */
public class ProvisionAgreementMutableBeanImpl extends MaintainableMutableBeanImpl implements ProvisionAgreementMutableBean {
    private static final long serialVersionUID = 1L;

    private StructureReferenceBean structureUseage;
    private StructureReferenceBean dataproviderRef;

    /**
     * Instantiates a new Provision agreement mutable bean.
     */
    public ProvisionAgreementMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.PROVISION_AGREEMENT);
    }

    /**
     * Instantiates a new Provision agreement mutable bean.
     *
     * @param bean the bean
     */
    public ProvisionAgreementMutableBeanImpl(ProvisionAgreementBean bean) {
        super(bean);
        if (bean.getStructureUseage() != null) {
            this.structureUseage = bean.getStructureUseage().createMutableInstance();
        }

        if (bean.getDataproviderRef() != null) {
            this.dataproviderRef = bean.getDataproviderRef().createMutableInstance();
        }
    }

    @Override
    public StructureReferenceBean getStructureUsage() {
        return structureUseage;
    }

    @Override
    public void setStructureUsage(StructureReferenceBean structureUseage) {
        this.structureUseage = structureUseage;
    }

    @Override
    public StructureReferenceBean getDataproviderRef() {
        return dataproviderRef;
    }

    @Override
    public void setDataproviderRef(StructureReferenceBean dataproviderRef) {
        this.dataproviderRef = dataproviderRef;
    }

    @Override
    public ProvisionAgreementBean getImmutableInstance() {
        return new ProvisionAgreementBeanImpl(this);
    }
}
