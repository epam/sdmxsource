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
package org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.datastructure;

import org.sdmxsource.sdmx.api.model.mutablesuperbeans.datastructure.GroupMutableSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.GroupSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.base.IdentifiableMutableSuperBeanImpl;


/**
 * The type Group mutable super bean.
 */
public class GroupMutableSuperBeanImpl extends IdentifiableMutableSuperBeanImpl implements GroupMutableSuperBean {
    private static final long serialVersionUID = 5282160962223299927L;

    /**
     * Instantiates a new Group mutable super bean.
     *
     * @param group the group
     */
//	private String attachmentConstraintRef;
//	private List<TextTypeWrapper> description;
//	private List<DimensionSuperBean> dimension = new ArrayList<DimensionSuperBean>();
//	private String id;
    //TODO implement
    public GroupMutableSuperBeanImpl(GroupSuperBean group) {
        super(group);
    }

    /**
     * Instantiates a new Group mutable super bean.
     */
    public GroupMutableSuperBeanImpl() {

    }

}
