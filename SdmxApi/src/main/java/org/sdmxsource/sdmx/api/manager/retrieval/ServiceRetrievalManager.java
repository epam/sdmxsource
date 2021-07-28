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

import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;

import java.net.URL;


/**
 * Gives the ability to retrieve the structure, or the service URL for the application, allowing beans to be built as a stub
 */
public interface ServiceRetrievalManager {

    /**
     * Returns the URL to obtain structures from, this will be either a service URL
     *
     * @param maint the maint
     * @return structure or service url
     */
    ArtefactURL getStructureOrServiceURL(MaintainableBean maint);

    /**
     * Creates a stub bean from the given maintainable artefact
     *
     * @param maint the maint
     * @return maintainable bean
     */
    MaintainableBean createStub(MaintainableBean maint);

    /**
     * The artefact URL contains the url to resolve the artefact, and a boolean defining if it is a service URL or a structure URL
     */
    public class ArtefactURL {
        private URL url;
        private boolean serviceUrl;

        /**
         * Instantiates a new Artefact url.
         *
         * @param url        the url
         * @param serviceUrl the service url
         */
        public ArtefactURL(URL url, boolean serviceUrl) {
            super();
            this.url = url;
            this.serviceUrl = serviceUrl;
        }

        /**
         * Gets url.
         *
         * @return the url
         */
        public URL getUrl() {
            return url;
        }

        /**
         * Is sservice url boolean.
         *
         * @return the boolean
         */
        public boolean isSserviceUrl() {
            return serviceUrl;
        }
    }
}
