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

import org.sdmx.resources.sdmxml.schemas.v20.structure.GroupType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.GroupDimensionType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.base.SdmxStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.GroupBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.GroupMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.IdentifiableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.util.RefUtil;
import org.sdmxsource.sdmx.util.beans.reference.CrossReferenceBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The type Group bean.
 */
public class GroupBeanImpl extends IdentifiableBeanImpl implements GroupBean {
    private static final long serialVersionUID = 1L;
    private List<String> dimensionRef = new ArrayList<String>();
    private CrossReferenceBean attachmentConstraintRef;

    /**
     * Instantiates a new Group bean.
     *
     * @param bean   the bean
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS 			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public GroupBeanImpl(GroupMutableBean bean, SdmxStructureBean parent) {
        super(bean, parent);
        if (bean.getAttachmentConstraintRef() != null) {
            this.attachmentConstraintRef = new CrossReferenceBeanImpl(this, bean.getAttachmentConstraintRef());
        }
        if (bean.getDimensionRef() != null) {
            this.dimensionRef = new ArrayList<String>(bean.getDimensionRef());
        }
        validateGroupAttributes();
    }

    /**
     * Instantiates a new Group bean.
     *
     * @param group  the group
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public GroupBeanImpl(org.sdmx.resources.sdmxml.schemas.v21.structure.GroupType group, SdmxStructureBean parent) {
        super(group, SDMX_STRUCTURE_TYPE.GROUP, parent);
        if (group.getAttachmentConstraint() != null) {
            this.attachmentConstraintRef = RefUtil.createReference(this, group.getAttachmentConstraint());
        }
        if (group.getGroupDimensionList() != null) {
            this.dimensionRef = new ArrayList<String>();
            for (GroupDimensionType each : group.getGroupDimensionList()) {
                this.dimensionRef.add(RefUtil.createLocalIdReference(each.getDimensionReference()));
            }
        }
        validateGroupAttributes();
    }

    /**
     * Instantiates a new Group bean.
     *
     * @param group  the group
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public GroupBeanImpl(GroupType group, SdmxStructureBean parent) {
        super(group, SDMX_STRUCTURE_TYPE.GROUP, group.getId(), null, group.getAnnotations(), parent);
        /*
         * We do NOT support Attachment Constraint References in Version 2 since they are essentially useless.
         */
        if (group.getDimensionRefList() != null) {
            this.dimensionRef = new ArrayList<String>(group.getDimensionRefList());
        }
        validateGroupAttributes();
    }

    /**
     * Instantiates a new Group bean.
     *
     * @param group  the group
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public GroupBeanImpl(org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.GroupType group, SdmxStructureBean parent) {
        super(group, SDMX_STRUCTURE_TYPE.GROUP, group.getId(), null, group.getAnnotations(), parent);
        if (group.getDimensionRefList() != null) {
            this.dimensionRef = new ArrayList<String>(group.getDimensionRefList());
        }
        validateGroupAttributes();
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
            GroupBean that = (GroupBean) bean;
            if (!ObjectUtil.equivalentCollection(dimensionRef, that.getDimensionRefs())) {
                return false;
            }
            if (!ObjectUtil.equivalent(attachmentConstraintRef, that.getAttachmentConstraintRef())) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
        }
        return false;
    }

    /**
     * Validate group attributes.
     *
     * @throws SdmxSemmanticException the sdmx semmantic exception
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected void validateGroupAttributes() throws SdmxSemmanticException {
        if (attachmentConstraintRef == null && (this.dimensionRef == null || this.dimensionRef.size() == 0)) {
            throw new SdmxSemmanticException(ExceptionCode.BEAN_MISSING_REQUIRED_ELEMENT, "Group", "DimensionRef/AttachmentConstraintRef");
        }
        if (this.attachmentConstraintRef != null && this.dimensionRef.size() > 0) {
            throw new SdmxSemmanticException(ExceptionCode.BEAN_MUTUALLY_EXCLUSIVE, "DimensionRef", "AttachmentConstraintRef", "Group");
        }
    }

    @Override
    protected Set<CrossReferenceBean> getCrossReferenceInternal() {
        Set<CrossReferenceBean> references = new HashSet<CrossReferenceBean>();
        if (attachmentConstraintRef != null) {
            references.add(attachmentConstraintRef);
        }
        return references;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public List<String> getDimensionRefs() {
        return new ArrayList<String>(dimensionRef);
    }

    @Override
    public CrossReferenceBean getAttachmentConstraintRef() {
        return attachmentConstraintRef;
    }
}
