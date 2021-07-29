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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.datastructure;

import org.sdmx.resources.sdmxml.schemas.v20.structure.AttachmentLevelType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.AttributeType;
import org.sdmx.resources.sdmxml.schemas.v21.common.ConceptReferenceType;
import org.sdmx.resources.sdmxml.schemas.v21.common.LocalDimensionReferenceType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.AttributeRelationshipType;
import org.sdmxsource.sdmx.api.constants.ATTRIBUTE_ATTACHMENT_LEVEL;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.*;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.AttributeMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.ComponentBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.util.RefUtil;
import org.sdmxsource.sdmx.util.beans.reference.CrossReferenceBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * The type Attribute bean.
 */
public class AttributeBeanImpl extends ComponentBeanImpl implements AttributeBean {
    private static final long serialVersionUID = 1L;

    private ATTRIBUTE_ATTACHMENT_LEVEL attachmentLevel;
    private String assignmentStatus;

    private String attachmentGroup;
    private List<String> dimensionReference = new ArrayList<String>();
    private List<CrossReferenceBean> conceptRoles = new ArrayList<CrossReferenceBean>();
    private String primaryMeasureReference;

    /**
     * Instantiates a new Attribute bean.
     *
     * @param attribute the attribute
     * @param parent    the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS 			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public AttributeBeanImpl(AttributeMutableBean attribute, AttributeListBean parent) {
        super(attribute, parent);
        try {
            this.attachmentLevel = attribute.getAttachmentLevel();
            this.assignmentStatus = attribute.getAssignmentStatus();
            this.attachmentGroup = attribute.getAttachmentGroup();
            if (attribute.getDimensionReferences() != null) {
                this.dimensionReference = new ArrayList<String>(attribute.getDimensionReferences());
            }
            if (attribute.getConceptRoles() != null) {
                for (StructureReferenceBean currentConceptRole : attribute.getConceptRoles()) {
                    this.conceptRoles.add(new CrossReferenceBeanImpl(this, currentConceptRole));
                }
            }
            this.primaryMeasureReference = attribute.getPrimaryMeasureReference();
            validateAttributeAttributes();
        } catch (Throwable th) {
            throw new SdmxSemmanticException(th, "Error creating structure: " + this.toString());
        }
    }

    /**
     * Instantiates a new Attribute bean.
     *
     * @param attribute the attribute
     * @param parent    the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public AttributeBeanImpl(org.sdmx.resources.sdmxml.schemas.v21.structure.AttributeType attribute, AttributeListBean parent) {
        super(attribute, SDMX_STRUCTURE_TYPE.DATA_ATTRIBUTE, parent);
        if (attribute.getAttributeRelationship() != null) {
            AttributeRelationshipType attRelationShip = attribute.getAttributeRelationship();
            if (attRelationShip.getGroup() != null) {
                //FUNC - this can also be attached to a dimension list
                attachmentGroup = RefUtil.createLocalIdReference(attRelationShip.getGroup());
                this.attachmentLevel = ATTRIBUTE_ATTACHMENT_LEVEL.GROUP;
            } else if (ObjectUtil.validCollection(attRelationShip.getDimensionList())) {
                //DEFAULT TO DIMENSION GROUP
                this.attachmentLevel = ATTRIBUTE_ATTACHMENT_LEVEL.DIMENSION_GROUP;

                for (LocalDimensionReferenceType lDim : attRelationShip.getDimensionList()) {
                    this.dimensionReference.add(RefUtil.createLocalIdReference(lDim));
                }
                DataStructureBean parentDSD = (DataStructureBean) getMaintainableParent();
                for (DimensionBean currentDimension : parentDSD.getDimensions()) {
                    if (this.dimensionReference.contains(currentDimension.getId())) {
                        if (currentDimension.isTimeDimension()) {
                            //REFERENCING THE TIME DIMENSION THEREFOR OBSERVATION LEVEL
                            this.attachmentLevel = ATTRIBUTE_ATTACHMENT_LEVEL.OBSERVATION;
                            break;
                        }
                    }
                }

                if (ObjectUtil.validCollection(attRelationShip.getAttachmentGroupList())) {
                    attachmentGroup = RefUtil.createLocalIdReference(attRelationShip.getAttachmentGroupList().get(0));
                    this.attachmentLevel = ATTRIBUTE_ATTACHMENT_LEVEL.GROUP;
                }
            } else if (attRelationShip.getPrimaryMeasure() != null) {
                primaryMeasureReference = RefUtil.createLocalIdReference(attRelationShip.getPrimaryMeasure());
                this.attachmentLevel = ATTRIBUTE_ATTACHMENT_LEVEL.OBSERVATION;
            } else {
                this.attachmentLevel = ATTRIBUTE_ATTACHMENT_LEVEL.DATA_SET;
            }
        }

        this.assignmentStatus = attribute.getAssignmentStatus().toString();
        if (attribute.getConceptRoleList() != null) {
            for (ConceptReferenceType conceptRef : attribute.getConceptRoleList()) {
                this.conceptRoles.add(RefUtil.createReference(this, conceptRef));
            }
        }
        try {
            validateAttributeAttributes();
        } catch (Throwable th) {
            throw new SdmxSemmanticException(th, "Error creating structure: " + this.toString());
        }
    }

    /**
     * Instantiates a new Attribute bean.
     *
     * @param attribute the attribute
     * @param parent    the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public AttributeBeanImpl(AttributeType attribute, AttributeListBean parent) {
        super(attribute,
                SDMX_STRUCTURE_TYPE.DATA_ATTRIBUTE,
                attribute.getAnnotations(),
                attribute.getTextFormat(),
                attribute.getCodelistAgency(),
                attribute.getCodelist(),
                attribute.getCodelistVersion(),
                attribute.getConceptSchemeAgency(),
                attribute.getConceptSchemeRef(),
                attribute.getConceptVersion(),
                attribute.getConceptAgency(),
                attribute.getConceptRef(), parent);

        if (attribute.getAttachmentGroupList() != null) {
            if (attribute.getAttachmentGroupList().size() > 1) {
                throw new SdmxSemmanticException(ExceptionCode.UNSUPPORTED, "Attribute with more then one group attachment");
            }
            if (attribute.getAttachmentGroupList().size() == 1) {
                this.attachmentGroup = attribute.getAttachmentGroupList().get(0);
            }
        }
        ATTRIBUTE_ATTACHMENT_LEVEL attachmentLevel = null;
        DataStructureBean parentDSD = (DataStructureBean) getMaintainableParent();

        switch (attribute.getAttachmentLevel().intValue()) {
            case AttachmentLevelType.INT_DATA_SET:
                attachmentLevel = ATTRIBUTE_ATTACHMENT_LEVEL.DATA_SET;
                break;
            case AttachmentLevelType.INT_GROUP:
                attachmentLevel = ATTRIBUTE_ATTACHMENT_LEVEL.GROUP;
                break;
            case AttachmentLevelType.INT_OBSERVATION:
                attachmentLevel = ATTRIBUTE_ATTACHMENT_LEVEL.OBSERVATION;
                this.primaryMeasureReference = parentDSD.getPrimaryMeasure().getId();
                break;
            case AttachmentLevelType.INT_SERIES:
                attachmentLevel = ATTRIBUTE_ATTACHMENT_LEVEL.DIMENSION_GROUP;
                for (DimensionBean currentDimension : parentDSD.getDimensions(SDMX_STRUCTURE_TYPE.DIMENSION, SDMX_STRUCTURE_TYPE.MEASURE_DIMENSION)) {
                    if (!this.dimensionReference.contains(currentDimension.getId())) {
                        this.dimensionReference.add(currentDimension.getId());
                    }
                }
                break;
            default:
                throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "Attachment:" + attribute.getAttachmentLevel().toString());
        }
        this.attachmentLevel = attachmentLevel;
        this.assignmentStatus = attribute.getAssignmentStatus().toString();

        try {
            validateAttributeAttributes();
        } catch (Throwable th) {
            throw new SdmxSemmanticException(th, "Error creating structure: " + this.toString());
        }
    }

    /**
     * Instantiates a new Attribute bean.
     *
     * @param attribute the attribute
     * @param parent    the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public AttributeBeanImpl(org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.AttributeType attribute, AttributeListBean parent) {
        super(attribute,
                SDMX_STRUCTURE_TYPE.DATA_ATTRIBUTE,
                attribute.getAnnotations(),
                attribute.getCodelist(),
                attribute.getConcept(), parent);

        if (attribute.getAttachmentGroupList() != null) {
            if (attribute.getAttachmentGroupList().size() > 1) {
                throw new SdmxSemmanticException(ExceptionCode.UNSUPPORTED, "Attribute with more then one group attachment");
            }
            if (attribute.getAttachmentGroupList().size() == 1) {
                this.attachmentGroup = attribute.getAttachmentGroupList().get(0);
            }
        }
        ATTRIBUTE_ATTACHMENT_LEVEL attachmentLevel = null;
        DataStructureBean parentDSD = (DataStructureBean) getMaintainableParent();

        switch (attribute.getAttachmentLevel().intValue()) {
            case org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.AttachmentLevelType.INT_DATA_SET:
                attachmentLevel = ATTRIBUTE_ATTACHMENT_LEVEL.DATA_SET;
                break;
            case org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.AttachmentLevelType.INT_GROUP:
                attachmentLevel = ATTRIBUTE_ATTACHMENT_LEVEL.GROUP;
                break;
            case org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.AttachmentLevelType.INT_OBSERVATION:
                attachmentLevel = ATTRIBUTE_ATTACHMENT_LEVEL.OBSERVATION;
                this.primaryMeasureReference = parentDSD.getPrimaryMeasure().getId();
                break;
            case org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.AttachmentLevelType.INT_SERIES:
                attachmentLevel = ATTRIBUTE_ATTACHMENT_LEVEL.DIMENSION_GROUP;
                for (DimensionBean currentDimension : parentDSD.getDimensions(SDMX_STRUCTURE_TYPE.DIMENSION)) {
                    if (!this.dimensionReference.contains(currentDimension.getId())) {
                        this.dimensionReference.add(currentDimension.getId());
                    }
                }
                break;
            default:
                throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "Attachment:" + attribute.getAttachmentLevel().toString());
        }
        this.attachmentLevel = attachmentLevel;
        this.assignmentStatus = attribute.getAssignmentStatus().toString();

        try {
            validateAttributeAttributes();
        } catch (Throwable th) {
            throw new SdmxSemmanticException(th, "Error creating structure: " + this.toString());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////DEEP EQUALS							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean deepEquals(SDMXBean bean, boolean includeFinalProperties) {
        if (bean == null) {
            return false;
        }
        if (bean.getStructureType() == this.getStructureType()) {
            AttributeBean that = (AttributeBean) bean;
            if (!ObjectUtil.equivalent(attachmentLevel, that.getAttachmentLevel())) {
                return false;
            }
            if (!ObjectUtil.equivalent(assignmentStatus, that.getAssignmentStatus())) {
                return false;
            }
            if (!ObjectUtil.equivalent(attachmentGroup, that.getAttachmentGroup())) {
                return false;
            }
            if (!ObjectUtil.equivalentCollection(dimensionReference, that.getDimensionReferences())) {
                return false;
            }
            if (!ObjectUtil.equivalentCollection(conceptRoles, that.getConceptRoles())) {
                return false;
            }
            if (!ObjectUtil.equivalent(primaryMeasureReference, that.getPrimaryMeasureReference())) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
        }
        return false;
    }

    /**
     * Validate attribute attributes.
     *
     * @throws ValidationException the validation exception
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected void validateAttributeAttributes() throws ValidationException {
        if (this.attachmentLevel == null) {
            throw new SdmxSemmanticException(ExceptionCode.BEAN_MISSING_REQUIRED_ATTRIBUTE, this.structureType, "attachmentLevel");
        }
        if (attachmentLevel == ATTRIBUTE_ATTACHMENT_LEVEL.OBSERVATION) {
            if (!ObjectUtil.validCollection(dimensionReference)) {
                //Ensure that the primary measure reference is set if the dimension references are empty
                this.primaryMeasureReference = PrimaryMeasureBean.FIXED_ID;
            } else {
                DataStructureBean parentDSD = (DataStructureBean) getMaintainableParent();
                DimensionBean timeDimension = parentDSD.getTimeDimension();
                if (timeDimension == null) {
                    // This is not an observation attribute as it does not include the time dimension
                    attachmentLevel = ATTRIBUTE_ATTACHMENT_LEVEL.DIMENSION_GROUP;
                } else {
                    String timeDimensionId = timeDimension.getId();
                    if (!dimensionReference.contains(timeDimensionId)) {
                        // This is not an observation attribute as it does not include the time dimension
                        attachmentLevel = ATTRIBUTE_ATTACHMENT_LEVEL.DIMENSION_GROUP;
                    }
                }
            }
        }
        if (this.assignmentStatus == null) {
            throw new SdmxSemmanticException(ExceptionCode.BEAN_MISSING_REQUIRED_ATTRIBUTE, this.structureType, "assignmentStatus");
        }
        if (!ObjectUtil.validString(attachmentGroup) && attachmentLevel == ATTRIBUTE_ATTACHMENT_LEVEL.GROUP) {
            throw new SdmxSemmanticException("Attribute has specified attachment level specified as Group, but does not specifies the attachment groups. Either change the attachment level, or declare which group the attribute attaches to: " + getId());
        }
        if (ObjectUtil.validString(attachmentGroup) && attachmentLevel != ATTRIBUTE_ATTACHMENT_LEVEL.GROUP) {
            throw new SdmxSemmanticException("Attribute specifies an attachment group of '" + attachmentGroup + "', but the the attachment level is not set to GROUP, it is set to  '" + attachmentLevel + "'.  Either remove the attribute's reference to the '" + attachmentGroup + "' or change the attribute's attachment level to GROUP");
        }
        if (attachmentLevel == ATTRIBUTE_ATTACHMENT_LEVEL.DIMENSION_GROUP) {
            if (!ObjectUtil.validCollection(dimensionReference)) {
                throw new SdmxSemmanticException("Attribute specifies attachment level of 'Dimension Group' but does not specify any dimensions");
            }
        }
        for (CrossReferenceBean conceptRole : conceptRoles) {
            if (conceptRole.getTargetReference() != SDMX_STRUCTURE_TYPE.CONCEPT) {
                throw new SdmxSemmanticException("Illegal concept role '" + conceptRole.getTargetUrn() + "'.  Concept Role must reference a concept.");
            }
        }

    }

    @Override
    protected Set<CrossReferenceBean> getCrossReferenceInternal() {
        Set<CrossReferenceBean> references = super.getCrossReferenceInternal();
        if (conceptRoles != null) {
            references.addAll(conceptRoles);
        }
        return references;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected List<String> getParentIds(boolean includeDifferentTypes) {
        List<String> returnList = new ArrayList<String>();
        returnList.add(this.getId());
        return returnList;
    }

    @Override
    public String getAttachmentGroup() {
        return attachmentGroup;
    }

    @Override
    public List<CrossReferenceBean> getConceptRoles() {
        return new ArrayList<CrossReferenceBean>(conceptRoles);
    }

    @Override
    public boolean isTimeFormat() {
        return this.getId().equals("TIME_FORMAT");
    }

    @Override
    public ATTRIBUTE_ATTACHMENT_LEVEL getAttachmentLevel() {
        return attachmentLevel;
    }

    @Override
    public String getAssignmentStatus() {
        return assignmentStatus;
    }

    @Override
    public List<String> getDimensionReferences() {
        return dimensionReference;
    }

    @Override
    public String getPrimaryMeasureReference() {
        return primaryMeasureReference;
    }

    @Override
    public boolean isMandatory() {
        return assignmentStatus.equals("Mandatory");
    }
}
