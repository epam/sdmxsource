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
package org.sdmxsource.sdmx.api.model.mutable.mapping;

import org.sdmxsource.sdmx.api.constants.TO_VALUE;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.base.MutableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.TextFormatMutableBean;

import java.util.Map;
import java.util.Set;


/**
 * The interface Representation map ref mutable bean.
 */
public interface RepresentationMapRefMutableBean extends MutableBean {


    /**
     * Gets codelist map.
     *
     * @return the codelist map
     */
    StructureReferenceBean getCodelistMap();

    /**
     * Sets codelist map.
     *
     * @param codelistRef the codelist ref
     */
    void setCodelistMap(StructureReferenceBean codelistRef);

    /**
     * Gets to text format.
     *
     * @return the to text format
     */
    TextFormatMutableBean getToTextFormat();

    /**
     * Sets to text format.
     *
     * @param toTextFormat the to text format
     */
    void setToTextFormat(TextFormatMutableBean toTextFormat);

    /**
     * Gets to value type.
     *
     * @return the to value type
     */
    TO_VALUE getToValueType();

    /**
     * Sets to value type.
     *
     * @param toValue the to value
     */
    void setToValueType(TO_VALUE toValue);

    /**
     * Gets value mappings.
     *
     * @return the value mappings
     */
    Map<String, Set<String>> getValueMappings();

    /**
     * Sets value mappings.
     *
     * @param mapping the mapping
     */
    void setValueMappings(Map<String, Set<String>> mapping);

    /**
     * Maps the component id to the component with the given value
     *
     * @param componentId    the component id
     * @param componentValue the component value
     */
    void addMapping(String componentId, String componentValue);

}
