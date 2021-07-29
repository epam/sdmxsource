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

import org.sdmxsource.sdmx.api.model.beans.base.ItemSchemeBean;
import org.sdmxsource.sdmx.api.model.mutable.codelist.CodelistMutableBean;

import java.net.URL;


/**
 * Represents an SDMX Code List
 * <pre>
 *               __
 *              / _)
 *     _/\/\/\_/ /
 *   _|         /
 *  _|  (  | (  |
 * /__.-'|_|--|_|     Raaar
 * </pre>
 *
 * @author Matt Nelson
 */
public interface CodelistBean extends ItemSchemeBean<CodeBean> {

    /**
     * Returns true if the codelist is only reporting a subset of the codes.
     * <p>
     * Partial codelists are used for dissemination purposes only not for reporting updates.
     *
     * @return is partial
     */
    @Override
    boolean isPartial();

    /**
     * Returns the code with the given id, returns null if there is no code with the id provided
     *
     * @param id the id
     * @return code by id
     */
    CodeBean getCodeById(String id);

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
    CodelistBean getStub(URL actualLocation, boolean isServiceUrl);

    /**
     * Returns a representation of itself in a bean which can be modified, modifications to the mutable bean
     * are not reflected in the instance of the MaintainableBean.
     *
     * @return the mutable instance
     */
    @Override
    CodelistMutableBean getMutableInstance();

}
