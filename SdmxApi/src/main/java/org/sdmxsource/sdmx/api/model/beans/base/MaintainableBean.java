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
package org.sdmxsource.sdmx.api.model.beans.base;

import org.sdmxsource.sdmx.api.constants.TERTIARY_BOOL;
import org.sdmxsource.sdmx.api.model.base.SdmxDate;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.base.AnnotationMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.MaintainableMutableBean;

import java.net.URL;
import java.util.Map;
import java.util.Set;


/**
 * A MaintainableBean is a top level bean (contains no parents), it has a reference to an Agency <code>getAgencyId()</code>
 * and has a <b>mandatory id</b> and a <b>mandatory version</b>, defaulting to 1.0.
 * <p>
 * The unique identifier of a maintainable artefact is the AgencyId, Id and Version.
 * <p>
 * Each maintainable artefact can create a mutable representation of itself (<code>getMutableInstance()</code>)
 * and can also return a stub representation of itself <code>getStub()</code>.
 *
 * @author Matt Nelson
 */
public interface MaintainableBean extends NameableBean, Comparable<MaintainableBean> {
    /**
     * The constant DEFAULT_VERSION.
     */
    static String DEFAULT_VERSION = "1.0";

    /**
     * Returns the agency id that is responsible for maintaining this maintainable artifact
     *
     * @return agency id
     */
    String getAgencyId();

    /**
     * Returns the version of this maintainable artifact, default version is 1.0
     * <p>
     * Version is a integer value with period '.' separators between integers, for example 1.2.3.19
     *
     * @return version version
     */
    String getVersion();

    /**
     * Returns the start date of this maintainable artifact, returns null if there is no startDate
     * <p>
     *
     * @return start date
     */
    SdmxDate getStartDate();

    /**
     * Returns the end date of this maintainable artifact, returns null if there is no endDate
     * <p>
     *
     * @return end date
     */
    SdmxDate getEndDate();

    /**
     * Returns TERTIARY_BOOL.TRUE if the structure is marked as final, meaning the structure can not be modified
     *
     * @return tertiary bool
     */
    TERTIARY_BOOL isFinal();

    /**
     * Returns TERTIARY_BOOL.TRUE if this maintainable artifact is externally referenced
     *
     * @return tertiary bool
     */
    TERTIARY_BOOL isExternalReference();

    /**
     * Returns a stub reference of itself.
     * <p>
     * A stub bean only contains Maintainable and Identifiable Attributes, not the composite Objects that are
     * contained within the Maintainable
     *
     * @param actualLocation the URL indicating where the full structure can be returned from
     * @param isServiceUrl   if true this URL will be present on the serviceURL attribute, otherwise it will be treated as a structureURL attribute
     * @return stub stub
     */
    MaintainableBean getStub(URL actualLocation, boolean isServiceUrl);

    /**
     * Adds a annotations to a copy of this bean, and returns the copy
     *
     * @param annotations the annotations
     * @return maintainable bean
     */
    MaintainableBean addAnnotations(Map<StructureReferenceBean, Set<AnnotationMutableBean>> annotations);

    /**
     * Returns a representation of itself in a bean which can be modified, modifications to the mutable bean
     * are not reflected in the instance of the MaintainableBean.
     *
     * @return mutable instance
     */
    MaintainableMutableBean getMutableInstance();

    /**
     * The serviceURL attribute indicates the URL of an SDMX SOAP web service from which the details of the object can be retrieved.
     * Note that this can be a registry or and SDMX structural metadata repository,
     * as they both implement that same web service interface.
     * Optional.
     *
     * @return service url
     * @since v2.1 SDMX Schema
     */
    URL getServiceURL();

    /**
     * The structureURL attribute indicates the URL of a SDMX-ML structure message
     * (in the same version as the source document) in which the externally referenced object is contained.
     * Note that this my be a URL of an SDMX RESTful web service which will return the referenced object.
     * Optional.
     *
     * @return structure url
     * @since v2.1 SDMX Schema
     */
    URL getStructureURL();
}
