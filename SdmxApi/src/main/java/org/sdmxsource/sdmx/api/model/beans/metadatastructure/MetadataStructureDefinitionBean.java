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
package org.sdmxsource.sdmx.api.model.beans.metadatastructure;

import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.mutable.metadatastructure.MetadataStructureDefinitionMutableBean;

import java.net.URL;
import java.util.List;


/**
 * Represents a SDMX Metadata Structure Definition (MSD)
 */
public interface MetadataStructureDefinitionBean extends MaintainableBean {


    /**
     * Returns all the available targets as defined by this MSD
     *
     * @return metadata targets
     */
    List<MetadataTargetBean> getMetadataTargets();

    /**
     * Returns the metadata target with the given id, or null if no such target exists
     *
     * @param id the id
     * @return metadata target
     */
    MetadataTargetBean getMetadataTarget(String id);

    /**
     * Returns all the report structures defined by the MSD
     *
     * @return report structures
     */
    List<ReportStructureBean> getReportStructures();

    /**
     * Returns the report structure with the given id, or null if no such report structure exists
     *
     * @param id the id
     * @return report structure
     */
    ReportStructureBean getReportStructure(String id);

    /**
     * Returns a stub reference of itself.
     * <p>
     * A stub bean only contains Maintainable and Identifiable Attributes, not the composite Objects that are
     * contained within the Maintainable
     *
     * @param actualLocation the URL indicating where the full structure can be returned from
     * @param isServiceUrl   if true this URL will be present on the serviceURL attribute, otherwise it will be treated as a structureURL attribute
     * @return the stub
     */
    @Override
    MetadataStructureDefinitionBean getStub(URL actualLocation, boolean isServiceUrl);

    /**
     * Returns a representation of itself in a bean which can be modified, modifications to the mutable bean
     * are not reflected in the instance of the MaintainableBean.
     *
     * @return the mutable instance
     */
    @Override
    MetadataStructureDefinitionMutableBean getMutableInstance();
}
