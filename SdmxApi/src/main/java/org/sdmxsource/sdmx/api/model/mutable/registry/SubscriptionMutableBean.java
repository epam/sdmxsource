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
package org.sdmxsource.sdmx.api.model.mutable.registry;

import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.SubscriptionBean;
import org.sdmxsource.sdmx.api.model.beans.registry.SubscriptionBean.SUBSCRIPTION_TYPE;
import org.sdmxsource.sdmx.api.model.mutable.base.MaintainableMutableBean;

import java.util.List;


/**
 * The interface Subscription mutable bean.
 */
public interface SubscriptionMutableBean extends MaintainableMutableBean {

    /**
     * Gets owner.
     *
     * @return the owner
     */
    StructureReferenceBean getOwner();

    /**
     * Sets owner.
     *
     * @param owner the owner
     */
    void setOwner(StructureReferenceBean owner);

    /**
     * Gets mail to.
     *
     * @return the mail to
     */
    List<String> getMailTo();

    /**
     * Sets mail to.
     *
     * @param mailTo the mail to
     */
    void setMailTo(List<String> mailTo);

    /**
     * Gets http post to.
     *
     * @return the http post to
     */
    List<String> getHTTPPostTo();

    /**
     * Sets http post to.
     *
     * @param hTTPPostTo the h ttp post to
     */
    void setHTTPPostTo(List<String> hTTPPostTo);

    /**
     * Gets references.
     *
     * @return the references
     */
    List<StructureReferenceBean> getReferences();

    /**
     * Sets references.
     *
     * @param references the references
     */
    void setReferences(List<StructureReferenceBean> references);

    /**
     * Add reference.
     *
     * @param reference the reference
     */
    void addReference(StructureReferenceBean reference);

    /**
     * Gets subscription type.
     *
     * @return the subscription type
     */
    SUBSCRIPTION_TYPE getSubscriptionType();

    /**
     * Sets subscription type.
     *
     * @param subscriptionType the subscription type
     */
    void setSubscriptionType(SUBSCRIPTION_TYPE subscriptionType);


    /**
     * Returns a representation of itself in a bean which can not be modified, modifications to the mutable bean
     * are not reflected in the returned instance of the MaintainableBean.
     *
     * @return the subscription
     */
    @Override
    SubscriptionBean getImmutableInstance();

}
