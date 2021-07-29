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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.metadatastructure;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TERTIARY_BOOL;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataAttributeBean;
import org.sdmxsource.sdmx.api.model.mutable.metadatastructure.MetadataAttributeMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.ComponentMutableBeanImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Metadata attribute mutable bean.
 */
public class MetadataAttributeMutableBeanImpl extends ComponentMutableBeanImpl implements MetadataAttributeMutableBean {
    private static final long serialVersionUID = 1L;

    private List<MetadataAttributeMutableBean> metadataAttributes = new ArrayList<MetadataAttributeMutableBean>();
    private Integer minOccurs;
    private Integer maxOccurs;
    private TERTIARY_BOOL presentational = TERTIARY_BOOL.UNSET;

    /**
     * Instantiates a new Metadata attribute mutable bean.
     *
     * @param bean the bean
     */
    public MetadataAttributeMutableBeanImpl(MetadataAttributeBean bean) {
        super(bean);
        if (bean.getMetadataAttributes() != null) {
            for (MetadataAttributeBean currentMetadatAttribute : bean.getMetadataAttributes()) {
                this.metadataAttributes.add(new MetadataAttributeMutableBeanImpl(currentMetadatAttribute));
            }
        }
        this.minOccurs = bean.getMinOccurs();
        this.maxOccurs = bean.getMaxOccurs();
        this.presentational = bean.getPresentational();
    }

    /**
     * Instantiates a new Metadata attribute mutable bean.
     */
    public MetadataAttributeMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.METADATA_ATTRIBUTE);
    }

    @Override
    public Integer getMinOccurs() {
        return minOccurs;
    }

    @Override
    public void setMinOccurs(Integer minOccurs) {
        this.minOccurs = minOccurs;
    }

    @Override
    public Integer getMaxOccurs() {
        return this.maxOccurs;
    }

    @Override
    public void setMaxOccurs(Integer maxOccurs) {
        this.maxOccurs = maxOccurs;
    }

    @Override
    public TERTIARY_BOOL getPresentational() {
        return this.presentational;
    }

    @Override
    public void setPresentational(TERTIARY_BOOL bool) {
        this.presentational = bool;
    }

    @Override
    public List<MetadataAttributeMutableBean> getMetadataAttributes() {
        return metadataAttributes;
    }

    @Override
    public void setMetadataAttributes(List<MetadataAttributeMutableBean> list) {
        this.metadataAttributes = list;
    }

}
