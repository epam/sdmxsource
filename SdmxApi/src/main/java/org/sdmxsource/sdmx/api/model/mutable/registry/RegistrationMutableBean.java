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
package org.sdmxsource.sdmx.api.model.mutable.registry;

import org.sdmxsource.sdmx.api.constants.TERTIARY_BOOL;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.RegistrationBean;
import org.sdmxsource.sdmx.api.model.mutable.base.DataSourceMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.MaintainableMutableBean;

import java.util.Date;


/**
 * The interface Registration mutable bean.
 */
public interface RegistrationMutableBean extends MaintainableMutableBean {

    /**
     * Gets index time series.
     *
     * @return the index time series
     */
    TERTIARY_BOOL getIndexTimeSeries();

    /**
     * Sets index time series.
     *
     * @param indexTimeSeries the index time series
     */
    void setIndexTimeSeries(TERTIARY_BOOL indexTimeSeries);

    /**
     * Gets index dataset.
     *
     * @return the index dataset
     */
    TERTIARY_BOOL getIndexDataset();

    /**
     * Sets index dataset.
     *
     * @param indexDataset the index dataset
     */
    void setIndexDataset(TERTIARY_BOOL indexDataset);

    /**
     * Gets index attributes.
     *
     * @return the index attributes
     */
    TERTIARY_BOOL getIndexAttributes();

    /**
     * Sets index attributes.
     *
     * @param indexAttributes the index attributes
     */
    void setIndexAttributes(TERTIARY_BOOL indexAttributes);

    /**
     * Gets index reporting period.
     *
     * @return the index reporting period
     */
    TERTIARY_BOOL getIndexReportingPeriod();

    /**
     * Sets index reporting period.
     *
     * @param indexReportingPeriod the index reporting period
     */
    void setIndexReportingPeriod(TERTIARY_BOOL indexReportingPeriod);

    /**
     * Gets last updated.
     *
     * @return the last updated
     */
    Date getLastUpdated();

    /**
     * Sets last updated.
     *
     * @param lastUpdated the last updated
     */
    void setLastUpdated(Date lastUpdated);

    /**
     * Gets valid from.
     *
     * @return the valid from
     */
    Date getValidFrom();

    /**
     * Sets valid from.
     *
     * @param validFrom the valid from
     */
    void setValidFrom(Date validFrom);

    /**
     * Gets valid to.
     *
     * @return the valid to
     */
    Date getValidTo();

    /**
     * Sets valid to.
     *
     * @param validTo the valid to
     */
    void setValidTo(Date validTo);

    /**
     * Gets data source.
     *
     * @return the data source
     */
    DataSourceMutableBean getDataSource();

    /**
     * Sets data source.
     *
     * @param dataSource the data source
     */
    void setDataSource(DataSourceMutableBean dataSource);

    /**
     * Gets provision agreement ref.
     *
     * @return the provision agreement ref
     */
    StructureReferenceBean getProvisionAgreementRef();

    /**
     * Sets provision agreement ref.
     *
     * @param provisionAgreementRef the provision agreement ref
     */
    void setProvisionAgreementRef(StructureReferenceBean provisionAgreementRef);


    /**
     * Returns a representation of itself in a bean which can not be modified, modifications to the mutable bean
     * are not reflected in the returned instance of the RegistrationBean.
     *
     * @return the immutable instance
     */
    @Override
    RegistrationBean getImmutableInstance();


}
