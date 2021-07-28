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
import org.sdmx.resources.sdmxml.schemas.v21.common.QueryableDataSourceType;
import org.sdmx.resources.sdmxml.schemas.v21.common.SetReferenceType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.ConstraintAttachmentType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.DataSourceBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.DataAndMetadataSetReference;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ConstraintAttachmentBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ConstraintBean;
import org.sdmxsource.sdmx.api.model.mutable.base.DataSourceMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.ConstraintAttachmentMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.DataSourceBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.SdmxStructureBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.reference.DataAndMetadataSetReferenceImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.registry.ContentConstraintAttachmentMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.util.RefUtil;
import org.sdmxsource.sdmx.util.beans.reference.CrossReferenceBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The type Constraint attachment bean.
 */
public class ConstraintAttachmentBeanImpl extends SdmxStructureBeanImpl implements ConstraintAttachmentBean {
    private static final long serialVersionUID = -1569557335515629246L;
    private DataAndMetadataSetReference dataOrMetadataSetReference;
    private Set<CrossReferenceBean> crossReference = new HashSet<CrossReferenceBean>();
    private List<DataSourceBean> dataSources = new ArrayList<DataSourceBean>();
    //FUNC Validation of ALL Constraints and This CrossRefence/DataSource Pair should be grouped

