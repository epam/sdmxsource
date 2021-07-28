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
package org.sdmxsource.sdmx.util.beans.container;

import org.sdmxsource.sdmx.api.model.beans.AgencyMetadata;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeansInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Sdmx beans info.
 */
public class SdmxBeansInfoImpl implements SdmxBeansInfo {
    private List<AgencyMetadata> agencyMetadata = new ArrayList<AgencyMetadata>();


    @Override
    public List<AgencyMetadata> getAgencyMetadata() {
        return agencyMetadata;
    }

    /**
     * Sets agency metadata.
     *
     * @param agencyMetadata the agency metadata
     */
    public void setAgencyMetadata(List<AgencyMetadata> agencyMetadata) {
        if (agencyMetadata == null) {
            this.agencyMetadata = new ArrayList<AgencyMetadata>();
        } else {
            this.agencyMetadata = agencyMetadata;
        }
    }

    @Override
    public Integer getNumberMaintainables() {
        int i = 0;
        for (AgencyMetadata currentAgencyMetadata : getAgencyMetadata()) {
            i += currentAgencyMetadata.getNumberMaintainables();
        }
        return i;
    }

    /**
     * Sets number maintainables.
     *
     * @param num the num
     */
    public void setNumberMaintainables(Integer num) {
        //DO NOTHING - this method is here for passing to external applications
    }
}
