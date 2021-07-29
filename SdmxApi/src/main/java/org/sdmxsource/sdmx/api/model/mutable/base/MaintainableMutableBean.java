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
package org.sdmxsource.sdmx.api.model.mutable.base;

import org.sdmxsource.sdmx.api.constants.TERTIARY_BOOL;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;

import java.util.Date;


/**
 * The interface Maintainable mutable bean.
 */
public interface MaintainableMutableBean extends NameableMutableBean {

    /**
     * Gets agency id.
     *
     * @return the agency id
     */
    String getAgencyId();

    /**
     * Sets agency id.
     *
     * @param agencyId the agency id
     */
    void setAgencyId(String agencyId);

    /**
     * Gets version.
     *
     * @return the version
     */
    String getVersion();

    /**
     * Sets version.
     *
     * @param version the version
     */
    void setVersion(String version);

    /**
     * Gets start date.
     *
     * @return the start date
     */
    Date getStartDate();

    /**
     * Sets start date.
     *
     * @param startDate the start date
     */
    void setStartDate(Date startDate);

    /**
     * Gets end date.
     *
     * @return the end date
     */
    Date getEndDate();

    /**
     * Sets end date.
     *
     * @param endDate the end date
     */
    void setEndDate(Date endDate);

    /**
     * Gets final structure.
     *
     * @return the final structure
     */
    TERTIARY_BOOL getFinalStructure();

    /**
     * Sets final structure.
     *
     * @param isFinal the is final
     */
    void setFinalStructure(TERTIARY_BOOL isFinal);

    /**
     * Gets external reference.
     *
     * @return the external reference
     */
    TERTIARY_BOOL getExternalReference();

    /**
     * Sets external reference.
     *
     * @param isExternalReference the is external reference
     */
    void setExternalReference(TERTIARY_BOOL isExternalReference);

    /**
     * Is stub boolean.
     *
     * @return the boolean
     */
    boolean isStub();

    /**
     * Sets stub.
     *
     * @param isStub the is stub
     */
    void setStub(boolean isStub);

    /**
     * Gets service url.
     *
     * @return the service url
     */
    String getServiceURL();

    /**
     * Sets service url.
     *
     * @param serviceURL the service url
     */
    void setServiceURL(String serviceURL);

    /**
     * Gets structure url.
     *
     * @return the structure url
     */
    String getStructureURL();

    /**
     * Sets structure url.
     *
     * @param structureURL the structure url
     */
    void setStructureURL(String structureURL);

    /**
     * Returns a representation of itself in a bean which can not be modified, modifications to the mutable bean
     * after this point will not be reflected in the returned instance of the MaintainableBean.
     *
     * @return immutable instance
     */
    MaintainableBean getImmutableInstance();

}
