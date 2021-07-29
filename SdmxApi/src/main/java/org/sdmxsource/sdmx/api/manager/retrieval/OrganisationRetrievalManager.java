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
package org.sdmxsource.sdmx.api.manager.retrieval;

import org.sdmxsource.sdmx.api.model.beans.base.AgencyBean;
import org.sdmxsource.sdmx.api.model.beans.base.DataConsumerBean;
import org.sdmxsource.sdmx.api.model.beans.base.DataProviderBean;

/**
 * Used to retrieve Organisations
 *
 * @author MetaTech
 */
public interface OrganisationRetrievalManager {

    /**
     * Returns the data provider belongning to the given agency, with the given id
     *
     * @param agencyId a period separated agency id, where the periods are used to define sub Agencies
     * @param id       id of the data provider to return
     * @return DataProvider, or null if one can not be found with the given reference parameters
     */
    DataProviderBean getDataProvider(String agencyId, String id);

    /**
     * Returns the data consumer belonging to the given agency, with the given id
     *
     * @param agencyId a period separated agency id, where the periods are used to define sub Agencies
     * @param id       id of the data provider to return
     * @return DataProvider, or null if one can not be found with the given reference parameters
     */
    DataConsumerBean getDataConsumerBean(String agencyId, String id);

    /**
     * Returns the agency with the given id
     *
     * @param agencyId a period separated agency id, where the periods are used to define sub Agencies
     * @return DataProvider, or null if one can not be found with the given reference parameters
     */
    AgencyBean getAgency(String agencyId);
}
