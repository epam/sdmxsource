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
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataStructureDefinitionBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataTargetBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.ReportStructureBean;
import org.sdmxsource.sdmx.api.model.mutable.metadatastructure.MetadataStructureDefinitionMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.metadatastructure.MetadataTargetMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.metadatastructure.ReportStructureMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.metadatastructure.MetadataStructureDefinitionBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.MaintainableMutableBeanImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Metadata structure definition mutable bean.
 */
public class MetadataStructureDefinitionMutableBeanImpl extends MaintainableMutableBeanImpl implements MetadataStructureDefinitionMutableBean {
    private static final long serialVersionUID = 720851779528287427L;

    private List<MetadataTargetMutableBean> metadataTargets = new ArrayList<MetadataTargetMutableBean>();
    private List<ReportStructureMutableBean> reportStructures = new ArrayList<ReportStructureMutableBean>();

    /**
     * Instantiates a new Metadata structure definition mutable bean.
     *
     * @param bean the bean
     */
    public MetadataStructureDefinitionMutableBeanImpl(MetadataStructureDefinitionBean bean) {
        super(bean);
        if (bean.getMetadataTargets() != null) {
            for (MetadataTargetBean currentMt : bean.getMetadataTargets()) {
                this.metadataTargets.add(new MetadataTargetMutableBeanImpl(currentMt));
            }
        }
        if (bean.getReportStructures() != null) {
            for (ReportStructureBean currentReportStructure : bean.getReportStructures()) {
                this.reportStructures.add(new ReportStructureMutableBeanImpl(currentReportStructure));
            }
        }
    }

    /**
     * Instantiates a new Metadata structure definition mutable bean.
     */
    public MetadataStructureDefinitionMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.MSD);
    }

    @Override
    public List<MetadataTargetMutableBean> getMetadataTargets() {
        return metadataTargets;
    }

    @Override
    public void setMetadataTargets(List<MetadataTargetMutableBean> list) {
        this.metadataTargets = list;
    }

    @Override
    public List<ReportStructureMutableBean> getReportStructures() {
        return reportStructures;
    }

    @Override
    public void setReportStructures(List<ReportStructureMutableBean> list) {
        this.reportStructures = list;
    }

    @Override
    public MetadataStructureDefinitionBean getImmutableInstance() {
        return new MetadataStructureDefinitionBeanImpl(this);
    }
}
