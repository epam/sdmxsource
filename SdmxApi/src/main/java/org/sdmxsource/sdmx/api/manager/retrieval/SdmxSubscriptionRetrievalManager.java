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

import org.sdmxsource.sdmx.api.exception.CrossReferenceException;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.beans.base.OrganisationBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.SubscriptionBean;

import java.util.Set;


/**
 * Manages the retrieval of subscriptions
 *
 * @author Matt Nelson
 */
public interface SdmxSubscriptionRetrievalManager {


    /**
     * Returns a count of all the subscriptions in the system
     *
     * @return subscription count
     */
    int getSubscriptionCount();

    /**
     * Returns a set of all the subscriptions which are owned by the a given organisation.
     * <p>
     * Returns an empty set if no subscriptions are found
     *
     * @param organisation the organisation
     * @return subscriptions
     */
    Set<SubscriptionBean> getSubscriptions(OrganisationBean organisation);

    /**
     * Returns a set of all the subscriptions for a given organisation (Data Consumer, Data Provider or Agency)
     *
     * @param organisationReference this will be validated that the reference is to a data consumer, and it a full reference (contains all reference parameters)
     * @return subscriptions
     * @throws CrossReferenceException if the StructureReferenceBean does not reference a valid Organisation
     */
    Set<SubscriptionBean> getSubscriptions(StructureReferenceBean organisationReference);

    /**
     * Returns a set of subscriptions of the given type for the trigger identifiable
     *
     * @param triggerEvent the identifiable that triggers this event
     * @param type         This defines the type of subscriptions to return
     * @return subscriptions for event
     */
    Set<SubscriptionBean> getSubscriptionsForEvent(IdentifiableBean triggerEvent, SubscriptionBean.SUBSCRIPTION_TYPE type);

}
