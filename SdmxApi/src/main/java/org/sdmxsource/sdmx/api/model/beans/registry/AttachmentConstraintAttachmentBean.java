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

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.DataSourceBean;
import org.sdmxsource.sdmx.api.model.beans.base.SdmxStructureBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.DataAndMetadataSetReference;

import java.util.List;


/**
 * The interface Attachment constraint attachment bean.
 */
public interface AttachmentConstraintAttachmentBean extends SdmxStructureBean {

    /**
     * Returns the target structure for this constraint attachment
     *
     * @return target structure type
     */
    SDMX_STRUCTURE_TYPE getTargetStructureType();

    /**
     * Returns the structure references that this contstraint is referencing
     *
     * @return structure references
     */
    List<CrossReferenceBean> getStructureReferences();

    /**
     * If the target structure is a dataset or metadata set, then this will return the id of that set
     *
     * @return data or metadata set reference
     */
    List<DataAndMetadataSetReference> getDataOrMetadataSetReference();


    /**
     * Gets datasources.
     *
     * @return the datasources
     */
    List<DataSourceBean> getDatasources();
}
