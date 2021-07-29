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
package org.sdmxsource.sdmx.structureparser.workspace;

import org.sdmxsource.sdmx.api.model.beans.base.DataProviderBean;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;

import java.util.List;


/**
 * The provision workspace holds reference to provision agreements, and can also contain the cross referenced structures.
 */
public interface ProvisionWorkspace {

    /**
     * Returns a list of provision agreements in the workspace
     *
     * @return provisions provisions
     */
    List<ProvisionAgreementBean> getProvisions();

    /**
     * Returns the flow references for the provision
     *
     * @param provision the provision
     * @return flow reference
     */
    MaintainableBean getFlowReference(ProvisionAgreementBean provision);

    /**
     * Returns the data provider references for the provision
     *
     * @param provision the provision
     * @return provider reference
     */
    DataProviderBean getProviderReference(ProvisionAgreementBean provision);

    /**
     * Returns true if this workspace was built with all the cross references
     *
     * @return true if this workspace was built with all the cross references
     */
    boolean containsCrossReferences();


}
