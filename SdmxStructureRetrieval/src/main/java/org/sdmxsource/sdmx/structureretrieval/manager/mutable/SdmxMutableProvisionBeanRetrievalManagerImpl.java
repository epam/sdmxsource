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
package org.sdmxsource.sdmx.structureretrieval.manager.mutable;

import org.sdmxsource.sdmx.api.manager.retrieval.ProvisionBeanRetrievalManager;
import org.sdmxsource.sdmx.api.manager.retrieval.mutable.SdmxMutableProvisionBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.ProvisionAgreementMutableBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * Decorates an SdmxBeanRetrievalManager to retrieve the immutable structures and create the mutable instances.
 */
public class SdmxMutableProvisionBeanRetrievalManagerImpl implements SdmxMutableProvisionBeanRetrievalManager {
    private ProvisionBeanRetrievalManager provisionBeanRetrievalManager;

    /**
     * Instantiates a new Sdmx mutable provision bean retrieval manager.
     *
     * @param provisionBeanRetrievalManager the provision bean retrieval manager
     */
    public SdmxMutableProvisionBeanRetrievalManagerImpl(ProvisionBeanRetrievalManager provisionBeanRetrievalManager) {
        this.provisionBeanRetrievalManager = provisionBeanRetrievalManager;
        if (provisionBeanRetrievalManager == null) {
            throw new IllegalArgumentException("ProvisionBeanRetrievalManager can not be null");
        }
    }


    @Override
    public List<ProvisionAgreementMutableBean> getProvisions(StructureReferenceBean ref) {
        List<ProvisionAgreementMutableBean> returnedProvisions = new ArrayList<ProvisionAgreementMutableBean>();
        Set<ProvisionAgreementBean> provisions = provisionBeanRetrievalManager.getProvisions(ref);
        if (provisions != null) {
            for (ProvisionAgreementBean currentProvision : provisions) {
                returnedProvisions.add(currentProvision.getMutableInstance());
            }
        }
        return returnedProvisions;
    }
}
