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
package org.sdmxsource.sdmx.structureretrieval.manager;

import org.sdmxsource.sdmx.api.manager.retrieval.ServiceRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * The type Default service retrieval manager.
 */
public class DefaultServiceRetrievalManager implements ServiceRetrievalManager {
    private String baseUrl;

    @Override
    public ArtefactURL getStructureOrServiceURL(MaintainableBean maint) {
        String fullRest = baseUrl + "/" + maint.getStructureType().getUrnClass().toLowerCase() + "/" + maint.getAgencyId() + "/" + maint.getId() + "/" + maint.getVersion();

        try {
            return new ArtefactURL(new URL(fullRest), false);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not return service URL for structure - bad URL syntax " + fullRest, e);
        }
    }

    @Override
    public MaintainableBean createStub(MaintainableBean maint) {
        ArtefactURL artefactUrl = getStructureOrServiceURL(maint);
        return maint.getStub(artefactUrl.getUrl(), artefactUrl.isSserviceUrl());
    }

    /**
     * Sets base url.
     *
     * @param baseUrl the base url
     */
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
