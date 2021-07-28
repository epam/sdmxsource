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
package org.sdmxsource.sdmx.querybuilder.manager;

import org.sdmxsource.sdmx.api.builder.DataQueryBuilder;
import org.sdmxsource.sdmx.api.exception.SdmxUnauthorisedException;
import org.sdmxsource.sdmx.api.factory.DataQueryFactory;
import org.sdmxsource.sdmx.api.manager.query.DataQueryBuilderManager;
import org.sdmxsource.sdmx.api.model.data.query.DataQuery;
import org.sdmxsource.sdmx.api.model.format.DataQueryFormat;

/**
 * The type Data query builder manager.
 */
public class DataQueryBuilderManagerImpl implements DataQueryBuilderManager {

    private final DataQueryFactory[] factories;

    /**
     * Instantiates a new Data query builder manager.
     *
     * @param factories the factories
     */
    public DataQueryBuilderManagerImpl(final DataQueryFactory[] factories) {
        this.factories = factories;
    }

    @Override
    public <T> T buildDataQuery(DataQuery dataQuery, DataQueryFormat<T> dataQueryFormat) {
        if (factories != null && factories.length != 0) {
            for (DataQueryFactory currentFactory : factories) {
                DataQueryBuilder<T> builder = currentFactory.getDataQueryBuilder(dataQueryFormat);
                if (builder != null) {
                    return builder.buildDataQuery(dataQuery);
                }
            }
        }
        throw new SdmxUnauthorisedException("Unsupported DataQueryFormat: " + dataQueryFormat);
    }
}
