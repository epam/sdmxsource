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

import org.sdmxsource.sdmx.api.manager.retrieval.SdmxSuperBeanRetrievalManager;
import org.sdmxsource.sdmx.api.manager.retrieval.mutable.MutableSuperBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.sdmx.api.model.mutablesuperbeans.codelist.HierarchicalCodelistMutableSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.HierarchicalCodelistSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.codelist.HierarchicalCodelistMutableSuperBeanImpl;


/**
 * This implementation of the MutableSuperBeanRetrievalManager wraps a SdmxSuperBeanRetrievalManager and mutates the responses.
 */
public class MutableSuperBeanRetrievalManagerImpl implements MutableSuperBeanRetrievalManager {

    private SdmxSuperBeanRetrievalManager sdmxSuperBeanRetrievalManager;

    /**
     * Instantiates a new Mutable super bean retrieval manager.
     *
     * @param sdmxSuperBeanRetrievalManager the sdmx super bean retrieval manager
     */
    public MutableSuperBeanRetrievalManagerImpl(SdmxSuperBeanRetrievalManager sdmxSuperBeanRetrievalManager) {
        this.sdmxSuperBeanRetrievalManager = sdmxSuperBeanRetrievalManager;
    }

    @Override
    public HierarchicalCodelistMutableSuperBean getHierarchicCodeListSuperBean(MaintainableRefBean ref) {
        HierarchicalCodelistSuperBean hclSuperBean = sdmxSuperBeanRetrievalManager.getHierarchicCodeListSuperBean(ref);
        if (hclSuperBean != null) {
            return new HierarchicalCodelistMutableSuperBeanImpl(hclSuperBean);
        }
        return null;
    }
}