    /**
     * Instantiates a new Constraint attachment bean.
     *
     * @param mutable    the mutable
     * @param constraint the constraint
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEAN				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ConstraintAttachmentBeanImpl(ConstraintAttachmentMutableBean mutable, ConstraintBean constraint) {
        super(SDMX_STRUCTURE_TYPE.CONTENT_CONSTRAINT_ATTACHMENT, constraint);
        if (mutable.getDataOrMetadataSetReference() != null)
            this.dataOrMetadataSetReference = new DataAndMetadataSetReferenceImpl(mutable.getDataOrMetadataSetReference());
        if (mutable.getStructureReference() != null) {
            for (StructureReferenceBean sRef : mutable.getStructureReference()) {
                this.crossReference.add(new CrossReferenceBeanImpl(this, sRef));
            }
        }
        if (ObjectUtil.validCollection(mutable.getDataSources())) {
            for (DataSourceMutableBean each : mutable.getDataSources())
                this.dataSources.add(new DataSourceBeanImpl(each, this));
        }
        validate();
    }

    /**
     * Instantiates a new Constraint attachment bean.
     *
     * @param type       the type
     * @param constraint the constraint
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ConstraintAttachmentBeanImpl(ConstraintAttachmentType type, ConstraintBean constraint) {
        super(SDMX_STRUCTURE_TYPE.CONTENT_CONSTRAINT_ATTACHMENT, constraint);
        if (type.getSimpleDataSourceList() != null) {
            for (String dataSource : type.getSimpleDataSourceList()) {
                dataSources.add(new DataSourceBeanImpl(dataSource, this));
            }
        }
        if (type.getDataProvider() != null) {
            crossReference.add(RefUtil.createReference(this, type.getDataProvider()));
        }

        if (ObjectUtil.validCollection(type.getDataSetList())) {
            //THERE IS ONLY ONE DATA SET
            SetReferenceType setRef = type.getDataSetList().get(0);
            CrossReferenceBean ref = RefUtil.createReference(this, setRef.getDataProvider());
            dataOrMetadataSetReference = new DataAndMetadataSetReferenceImpl(ref, setRef.getID(), true);
        }
        if (ObjectUtil.validCollection(type.getMetadataSetList())) {
            //THERE IS ONLY ONE METADATA SET
            SetReferenceType setRef = type.getMetadataSetList().get(0);
            CrossReferenceBean ref = RefUtil.createReference(this, setRef.getDataProvider());
            dataOrMetadataSetReference = new DataAndMetadataSetReferenceImpl(ref, setRef.getID(), false);
        }
        addRef(type.getDataStructureList());
        addRef(type.getMetadataStructureList());
        addRef(type.getDataflowList());
        addRef(type.getMetadataflowList());
        addRef(type.getProvisionAgreementList());
        for (QueryableDataSourceType queryableDataSource : type.getQueryableDataSourceList()) {
            dataSources.add(new DataSourceBeanImpl(queryableDataSource, this));
        }
        validate();
    }

    private void addRef(List<? extends MaintainableReferenceBaseType> refListType) {
        if (ObjectUtil.validCollection(refListType)) {
            for (MaintainableReferenceBaseType ref : refListType) {
                CrossReferenceBean crossRef = RefUtil.createReference(this, ref);
                crossReference.add(crossRef);
            }
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
            ConstraintAttachmentBean that = (ConstraintAttachmentBean) bean;
            if (!ObjectUtil.equivalent(dataOrMetadataSetReference, that.getDataOrMetadataSetReference())) {
                return false;
            }
            if (!ObjectUtil.equivalentCollection(crossReference, that.getStructureReference())) {
                return false;
            }
            if (!super.equivalent(dataSources, that.getDataSources(), includeFinalProperties)) {
                return false;
            }
            return true;
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATE				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() throws SdmxSemmanticException {
        SDMX_STRUCTURE_TYPE constrainingType = null;

        // Checking that there is at least something in this Attachment
        if (dataOrMetadataSetReference == null &&
                !(ObjectUtil.validCollection(crossReference)) &&
                !(ObjectUtil.validCollection(dataSources))) {
            throw new SdmxSemmanticException("The ContentConstraint doesn't have a Constraint Attachment defined");
        }

        // May not have more than one Data Provider for a Content Constraint
        if (ObjectUtil.validCollection(crossReference) && crossReference.size() > 1) {
            boolean foundDp = false;
            for (CrossReferenceBean xsRef : crossReference) {
                if (xsRef.getMaintainableStructureType() == SDMX_STRUCTURE_TYPE.DATA_PROVIDER_SCHEME) {
                    if (foundDp) {
                        throw new SdmxSemmanticException("A ContentConstraint may not have multiple Data Providers as Constraint Attachments. A maximum of 1 Data Provider per Constraint Attachment is defined by the SDMX schema.");
                    }
                    foundDp = true;
                }
            }
        }

        for (CrossReferenceBean xsRef : crossReference) {
            //NOTE this is to get around the issue where a constraint is linked to a registration (which is not allowed)
            //But it is required for an application to know which registration is associated with which constraint.
            //In this case, allow the attachment, but *hide* it, i.e it should not be written out in SDMX, and ignored in validation
            if (xsRef.getTargetReference() == SDMX_STRUCTURE_TYPE.REGISTRATION) {
                continue;
            }
            if (constrainingType == null) {
                constrainingType = xsRef.getTargetReference();
            } else if (constrainingType != xsRef.getTargetReference()) {
                throw new SdmxSemmanticException("ContentConstraint's ConstraintAttachment may only reference structures of the same type, got '" + constrainingType + "' and '" + xsRef.getTargetReference() + "'");
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public DataAndMetadataSetReference getDataOrMetadataSetReference() {
        return dataOrMetadataSetReference;
    }

    @Override
    public Set<CrossReferenceBean> getStructureReference() {
        return crossReference;
    }

    @Override
    public List<DataSourceBean> getDataSources() {
        return new ArrayList<DataSourceBean>(dataSources);
    }

    @Override
    public ConstraintAttachmentMutableBean createMutableInstance() {
        return new ContentConstraintAttachmentMutableBeanImpl(this);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES		                     //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected Set<CrossReferenceBean> getCrossReferenceInternal() {
        Set<CrossReferenceBean> references = new HashSet<CrossReferenceBean>();
        if (crossReference != null) {
            references.addAll(crossReference);
        }
        return references;
    }

    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(dataSources, composites);
        return composites;
    }
}
