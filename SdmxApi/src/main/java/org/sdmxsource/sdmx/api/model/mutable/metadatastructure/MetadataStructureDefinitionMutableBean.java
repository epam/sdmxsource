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
package org.sdmxsource.sdmx.api.model.mutable.metadatastructure;

import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataStructureDefinitionBean;
import org.sdmxsource.sdmx.api.model.mutable.base.MaintainableMutableBean;

import java.util.List;


/**
 * The interface Metadata structure definition mutable bean.
 */
public interface MetadataStructureDefinitionMutableBean extends MaintainableMutableBean {

    /**
     * Gets metadata targets.
     *
     * @return the metadata targets
     */
    List<MetadataTargetMutableBean> getMetadataTargets();

    /**
     * Sets metadata targets.
     *
     * @param list the list
     */
    void setMetadataTargets(List<MetadataTargetMutableBean> list);

    /**
     * Gets report structures.
     *
     * @return the report structures
     */
    List<ReportStructureMutableBean> getReportStructures();

    /**
     * Sets report structures.
     *
     * @param list the list
     */
    void setReportStructures(List<ReportStructureMutableBean> list);

    /**
     * Returns a representation of itself in a bean which can not be modified, modifications to the mutable bean
     * are not reflected in the returned instance of the MaintainableBean.
     *
     * @return the immutable instance
     */
    @Override
    MetadataStructureDefinitionBean getImmutableInstance();

}
