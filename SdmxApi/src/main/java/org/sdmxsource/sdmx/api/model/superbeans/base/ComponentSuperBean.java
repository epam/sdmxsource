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
package org.sdmxsource.sdmx.api.model.superbeans.base;

import org.sdmxsource.sdmx.api.model.beans.base.ComponentBean;
import org.sdmxsource.sdmx.api.model.beans.base.TextFormatBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodelistSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.conceptscheme.ConceptSuperBean;

/**
 * A component is something that can be conceptualised and where the values of the component
 * can also be taken from a Codelist, an example is a Dimension
 *
 * @author Matt Nelson
 */
public interface ComponentSuperBean extends IdentifiableSuperBean {

    /**
     * Returns the codelist, this may be null if there is none
     *
     * @param useConceptIfRequired if the representation is uncoded, but the concept has default representation, then the codelist for the concept will be returned if this parameter is set to true
     * @return codelist
     */
    CodelistSuperBean getCodelist(boolean useConceptIfRequired);

    /**
     * Returns the text format, this may be null if there is none
     *
     * @return text format
     */
    TextFormatBean getTextFormat();


    /**
     * Returns the concept, this is mandatory and will always return a value
     *
     * @return concept
     */
    ConceptSuperBean getConcept();

    @Override
    ComponentBean getBuiltFrom();

}
