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
package org.sdmxsource.sdmx.structureretrieval.manager;

import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.manager.retrieval.StructureVersionRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;

/**
 * The type Structure version retrieval manager.
 */
public class StructureVersionRetrievalManagerImpl implements StructureVersionRetrievalManager {
    private SdmxBeanRetrievalManager beanRetrievalManager;

    @Override
    public MaintainableBean getLatest(MaintainableBean maintainableBean) {
        if (maintainableBean == null) {
            return null;
        }
        return beanRetrievalManager.getMaintainableBean(maintainableBean.asReference(), false, true);
    }

    /**
     * Sets bean retrieval manager.
     *
     * @param beanRetrievalManager the bean retrieval manager
     */
    public void setBeanRetrievalManager(SdmxBeanRetrievalManager beanRetrievalManager) {
        this.beanRetrievalManager = beanRetrievalManager;
    }
}
