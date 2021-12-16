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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sdmx.resources.sdmxml.schemas.v20.registry.DatasourceType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.ProvisionAgreementRefType;
import org.sdmx.resources.sdmxml.schemas.v20.registry.RegistrationType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TERTIARY_BOOL;
import org.sdmxsource.sdmx.api.constants.TIME_FORMAT;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.base.SdmxDate;
import org.sdmxsource.sdmx.api.model.beans.base.DataSourceBean;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.RegistrationBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.RegistrationMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.DataSourceBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.MaintainableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.SdmxDateImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.registry.RegistrationMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.util.RefUtil;
import org.sdmxsource.sdmx.util.beans.reference.CrossReferenceBeanImpl;
import org.sdmxsource.util.ObjectUtil;
import org.sdmxsource.util.random.RandomUtil;

import java.net.URL;
import java.util.*;


/**
 * The type Registration bean.
 */
public class RegistrationBeanImpl extends MaintainableBeanImpl implements RegistrationBean {
    private static final long serialVersionUID = 1L;
    private static Logger LOG = LogManager.getLogger(RegistrationBeanImpl.class);
    private SdmxDate lastUpdated = new SdmxDateImpl(new Date(), TIME_FORMAT.DATE_TIME);
    private SdmxDate validFrom;
    private SdmxDate validTo;
    private DataSourceBean dataSource;
    private CrossReferenceBean provisionAgreementRef;

    private TERTIARY_BOOL indexTimeSeries = TERTIARY_BOOL.UNSET;
    private TERTIARY_BOOL indexDataset = TERTIARY_BOOL.UNSET;
    private TERTIARY_BOOL indexAttributes = TERTIARY_BOOL.UNSET;
    private TERTIARY_BOOL indexReportingPeriod = TERTIARY_BOOL.UNSET;

