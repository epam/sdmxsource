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
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataFlowBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.metadatastructure.MetadataFlowMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.metadatastructure.MetadataflowBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.MaintainableMutableBeanImpl;


/**
 * The type Metadataflow mutable bean.
 */
public class MetadataflowMutableBeanImpl extends MaintainableMutableBeanImpl implements MetadataFlowMutableBean {
    private static final long serialVersionUID = 1L;

    private StructureReferenceBean metadataStructureRef;

    /**
     * Instantiates a new Metadataflow mutable bean.
     */
    public MetadataflowMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.METADATA_FLOW);
    }

    /**
     * Instantiates a new Metadataflow mutable bean.
     *
     * @param bean the bean
     */
    public MetadataflowMutableBeanImpl(MetadataFlowBean bean) {
        super(bean);
        if (bean.getMetadataStructureRef() != null) {
            this.metadataStructureRef = bean.getMetadataStructureRef().createMutableInstance();
        }
    }

    @Override
    public MetadataFlowBean getImmutableInstance() {
        return new MetadataflowBeanImpl(this);
    }

    @Override
    public StructureReferenceBean getMetadataStructureRef() {
        return metadataStructureRef;
    }

    @Override
    public void setMetadataStructureRef(StructureReferenceBean metadataStructureRef) {
        this.metadataStructureRef = metadataStructureRef;
    }
}
