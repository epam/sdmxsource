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
package org.sdmxsource.sdmx.api.factory;

import org.sdmxsource.sdmx.api.builder.StructureQueryBuilder;
import org.sdmxsource.sdmx.api.model.format.StructureQueryFormat;

/**
 * A structure query factory is a factory which is responsible fore creating a builder that can build
 * a StructureQuery message in the format defined by the StructureQueryFormat
 */
public interface StructureQueryFactory {

    /**
     * Returns a StructureQueryBuilder only if this factory understands the StructureQueryFormat.  If the format is unknown, null will be returned
     *
     * @param <T>    the type parameter
     * @param format the format
     * @return StructureQueryBuilder is this factory knows how to build this query format, or null if it doesn't
     */
    <T> StructureQueryBuilder<T> getStructureQueryBuilder(StructureQueryFormat<T> format);

}
