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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.datastructure;

import org.sdmxsource.sdmx.api.constants.ATTRIBUTE_ATTACHMENT_LEVEL;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.datastructure.AttributeBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.AttributeMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.ComponentMutableBeanImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Attribute mutable bean.
 */
public class AttributeMutableBeanImpl extends ComponentMutableBeanImpl implements AttributeMutableBean {
    private static final long serialVersionUID = -4692032103270271465L;
    private ATTRIBUTE_ATTACHMENT_LEVEL attachmentLevel;
    private String assignmentStatus;

    private String attachmentGroup;
    private List<String> dimensionReference = new ArrayList<String>();
    private String primaryMeasureReference;
    private List<StructureReferenceBean> conceptRoles = new ArrayList<StructureReferenceBean>();

    /**
     * Instantiates a new Attribute mutable bean.
     */
    public AttributeMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.DATA_ATTRIBUTE);
    }

    /**
     * Instantiates a new Attribute mutable bean.
     *
     * @param bean the bean
     */
    public AttributeMutableBeanImpl(AttributeBean bean) {
        super(bean);
        this.attachmentLevel = bean.getAttachmentLevel();
        this.assignmentStatus = bean.getAssignmentStatus();
        this.attachmentGroup = bean.getAttachmentGroup();
        this.dimensionReference = bean.getDimensionReferences();
        this.primaryMeasureReference = bean.getPrimaryMeasureReference();
        if (bean.getConceptRoles() != null) {
            for (CrossReferenceBean currentConceptRole : bean.getConceptRoles()) {
                this.conceptRoles.add(currentConceptRole.createMutableInstance());
            }
        }
    }

    @Override
    public void addConceptRole(StructureReferenceBean sRef) {
        if (conceptRoles == null) {
            conceptRoles = new ArrayList<StructureReferenceBean>();
        }
        conceptRoles.add(sRef);
    }

    @Override
    public List<StructureReferenceBean> getConceptRoles() {
        return conceptRoles;
    }

    @Override
    public void setConceptRoles(List<StructureReferenceBean> conceptRoles) {
        this.conceptRoles = conceptRoles;
    }

    @Override
    public ATTRIBUTE_ATTACHMENT_LEVEL getAttachmentLevel() {
        return attachmentLevel;
    }

    @Override
    public void setAttachmentLevel(ATTRIBUTE_ATTACHMENT_LEVEL attachmentLevel) {
        this.attachmentLevel = attachmentLevel;
    }

    @Override
    public String getAssignmentStatus() {
        return assignmentStatus;
    }

    @Override
    public void setAssignmentStatus(String assignmentStatus) {
        this.assignmentStatus = assignmentStatus;
    }

    @Override
    public String getAttachmentGroup() {
        return attachmentGroup;
    }

    @Override
    public void setAttachmentGroup(String attachmentGroup) {
        this.attachmentGroup = attachmentGroup;
    }

    @Override
    public List<String> getDimensionReferences() {
        return dimensionReference;
    }

    @Override
    public void setDimensionReferences(List<String> dimensionReference) {
        this.dimensionReference = dimensionReference;
    }

    @Override
    public String getPrimaryMeasureReference() {
        return primaryMeasureReference;
    }

    @Override
    public void setPrimaryMeasureReference(String primaryMeasureReference) {
        this.primaryMeasureReference = primaryMeasureReference;
    }
}
