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
package org.sdmxsource.sdmx.sdmxbeans.model.superbeans.metadata;

import org.sdmxsource.sdmx.api.manager.retrieval.IdentifiableRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ContentConstraintBean;
import org.sdmxsource.sdmx.api.model.metadata.DataKeyBean;
import org.sdmxsource.sdmx.api.model.metadata.ReferenceValueBean;
import org.sdmxsource.sdmx.api.model.superbeans.metadata.ReferenceValueSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.base.SuperBeanImpl;

import java.util.List;
import java.util.Set;


/**
 * ReferenceValue contains a value for a target reference object reference.
 * <p>
 * When this is taken with its sibling elements, they identify the object or objects to which the reported metadata apply.
 * <p>
 * The content of this will either be a reference to an identifiable object, a data key, a reference to a data set, or a report period.
 *
 * @author Matt Nelson
 */
public class ReferenceValueSuperBeanImpl extends SuperBeanImpl implements ReferenceValueSuperBean {
    private static final long serialVersionUID = 3931084247512860391L;
    private IdentifiableBean identifiableReference;
    private ContentConstraintBean contentConstraintReference;
    private ReferenceValueBean builtFrom;

    /**
     * Instantiates a new Reference value super bean.
     *
     * @param builtFrom        the built from
     * @param retrievalManager the retrieval manager
     */
    public ReferenceValueSuperBeanImpl(ReferenceValueBean builtFrom, IdentifiableRetrievalManager retrievalManager) {
        super(builtFrom);
        this.builtFrom = builtFrom;
        if (builtFrom.getIdentifiableReference() != null) {
            identifiableReference = retrievalManager.getIdentifiableBean(builtFrom.getIdentifiableReference());
        }
        if (builtFrom.getContentConstraintReference() != null) {
            contentConstraintReference = retrievalManager.getIdentifiableBean(builtFrom.getContentConstraintReference(), ContentConstraintBean.class);
        }
    }

    @Override
    public String getId() {
        return builtFrom.getId();
    }

    @Override
    public String getDatasetId() {
        return builtFrom.getDatasetId();
    }

    @Override
    public List<DataKeyBean> getDataKeys() {
        return builtFrom.getDataKeys();
    }

    @Override
    public boolean isDatasetReference() {
        return builtFrom.isDatasetReference();
    }

    @Override
    public boolean isIdentifiableReference() {
        return builtFrom.isIdentifiableReference();
    }

    @Override
    public boolean isDatakeyReference() {
        return builtFrom.isDatakeyReference();
    }

    @Override
    public boolean isContentConstriantReference() {
        return builtFrom.isContentConstriantReference();
    }

    @Override
    public IdentifiableBean getIdentifiableReference() {
        return identifiableReference;
    }

    @Override
    public ContentConstraintBean getContentConstraintReference() {
        return contentConstraintReference;
    }

    @Override
    public ReferenceValueBean getBuiltFrom() {
        return builtFrom;
    }

    @Override
    public Set<MaintainableBean> getCompositeBeans() {
        Set<MaintainableBean> returnSet = super.getCompositeBeans();
        if (identifiableReference != null) {
            returnSet.add(identifiableReference.getMaintainableParent());
        }
        if (contentConstraintReference != null) {
            returnSet.add(contentConstraintReference);
        }
        return returnSet;
    }
}
