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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.registry;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.SubscriptionBean;
import org.sdmxsource.sdmx.api.model.beans.registry.SubscriptionBean.SUBSCRIPTION_TYPE;
import org.sdmxsource.sdmx.api.model.mutable.registry.SubscriptionMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.registry.SubscriptionBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.MaintainableMutableBeanImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Subscription mutable bean.
 */
public class SubscriptionMutableBeanImpl extends MaintainableMutableBeanImpl implements SubscriptionMutableBean {
    private static final long serialVersionUID = 1L;

    private StructureReferenceBean owner;
    private List<String> mailTo = new ArrayList<String>();
    private List<String> HTTPPostTo = new ArrayList<String>();
    private List<StructureReferenceBean> references = new ArrayList<StructureReferenceBean>();
    private SUBSCRIPTION_TYPE subscriptionType;

    /**
     * Instantiates a new Subscription mutable bean.
     */
    public SubscriptionMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.SUBSCRIPTION);
    }

    /**
     * Instantiates a new Subscription mutable bean.
     *
     * @param subscriptionBean the subscription bean
     */
    public SubscriptionMutableBeanImpl(SubscriptionBean subscriptionBean) {
        super(subscriptionBean);
        if (subscriptionBean.getHTTPPostTo() != null) {
            this.HTTPPostTo = new ArrayList<String>(subscriptionBean.getHTTPPostTo());
        }
        if (subscriptionBean.getMailTo() != null) {
            this.mailTo = new ArrayList<String>(subscriptionBean.getMailTo());
        }
        if (subscriptionBean.getReferences() != null) {
            for (StructureReferenceBean sRef : subscriptionBean.getReferences()) {
                this.references.add(sRef.createCopy());
            }
        }
        this.structureType = subscriptionBean.getStructureType();
        this.owner = subscriptionBean.getOwner().createMutableInstance();
        this.subscriptionType = subscriptionBean.getSubscriptionType();
    }

    @Override
    public StructureReferenceBean getOwner() {
        return owner;
    }

    @Override
    public void setOwner(StructureReferenceBean owner) {
        this.owner = owner;
    }

    @Override
    public List<String> getMailTo() {
        return mailTo;
    }

    @Override
    public void setMailTo(List<String> mailTo) {
        this.mailTo = mailTo;
    }

    @Override
    public List<String> getHTTPPostTo() {
        return HTTPPostTo;
    }

    @Override
    public void setHTTPPostTo(List<String> hTTPPostTo) {
        HTTPPostTo = hTTPPostTo;
    }

    @Override
    public void addReference(StructureReferenceBean reference) {
        if (this.references == null) {
            this.references = new ArrayList<StructureReferenceBean>();
        }
        this.references.add(reference);
    }

    @Override
    public List<StructureReferenceBean> getReferences() {
        return references;
    }

    @Override
    public void setReferences(List<StructureReferenceBean> references) {
        this.references = references;
    }

    @Override
    public SUBSCRIPTION_TYPE getSubscriptionType() {
        return subscriptionType;
    }

    @Override
    public void setSubscriptionType(SUBSCRIPTION_TYPE subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    @Override
    public SubscriptionBean getImmutableInstance() {
        return new SubscriptionBeanImpl(this);
    }
}
