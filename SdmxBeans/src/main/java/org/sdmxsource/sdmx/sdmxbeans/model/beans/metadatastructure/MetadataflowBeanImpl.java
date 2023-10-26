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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.metadatastructure;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.sdmx.resources.sdmxml.schemas.v20.structure.MetadataStructureRefType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.MetadataflowType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataFlowBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.metadatastructure.MetadataFlowMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.MaintainableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.metadatastructure.MetadataflowMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.util.RefUtil;
import org.sdmxsource.sdmx.util.beans.reference.CrossReferenceBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;


/**
 * The type Metadataflow bean.
 */
public class MetadataflowBeanImpl extends MaintainableBeanImpl implements MetadataFlowBean {
    private static final long serialVersionUID = 1L;
    private static Logger LOG = LoggerFactory.getLogger(MetadataflowBeanImpl.class);
    private CrossReferenceBean metadataStructureRef;


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM ITSELF, CREATES STUB BEAN //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private MetadataflowBeanImpl(MetadataFlowBean bean, URL actualLocation, boolean isServiceUrl) {
        super(bean, actualLocation, isServiceUrl);
        LOG.debug("Stub MetadataFlowBean Built");
    }


    /**
     * Instantiates a new Metadataflow bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public MetadataflowBeanImpl(MetadataFlowMutableBean bean) {
        super(bean);
        LOG.debug("Building MetadataFlowBean from Mutable Bean");
        if (bean.getMetadataStructureRef() != null) {
            this.metadataStructureRef = new CrossReferenceBeanImpl(this, bean.getMetadataStructureRef());
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("MetadataFlowBean Built " + this.getUrn());
        }
    }


    /**
     * Instantiates a new Metadataflow bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public MetadataflowBeanImpl(org.sdmx.resources.sdmxml.schemas.v21.structure.MetadataflowType bean) {
        super(bean, SDMX_STRUCTURE_TYPE.METADATA_FLOW);
        LOG.debug("Building MetadataFlowBean from 2.1 SDMX");
        this.metadataStructureRef = RefUtil.createReference(this, bean.getStructure());
        if (LOG.isDebugEnabled()) {
            LOG.debug("MetadataFlowBean Built " + this.getUrn());
        }
    }

    /**
     * Instantiates a new Metadataflow bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public MetadataflowBeanImpl(MetadataflowType bean) {
        super(bean, SDMX_STRUCTURE_TYPE.METADATA_FLOW,
                bean.getValidTo(),
                bean.getValidFrom(),
                bean.getVersion(),
                createTertiary(bean.isSetIsFinal(), bean.getIsFinal()),
                bean.getAgencyID(),
                bean.getId(),
                bean.getUri(),
                bean.getNameList(),
                bean.getDescriptionList(),
                createTertiary(bean.isSetIsExternalReference(), bean.getIsExternalReference()),
                bean.getAnnotations());
        LOG.debug("Building MetadataFlowBean from 2.0 SDMX");
        if (bean.getMetadataStructureRef() != null) {
            MetadataStructureRefType ref = bean.getMetadataStructureRef();
            if (ObjectUtil.validString(ref.getURN())) {
                this.metadataStructureRef = new CrossReferenceBeanImpl(this, ref.getURN());
            } else {
                this.metadataStructureRef = new CrossReferenceBeanImpl(this, ref.getMetadataStructureAgencyID(), ref.getMetadataStructureID(), ref.getVersion(), SDMX_STRUCTURE_TYPE.MSD);
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("MetadataFlowBean Built " + this.getUrn());
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
            MetadataFlowBean that = (MetadataFlowBean) bean;
            if (!super.equivalent(metadataStructureRef, that.getMetadataStructureRef())) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected Set<CrossReferenceBean> getCrossReferenceInternal() {
        Set<CrossReferenceBean> references = new HashSet<CrossReferenceBean>();
        if (metadataStructureRef != null) {
            references.add(metadataStructureRef);
        }
        return references;
    }

    @Override
    public MetadataFlowBean getStub(URL actualLocation, boolean isServiceUrl) {
        return new MetadataflowBeanImpl(this, actualLocation, isServiceUrl);
    }

    @Override
    public MetadataFlowMutableBean getMutableInstance() {
        return new MetadataflowMutableBeanImpl(this);
    }

    @Override
    public CrossReferenceBean getMetadataStructureRef() {
        return metadataStructureRef;
    }
}
