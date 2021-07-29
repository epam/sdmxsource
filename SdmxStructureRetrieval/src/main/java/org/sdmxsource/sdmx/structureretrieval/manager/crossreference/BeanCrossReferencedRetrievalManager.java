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
package org.sdmxsource.sdmx.structureretrieval.manager.crossreference;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.manager.retrieval.crossreference.CrossReferencedRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The type Bean cross referenced retrieval manager.
 */
public class BeanCrossReferencedRetrievalManager implements CrossReferencedRetrievalManager {
    private SdmxBeanRetrievalManager beanRetrievalManager;

    /**
     * Instantiates a new Bean cross referenced retrieval manager.
     *
     * @param beanRetrievalManager the bean retrieval manager
     */
    public BeanCrossReferencedRetrievalManager(SdmxBeanRetrievalManager beanRetrievalManager) {
        this.beanRetrievalManager = beanRetrievalManager;
        if (beanRetrievalManager == null) {
            throw new SdmxException("BeanCrossReferencedRetrievalManager - SdmxBeanRetrievalManager is required and was not provided");
        }
    }

    @Override
    public Set<MaintainableBean> getCrossReferencedStructures(
            StructureReferenceBean structureReference, boolean returnStub,
            SDMX_STRUCTURE_TYPE... structures) {
        return getCrossReferencedStructures(beanRetrievalManager.getMaintainableBean(structureReference), returnStub, structures);
    }

    @Override
    public Set<MaintainableBean> getCrossReferencedStructures(
            IdentifiableBean identifiable, boolean returnStub,
            SDMX_STRUCTURE_TYPE... structures) {

        Set<MaintainableBean> returnList = new HashSet<MaintainableBean>();
        Map<SDMX_STRUCTURE_TYPE, Set<MaintainableRefBean>> map = new HashMap<SDMX_STRUCTURE_TYPE, Set<MaintainableRefBean>>();

        for (CrossReferenceBean currentCrossReference : identifiable.getCrossReferences()) {
            Set<MaintainableRefBean> references = map.get(currentCrossReference.getMaintainableStructureType());
            if (references == null) {
                references = new HashSet<MaintainableRefBean>();
                map.put(currentCrossReference.getMaintainableStructureType(), references);
            }

            if (references.contains(currentCrossReference.getMaintainableReference())) {
                continue;
            }
            references.add(currentCrossReference.getMaintainableReference());

            MaintainableBean maint = beanRetrievalManager.getMaintainableBean(currentCrossReference, returnStub, false);
            if (!returnList.contains(maint)) {
                returnList.add(maint);
            }
        }
        return returnList;
    }

}