    /**
     * Instantiates a new Registration bean.
     *
     * @param registrationMutable the registration mutable
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public RegistrationBeanImpl(RegistrationMutableBean registrationMutable) {
        super(registrationMutable);
        LOG.debug("Building RegistrationBean from Mutable Bean");
        if (registrationMutable.getLastUpdated() != null) {
            lastUpdated = new SdmxDateImpl(registrationMutable.getLastUpdated(), TIME_FORMAT.DATE_TIME);
        }
        if (registrationMutable.getValidFrom() != null) {
            validFrom = new SdmxDateImpl(registrationMutable.getValidFrom(), TIME_FORMAT.DATE_TIME);
        }
        if (registrationMutable.getValidTo() != null) {
            validTo = new SdmxDateImpl(registrationMutable.getValidTo(), TIME_FORMAT.DATE_TIME);
        }
        if (registrationMutable.getProvisionAgreementRef() != null) {
            this.provisionAgreementRef = new CrossReferenceBeanImpl(this, registrationMutable.getProvisionAgreementRef());
        }
        if (registrationMutable.getIndexAttributes() != null) {
            this.indexAttributes = registrationMutable.getIndexAttributes();
        }
        if (registrationMutable.getIndexDataset() != null) {
            this.indexDataset = registrationMutable.getIndexDataset();
        }
        if (registrationMutable.getIndexReportingPeriod() != null) {
            this.indexReportingPeriod = registrationMutable.getIndexReportingPeriod();
        }
        if (registrationMutable.getIndexTimeSeries() != null) {
            this.indexTimeSeries = registrationMutable.getIndexTimeSeries();
        }
        if (registrationMutable.getDataSource() != null) {
            this.dataSource = new DataSourceBeanImpl(registrationMutable.getDataSource(), this);
        }
        validate();
        if (LOG.isDebugEnabled()) {
            LOG.debug("RegistrationBean Built " + this.getUrn());
        }
    }


    /**
     * Instantiates a new Registration bean.
     *
     * @param registrationType the registration type
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public RegistrationBeanImpl(org.sdmx.resources.sdmxml.schemas.v21.registry.RegistrationType registrationType) {
        super(null, SDMX_STRUCTURE_TYPE.REGISTRATION, null, null, null, TERTIARY_BOOL.FALSE, "NA", registrationType.getId(), null, null, null, TERTIARY_BOOL.FALSE, null);
        LOG.debug("Building RegistrationBean from 2.1 SDMX");
        if (registrationType.getValidFrom() != null) {
            this.validFrom = new SdmxDateImpl(registrationType.getValidFrom().getTime(), TIME_FORMAT.DATE_TIME);
        }
        if (registrationType.getLastUpdated() != null) {
            this.lastUpdated = new SdmxDateImpl(registrationType.getLastUpdated().getTime(), TIME_FORMAT.DATE_TIME);
        }
        if (registrationType.getValidTo() != null) {
            this.validTo = new SdmxDateImpl(registrationType.getValidTo().getTime(), TIME_FORMAT.DATE_TIME);
        }

        if (registrationType.getDatasource() != null) {
            if (ObjectUtil.validCollection(registrationType.getDatasource().getQueryableDataSourceList())) {
                this.dataSource = new DataSourceBeanImpl(registrationType.getDatasource().getQueryableDataSourceList().get(0), this);
            } else if (ObjectUtil.validCollection(registrationType.getDatasource().getSimpleDataSourceList())) {
                this.dataSource = new DataSourceBeanImpl(registrationType.getDatasource().getSimpleDataSourceList().get(0), this);
            }

        }
        if (registrationType.getProvisionAgreement() != null) {
            provisionAgreementRef = RefUtil.createReference(this, registrationType.getProvisionAgreement());
        }
        if (registrationType.isSetIndexAttributes()) {
            this.indexAttributes = TERTIARY_BOOL.parseBoolean(registrationType.getIndexAttributes());
        }
        if (registrationType.isSetIndexDataSet()) {
            this.indexDataset = TERTIARY_BOOL.parseBoolean(registrationType.getIndexDataSet());
        }
        if (registrationType.isSetIndexReportingPeriod()) {
            this.indexReportingPeriod = TERTIARY_BOOL.parseBoolean(registrationType.getIndexReportingPeriod());
        }
        if (registrationType.isSetIndexTimeSeries()) {
            this.indexTimeSeries = TERTIARY_BOOL.parseBoolean(registrationType.getIndexTimeSeries());
        }
        validate();
        if (LOG.isDebugEnabled()) {
            LOG.debug("RegistrationBean Built " + this.getUrn());
        }
    }


    /**
     * Instantiates a new Registration bean.
     *
     * @param registrationType the registration type
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public RegistrationBeanImpl(RegistrationType registrationType) {
        super(null, SDMX_STRUCTURE_TYPE.REGISTRATION, null, null, null, TERTIARY_BOOL.FALSE, "NA", null, null, null, null, TERTIARY_BOOL.FALSE, null);
        LOG.debug("Building RegistrationBean from 2.0 SDMX");
        if (registrationType.getValidFrom() != null) {
            this.validFrom = new SdmxDateImpl(registrationType.getValidFrom().getTime(), TIME_FORMAT.DATE_TIME);
        }
        if (registrationType.getLastUpdated() != null) {
            this.lastUpdated = new SdmxDateImpl(registrationType.getLastUpdated().getTime(), TIME_FORMAT.DATE_TIME);
        }
        if (registrationType.getValidTo() != null) {
            this.validTo = new SdmxDateImpl(registrationType.getValidTo().getTime(), TIME_FORMAT.DATE_TIME);
        }
        if (registrationType.getDatasource() != null) {
            this.dataSource = new DataSourceBeanImpl(registrationType.getDatasource(), this);
        }
        if (registrationType.getProvisionAgreementRef() != null) {
            ProvisionAgreementRefType provRef = registrationType.getProvisionAgreementRef();
            if (ObjectUtil.validString(provRef.getURN())) {
                provisionAgreementRef = new CrossReferenceBeanImpl(this, registrationType.getProvisionAgreementRef().getURN());
            } else if (ObjectUtil.validOneString(provRef.getDataflowID(), provRef.getOrganisationSchemeID())) {
                throw new SdmxNotImplementedException("Registrations submitted in version 2.0 of SDMX must reference a " +
                        "Provision agreement by URN.  Note : The 2.1 URN syntax for provision agreements must be used; the 2.0 URN syntax for provision agreements is not longer supported");
            }
        }
        validate();
        if (LOG.isDebugEnabled()) {
            LOG.debug("RegistrationBean Built " + this.getUrn());
        }
    }

    /**
     * Instantiates a new Registration bean.
     *
     * @param provRef        the prov ref
     * @param datasourceType the datasource type
     */
    public RegistrationBeanImpl(ProvisionAgreementRefType provRef, DatasourceType datasourceType) {
        super(null, SDMX_STRUCTURE_TYPE.REGISTRATION, null, null, null, TERTIARY_BOOL.FALSE, "NA", null, null, null, null, TERTIARY_BOOL.FALSE, null);
        LOG.debug("Building RegistrationBean from Provision and Datasource");
        if (datasourceType != null) {
            this.dataSource = new DataSourceBeanImpl(datasourceType, this);
        }
        if (provRef != null) {
            provisionAgreementRef = new CrossReferenceBeanImpl(this, provRef.getURN());
        }
        validate();
        if (LOG.isDebugEnabled()) {
            LOG.debug("RegistrationBean Built " + this.getUrn());
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATE				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() throws SdmxSemmanticException {
        if (provisionAgreementRef == null) {
            throw new SdmxSemmanticException("Registration must reference a provision agreement");
        }
        if (provisionAgreementRef.getTargetReference() != SDMX_STRUCTURE_TYPE.PROVISION_AGREEMENT) {
            throw new SdmxSemmanticException("Registration must reference a provision agreement, actual referenced structre supplied was " + provisionAgreementRef.getTargetReference().getType());
        }
        if (dataSource == null) {
            throw new SdmxSemmanticException("Registration must have a datasource");
        }
        this.agencyId = provisionAgreementRef.getMaintainableReference().getAgencyId();
        if (!ObjectUtil.validString(getId())) {
            //If an Id was not provided then generate one
            setId(generateId());
        }
        if (this.lastUpdated == null) {
            this.lastUpdated = new SdmxDateImpl(new Date(), TIME_FORMAT.DATE_TIME);
        }
        super.validateId(true);
        super.validateAgencyId();
    }


    @Override
    protected void validateAgencyId() {
        //Do nothing yet, not yet fully built
    }

    @Override
    protected void validateId(boolean startWithIntAllowed) {
        //Do nothing yet, not yet fully built
    }

    @Override
    public boolean deepEquals(SDMXBean bean, boolean includeFinalProperties) {
        throw new SdmxNotImplementedException("deepEquals on registration");
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private String generateId() {
        return RandomUtil.generateRandomString();
    }

    @Override
    protected Set<CrossReferenceBean> getCrossReferenceInternal() {
        Set<CrossReferenceBean> references = new HashSet<CrossReferenceBean>();
        if (provisionAgreementRef != null) {
            references.add(provisionAgreementRef);
        }
        return references;
    }


    @Override
    public boolean isIndexed() {
        return indexAttributes.isTrue() || indexDataset.isTrue() || indexReportingPeriod.isTrue() || indexTimeSeries.isTrue();
    }

    @Override
    public MaintainableBean getStub(URL actualLocation, boolean isServiceUrl) {
        return this;
    }

    @Override
    public RegistrationMutableBean getMutableInstance() {
        return new RegistrationMutableBeanImpl(this);
    }

    @Override
    public TERTIARY_BOOL getIndexTimeSeries() {
        return indexTimeSeries;
    }

    @Override
    public TERTIARY_BOOL getIndexDataset() {
        return indexDataset;
    }

    @Override
    public TERTIARY_BOOL getIndexAttributes() {
        return indexAttributes;
    }

    @Override
    public TERTIARY_BOOL getIndexReportingPeriod() {
        return indexReportingPeriod;
    }

    @Override
    public DataSourceBean getDataSource() {
        return dataSource;
    }

    @Override
    public SdmxDate getLastUpdated() {
        return lastUpdated;
    }

    @Override
    public List<CrossReferenceBean> getCrossReferencedConstrainables() {
        List<CrossReferenceBean> returnList = new ArrayList<CrossReferenceBean>();
        returnList.add(getProvisionAgreementRef());
        return returnList;
    }

    @Override
    public CrossReferenceBean getProvisionAgreementRef() {
        return provisionAgreementRef;
    }

    @Override
    public SdmxDate getValidFrom() {
        return validFrom;
    }

    @Override
    public SdmxDate getValidTo() {
        return validTo;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES		                     //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(dataSource, composites);
        return composites;
    }
}
