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
package org.sdmxsource.sdmx.api.model.beans.codelist;

import org.sdmxsource.sdmx.api.model.beans.base.NameableBean;
import org.sdmxsource.sdmx.api.model.beans.base.TextFormatBean;

/**
 * Represents an SDMX Level as defined by a HierarchyBean
 *
 * @author Matt Nelson
 * @see HierarchyBean
 */
public interface LevelBean extends NameableBean {

    /**
     * Gets coding format.
     *
     * @return the codeing format for this level
     */
    TextFormatBean getCodingFormat();

    /**
     * Returns the child level, returns null if there is no child
     *
     * @return child level
     */
    LevelBean getChildLevel();

    /**
     * Returns true if there is a child level, in which case the call to getChildLevel will always return a value
     *
     * @return the boolean
     */
    boolean hasChild();


}
