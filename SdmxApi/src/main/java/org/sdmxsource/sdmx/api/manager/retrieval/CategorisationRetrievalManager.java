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
package org.sdmxsource.sdmx.api.manager.retrieval;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategorisationBean;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategoryBean;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategorySchemeBean;
import org.sdmxsource.sdmx.api.model.superbeans.categoryscheme.CategorisationSuperBean;

import java.util.Set;


/**
 * Responsible for retrieving categorisations for particular structures
 *
 * @author MetaTech
 */
public interface CategorisationRetrievalManager {

    /**
     * Returns categorisations for a given identifiable
     *
     * @param identifiable the identifiable
     * @return categorisations
     */
    Set<CategorisationBean> getCategorisations(IdentifiableBean identifiable);


    /**
     * Returns all categorisations for the given category scheme
     *
     * @param scheme the scheme
     * @return categorisations for category scheme
     */
    Set<CategorisationBean> getCategorisationsForCategoryScheme(CategorySchemeBean scheme);

    /**
     * Returns all categorisations for the given category
     *
     * @param category the category
     * @return categorisations for category
     */
    Set<CategorisationBean> getCategorisationsForCategory(CategoryBean category);

    /**
     * Returns the categorisation super beans for a given identifiable
     *
     * @param identifiable the identifiable
     * @return categorisation super bean
     */
    Set<CategorisationSuperBean> getCategorisationSuperBean(IdentifiableBean identifiable);

    /**
     * Returns the categorisation super beans for a given category, optionally a filter of allowable categorised
     * structure types can be returned
     *
     * @param category     the category
     * @param includeTypes the include types
     * @return categorisation super bean for category
     */
    Set<CategorisationSuperBean> getCategorisationSuperBeanForCategory(CategoryBean category, SDMX_STRUCTURE_TYPE... includeTypes);


}
