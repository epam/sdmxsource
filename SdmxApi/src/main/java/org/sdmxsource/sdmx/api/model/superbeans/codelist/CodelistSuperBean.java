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
package org.sdmxsource.sdmx.api.model.superbeans.codelist;

import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistBean;
import org.sdmxsource.sdmx.api.model.superbeans.base.MaintainableSuperBean;

import java.util.List;


/**
 * A CodelistSuperBean is a container for codes.
 */
public interface CodelistSuperBean extends MaintainableSuperBean {

    /**
     * Returns the codes in this codelist. As codes are hierarchical only the top level codes, with no parents, will be returned.
     *
     * @return the codes in this codelist.
     */
    List<CodeSuperBean> getCodes();

    /**
     * Iterates through the code hierarchy and returns the CodeSuperBean that has the same id as that supplied.
     *
     * @param id the id of a CodeSuperBean to search for.
     * @return the matching CodeSuperBean or null if there was no match.
     */
    CodeSuperBean getCodeByValue(String id);

    /**
     * @return The CodelistBean that this CodelistSuperBean was built from.
     */
    @Override
    CodelistBean getBuiltFrom();
}
