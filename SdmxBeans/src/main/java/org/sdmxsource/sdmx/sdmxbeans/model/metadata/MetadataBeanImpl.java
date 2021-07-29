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
package org.sdmxsource.sdmx.sdmxbeans.model.metadata;

import org.sdmx.resources.sdmxml.schemas.v21.message.GenericMetadataDocument;
import org.sdmx.resources.sdmxml.schemas.v21.metadata.generic.MetadataSetType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.header.HeaderBean;
import org.sdmxsource.sdmx.api.model.metadata.MetadataBean;
import org.sdmxsource.sdmx.api.model.metadata.MetadataSetBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.SDMXBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.header.HeaderBeanImpl;

import java.util.*;


/**
 * The type Metadata bean.
 */
public class MetadataBeanImpl extends SDMXBeanImpl implements MetadataBean {
    private static final long serialVersionUID = -2901486116496609171L;
    private HeaderBean headerBean;
    private List<MetadataSetBean> metadataSets = new ArrayList<MetadataSetBean>();


    /**
     * Instantiates a new Metadata bean.
     *
     * @param metadata the metadata
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public MetadataBeanImpl(GenericMetadataDocument metadata) {
        super(SDMX_STRUCTURE_TYPE.METADATA_DOCUMENT, null);

        headerBean = new HeaderBeanImpl(metadata.getGenericMetadata().getHeader());

        for (MetadataSetType metadataset : metadata.getGenericMetadata().getMetadataSetList()) {
            metadataSets.add(new MetadataSetBeanImpl(this, metadataset));
        }
    }


    /**
     * Instantiates a new Metadata bean.
     *
     * @param metadataSets the metadata sets
     */
    public MetadataBeanImpl(Collection<MetadataSetBean> metadataSets) {
        super(SDMX_STRUCTURE_TYPE.METADATA_DOCUMENT, null);
        if (metadataSets != null) {
            this.metadataSets.addAll(metadataSets);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public HeaderBean getHeader() {
        return headerBean;
    }

    @Override
    public List<MetadataSetBean> getMetadataSet() {
        return metadataSets;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////DEEP EQUALS							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean deepEquals(SDMXBean bean, boolean includeFinalProperties) {
        if (bean == null) {
            return false;
        }
        if (bean.getStructureType() == this.getStructureType()) {
            MetadataBean that = (MetadataBean) bean;
            if (!super.equivalent(this.metadataSets, that.getMetadataSet(), includeFinalProperties)) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES		                     //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = new HashSet<SDMXBean>();
        super.addToCompositeSet(metadataSets, composites);
        return composites;
    }
}
