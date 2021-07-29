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
package org.sdmxsource.sdmx.api.model.beans.registry;

import org.sdmxsource.sdmx.api.constants.TERTIARY_BOOL;
import org.sdmxsource.sdmx.api.model.base.SdmxDate;
import org.sdmxsource.sdmx.api.model.beans.base.ConstrainableBean;
import org.sdmxsource.sdmx.api.model.beans.base.DataSourceBean;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.RegistrationMutableBean;

/**
 * The interface Registration bean.
 */
public interface RegistrationBean extends MaintainableBean, ConstrainableBean {

    /**
     * Returns true if one of the index getters is set to true
     *
     * @return true if one of the index getters is set to true
     */
    boolean isIndexed();

    /**
     * The indexTimeSeries, if true, indicates that the registry must index all time series for the registered data.
     * <p>
     * The index will create a Keyset Constraint
     * <p>
     * The default value is false, and the attribute will always be assumed false when the provision agreement references a metadata flow.
     *
     * @return index time series
     */
    TERTIARY_BOOL getIndexTimeSeries();

    /**
     * The indexDataSet, if true, indicates that the registry must index the range of actual (present)
     * values for each dimension of the data set or identifier component of the metadata set (as indicated in the set's structure definition).
     * <p>
     * The index will create a Cube Region Constraint
     * <p>
     * The default value is false.
     *
     * @return index dataset
     */
    TERTIARY_BOOL getIndexDataset();

    /**
     * The indexAttributes, if true, indicates that the registry must index the range of actual (present) values
     * for each attribute of the data set or the presence of the metadata attributes of the metadata set (as indicated in the set's structure definition).
     * <p>
     * The default value is false.
     *
     * @return index attributes
     */
    TERTIARY_BOOL getIndexAttributes();

    /**
     * The indexReportingPeriod, if true, indicates that the registry must index the time period ranges for which data is present for the data source.
     * <p>
     * The default value is false, and the attribute will always be assumed false when the provision agreement references a metadata flow.
     *
     * @return index reporting period
     */
    TERTIARY_BOOL getIndexReportingPeriod();

    /**
     * Returns when the registration was last updated
     *
     * @return last updated
     */
    SdmxDate getLastUpdated();

    /**
     * Gets valid from.
     *
     * @return the valid from
     */
    SdmxDate getValidFrom();

    /**
     * Gets valid to.
     *
     * @return the valid to
     */
    SdmxDate getValidTo();

    /**
     * Gets data source.
     *
     * @return the data source
     */
    DataSourceBean getDataSource();

    /**
     * Gets provision agreement ref.
     *
     * @return the provision agreement ref
     */
    CrossReferenceBean getProvisionAgreementRef();

    /**
     * Returns a representation of itself in a bean which can be modified, modifications to the mutable bean
     * are not reflected in the instance of the RegistrationMutableBean.
     *
     * @return the mutable instance
     */
    @Override
    RegistrationMutableBean getMutableInstance();

}
