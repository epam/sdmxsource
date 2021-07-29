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
import org.sdmxsource.sdmx.api.model.beans.registry.KeyValues;
import org.sdmxsource.sdmx.api.model.beans.registry.MetadataTargetKeyValuesBean;
import org.sdmxsource.sdmx.api.model.beans.registry.MetadataTargetRegionBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.KeyValuesMutable;
import org.sdmxsource.sdmx.api.model.mutable.registry.MetadataTargetKeyValuesMutable;
import org.sdmxsource.sdmx.api.model.mutable.registry.MetadataTargetRegionMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.MutableBeanImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Metadata target region mutable bean.
 */
public class MetadataTargetRegionMutableBeanImpl extends MutableBeanImpl implements MetadataTargetRegionMutableBean {
    private static final long serialVersionUID = 7159174068190000136L;
    private boolean include;
    private String report;
    private String metadataTarget;
    private List<KeyValuesMutable> attributes = new ArrayList<KeyValuesMutable>();
    private List<MetadataTargetKeyValuesMutable> key = new ArrayList<MetadataTargetKeyValuesMutable>();

    /**
     * Instantiates a new Metadata target region mutable bean.
     */
    public MetadataTargetRegionMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.METADATA_TARGET_REGION);
    }


    /**
     * Instantiates a new Metadata target region mutable bean.
     *
     * @param createdFrom the created from
     */
    public MetadataTargetRegionMutableBeanImpl(MetadataTargetRegionBean createdFrom) {
        super(createdFrom);
        this.include = createdFrom.isInclude();
        this.report = createdFrom.getReport();
        this.metadataTarget = createdFrom.getMetadataTarget();
        for (MetadataTargetKeyValuesBean currentKv : createdFrom.getKey()) {
            key.add(new MetadataTargetKeyValuesMutableImpl(currentKv));
        }
        for (KeyValues currentKv : createdFrom.getAttributes()) {
            attributes.add(new KeyValuesMutableImpl(currentKv));
        }
    }


    @Override
    public boolean isInclude() {
        return include;
    }

    @Override
    public String getReport() {
        return report;
    }

    @Override
    public String getMetadataTarget() {
        return metadataTarget;
    }

    @Override
    public List<MetadataTargetKeyValuesMutable> getKey() {
        return this.key;
    }

    @Override
    public void setKey(List<MetadataTargetKeyValuesMutable> key) {
        this.key = key;
    }

    @Override
    public void addKey(MetadataTargetKeyValuesMutable key) {
        if (this.key == null) {
            this.key = new ArrayList<MetadataTargetKeyValuesMutable>();
        }
        this.key.add(key);
    }

    @Override
    public List<KeyValuesMutable> getAttributes() {
        return attributes;
    }

    @Override
    public void setAttributes(List<KeyValuesMutable> attributes) {
        this.attributes = attributes;
    }

    @Override
    public void addAttribute(KeyValuesMutable attribute) {
        if (this.attributes == null) {
            this.attributes = new ArrayList<KeyValuesMutable>();
        }
        this.attributes.add(attribute);
    }

}
