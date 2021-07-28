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
package org.sdmxsource.sdmx.api.model.beans;

import org.sdmxsource.sdmx.api.model.beans.registry.RegistrationBean;
import org.sdmxsource.sdmx.api.model.beans.registry.SubscriptionBean;

import java.util.List;


/**
 * RegistryBeans is a container for all registry content including the following; SdmxBeans which is a container for all structural metadata,
 * RegistrationBeans and SubscriptionBeans.
 *
 * @author Matt Nelson
 */
public interface RegistryBeans {

    /**
     * Returns true if there are SdmxBeans contained in this container
     *
     * @return boolean
     */
    boolean hasSdmxBeans();

    /**
     * Returns the Sdmxbeans contained in this container, returns <code>null</code> if there are none
     *
     * @return sdmx beans
     */
    SdmxBeans getSdmxBeans();

    /**
     * Returns true if there are RegistrationBean contained in this container
     *
     * @return boolean
     */
    boolean hasRegistrationBean();

    /**
     * Returns all the registrations in this container, returns an empty list if there are none
     *
     * @return registrations
     */
    List<RegistrationBean> getRegistrations();

    /**
     * Returns true if there are SubscriptionBean contained in this container
     *
     * @return boolean
     */
    boolean hasSubscriptionBean();

    /**
     * Returns all the subscriptions in this container, returns an empty list if there are none
     *
     * @return the subscriptions
     */
    List<SubscriptionBean> getSubscriptions();

}
