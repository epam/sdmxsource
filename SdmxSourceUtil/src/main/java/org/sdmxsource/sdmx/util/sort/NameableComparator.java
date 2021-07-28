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
package org.sdmxsource.sdmx.util.sort;

import org.sdmxsource.sdmx.api.model.beans.base.NameableBean;

import java.util.Comparator;

/**
 * The type Nameable comparator.
 */
public class NameableComparator implements Comparator<NameableBean> {
    /**
     * The constant INSTANCE.
     */
    public static final NameableComparator INSTANCE = new NameableComparator();

    private NameableComparator() {
    }

    @Override
    /**
     * Compares two nameables by name
     */
    public int compare(NameableBean iBean1, NameableBean iBean2) {
        String id1 = iBean1.getName();
        String id2 = iBean2.getName();
        int compare = id1.compareToIgnoreCase(id2);
        if (compare == 0) {
            return iBean1.getUrn().compareTo(iBean2.getUrn());
        }
        return compare;
    }

}
