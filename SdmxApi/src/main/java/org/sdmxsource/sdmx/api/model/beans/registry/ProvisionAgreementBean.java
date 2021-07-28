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
package org.sdmxsource.sdmx.api.model.beans.registry;

import org.sdmxsource.sdmx.api.model.beans.base.ConstrainableBean;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.ProvisionAgreementMutableBean;

/**
 * Represents an SDMX Provision Agreement
 *
 * @author Matt Nelson
 */
public interface ProvisionAgreementBean extends MaintainableBean, ConstrainableBean {

    /**
     * Returns the reference to the flow, this will either be a dataflow or metadataflow reference.
     * <p>
     * This reference is mandatory and will never return <code>null</code>
     *
     * @return structure usage
     */
    CrossReferenceBean getStructureUseage();

    /**
     * Returns a reference to the data provider that this provision agreement is for.
     * <p>
     * This reference is mandatory and will never return <code>null</code>
     *
     * @return data provider ref
     */
    CrossReferenceBean getDataproviderRef();

    /**
     * Returns a representation of itself in a bean which can be modified, modifications to the mutable bean
     * are not reflected in the instance of the MaintainableBean.
     *
     * @return mutable instance
     */
    @Override
    ProvisionAgreementMutableBean getMutableInstance();
}
