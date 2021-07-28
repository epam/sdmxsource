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

import org.sdmxsource.sdmx.api.manager.retrieval.SdmxSubscriptionRetrievalManager;
import org.sdmxsource.sdmx.api.manager.retrieval.mutable.SdmxMutableSubscriptionBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.SubscriptionBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.SubscriptionMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.registry.SubscriptionMutableBeanImpl;

import java.util.HashSet;
import java.util.Set;


/**
 * The type Sdmx mutable subscription bean retrieval manager.
 */
public class SdmxMutableSubscriptionBeanRetrievalManagerImpl implements SdmxMutableSubscriptionBeanRetrievalManager {
    private SdmxSubscriptionRetrievalManager subscriptionRetrievalManager;

    /**
     * Instantiates a new Sdmx mutable subscription bean retrieval manager.
     *
     * @param sdmxSubscriptionRetrievalManager the sdmx subscription retrieval manager
     */
    public SdmxMutableSubscriptionBeanRetrievalManagerImpl(SdmxSubscriptionRetrievalManager sdmxSubscriptionRetrievalManager) {
        this.subscriptionRetrievalManager = sdmxSubscriptionRetrievalManager;
        if (sdmxSubscriptionRetrievalManager == null) {
            throw new IllegalArgumentException("SdmxSubscriptionRetrievalManager can not be null");
        }
    }

    @Override
    public Set<SubscriptionMutableBean> getSubscriptions(StructureReferenceBean organisationReference) {
        Set<SubscriptionMutableBean> returnSet = new HashSet<SubscriptionMutableBean>();
        Set<SubscriptionBean> queryResults = subscriptionRetrievalManager.getSubscriptions(organisationReference);
        for (SubscriptionBean currentResult : queryResults) {
            returnSet.add(new SubscriptionMutableBeanImpl(currentResult));
        }
        return returnSet;
    }
}
