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
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataAttributeBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.ReportStructureBean;
import org.sdmxsource.sdmx.api.model.mutable.metadatastructure.MetadataAttributeMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.metadatastructure.ReportStructureMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.IdentifiableMutableBeanImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Report structure mutable bean.
 */
public class ReportStructureMutableBeanImpl extends IdentifiableMutableBeanImpl implements ReportStructureMutableBean {
    private static final long serialVersionUID = 1L;

    private List<MetadataAttributeMutableBean> metadataAttributes = new ArrayList<MetadataAttributeMutableBean>();
    private List<String> targetMetadatas;

    /**
     * Instantiates a new Report structure mutable bean.
     *
     * @param bean the bean
     */
    public ReportStructureMutableBeanImpl(ReportStructureBean bean) {
        super(bean);
        if (bean.getMetadataAttributes() != null) {
            for (MetadataAttributeBean currentBean : bean.getMetadataAttributes()) {
                this.metadataAttributes.add(new MetadataAttributeMutableBeanImpl(currentBean));
            }
        }
        this.targetMetadatas = bean.getTargetMetadatas();
    }

    /**
     * Instantiates a new Report structure mutable bean.
     */
    public ReportStructureMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.REPORT_STRUCTURE);
    }


    @Override
    public List<MetadataAttributeMutableBean> getMetadataAttributes() {
        return metadataAttributes;
    }

    @Override
    public void setMetadataAttributes(List<MetadataAttributeMutableBean> metadataAttributes) {
        this.metadataAttributes = metadataAttributes;
    }

    @Override
    public List<String> getTargetMetadatas() {
        return targetMetadatas;
    }

    @Override
    public void setTargetMetadatas(List<String> targetMetadata) {
        this.targetMetadatas = targetMetadata;
    }
}
