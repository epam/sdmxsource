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
package org.sdmxsource.sdmx.api.model.superbeans.metadata;

import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistBean;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptBean;
import org.sdmxsource.sdmx.api.model.metadata.ReportedAttributeBean;
import org.sdmxsource.sdmx.api.model.superbeans.base.SuperBean;

import java.util.List;

/**
 * Wraps a reported attribute bean, has all referenced structures by compoisition
 */
public interface ReportedAttributeSuperBean extends SuperBean {

    /**
     * Gets id.
     *
     * @return the id
     */
    String getId();

    /**
     * Gets concept.
     *
     * @return the concept
     */
    ConceptBean getConcept();

    /**
     * Gets codelist.
     *
     * @return the codelist
     */
    CodelistBean getCodelist();

    /**
     * Has coded representation boolean.
     *
     * @return the boolean
     */
    boolean hasCodedRepresentation();


    /**
     * Returns a simple value for this attribute, returns null if there is no simple value
     *
     * @return simple value
     */
    String getSimpleValue();


    /**
     * Gets metadata text.
     *
     * @return a list of structured texts for this component - will return an empty list if no Texts exist.
     */
    List<TextTypeWrapper> getMetadataText();

    /**
     * Gets reported attributes.
     *
     * @return child attributes
     */
    List<ReportedAttributeSuperBean> getReportedAttributes();

    /**
     * Returns true if getMetadataText returns an empty list
     *
     * @return boolean
     */
    boolean isPresentational();

    /**
     * Returns true if this ReportedAttributeBean has a simple value, in which case getSimpleValue will return a not null value
     *
     * @return boolean
     */
    boolean hasSimpleValue();

    /**
     * Override from parent - returns the ReferenceValueBean that was used to build this SuperBean
     */
    @Override
    ReportedAttributeBean getBuiltFrom();

}
