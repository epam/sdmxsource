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
package org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.datastructure;

import org.sdmxsource.sdmx.api.constants.ATTRIBUTE_ATTACHMENT_LEVEL;
import org.sdmxsource.sdmx.api.model.mutablesuperbeans.datastructure.AttributeMutableSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.AttributeSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.base.ComponentMutableSuperBeanImpl;

import java.util.List;


/**
 * The type Attribute mutable super bean.
 */
public class AttributeMutableSuperBeanImpl extends ComponentMutableSuperBeanImpl implements AttributeMutableSuperBean {
    private static final long serialVersionUID = 1L;

    private String assignmentStatus;
    private ATTRIBUTE_ATTACHMENT_LEVEL attachmantLevel;
    private String attachmentGroup;
    private List<String> attachmentMeasure;
    private boolean isCountAttribute;
    private boolean isEntityAttribute;
    private boolean isFrequencyAttribute;
    private boolean isIdentityAttribute;
    private boolean isMandatory;
    private boolean isNonObservationTimeAttribute;
    private boolean isTimeFormat;

    /**
     * Instantiates a new Attribute mutable super bean.
     *
     * @param attributeBean the attribute bean
     */
    public AttributeMutableSuperBeanImpl(AttributeSuperBean attributeBean) {
        super(attributeBean);
        //TODO Implement
        this.assignmentStatus = attributeBean.getAssignmentStatus();
        this.attachmantLevel = attributeBean.getAttachmentLevel();
        this.isMandatory = attributeBean.isMandatory();
    }


    /**
     * Instantiates a new Attribute mutable super bean.
     */
    public AttributeMutableSuperBeanImpl() {
    }


    /**
     * Gets assignment status.
     *
     * @return the assignment status
     */
    public String getAssignmentStatus() {
        return assignmentStatus;
    }


    /**
     * Sets assignment status.
     *
     * @param assignmentStatus the assignment status
     */
    public void setAssignmentStatus(String assignmentStatus) {
        this.assignmentStatus = assignmentStatus;
    }


    /**
     * Gets attachmant level.
     *
     * @return the attachmant level
     */
    public ATTRIBUTE_ATTACHMENT_LEVEL getAttachmantLevel() {
        return attachmantLevel;
    }


    /**
     * Sets attachmant level.
     *
     * @param attachmantLevel the attachmant level
     */
    public void setAttachmantLevel(ATTRIBUTE_ATTACHMENT_LEVEL attachmantLevel) {
        this.attachmantLevel = attachmantLevel;
    }


    /**
     * Gets attachment group.
     *
     * @return the attachment group
     */
    public String getAttachmentGroup() {
        return attachmentGroup;
    }


    /**
     * Sets attachment group.
     *
     * @param attachmentGroup the attachment group
     */
    public void setAttachmentGroup(String attachmentGroup) {
        this.attachmentGroup = attachmentGroup;
    }


    /**
     * Gets attachment measure.
     *
     * @return the attachment measure
     */
    public List<String> getAttachmentMeasure() {
        return attachmentMeasure;
    }


    /**
     * Sets attachment measure.
     *
     * @param attachmentMeasure the attachment measure
     */
    public void setAttachmentMeasure(List<String> attachmentMeasure) {
        this.attachmentMeasure = attachmentMeasure;
    }


    /**
     * Gets count attribute.
     *
     * @return the count attribute
     */
    public boolean getCountAttribute() {
        return isCountAttribute;
    }


    /**
     * Sets count attribute.
     *
     * @param isCountAttribute the is count attribute
     */
    public void setCountAttribute(boolean isCountAttribute) {
        this.isCountAttribute = isCountAttribute;
    }


    /**
     * Gets entity attribute.
     *
     * @return the entity attribute
     */
    public boolean getEntityAttribute() {
        return isEntityAttribute;
    }


    /**
     * Sets entity attribute.
     *
     * @param isEntityAttribute the is entity attribute
     */
    public void setEntityAttribute(boolean isEntityAttribute) {
        this.isEntityAttribute = isEntityAttribute;
    }


    /**
     * Gets frequency attribute.
     *
     * @return the frequency attribute
     */
    public boolean getFrequencyAttribute() {
        return isFrequencyAttribute;
    }


    /**
     * Sets frequency attribute.
     *
     * @param isFrequencyAttribute the is frequency attribute
     */
    public void setFrequencyAttribute(boolean isFrequencyAttribute) {
        this.isFrequencyAttribute = isFrequencyAttribute;
    }


    /**
     * Gets identity attribute.
     *
     * @return the identity attribute
     */
    public boolean getIdentityAttribute() {
        return isIdentityAttribute;
    }


    /**
     * Sets identity attribute.
     *
     * @param isIdentityAttribute the is identity attribute
     */
    public void setIdentityAttribute(boolean isIdentityAttribute) {
        this.isIdentityAttribute = isIdentityAttribute;
    }


    /**
     * Gets mandatory.
     *
     * @return the mandatory
     */
    public boolean getMandatory() {
        return isMandatory;
    }


    /**
     * Sets mandatory.
     *
     * @param isMandatory the is mandatory
     */
    public void setMandatory(boolean isMandatory) {
        this.isMandatory = isMandatory;
    }


    /**
     * Gets non observation time attribute.
     *
     * @return the non observation time attribute
     */
    public boolean getNonObservationTimeAttribute() {
        return isNonObservationTimeAttribute;
    }


    /**
     * Sets non observation time attribute.
     *
     * @param isNonObservationTimeAttribute the is non observation time attribute
     */
    public void setNonObservationTimeAttribute(boolean isNonObservationTimeAttribute) {
        this.isNonObservationTimeAttribute = isNonObservationTimeAttribute;
    }


    /**
     * Gets time format.
     *
     * @return the time format
     */
    public boolean getTimeFormat() {
        return isTimeFormat;
    }


    /**
     * Sets time format.
     *
     * @param isTimeFormat the is time format
     */
    public void setTimeFormat(boolean isTimeFormat) {
        this.isTimeFormat = isTimeFormat;
    }
}
