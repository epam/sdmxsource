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

import org.sdmxsource.sdmx.api.manager.retrieval.crossreference.CrossReferenceInformationManager;
import org.sdmxsource.sdmx.api.manager.retrieval.crossreference.CrossReferencingRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferencingTree;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.reference.CrossReferencingTreeImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The type Cross reference information manager.
 */
public class CrossReferenceInformationManagerImpl implements CrossReferenceInformationManager {
    private CrossReferencingRetrievalManager crossReferencingRetrievalManager;

    @Override
    public CrossReferencingTree getCrossReferenceTree(MaintainableBean maintainableBean) {
        Set<MaintainableBean> allMaintainables = new HashSet<MaintainableBean>();
        allMaintainables.add(maintainableBean);
        return getCrossReferenceTree(maintainableBean, allMaintainables);
    }

    private CrossReferencingTree getCrossReferenceTree(MaintainableBean maintainableBean, Set<MaintainableBean> allMaintainables) {
        List<CrossReferencingTree> referencingBeans = new ArrayList<CrossReferencingTree>();
        for (MaintainableBean currentReferencing : crossReferencingRetrievalManager.getCrossReferencingStructures(maintainableBean, true)) {
            if (allMaintainables.add(currentReferencing)) {
                referencingBeans.add(getCrossReferenceTree(currentReferencing));
            }
        }
        return new CrossReferencingTreeImpl(maintainableBean, referencingBeans);
    }

    /**
     * Sets cross referencing retrieval manager.
     *
     * @param crossReferencingRetrievalManager the cross referencing retrieval manager
     */
    public void setCrossReferencingRetrievalManager(CrossReferencingRetrievalManager crossReferencingRetrievalManager) {
        this.crossReferencingRetrievalManager = crossReferencingRetrievalManager;
    }
}
