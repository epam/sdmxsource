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
package org.sdmxsource.sdmx.structureparser.builder.sdmxbeans.impl;

import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;

import java.util.Set;


/**
 * The type Abstract sdmx beans builder.
 */
public abstract class AbstractSdmxBeansBuilder {

    /**
     * Adds a bean to the container as long as the urn of the bean to add is not contained in the set of URNs.
     * <p>
     * If successfully added, will add the bean urn to the set of urns
     *
     * @param beans container to add to
     * @param urns  to exclude
     * @param bean  to add to the beans container
     */
    protected void addIfNotDuplicateURN(SdmxBeans beans, Set<String> urns, IdentifiableBean bean) {
        if (!urns.add(bean.getUrn())) {
            throw new SdmxSemmanticException(ExceptionCode.DUPLICATE_URN, bean.getUrn());
        }
        beans.addIdentifiable(bean);
    }

}
