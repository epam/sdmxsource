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
package org.sdmxsource.sdmx.api.model.mutable.datastructure;

import org.sdmxsource.sdmx.api.constants.ATTRIBUTE_ATTACHMENT_LEVEL;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.base.ComponentMutableBean;

import java.util.List;


/**
 * The interface Attribute mutable bean.
 */
public interface AttributeMutableBean extends ComponentMutableBean {

    /**
     * Gets concept roles.
     *
     * @return the concept roles
     */
    List<StructureReferenceBean> getConceptRoles();

    /**
     * Sets concept roles.
     *
     * @param conceptRoles the concept roles
     */
    void setConceptRoles(List<StructureReferenceBean> conceptRoles);

    /**
     * Adds a concept Role to the existing list of concept roles
     *
     * @param sRef the s ref
     */
    void addConceptRole(StructureReferenceBean sRef);

    /**
     * Gets dimension references.
     *
     * @return the dimension references
     */
    List<String> getDimensionReferences();

    /**
     * Sets dimension references.
     *
     * @param dimensionRef the dimension ref
     */
    void setDimensionReferences(List<String> dimensionRef);

    /**
     * Gets attachment group.
     *
     * @return the attachment group
     */
    String getAttachmentGroup();

    /**
     * Sets attachment group.
     *
     * @param attachmentGroup the attachment group
     */
    void setAttachmentGroup(String attachmentGroup);

    /**
     * Gets primary measure reference.
     *
     * @return the primary measure reference
     */
    String getPrimaryMeasureReference();

    /**
     * Sets primary measure reference.
     *
     * @param primaryMeasure the primary measure
     */
    void setPrimaryMeasureReference(String primaryMeasure);

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
     * Sets attachment level.
     *
     * @param attachmentLevel the attachment level
     */
    void setAttachmentLevel(ATTRIBUTE_ATTACHMENT_LEVEL attachmentLevel);


    /**
     * The assignmentStatus attribute indicates whether a
     * value must be provided for the attribute when sending documentation along with the data.
     *
     * @return assignment status
     */
    String getAssignmentStatus();

    /**
     * Sets assignment status.
     *
     * @param assignmentStatus the assignment status
     */
    void setAssignmentStatus(String assignmentStatus);

}
