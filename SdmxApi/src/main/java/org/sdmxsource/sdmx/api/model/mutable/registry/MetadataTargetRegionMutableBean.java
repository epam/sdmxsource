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
package org.sdmxsource.sdmx.api.model.mutable.registry;

import org.sdmxsource.sdmx.api.model.mutable.base.MutableBean;

import java.util.List;

/**
 * The interface Metadata target region mutable bean.
 */
public interface MetadataTargetRegionMutableBean extends MutableBean {
    /**
     * Returns true if the information reported is to be included or excluded
     *
     * @return boolean
     */
    boolean isInclude();

    /**
     * Gets report.
     *
     * @return the report
     */
    String getReport();

    void setReport(String report);

    /**
     * Gets metadata target.
     *
     * @return the metadata target
     */
    String getMetadataTarget();

    void setMetadataTarget(String metadataTarget);

    /**
     * Returns the key values restrictions for the metadata target region
     *
     * @return key
     */
    List<MetadataTargetKeyValuesMutable> getKey();

    /**
     * Sets key.
     *
     * @param key the key
     */
    void setKey(List<MetadataTargetKeyValuesMutable> key);

    /**
     * Add key.
     *
     * @param key the key
     */
    void addKey(MetadataTargetKeyValuesMutable key);

    /**
     * Returns the attributes restrictions for the metadata target region
     *
     * @return attributes
     */
    List<KeyValuesMutable> getAttributes();


    /**
     * Sets attributes.
     *
     * @param attributes the attributes
     */
    void setAttributes(List<KeyValuesMutable> attributes);

    /**
     * Add attribute.
     *
     * @param attribute the attribute
     */
    void addAttribute(KeyValuesMutable attribute);

}
