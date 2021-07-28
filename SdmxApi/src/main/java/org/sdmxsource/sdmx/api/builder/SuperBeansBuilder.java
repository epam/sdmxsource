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

import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.superbeans.SuperBeans;

/**
 * Builds a SuperBeans container from the contents of the SdmxBeans container.
 *
 * @see SuperBeans
 * @see SdmxBeans
 */
public interface SuperBeansBuilder extends Builder<SuperBeans, SdmxBeans> {

    /**
     * Builds SuperBeans from the input beans, obtains any additional required beans from the retrieval manager.
     *
     * @param buildFrom        the build from
     * @param existingBeans    if any super beans exist then they should be passed in here in order to reuse - new beans will be added to this container
     * @param retrievalManager the retrieval manager
     * @return super beans
     * @throws SdmxException the sdmx exception
     */
    SuperBeans build(SdmxBeans buildFrom, SuperBeans existingBeans, SdmxBeanRetrievalManager retrievalManager) throws SdmxException;


}
