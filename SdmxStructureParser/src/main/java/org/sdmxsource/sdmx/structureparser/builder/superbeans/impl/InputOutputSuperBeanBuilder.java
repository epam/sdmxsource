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
import org.sdmxsource.sdmx.api.model.beans.process.InputOutputBean;
import org.sdmxsource.sdmx.api.model.superbeans.process.InputOutputSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.process.InputOutputSuperBeanImpl;
import org.sdmxsource.sdmx.structureretrieval.engine.CrossReferenceResolverEngine;
import org.sdmxsource.sdmx.structureretrieval.engine.impl.CrossReferenceResolverEngineImpl;

/**
 * The type Input output super bean builder.
 */
public class InputOutputSuperBeanBuilder {

    /**
     * Build input output super bean.
     *
     * @param buildFrom        the build from
     * @param retrievalManager the retrieval manager
     * @return the input output super bean
     */
    public InputOutputSuperBean build(InputOutputBean buildFrom,
                                      IdentifiableRetrievalManager retrievalManager) {
        CrossReferenceResolverEngine resolver = new CrossReferenceResolverEngineImpl();
        IdentifiableBean ibean = resolver.resolveCrossReference(buildFrom.getStructureReference(), retrievalManager);
        return new InputOutputSuperBeanImpl(buildFrom, ibean);
    }
}
