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
package org.sdmxsource.sdmx.api.builder;

import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.superbeans.SuperBeans;
import org.sdmxsource.sdmx.api.model.superbeans.base.SuperBean;

/**
 * Base interface for specific super bean builder classes, to be built from corresponding plain beans.
 * <p>
 * &lt;K&gt;structure super bean (to)
 * &lt;V&gt;corresponding plain bean (from)
 */
public interface SuperBeanBuilder<K extends SuperBean, V extends SDMXBean> {

    /**
     * Build a SuperBean from a SdmxBean and a corresponding SdmxBeanRetrievalManager (optional) to resolve any cross-referenced artifacts
     *
     * @param buildFrom        - the SdmxBean to build from
     * @param retrievalManager - to resolve any cross referenced artifacts declared by V - the buildFrom argument
     * @param existingBeans    - can be null if none already exist
     * @return K - the SdmxSuper bean representation of the SdmxBean argument
     */
    K build(V buildFrom, SdmxBeanRetrievalManager retrievalManager, SuperBeans existingBeans);
}
