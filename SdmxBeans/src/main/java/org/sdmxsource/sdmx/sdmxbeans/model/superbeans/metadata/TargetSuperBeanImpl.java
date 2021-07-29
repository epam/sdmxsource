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
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.metadata.ReferenceValueBean;
import org.sdmxsource.sdmx.api.model.metadata.TargetBean;
import org.sdmxsource.sdmx.api.model.superbeans.metadata.ReferenceValueSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.metadata.TargetSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.base.SuperBeanImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * Target contains a set of target reference values which when taken together,
 * identify the object or objects to which the reported metadata apply.
 *
 * @author Matt Nelson
 */
public class TargetSuperBeanImpl extends SuperBeanImpl implements TargetSuperBean {
    private static final long serialVersionUID = 5177335379654046347L;
    private List<ReferenceValueSuperBean> referenceValues = new ArrayList<ReferenceValueSuperBean>();
    private TargetBean builtFrom;


    /**
     * Instantiates a new Target super bean.
     *
     * @param builtFrom        the built from
     * @param retrievalManager the retrieval manager
     */
    public TargetSuperBeanImpl(TargetBean builtFrom, IdentifiableRetrievalManager retrievalManager) {
        super(builtFrom);
        this.builtFrom = builtFrom;
        for (ReferenceValueBean rvBean : builtFrom.getReferenceValues()) {
            referenceValues.add(new ReferenceValueSuperBeanImpl(rvBean, retrievalManager));
        }
    }

    @Override
    public String getId() {
        return builtFrom.getId();
    }

    @Override
    public List<ReferenceValueSuperBean> getReferenceValues() {
        return new ArrayList<ReferenceValueSuperBean>(referenceValues);
    }

    @Override
    public TargetBean getBuiltFrom() {
        return builtFrom;
    }

    @Override
    public Set<MaintainableBean> getCompositeBeans() {
        Set<MaintainableBean> returnSet = super.getCompositeBeans();

        if (referenceValues != null) {
            for (ReferenceValueSuperBean referenceValue : referenceValues) {
                returnSet.addAll(referenceValue.getCompositeBeans());
            }
        }
        return returnSet;
    }
}
