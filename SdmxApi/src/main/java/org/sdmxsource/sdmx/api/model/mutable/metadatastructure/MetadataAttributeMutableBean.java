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
package org.sdmxsource.sdmx.api.model.mutable.metadatastructure;

import org.sdmxsource.sdmx.api.constants.TERTIARY_BOOL;
import org.sdmxsource.sdmx.api.model.mutable.base.ComponentMutableBean;

import java.util.List;


/**
 * The interface Metadata attribute mutable bean.
 */
public interface MetadataAttributeMutableBean extends ComponentMutableBean {

    /**
     * Gets min occurs.
     *
     * @return the min occurs
     */
    Integer getMinOccurs();

    /**
     * Sets min occurs.
     *
     * @param minOccurs the min occurs
     */
    void setMinOccurs(Integer minOccurs);

    /**
     * Gets max occurs.
     *
     * @return the max occurs
     */
    Integer getMaxOccurs();

    /**
     * Sets max occurs.
     *
     * @param maxOccurs the max occurs
     */
    void setMaxOccurs(Integer maxOccurs);

    /**
     * Gets presentational.
     *
     * @return the presentational
     */
    TERTIARY_BOOL getPresentational();

    /**
     * Sets presentational.
     *
     * @param bool the bool
     */
    void setPresentational(TERTIARY_BOOL bool);

    /**
     * Returns any child metadata attributes
     *
     * <b>NOTE</b>The list is a copy so modifying the returned list will not
     * be reflected in the MetadataAttributeBean instance
     *
     * @return metadata attributes
     */
    List<MetadataAttributeMutableBean> getMetadataAttributes();

    /**
     * Sets metadata attributes.
     *
     * @param list the list
     */
    void setMetadataAttributes(List<MetadataAttributeMutableBean> list);
}
