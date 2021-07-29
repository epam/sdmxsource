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
package org.sdmxsource.sdmx.api.model.mutable.reference;

import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;

/**
 * The interface Data and metadata set mutable reference.
 */
public interface DataAndMetadataSetMutableReference {

    /**
     * Returns the dataset referenced by data flow/provider/provision
     *
     * @return data set reference
     */
    StructureReferenceBean getDataSetReference();

    /**
     * Returns the id of the data or metadata set
     *
     * @return set id
     */
    String getSetId();

    /**
     * Sets set id.
     *
     * @param id the id
     */
    void setSetId(String id);

    /**
     * Returns true if this is a dataset reference, false if it is a metadata set reference
     *
     * @return true if this is a dataset reference, false if it is a metadata set reference
     */
    boolean isDataSetReference();

    /**
     * Sets data set reference.
     *
     * @param bean the bean
     */
    void setDataSetReference(StructureReferenceBean bean);

    /**
     * Sets is data set reference.
     *
     * @param isRef the is ref
     */
    void setIsDataSetReference(boolean isRef);
}
