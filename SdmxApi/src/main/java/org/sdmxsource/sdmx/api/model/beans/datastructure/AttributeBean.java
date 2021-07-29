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
package org.sdmxsource.sdmx.api.model.beans.datastructure;

import org.sdmxsource.sdmx.api.constants.ATTRIBUTE_ATTACHMENT_LEVEL;
import org.sdmxsource.sdmx.api.model.beans.base.ComponentBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;

import java.util.List;


/**
 * Attribute describes the definition of a data attribute, which is defined as a characteristic of an object or entity.
 * <p>
 * This is an immutable bean - this bean can not be modified
 *
 * @author Matt Nelson
 */
public interface AttributeBean extends ComponentBean {

    /**
     * Returns true IF the Id = TIME_FORMAT
     *
     * @return boolean
     */
    boolean isTimeFormat();

    /**
     * Returns the list of dimension ids that this attribute references, this is only relevant if ATTRIBUTE_ATTACHMENT_LEVEL is
     * DIMENSION_GROUP.  The returned list is a copy of the underlying list
     * <p>
     * Returns an empty list if this attribute does not reference any dimensions
     *
     * @return dimension references
     */
    List<String> getDimensionReferences();

    /**
     * Returns a list of cross references to concepts that are used to define the role(s) of this attribute.
     * The returned list is a copy of the underlying list
     * <p>
     * Returns an empty list if this attribute does not reference any concept roles
     *
     * @return concept roles
     */
    List<CrossReferenceBean> getConceptRoles();

    /**
     * If ATTRIBUTE_ATTACHMENT_LEVEL is GROUP then returns a reference to the Group id that this attribute is attached to.  Returns
     * null if ATTRIBUTE_ATTACHMENT_LEVEL is not GROUP
     *
     * @return attachment group
     */
    String getAttachmentGroup();

    /**
     * If ATTRIBUTE_ATTACHMENT_LEVEL is OBSERVATION then returns a reference to the Primary Measure id that this attribute is attached to.  Returns
     * null if ATTRIBUTE_ATTACHMENT_LEVEL is not GROUP
     *
     * @return primary measure reference
     */
    String getPrimaryMeasureReference();

    /**
     * Returns the ATTRIBUTE_ATTACHMENT_LEVEL attribute indicating the level to which the attribute is attached in time-series formats
     * (generic, compact, utility data formats).
     * Attributes with an attachment level of Group are only available if the data is organised in groups,
     * and should be used appropriately, as the values may not be communicated if the data is not grouped.
     *
     * @return attachment level
     */
    ATTRIBUTE_ATTACHMENT_LEVEL getAttachmentLevel();


    /**
     * The assignmentStatus attribute indicates whether a
     * value must be provided for the attribute when sending documentation along with the data.
     *
     * @return assignment status
     */
    String getAssignmentStatus();


    /**
     * Returns true if getAssignmentStatus()==MANDATORY
     *
     * @return mandatory
     */
    boolean isMandatory();

}
