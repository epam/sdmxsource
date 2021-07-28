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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.registry;

import org.sdmx.resources.sdmxml.schemas.v21.common.MaintainableReferenceBaseType;
import org.sdmx.resources.sdmxml.schemas.v21.common.SetReferenceType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.ConstraintAttachmentType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.DataSourceBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.DataAndMetadataSetReference;
import org.sdmxsource.sdmx.api.model.beans.registry.AttachmentConstraintAttachmentBean;
import org.sdmxsource.sdmx.api.model.beans.registry.AttachmentConstraintBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.DataSourceBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.SdmxStructureBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.reference.DataAndMetadataSetReferenceImpl;
import org.sdmxsource.sdmx.sdmxbeans.util.RefUtil;
import org.sdmxsource.util.ObjectUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The type Attachment constraint attachment bean.
 */
public class AttachmentConstraintAttachmentBeanImpl extends SdmxStructureBeanImpl implements AttachmentConstraintAttachmentBean {
    private static final long serialVersionUID = 8990636645123835141L;
    private List<DataAndMetadataSetReference> dataOrMetadataSetReference = new ArrayList<DataAndMetadataSetReference>();
    private List<CrossReferenceBean> structureReferences = new ArrayList<CrossReferenceBean>();
    private List<DataSourceBean> dataSources = new ArrayList<DataSourceBean>();
    private SDMX_STRUCTURE_TYPE targetStructure;


    /**
     * Instantiates a new Attachment constraint attachment bean.
     *
     * @param type   the type
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public AttachmentConstraintAttachmentBeanImpl(ConstraintAttachmentType type, AttachmentConstraintBean parent) {
        super(SDMX_STRUCTURE_TYPE.ATTACHMENT_CONSTRAINT_ATTACHMENT, parent);
        if (ObjectUtil.validCollection(type.getDataSetList())) {
            targetStructure = SDMX_STRUCTURE_TYPE.DATASET;
            for (SetReferenceType setReference : type.getDataSetList()) {
                CrossReferenceBean dataProviderRef = RefUtil.createReference(this, setReference.getDataProvider());
                dataOrMetadataSetReference.add(new DataAndMetadataSetReferenceImpl(dataProviderRef, setReference.getID(), true));
            }
        }
        if (ObjectUtil.validCollection(type.getMetadataSetList())) {
            targetStructure = SDMX_STRUCTURE_TYPE.METADATA_SET;
            for (SetReferenceType setReference : type.getMetadataSetList()) {
                CrossReferenceBean dataProviderRef = RefUtil.createReference(this, setReference.getDataProvider());
                dataOrMetadataSetReference.add(new DataAndMetadataSetReferenceImpl(dataProviderRef, setReference.getID(), false));
            }
        }
        if (ObjectUtil.validCollection(type.getSimpleDataSourceList())) {
            targetStructure = SDMX_STRUCTURE_TYPE.DATASOURCE;
            for (String dataSource : type.getSimpleDataSourceList()) {
                dataSources.add(new DataSourceBeanImpl(dataSource, this));
            }
        }
        for (MaintainableReferenceBaseType ref : type.getDataStructureList()) {
            addRef(ref);
        }
        for (MaintainableReferenceBaseType ref : type.getMetadataStructureList()) {
            addRef(ref);
        }
        for (MaintainableReferenceBaseType ref : type.getDataflowList()) {
            addRef(ref);
        }
        for (MaintainableReferenceBaseType ref : type.getMetadataflowList()) {
            addRef(ref);
        }
        for (MaintainableReferenceBaseType ref : type.getProvisionAgreementList()) {
            addRef(ref);
        }
    }

    private void addRef(MaintainableReferenceBaseType refType) {
        CrossReferenceBean crossRef = RefUtil.createReference(this, refType);
        targetStructure = crossRef.getTargetReference();
        structureReferences.add(crossRef);
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
            AttachmentConstraintAttachmentBean that = (AttachmentConstraintAttachmentBean) bean;
            if (!ObjectUtil.equivalentCollection(dataOrMetadataSetReference, that.getDataOrMetadataSetReference())) {
                return false;
            }
            if (!super.equivalent(dataSources, that.getDatasources(), includeFinalProperties)) {
                return false;
            }
            if (!ObjectUtil.equivalentCollection(structureReferences, that.getStructureReferences())) {
                return false;
            }
            if (targetStructure != that.getTargetStructureType()) {
                return false;
            }
            return true;
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public List<CrossReferenceBean> getStructureReferences() {
        return new ArrayList<CrossReferenceBean>(structureReferences);
    }

    @Override
    protected Set<CrossReferenceBean> getCrossReferenceInternal() {
        Set<CrossReferenceBean> references = new HashSet<CrossReferenceBean>();
        if (structureReferences != null) {
            references.addAll(structureReferences);
        }
        return references;
    }

    @Override
    public List<DataAndMetadataSetReference> getDataOrMetadataSetReference() {
        return new ArrayList<DataAndMetadataSetReference>(dataOrMetadataSetReference);
    }


    @Override
    public SDMX_STRUCTURE_TYPE getTargetStructureType() {
        return targetStructure;
    }

    @Override
    public ArrayList<DataSourceBean> getDatasources() {
        return new ArrayList<DataSourceBean>(dataSources);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES		                     //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(dataSources, composites);
        return composites;
    }
}
