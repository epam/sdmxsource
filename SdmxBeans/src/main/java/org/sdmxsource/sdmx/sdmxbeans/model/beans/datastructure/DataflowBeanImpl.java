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

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.sdmx.resources.sdmxml.schemas.v20.structure.DataflowType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.KeyFamilyRefType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.DataflowMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.MaintainableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.metadatastructure.DataflowMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.util.RefUtil;
import org.sdmxsource.sdmx.util.beans.reference.CrossReferenceBeanImpl;

import javax.xml.bind.ValidationException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The type Dataflow bean.
 */
public class DataflowBeanImpl extends MaintainableBeanImpl implements DataflowBean {
    private static final long serialVersionUID = 1L;
    private static Logger LOG = LoggerFactory.getLogger(DataflowBeanImpl.class);
    private CrossReferenceBean keyFamilyRef;


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM ITSELF, CREATES STUB BEAN //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private DataflowBeanImpl(DataflowBean bean, URL actualLocation, boolean isServiceUrl) {
        super(bean, actualLocation, isServiceUrl);
        LOG.debug("Stub DataflowBean Built");
    }

    /**
     * Instantiates a new Dataflow bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public DataflowBeanImpl(DataflowMutableBean bean) {
        super(bean);
        LOG.debug("Building DataflowBean from Mutable Bean");
        if (bean.getDataStructureRef() != null) {
            this.keyFamilyRef = new CrossReferenceBeanImpl(this, bean.getDataStructureRef());
        }
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("DataflowBean Built " + this);
        }
    }


    /**
     * Instantiates a new Dataflow bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public DataflowBeanImpl(org.sdmx.resources.sdmxml.schemas.v21.structure.DataflowType bean) {
        super(bean, SDMX_STRUCTURE_TYPE.DATAFLOW);
        LOG.debug("Building DataflowBean from 2.1 SDMX");
        if (bean.getStructure() != null) {
            keyFamilyRef = RefUtil.createReference(this, bean.getStructure());
        }
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("DataflowBean Built " + this);
        }
    }


    /**
     * Instantiates a new Dataflow bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public DataflowBeanImpl(DataflowType bean) {
        super(bean, SDMX_STRUCTURE_TYPE.DATAFLOW,
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
        LOG.debug("Building DataflowBean from 2.0 SDMX");
        if (bean.getKeyFamilyRef() != null) {
            KeyFamilyRefType kfRef = bean.getKeyFamilyRef();
            if (kfRef.getURN() != null) {
                keyFamilyRef = new CrossReferenceBeanImpl(this, kfRef.getURN());
            } else {
                keyFamilyRef = new CrossReferenceBeanImpl(this,
                        kfRef.getKeyFamilyAgencyID(),
                        kfRef.getKeyFamilyID(),
                        kfRef.getVersion(),
                        SDMX_STRUCTURE_TYPE.DSD);
            }
        }
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("DataflowBean Built " + this);
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
            DataflowBean that = (DataflowBean) bean;
            if (!super.equivalent(keyFamilyRef, that.getDataStructureRef())) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
        }
        return false;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() throws ValidationException {
        if (!this.isExternalReference.isTrue()) {
            if (keyFamilyRef == null) {
                throw new SdmxSemmanticException("Dataflow must reference a Data Structure Definition");
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public DataflowBean getStub(URL actualLocation, boolean isServiceUrl) {
        return new DataflowBeanImpl(this, actualLocation, isServiceUrl);
    }

    @Override
    public DataflowMutableBean getMutableInstance() {
        return new DataflowMutableBeanImpl(this);
    }

    @Override
    public List<CrossReferenceBean> getCrossReferencedConstrainables() {
        List<CrossReferenceBean> returnList = new ArrayList<CrossReferenceBean>();
        if (keyFamilyRef != null) {
            returnList.add(getDataStructureRef());
        }
        return returnList;
    }

    @Override
    protected Set<CrossReferenceBean> getCrossReferenceInternal() {
        Set<CrossReferenceBean> references = new HashSet<CrossReferenceBean>();
        if (keyFamilyRef != null) {
            references.add(keyFamilyRef);
        }
        return references;
    }

    @Override
    public CrossReferenceBean getDataStructureRef() {
        return keyFamilyRef;
    }
}
