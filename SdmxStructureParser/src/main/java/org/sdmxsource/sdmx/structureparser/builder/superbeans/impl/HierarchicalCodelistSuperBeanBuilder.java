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
package org.sdmxsource.sdmx.structureparser.builder.superbeans.impl;

import org.sdmxsource.sdmx.api.manager.retrieval.IdentifiableRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.HierarchicalCodelistBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.HierarchicalCodelistSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.codelist.HierarchicalCodelistSuperBeanImpl;
import org.sdmxsource.sdmx.structureretrieval.engine.impl.CrossReferenceResolverEngineImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * The type Hierarchical codelist super bean builder.
 */
public class HierarchicalCodelistSuperBeanBuilder {

    /**
     * Build hierarchical codelist super bean.
     *
     * @param buildFrom        the build from
     * @param retrievalManager the retrieval manager
     * @return the hierarchical codelist super bean
     */
    public HierarchicalCodelistSuperBean build(HierarchicalCodelistBean buildFrom,
                                               IdentifiableRetrievalManager retrievalManager) {

        CrossReferenceResolverEngineImpl resolverEngine = new CrossReferenceResolverEngineImpl();
        Set<IdentifiableBean> identifiables = resolverEngine.resolveReferences(buildFrom, false, 1, retrievalManager);

        List<CodelistBean> referencedCodelists = new ArrayList<CodelistBean>();
        for (IdentifiableBean currentIdentifiable : identifiables) {
            MaintainableBean maint = currentIdentifiable.getMaintainableParent();
            if (!referencedCodelists.contains(maint)) {
                if (maint instanceof CodelistBean) {
                    referencedCodelists.add((CodelistBean) maint);
                }
            }
        }
        return new HierarchicalCodelistSuperBeanImpl(buildFrom, referencedCodelists);
    }
}
