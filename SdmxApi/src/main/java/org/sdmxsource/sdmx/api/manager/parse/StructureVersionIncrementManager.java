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
package org.sdmxsource.sdmx.api.manager.parse;

import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;

/**
 * Increments the version of any maintainable beans if the bean already exists.  The rules are:
 * <br>
 * <ul>
 *   <li>If the beans contains a structure that does not yet exist in no action is taken</li>
 * 	 <li>If the structure already exists then the version number of the SdmxBeans structure is incremented, meaning the existing structure remains unchanged</li>
 *   <li>A minor version increment is performed if the structure does differ in the identifiable composite structures. For example, a codelist with exactly the same codes (a code is identifiable as it has a URN)</li>
 * 	 <li>A major increment is performed if the submitted structure has an additional identifiable or has had an identifiable removed. For example a codelist may have had a code removed</li>
 * 	 <li>A minor version increment is a .1 increase. For example 1.2 becomes 1.3 (or 1.3.2 becomes 1.4)</li>
 *   <li>A major version increment is a full integer increase. For example version 1.0 becomes 2.0 (or 1.4 becomes 2.0)</li>
 *   <li>Any structures which cross reference the existing structure will also have a minor version increment and the references will be updated to reference the latest structure.
 *       This rule is recursive, so any structures that reference the references are also updated. This rule will also apply if the referencing document is the same structure submission, as long as the referencing structure in the submission has had the reference modified</li>
 * </ul>
 */
public interface StructureVersionIncrementManager {

    /**
     * Processes the SdmxBeans to determine if any already exist. If they do then
     * it will determine if there are any changes to the structure, if there are then the submitted structure will have it's
     * version number automatically incremented
     *
     * @param beans the beans
     */
    void incrementVersions(SdmxBeans beans);

}
