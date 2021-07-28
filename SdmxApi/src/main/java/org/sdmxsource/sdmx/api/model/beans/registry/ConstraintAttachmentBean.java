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
package org.sdmxsource.sdmx.api.model.beans.registry;

import org.sdmxsource.sdmx.api.model.beans.base.DataSourceBean;
import org.sdmxsource.sdmx.api.model.beans.base.SdmxStructureBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.DataAndMetadataSetReference;
import org.sdmxsource.sdmx.api.model.mutable.registry.ConstraintAttachmentMutableBean;

import java.util.List;
import java.util.Set;


/**
 * The interface Constraint attachment bean.
 */
public interface ConstraintAttachmentBean extends SdmxStructureBean {

    /**
     * If this constraint is built from a dataset/metadataset, this will returns the data provider and dataset id
     *
     * @return data or metadata set reference
     */
    DataAndMetadataSetReference getDataOrMetadataSetReference();

    /**
     * Returns the structures that this constraint is attached to, this can be one or more of the following:
     * <ul>
     *   <li>Data Provider</li>
     *   <li>Data Structure</li>
     *   <li>Metadata Structure</li>
     *   <li>Data Flow</li>
     *   <li>Metadata Flow</li>
     *   <li>Provision Agreement</li>
     *   <li>Registration Bean</li>
     * </ul>
     * <p><b>NOTE: </b> This list of cross references can not be a mixed bag, it can be one or more OF THE SAME TYPE.
     *
     * @return structure reference
     */
    Set<CrossReferenceBean> getStructureReference();

    /**
     * Returns the datasource(s) that this constraint is built from
     *
     * @return not null
     */
    List<DataSourceBean> getDataSources();

    /**
     * Create mutable instance constraint attachment mutable bean.
     *
     * @return the constraint attachment mutable bean
     */
    ConstraintAttachmentMutableBean createMutableInstance();
}
