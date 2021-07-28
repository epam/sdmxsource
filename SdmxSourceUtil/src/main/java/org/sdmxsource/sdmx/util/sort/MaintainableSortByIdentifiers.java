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

import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.util.VersionableUtil;

import java.util.Comparator;


/**
 * The type Maintainable sort by identifiers.
 */
public class MaintainableSortByIdentifiers implements Comparator<MaintainableBean> {

    @Override
    public int compare(MaintainableBean maintOne, MaintainableBean maintTwo) {
        if (maintOne.equals(maintTwo)) {
            return 0;
        }

        int comp = maintOne.getStructureType().getType().compareTo(maintTwo.getStructureType().getType());
        if (comp != 0) {
            return comp;
        }

        String agencyId1 = maintOne.getAgencyId();
        String agencyId2 = maintTwo.getAgencyId();
        comp = agencyId1.compareTo(agencyId2);
        if (comp != 0) {
            return comp;
        }

        String id1 = maintOne.getId();
        String id2 = maintTwo.getId();
        comp = id1.compareTo(id2);
        if (comp != 0) {
            return comp;
        }

        String v1 = maintOne.getVersion();
        String v2 = maintTwo.getVersion();
        if (v1.equals(v2)) {
            //SHOULD NEVER HAPPEN
            return -1;
        }
        return VersionableUtil.isHigherVersion(v2, v1) ? -1 : 1;
    }
}
