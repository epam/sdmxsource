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
import org.sdmxsource.sdmx.api.model.metadata.MetadataBean;
import org.sdmxsource.sdmx.api.model.metadata.MetadataSetBean;
import org.sdmxsource.sdmx.api.model.superbeans.metadata.MetadataSetSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.metadata.MetadataSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.base.SuperBeanImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * The type Metadata super bean.
 */
public class MetadataSuperBeanImpl extends SuperBeanImpl implements MetadataSuperBean {
    private static final long serialVersionUID = 6910821802418090357L;
    private List<MetadataSetSuperBean> metadataSet = new ArrayList<MetadataSetSuperBean>();
    private MetadataBean builtFrom;


    /**
     * Instantiates a new Metadata super bean.
     *
     * @param builtFrom        the built from
     * @param retrievalManager the retrieval manager
     */
    public MetadataSuperBeanImpl(MetadataBean builtFrom, IdentifiableRetrievalManager retrievalManager) {
        super(builtFrom);
        this.builtFrom = builtFrom;
        for (MetadataSetBean currentMS : builtFrom.getMetadataSet()) {
            metadataSet.add(new MetadataSetSuperBeanImpl(currentMS, retrievalManager));
        }
    }

    @Override
    public List<MetadataSetSuperBean> getMetadataSet() {
        return new ArrayList<MetadataSetSuperBean>(metadataSet);
    }

    @Override
    public Set<MaintainableBean> getCompositeBeans() {
        Set<MaintainableBean> returnSet = super.getCompositeBeans();
        if (metadataSet != null) {
            for (MetadataSetSuperBean mds : metadataSet) {
                returnSet.addAll(mds.getCompositeBeans());
            }
        }
        return returnSet;
    }

    @Override
    public MetadataBean getBuiltFrom() {
        return builtFrom;
    }
}
