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

import org.sdmxsource.sdmx.api.constants.DATASET_ACTION;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;

import java.util.Date;


/**
 * Defines a notification event sent out from a registry.  The notification contains reference to the object that
 * caused the notification to occur.  In the case of an identifiable the object urn is provided, from which the object
 * can be queries.  In the case of a Registration, which has no URN the registration itself is sent inside the NotificationEvent.
 *
 * @author Matt Nelson
 */
public interface NotificationEvent {

    /**
     * Returns the time that the event was created
     *
     * @return event time
     */
    Date getEventTime();

    /**
     * Returns the urn of the object that caused the notification event to be created (no applicable for registrations)
     *
     * @return object urn
     */
    String getObjectUrn();

    /**
     * Returns the urn of the subscription that this notification event was triggered from
     *
     * @return subscription urn
     */
    String getSubscriptionUrn();

    /**
     * Returns the action on the object that this event was triggered from
     *
     * @return action
     */
    DATASET_ACTION getAction();

    /**
     * Returns the Registaration that triggered thiws event.  This can be null if it was not a registartion that triggered the
     * event.
     *
     * @return registration
     */
    RegistrationBean getRegistration();

    /**
     * Returns the structures contained in this subscription
     *
     * @return beans
     */
    SdmxBeans getBeans();
}
