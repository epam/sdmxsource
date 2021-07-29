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
package org.sdmxsource.sdmx.querybuilder.factory;

import org.sdmxsource.sdmx.api.builder.DataQueryBuilder;
import org.sdmxsource.sdmx.api.factory.DataQueryFactory;
import org.sdmxsource.sdmx.api.model.format.DataQueryFormat;
import org.sdmxsource.sdmx.querybuilder.builder.DataQueryBuilderRest;
import org.sdmxsource.sdmx.querybuilder.model.RestQueryFormat;

/**
 * If the required format is RestQueryFormat, then will returns a builder that can build REST
 */
public class RestDataQueryFactory implements DataQueryFactory {

    private final DataQueryBuilderRest dataQueryBuilderRest;

    /**
     * Instantiates a new Rest data query factory.
     */
    public RestDataQueryFactory() {
        this(new DataQueryBuilderRest());
    }

    /**
     * Instantiates a new Rest data query factory.
     *
     * @param dataQueryBuilderRest the data query builder rest
     */
    public RestDataQueryFactory(final DataQueryBuilderRest dataQueryBuilderRest) {
        this.dataQueryBuilderRest = dataQueryBuilderRest;
    }

    @Override
    public <T> DataQueryBuilder<T> getDataQueryBuilder(DataQueryFormat<T> format) {
        if (format instanceof RestQueryFormat) {
            return (DataQueryBuilder<T>) dataQueryBuilderRest;
        }
        return null;
    }

}
