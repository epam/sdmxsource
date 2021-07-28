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
package org.sdmxsource.sdmx.api.model.beans.registry;

import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.SubscriptionMutableBean;

import java.util.List;


/**
 * Represents an SDMX Subscription
 *
 * @author Matt Nelson
 */
public interface SubscriptionBean extends MaintainableBean {
    /**
     * The constant WILDCARD.
     */
    static String WILDCARD = "%";

    /**
     * Returns a reference to the owner of this subscription
     *
     * @return owner owner
     */
    CrossReferenceBean getOwner();

    /**
     * Returns a list of email addresses to mail any notifications to
     *
     * @return mail to
     */
    List<String> getMailTo();

    /**
     * Returns a list of HTTP addresses to POST any notifications to
     *
     * @return http post to
     */
    List<String> getHTTPPostTo();

    /**
     * Returns a list of structures that this subscription is subscribing to,
     * or in the case that this is subscribing to a provision or registration, returns the structure
     * reference that it is subscribing to the provision or registration by.
     *
     * @return references references
     */
    List<StructureReferenceBean> getReferences();


    /**
     * Returns if this is a subscription for a structure event a provision event or a registration event
     *
     * @return subscription type
     */
    SUBSCRIPTION_TYPE getSubscriptionType();


    /**
     * Returns a representation of itself in a bean which can be modified, modifications to the mutable bean
     * are not reflected in the instance of the MaintainableBean.
     *
     * @return the mutable instance
     */
    @Override
    SubscriptionMutableBean getMutableInstance();


    /**
     * The enum Subscription type.
     */
    enum SUBSCRIPTION_TYPE {
        /**
         * Data registration subscription type.
         */
        DATA_REGISTRATION,
        /**
         * Metadata registration subscription type.
         */
        METADATA_REGISTRATION,
        /**
         * Structure subscription type.
         */
        STRUCTURE;
    }
}
