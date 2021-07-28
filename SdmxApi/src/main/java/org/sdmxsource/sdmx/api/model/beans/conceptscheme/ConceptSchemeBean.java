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
package org.sdmxsource.sdmx.api.model.beans.conceptscheme;

import org.sdmxsource.sdmx.api.model.beans.base.ItemSchemeBean;
import org.sdmxsource.sdmx.api.model.mutable.conceptscheme.ConceptSchemeMutableBean;

import java.net.URL;


/**
 * Represents an SDMX Concept Scheme
 *
 * @author Matt Nelson
 */
public interface ConceptSchemeBean extends ItemSchemeBean<ConceptBean> {
    /**
     * The constant DEFAULT_SCHEME_ID.
     */
    static String DEFAULT_SCHEME_ID = "STANDALONE_CONCEPT_SCHEME";
    /**
     * The constant DEFAULT_SCHEME_NAME.
     */
    static String DEFAULT_SCHEME_NAME = "Default Scheme";
    /**
     * The constant DEFAULT_SCHEME_VERSION.
     */
    static String DEFAULT_SCHEME_VERSION = "1.0";

    /**
     * Returns a stub reference of itself.
     * <p>
     * A stub bean only contains Maintainable and Identifiable Attributes, not the composite Objects that are
     * contained within the Maintainable
     *
     * @param actualLocation the URL indicating where the full structure can be returned from
     * @param isServiceUrl   if true this URL will be present on the serviceURL attribute, otherwise it will be treated as a structureURL attribute
     * @return the stub
     */
    @Override
    ConceptSchemeBean getStub(URL actualLocation, boolean isServiceUrl);

    /**
     * Returns the concept by id, or null if there is no concept with the given id
     *
     * @param id the id
     * @return item by id
     */
    ConceptBean getItemById(String id);

    /**
     * Returns a representation of itself in a bean which can be modified, modifications to the mutable bean
     * are not reflected in the instance of the MaintainableBean.
     *
     * @return the concept scheme
     */
    @Override
    ConceptSchemeMutableBean getMutableInstance();

    /**
     * Returns true if this is the default concept scheme
     *
     * @return is the default scheme set
     */
    boolean isDefaultScheme();

}
