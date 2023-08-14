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

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.sdmx.resources.sdmxml.schemas.v20.registry.DataProviderRefType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.DataflowRefType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.MetadataflowRefType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.ProvisionAgreementType;
import org.sdmx.resources.sdmxml.schemas.v21.common.DataProviderReferenceType;
import org.sdmx.resources.sdmxml.schemas.v21.common.StructureUsageReferenceType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TERTIARY_BOOL;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.DataProviderSchemeBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.ProvisionAgreementMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.MaintainableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.TextTypeWrapperImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.registry.ProvisionAgreementMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.util.RefUtil;
import org.sdmxsource.sdmx.util.beans.reference.CrossReferenceBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import javax.xml.bind.ValidationException;
import java.net.URL;
import java.util.*;


/**
 * The type Provision agreement bean.
 */
public class ProvisionAgreementBeanImpl extends MaintainableBeanImpl implements ProvisionAgreementBean {
    private static final long serialVersionUID = 1L;
    private static Logger LOG = LoggerFactory.getLogger(ProvisionAgreementBeanImpl.class);
    private CrossReferenceBean structureUseage;
    private CrossReferenceBean dataproviderRef;

    /**
     * Instantiates a new Provision agreement bean.
     *
     * @param provisionAgreementMutable the provision agreement mutable
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ProvisionAgreementBeanImpl(ProvisionAgreementMutableBean provisionAgreementMutable) {
        super(provisionAgreementMutable);
        LOG.debug("Building ProvisionAgreementBean from Mutable Bean");
        try {
            if (provisionAgreementMutable.getStructureUsage() != null) {
                this.structureUseage = new CrossReferenceBeanImpl(this, provisionAgreementMutable.getStructureUsage());
            }
            if (provisionAgreementMutable.getDataproviderRef() != null) {
                this.dataproviderRef = new CrossReferenceBeanImpl(this, provisionAgreementMutable.getDataproviderRef());
            }
        } catch (Throwable th) {
            throw new SdmxSemmanticException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this);
        }
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("ProvisionAgreementBean Built " + this.getUrn());
        }
    }

    /**
     * Instantiates a new Provision agreement bean.
     *
     * @param provisionAgreementType the provision agreement type
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ProvisionAgreementBeanImpl(org.sdmx.resources.sdmxml.schemas.v21.structure.ProvisionAgreementType provisionAgreementType) {
        super(provisionAgreementType, SDMX_STRUCTURE_TYPE.PROVISION_AGREEMENT);
        LOG.debug("Building ProvisionAgreementBean from 2.1 SDMX");
        try {
            if (provisionAgreementType.getStructureUsage() != null) {
                StructureUsageReferenceType dfRef = provisionAgreementType.getStructureUsage();
                structureUseage = RefUtil.createReference(this, dfRef);
            }
            if (provisionAgreementType.getDataProvider() != null) {
                DataProviderReferenceType dpRef = provisionAgreementType.getDataProvider();
                dataproviderRef = RefUtil.createReference(this, dpRef);
            }
        } catch (Throwable th) {
            throw new SdmxSemmanticException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this);
        }
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("ProvisionAgreementBean Built " + this.getUrn());
        }
    }


    /**
     * Instantiates a new Provision agreement bean.
     *
     * @param provisionAgreementType the provision agreement type
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ProvisionAgreementBeanImpl(ProvisionAgreementType provisionAgreementType) {
        super(provisionAgreementType,
                SDMX_STRUCTURE_TYPE.PROVISION_AGREEMENT,
                provisionAgreementType.getValidTo(),
                provisionAgreementType.getValidFrom(),
                null,
                null,
                null,
                provisionAgreementType.getId(),
                provisionAgreementType.getUri(),
                provisionAgreementType.getNameList(),
                provisionAgreementType.getDescriptionList(),
                null,
                provisionAgreementType.getAnnotations());
        LOG.debug("Building ProvisionAgreementBean from 2.0 SDMX");
        try {
            if (provisionAgreementType.getDataflowRef() != null) {
                DataflowRefType dfRef = provisionAgreementType.getDataflowRef();
                if (ObjectUtil.validString(dfRef.getURN())) {
                    structureUseage = new CrossReferenceBeanImpl(this, dfRef.getURN());
                } else {
                    structureUseage = new CrossReferenceBeanImpl(this, dfRef.getAgencyID(), dfRef.getDataflowID(), dfRef.getVersion(), SDMX_STRUCTURE_TYPE.DATAFLOW);
                }
            } else if (provisionAgreementType.getMetadataflowRef() != null) {
                MetadataflowRefType mdfRef = provisionAgreementType.getMetadataflowRef();
                if (ObjectUtil.validString(mdfRef.getURN())) {
                    structureUseage = new CrossReferenceBeanImpl(this, mdfRef.getURN());
                } else {
                    structureUseage = new CrossReferenceBeanImpl(this, mdfRef.getAgencyID(), mdfRef.getMetadataflowID(), mdfRef.getVersion(), SDMX_STRUCTURE_TYPE.METADATA_FLOW);
                }
            }

            if (provisionAgreementType.getDataProviderRef() != null) {
                DataProviderRefType dpRef = provisionAgreementType.getDataProviderRef();
                if (ObjectUtil.validString(dpRef.getURN())) {
                    dataproviderRef = new CrossReferenceBeanImpl(this, dpRef.getURN());
                } else {
                    dataproviderRef = new CrossReferenceBeanImpl(this,
                            dpRef.getOrganisationSchemeAgencyID(),
                            dpRef.getOrganisationSchemeID(),
                            dpRef.getVersion(),
                            SDMX_STRUCTURE_TYPE.DATA_PROVIDER,
                            dpRef.getDataProviderID());
                }
            }
            //Set Agency ID, Id and Name
            super.agencyId = structureUseage.getMaintainableReference().getAgencyId();

            if (this.getId() == null) {
                this.setId(UUID.randomUUID().toString());
            }
            if (super.getName() == null) {
                super.name.add(new TextTypeWrapperImpl("en", getDataproviderRef().getChildReference().getId() + " provising for " + getStructureUseage().getMaintainableId(), this));
            }
        } catch (Throwable th) {
            throw new SdmxSemmanticException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this);
        }
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("ProvisionAgreementBean Built " + this.getUrn());
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
            ProvisionAgreementBean that = (ProvisionAgreementBean) bean;
            if (!super.equivalent(structureUseage, that.getStructureUseage())) {
                return false;
            }
            if (!super.equivalent(dataproviderRef, that.getDataproviderRef())) {
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
        if (dataproviderRef == null) {
            throw new SdmxSemmanticException("Provision Agreement missing reference to a data provider");
        }
        if (!dataproviderRef.getMaintainableReference().getVersion().equals(DataProviderSchemeBean.FIXED_VERSION)) {
            throw new SdmxSemmanticException("Version 2.0 Data Provider Scheme is no longer supported.  Data Provider Scheme has a fixed version of 1.0 in SDMX 2.1");
        }
        if (structureUseage == null) {
            throw new SdmxSemmanticException("Provision Agreement missing reference to a data/metadata flows");
        }
        super.validateAgencyId();
        super.validateIdentifiableAttributes();
        super.validateNameableAttributes();
    }

    @Override
    protected void validateAgencyId() {
        //Do nothing yet, not yet fully built
    }

    @Override
    protected void validateNameableAttributes() throws SdmxSemmanticException {
        //Do nothing yet, not yet fully built
    }

    @Override
    protected void validateIdentifiableAttributes() throws SdmxSemmanticException {
        //Do nothing yet, not yet fully built
    }


    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public CrossReferenceBean getStructureUseage() {
        return structureUseage;
    }

    @Override
    protected Set<CrossReferenceBean> getCrossReferenceInternal() {
        Set<CrossReferenceBean> references = new HashSet<CrossReferenceBean>();
        if (dataproviderRef != null) {
            references.add(dataproviderRef);
        }
        if (structureUseage != null) {
            references.add(structureUseage);
        }
        return references;
    }

    @Override
    public List<CrossReferenceBean> getCrossReferencedConstrainables() {
        List<CrossReferenceBean> returnList = new ArrayList<CrossReferenceBean>();
        returnList.add(getStructureUseage());
        returnList.add(getDataproviderRef());
        return returnList;
    }

    @Override
    public CrossReferenceBean getDataproviderRef() {
        return dataproviderRef;
    }

    @Override
    public ProvisionAgreementMutableBean getMutableInstance() {
        return new ProvisionAgreementMutableBeanImpl(this);
    }

    @Override
    public ProvisionAgreementBean getStub(URL actualLocation, boolean isServiceUrl) {
        if (actualLocation == null) {
            return this;
        }
        ProvisionAgreementMutableBean mutable = this.getMutableInstance();
        mutable.setExternalReference(TERTIARY_BOOL.TRUE);
        if (isServiceUrl) {
            mutable.setServiceURL(actualLocation.toString());
        } else {
            mutable.setStructureURL(actualLocation.toString());
        }
        return mutable.getImmutableInstance();
    }
}
