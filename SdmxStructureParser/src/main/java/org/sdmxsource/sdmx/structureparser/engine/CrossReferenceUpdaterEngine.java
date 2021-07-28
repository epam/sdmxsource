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
package org.sdmxsource.sdmx.structureparser.engine;

import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;

import java.util.Map;

/**
 * Updates all cross references in a maintainable and reversions the maintainable
 *
 * @param <T> maintainableBean
 */
public interface CrossReferenceUpdaterEngine<T extends MaintainableBean> {

    /**
     * Updates the maintainable (T) by altering any references that exist in the key of the updateReferences Map, to the corresponding value in the Map, the version
     * of the maintainable is also changed to the newVersionNumber.
     *
     * @param maintianable     the maintainable to alter
     * @param updateReferences the references from/to map
     * @param newVersionNumber the version number to give the new maintainable
     * @return the newly created maintainable
     */
    T updateReferences(T maintianable, Map<StructureReferenceBean, StructureReferenceBean> updateReferences, String newVersionNumber);
}
