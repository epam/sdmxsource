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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.registry;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.DataSourceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ConstraintAttachmentBean;
import org.sdmxsource.sdmx.api.model.mutable.base.DataSourceMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.reference.DataAndMetadataSetMutableReference;
import org.sdmxsource.sdmx.api.model.mutable.registry.ConstraintAttachmentMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.MutableBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The type Content constraint attachment mutable bean.
 */
public class ContentConstraintAttachmentMutableBeanImpl extends MutableBeanImpl implements ConstraintAttachmentMutableBean {
    private static final long serialVersionUID = 1L;

    private DataAndMetadataSetMutableReference dataOrMetadataSetReference;
    private Set<StructureReferenceBean> structureReference = new HashSet<StructureReferenceBean>();
    private List<DataSourceMutableBean> dataSources = new ArrayList<DataSourceMutableBean>();

    /**
     * Instantiates a new Content constraint attachment mutable bean.
     */
    public ContentConstraintAttachmentMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.CONTENT_CONSTRAINT_ATTACHMENT);
    }

    /**
     * Instantiates a new Content constraint attachment mutable bean.
     *
     * @param immutable the immutable
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM IMMUTABLE BEAN				 //////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ContentConstraintAttachmentMutableBeanImpl(ConstraintAttachmentBean immutable) {
        super(immutable);
        if (immutable.getDataOrMetadataSetReference() != null)
            this.dataOrMetadataSetReference = immutable.getDataOrMetadataSetReference().createMutableInstance();
        if (immutable.getStructureReference() != null)
            for (CrossReferenceBean xsRef : immutable.getStructureReference()) {
                this.structureReference.add(xsRef.createMutableInstance());
            }
        if (ObjectUtil.validCollection(immutable.getDataSources())) {
            for (DataSourceBean dsbean : immutable.getDataSources()) {
                this.dataSources.add(dsbean.createMutableInstance());
            }
        }
    }


    @Override
    public DataAndMetadataSetMutableReference getDataOrMetadataSetReference() {
        return this.dataOrMetadataSetReference;
    }

    @Override
    public void setDataOrMetadataSetReference(DataAndMetadataSetMutableReference ref) {
        this.dataOrMetadataSetReference = ref;
    }

    @Override
    public void addStructureReference(StructureReferenceBean bean) {
        if (structureReference == null) {
            structureReference = new HashSet<StructureReferenceBean>();
        }
        structureReference.add(bean);
    }

    @Override
    public Set<StructureReferenceBean> getStructureReference() {
        return structureReference;
    }

    @Override
    public void setStructureReference(Set<StructureReferenceBean> structureReference) {
        if (structureReference == null) {
            structureReference = new HashSet<StructureReferenceBean>();
        }
        this.structureReference = structureReference;
    }

    @Override
    public List<DataSourceMutableBean> getDataSources() {
        return this.dataSources;
    }

    @Override
    public void setDataSources(List<DataSourceMutableBean> beans) {
        if (beans == null) {
            beans = new ArrayList<DataSourceMutableBean>();
        }
        this.dataSources = beans;
    }

    @Override
    public void addDataSources(DataSourceMutableBean bean) {
        if (bean != null) {
            this.dataSources.add(bean);
        }
    }
}
