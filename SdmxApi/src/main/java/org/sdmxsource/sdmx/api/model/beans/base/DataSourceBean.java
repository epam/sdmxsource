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
package org.sdmxsource.sdmx.api.model.beans.base;

import org.sdmxsource.sdmx.api.model.mutable.base.DataSourceMutableBean;

import java.net.URL;

/**
 * Describes the location and type of DataSource.
 */
public interface DataSourceBean extends SdmxStructureBean {

    /**
     * Returns true if the getDataUrl() is pointing at a REST web service.
     *
     * @return rest datasource
     */
    boolean isRESTDatasource();

    /**
     * Returns true if the getDataUrl() is pointing at a SOAP web service.
     *
     * @return web service datasource
     */
    boolean isWebServiceDatasource();

    /**
     * Returns true if the getDataUrl() is pointing at a file location.
     *
     * @return simple datasource
     */
    boolean isSimpleDatasource();

    /**
     * Returns the URL of the datasource, this can never be null.
     *
     * @return data url
     */
    URL getDataUrl();

    /**
     * Returns the URL of the WSDL or null if no WSDL location has been defined.
     *
     * @return wsdl url
     */
    URL getWSDLUrl();

    /**
     * Returns the WADL URL or null if no WADL location has been defined.
     *
     * @return wadl url
     */
    URL getWadlUrl();

    /**
     * Returns a mutable version of this bean instance.
     *
     * @return data source mutable bean
     */
    DataSourceMutableBean createMutableInstance();
}
