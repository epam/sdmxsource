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
package org.sdmxsource.sdmx.api.manager.retrieval.mutable;

import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.MutableBeans;
import org.sdmxsource.sdmx.api.model.mutable.base.MaintainableMutableBean;
import org.sdmxsource.sdmx.api.model.query.RESTStructureQuery;

import java.util.List;


/**
 * The interface Mutable structure search manager.
 */
public interface MutableStructureSearchManager {

    /**
     * Returns the latest version of the maintainable for the given maintainable input
     *
     * @param maintainableBean the maintainable bean
     * @return latest
     */
    MaintainableMutableBean getLatest(MaintainableMutableBean maintainableBean);

    /**
     * Returns a set of maintainables that match the given query parameters
     *
     * @param structureQuery the structure query
     * @return maintainables
     */
    MutableBeans getMaintainables(RESTStructureQuery structureQuery);

    /**
     * Retrieves all structures that match the given query parameters in the list of query objects.  The list
     * must contain at least one StructureQueryObject.
     *
     * @param queries           the queries
     * @param resolveReferences - if set to true then any cross referenced structures will also be available in the SdmxBeans container
     * @param returnStub        the return stub
     * @return mutable beans
     */
    MutableBeans retrieveStructures(List<StructureReferenceBean> queries, boolean resolveReferences, boolean returnStub);


}
